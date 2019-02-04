/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.compiler;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tirion.common.file.FileUtil;
import com.tirion.compiler.Result.Status;
import com.tirion.compiler.registry.LocalInMemoryRegistry;
import com.tirion.compiler.registry.Registry;
import com.tirion.compiler.registry.classloader.DbClassLoader;
import com.tirion.compiler.registry.repository.Entry;
import com.tirion.compiler.source.Source;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public final class JdkCompiler implements Compiler {
	
	private static final Logger log = LoggerFactory.getLogger(JdkCompiler.class);
	
	private final Charset charset;

	private File inputDir;
	private File outputDir;
	private boolean cleanOnStart;
	
	private JavaCompiler compiler;
	private StandardJavaFileManager fileManager;
	private SimpleDiagnosticListener listener;
	
	private Set<String> loadedClasses; // list of file names that we already loaded...
	private DbClassLoader classLoader;
	private Registry registry;
	
	public JdkCompiler() {
		super();
		try {
			charset = Charset.forName("UTF-8");
		} catch (Exception e) {
			throw new IllegalStateException("Exception while trying to load UTF-8 Charset from JVM. Does your operating system have it installed? Following Java code failed: 'Charset.forName(\"UTF-8\")'");
		}
	}

	public void setInputDir(String inputDir) {
		this.inputDir = new File(inputDir);
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = new File(outputDir);
	}
	
	public void setCleanOnStart(boolean cleanOnStart) {
		this.cleanOnStart = cleanOnStart;
	}

	@Override
	public void init() {
		if(cleanOnStart) {
			clean();
		}
		loadedClasses = new HashSet<String>();
		compiler = ToolProvider.getSystemJavaCompiler();
		if(compiler == null) {
			throw new IllegalStateException("Unable to load native compiler from JVM. Do you have JDK on system path (javac compiler)?");
		}
		listener = new SimpleDiagnosticListener();
		fileManager = compiler.getStandardFileManager(listener, Locale.US, charset);
		
		registry = new LocalInMemoryRegistry();
		
		log.info("Injecting custom class loader");
		classLoader = new DbClassLoader(registry);
		Thread.currentThread().setContextClassLoader(classLoader);
	}

	@Override
	public void shutdown() {
	}

	@Override
	public synchronized Result compile(Source source) {
		File file = writeSourceToFile(source);
		boolean compileOk = compileSourceFile(file);
		Entry entry = null;
		if(compileOk) {		
			entry = loadEntry(source.getSimpleClassName());
			registry.register(entry);
			entry.setClazz(loadClassFromClassLoader(entry.getFullClassName()));
		}
		String messages = listener.getMessages();
		listener.reset();
		return new CompilationResult(compileOk ? Status.OK : Status.NOT_OK, 
								     messages, 
								     entry != null ? entry.getClazz() : null);
	}
	
	/**
	 * TODO FIXME abstraction leaks, dependency cycle...
	 */
	private Class<?> loadClassFromClassLoader(String className) {
		try {
			return classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private File writeSourceToFile(Source source) {
		long start = System.currentTimeMillis();
		File dir = new File(inputDir, source.getNormalizedPackageName());
		dir.mkdirs();
		File file = new File(dir, source.getSimpleClassName() + ".java");
		FileUtil.writeTextFileFully(file, source.getSource());
		long duration = System.currentTimeMillis() - start;
		if(log.isTraceEnabled()) {			
			log.trace("Wrote source to '" + file.getAbsolutePath() + "' files in " + duration + " millis");
		}
		return file;
	}
	
	/**
	 * True means everything went ok.
	 */
	private boolean compileSourceFile(File file) {
		List<File> files = new ArrayList<File>();
		files.add(file);
		try {
			long start = System.currentTimeMillis();
			
			listener.reset();
			
			fileManager.setLocation(StandardLocation.SOURCE_PATH, Arrays.asList(inputDir));
			fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(outputDir));
			
			Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(files);
			CompilationTask task = compiler.getTask(null, fileManager, listener, null, null, compilationUnits);
			boolean ok = task.call();
			
			long duration = System.currentTimeMillis() - start;
			if(log.isTraceEnabled()) {
				log.trace("Compilation of " + files.size() + " files took " + duration + " millis"); 				
			}
			return ok;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Entry loadEntry(String simpleClassName) {
		try {
			Set<JavaFileObject.Kind> set = new HashSet<JavaFileObject.Kind>();
			set.add(Kind.CLASS);
			Iterator<JavaFileObject> it = fileManager.list(StandardLocation.CLASS_OUTPUT, "com.tirion.db.generated", set, true).iterator();
			while(it.hasNext()) {
				JavaFileObject object = it.next();
				if(loadedClasses.add(object.getName())) {	
					File file = new File(object.getName());
					byte[] bytecode = FileUtil.readBinaryFileFully(file);
					String cName = file.getName().substring(0, file.getName().indexOf(".class"));
					if(cName.equals(simpleClassName)) {						
						Entry entry = new Entry(Source.PACKAGE_NAME + "." + simpleClassName);
						entry.setBytecode(bytecode);
						return entry;
					}
				}
			}
			throw new IllegalStateException("Unable to find entry for class '" + simpleClassName + "'");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static final class CompilationResult implements Result {

		private final Status status;
		private final String messages;
		private final Class<?> clazz;
		
		private CompilationResult(Status status, String messages, Class<?> clazz) {
			this.status = status;
			this.messages = messages;
			this.clazz = clazz;
		}
		@Override
		public Status getStatus() {
			return status;
		}

		@Override
		public String getMessages() {
			return messages;
		}
		
		@Override
		public Class<?> getClazz() {
			return clazz;
		}	
	}
	
	private void clean() {
		File[] files = inputDir.listFiles(new InternalFileFilter());
		for(File file : files) {
			file.delete();
		}
		log.info("Tried to delete compiler input files '" + Arrays.asList(files) + "'");
		
		files = outputDir.listFiles(new InternalFileFilter());
		for(File file : files) {
			file.delete();
		}
		log.info("Tried to delete compiler output files '" + Arrays.asList(files) + "'");
	}
	
	private static final class InternalFileFilter implements FileFilter {
		@Override
		public boolean accept(File file) {
			return file.isFile() && !file.isHidden();
		}	
	}
}

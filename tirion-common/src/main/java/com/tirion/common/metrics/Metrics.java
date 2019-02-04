/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.metrics;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.CsvReporter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jvm.BufferPoolMetricSet;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;

public abstract class Metrics {

	private static final Logger log = LoggerFactory.getLogger(Metrics.class);
	
	// for now a singleton hack...
	
	private static final MetricRegistry REGISTRY = new MetricRegistry();
	static {
		registerJvmMetrics();
		JmxReporter reporter = JmxReporter.forRegistry(REGISTRY).build();
		reporter.start();
	}
	
	private static void registerJvmMetrics() {
		GarbageCollectorMetricSet gc = new GarbageCollectorMetricSet();
		MemoryUsageGaugeSet memory = new MemoryUsageGaugeSet();
		BufferPoolMetricSet pool = new BufferPoolMetricSet(ManagementFactory.getPlatformMBeanServer());
		
		REGISTRY.registerAll(gc);
		REGISTRY.registerAll(memory);
		REGISTRY.registerAll(pool);
		
	}
	
	public static void startLoggingToCsv(File directory, int frequency) {
		log.info("Starting CSV metrics report in directory '" 
				+ directory.getAbsolutePath() + "' with frequency " + frequency + " second(s)");
		CsvReporter csvReporter = CsvReporter.forRegistry(REGISTRY)
				.formatFor(Locale.US)
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.build(directory);
		csvReporter.start(frequency, TimeUnit.SECONDS);
	}
	
	public static MetricRegistry get() {
		return REGISTRY;
	}
}

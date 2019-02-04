/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.common.runtime;

import com.tirion.common.Configuration;
import com.tirion.compressor.Compressor;
import com.tirion.executor.Executor;
import com.tirion.executor.job.JobFactory;
import com.tirion.pool.Pool;

/**
 * Pass this around when submodules need access to
 * some global services.
 * 
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface Runtime {
	
	Configuration configuration();
	
	Compressor compressor();
	
	Executor executor();
	
	JobFactory jobFactory();
	
	Pool pool();
}

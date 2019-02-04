/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
//package com.tirion.db.common.runtime;
//
//import com.tirion.db.engine.client.reqres.req.Request;
//import com.tirion.executor.Task;
//import com.tirion.profiler.statistics.Statistics;
//
///**
// * Internal representation of {@link Request}. Request ID
// * and job ID are same.<p>
// * 
// * Each task backend does is associated with certain job. 
// * Multiple {@link Task}s may be executed under the same job 
// * context.
// * 
// * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
// */
//public interface Job {
//	
//	void register(Statistics stats);
//
//	long getId();
//	
//	long nextTaskId();
//	
//	Runtime getRuntime();
//}

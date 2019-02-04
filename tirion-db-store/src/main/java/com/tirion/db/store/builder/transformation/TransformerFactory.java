/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.builder.transformation;

import com.tirion.db.catalog.model.Entity;
import com.tirion.db.common.Config;
import com.tirion.db.store.builder.transformation.context.TransformationContext;
import com.tirion.executor.job.Job;

/**
 * @author Veljko Zivkovic, Copyright (C) All Rights Reserved
 */
public interface TransformerFactory {

	TransformationContext newContext(Entity entity, Config config, Job job);
}

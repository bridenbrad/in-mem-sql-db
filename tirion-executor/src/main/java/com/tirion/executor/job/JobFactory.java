/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.executor.job;

import com.tirion.common.Lifecycle;

public interface JobFactory extends Lifecycle {

	Job newJob();
}

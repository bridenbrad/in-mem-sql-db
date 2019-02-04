/**
 * Copyright © 2013/2014, Veljko Zivkovic
 * All rights reserved.
 *
 * No portion of this file may be reproduced in any form, or by any means, without the prior written
 * consent of the author.
 */
package com.tirion.db.store.page.source;

public interface SplittablePageSource extends PageSource {

	/**
	 * Split count is a hint, not an obligation.
	 */
	PageSource split(int splitCount);
}

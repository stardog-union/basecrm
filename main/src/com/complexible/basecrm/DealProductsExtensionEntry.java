/*
 * Copyright (c) 2015 Complexible Inc. <http://complexible.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.complexible.basecrm;

import com.google.common.base.Function;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.1
 */
public final class DealProductsExtensionEntry {
	public static final Function<DealProductsExtensionEntry, DealProductsExtension> UNWRAP = new Function<DealProductsExtensionEntry, DealProductsExtension>() {
		@Override
		public DealProductsExtension apply(final DealProductsExtensionEntry input) {
			return input.getDealProductsExtension();
		}
	};

	private DealProductsExtension mDealProductsExtension;

	public DealProductsExtension getDealProductsExtension() {
		return mDealProductsExtension;
	}

	public void setDealProductsExtension(final DealProductsExtension theDealProductsExtension) {
		mDealProductsExtension = theDealProductsExtension;
	}
}

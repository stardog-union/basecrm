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

import java.util.List;

import com.google.common.base.Objects;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.1
 */
public class DealProductsExtension {
	private String mDiscount;
	private String mId;
	private List<Product> mProducts;
	private boolean mSuccess;

	public String getDiscount() {
		return mDiscount;
	}

	public void setDiscount(final String theDiscount) {
		mDiscount = theDiscount;
	}

	public String getId() {
		return mId;
	}

	public void setId(final String theId) {
		mId = theId;
	}

	public List<Product> getProducts() {
		return mProducts;
	}

	public void setProducts(final List<Product> theProducts) {
		mProducts = theProducts;
	}

	public boolean isSuccess() {
		return mSuccess;
	}

	public void setSuccess(final boolean theSuccess) {
		mSuccess = theSuccess;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("DealProductsExtension(%s", mId);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(mId);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public boolean equals(final Object theObj) {
		if (theObj == this) {
			return true;
		}
		else if (theObj instanceof DealProductsExtension) {
			DealProductsExtension aProduct = (DealProductsExtension) theObj;

			return Objects.equal(mId, aProduct.mId);
		}
		else {
			return false;
		}
	}
}

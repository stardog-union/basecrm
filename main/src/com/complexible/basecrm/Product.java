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

import com.google.common.base.Objects;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.1
 */
public final class Product {
	private String mCost;
	private String mCurrency;
	private String mDealId;
	private String mDescription;

	private String mId;

	private int mMaxDiscount;
	private int mMaxMarkup;

	private String mName;

	private String mProductTemplateId;
	private String mProductTemplateVersion;

	private int mQuantity;

	private String mSKU;

	private String mValue;

	private String mVariation;

	public String getCost() {
		return mCost;
	}

	public void setCost(final String theCost) {
		mCost = theCost;
	}

	public String getCurrency() {
		return mCurrency;
	}

	public void setCurrency(final String theCurrency) {
		mCurrency = theCurrency;
	}

	public String getDealId() {
		return mDealId;
	}

	public void setDealId(final String theDealId) {
		mDealId = theDealId;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(final String theDescription) {
		mDescription = theDescription;
	}

	public String getId() {
		return mId;
	}

	public void setId(final String theId) {
		mId = theId;
	}

	public int getMaxDiscount() {
		return mMaxDiscount;
	}

	public void setMaxDiscount(final int theMaxDiscount) {
		mMaxDiscount = theMaxDiscount;
	}

	public int getMaxMarkup() {
		return mMaxMarkup;
	}

	public void setMaxMarkup(final int theMaxMarkup) {
		mMaxMarkup = theMaxMarkup;
	}

	public String getName() {
		return mName;
	}

	public void setName(final String theName) {
		mName = theName;
	}

	public String getProductTemplateId() {
		return mProductTemplateId;
	}

	public void setProductTemplateId(final String theProductTemplateId) {
		mProductTemplateId = theProductTemplateId;
	}

	public String getProductTemplateVersion() {
		return mProductTemplateVersion;
	}

	public void setProductTemplateVersion(final String theProductTemplateVersion) {
		mProductTemplateVersion = theProductTemplateVersion;
	}

	public int getQuantity() {
		return mQuantity;
	}

	public void setQuantity(final int theQuantity) {
		mQuantity = theQuantity;
	}

	public String getSKU() {
		return mSKU;
	}

	public void setSKU(final String theSKU) {
		mSKU = theSKU;
	}

	public String getValue() {
		return mValue;
	}

	public void setValue(final String theValue) {
		mValue = theValue;
	}

	public String getVariation() {
		return mVariation;
	}

	public void setVariation(final String theVariation) {
		mVariation = theVariation;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("Product(%s%s)", mName, mValue != null ? " -- "+mQuantity +"x @ $" + mValue : "");
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(mId, mName, mDealId, mSKU);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public boolean equals(final Object theObj) {
		if (theObj == this) {
			return true;
		}
		else if (theObj instanceof Product) {
			Product aProduct = (Product) theObj;

			return Objects.equal(mId, aProduct.mId)
				&& Objects.equal(mName, aProduct.mName)
				&& Objects.equal(mDealId, aProduct.mDealId)
				&& Objects.equal(mSKU, aProduct.mSKU);
		}
		else {
			return false;
		}
	}
}

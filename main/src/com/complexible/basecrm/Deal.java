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

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.1
 */
public class Deal extends BaseObject {

	private String mName;
	private String mStageName;
	private boolean mHot;
	private boolean mIsNew;
	private Contact mMainContact;
	private Contact mCompany;

	public Contact getCompany() {
		return mCompany;
	}

	public void setCompany(final Contact theCompany) {
		mCompany = theCompany;
	}

	public boolean isHot() {
		return mHot;
	}

	public void setHot(final boolean theHot) {
		mHot = theHot;
	}

	public boolean isNew() {
		return mIsNew;
	}

	public void setNew(final boolean theIsNew) {
		mIsNew = theIsNew;
	}

	public Contact getMainContact() {
		return mMainContact;
	}

	public void setMainContact(final Contact theMainContact) {
		mMainContact = theMainContact;
	}

	public String getName() {
		return mName;
	}

	public void setName(final String theName) {
		mName = theName;
	}

	public String getStageName() {
		return mStageName;
	}

	public void setStageName(final String theStageName) {
		mStageName = theStageName;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(mId, mName);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public boolean equals(final Object theObj) {
		if (theObj == this) {
			return true;
		}
		else if (theObj instanceof Deal) {
			Deal aDeal = (Deal) theObj;

			return Objects.equal(mName, aDeal.mName)
				&& Objects.equal(mId, aDeal.mId);
		}
		else {
			return false;
		}
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public String toString() {
		return MoreObjects.toStringHelper("Deal")
			.add("name", mName)
			.add("stage", mStageName)
			.add("company", mCompany)
			.toString();
	}
}

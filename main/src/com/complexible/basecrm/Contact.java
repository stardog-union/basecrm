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

import java.util.Date;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

import static com.google.common.collect.Iterators.filter;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.1
 */
public class Contact extends BaseObject {
	private String mName;
	private String mEmail;

	private Date mCreatedAt;
	private Date mUpdatedAt;

	private String mAddress;
	private String mCity;
	private String mCountry;
	private String mRegion;
	private String mZip;

	private String mDescription;

	private String mWebsite;
	private String mLinkedInDisplay;
	private String mFacebook;
	private String mLinkedIn;
	private String mTwitter;
	private String mSkype;
	private String mFax;
	private String mMobile;
	private String mPhone;
	private String mIndustry;

	private boolean mIsOrganization;
	private boolean mIsSalesAccount;
	private boolean mPrivate;

	private ContactEntry mOrganisation;
	private String mOrganisationName;

	private String mTagsJoinedByComma;

	private String mCreatorId;
	private String mContactId;
	private String mAccountId;
	private String mUserId;

	private String mCustomerStatus;

	private Account mSalesAccount;

	public Contact() {
	}

	public void addTags(final String... theTags) {
		String aNewTags = Joiner.on(",").join(filter(Iterators.forArray(theTags), new Predicate<String>() {
			public boolean apply(final String theString) {
				// TODO should use a regex here, but this is a really primitive tag representation
				return mTagsJoinedByComma == null || !mTagsJoinedByComma.contains(theString);
			}
		}));

		if (mTagsJoinedByComma != null && mTagsJoinedByComma.length() > 0) {
			mTagsJoinedByComma += ","+aNewTags;
		}
		else {
			mTagsJoinedByComma = aNewTags;
		}
	}

	public String getTagsJoinedByComma() {
		return mTagsJoinedByComma;
	}

	public void setTagsJoinedByComma(final String theTagsJoinedByComma) {
		mTagsJoinedByComma = theTagsJoinedByComma;
	}

	public String getCustomerStatus() {
		return mCustomerStatus;
	}

	public void setCustomerStatus(final String theCustomerStatus) {
		mCustomerStatus = theCustomerStatus;
	}

	public Account getSalesAccount() {
		return mSalesAccount;
	}

	public void setSalesAccount(final Account theSalesAccount) {
		mSalesAccount = theSalesAccount;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(final String theAddress) {
		mAddress = theAddress;
	}

	public String getCity() {
		return mCity;
	}

	public void setCity(final String theCity) {
		mCity = theCity;
	}

	public String getContactId() {
		return mContactId;
	}

	public void setContactId(final String theContactId) {
		mContactId = theContactId;
	}

	public String getCountry() {
		return mCountry;
	}

	public void setCountry(final String theCountry) {
		mCountry = theCountry;
	}

	public String getCreatorId() {
		return mCreatorId;
	}

	public void setCreatorId(final String theCreatorId) {
		mCreatorId = theCreatorId;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(final String theDescription) {
		mDescription = theDescription;
	}

	public String getFacebook() {
		return mFacebook;
	}

	public void setFacebook(final String theFacebook) {
		mFacebook = theFacebook;
	}

	public String getFax() {
		return mFax;
	}

	public void setFax(final String theFax) {
		mFax = theFax;
	}

	public String getIndustry() {
		return mIndustry;
	}

	public void setIndustry(final String theIndustry) {
		mIndustry = theIndustry;
	}

	public boolean isOrganisation() {
		return mIsOrganization;
	}

	public void setIsOrganisation(final boolean theIsOrganization) {
		mIsOrganization = theIsOrganization;
	}

	public boolean isSalesAccount() {
		return mIsSalesAccount;
	}

	public void setIsSalesAccount(final boolean theIsSalesAccount) {
		mIsSalesAccount = theIsSalesAccount;
	}

	public String getLinkedIn() {
		return mLinkedIn;
	}

	public void setLinkedIn(final String theLinkedIn) {
		mLinkedIn = theLinkedIn;
	}

	public String getLinkedInDisplay() {
		return mLinkedInDisplay;
	}

	public void setLinkedInDisplay(final String theLinkedInDisplay) {
		mLinkedInDisplay = theLinkedInDisplay;
	}

	public String getMobile() {
		return mMobile;
	}

	public void setMobile(final String theMobile) {
		mMobile = theMobile;
	}

	public ContactEntry getOrganisation() {
		return mOrganisation;
	}

	public void setOrganisation(final ContactEntry theOrganisation) {
		mOrganisation = theOrganisation;
	}

	public String getOrganisationName() {
		return mOrganisationName;
	}

	public void setOrganisationName(final String theOrganisationName) {
		mOrganisationName = theOrganisationName;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(final String thePhone) {
		mPhone = thePhone;
	}

	public boolean isPrivate() {
		return mPrivate;
	}

	public void setPrivate(final boolean thePrivate) {
		mPrivate = thePrivate;
	}

	public String getRegion() {
		return mRegion;
	}

	public void setRegion(final String theRegion) {
		mRegion = theRegion;
	}

	public String getSkype() {
		return mSkype;
	}

	public void setSkype(final String theSkype) {
		mSkype = theSkype;
	}

	public String getTwitter() {
		return mTwitter;
	}

	public void setTwitter(final String theTwitter) {
		mTwitter = theTwitter;
	}

	public Date getUpdatedAt() {
		return mUpdatedAt;
	}

	public void setUpdatedAt(final Date theUpdatedAt) {
		mUpdatedAt = theUpdatedAt;
	}

	public String getWebsite() {
		return mWebsite;
	}

	public void setWebsite(final String theWebsite) {
		mWebsite = theWebsite;
	}

	public String getZip() {
		return mZip;
	}

	public void setZip(final String theZip) {
		mZip = theZip;
	}

	public Date getCreatedAt() {
		return mCreatedAt;
	}

	public void setCreatedAt(final Date theCreatedAt) {
		mCreatedAt = theCreatedAt;
	}

	public String getUserId() {
		return mUserId;
	}

	public void setUserId(final String theUserId) {
		mUserId = theUserId;
	}

	public String getAccountId() {
		return mAccountId;
	}

	public void setAccountId(final String theAccountId) {
		mAccountId = theAccountId;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(final String theEmail) {
		mEmail = theEmail;
	}

	public String getName() {
		return mName;
	}

	public void setName(final String theName) {
		mName = theName;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(mId, mName, mEmail);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public boolean equals(final Object theObj) {
		if (theObj == this) {
			return true;
		}
		else if (theObj instanceof Contact) {
			Contact aContact = (Contact) theObj;

			return Objects.equal(mId, aContact.mId)
				&& Objects.equal(mName, aContact.mName)
				&& Objects.equal(mEmail, aContact.mEmail);
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
		return MoreObjects.toStringHelper("Contact")
		                  .add("id", mId)
		                  .add("name", mName)
		                  .add("email", mEmail)
		                  .add("status", mCustomerStatus)
		                  .toString();
	}

}

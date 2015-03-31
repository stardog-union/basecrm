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

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.1
 */
public class Note {
	private String mContent;
	private String mDealId;
	private String mContactId;
	private String mId;
	private String mUsername;

	private Date mCreatedAt;
	private Date mUpdatedAt;

	public Note() {
	}

	public Note(final Contact theContact, final String theContent) {
		mId = theContact.getId();
		mContent = theContent;
	}

	public String getContactId() {
		return mContactId;
	}

	public void setContactId(final String theContactId) {
		mContactId = theContactId;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(final String theContent) {
		mContent = theContent;
	}

	public Date getCreatedAt() {
		return mCreatedAt;
	}

	public void setCreatedAt(final Date theCreatedAt) {
		mCreatedAt = theCreatedAt;
	}

	public String getDealId() {
		return mDealId;
	}

	public void setDealId(final String theDealId) {
		mDealId = theDealId;
	}

	public String getId() {
		return mId;
	}

	public void setId(final String theId) {
		mId = theId;
	}

	public Date getUpdatedAt() {
		return mUpdatedAt;
	}

	public void setUpdatedAt(final Date theUpdatedAt) {
		mUpdatedAt = theUpdatedAt;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(final String theUsername) {
		mUsername = theUsername;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public String toString() {
		return MoreObjects.toStringHelper("Note")
			.add("user", mUsername)
			.add("content", mContent)
			.toString();
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public boolean equals(final Object theObj) {
		if (theObj == this) {
			return true;
		}
		else if (theObj instanceof Note) {
			Note aNote = (Note) theObj;

			return Objects.equal(mContent, aNote.mContent)
				&& Objects.equal(mDealId, aNote.mDealId)
				&& Objects.equal(mContactId, aNote.mContactId)
				&& Objects.equal(mId, aNote.mId)
			    && Objects.equal(mUsername, aNote.mUsername)
			    && Objects.equal(mCreatedAt, aNote.mCreatedAt)
				&& Objects.equal(mUpdatedAt, aNote.mUpdatedAt);
		}
		else {
			return false;
		}
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(mId, mContactId, mDealId, mContent, mUsername, mCreatedAt, mUpdatedAt);
	}
}
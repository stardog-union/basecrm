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
public class Reminder {
	private boolean mRemind = false;
	private boolean mDone = false;
	private String mContent;
	private String mId;
	private String mOwnerId;
	private Date mDate;
	private String mTime;
	private String mHour;

	private String mDoneAt;
	private Date mSendTime;
	private String mSendTimeDisplay;
	private Date mCreatedAt;
	private Date mUpdatedAt;

	private Contact mRelatedTo;

	public Reminder() {
	}

	public Reminder(final String theOwnerId, final String theContent) {
		mOwnerId = theOwnerId;
		mContent = theContent;
	}

	public Contact getRelatedTo() {
		return mRelatedTo;
	}

	public void setRelatedTo(final Contact theRelatedTo) {
		mRelatedTo = theRelatedTo;
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

	public Date getDate() {
		return mDate;
	}

	public void setDate(final Date theDate) {
		mDate = theDate;
	}

	public boolean isDone() {
		return mDone;
	}

	public void setDone(final boolean theDone) {
		mDone = theDone;
	}

	public String getDoneAt() {
		return mDoneAt;
	}

	public void setDoneAt(final String theDoneAt) {
		mDoneAt = theDoneAt;
	}

	public String getHour() {
		return mHour;
	}

	public void setHour(final String theHour) {
		mHour = theHour;
	}

	public String getId() {
		return mId;
	}

	public void setId(final String theId) {
		mId = theId;
	}

	public String getOwnerId() {
		return mOwnerId;
	}

	public void setOwnerId(final String theOwnerId) {
		mOwnerId = theOwnerId;
	}

	public boolean isRemind() {
		return mRemind;
	}

	public void setRemind(final boolean theRemind) {
		mRemind = theRemind;
	}

	public Date getSendTime() {
		return mSendTime;
	}

	public void setSendTime(final Date theSendTime) {
		mSendTime = theSendTime;
	}

	public String getSendTimeDisplay() {
		return mSendTimeDisplay;
	}

	public void setSendTimeDisplay(final String theSendTimeDisplay) {
		mSendTimeDisplay = theSendTimeDisplay;
	}

	public String getTime() {
		return mTime;
	}

	public void setTime(final String theTime) {
		mTime = theTime;
	}

	public Date getUpdatedAt() {
		return mUpdatedAt;
	}

	public void setUpdatedAt(final Date theUpdatedAt) {
		mUpdatedAt = theUpdatedAt;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(mId, mContent);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public boolean equals(final Object theObj) {
		if (theObj == this) {
			return true;
		}
		else if (theObj instanceof Reminder) {
			Reminder aReminder = (Reminder) theObj;

			return Objects.equal(mId, aReminder.mId);
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
		return MoreObjects.toStringHelper("Reminder").add("content", mContent).toString();
	}
}

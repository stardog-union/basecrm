package com.complexible.basecrm;

import com.google.common.base.Objects;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.1
 */
public class Account {
	private String mId;
	private String mAccountId;

	public String getAccountId() {
		return mAccountId;
	}

	public void setAccountId(final String theAccountId) {
		mAccountId = theAccountId;
	}

	public String getId() {
		return mId;
	}

	public void setId(final String theId) {
		mId = theId;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public boolean equals(final Object theObject) {
		if (theObject == this) {
			return true;
		}
		else if (theObject instanceof Account) {
			Account aAccount = (Account) theObject;

			return Objects.equal(mId, aAccount.mId)
				&& Objects.equal(mAccountId, aAccount.mAccountId);
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
		return Objects.hashCode(mId, mAccountId);
	}
}

package com.complexible.basecrm;

import com.google.common.base.Objects;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.1
 */
public class AuthenticationToken {
	private String mToken;

	public AuthenticationToken() {
	}

	public AuthenticationToken(final String theToken) {
		mToken = theToken;
	}

	public String getToken() {
		return mToken;
	}

	public void setToken(final String theToken) {
		mToken = theToken;
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(mToken);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public boolean equals(final Object theObj) {
		if (theObj == this) {
			return true;
		}
		else if (theObj instanceof AuthenticationToken) {
			AuthenticationToken aToken = (AuthenticationToken) theObj;
			return Objects.equal(mToken, aToken.getToken());
		}
		else {
			return false;
		}
	}
}

package com.complexible.basecrm;

import com.google.common.base.Objects;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.1
 */
public final class CustomField {
	private boolean mFilterable;
	private String mId;
	private String mType;
	private String mValue;
	private boolean mValueEditableOnlyByAdmin;

	private String mName;

	public CustomField() {
	}

	private CustomField(final CustomField theField) {
		mName = theField.mName;
		mId = theField.mId;
		mFilterable = theField.mFilterable;
		mType = theField.mType;
		mValue = theField.mValue;
	    mValueEditableOnlyByAdmin = theField.mValueEditableOnlyByAdmin;
	}

	public String getName() {
		return mName;
	}

	public void setName(final String theName) {
		mName = theName;
	}

	public boolean isFilterable() {
		return mFilterable;
	}

	public void setFilterable(final boolean theFilterable) {
		mFilterable = theFilterable;
	}

	public String getId() {
		return mId;
	}

	public void setId(final String theId) {
		mId = theId;
	}

	public String getType() {
		return mType;
	}

	public void setType(final String theType) {
		mType = theType;
	}

	public String getValue() {
		return mValue;
	}

	public void setValue(final String theValue) {
		mValue = theValue;
	}

	public boolean getValueEditableOnlyByAdmin() {
		return mValueEditableOnlyByAdmin;
	}

	public void setValueEditableOnlyByAdmin(final boolean theValueEditableOnlyByAdmin) {
		mValueEditableOnlyByAdmin = theValueEditableOnlyByAdmin;
	}

	public CustomField copy() {
		return new CustomField(this);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public String toString() {
		return String.format("CustomField(%s = %s)", mName != null ? mName : mId, mValue);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(mId, mValue);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public boolean equals(final Object theObj) {
		if (theObj == this) {
			return true;
		}
		else if (theObj instanceof CustomField) {
			CustomField aField = (CustomField) theObj;

			return Objects.equal(mId, aField.mId)
				&& Objects.equal(mValue, aField.mValue);
		}
		else {
			return false;
		}
	}
}

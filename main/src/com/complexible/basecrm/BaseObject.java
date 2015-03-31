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

import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.1
 */
public abstract class BaseObject {

	protected String mId;

	protected Map<String, CustomField> mCustomFields = Maps.newHashMap();


	public CustomField getCustomField(final String theName) {
		return mCustomFields.get(theName);
	}

	public void setCustomField(final CustomField theField) {
		Preconditions.checkNotNull(theField.getName());

		mCustomFields.put(theField.getName(), theField);
	}

	public Map<String, CustomField> getCustomFields() {
		return mCustomFields;
	}

	public void setCustomFields(final Map<String, CustomField> theCustomFields) {
		mCustomFields = theCustomFields;

		for (Map.Entry<String, CustomField> aEntry : mCustomFields.entrySet()) {
			aEntry.getValue().setName(aEntry.getKey());
		}
	}

	public String getId() {
		return mId;
	}

	public void setId(final String theId) {
		mId = theId;
	}
}

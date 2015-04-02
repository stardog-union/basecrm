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

/**
 * <p>Interface for working with the Base Contacts API</p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.2
 */
public interface Contacts {
	public Contact add(final Contact theContact);

	public Contact update(final Contact theContact);

	public void delete(final Contact theContact);

	/**
	 * Set the tags of the contact record to the list of tags on the contact
	 * @param theContact    the contact to set the tags for
	 * @return              the list of tags
	 */
	public List<Tag> setTags(final Contact theContact);

	public ContactFinder find();
}

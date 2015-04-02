package com.complexible.basecrm;

import com.google.common.collect.Iterables;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.2
 * @version 0.2
 */
public class TestContact {
	@Test
	public void testAddTag() {
		Contact aContact = new Contact();

		aContact.addTags("tag1", "tag2");

		assertTrue(aContact.hasTag("tag1"));
		assertTrue(aContact.hasTag("tag2"));
	}

	@Test
	public void testAddExistingTag() {

		Contact aContact = new Contact();

		aContact.addTags("tag1", "tag2");

		assertTrue(aContact.hasTag("tag1"));
		assertTrue(aContact.hasTag("tag2"));

		aContact.addTags("tag1", "tag2");

		assertEquals(2, Iterables.size(aContact.tags()));
	}

	@Test
	public void testRemoveTag() {

		Contact aContact = new Contact();

		aContact.addTags("tag1", "tag2");

		aContact.removeTags("tag1");
	}

	@Test
	public void testRemoveFromEmpty() {

		Contact aContact = new Contact();

		aContact.removeTags("tag1");

		assertTrue(Iterables.isEmpty(aContact.tags()));
	}

	@Test
	public void testRemoveNotExists() {

		Contact aContact = new Contact();

		aContact.addTags("tag1", "tag2");

		aContact.removeTags("asdf");

		assertEquals(2, Iterables.size(aContact.tags()));
	}


}

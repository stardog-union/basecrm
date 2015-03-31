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

package com.complexible.basecrm.v1;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import com.complexible.basecrm.AbstractContactFinder;
import com.complexible.basecrm.AuthenticationToken;
import com.complexible.basecrm.BaseAPI;
import com.complexible.basecrm.BaseAPIException;
import com.complexible.basecrm.Contact;
import com.complexible.basecrm.ContactEntry;
import com.complexible.basecrm.ContactFinder;
import com.complexible.basecrm.Contacts;
import com.complexible.basecrm.Deal;
import com.complexible.basecrm.DealEntry;
import com.complexible.basecrm.DealProductsExtensionEntry;
import com.complexible.basecrm.Deals;
import com.complexible.basecrm.Note;
import com.complexible.basecrm.NoteEntry;
import com.complexible.basecrm.Notes;
import com.complexible.basecrm.Product;
import com.complexible.basecrm.Reminder;
import com.complexible.basecrm.ReminderEntry;
import com.complexible.basecrm.Reminders;
import com.complexible.basecrm.Tag;
import com.complexible.basecrm.TagEntry;
import com.complexible.common.http.ApacheHttp;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.collect.Iterators.filter;
import static com.google.common.collect.Iterators.transform;

/**
 * <p></p>
 *
 * @author  Michael Grove
 * @since   0.1
 * @version 0.1
 */
public final class BaseAPIV1 implements BaseAPI {
	/**
	 * the logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseAPIV1.class);

	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static final String HEADER_AUTH = "X-Pipejump-Auth";
	public static final String HEADER_AUTH2 = "X-Futuresimple-Token";

	public static final String ROOT_URL_V1 = "https://sales.futuresimple.com/api/v1";

	private static final URI AUTH = URI.create(ROOT_URL_V1 + "/authentication.json");

	private final CloseableHttpClient mClient;
	private final AuthenticationToken mAuth;

	private BaseAPIV1(final CloseableHttpClient theClient, final AuthenticationToken theAuthentication) {
		mClient = theClient;
		mAuth = theAuthentication;
	}

	public static BaseAPI create(final String theToken) throws IOException {
		return create(null, theToken);
	}

	public static BaseAPI create(final String theUser, final String thePW) throws IOException {
		// TODO: HOW LONG DOES THIS LOGIN LAST??

		MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
		MAPPER.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
		MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss"));

		HttpPost aPost = new HttpPost(BaseAPIV1.AUTH);

		aPost.setEntity(ApacheHttp.Parameters.builder()
		                                     .parameter("email", theUser)
		                                     .parameter("password", thePW)
		                                     .postBody());

		CloseableHttpClient aHttpClient = HttpClientBuilder.create()
		                                                   .setUserAgent("getbase.api")
		                                                   .setConnectionManager(new PoolingHttpClientConnectionManager())
		                                                   .build();

		HttpResponse aResponse = null;

		try {
			if (Strings.isNullOrEmpty(theUser)) {
				return new BaseAPIV1(aHttpClient, new AuthenticationToken(thePW));
			}

			aResponse = aHttpClient.execute(aPost);

			LoginResult aResult = MAPPER.readValue(aResponse.getEntity().getContent(), LoginResult.class);

			if (aResult.getAuthentication() == null || aResult.getAuthentication().getToken() == null) {
				throw new IOException("Authentication failed");
			}

			return new BaseAPIV1(aHttpClient, aResult.getAuthentication());
		}
		catch (IOException e) {
			try {
				aHttpClient.close();
			}
			catch (IOException e1) {
				LOGGER.error("There was an error while closing the http client", e1);
			}

			throw e;
		}
		finally {
			ApacheHttp.HttpResponses.consumeQuietly(aResponse);
		}
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		mClient.close();
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public Contacts contacts() {
		return new ContactsV1(mClient, mAuth);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public Notes notes(final Contact theContact) {
		Preconditions.checkArgument(theContact.getId() != null, "Contact must have an id");

		return new NotesV1(mClient, mAuth, theContact);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public Reminders reminders(final Contact theContact) {
		Preconditions.checkArgument(theContact.getId() != null, "Contact must have an id");

		return new RemindersV1(mClient, mAuth, theContact);
	}

	/**
	 * @{inheritDoc}
	 */
	@Override
	public Deals deals() {
		return new DealsV1(mClient, mAuth);
	}

	private static final class ContactsV1 implements Contacts {

		private final CloseableHttpClient mClient;
		private final AuthenticationToken mAuth;
		private final JsonFactory mJsonFactory = new JsonFactory();

		public ContactsV1(final CloseableHttpClient theClient, final AuthenticationToken theAuth) {
			mClient = theClient;
			mAuth = theAuth;
			mJsonFactory.enable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public List<Tag> addTags(final Contact theContact, final String... theTags) {
			Preconditions.checkArgument(theContact.getId() != null, "Contact must have an id to be tagged");

			HttpPost aRequest = new HttpPost("https://tags.futuresimple.com/api/v1/taggings.json");

			final String aTags = theContact.getTagsJoinedByComma();
			String aNewTags = Joiner.on(",").join(filter(Iterators.forArray(theTags), new Predicate<String>() {
				public boolean apply(final String theString) {
					// TODO should use a regex here, but this is a really primitive tag representation
					return aTags == null || !aTags.contains(theString);
				}
			}));

			try {
				ApacheHttp.HttpRequests.header(aRequest, BaseAPIV1.HEADER_AUTH2, mAuth.getToken());


				aRequest.setEntity(ApacheHttp.Parameters.builder()
				                                        .parameter("app_id", "4")
				                                        .parameter("taggable_type", "Contact")
				                                        .parameter("taggable_id", theContact.getId())
				                                        .parameter("tag_list", aTags == null
				                                                               ? aNewTags
				                                                               : aTags + "," + aNewTags)
				                                        .postBody());

				HttpResponse aResponse = null;

				try {
					aResponse = mClient.execute(aRequest);

					if (aResponse.getStatusLine().getStatusCode() >= 400) {
						throw new BaseAPIException(String.format("There was an error adding the tags; a %s code was returned and the status message was %s",
						                                         aResponse.getStatusLine().getStatusCode(), aResponse.getStatusLine().getStatusCode()));
					}
					else {
						try {
							final List<TagEntry> aList = MAPPER.readValue(aResponse.getEntity().getContent(), new TypeReference<List<TagEntry>>() {});

							return Lists.transform(aList, TagEntry.UNWRAP);
						}
						catch (Exception e) {
							throw new BaseAPIException("There was an error parsing the response", e);
						}
					}
				}
				finally {
					ApacheHttp.HttpResponses.consumeQuietly(aResponse);
				}
			}
			catch (IOException e) {
				throw new BaseAPIException(e);
			}
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public void delete(final Contact theContact) {
			Preconditions.checkArgument(theContact.getId() != null, "Contact must have an id to be deleted");

			HttpDelete aRequest = new HttpDelete(BaseAPIV1.ROOT_URL_V1 + "/contacts/" + theContact.getId() + ".json");

			ApacheHttp.HttpRequests.header(aRequest, BaseAPIV1.HEADER_AUTH, mAuth.getToken());

			HttpResponse aResponse = null;

			try {
				aResponse = mClient.execute(aRequest);

				if (aResponse.getStatusLine().getStatusCode() >= 400) {
					throw new BaseAPIException(String.format("There was an error deleting the contact; a %s code was returned and the status message was %s",
					                                         aResponse.getStatusLine().getStatusCode(), aResponse.getStatusLine().getStatusCode()));
				}
			}
			catch (Exception e) {
				throw new BaseAPIException(e);
			}
			finally {
				ApacheHttp.HttpResponses.consumeQuietly(aResponse);
			}
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public Contact update(final Contact theContact) {
			Preconditions.checkArgument(theContact.getId() != null, "Contact must have an id to be updated");

			try {
				HttpPut aReq = new HttpPut(BaseAPIV1.ROOT_URL_V1 + "/contacts/"+theContact.getId()+ ".json");

				ApacheHttp.HttpRequests.header(aReq, BaseAPIV1.HEADER_AUTH, mAuth.getToken());

				aReq.setEntity(ApacheHttp.Entities.create(MAPPER.writeValueAsString(new ContactEntry(theContact)),
				                                          ContentType.APPLICATION_JSON));


				HttpResponse aResponse = null;

				try {
					aResponse = mClient.execute(aReq);

					if (aResponse.getStatusLine().getStatusCode() >= 400) {
						throw new BaseAPIException(String.format("There was an error updating the contact; a %s code was returned and the status message was %s",
						                                         aResponse.getStatusLine().getStatusCode(), aResponse.getStatusLine().getStatusCode()));
					}
					else {
						try {
							final Contact aContact = MAPPER.readValue(aResponse.getEntity()
							                                                   .getContent(), ContactEntry.class)
							                               .getContact();

							if (theContact.getOrganisation() != null && theContact.getOrganisation().getContact() != null) {
								addToOrganization(aContact, theContact.getOrganisation().getContact());

								aContact.setOrganisation(new ContactEntry(theContact.getOrganisation().getContact()));
							}

							return aContact;
						}
						catch (Exception e) {
							throw new BaseAPIException("There was an error parsing the response", e);
						}
					}
				}
				finally {
					ApacheHttp.HttpResponses.consumeQuietly(aResponse);
				}
			}
			catch (IOException e) {
				throw new BaseAPIException(e);
			}
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public Contact add(final Contact theContact) {
			try {
				HttpPost aPost = new HttpPost(BaseAPIV1.ROOT_URL_V1 + "/contacts.json");

				ApacheHttp.HttpRequests.header(aPost, BaseAPIV1.HEADER_AUTH, mAuth.getToken());

				aPost.setEntity(ApacheHttp.Entities.create(MAPPER.writeValueAsString(new ContactEntry(theContact)),
				                                           ContentType.APPLICATION_JSON));


				HttpResponse aResponse = null;

				try {
					aResponse = mClient.execute(aPost);

					if (aResponse.getStatusLine().getStatusCode() >= 400) {
						throw new BaseAPIException(String.format("There was an error adding the contact; a %s code was returned and the status message was %s",
						                                         aResponse.getStatusLine().getStatusCode(), aResponse.getStatusLine().getStatusCode()));
					}
					else {
						try {
							Contact aContact = MAPPER.readValue(aResponse.getEntity().getContent(), ContactEntry.class).getContact();

							if (theContact.getOrganisation() != null && theContact.getOrganisation().getContact() != null) {
								addToOrganization(aContact, theContact.getOrganisation().getContact());

								aContact.setOrganisation(new ContactEntry(theContact.getOrganisation().getContact()));
							}

							return aContact;
						}
						catch (Exception e) {
							throw new BaseAPIException("There was an error parsing the response", e);
						}
					}
				}
				finally {
					ApacheHttp.HttpResponses.consumeQuietly(aResponse);
				}
			}
			catch (IOException e) {
				throw new BaseAPIException(e);
			}
		}

		private void addToOrganization(final Contact thePerson, final Contact theOrg) {
			HttpPut aRequest = new HttpPut("https://app.futuresimple.com/apis/crm/api/v1/contacts/"+thePerson.getId()+".json");

			try {
				ApacheHttp.HttpRequests.header(aRequest, BaseAPIV1.HEADER_AUTH, mAuth.getToken());

				aRequest.setEntity(ApacheHttp.Entities.create(String.format("{ \"contact\": { \"contact_id\" : \"%s\" } }", theOrg
					                                                                                                            .getId()),
				                                              ContentType.APPLICATION_JSON));


				HttpResponse aResponse = null;

				try {
					aResponse = mClient.execute(aRequest);

					if (aResponse.getStatusLine().getStatusCode() >= 400) {
						throw new BaseAPIException(String.format("There was an error adding the contact to their org; a %s code was returned and the status message was %s",
						                                         aResponse.getStatusLine().getStatusCode(), aResponse.getStatusLine().getStatusCode()));
					}
				}
				finally {
					ApacheHttp.HttpResponses.consumeQuietly(aResponse);
				}
			}
			catch (IOException e) {
				throw new BaseAPIException(e);
			}
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public ContactFinder find() {
			return new AbstractContactFinder() {
				@Override
				public Iterator<Contact> find() {
					return transform(new PageIterator<ContactEntry>(mClient, new TypeReference<List<ContactEntry>>() {}) {
						@Override
						protected HttpUriRequest createPageRequest(final int thePage) {
							HttpGet aGet = new HttpGet(BaseAPIV1.ROOT_URL_V1 + "/contacts.json");

							ApacheHttp.HttpRequests.header(aGet, BaseAPIV1.HEADER_AUTH, mAuth.getToken());

							if (mEmail != null) {
								ApacheHttp.HttpRequests.appendURIParameter(aGet, "email", mEmail);
							}

							if (mName != null) {
								ApacheHttp.HttpRequests.appendURIParameter(aGet, "name", mName);
							}

							if (mIsOrganization != null) {
								ApacheHttp.HttpRequests.appendURIParameter(aGet, "is_organisation", String.valueOf(mIsOrganization));
							}

							return aGet;
						}
					}, ContactEntry.UNWRAP);
				}
			};
		}
	}

	public static final class DealsV1 implements Deals {
		private final CloseableHttpClient mClient;
		private final AuthenticationToken mAuth;

		public DealsV1(final CloseableHttpClient theClient, final AuthenticationToken theAuth) {
			mClient = theClient;
			mAuth = theAuth;
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public Iterator<Product> products(final String theId) {
			HttpGet aRequest = new HttpGet("https://app.futuresimple.com/apis/products/api/v1/deal_products_extensions/"+theId+".json");

			ApacheHttp.HttpRequests.header(aRequest, BaseAPIV1.HEADER_AUTH2, mAuth.getToken());

			HttpResponse aResponse = null;

			try {
				aResponse = mClient.execute(aRequest);

				if (aResponse.getStatusLine().getStatusCode() >= 400) {
					throw new BaseAPIException(String.format("There was an error getting the deal; a %s code was returned and the status message was %s",
					                                         aResponse.getStatusLine().getStatusCode(), aResponse.getStatusLine().getStatusCode()));
				}
				else {
					return MAPPER.readValue(aResponse.getEntity().getContent(), DealProductsExtensionEntry.class).getDealProductsExtension().getProducts().iterator();
				}
			}
			catch (Exception e) {
				throw new BaseAPIException("There was an error parsing the response", e);
			}
			finally {
				ApacheHttp.HttpResponses.consumeQuietly(aResponse);
			}
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public Deal get(final String theId) {
			HttpGet aRequest = new HttpGet(BaseAPIV1.ROOT_URL_V1 + "/deals/" + theId + ".json");

			ApacheHttp.HttpRequests.header(aRequest, BaseAPIV1.HEADER_AUTH, mAuth.getToken());

			HttpResponse aResponse = null;

			try {
				aResponse = mClient.execute(aRequest);

				if (aResponse.getStatusLine().getStatusCode() >= 400) {
					throw new BaseAPIException(String.format("There was an error getting the deal; a %s code was returned and the status message was %s",
					                                         aResponse.getStatusLine().getStatusCode(), aResponse.getStatusLine().getStatusCode()));
				}
				else {
					return MAPPER.readValue(aResponse.getEntity().getContent(), DealEntry.class).getDeal();
				}
			}
			catch (Exception e) {
				throw new BaseAPIException("There was an error parsing the response", e);
			}
			finally {
				ApacheHttp.HttpResponses.consumeQuietly(aResponse);
			}
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public Iterator<Deal> list() {
			return transform(new PageIterator<DealEntry>(mClient, new TypeReference<List<DealEntry>>() {}) {
				@Override
				protected HttpUriRequest createPageRequest(final int thePage) {
					HttpGet aGet = new HttpGet(BaseAPIV1.ROOT_URL_V1 + "/deals.json");

					ApacheHttp.HttpRequests.header(aGet, BaseAPIV1.HEADER_AUTH, mAuth.getToken());

					return aGet;
				}
			}, DealEntry.UNWRAP);
		}
	}

	public static final class NotesV1 implements Notes {
		private final CloseableHttpClient mClient;
		private final AuthenticationToken mAuth;
		private final Contact mContact;

		public NotesV1(final CloseableHttpClient theClient, final AuthenticationToken theAuth, final Contact theContact) {
			mAuth = theAuth;
			mClient = theClient;
			mContact = theContact;
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public Note add(final Note theNote) {
			HttpPost aRequest = new HttpPost(BaseAPIV1.ROOT_URL_V1 + "/contacts/"+mContact.getId()+"/notes.json");

			ApacheHttp.HttpRequests.header(aRequest, BaseAPIV1.HEADER_AUTH, mAuth.getToken());
			try {
				aRequest.setEntity(ApacheHttp.Entities.create(MAPPER.writeValueAsString(new NoteEntry(theNote)),
				                                              ContentType.APPLICATION_JSON));


				HttpResponse aResponse = null;

				try {
					aResponse = mClient.execute(aRequest);

					if (aResponse.getStatusLine().getStatusCode() >= 400) {
						throw new BaseAPIException(String.format("There was an error adding the note; a %s code was returned and the status message was %s",
						                                         aResponse.getStatusLine().getStatusCode(), aResponse.getStatusLine().getStatusCode()));
					}
					else {
						try {
							return MAPPER.readValue(aResponse.getEntity().getContent(), NoteEntry.class).getNote();
						}
						catch (Exception e) {
							throw new BaseAPIException("There was an error parsing the response", e);
						}
					}
				}
				finally {
					ApacheHttp.HttpResponses.consumeQuietly(aResponse);
				}
			}
			catch (IOException e) {
				throw new BaseAPIException(e);
			}
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public void delete(final Note theNote) {
			HttpDelete aRequest = new HttpDelete(BaseAPIV1.ROOT_URL_V1 + "/contacts/"+mContact.getId()+"/notes/"+theNote.getId()+".json");

			ApacheHttp.HttpRequests.header(aRequest, BaseAPIV1.HEADER_AUTH, mAuth.getToken());

			HttpResponse aResponse = null;

			try {
				aResponse = mClient.execute(aRequest);

				if (aResponse.getStatusLine().getStatusCode() >= 400) {
					throw new BaseAPIException(String.format("There was an error deleting the note; a %s code was returned and the status message was %s",
					                                         aResponse.getStatusLine().getStatusCode(), aResponse.getStatusLine().getStatusCode()));
				}
			}
			catch (Exception e) {
				throw new BaseAPIException(e);
			}
			finally {
				ApacheHttp.HttpResponses.consumeQuietly(aResponse);
			}
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public Iterator<Note> list() {
			return transform(new PageIterator<NoteEntry>(mClient, new TypeReference<List<NoteEntry>>() {}) {
				@Override
				protected HttpUriRequest createPageRequest(final int thePage) {
					HttpGet aGet = new HttpGet(BaseAPIV1.ROOT_URL_V1 + "/contacts/"+mContact.getId()+"/notes.json");

					ApacheHttp.HttpRequests.header(aGet, BaseAPIV1.HEADER_AUTH, mAuth.getToken());

					return aGet;
				}
			}, NoteEntry.UNWRAP);
		}
	}

	public static final class RemindersV1 implements Reminders {
		private final CloseableHttpClient mClient;
		private final AuthenticationToken mAuth;
		private final Contact mContact;

		public RemindersV1(final CloseableHttpClient theClient, final AuthenticationToken theAuth, final Contact theContact) {
			mAuth = theAuth;
			mClient = theClient;
			mContact = theContact;
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public Reminder add(final Reminder theReminder) {
			HttpPost aRequest = new HttpPost(BaseAPIV1.ROOT_URL_V1 + "/contacts/"+mContact.getId()+"/reminders.json");

			ApacheHttp.HttpRequests.header(aRequest, BaseAPIV1.HEADER_AUTH, mAuth.getToken());
			try {
				aRequest.setEntity(ApacheHttp.Entities.create(MAPPER.writeValueAsString(new ReminderEntry(theReminder)),
				                                              ContentType.APPLICATION_JSON));


				HttpResponse aResponse = null;

				try {
					aResponse = mClient.execute(aRequest);

					if (aResponse.getStatusLine().getStatusCode() >= 400) {
						throw new BaseAPIException(String.format("There was an error adding the reminder; a %s code was returned and the status message was %s",
						                                         aResponse.getStatusLine().getStatusCode(), aResponse.getStatusLine().getStatusCode()));
					}
					else {
						try {
							return MAPPER.readValue(aResponse.getEntity().getContent(), ReminderEntry.class).getReminder();
						}
						catch (Exception e) {
							throw new BaseAPIException("There was an error parsing the response", e);
						}
					}
				}
				finally {
					ApacheHttp.HttpResponses.consumeQuietly(aResponse);
				}
			}
			catch (IOException e) {
				throw new BaseAPIException(e);
			}
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public void delete(final Reminder theReminder) {
			HttpDelete aRequest = new HttpDelete(BaseAPIV1.ROOT_URL_V1 + "/contacts/"+mContact.getId()+"/reminders/"+theReminder.getId()+".json");

			ApacheHttp.HttpRequests.header(aRequest, BaseAPIV1.HEADER_AUTH, mAuth.getToken());

			HttpResponse aResponse = null;

			try {
				aResponse = mClient.execute(aRequest);

				if (aResponse.getStatusLine().getStatusCode() >= 400) {
					throw new BaseAPIException(String.format("There was an error deleting the reminder; a %s code was returned and the status message was %s",
					                                         aResponse.getStatusLine().getStatusCode(), aResponse.getStatusLine().getStatusCode()));
				}
			}
			catch (Exception e) {
				throw new BaseAPIException(e);
			}
			finally {
				ApacheHttp.HttpResponses.consumeQuietly(aResponse);
			}
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		public Iterator<Reminder> list() {
			return transform(new PageIterator<ReminderEntry>(mClient, new TypeReference<List<ReminderEntry>>() {}) {
				@Override
				protected HttpUriRequest createPageRequest(final int thePage) {
					HttpGet aGet = new HttpGet(BaseAPIV1.ROOT_URL_V1 + "/contacts/"+mContact.getId()+"/reminders.json");

					ApacheHttp.HttpRequests.header(aGet, BaseAPIV1.HEADER_AUTH, mAuth.getToken());

					return aGet;
				}
			}, ReminderEntry.UNWRAP);
		}
	}

	private static abstract class PageIterator<T> extends AbstractIterator<T> {
		private static final int MAX = 20;

		private int mPage = 1;
		private int mPageCount = MAX;

		private Iterator<T> mIter = ImmutableList.<T>of().iterator();

		private final TypeReference<List<T>> mTypeReference;

		private final HttpClient mClient;

		public PageIterator(final HttpClient theClient, final TypeReference<List<T>> theTypeReference) {
			mClient = theClient;
			mTypeReference = theTypeReference;
		}

		/**
		 * @{inheritDoc}
		 */
		@Override
		protected T computeNext() {
			if (!mIter.hasNext() && mPageCount <= MAX) {
				mPageCount = 0;

				HttpUriRequest aRequest = createPageRequest(mPage);

				if (mPage > 1) {
					ApacheHttp.HttpRequests.appendURIParameter(aRequest, "page", String.valueOf(mPage));
				}

				HttpResponse aResponse = null;

				try {
					aResponse = mClient.execute(aRequest);

					List<T> aList = MAPPER.readValue(aResponse.getEntity().getContent(), mTypeReference);

					mIter = aList.iterator();
				}
				catch (Exception e) {
					// TODO: users beware!
					throw new BaseAPIException(e);
				}
				finally {
					mPage++;
					ApacheHttp.HttpResponses.consumeQuietly(aResponse);
				}
			}

			if (mIter.hasNext()) {
				mPageCount++;
				return mIter.next();
			}

			return endOfData();
		}

		protected abstract HttpUriRequest createPageRequest(final int thePage);
	}

	private static final class LoginResult {
		private AuthenticationToken mAuthentication;

		public AuthenticationToken getAuthentication() {
			return mAuthentication;
		}

		public void setAuthentication(final AuthenticationToken theAuthentication) {
			mAuthentication = theAuthentication;
		}
	}
}

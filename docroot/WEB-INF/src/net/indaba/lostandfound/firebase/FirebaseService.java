package net.indaba.lostandfound.firebase;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.util.portlet.PortletProps;

import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;
import net.thegreshams.firebase4j.model.FirebaseResponse;
import net.thegreshams.firebase4j.service.Firebase;

public abstract class FirebaseService<T extends BaseModel<T>> {
	
	private String fbURI;
	
	private String fbModelSingular;
	private String fbModelPlural;
	
	private FirebaseMapper<T> mapper;
	
	private String fbIdField = "\"id\"";

	public FirebaseService(String fbBaseURL, String fbModelSingular, String fbModelPlural, FirebaseMapper<T> mapper) {
		super();
		this.fbModelSingular = fbModelSingular;
		this.fbModelPlural = fbModelPlural;
		this.mapper = mapper;
		
		this.fbURI = fbBaseURL + fbModelPlural;
	}
	
	public final String getFbURI() {
		return this.fbURI;
	}
	
	public final String getFbModelSingular() {
		return this.fbModelSingular;
	}
	
	public final String getFbModelPlural() {
		return this.fbModelPlural;
	}
	
	public final FirebaseMapper<T> getFbMapper() {
		return this.mapper;
	}
	
	public final String getFbIdField() {
		return this.fbIdField;
	}
	
	public final void setFbURI(String fbURI) {
		this.fbURI = fbURI;
	}
	
	public final void setFbIdField(String fbIdField) {
		this.fbIdField = fbIdField;
	}
	
	/**
	 * Adds the entity to Firebase
	 * @param entity
	 * @return The FirebaseKey for the entity
	 */
	public abstract String add(T entity);

	/**
	 * Updates the entity in Firebase
	 * @param entity
	 * @return The FirebaseKey for the entity
	 */
	public String update(T entity) {
		String firebaseKey = getFirebaseKey(entity);
		return update(entity, firebaseKey);
	}
	
	protected String update(T entity, String firebaseKey) {
		try {
			Firebase firebase = new Firebase(getFbURI());
			Map<String, Object> itemMap = mapper.toMap(entity);
			itemMap.put("_liferay", true);
			FirebaseResponse response;
			response = firebase.patch("/" + firebaseKey, itemMap);
			if (response.getCode() == 200) {
				_log.debug("Firebase update sucessful");
				return (String) response.getBody().keySet().iterator().next();
			} else {
				_log.error("Firebase update unsuccessful. Response code: " + response.getCode());
			}
		} catch (FirebaseException | JacksonUtilityException | UnsupportedEncodingException e) {
			_log.error("Firebase update unsuccessful. Error: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Adds or updates the entity in Firebase
	 * @param entity
	 * @return The FirebaseKey for the entity
	 */
	public String addOrUpdate(T entity) {
		String fbKey = getFirebaseKey(entity);
		if (fbKey != null) { // Entity exists already in Firebase: update
			return update(entity, fbKey);
		} else { // Entity does not exist in Firebase: create
			return add(entity);
		}
	}
	
	/**
	 * Deletes the entity in Firebase
	 * @param entity
	 * @return True iff the entity was found and removed
	 */
	public boolean delete(T entity) {
		try {
			Firebase firebase = new Firebase(getFbURI());
			String itemKey = getFirebaseKey(entity);
			FirebaseResponse response;
			if (itemKey != null) {
				//setRelationManyToMany(entity, new ArrayList<AssetCategory>(), null, null).get();
				response = firebase.delete("/" + itemKey);
				if (response.getCode() == 200) {
					_log.debug("Firebase delete sucessful");
					return true;
				} else {
					_log.error("Firebase delete unsuccessful. Response code: " + response.getCode());
				}
			} else {
				_log.error("Could not find entity with id " + entity.getPrimaryKeyObj());
			}
		} catch (FirebaseException | UnsupportedEncodingException e) {
			_log.error("Firebase delete unsuccessful. Error: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Asynchronously adds the entity to Firebase
	 * @param entity
	 * @param previousFuture Future object to wait for before the method is really executed.
	 * 	Set to null to avoid any wait
	 * @return A promise containing the FirebaseKey for the entity
	 */
	public final Future<String> add(T entity, Future<?> previousFuture) {
		return asyncWrapper(() -> {
			waitFor(previousFuture);
			return add(entity);
		});
	};

	/**
	 * Asynchronously updates the entity in Firebase
	 * @param entity
	 * @param previousFuture Future object to wait for before the method is really executed.
	 * 	Set to null to avoid any wait
	 * @return A promise containing the FirebaseKey for the entity
	 */
	public final Future<String> update(T entity, Future<?> previousFuture) {
		return asyncWrapper(() -> {
			waitFor(previousFuture);
			return update(entity);
		});
	}

	/**
	 * Asynchronously adds or updates the entity in Firebase
	 * @param entity
	 * @param previousFuture Future object to wait for before the method is really executed.
	 * 	Set to null to avoid any wait
	 * @return A promise containing the FirebaseKey for the entity
	 */
	public final Future<String> addOrUpdate(T entity, Future<?> previousFuture) {
		return asyncWrapper(() -> {
			waitFor(previousFuture);
			return addOrUpdate(entity);
		});
	}

	/**
	 * Asynchronously deletes the entity in Firebase
	 * @param entity
	 * @param previousFuture Future object to wait for before the method is really executed.
	 * 	Set to null to avoid any wait
	 * @return A promise boolean with true iff the entity was found and removed
	 */
	public final Future<Boolean> delete(T entity, Future<?> previousFuture) {
		return asyncWrapper(() -> {
			waitFor(previousFuture);
			return delete(entity);
		});
	}

	/**
	 * Adds/Removes a reference on firebase entity with fbKey
	 * @param type A String of the following: {"ToOne", "ToMany"}
	 * @param operation A String of the following: {"add", "delete"}
	 * @param fbKey The FirebaseKey for the entity
	 * @param referenceKey The Reference value to add
	 * @param referenceField The field name for the reference
	 * @param previousFuture Future object to wait for before the method is really executed.
	 * 	Set to null to avoid any wait
	 * @return A future object which contains true if the reference is successfully added
	 */
	private Future<Boolean> setReference(String type, String operation, String fbKey, String referenceKey,
			String referenceField, Future<?> previousFuture) {
		return asyncWrapper(() -> {
			waitFor(previousFuture);
			String URI = null;
			Map<String, Object> referenceMap = new LinkedHashMap<String, Object>();
			
			switch (type) {
			case "ToOne":
				referenceMap.put(referenceField, referenceKey);
				URI = getFbURI() + "/" + fbKey;
				break;
			case "ToMany":
				referenceMap.put(referenceKey, true);
				URI = getFbURI() + "/" + fbKey + "/" + referenceField;
				break;
			}
			try {
				Firebase firebase = new Firebase(URI);
				FirebaseResponse response = null;
				switch (operation) {
				case "add":
					response = firebase.patch(referenceMap);
					break;
				case "delete":
					response = firebase.delete("/" + referenceMap.keySet().iterator().next());
					break;
				}
				if (response.getCode() == 200) {
					_log.debug("Reference added on " + fbKey);
					return true;
				} else {
					_log.error("Error adding reference: " + response.getBody().get("error"));
					return false;
				}
			} catch (FirebaseException | JacksonUtilityException e) {
				e.printStackTrace();
			}
			return false;
		});
	}
	
	/**
	 * Sets the references for a X-to-many relationship
	 * @param X A String in {"One", "Many"}
	 * @param entity
	 * @param relatedEntities
	 * @param previousFuture Future object to wait for before the method is really executed.
	 * 	Set to null to avoid any wait
	 * @return A future object which contains true if the reference is successfully added
	 */
	private <S extends BaseModel<S>> Future<Boolean> setRelationXToMany(String X, T entity, List<S> relatedEntities, 
			FirebaseService<S> relatedEntityService, Future<?> previousFuture) {
		return asyncWrapper(() -> {
			waitFor(previousFuture);
			try {
				String fbKey = getFirebaseKey(entity);
				if (fbKey != null) {
					String referenceField = relatedEntityService.getFbModelPlural();
					String entityRefName = null;
					switch (X) {
					case "One":
						entityRefName = getFbModelSingular();
						break;
					case "Many":
						entityRefName = getFbModelPlural();
						break;
					}

					Firebase firebase;
					FirebaseResponse response;

					/* Obtain previous references */
					firebase = new Firebase(getFbURI() + "/" + fbKey);
					response = firebase.get("/" + referenceField);
					Map<String, Object> oldRefMap = new LinkedHashMap<String, Object>();
					if (response.getCode() == 200) {
						Map<String, Object> o = response.getBody();
						if (o != null) {
							oldRefMap = flatten(o);
						}
					}

					/* Update related-entities */
					Map<String, Object> newRefMap = new LinkedHashMap<String, Object>();
					for (S relatedEntity : relatedEntities) {
						/* Compare oldRelatedEntities with newRelatedEntities */
						String relatedEntityKey = relatedEntityService.getFirebaseKey(relatedEntity);
						if (oldRefMap.containsKey(relatedEntityKey)) {
							oldRefMap.remove(relatedEntityKey);
						} else {
								relatedEntityService.setReference("To" + X, "add", relatedEntityKey, fbKey, entityRefName, null);
						}
						/* Collect the relatedEntity into the newMap */
						newRefMap.put(relatedEntityKey, true);
					}

					/* Delete references from entity to remaining oldRelatedEntities */
					for (Entry<String, Object> e : oldRefMap.entrySet()) {
						String relatedEntityKey = e.getKey();
						relatedEntityService.setReference("To" + X, "delete", relatedEntityKey, fbKey, entityRefName, null);
					}

					/* Set references on entity to newRelatedEntities */
					Map<String, Object> entityMap = new LinkedHashMap<String, Object>();
					entityMap.put("_liferay", true);
					if (relatedEntities.size() > 0) {
						entityMap.put(referenceField, newRefMap);
					} else {
						entityMap.put(referenceField, null);
					}

					firebase = new Firebase(getFbURI() + "/" + fbKey);
					response = firebase.patch(entityMap);
					if (response.getCode() == 200) {
						_log.debug("Firebase relation add sucessful");
						return true;
					} else {
						_log.error("Firebase relation add unsuccessful. Error " + response.getCode() + " "
								+ response.getBody().get("error"));
					}
				}
			} catch (FirebaseException | JacksonUtilityException e1) {
				e1.printStackTrace();
			}
			return false;
		});
	}
	
	private Map<String, Object> flatten(Map<String, Object> map) {
		Map<String, Object> result = new LinkedHashMap<String, Object>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String root = it.next();
			Object o = map.get(root);
			if (o instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> m = Map.class.cast(o);
				m = flatten(m);
				for (String k : m.keySet()) {
					result.put(root + "/" + k, m.get(k));
				}
			} else {
				result.put(root, o);
			}
		}
		return result;
	}

	/**
	 * Sets the references for a many-to-many relationship
	 * @param entity
	 * @param relatedEntities
	 * @param previousFuture Future object to wait for before the method is really executed.
	 * 	Set to null to avoid any wait
	 * @return A future object which contains true if the reference is successfully added
	 */
	public <S extends BaseModel<S>> Future<Boolean> setRelationManyToMany(T entity, List<S> relatedEntities, 
			FirebaseService<S> relatedEntityService, Future<?> previousFuture) {
		return setRelationXToMany("Many", entity, relatedEntities, relatedEntityService, previousFuture);
	}
	
	/**
	 * Sets the references for a one-to-many relationship
	 * @param entity
	 * @param relatedEntities
	 * @param previousFuture Future object to wait for before the method is really executed.
	 * 	Set to null to avoid any wait
	 * @return A future object which contains true if the reference is successfully added
	 */
	public <S extends BaseModel<S>> Future<Boolean> setRelationOneToMany(T entity, List<S> relatedEntities, 
			FirebaseService<S> relatedEntityService, Future<?> previousFuture) {
		return setRelationXToMany("One", entity, relatedEntities, relatedEntityService, previousFuture);
	}
	
	/**
	 * Sets the references for a X-to-one relationship
	 * @param X A String in {"One", "Many"}
	 * @param entity
	 * @param relatedEntities
	 * @param previousFuture Future object to wait for before the method is really executed.
	 * 	Set to null to avoid any wait
	 * @return A future object which contains true if the reference is successfully added
	 */
	private <S extends BaseModel<S>> Future<Boolean> setRelationXToOne(String X, T entity, S relatedEntity, 
			FirebaseService<S> relatedEntityService, Future<?> previousFuture) {
		return asyncWrapper(() -> {
			waitFor(previousFuture);
			try {
				String fbKey = getFirebaseKey(entity);
				if (fbKey != null) {
					String referenceField = relatedEntityService.getFbModelSingular();
		
					String entityRefName = null;
					switch (X) {
					case "One":
						entityRefName = getFbModelSingular();
						break;
					case "Many":
						entityRefName = getFbModelPlural();
						break;
					}
					
					Firebase firebase;
					FirebaseResponse response;
					
					/* Obtain previous reference */
					firebase = new Firebase(getFbURI() + "/" + fbKey);
					response = firebase.get();
					String oldRef = null;
					if (response.getCode() == 200) {
						oldRef = (String) response.getBody().get(referenceField);
					}
					
					String newRef = null;
					if (relatedEntity != null) {
						newRef = relatedEntityService.getFirebaseKey(relatedEntity);
						/* Update relatedEntity's reference(s) */
						if (newRef != null && !newRef.equals(oldRef)) {
							relatedEntityService.setReference("To" + X, "add", newRef, fbKey, entityRefName, null);
							if (oldRef != null)
								relatedEntityService.setReference("To" + X, "delete", oldRef, fbKey, entityRefName, null);
						}
					} else {
						if (oldRef != null)
							relatedEntityService.setReference("To" + X, "delete", oldRef, fbKey, entityRefName, null);
					}

					/* Set references on entity to newRelatedEntity */
					Map<String, Object> entityMap = new LinkedHashMap<String, Object>();
					entityMap.put("_liferay", true);
					entityMap.put(referenceField, newRef);

					firebase = new Firebase(getFbURI() + "/" + fbKey);
					response = firebase.patch(entityMap);
					if (response.getCode() == 200) {
						_log.info("Firebase relation add sucessful");
						return true;
					} else {
						_log.error("Firebase relation add unsuccessful. Error " + response.getCode() + " "
								+ response.getBody().get("error"));
					}
				}
			} catch (FirebaseException | JacksonUtilityException e1) {
				_log.error("Error");
				e1.printStackTrace();
			}
			return false;
		});
	}
	
	/**
	 * Sets the references for a many-to-one relationship
	 * @param entity
	 * @param relatedEntities
	 * @param @param previousFuture Future object to wait for before the method is really executed.
	 * 	Set to null to avoid any wait
	 * @return A future object which contains true if the reference is successfully added
	 */
	public <S extends BaseModel<S>> Future<Boolean> setRelationManyToOne(T entity, S relatedEntity, 
			FirebaseService<S> relatedEntityService, Future<?> previousFuture) {
		return setRelationXToOne("Many", entity, relatedEntity, relatedEntityService, previousFuture);
	}
	
	/**
	 * Sets the references for a one-to-one relationship
	 * @param entity
	 * @param relatedEntities
	 * @param previousFuture Future object to wait for before the method is really executed.
	 * 	Set to null to avoid any wait
	 * @return A future object which contains true if the reference is successfully added
	 */
	public <S extends BaseModel<S>> Future<Boolean> setRelationOneToOne(T entity, S relatedEntity, 
			FirebaseService<S> relatedEntityService, Future<?> previousFuture) {
		return setRelationXToOne("One", entity, relatedEntity, relatedEntityService, previousFuture);
	}
	
	/**
	 * Gets the Firebase object key for the given entity if it exists
	 * @param entity
	 * @return A String containing the FirebaseKey
	 * 	or null if no matching entity was found in Firebase
	 */
	public abstract String getFirebaseKey(T entity);

	protected List<T> getLiferayItemsAfter(long liferayTS) {
		return null;
	};
	
	protected Map<String, T> getFirebaseItemsAfter(long firebaseTS) throws FirebaseException, UnsupportedEncodingException {
		Map<String, T> items = new LinkedHashMap<String, T>();

		Firebase firebase = new Firebase(getFbURI());
		/* Get alerts */
		firebase.addQuery("orderBy", "\"modifiedAt\"");
		firebase.addQuery("startAt", String.valueOf(firebaseTS));
		FirebaseResponse response = firebase.get("/alert");
		Map<String, Object> lostItems = response.getBody();
		T item;
		Iterator<Entry<String, Object>> it = lostItems.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> e = it.next();
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) e.getValue();
			item = mapper.parseMap(map);
			if (Long.valueOf((String) item.getPrimaryKeyObj()) != 0) {
				items.put(e.getKey(), item);
			} else {
				item.setNew(true);
				items.put(e.getKey(), item);
			}
		}
		return items;
	}

	public Map<T, String> getUnsyncedItemsSince(long date) throws UnsupportedEncodingException, FirebaseException {
		Map<T, String> unsyncedItems = new LinkedHashMap<T, String>();

		/* Get Liferay items that were added/updated after last sync time */
		List<T> lrItemList = getLiferayItemsAfter(date);
		/* Get Firebase items that were added/updated after last sync time */
		Map<String, T> fbItemSet = getFirebaseItemsAfter(date);

		Map<Serializable, T> lrItemSet = new LinkedHashMap<Serializable, T>();

		/* Convert list to map for easier access by itemId */
		for (T i : lrItemList) {
			lrItemSet.put(i.getPrimaryKeyObj(), i);
		}
		/* Add fbItem in fbItemSet */
		T lrItem, fbItem;
		for (Entry<String, T> e : fbItemSet.entrySet()) {
			fbItem = e.getValue();
			lrItem = lrItemSet.get(fbItem.getPrimaryKeyObj());
			if (lrItem != null) {
				/* item exists in FB and LR; compare modified date */
//				int dateComp = lrItem.getModifiedDate().compareTo(fbItem.getModifiedDate());
//				if (dateComp == 0) {
//					/* item has not changed; remove from lrItemSet */
//					lrItemSet.remove(lrItem.getPrimaryKeyObj()());
//				} else if (dateComp < 0) {
//					/* fbItem is more recent; add to result */
//					unsyncedItems.put(fbItem, e.getKey());
//				}
			} else {
				/* Item exists in FB but not in LR */
				unsyncedItems.put(fbItem, e.getKey());
			}
		}
		/* Add remaining LR items to result */
		for (Entry<Serializable, T> e : lrItemSet.entrySet()) {
			unsyncedItems.put(e.getValue(), "liferay");
		}
		
		return unsyncedItems;
	}

	/**
	 * Checks whether synchronization is enabled at portlet.properties
	 * @return true if Firebase replication is enabled at the Portlet Properties
	 */
	public boolean isSyncEnabled() {
		String firebaseSyncEnabled = PortletProps.get("firebase.sync.enabled");
		return Boolean.parseBoolean(firebaseSyncEnabled);
	}
	
	/**
	 * Blocks a thread until the 'future' is completed
	 * @param future Future object to wait for
	 * @return S, the object 'future' returns after it is done
	 */
	protected <S> S waitFor(Future<S> future) {
		try {
			if (future != null) {
				return future.get();	
			}
		} catch (InterruptedException | ExecutionException e) {
			_log.error("Previous method error: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Wraps a Callable object and executes it concurrently
	 * @param task The task to run
	 * @return A 'future' promise with the result of the task given
	 */
	protected <S> Future<S> asyncWrapper(Callable<S> task) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<S> future = executor.submit(task);
		executor.shutdown();
		return future;
	}

	protected final Log _log = LogFactoryUtil.getLog(this.getClass());
}


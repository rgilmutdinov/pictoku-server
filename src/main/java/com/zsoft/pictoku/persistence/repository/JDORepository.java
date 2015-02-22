package com.zsoft.pictoku.persistence.repository;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.zsoft.pictoku.persistence.PMF;

public abstract class JDORepository<T, ID> {

	protected Class<T> clazz;

	public JDORepository(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Invokes getters of the given object.
	 * Used for "touching" childs so we can access them after the persistence manager closes.
	 */
	private void invokeGetters(T object, Class<T> clazz) {
		try {
			for (PropertyDescriptor properyDescriptor : Introspector.getBeanInfo(clazz,
					Object.class).getPropertyDescriptors()) {
				Method method = properyDescriptor.getReadMethod();
				if (method != null) {
					method.invoke(object);
				}
			}
		} catch (IntrospectionException e) {
		} catch (IllegalAccessException e) {
		} catch (IllegalArgumentException e) {
		} catch (InvocationTargetException e) {
		}
	}

	public T get(ID id) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			T object = pm.getObjectById(clazz, id);
			invokeGetters(object, clazz);
			return object;
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<T> getAll() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = null;
		try {
			query = pm.newQuery(clazz);
			return new ArrayList<T> ((Collection<T>) query.execute());
		} finally {
			if (query != null) {
				query.closeAll();
			}
			pm.close();
		}
	}

	public T save(T entity) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			T result = pm.makePersistent(entity);
			tx.commit();
			return result;
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			pm.close();
		}
	}

	public void delete(T entity) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.detachCopy(entity);
		pm.deletePersistent(entity);
	}

	public void deleteAll(Collection<T> entities) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		pm.deletePersistentAll(entities);
	}
}

package com.zsoft.pictoku.persistence.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.stereotype.Service;

import com.zsoft.pictoku.model.GaeUser;
import com.zsoft.pictoku.persistence.PMF;

@Service
public class GaeUserRepository extends JDORepository<GaeUser, Long> {

	public GaeUserRepository() {
		super(GaeUser.class);
	}

	@SuppressWarnings("unchecked")
	public Collection<GaeUser> findByName(String name) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = null;
		try {
			query = pm.newQuery(GaeUser.class);
			query.setFilter("name == n");
			query.declareParameters("String n");
			return new ArrayList<GaeUser>((Collection<GaeUser>) query.execute(name));
		} finally {
			if (query != null) {
				query.closeAll();
			}
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public GaeUser findByEmail(String email) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = null;
		try {
			query = pm.newQuery(GaeUser.class);
			query.setFilter("email == n");
			query.declareParameters("String n");
			List<GaeUser> users = new ArrayList<GaeUser>((Collection<GaeUser>) query.execute(email));
			return users.isEmpty() ? null : users.get(0);
		} finally {
			if (query != null) {
				query.closeAll();
			}
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<GaeUser> getTopGifters() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = null;
		try {
			query = pm.newQuery(clazz);
			query.setOrdering("touches descending");
			return new ArrayList<GaeUser> ((Collection<GaeUser>) query.execute());
		} finally {
			if (query != null) {
				query.closeAll();
			}
			pm.close();
		}
	}
}

package com.zsoft.pictoku.persistence;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * Singleton for the App Engine Data Store persistence manager.
 */
public final class PMF {

	private static final PersistenceManagerFactory pmfInstance =
			JDOHelper.getPersistenceManagerFactory("transactions-optional");

	private PMF() {
	}

	/**
	 * Returns an instance of the Persistence Manager.
	 * @return persistence manager instance.
	 */
	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}
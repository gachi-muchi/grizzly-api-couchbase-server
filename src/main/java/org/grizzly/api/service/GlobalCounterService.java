package org.grizzly.api.service;

import javax.inject.Inject;

import org.grizzly.api.dao.BucketType;
import org.grizzly.api.dao.CouchbaseClientFactory;

import com.couchbase.client.CouchbaseClient;

public class GlobalCounterService  {

	private CouchbaseClient counterClient;

	@Inject
	public GlobalCounterService(CouchbaseClientFactory factory) {
		counterClient = factory.getClient(BucketType.COUNTER);
	}

	public String generateId() {
		return String.valueOf(counterClient.incr("global_id_counter", 1, 1));
	}
}

package org.grizzly.api.dao;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.grizzly.api.config.Config;

import com.couchbase.client.CouchbaseClient;

public class CouchbaseClientFactory {

	private static Map<String, CouchbaseClient> clientMap = new HashMap<>();

	@Inject
	public CouchbaseClientFactory(Config config) throws URISyntaxException, IOException {
		List<String> urls = config.getCouchBaseUrls();
		List<URI> hosts = new ArrayList<URI>();
		for (String u : urls) {
			hosts.add(new URI(u));
		}

		for (BucketType type : BucketType.values()) {
			CouchbaseClient client = new CouchbaseClient(hosts, type.code(), "");
			clientMap.put(type.code(), client);
		}
	}

	public CouchbaseClientFactory(List<String> urls) throws URISyntaxException, IOException {
		List<URI> uris = new ArrayList<URI>();
		for (String u : urls) {
			uris.add(new URI(u));
		}

		for (BucketType type : BucketType.values()) {
			CouchbaseClient client = new CouchbaseClient(uris, type.code(), "");
			clientMap.put(type.code(), client);
		}
	}

	public CouchbaseClient getClient(BucketType bucketType) {
		return clientMap.get(bucketType.code());
	}

	public void shutdonw() {
		for (CouchbaseClient client : clientMap.values()) {
			client.shutdown(1L, TimeUnit.SECONDS);
		}
	}
}

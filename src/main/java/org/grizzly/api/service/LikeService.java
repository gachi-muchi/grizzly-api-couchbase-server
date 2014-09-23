package org.grizzly.api.service;

import java.util.Date;

import javax.inject.Inject;

import org.grizzly.api.dao.BucketType;
import org.grizzly.api.dao.CouchbaseClientFactory;
import org.grizzly.api.entity.Like;
import org.grizzly.utils.JsonUtils;

import com.couchbase.client.CouchbaseClient;

public class LikeService {

	private CouchbaseClient likeClient;

	@Inject
	public LikeService(CouchbaseClientFactory factory) {
		likeClient = factory.getClient(BucketType.LIKE);
	}

	public long like(String id, String userId) {
		if (likeClient.get(id + "." + userId) != null) {
			return getCount(id);
		}

		Like like = new Like();
		like.setToId(id);
		like.setFromId(userId);
		like.setDate(new Date());
		likeClient.set(id + "." + userId, JsonUtils.toString(like));

		return likeClient.incr(id + ".count", 1, 1);
	}

	public long unLike(String id, String userId) {
		if (likeClient.get(id + "." + userId) == null) {
			return getCount(id);
		}
		likeClient.delete(id + "." + userId);
		return likeClient.decr(id + ".count", 1);
	}

	public long getCount(String id) {
		Object obj = likeClient.get(id + ".count");
		return obj == null ? 0L : Long.valueOf((String) obj);
	}

}
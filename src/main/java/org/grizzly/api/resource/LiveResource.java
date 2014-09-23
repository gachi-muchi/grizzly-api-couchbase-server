package org.grizzly.api.resource;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("live")
@Singleton
public class LiveResource {

	@GET
	public boolean live() {
		return true;
	}
}
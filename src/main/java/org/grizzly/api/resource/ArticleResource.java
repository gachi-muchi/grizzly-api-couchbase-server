package org.grizzly.api.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.grizzly.api.entity.Article;
import org.grizzly.api.service.ArticleService;
import org.grizzly.api.service.LikeService;

@Path("/blog/{userId}/article")
public class ArticleResource {

	@Inject
	private ArticleService articleService;

	@Inject
	private LikeService likeService;

	@GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public List<Article> list(@PathParam("userId") String userId) {
		return articleService.list(userId, 0 , 20);
	}

	@POST
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Article create(@PathParam("userId") String userId, Article article) {
		return articleService.create(userId, article);
	}

	@GET
	@Path("{articleId}")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Article read(
			@PathParam("userId") String userId, @PathParam("articleId") String articleId) {
		return articleService.read(articleId);
	}

	@PUT
	@Path("{articleId}")
	@Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response update(@PathParam("userId") String userId, @PathParam("articleId") String articleId, Article article) {
		articleService.update(articleId, article);
		return Response.ok().build();
	}

	@DELETE
	@Path("{articleId}")
	public Response delete(@PathParam("userId") String userId, @PathParam("articleId") String articleId) {
		articleService.delete(userId, articleId);
		return Response.ok().build();
	}

	@POST
	@Path("{articleId}/like")
	@Consumes({ MediaType.APPLICATION_JSON })
	public long like(@PathParam("userId") String userId, @PathParam("articleId") String articleId) {
		return likeService.like(articleId, userId);
	}

	@GET
	@Path("{articleId}/like")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public long getLikeCount(
			@PathParam("userId") String userId, @PathParam("articleId") String articleId) {
		return likeService.getCount(articleId);
	}

	@DELETE
	@Path("{articleId}/like")
	public long unLike(@PathParam("userId") String userId, @PathParam("articleId") String articleId) {
		return likeService.unLike(articleId, userId);
	}
}

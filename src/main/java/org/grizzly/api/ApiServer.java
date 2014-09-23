package org.grizzly.api;

import java.net.URI;

import javax.inject.Singleton;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.grizzly.api.config.Config;
import org.grizzly.api.dao.CouchbaseClientFactory;
import org.grizzly.api.resource.ArticleResource;
import org.grizzly.api.resource.LiveResource;
import org.grizzly.api.service.ArticleService;
import org.grizzly.api.service.GlobalCounterService;
import org.grizzly.api.service.LikeService;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiServer {

	private Config config;

	public static void main(String[] args) throws Exception {
		ApiServer apiServer = new ApiServer();
		apiServer.loadConfig(args.length > 0 ? args[0] : "default.json");
		apiServer.start();
	}

	private void loadConfig(String path) throws Exception {
		config =
				new ObjectMapper()
						.readValue(ApiServer.class.getClassLoader().getResourceAsStream(path),
								Config.class);
	}

	public void start() throws Exception {
		// port
		URI uri = UriBuilder.fromUri(config.getBaseUrl()).port(config.getPort()).build();

		// resource
		ResourceConfig resourceConfig = new ResourceConfig();
		bindService(resourceConfig);
		bindResource(resourceConfig);

		// create server
		HttpServer server =
				GrizzlyHttpServerFactory.createHttpServer(uri, resourceConfig, false);
		// worker setting
		settingWorkerPool(server);

		// server start
		server.start();
		System.in.read();
	}

	private void settingWorkerPool(HttpServer server) {
		NetworkListener listener = server.getListener("grizzly");
		TCPNIOTransport transport = listener.getTransport();
		ThreadPoolConfig threadPoolConfig = ThreadPoolConfig.defaultConfig();
		threadPoolConfig.setCorePoolSize(config.getWorkerCorePool());
		threadPoolConfig.setMaxPoolSize(config.getWorkerMaxPool());
		transport.setWorkerThreadPoolConfig(threadPoolConfig);
		listener.setTransport(transport);
		server.addListener(listener);
	}

	private void bindResource(ResourceConfig resourceConfig) {
		resourceConfig.register(JacksonFeature.class);
		resourceConfig.register(LiveResource.class);
		resourceConfig.register(ArticleResource.class);
	}

	private void bindService(ResourceConfig resourceConfig) {
		resourceConfig.register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(config);
				bindAsContract(CouchbaseClientFactory.class).in(Singleton.class);
				bindAsContract(GlobalCounterService.class).in(Singleton.class);
				bindAsContract(ArticleService.class).in(Singleton.class);
				bindAsContract(LikeService.class).in(Singleton.class);
			}
		});
	}

}

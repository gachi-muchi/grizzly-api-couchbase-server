package org.grizzly.api.config;

import java.util.List;

public class Config {

	private String baseUrl = "http://localhost";

	private int port = 9000;

	private List<String> couchBaseUrls;

	private int workerCorePool;

	private int workerMaxPool;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public List<String> getCouchBaseUrls() {
		return couchBaseUrls;
	}

	public void setCouchBaseUrls(List<String> couchBaseUrls) {
		this.couchBaseUrls = couchBaseUrls;
	}

	public int getWorkerCorePool() {
		return workerCorePool;
	}

	public void setWorkerCorePool(int workerCorePool) {
		this.workerCorePool = workerCorePool;
	}

	public int getWorkerMaxPool() {
		return workerMaxPool;
	}

	public void setWorkerMaxPool(int workerMaxPool) {
		this.workerMaxPool = workerMaxPool;
	}

}

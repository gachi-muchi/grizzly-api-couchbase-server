package org.grizzly.api.dao;

public enum BucketType {
	LIKE("like", ""),
	USER("user", ""),
	ARTICLE("article", ""),
	COUNTER("counter", ""),
	;

	private String code;

	private String password;

	private BucketType(String code, String password) {
		this.code = code;
		this.password = password;
	}

	public String code() {
		return code;
	}

	public String password() {
		return password;
	}
}

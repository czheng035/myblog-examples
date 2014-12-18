package com.gmail.czheng035.moves.api.exception;

public class ApiContextNotInitialized extends RuntimeException {

	private static final long serialVersionUID = 7298541672320050481L;

	public ApiContextNotInitialized() {
		super("API initialization not run successfully");
	}
}

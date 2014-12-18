package com.gmail.czheng035.moves.api.exception;

public class ApiManagerNotInitialized extends RuntimeException {

	private static final long serialVersionUID = 7298541672320050481L;

	public ApiManagerNotInitialized() {
		super("API Manager initialization not run successfully");
	}
}

package com.gmail.czheng035.moves.api.entity;

import java.util.Date;

public class Profile {
	
	private static final String PATH = "/user/profile";
	
	private long id;
	private Date firstDate;
	private TimeZone currentTimeZone;
	
	boolean caloriesAvailable;
	String platform;
	
	public static class TimeZone {
		private String id;
		private int offset;
	}
	
	public static class Localization {
		private String language;
		private String locale;
		private int firstWeekDay;
		private boolean metric;
	}
}

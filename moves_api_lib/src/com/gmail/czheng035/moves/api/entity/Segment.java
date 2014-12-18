package com.gmail.czheng035.moves.api.entity;

import java.util.Date;
import java.util.List;

public class Segment {
	String type;	
	Date startTime;
	Date endTime;
	Place place;
	List<ActivityInstantce> activities;
	Date lastUpdate;
}

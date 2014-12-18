package com.gmail.czheng035.moves.api.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ActivityInstantce {
	String activity;
	String group;
	boolean manual;
	Date startTime;
	Date endTime;
	int duration;
	Map<String, Integer> measurements;
	List<TrackPoint> trackPoints;
}

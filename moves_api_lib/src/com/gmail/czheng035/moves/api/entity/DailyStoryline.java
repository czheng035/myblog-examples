package com.gmail.czheng035.moves.api.entity;

import java.util.Date;
import java.util.List;

public class DailyStoryline {
	Date date;
	List<ActivitySummary> activitySummaries;
	List<Segment> segments;
	int caloriesIdle;
	Date lastUpdate;
}

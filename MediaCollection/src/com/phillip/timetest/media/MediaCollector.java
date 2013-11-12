package com.phillip.timetest.media;

import java.util.List;

public interface MediaCollector {
	void addMediaCollectionTask(MediaCollectionTask mediaCollectionTask);
	List<MediaCollectionTask> getMediaCollectionTasks();
}

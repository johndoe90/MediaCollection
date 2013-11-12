package com.phillip.timetest.media;

import org.jsoup.nodes.Document;

import com.phillip.timetest.domain.Category;
import com.phillip.timetest.domain.Media;

public interface MediaMapper {

	String getUrl(Document document);
	String getType(Document document);
	String getTitle(Document document);
	String getDescription(Document document);
	String getImage(Document document);
	String getKeywords(Document document);
	Long getUtcDate(Document document);
	Category getCategory(Document document);
	
	Media map(Document document);
}

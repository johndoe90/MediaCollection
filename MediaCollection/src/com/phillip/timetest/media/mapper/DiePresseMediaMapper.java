package com.phillip.timetest.media.mapper;

import org.jsoup.nodes.Document;

import com.phillip.timetest.domain.Media;
import com.phillip.timetest.domain.MediaProvider;
import com.phillip.timetest.media.AbstractMediaMapper;

public class DiePresseMediaMapper extends AbstractMediaMapper{

	@Override
	public Media map(Document document) {
		return new Media(getUrl(document), 
		         getType(document), 
		         getTitle(document), 
		         getDescription(document), 
		         getImage(document), 
		         getKeywords(document),
		         getUtcDate(document),
		         getCategory(document),
		         new MediaProvider("derStandard.at"));
	}

	@Override
	public Long getUtcDate(Document document) {
		return null;
	}

}

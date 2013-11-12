package com.phillip.timetest.media;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.phillip.timetest.domain.Category;
import com.phillip.timetest.domain.MediaProvider;

public abstract class AbstractMediaMapper implements MediaMapper{
	
	protected String getMetaProperty(Document document, String cssQuery){
		Element element = document.select(cssQuery).first();
		
		return element != null ? element.attr("content") : null;
	}
	
	protected boolean isValidMedia(Document document){
		return (getUrl(document) != null && getType(document) != null && getTitle(document) != null && getDescription(document) != null && getUtcDate(document) != null);
	}
	
	@Override
	public String getUrl(Document document) {
		return getMetaProperty(document, "meta[property=og:url]");
	}

	@Override
	public String getType(Document document) {
		return getMetaProperty(document, "meta[property=og:type]");
	}

	@Override
	public String getTitle(Document document) {
		return getMetaProperty(document, "meta[property=og:title]");
	}

	@Override
	public String getDescription(Document document) {
		return getMetaProperty(document, "meta[property=og:description]");
	}

	@Override
	public String getImage(Document document) {
		return getMetaProperty(document, "meta[property=og:image]");
	}

	@Override
	public String getKeywords(Document document) {
		String keywords = getMetaProperty(document, "meta[name=news_keywords]");
		if(keywords != null)
			return keywords;
		
		keywords = getMetaProperty(document, "meta[name=keywords]");
		if(keywords != null)
			return keywords;
		
		return getTitle(document);
	}

	@Override
	public Long getUtcDate(Document document) {
		return null;
	}
	
	@Override
	public Category getCategory(Document document) {
		return null;
	}
}

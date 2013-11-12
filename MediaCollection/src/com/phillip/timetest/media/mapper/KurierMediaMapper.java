package com.phillip.timetest.media.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.jsoup.nodes.Document;

import com.phillip.timetest.domain.Categories;
import com.phillip.timetest.domain.Category;
import com.phillip.timetest.domain.Media;
import com.phillip.timetest.domain.MediaProvider;
import com.phillip.timetest.domain.MediaProviders;
import com.phillip.timetest.media.AbstractMediaMapper;

public class KurierMediaMapper extends AbstractMediaMapper{
	
	@Override
	public Long getUtcDate(Document document) {
		String oldDate = getMetaProperty(document, "meta[name=sailthru.date]");
		if(oldDate != null){
			try{
				String[] parts = oldDate.split(" ");
				String newDate = parts[1] + "-" + parts[2] + "-" + parts[3] + " " + parts[4];
				return new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss", Locale.ENGLISH).parse(newDate).getTime();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	

	@Override
	public Category getCategory(Document document) {
		String url = document.baseUri();
		String[] parts = url.replaceAll("http://|https://", "").split("/");
		String superCategory = parts[1].toLowerCase();
		String subCategory = parts[2].toLowerCase();
		
		switch(superCategory){
			case "politik":
				switch(subCategory){
					case "inland":
						return Categories.POLITICS_DOMESTIC;
					case "ausland":
						return Categories.POLITICS_FOREIGN;
					case "eu":
						return Categories.POLITICS_EU;
					case "weltchronik":
						return Categories.POLITICS_WORLD;
					default:
						return Categories.POLITICS;
				}
			default:
				return Categories.OTHER;
		}
	}



	@Override
	public Media map(Document document) {
		if(isValidMedia(document)){
			return new Media(getUrl(document), 
			         getType(document), 
			         getTitle(document), 
			         getDescription(document), 
			         getImage(document), 
			         getKeywords(document),
			         getUtcDate(document),
			         getCategory(document),
			         MediaProviders.kurier);
		}
		
		return null;
	}

}

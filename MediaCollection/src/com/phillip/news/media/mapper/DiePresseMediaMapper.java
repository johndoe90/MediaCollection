package com.phillip.news.media.mapper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jsoup.nodes.Document;

import com.phillip.news.media.AbstractMediaMapper;
import com.phillip.news.domain.Categories;
import com.phillip.news.domain.Category;
import com.phillip.news.domain.Media;
import com.phillip.news.domain.MediaProvider;

public class DiePresseMediaMapper extends AbstractMediaMapper{

	/*public KurierMediaMapper(MediaProvider mediaProvider){
		super(mediaProvider);
	}
	
	@Override
	public Long getDate(Document document) {
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
					case "inland": return Categories.POLITICS_DOMESTIC;
					case "ausland": return Categories.POLITICS_FOREIGN;
					case "eu": return Categories.POLITICS_EU;
					case "weltchronik": return Categories.POLITICS_WORLD;
					default: return Categories.POLITICS;
				}
			case "sport":
				switch(subCategory){
					case "fussball": return Categories.SPORTS_FOOTBALL;
					case "wintersport": return Categories.SPORTS_WINTERSPORTS;
					case "sportmix": return Categories.SPORTS;
					case "motorsport": return Categories.SPORTS_MOTORSPORTS;
					default: return Categories.SPORTS;
				}
			default:
				return Categories.OTHER;
		}
	}*/
	
	public DiePresseMediaMapper(MediaProvider mediaProvider) {
		super(mediaProvider);
	}

	@Override
	public Long getDate(Document document) {
		return new Date().getTime();
	}

	@Override
	public Category getCategory(Document document) {
		return Categories.POLITICS_FOREIGN;
	}
}

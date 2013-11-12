package com.phillip.timetest.media.collector.article;

import java.util.Arrays;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.phillip.timetest.domain.Media;
import com.phillip.timetest.media.AbstractArticleCollector;
import com.phillip.timetest.service.MediaService;

public class DiePresseArticleCollectionTask extends AbstractArticleCollector{

	private final MediaService mediaService;
	
	public DiePresseArticleCollectionTask(MediaService mediaService){
		this(mediaService,
			 new ArticleCollectionTaskConfiguration()
				.setMaxLevel(1)
				.setPause(1000)
				.setSeeds(Arrays.asList("http://diepresse.com/home/politik/innenpolitik")));
	}
	public DiePresseArticleCollectionTask(MediaService mediaService, ArticleCollectionTaskConfiguration config) {
		super(config);
		this.mediaService = mediaService;
	}

	@Override
	protected boolean shouldVisit(String URL) {
		if(getConfig().getFilters().matcher(URL).matches())
			return false;
		
		for(String domain : getConfig().getSeeds()){
			if(URL.startsWith(domain) && !URL.contains("#"))
				return true;
		}
		
		return false;
	}
	
	@Override
	protected void visit(Document doc) {
		/*Element metaUrl = doc.select("meta[property=og:url]").first();
		Element metaType = doc.select("meta[property=og:type]").first();
		Element metaDesc = doc.select("meta[property=og:description]").first();
		Element metaTitle = doc.select("meta[property=og:title]").first();
		Element metaImage = doc.select("meta[property=og:image]").first();
		
		if(metaUrl != null && metaType != null && metaDesc != null && metaTitle != null && metaImage != null){
			String url = metaUrl.attr("content");
			String title = metaTitle.attr("content");
			String desc = metaDesc.attr("content");
			String type = metaType.attr("content").toLowerCase();
			String image = metaImage.attr("content");
			
			if(!mediaService.mediaExists(url)){
				Media media = new Media(url, type, title, desc, image, "keywords");
				mediaService.persist(media);
			}
		}*/
	}

}

package com.phillip.timetest.media.collector.article;

import java.util.Arrays;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.phillip.timetest.domain.Media;
import com.phillip.timetest.media.AbstractArticleCollector;
import com.phillip.timetest.media.mapper.KurierMediaMapper;
import com.phillip.timetest.service.MediaService;

public class KurierArticleCollectionTask extends AbstractArticleCollector{
	
	private final MediaService mediaService;
	private final KurierMediaMapper mediaMapper;
	
	public KurierArticleCollectionTask(ArticleCollectionTaskConfiguration config, MediaService mediaService){
		super(config);
		this.mediaService = mediaService;
		this.mediaMapper = new KurierMediaMapper();
	}

	@Override
	protected boolean shouldVisit(String URL) {
		if(getConfig().getFilters().matcher(URL).matches())
			return false;
		
		//contains # in filter regex reinschmeißen
		for(String domain : getConfig().getSeeds()){
			if(URL.startsWith(domain) && !URL.endsWith("/print") && !URL.contains("#"))
				return true;
		}
		
		return false;
	}
	
	@Override
	protected void visit(Document document) {
		Media media = mediaMapper.map(document);
		if(media != null && !mediaService.mediaExists(media.getUrl())){
			mediaService.persist(media);
		}
	}
	
	
}

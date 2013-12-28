package com.phillip.news.media.collector.article;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.phillip.news.config.FilesConfig;
import com.phillip.news.media.AbstractArticleCollector;
import com.phillip.news.media.mapper.KurierMediaMapper;
import com.phillip.news.service.MediaService;
import com.phillip.news.utils.ImageUtils;
import com.phillip.news.domain.Media;
import com.phillip.news.domain.MediaProvider;

public class KurierArticleCollectionTask extends AbstractArticleCollector{
	
	private final MediaService mediaService;
	private final KurierMediaMapper mediaMapper;
	
	public KurierArticleCollectionTask(ArticleCollectionTaskConfiguration config, MediaProvider mediaProvider, MediaService mediaService){
		super(config);
		this.mediaService = mediaService;
		this.mediaMapper = new KurierMediaMapper(mediaProvider);
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
		if(media != null && !mediaService.exists(media.getUrl())){
			Map<String, String> links = ImageUtils.buildImageTree(media.getImageSmall());
			media.setImageSmall(links.get("small") != null ? links.get("small") : media.getImageSmall());
			media.setImageMedium(links.get("medium"));
			media.setImageLarge(links.get("large"));
			media.setImageWidth(links.get("width") != null ? Integer.parseInt(links.get("width")) : null);
			media.setImageHeight(links.get("height") != null ? Integer.parseInt(links.get("height")) : null);
			
			mediaService.persist(media);
		}
	}
	
	
}

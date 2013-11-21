package com.phillip.news.media.collector.article;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.phillip.news.config.FilesConfig;
import com.phillip.news.domain.Media;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.media.AbstractArticleCollector;
import com.phillip.news.media.mapper.KurierMediaMapper;
import com.phillip.news.service.MediaService;
import com.phillip.news.utils.ImageUtils;

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
			try{
				URL url = new URL(media.getImage());
				BufferedImage original = ImageIO.read(url);
				BufferedImage resized = ImageUtils.fitToFrame(original, 250, 250);		
				String random = ImageUtils.createRandomFileName(15);
				String fileName;
				do { 
					fileName = FilesConfig.TOMCAT_RESOURCE_LOCATION + "/" + random + ".jpg";
				}while(new File(fileName).exists());
				if(ImageIO.write(resized, "jpg", new File(fileName))){
					media.setImage(FilesConfig.PREFIX + "/" + random + ".jpg");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			mediaService.persist(media);
		}
	}
	
	
}

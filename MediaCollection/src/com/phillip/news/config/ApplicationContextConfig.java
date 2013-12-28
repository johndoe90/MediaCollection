package com.phillip.news.config;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.phillip.news.media.MediaCollector;
import com.phillip.news.media.MediaCollectorImpl;
import com.phillip.news.media.collector.MediaCollectors;
import com.phillip.news.media.collector.article.ArticleCollectionTaskConfiguration;
import com.phillip.news.media.collector.article.DiePresseArticleCollectionTask;
import com.phillip.news.media.collector.article.KurierArticleCollectionTask;
import com.phillip.news.service.MediaService;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.domain.MediaProviders;

@Configuration
@Import({PersistenceConfig.class})
@ComponentScan(basePackages = "com.phillip.news")
@PropertySource("classpath:com/phillip/news/config/application.properties")
public class ApplicationContextConfig {
	@Inject
	private Environment env;
	
	@Inject
	private MediaService mediaService;
	
	private List<String> assembleSeeds(MediaProvider mediaProvider){
		String[] seeds = env.getRequiredProperty("mediaProviders." + mediaProvider.getMediaProviderId() + ".seeds").split(" ");
		
		return Arrays.asList(seeds);
	}
	
	private ArticleCollectionTaskConfiguration assembleConfiguration(MediaProvider mediaProvider){
		ArticleCollectionTaskConfiguration config = new ArticleCollectionTaskConfiguration();
		config.setPause(1000);
		config.setTimeout(10000);
		config.setSeeds(assembleSeeds(mediaProvider));
		config.setHistoryLocation(env.getRequiredProperty("mediaProviders.historyLocation") + "/" + mediaProvider.getMediaProviderId());
		
		return config;
	}
	
	@Bean
	public MediaCollectors mediaCollectors(){
		MediaCollectors mediaCollectors = new MediaCollectors();
		mediaCollectors.addMediaCollector(kurierMediaCollector());
		mediaCollectors.addMediaCollector(diePresseMediaCollector());
		
		return mediaCollectors;
		
	}
	
	public MediaCollector kurierMediaCollector(){
		MediaCollector kurierMediaCollector = new MediaCollectorImpl();
		ArticleCollectionTaskConfiguration config = assembleConfiguration(MediaProviders.KURIER);
		KurierArticleCollectionTask kurierArticleCollectionTask = 
			new KurierArticleCollectionTask(config, MediaProviders.KURIER, mediaService);
		kurierMediaCollector.addMediaCollectionTask(kurierArticleCollectionTask);
		
		return kurierMediaCollector;
	}
	
	public MediaCollector diePresseMediaCollector(){
		MediaCollector diePresseMediaCollector = new MediaCollectorImpl();
		ArticleCollectionTaskConfiguration config = assembleConfiguration(MediaProviders.DIE_PRESSE);
		DiePresseArticleCollectionTask diePresseArticleCollectionTask = 
			new DiePresseArticleCollectionTask(config, MediaProviders.DIE_PRESSE, mediaService);
		diePresseMediaCollector.addMediaCollectionTask(diePresseArticleCollectionTask);
		
		return diePresseMediaCollector;
	}
}

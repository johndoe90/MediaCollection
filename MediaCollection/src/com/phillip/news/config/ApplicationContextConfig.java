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

import com.phillip.news.domain.MediaProvider;
import com.phillip.news.media.MediaCollector;
import com.phillip.news.media.MediaCollectorImpl;
import com.phillip.news.media.collector.MediaCollectors;
import com.phillip.news.media.collector.article.ArticleCollectionTaskConfiguration;
import com.phillip.news.media.collector.article.KurierArticleCollectionTask;
import com.phillip.news.service.MediaService;

@Configuration
@Import({PersistenceConfig.class})
@ComponentScan(basePackages = "com.phillip.timetest")
@PropertySource("classpath:com/phillip/timetest/config/application.properties")
public class ApplicationContextConfig {

	@Inject
	private MediaService mediaService;
	
	@Inject
	private Environment env;
	
	private List<String> getSeedsForProvider(String provider){
		return Arrays.asList(env.getProperty(provider + ".seeds").split(" "));
	}
	
	private ArticleCollectionTaskConfiguration getConfigurationForProvider(String provider){
		ArticleCollectionTaskConfiguration config = new ArticleCollectionTaskConfiguration();
		config.setSeeds(getSeedsForProvider(provider));
		config.setHistoryLocation(env.getProperty("historyLocation") + "/" + env.getProperty(provider + ".mediaProviderId"));
		config.setPause(1000);
		
		return config;
	}
	
	@Bean
	public MediaCollectors mediaCollectors(){
		MediaCollectors mediaCollectors = new MediaCollectors();
		mediaCollectors.addMediaCollector(kurierMediaCollector());
		
		return mediaCollectors;
		
	}
	
	@Bean
	public MediaCollector kurierMediaCollector(){
		MediaCollector kurierMediaCollector = new MediaCollectorImpl();
		KurierArticleCollectionTask kurierArticleCollectionTask = new KurierArticleCollectionTask(getConfigurationForProvider("kurier"), 
				 																				  new MediaProvider(env.getProperty("kurier.mediaProviderId"), null, null, null, null),
				 																				  mediaService);
		kurierMediaCollector.addMediaCollectionTask(kurierArticleCollectionTask);
		
		return kurierMediaCollector;
	}
}

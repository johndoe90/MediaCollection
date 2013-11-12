package com.phillip.timetest.config;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.phillip.timetest.media.MediaCollector;
import com.phillip.timetest.media.MediaCollectorImpl;
import com.phillip.timetest.media.collector.MediaCollectors;
import com.phillip.timetest.media.collector.article.ArticleCollectionTaskConfiguration;
import com.phillip.timetest.media.collector.article.KurierArticleCollectionTask;
import com.phillip.timetest.service.MediaService;

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
		return Arrays.asList(env.getProperty(provider + ".articleCollector.seeds").split(","));
	}
	
	private ArticleCollectionTaskConfiguration getConfigurationForProvider(String provider){
		ArticleCollectionTaskConfiguration config = new ArticleCollectionTaskConfiguration();
		config.setSeeds(getSeedsForProvider(provider));
		config.setHistoryLocation("/home/johndoe/Dokumente/SoftwareDevelopment/springsource workspace/TimeTest/data/ArticleCollectors/" + provider);
		
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
		KurierArticleCollectionTask kurierArticleCollectionTask = new KurierArticleCollectionTask(getConfigurationForProvider("kurier"), mediaService);
		kurierMediaCollector.addMediaCollectionTask(kurierArticleCollectionTask);
		
		return kurierMediaCollector;
	}
}

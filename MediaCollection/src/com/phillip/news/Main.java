package com.phillip.news;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.phillip.news.config.ApplicationContextConfig;
import com.phillip.news.media.MediaCollectionTask;
import com.phillip.news.media.MediaCollector;
import com.phillip.news.media.collector.MediaCollectors;
import com.phillip.news.service.CategoryService;
import com.phillip.news.domain.Category;

public class Main {
	
	private static final Integer numberOfThreads = 1;
	private static final Integer threadPoolSize = 2;
	
	private static ExecutorService startExecution(MediaCollectors mediaCollectors){
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
		for(MediaCollector mediaCollector : mediaCollectors.getMediaCollectors()){
			for(MediaCollectionTask task : mediaCollector.getMediaCollectionTasks()){
				for(int i = 0; i < numberOfThreads; i++){
					executor.execute(task);
				}
			}
		}
		executor.shutdown();
		
		return executor;
	}
	
	public static void main(String[] args) throws Exception{
		ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextConfig.class);
		MediaCollectors mediaCollectors = context.getBean(MediaCollectors.class);
		ExecutorService executor = startExecution(mediaCollectors);
		
		while(true){
			if(executor.isTerminated()){
				executor = startExecution(mediaCollectors);
				System.out.println("LOOKING FOR NEW ARTICLES " + new Date());
			}
			
			Thread.sleep(300000);
		}
	}
}

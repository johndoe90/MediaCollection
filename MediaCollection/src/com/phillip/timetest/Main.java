package com.phillip.timetest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.phillip.timetest.config.ApplicationContextConfig;
import com.phillip.timetest.media.MediaCollectionTask;
import com.phillip.timetest.media.MediaCollector;
import com.phillip.timetest.media.collector.MediaCollectors;
import com.phillip.timetest.utils.MyFileUtils;

public class Main {
	
	private static final Integer numberOfThreads = 1;
	private static final Integer threadPoolSize = 1;
	
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
			
			Thread.sleep(5000);
		}
		
	}
}

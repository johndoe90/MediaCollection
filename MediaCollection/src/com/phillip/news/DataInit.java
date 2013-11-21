package com.phillip.news;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.phillip.news.domain.Categories;
import com.phillip.news.domain.Category;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.service.CategoryService;
import com.phillip.news.service.MediaProviderService;

@Component
public class DataInit implements InitializingBean{

	@Inject
	private Environment env;
	
	@Inject
	private MediaProviderService mediaProviderService;
	
	@Inject
	private CategoryService categoryService;
	
	private MediaProvider assembleMediaProvider(String mediaProviderId){
		String mediaProviderName = env.getProperty(mediaProviderId + ".mediaProviderName");
		String logoSmall = env.getProperty(mediaProviderId + ".logoSmall");
		String logoMedium = env.getProperty(mediaProviderId + ".logoMedium");
		String logoLarge = env.getProperty(mediaProviderId + ".logoLarge");
		MediaProvider provider = new MediaProvider(mediaProviderId, mediaProviderName, logoSmall, logoMedium, logoLarge);
		
		return provider;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(env.getProperty("hibernate.hbm2ddl.auto").contains("create")){
			String[] mediaProviderIds = env.getProperty("mediaProviders").split(",");
			
			MediaProvider mediaProvider;
			for(String mediaProviderId : mediaProviderIds){
				mediaProvider = assembleMediaProvider(mediaProviderId);
				mediaProviderService.persist(mediaProvider);
			}
			
			for(Category category : Categories.getCategories()){
				categoryService.persist(category);
			}
		}
	}

}

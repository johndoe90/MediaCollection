package com.phillip.news;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.phillip.news.domain.Category;
import com.phillip.news.domain.CategoryTranslation;
import com.phillip.news.domain.Language;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.service.CategoryService;
import com.phillip.news.service.LanguageService;
import com.phillip.news.service.MediaProviderService;

@Component
public class DataInit implements InitializingBean{
	
	@Inject
	private Environment env;
	
	@Inject
	private MediaProviderService mediaProviderService;
	
	@Inject
	private CategoryService categoryService;
	
	@Inject
	private LanguageService languageService;
	
	private Map<String, Language> languages = new HashMap<String, Language>();
	private Map<String, Category> categories = new HashMap<String, Category>();
	private Map<String, MediaProvider> mediaProviders = new HashMap<String, MediaProvider>();
	
	private Language assembleLanguage(String languageId){
		String languageCode = env.getRequiredProperty("languages." + languageId + ".code");
		
		return new Language(languageCode);
	}
	
	private void initializeLanguages(){
		Language language;
		String[] langs =  env.getRequiredProperty("languages").split(",");
		for(String lang : langs){
			language = assembleLanguage(lang);
			language = languageService.persist(language);
			languages.put(lang, language);
		}
	}
	
	private MediaProvider assembleMediaProvider(String mediaProviderId){
		String mediaProviderName = env.getRequiredProperty("mediaProviders." + mediaProviderId + ".mediaProviderName");
		String logoSmall = env.getRequiredProperty("mediaProviders." + mediaProviderId + ".logoSmall");
		String logoMedium = env.getRequiredProperty("mediaProviders." + mediaProviderId + ".logoMedium");
		String logoLarge = env.getRequiredProperty("mediaProviders." + mediaProviderId + ".logoLarge");
		
		return new MediaProvider(mediaProviderId, mediaProviderName, logoSmall, logoMedium, logoLarge);
	}
	
	private void initializeMediaProviders(){
		MediaProvider mediaProvider;
		String[] provs = env.getRequiredProperty("mediaProviders").split(",");
		for(String prov : provs){
			mediaProvider = assembleMediaProvider(prov);
			mediaProvider = mediaProviderService.persist(mediaProvider);
			mediaProviders.put(prov, mediaProvider);
		}
	}
	
	private List<CategoryTranslation> assembleCategoryTranslations(String categoryId, Category category){
		List<CategoryTranslation> translations = new ArrayList<CategoryTranslation>();
		for(Map.Entry<String, Language> entry : languages.entrySet()){
			translations.add(
				new CategoryTranslation(
					env.getRequiredProperty("categories." + categoryId + ".name." + entry.getKey()),
					category,
					entry.getValue()
				)	
			);
		}
		
		return translations;
	}
	
	private Category assembleCategory(String categoryId){
		String parentName = env.getRequiredProperty("categories." + categoryId + ".parent");
		Category parent = parentName.isEmpty() ? null : categories.get(parentName);
		String qualifiedName = env.getRequiredProperty("categories." + categoryId + ".qualifiedName");

		return new Category(qualifiedName, parent, new ArrayList<CategoryTranslation>());
	}
	
	private void initializeCategories(){
		Category category;
		List<CategoryTranslation> translations;
		String[] cats = env.getRequiredProperty("categories").split(",");
		for(String cat : cats){
			category = assembleCategory(cat);
			translations = assembleCategoryTranslations(cat, category);
			category.setTranslations(translations);
			category = categoryService.persist(category);
			
			categories.put(cat, category);
		}
	}

	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(env.getRequiredProperty("hibernate.hbm2ddl.auto").contains("create")){
			initializeLanguages();
			initializeMediaProviders();
			initializeCategories();
		}
	}
	
	/*public MediaProvider randProvider(List<MediaProvider> providers){
		Random rand = new Random();
		
		return providers.get(rand.nextInt(providers.size()));
	}
	
	public Category randCategory(List<Category> categories){
		Random rand = new Random();
		
		return categories.get(rand.nextInt(categories.size()));
	}
	
	@Transactional
	public void insertTestData(){
		String[] mediaProviderIds = env.getProperty("mediaProviders").split(",");
		
		MediaProvider mediaProvider;
		List<MediaProvider> providers = new ArrayList<MediaProvider>();
		for(String mediaProviderId : mediaProviderIds){
			mediaProvider = assembleMediaProvider(mediaProviderId);
			providers.add(mediaProviderService.persist(mediaProvider));
		}
		
		List<Category> categories = new ArrayList<Category>();
		for(Category category : Categories.getCategories()){
			categories.add(categoryService.persist(category));
		}
		
		Media media;	
		for(int i = 0; i < 100000; i++){
			media = new Media(randProvider(providers), randCategory(categories), new Date().getTime(), "Test titel", "article", "http://kurier.at/menschen/international/jfk-attentat-wo-waren-die-stars/37.002.395/slideshow" + ImageUtils.createRandomFileName(15), "John F. Kennedy, Stars, Ermordung, Attentat");
			media.setDescription("Als Meryl Streep erfuhr, dass der Pr&auml;sident ermordet wurde, paukte sie gerade Franz&ouml;sisch.");
			media.setImageSmall("http://images03.kurier.at/46-47632878.jpg/200x200/37.001.188");
			media.setImageMedium("http://images03.kurier.at/46-47632878.jpg/200x200/37.001.188");
			media.setImageLarge("http://images03.kurier.at/46-47632878.jpg/200x200/37.001.188");
			media.setImageWidth(200);
			media.setImageHeight(200);

			mediaService.persist(media);
			
			if(i % 100 == 0){
				System.out.println(i);
			}
		}
	}*/

}

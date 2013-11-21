package com.phillip.news.service;

import java.util.List;

import com.phillip.news.domain.Category;
import com.phillip.news.domain.Media;
import com.phillip.news.domain.MediaProvider;

public interface MediaService {
	Media persist(Media media);
	Boolean exists(String URL);
	
	Media findByUrl(String url);
	
	Media findByCategory(Category category);
	
	List<Media> findAll();
	
	List<Media> findAfterThis(Long date, Long last);
	List<Media> findAfterThis(Long date, Long last, Integer quantity);
	
	List<Media> findBeforeThis(Long date, Long first);
	List<Media> findBeforeThis(Long date, Long first, Integer quantity);
	
	List<Media> findLast();
	List<Media> findLast(Integer quantity);
	
	List<Media> test();
}

package com.phillip.news.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.phillip.news.domain.MediaProvider;

public interface MediaProviderRepository extends CrudRepository<MediaProvider, Long>{
	MediaProvider findByMediaProviderId(String mediaProviderId);
	List<MediaProvider> findByMediaProviderIdLike(String q);
}

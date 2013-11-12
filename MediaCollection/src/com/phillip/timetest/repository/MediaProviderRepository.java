package com.phillip.timetest.repository;

import org.springframework.data.repository.CrudRepository;

import com.phillip.timetest.domain.MediaProvider;

public interface MediaProviderRepository extends CrudRepository<MediaProvider, Long>{
	MediaProvider findByMediaProviderId(String mediaProviderId);
}

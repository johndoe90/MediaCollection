package com.phillip.timetest.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.phillip.timetest.domain.MediaProvider;
import com.phillip.timetest.repository.MediaProviderRepository;
import com.phillip.timetest.service.MediaProviderService;

import static org.springframework.data.neo4j.support.ParameterCheck.notNull;

@Service
public class MediaProviderServiceImpl implements MediaProviderService{

	private final MediaProviderRepository mediaProviderRepository;
	
	@Inject
	public MediaProviderServiceImpl(MediaProviderRepository mediaProviderRepository){
		this.mediaProviderRepository = mediaProviderRepository;
	}

	@Override
	public MediaProvider persist(MediaProvider mediaProvider) {
		notNull(mediaProvider);
		
		return mediaProviderRepository.save(mediaProvider);
	}
}

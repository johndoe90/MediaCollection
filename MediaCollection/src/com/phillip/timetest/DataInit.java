package com.phillip.timetest;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.phillip.timetest.domain.MediaProvider;
import com.phillip.timetest.domain.MediaProviders;
import com.phillip.timetest.service.MediaProviderService;

@Component
public class DataInit implements InitializingBean{

	@Inject
	private MediaProviderService mediaProviderService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		for(MediaProvider mediaProvider : MediaProviders.getMediaProviders()){
			mediaProviderService.persist(mediaProvider);
		}
	}

}

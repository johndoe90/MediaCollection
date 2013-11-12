package com.phillip.timetest.service;

import com.phillip.timetest.domain.Media;
import com.phillip.timetest.domain.MediaProvider;

public interface MediaService {
	Media persist(Media media);
	Boolean mediaExists(String URL);
	
	Media findByUrl(String url);
	
	MediaProvider findByMediaProviderId(String mediaProviderId);
}

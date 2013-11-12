package com.phillip.timetest.domain;

import java.util.Arrays;
import java.util.List;

public class MediaProviders {

	public static final MediaProvider kurier = new MediaProvider("kurier.at");
	
	public static List<MediaProvider> getMediaProviders(){
		return Arrays.asList(kurier);
	}
}

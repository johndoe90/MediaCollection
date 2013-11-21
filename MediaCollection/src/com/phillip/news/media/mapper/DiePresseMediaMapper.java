package com.phillip.news.media.mapper;

import org.jsoup.nodes.Document;

import com.phillip.news.domain.Media;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.media.AbstractMediaMapper;

public class DiePresseMediaMapper extends AbstractMediaMapper{

	public DiePresseMediaMapper(MediaProvider mediaProvider) {
		super(mediaProvider);
	}

	@Override
	public Long getDate(Document document) {
		return null;
	}

}

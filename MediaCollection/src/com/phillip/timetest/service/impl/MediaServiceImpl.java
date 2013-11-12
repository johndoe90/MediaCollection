package com.phillip.timetest.service.impl;

import static org.springframework.data.neo4j.support.ParameterCheck.notNull;

import javax.inject.Inject;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.mapping.MappingPolicy;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.data.neo4j.support.index.NoSuchIndexException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phillip.timetest.domain.Media;
import com.phillip.timetest.domain.MediaProvider;
import com.phillip.timetest.repository.MediaProviderRepository;
import com.phillip.timetest.repository.MediaRepository;
import com.phillip.timetest.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService{

	private final MediaRepository mediaRepository;
	private final MediaProviderRepository mediaProviderRespository;
	
	@Inject
	public MediaServiceImpl(MediaRepository mediaRepository, MediaProviderRepository mediaProviderRespository){
		this.mediaRepository = mediaRepository;
		this.mediaProviderRespository = mediaProviderRespository;
	}
	
	@Override
	public Media persist(Media media) {
		notNull(media);
		
		MediaProvider mediaProvider = findByMediaProviderId(media.getMediaProvider().getMediaProviderId());		
		media.setMediaProvider(mediaProvider);
		
		return mediaRepository.save(media);
	}

	@Override
	public Boolean mediaExists(String URL) {
		notNull(URL);
		
		return findByUrl(URL) != null ? true : false;
	}

	@Override
	public Media findByUrl(String URL) {
		notNull(URL);
		
		return mediaRepository.findByUrl(URL);
	}

	@Override
	public MediaProvider findByMediaProviderId(String mediaProviderId) {
		notNull(mediaProviderId);
		
		return mediaProviderRespository.findByMediaProviderId(mediaProviderId);
	}

	/*private final Neo4jTemplate template;
	private final GraphDatabase graphDb;
	
	@Inject
	public MediaServiceImpl(Neo4jTemplate template){
		this.template = template;
		this.graphDb = template.getGraphDatabase();
	}
	
	private Node findNodeByURL(String URL){
		notNull(URL);
		
		try{
			Index<Node> index = graphDb.getIndex(Indices.URL.INDEX_NAME);
			return index.get(Indices.URL.FIELD_NAME, URL).getSingle();
		}catch(NoSuchIndexException e){
			return null;
		}
	}
	
	@Override
	@Transactional
	public Media persist(Media media) {
		System.out.println("Persisting Media: " + media.getUrl());
		
		return template.save(media);
	}

	@Override
	public Media findByUrl(String URL) {
		notNull(URL);
		Node node = findNodeByURL(URL);
		System.out.println("Lookup: " + URL);

		return node != null ? template.createEntityFromState(node, Media.class, MappingPolicy.DEFAULT_POLICY) : null;
	}

	@Override
	public Boolean mediaExists(String URL) {
		return findNodeByURL(URL) != null ? true : false;
	}*/
}

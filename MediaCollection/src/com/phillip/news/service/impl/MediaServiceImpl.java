package com.phillip.news.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.phillip.news.domain.Category;
import com.phillip.news.domain.Media;
import com.phillip.news.domain.MediaProvider;
import com.phillip.news.repository.CategoryRepository;
import com.phillip.news.repository.MediaProviderRepository;
import com.phillip.news.repository.MediaRepository;
import com.phillip.news.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService{

	@PersistenceContext
	private EntityManager em;
	
	private final MediaRepository mediaRepository;
	private final CategoryRepository categoryRepository;
	private final MediaProviderRepository mediaProviderRespository;
	
	@Inject
	public MediaServiceImpl(MediaRepository mediaRepository, MediaProviderRepository mediaProviderRespository, CategoryRepository categoryRepository){
		this.mediaRepository = mediaRepository;
		this.mediaProviderRespository = mediaProviderRespository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Media persist(Media media) {
		Assert.notNull(media);
		
		MediaProvider mediaProvider = mediaProviderRespository.findByMediaProviderId(media.getMediaProvider().getMediaProviderId());		
		media.setMediaProvider(mediaProvider);
		
		Category category = categoryRepository.findByQualifiedName(media.getCategory().getQualifiedName());
		media.setCategory(category);
		
		return mediaRepository.save(media);
	}

	@Override
	public Boolean exists(String URL) {
		Assert.hasText(URL);
		
		return findByUrl(URL) != null ? true : false;
	}

	@Override
	public Media findByUrl(String URL) {
		Assert.hasText(URL);
		
		return mediaRepository.findByUrl(URL);
	}
	
	@Override
	public List<Media> findAll() {
		List<Media> all = new ArrayList<Media>();
		for(Media media : mediaRepository.findAll()){
			all.add(media);
		}
		
		return all;
	}

	@Override
	public List<Media> findAfterThis(Long date, Long last) {
		return findAfterThis(date, last, 10);
	}

	@Override
	public List<Media> findAfterThis(Long date, Long last, Integer quantity) {
		Assert.notNull(last);
		
		return mediaRepository.findAfterThis(date, last, new PageRequest(0, quantity != null ? quantity : 10));
	}

	@Override
	public List<Media> findBeforeThis(Long date, Long first) {
		return findBeforeThis(date, first, 10);
	}

	@Override
	public List<Media> findBeforeThis(Long date, Long first, Integer quantity) {
		Assert.notNull(first);
		
		return mediaRepository.findBeforeThis(date, first, new PageRequest(0, quantity != null ? quantity : 10));
	}

	@Override
	public List<Media> findLast() {
		return findLast(10);
	}

	@Override
	public List<Media> findLast(Integer quantity) {
		return mediaRepository.findLast(new PageRequest(0, quantity != null ? quantity : 10));
	}
		
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Media> test(){
		Session session = em.unwrap(Session.class);

		List<MediaProvider> providers = session.createCriteria(MediaProvider.class)
												.list();
		
		for(MediaProvider provider : providers){
			System.out.println("Id: " + provider.getMediaProviderId() + " Name: " + provider.getMediaProviderName());
		}
		
		
		
		List<Media> media = session.createCriteria(Media.class)
								.setMaxResults(10)
								.add(Restrictions.in("mediaProvider", providers))
								.add(Restrictions.or(
										Restrictions.and(
												Restrictions.eq("date", new Long(1384884022000L)),
												Restrictions.gt("id", new Long(1L))
										),Restrictions.gt("date", new Long(1384884022000L))))
								.addOrder(Order.asc("date"))
								.addOrder(Order.asc("id"))
								.list();
		
		for(int i = 0; i < media.size(); i++){
			System.out.println("Id: " + media.get(i).getId() + " Date: " + media.get(i).getDate());
		}

		
		
		return media;
	}
	
	@Override
	public Media findByCategory(Category category) {
		// TODO Auto-generated method stub
		return null;
	}
}

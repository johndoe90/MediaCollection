package com.phillip.timetest.repository;

import org.springframework.data.repository.CrudRepository;

import com.phillip.timetest.domain.Media;

public interface MediaRepository extends CrudRepository<Media, Long>{
	Media findByUrl(String url);
}

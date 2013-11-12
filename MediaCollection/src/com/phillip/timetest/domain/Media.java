package com.phillip.timetest.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "MEDIA", uniqueConstraints = @UniqueConstraint(columnNames = {"URL"}))
public class Media extends AbstractPersistable<Long>{
	
	private Media(){}
	public Media(String url, String type, String title, String description, String image, String keywords, Long date, Category category, MediaProvider mediaProvider){
		this.url = url.toLowerCase();
		this.type = type.toLowerCase();
		this.title = title;
		this.description = description;
		this.image = image.toLowerCase();
		this.keywords = keywords.toLowerCase();
		this.date = date;
		this.category = category;
		this.mediaProvider = mediaProvider;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEDIA_PROVIDER_ID", nullable = false)
	private MediaProvider mediaProvider;
	
	@Embedded
	private Category category;
	
	@Column(name = "URL", nullable = false, unique = true)
	private String url;
	
	@Column(name = "KEYWORDS", nullable = false)
	private String keywords;
	
	@Column(name = "TYPE", nullable = false)
	private String type;
	
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column(name = "DESCRIPTION", nullable = false, length = 512)
	private String description;
	
	@Column(name = "IMAGE", nullable = true)
	private String image;
	
	@Column(name = "DATE", nullable = false)
	private Long date;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public MediaProvider getMediaProvider() {
		return mediaProvider;
	}
	public void setMediaProvider(MediaProvider mediaProvider) {
		this.mediaProvider = mediaProvider;
	}
	
	
}

/*@NodeEntity
public class Media {

	private Media(){}
	public Media(String url, String type, String title, String description, String image, String keywords, Long date){
		this.url = url.toLowerCase();
		this.type = type.toLowerCase();
		this.title = title;
		this.description = description;
		this.image = image.toLowerCase();
		this.keywords = keywords.toLowerCase();
		this.date = date;
	}
	
	@Indexed(indexName = Indices.URL.INDEX_NAME, fieldName = Indices.URL.FIELD_NAME, unique = true)
	private String url;
	
	@Indexed(indexName = Indices.KEYWORDS.INDEX_NAME, fieldName = Indices.KEYWORDS.FIELD_NAME, indexType = IndexType.FULLTEXT)
	private String keywords;
	
	private String type;
	private String title;
	private String description;
	private String image;
	
	//UTC
	private Long date;
	
	
	
	//private String date;
	//private String category;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}*/

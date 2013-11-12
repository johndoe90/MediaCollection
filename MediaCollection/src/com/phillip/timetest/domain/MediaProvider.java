package com.phillip.timetest.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "MEDIA_PROVIDERS", uniqueConstraints = @UniqueConstraint(columnNames = "MEDIA_PROVIDER_ID"))
public class MediaProvider extends AbstractPersistable<Long>{

	private MediaProvider(){}
	public MediaProvider(String mediaProviderId){
		this.mediaProviderId = mediaProviderId;
	}
	
	@OneToMany(mappedBy = "mediaProvider")
	private List<Media> media = new ArrayList<Media>();
	
	@Column(name = "MEDIA_PROVIDER_ID", nullable = false, unique = true)
	private String mediaProviderId;
	
	@Column(name = "LOGO_MEDIUM", nullable = true)
	private String logoMedium;

	public String getMediaProviderId() {
		return mediaProviderId;
	}

	public void setMediaProviderId(String mediaProviderId) {
		this.mediaProviderId = mediaProviderId;
	}

	public String getLogoMedium() {
		return logoMedium;
	}

	public void setLogoMedium(String logoMedium) {
		this.logoMedium = logoMedium;
	}
}

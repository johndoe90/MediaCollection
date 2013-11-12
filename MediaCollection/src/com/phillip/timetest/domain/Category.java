package com.phillip.timetest.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Embeddable
public class Category{

	private Category(){}
	public Category (String superCategory, String subCategory){
		this.superCategory = superCategory;
		this.subCategory = subCategory;
	}
	
	@Column(name = "SUPER_CATEGORY", nullable = false)
	private String superCategory;
	
	@Column(name = "SUB_CATEGORY", nullable = true)
	private String subCategory;

	public String getSuperCategory() {
		return superCategory;
	}

	public void setSuperCategory(String superCategory) {
		this.superCategory = superCategory;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
}

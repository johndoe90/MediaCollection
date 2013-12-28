package com.phillip.news.config;

import java.io.File;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class FilesConfig implements InitializingBean{
	public static final String DOMAIN_RESOURCES_IMG = "http://10.0.0.56:8080/news/resources/img";
	public static final String TOMCAT_LOCAL_DIRECTORY = "/home/johndoe/Dokumente/SoftwareDevelopment/springsource workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/News/resources/img";
	
	@Override
	public void afterPropertiesSet() throws Exception {
		File temp = new File(TOMCAT_LOCAL_DIRECTORY);
		
		if(!temp.exists()){ 
			temp.mkdir();
		}
	}

}

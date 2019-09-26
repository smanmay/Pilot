/* *
 * Copyright (C) 2019 Manmay Sethi, All rights reserved. 
 *
 * Created By: Manmay Sethi
 *
 * File Name: AppUtil.java
 *
 * Created On: Sep 26, 2019 2:35:59 PM
 *
 */
package com.test.pilot.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author Manmay Sethi
 *
 */
public class AppUtil {
	
	private static Logger logger = Logger.getLogger(AppUtil.class);
	
	public static Properties loadProperties(String propertyFileName) throws IOException {
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		
		try (InputStream resourceStream = loader.getResourceAsStream(propertyFileName)) {
			props.load(resourceStream);
			logger.debug(propertyFileName + " propertyKeys: " + props);
		} catch (IOException e) {
			logger.error("Exception occured while loading properties.", e);
			throw e;
		}
		
		return props;
	}
}

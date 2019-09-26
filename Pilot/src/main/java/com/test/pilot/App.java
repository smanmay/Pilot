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
package com.test.pilot;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import org.apache.livy.LivyClient;
import org.apache.livy.LivyClientBuilder;
import org.apache.log4j.Logger;

import com.test.pilot.job.PiJob;
import com.test.pilot.util.AppUtil;

/**
 * @author Manmay Sethi
 *
 */
public class App {
	
	private static Logger logger = Logger.getLogger(App.class);
	
	private static final String	APP_RESOURCE_NAME	= "app.properties";
	private static final String	SPARK_RESOURCE_NAME	= "spark.properties";
	
	public static void main(String[] args) throws IOException {
		
		Properties appProps = AppUtil.loadProperties(APP_RESOURCE_NAME);
		Properties sparkProps = AppUtil.loadProperties(SPARK_RESOURCE_NAME);
		
		runLivyDefaultProgram(appProps, sparkProps);
	}
	
	/**
	 * @param appProps
	 * @param sparkProps
	 */
	private static void runLivyDefaultProgram(Properties appProps, Properties sparkProps) {
		
		LivyClient client = null;
		try {
			client = new LivyClientBuilder().setURI(new URI(appProps.getProperty("livy.url"))).setAll(sparkProps).build();
			int samples = 5;
			client.uploadJar(new File("pilot-0.0.1.jar")).get();
			
			logger.debug(String.format("Running PiJob with %d samples...\n", samples));
			double pi = client.submit(new PiJob(samples)).get();
			
			logger.debug("Pi is roughly: " + pi);
		} catch (Exception e) {
			logger.error("Error occured.", e);
		} finally {
			if (client != null)
				client.stop(true);
		}
		
	}
	
}

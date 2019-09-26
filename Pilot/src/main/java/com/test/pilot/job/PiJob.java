package com.test.pilot.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.livy.Job;
import org.apache.livy.JobContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;

public class PiJob implements Job<Double>, Function<Integer, Integer>, Function2<Integer, Integer, Integer> {
	
	/** */
	private static final long	serialVersionUID	= -8590291715328332615L;
	private final int			samples;
	
	public PiJob(int samples) {
		
		this.samples = samples;
	}
	
	public Double call(JobContext ctx) throws Exception {
		
		List<Integer> sampleList = new ArrayList<Integer>();
		for (int i = 0; i < samples; i++) {
			sampleList.add(i + 1);
		}
		
		return (Double) 4.0d * ctx.sc().parallelize(sampleList).map(this).reduce(this) / samples;
	}
	
	public Integer call(Integer v1) {
		
		double x = Math.random();
		double y = Math.random();
		return (x * x + y * y < 1) ? 1 : 0;
	}
	
	public Integer call(Integer v1, Integer v2) {
		
		return v1 + v2;
	}
	
}

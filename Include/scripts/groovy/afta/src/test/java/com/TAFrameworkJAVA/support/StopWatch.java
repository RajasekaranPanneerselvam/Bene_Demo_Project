package com.TAFrameworkJAVA.support;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Stopwatch;

/**
 * Stopwatch the elapsed/start time of the stop watch
 * 
 */
public class StopWatch {

//	private static Stopwatch sw = new Stopwatch().start();

	/**
	 * Returns the start time of the stop watch
	 * 
	 * @return time in seconds
	 */
//	@SuppressWarnings("deprecation")
	public static long startTime() {
		return (long) 1.9;
//		return sw.elapsedTime(TimeUnit.MILLISECONDS);
	}

	/**
	 * Returns the elapsed time of the stop watch.
	 * 
	 * @param startTime
	 *            - start time
	 * 
	 * @return elapsed time in seconds
	 */
//	@SuppressWarnings("deprecation")
	public static long elapsedTime(long startTime) {
		return (long) 1.9;
//		return (long) (sw.elapsedTime(TimeUnit.MILLISECONDS) - startTime) / 1000;
	}

}

package com.kakaosender.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class PRes {
	public static ExecutorService threadPool;
	public static ThreadPoolExecutor threadCountAlerter;
	public static boolean isThreadActive;
}

package com.kakaosender.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 프로젝트 내부적으로 사용되는 공유자원
 * @author root
 *
 */
public abstract class PRes {
	public static ExecutorService threadPool;
	public static ThreadPoolExecutor threadCountAlerter;
	public static boolean isThreadActive;
}

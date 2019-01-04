/**
 * @(#)EhcacheUtils.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 29, 2013 chenwei 创建版本
 */
package com.hd.agent.common.util;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 
 * 自定义缓存
 * @author chenwei
 */
public class EhcacheUtils {
	/**
	 * 根据configname缓存配置添加缓存
	 * @param cachename
	 * @param object
	 * @author chenwei 
	 * @date Apr 29, 2013
	 */
	public static void addCache(String cachename,Object object){
		addCache("SYS_DATA_CACHE",cachename,object);
	}
	/**
	 * 根据SYS_DATA_CACHE缓存配置移除KEY值为cachename缓存
	 * @param cachename
	 * @author chenwei 
	 * @date Apr 29, 2013
	 */
	public static boolean removeCache(String cachename){
		return removeCache("SYS_DATA_CACHE",cachename);
	}
	/**
	 * 根据SYS_DATA_CACHE缓存配置获取缓存数据。
	 * @param cachename
	 * @return
	 * @author chenwei 
	 * @date Apr 29, 2013
	 */
	public static Object getCacheData(String cachename){
		return getCacheData("SYS_DATA_CACHE",cachename);
	}
	/**
	 * 根据configname缓存配置添加缓存
	 * @param configname 配置文件名
	 * @param cachename 缓存名
	 * @param object 被缓存对象
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 13, 2016
	 */
	public static void addCache(String configname,String cachename,Object object){
		if(configname==null || "".equals(configname.trim())){
			return;
		}
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache(configname);
		if(null==cache){
			manager.addCache(configname);
			cache = manager.getCache(configname);
			Element element = new Element(cachename, object);
			cache.put(element);
		}else{
			Element element = new Element(cachename, object);
			cache.put(element);
		}
	}


	/**
	 * 根据configname缓存配置移除缓存
	 * @param configname 配置文件名
	 * @param cachename 缓存名
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 13, 2016
	 */
	public static boolean removeCache(String configname,String cachename){
		if(configname==null || "".equals(configname.trim())){
			return false;
		}
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache(configname);
		if(null!=cache){
			return cache.remove(cachename);
		}
		return false;
	}

	/**
	 * 根据configname缓存配置获取缓存数据。
	 * @param configname 配置文件名
	 * @param cachename 缓存名
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 13, 2016
	 */
	public static Object getCacheData(String configname,String cachename){
		if(configname==null || "".equals(configname.trim())){
			return null;
		}
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache(configname);
		Object object = null;
		if(null!=cache){
			Element element =  cache.get(cachename);
			if(null!=element){
				object = element.getObjectValue();
			}
		}
		return object;
	}

	/**
	 * 清除configname缓存配置结点里的所有缓存数据。
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 13, 2016
	 */
	public void clearAllCache(){
		clearAllCache("SYS_DATA_CACHE");
	}
	/**
	 * 清除configname缓存配置结点里的所有缓存数据。
	 * @param configname 配置文件名
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 13, 2016
	 */
	public void clearAllCache(String configname){
		if(configname==null || "".equals(configname.trim())){
			return;
		}
		CacheManager manager = CacheManager.create();
		Cache cache = manager.getCache(configname);
		if(null!=cache){
			cache.removeAll();
		}
	}
}


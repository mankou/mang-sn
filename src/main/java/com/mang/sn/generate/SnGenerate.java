package com.mang.sn.generate;

import java.util.Map;

/**
 * 单号生成
 * */
public interface SnGenerate {
	
	/**
	 * 生成具体的单号
	 * 
	 * @param prefix 单号前缀
	 * @param maxIndex 单号序号
	 * @param paraMap 其它传口实现类的参数 用于扩展
	 * 
	 * @return 单号
	 * */
	public String generateSn(String prefix,Long maxIndex,Map<String,Object> paraMap);
}

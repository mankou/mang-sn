package com.mang.sn.service;

import com.mang.sn.generate.SnGenerate;
import com.mang.sn.tools.SnType;

/**
 * 单号生成
 * 
 * @author mang
 * */
public interface SnService {
	
	/**
	 * 生成时间类型单号.
	 * @param prefix 单号前缀
	 * @param busType 业务类型 用于区分不同的业务 以满足不同的业务类型走不同的单号计数
	 * */
	public String doGetDateSn(String prefix,String busType);
	
	/**
	 * 生成单号
	 * @param prefix:单号前缀
	 * @param busType:业务类型 用于区分不同的业务 以满足不同的业务类型走不同的单号计数
	 * @param snType:单号生成方式 
	 * @param snGenerate 用于生成具体的单号 需实现SnGenerate接口
	 * */
	public String doGetSn(String prefix,String busType,SnType snType,SnGenerate snGenerate);
	
	
	/**
	 * 专门获取时间类型单号的,但maxIndex不加1
	 * 因历史原因该方法保留,但其是专门产生时间类型单号的方法。
	 * 以后尽量采用下面的方法
	 * */
	public String getCurrentSn(String prefix,String type);
	

	/**
	 * 获取当前单号的,但maxIndex不加1
	 * @param prefix:编号前缀
	 * @param busType:业务类型
	 * @param syType: 单号生成方式
	 * @param snGenerate 用于生成具体的单号 需实现SnGenerate接口
	 * */
	public String getCurrentSn(String prefix, String busType, SnType syType,SnGenerate snGenerate);
	
	/**
	 * 单号maxindex加1
	 * */
	public void addMaxIndex(String busType,SnType snType);
}

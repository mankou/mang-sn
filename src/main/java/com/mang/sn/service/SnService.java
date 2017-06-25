package com.mang.sn.service;

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
	 * @param busType 业务类型 不同的业务类型可走不同的单号计数
	 * */
	public String doGetDateSn(String prefix,String busType);
	
	/**
	 * 生成单号
	 * @param prefix:单号前缀
	 * @param type:单号类型
	 * @param snType:单号类型.
	 * @param precision:精度 
	 * */
	public String doGetSn(String prefix,String type,SnType snType,String precision);
	
	
	/**
	 * descirption:专门获取时间类型单号的,但maxIndex不加1
	 * 因历史原因该方法保留,但其是专门产生时间类型单号的方法。
	 * 以后尽量采用下面的方法
	 * */
	public String doGetCurrentSn(String prefix,String type);
	

	/**
	 * 获取当前单号的,但maxIndex不加1
	 * @param prefix:编号前缀
	 * @param busType:业务类型
	 * @param syType: 单号生成方式类型.
	 * <p>目前有date类型和number类型. date类型生成的格式为T20141022-1 number类型生成的格式为T00000001</p>
	 * @param precision:精度。
	 * <p>当option类型为number时,该字段有效。如为8 则单号长度为8(不包括前缀)</p>
	 * */
	public String doGetCurrentSn(String prefix, String busType, SnType syType,String precision);
	
	/**
	 * 单号maxindex加1
	 * */
	public void addMaxIndex(String busType,SnType snType);
}

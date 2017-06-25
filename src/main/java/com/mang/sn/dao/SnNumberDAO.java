package com.mang.sn.dao;

import com.mang.sn.entity.SnNumber;

public interface SnNumberDAO extends BaseDAO<SnNumber> {

	public Long getMaxIndex(String busType,int codeType);
	
	public void updateMaxIndex(Long maxIndex,String busType,int codeType);
	
	public void insertMaxIndex(String busType,int codeType);
	
}

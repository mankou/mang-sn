package com.mang.sn.dao;

import com.mang.sn.entity.SnNumber;

public interface SnNumberDAO extends BaseDAO<SnNumber> {

	public Long getMaxIndex(String type,String codeType);
	
	public void updateMaxIndex(Long maxIndex,String type,String codeType);
	
	public void insertMaxIndex(String type,String codeType);
	
}

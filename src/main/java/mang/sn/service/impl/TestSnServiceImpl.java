package mang.sn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mang.sn.dao.SnNumberLogDAO;
import mang.sn.entity.SnNumberLog;
import mang.sn.service.SnService;
import mang.sn.service.TestSnService;

@Service
@Transactional
public class TestSnServiceImpl implements TestSnService {
	
	@Autowired
	private SnService snService;
	
	@Autowired
	private SnNumberLogDAO snNumberLogDAO;

	@Override
	public void testTransaction() {
		SnNumberLog snLog1=new SnNumberLog();
		snLog1.setRunMemo("取单号前");
		snNumberLogDAO.save(snLog1);
		
		String sn=snService.doGetDayDateSn("QC", "Test");
		System.out.println(sn);
		
		SnNumberLog snLog2=new SnNumberLog();
		snLog2.setRunMemo("取单号后");
		snNumberLogDAO.save(snLog2);
		
		
	}

}
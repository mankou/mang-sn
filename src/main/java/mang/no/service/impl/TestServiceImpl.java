package mang.no.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mang.no.dao.SnNumberLogDAO;
import mang.no.entity.SnNumberLog;
import mang.no.service.SerialNumberService;
import mang.no.service.TestService;

@Service
@Transactional
public class TestServiceImpl implements TestService {
	
	@Autowired
	private SerialNumberService serialNumberService;
	
	@Autowired
	private SnNumberLogDAO snNumberLogDAO;

	@Override
	public void testTransaction() {
		SnNumberLog snLog1=new SnNumberLog();
		snLog1.setRunMemo("取单号前");
		snNumberLogDAO.save(snLog1);
		
		String sn=serialNumberService.doGetDateSn("QC", "Test");
		
		SnNumberLog snLog2=new SnNumberLog();
		snLog2.setRunMemo("取单号后");
		snNumberLogDAO.save(snLog2);
		
		
	}

}

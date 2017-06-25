package com.mang.sn.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.SynchronizationType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mang.sn.entity.SnNumberLog;
import com.mang.sn.generate.DateGenerate;
import com.mang.sn.generate.SnGenerate;
import com.mang.sn.service.SnService;
import com.mang.sn.tools.SnType;
import com.mang.sn.dao.SnNumberLogDAO;
import com.mang.sn.dao.TimeDAO;
import com.mang.sn.dao.SnNumberDAO;

@Service
@Transactional
public class SnServiceImpl implements SnService {
	private static Logger logger = Logger. getLogger(SnServiceImpl.class);
	
	@Autowired
	private SnNumberDAO buNumberDAO;
	
	@Autowired
	private SnNumberLogDAO buNumberLogDAO;
	
	@Autowired
	private TimeDAO timeDAO;
	


	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String doGetDateSn(String prefix, String type) {
		String code = doGetSn(prefix, type, SnType.date, new DateGenerate());
		return code;
	}

	@Override
	public String doGetCurrentSn(String prefix, String type) {
		return doGetCurrentSn(prefix, type, SnType.date, null);
	}
	
	@Override
	public String doGetCurrentSn(String prefix, String busType, SnType snType,SnGenerate generate) {
		String code=null;
		Long maxIndex=null;
		
		Integer codeType=snType.getCode();
		
		//取最大值
		maxIndex = getMaxIndex(busType, codeType);
		
		code=generate.generateSn(prefix, maxIndex, null);
		return code;
	}

	@Override
	public void addMaxIndex(String busType,SnType snType) {
		
		int codeType=snType.getCode();

		Long maxIndex = buNumberDAO.getMaxIndex(busType, codeType);
		if(maxIndex==null){
			buNumberDAO.insertMaxIndex(busType,codeType);
			maxIndex = 1L;
		}

		buNumberDAO.updateMaxIndex(maxIndex+1, busType, codeType);

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW) 
	public String doGetSn(String prefix, String busType, SnType snType,SnGenerate generate) {
		logger.info("[生成单号]" + "请求单号开始");

		String code = null;
		int codeType;
		
		codeType=snType.getCode();

		logger.info("[生成单号]前缀:" + prefix + "\t单号生成方式:" + codeType + "\t单号类型:" + busType);

		
		//XXX 这里时间和maxIndex不同步 有可能导致单号错误
		
		// 取最大值
		Long maxIndex = null;
		maxIndex = getMaxIndex(busType, codeType);
		Timestamp time=timeDAO.getOracleTime();
		
		//生成单号
		Map<String,Object> paraMap=new HashMap<String,Object>();
		paraMap.put("dbTime", time);
		code=generate.generateSn(prefix, maxIndex, paraMap);

		// 最大值加1
		buNumberDAO.updateMaxIndex(maxIndex + 1, busType, codeType);
		
		// 写日志
		saveNumberLog(prefix, busType, code, codeType); // 写单号生成日志(0表示时间类型 1表示数字类型)
		logger.info("[生成单号]" + "生成的单号为:" + code);
		logger.info("[生成单号]" + "请求单号结束");
		return code;
	}
		
	private Long getMaxIndex(String busType,int codeType) {
		Long maxIndex=buNumberDAO.getMaxIndex(busType,codeType);
		if(maxIndex==null){
			//说明没有,先建一新的
			buNumberDAO.insertMaxIndex(busType,codeType);
			maxIndex=1L;
		}
		return maxIndex;
	}
	
	
	
	
	private SnNumberLog saveNumberLog(String prefix, String bustype,String sn,Integer snType){
		SnNumberLog numberLog = new SnNumberLog();
		numberLog.setSn(sn);
		numberLog.setPrefix(prefix);
		numberLog.setBusType(bustype);//编号类型
//		numberLog.setRuid(logBO.getCurrentUserCode());
//		numberLog.setRuname(logBO.getCurrentUserName());
//		numberLog.setRundate(TimeUtil.getCurrentTime());
		numberLog.setSnType(snType);
		
		//获取调用该方法的方法的信息
		String message = "";
		StringBuffer sb = new StringBuffer();
		StackTraceElement stack[] = Thread.currentThread().getStackTrace();
		for (StackTraceElement ste : stack) {
			String className = ste.getClassName();
			String methodName = ste.getMethodName();
			int line = ste.getLineNumber();
			if (className.indexOf("yhsq") != -1) {
				sb.append(className + "." + methodName + ":" + line + "&");
			}

		}
		if(sb.length()>0){
			int length =sb.length()>800?800:sb.length();
			message=sb.substring(0, length-1); //截取字符串限制长度
		}
		
		logger.info("[生成单号]调用java类信息:"+message);
		numberLog.setInvokeCode(message);
		return buNumberLogDAO.saveOrUpdate(numberLog);
		
	}
	
	
}

package mang.no.service.impl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mang.no.dao.SnNumberLogDAO;
import mang.no.dao.TimeDAO;
import mang.no.dao.SnNumberDAO;
import mang.no.entity.SnNumberLog;
import mang.no.service.SerialNumberService;

@Service
@Transactional
public class SerialNumberServiceImpl implements SerialNumberService {
	private static Logger logger = Logger. getLogger(SerialNumberServiceImpl.class);
	
	@Autowired
	private SnNumberDAO buNumberDAO;
	
	@Autowired
	private SnNumberLogDAO buNumberLogDAO;
	
	@Autowired
	private TimeDAO timeDAO;
	


	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String doGetDateSn(String prefix, String type) {
		String code = doGetSn(prefix, type, "date", "null");
		return code;
	}

	@Override
	public String doGetCurrentSn(String prefix, String type) {
		return doGetCurrentSn(prefix, type, "date", null);
	}
	
	@Override
	public String doGetCurrentSn(String prefix, String type, String option,String precision) {
		String code=null;
		Long maxIndex=null;
		
		//根据option 取出code类型 是date、number、datenumber等  默认取date类型
		String codeType="0"; 
		if("date".equals(option)){
			codeType="0";
		}else if("number".equals(option)){
			codeType="1";
		}else if("datenumber".equals(option)){
			codeType="2";
		}
		
		//取最大值
		maxIndex = getMaxIndex(type, codeType);
		
		//生成单号
		if("date".equals(option)){
			code= generateCode_Date(type,prefix, maxIndex);
		}
		if("number".equals(option)){
			code=generateCode_Number(type,prefix,precision,maxIndex);
		}
		
		if("datenumber".equals(option)){
			code=generateCode_DateNumber(type,prefix,maxIndex);
		}
		
		return code;
	}

	@Override
	public void addMaxIndex(String type,String option) {
		String codeType="0";
		if("date".equals(option)){
			codeType="0";
		}else if("number".equals(option)){
			codeType="1";
		}

		Long maxIndex = buNumberDAO.getMaxIndex(type, codeType);
		if(maxIndex==null){
			buNumberDAO.insertMaxIndex(type,codeType);
			maxIndex = 1L;
		}

		buNumberDAO.updateMaxIndex(maxIndex+1, type, codeType);

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW) 
	public String doGetSn(String prefix, String type, String option, String precision) {
		logger.info("[生成单号]" + "请求单号开始");

		String code = null;
		String codeType = null;

		if (option == null || "".equals(option)) {
			option = "date";// 默认取date类型
		}

		// 根据option 取出code类型 是date、number、datenumber等
		if ("date".equals(option)) {
			codeType = "0";
		} else if ("number".equals(option)) {
			codeType = "1";
		} else if ("datenumber".equals(option)) {
			codeType = "2";
		}

		logger.info("[生成单号]前缀:" + prefix + "\t单号生成方式:" + codeType + "\t单号类型:" + type);

		// 注 刚本来推崇存储过程方式 因为只要调一下就能取一个单号 但测试时发现因为存储过程中有commit操作 其会影响外层java的事务
		// 所以决定放弃
		// 后来解决了spring 配置getCode的事务为独立事务配置 所以也就不需要存储过程的方式了
		Long maxIndex = null;
		// 取最大值
		maxIndex = getMaxIndex(type, codeType);
		// 生成单号
		if ("date".equals(option)) {
			code = generateCode_Date(type, prefix, maxIndex);
		} else if ("number".equals(option)) {
			code = generateCode_Number(type, prefix, precision, maxIndex);
		} else if ("datenumber".equals(option)) {
			code = generateCode_DateNumber(type, prefix, maxIndex);
		}

		// 最大值加1
		buNumberDAO.updateMaxIndex(maxIndex + 1, type, codeType);

		// 写日志
		saveNumberLog(prefix, type, code, codeType); // 写单号生成日志(0表示时间类型 1表示数字类型)
		logger.info("[生成单号]" + "生成的单号为:" + code);
		logger.info("[生成单号]" + "请求单号结束");

		return code;
	}
		
	private Long getMaxIndex(String type,String codeType) {
		Long maxIndex=buNumberDAO.getMaxIndex(type,codeType);
		if(maxIndex==null){
			//说明没有,先建一新的
			buNumberDAO.insertMaxIndex(type,codeType);
			maxIndex=1L;
		}
		return maxIndex;
	}
	
	private String generateCode_Number(String type,String prefix,String precision,Long maxIndex) {
		// 如果前缀为空则用""代替
		if (prefix == null) {
			prefix = "";
		}

		// 如果不输入默认是8位数字
		if (precision == null || "".equals(precision)) {
			precision = "8";
		}

		String orderNumber;
		if ("-1".equals(precision)) {
			orderNumber = prefix + maxIndex;
		} else {
			DecimalFormat dfInt = new DecimalFormat("00");
			precision = dfInt.format(Integer.valueOf(precision));

			String format = "%" + precision + "d";
			String businessPrefix=getBusinessPrefix(type);
			orderNumber = prefix +businessPrefix+ String.format(format, maxIndex);
		}

		return orderNumber;
	}
	
	private String generateCode_Date(String type,String prefix,Long maxIndex){
		String code = null;
		// 获得当前时间
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyyMMdd"); 
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		
		Timestamp time=timeDAO.getOracleTime();
		String   dateNum   =   formatter.format(time);  
		String businessPrefix=getBusinessPrefix(type);
		code = prefix+businessPrefix+dateNum+"-"+maxIndex;
		
		return code;
	}
	
	/**
	 * @author m-ning@neusoft.com
	 * @param prefix:编号前缀
	 * @param type:编号类型
	 * @param precision:精度。如为8 则单号长度为8(不包括前缀)
	 * descirption:生成类似Z20150615-2的编号，但编号是递增的,不是每天都从1开始的那种。
	 * */
	private String generateCode_DateNumber(String type,String prefix,Long maxIndex){
		
		String code = null;
		// 获得当前时间
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyyMMdd"); 
		Timestamp time=new Timestamp(System.currentTimeMillis());
		String   dateNum   =   formatter.format(time); 
		String businessPrefix=getBusinessPrefix(type);
		code = prefix+businessPrefix+dateNum+"-"+maxIndex;
				
		return code;
	}
	

	/**
	 * @author m-ning@neusft.com
	 * desc: 写日志 (谁? 请求的单号是多少? 是什么代码调的)
	 * 
	 * */
	private SnNumberLog saveNumberLog(String prefix, String bustype,String sn,String codeType){
		SnNumberLog numberLog = new SnNumberLog();
		numberLog.setSn(sn);
		numberLog.setPrefix(prefix);
		numberLog.setBusType(bustype);//编号类型
//		numberLog.setRuid(logBO.getCurrentUserCode());
//		numberLog.setRuname(logBO.getCurrentUserName());
//		numberLog.setRundate(TimeUtil.getCurrentTime());
		numberLog.setCodeType(codeType); //单号类型0时间 1数字
		
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
	
	
	/**
	 * 判断是否需要加前缀 如果需要加前缀则返回前缀 否则返回空字符串
	 * */
	private String getBusinessPrefix(String type){
		String prefix="";
		return prefix;
	}
	
}

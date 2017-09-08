package mang.sn.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mang.sn.dao.SnNumberDAO;
import mang.sn.dao.SnNumberLogDAO;
import mang.sn.dao.TimeDAO;
import mang.sn.entity.SnNumberLog;
import mang.sn.generate.DayNumberGenerate;
import mang.sn.generate.SnGenerate;
import mang.sn.service.SnService;
import mang.sn.tools.InvokeCode;
import mang.sn.tools.SnType;
import mang.sn.util.TimestampUtil;

@Service
@Transactional
public class SnServiceImpl implements SnService {
	private static Logger logger = Logger. getLogger(SnServiceImpl.class);
	
	
	@Autowired(required=false)
	@Qualifier("sn-invokeCode")
	private InvokeCode invodeCode;
	
	@Autowired
	private SnNumberLogDAO snNumberLogDAO;
	
	private SnNumberDAO snNumberDAO;
	
	private TimeDAO timeDAO;
	
	
	
	
	


	//走单独事务 
	//原因：如果人调用者走一个事务 如果调用者的事务一直不结束 你就不能提交 则别人再调该方法就阻塞了
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String doGetDayDateSn(String prefix, String type) {
		String code = doGetSn(prefix, type, SnType.dayDate, new DayNumberGenerate());
		return code;
	}

	@Override
	public String getCurrentDayDateSn(String prefix, String type) {
		return getCurrentSn(prefix, type, SnType.dayDate, null);
	}
	
	@Override
	public String getCurrentSn(String prefix, String busType, SnType snType,SnGenerate snGenerate) {
		String code=null;
		Long maxIndex=null;
		
		Integer codeType=snType.getCode();
		
		//取最大值
		maxIndex = getMaxIndex(busType, codeType);
		
		code=snGenerate.generateSn(prefix, maxIndex, null);
		return code;
	}

	@Override
	public void addMaxIndex(String busType,SnType snType) {
		
		int codeType=snType.getCode();

		Long maxIndex = snNumberDAO.getMaxIndex(busType, codeType);
		if(maxIndex==null){
			snNumberDAO.insertMaxIndex(busType,codeType);
			maxIndex = 1L;
		}

		snNumberDAO.updateMaxIndex(maxIndex+1, busType, codeType);

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW) 
	public String doGetSn(String prefix, String busType, SnType snType,SnGenerate snGenerate) {
		Timestamp startTime=new Timestamp(System.currentTimeMillis());
		logger.info("[生成单号]" + "请求单号开始");

		String code = null;
		int snTypeCode=snType.getCode();

		logger.info("[生成单号]前缀:" + prefix + "\t单号生成方式:" + snTypeCode+"\t业务类型:" + busType);

		
		//XXX 这里时间和maxIndex不同步 有可能导致单号重复
		
		// 取最大值
		Long maxIndex = null;
		maxIndex = getMaxIndex(busType, snTypeCode);
		Timestamp time=timeDAO.getDbTime();
		String dbTimeStr_Hour=TimestampUtil.getDateString(time, "yyyyMMddHH");
		String dbTimeStr_Day=TimestampUtil.getDateString(time, "yyyyMMdd");
		String dbTimeStr_Year=TimestampUtil.getDateString(time, "yyyy");
		
		//生成单号
		Map<String,Object> paraMap=new HashMap<String,Object>();
		paraMap.put("dbTime", time);
		paraMap.put("dbTimeStr_Hour", dbTimeStr_Hour);
		paraMap.put("dbTimeStr_Day", dbTimeStr_Day);
		paraMap.put("dbTimeStr_Year", dbTimeStr_Year);
		
		
		if(snGenerate==null){
			logger.info("[生成单号]生成类snGenerate为空 这里采用默认的生成类");
			snGenerate=new DayNumberGenerate();
		}
		
		code=snGenerate.generateSn(prefix, maxIndex, paraMap);

		// 最大值加1
		snNumberDAO.updateMaxIndex(maxIndex + 1, busType, snTypeCode);
	
		Timestamp endTime=new Timestamp(System.currentTimeMillis());
		String invokeMessage=this.getInvokeInfo();
		SnNumberLog numberLog = new SnNumberLog();
		numberLog.setSn(code);
		numberLog.setPrefix(prefix);
		numberLog.setBusType(busType);//编号类型
		numberLog.setSnType(snTypeCode);
		numberLog.setRuntimeStart(startTime);
		numberLog.setRundate(startTime);
		numberLog.setRuntimeEnd(endTime);
		numberLog.setRuntimeDuration(endTime.getTime()-startTime.getTime());
		numberLog.setInvokeCode(invokeMessage);
		snNumberLogDAO.saveOrUpdate(numberLog);

		
		logger.info("[生成单号]" + "生成的单号为:" + code);
		logger.info("[生成单号]" + "请求单号结束");
		return code;
	}
		
	private Long getMaxIndex(String busType,int codeType) {
		//XXX 这块有可能重 必须保证同一时间只能有一个调用能进来
		Long maxIndex=snNumberDAO.getMaxIndex(busType,codeType);
		if(maxIndex==null){
			//说明没有,先建一新的
			snNumberDAO.insertMaxIndex(busType,codeType);
			maxIndex=1L;
		}
		return maxIndex;
	}
	
	
	private String getInvokeInfo(){
		//获取调用该方法的方法的信息
		String message ="";
		if(invodeCode!=null &&invodeCode.getClassPath()!=null && !"".equals(invodeCode.getClassPath())){
			String invokeCodePath=invodeCode.getClassPath();
			
			
			StringBuffer sb = new StringBuffer();
			StackTraceElement stack[] = Thread.currentThread().getStackTrace();
			for (StackTraceElement ste : stack) {
				String className = ste.getClassName();
				String methodName = ste.getMethodName();
				int line = ste.getLineNumber();
				if (className.indexOf(invokeCodePath) != -1) {
					sb.append(className + "." + methodName + ":" + line + "&");
				}
			}
			if(sb.length()>0){
				int length =sb.length()>800?800:sb.length();
				message=sb.substring(0, length-1); //截取字符串限制长度
			}
			
			logger.info("[生成单号]调用java类信息:"+message);
		}
		return message;
	}

	public SnNumberDAO getSnNumberDAO() {
		return snNumberDAO;
	}

	public void setSnNumberDAO(SnNumberDAO snNumberDAO) {
		this.snNumberDAO = snNumberDAO;
	}

	public TimeDAO getTimeDAO() {
		return timeDAO;
	}

	public void setTimeDAO(TimeDAO timeDAO) {
		this.timeDAO = timeDAO;
	}

	public InvokeCode getInvodeCode() {
		return invodeCode;
	}

	public void setInvodeCode(InvokeCode invodeCode) {
		this.invodeCode = invodeCode;
	}
	
}

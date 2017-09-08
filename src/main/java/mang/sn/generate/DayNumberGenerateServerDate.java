package mang.sn.generate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 单号样例 QC20170907-4
 * 注 时间取服务器时间 不取数据库时间
 * */
public class DayNumberGenerateServerDate implements SnGenerate {
	
	private String dateFormat = "yyyyMMdd";
	private String joinStr = "-";

	@Override
	public String generateSn(String prefix, Long maxIndex, Map<String, Object> map) {
		String code = null;
		// 获得当前时间
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   (dateFormat); 
		Timestamp time=new Timestamp(System.currentTimeMillis());
		String   dateNum   =   formatter.format(time); 
		code = prefix+dateNum+joinStr+maxIndex;
		return code;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getJoinStr() {
		return joinStr;
	}

	public void setJoinStr(String joinStr) {
		this.joinStr = joinStr;
	}
	
	

}

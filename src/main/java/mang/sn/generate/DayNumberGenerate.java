package mang.sn.generate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

/**
 * 单号样例 QC20170907-3
 * 时间从数据库中取
 * */
public class DayNumberGenerate implements SnGenerate {
	private String dateFormat = "yyyyMMdd";
	private String joinStr = "-";

	@Override
	public String generateSn(String prefix,Long maxIndex,Map<String,Object> map) {
		String code = null;
		// 获得当前时间
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   (dateFormat); 
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Timestamp time=(Timestamp) map.get("dbTime");
		
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

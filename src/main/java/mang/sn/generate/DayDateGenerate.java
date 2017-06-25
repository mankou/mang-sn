package mang.sn.generate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

public class DayDateGenerate implements SnGenerate {

	@Override
	public String generateSn(String prefix,Long maxIndex,Map<String,Object> map) {
		String code = null;
		// 获得当前时间
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyyMMdd"); 
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Timestamp time=(Timestamp) map.get("dbTime");
		
		String   dateNum   =   formatter.format(time);  
		code = prefix+dateNum+"-"+maxIndex;
		return code;
	}

}

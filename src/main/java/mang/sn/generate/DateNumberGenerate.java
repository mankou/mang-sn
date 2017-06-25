package mang.sn.generate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

public class DateNumberGenerate implements SnGenerate {

	@Override
	public String generateSn(String prefix, Long maxIndex, Map<String, Object> map) {
		String code = null;
		// 获得当前时间
		SimpleDateFormat   formatter   =   new   SimpleDateFormat   ("yyyyMMdd"); 
		Timestamp time=new Timestamp(System.currentTimeMillis());
		String   dateNum   =   formatter.format(time); 
		code = prefix+dateNum+"-"+maxIndex;
		return code;
	}

}

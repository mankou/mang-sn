package mang.sn.generate;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

/**
 * 单号样例  QC201709070005
 * 如果计数值超出精度 则以计数值为准 如前缀为QC 日期为20170907 计数值为10245 但精度为4位 则生成的单号为 QC2017090710245
 */
public class DayFormatNumberGenerate implements SnGenerate {

	private String dateFormat = "yyyyMMdd";
	private String precision = "4";
	private String joinStr = "";

	@Override
	public String generateSn(String prefix, Long maxIndex, Map<String, Object> map) {

		// 如果前缀为空则用""代替
		if (prefix == null) {
			prefix = "";
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		Timestamp time = (Timestamp) map.get("dbTime");
		String dateNum = formatter.format(time);

		DecimalFormat dfInt = new DecimalFormat("00");
		precision = dfInt.format(Integer.valueOf(precision));

		String format = "%" + precision + "d";
		String result = prefix + dateNum+joinStr + String.format(format, maxIndex);

		return result;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getJoinStr() {
		return joinStr;
	}

	public void setJoinStr(String joinStr) {
		this.joinStr = joinStr;
	}

}

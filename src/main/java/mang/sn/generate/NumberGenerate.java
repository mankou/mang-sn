package mang.sn.generate;

import java.text.DecimalFormat;
import java.util.Map;

public class NumberGenerate implements SnGenerate {
	
	private String precision="8";
	
	@Override
	public String generateSn(String prefix, Long maxIndex, Map<String, Object> map) {
		// 如果前缀为空则用""代替
		if (prefix == null) {
			prefix = "";
		}

		String orderNumber;
		if ("-1".equals(precision)) {
			orderNumber = prefix + maxIndex;
		} else {
			DecimalFormat dfInt = new DecimalFormat("00");
			precision = dfInt.format(Integer.valueOf(precision));

			String format = "%" + precision + "d";
			orderNumber = prefix + String.format(format, maxIndex);
		}

		return orderNumber;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}
	

}

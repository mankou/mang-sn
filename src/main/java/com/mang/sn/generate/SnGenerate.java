package com.mang.sn.generate;

import java.util.Map;

public interface SnGenerate {
	public String generateSn(String prefix,Long maxIndex,Map<String,Object> map);
}

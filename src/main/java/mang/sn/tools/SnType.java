package mang.sn.tools;

public enum SnType {
	/**
	 * 计数每天从1开始计
	 * */
	dayDate(0,"dayDate"),
	
	/**
	 * 计数一直累加 不清零
	 * */
	number(1,"number"),
	
	/**
	 * 计数每周从1开始计
	 * */
	weekDate(2,"weekDate"),
	
	/**
	 * 计数每月从1开始计
	 * */
	monthDate(3,"monthDate"),
	
	/**
	 * 计数每年从1开始计
	 * */
	yearDate(4,"yearDate");
	
	
	
	public static SnType getInstance(int code) {
        // 循环输出 值
        for (SnType e : SnType.values()) {
            if (e.getCode()==code) {
                return e;
            }
        }
        return null;
    }

	
	
	
	public int code; 
	public String message; 
	
	
	private SnType(int code, String message){ 
		this.code = code; 
		this.message = message; 
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

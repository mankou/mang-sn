package com.mang.sn.tools;

public enum SnType {
	/**
	 * 计数每天从1开始计
	 * */
	dayDate(0,"date"),
	
	/**
	 * 计数一直累加 不清零
	 * */
	number(1,"number");
	
	
	
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

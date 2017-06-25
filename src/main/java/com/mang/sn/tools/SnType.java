package com.mang.sn.tools;

public enum SnType {
	date(0,"date"),
	number(1,"number"),
	datenumber(2,"dateNumber");
	
	
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

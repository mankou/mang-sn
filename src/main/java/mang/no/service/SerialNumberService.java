package mang.no.service;

public interface SerialNumberService {
	/**
	 * descirption:生成时间类型单号
	 * 因历史原因该方法保留,但其是专门产生时间类型单号的方法。
	 * 以后尽量采用下面的方法
	 * */
	public String doGetDateSn(String prefix,String type);
	
	/**
	 * descirption:生成单号
	 * @param prefix:编号前缀
	 * @param type:编号类型
	 * @param option:单号类型.
	 * <p>目前有date类型、number类型、datenumber类型. date类型生成的格式为T20141022-1 number类型生成的格式为T00000001 datenumber类型生成的格式为T20141022-1 数字是累加的 不是每天从1开始的</p>
	 * @param precision:精度。
	 * <p>当option类型为number时,该字段有效。如为8 则单号长度为8(不包括前缀) 如为-1则没有长度 不进行格式化 如1就1 不会格式化成00000001的形式</p>
	 * */
	public String doGetSn(String prefix,String type,String option,String precision);
	
	
	/**
	 * descirption:专门获取时间类型单号的,但maxIndex不加1
	 * 因历史原因该方法保留,但其是专门产生时间类型单号的方法。
	 * 以后尽量采用下面的方法
	 * */
	public String doGetCurrentSn(String prefix,String type);
	

	/**
	 * descirption:获取当前单号的,但maxIndex不加1
	 * @param prefix:编号前缀
	 * @param type:编号类型
	 * @param option:单号类型.
	 * <p>目前有date类型和number类型. date类型生成的格式为T20141022-1 number类型生成的格式为T00000001</p>
	 * @param precision:精度。
	 * <p>当option类型为number时,该字段有效。如为8 则单号长度为8(不包括前缀)</p>
	 * */
	public String doGetCurrentSn(String prefix,String type,String option,String precision);
	
	/**
	 * descirption:单号maxindex加1
	 * 
	 * */
	public void addMaxIndex(String type,String option);
}

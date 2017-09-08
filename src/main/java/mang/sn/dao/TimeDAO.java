package mang.sn.dao;

import java.sql.Timestamp;

public interface TimeDAO {
	/**
	 * 获取数据库时间
	 * */
	public Timestamp getDbTime();
}

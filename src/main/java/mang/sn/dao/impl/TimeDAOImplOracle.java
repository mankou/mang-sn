package mang.sn.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import mang.sn.dao.TimeDAO;

@Repository
public class TimeDAOImplOracle extends BaseDAOImpl implements TimeDAO {

	@SuppressWarnings("unchecked")
	@Override
	public Timestamp getDbTime() {
		// 还是有最原始的sql方式处理
		String sql = "select to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') as TIME from dual ";
		SQLQuery query=this.getSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list=query.list();
		if (list != null && list.size() > 0) {
			Map map = (Map) list.get(0);
			String timeStr = (String) map.get("TIME");
			Timestamp time = Timestamp.valueOf(timeStr);
			return time;
		}
		return null;
	}

}

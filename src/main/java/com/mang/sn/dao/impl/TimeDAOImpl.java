package com.mang.sn.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.mang.sn.dao.TimeDAO;

@Repository
public class TimeDAOImpl extends BaseDAOImpl implements TimeDAO {

	@SuppressWarnings("unchecked")
	@Override
	public Timestamp getOracleTime() {
		// 还是有最原始的sql方式处理
		final String sql = "select to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') as time from dual ";
		List list = (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery query = session.createSQLQuery(sql);
				query = (SQLQuery) query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
				List list = query.list();
				return list;
			}
		});
		if (list != null && list.size() > 0) {
			Map map = (Map) list.get(0);
			String timeStr = (String) map.get("TIME");
			Timestamp time = Timestamp.valueOf(timeStr);
			return time;
		}
		return null;
	}

}

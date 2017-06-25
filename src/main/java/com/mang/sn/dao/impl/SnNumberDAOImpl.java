package com.mang.sn.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.mang.sn.entity.SnNumber;
import com.mang.sn.tools.SnType;
import com.mang.sn.dao.SnNumberDAO;

@Repository
public class SnNumberDAOImpl  extends BaseDAOImpl<SnNumber> implements SnNumberDAO {
	private static Logger logger = Logger. getLogger(SnNumberDAOImpl.class);

	
	/**
	 * 
	 */
	public SnNumberDAOImpl() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getMaxIndex(final String busType,final int snType) {
		
		String sql="";
		if(SnType.dayDate.code==snType){
			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and to_char(t.num_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd') and t.sn_type=:snType for update";
		}else if(SnType.number.code==snType){
			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and t.sn_type=:snType for update";
		}
		
		 final String ORDERNUMBER_SQL =sql;
        Long orderNumber = (Long)getHibernateTemplate().execute(new HibernateCallback() {
                public Object doInHibernate(Session session)
                                throws HibernateException{
                        List list = session.createSQLQuery(ORDERNUMBER_SQL)
                        .setString("busType", busType)
                        .setInteger("snType", snType)
                        .list();
                        if(list.size() > 0){
                                return ((BigDecimal)list.get(0)).longValue();
                        }                        
                        return null;
                }
        });
        return orderNumber;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void updateMaxIndex(final Long maxIndex,final String busType,final int snType) {
		String sql="";
		if(SnType.dayDate.code==snType){
			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and to_char(t.num_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd') and t.sn_type=:snType";
		}else if(SnType.number.code==snType){
			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and t.sn_type=:snType";
		}
		
		final String ORDERNUMBER_SQL =sql; 
         Long orderNumber = (Long)getHibernateTemplate().execute(new HibernateCallback() {
                 public Object doInHibernate(Session session)
                                 throws HibernateException {
                         session.createSQLQuery(ORDERNUMBER_SQL)
                         .setLong("maxIndex", maxIndex)
                         .setString("busType", busType)
                         .setInteger("snType", snType)
                         .executeUpdate();                
                         return null;
                 }
         });          
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void insertMaxIndex(final String bus_type,final int codeType) {
		if (bus_type != null && !("".equals(bus_type))) {
			String sql = "";
			if(SnType.dayDate.code==codeType){
				sql="insert into sn_number(id,maxindex,bus_type,num_date,sn_type) values(S_SN_NUMBER.NEXTVAL,1,?,sysdate,?)";
			}else if(SnType.number.code==codeType){
				sql="insert into sn_number(id,maxindex,bus_type,sn_type) values(S_SN_NUMBER.NEXTVAL,1,?,?)";
			}
			
			final String ORDERNUMBER_SQL = sql;
			logger.debug("生成的sql为"+ORDERNUMBER_SQL);

			Long orderNumber = (Long) getHibernateTemplate().execute(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException {
							session.createSQLQuery(ORDERNUMBER_SQL)
									 .setString(0, bus_type)
									 .setInteger(1, codeType)
									.executeUpdate();
							return null;
						}
					});

		}
	}


}

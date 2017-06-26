package mang.sn.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.SynchronizationType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.sql.CaseFragment;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import mang.sn.dao.SnNumberDAO;
import mang.sn.entity.SnNumber;
import mang.sn.tools.SnType;

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
		
		SnType snTypeEnum=SnType.getInstance(snType);
		
		switch (snTypeEnum) {
		case dayDate:
			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and to_char(t.num_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd') and t.sn_type=:snType for update";
			break;
		case number:
			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and t.sn_type=:snType for update";
			break;
		case weekDate:
			sql="select t.maxindex from sn_number t where t.bus_type =:busType and t.num_date>=trunc(sysdate,'d')+1 and t.num_date<sysdate and t.sn_type =:snType for update";
			break;
		case monthDate:
			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and to_char(t.num_date,'yyyymm')=to_char(sysdate,'yyyymm') and t.sn_type=:snType for update";
			break;
		case yearDate:
			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and to_char(t.num_date,'yyyy')=to_char(sysdate,'yyyy') and t.sn_type=:snType for update";
			break;
		default:
			break;
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
		
		SnType snTypeEnum=SnType.getInstance(snType);
		switch (snTypeEnum) {
		case dayDate: 
			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and to_char(t.num_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd') and t.sn_type=:snType";
			break;
		case monthDate: 
			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and to_char(t.num_date,'yyyymm')=to_char(sysdate,'yyyymm') and t.sn_type=:snType";
			break;
		case yearDate:
			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and to_char(t.num_date,'yyyy')=to_char(sysdate,'yyyy') and t.sn_type=:snType";
			break;
		case weekDate:
			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type =:busType and t.num_date>=trunc(sysdate,'d')+1 and t.num_date<sysdate and t.sn_type =:snType ";
			break;
		case number:
			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and t.sn_type=:snType";
			break;
		default:
			break;
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
	public void insertMaxIndex(final String bus_type,final int snType) {
		if (bus_type != null && !("".equals(bus_type))) {
			String sql = "";
			
			SnType snTypeEnum=SnType.getInstance(snType);
			
			switch (snTypeEnum) {
			case dayDate: 
			case weekDate: 
			case monthDate: 
			case yearDate:
				sql="insert into sn_number(id,maxindex,bus_type,num_date,sn_type) values(S_SN_NUMBER.NEXTVAL,1,?,sysdate,?)";
				break;
			case number:
				sql="insert into sn_number(id,maxindex,bus_type,sn_type) values(S_SN_NUMBER.NEXTVAL,1,?,?)";
				break;
			default:
				break;
			}
			
			final String ORDERNUMBER_SQL = sql;
			logger.debug("生成的sql为"+ORDERNUMBER_SQL);

			Long orderNumber = (Long) getHibernateTemplate().execute(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException {
							session.createSQLQuery(ORDERNUMBER_SQL)
									 .setString(0, bus_type)
									 .setInteger(1, snType)
									.executeUpdate();
							return null;
						}
					});

		}
	}


}

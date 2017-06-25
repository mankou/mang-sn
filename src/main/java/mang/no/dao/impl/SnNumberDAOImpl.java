package mang.no.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import mang.no.dao.SnNumberDAO;
import mang.no.entity.SnNumber;

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
	public Long getMaxIndex(final String busType,final String codeType) {
		
		
		//bug修复：2014.07.08修复跳号问题
		String sql="";
		if("0".equals(codeType)){
			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and to_char(t.num_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd') and t.code_type=:codeType for update";
		}else if("1".equals(codeType)){
			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and t.code_type=:codeType for update";
		}else if("2".equals(codeType)){
			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and t.code_type=:codeType for update";
		}
		 final String ORDERNUMBER_SQL =sql;
        Long orderNumber = (Long)getHibernateTemplate().execute(new HibernateCallback() {
                public Object doInHibernate(Session session)
                                throws HibernateException{
                        List list = session.createSQLQuery(ORDERNUMBER_SQL)
                        .setString("busType", busType)
                        .setString("codeType", codeType)
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
	public void updateMaxIndex(final Long maxIndex,final String busType,final String codeType) {
//		 final String ORDERNUMBER_SQL = "update BU_NUMBER t set t.maxindex = :maxIndex where t.type = :type";
		//bug修复：2014.07.08修复跳号问题
		String sql="";
		if("0".equals(codeType)){
			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and to_char(t.num_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd') and t.code_type=:codeType";
		}else if("1".equals(codeType)){
			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and t.code_type=:codeType";
		}else if("2".equals(codeType)){
			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and t.code_type=:codeType";
		}
		 final String ORDERNUMBER_SQL =sql; 
         Long orderNumber = (Long)getHibernateTemplate().execute(new HibernateCallback() {
                 public Object doInHibernate(Session session)
                                 throws HibernateException {
                         session.createSQLQuery(ORDERNUMBER_SQL)
                         .setLong("maxIndex", maxIndex)
                         .setString("busType", busType)
                         .setString("codeType", codeType)
                         .executeUpdate();                
                         return null;
                 }
         });          
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void insertMaxIndex(final String bus_type,final String codeType) {
		if (bus_type != null && !("".equals(bus_type))) {
			String sql = "";
			if("0".equals(codeType)){
				sql="insert into sn_number(id,maxindex,bus_type,num_date,code_type) values(S_SN_NUMBER.NEXTVAL,1,?,sysdate,?)";
			}else if("1".equals(codeType)){
				sql="insert into sn_number(id,maxindex,bus_type,code_type) values(S_SN_NUMBER.NEXTVAL,1,?,?)";
			}else if("2".equals(codeType)){
				sql="insert into sn_number(id,maxindex,bus_type,code_type) values(S_SN_NUMBER.NEXTVAL,1,?,?)";
			}
//			final String ORDERNUMBER_SQL = "insert into bu_number(id,maxindex,type,code_type) values(S_BU_NUMBER.NEXTVAL,1,?,?)";
			
			final String ORDERNUMBER_SQL = sql;
			logger.debug("生成的sql为"+ORDERNUMBER_SQL);

			Long orderNumber = (Long) getHibernateTemplate().execute(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException {
							session.createSQLQuery(ORDERNUMBER_SQL)
//							 		.setString("type", type)
									 .setString(0, bus_type)
									 .setString(1, codeType)
									.executeUpdate();
							return null;
						}
					});

		}
	}


}

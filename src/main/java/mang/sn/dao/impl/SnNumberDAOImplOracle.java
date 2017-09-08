package mang.sn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import mang.sn.dao.SnNumberDAO;
import mang.sn.entity.SnNumber;
import mang.sn.tools.SnType;
import mang.sn.util.UUIDUtil;

@Repository
public class SnNumberDAOImplOracle  extends BaseDAOImpl<SnNumber> implements SnNumberDAO {
	private static Logger logger = Logger. getLogger(SnNumberDAOImpl.class);

	
	/**
	 * 
	 */
	public SnNumberDAOImplOracle() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getMaxIndex(final String busType,final int snType) {
		
		String hql="";
		
		SnType snTypeEnum=SnType.getInstance(snType);
		
		switch (snTypeEnum) {
		case dayDate:
//			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and to_char(t.num_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd') and t.sn_type=:snType for update";
//			hql="select t.maxindex  from SnNumber t where t.busType = :busType and to_char(t.numDate,'yyyymmdd')=to_char(sysdate,'yyyymmdd') and t.snType=:snType";
			hql="from SnNumber t where t.busType = :busType and to_char(t.numDate,'yyyymmdd')=to_char(sysdate,'yyyymmdd') and t.snType=:snType";
			break;
		case number:
//			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and t.sn_type=:snType for update";
			hql="from SnNumber t where t.busType = :busType and t.snType=:snType";
			break;
		case weekDate:
//			sql="select t.maxindex from sn_number t where t.bus_type =:busType and t.num_date>=trunc(sysdate,'d')+1 and t.num_date<sysdate and t.sn_type =:snType for update";
			hql="from SnNumber t where t.busType =:busType and t.numDate>=trunc(sysdate,'d')+1 and t.numDate<sysdate and t.snType =:snType ";
			break;
		case monthDate:
//			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and to_char(t.num_date,'yyyymm')=to_char(sysdate,'yyyymm') and t.sn_type=:snType for update";
			hql="from SnNumber t where t.busType = :busType and to_char(t.numDate,'yyyymm')=to_char(sysdate,'yyyymm') and t.snType=:snType";
			break;
		case yearDate:
//			sql="select t.maxindex  from sn_number t where t.bus_type = :busType and to_char(t.num_date,'yyyy')=to_char(sysdate,'yyyy') and t.sn_type=:snType for update";
			hql="from SnNumber t where t.busType = :busType and to_char(t.numDate,'yyyy')=to_char(sysdate,'yyyy') and t.snType=:snType";
			break;
		default:
			break;
		}
		
		Long maxIndex=null;
		
		Query query=this.getSession().createQuery(hql);
		query.setString("busType", busType);
		query.setInteger("snType", snType);
//		query.setLockOptions();
		query.setLockMode("t", LockMode.UPGRADE);
		List lis=query.list();
		if(lis.size()>0){
		 SnNumber snNumber=(SnNumber) lis.get(0);
		 Integer maxIndex_Int=snNumber.getMaxindex();
		 maxIndex=maxIndex_Int.longValue();
		}
		return maxIndex;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void updateMaxIndex(final Long maxIndex,final String busType,final int snType) {
		String hql="";
		
		SnType snTypeEnum=SnType.getInstance(snType);
		switch (snTypeEnum) {
		case dayDate: 
//			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and to_char(t.num_date,'yyyymmdd')=to_char(sysdate,'yyyymmdd') and t.sn_type=:snType";
			hql="update SnNumber t set t.maxindex = :maxIndex where t.busType = :busType and to_char(t.numDate,'yyyymmdd')=to_char(sysdate,'yyyymmdd') and t.snType=:snType";
			break;
		case monthDate: 
//			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and to_char(t.num_date,'yyyymm')=to_char(sysdate,'yyyymm') and t.sn_type=:snType";
			hql="update SnNumber t set t.maxindex = :maxIndex where t.busType = :busType and to_char(t.numDate,'yyyymm')=to_char(sysdate,'yyyymm') and t.snType=:snType";
			break;
		case yearDate:
//			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and to_char(t.num_date,'yyyy')=to_char(sysdate,'yyyy') and t.sn_type=:snType";
			hql="update SnNumber t set t.maxindex = :maxIndex where t.busType = :busType and to_char(t.numDate,'yyyy')=to_char(sysdate,'yyyy') and t.snType=:snType";
			break;
		case weekDate:
//			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type =:busType and t.num_date>=trunc(sysdate,'d')+1 and t.num_date<sysdate and t.sn_type =:snType ";
			hql="update SnNumber t set t.maxindex = :maxIndex where t.busType =:busType and t.numDate>=trunc(sysdate,'d')+1 and t.numDate<sysdate and t.snType =:snType ";
			break;
		case number:
//			sql="update SN_NUMBER t set t.maxindex = :maxIndex where t.bus_type = :busType and t.sn_type=:snType";
			hql="update SnNumber t set t.maxindex = :maxIndex where t.busType = :busType and t.snType=:snType";
			break;
		default:
			break;
		}
		
		 Query query=this.getSession().createQuery(hql);
		 query.setLong("maxIndex", maxIndex);
		 query.setString("busType", busType);
		 query.setInteger("snType", snType);
		 
		 query.executeUpdate();
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void insertMaxIndex(final String bus_type,final int snType) {
		if (bus_type != null && !("".equals(bus_type))) {
			String sql = "";
			
			SnType snTypeEnum=SnType.getInstance(snType);
			
			SnNumber snNmber=new SnNumber();
			
			switch (snTypeEnum) {
			case dayDate: 
			case weekDate: 
			case monthDate: 
			case yearDate:
				sql="insert into sn_number(id,maxindex,bus_type,num_date,sn_type) values(?,1,?,sysdate,?)";
				break;
			case number:
				sql="insert into sn_number(id,maxindex,bus_type,sn_type) values(?,1,?,?)";
				break;
			default:
				break;
			}
			
//			final String ORDERNUMBER_SQL = sql;
//			logger.debug("生成的sql为"+ORDERNUMBER_SQL);
//			Long orderNumber = (Long) getHibernateTemplate().execute(
//					new HibernateCallback() {
//						public Object doInHibernate(Session session)
//								throws HibernateException {
//							session.createSQLQuery(ORDERNUMBER_SQL)
//									 .setString(0, UUIDUtil.getUUID())
//									 .setString(1, bus_type)
//									 .setInteger(2, snType)
//									.executeUpdate();
//							return null;
//						}
//					});
			
			SQLQuery query=this.getSession().createSQLQuery(sql);
			query.setString(0, UUIDUtil.getUUID());
			query.setString(1, bus_type);
			query.setInteger(2, snType);
			query.executeUpdate();
		}
	}


}

package mang.sn.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import mang.sn.dao.SnNumberDAO;
import mang.sn.entity.SnNumber;
import mang.sn.tools.SnType;
import mang.sn.util.UUIDUtil;

@Repository
public class SnNumberDAOImplMysql  extends BaseDAOImpl<SnNumber> implements SnNumberDAO {
	private static Logger logger = Logger. getLogger(SnNumberDAOImpl.class);

	
	/**
	 * 
	 */
	public SnNumberDAOImplMysql() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public Long getMaxIndex(final String busType,final int snType) {
		
		String hql="";
		
		SnType snTypeEnum=SnType.getInstance(snType);
		
		switch (snTypeEnum) {
		case dayDate:
			hql="from SnNumber t where t.busType = :busType and date_format(t.numDate,'%Y-%m-%d')=date_format(sysdate(),'%Y-%m-%d') and t.snType=:snType order by t.numDate desc";
			break;
		case weekDate:
//			hql="from SnNumber t where t.busType =:busType and t.numDate>=subdate(curdate(),date_format(curdate(),'%w')-1) and t.numDate<sysdate() and t.snType =:snType order by t.numDate desc";
			//说明 因数据库取周几的函数   0表示周日 1表示周一  如果当前时间是周一到周六都正常 但如果是周日其取出来的是下周一的时间 所以需要在sql语句中写case when语句
			hql="from SnNumber t where t.busType =:busType and t.numDate>=subdate( curdate(), case date_format(curdate(), '%w') when 0 then 6 else (date_format(curdate(), '%w')-1) end) and t.numDate<sysdate() and t.snType =:snType order by t.numDate desc";
			break;
		case monthDate:
			hql="from SnNumber t where t.busType = :busType and date_format(t.numDate,'%Y-%m')=date_format(sysdate(),'%Y-%m') and t.snType=:snType order by t.numDate desc ";
			break;
		case yearDate:
			hql="from SnNumber t where t.busType = :busType and date_format(t.numDate,'%Y')=date_format(sysdate(),'%Y') and t.snType=:snType order by t.numDate desc";
			break;
		case number:
			hql="from SnNumber t where t.busType = :busType and t.snType=:snType order by t.numDate desc";
			break;
		default:
			break;
		}
		
		Long maxIndex=null;
		
		Query query=this.getSession().createQuery(hql);
		query.setString("busType", busType);
		query.setInteger("snType", snType);
		query.setLockOptions(LockOptions.UPGRADE);
//		query.setLockMode("t", LockMode.UPGRADE);
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
			hql="update SnNumber t set t.maxindex = :maxIndex where t.busType = :busType and date_format(t.numDate,'%Y%m%d')=date_format(sysdate(),'%Y%m%d') and t.snType=:snType";
			break;
		case weekDate:
//			hql="update SnNumber t set t.maxindex = :maxIndex where t.busType =:busType and t.numDate>=subdate(curdate(),date_format(curdate(),'%w')-1) and t.numDate<sysdate() and t.snType =:snType ";
			hql="update SnNumber t set t.maxindex = :maxIndex where t.busType =:busType and t.numDate>=subdate( curdate(), case date_format(curdate(), '%w') when 0 then 6 else (date_format(curdate(), '%w')-1) end) and t.numDate<sysdate() and t.snType =:snType ";
			break;
		case monthDate: 
			hql="update SnNumber t set t.maxindex = :maxIndex where t.busType = :busType and date_format(t.numDate,'%Y-%m')=date_format(sysdate(),'%Y-%m') and t.snType=:snType";
			break;
		case yearDate:
			hql="update SnNumber t set t.maxindex = :maxIndex where t.busType = :busType and date_format(t.numDate,'%Y')=date_format(sysdate(),'%Y') and t.snType=:snType";
			break;
		case number:
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
				sql="insert into sn_number(id,maxindex,bus_type,num_date,sn_type) values(?,1,?,sysdate(),?)";
				break;
			case number:
				sql="insert into sn_number(id,maxindex,bus_type,sn_type) values(?,1,?,?)";
				break;
			default:
				break;
			}
			
			SQLQuery query=this.getSession().createSQLQuery(sql);
			query.setString(0, UUIDUtil.getUUID());
			query.setString(1, bus_type);
			query.setInteger(2, snType);
			query.executeUpdate();
		}
	}


}

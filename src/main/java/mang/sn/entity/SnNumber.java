package mang.sn.entity;


import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 */
@Entity
@Table(name = "SN_NUMBER")
public class SnNumber implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
   	@Column(name = "id", nullable = true)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;
    
    /**
     * 单号类型0时间类型 1数字类型
     */
    @Column(name = "SN_TYPE", nullable = true)   
    private Integer snType;
    
    
    /**
     * 编号类型 取值为各自的表名
     */
    @Column(name = "bus_type", nullable = true)   
    private String busType;

    /**
     * 当前最大编号值
     */
    @Column(name = "MAXINDEX", nullable = true)   
    private int maxindex;

    /**
     * 当前日期
     */
    @Column(name = "NUM_DATE", nullable = true)   
    private Date numDate;

    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMaxindex(int maxindex) {
        this.maxindex = maxindex;
    }

    public int getMaxindex() {
        return maxindex;
    }

    public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public void setNumDate(Date numDate) {
        this.numDate = numDate;
    }

    public Date getNumDate() {
        return numDate;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getSnType() {
		return snType;
	}

	public void setSnType(Integer snType) {
		this.snType = snType;
	}
	
}

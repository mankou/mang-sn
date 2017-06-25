package mang.no.entity;


import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 */
@Entity
@Table(name = "SN_NUMBER")
public class SnNumber implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
   	@Column(name = "id", nullable = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq", sequenceName="s_bu_number")
    private Long id;
    
    /**
     * 单号类型0时间类型 1数字类型
     */
    @Column(name = "CODE_TYPE", nullable = true)   
    private String codeType;
    
    
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setMaxindex(int maxindex) {
        this.maxindex = maxindex;
    }

    public int getMaxindex() {
        return maxindex;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeType() {
        return codeType;
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
}

package mang.sn.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


/**
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "SN_NUMBER_LOG")
public class SnNumberLog implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
   	@Column(name = "id", nullable = true)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    @SequenceGenerator(name="seq", sequenceName="S_SN_NUMBER_LOG")
    private Long id;

    /**
     * 单号
     */
    @Column(name = "sn", nullable = true)   
    private String sn;
    
    /**
     * 单号生成时间
     */
    @Column(name = "RUNDATE", nullable = true) 
    private Timestamp rundate;

    /**
     * 请求人id
     */
    @Column(name = "RUID", nullable = true) 
    private String ruid;
    
    /**
     * 请示人姓名
     */
    @Column(name = "RUNAME", nullable = true) 
    private String runame;
    
    
    /**
     * 前缀
     */
    @Column(name = "PREFIX", nullable = true) 
    private String prefix;
    
    /**
     * 编号业务类型
     */
    @Column(name = "BUS_TYPE", nullable = true) 
    private String busType;

    /**
     * 单号类型（0时间类型 1数字类型）
     */
    @Column(name = "SN_TYPE", nullable = true) 
    private Integer snType;

    
    
    /**
     * 备注（调用类）
     */
    @Column(name = "INVOKE_CODE", nullable = true) 
    private String invokeCode;

    /**
     * 备注如精度等
     */
    @Column(name = "OPTION_INFO", nullable = true) 
    private String optionInfo;

    /**
     * 运行备注
     */
    @Column(name = "RUN_MEMO", nullable = true) 
    private String runMemo;

    /**
     * 运行时长单位毫秒
     */
    @Column(name = "RUNTIME_DURATION", nullable = true) 
    private Long runtimeDuration;

    /**
     * 运行开始时间精确到毫秒
     */
    @Column(name = "RUNTIME_START", nullable = true) 
    private Timestamp runtimeStart;

    /**
     * 运行结束时间精确到毫秒
     */
    @Column(name = "RUNTIME_END", nullable = true) 
    private Timestamp runtimeEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public Timestamp getRundate() {
		return rundate;
	}

	public void setRundate(Timestamp rundate) {
		this.rundate = rundate;
	}

	public String getRuid() {
		return ruid;
	}

	public void setRuid(String ruid) {
		this.ruid = ruid;
	}

	public String getRuname() {
		return runame;
	}

	public void setRuname(String runame) {
		this.runame = runame;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}


	public String getInvokeCode() {
		return invokeCode;
	}

	public void setInvokeCode(String invokeCode) {
		this.invokeCode = invokeCode;
	}

	public String getOptionInfo() {
		return optionInfo;
	}

	public void setOptionInfo(String optionInfo) {
		this.optionInfo = optionInfo;
	}

	public String getRunMemo() {
		return runMemo;
	}

	public void setRunMemo(String runMemo) {
		this.runMemo = runMemo;
	}

	public Long getRuntimeDuration() {
		return runtimeDuration;
	}

	public void setRuntimeDuration(Long runtimeDuration) {
		this.runtimeDuration = runtimeDuration;
	}

	public Timestamp getRuntimeStart() {
		return runtimeStart;
	}

	public void setRuntimeStart(Timestamp runtimeStart) {
		this.runtimeStart = runtimeStart;
	}

	public Timestamp getRuntimeEnd() {
		return runtimeEnd;
	}

	public void setRuntimeEnd(Timestamp runtimeEnd) {
		this.runtimeEnd = runtimeEnd;
	}

	public int getSnType() {
		return snType;
	}

	public void setSnType(int snType) {
		this.snType = snType;
	}
	


    
}

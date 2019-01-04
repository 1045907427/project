package com.hd.agent.report.model;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 各类报表考核目标设置
 * @author chenwei
 */
public class ReportTarget implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 目标类型(1品牌销售)
     */
    private String billtype;

    /**
     * 业务相关编号（如品牌编号）
     */
    private String busid;

    /**
     * 目标金额
     */
    private BigDecimal targetamount;

    /**
     * 年度
     */
    private String year;

    /**
     * 月份
     */
    private String month;

    /**
     * 开始日期
     */
    private String begindate;

    /**
     * 结束日期
     */
    private String enddate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否启用1是0否
     */
    private String state;

    /**
     * 次数
     */
    private Integer nums;

    /**
     * 自定义信息1
     */
    private String field01;

    /**
     * 自定义信息2
     */
    private String field02;

    /**
     * 自定义信息3
     */
    private String field03;

    /**
     * 自定义信息4
     */
    private BigDecimal field04;

    /**
     * 自定义信息5
     */
    private BigDecimal field05;

    /**
     * 自定义信息6
     */
    private BigDecimal field06;

    /**
     * 自定义信息7
     */
    private String field07;

    /**
     * 自定义信息8
     */
    private String field08;
    
    /**
     * 本期目标毛利
     */
    private BigDecimal salemargintargetamount;
    
    /**
     * 回笼目标毛利
     */
    private BigDecimal withdrawnmargintargetamount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return 目标类型(1品牌销售)
     */
    public String getBilltype() {
        return billtype;
    }

    /**
     * @param billtype 
	 *            目标类型(1品牌销售)
     */
    public void setBilltype(String billtype) {
        this.billtype = billtype == null ? null : billtype.trim();
    }

    /**
     * @return 业务相关编号（如品牌编号）
     */
    public String getBusid() {
        return busid;
    }

    /**
     * @param busid 
	 *            业务相关编号（如品牌编号）
     */
    public void setBusid(String busid) {
        this.busid = busid == null ? null : busid.trim();
    }

    /**
     * @return 目标金额
     */
    public BigDecimal getTargetamount() {
        return targetamount;
    }

    /**
     * @param targetamount 
	 *            目标金额
     */
    public void setTargetamount(BigDecimal targetamount) {
        this.targetamount = targetamount;
    }

    /**
     * @return 年度
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year 
	 *            年度
     */
    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    /**
     * @return 月份
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month 
	 *            月份
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    /**
     * @return 开始日期
     */
    public String getBegindate() {
        return begindate;
    }

    /**
     * @param begindate 
	 *            开始日期
     */
    public void setBegindate(String begindate) {
        this.begindate = begindate == null ? null : begindate.trim();
    }

    /**
     * @return 结束日期
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * @param enddate 
	 *            结束日期
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
    }

    /**
     * @return 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark 
	 *            备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return 是否启用1是0否
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            是否启用1是0否
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * @return 次数
     */
    public Integer getNums() {
        return nums;
    }

    /**
     * @param nums 
	 *            次数
     */
    public void setNums(Integer nums) {
        this.nums = nums;
    }

    /**
     * @return 自定义信息1
     */
    public String getField01() {
        return field01;
    }

    /**
     * @param field01 
	 *            自定义信息1
     */
    public void setField01(String field01) {
        this.field01 = field01 == null ? null : field01.trim();
    }

    /**
     * @return 自定义信息2
     */
    public String getField02() {
        return field02;
    }

    /**
     * @param field02 
	 *            自定义信息2
     */
    public void setField02(String field02) {
        this.field02 = field02 == null ? null : field02.trim();
    }

    /**
     * @return 自定义信息3
     */
    public String getField03() {
        return field03;
    }

    /**
     * @param field03 
	 *            自定义信息3
     */
    public void setField03(String field03) {
        this.field03 = field03 == null ? null : field03.trim();
    }

    /**
     * @return 自定义信息4
     */
    public BigDecimal getField04() {
        return field04;
    }

    /**
     * @param field04 
	 *            自定义信息4
     */
    public void setField04(BigDecimal field04) {
        this.field04 = field04;
    }

    /**
     * @return 自定义信息5
     */
    public BigDecimal getField05() {
        return field05;
    }

    /**
     * @param field05 
	 *            自定义信息5
     */
    public void setField05(BigDecimal field05) {
        this.field05 = field05;
    }

    /**
     * @return 自定义信息6
     */
    public BigDecimal getField06() {
        return field06;
    }

    /**
     * @param field06 
	 *            自定义信息6
     */
    public void setField06(BigDecimal field06) {
        this.field06 = field06;
    }

    /**
     * @return 自定义信息7
     */
    public String getField07() {
        return field07;
    }

    /**
     * @param field07 
	 *            自定义信息7
     */
    public void setField07(String field07) {
        this.field07 = field07 == null ? null : field07.trim();
    }

    /**
     * @return 自定义信息8
     */
    public String getField08() {
        return field08;
    }

    /**
     * @param field08 
	 *            自定义信息8
     */
    public void setField08(String field08) {
        this.field08 = field08 == null ? null : field08.trim();
    }

	public BigDecimal getSalemargintargetamount() {
		return salemargintargetamount;
	}

	public void setSalemargintargetamount(BigDecimal salemargintargetamount) {
		this.salemargintargetamount = salemargintargetamount;
	}

	public BigDecimal getWithdrawnmargintargetamount() {
		return withdrawnmargintargetamount;
	}

	public void setWithdrawnmargintargetamount(
			BigDecimal withdrawnmargintargetamount) {
		this.withdrawnmargintargetamount = withdrawnmargintargetamount;
	}
}
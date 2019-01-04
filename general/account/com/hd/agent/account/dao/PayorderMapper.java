/**
 * @(#)PayorderMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 15, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.account.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.account.model.Payorder;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 付款单dao
 * @author panxiaoxiao
 */
public interface PayorderMapper {

	/**
	 * 新增付款单
	 * @param payorder
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public int addPayorder(Payorder payorder);
	
	/**
	 * 获取付款单详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public Payorder getPayorderInfo(String id);
	
	/**
	 * 获取付款单列表数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public List getPayorderList(PageMap pageMap);
	
	/**
	 * 获取付款单列表数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public int getPayorderListCount(PageMap pageMap);
	
	/**
	 * 获取付款单合计
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao
	 * @date Jan 22, 2014
	 */
	public Payorder getPayorderSum(PageMap pageMap);
	
	/**
	 * 删除付款单
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public int deletePayorder(String id);
	
	/**
	 * 修改付款单
	 * @param payorder
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public int editPayorder(Payorder payorder);
	
	/**
	 * 审核付款单
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public int auditPayorder(Map map);
	
	/**
	 * 反审付款单
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 16, 2013
	 */
	public int oppauditPayorder(Map map);
	
	/**
	 * 付款单核销
	 * @param id
	 * @param writeoffamount
	 * @param remainderamount
	 * @param iswriteoff
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 17, 2013
	 */
	public int writeOffPayorder(@Param("id")String id,@Param("writeoffamount")BigDecimal writeoffamount,@Param("remainderamount")BigDecimal remainderamount,@Param("iswriteoff")String iswriteoff);

	/**
	 * 根据OA号查询付款单
	 * @param oaid
	 * @return
	 * @author limin 
	 * @date 2015-1-10
	 */
	public List<Payorder> selectPayOrderByOaid(@Param("oaid")String oaid);

    /**
     * 根据付款单编号 获取供应商的汇总金额
     * @param idarr
     * @return
     * @author lin_xx
     * 2016.7.18
     */
    public  List<Map> getSupplierPaySumData(List idarr);
    /**
     * 更新凭证生成次数
     * @throws
     * @author lin_xx
     * @date 2017-12-01
     */
    public int updatePayorderVouchertimes(Payorder payorder);

}


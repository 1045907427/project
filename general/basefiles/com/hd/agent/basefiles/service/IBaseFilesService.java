/**
 * @(#)IBaseFilesService.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 11, 2013 chenwei 创建版本
 */
package com.hd.agent.basefiles.service;

import com.hd.agent.basefiles.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 * 基础档案service
 * 提供接口与方法给业务模块使用
 * @author chenwei
 */
public interface IBaseFilesService {
	
	/**
	 * 根据商品编码获取商品详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 10, 2013
	 */
	public GoodsInfo getGoodsInfoByID(String id) throws Exception;

    /**
     * 获取商品成本价
     * @param storageid
     * @param goodsid
     * @return
     */
    public BigDecimal getGoodsCostprice(String storageid,String goodsid)throws Exception;
    /**
     * 根据商品编码获取商品(含状态)详细信息
     * @param id
     * @return
     * @throws Exception
     * @author chenwei
     * @date May 10, 2013
     */
    public GoodsInfo getGoodsStatesInfoByID(String id) throws Exception;
	
	/**
	 * 根据商品编码,主单位数量,辅单位编码
	 * 计算得到金额,辅单位数量,辅单位数量描述信息等
	 * @param goodsid		商品编码
	 * @param auxunitid		辅单位编码
	 * @param price			主单位单价
	 * @param unitNum		主单位数量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 11, 2013
	 */
	public Map countGoodsInfoNumber(String goodsid,String auxunitid,BigDecimal price,BigDecimal unitNum) throws Exception;
	
	/**
	 * 通过商品编码、主单位数量、辅单位编码计算得到辅单位数量,辅单位数量描述信息
	 * @param goodsId 商品编码
	 * @param auxUnitId 辅单位编码
	 * @param unitNum 主单位数量
	 * @return auxnum:辅单位数量、auxnumdetail:辅单位数量描述信息
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 14, 2013
	 */
	public Map countGoodsInfoNumber(String goodsId, String auxUnitId, BigDecimal unitNum) throws Exception;
	/**
	 * 获取计量单位    
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-15
	 */
	public MeteringUnit getMeteringUnitById(String id) throws Exception;
	/**
	 * 获取商品默认辅助单位信息
	 * @param goodsId
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-15
	 */
	public GoodsInfo_MteringUnitInfo getDefaultGoodsAuxMeterUnitInfo(String goodsId) throws Exception;
	/**
	 * 获取商品辅助单位信息
	 * @param goodsId
	 * @param auxUnitId
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-5-15
	 */
	public GoodsInfo_MteringUnitInfo getGoodsAuxMeterUnitInfo(String goodsId,String auxUnitId) throws Exception;
	
//	/**
//	 * 根据客户编码、品牌编码获取品牌业务员信息
//	 * @param brandid
//	 * @param customerid
//	 * @return
//	 * @throws Exception
//	 * @author panxiaoxiao 
//	 * @date Jul 23, 2013
//	 */
//	public Personnel getPersonnelByGCB(String brandid,String customerid)throws Exception;
	
	/**
	 * 获取供应商档案信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-7
	 */
	public BuySupplier getSupplierInfoById(String id) throws Exception;
	
	/**
	 * 根据辅单位，商品编码获取主单位
	 * @param unitNum
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public Map retMainUnitByUnitAndGoodid(BigDecimal unitNum,String goodsid)throws Exception;
	/**
	 * 获取仓库全部列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 5, 2013
	 */
	public List getStorageInfoAllList() throws Exception;
	/**
	 * 获取出入库类型列表
	 * @param type	1表示入库 2表示出库
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 5, 2013
	 */
	public List getStorageInOutAllList(String type) throws Exception;
	/**
	 * 根据客户编号获取联系人列表
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 12, 2013
	 */
	public List getContacterByCustomerid(String customerid) throws Exception;
	/**
	 * 根据供应商编号获取联系人列表
	 * @param supplierid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 12, 2013
	 */
	public List getContacterBySupplierid(String supplierid) throws Exception;
	/**
	 * 根据客户编号获取客户信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 15, 2014
	 */
	public Customer getCustomerByID(String id) throws Exception;
	
	/**
	 * 根据表名获取当前用户访问该表的字段权限
	 * @param tablename
	 * @param alias
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 16, 2014
	 */
	public String getAccessColunmnList(String tablename,String alias)throws Exception;
	/**
	 * 根据用户编号获取相关的仓库
	 * @param userid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 11, 2014
	 */
	public StorageInfo getStorageInfoByCarsaleuser(String userid) throws Exception;

    /**
     * 供应商档案修改后更新相关单据
     * @param map
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-26
     */
    public void editBuySupplierChangeJob(Map map)throws Exception;

    /**
     * 品牌业务员修改后更新相关单据
     * @param map
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-26
     */
    public void editCustomerChangeBranduserJob(Map map)throws Exception;

    /**
     * 客户档案修改后更新相关单据
     * @param map
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-26
     */
    public void editCustomerChangeJob(Map map)throws Exception;

    /**
     * 客户档案默认价格套修改后更新合同商品价格套价格
     * @param map
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-26
     */
    public void editCustomerChangePricesortJob(Map map)throws Exception;

    /**
     * 厂家业务员修改后更新相关单据任务
     * @param map
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-29
     */
    public void editCustomerChangeSupplieruserJob(Map map)throws Exception;

    /**
     * 商品修改后更新相关单据任务
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-06-29
     */
    public void editGoodsChangeJob()throws Exception;

    /**
     * 文件上传格式转换html(按单次计划)
     * @param map
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-07-01
     */
    public void editAttachOneConvertHtmlJob(Map map)throws Exception;

	/**
	 * 获取商品销售价格
	 * @param customerid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Dec 14, 2015
	 */
	public BigDecimal getDefaultSalesPrice(String customerid,String goodsid)throws Exception;

	/**
	 * 全局更新单据品牌业务员
	 * @return
	 * @throws Exception
	 */
	public void doGlobalUpdateBillsBranduser()throws Exception;

    /**
     * 初始化缓存客户档案与商品档案
     * @throws Exception
     */
    public void initCustomerGoodsCache() throws Exception;

	/**
	 * 客户应收款、余额<br/>
	 * 返回结果<br/>
	 * receivableAmount:客户应收款<br/>
	 * leftAmount : 余额<br/>
	 * @param customerid
	 * @return
	 * @author zhanghonghui
	 * @date 2015年11月12日
	 */
	public Map showCustomerReceivableInfoData(String customerid) throws Exception;

	/**
	 * 根据供应商档案的采购部门调整代垫收回和代垫录入、付款单中的所属部门
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-11-15
	 */
	public void doBuySupplierDeptChangeJob()throws Exception;

	/**
	 * 自动更新商品最新销售日期
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-11-21
	 */
	public void doAutoUpdateGoodsNewsaledate()throws Exception;

	/**
	 * 自动更新客户最新销售日期
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-11-21
	 */
	public void doAutoUpdateCustomerNewsalesdate()throws Exception;

	/**
	 * 基础档案更新后，更新单据中相关的信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-12-28
	 */
	public void doAutoUpdateBillByBaseFiles()throws Exception;

	/**
	 * 获取启用的仓库列表
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	public List getStorageInfoOpenList() throws Exception;
}


/**
 * @(#)IPhoneService.java
 * @author zhengziyong
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 7, 2013 zhengziyong 创建版本
 */
package com.hd.agent.phone.service;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.phone.model.Location;
import com.hd.agent.phone.model.RouteDistance;
import com.hd.agent.storage.model.Saleout;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author zhengziyong
 */
public interface IPhoneService {

    /**
     * 系统用户信息
     * @param userId
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Dec 30, 2013
     */
    public SysUser getSysUser(String userId) throws Exception;

    /**
     * 用户角色
     * @param userId
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Dec 30, 2013
     */
    public List<String> getUserAuthorityList(String userId) throws Exception;

    /**
     * 获取用户的商品档案列表
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jul 13, 2013
     */
    public PageData getGoodsInfoListBySysUser(PageMap pageMap) throws Exception;

    /**
     * 获取全部商品档案
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年6月12日
     */
    public List getAllGoodsInfoList() throws Exception;

    /**
     * 获取品牌档案列表
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Aug 14, 2013
     */
    public List getAllBrandList() throws Exception;

    /**
     * 获取所有价格套信息（不包括taxprice为null和0的数据）
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jul 24, 2013
     */
    public PageData getAllPriceInfoList(PageMap pageMap) throws Exception;

    /**
     * 查询登录用户对应人员档案关联的客户档案
     * @param username
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jul 24, 2013
     */
    public List getCustomerBySalesman(String username) throws Exception;

    /**
     * 查询登录用户对应的客户档案
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jul 24, 2013
     */
    public List getCustomerBySalesmanId() throws Exception;

    /**
     * 查询登录用户对应人员档案关联的客户合同数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Aug 14, 2013
     */
    public PageData getCustomerPriceList(PageMap pageMap) throws Exception;

    /**
     * 获取用户对应人员档案关联的客户合同数据数量
     * @param userId
     * @return
     * @throws Exception
     * @author chenwei
     * @date Mar 17, 2014
     */
    public int getCustomerPriceCount(String userId) throws Exception;

    /**
     * 获取业务员客户产品档案列表
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Jul 13, 2013
     */
    public PageData getCustomerGoodsList(PageMap pageMap) throws Exception;

    /**
     * 获取支付方式、结算方式列表
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Aug 17, 2013
     */
    public List getSameFinanceList() throws Exception;

    /**
     * 根据用户获取该用户手机客户端，数据同步是否需要更新
     * @param syncdate      同步日期
     * @return
     * @throws Exception
     */
    public Map getSyncFlag(String syncdate) throws Exception;

    /**
     *
     * @param syncdate
     * @return
     * @throws Exception
     */
    public Map getSyncBaseData(String syncdate) throws Exception;

    /**
     * 获取客户商品列表
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年8月31日
     */
    public PageData searchCustomerGoodsList(PageMap pageMap) throws Exception;

    /**
     * 根据客户编号和商品编号 获取商品价格信息
     * @param customerid
     * @param goodsid
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年9月1日
     */
    public Map getCustomerGoodsInfo(String customerid, String goodsid, String type) throws Exception;

    /**
     * 获取商品图片信息列表
     * @param pageMap
     * @return
     */
    public PageData showGoodsImageInfoList(PageMap pageMap) throws Exception;

    /**
     * 上传要货订单
     * @param jsonObject
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Aug 25, 2013
     */
    public Map updateOrder(JSONObject jsonObject) throws Exception;

    /**
     * 上传手机退货申请单
     * @param jsonObject
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年10月11日
     */
    public Map addRejectOrder(JSONObject jsonObject) throws Exception;

    /**
     * 验证销售退货的商品 是否销售过
     * @param jsonObject
     * @return
     * @throws Exception
     */
    public Map checkRejectOrder(JSONObject jsonObject) throws Exception;

    /**
     * 上传车销订单
     * @param jsonObject
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年6月25日
     */
    public Map updateOrderCar(JSONObject jsonObject) throws Exception;

    /**
     * 上传手机车销退货申请单
     * @param jsonObject
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年10月11日
     */
    public Map addCarRejectOrder(JSONObject jsonObject) throws Exception;

    /**
     * 手机上传订单，获取该客户的超账期 超信用额度信息
     * @param customerid            客户编号
     * @param  demandid             要货单编号
     * @return
     * @throws Exception
     * @author chenwei
     * @date Apr 8, 2014
     */
    public String uploadOrderMsg(String customerid, String demandid) throws Exception;

    /**
     * 添加手机位置
     * @param location
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Aug 27, 2013
     */
    public boolean addLocation(Location location) throws Exception;

    /**
     * 获取位置列表
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Aug 28, 2013
     */
    public List getLocationList() throws Exception;

    /**
     * 获取历史位置
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Sep 5, 2013
     */
    public List getLocationHistoryList(Map map) throws Exception;

    /**
     * 获取历史位置
     * @return
     * @throws Exception
     * @author lin_xx
     * @date June 13, 2016
     */
    public List getNewLocationByInfo(Map map) throws Exception;

    /**
     * 获取所有客户数据
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Nov 13, 2013
     */
    public List getAllCustomer() throws Exception;

    /**
     * 获取所有门店客户
     * @return
     * @throws Exception
     * @author chenwei
     * @date Apr 14, 2014
     */
    public List getAllChildCustomer() throws Exception;

    /**
     * 扫描系统上传退货单
     * @param jsonObject
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Nov 13, 2013
     */
    public Map uploadReject(JSONObject jsonObject) throws Exception;

    /**
     * 获取所有仓库数据
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Nov 18, 2013
     */
    public List getAllStorage() throws Exception;

    /**
     * 获取客户分销规则列表
     * @param date          日期
     * @return
     * @throws Exception
     */
    public List getDistributionRuleList(String date) throws Exception;

    /**
     * 获取特价促销
     * @return
     * @throws Exception
     */
    public List getOffPriceList(PageMap pageMap) throws Exception;
    /**
     * 获取未完成盘点单(同步至扫描系统)
     * @param map               查询条件
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Nov 18, 2013
     */
    public List getCheckListUnfinish(Map map) throws Exception;

    /**
     * 通过盘点单编号获取盘点信息和明细列表
     * @param id
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Nov 19, 2013
     */
    public List getCheckListDetail(String id) throws Exception;

    /**
     * 更新盘点单数据
     * @param jsonObject
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Nov 29, 2013
     */
    public boolean updateCheckList(JSONObject jsonObject) throws Exception;

    /**
     * 获取登录用户相关信息
     * @param userid
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Nov 29, 2013
     */
    public Map getLoginUserInfo(String userid) throws Exception;

    /**
     * 用户是否为部门主管，返回部门编号
     * @param uid
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Dec 10, 2013
     */
    public String getManagerUserDepartment(String uid) throws Exception;

    /**
     * 用户同部门所有人员列表
     * @param uid
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Dec 10, 2013
     */
    public List<Map> getDepartmentUserList(String uid, String con) throws Exception;

    /**
     * 抄单汇总报表
     * @param map
     * @return
     * @throws Exception
     */
    public List<Map> getBaseOrderQueryReport(Map map) throws Exception;

    /**
     * 客户抄货汇总查询
     * @param ids 客户编号集合
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return
     * @author zhengziyong
     * @date Dec 7, 2013
     */
    public List<Map> getCustomerOrderQueryReport(String ids, String beginDate, String endDate) throws Exception;

    /**
     * 客户抄货汇总单品明细查询
     * @param map
     * @return
     * @author zhengziyong
     * @date Dec 7, 2013
     */
    public List<Map> getCustomerGoodsOrderQueryReport(Map map) throws Exception;

    /**
     * 产品抄单汇总查询
     * @param map （t1开始日期,t2结束日期,con条件）
     * @return
     * @author zhengziyong
     * @date Dec 9, 2013
     */
    public List<Map> getGoodsOrderQueryReport(Map map) throws Exception;

    /**
     * 产品抄单汇总客户明细查询
     * @param map
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Dec 9, 2013
     */
    public List<Map> getGoodsCustomerOrderQueryReport(Map map) throws Exception;

    /**
     * 业务员抄单汇总查询
     * @param map
     * @return
     * @author zhengziyong
     * @date Dec 9, 2013
     */
    public List<Map> getSalerOrderQueryReport(Map map) throws Exception;

    /**
     * 业务员抄单汇总客户明细查询
     * @param map
     * @return
     * @author zhengziyong
     * @date Dec 10, 2013
     */
    public List<Map> getSalerCustomerOrderQueryReport(Map map) throws Exception;

    /**
     * 业务员抄单汇总客户商品明细查询
     * @param map
     * @return
     * @author zhengziyong
     * @date Dec 10, 2013
     */
    public List<Map> getSalerCustomerGoodsOrderQueryReport(Map map) throws Exception;

    /**
     * 客户销售汇总查询
     * @param map
     * @return
     * @author zhengziyong
     * @date Dec 13, 2013
     */
    public List<Map> getCustomerSaleQueryReport(Map map) throws Exception;

    /**
     * 客户销售汇总-产品明细查询
     * @param map
     * @return
     * @author zhengziyong
     * @date Dec 13, 2013
     */
    public List<Map> getCustomerGoodsSaleQueryReport(Map map) throws Exception;

    /**
     * 业务员销售汇总查询
     * @param pageMap
     * @return
     * @author zhengziyong
     * @date Dec 13, 2013
     */
    public List<Map> getSalerSaleQueryReport(PageMap pageMap) throws Exception;

    /**
     * 业务员销售汇总-产品明细查询
     * @param map
     * @return
     * @author zhengziyong
     * @date Dec 13, 2013
     */
    public List<Map> getSalerGoodsSaleQueryReport(Map map) throws Exception;

    /**
     * 销售基础报表数据
     * @param pageMap
     * @return
     * @author chenwei
     * @date 2014年5月30日
     */
    public Map getBaseSalesReport(PageMap pageMap) throws Exception;

    /**
     * 销售回笼基础报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年9月12日
     */
    public Map getBaseWithdrawReport(PageMap pageMap) throws Exception;

    /**
     * 添加反馈信息
     * @param map
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Dec 31, 2013
     */
    public boolean addFeed(Map map) throws Exception;

    /**
     * 添加业务员行程信息
     * @param distance
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Feb 11, 2014
     */
    public boolean addRouteDistance(RouteDistance distance) throws Exception;

    /**
     * 删除相关的行程记录
     * @param dateStr 日期
     * @param userId 用户编号
     * @return
     * @throws Exception
     */
    public boolean deleteRouteDistance(String dateStr, String userId) throws Exception;

    /**
     * 行程信息列表
     * @param pageMap
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Feb 11, 2014
     */
    public PageData getRouteDistanceData(PageMap pageMap) throws Exception;

    /**
     * 根据仓库编号，业务日期 获取未核对的发货单列表
     * @param storageid
     * @param date
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年6月27日
     */
    public List getSaleoutUnCheckList(String storageid, String date) throws Exception;

    /**
     * 扫描枪获取发货单列表
     * @param map           查询条件
     * @return
     * @throws Exception
     */
    public List getSaleoutForScanList(Map map) throws Exception;

    /**
     * 根据发货单编号 获取相关发货单信息
     * @param id
     * @return
     * @throws Exception
     */
    public Saleout getSaleoutInfoByID(String id) throws Exception;

    /**
     * 根据单据编号获取发货单明细列表
     * @param billid
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年6月30日
     */
    public List getSaleoutDetail(String billid) throws Exception;

    /**
     * 根据发货单编号，更新发货单发货核对确认状态
     * @param billid
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年6月30日
     */
    public Map updateSaleoutCheckFlag(String billid) throws Exception;

    /**
     * 扫描枪同步发货单后 更新发货单核对人
     * @param billid
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年6月30日
     */
    public Map updateSaleoutCheckFlagByScan(String billid) throws Exception;

    /**
     * 更新发货单明细数量 并且审核
     * @param json
     * @return
     * @throws Exception
     */
    public Map updateSaleoutAndAudit(String json) throws Exception;

    /**
     * 获取每日行程报表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 10, 2014
     */
    public List getRouteReportList(PageMap pageMap) throws Exception;

    /**
     * 获取客户商品历史销售数据
     * @param customerid
     * @param goodsid
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年10月5日
     */
    public List getCustomerHisGoodsSalesList(String customerid, String goodsid) throws Exception;

    /**
     * 根据捆绑产品组编号和客户编号 获取捆绑信息
     * @param id                捆绑产品组编号
     * @param customerid        客户编号
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年9月2日
     */
    public Map getPromotionBundData(String id, String customerid) throws Exception;

    /**
     * 获取客户列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年9月10日
     */
    public PageData searchCustomerList(PageMap pageMap) throws Exception;

    /**
     * 为扫描枪提供采购入库单列表
     * @param map               查询条件
     * @return
     * @throws Exception
     */
    public List getPurchaseEnterForScanList(Map map) throws Exception;

    /**
     * 获取采购入库单明细列表
     * @param id
     * @return
     * @throws Exception
     */
    public List getPurchaseEnterDetail(String id) throws Exception;

    /**
     * 扫描枪上传采购入库单 并且审核
     * @param json
     * @return
     * @throws Exception
     */
    public Map uploadPurchaseEnterAndAudit(String json) throws Exception;

    /**
     * 新增行程
     * @param startDate
     * @param endDate
     * @param userid
     * @return
     * @author huangzhiqian
     * @date 2016年1月20日
     */
    public Map addRootByDateAndUserId(String startDate, String endDate, String userid) throws Exception;

    /**
     * 获取客户列表数据
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年9月10日
     */
    public PageData searchOrderList(PageMap pageMap) throws Exception;

    /**
     * 根据订单编号 获取销售订单明细
     * @param orderid
     * @return
     * @throws Exception
     */
    public List getOrderDetail(String orderid) throws Exception;

    /**
     * 获取所有仓库数据
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Nov 18, 2013
     */
    public List getAllBank() throws Exception;

    /**
     * 获取业务员信息
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Nov 18, 2013
     */
    public List<Map> getSaleUser() throws Exception;
}


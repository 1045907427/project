package com.hd.agent.crm.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.model.CrmVisitRecord;
import com.hd.agent.crm.model.CrmVisitRecordDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 客户拜访记录
 * @author master
 *
 */
public interface CrmVisitRecordMapper {/**
	 * 客户拜访分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public List getCrmVisitRecordPageList(PageMap pageMap);
	/**
	 * 客户拜访分页条数统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public int getCrmVisitRecordPageCount(PageMap pageMap);
	/**
	 * 添加客户拜访记录
	 * @param crmVisitRecord
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public int insertCrmVisitRecord(CrmVisitRecord crmVisitRecord);
	/**
	 * 更新客户拜访记录
	 * @param crmVisitRecord
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public int updateCrmVisitRecord(CrmVisitRecord crmVisitRecord);
	/**
	 * 更新客户拜访记录状态
	 * @param crmVisitRecord
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public int updateCrmVisitRecordStatus(CrmVisitRecord crmVisitRecord);
	/**
	 * 删除客户拜访记录
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public int deleteCrmVisitRecord(@Param("id")String id);
	/**
	 * 删除客户拜访<br/>
	 * map中参数<br/>
	 * id :　客户拜访编号<br/>
	 * notAudit :　未审核<br/>
	 * authDataSql  ：数据权限<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public int deleteCrmVisitRecordBy(Map map);
	/**
	 * 获取客户拜访主记录
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public CrmVisitRecord getCrmVisitRecord(@Param("id")String id);
	/**
	 * map中的参数<br/>
	 * 条件参数：<br/>
	 * id : 编号
	 * authDataSql ： 数据权限参数<br/>
	 * cols: 字段权限<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public CrmVisitRecord getCrmVisitRecordBy(Map map);
	/**
	 * 更新客户摆放审核状态<br/>
	 * 条件:id
	 * 更新内容：
	 * status
	 * @param crmVisitRecord
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public int udpateCrmVisitRecordStatus(CrmVisitRecord crmVisitRecord);
	
	/**
	 * 根据客户拜访记录编号，客户拜访记录明细
	 * @param billid
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public List getCrmVisitRecordDetailList(@Param("billid")String billid);
	/**
	 * 根据ID编号，删除客户拜访记录明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int deleteCrmVisitRecordDetail(@Param("id")String id);
	/**
	 * 根据客户拜访记录编号，删除客户拜访记录明细
	 * @param billid
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int deleteCrmVisitRecordDetailByOrderid(@Param("billid")String billid);
	/**
	 * 新增客户拜访记录明细
	 * @param paymentVoucherDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int insertCrmVisitRecordDetail(CrmVisitRecordDetail paymentVoucherDetail);
	/**
	 * 更新客户拜访记录明细
	 * @param paymentVoucherDetail
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年3月24日
	 */
	public int updateCrmVisitRecordDetail(CrmVisitRecordDetail paymentVoucherDetail);
	
	/**
	 * 客户拜访记录明细查询统计<br/>
	 * map中参数<br/>
	 * billid :　客户拜访记录编号<br/>
	 * authDataSql  ：数据权限<br/>
	 * isCheck:1表示检查完成<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-6-20
	 */
	public int getCrmVisitRecordDetailCountBy(Map map);
	/**
	 * 客户拜访记录明细查询分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月8日
	 */
	public List getCrmVisitRecordDetailPageList(PageMap pageMap);
	/**
	 * 客户拜访记录明细查询分页列表统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月8日
	 */
	public int getCrmVisitRecordDetailPageCount(PageMap pageMap);
	/**
	 * 更新拜访记录明细是否合格<br/>
	 * map中参数：<br/>
	 * idarrs ：明细编号字符串<br/>
	 * id : 明细编号<br/>
	 * billid : 单据编号<br/>
	 * isqa : 合格状态,1合格，0不合格<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月9日
	 */
	public int updateOKVisitRecordDetail(Map map);
	/**
	 * 单据关联明细一起查询
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月10日
	 */
	public List getCrmVisitRecordOrderDetailPageList(PageMap pageMap);
	/**
	 * 单据关联明细一起查询 统计条数
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月10日
	 */
	public int getCrmVisitRecordOrderDetailPageCount(PageMap pageMap);
	/**
	 * 获取拜访记录及其明细信息
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月14日
	 */
	public Map getCrmVisitRecordAndDetail(Map map);
	/**
	 * 获取拜访记录明细信息
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月15日
	 */
	public CrmVisitRecordDetail getCrmVisitRecordDetail(@Param("id")String id);

    /**
     * 根据keyid获取客户拜访记录
     *
     * @param keyid
     * @return
     */
    public CrmVisitRecord getCrmVisitRecordByKeyid(@Param("keyid") String keyid);
}

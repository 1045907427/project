package com.hd.agent.crm.service;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.crm.model.CrmVisitRecord;

import java.util.Map;

/**
 * 客户拜访记录业务接口
 * @author master
 *
 */
public interface ICrmVisitRecordService {
	/**
	 * 添加客户拜访记录及其明细信息
	 * @param crmVisitRecord
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public boolean addCrmVisitRecordAndDetail(CrmVisitRecord crmVisitRecord) throws Exception;
	/**
	 * 添加客户拜访记录
	 * @param crmVisitRecord
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public boolean insertCrmVisitRecord(CrmVisitRecord crmVisitRecord) throws Exception;
	/**
	 * 更新客户拜访记录
	 * @param crmVisitRecord
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public boolean updateCrmVisitRecord(CrmVisitRecord crmVisitRecord) throws Exception;
	/**
	 * 客户拜访记录分页列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public PageData showCrmVisitRecordPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 根据ID编号获取客户拜访记录
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public CrmVisitRecord showCrmVisitRecord(String id) throws Exception;
	/**
	 * 根据ID编号删除客户拜访记录及其明细
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public boolean deleteCrmVisitRecordAndDetail(String id) throws Exception;
	/**
	 * 审核客户拜访记录
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public Map auditCrmVisitRecord(String id) throws Exception;
	/**
	 * 反审核客户拜访记录
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public Map oppauditCrmVisitRecord(String id) throws Exception;
	/**
	 *  根据数据ID编号及数据权限获取客户拜访记录
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public CrmVisitRecord showCrmVisitRecordByDataAuth(String id) throws Exception;
	/**
	 * 更新客户拜访记录及其明细
	 * @param paymentVoucher
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public boolean updateCrmVisitRecordAddDetail(CrmVisitRecord paymentVoucher) throws Exception;
	/**
	 * 根据ID编号查看客户拜访记录及明细
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public CrmVisitRecord showCrmVisitRecordAndDetail(String id) throws Exception;

	/**
	 * 客户拜访记录明细分页列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月3日
	 */
	public PageData showCrmVisitRecordDetailPageList(PageMap pageMap) throws Exception;
	/**
	 * 设置拜访记录明细合格
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月9日
	 */
	public Map setOKVisitRecordDetail(String idarrs) throws Exception;
	/**
	 * 设置拜访记录明细不合格
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月9日
	 */
	public Map setNotOKVisitRecordDetail(String idarrs) throws Exception;
	/**
	 * 客户拜访记录关联明细查询
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月10日
	 */
	public PageData showCrmVisitRecordOrderDetailPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 获取拜访记录及其明细信息<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年4月14日
	 */
	public Map getCrmVisitRecordAndDetail(Map map) throws Exception;

    /**
     * 根据keyid 获取客户拜访记录编号 没有返回null
     * 用来判断记录是否重复
     * @param keyid
     * @return
     * @throws Exception
     */
    public String geCrmVisitRecordByKeyid(String keyid) throws Exception;
}

package com.hd.agent.storage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.AllocateNotice;
import com.hd.agent.storage.model.AllocateNoticeDetail;

/**
 * 调拨通知单dao
 * @author chenwei
 */
public interface AllocateNoticeMapper {
	/**
	 * 调拨通知单添加
	 * @param allocateNotice
	 * @return
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public int addAllocateNotice(AllocateNotice allocateNotice);
	/**
	 * 调拨通知单明细添加
	 * @param allocateNoticeDetail
	 * @return
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public int addAllocateNoticeDetail(AllocateNoticeDetail allocateNoticeDetail);
	/**
	 * 获取调拨通知单列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public List showAllocateNoticeList(PageMap pageMap);
	/**
	 * 获取调拨通知单数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int showAllocateNoticeCount(PageMap pageMap);
    /**
     * 获取调拨通知单列表 (提供手机端查询)
     * @param pageMap
     * @return
     * @author chenwei
     * @date Jun 26, 2013
     */
    public List showAllocateNoticeListForPhone(PageMap pageMap);
	/**
	 * 获取调拨通知单基本信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public AllocateNotice getAllocateNoticeInfo(@Param("id")String id);
	/**
	 * 根据单据编号获取调拨通知单明细列表
	 * @param billid
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public List getAllocateNoticeDetailListByBillno(@Param("billno")String billno);
	/**
	 * 根据单据编号删除调拨通知单明细列表
	 * @param billno
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int deleteAllocateNoticeDetailByBillno(@Param("billno")String billno);
	/**
	 * 调拨通知单修改
	 * @param allocateNotice
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int editAllocateNotice(AllocateNotice  allocateNotice);
	/**
	 * 调拨通知单删除
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int deleteAllocateNotice(@Param("id")String id);
	/**
	 * 调拨通知单审核
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int auditAllocateNotice(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("businessdate")String businessdate);
	/**
	 * 调拨通知单反审
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int oppauditAllocateNotice(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
	/**
	 * 更新调拨通知单参照状态
	 * @param isrefer
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 26, 2013
	 */
	public int updateAllocateNoticeRefer(@Param("isrefer")String isrefer,@Param("id")String id);
	/**
	 * 根据单据编号和调拨通知单明细编号 获取调拨通知单明细信息
	 * @param billno
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public AllocateNoticeDetail getAllocaeNoticeDetail(@Param("billno")String billno,@Param("id")String id);
	/**
	 * 调拨通知单更新
	 * @param allocateNoticeDetail
	 * @return
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public int updateAllocateNoticeDetail(AllocateNoticeDetail allocateNoticeDetail);
	/**
	 * 关闭调拨通知单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public int closeAllocateNotice(@Param("id")String id);
	/**
	 * 获取调拨通知单导出明细列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date 2015年10月28日
	 */
	public List getAllocateNoticeListExport(PageMap pageMap);

    /**
     * 判断手机调拨申请单是否上传过
     * @param billid
     * @return
     */
    public int hasPhoneBillByAllocateNotice(@Param("billid")String billid);

    /**
     * 根据手机调拨申请彪悍 获取调拨通知单编号
     * @param billid
     * @return
     */
    public String getAllocateNoticeBillIDByPhoneBillid(@Param("billid")String billid);
}
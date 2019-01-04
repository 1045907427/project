package com.hd.agent.storage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.AllocateEnter;
import com.hd.agent.storage.model.AllocateEnterDetail;

/**
 * 调拨入库单dao
 * @author chenwei
 */
public interface AllocateEnterMapper {
	/**
	 * 调拨入库单添加
	 * @param allocateEnter
	 * @return
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public int addAllocateEnter(AllocateEnter allocateEnter);
	/**
	 * 调拨入库单明细添加
	 * @param allocateEnterDetail
	 * @return
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public int addAllocateEnterDetail(AllocateEnterDetail allocateEnterDetail);
	/**
	 * 获取调拨入库单信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public AllocateEnter getAllocateEnter(@Param("id")String id);
	/**
	 * 根据来源单据编号获取调拨入库单信息
	 * @param souceid
	 * @return
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public AllocateEnter getAllocateEnterBySourceid(@Param("sourceid")String souceid);
	/**
	 * 删除调拨入库单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public int deleteAllocateEnter(@Param("id")String id);
	/**
	 * 删除调拨入库单明细
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public int deleteAllocateEnterDetail(@Param("billno")String billno);
	/**
	 * 获取调拨入库单列表数据
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public List showAllocateEnterList(PageMap pageMap);
	/**
	 * 获取调拨入库单列表数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date Jun 27, 2013
	 */
	public int showAllocateEnterCount(PageMap pageMap);
	/**
	 * 根据单据编号获取调拨入库单明细列表
	 * @param billno
	 * @return
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public List getAllocateEnterDetailListByBillno(@Param("billno")String billno);
	/**
	 * 调拨入库单修改
	 * @param allocateEnter
	 * @return
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public int editAllocateEnter(AllocateEnter allocateEnter);
	/**
	 * 调拨入库单审核
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date Jun 28, 2013
	 */
	public int auditAllocateEnter(@Param("id")String id,@Param("userid")String userid,@Param("username")String username);
}
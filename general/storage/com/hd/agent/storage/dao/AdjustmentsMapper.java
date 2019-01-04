package com.hd.agent.storage.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.Adjustments;
import com.hd.agent.storage.model.AdjustmentsDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 库存调账单dao
 * @author chenwei
 */
public interface AdjustmentsMapper {
	/**
	 * 添加调账单基本信息
	 * @param adjustments
	 * @return
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public int addAdjustments(Adjustments adjustments);
	/**
	 * 添加调整单明细信息
	 * @param adjustmentsDetail
	 * @return
	 * @author chenwei 
	 * @date May 24, 2013
	 */
	public int addAdjustmentsDetail(AdjustmentsDetail adjustmentsDetail);
	/**
	 * 获取调账单列表
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public List showAdjustmentsList(PageMap pageMap);
	/**
	 * 获取调账单数量
	 * @param pageMap
	 * @return
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public int showAdjustmentsCount(PageMap pageMap);
	/**
	 * 获取调账单信息
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public Adjustments getAdjustmentsInfo(@Param("id")String id);
	/**
	 * 根据调账单编号获取调账单明细列表
	 * @param adjustmentsid
     * @param storageid
	 * @return
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public List getAdjustmentsDetailList(@Param("adjustmentsid")String adjustmentsid, @Param("storageid")String storageid);
	/**
	 * 删除调账单
	 * @param id
	 * @return
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public int deleteAdjustments(@Param("id")String id);
	/**
	 * 删除调账单明细
	 * @param adjustmentsid
	 * @return
	 * @author chenwei 
	 * @date May 25, 2013
	 */
	public int deleteAdjustmentsDetail(@Param("adjustmentsid")String adjustmentsid);
	/**
	 * 修改调账单
	 * @param adjustments
	 * @return
	 * @author chenwei 
	 * @date May 27, 2013
	 */
	public int editAdjustments(Adjustments adjustments);
	/**
	 * 通过盘点单编号，获取调账单信息
	 * @param checkListid
	 * @return
	 * @author chenwei 
	 * @date May 28, 2013
	 */
	public Adjustments getAdjustmentsByCheckListid(String checkListid);
	/**
	 * 审核调账单
	 * @param id
	 * @param userid
	 * @param username
	 * @return
	 * @author chenwei 
	 * @date May 28, 2013
	 */
	public int auditAdjustments(@Param("id")String id,@Param("userid")String userid,@Param("username")String username,@Param("businessdate")String businessdate);

    /**
     * 反审调账单
     * @param id
     * @return
     * @author chenwei
     * @date May 28, 2013
     */
    public int oppauditAdjustments(@Param("id")String id);
	/**
	 * 导出调账单
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 29, 2014
	 */
	public List<Map<String, Object>> getAdjustmentListExport(PageMap pageMap);
	

	/**
	 * 获取调账单列表<br/>
	 * map中参数:<br/>
	 * dataSql：数据权限<br/>
	 * idarrs: 编号字符串组，类似 1,2,3<br/>
	 * status: 表示状态3<br/>
	 * statusarr: 表示状态，类似 1,2,3<br/>
	 * billtype : 单据类型1报益单2报损单<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-10-16
	 */
	public List showAdjustmentsListBy(Map map);
	/**
	 * 更新打印次数
	 * @param adjustments
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年1月9日
	 */
	public int updateOrderPrinttimes(Adjustments adjustments);
	/**
	 * 更新调账单明细相关的商品批次编号
	 * @param id
	 * @param summarybatchid
	 * @return
	 * @author chenwei 
	 * @date 2015年10月28日
	 */
	public int updateAdjustmentsDetailSummarybatchid(@Param("id")String id,@Param("summarybatchid")String summarybatchid,@Param("price")BigDecimal price);

	/**
	 * 获取报溢调账单生成凭证的数据
	 * @param list
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Jan 03, 2018
	 */
	public List getAdjustmentsSumData(@Param("list")List list,@Param("billtype")String billtype);
}
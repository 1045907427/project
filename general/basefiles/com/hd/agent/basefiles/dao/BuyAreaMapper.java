/**
 * @(#)BuyAreaMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-17 zhanghonghui 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.BuyArea;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface BuyAreaMapper {
	/**
	 * 根据ID，获取采购区域
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public BuyArea getBuyAreaDetail(String id);
	
	/**
	 * 根据map参数，获取采购区域信息<br/>
	 *  pageMap中condition参数<br/>
	 *  parentAllChildren：获取节点及其所有子结点 <br/>	  
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public List getBuyAreaListByMap(Map map);
	/**
	 * 获取采购区域信息列表
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public List getBuyAreaList();
	/**
	 * 删除采购区域
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public int deleteBuyArea(String id);
	/**
	 * 添加采购区域
	 * @param area
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public int addBuyArea(BuyArea area);
	/**
	 * 更新采购区域
	 * @param area
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public int updateBuyArea(BuyArea area);
	/**
	 * 更新采购区域<br/>
	 * map中参数:<br/>
	 * 可更新参数<br/>
	 * newid : 新编号<br/>
	 * state : 状态4新增3暂存2保存1启用0禁用<br/>
	 * modifyuserid : 最后修改人编号<br/>
	 * modifyusername : 最后修改人<br/>
	 * modifytime : 最后修改时间<br/>
	 * openuserid : 启用人编号<br/>
	 * openusername : 启用人<br/>
	 * opentime : 启用时间<br/>
	 * closeuserid : 禁用人 编号<br/>
	 * closeusername : 禁用人<br/>
	 * closetime : 禁用时间<br/>
	 * 条件参数<br/>
	 * id : 编号<br/>
	 * authDataSql : 权限控制SQL<br/>
	 * wadduserid : 添加用户编号<br/>
	 * @param area
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public int updateBuyAreaBy(Map map);
	/**
	 * 采购区域统计<br/>
	 * Map中参数: <br/>
	 * id: 编号<br/>
	 * thisid : 本级编码 <br/>
	 * adduserid : 建档人部门编号 <br/>
	 * authDataSql : 数据权限sql <br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-19
	 */
	public int getBuyAreaCountBy(Map map);	
	
	public List returnBuyAreaIdByName(String name);
	
	/**
	 * 根据父级编码获取所有下属采购区域列表
	 * @param pid
	 * @return
	 * @author panxiaoxiao 
	 * @date May 12, 2014
	 */
	public List getBuyAreaChildList(String pid);
	
	/**
	 * 批量修改采购区域
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date May 12, 2014
	 */
	public int editBuyAreaBatch(List<BuyArea> list);
	
	/**
	 * 判断本级名称是否重复
	 * @param thisname
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public int isRepeatThisName(String thisname);
    /**
     * 获取名称为空，上级编码不为空的采购区域
     * @return
     * @author panxiaoxiao
     * @date 2015-03-18
     */
    public List<BuyArea> getBuyAreaWithoutName();
    
    /**
     * 根据本级名称获取采购区域发票
     * @param thisname
     * @return
     * @author panxiaoxiao 
     * @date Apr 16, 2015
     */
    public BuyArea getBuyAreaByThisname(@Param("thisname")String thisname);
}


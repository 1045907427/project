/**
 * @(#)BuySupplierSortMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-18 zhanghonghui 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.BuySupplierSort;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface BuySupplierSortMapper {
	/**
	 * 根据ID，获取供应商分类
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public BuySupplierSort getBuySupplierSortDetail(String id);
	
	/**
	 * 根据本级名称获取供应商详情
	 * @param thisname
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 7, 2015
	 */
	public BuySupplierSort getBuySupplierSortInfoByThisname(@Param("thisname")String thisname);
	/**
	 * 获取供应商分类信息列表
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public List getBuySupplierSortList();
	
	/**
	 * 根据map参数，获取供应商分类信息<br/>
	 *  map中参数<br/>
	 *  parentAllChildren：获取节点及其所有子结点 <br/>	
	 *  cols :字段权限<br/>
	 *  dataSql : 数据权限<br/>  
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 12, 2013
	 */
	public List getBuySupplierSortListByMap(Map map);
	/**
	 * 删除供应商分类
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public int deleteBuySupplierSort(String id);
	/**
	 * 添加供应商分类
	 * @param buySupplierSort
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public int addBuySupplierSort(BuySupplierSort buySupplierSort);
	/**
	 * 更新供应商分类
	 * @param area
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public int updateBuySupplierSort(BuySupplierSort buySupplierSort);
	/**
	 * 更新供应商分类<br/>
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
	public int updateBuySupplierSortBy(Map map);
	/**
	 * 供应商分类统计<br/>
	 * Map中参数: <br/>
	 * thisid : 本级编码 <br/>
	 * adduserid : 建档人编号 <br/>
	 * authDataSql : 数据权限sql <br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-19
	 */
	public int getBuySupplierSortCountBy(Map map);
	
	/**
	 * 根据父级编码获取所有下属供应商分类列表
	 * @param pid
	 * @return
	 * @author panxiaoxiao 
	 * @date May 12, 2014
	 */
	public List getBuySupplierSortChildList(String pid);
	
	/**
	 * 批量修改供应商分类
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date May 12, 2014
	 */
	public int editBuySupplierSortBatch(List<BuySupplierSort> list);
	
	/**
	 * 判断供应商分类本级名称是否重复
	 * @param thisname
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public int isRepeatThisName(String thisname);
    /**
     * 获取名称为空，上级编码不为空的供应分类
     * @return
     * @author panxiaoxiao
     * @date 2015-03-18
     */
    public List<BuySupplierSort> getSupplierSortWithoutName();
}


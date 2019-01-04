/**
 * @(#)IContacterService.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 17, 2013 zhengziyong 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.Contacter;
import com.hd.agent.basefiles.model.ContacterAndSort;
import com.hd.agent.basefiles.model.ContacterSort;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author zhengziyong
 */
public interface IContacterService {

	/**
	 * 获取联系人分类列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public List getContacterSortList(PageMap pageMap) throws Exception;
	
	/**
	 * 获取联系人分类详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public ContacterSort getContacterSortDetail(String id) throws Exception;
	
	/**
	 * 获取联系人信息及所有子节点信息
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public List getContacterSortParentAllChildren(PageMap pageMap) throws Exception;
	
	/**
	 * 添加联系人分类
	 * @param sort
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public boolean addContacterSort(ContacterSort sort) throws Exception;
	
	/**
	 * 新增导入联系人档案
	 * @param contacter
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 5, 2013
	 */
	public Map addDRContancter(Contacter contacter) throws Exception;
	
	/**
	 * 修改联系人信息
	 * @param sort
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public Map updateContacterSort(ContacterSort sort) throws Exception;
	
	/**
	 * 删除联系人分类
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public boolean deleteContacterSort(String id) throws Exception;
	
	/**
	 * 启用联系人分类
	 * @param sort
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public boolean updateContacterSortOpen(ContacterSort sort) throws Exception;
	
	/**
	 * 禁用联系人分类
	 * @param sort
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 17, 2013
	 */
	public boolean updateContacterSortClose(ContacterSort sort) throws Exception;
	
	/**
	 * 判断联系人分类本级名称是否重复
	 * @param thisname
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2014
	 */
	public boolean isRepeatContacterSortThisname(String thisname)throws Exception;
	
	/**
	 * 获取联系人列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 18, 2013
	 */
	public PageData getContacterData(PageMap pageMap) throws Exception;
	
	/**
	 * 获取联系人列表通过客户或供应商
	 * @param type 1为客户2为供应商
	 * @param id 客户或供应商编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 22, 2013
	 */
	public List getContacterListByCustomer(String type, String id) throws Exception;
	
	/**
	 * 添加联系人信息
	 * @param contacter
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 18, 2013
	 */
	public boolean addContacter(Contacter contacter) throws Exception;
	
	/**
	 * 获取联系人详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 19, 2013
	 */
	public Contacter getContacterDetail(String id) throws Exception;
	
	/**
	 * 获取联系人详细信息，不获取对应分类信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 4, 2013
	 */
	public Contacter getContacter(String id) throws Exception;
	
	/**
	 * 修改联系人信息
	 * @param contacter
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 19, 2013
	 */
	public boolean updateContacter(Contacter contacter, String sortEdit) throws Exception;
	
	/**
	 * 删除联系人信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 19, 2013
	 */
	public boolean deleteContacter(String id) throws Exception;
	
	/**
	 * 启用联系人信息
	 * @param contacter
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 19, 2013
	 */
	public boolean updateContacterOpen(Contacter contacter) throws Exception;
	
	/**
	 * 禁用联系人信息
	 * @param contacter
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 19, 2013
	 */
	public boolean updateContacterClose(Contacter contacter) throws Exception;
	
	/**
	 * 获取联系人对应分类列表
	 * @param id 联系人编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 20, 2013
	 */
	public List getContacterAndSortList(String id) throws Exception;
	
	/**
	 * 更新联系人默认状态（1默认0非默认）
	 * @param state 状态
	 * @param id 联系人编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Apr 25, 2013
	 */
	public boolean updateContacterDetault(String state, String id) throws Exception;
	
	/**
	 * 更新供应商联系人的默认联系人
	 * @param supplier 供应商编号
	 * @param contacterId 联系人编号，被设置成默认联系人的编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 4, 2013
	 */
	public boolean updateContacterDetaultBySupplier(String supplier, String contacterId) throws Exception;
	
	/**
	 * 导入联系人所属分类
	 * @param contacterAndSort
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 4, 2013
	 */
	public Map addDRContacterAndSort(ContacterAndSort contacterAndSort)throws Exception;
	
}


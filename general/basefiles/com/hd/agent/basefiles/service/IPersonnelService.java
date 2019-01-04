/**
 * @(#)IPersonnelService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-29 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.PersonnelBrand;
import com.hd.agent.basefiles.model.PersonnelCustomer;
import com.hd.agent.basefiles.model.Personneledu;
import com.hd.agent.basefiles.model.Personnelpost;
import com.hd.agent.basefiles.model.Personnelworks;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface IPersonnelService {

	/**
	 * 根据部门编号显示人员档案列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-29
	 */
	public PageData showPersonnelList(PageMap pageMap)throws Exception;
	
	/**
	 * 添加人员信息
	 * @param personnel
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-1
	 */
	public Map addPersonnelInfo(Personnel personnel,
			List<Personneledu> personnelEduList,
			List<Personnelworks> personnelWorksList,
			List<Personnelpost> personnelPostsList,
			List<PersonnelCustomer> personnelCustomerList,
			List<PersonnelBrand> personnelBrandList)throws Exception;
	
	/**
	 * 人员编号是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-2
	 */
	public boolean isExistPersonnelId(String id)throws Exception;
	
	/**
	 * 显示人员信息详情
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-5
	 */
	public Personnel showPersonnelInfo(String id)throws Exception;
	
	/**
	 * 人员信息编辑
	 * @param personnel
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-5
	 */
	public Map editPersonnelInfo(Personnel personnel,
			List<Personneledu> personnelEduList,
			List<Personnelworks> personnelWorksList,
			List<Personnelpost> personnelPostsList,
			List<PersonnelCustomer> personnelCustomerList,
			List<PersonnelBrand> personnelBrandList)throws Exception;
	
	/**
	 * 删除人员信息(批量)
	 * @param perIdArr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-17
	 */
	public Map deletePersonnel(String[] perIdArr)throws Exception;
	
	/**
	 * 启用人员(批量)
	 * @param perIdArr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-18
	 */
	public Map enablePersonnels(String perIdArr,String openuserid)throws Exception;
	
	/**
	 * 禁用人员(批量)
	 * @param perIdArr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-18
	 */
	public Map disablePersonnels(String perIdArr,String closeuserid,String type)throws Exception;
	
	/*------------------教育经历-------------------------*/
	/**
	 * 根据人员编号查询对应的教育经历列表 
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-2
	 */
	public List showEducationList(String personid)throws Exception;
	
	/**
	 * 删除教育经历 or 批量
	 * @param idMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean deleteEdus(String idArr)throws Exception;
	
	/**
	 * 添加教育经历 or 批量
	 * @param eduMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean addEdus(List<Personneledu> list)throws Exception;
	
	/*------------------工作经历-------------------------*/
	/**
	 * 根据人员编号查询对应的工作经历列表 
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-2
	 */
	public List showWorksList(String personid)throws Exception;
	
	/**
	 * 删除工作经历 or 批量
	 * @param idMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean deleteWorks(String idArr)throws Exception;
	
	/**
	 * 添加工作经历 or 批量
	 * @param eduMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean addWorks(List<Personnelworks> list)throws Exception;
	
	/*------------------岗位变动记录-------------------------*/
	/**
	 * 根据人员编号查询对应的岗位变动记录列表 
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-2
	 */
	public List showPostsList(String personid)throws Exception;
	
	/**
	 * 删除岗位变动记录 or 批量
	 * @param idMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean deletePosts(String idArr)throws Exception;
	
	/**
	 * 添加岗位变动记录 or 批量
	 * @param eduMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public boolean addPosts(List<Personnelpost> list)throws Exception;
	
	/**
	 * 根据籍贯编号id获取籍贯名称
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-28
	 */
	public String getNPname(String id)throws Exception;
	
	/**
	 * 根据人员id数组字符串获取人员档案列表
	 * @param idsStr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-28
	 */
	public List<Personnel> getPersonnelListByIds(String idsStr)throws Exception;
	
	/**
	 * Excel导入添加人员信息
	 * @param personnel
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-20
	 */
	public Map addDRPersonnelInfo(Personnel personnel)throws Exception;
	
	/**
	 * 导入新增工作岗位变更
	 * @param personnelpost
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2013
	 */
	public Map addDRPersonnelPost(List<Personnelpost> list)throws Exception;
	
	/**
	 * 导入新增工作经历
	 * @param personnelworks
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2013
	 */
	public Map addDRPersonnelWork(List<Personnelworks> list)throws Exception;
	
	/**
	 * 导入新增教育经历
	 * @param personneledu
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 26, 2013
	 */
	public Map addDRPersonnelEdu(List<Personneledu> list)throws Exception;
	
	/**
	 * 根据业务属性employetype，获取人员列表
	 * @param employetypeArr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public List<Personnel> getPersListByOperType(String employetypeId)throws Exception;
	
	/**
	 * 根据业务属性employetype，获取人员所属部门列表
	 * @param employetypeId
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 17, 2015
	 */
	public List<Map> getDeptListByOpertype(String employetypeId)throws Exception;
	/**
	 * 根据业务属性employetypeId，部门编号，获取人员列表
	 * 0:采购员 1：客户业务员 2理货员 3品牌业务员
	 * @param employetypeId
	 * @param deptid
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 17, 2013
	 */
	public List<Personnel> getPersListByOperTypeAndDeptid(String employetypeId,String deptid)throws Exception;
	
	/**
	 * 根据人员编码获取对应客户列表数据
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 6, 2014
	 */
	public List showCustomerList(String personid)throws Exception;
	
	public PageData showCustomerList(PageMap pageMap)throws Exception;
	
	/**
	 * 根据人员编码获取对应品牌列表数据
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 6, 2014
	 */
	public List showBrandList(String personid)throws Exception;
	
	/**
	 * 获取对应品牌列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public PageData showBrandList(PageMap pageMap)throws Exception;
	
	/**
	 * 获取厂家对应品牌列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public PageData showSupplierBrandList(PageMap pageMap)throws Exception;
	
	/**
	 * 根据商品编码、客户编码、品牌编码获取人员信息
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 22, 2013
	 */
	public Personnel getPersonnelByGCB(Map map)throws Exception;
	
	/**
	 * 获取对应品牌详情
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 22, 2013
	 */
	public PersonnelBrand getBrandInfo(Integer id)throws Exception;
	
	public PageData getCustomerListForPsnCstm(PageMap pageMap)throws Exception;
	
	/**
	 * 根据客户编码获取对应客户列表数据
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 31, 2013
	 */
	public List<PersonnelCustomer> getCustomerListByCustomerid(String customerid)throws Exception;
	
	/**
	 * 根据部门编码获取人员所有列表数据
	 * @param belongdeptid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getPersonnelListByDeptid(String belongdeptid)throws Exception;
	
	/**
	 * 根据人员编码获取对应客户列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public PageData getCustomerListFromPsnBrandAndCustomer(PageMap pageMap)throws Exception;
	
	/**
	 * 根据人员编码获取对应品牌列表数据
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public PageData getBrandListFromPsnBrandAndCustomer(PageMap pageMap)throws Exception;
	
	/**
	 * 导入对应品牌和客户
	 * @param map (List<PersonnelBrand>,List<PersonnelCustomer>)
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public Map addDRPsnlBrandAndCustomer(Map map)throws Exception;
	
	/**
	 * 根据客户验证对应品牌和客户是否重复
	 * @param customerid
	 * @param brandidStr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 25, 2013
	 */
	public boolean checkBrandAndCustomerRepeat(String customerid,String brandidStr,String employetype)throws Exception;
	
	/**
	 * 根据品牌验证对应品牌和客户是否重复
	 * @param brandid
	 * @param customeridStr
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Aug 25, 2013
	 */
	public boolean checkBrandAndCustomerRepeat2(String brandid,String customeridStr,String employetype)throws Exception;
	
	/**
	 * 修改对应品牌对应客户
	 * @param customerList
	 * @param brandList
	 * @param personnel
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 6, 2014
	 */
	public Map editBrandAndCustomer(List<PersonnelCustomer> customerList,
			List<PersonnelBrand> brandList,Personnel personnel)throws Exception;
	
	/**
	 * 分配人员对应客户调用接口
	 * @param blist
	 * @param clist
	 * @param personnel
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 7, 2014
	 */
	public boolean addPsnBrandCustomer(Map paramMap)throws Exception;
	
	/**
	 * 判断人员业务属性新增对应客户（分配人员对应客户）
	 * @param paramMap （employetype 3品牌业务员 7厂家业务员）
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 7, 2014
	 */
	public boolean addOfEmployetypeToPsnBrandCustomer(Map paramMap)throws Exception;

	/**
	 * 分配人员对应客户
	 * @param paramMap （employetype 3品牌业务员 7厂家业务员）
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-06-06
	 */
	public boolean addOfEmployetypeToPsnBrandCustomer2(Map paramMap)throws Exception;
	
	/**
	 * 根据客户数组字符串获取人员列表数据(分配品牌业务员)
	 * @param customerids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 8, 2014
	 */
	public List getPersonListByCustoemrids(String customerids)throws Exception;

	/**
	 * 根据人员编码获取对应客户列表数据分页
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 8, 2014
	 */
	public PageData getPersonCustomerList(PageMap pageMap)throws Exception;
	
	/**
	 * 根据人员编码获取厂家对应客户列表数据分页
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public PageData getPersonSupplierCustomerList(PageMap pageMap)throws Exception;
	
	/**
	 * 根据对应客户编码删除人员对应客户
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 8, 2014
	 */
	public boolean deletePersonCustomer(String customerids,String personid,String employetype)throws Exception;

	/**
	 * 根据人员添加对应客户（品牌业务员）
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 8, 2014
	 */
	public boolean addPersonCustomer(String customerids,String personid,String employetype)throws Exception;

	/**
	 * 根据对应品牌编码删除人员对应品牌
	 * @param brandids
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 5, 2014
	 */
	public boolean deletePersonBrand(String brandids,String personid)throws Exception;

	/**
	 * 根据对应品牌编码删除人员对应品牌
	 * @param brandids
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Nov 5, 2014
	 */
	public boolean addPersonBrand(String brandids,String personid)throws Exception;

	/**
	 * 根据客户编号集获取对应客户数据列表
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 9, 2014
	 */
	public List getPersonCustomerListByCustomerids(String customerids)throws Exception;
	
	/**
	 * 根据品牌编号集获取对应品牌数据列表
	 * @param brandids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 12, 2014
	 */
	public List getPersonBrandListByBrandids(String brandids)throws Exception;
	
	/**
	 * 根据客户编号集获取对应客户数据列表(分页)
	 * @param customerids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 28, 2014
	 */
	public PageData getPersonCustomerListByCustomerids(String customerids,PageMap pageMap)throws Exception;
	
	/**
	 * 根据品牌编码字符串集合获取对应品牌列表数据
	 * @param brandids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 4, 2014
	 */
	public List getBrandToPersonList(String brandids,String personid)throws Exception;
	
	/**
	 * 根据人员编码获取厂家业务员对应品牌列表数据
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 7, 2014
	 */
	public List getSupplierBrandListNoPage(String personid)throws Exception;
	
	/**
	 * 根据人员编码获取厂家业务员对应客户列表数据
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 20, 2015
	 */
	public List getSupplierCustomerListNoPage(String personid)throws Exception;
	
	/**
	 * 根据客户数组字符串获取人员列表数据(分配厂家业务员)
	 * @param customerids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 8, 2014
	 */
	public List getSupplierPersonListByCustoemrids(String customerids)throws Exception;
	
	/**
	 * 判断客户业务员是否存在对应客户数据
	 * @param personid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Nov 8, 2014
	 */
	public boolean checkEmployetypeOfCustomerData(String personid,String employetype)throws Exception;
	
	/**
	 * 添加删除对应品牌或对应客户要清空业务员的数据
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jan 28, 2015
	 */
	public boolean addNoPersonBrandAndCustomer(List<Map<String, String>> list)throws Exception;

    /**
     * 判断分配的业务员是否存在重复的品牌，具体哪些业务员重复
     * @param sql
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-28
     */
    public Map checkPersonsIsRepeatBrandids(String sql)throws Exception;
    
    /**
     * 删除人员档案附件
     * @param personid
     * @param fileid
     * @return
     * @throws Exception
     * @author panxiaoxiao 
     * @date 2015年6月5日
     */
    public boolean deletePersonFiles(String personid,String fileid)throws Exception;
    
    /**
     * 添加人员档案附件
     * @param personid
     * @param fileid
     * @return
     * @throws Exception
     * @author panxiaoxiao 
     * @date 2015年6月5日
     */
    public boolean addPersonFiles(String personid,String fileid)throws Exception;

	/**
	 * 根据参数删除需清空业务员所对应品牌和对应客户数据
	 * @param map1
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-04-22
	 */
	public boolean deleteNoPersonBrandAndCustomer(Map map1)throws Exception;

	/**
	 * 清除业务员
	 * @param employetype
	 * @param customerids
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-06-06
	 */
	public boolean doClearCustomerToPsn(String employetype, String customerids)throws Exception;

	/**
	 * 所有单据的品牌业务员重新生成
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-07-14
	 */
	public boolean doTotalBillsBranduserReset()throws Exception;
}


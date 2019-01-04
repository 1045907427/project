/**
 * @(#)PersonnelMapper.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-29 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.PersonnelBrand;
import com.hd.agent.basefiles.model.PersonnelBrandAndCustomer;
import com.hd.agent.basefiles.model.PersonnelCustomer;
import com.hd.agent.basefiles.model.Personneledu;
import com.hd.agent.basefiles.model.Personnelpost;
import com.hd.agent.basefiles.model.Personnelworks;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public interface PersonnelMapper {

    /**
     * 获取客户业务员的客户
     * @param map
     * @return
     */
    public List<PersonnelBrandAndCustomer> getCustomeridUnionBrandid (Map map);

    /**
     * 获取品牌业务员的客户
     * @param map
     * @return
     */
    public List<PersonnelBrandAndCustomer> getCustListByMap (Map map);

    public Personnel returnPersnnelById(String id);
	
	/**
	 * 根据部门编号获取人员列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-29
	 */
	public List getPersonnelList(PageMap pageMap);

	/**
	 * 根据部门编号获取到的人员列表列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-29
	 */
	public int getPersonnelCount(PageMap pageMap);
	
	/**
	 * 添加人员信息
	 * @param personnel
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-1
	 */
	public int addPersonnelInfo(Personnel personnel);
	
	/**
	 * 人员编号是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-2
	 */
	public int isExistPersonnelId(@Param("id")String id);
	
	/**
	 * 获取人员信息详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-5
	 */
	public Personnel getPersonnelInfo(@Param("id")String id);
	/**
	 * 通过姓名获取人员列表
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date Sep 28, 2013
	 */
	public List getPersonnelByName(@Param("name")String name);
	/**
	 * 根据部门编号和姓名获取该人员档案
	 * @param deptid
	 * @param name
	 * @return
	 * @author chenwei 
	 * @date 2014年11月28日
	 */
	public Personnel getPersonnelByDeptidAndName(@Param("deptid")String deptid,@Param("name")String name);
	/**
	 * 人员信息编辑
	 * @param personnel
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-5
	 */
	public int editPersonnelInfo(Personnel personnel);
	
	/**
	 * 人员信息删除(批量)
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-17
	 */
	public int deletePersonnelInfos(@Param("perIdsArr")String[] perIdsArr);
	
	/**
	 * 启用人员(批量)
	 * @param enableList
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-18
	 */
	public int enablePersonnels(Map listMap);
	
	/**
	 * 禁用人员(批量)
	 * @param disableList
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-18
	 */
	public int disablePersonnels(Map listMap);
	
	/**
	 * 启用人员
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-3
	 */
	public int enablePersonnel(Map paramMap);
	
	/**
	 * 禁用人员
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-3
	 */
	public int disablePersonnel(Map paramMap);
	
	/**
	 * 根据部门id数组字符串获取部门详情列表
	 * @param idsStr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-3-28
	 */
	public List<Personnel> getPersonnelListByIds(@Param("idsArr")String[] idsArr);
	
	/**
	 * 根据业务属性employetype，获取人员列表
	 * @param employetypeArr
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public List<Personnel> getPersListByOperType(@Param("employetype")String employetype);
	
	/**
	 * 根据业务属性employetype，获取人员所属部门列表
	 * @param employetype
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 17, 2015
	 */
	public List<Map> getDeptListByOpertype(@Param("employetype")String employetype);
	/**
	 * 根据业务属性employetypeId，部门编号，获取人员列表
	 * 0:采购员 1：客户业务员 2理货员 3品牌业务员
	 * @param employetype
	 * @param deptid
	 * @return
	 * @author chenwei 
	 * @date Aug 17, 2013
	 */
	public List<Personnel> getPersListByOperTypeAndDeptid(@Param("employetype")String employetype,@Param("deptid")String deptid);

    /**
     * 获取对应部门编号下的启用的客户业务员
     * @param deptid
     * @return
     * @author chenwei
     * @date June 13, 2016
     */
    public List<Personnel> getPersonListByEmploytypeAndDeptid(@Param("deptid")String deptid);

	/**
	 * 获取所有人员列表
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-4-17
	 */
	public List<Personnel> getAllPersonList();
	
	/**
	 * 根据业务属性获取不重复的所属部门列表数据
	 * @param employetype
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 6, 2014
	 */
	public List<Personnel> getPsnBelongDeptByOperType(@Param("employetype")String employetype);

	/**
	 * 获取客户业务员
	 * @return
	 * @author wanghongteng
	 * @date 2018-4-20
	 */
	public List<Map> getSaleUser();
	
	/*----------------------教育经历----------------------------------------*/
	
	/**
	 * 根据人员编号获取教育经历列表
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public List getEducationList(@Param("personid")String personid);
	
	/**
	 * 删除教育经历(批量)
	 * @param idMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public int deleteEdus(List<String> list);
	
	/**
	 * 添加教育经历(批量)
	 * @param eduMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public int addEdus(Map eduListMap);
	
	/**
	 * 修改教育经历 
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-4
	 */
	public int editEdus(Personneledu personneledu);
	
	/**
	 * 根据人员编号personid删除教育经历 
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-17
	 */
	public int deleteEduByPerid(@Param("personid")String personid);
	
	/**
	 * 判断教育经历是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 25, 2013
	 */
	public int isExistPersonnelEdu(@Param("id")int id);
	
	/*----------------------工作经历----------------------------------------*/
	
	/**
	 * 根据人员编号获取工作经历列表 
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-16
	 */
	public List getWorksList(@Param("personid")String personid);
	
	/**
	 * 删除工作经历(批量)
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-16
	 */
	public int deleteWorks(List<String> list);
	
	/**
	 * 添加工作经历(批量)
	 * @param workListMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-16
	 */
	public int addWorks(Map workListMap);
	
	/**
	 * 修改工作经历
	 * @param personnelworks
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-16
	 */
	public int editWork(Personnelworks personnelworks);
	
	/**
	 * 根据人员编号personid删除工作经历
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-17
	 */
	public int deleteWorkByPerid(@Param("personid")String personid);
	
	/**
	 * 判断工作经历是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 25, 2013
	 */
	public int isExistPersonnelWork(@Param("id")int id);
	
	/*----------------------岗位变动记录----------------------------------------*/
	
	/**
	 * 根据人员编号获取岗位变动记录
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-16
	 */
	public List getPostList(@Param("personid")String personid);
	
	/**
	 * 删除岗位变动记录(批量)
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-16
	 */
	public int deletePosts(List<String> list);
	
	/**
	 * 添加岗位变动记录(批量)
	 * @param postListMap
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-16
	 */
	public int addPosts(Map postListMap);
	
	/**
	 * 修改岗位变动记录
	 * @param personnelpost
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-16
	 */
	public int editPost(Personnelpost personnelpost);
	
	/**
	 * 根据人员编号personid删除岗位变动记录
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-17
	 */
	public int deletePostByPerid(@Param("personid")String personid);
	
	/**
	 * 判断工作岗位是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 25, 2013
	 */
	public int isExistPersonnelPost(@Param("id")int id);
	
	/*----------------------对应客户----------------------------------------*/
	
	/**
	 * 根据人员编号获取对应客户列表数据
	 */
	public List getCustomerList(@Param("personid")String personid);
	
	/**
	 * 根据人员编码获取对应客户列表数据分页
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 24, 2013
	 */
	public List getCustomerList2(PageMap pageMap);
	
	/**
	 * 根据人员编号获取对应客户列表数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 24, 2013
	 */
	public int getCustomerListCount(PageMap pageMap);
	
	/**
	 * 根据编号删除对应客户
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public int deleteCustomer(List<String> list);
	
	/**
	 * 批量添加对应客户
	 * @param customerListMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public int addCustomer(Map customerListMap);

	/**
	 * 品牌人员添加对应客户
	 * @return
	 * @author panxiaoxiao
	 * @date Jul 19, 2013
	 */
	public int addPersonnelCustomer(@Param("personid") String personid,@Param("customerid") String customerid);
	/**
	 * 修改对应客户
	 * @param personnelCustomer
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public int editCustomer(PersonnelCustomer personnelCustomer);
	
	/**
	 * 根据人员编号personid删除对应客户
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public int deleteCustomerByPerid(@Param("personid")String personid);
	
	/**
	 * 判断对应客户是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public int isExistPersonnelCustomer(@Param("id")int id);
	
	/*----------------------对应品牌----------------------------------------*/
	
	/**
	 * 根据人员编号获取对应品牌列表数据
	 */
	public List getBrandList(@Param("personid")String personid);
	
	/**
	 * 根据编号删除对应品牌
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public int deleteBrand(List<String> list);
	
	/**
	 * 批量添加对应品牌
	 * @param brandListMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public int addBrand(Map brandListMap);

	/**
	 * 添加对应品牌
	 * @return
	 * @author panxiaoxiao
	 * @date Jul 19, 2013
	 */
	public int addPersonBrand(@Param("personid") String personid,@Param("brandid") String brandid);
	/**
	 * 修改对应品牌
	 * @param personnelBrand
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public int editBrand(PersonnelBrand personnelBrand);
	
	/**
	 * 根据人员编号personid删除对应品牌
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public int deleteBrandByPerid(@Param("personid")String personid);
	
	/**
	 * 判断对应品牌是否存在
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 19, 2013
	 */
	public int isExistPersonnelBrand(@Param("id")int id);
	
	public int isExistPersonBrandList(@Param("personid")String personid);
	
	public int editBrandAndCutomer(PersonnelBrandAndCustomer personnelBrandAndCustomer);
	
	public List getBrandCustomerListByBrandid(@Param("brandid")String brandid);
	/*----------------------籍贯----------------------------------------*/
	
	/**
	 * 根据籍贯编号id获取籍贯名称
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-2-28
	 */
	public String getNPname(@Param("id")String id);
	
	/**
	 * 根据商品编码、客户编码、品牌编码获取人员信息
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 22, 2013
	 */
	public Personnel getPersonnelByGCB(Map map);
	/**
	 * 根据商品编码、客户编码、品牌编码获取品牌业务员人员编号
	 * @param customerid
	 * @param brandid
	 * @return
	 * @author chenwei 
	 * @date 2014年10月16日
	 */
	public String getBrandUserIdByCustomerAndBrand(@Param("customerid")String customerid,@Param("brandid")String brandid);
	/**
	 * 获取对应品牌详情
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 22, 2013
	 */
	public PersonnelBrand getBrandInfo(Integer id);
	
	/**
	 * 获取对应品牌数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public List getBrandList2(PageMap pageMap);
	
	/**
	 * 获取对应品牌数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int getBrandListCount(PageMap pageMap);
	
	/**
	 * 根据客户编码获取对应客户列表
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 31, 2013
	 */
	public List<PersonnelCustomer> getCustomerByCustomerid(String customerid);
	
	/**
	 * 根据客户编码、所属分公司获取对应客户列表
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 11, 2015
	 */
	public List<PersonnelCustomer> getPsnCustomerListByMap(Map map);
	
	/**
	 * 根据客户编码修改对应客户
	 * @param oldcustomerid
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 22, 2014
	 */
	public int editPersonCustomer(@Param("oldcustomerid")String oldcustomerid,@Param("customerid")String customerid);
	
	/**
	 * 根据部门编码获取人员所有列表数据 
	 * @param belongdeptid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 2, 2013
	 */
	public List getPersonnelListByDeptid(@Param("belongdeptid")String belongdeptid);
	
	/**
	 * 根据部门编码获取上级部门编码获取所有所属该上级部门的人员
	 * @param belongdeptid
	 * @return
	 * @author panxiaoxiao 
	 * @date Mar 29, 2014
	 */
	public List getPersonListByWithoutDeptid(@Param("belongdeptid")String belongdeptid);
	
	/**
	 * 根据人员编码获取客户数据列表
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 23, 2013
	 */
	public List getCustomerListFromPsnBrandAndCustomer(PageMap pageMap);
	
	/**
	 * 根据人员编码获取客户数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 23, 2013
	 */
	public int getCustomerCountFromPsnBrandAndCustomer(PageMap pageMap);
	
	/**
	 * 根据人员编码获取品牌数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 23, 2013
	 */
	public List getBrandListFromPsnBrandAndCustomer(PageMap pageMap);
	
	/**
	 * 根据人员编码获取品牌数据数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 23, 2013
	 */
	public int getBrandCountFromPsnBrandAndCustomer(PageMap pageMap);
	
	/**
	 * 批量添加对应客户和品牌
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public int addBrandAndCustomer(PersonnelBrandAndCustomer personnelBrandAndCustomer);
	
	/**
	 * 根据人员编码判断是否存在对应客户和品牌
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public int getBrandAndCustomerCount(@Param("personid")String personid);

	/**
	 * 根据人员编码删除对应客户对应品牌
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public int deleteBrandAndCustomerByPersonid(@Param("personid")String personid);
	
	/**
	 * 根据人员编码获取所有不重复的对应品牌
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public List getBrandListByPersonnel(@Param("personid")String personid);
	
	/**
	 * 根据参数删除对应客户和对应品牌
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public int deleteBrandAndCustomerByCustomerid(Map map);
	
	/**
	 * 根据品牌编码删除对应客户和对应品牌
	 * @param strArr
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public int deleteBrandAndCustomerByBrandid(Map map);
	
	/**
	 * 根据参数删除对应客户对应品牌
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 10, 2014
	 */
	public int deleteBrandAndCustomerByParam(Map map);
	
	/**
	 * 根据品牌编码判断是否存在对应客户和品牌
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 25, 2013
	 */
	public int getBrandListByMap(Map map);
	
	/**
	 * 判断对应品牌和客户是否重复
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public int checkBrandAndCustomerRepeat(PersonnelBrandAndCustomer personnelBrandAndCustomer);
	
	/**
	 * 根据人员编号获取对应品牌和客户列表数据
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public List getBrandAndCustomerList(@Param("personid")String personid);

	/**
	 * 根据人员编号获取对应品牌和客户列表数据
	 * @param map
	 * @return
	 */
	public List getBrandAndCustomerListMap(Map map);
	
	/**
	 * 根据客户编码获取品牌数据列表
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 25, 2013
	 */
	public List getBrandListByCustomerid(@Param("customerid")String customerid);
	
	/**
	 * 根据品牌编码获取客户数据列表
	 * @param brandid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 25, 2013
	 */
	public List getCustomerListByBrandid(@Param("brandid")String brandid);
	
	/**
	 * 根据客户编码获取对应品牌客户列表数据
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 22, 2014
	 */
	public List<PersonnelBrandAndCustomer> getBrandCustomerListByCustomerid(@Param("customerid")String customerid);
	
	/**
	 * 根据客户编码修改对应品牌客户数据
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Oct 22, 2014
	 */
	public int editPersonBrandAndCustomerByCustomerid(@Param("oldcustomerid")String oldcustomerid,@Param("customerid")String customerid);
	
	public List returnPersnnelIdByName(String name);
	
	/**
	 * 根据人员编码、客户编码生成对应客户对应品牌品牌业务员表
	 * @param personid
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 26, 2014
	 */
	public int addPsnBrandAndCustomerFromTbrandAndTcustomerBypsnWithcst(@Param("personid")String personid,@Param("customerid")String customerid);
	
	/**
	 * 判断是否已存在对应品牌客户人员
	 * @param brandid
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 5, 2014
	 */
	public int checkExistBrandAndCusotmerPerson(@Param("brandid")String brandid,@Param("customerid")String customerid);
	
	/**
	 * 根据人员编码生成对应客户对应品牌品牌业务员表
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 26, 2014
	 */
	public int addPsnBrandAndCustomerFromTbrandAndTcustomer(@Param("personid")String personid);
	
	/**
	 * 根据人员编码客户编码删除对应客户对应品牌
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 10, 2014
	 */
	public int deletePsnCustomerByPsnidAndCustomerid(Map map);
	
	public Personnel getPersonnelByUserId(String userId);
	
	/**
	 * 根据品牌编码获取所有对应品牌列表数据
	 * @param brandid
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 4, 2013
	 */
	public List getPersonBrandListByBrandid(String brandid);
	
	/**
	 * 根据品牌编码集字符串获取对应品牌列表数据
	 * @param brandids
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 12, 2014
	 */
	public List getPersonBrandListByBrandids(String brandids);
	
	/**
	 * 根据部门编码获取不同人员编码对应品牌列表
	 * @param deptid
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 6, 2013
	 */
	public List getDistinctBrandGroupPersonid(String deptid);
	
	/**
	 * 新增对应品牌（单个）
	 * @param personnelBrand
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 6, 2013
	 */
	public int addBrandAlone(PersonnelBrand personnelBrand);
	
	/**
	 * 根据品牌编码删除对应品牌
	 * @param brandid
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 6, 2013
	 */
	public int deleteBrandListByBrandid(String brandid);
	
	/**
	 * 根据品牌编码删除对应品牌客户
	 * @param brandid
	 * @return
	 * @author panxiaoxiao 
	 * @date Dec 6, 2013
	 */
	public int deleteBrandAndCustomerByBrand(String brandid);
	
	/**
	 * 根据客户编码删除对应客户
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 7, 2014
	 */
	public int deleteCustomerListByCustomerid(String customerid);
	
	/**
	 * 根据客户编码删除对应客户对应品牌
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 7, 2014
	 */
	public int deleteBrandAndCustomerByCustomer(String customerid);
	
	/**
	 * 根据客户编码品牌编码删除对应客户对应品牌表数据
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 7, 2014
	 */
	public int deleteBrandAndCustomerByCustomerWithBrandids(Map map);
	
	/**
	 * 根据客户数组字符串获取人员列表数据(分配品牌业务员)
	 * @param customerids
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 8, 2014
	 */
	public List getPersonListByCustoemrids(String customerids);
	
	/**
	 * 根据编码获取对应客户信息
	 * @param id
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 9, 2014
	 */
	public PersonnelCustomer getPersonCustomerInfo(Map map);
	
	/**
	 * 根据人员编码和品牌编码获取对应品牌信息
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 5, 2014
	 */
	public PersonnelBrand getPersonBrandInfo(Map map);
	
	/**
	 * 根据客户编码人员编码删除对应客户
	 * @param customerid
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 9, 2014
	 */
	public int deletePersonCustomerByParam(Map map);
	
	/**
	 * 根据品牌编码人员编码删除对应品牌
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 5, 2014
	 */
	public int deletePersonBrandByParam(Map map);
	
	/**
	 * 根据参数删除对应品牌对应客户数据
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 10, 2014
	 */
	public int deleteBrandAndCustomerListByParam(Map map);
	
	/**
	 * 根据人员所属部门，对应客户获取人员编码集
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 25, 2014
	 */
	public List getPersonListByCustomerWithCustomer(Map map);
	
	/**
	 * 根据参数获取品牌业务员对应客户品牌数据列表
	 * @param map （brandArr，customerid，personid）
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 7, 2014
	 */
	public List getBrandCustomerListByMap(Map map);
	
	/*------------------------- 厂家业务员对应客户---------------------------------------*/
	
	/**
	 * 根据人员编码获取厂家业务员对应客户数量
	 */
	public int getSupplierCustomerListCount(@Param("personid")String personid);
	
	/**
	 * 根据人员编码删除对应的厂家业务员对应客户
	 * @param map (personid,customerid)
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int deleteSupplierCustomerListByParam(Map map);
	
	/**
	 * 批量新增厂家业务员对应客户
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int addSupplierCustomer(Map map);

	/**
	 * 批量新增厂家业务员对应客户
	 * @param map
	 * @return
	 * @author panxiaoxiao
	 * @date Nov 6, 2014
	 */
	public int addSupplierCustomerDetail(@Param("personid")String personid,@Param("customerid") String customerid);
	/**
	 * 根据人员编码获取厂家业务员对应品牌数量
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int getSupplierBrandListCount(@Param("personid")String personid);
	
	/**
	 * 根据人员编码删除对应的厂家业务员对应品牌
	 * @param map (personid,brandid)
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int deleteSupplierBrandListByParam(Map map);
	
	/**
	 * 批量新增厂家业务员对应品牌
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int addSupplierBrand(Map map);

	/**
	 * 新增厂家业务员对应品牌
	 * @param map
	 * @return
	 * @author panxiaoxiao
	 * @date Nov 6, 2014
	 */
	public int addSupplierBrandDetail(@Param("personid") String personid,@Param("brandid") String brandid);
	/**
	 * 根据人员编码删除厂家对应客户对应品牌
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int deleteSupplierBrandAndCustomerByPersonid(@Param("personid")String personid);
	
	/**
	 * 删除组合的对应客户对应品牌表
	 * @param map (personid,brandid,customerid)
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int deleteSupplierBrandAndCustomerByMap(Map map);
	
	/**
	 * 根据人员编码组合对应客户对应品牌厂家业务员表
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int addOfSupplierBrandAndCustomer(@Param("personid")String personid);
	
	/**
	 * 根据人员编码、客户编码生成对应客户对应品牌厂家业务员表
	 * @param personid
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Apr 26, 2014
	 */
	public int addOfSupplierBrandAndCustomerBypsnWithcst(@Param("personid")String personid,@Param("customerid")String customerid);
	
	/**
	 * 根据参数获取厂家对应客户列表数据
	 * @param map (personid,customerid)
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public List getSupplierCustomerListByMap(Map map);
	
	/**
	 * 根据客户编码、所属分公司获取厂家对应客户列表数据
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Feb 11, 2015
	 */
	public List getSupplierCustomerListByMapLeft(Map map);
	
	/**
	 * 修改厂家对应客户
	 * @param personnelCustomer
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int editSupplierCustomer(PersonnelCustomer personnelCustomer);
	
	/**
	 * 修改厂家对应品牌
	 * @param personnelBrand
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 8, 2014
	 */
	public int editSupplierBrand(PersonnelBrand personnelBrand);
	
	/**
	 * 获取厂家对应客户数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public List getSupplierCustomerList(PageMap pageMap);
	
	/**
	 * 获取厂家对应客户数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int getSupplierCustomerCount(PageMap pageMap);
	
	/**
	 * 获取厂家对应品牌数据
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public List getSupplierBrandList(PageMap pageMap);
	
	/**
	 * 获取厂家对应品牌数量
	 * @param pageMap
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int getSupplierBrandCount(PageMap pageMap);
	
	/**
	 * 根据人员编码获取所有厂家对应品牌数据
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public List getSupplierBrandListNoPage(@Param("personid")String personid);
	
	/**
	 * 根据人员编码获取所有厂家对应客户数据
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public List getSupplierCustomerListNoPage(@Param("personid")String personid);
	
	/**
	 * 判断是否已存在对应品牌客户厂家人员
	 * @param brandid
	 * @param customerid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public int checkExistBrandAndCusotmerSupplierPerson(@Param("brandid")String brandid,@Param("customerid")String customerid);
	
	/**
	 * 根据人员编码判断是否存在对应客户和品牌(厂家业务员)
	 * @param personid
	 * @return
	 * @author panxiaoxiao 
	 * @date Aug 24, 2013
	 */
	public int getSupplieruserBrandAndCustomerCount(@Param("personid")String personid);

	/**
	 * 根据人员编码获取对应客户和品牌数据(厂家业务员)
	 * @param personid
	 * @return
	 */
	public List<Map> getSupplieruserBrandAndCustomerList(@Param("personid")String personid);

	/**
	 * 根据人员编码获取对应客户和品牌数据(厂家业务员)
	 * @param map
	 * @return
	 */
	public List getSupplieruserBrandAndCustomerListMap(Map map);
	
	/**
	 * 根据人员编码和客户编码获取厂家对应客户信息
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public PersonnelCustomer getPersonSupplierCustomerInfo(Map map);
	
	/**
	 * 根据人员编码和客户编码获取厂家对应品牌信息
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 6, 2014
	 */
	public PersonnelBrand getPersonSupplierBrandInfo(Map map);
	
	/**
	 * 根据参数获取厂家对应品牌对应客户表数据
	 * @param map （personid,brandid,customerid）
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 7, 2014
	 */
	public List getSupplierBrandCustomerList(Map map);
	
	/**
	 * 根据参数获取品牌业务员对应客户品牌数据列表
	 * @param map （brandArr，customerid，personid）
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 7, 2014
	 */
	public List getSupplierBrandCustomerListByMap(Map map);
	
	/**
	 * 根据客户数组字符串获取人员列表数据(分配厂家业务员)
	 * @param customerids
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 8, 2014
	 */
	public List getSupplierPersonListByCustoemrids(String customerids);
	
	/**
	 * 根据客户编码、品牌编码获取厂家业务员人员编号
	 * @param customerid
	 * @param brandid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 7, 2014
	 */
	public String getSupplierUserIdByCustomerAndBrand(@Param("customerid")String customerid,@Param("brandid")String brandid);
	
	/**
	 * 根据品牌编码获取厂家对应品牌列表数据
	 * @param brandid
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 8, 2014
	 */
	public List getSupplierBrandListByBrandid(@Param("brandid")String brandid);
	
	/**
	 * 添加删除对应品牌或对应客户要清空业务员的数据
	 * @param list
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 28, 2015
	 */
	public int addNoPersonBrandAndCustomer(List<Map<String,String>> list);
	
	/**
	 * 根据删除对应品牌或对应客户的人员编码获取要清空业务员的数据
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 28, 2015
	 */
	public List<Map<String,String>> getNoPersonBrandAndCustomerList(@Param("delpersonid")String delpersonid);
	
	/**
	 * 根据删除对应品牌或对应客户的人员编码删除要清空业务员的数据
	 * @param delpersonid
	 * @return
	 * @author panxiaoxiao 
	 * @date Jan 28, 2015
	 */
	public int deleteNoPersonBrandAndCustomerByDelpersonid(@Param("delpersonid")String delpersonid);
	
	/**
	 * 根据删除对应品牌或对应客户的人员编码&业务属性删除要清空业务员的数据
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 3, 2015
	 */
	public int deleteNoPersonBrandAndCustomerByMap(Map map);
	
	/**
	 * 根据人员编码判断是否删除对应品牌或对应客户
	 * @param map
	 * @return
	 * @author panxiaoxiao 
	 * @date Jul 3, 2015
	 */
	public int getNoPersonBrandAndCustomerCount(Map map);

	/**
	 * 根据业务类型，人员编码获取删除对应品牌或对应客户数据
	 * @param map
	 * @return
	 */
	public List getNoPersonBrandAndCustomerListByEM(Map map);

    /**
     * 业务员之间是否有重复的品牌
     * @param sql
     * @return
     * @author panxiaoxiao
     * @date 2015-04-27
     */
    public List<Map> checkPersonsIsRepeatBrandids(@Param("sql")String sql);

	/**
	 * 根据人员档案更新客户档案中的销售业务员名称
	 * @param salesuserid
	 * @return
	 */
	public int updateCustomerSalesusername(@Param("salesuserid")String salesuserid);

	/**
	 * 全局更新昨天变动的单据数据品牌业务员
	 * @param yesdate
	 */
	public void doGlobalUpdateBillsBranduserYes(@Param("yesdate")String yesdate);

	public int deleteNoPersonBrandAndCustomerDelCustomer(Map map1);

	public int deleteNoPersonBrandAndCustomerDelBrand(Map map1);

	/**
	 * 根据人员编码对应的品牌及对应客户编码获取已存在业务员(品牌业务员)
	 * @param map
	 * @return
	 */
	public List<String> getPersonidByPSNCustomerAndBrand(Map map);

	/**
	 * 根据人员编码对应的品牌及对应客户编码获取已存在业务员(厂家业务员)
	 * @param map
	 * @return
	 */
	public List<String> getPersonidBySupplierPSNCustomerAndBrand(Map map);

	public int updateTotalBillsBranduser();
}


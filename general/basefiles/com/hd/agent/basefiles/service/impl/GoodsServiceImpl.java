/**
 * @(#)GoodsServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.*;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.IGoodsService;
import com.hd.agent.basefiles.service.ISalesService;
import com.hd.agent.common.dao.AttachFileMapper;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.util.*;
import com.hd.agent.storage.dao.StorageSummaryMapper;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysParam;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 
 * @author panxiaoxiao
 */
public class GoodsServiceImpl extends FilesLevelServiceImpl implements IGoodsService {

	private GoodsMapper goodsMapper;
	private StorageMapper storageMapper;
	private PersonnelMapper personnelMapper;
	private FinanceMapper financeMapper;
	private BuySupplierMapper buySupplierMapper;
	private AttachFileMapper attachFileMapper;

	private ISalesService salesService;

	public AttachFileMapper getAttachFileMapper() {
		return attachFileMapper;
	}

	public void setAttachFileMapper(AttachFileMapper attachFileMapper) {
		this.attachFileMapper = attachFileMapper;
	}

	public BuySupplierMapper getBuySupplierMapper() {
		return buySupplierMapper;
	}

	public void setBuySupplierMapper(BuySupplierMapper buySupplierMapper) {
		this.buySupplierMapper = buySupplierMapper;
	}

	public FinanceMapper getFinanceMapper() {
		return financeMapper;
	}

	public void setFinanceMapper(FinanceMapper financeMapper) {
		this.financeMapper = financeMapper;
	}

	public PersonnelMapper getPersonnelMapper() {
		return personnelMapper;
	}

	public void setPersonnelMapper(PersonnelMapper personnelMapper) {
		this.personnelMapper = personnelMapper;
	}

	public StorageMapper getStorageMapper() {
		return storageMapper;
	}

	public void setStorageMapper(StorageMapper storageMapper) {
		this.storageMapper = storageMapper;
	}

	public GoodsMapper getGoodsMapper() {
		return goodsMapper;
	}

	public void setGoodsMapper(GoodsMapper goodsMapper) {
		this.goodsMapper = goodsMapper;
	}

    public ISalesService getSalesService() {
        return salesService;
    }

    public void setSalesService(ISalesService salesService) {
        this.salesService = salesService;
    }

	private JsTaxTypeCodeMapper jsTaxTypeCodeMapper;

	public JsTaxTypeCodeMapper getJsTaxTypeCodeMapper() {
		return jsTaxTypeCodeMapper;
	}

	public void setJsTaxTypeCodeMapper(JsTaxTypeCodeMapper jsTaxTypeCodeMapper) {
		this.jsTaxTypeCodeMapper = jsTaxTypeCodeMapper;
	}
	/*------------------------------------计量单位----------------------------------------------------*/

	/**
	 * 获取计量单位列表
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public PageData getMeteringUnitList(PageMap pageMap) throws Exception {
		// 数据权限
		String sql = getDataAccessRule("t_base_goods_meteringunit", null);
		pageMap.setDataSql(sql);
		PageData pageData = new PageData(goodsMapper.getMeteringUnitCount(pageMap), goodsMapper.getMeteringUnitList(pageMap), pageMap);
		return pageData;
	}

	/**
	 * 新增计量单位
	 * 
	 * @param meteringUnit
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public boolean addMeteringUnit(MeteringUnit meteringUnit) throws Exception {
		int i = goodsMapper.addMeteringUnit(meteringUnit);
		return i > 0;
	}

	/**
	 * 判断是否id重复，true 重复 false 不重复
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public boolean isRepeatMUID(String id) throws Exception {
		String str = goodsMapper.isRepeatMUID(id);
		if (StringUtils.isNotEmpty(str)) {// 不为空，存在该id
			return true;
		}
		return false;
	}

	/**
	 * 判断是否name重复，true 重复 false 不重复
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public boolean isRepeatMUName(String name) throws Exception {
		String str = goodsMapper.isRepeatMUName(name);
		if (StringUtils.isNotEmpty(str)) {// 不为空，存在该name
			return true;
		}
		return false;
	}

	/**
	 * 显示计量单位信息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public MeteringUnit showMeteringUnitInfo(String id) throws Exception {
		MeteringUnit meteringUnitInfo = goodsMapper.showMeteringUnitInfo(id);
		return meteringUnitInfo;
	}

	/**
	 * 修改计量单位 ，修改前判断是否被引用
	 * 
	 * @param meteringUnit
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public boolean editMeteringUnit(MeteringUnit meteringUnit) throws Exception {
		// 保存修改前判断数据是否已经被加锁 可以修改,true可以操作。false不可以操作。
		boolean lockFlag = isLockEdit("t_base_goods_meteringunit", meteringUnit.getId());
		if (lockFlag) {
			// 获取修改前部门信息
			MeteringUnit beforeMeteringUnit = goodsMapper.showMeteringUnitInfo(meteringUnit.getOldId());
			// 判断是否可以进行修改操作，若可以修改，更新级联关系数据
			boolean flag = canBasefilesIsEdit("goodsMapper", beforeMeteringUnit, meteringUnit);
			if (flag) {
				int i = goodsMapper.editMeteringUnit(meteringUnit);
				return i > 0;
			}
		}
		return false;
	}

	/**
	 * 删除计量单位,判断是否可以删除
	 * 
	 * @param id
	 * @return
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public boolean deleteMeteringUnit(String id) throws Exception {
		MeteringUnit meteringUnitInfo = goodsMapper.showMeteringUnitInfo(id);
		if (null != meteringUnitInfo) {
			int i = goodsMapper.deleteMeteringUnit(id);
			return i > 0;
		}
		return false;
	}

	/**
	 * 启用计量单位
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public boolean enableMeteringUnit(String id, String openuserid) throws Exception {
		MeteringUnit meteringUnitInfo = showMeteringUnitInfo(id);
		if (meteringUnitInfo != null) {
			if ("2".equals(meteringUnitInfo.getState()) || "0".equals(meteringUnitInfo.getState())) {// 状态为保存或禁用状态，才不允许启用
				Map paramMap = new HashMap();
				paramMap.put("id", id);
				paramMap.put("openuserid", openuserid);
				int i = goodsMapper.enableMeteringUnit(paramMap);
				return i > 0;
			}
		}
		return false;
	}

	/**
	 * 禁用计量单位
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public boolean disableMeteringUnit(String id, String closeuserid) throws Exception {
		MeteringUnit meteringUnitInfo = showMeteringUnitInfo(id);
		if (meteringUnitInfo != null) {
			if ("1".equals(meteringUnitInfo.getState())) {// 状态为启用时，才允许禁用
				Map paramMap = new HashMap();
				paramMap.put("id", id);
				paramMap.put("closeuserid", closeuserid);
				int i = goodsMapper.disableMeteringUnit(paramMap);
				return i > 0;
			}
		}
		return false;
	}

	/**
	 * Excel导入添加记录单位信息， 判断导入的记录单位是否重复，导入成功后的状态为暂存3
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-3-20
	 */
	public Map addDRMeteringUnitInfo(List<MeteringUnit> list) throws Exception {
		String failStr = "", closeVal = "", repeatVal = "";
		int closeNum = 0, repeatNum = 0, failureNum = 0, successNum = 0;
		boolean flag = false;
		if (list.size() != 0) {
			for (MeteringUnit meteringUnit : list) {
				// 判断记录单位是否重复
				MeteringUnit meteringUnitInfo = goodsMapper.showMeteringUnitInfo(meteringUnit.getId());
				if (meteringUnitInfo == null) {// 不重复
					SysUser sysUser = getSysUser();
					meteringUnit.setAdduserid(sysUser.getUserid());
					meteringUnit.setAdddeptid(sysUser.getDepartmentid());
					if (StringUtils.isEmpty(meteringUnit.getState())) {
						meteringUnit.setState("1");
					}
					flag = goodsMapper.addMeteringUnit(meteringUnit) > 0;
					if (!flag) {
						if (StringUtils.isNotEmpty(failStr)) {
							failStr += "," + meteringUnit.getId();
						} else {
							failStr = meteringUnit.getId();
						}
						failureNum++;
					} else {
						successNum++;
					}
				} else {
					if ("0".equals(meteringUnitInfo.getState())) {// 禁用状态，不允许导入
						if (StringUtils.isEmpty(closeVal)) {
							closeVal = meteringUnitInfo.getId();
						} else {
							closeVal += "," + meteringUnitInfo.getId();
						}
						closeNum++;
					} else {
						if (StringUtils.isEmpty(repeatVal)) {
							repeatVal = meteringUnitInfo.getId();
						} else {
							repeatVal += "," + meteringUnitInfo.getId();
						}
						repeatNum++;
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("repeatNum", repeatNum);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		map.put("repeatVal", repeatVal);
		return map;
	}

	@Override
	public List getMUListForCombobox() throws Exception {
		return goodsMapper.getMUListForCombobox();
	}

	/*------------------------------------商品品牌----------------------------------------------------*/

	@Override
	public PageData getBrandListPage(PageMap pageMap) throws Exception {
		// 数据权限
		String sql = getDataAccessRule("t_base_goods_brand", null);
		pageMap.setDataSql(sql);
        List<Brand> list = goodsMapper.getBrandListPage(pageMap);
        for (Brand brandInfo : list) {
            if (StringUtils.isNotEmpty(brandInfo.getState())) { // 状态
                SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(brandInfo.getState(), "state");
                if (sysCode != null) {
                    brandInfo.setStateName(sysCode.getCodename());
                }
            }
            if (StringUtils.isNotEmpty(brandInfo.getDeptid())) { // 所属部门
                DepartMent dept = getDepartmentByDeptid(brandInfo.getDeptid());
                if (dept != null) {
                    brandInfo.setDeptName(dept.getName());
                }
            }
            if (StringUtils.isNotEmpty(brandInfo.getSupplierid())) { // 所属供应商
                BuySupplier buySupplier = buySupplierMapper.getBuySupplier(brandInfo.getSupplierid());
                if (buySupplier != null) {
                    brandInfo.setSupplierName(buySupplier.getName());
                }
            }
            if(StringUtils.isNotEmpty(brandInfo.getDefaulttaxtype())){//默认税种
                TaxType taxType = financeMapper.getTaxTypeInfo(brandInfo.getDefaulttaxtype());
                if(null != taxType){
                    brandInfo.setDefaulttaxtypename(taxType.getName());
                }
            }
        }
		PageData pageData = new PageData(goodsMapper.getBrandListCount(pageMap), list, pageMap);
		return pageData;
	}

	@Override
	public boolean addBrand(Brand brand) throws Exception {
		SysUser sysUser = getSysUser();
		brand.setAdddeptid(sysUser.getDepartmentid());
		brand.setAdddeptname(sysUser.getDepartmentname());
		brand.setAdduserid(sysUser.getUserid());
		brand.setAddusername(sysUser.getName());
		boolean flag = goodsMapper.addBrand(brand) > 0;
		return flag;
	}

	/**
	 * 商品品牌新增时，品牌业务员中的对应品牌、对应客户要有相应的变动
	 * 
	 * @param brand
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Dec 6, 2013
	 */
	private void addPersonBrandAndCustomer(Brand brand, Brand oldBrand) throws Exception {
		if (null != brand && null != oldBrand && !brand.getId().equals(oldBrand.getId())) {
			// 品牌业务员
			List<PersonnelBrand> brandList = personnelMapper.getPersonBrandListByBrandid(oldBrand.getId());
			if (brandList.size() != 0) {
				personnelMapper.deleteBrandAndCustomerByBrand(oldBrand.getId());
				for (PersonnelBrand personBrand : brandList) {
					personBrand.setBrandid(brand.getId());
					personnelMapper.editBrand(personBrand);
					personnelMapper.addPsnBrandAndCustomerFromTbrandAndTcustomer(personBrand.getPersonid());
				}
			}
			// 厂家业务员
			List<PersonnelBrand> supplierBrandList = personnelMapper.getSupplierBrandListByBrandid(oldBrand.getId());
			if (supplierBrandList.size() != 0) {
				Map map2 = new HashMap();
				map2.put("brandid", oldBrand.getId());
				personnelMapper.deleteSupplierBrandAndCustomerByMap(map2);
				for (PersonnelBrand supplierBrand : supplierBrandList) {
					supplierBrand.setBrandid(brand.getId());
					personnelMapper.editSupplierBrand(supplierBrand);
					Map map = new HashMap();
					map.put("personid", supplierBrand.getPersonid());
					personnelMapper.deleteSupplierBrandAndCustomerByMap(map);
					personnelMapper.addOfSupplierBrandAndCustomer(supplierBrand.getPersonid());
				}
			}
		}
	}

	@Override
	public Map addDRBrand(List<Brand> list) throws Exception {
		String failStr = "", closeVal = "", repeatVal = "";
		int closeNum = 0, repeatNum = 0, failureNum = 0, successNum = 0;
		boolean flag = false;
		if (list.size() != 0) {
			for (Brand brand : list) {
				SysUser sysUser = getSysUser();
				brand.setAdddeptid(sysUser.getDepartmentid());
				brand.setAdddeptname(sysUser.getDepartmentname());
				brand.setAdduserid(sysUser.getUserid());
				brand.setAddusername(sysUser.getName());
				// 状态若为1启用时，则需添加该方法：addPersonBrandAndCustomer(brand);
				brand.setState("2");
				if (null != brand.getId()) {
					if (!(goodsMapper.isRepeatBrandById(brand.getId()) > 0)) {// 不存在，或重复
						if (StringUtils.isNotEmpty(brand.getId())) {
							brand.setId(brand.getId());
						}
						flag = goodsMapper.addBrand(brand) > 0;
						if (!flag) {
							if (StringUtils.isNotEmpty(failStr)) {
								failStr += "," + brand.getId();
							} else {
								failStr = brand.getId();
							}
							failureNum++;
						} else {
							successNum++;
						}
					} else {
						Brand brand2 = goodsMapper.getBrandInfo(brand.getId());
						if (null != brand2) {
							if ("0".equals(brand2.getState())) {// 禁用状态，不允许导入
								if (StringUtils.isEmpty(closeVal)) {
									closeVal = brand2.getId();
								} else {
									closeVal += "," + brand2.getId();
								}
								closeNum++;
							} else {
								if (StringUtils.isEmpty(repeatVal)) {
									repeatVal = brand2.getId();
								} else {
									repeatVal += "," + brand2.getId();
								}
								repeatNum++;
							}
						}
					}
				} else {
					String id = super.getAutoCreateSysNumbderForeign(brand, "t_base_goods_brand");
					if (StringUtils.isNotEmpty(id)) {
						brand.setId(id);
					}
					flag = goodsMapper.addBrand(brand) > 0;
					if (!flag) {
						if (StringUtils.isNotEmpty(failStr)) {
							failStr += "," + id;
						} else {
							failStr = id;
						}
						failureNum++;
					} else {
						successNum++;
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("repeatNum", repeatNum);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		map.put("repeatVal", repeatVal);
		return map;
	}

	@Override
	public boolean deleteBrand(String id) throws Exception {
		boolean flag = goodsMapper.deleteBrand(id) > 0;
		if (flag) {
			personnelMapper.deleteBrandListByBrandid(id);
			personnelMapper.deleteBrandAndCustomerByBrand(id);
		}
		return flag;
	}

	@Override
	public boolean editBrand(Brand brand) throws Exception {
		Brand beforeBrand = goodsMapper.getBrandInfo(brand.getOldId());
		boolean flag = goodsMapper.editBrand(brand) > 0;
		// 商品品牌修改成功，修改人员档案对应品牌
		if (flag) {
			SysUser sysUser = getSysUser();

			addPersonBrandAndCustomer(brand, beforeBrand);

			// 若editSupplier为1时，商品所属供应商要相应的修改
			if ("1".equals(brand.getEditSupplier())) {
				List<GoodsInfo> gdslsit = goodsMapper.getGoodsListByBrand(brand.getId());
				for (GoodsInfo goodsInfo : gdslsit) {
					if (beforeBrand.getSupplierid().equals(goodsInfo.getDefaultsupplier())) {
						goodsInfo.setDefaultsupplier(brand.getSupplierid());
						goodsInfo.setOldId(goodsInfo.getId());
						if(null != sysUser){
							goodsInfo.setModifyuserid(sysUser.getUserid());
							goodsInfo.setModifyusername(sysUser.getName());
						}
						goodsMapper.editGoodsInfo(goodsInfo);
					}
				}
			} else if ("0".equals(brand.getEditSupplier())) {
				List<GoodsInfo> gdslsit = goodsMapper.getGoodsListByBrandWithSupplierid(brand.getId(), beforeBrand.getSupplierid());
				if (gdslsit.size() != 0) {
					for (GoodsInfo goodsInfo : gdslsit) {
						if (beforeBrand.getSupplierid().equals(goodsInfo.getDefaultsupplier())) {
							goodsInfo.setDefaultsupplier(brand.getSupplierid());
							goodsInfo.setOldId(goodsInfo.getId());
							if(null != sysUser){
								goodsInfo.setModifyuserid(sysUser.getUserid());
								goodsInfo.setModifyusername(sysUser.getName());
							}
							goodsMapper.editGoodsInfo(goodsInfo);
						}
					}
				}
			}
		}
		return flag;
	}

	@Override
	public boolean isRepeatBrandById(String id) throws Exception {
		return goodsMapper.isRepeatBrandById(id) > 0;
	}

	@Override
	public Brand getBrandInfo(String id) throws Exception {
		Brand brand = goodsMapper.getBrandInfo(id);
		if (null != brand) {
			BuySupplier buySupplier = getBaseBuySupplierMapper().getBuySupplier(brand.getSupplierid());
			if (null != buySupplier) {
				brand.setSupplierName(buySupplier.getName());
			}
		}
		return brand;
	}

	@Override
	public boolean isRepeatBrandName(String name) throws Exception {
		return goodsMapper.isRepeatBrandName(name) > 0;
	}

	@Override
	public boolean disableBrand(Map paramMap) throws Exception {
		// TODO Auto-generated method stub
		return goodsMapper.disableBrand(paramMap) > 0;
	}

	@Override
	public boolean enableBrand(Map paramMap) throws Exception {
		boolean flag = goodsMapper.enableBrand(paramMap) > 0;
		return flag;
	}

	@Override
	public List getBrandListWithParentByDeptid(String deptid) throws Exception {
		return goodsMapper.getBrandListWithParentByDeptid(deptid);
	}

	@Override
	public List getBrandListByDeptid(String deptid) throws Exception {
		return goodsMapper.getBrandListByDeptid(deptid);
	}

	@Override
	public List getBrandListBySupplierid(String supplierid) throws Exception {
		return goodsMapper.getBrandListBySupplierid(supplierid);
	}

	@Override
	public List getBrandListBySupplierids(String supplierids) throws Exception {
		List<Brand> list = new ArrayList<Brand>();
		if (StringUtils.isNotEmpty(supplierids)) {
			String[] supplieridArr = supplierids.split(",");
			list = goodsMapper.getBrandListBySupplierids(supplieridArr);
		}
		return list;
	}
	@Override
	public List getBrandListByMap(Map map) throws Exception{
		List<Brand> list=goodsMapper.getBrandListByMap(map);
		return list;
	}


	@Override
	public Map updateBrandInfoForJS(Brand brandInfo) throws Exception{
		Map resultMap=new HashMap();
		if(null == brandInfo){
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关品牌信息");
			return resultMap;
		}
		if(StringUtils.isEmpty(brandInfo.getId())){
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关品牌编号");
			return resultMap;
		}
		Brand oldBrandInfo=goodsMapper.getBrandInfo(brandInfo.getId());
		if(null==oldBrandInfo){
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关品牌信息");
			return resultMap;
		}
		SysUser sysUser=getSysUser();
		brandInfo.setModifyuserid(sysUser.getUserid());
		brandInfo.setModifyusername(sysUser.getName());
		boolean flag=goodsMapper.updateBrandInfoForJS(brandInfo)>0;
		resultMap.put("flag",flag);
		return resultMap;
	}

	/*------------------------------------商品分类----------------------------------------------------*/

	/**
	 * 获取商品分类列表
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-12
	 */
	public PageData getWaresClassListPage(PageMap pageMap) throws Exception {
		// 数据权限
		String sql = getDataAccessRule("t_base_goods_waresclass", null);
		pageMap.setDataSql(sql);

		PageData pageData = new PageData(goodsMapper.getWaresClassListCount(pageMap), goodsMapper.getWaresClassListPage(pageMap), pageMap);
		return pageData;
	}

	/**
	 * 获取树状商品分类列表
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-12
	 */
	public List<WaresClass> getWaresClassTreeList() throws Exception {
		List<WaresClass> WCList = goodsMapper.getWaresClassTreeList();
		return WCList;
	}

	/**
	 * 获取树状商品分类启用列表
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-12
	 */
	public List<WaresClass> getWaresClassTreeOpenList() throws Exception {
		List<WaresClass> WCList = goodsMapper.getWaresClassTreeOpenList();
		return WCList;
	}

	/**
	 * 编码是否重复
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	public boolean isRepeatWCID(String id) throws Exception {
		String str = goodsMapper.isRepeatWCID(id);
		if (StringUtils.isNotEmpty(str)) {// 不为空，已存在该编码
			return false;
		}
		return true;
	}

	/**
	 * 本级名称是否重复
	 * 
	 * @param thisname
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	public boolean isRepeatThisName(String thisname) throws Exception {
		if (goodsMapper.isRepeatThisName(thisname) > 0) {// 不为空，已存在该本级名称
			return false;
		}
		return true;
	}

	/**
	 * 新增商品分类
	 * 
	 * @param waresClass
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	public boolean addWaresClass(WaresClass waresClass) throws Exception {
		int i = goodsMapper.addWaresClass(waresClass);
		return i > 0;
	}

	/**
	 * 判断上级编号pid是否存在，若存在则为末及标志，反之进行级联替换
	 */
	public boolean isTreeLeaf() throws Exception {
		int count = 0;
		List<WaresClass> list = goodsMapper.getWaresClassByState();// 获取保存、暂存以外的商品分类列表数据
		if (list.size() != 0) {
			for (WaresClass waresClass : list) {
				String pid = goodsMapper.isExistPidJudgeLeaf(waresClass.getId());// 判断pid是否存在,若存在则为末及标志，否则，不是
				if (StringUtils.isNotEmpty(pid)) {
					waresClass.setLeaf("0");
				} else {
					waresClass.setLeaf("1");
				}
				waresClass.setOldId(waresClass.getId());
				WaresClass beforeWCInfo = goodsMapper.getWaresClassInfo(waresClass.getOldId());
				if (beforeWCInfo != null) {
					// 判断是否可以进行修改操作,如果可以修改，同时更新级联关系数据
					boolean flag = canBasefilesIsEdit("t_base_goods_waresclass", beforeWCInfo, waresClass);
					if (flag) {
						int i = goodsMapper.editWaresClass(waresClass);
						if (i > 0) {
							count += i;
						}
					}
				}
			}
			if (count == list.size()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取商品分类详情
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	public WaresClass getWaresClassInfo(String id) throws Exception {
		WaresClass waresClassInfo = goodsMapper.getWaresClassInfo(id);
		return waresClassInfo;
	}

	/**
	 * 修改商品分类，且更新级联关系
	 * 
	 * @param waresClass
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	public Map editWaresClass(WaresClass waresClass) throws Exception {
		String Mes = "";
		boolean flag = false;
		Map retMap = new HashMap();
		Map map2 = new HashMap();
		// 保存修改前判断数据是否已经被加锁 可以修改,true可以操作。false不可以操作。
		boolean lockFlag = isLockEdit("t_base_goods_waresclass", waresClass.getOldId());
		if (lockFlag) {
			// 获取修改前部门信息
			WaresClass beforeWCInfo = goodsMapper.getWaresClassInfo(waresClass.getOldId());
			// 判断是否可以进行修改操作，若可以修改，更新级联关系数据
			boolean editFlag = canBasefilesIsEdit("t_base_goods_waresclass", beforeWCInfo, waresClass);
			if (editFlag) {
				if (null != beforeWCInfo) {
					if (!beforeWCInfo.getThisid().equals(waresClass.getThisid()) || !beforeWCInfo.getThisname().equals(waresClass.getThisname())) {
						// 获取下级所有分类列表
						List<WaresClass> childList = goodsMapper.getWaresClassChildListByPid(beforeWCInfo.getId());
						if (childList.size() != 0) {
							for (WaresClass repeatWC : childList) {
								repeatWC.setOldId(repeatWC.getId());
								if (!beforeWCInfo.getThisid().equals(waresClass.getThisid())) {
									String newid = repeatWC.getId().replaceFirst(beforeWCInfo.getThisid(), waresClass.getThisid()).trim();
									repeatWC.setId(newid);
									String newpid = repeatWC.getPid().replaceFirst(beforeWCInfo.getThisid(), waresClass.getThisid()).trim();
									repeatWC.setPid(newpid);
								}
								if (!beforeWCInfo.getThisname().equals(waresClass.getThisname())) {
									// 若本级名称改变，下属所有的任务分类名称应做对应的替换
									String newname = repeatWC.getName().replaceFirst(beforeWCInfo.getThisname(), waresClass.getThisname());
									repeatWC.setName(newname);
								}
								Tree node = new Tree();
								node.setId(repeatWC.getId());
								node.setParentid(repeatWC.getPid());
								node.setText(repeatWC.getThisname());
								node.setState(repeatWC.getState());
								map2.put(repeatWC.getOldId(), node);
							}
							goodsMapper.editWaresClassBatch(childList);
						}
					}
				}
				flag = goodsMapper.editWaresClass(waresClass) > 0;
				if (flag) {
					Tree node = new Tree();
					node.setId(waresClass.getId());
					node.setParentid(waresClass.getPid());
					node.setText(waresClass.getThisname());
					node.setState(waresClass.getState());
					map2.put(waresClass.getOldId(), node);
				}
				retMap.put("nodes", map2);
			} else {
				Mes = "数据不允许修改;";
			}
		} else {
			Mes = "数据已被加锁,暂时不能修改!";
		}
		retMap.put("flag", flag);
		retMap.put("Mes", Mes);
		return retMap;
	}

	/**
	 * 删除商品分类,子节点删除完后再可删除父节点
	 * 
	 * @param id
	 *            编码作为上级编码获取下级所有的商品分类列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-15
	 */
	public boolean deleteWaresClass(String id) throws Exception {
		return goodsMapper.deleteWaresClass(id) > 0;
	}

	/**
	 * 启用商品分类，一个一个启用
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-15
	 */
	public boolean enableWaresClass(Map paramMap) throws Exception {
		return (goodsMapper.enableWaresClass(paramMap) > 0) && isTreeLeaf();
	}

	/**
	 * 禁用商品分类，禁用禁用全部
	 * 
	 * @param waresClass
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-15
	 */
	public boolean disableWaresClass(WaresClass waresClass) throws Exception {
		return goodsMapper.disableWaresClass(waresClass) > 0;
	}

	/**
	 * 判断子节点是否存在商品分类列表,true 存在，false 不存在
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-15
	 */
	public boolean isExistChildWCList(String id) throws Exception {
		List list = goodsMapper.getWaresClassChildListByPid(id);
		return list.size() > 0;
	}

	/**
	 * Excel导入添加记录单位信息， 判断导入的记录单位是否重复，导入成功后的状态为暂存3
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-3-20
	 */
	public Map addDRWaresClassInfo(List<WaresClass> list) throws Exception {
		boolean flag = false;
		String failStr = "", closeVal = "", repeatVal = "";
		int closeNum = 0, repeatNum = 0, failureNum = 0, successNum = 0;
		if (list.size() != 0) {
			for (WaresClass waresClass : list) {
				// 判断记录单位是否重复
				WaresClass waresClassInfo = goodsMapper.getWaresClassInfo(waresClass.getId());
				if (waresClassInfo == null) {// 不重复
					SysUser sysUser = getSysUser();
					waresClass.setAdduserid(sysUser.getUserid());
					waresClass.setAdddeptid(sysUser.getDepartmentid());
					if (StringUtils.isEmpty(waresClass.getState())) {
						waresClass.setState("2");
					}
					if (StringUtils.isEmpty(waresClass.getPid())) {
						waresClass.setPid("");
					}
					waresClass.setAdddeptidname(sysUser.getDepartmentname());
					waresClass.setAddusername(sysUser.getName());
					flag = goodsMapper.addWaresClass(waresClass) > 0;
					if (!flag) {
						if (StringUtils.isNotEmpty(failStr)) {
							failStr += "," + waresClass.getId();
						} else {
							failStr = waresClass.getId();
						}
						failureNum++;
					} else {
						successNum++;
					}
				} else {
					if ("0".equals(waresClassInfo.getState())) {// 禁用状态，不允许导入
						if (StringUtils.isEmpty(closeVal)) {
							closeVal = waresClassInfo.getId();
						} else {
							closeVal += "," + waresClassInfo.getId();
						}
						closeNum++;
					} else {
						if (StringUtils.isEmpty(repeatVal)) {
							repeatVal = waresClassInfo.getId();
						} else {
							repeatVal += "," + waresClassInfo.getId();
						}
						repeatNum++;
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("repeatNum", repeatNum);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		map.put("repeatVal", repeatVal);
		return map;
	}

	@Override
	public Map addDRGoodsSortExcel(List<WaresClass> list) throws Exception {
		Map returnMap = new HashMap();
		boolean flag = false;
		int successNum = 0, failureNum = 0, repeatNum = 0, closeNum = 0, errorNum = 0, levelNum = 0,reptthisNum = 0;
		String closeVal = "", repeatVal = "", failStr = "", errorVal = "", levelVal = "",reptthisnameVal = "";
		if (list.size() != 0) {
			String tn = "t_base_goods_waresclass";
			List levelList = showFilesLevelList(tn);
			if (levelList.size() != 0) {
				for (WaresClass waresClass : list) {
					if (StringUtils.isEmpty(waresClass.getId())) {
						continue;
					}
					// 根据档案级次信息根据编码获取本级编码
					Map map = getObjectThisidByidCaseFilesLevel(tn, waresClass.getId());
					if (null != map && !map.isEmpty()) {
						String id = waresClass.getId();
						String thisid = (null != map.get("thisid")) ? map.get("thisid").toString() : "";
						String pid = (null != map.get("pid")) ? map.get("pid").toString() : "";
						try {
							// 判断商品分类是否重复
							WaresClass waresClassInfo = goodsMapper.getWaresClassInfo(id);
							if (waresClassInfo == null) {// 不重复
								//判断本机名称是否重复
								if(goodsMapper.isRepeatThisName(waresClass.getThisname()) == 0){
									waresClass.setThisid(thisid);
									waresClass.setPid(pid);
									SysUser sysUser = getSysUser();
									waresClass.setAdduserid(sysUser.getUserid());
									waresClass.setAdddeptid(sysUser.getDepartmentid());
									waresClass.setAdddeptidname(sysUser.getDepartmentname());
									waresClass.setAddusername(sysUser.getName());
									if (StringUtils.isEmpty(waresClass.getPid())) {
										waresClass.setName(waresClass.getThisname());
									}
									waresClass.setState("1");
									flag = goodsMapper.addWaresClass(waresClass) > 0;
									if (!flag) {
										if (StringUtils.isNotEmpty(failStr)) {
											failStr += "," + waresClass.getId();
										} else {
											failStr = waresClass.getId();
										}
										failureNum++;
									} else {
										successNum++;
									}
								}else{
									if(StringUtils.isEmpty(reptthisnameVal)){
										reptthisnameVal = waresClass.getId();
									}else{
										reptthisnameVal += "," + waresClass.getId();
									}
									reptthisNum++;
								}
							} else {
								if ("0".equals(waresClassInfo.getState())) {// 禁用状态，不允许导入
									if (StringUtils.isEmpty(closeVal)) {
										closeVal = waresClassInfo.getId();
									} else {
										closeVal += "," + waresClassInfo.getId();
									}
									closeNum++;
								} else {
									if (StringUtils.isEmpty(repeatVal)) {
										repeatVal = waresClassInfo.getId();
									} else {
										repeatVal += "," + waresClassInfo.getId();
									}
									repeatNum++;
								}
							}
						} catch (Exception e) {
							if (StringUtils.isEmpty(errorVal)) {
								errorVal = id;
							} else {
								errorVal += "," + id;
							}
							errorNum++;
							continue;
						}
					} else {
						levelNum++;
						if (StringUtils.isNotEmpty(levelVal)) {
							levelVal += "," + waresClass.getId();
						} else {
							levelVal = waresClass.getId();
						}
					}
				}
				// 末级标志
				isTreeLeaf();
				// 名称
				List<WaresClass> nonameList = goodsMapper.getWCWithoutName();
				if (nonameList.size() != 0) {
					for (WaresClass nonameWC : nonameList) {
						WaresClass pWC = getWaresClassInfo(nonameWC.getPid());
						if (null != pWC) {
							nonameWC.setOldId(nonameWC.getId());
							String name = "";
							if (StringUtils.isEmpty(pWC.getName())) {
								name = nonameWC.getThisname();
							} else {
								name = pWC.getName() + "/" + nonameWC.getThisname();
							}
							if (StringUtils.isNotEmpty(pWC.getName())) {
								nonameWC.setName(name);
							} else {
								nonameWC.setName(pWC.getThisname());
							}
							goodsMapper.editWaresClass(nonameWC);
						}
					}
				}
			} else {
				returnMap.put("nolevel", true);
			}
		}
		returnMap.put("flag", flag);
		returnMap.put("success", successNum);
		returnMap.put("failure", failureNum);
		returnMap.put("failStr", failStr);
		returnMap.put("repeatNum", repeatNum);
		returnMap.put("repeatVal", repeatVal);
		returnMap.put("closeNum", closeNum);
		returnMap.put("closeVal", closeVal);
		returnMap.put("errorNum", errorNum);
		returnMap.put("errorVal", errorVal);
		returnMap.put("levelNum", levelNum);
		returnMap.put("levelVal", levelVal);
		returnMap.put("reptthisNum", reptthisNum);
		returnMap.put("reptthisnameVal", reptthisnameVal);
		return returnMap;
	}

	@Override
	public List getWaresClassListByPid(String id) {
		return goodsMapper.getWaresClassListByPid(id);
	}

	/*------------------------------------商品档案----------------------------------------------------*/

	@Override
	public PageData goodsInfoListPage(PageMap pageMap) throws Exception {
		// 单表取字段权限
		String cols = getAccessColumnList("t_base_goods_info_h", null);
		pageMap.setCols(cols);
		// 数据权限
		String sql = getDataAccessRule("t_base_goods_info", null);
		Map condition = pageMap.getCondition();
		// 是否打印
		boolean isPrintFlag = false;
		if (condition.containsKey("isPrintFlag") && "true".equals((String) condition.get("isPrintFlag"))) {
			isPrintFlag = true;
			condition.put("isflag", "true");
		}
		pageMap.setDataSql(sql);
		List<GoodsInfo> list = goodsMapper.getGoodsInfoListPage(pageMap);
		if (list.size() != 0) {
			for (GoodsInfo goodsInfo : list) {
				if (StringUtils.isNotEmpty(goodsInfo.getState())) { // 状态
					List<SysCode> codeList = getBaseSysCodeMapper().getSysCodeListForeign("state");
					if (codeList.size() != 0) {
						for (SysCode sysCode : codeList) {
							if (goodsInfo.getState().equals(sysCode.getCode())) {
								goodsInfo.setStateName(sysCode.getCodename());
							}
						}
					}
				}
				// 采购价
				if (null != goodsInfo.getCostaccountprice() && goodsInfo.getCostaccountprice().compareTo(BigDecimal.ZERO) != 0) {
					goodsInfo.setField01(goodsInfo.getCostaccountprice().toString());
				} else if (null != goodsInfo.getHighestbuyprice()) {
					goodsInfo.setField01(goodsInfo.getHighestbuyprice().toString());
				}
				if (StringUtils.isNotEmpty(goodsInfo.getMainunit())) { // 计量单位
					MeteringUnit meteringUnit = goodsMapper.showMeteringUnitInfo(goodsInfo.getMainunit());
					if (meteringUnit != null) {
						goodsInfo.setMainunitName(meteringUnit.getName());
					}
				}
				// 箱装量
				GoodsInfo_MteringUnitInfo muInfo = goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfo.getId());
				if (null != muInfo) {
					goodsInfo.setBoxnum(muInfo.getRate());
				}
				// 价格套
				List<GoodsInfo_PriceInfo> priceList = goodsMapper.getPriceListByGoodsid(goodsInfo.getId());
				if (priceList.size() != 0) {
					goodsInfo.setPriceList(priceList);
				}
				if (StringUtils.isNotEmpty(goodsInfo.getBrand())) { // 商品品牌
					Brand brand = goodsMapper.getBrandInfo(goodsInfo.getBrand());
					if (brand != null) {
						goodsInfo.setBrandName((brand.getName()));
					}
				}
				if (StringUtils.isNotEmpty(goodsInfo.getDefaultsort())) { // 默认分类（来源商品分类）
					WaresClass waresClass = goodsMapper.getWaresClassInfo(goodsInfo.getDefaultsort());
					if (waresClass != null) {
						goodsInfo.setDefaultsortName(waresClass.getThisname());
					}
				}
				if (StringUtils.isNotEmpty(goodsInfo.getStorageid())) {// 默认仓库
					StorageInfo storageInfo = storageMapper.showStorageInfo(goodsInfo.getStorageid());
					if (storageInfo != null) {
						goodsInfo.setStorageName(storageInfo.getName());
					}
				}
				if (StringUtils.isNotEmpty(goodsInfo.getStoragelocation())) {// 默认库位
					StorageLocation storageLocation = storageMapper.showStorageLocationInfo(goodsInfo.getStoragelocation());
					if (null != storageLocation) {
						goodsInfo.setStoragelocationName(storageLocation.getName());
					}
				}
				if (StringUtils.isNotEmpty(goodsInfo.getDefaultbuyer())) {// 默认采购员
					Personnel personnel = personnelMapper.getPersonnelInfo(goodsInfo.getDefaultbuyer());
					if (null != personnel) {
						goodsInfo.setDefaultbuyerName(personnel.getName());
					}
				}
				if (StringUtils.isNotEmpty(goodsInfo.getDefaultsaler())) {// 默认业务员
					Personnel personnel = personnelMapper.getPersonnelInfo(goodsInfo.getDefaultsaler());
					if (null != personnel) {
						goodsInfo.setDefaultsalerName(personnel.getName());
					}
				}
				if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())) {// 默认税种
					TaxType taxType = financeMapper.getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
					if (null != taxType) {
						goodsInfo.setDefaulttaxtypeName(taxType.getName());
					}
				}
				if (StringUtils.isNotEmpty(goodsInfo.getDefaultsupplier())) {// 默认供应商
					BuySupplier buySupplier = buySupplierMapper.getBuySupplier(goodsInfo.getDefaultsupplier());
					if (null != buySupplier) {
						goodsInfo.setDefaultsupplierName(buySupplier.getName());
					}
				}
				if (StringUtils.isNotEmpty(goodsInfo.getSecondsupplier())) {// 第二供应商
					BuySupplier buySupplier = buySupplierMapper.getBuySupplier(goodsInfo.getSecondsupplier());
					if (null != buySupplier) {
						goodsInfo.setSecondsuppliername(buySupplier.getName());
					}
				}
			}
		}
		int icount = 0;
		// 如果不是打印则计算分页条数
		if (!isPrintFlag) {
			icount = goodsMapper.getGoodsInfoListCount(pageMap);
		}
		PageData pageData = new PageData(icount, list, pageMap);
		return pageData;
	}

	@Override
	public boolean disableGoodsInfos(Map map) throws Exception {
		return goodsMapper.disableGoodsInfos(map) > 0;
	}

	@Override
	public boolean enableGoodsInfos(Map map) throws Exception {
		return goodsMapper.enableGoodsInfos(map) > 0;
	}

	@Override
	public GoodsInfo showGoodsInfo(String id) throws Exception {
		GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(id);
		if (null != goodsInfo) {
			GoodsStorageLocation goodsStorageLocation = goodsMapper.getSLByGoodsidAndIsdefault(id);
			if (null != goodsStorageLocation) {
				goodsInfo.setSlboxnum(goodsStorageLocation.getBoxnum());
			}
			MeteringUnit meteringUnit = showMeteringUnitInfo(goodsInfo.getMainunit());
			if (null != meteringUnit) {
				goodsInfo.setMainunitName(meteringUnit.getName());
			}
			GoodsInfo_MteringUnitInfo goodsMU = goodsMapper.getMUInfoByGoodsIdAndIsdefault(id);
			if (null != goodsMU) {
				goodsInfo.setAuxunitid(goodsMU.getMeteringunitid());
				MeteringUnit meteringUnit2 = showMeteringUnitInfo(goodsMU.getMeteringunitid());
				if (null != meteringUnit2) {
					goodsInfo.setAuxunitname(meteringUnit2.getName());
				}
				goodsInfo.setBoxnum(goodsMU.getRate());
			}
			// 采购箱价
			BigDecimal boxnum = null != goodsInfo.getBoxnum() ? goodsInfo.getBoxnum() : BigDecimal.ZERO;
			BigDecimal highestbuyprice = null != goodsInfo.getHighestbuyprice() ? goodsInfo.getHighestbuyprice() : BigDecimal.ZERO;
			goodsInfo.setBuyboxprice(highestbuyprice.multiply(boxnum));
            BigDecimal costDiffAmount = goodsMapper.getCostDiffAmountByGoodsid(null,id);
            if(null==costDiffAmount){
                costDiffAmount = BigDecimal.ZERO;
            }
            goodsInfo.setField12(costDiffAmount.toString());
		}
		return goodsInfo;
	}

    /**
     * 商品档案详情
     *
     * @param barcode
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2013-4-18
     */
    @Override
    public GoodsInfo getGoodsInfoByBarcode(String barcode) throws Exception {
        GoodsInfo goodsInfo = goodsMapper.getGoodsInfoByBarcodeLimitOne(barcode);
        if (null != goodsInfo) {
            GoodsStorageLocation goodsStorageLocation = goodsMapper.getSLByGoodsidAndIsdefault(goodsInfo.getId());
            if (null != goodsStorageLocation) {
                goodsInfo.setSlboxnum(goodsStorageLocation.getBoxnum());
            }
            MeteringUnit meteringUnit = showMeteringUnitInfo(goodsInfo.getMainunit());
            if (null != meteringUnit) {
                goodsInfo.setMainunitName(meteringUnit.getName());
            }
            GoodsInfo_MteringUnitInfo goodsMU = goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfo.getId());
            if (null != goodsMU) {
                goodsInfo.setAuxunitid(goodsMU.getMeteringunitid());
                MeteringUnit meteringUnit2 = showMeteringUnitInfo(goodsMU.getMeteringunitid());
                if (null != meteringUnit2) {
                    goodsInfo.setAuxunitname(meteringUnit2.getName());
                }
                goodsInfo.setBoxnum(goodsMU.getRate());
            }
            // 采购箱价
            BigDecimal boxnum = null != goodsInfo.getBoxnum() ? goodsInfo.getBoxnum() : BigDecimal.ZERO;
            BigDecimal highestbuyprice = null != goodsInfo.getHighestbuyprice() ? goodsInfo.getHighestbuyprice() : BigDecimal.ZERO;
            goodsInfo.setBuyboxprice(highestbuyprice.multiply(boxnum));
            BigDecimal costDiffAmount = goodsMapper.getCostDiffAmountByGoodsid(null,goodsInfo.getId());
            if(null==costDiffAmount){
                costDiffAmount = BigDecimal.ZERO;
            }
            goodsInfo.setField12(costDiffAmount.toString());
        }
        return goodsInfo;
    }

    @Override
	public boolean deleteGoodsInfos(String[] idsArr) throws Exception {
		String delimgids = "";
		for (String id : idsArr) {
			GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(id);
			if (StringUtils.isNotEmpty(goodsInfo.getImageids())) {
				if (delimgids == "") {
					delimgids = goodsInfo.getImageids();
				} else {
					delimgids += "," + goodsInfo.getImageids();
				}
			}
		}
		int g = goodsMapper.deleteGoodsInfos(idsArr);
		int p = 0, MU = 0, s = 0, WC = 0, SL = 0, count = 0;
		if (g > 0) {
			// 删除图片
			if (StringUtils.isNotEmpty(delimgids)) {
				String[] delimgArr = delimgids.split(",");
				for (String imgid : delimgArr) {
					attachFileMapper.deleteAttachFile(imgid);
				}
			}
			for (int i = 0; i < idsArr.length; i++) {
				int plist = goodsMapper.getPriceCountByGoodsId(idsArr[i]);
				if (plist != 0) {
					p = goodsMapper.deletePriceInfoByGoodsId(idsArr[i]);
				} else {
					p = 1;
				}
				int MUlist = goodsMapper.getMUCountByGoodsId(idsArr[i]);
				if (MUlist != 0) {
					MU = goodsMapper.deleteMereringUnitInfoByGoodsId(idsArr[i]);
				} else {
					MU = 1;
				}
				int slist = goodsMapper.getStorageCountByGoodsId(idsArr[i]);
				if (slist != 0) {
					s = goodsMapper.deleteStorageInfoByGoodsId(idsArr[i]);
				} else {
					s = 1;
				}
				int WClist = goodsMapper.getWCCountByGoodsId(idsArr[i]);
				if (WClist != 0) {
					WC = goodsMapper.deleteWaresClassInfoByGoodsId(idsArr[i]);
				} else {
					WC = 1;
				}
				int SLList = goodsMapper.getStorageLocationCountByGoodsId(idsArr[i]);
				if (SLList != 0) {
					SL = goodsMapper.deleteGoodsStorageLocationByGoodsId(idsArr[i]);
				} else {
					SL = 1;
				}
				if ((p > 0) && (MU > 0) && (s > 0) && (WC > 0) && (SL > 0)) {
					count++;
				}
			}
			if (count == idsArr.length) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean addGoodsInfo(GoodsInfo goodsInfo, List<GoodsInfo_PriceInfo> priceInfoList, List<GoodsInfo_MteringUnitInfo> meteringUnitInfoList, List<GoodsInfo_StorageInfo> storageInfoList, List<GoodsInfo_WaresClassInfo> waresClassList, List<GoodsStorageLocation> SLList) throws Exception {
		int p = -1, m = -1, s = -1, w = -1, sl = -1;
		if (goodsMapper.isRepeatGoodsInfoID(goodsInfo.getId()) == 0) {// 若为0，不存在存在
			Map listMap = new HashMap();
			if (null != priceInfoList && priceInfoList.size() != 0) {// 价格套
				List<GoodsInfo_PriceInfo> addPriceList = new ArrayList<GoodsInfo_PriceInfo>();
				for (GoodsInfo_PriceInfo goodsInfo_PriceInfo : priceInfoList) {
					if (null != goodsInfo_PriceInfo.getTaxprice() && goodsInfo_PriceInfo.getTaxprice().compareTo(new BigDecimal(0)) != 0) {
						goodsInfo_PriceInfo.setId(null);
						goodsInfo_PriceInfo.setGoodsid(goodsInfo.getId());
						if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())) {
							goodsInfo_PriceInfo.setTaxtype(goodsInfo.getDefaulttaxtype());
						}
						addPriceList.add(goodsInfo_PriceInfo);
					}
				}
				if (addPriceList.size() > 0) {
					listMap.put("priceInfoMap", addPriceList);
					p = goodsMapper.addPriceInfos(listMap);
				}
			} else {
				if (StringUtils.isNotEmpty(goodsInfo.getOldId())) {
					List<GoodsInfo_PriceInfo> addPriceList = goodsMapper.getPriceListByGoodsid(goodsInfo.getOldId());
					if (null != addPriceList && addPriceList.size() != 0) {
						for (GoodsInfo_PriceInfo gPriceInfo : addPriceList) {
							gPriceInfo.setGoodsid(goodsInfo.getId());
							gPriceInfo.setTaxtype(goodsInfo.getDefaulttaxtype());
							TaxType taxType = getBaseFinanceMapper().getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
							if (null != taxType) {
								BigDecimal rate = taxType.getRate().divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
								if (rate.compareTo(BigDecimal.ZERO) != 0) {
									gPriceInfo.setPrice(gPriceInfo.getTaxprice().divide(rate, 6, BigDecimal.ROUND_HALF_UP));
								} else {
									gPriceInfo.setPrice(gPriceInfo.getTaxprice());
								}
							}
						}
						listMap.put("priceInfoMap", addPriceList);
						p = goodsMapper.addPriceInfos(listMap);
					}
				}
			}
			GoodsInfo_MteringUnitInfo goodsMUInfo = null;
			if (null != meteringUnitInfoList && meteringUnitInfoList.size() > 0) {// 主计量单位
				for (GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo : meteringUnitInfoList) {
					goodsInfo_MteringUnitInfo.setId(null);
					goodsInfo_MteringUnitInfo.setGoodsid(goodsInfo.getId());
					if ("1".equals(goodsInfo_MteringUnitInfo.getIsdefault())) {
						goodsMUInfo = new GoodsInfo_MteringUnitInfo();
						if(null != goodsInfo_MteringUnitInfo.getRate()){
							if(goodsInfo_MteringUnitInfo.getRate().compareTo(BigDecimal.ZERO) != 0){
								goodsMUInfo.setRate(goodsInfo_MteringUnitInfo.getRate());
							}else{
								goodsMUInfo.setRate(new BigDecimal("9999"));
							}
						}else{
							goodsMUInfo.setRate(new BigDecimal("9999"));
						}
					}
				}
				listMap.put("meteringUnitListMap", meteringUnitInfoList);
				m = goodsMapper.addMeteringUnitInfos(listMap);
			} else {
				if (StringUtils.isNotEmpty(goodsInfo.getOldId())) {
					List<GoodsInfo_MteringUnitInfo> muList = goodsMapper.getMUListByGoodsId(goodsInfo.getOldId());
					if (null != muList && muList.size() != 0) {
						for (GoodsInfo_MteringUnitInfo muInfo : muList) {
							muInfo.setGoodsid(goodsInfo.getId());
							if ("1".equals(muInfo.getIsdefault())) {
								goodsMUInfo = new GoodsInfo_MteringUnitInfo();
								if(null != muInfo.getRate()){
									if(muInfo.getRate().compareTo(BigDecimal.ZERO) != 0){
										goodsMUInfo.setRate(muInfo.getRate());
									}else{
										goodsMUInfo.setRate(new BigDecimal("9999"));
									}
								}else{
									goodsMUInfo.setRate(new BigDecimal("9999"));
								}
								goodsMUInfo.setRate(muInfo.getRate());
							}
						}
						listMap.put("meteringUnitListMap", muList);
						m = goodsMapper.addMeteringUnitInfos(listMap);
					}
				}
			}
			if (null != storageInfoList && storageInfoList.size() > 0) {// 对应仓库
				for (GoodsInfo_StorageInfo goodsInfo_StorageInfo : storageInfoList) {
					goodsInfo_StorageInfo.setId(null);
					goodsInfo_StorageInfo.setGoodsid(goodsInfo.getId());
				}
				listMap.put("storageInfoListMap", storageInfoList);
				s = goodsMapper.addStorageInfos(listMap);
			} else {
				if (StringUtils.isNotEmpty(goodsInfo.getOldId())) {
					List<GoodsInfo_StorageInfo> sList = goodsMapper.getStorageListByGoodsid(goodsInfo.getOldId());
					if (null != sList && sList.size() != 0) {
						for (GoodsInfo_StorageInfo sInfo : sList) {
							sInfo.setGoodsid(goodsInfo.getId());
						}
						listMap.put("storageInfoListMap", sList);
						s = goodsMapper.addStorageInfos(listMap);
					}
				}
			}
			if (null != waresClassList && waresClassList.size() > 0) {// 对应分类
				for (GoodsInfo_WaresClassInfo goodsInfo_WaresClassInfo : waresClassList) {
					goodsInfo_WaresClassInfo.setId(null);
					goodsInfo_WaresClassInfo.setGoodsid(goodsInfo.getId());
				}
				listMap.put("waresClassInfoListMap", waresClassList);
				w = goodsMapper.addWaresClassInfos(listMap);
			} else {
				if (StringUtils.isNotEmpty(goodsInfo.getOldId())) {
					List<GoodsInfo_WaresClassInfo> wcList = goodsMapper.getWCListByGoodsid(goodsInfo.getOldId());
					if (null != wcList && wcList.size() != 0) {
						for (GoodsInfo_WaresClassInfo wcInfo : wcList) {
							wcInfo.setGoodsid(goodsInfo.getId());
						}
						listMap.put("waresClassInfoListMap", wcList);
						w = goodsMapper.addWaresClassInfos(listMap);
					}
				}
			}
			if (null != SLList && SLList.size() > 0) {// 对应库位
				for (GoodsStorageLocation SL : SLList) {
					SL.setId(null);
					SL.setGoodsid(goodsInfo.getId());
				}
				listMap.put("SLListMap", SLList);
				sl = goodsMapper.addGoodsStorageLocation(listMap);
			} else {
				if (StringUtils.isNotEmpty(goodsInfo.getOldId())) {
					List<GoodsStorageLocation> slList = goodsMapper.getStorageLocationListByGoodsid(goodsInfo.getOldId());
					if (null != slList && slList.size() != 0) {
						for (GoodsStorageLocation slInfo : slList) {
							slInfo.setGoodsid(goodsInfo.getId());
						}
						listMap.put("SLListMap", slList);
						sl = goodsMapper.addGoodsStorageLocation(listMap);
					}
				}
			}
			if (p > 0 && m > 0 && s > 0 && w > 0 && sl > 0 || p == -1 || m == -1 || s == -1 || w == -1 || sl == -1) {
				// 最高采购价 == 最新采购价 == 最新库存价
				if (null != goodsInfo.getHighestbuyprice()) {
					goodsInfo.setNewstorageprice(goodsInfo.getHighestbuyprice());
					goodsInfo.setNewbuyprice(goodsInfo.getHighestbuyprice());
				}
				// 拼音
				if (StringUtils.isEmpty(goodsInfo.getPinyin()) && StringUtils.isNotEmpty(goodsInfo.getName())) {
					goodsInfo.setPinyin(CommonUtils.getPinYingJCLen(goodsInfo.getName()));
				}
				// 长宽高计算单体积
				if (null == goodsInfo.getTotalvolume()) {
					if (null != goodsInfo.getGlength() && null != goodsInfo.getGwidth() && null != goodsInfo.getGhight()) {
						BigDecimal totalvolume = goodsInfo.getGlength().multiply(goodsInfo.getGwidth()).multiply(goodsInfo.getGhight());
						goodsInfo.setTotalvolume(totalvolume);
					}
				}
				// 输入箱重、箱体积后，自动计算毛重、单体积
				if (null != goodsMUInfo && null != goodsMUInfo.getRate() && goodsMUInfo.getRate().compareTo(BigDecimal.ZERO) != 0) {
					if (null != goodsInfo.getTotalweight()) {
						BigDecimal grossweight = goodsInfo.getTotalweight().divide(goodsMUInfo.getRate(), 6, BigDecimal.ROUND_HALF_UP);
						goodsInfo.setGrossweight(grossweight);
					} else {
						if (null != goodsInfo.getGrossweight()) {
							goodsInfo.setTotalweight(goodsInfo.getGrossweight().multiply(goodsMUInfo.getRate()));
						}
					}
					if (null != goodsInfo.getTotalvolume()) {
						BigDecimal singlevolume = goodsInfo.getTotalvolume().divide(goodsMUInfo.getRate(), 6, BigDecimal.ROUND_HALF_UP);
						goodsInfo.setSinglevolume(singlevolume);
					} else {
						if (null != goodsInfo.getSinglevolume()) {
							BigDecimal totalvolume = goodsInfo.getSinglevolume().multiply(goodsMUInfo.getRate());
							goodsInfo.setTotalvolume(totalvolume);
						}
					}
				}
				// 根据品牌档案获取所属部门
				if (StringUtils.isEmpty(goodsInfo.getDeptid())) {
					Brand brand = getBaseGoodsMapper().getBrandInfo(goodsInfo.getBrand());
					if (null != brand) {
						goodsInfo.setDeptid(brand.getDeptid());
					}
				}
				int g = goodsMapper.addGoodsInfo(goodsInfo);// 商品基本信息
				return g > 0;
			}
		}
		return false;
	}

	@Override
	public Map editGoodsInfo(GoodsInfo goodsInfo, List<GoodsInfo_PriceInfo> priceInfoList, List<GoodsInfo_MteringUnitInfo> meteringUnitInfoList, List<GoodsInfo_StorageInfo> storageInfoList, List<GoodsInfo_WaresClassInfo> waresClassList, List<GoodsStorageLocation> SLList) throws Exception {
		boolean retFlag = false, unEditFlag = true, lockFlag = true;
		if (goodsInfo != null) {
			int g = 0, count = 0, s = 0, ap = 0, aMU = 0, aS = 0, aWC = 0, aSL = 0, allp = 0, allMU = 0, allS = 0, allWC = 0, allSL = 0;
			boolean delMUBool = true, delSBool = true, delWCbool = true, delSLbool = true, editPFlag = false, editMUFlag = false, editSFlag = false, editWCFlag = false, editSLFlag = false;
			// 保存修改前判断数据是否已经被加锁 可以修改
			if (isLockEdit("t_base_goods_info", goodsInfo.getOldId())) {
				// 是否允许修改
				GoodsInfo beforeGoodsInfo = goodsMapper.getGoodsInfo(goodsInfo.getOldId());
				// 判断是否可以进行修改操作，若可以修改，更新级联关系数据
				if (canBasefilesIsEdit("t_base_goods_info", beforeGoodsInfo, goodsInfo)) {
					if (StringUtils.isEmpty(goodsInfo.getPinyin()) && StringUtils.isNotEmpty(goodsInfo.getName())) {
						goodsInfo.setPinyin(CommonUtils.getPinYingJCLen(goodsInfo.getName()));
					}
					// 最高采购价 == 最新采购价
					if (null != goodsInfo.getHighestbuyprice() && null != beforeGoodsInfo.getHighestbuyprice() && goodsInfo.getHighestbuyprice().compareTo(beforeGoodsInfo.getHighestbuyprice()) != 0) {
						goodsInfo.setNewbuyprice(goodsInfo.getHighestbuyprice());
					}
					if (StringUtils.isEmpty(goodsInfo.getEdittype())) {
						// 主计量单位
						GoodsInfo_MteringUnitInfo goodsMUInfo = null;
						if (null != meteringUnitInfoList && meteringUnitInfoList.size() != 0) {
							for (GoodsInfo_MteringUnitInfo MUInfo : meteringUnitInfoList) {
								if ("1".equals(MUInfo.getIsdefault())) {
									goodsMUInfo = new GoodsInfo_MteringUnitInfo();
									if(null != MUInfo.getRate()){
										if(MUInfo.getRate().compareTo(BigDecimal.ZERO) != 0){
											goodsMUInfo.setRate(MUInfo.getRate());
										}else{
											goodsMUInfo.setRate(new BigDecimal("9999"));
										}
									}else{
										goodsMUInfo.setRate(new BigDecimal("9999"));
									}
								}
							}
						}
						// 长宽高计算单体积
						if (null == goodsInfo.getTotalvolume()) {
							if (null != goodsInfo.getGlength() && null != goodsInfo.getGwidth() && null != goodsInfo.getGhight()) {
								BigDecimal totalvolume = goodsInfo.getGlength().multiply(goodsInfo.getGwidth()).multiply(goodsInfo.getGhight());
								goodsInfo.setTotalvolume(totalvolume);
							}
						}
						// 输入箱重、箱体积后，自动计算毛重、单体积
						if (null != goodsMUInfo && null != goodsMUInfo.getRate() && goodsMUInfo.getRate().compareTo(BigDecimal.ZERO) != 0) {
							if (null != goodsInfo.getTotalweight()) {
								BigDecimal grossweight = goodsInfo.getTotalweight().divide(goodsMUInfo.getRate(), 6, BigDecimal.ROUND_HALF_UP);
								goodsInfo.setGrossweight(grossweight);
							} else {
								if (null != goodsInfo.getGrossweight()) {
									goodsInfo.setTotalweight(goodsInfo.getGrossweight().multiply(goodsMUInfo.getRate()));
								}
							}
							if (null != goodsInfo.getTotalvolume()) {
								BigDecimal singlevolume = goodsInfo.getTotalvolume().divide(goodsMUInfo.getRate(), 6, BigDecimal.ROUND_HALF_UP);
								goodsInfo.setSinglevolume(singlevolume);
							} else {
								if (null != goodsInfo.getSinglevolume()) {
									BigDecimal totalvolume = goodsInfo.getSinglevolume().multiply(goodsMUInfo.getRate());
									goodsInfo.setTotalvolume(totalvolume);
								}
							}
						}
						g = goodsMapper.editGoodsInfo(goodsInfo);
					} else if (goodsInfo.getEdittype().equals("more")) {
						g = goodsMapper.editMoreGoodsInfo(goodsInfo);
					}
					if (g > 0) {
						Map listMap = new HashMap();
						// 价格套
						if (null != priceInfoList && priceInfoList.size() != 0) {
							List<GoodsInfo_PriceInfo> addPriceList = new ArrayList<GoodsInfo_PriceInfo>();
							for (GoodsInfo_PriceInfo priceInfo : priceInfoList) {
								priceInfo.setGoodsid(goodsInfo.getId());
								GoodsInfo_PriceInfo price = goodsMapper.getPriceDataByGoodsidAndCode(goodsInfo.getOldId(), priceInfo.getCode());
								if (null != price) {
									count++;
									if (null != priceInfo.getTaxprice()) {
										if (goodsMapper.editPriceInfo(priceInfo) > 0) {
											s++;
										}
									} else {
										if (goodsMapper.deletePriceDataByGoodsidAndCode(goodsInfo.getOldId(), priceInfo.getCode()) > 0) {
											s++;
										}
									}
								} else {
									if (null != priceInfo.getTaxprice()) {
										addPriceList.add(priceInfo);
									}
								}
							}
							if (addPriceList.size() != 0) {
								listMap.put("priceInfoMap", addPriceList);
								ap = goodsMapper.addPriceInfos(listMap);
							} else {
								ap = 1;
							}
							// 价格套修改结果
							editPFlag = (count == s) && (ap > 0);
						} else {
							if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())) {
								if (null != beforeGoodsInfo) {
									if (!goodsInfo.getDefaulttaxtype().equals(beforeGoodsInfo.getDefaulttaxtype())) {
										List<GoodsInfo_PriceInfo> priceList = goodsMapper.getPriceListByGoodsid(goodsInfo.getOldId());
										if (priceList.size() != 0) {
											for (GoodsInfo_PriceInfo priceInfo2 : priceList) {
												count++;
												priceInfo2.setGoodsid(goodsInfo.getId());
												TaxType taxTypeInfo = financeMapper.getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
												if (null != taxTypeInfo) {
													priceInfo2.setTaxtypeName(taxTypeInfo.getName());
													priceInfo2.setTaxtypeRate(taxTypeInfo.getRate());
													BigDecimal rate = priceInfo2.getTaxtypeRate().divide(new BigDecimal(100)).add(new BigDecimal(1));
													BigDecimal price = priceInfo2.getTaxprice().divide(rate, 8, BigDecimal.ROUND_HALF_UP);
													priceInfo2.setPrice(price);
												}
												if (goodsMapper.editPriceInfo(priceInfo2) > 0) {
													s++;
												}
											}
											editPFlag = (count == s);
										}
									} else {
										editPFlag = true;
									}
								}
							} else {
								editPFlag = true;
							}
						}
						// 主计量单位
						if (null != meteringUnitInfoList && meteringUnitInfoList.size() != 0) {
							List<GoodsInfo_MteringUnitInfo> addMUList = new ArrayList<GoodsInfo_MteringUnitInfo>();
							for (GoodsInfo_MteringUnitInfo MUInfo : meteringUnitInfoList) {
								// 判断主计量单位是否存在，i > 0,存在,修改，不存在，新增
								int i = 0;
								if (null != MUInfo.getId()) {
									i = goodsMapper.isExistMeteringUnitInfo(MUInfo.getId());
								}
								MUInfo.setGoodsid(goodsInfo.getId());
								if (i > 0) {
									count += 1;
									int ep = goodsMapper.editMeteringUnitInfo(MUInfo);
									if (ep > 0) {
										s += 1;
									}
								} else {
									addMUList.add(MUInfo);
								}
							}
							if (addMUList.size() != 0) {
								listMap.put("meteringUnitListMap", addMUList);
								aMU = goodsMapper.addMeteringUnitInfos(listMap);
							} else {
								aMU = 1;
							}
							if (StringUtils.isNotEmpty(goodsInfo.getDelMUIds())) {
								delMUBool = deleteMeteringUnitInfos(goodsInfo.getDelMUIds());
							}
							// 主计量单位修改结果
							editMUFlag = (count == s) && (aMU > 0) && delMUBool;
						} else {
							if (StringUtils.isNotEmpty(goodsInfo.getDelMUIds())) {
								allMU = goodsMapper.deleteMereringUnitInfoByGoodsId(goodsInfo.getOldId());
								editMUFlag = allMU > 0;
							} else {
								editMUFlag = true;
							}
						}
						// 对应仓库
						if (null != storageInfoList && storageInfoList.size() != 0) {
							List<GoodsInfo_StorageInfo> addSList = new ArrayList<GoodsInfo_StorageInfo>();
							for (GoodsInfo_StorageInfo SInfo : storageInfoList) {
								// 判断对应仓库是否存在，i > 0,存在,修改，不存在，新增
								int i = 0;
								if (null != SInfo.getId()) {
									i = goodsMapper.isExistStorageInfo(SInfo.getId());
								}
								SInfo.setGoodsid(goodsInfo.getId());
								if (i > 0) {
									count += 1;
									int ep = goodsMapper.editStorageInfo(SInfo);
									if (ep > 0) {
										s += 1;
									}
								} else {
									addSList.add(SInfo);
								}
							}
							if (addSList.size() != 0) {
								listMap.put("storageInfoListMap", addSList);
								aS = goodsMapper.addStorageInfos(listMap);
							} else {
								aS = 1;
							}
							if (StringUtils.isNotEmpty(goodsInfo.getDelStorageIds())) {
								delSBool = deleteStorageInfos(goodsInfo.getDelStorageIds());
							}

							// 对应仓库修改结果
							editSFlag = (count == s) && (aS > 0) && delSBool;
						} else {
							if (StringUtils.isNotEmpty(goodsInfo.getDelStorageIds())) {
								allS = goodsMapper.deleteStorageInfoByGoodsId(goodsInfo.getOldId());
								editSFlag = allS > 0;
							} else {
								editSFlag = true;
							}
						}
						// 对应分类
						if (null != waresClassList && waresClassList.size() != 0) {
							List<GoodsInfo_WaresClassInfo> addWCList = new ArrayList<GoodsInfo_WaresClassInfo>();
							for (GoodsInfo_WaresClassInfo WCInfo : waresClassList) {
								// 判断价格套是否存在，i > 0,存在,修改，不存在，新增
								int i = 0;
								if (null != WCInfo.getId()) {
									i = goodsMapper.isExistWaresClassInfo(WCInfo.getId());
								}
								WCInfo.setGoodsid(goodsInfo.getId());
								if (i > 0) {
									count += 1;
									int ep = goodsMapper.editWaresClassInfo(WCInfo);
									if (ep > 0) {
										s += 1;
									}
								} else {
									addWCList.add(WCInfo);
								}
							}
							if (addWCList.size() != 0) {
								listMap.put("waresClassInfoListMap", addWCList);
								aWC = goodsMapper.addWaresClassInfos(listMap);
							} else {
								aWC = 1;
							}
							if (StringUtils.isNotEmpty(goodsInfo.getDelWCIds())) {
								delWCbool = deleteWaresClassInfos(goodsInfo.getDelWCIds());
							}
							// 对应分类修改结果
							editWCFlag = (count == s) && (aWC > 0) && delWCbool;
						} else {
							if (StringUtils.isNotEmpty(goodsInfo.getDelWCIds())) {
								allWC = goodsMapper.deleteWaresClassInfoByGoodsId(goodsInfo.getOldId());
								editWCFlag = allWC > 0;
							} else {
								editWCFlag = true;
							}
						}
						// 对应库位
						if (null != SLList && SLList.size() != 0) {
							List<GoodsStorageLocation> addSLList = new ArrayList();
							for (GoodsStorageLocation SLInfo : SLList) {
								// 判断库位是否存在，i > 0,存在,修改，不存在，新增
								int i = 0;
								if (null != SLInfo.getId()) {
									i = goodsMapper.isExistGoodsStorageLocation(SLInfo.getId());
								}
								SLInfo.setGoodsid(goodsInfo.getId());
								if (i > 0) {
									count += 1;
									int esl = goodsMapper.editGoodsStorageLocation(SLInfo);
									if (esl > 0) {
										s += 1;
									}
								} else {
									addSLList.add(SLInfo);
								}
							}
							if (addSLList.size() != 0) {
								listMap.put("SLListMap", addSLList);
								aSL = goodsMapper.addGoodsStorageLocation(listMap);
							} else {
								aSL = 1;
							}
							if (StringUtils.isNotEmpty(goodsInfo.getDelSLIds())) {
								delSLbool = deleteSLInfos(goodsInfo.getDelSLIds());
							}
							// 对应库位修改结果
							editSLFlag = (count == s) && (aSL > 0) && delSLbool;
						} else {
							if (StringUtils.isNotEmpty(goodsInfo.getDelSLIds())) {
								allSL = goodsMapper.deleteStorageInfoByGoodsId(goodsInfo.getOldId());
								editSLFlag = allSL > 0;
							} else {
								editSLFlag = true;
							}
						}
						retFlag = editPFlag && editMUFlag && editSFlag && editWCFlag && editSLFlag;
					}
				} else {
					unEditFlag = false;
				}
			} else {
				lockFlag = false;
			}
		}
		Map map = new HashMap();
		map.put("retFlag", retFlag);
		map.put("unEditFlag", unEditFlag);
		map.put("lockFlag", lockFlag);
		return map;
	}

	@Override
	public Map addDRGoodsInfo(GoodsInfo goodsInfo) throws Exception {
		boolean flag = false;
		String failStr = "", closeVal = "", repeatVal = "", errorVal = "";
		int closeNum = 0, repeatNum = 0, failureNum = 0, successNum = 0, errorNum = 0;
		try {
			if (goodsMapper.isRepeatGoodsInfoID(goodsInfo.getId()) > 0) {// 若i>0，已存在
				GoodsInfo goodsInfo2 = goodsMapper.getGoodsInfo(goodsInfo.getId());
				if (null != goodsInfo2) {
					if ("0".equals(goodsInfo2.getState())) {// 禁用状态，不允许导入
						if (StringUtils.isEmpty(closeVal)) {
							closeVal = goodsInfo2.getId();
						} else {
							closeVal += "," + goodsInfo2.getId();
						}
						closeNum++;
					} else {
						if (StringUtils.isEmpty(repeatVal)) {
							repeatVal = goodsInfo2.getId();
						} else {
							repeatVal += "," + goodsInfo2.getId();
						}
						repeatNum++;
					}
				}
			} else {
				Map map = new HashMap();
				List<GoodsInfo_WaresClassInfo> gwcList = new ArrayList<GoodsInfo_WaresClassInfo>();
				List<GoodsInfo_StorageInfo> gsList = new ArrayList<GoodsInfo_StorageInfo>();
				List<GoodsStorageLocation> gslList = new ArrayList<GoodsStorageLocation>();
				SysUser sysUser = getSysUser();
				goodsInfo.setAdddeptid(sysUser.getDepartmentid());
				goodsInfo.setAdddeptname(sysUser.getDepartmentname());
				goodsInfo.setAdduserid(sysUser.getUserid());
				goodsInfo.setAddusername(sysUser.getName());
				// 最高采购价 == 最新采购价 == 最新库存价
				if (null != goodsInfo.getHighestbuyprice()) {
					goodsInfo.setNewstorageprice(goodsInfo.getHighestbuyprice());
					goodsInfo.setNewbuyprice(goodsInfo.getHighestbuyprice());
				}
				if (StringUtils.isEmpty(goodsInfo.getState())) {
					goodsInfo.setState("2");
				}
				if (StringUtils.isEmpty(goodsInfo.getPinyin()) && StringUtils.isNotEmpty(goodsInfo.getName())) {
					goodsInfo.setPinyin(CommonUtils.getPinYingJCLen(goodsInfo.getName()));
				}
				if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())) {
					SysParam sysParamTaxType = getBaseSysParamMapper().getSysParam("DEFAULTAXTYPE");
					if (null != sysParamTaxType) {
						goodsInfo.setDefaulttaxtype(sysParamTaxType.getPvalue());
						goodsInfo.setDefaulttaxtypeName(sysParamTaxType.getPvdescription());
					}
				}
				if (StringUtils.isEmpty(goodsInfo.getBarcode())) {
					goodsInfo.setBarcode(goodsInfo.getId());
				}
				// 箱重
				if (null == goodsInfo.getTotalvolume() || goodsInfo.getTotalvolume().compareTo(new BigDecimal(0)) == 0) {
					if (null != goodsInfo.getGlength() && null != goodsInfo.getGhight() && null != goodsInfo.getGwidth() && goodsInfo.getGlength().compareTo(BigDecimal.ZERO) != 0 && goodsInfo.getGhight().compareTo(BigDecimal.ZERO) != 0 && goodsInfo.getGwidth().compareTo(BigDecimal.ZERO) != 0) {
						BigDecimal totalvolume = goodsInfo.getGlength().multiply(goodsInfo.getGhight()).multiply(goodsInfo.getGwidth());
						goodsInfo.setTotalvolume(totalvolume);
					}
				}
				// 商品品牌关联所属部门
				if (StringUtils.isNotEmpty(goodsInfo.getBrand())) {
					Brand brand = getBrandInfo(goodsInfo.getBrand());
					if (null != brand) {
						goodsInfo.setDeptid(brand.getDeptid());
					}
				}
				flag = goodsMapper.addGoodsInfo(goodsInfo) > 0;
				if (!flag) {
					if (StringUtils.isNotEmpty(failStr)) {
						failStr += "," + goodsInfo.getId();
					} else {
						failStr = goodsInfo.getId();
					}
					failureNum++;
				} else {
					successNum++;
					// 对应分类
					if (StringUtils.isNotEmpty(goodsInfo.getDefaultsort())) {
						GoodsInfo_WaresClassInfo gwc = new GoodsInfo_WaresClassInfo();
						gwc.setGoodsid(goodsInfo.getId());
						gwc.setIsdefault("1");
						gwc.setWaresclass(goodsInfo.getDefaultsort());
						gwcList.add(gwc);
					}
					if (gwcList.size() != 0) {
						map.put("waresClassInfoListMap", gwcList);
						goodsMapper.addWaresClassInfos(map);
					}
					// 对应仓库
					if (StringUtils.isNotEmpty(goodsInfo.getStorageid())) {
						GoodsInfo_StorageInfo gsInfo = new GoodsInfo_StorageInfo();
						gsInfo.setGoodsid(goodsInfo.getId());
						gsInfo.setStorageid(goodsInfo.getStorageid());
						gsInfo.setIsdefault("1");
						gsInfo.setChecktype("1");
						gsInfo.setCheckunit("1");
						gsList.add(gsInfo);
					}
					if (gsList.size() != 0) {
						map.put("storageInfoListMap", gsList);
						goodsMapper.addStorageInfos(map);
					}
					// 对应库位
					if (StringUtils.isNotEmpty(goodsInfo.getStoragelocation())) {
						GoodsStorageLocation slInfo = new GoodsStorageLocation();
						slInfo.setGoodsid(goodsInfo.getId());
						slInfo.setIsdefault("1");
						slInfo.setStoragelocationid(goodsInfo.getStoragelocation());
						gslList.add(slInfo);
					}
					if (gslList.size() != 0) {
						map.put("SLListMap", gslList);
						goodsMapper.addGoodsStorageLocation(map);
					}
					// 价格套
					// 辅助计量单位
				}
			}
		} catch (Exception e) {
			if (StringUtils.isEmpty(repeatVal)) {
				errorVal = goodsInfo.getId();
			} else {
				errorVal += "," + goodsInfo.getId();
			}
			errorNum++;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("success", successNum);
		map.put("failure", failureNum);
		map.put("failStr", failStr);
		map.put("repeatNum", repeatNum);
		map.put("closeNum", closeNum);
		map.put("closeVal", closeVal);
		map.put("repeatVal", repeatVal);
		map.put("errorNum", errorNum);
		map.put("errorVal", errorVal);
		return map;
	}

	@Override
	public Map addShortcutGoodsExcel(List<GoodsInfo> list) throws Exception {
		boolean flag = false, gflag = true, pflag = true, muflag = true, wcflag = true, sflag = true, slflag = true;
		int successNum = 0, failureNum = 0, repeatNum = 0, closeNum = 0, errorNum = 0,noidnum = 0;
		String closeVal = "", repeatVal = "", failStr = "", errorVal = "",noidval = "", repeateIndex = "", closeIndex = "", errorIndex = "",noidindex = "";
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		//系统默认主单位
		String unitid = "";
		SysParam unitSysParam = getBaseSysParamMapper().getSysParam("DEFAULTUNITID");
		if(null != unitSysParam){
			unitid = unitSysParam.getPvalue();
		}
		Map returnMap = new HashMap();
		if (list.size() != 0) {
			for (GoodsInfo goodsInfo : list) {
				try {
					GoodsInfo goodsInfo2 = getBaseGoodsMapper().getGoodsInfo(goodsInfo.getId());
					if (null != goodsInfo2) {// 重复
						if ("0".equals(goodsInfo2.getState())) {// 禁用状态，不允许导入
							if (StringUtils.isEmpty(closeVal)) {
								closeVal = goodsInfo.getId();
								closeIndex = (list.indexOf(goodsInfo) + 2) + "";
							} else {
								closeVal += "," + goodsInfo.getId();
								closeIndex += "," + (list.indexOf(goodsInfo) + 2);
							}
							closeNum++;
						} else {
							if (StringUtils.isEmpty(repeatVal)) {
								repeatVal = goodsInfo.getId();
								repeateIndex = (list.indexOf(goodsInfo) + 2) + "";
							} else {
								repeatVal += "," + goodsInfo.getId();
								repeateIndex += "," + (list.indexOf(goodsInfo) + 2);
							}
							repeatNum++;
						}
					} else {
						if(StringUtils.isNotEmpty(goodsInfo.getId())){
							Map map = new HashMap();
							List<GoodsInfo_PriceInfo> plist = new ArrayList<GoodsInfo_PriceInfo>();
							List<GoodsInfo_MteringUnitInfo> mulist = new ArrayList<GoodsInfo_MteringUnitInfo>();
							List<GoodsInfo_WaresClassInfo> gwcList = new ArrayList<GoodsInfo_WaresClassInfo>();
							List<GoodsInfo_StorageInfo> gsList = new ArrayList<GoodsInfo_StorageInfo>();
							List<GoodsStorageLocation> gslList = new ArrayList<GoodsStorageLocation>();
							if (StringUtils.isEmpty(goodsInfo.getPinyin()) && StringUtils.isNotEmpty(goodsInfo.getName())) {
								goodsInfo.setPinyin(CommonUtils.getPinYingJCLen(goodsInfo.getName()));
							}
							if (StringUtils.isNotEmpty(goodsInfo.getBarcode())) {
								goodsInfo.setBarcode(goodsInfo.getBarcode());
							}
							if (StringUtils.isNotEmpty(goodsInfo.getBoxbarcode())) {
								goodsInfo.setBoxbarcode(goodsInfo.getBoxbarcode());
							}
							// 默认分类
							if (null != goodsInfo.getDefaultsortName()) {
								List<WaresClass> WCList = getBaseGoodsMapper().retunWCIdBythisname(goodsInfo.getDefaultsortName());
								if (WCList.size() == 0) {
									WCList = getBaseGoodsMapper().returnWCListByName(goodsInfo.getDefaultsortName());
								}
								if (WCList.size() != 0) {
									goodsInfo.setDefaultsort(WCList.get(0).getId());
									GoodsInfo_WaresClassInfo gwcInfo = new GoodsInfo_WaresClassInfo();
									gwcInfo.setGoodsid(goodsInfo.getId());
									gwcInfo.setIsdefault("1");
									gwcInfo.setWaresclass(WCList.get(0).getId());
									gwcList.add(gwcInfo);
								}
							}
							if (gwcList.size() != 0) {
								map.put("waresClassInfoListMap", gwcList);
								wcflag = goodsMapper.addWaresClassInfos(map) > 0;
							}
							//箱装量
							if(goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) == 0){
								goodsInfo.setBoxnum(new BigDecimal("9999"));
							}else if(decimalScale == 0){
								BigDecimal newboxnum = goodsInfo.getBoxnum().setScale(decimalScale,BigDecimal.ROUND_DOWN);
								goodsInfo.setBoxnum(newboxnum);
							}else if(decimalScale != 0){
								BigDecimal newboxnum = goodsInfo.getBoxnum().setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
								goodsInfo.setBoxnum(newboxnum);
							}
							// 主单位
							if (StringUtils.isNotEmpty(goodsInfo.getMainunitName())) {
								List<MeteringUnit> muList = getBaseGoodsMapper().retunMUIdByName(goodsInfo.getMainunitName());
								if (muList.size() != 0) {
									goodsInfo.setMainunit(muList.get(0).getId());
								}
							}else{
								goodsInfo.setMainunit(unitid);
							}
							// 商品类型
							if (null != goodsInfo.getGoodstypeName()) {
								String goodstype = getBaseSysCodeMapper().codenametocode(goodsInfo.getGoodstypeName(), "goodstype");
								goodsInfo.setGoodstype(goodstype);
							}
							// 商品品牌
							if (null != goodsInfo.getBrandName()) {
								List<Brand> bList = getBaseGoodsMapper().retunBrandIdByName(goodsInfo.getBrandName());
								if (bList.size() != 0) {
									goodsInfo.setBrand(bList.get(0).getId());
									goodsInfo.setDeptid(bList.get(0).getDeptid());
								}
							}
							// 状态
							if (StringUtils.isNotEmpty(goodsInfo.getStateName())) {
								String state = getBaseSysCodeMapper().codenametocode(goodsInfo.getStateName(), "state");
								if (StringUtils.isNotEmpty(state)) {
									goodsInfo.setState(state);
								}
							} else {
								goodsInfo.setState("1");
							}
							BigDecimal taxrate = new BigDecimal(0);
							String taxtype = "";
							// 默认税种
							if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtypeName())) {
								List<TaxType> tList = getBaseFinanceMapper().returnTaxIdByName(goodsInfo.getDefaulttaxtypeName());
								if (tList.size() != 0) {
									taxtype = tList.get(0).getId();
									goodsInfo.setDefaulttaxtype(tList.get(0).getId());
									taxrate = tList.get(0).getRate().divide(new BigDecimal(100)).add(new BigDecimal(1));
								}
							} else {
								SysParam sysParamTaxType = getBaseSysParamMapper().getSysParam("DEFAULTAXTYPE");
								if (null != sysParamTaxType) {
									taxtype = sysParamTaxType.getPvalue();
									goodsInfo.setDefaulttaxtype(sysParamTaxType.getPvalue());
									goodsInfo.setDefaulttaxtypeName(sysParamTaxType.getPvdescription());
									TaxType taxType2 = getBaseFinanceMapper().getTaxTypeInfo(sysParamTaxType.getPvalue());
									if (null != taxType2) {
										taxrate = taxType2.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1));
									}
								}
							}
							// 默认仓库
							if (null != goodsInfo.getStorageName()) {
								List<StorageInfo> sList = getBaseStorageMapper().returnStorageIdByName(goodsInfo.getStorageName());
								if (sList.size() != 0) {
									goodsInfo.setStorageid(sList.get(0).getId());
									GoodsInfo_StorageInfo gsInfo = new GoodsInfo_StorageInfo();
									gsInfo.setGoodsid(goodsInfo.getId());
									gsInfo.setStorageid(sList.get(0).getId());
									gsInfo.setIsdefault("1");
									gsInfo.setChecktype("1");
									gsInfo.setCheckunit("1");
									gsList.add(gsInfo);
								}
							}
							if (gsList.size() != 0) {
								map.put("storageInfoListMap", gsList);
								sflag = goodsMapper.addStorageInfos(map) > 0;
							}
							// 默认库位
							if (null != goodsInfo.getStoragelocationName()) {
								List<StorageLocation> slList = getBaseStorageMapper().returnSLIdByName(goodsInfo.getStoragelocationName());
								if (slList.size() != 0) {
									goodsInfo.setStoragelocation(slList.get(0).getId());
									GoodsStorageLocation slInfo = new GoodsStorageLocation();
									slInfo.setGoodsid(goodsInfo.getId());
									slInfo.setIsdefault("1");
									if (null != goodsInfo.getSlboxnum()) {
										slInfo.setBoxnum(goodsInfo.getSlboxnum());
									}
									slInfo.setStoragelocationid(slList.get(0).getId());
									gslList.add(slInfo);
								}
							}
							if (gslList.size() != 0) {
								map.put("SLListMap", gslList);
								slflag = goodsMapper.addGoodsStorageLocation(map) > 0;
							}
							//是否批次管理
							if(null != goodsInfo.getIsbatchname()){
								String isbatch = getBaseSysCodeMapper().codenametocode(goodsInfo.getIsbatchname(), "yesorno");
								if(StringUtils.isNotEmpty(isbatch)){
									goodsInfo.setIsbatch(isbatch);
								}
							}
							//是否库位管理
							if(null != goodsInfo.getIsstoragelocationname()){
								String isstoragelocation = getBaseSysCodeMapper().codenametocode(goodsInfo.getIsstoragelocationname(), "yesorno");
								if(StringUtils.isNotEmpty(isstoragelocation)){
									goodsInfo.setIsstoragelocation(isstoragelocation);
								}
							}
							// 是否保质期管理
							if (null != goodsInfo.getIsshelflifename()) {
								String isshelflife = getBaseSysCodeMapper().codenametocode(goodsInfo.getIsshelflifename(), "yesorno");
								if (StringUtils.isNotEmpty(isshelflife)) {
									goodsInfo.setIsshelflife(isshelflife);
								}
							}
							// 保质期
							if (StringUtils.isNotEmpty(goodsInfo.getShelflifedetail())) {
								String aa = goodsInfo.getShelflifedetail();
								for (int i = 0; i < aa.length(); i++) {
									String bb = aa.substring(i, i + 1);
									boolean cc = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", bb);
									if (cc) {
										String shelflife = aa.substring(0, i);
										if (StringUtils.isNotEmpty(shelflife)) {
											goodsInfo.setShelflife(new BigDecimal(shelflife));
										}
										if ("天".equals(bb)) {
											goodsInfo.setShelflifeunit("1");
										} else if ("周".equals(bb)) {
											goodsInfo.setShelflifeunit("2");
										} else if ("月".equals(bb)) {
											goodsInfo.setShelflifeunit("3");
										} else if ("年".equals(bb)) {
											goodsInfo.setShelflifeunit("4");
										}
										break;
									}
								}
							}
							// 默认供应商
							if (null != goodsInfo.getDefaultsupplierName()) {
								List<BuySupplier> bsList = getBaseBuySupplierMapper().returnSupplierIdByName(goodsInfo.getDefaultsupplierName());
								if (bsList.size() != 0) {
									goodsInfo.setDefaultsupplier(bsList.get(0).getId());
								}
							}
							// 第二供应商
							if (StringUtils.isNotEmpty(goodsInfo.getSecondsuppliername())) {
								String secondsupplierid = "";
								String[] secondsuppliernameArr = goodsInfo.getSecondsuppliername().split(",");
								for(String secondsuppliername : secondsuppliernameArr){
									List<BuySupplier> bsList = getBaseBuySupplierMapper().returnSupplierIdByName(secondsuppliername);
									if (bsList.size() != 0) {
										if(StringUtils.isEmpty(secondsupplierid)){
											secondsupplierid = bsList.get(0).getId();
										}else{
											secondsupplierid += "," + bsList.get(0).getId();
										}
									}
								}
								goodsInfo.setSecondsupplier(secondsupplierid);
							}
							//购销类型
							if (null != goodsInfo.getBstypeName()) {
								String bstype = getBaseSysCodeMapper().codenametocode(goodsInfo.getBstypeName(), "bstype");
								if(StringUtils.isNotEmpty(bstype)){
									goodsInfo.setBstype(bstype);
								}
							}
							// 货号，判断是否重复
//							if (StringUtils.isNotEmpty(goodsInfo.getItemno())) {
//								int co = goodsMapper.isRepeatGoodsInfoItemno(goodsInfo.getItemno());
//								if (co > 0) {
//									goodsInfo.setItemno("");
//								} else {
//									goodsInfo.setItemno(goodsInfo.getItemno());
//								}
//							}
							if (StringUtils.isEmpty(goodsInfo.getBarcode())) {
								goodsInfo.setBarcode(goodsInfo.getId());
							}
							// 最高采购价 = 最新库存价 = 最新采购价
							if (null != goodsInfo.getHighestbuyprice()) {
								goodsInfo.setNewstorageprice(goodsInfo.getHighestbuyprice());
								goodsInfo.setNewbuyprice(goodsInfo.getHighestbuyprice());
							}
							// 毛重=箱重/箱装量(换算比率)
							if (null != goodsInfo.getTotalweight() && null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0 && goodsInfo.getTotalweight().compareTo(BigDecimal.ZERO) != 0) {
								BigDecimal grossweight = goodsInfo.getTotalweight().divide(goodsInfo.getBoxnum(), 6, BigDecimal.ROUND_HALF_UP);
								goodsInfo.setGrossweight(grossweight);
							}
							// 箱体积 = 长*高*宽*数量
							if (null == goodsInfo.getTotalvolume() || goodsInfo.getTotalvolume().compareTo(BigDecimal.ZERO) == 0) {
								if (null != goodsInfo.getGlength() && null != goodsInfo.getGhight() && null != goodsInfo.getGwidth() && goodsInfo.getGlength().compareTo(BigDecimal.ZERO) != 0 && goodsInfo.getGhight().compareTo(BigDecimal.ZERO) != 0 && goodsInfo.getGwidth().compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal totalvolume = goodsInfo.getGlength().multiply(goodsInfo.getGhight()).multiply(goodsInfo.getGwidth());
									goodsInfo.setTotalvolume(totalvolume);
								}
							}
							// 单体积 = 箱体积/数量
							if (null == goodsInfo.getSinglevolume() || goodsInfo.getSinglevolume().compareTo(BigDecimal.ZERO) == 0) {
								if (null != goodsInfo.getTotalvolume() && null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0 && goodsInfo.getTotalvolume().compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal singlevolume = goodsInfo.getTotalvolume().divide(goodsInfo.getBoxnum(), 6, BigDecimal.ROUND_HALF_UP);
									goodsInfo.setSinglevolume(singlevolume);
								}
							}
							gflag = goodsMapper.addGoodsInfo(goodsInfo) > 0;

							// 价格套
							if (null != goodsInfo.getPrice1()) {
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("1");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("1", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								gpriceInfo.setTaxprice(goodsInfo.getPrice1());
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = goodsInfo.getPrice1().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								if(null != goodsInfo.getBoxprice1() && goodsInfo.getBoxprice1().compareTo(BigDecimal.ZERO) != 0){
									gpriceInfo.setBoxprice(goodsInfo.getBoxprice1());
								}else{
									BigDecimal boxprice = goodsInfo.getPrice1().multiply(goodsInfo.getBoxnum());
									gpriceInfo.setBoxprice(boxprice);
								}
								plist.add(gpriceInfo);
							}else if(null != goodsInfo.getBoxprice1()){
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setBoxprice(goodsInfo.getBoxprice1());
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("1");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("1", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								BigDecimal taxprice = BigDecimal.ZERO;
								if(null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0){
									taxprice = goodsInfo.getBoxprice1().divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setTaxprice(taxprice);
								}
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = taxprice.divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								plist.add(gpriceInfo);
							}
							if (null != goodsInfo.getPrice2()) {
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("2");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("2", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								gpriceInfo.setTaxprice(goodsInfo.getPrice2());
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = goodsInfo.getPrice2().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								if(null != goodsInfo.getBoxprice2() && goodsInfo.getBoxprice2().compareTo(BigDecimal.ZERO) != 0){
									gpriceInfo.setBoxprice(goodsInfo.getBoxprice2());
								}else{
									BigDecimal boxprice = goodsInfo.getPrice2().multiply(goodsInfo.getBoxnum());
									gpriceInfo.setBoxprice(boxprice);
								}
								plist.add(gpriceInfo);
							}else if(null != goodsInfo.getBoxprice2()){
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setBoxprice(goodsInfo.getBoxprice2());
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("2");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("2", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								BigDecimal taxprice = BigDecimal.ZERO;
								if(null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0){
									taxprice = goodsInfo.getBoxprice2().divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setTaxprice(taxprice);
								}
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = taxprice.divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								plist.add(gpriceInfo);
							}
							if (null != goodsInfo.getPrice3()) {
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("3");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("3", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								gpriceInfo.setTaxprice(goodsInfo.getPrice3());
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = goodsInfo.getPrice3().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								if(null != goodsInfo.getBoxprice3() && goodsInfo.getBoxprice3().compareTo(BigDecimal.ZERO) != 0){
									gpriceInfo.setBoxprice(goodsInfo.getBoxprice3());
								}else{
									BigDecimal boxprice = goodsInfo.getPrice3().multiply(goodsInfo.getBoxnum());
									gpriceInfo.setBoxprice(boxprice);
								}
								plist.add(gpriceInfo);
							}else if(null != goodsInfo.getBoxprice3()){
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setBoxprice(goodsInfo.getBoxprice3());
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("3");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("3", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								BigDecimal taxprice = BigDecimal.ZERO;
								if(null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0){
									taxprice = goodsInfo.getBoxprice3().divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setTaxprice(taxprice);
								}
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = taxprice.divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								plist.add(gpriceInfo);
							}
							if (null != goodsInfo.getPrice4()) {
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("4");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("4", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								gpriceInfo.setTaxprice(goodsInfo.getPrice4());
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = goodsInfo.getPrice4().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								if(null != goodsInfo.getBoxprice4() && goodsInfo.getBoxprice4().compareTo(BigDecimal.ZERO) != 0){
									gpriceInfo.setBoxprice(goodsInfo.getBoxprice4());
								}else{
									BigDecimal boxprice = goodsInfo.getPrice4().multiply(goodsInfo.getBoxnum());
									gpriceInfo.setBoxprice(boxprice);
								}
								plist.add(gpriceInfo);
							}else if(null != goodsInfo.getBoxprice4()){
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setBoxprice(goodsInfo.getBoxprice4());
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("4");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("4", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								BigDecimal taxprice = BigDecimal.ZERO;
								if(null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0){
									taxprice = goodsInfo.getBoxprice4().divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setTaxprice(taxprice);
								}
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = taxprice.divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								plist.add(gpriceInfo);
							}
							if (null != goodsInfo.getPrice5() ) {
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("5");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("5", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								gpriceInfo.setTaxprice(goodsInfo.getPrice5());
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = goodsInfo.getPrice5().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								if(null != goodsInfo.getBoxprice5() && goodsInfo.getBoxprice5().compareTo(BigDecimal.ZERO) != 0){
									gpriceInfo.setBoxprice(goodsInfo.getBoxprice5());
								}else{
									BigDecimal boxprice = goodsInfo.getPrice5().multiply(goodsInfo.getBoxnum());
									gpriceInfo.setBoxprice(boxprice);
								}
								plist.add(gpriceInfo);
							}else if(null != goodsInfo.getBoxprice5() ){
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setBoxprice(goodsInfo.getBoxprice5());
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("5");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("5", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								BigDecimal taxprice = BigDecimal.ZERO;
								if(null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0){
									taxprice = goodsInfo.getBoxprice5().divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setTaxprice(taxprice);
								}
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = taxprice.divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								plist.add(gpriceInfo);
							}
							if (null != goodsInfo.getPrice6() ) {
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("6");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("6", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								gpriceInfo.setTaxprice(goodsInfo.getPrice6());
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = goodsInfo.getPrice6().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								if(null != goodsInfo.getBoxprice6() && goodsInfo.getBoxprice6().compareTo(BigDecimal.ZERO) != 0){
									gpriceInfo.setBoxprice(goodsInfo.getBoxprice6());
								}else{
									BigDecimal boxprice = goodsInfo.getPrice6().multiply(goodsInfo.getBoxnum());
									gpriceInfo.setBoxprice(boxprice);
								}
								plist.add(gpriceInfo);
							}else if(null != goodsInfo.getBoxprice6() ){
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setBoxprice(goodsInfo.getBoxprice6());
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("6");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("6", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								BigDecimal taxprice = BigDecimal.ZERO;
								if(null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0){
									taxprice = goodsInfo.getBoxprice6().divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setTaxprice(taxprice);
								}
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = taxprice.divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								plist.add(gpriceInfo);
							}
							if (null != goodsInfo.getPrice7() ) {
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("7");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("7", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								gpriceInfo.setTaxprice(goodsInfo.getPrice7());
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = goodsInfo.getPrice7().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								if(null != goodsInfo.getBoxprice7() && goodsInfo.getBoxprice7().compareTo(BigDecimal.ZERO) != 0){
									gpriceInfo.setBoxprice(goodsInfo.getBoxprice7());
								}else{
									BigDecimal boxprice = goodsInfo.getPrice7().multiply(goodsInfo.getBoxnum());
									gpriceInfo.setBoxprice(boxprice);
								}
								plist.add(gpriceInfo);
							}else if(null != goodsInfo.getBoxprice7() ){
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setBoxprice(goodsInfo.getBoxprice7());
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("7");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("7", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								BigDecimal taxprice = BigDecimal.ZERO;
								if(null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0){
									taxprice = goodsInfo.getBoxprice7().divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setTaxprice(taxprice);
								}
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = taxprice.divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								plist.add(gpriceInfo);
							}
							if (null != goodsInfo.getPrice8() ) {
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("8");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("8", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								gpriceInfo.setTaxprice(goodsInfo.getPrice8());
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = goodsInfo.getPrice8().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								if(null != goodsInfo.getBoxprice8() && goodsInfo.getBoxprice8().compareTo(BigDecimal.ZERO) != 0){
									gpriceInfo.setBoxprice(goodsInfo.getBoxprice8());
								}else{
									BigDecimal boxprice = goodsInfo.getPrice8().multiply(goodsInfo.getBoxnum());
									gpriceInfo.setBoxprice(boxprice);
								}
								plist.add(gpriceInfo);
							}else if(null != goodsInfo.getBoxprice8()){
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setBoxprice(goodsInfo.getBoxprice8());
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("8");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("8", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								BigDecimal taxprice = BigDecimal.ZERO;
								if(null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0){
									taxprice = goodsInfo.getBoxprice8().divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setTaxprice(taxprice);
								}
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = taxprice.divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								plist.add(gpriceInfo);
							}
							if (null != goodsInfo.getPrice9()) {
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("9");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("9", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								gpriceInfo.setTaxprice(goodsInfo.getPrice9());
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = goodsInfo.getPrice9().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								if(null != goodsInfo.getBoxprice9() && goodsInfo.getBoxprice9().compareTo(BigDecimal.ZERO) != 0){
									gpriceInfo.setBoxprice(goodsInfo.getBoxprice9());
								}else{
									BigDecimal boxprice = goodsInfo.getPrice9().multiply(goodsInfo.getBoxnum());
									gpriceInfo.setBoxprice(boxprice);
								}
								plist.add(gpriceInfo);
							}else if(null != goodsInfo.getBoxprice9()){
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setBoxprice(goodsInfo.getBoxprice9());
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("9");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("9", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								BigDecimal taxprice = BigDecimal.ZERO;
								if(null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0){
									taxprice = goodsInfo.getBoxprice9().divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setTaxprice(taxprice);
								}
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = taxprice.divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								plist.add(gpriceInfo);
							}
							if (null != goodsInfo.getPrice10() ) {
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("10");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("10", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								gpriceInfo.setTaxprice(goodsInfo.getPrice10());
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = goodsInfo.getPrice10().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								if(null != goodsInfo.getBoxprice10() && goodsInfo.getBoxprice10().compareTo(BigDecimal.ZERO) != 0){
									gpriceInfo.setBoxprice(goodsInfo.getBoxprice10());
								}else{
									BigDecimal boxprice = goodsInfo.getPrice10().multiply(goodsInfo.getBoxnum());
									gpriceInfo.setBoxprice(boxprice);
								}
								plist.add(gpriceInfo);
							}else if(null != goodsInfo.getBoxprice10() ){
								GoodsInfo_PriceInfo gpriceInfo = new GoodsInfo_PriceInfo();
								gpriceInfo.setBoxprice(goodsInfo.getBoxprice10());
								gpriceInfo.setGoodsid(goodsInfo.getId());
								gpriceInfo.setCode("10");
								SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo("10", "price_list");
								if (null != sysCode) {
									gpriceInfo.setName(sysCode.getCodename());
								}
								BigDecimal taxprice = BigDecimal.ZERO;
								if(null != goodsInfo.getBoxnum() && goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO) != 0){
									taxprice = goodsInfo.getBoxprice10().divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setTaxprice(taxprice);
								}
								if (taxrate.compareTo(BigDecimal.ZERO) != 0) {
									BigDecimal price = taxprice.divide(taxrate, 6, BigDecimal.ROUND_HALF_UP);
									gpriceInfo.setPrice(price);
								}
								gpriceInfo.setTaxtype(taxtype);
								plist.add(gpriceInfo);
							}
							if (plist.size() != 0) {
								map.put("priceInfoMap", plist);
								pflag = goodsMapper.addPriceInfos(map) > 0;
							}

							// 辅单位
							if (null != goodsInfo.getAuxunitname()) {
								GoodsInfo_MteringUnitInfo gmuInfo = new GoodsInfo_MteringUnitInfo();
								List<MeteringUnit> auxmuList = getBaseGoodsMapper().retunMUIdByName(goodsInfo.getAuxunitname());
								if (auxmuList.size() != 0) {
									gmuInfo.setGoodsid(goodsInfo.getId());
									gmuInfo.setIsdefault("1");
									gmuInfo.setMeteringunitid(auxmuList.get(0).getId());
									gmuInfo.setMode("1");
									gmuInfo.setType("1");
									if (null != goodsInfo.getBoxnum()) {
										gmuInfo.setRate(goodsInfo.getBoxnum());
									}
								}
								mulist.add(gmuInfo);
							}else {
								String defaultauxunitid = getSysParamValue("DEFAULTAUXUNITID");
								if (StringUtils.isNotEmpty(defaultauxunitid)) {
									GoodsInfo_MteringUnitInfo gmuInfo = new GoodsInfo_MteringUnitInfo();
									gmuInfo.setGoodsid(goodsInfo.getId());
									gmuInfo.setIsdefault("1");
									gmuInfo.setMeteringunitid(defaultauxunitid);
									gmuInfo.setMode("1");
									gmuInfo.setType("1");
									if (null != goodsInfo.getBoxnum()) {
										gmuInfo.setRate(goodsInfo.getBoxnum());
									}
									mulist.add(gmuInfo);
								}
							}
							if (mulist.size() != 0) {
								map.put("meteringUnitListMap", mulist);
								muflag = goodsMapper.addMeteringUnitInfos(map) > 0;
							}
							if (gflag && pflag && muflag && wcflag && sflag && slflag) {
								flag = true;
								successNum++;
							} else {
								if (StringUtils.isNotEmpty(failStr)) {
									failStr += "," + goodsInfo.getId();
								} else {
									failStr = goodsInfo.getId();
								}
								failureNum++;
							}
						}else{
							if(StringUtils.isEmpty(noidval)){
								noidval = goodsInfo.getName();
								noidindex = (list.indexOf(goodsInfo)+2)+"";
							}
							else{
								noidval += "," + goodsInfo.getName();
								noidindex +="," + (list.indexOf(goodsInfo)+2);
							}
							noidnum++;
						}
					}
				} catch (Exception e) {
					// 删除对应价格套
					goodsMapper.deletePriceInfoByGoodsId(goodsInfo.getId());
					// 删除辅助计量单位
					goodsMapper.deleteMereringUnitInfoByGoodsId(goodsInfo.getId());
					// 删除对应仓库
					goodsMapper.deleteStorageInfoByGoodsId(goodsInfo.getId());
					// 删除对应分类
					goodsMapper.deleteWaresClassInfoByGoodsId(goodsInfo.getId());
					// 删除对应库位
					goodsMapper.deleteGoodsStorageLocationByGoodsId(goodsInfo.getId());
					if (StringUtils.isEmpty(errorVal)) {
						errorVal = goodsInfo.getId();
						errorIndex = (list.indexOf(goodsInfo) + 2) + "";
					} else {
						errorVal += "," + goodsInfo.getId();
						errorIndex += "," + (list.indexOf(goodsInfo) + 2);
					}
					errorNum++;
					continue;
				}
			}
		}
		if (repeateIndex != "") {
			repeatVal = repeatVal + "(导入数据中第" + repeateIndex + "行数据) ";
		}
		if (closeIndex != "") {
			closeVal = closeVal + "(导入数据中第" + closeIndex + "行数据) ";
		}
		if (errorIndex != "") {
			errorVal = errorVal + "(导入数据中第" + errorIndex + "行数据) 导入格式";
		}
		if(noidindex != ""){
			noidval = noidval +"(导入数据中第"+noidindex+"行数据) 编码为空";
		}
		returnMap.put("flag", flag);
		returnMap.put("success", successNum);
		returnMap.put("failure", failureNum);
		returnMap.put("failStr", failStr);
		returnMap.put("repeatNum", repeatNum);
		returnMap.put("repeatVal", repeatVal);
		returnMap.put("closeNum", closeNum);
		returnMap.put("closeVal", closeVal);
		returnMap.put("errorNum", errorNum);
		returnMap.put("errorVal", errorVal);
		returnMap.put("noidval", noidval);
		returnMap.put("noidnum", noidnum);
		return returnMap;
	}

	@Override
	public Map addDRGoodsInfoMU(List<GoodsInfo_MteringUnitInfo> list) throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		if (list.size() != 0) {
			List<GoodsInfo_MteringUnitInfo> addMUList = new ArrayList();
			for (GoodsInfo_MteringUnitInfo goodsInfoMU : list) {
				try {
					if (null != goodsInfoMU.getId()) {
						if (!(goodsMapper.isExistMeteringUnitInfo(goodsInfoMU.getId()) > 0)) {// 不存在
							GoodsInfo_MteringUnitInfo gMteringUnitInfo = goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfoMU.getGoodsid());
							if (null == gMteringUnitInfo) {
								goodsInfoMU.setId(null);
								//箱装量
								if(null == goodsInfoMU.getRate() || goodsInfoMU.getRate().compareTo(BigDecimal.ZERO) == 0){
									goodsInfoMU.setRate(new BigDecimal("9999"));
								}else if(decimalScale == 0){
									BigDecimal newboxnum = goodsInfoMU.getRate().setScale(decimalScale,BigDecimal.ROUND_DOWN);
									goodsInfoMU.setRate(newboxnum);
								}else if(decimalScale != 0){
									BigDecimal newboxnum = goodsInfoMU.getRate().setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
									goodsInfoMU.setRate(newboxnum);
								}
								addMUList.add(goodsInfoMU);
								map.put("meteringUnitListMap", addMUList);
								flag = goodsMapper.addMeteringUnitInfos(map) > 0;
							} else {
								BigDecimal boxnum = new BigDecimal(1);
								if (null != gMteringUnitInfo.getRate() && gMteringUnitInfo.getRate().compareTo(BigDecimal.ZERO) != 0) {
									boxnum = gMteringUnitInfo.getRate();
								}
								GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(gMteringUnitInfo.getGoodsid());
								if (null != goodsInfo) {
									// 毛重、箱重
									if (null != goodsInfo.getTotalweight()) {
										BigDecimal grossweight = goodsInfo.getTotalweight().divide(boxnum);
										goodsInfo.setGrossweight(grossweight);
									} else {
										if (null != goodsInfo.getGrossweight()) {
											goodsInfo.setTotalweight(goodsInfo.getGrossweight().multiply(boxnum));
										}
									}
									// 单体积、箱体积
									if (null != goodsInfo.getTotalvolume()) {
										BigDecimal singlevolume = goodsInfo.getTotalvolume().divide(boxnum);
										goodsInfo.setSinglevolume(singlevolume);
									} else {
										if (null != goodsInfo.getSinglevolume()) {
											goodsInfo.setTotalvolume(goodsInfo.getSinglevolume().multiply(boxnum));
										}
									}
									goodsInfo.setOldId(goodsInfo.getId());
									goodsMapper.editGoodsInfo(goodsInfo);
								}
								flag = true;
							}
						}
					} else {
						//箱装量
						if(null == goodsInfoMU.getRate() || goodsInfoMU.getRate().compareTo(BigDecimal.ZERO) == 0){
							goodsInfoMU.setRate(new BigDecimal("9999"));
						}else if(decimalScale == 0){
							BigDecimal newboxnum = goodsInfoMU.getRate().setScale(decimalScale,BigDecimal.ROUND_DOWN);
							goodsInfoMU.setRate(newboxnum);
						}else if(decimalScale != 0){
							BigDecimal newboxnum = goodsInfoMU.getRate().setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
							goodsInfoMU.setRate(newboxnum);
						}
						addMUList.add(goodsInfoMU);
						map.put("meteringUnitListMap", addMUList);
						flag = goodsMapper.addMeteringUnitInfos(map) > 0;
					}
				} catch (Exception e) {
					flag = false;
					break;
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map addDRGoodsInfoPrice(List<GoodsInfo_PriceInfo> list2) throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		if (list2.size() != 0) {
			for (GoodsInfo_PriceInfo goodsInfoPrice : list2) {
				try {
					List<GoodsInfo_PriceInfo> addPriceList = new ArrayList();
					List<SysCode> list = super.getBaseSysCodeMapper().getSysCodeListForeign("price_list");
					if (null != goodsInfoPrice.getId()) {
						if (!(goodsMapper.isExistPriceInfo(goodsInfoPrice.getId()) > 0)) {// 不存在
							goodsInfoPrice.setId(null);
							if (!(StringUtils.isNotEmpty(goodsInfoPrice.getCode()) && StringUtils.isNotEmpty(goodsInfoPrice.getName()))) {
								if (StringUtils.isNotEmpty(goodsInfoPrice.getCode())) {
									for (SysCode sysCode : list) {
										if (goodsInfoPrice.getCode().equals(sysCode.getCode())) {
											goodsInfoPrice.setName(sysCode.getCodename());
										}
									}
								}
								if (StringUtils.isNotEmpty(goodsInfoPrice.getName())) {
									for (SysCode sysCode : list) {
										if (goodsInfoPrice.getName().equals(sysCode.getCodename())) {
											goodsInfoPrice.setCode(sysCode.getCode());
										}
									}
								}
							}
							addPriceList.add(goodsInfoPrice);
							map.put("priceInfoMap", addPriceList);
							flag = goodsMapper.addPriceInfos(map) > 0;
						}
					} else {
						if (StringUtils.isNotEmpty(goodsInfoPrice.getCode())) {
							for (SysCode sysCode : list) {
								if (goodsInfoPrice.getCode().equals(sysCode.getCode())) {
									goodsInfoPrice.setName(sysCode.getCodename());
								}
							}
						} else if (StringUtils.isNotEmpty(goodsInfoPrice.getName())) {
							for (SysCode sysCode : list) {
								if (goodsInfoPrice.getName().equals(sysCode.getCodename())) {
									goodsInfoPrice.setCode(sysCode.getCode());
								}
							}
						}
						addPriceList.add(goodsInfoPrice);
						map.put("priceInfoMap", addPriceList);
						flag = goodsMapper.addPriceInfos(map) > 0;
					}
				} catch (Exception e) {
					flag = false;
					break;
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map addDRGoodsInfoSL(List<GoodsStorageLocation> list) throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		if (list.size() != 0) {
			for (GoodsStorageLocation goodsInfoSL : list) {
				try {
					List<GoodsStorageLocation> addSLList = new ArrayList();
					if (null != goodsInfoSL.getId()) {
						if (!(goodsMapper.isExistGoodsStorageLocation(goodsInfoSL.getId()) > 0)) {// 不存在
							GoodsStorageLocation goodsStorageLocation = goodsMapper.getSLByGoodsidAndIsdefault(goodsInfoSL.getGoodsid());
							if (null != goodsStorageLocation) {
								goodsStorageLocation.setBoxnum(goodsInfoSL.getBoxnum());
								flag = goodsMapper.editGoodsStorageLocation(goodsStorageLocation) > 0;
							} else {
								goodsInfoSL.setId(null);
								addSLList.add(goodsInfoSL);
								map.put("SLListMap", addSLList);
								flag = goodsMapper.addGoodsStorageLocation(map) > 0;
							}
						}
					} else {
						addSLList.add(goodsInfoSL);
						map.put("SLListMap", addSLList);
						flag = goodsMapper.addGoodsStorageLocation(map) > 0;
					}
				} catch (Exception e) {
					flag = false;
					break;
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map addDRGoodsInfoStorage(List<GoodsInfo_StorageInfo> list) throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		if (list.size() != 0) {
			int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
			for (GoodsInfo_StorageInfo goodsInfoSt : list) {
				if(decimalScale == 0){
					goodsInfoSt.setHighestinventory(goodsInfoSt.getHighestinventory().setScale(decimalScale,BigDecimal.ROUND_DOWN));
					goodsInfoSt.setLowestinventory(goodsInfoSt.getLowestinventory().setScale(decimalScale,BigDecimal.ROUND_DOWN));
					goodsInfoSt.setSafeinventory(goodsInfoSt.getSafeinventory().setScale(decimalScale,BigDecimal.ROUND_DOWN));
				}else{
					goodsInfoSt.setHighestinventory(goodsInfoSt.getHighestinventory().setScale(decimalScale,BigDecimal.ROUND_HALF_UP));
					goodsInfoSt.setLowestinventory(goodsInfoSt.getLowestinventory().setScale(decimalScale,BigDecimal.ROUND_HALF_UP));
					goodsInfoSt.setSafeinventory(goodsInfoSt.getSafeinventory().setScale(decimalScale,BigDecimal.ROUND_HALF_UP));
				}
				try {
					List<GoodsInfo_StorageInfo> addStList = new ArrayList();
					if (null != goodsInfoSt.getId()) {
						if (!(goodsMapper.isExistStorageInfo(goodsInfoSt.getId()) > 0)) {// 存在
							GoodsInfo_StorageInfo gStorageInfo = goodsMapper.getStorageByGoodsidAndIsdefault(goodsInfoSt.getGoodsid());
							if (null == gStorageInfo) {
								goodsInfoSt.setId(null);
								addStList.add(goodsInfoSt);
								map.put("storageInfoListMap", addStList);
								flag = goodsMapper.addStorageInfos(map) > 0;
							} else {
								gStorageInfo.setHighestinventory(goodsInfoSt.getHighestinventory());
								gStorageInfo.setLowestinventory(goodsInfoSt.getLowestinventory());
								gStorageInfo.setSafeinventory(goodsInfoSt.getSafeinventory());
								flag = goodsMapper.editStorageInfo(gStorageInfo) > 0;
							}
						}
					} else {
						addStList.add(goodsInfoSt);
						map.put("storageInfoListMap", addStList);
						flag = goodsMapper.addStorageInfos(map) > 0;
					}
				} catch (Exception e) {
					flag = false;
					break;
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map addDRGoodsInfoWC(List<GoodsInfo_WaresClassInfo> list) throws Exception {
		Map map = new HashMap();
		boolean flag = false;
		if (list.size() != 0) {
			for (GoodsInfo_WaresClassInfo goodsInfoWC : list) {
				try {
					List<GoodsInfo_WaresClassInfo> addWCList = new ArrayList();
					if (null != goodsInfoWC.getId()) {
						if (!(goodsMapper.isExistWaresClassInfo(goodsInfoWC.getId()) > 0)) {// 不存在
							GoodsInfo_WaresClassInfo gWaresClassInfo = goodsMapper.getWCByGoodsidAndIsdefault(goodsInfoWC.getGoodsid());
							if (null == gWaresClassInfo) {
								goodsInfoWC.setId(null);
								addWCList.add(goodsInfoWC);
								map.put("waresClassInfoListMap", addWCList);
								flag = goodsMapper.addWaresClassInfos(map) > 0;
							} else {
								flag = true;
							}
						}
					} else {
						addWCList.add(goodsInfoWC);
						map.put("waresClassInfoListMap", addWCList);
						flag = goodsMapper.addWaresClassInfos(map) > 0;
					}
				} catch (Exception e) {
					flag = false;
					break;
				}
			}
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public boolean isRepeatGoodsInfoID(String id) throws Exception {
		return goodsMapper.isRepeatGoodsInfoID(id) > 0;
	}

	@Override
	public boolean isRepeatGoodsInfoName(String name) throws Exception {
		return goodsMapper.isRepeatGoodsInfoName(name) > 0;
	}

	@Override
	public boolean isRepeatGoodsInfoBarcode(String barcode) throws Exception {
		return goodsMapper.isRepeatGoodsInfoBarcode(barcode) > 0;
	}

	@Override
	public boolean isRepeatGoodsInfoBoxbarcode(String boxbarcode) throws Exception {
		return goodsMapper.isRepeatGoodsInfoBoxbarcode(boxbarcode) > 0;
	}

	@Override
	public boolean isRepeatGoodsInfoItemno(String itemno) throws Exception {
		return goodsMapper.isRepeatGoodsInfoItemno(itemno) > 0;
	}

	@Override
	public List showMeteringUnitInfoList(PageMap pageMap) throws Exception {
		// 数据权限
		String sql = getDataAccessRule("t_base_goods_info_meteringunit", null);
		pageMap.setDataSql(sql);
		return goodsMapper.getMeteringUnitInfoList(pageMap);
	}

	@Override
	public List showPriceInfoList(PageMap pageMap) throws Exception {
		// 单表取字段权限
		String cols = getAccessColumnList("t_base_goods_info_price", null);
		pageMap.setCols(cols);
		// 数据权限
		String sql = getDataAccessRule("t_base_goods_info_price", null);
		pageMap.setDataSql(sql);
		return goodsMapper.getPriceInfoList(pageMap);
	}

	@Override
	public List showStorageInfoList(PageMap pageMap) throws Exception {
		// 数据权限
		String sql = getDataAccessRule("t_base_goods_info_storage", null);
		pageMap.setDataSql(sql);
		return goodsMapper.getStorageInfoList(pageMap);
	}

	@Override
	public List showGoodsStorageLocationList(PageMap pageMap) throws Exception {
		// 数据权限
		String sql = getDataAccessRule("t_base_goods_info_storagelocation", null);
		pageMap.setDataSql(sql);
		return goodsMapper.getGoodsStorageLocationList(pageMap);
	}

	@Override
	public List showWaresClassInfoList(PageMap pageMap) throws Exception {
		// 数据权限
		String sql = getDataAccessRule("t_base_goods_info_waresclass", null);
		pageMap.setDataSql(sql);
		return goodsMapper.getWaresClassInfoList(pageMap);
	}

	/**
	 * 根据价格套id批量删除价格套
	 * 
	 * @param delPriceIds
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 23, 2013
	 */
	public boolean deletePriceInfos(String delPriceIds) throws Exception {
		String[] delPriceArr = delPriceIds.split(",");
		int d = goodsMapper.deletePriceInfos(delPriceArr);
		return d > 0;
	}

	/**
	 * 根据主计量单位id批量删除主计量单位
	 * 
	 * @param delMUIds
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 23, 2013
	 */
	public boolean deleteMeteringUnitInfos(String delMUIds) throws Exception {
		String[] delMUArr = delMUIds.split(",");
		int d = goodsMapper.deleteMeteringUnitInfos(delMUArr);
		return d > 0;
	}

	/**
	 * 根据对应仓库id批量删除对应仓库
	 * 
	 * @param delStorageIds
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 23, 2013
	 */
	public boolean deleteStorageInfos(String delStorageIds) throws Exception {
		String[] delStorageArr = delStorageIds.split(",");
		int d = goodsMapper.deleteStorageInfos(delStorageArr);
		return d > 0;
	}

	/**
	 * 根据对应分类id批量删除对应分类
	 * 
	 * @param delWCIds
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 23, 2013
	 */
	public boolean deleteWaresClassInfos(String delWCIds) throws Exception {
		String[] idsArr = delWCIds.split(",");
		int d = goodsMapper.deleteWaresClassInfos(idsArr);
		return d > 0;
	}

	public boolean deleteSLInfos(String delSLIds) throws Exception {
		String[] idsArr = delSLIds.split(",");
		int sl = goodsMapper.deleteGoodsStorageLocation(idsArr);
		return sl > 0;
	}

	@Override
	public boolean addPriceInfos(Map priceInfoMap) throws Exception {
		return goodsMapper.addPriceInfos(priceInfoMap) > 0;
	}

	@Override
	public PageData getGoodsInfoPriceByCode(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(goodsMapper.getPriceListByCodeCount(pageMap), goodsMapper.getPriceListByCode(pageMap), pageMap);
		return pageData;
	}

	@Override
	public boolean editPriceInfo(GoodsInfo_PriceInfo goodsInfo_PriceInfo) throws Exception {
		return goodsMapper.editPriceInfo(goodsInfo_PriceInfo) > 0;
	}

	@Override
	public List<GoodsInfo_MteringUnitInfo> getMUListByGoodsId(String goodsid) throws Exception {
		return goodsMapper.getMUListByGoodsId(goodsid);
	}

	@Override
	public List<GoodsInfo> getGoodsInfoByCondition(PageMap pageMap) throws Exception {
		return goodsMapper.getGoodsInfoByCondition(pageMap);
	}

	@Override
	public boolean updateGoodsInfoWriteBack(GoodsInfo goodsInfo) throws Exception {
		return goodsMapper.updateGoodsInfoWriteBack(goodsInfo) > 0;
	}

	@Override
	public PageData getBrandListForCombobox(PageMap pageMap) throws Exception {
		if (null != pageMap.getCondition().get("brandid")) {
			String brandid = pageMap.getCondition().get("brandid").toString();
			if (brandid.indexOf(",") != -1) {
				String[] idArr = brandid.split(",");
				List<String> list = new ArrayList<String>(Arrays.asList(idArr));
				pageMap.getCondition().put("list", list);
			} else {
				String idArr[] = new String[0];
				idArr[0] = brandid;
				List<String> list = new ArrayList<String>(Arrays.asList(idArr));
				pageMap.getCondition().put("list", list);
			}
		} else {
			pageMap.getCondition().put("list", null);
		}
		if (null != pageMap.getCondition().get("deptid")) {
			String deptids = pageMap.getCondition().get("deptid").toString();
			String str = "";
			if (deptids.indexOf(",") != -1) {
				String[] deptArr = deptids.split(",");
				for (String deptid : deptArr) {
					if (StringUtils.isEmpty(str)) {
						str = "deptid like '" + deptid + "%'";
					} else {
						str += " or " + "deptid like '" + deptid + "%'";
					}
				}
			} else {
				if (StringUtils.isEmpty(str)) {
					str = "deptid like '" + deptids + "%'";
				} else {
					str += " or " + "deptid like '" + deptids + "%'";
				}
			}
			pageMap.getCondition().put("deptid", str);
		}
		PageData pageData = new PageData(goodsMapper.getBrandListForComboboxCount(pageMap), goodsMapper.getBrandListForCombobox(pageMap), pageMap);
		return pageData;
	}

	@Override
	public List getGoodsListForCombobox(String brandid) throws Exception {
		return goodsMapper.getGoodsListForCombobox(brandid);
	}

	@Override
	public PageData getGoodsSelectListData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_base_goods_info", "g");
		pageMap.getCondition().put("datasql", dataSql);
		SysUser sysUser = getSysUser();
		// 品牌业务员与厂家业务员不能同时存在
		// 判断是否品牌业务员
		String brandUserRoleName = getSysParamValue("BrandUserRoleName");
		boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
		if (isBrandUser) {
			pageMap.getCondition().put("isBrandUser", true);
			pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
		} else {
			// 判断是否厂家业务员
			String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
			boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
			if (isSupplierUser) {
				pageMap.getCondition().put("isSupplierUser", true);
				pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
			}
		}

		// 控件传过来的参数 生成sql语句
		String paramRule = (String) pageMap.getCondition().get("paramRule");
		if (StringUtils.isNotEmpty(paramRule)) {
			String paramRuleSql = RuleJSONUtils.widgetParamToSql(paramRule, "g");
			pageMap.getCondition().put("paramRuleSql", paramRuleSql);
		}
		// 买赠捆绑
		boolean isPromotion = false;
		if (pageMap.getCondition().containsKey("isPromotion")) {
			String isPromotionStr = (String) pageMap.getCondition().get("isPromotion");
			if ("1".equals(isPromotionStr)) {
				isPromotion = true;
			} else {
				pageMap.getCondition().remove("isPromotion");
			}
		}
		// 多条件模糊查询 以空格为标识
		if (pageMap.getCondition().containsKey("id")) {
			String id = (String) pageMap.getCondition().get("id");
			if (StringUtils.isNotEmpty(id) && id.indexOf(" ") >= 0) {
				String[] conArr = id.split(" ");
				List<String> conList = new ArrayList<String>();
				for (String con : conArr) {
					con = con.trim();
					if (StringUtils.isNotEmpty(con)) {
						conList.add(con);
					}
				}
				pageMap.getCondition().put("con", conList);
			}
		}
        String customerid = (String) pageMap.getCondition().get("customerid");
		if (pageMap.getCondition().containsKey("customerid")) {

			Customer customer = getBaseCustomerMapper().getCustomerInfo(customerid);
            if(null!=customer){
                pageMap.getCondition().put("pcustomerid",customer.getPid());
            }
            if(isPromotion){
                // 1单一客户2促销分类3分类客户4价格套5销售区域7信用等级6总店客户
                if (customer != null) {
                    Map priceMap = new HashMap();
                    // 单一客户
                    priceMap.put("type", "1");
                    priceMap.put("customerid", customerid);
                    // 促销分类
                    String csort = customer.getPromotionsort();
                    if (StringUtils.isNotEmpty(csort)) {
                        priceMap.put("promotionsort", csort);
                    }
                    // 取客户的默认分类
                    String customerSort = customer.getCustomersort();
                    if (StringUtils.isNotEmpty(customerSort)) {
                        priceMap.put("customersort", customerSort);
                    }
                    // 取客户价格套
                    String priceSort = customer.getPricesort();
                    if (StringUtils.isNotEmpty(priceSort)) {
                        priceMap.put("pricesort", priceSort);
                    }
                    // 取客户所属销售区域
                    String saleArea = customer.getSalesarea();
                    if (StringUtils.isNotEmpty(saleArea)) {
                        priceMap.put("salesarea", saleArea);
                    }
                    // 取客户 总店
                    String pcustomerid = customer.getPid();
                    if (StringUtils.isNotEmpty(pcustomerid)) {
                        priceMap.put("pcustomerid", pcustomerid);
                    }
                    // 取信用等级
                    String credit = customer.getCreditrating();
                    if (StringUtils.isNotEmpty(credit)) {
                        priceMap.put("credit", credit);
                    }
                    // 取核销方式
                    String canceltype = customer.getCanceltype();
                    if (StringUtils.isNotEmpty(canceltype)) {
                        priceMap.put("canceltype", canceltype);
                    }
                    pageMap.getCondition().put("promotionMap", priceMap);
                }
            }
		}

        /***********************************************************************
         * 分销规则 start
         **********************************************************************/
        {
            if("true".equals(pageMap.getCondition().get("distribution"))) {

				List okDistributions = salesService.selectDistributionRuleIdByCustomer(customerid, "1");
				if(okDistributions.size() > 0) {

					pageMap.getCondition().put("okDistributions", okDistributions);
				}
				List ngDistributions = salesService.selectDistributionRuleIdByCustomer(customerid, "0");
				if(ngDistributions.size() > 0) {

					pageMap.getCondition().put("ngDistributions", ngDistributions);
				}
            }
        }
        /***********************************************************************
         * 分销规则 end
         **********************************************************************/

		List<GoodsInfo> list = goodsMapper.getGoodsSelectListData(pageMap);
        StorageInfo storageInfo  = null;
        if(pageMap.getCondition().containsKey("storageid")){
            String storageid = (String) pageMap.getCondition().get("storageid");
            storageInfo  = getStorageMapper().showStorageInfo(storageid);
        }

		// 获取商品可用量字段权限
		Map colMap = getAccessColumn("t_storage_summary");
		for (GoodsInfo goodsInfo : list) {
			if ("0".equals(goodsInfo.getPtype())) {
//                if(null!=storageInfo && "0".equals(storageInfo.getIsbatch())){
//                    goodsInfo.setIsbatch("0");
//                }
				Brand brand = getBrandInfo(goodsInfo.getBrand());
				if (null != brand) {
					goodsInfo.setBrandName(brand.getName());
				}
				MeteringUnit meteringUnit = showMeteringUnitInfo(goodsInfo.getMainunit());
				if (null != meteringUnit) {
					goodsInfo.setMainunitName(meteringUnit.getName());
				}
				if (null == colMap || colMap.size() == 0 || colMap.containsKey("usablenum")) {
					String storageid = null;
					if (pageMap.getCondition().containsKey("storageid")) {
						storageid = (String) pageMap.getCondition().get("storageid");
					}
					// 获取库存dao
					StorageSummaryMapper storageSummaryMapper = (StorageSummaryMapper) SpringContextUtils.getBean("storageSummaryMapper");
					if (StringUtils.isNotEmpty(storageid)) {
						StorageSummary storageSummary = storageSummaryMapper.getStorageSummaryInfoByGoodsidAndStorageid(goodsInfo.getId(), storageid);
						if (null != storageSummary) {
							goodsInfo.setNewinventory(storageSummary.getUsablenum());
							goodsInfo.setHighestinventory(storageSummary.getExistingnum());
						}
						//仓库独立核算 成本价设置为该仓库的成本价
						if(null!=storageInfo && "1".equals(storageInfo.getIsaloneaccount())){
							goodsInfo.setNewstorageprice(storageSummary.getCostprice());
						}
//                        StorageInfo storageInfo = getStorageMapper().showStorageInfo(storageid);
//                        if(null!=storageInfo && "0".equals(storageInfo.getIsbatch())){
//                            goodsInfo.setIsbatch("0");
//                        }
					} else {
						StorageSummary storageSummary = storageSummaryMapper.getStorageSummarySumByGoodsid(goodsInfo.getId());
						if (null != storageSummary) {
							goodsInfo.setNewinventory(storageSummary.getUsablenum());
							goodsInfo.setHighestinventory(storageSummary.getExistingnum());
						}
					}
				} else {
					goodsInfo.setNewinventory(null);
				}
				GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = getGoodsMapper().getMUInfoByGoodsIdAndIsdefault(goodsInfo.getId());
				if (null != goodsInfo_MteringUnitInfo) {
					goodsInfo.setBoxnum(goodsInfo_MteringUnitInfo.getRate());
					goodsInfo.setAuxunitid(goodsInfo_MteringUnitInfo.getMeteringunitid());
					MeteringUnit auxmeteringUnit = showMeteringUnitInfo(goodsInfo_MteringUnitInfo.getMeteringunitid());
					if (null != auxmeteringUnit) {
						goodsInfo.setAuxunitname(auxmeteringUnit.getName());
					}
					
				}
			} else {

			}

		}
		PageData pageData = new PageData(goodsMapper.getGoodsSelectListDataCount(pageMap), list, pageMap);
		return pageData;
	}



	@Override
	public List<GoodsInfo> getGoodsSelectListDataSimple(PageMap pageMap) throws Exception {
		List<GoodsInfo> list = goodsMapper.getGoodsSelectListDataSimple(pageMap);
		return list;
	}
	@Override
	public List getPriceListByGoodsid(String goodsid) throws Exception {
		return goodsMapper.getPriceListByGoodsid(goodsid);
	}

	@Override
	public boolean addGoodsInfoShortcut(GoodsInfo goodsInfo, List<GoodsInfo_PriceInfo> priceInfoList, GoodsInfo_MteringUnitInfo goodsMUInfo) throws Exception {
		int p = 0, mu = 0, s = 0, wc = 0, sl = 0;
		Map map = new HashMap();
		// 判断是否存在默认税种,若不存在,则取默认税种
		if (StringUtils.isEmpty(goodsInfo.getDefaulttaxtype())) {
			SysParam sysParamTaxType = getBaseSysParamMapper().getSysParam("DEFAULTAXTYPE");
			if (null != sysParamTaxType) {
				goodsInfo.setDefaulttaxtype(sysParamTaxType.getPvalue());
				goodsInfo.setDefaulttaxtypeName(sysParamTaxType.getPvdescription());
			}
		}
		// 新增价格套管理信息
		if (priceInfoList != null && priceInfoList.size() != 0) {
			List<GoodsInfo_PriceInfo> addPriceList = new ArrayList<GoodsInfo_PriceInfo>();
			for (GoodsInfo_PriceInfo priceInfo2 : priceInfoList) {
				if (null != priceInfo2.getTaxprice()) {
					priceInfo2.setGoodsid(goodsInfo.getId());
					// 税种赋值
					if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())) {
						priceInfo2.setTaxtype(goodsInfo.getDefaulttaxtype());
						// 获取税率
						TaxType taxType = financeMapper.getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
						if (null != taxType && null != taxType.getRate() && taxType.getRate().compareTo(new BigDecimal(0)) != -1) {
							// 计算无税单价
							BigDecimal rate = taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1));
							BigDecimal price = priceInfo2.getTaxprice().divide(rate, 6, BigDecimal.ROUND_HALF_UP);
							priceInfo2.setPrice(price);
						}
					}
					// addPriceList.add(priceInfo2);
				}
				goodsMapper.addPriceInfo(priceInfo2);
			}
			// map.put("priceInfoMap", addPriceList);
		}
		// 新增辅计量单位
		if (null != goodsMUInfo) {
			goodsMUInfo.setGoodsid(goodsInfo.getId());
			// 换算类型
			goodsMUInfo.setType("1");
			// 换算方式
			goodsMUInfo.setMode("1");
			goodsMUInfo.setIsdefault("1");
			if (null != goodsMUInfo.getMeteringunitid()) {
				goodsMUInfo.setMeteringunitid(goodsMUInfo.getMeteringunitid());
			}
			if (null != goodsMUInfo.getRate()) {
				if(goodsMUInfo.getRate().compareTo(BigDecimal.ZERO) != 0){
					goodsMUInfo.setRate(goodsMUInfo.getRate());
					goodsInfo.setBoxnum(goodsMUInfo.getRate());
				}else{
					goodsMUInfo.setRate(new BigDecimal("9999"));
					goodsInfo.setBoxnum(new BigDecimal("9999"));
				}
			}else{
				goodsMUInfo.setRate(new BigDecimal("9999"));
				goodsInfo.setBoxnum(new BigDecimal("9999"));
			}
			if (StringUtils.isNotEmpty(goodsInfo.getBoxbarcode())) {
				goodsMUInfo.setBarcode(goodsInfo.getBoxbarcode());
			}
			List<GoodsInfo_MteringUnitInfo> muList = new ArrayList<GoodsInfo_MteringUnitInfo>();
			muList.add(goodsMUInfo);
			map.put("meteringUnitListMap", muList);
		}
		// 新增对应仓库
		if (StringUtils.isNotEmpty(goodsInfo.getStorageid())) {
			GoodsInfo_StorageInfo storageInfo = new GoodsInfo_StorageInfo();
			storageInfo.setGoodsid(goodsInfo.getId());
			storageInfo.setStorageid(goodsInfo.getStorageid());
			storageInfo.setIsdefault("1");
			List<GoodsInfo_StorageInfo> sList = new ArrayList<GoodsInfo_StorageInfo>();
			sList.add(storageInfo);
			map.put("storageInfoListMap", sList);
		}
		// 新增对应分类
		if (StringUtils.isNotEmpty(goodsInfo.getDefaultsort())) {
			GoodsInfo_WaresClassInfo wcInfo = new GoodsInfo_WaresClassInfo();
			wcInfo.setGoodsid(goodsInfo.getId());
			wcInfo.setWaresclass(goodsInfo.getDefaultsort());
			wcInfo.setIsdefault("1");
			List<GoodsInfo_WaresClassInfo> wcList = new ArrayList<GoodsInfo_WaresClassInfo>();
			wcList.add(wcInfo);
			map.put("waresClassInfoListMap", wcList);
		}
		// 新增对应库位
		if (StringUtils.isNotEmpty(goodsInfo.getStoragelocation())) {
			GoodsStorageLocation slInfo = new GoodsStorageLocation();
			slInfo.setGoodsid(goodsInfo.getId());
			slInfo.setStoragelocationid(goodsInfo.getStoragelocation());
			slInfo.setIsdefault("1");
			if (null != goodsInfo.getSlboxnum()) {
				slInfo.setBoxnum(goodsInfo.getSlboxnum());
			}
			List<GoodsStorageLocation> slList = new ArrayList<GoodsStorageLocation>();
			slList.add(slInfo);
			map.put("SLListMap", slList);
		}
		if (map.containsKey("priceInfoMap")) {
			p = goodsMapper.addPriceInfos(map);
		} else {
			p = 1;
		}
		if (map.containsKey("meteringUnitListMap")) {
			mu = goodsMapper.addMeteringUnitInfos(map);
		} else {
			mu = 1;
		}
		if (map.containsKey("storageInfoListMap")) {
			s = goodsMapper.addStorageInfos(map);
		} else {
			s = 1;
		}
		if (map.containsKey("waresClassInfoListMap")) {
			wc = goodsMapper.addWaresClassInfos(map);
		} else {
			wc = 1;
		}
		if (map.containsKey("SLListMap")) {
			sl = goodsMapper.addGoodsStorageLocation(map);
		} else {
			sl = 1;
		}
		if (StringUtils.isEmpty(goodsInfo.getState())) {
			goodsInfo.setState("2");
		}
		// 拼音
		if (StringUtils.isEmpty(goodsInfo.getPinyin()) && StringUtils.isNotEmpty(goodsInfo.getName())) {
			goodsInfo.setPinyin(CommonUtils.getPinYingJCLen(goodsInfo.getName()));
		}
		// 最高采购价 == 最新采购价 == 最新库存价
		if (null != goodsInfo.getHighestbuyprice()) {
			goodsInfo.setNewstorageprice(goodsInfo.getHighestbuyprice());
			goodsInfo.setNewbuyprice(goodsInfo.getHighestbuyprice());
		}
		// 长宽高计算单体积
		if (null == goodsInfo.getTotalvolume()) {
			if (null != goodsInfo.getGlength() && null != goodsInfo.getGwidth() && null != goodsInfo.getGhight()) {
				BigDecimal totalvolume = goodsInfo.getGlength().multiply(goodsInfo.getGwidth()).multiply(goodsInfo.getGhight());
				goodsInfo.setTotalvolume(totalvolume);
			}
		}
		// 输入箱重、箱体积后，自动计算毛重、单体积
		if (null != goodsMUInfo.getRate() && goodsMUInfo.getRate().compareTo(BigDecimal.ZERO) != 0) {
			if (null != goodsInfo.getTotalweight()) {
				BigDecimal grossweight = goodsInfo.getTotalweight().divide(goodsMUInfo.getRate(), 6, BigDecimal.ROUND_HALF_UP);
				goodsInfo.setGrossweight(grossweight);
			} else {
				if (null != goodsInfo.getGrossweight()) {
					goodsInfo.setTotalweight(goodsInfo.getGrossweight().multiply(goodsMUInfo.getRate()));
				}
			}
			if (null != goodsInfo.getTotalvolume()) {
				BigDecimal singlevolume = goodsInfo.getTotalvolume().divide(goodsMUInfo.getRate(), 6, BigDecimal.ROUND_HALF_UP);
				goodsInfo.setSinglevolume(singlevolume);
			} else {
				if (null != goodsInfo.getSinglevolume()) {
					BigDecimal totalvolume = goodsInfo.getSinglevolume().multiply(goodsMUInfo.getRate());
					goodsInfo.setTotalvolume(totalvolume);
				}
			}
		}
		// 根据品牌获取所属部门
		if (StringUtils.isEmpty(goodsInfo.getDeptid())) {
			Brand brand = getBaseGoodsMapper().getBrandInfo(goodsInfo.getBrand());
			if (null != brand) {
				goodsInfo.setDeptid(brand.getDeptid());
			}
		}
		if ((p > 0) && (mu > 0) && (s > 0) && (wc > 0) && (sl > 0)) {
			return goodsMapper.addGoodsInfo(goodsInfo) > 0;
		} else {
			return false;
		}
	}

	@Override
	public Map editGoodsInfoShortcut(GoodsInfo goodsInfo, List<GoodsInfo_PriceInfo> priceInfoList, GoodsInfo_MteringUnitInfo goodsMUInfo) throws Exception {
		boolean flag = false, unEditFlag = true, lockFlag = true;
		// 保存修改前判断数据是否已经被加锁 可以修改
		if (isLockEdit("t_base_goods_info", goodsInfo.getOldId())) {
			// 是否允许修改
			GoodsInfo beforeGoodsInfo = goodsMapper.getGoodsInfo(goodsInfo.getOldId());
			// 判断是否可以进行修改操作，若可以修改，更新级联关系数据
			if (canBasefilesIsEdit("t_base_goods_info", beforeGoodsInfo, goodsInfo)) {
				// 最高采购价 == 最新采购价
				if (null != goodsInfo.getHighestbuyprice() && null != beforeGoodsInfo.getHighestbuyprice() && goodsInfo.getHighestbuyprice().compareTo(beforeGoodsInfo.getHighestbuyprice()) != 0) {
					goodsInfo.setNewbuyprice(goodsInfo.getHighestbuyprice());
				}
				// 长宽高计算单体积
				if (null == goodsInfo.getTotalvolume()) {
					if (null != goodsInfo.getGlength() && null != goodsInfo.getGwidth() && null != goodsInfo.getGhight()) {
						BigDecimal totalvolume = goodsInfo.getGlength().multiply(goodsInfo.getGwidth()).multiply(goodsInfo.getGhight());
						goodsInfo.setTotalvolume(totalvolume);
					}
				}
				// 输入箱重、箱体积后，自动计算毛重、单体积
				if (null != goodsMUInfo.getRate() && goodsMUInfo.getRate().compareTo(BigDecimal.ZERO) != 0) {
					if (null != goodsInfo.getTotalweight()) {
						BigDecimal grossweight = goodsInfo.getTotalweight().divide(goodsMUInfo.getRate(), 6, BigDecimal.ROUND_HALF_UP);
						goodsInfo.setGrossweight(grossweight);
					} else {
						if (null != goodsInfo.getGrossweight()) {
							goodsInfo.setTotalweight(goodsInfo.getGrossweight().multiply(goodsMUInfo.getRate()));
						}
					}
					if (null != goodsInfo.getTotalvolume()) {
						BigDecimal singlevolume = goodsInfo.getTotalvolume().divide(goodsMUInfo.getRate(), 6, BigDecimal.ROUND_HALF_UP);
						goodsInfo.setSinglevolume(singlevolume);
					} else {
						if (null != goodsInfo.getSinglevolume()) {
							BigDecimal totalvolume = goodsInfo.getSinglevolume().multiply(goodsMUInfo.getRate());
							goodsInfo.setTotalvolume(totalvolume);
						}
					}
				}
				int i = goodsMapper.editGoodsInfo(goodsInfo);
				if (i > 0) {
					int p = 0, mu = 0, s = 0, wc = 0, sl = 0;
					Map map = new HashMap();
					// 修改价格套管理
					List<GoodsInfo_PriceInfo> addPriceList = new ArrayList<GoodsInfo_PriceInfo>();
					// 根据商品编码获取价格套管理列表数据
					List<GoodsInfo_PriceInfo> priceList = goodsMapper.getPriceListByGoodsid(goodsInfo.getOldId());
					// 若该商品价格套管理为空
					if (priceList.size() == 0) {
						if (priceInfoList != null && priceInfoList.size() != 0) {
							for (GoodsInfo_PriceInfo priceInfo2 : priceInfoList) {
								if (null != priceInfo2.getTaxprice()) {
									priceInfo2.setGoodsid(goodsInfo.getId());
									// 税种赋值
									if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())) {
										priceInfo2.setTaxtype(goodsInfo.getDefaulttaxtype());
										// 获取税率
										TaxType taxType = financeMapper.getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
										if (null != taxType && null != taxType.getRate() && taxType.getRate().compareTo(new BigDecimal(0)) != -1) {
											// 计算无税单价
											BigDecimal rate = taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1));
											BigDecimal price = priceInfo2.getTaxprice().divide(rate, 6, BigDecimal.ROUND_HALF_UP);
											priceInfo2.setPrice(price);
										}
									}
									addPriceList.add(priceInfo2);
								}
							}
							if (addPriceList.size() != 0) {
								map.put("priceInfoMap", addPriceList);
								p = goodsMapper.addPriceInfos(map);
							} else {
								p = 1;
							}
						}
					}// 若该商品价格套管理不为空，则判断修改的价格套管理数据是否存在删除，修改操作
					else {
						if (priceInfoList != null && priceInfoList.size() != 0) {
							for (GoodsInfo_PriceInfo priceInfo2 : priceInfoList) {
								GoodsInfo_PriceInfo priceInfo = goodsMapper.getPriceDataByGoodsidAndCode(goodsInfo.getOldId(), priceInfo2.getCode());
								if (null != priceInfo) {// 数据库中存在该商品价格套数据
									if (null != priceInfo2.getTaxprice()) {// 修改操作
										priceInfo.setGoodsid(goodsInfo.getId());
										// 税种赋值
										if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())) {
											priceInfo.setTaxtype(goodsInfo.getDefaulttaxtype());
											// 获取税率
											TaxType taxType = financeMapper.getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
											if (null != taxType && null != taxType.getRate() && taxType.getRate().compareTo(new BigDecimal(0)) != -1) {
												// 计算无税单价
												BigDecimal rate = taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1));
												BigDecimal price = priceInfo2.getTaxprice().divide(rate, 6, BigDecimal.ROUND_HALF_UP);
												priceInfo.setPrice(price);
											}
										}
										priceInfo.setTaxprice(priceInfo2.getTaxprice());
                                        priceInfo.setBoxprice(priceInfo2.getBoxprice());
										if (goodsMapper.editPriceInfo(priceInfo) > 0) {
											p++;
										}
									} else {// 删除操作
										if (goodsMapper.deletePriceDataByGoodsidAndCode(goodsInfo.getOldId(), priceInfo2.getCode()) > 0) {
											List<SysCode> codeList = super.getBaseSysCodeMapper().getSysCodeListForeign("price_list");
											if (codeList.size() != 0) {
												for (SysCode sysCode : codeList) {
													if (priceInfo2.getCode().equals(sysCode.getCode())) {
														sysCode.setVal(null);
													}
												}
											}
											p++;
										}
									}
								} else {// 新增操作
									if (null != priceInfo2.getTaxprice()) {
										priceInfo2.setGoodsid(goodsInfo.getId());
										// 税种赋值
										if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())) {
											priceInfo2.setTaxtype(goodsInfo.getDefaulttaxtype());
											// 获取税率
											TaxType taxType = financeMapper.getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
											if (null != taxType && null != taxType.getRate() && taxType.getRate().compareTo(new BigDecimal(0)) != -1) {
												// 计算无税单价
												BigDecimal rate = taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1));
												BigDecimal price = priceInfo2.getTaxprice().divide(rate, 6, BigDecimal.ROUND_HALF_UP);
												priceInfo2.setPrice(price);
											}
										}
										addPriceList.add(priceInfo2);
									}
								}
							}
							if (addPriceList.size() != 0) {
								map.put("priceInfoMap", addPriceList);
								if (goodsMapper.addPriceInfos(map) == addPriceList.size()) {
									p += addPriceList.size();
								}
							}
						}
					}
					// 修改辅计量单位
					// 根据商品编码、默认辅计量单位为是获取辅单位详情
					GoodsInfo_MteringUnitInfo muInfo = goodsMapper.getMUInfoByGoodsIdAndIsdefault(goodsInfo.getOldId());
					if (null != goodsMUInfo) {
						if (null != muInfo) {// 修改操作
							muInfo.setGoodsid(goodsInfo.getId());
							if (null != goodsMUInfo.getMeteringunitid()) {
								muInfo.setMeteringunitid(goodsMUInfo.getMeteringunitid());
							}
							if (null != goodsMUInfo.getRate()) {
								if(goodsMUInfo.getRate().compareTo(BigDecimal.ZERO) != 0){
									muInfo.setRate(goodsMUInfo.getRate());
								}else{
									muInfo.setRate(new BigDecimal("9999"));
								}
							}else{
								muInfo.setRate(new BigDecimal("9999"));
							}
							if (StringUtils.isNotEmpty(goodsInfo.getBoxbarcode())) {
								muInfo.setBarcode(goodsInfo.getBoxbarcode());
							}
							mu = goodsMapper.editMeteringUnitInfo(muInfo);
							//修改了主单位或者修改了辅单位,t_storage_summary,t_storage_summary_batch 表里相关字段也调整回来
							if(!beforeGoodsInfo.getMainunit().equals(goodsInfo.getMainunit())||!goodsMUInfo.getMeteringunitid().equals(muInfo.getMeteringunitid())){
								// 主单位名称
								MeteringUnit mainUnit = getBaseGoodsMapper().showMeteringUnitInfo(goodsInfo.getMainunit());
								MeteringUnit meteringUnit=getBaseGoodsMapper().showMeteringUnitInfo(goodsMUInfo.getMeteringunitid());
								goodsMapper.editStorageSummaryMeteringInfo(goodsInfo.getId(),goodsInfo.getMainunit(),mainUnit.getName(),goodsMUInfo.getMeteringunitid(),meteringUnit.getName());
							}
						} else {// 新增操作
							GoodsInfo_MteringUnitInfo muInfo2 = new GoodsInfo_MteringUnitInfo();
							muInfo2.setGoodsid(goodsInfo.getId());
							muInfo2.setIsdefault("1");
							muInfo2.setType("1");
							muInfo2.setMode("1");
							if (null != goodsMUInfo.getMeteringunitid()) {
								muInfo2.setMeteringunitid(goodsMUInfo.getMeteringunitid());
							}
							if (null != goodsMUInfo.getRate()) {
								if(goodsMUInfo.getRate().compareTo(BigDecimal.ZERO) != 0){
									muInfo2.setRate(goodsMUInfo.getRate());
								}else{
									muInfo2.setRate(new BigDecimal("9999"));
								}
							}else{
								muInfo2.setRate(new BigDecimal("9999"));
							}
							if (StringUtils.isNotEmpty(goodsInfo.getBoxbarcode())) {
								muInfo2.setBarcode(goodsInfo.getBoxbarcode());
							}
							List<GoodsInfo_MteringUnitInfo> muList = new ArrayList<GoodsInfo_MteringUnitInfo>();
							muList.add(muInfo2);
							map.put("meteringUnitListMap", muList);
							mu = goodsMapper.addMeteringUnitInfos(map);
                            //修改了主单位或者修改了辅单位,t_storage_summary,t_storage_summary_batch 表里相关字段也调整回来
                            if(!beforeGoodsInfo.getMainunit().equals(goodsInfo.getMainunit())){
                                // 主单位名称
                                MeteringUnit mainUnit = getBaseGoodsMapper().showMeteringUnitInfo(goodsInfo.getMainunit());
                                MeteringUnit meteringUnit=getBaseGoodsMapper().showMeteringUnitInfo(goodsMUInfo.getMeteringunitid());
                                goodsMapper.editStorageSummaryMeteringInfo(goodsInfo.getId(),goodsInfo.getMainunit(),mainUnit.getName(),goodsMUInfo.getMeteringunitid(),meteringUnit.getName());
                            }
						}
					} else {
						if (null != muInfo) {
							// 根据商品编码删除默认计量单位为是的辅计量单位
							mu = goodsMapper.deleteMUInfoByGoodsidAndIsdefault(goodsInfo.getOldId());
						} else {
							mu = 1;
						}
					}
					// 修改对应仓库
					// 根据商品编码、默认辅计量单位为是获取对应仓库详情
					GoodsInfo_StorageInfo storageInfo = goodsMapper.getStorageByGoodsidAndIsdefault(goodsInfo.getOldId());
					if (StringUtils.isNotEmpty(goodsInfo.getStorageid())) {
						if (null != storageInfo) {// 修改操作
							storageInfo.setGoodsid(goodsInfo.getId());
							storageInfo.setStorageid(goodsInfo.getStorageid());
							s = goodsMapper.editStorageInfo(storageInfo);
						} else {// 新增操作
							GoodsInfo_StorageInfo storageInfo2 = new GoodsInfo_StorageInfo();
							storageInfo2.setGoodsid(goodsInfo.getId());
							storageInfo2.setStorageid(goodsInfo.getStorageid());
							storageInfo2.setIsdefault("1");
							List<GoodsInfo_StorageInfo> sList = new ArrayList<GoodsInfo_StorageInfo>();
							sList.add(storageInfo2);
							map.put("storageInfoListMap", sList);
							s = goodsMapper.addStorageInfos(map);
						}
					} else {
						if (null != storageInfo) {// 删除操作
							s = goodsMapper.deleteStorageByGoodsidAndIsdefault(goodsInfo.getOldId());
						} else {
							s = 1;
						}

					}
					// 修改对应分类
					GoodsInfo_WaresClassInfo wcInfo = goodsMapper.getWCByGoodsidAndIsdefault(goodsInfo.getOldId());
					if (StringUtils.isNotEmpty(goodsInfo.getDefaultsort())) {
						if (null != wcInfo) {// 修改操作
							wcInfo.setGoodsid(goodsInfo.getId());
							wcInfo.setWaresclass(goodsInfo.getDefaultsort());
							wc = goodsMapper.editWaresClassInfo(wcInfo);
						} else {// 新增操作
							GoodsInfo_WaresClassInfo wcInfo2 = new GoodsInfo_WaresClassInfo();
							wcInfo2.setGoodsid(goodsInfo.getId());
							wcInfo2.setWaresclass(goodsInfo.getDefaultsort());
							wcInfo2.setIsdefault("1");
							List<GoodsInfo_WaresClassInfo> wcList = new ArrayList<GoodsInfo_WaresClassInfo>();
							wcList.add(wcInfo2);
							map.put("waresClassInfoListMap", wcList);
							wc = goodsMapper.addWaresClassInfos(map);
						}
					} else {
						if (null != wcInfo) {// 删除操作
							wc = goodsMapper.deleteWCByGoodsidAndIsdefault(goodsInfo.getOldId());
						} else {
							wc = 1;
						}
					}
					// 修改对应库位
					GoodsStorageLocation slInfo = goodsMapper.getSLByGoodsidAndIsdefault(goodsInfo.getOldId());
					if (StringUtils.isNotEmpty(goodsInfo.getStoragelocation())) {
						if (null != slInfo) {// 修改操作
							slInfo.setGoodsid(goodsInfo.getId());
							slInfo.setStoragelocationid(goodsInfo.getStoragelocation());
							if (null != goodsInfo.getSlboxnum()) {
								slInfo.setBoxnum(goodsInfo.getSlboxnum());
							}
							sl = goodsMapper.editGoodsStorageLocation(slInfo);
						} else {// 新增操作
							GoodsStorageLocation slInfo2 = new GoodsStorageLocation();
							slInfo2.setGoodsid(goodsInfo.getId());
							slInfo2.setStoragelocationid(goodsInfo.getStoragelocation());
							slInfo2.setIsdefault("1");
							if (null != goodsInfo.getSlboxnum()) {
								slInfo2.setBoxnum(goodsInfo.getSlboxnum());
							}
							List<GoodsStorageLocation> slList = new ArrayList<GoodsStorageLocation>();
							slList.add(slInfo2);
							map.put("SLListMap", slList);
							sl = goodsMapper.addGoodsStorageLocation(map);
						}
					} else {
						if (null != slInfo) {// 删除操作
							sl = goodsMapper.deleteSLByGoodsidAndIsdefault(goodsInfo.getOldId());
						} else {
							sl = 1;
						}
					}
					flag = (p > 0) && (mu > 0) && (s > 0) && (wc > 0) && (sl > 0);
				}
			} else {
				unEditFlag = false;
			}
		} else {
			lockFlag = false;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("unEditFlag", unEditFlag);
		map.put("lockFlag", lockFlag);
		return map;
	}

	@Override
	public boolean editGoodsImageInfo(GoodsInfo goodsInfo) throws Exception {
		return goodsMapper.editGoodsImageInfo(goodsInfo) > 0;
	}

	@Override
	public GoodsInfo_PriceInfo getPriceDataByGoodsidAndCode(String goodsid, String code) throws Exception {
		return goodsMapper.getPriceDataByGoodsidAndCode(goodsid, code);
	}

	@Override
	public Map getTaxPriceByGoodsidAndPriceCode(String goodsid, String pricesort) throws Exception {
		return goodsMapper.getTaxPriceByGoodsidAndPriceCode(goodsid, pricesort);
	}

	@Override
	public List<GoodsInfo> getUpLoadMod(String[] idlist) throws Exception {
		List<GoodsInfo> goodsInfoList = new ArrayList<GoodsInfo>();
		// 对编号进行排序
		Arrays.sort(idlist);

		for (String id : idlist) {
			GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(id);

			// 主单位名称
			MeteringUnit mu = getBaseGoodsMapper().showMeteringUnitInfo(goodsInfo.getMainunit());
			if (null != mu) {
				goodsInfo.setMainunitName(mu.getName());
			}
			// 辅单位名称
			List<GoodsInfo_MteringUnitInfo> me = getBaseGoodsMapper().getMUListByGoodsId(goodsInfo.getId());
			if (me.size() > 0) {
				String auxunitid = me.get(0).getMeteringunitid();
				MeteringUnit a = getBaseGoodsMapper().showMeteringUnitInfo(auxunitid);
				if (null != a) {
					goodsInfo.setAuxunitname(a.getName());
				}
			}
			// 状态名称
			SysCode state = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getState(), "state");
			if (null != state) {
				goodsInfo.setStateName(state.getCodename());
			}
			// 箱装量
			List<GoodsInfo_MteringUnitInfo> mInfo = getMUListByGoodsId(goodsInfo.getId());
			if (mInfo.size() > 0) {
				goodsInfo.setBoxnum(mInfo.get(0).getRate());
			}
			// 商品类型
			SysCode goodstype = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getGoodstype(), "goodstype");
			if (null != goodstype) {
				goodsInfo.setGoodstypeName(goodstype.getCodename());
			}
			// 商品分类
			WaresClass waresClass = getWaresClassInfo(goodsInfo.getDefaultsort());
			if (null != waresClass) {
				goodsInfo.setDefaultsortName(waresClass.getName());
			}
			// 所属部门
			DepartMent departMent = getDepartmentByDeptid(goodsInfo.getDeptid());
			if (null != departMent) {
				goodsInfo.setDeptname(departMent.getName());
			}
			// 默认供应商
			BuySupplier buySupplier = getBaseBuySupplierMapper().getBuySupplier(goodsInfo.getDefaultsupplier());
			if (null != buySupplier) {
				goodsInfo.setDefaultsupplierName(buySupplier.getName());
			}
			// 第二供应商
			BuySupplier buySupplier2 = getBaseBuySupplierMapper().getBuySupplier(goodsInfo.getSecondsupplier());
			if (null != buySupplier2) {
				goodsInfo.setSecondsuppliername(buySupplier2.getName());
			}
			// 默认仓库
			StorageInfo storageInfo = getStorageMapper().showStorageInfo(goodsInfo.getStorageid());
			if (null != storageInfo) {
				goodsInfo.setStorageName(storageInfo.getName());
			}
			// 是否保质期管理名称
			SysCode isshelflife = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getIsshelflife(), "yesorno");
			if (null != isshelflife) {
				goodsInfo.setIsshelflifename(isshelflife.getCodename());
			}
			// 是否批次管理
			SysCode isbatch = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getIsbatch(), "yesorno");
			if (null != isbatch) {
				goodsInfo.setIsbatchname(isbatch.getCodename());
			}
			SysCode isstoragelocation = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getIsstoragelocation(), "yesorno");
			if (null != isstoragelocation) {
				goodsInfo.setIsstoragelocationname(isstoragelocation.getCodename());
			}
			// 保质期单位名称
			if (null != goodsInfo.getShelflifeunit()) {
				if ("1".equals(goodsInfo.getShelflifeunit())) {
					goodsInfo.setShelflifeunitName("天");
				} else if ("2".equals(goodsInfo.getShelflifeunit())) {
					goodsInfo.setShelflifeunitName("周");
				} else if ("3".equals(goodsInfo.getShelflifeunit())) {
					goodsInfo.setShelflifeunitName("月");
				} else if ("4".equals(goodsInfo.getShelflifeunit())) {
					goodsInfo.setShelflifeunitName("年");
				}
			}
			// 保质期描述
			if (null != goodsInfo.getShelflife()) {
				BigDecimal shelflife = goodsInfo.getShelflife().setScale(2, BigDecimal.ROUND_HALF_UP);
				goodsInfo.setShelflifedetail(shelflife.toString() + goodsInfo.getShelflifeunitName());
			}
			// 默认税种
			if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())) {
				TaxType taxType = financeMapper.getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
				if (null != taxType) {
					goodsInfo.setDefaulttaxtypeName(taxType.getName());
				}
			}
			// 品牌
			if (goodsInfo.getBrandName() == null) {
				Brand Brand = goodsMapper.getBrandInfo(goodsInfo.getBrand());
				if (null != Brand) {
					goodsInfo.setBrandName(Brand.getName());
				}
			}
			// 价格套
			List<GoodsInfo_PriceInfo> priceList = goodsMapper.getPriceListByGoodsid(goodsInfo.getId());
			if (priceList.size() > 0) {
				for (GoodsInfo_PriceInfo priceInfo : priceList) {
					if (priceInfo.getCode().equals("1")) {
						goodsInfo.setPrice1(priceInfo.getTaxprice());
                        goodsInfo.setBoxprice1(priceInfo.getBoxprice());
					} else if (priceInfo.getCode().equals("2")) {
						goodsInfo.setPrice2(priceInfo.getTaxprice());
                        goodsInfo.setBoxprice2(priceInfo.getBoxprice());
					} else if (priceInfo.getCode().equals("3")) {
						goodsInfo.setPrice3(priceInfo.getTaxprice());
                        goodsInfo.setBoxprice3(priceInfo.getBoxprice());
					} else if (priceInfo.getCode().equals("4")) {
						goodsInfo.setPrice4(priceInfo.getTaxprice());
                        goodsInfo.setBoxprice4(priceInfo.getBoxprice());
					}
				}
			}
			// 取整
			if (null != goodsInfo.getBoxnum()) {
				int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
				if(decimalScale != 0){
					goodsInfo.setBoxnum(goodsInfo.getBoxnum().setScale(decimalScale, BigDecimal.ROUND_HALF_UP));
				}
			}
			goodsInfoList.add(goodsInfo);
		}
		return goodsInfoList;
	}

	@Override
	public List<GoodsInfo> getGoodsListForMecshop(Date time) throws Exception {

		return goodsMapper.getGoodsListForMecshop(time);
	}

	@Override
	public List<GoodsInfo_PriceInfo> getGoodsPriceListForMecshop(String offset, String rows) {

		return goodsMapper.getGoodsPriceListForMecshop(Integer.parseInt(offset), Integer.parseInt(rows));
	}

	@Override
	public List<MeteringUnit> getMeteringUnitListForMecshop() {

		return goodsMapper.getMeteringUnitListForMecshop();
	}

	@Override
	public List<Brand> getBrandListForMecshop() {

		return goodsMapper.getBrandListForMecshop();
	}

	@Override
	public List<WaresClass> getWaresClassListForMecshop() {

		return goodsMapper.getWaresClassListForMecshop();
	}

    /**
     * 获取商品的成本变更记录
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List getGoodesCostpriceChangeList(String id) throws Exception {
        return goodsMapper.getGoodesCostpriceChangeList(id);
    }

    @Override
	public List<Map> getGoodsListForHTJS(PageMap pageMap) throws Exception{
    	Map condition=(Map)pageMap.getCondition();
		String bmbbh="12";
		if(condition.containsKey("bmbbh")) {
			bmbbh= (String) condition.get("bmbbh");
		}
		if(null==bmbbh || "".equals(bmbbh.trim())){
			bmbbh="12.0";
		}
		bmbbh=CommonUtils.StringFilter(bmbbh);
		condition.put("bmbbh",bmbbh.trim());
    	List<Map> list= goodsMapper.getGoodsListForHTJS(pageMap);
    	for(Map itemMap:list){
    		if(!itemMap.containsKey("goodsid")){
    			continue;
			}
    		String goodsid=(String)itemMap.get("goodsid");
    		if(null==goodsid || "".equals(goodsid.trim())){
    			continue;
			}
			GoodsInfo goodsInfo=showGoodsInfo(goodsid);
    		if(null!=goodsInfo) {
				itemMap.put("jldw", goodsInfo.getMainunitName());
			}
		}
    	return list;
	}
	@Override
	public Map importAndUpdateJsgoodsForHTKP(List<Map<String,Object>> dataList) throws Exception{
    	Map resultMap=new HashMap();
    	if(null==dataList || dataList.size()==0){
    		resultMap.put("flag",false);
			resultMap.put("msg","导入的数据为空");
			return resultMap;
		}
		List<GoodsInfo> updateGoodsList=new ArrayList<GoodsInfo>();
    	List<Map<String,Object>> errorList=new ArrayList<Map<String, Object>>();
    	Date nowDate=new Date();
    	SysUser sysUser=getSysUser();
    	StringBuilder upLogsb=new StringBuilder();
    	Map jsTypeidMap =new HashMap();
		for(Map itemData : dataList){
			int ierror=1;
			StringBuilder errorsb=new StringBuilder();
			String jsgoodsid=(String)itemData.get("jsgoodsid");
			String goodsid=(String)itemData.get("goodsid");
			String ssflbm=(String) itemData.get("ssflbm");

			if(itemData.containsKey("issortline")) {
				String issortline = (String) itemData.get("issortline");
				if ((null == issortline || "1".equals(issortline.trim()) || "true".equals(issortline.toLowerCase().trim()))
						&& (null == goodsid || "".equals(goodsid.trim()))) {
					continue;
				}
			}else {
				String dj = (String) itemData.get("dj");
				if ((null == dj || "".equals(dj.trim())) && (null == goodsid || "".equals(goodsid.trim()))) {
					continue;
				}
			}
			if(null==goodsid || "".equals(goodsid.trim())){
				errorsb.append(ierror);
				errorsb.append(")");
				errorsb.append("商品编码不能为空");
				errorsb.append("。 ");
				ierror=ierror+1;
				continue;
			}
			if(null==ssflbm || "".equals(ssflbm.trim())){
				errorsb.append(ierror);
				errorsb.append(")");
				errorsb.append("税收分类编码不能为空");
				errorsb.append("。 ");
				ierror=ierror+1;
			}else {
				if (!jsTypeidMap.containsKey(ssflbm.trim()) || jsTaxTypeCodeMapper.isUsedJsTaxTypeCodeById(ssflbm.trim()) == 0) {
					errorsb.append(ierror);
					errorsb.append(")");
					errorsb.append("导入的税收分类编码在金税分类档案里不存在，请查看该编码是否存在");
					errorsb.append("。 ");
					ierror = ierror + 1;
				}else{
					if(!jsTypeidMap.containsKey(ssflbm.trim())) {
						jsTypeidMap.put(ssflbm.trim(), ssflbm.trim());
					}
				}
			}

			if(ierror>1){
				itemData.put("errormessage",errorsb.toString());
				errorList.add(itemData);
				continue;
			}

    		GoodsInfo upGoodsInfo=new GoodsInfo();
			upGoodsInfo.setId(goodsid);
			if(null!=jsgoodsid && !"".equals(jsgoodsid)){
				upGoodsInfo.setJsgoodsid(jsgoodsid);
			}
			upGoodsInfo.setJstaxsortid(ssflbm);
			upGoodsInfo.setJsgoodsmodifytime(nowDate);
			upGoodsInfo.setJsgoodsmodifyuserid(sysUser.getUserid());
			upGoodsInfo.setJsgoodsmodifyusername(sysUser.getName());
			updateGoodsList.add(upGoodsInfo);
			if(upLogsb.length()>0){
				upLogsb.append("，");
			}
			upLogsb.append("【商品编号：");
			upLogsb.append(goodsid);
			upLogsb.append(" 更新金税税收分类字段：");
			upLogsb.append(ssflbm);
			upLogsb.append("】");
		}
		if(updateGoodsList.size()==0){
			resultMap.put("flag",false);
			resultMap.put("msg","需要更新的数据为空");
			return resultMap;
		}
    	//批量更新条数
		int updateDBCount=300;
		int listLen = updateGoodsList.size();
		boolean canInsert = listLen > 0;
		int ipage = 0;
		//数据小于等于300条时，直接插入
		boolean isupdate=false;
		if(listLen<=updateDBCount) {
			isupdate=goodsMapper.updateJsgoodsForHTKPBatch(updateGoodsList)>0;
		}else{
			//数据大于300条时，就进行分页批量插入
			while (canInsert) {
				int row = (ipage + 1) * updateDBCount;
				if (listLen <= updateDBCount) {
					row = listLen;
				}
				if (row >= listLen) {
					row = listLen;
					canInsert = false;
				}
				List<GoodsInfo> tmpList = updateGoodsList.subList(ipage * updateDBCount, row);
				isupdate=goodsMapper.updateJsgoodsForHTKPBatch(tmpList)>0 || isupdate;
				ipage = ipage + 1;
			}
		}
		if(isupdate) {
			resultMap.put("flag", true);
			resultMap.put("msg", "金税商品批量更新成功");
		}else{
			resultMap.put("flag",false);
			resultMap.put("msg","金税商品批量更新未成功");
		}
		resultMap.put("errorDataList",errorList);
		resultMap.put("updateLogs",upLogsb.toString());
		return resultMap;
	}


	@Override
	public List<Map> getUpdatedGoodsList(Map param) throws Exception {

		return goodsMapper.getUpdatedGoodsList(param);
	}

	@Override
	public Map editGoodsItemno(String goodsid,String itemno) throws Exception{
		Map resultMap = new HashMap();
		String msg="";
    	GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(goodsid);
		if(null != goodsInfo){
			boolean flag = goodsMapper.editGoodsItemno(goodsid,itemno)>0;
			if(flag){
				msg="修改成功！";
				resultMap.put("flag",true);
			}else{
				msg="修改出错！";
				resultMap.put("flag",false);
			}
		}else{
			msg="商品不存在！";
			resultMap.put("flag",false);
		}
		resultMap.put("msg",msg);
		return  resultMap;
	}
	@Override
	public Map updateGoodsInfoForJS(GoodsInfo goodsInfo) throws Exception{
		Map resultMap=new HashMap();
		if(null == goodsInfo){
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关商品信息");
			return resultMap;
		}
		if(StringUtils.isEmpty(goodsInfo.getId())){
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关商品编号");
			return resultMap;
		}
		GoodsInfo oldGoodsInfo = goodsMapper.getGoodsInfo(goodsInfo.getId());
		if(null==oldGoodsInfo){
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关商品信息");
			return resultMap;
		}
		boolean flag=goodsMapper.updateGoodsInfoForJS(goodsInfo)>0;
		resultMap.put("flag",flag);
		return resultMap;
	}
	/**
	 * 获取商品成本变更记录数据
	 * @param pageMap
	 * @return com.hd.agent.common.util.PageData
	 * @throws
	 * @author luoqiang
	 * @date Nov 29, 2017
	 */
	public PageData getGoodesSimplifyViewCostpriceChageData(PageMap pageMap){
		List list=goodsMapper.getGoodesSimplifyViewCostpriceChageList(pageMap);
		int count=goodsMapper.getGoodesSimplifyViewCostpriceChageCount(pageMap);
		PageData pageData=new PageData(count,list,pageMap);
		return pageData;
	}
}

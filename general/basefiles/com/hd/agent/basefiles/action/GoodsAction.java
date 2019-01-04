/**
 * @(#)GoodsAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 17, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.basefiles.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.*;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.model.Tree;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.service.IExcelService;
import com.hd.agent.common.util.*;
import com.hd.agent.common.util.ftl.JSStringFilterMethodModel;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysCodeService;
import com.hd.agent.system.service.ITaskScheduleService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import net.sf.json.JSONArray;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 商品档案action
 * 
 * @author panxiaoxiao
 */
public class GoodsAction extends FilesLevelAction {

	protected static final String TO_PHONE = "phone";
    protected static final int secondSupplierNum = 10;

	private IGoodsService goodsService;
	private List<GoodsInfo_PriceInfo> priceInfoList;
	private List<GoodsInfo_MteringUnitInfo> meteringUnitInfoList;
	private List<GoodsInfo_StorageInfo> storageInfoList;
	private List<GoodsInfo_WaresClassInfo> waresClassList;
	private List<GoodsStorageLocation> SLList;
	private IStorageService storageService;
	private ISysCodeService sysCodeService;
	private IFinanceService financeService;
	private IPersonnelService personnelService;
	private IBuyService buyService;
	private ITaskScheduleService taskScheduleService;
	private IExcelService excelService;
	private MeteringUnit meteringUnit;
	private Brand brand;
	private WaresClass waresClass;
	private GoodsInfo goodsInfo;
	private BuySupplier buySupplier;
	private TaxType taxType;
	private GoodsInfo_MteringUnitInfo goodsMUInfo;
	private IAttachFileService attachFileService;
	

	public IAttachFileService getAttachFileService() {
		return attachFileService;
	}

	public void setAttachFileService(IAttachFileService attachFileService) {
		this.attachFileService = attachFileService;
	}

	public IExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(IExcelService excelService) {
		this.excelService = excelService;
	}

	public ITaskScheduleService getTaskScheduleService() {
		return taskScheduleService;
	}

	public void setTaskScheduleService(ITaskScheduleService taskScheduleService) {
		this.taskScheduleService = taskScheduleService;
	}

	public IGoodsService getGoodsService() {
		return goodsService;
	}

	public void setGoodsService(IGoodsService goodsService) {
		this.goodsService = goodsService;
	}

	public List<GoodsInfo_PriceInfo> getPriceInfoList() {
		return priceInfoList;
	}

	public void setPriceInfoList(List<GoodsInfo_PriceInfo> priceInfoList) {
		this.priceInfoList = priceInfoList;
	}

	public List<GoodsInfo_MteringUnitInfo> getMeteringUnitInfoList() {
		return meteringUnitInfoList;
	}

	public void setMeteringUnitInfoList(List<GoodsInfo_MteringUnitInfo> meteringUnitInfoList) {
		this.meteringUnitInfoList = meteringUnitInfoList;
	}

	public List<GoodsInfo_StorageInfo> getStorageInfoList() {
		return storageInfoList;
	}

	public void setStorageInfoList(List<GoodsInfo_StorageInfo> storageInfoList) {
		this.storageInfoList = storageInfoList;
	}

	public List<GoodsInfo_WaresClassInfo> getWaresClassList() {
		return waresClassList;
	}

	public void setWaresClassList(List<GoodsInfo_WaresClassInfo> waresClassList) {
		this.waresClassList = waresClassList;
	}

	public IStorageService getStorageService() {
		return storageService;
	}

	public void setStorageService(IStorageService storageService) {
		this.storageService = storageService;
	}

	public ISysCodeService getSysCodeService() {
		return sysCodeService;
	}

	public void setSysCodeService(ISysCodeService sysCodeService) {
		this.sysCodeService = sysCodeService;
	}

	public IFinanceService getFinanceService() {
		return financeService;
	}

	public void setFinanceService(IFinanceService financeService) {
		this.financeService = financeService;
	}

	public MeteringUnit getMeteringUnit() {
		return meteringUnit;
	}

	public void setMeteringUnit(MeteringUnit meteringUnit) {
		this.meteringUnit = meteringUnit;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public WaresClass getWaresClass() {
		return waresClass;
	}

	public void setWaresClass(WaresClass waresClass) {
		this.waresClass = waresClass;
	}

	public GoodsInfo getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public IPersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(IPersonnelService personnelService) {
		this.personnelService = personnelService;
	}

	public IBuyService getBuyService() {
		return buyService;
	}

	public void setBuyService(IBuyService buyService) {
		this.buyService = buyService;
	}

	public BuySupplier getBuySupplier() {
		return buySupplier;
	}

	public void setBuySupplier(BuySupplier buySupplier) {
		this.buySupplier = buySupplier;
	}

	public TaxType getTaxType() {
		return taxType;
	}

	public void setTaxType(TaxType taxType) {
		this.taxType = taxType;
	}

	public List<GoodsStorageLocation> getSLList() {
		return SLList;
	}

	public void setSLList(List<GoodsStorageLocation> list) {
		SLList = list;
	}

	public GoodsInfo_MteringUnitInfo getGoodsMUInfo() {
		return goodsMUInfo;
	}

	public void setGoodsMUInfo(GoodsInfo_MteringUnitInfo goodsMUInfo) {
		this.goodsMUInfo = goodsMUInfo;
	}

	/*----------------------------------------计量单位-----------------------------------------------*/

	/**
	 * 显示计量单位首页
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public String showMeteringUnitPage() throws Exception {
		return "success";
	}

	/**
	 * 显示计量单位列表
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-12
	 */
	public String getMeteringUnitList() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = goodsService.getMeteringUnitList(pageMap);
		List<MeteringUnit> meteringUnitlist = pageData.getList();
		for (MeteringUnit meteringUnit : meteringUnitlist) {
			if (StringUtils.isNotEmpty(meteringUnit.getState())) { // 状态
				SysCode sysCode = super.getBaseSysCodeService().showSysCodeInfo(meteringUnit.getState(), "state");
				if (sysCode != null) {
					meteringUnit.setStateName(sysCode.getCodename());
				}
			}
			if (StringUtils.isNotEmpty(meteringUnit.getAdduserid())) { // 建档人
				SysUser sysUser = super.getBaseSysUserService().showSysUserInfo(meteringUnit.getAdduserid());
				if (sysUser != null) {
					meteringUnit.setAdduserid(sysUser.getName());
				}
			}
			if (StringUtils.isNotEmpty(meteringUnit.getAdddeptid())) { // 建档部门
				DepartMent dept = super.getBaseDepartMentService().showDepartMentInfo(meteringUnit.getAdddeptid());
				if (dept != null) {
					meteringUnit.setAdddeptid(dept.getName());
				}
			}
			if (StringUtils.isNotEmpty(meteringUnit.getModifyuserid())) { // 最后修改人
				SysUser sysUser = super.getBaseSysUserService().showSysUserInfo(meteringUnit.getModifyuserid());
				if (sysUser != null) {
					meteringUnit.setModifyuserid(sysUser.getName());
				}
			}
			if (StringUtils.isNotEmpty(meteringUnit.getOpenuserid())) { // 启用人
				SysUser sysUser = super.getBaseSysUserService().showSysUserInfo(meteringUnit.getOpenuserid());
				if (sysUser != null) {
					meteringUnit.setOpenuserid(sysUser.getName());
				}
			}
			if (StringUtils.isNotEmpty(meteringUnit.getCloseuserid())) { // 禁用人
				SysUser sysUser = super.getBaseSysUserService().showSysUserInfo(meteringUnit.getCloseuserid());
				if (sysUser != null) {
					meteringUnit.setCloseuserid(sysUser.getName());
				}
			}
		}
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 显示计量单位新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public String showMeteringUnitAddPage() throws Exception {
		return "success";
	}

	/**
	 * 显示计量单位编辑页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public String showMeteringUnitEditPage() throws Exception {
		String id = request.getParameter("id");
		MeteringUnit meteringUnitInfo = goodsService.showMeteringUnitInfo(id);
		if (meteringUnitInfo != null) {
			// 显示修改页面时，给数据加锁
			boolean flag = lockData("t_base_goods_meteringunit", id);// true：可以操作,false:不可以操作
			if (flag) {
				// 往页面传值
				request.setAttribute("lockFlag", "1");// 解锁
			} else {
				request.setAttribute("lockFlag", "0");// 加锁
			}
			request.setAttribute("oldId", id);
			request.setAttribute("meteringUnit", meteringUnitInfo);
		}
		return "success";
	}

	/**
	 * 显示计量单位复制页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public String showMeteringUnitCopyPage() throws Exception {
		String id = request.getParameter("id");
		MeteringUnit meteringUnitInfo = goodsService.showMeteringUnitInfo(id);
		if (meteringUnitInfo != null) {
			request.setAttribute("meteringUnit", meteringUnitInfo);
		}
		return "success";
	}

	/**
	 * 新增暂存计量单位
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	@UserOperateLog(key = "MeteringUnit", type = 2, value = "")
	public String addMeteringUnitHold() throws Exception {
		boolean flag = goodsService.addMeteringUnit(meteringUnit);
		// 添加日志内容
		addLog("新增计量单位 编号:" + meteringUnit.getId(), flag);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 新增保存计量单位
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	@UserOperateLog(key = "MeteringUnit", type = 2, value = "")
	public String addMeteringUnitSave() throws Exception {
		SysUser sysUser = getSysUser();
		meteringUnit.setAdduserid(sysUser.getUserid());
		meteringUnit.setAdddeptid(sysUser.getDepartmentid());
		boolean flag = goodsService.addMeteringUnit(meteringUnit);
		// 添加日志内容
		addLog("新增记录单位 编号:" + meteringUnit.getId(), flag);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 判断id是否重复 true 重复 false 不重复
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public String isRepeatMUId() throws Exception {
		String id = request.getParameter("id");
		boolean flag = goodsService.isRepeatMUID(id);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 判断name是否重复
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public String isRepeatMUName() throws Exception {
		String name = request.getParameter("name");
		boolean flag = goodsService.isRepeatMUName(name);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 显示计量单位信息
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	public String showMeteringUnitInfoPage() throws Exception {
		String id = request.getParameter("id");
		// 获取t_demo_user表的字段编辑权限
		Map colMap = getAccessColumn("t_base_goods_meteringunit");
		MeteringUnit meteringUnitInfo = goodsService.showMeteringUnitInfo(id);
		if (meteringUnitInfo != null) {
			request.setAttribute("showMap", colMap);
			request.setAttribute("meteringUnit", meteringUnitInfo);
		}
		return "success";
	}

	/**
	 * 修改保存计量单位
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	@UserOperateLog(key = "MeteringUnit", type = 3, value = "")
	public String editMeteringUnitSave() throws Exception {
		SysUser sysUser = getSysUser();
		meteringUnit.setModifyuserid(sysUser.getUserid());
		boolean flag = goodsService.editMeteringUnit(meteringUnit);
		addLog("修改计量单位 编号:" + meteringUnit.getOldId(), flag);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 修改暂存计量单位
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	@UserOperateLog(key = "MeteringUnit", type = 3, value = "")
	public String editMeteringUnitHold() throws Exception {
		boolean flag = goodsService.editMeteringUnit(meteringUnit);
		addLog("修改计量单位 编号:" + meteringUnit.getOldId(), flag);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 删除计量单位，删除前检验是否被引用(加锁)
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	@UserOperateLog(key = "MeteringUnit", type = 4, value = "")
	public String deleteMeteringUnit() throws Exception {
		String id = request.getParameter("id");
		boolean retFlag = false;
		String lockFlag = "1";// 解锁
		Map retMap = new HashMap();
		// 删除部门时，判断是否网络互斥,返回true已被加锁，不可以删除，false,未被加锁，可以删除
		if (!isLock("t_base_goods_meteringunit", id)) {
			if (canTableDataDelete("t_base_goods_meteringunit", id)) {// 判断是否被引用
																		// true可以删除，false不可以删除
				boolean flag = goodsService.deleteMeteringUnit(id);
				if (flag) {
					retFlag = true;
				}
			} else {
				retMap.put("Mes", "该记录单位被引用,无法删除,");
			}
		} else {
			lockFlag = "0";// 加锁
			retMap.put("Mes", "网络互斥,无法删除,");
		}
		retMap.put("retFlag", retFlag);
		retMap.put("lockFlag", lockFlag);
		// 添加日志内容
		addLog("删除计量单位 编号:" + id + "部门类", retFlag);
		addJSONObject(retMap);
		return "success";
	}

	/**
	 * 启用计量单位
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	@UserOperateLog(key = "MeteringUnit", type = 0, value = "")
	public String enableMeteringUnit() throws Exception {
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		boolean flag = goodsService.enableMeteringUnit(id, sysUser.getUserid());
		addLog("启用计量单位 编号:" + id + "", flag);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 禁用计量单位，判断是否被引用
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-11
	 */
	@UserOperateLog(key = "MeteringUnit", type = 0, value = "")
	public String disableMeteringUnit() throws Exception {
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		// 判断数据是否被加锁,True已被加锁。Fasle未加锁。
		boolean flag = false;
		Map retMap = new HashMap();
		if (!isLock("t_base_goods_meteringunit", id)) {
			boolean retFlag = goodsService.disableMeteringUnit(id, sysUser.getUserid());
			if (retFlag) {
				flag = true;
			} else {
				retMap.put("Mes", "");
			}
		} else {
			retMap.put("Mes", "计量单位被加锁,暂不允许禁用,");
		}
		retMap.put("flag", flag);
		addJSONObject(retMap);
		addLog("禁用计量单位 编号:" + id + "", flag);
		return "success";
	}

	/**
	 * 显示导入窗口页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-3-20
	 */
	public String importExcelPage() throws Exception {
		return "success";
	}

	/**
	 * 获取计量单位列表Combobox
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date May 16, 2013
	 */
	public String getMUListForCombobox() throws Exception {
		List list = goodsService.getMUListForCombobox();
		addJSONArray(list);
		return SUCCESS;
	}

	/*----------------------------------------商品品牌-----------------------------------------------*/

	/**
	 * 显示商品品牌页面（主）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-16
	 */
	public String showBrandPage() throws Exception {
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		return "success";
	}

	/**
	 * 显示商品品牌新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-16
	 */
	public String showBrandAddPage() throws Exception {
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		return "success";
	}

	/**
	 * 显示商品品牌详情页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-16
	 */
	public String showBrandInfoPage() throws Exception {
		String id = request.getParameter("id");
		Brand brandInfo = goodsService.getBrandInfo(id);
		if (brandInfo != null) {
			request.setAttribute("brand", brandInfo);
		}
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		return "success";
	}

	/**
	 * 显示商品品牌修改页面，需要修改的数据加锁
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-16
	 */
	public String showBrandEditPage() throws Exception {
		String id = request.getParameter("id");
		// 显示修改页面时，给数据加锁
		boolean flag = lockData("t_base_goods_brand", id);
		if (flag) {
			request.setAttribute("lockFlag", "1");
			Brand brandInfo = goodsService.getBrandInfo(id);
			if (brandInfo != null) {
				request.setAttribute("brand", brandInfo);
				request.setAttribute("editFlag", canTableDataDelete("t_base_goods_brand", id));
			}
		} else {
			request.setAttribute("lockFlag", "0");
		}
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		return "success";
	}

	/**
	 * 显示商品品牌复制页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-16
	 */
	public String showBrandCopyPage() throws Exception {
		String id = request.getParameter("id");
		Brand brandInfo = goodsService.getBrandInfo(id);
		if (brandInfo != null) {
			request.setAttribute("brand", brandInfo);
		}

		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		return "success";
	}

	/**
	 * 获取商品品牌列表分页
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-16
	 */
	public String getBrandListPage() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = goodsService.getBrandListPage(pageMap);
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 检验编码是否重复，true 重复，false 不重复
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-16
	 */
	public String isRepeatBrandById() throws Exception {
		String id = request.getParameter("id");
		boolean flag = goodsService.isRepeatBrandById(id);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 检验名称是否重复，true 重复，false 不重复
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-16
	 */
	public String isRepeatBrandName() throws Exception {
		String name = request.getParameter("name");
		boolean flag = goodsService.isRepeatBrandName(name);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 新增商品品牌
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-16
	 */
	@UserOperateLog(key = "brand", type = 2, value = "")
	public String addBrand() throws Exception {
		String type = request.getParameter("type");
		if ("save".equals(type)) {
			brand.setState("2");
		} else {
			brand.setState("3");
		}
		boolean flag = goodsService.addBrand(brand);
		addJSONObject("flag", flag);
		// 添加日志内容
		addLog("新增商品品牌 编号:" + brand.getId(), flag);
		return "success";
	}

	/**
	 * 修改商品品牌
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-16
	 */
	@UserOperateLog(key = "Brand", type = 3, value = "")
	public String editBrand() throws Exception {
		String type = request.getParameter("type");
		if (!"1".equals(brand.getState())) {
			if ("save".equals(type)) {
				brand.setState("2");
			} else {
				brand.setState("3");
			}
		}
		SysUser sysUser = getSysUser();
		brand.setModifyuserid(sysUser.getUserid());
		brand.setModifyusername(sysUser.getName());
		boolean flag = goodsService.editBrand(brand);
		addJSONObject("flag", flag);
		addLog("修改商品品牌 编号:" + brand.getId(), flag);
		return "success";
	}

	/**
	 * 删除商品品牌，判断是否允许删除
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-16
	 */
	@UserOperateLog(key = "Brand", type = 4, value = "")
	public String deleteBrand() throws Exception {
		String strid = request.getParameter("strid");
		int sucNum = 0, failNum = 0, userNum = 0, lockNum = 0;
		String retids = null;
		Map map = new HashMap();
		if (StringUtils.isNotEmpty(strid)) {
			String[] idArr = strid.split(",");
			for (int i = 0; i < idArr.length; i++) {
				// 判断数据是否被加锁,True已被加锁。Fasle未加锁。（网络互斥）
				if (!isLock("t_base_goods_brand", idArr[i])) {
					// 判断是否被引用
					if (!canTableDataDelete("t_base_goods_brand", idArr[i])) {// true可以操作，false不可以操作
						userNum++;
					} else {
						if (goodsService.deleteBrand(idArr[i])) {
							sucNum++;
							if (StringUtils.isNotEmpty(retids)) {
								retids += "," + idArr[i];
							} else {
								retids = idArr[i];
							}
						} else {
							failNum++;
						}
					}
				} else {
					lockNum++;
				}
			}
		}
		if (sucNum > 0) {
			addLog("删除商品品牌 编号:" + retids, true);
		}
		map.put("sucNum", sucNum);
		map.put("failNum", failNum);
		map.put("userNum", userNum);
		map.put("lockNum", lockNum);
		addJSONObject(map);
		return "success";
	}

	/**
	 * 启用商品品牌
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-17
	 */
	@UserOperateLog(key = "Brand", type = 0, value = "")
	public String enableBrand() throws Exception {
		String strid = request.getParameter("strid");
		int ivdNum = 0, sucNum = 0, failNum = 0, userNum = 0;
		String retids = null;
		SysUser sysUser = getSysUser();
		Map paramMap = new HashMap();
		paramMap.put("openuserid", sysUser.getUserid());
		paramMap.put("openusername", sysUser.getName());
		if (StringUtils.isNotEmpty(strid)) {
			String[] idArr = strid.split(",");
			for (int i = 0; i < idArr.length; i++) {
				Brand brand = goodsService.getBrandInfo(idArr[i]);
				if (null != brand) {
					if (!"0".equals(brand.getState()) && !"2".equals(brand.getState())) {// 不为禁用状态和保存状态
						ivdNum++;
					} else {
						paramMap.put("id", idArr[i]);
						boolean flag = goodsService.enableBrand(paramMap);
						if (flag) {
							sucNum++;
							if (StringUtils.isNotEmpty(retids)) {
								retids += "," + idArr[i];
							} else {
								retids = idArr[i];
							}
						} else {
							failNum++;
						}
					}
				}
			}
		}
		if (sucNum > 0) {
			addLog("启用商品品牌 编号:" + retids, true);
		}
		Map map = new HashMap();
		map.put("ivdNum", ivdNum);
		map.put("sucNum", sucNum);
		map.put("failNum", failNum);
		map.put("userNum", userNum);
		addJSONObject(map);
		return "success";
	}

	/**
	 * 禁用商品品牌
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-17
	 */
	@UserOperateLog(key = "Brand", type = 0, value = "")
	public String disableBrand() throws Exception {
		String strid = request.getParameter("strid");
		int ivdNum = 0, sucNum = 0, failNum = 0;
		String retids = null;
		SysUser sysUser = getSysUser();
		Map paramMap = new HashMap();
		paramMap.put("closeuserid", sysUser.getUserid());
		paramMap.put("closeusername", sysUser.getName());
		if (StringUtils.isNotEmpty(strid)) {
			String[] idArr = strid.split(",");
			for (int i = 0; i < idArr.length; i++) {
				Brand brand = goodsService.getBrandInfo(idArr[i]);
				if (null != brand) {
					if (!"1".equals(brand.getState())) {// 不为启用状态
						ivdNum++;
					}// 判断是否被引用，是，则因被引用无法禁用记录
					else {
						paramMap.put("id", idArr[i]);
						boolean flag = goodsService.disableBrand(paramMap);
						if (flag) {
							sucNum++;
							if (StringUtils.isNotEmpty(retids)) {
								retids += "," + idArr[i];
							} else {
								retids = idArr[i];
							}
						} else {
							failNum++;
						}
					}
				}
			}
		}
		if (sucNum > 0) {
			addLog("禁用商品品牌 编号:" + retids, true);
		}
		Map map = new HashMap();
		map.put("ivdNum", ivdNum);
		map.put("sucNum", sucNum);
		map.put("failNum", failNum);
		addJSONObject(map);
		return "success";
	}

	/**
	 * 获取商品品牌
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jul 20, 2013
	 */
	public String getBrandListForCombobox() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = goodsService.getBrandListForCombobox(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 根据所属部门获取品牌列表数据
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Oct 28, 2013
	 */
	public String getBrandListByDeptid() throws Exception {
		String deptid = request.getParameter("deptid");
		String personid = request.getParameter("personid");
		String brandidstr = "";
		List<PersonnelBrand> pblist = new ArrayList<PersonnelBrand>();
		List<Brand> list = goodsService.getBrandListWithParentByDeptid(deptid);// 获取包括父级的品牌列表
		if (list.size() != 0) {
			for (Brand brand : list) {
				PersonnelBrand personnelBrand = new PersonnelBrand();
				personnelBrand.setBrandid(brand.getId());
				personnelBrand.setBrandname(brand.getName());
				if (StringUtils.isNotEmpty(personid)) {
					personnelBrand.setPersonid(personid);
				}
				pblist.add(personnelBrand);
				brandidstr += brand.getId() + ",";
			}
		}
		Map map = new HashMap();
		map.put("list", pblist);
		map.put("brandidstr", brandidstr);
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 获取属部门获取品牌列表数据
	 * 
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2014-4-17
	 */
	public String getBrandListDataByDeptid() throws Exception {
		String deptid = request.getParameter("deptid");
		List<Brand> list = goodsService.getBrandListByDeptid(deptid);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 获取属上级部门获取品牌列表数据
	 * 
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2014-4-17
	 */
	public String getBrandListWithParentByDeptid() throws Exception {
		String deptid = request.getParameter("deptid");
		List<Brand> list = goodsService.getBrandListWithParentByDeptid(deptid);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 获取启用状态的品牌列表
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Oct 12, 2016
	 */
	public String getBrandEnableList() throws Exception{
		Map paramMap=new HashMap();
		paramMap.put("state","1");
		List<Brand> list=goodsService.getBrandListByMap(paramMap);
		addJSONArray(list);
		return SUCCESS;
	}


	/**
	 * 显示品牌档案金税相关信息页面
	 *
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2017-07-14
	 */
	public String showBrandInfoForJSEditPage() throws Exception {
		String id = request.getParameter("id");
		Brand brandInfo=goodsService.getBrandInfo(id);
		request.setAttribute("brandInfo", brandInfo);
		return SUCCESS;
	}

	/**
	 * 更新品牌档案金税相关信息
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Jul 14, 2017
	 */
	@UserOperateLog(key = "Brand", type = 0, value = "")
	public String updateBrandInfoForJS() throws Exception{
		Map resultMap=new HashMap();

		String id=request.getParameter("id");
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关品牌编号");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		String jsclusterid=request.getParameter("jsclusterid");
		Brand brandInfo=new Brand();
		brandInfo.setId(id);
		brandInfo.setJsclusterid(jsclusterid);
		resultMap=goodsService.updateBrandInfoForJS(brandInfo);
		Boolean flag=false;
		if(resultMap.containsKey("flag")){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		addLog("修改品牌“"+id+"”档案金税相关信息",flag);
		addJSONObject(resultMap);
		return SUCCESS;
	}

	/*----------------------------------------商品分类-----------------------------------------------*/

	/**
	 * 显示商品分类页面(主)
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-12
	 */
	public String showWaresClassPage() throws Exception {
		return "success";
	}

	/**
	 * 显示商品分类列表页面（主）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	public String showWaresClassListPage() throws Exception {
		// 获取档案编码级次定义
		String msg = getTreeLevelLens("t_base_goods_waresclass");
		if (StringUtils.isNotEmpty(msg)) {
			request.setAttribute("msg", msg);
		}
		return "success";
	}

	/**
	 * 显示商品分类详情页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-12
	 */
	public String showWaresClassInfoPage() throws Exception {
		String id = request.getParameter("id");
		WaresClass waresClassInfo = goodsService.getWaresClassInfo(id);
		request.setAttribute("waresClass", waresClassInfo);
		return "success";
	}

	/**
	 * 显示商品分类添加页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-12
	 */
	public String showWaresClassAddPage() throws Exception {
		String id = request.getParameter("id");// 当为空时添加的商品分类为一级目录，否则该编号为添加商品分类的父级
		WaresClass waresClassInfo = goodsService.getWaresClassInfo(id);
		int idLen = 0;
		if (waresClassInfo != null && StringUtils.isNotEmpty(waresClassInfo.getId())) {
			idLen = waresClassInfo.getId().length();
		}
		// 根据选中的商品编码获取下级本级编码长度
		int nextLen = getBaseTreeFilesNext("t_base_goods_waresclass", idLen);
		request.setAttribute("nextLen", nextLen);
		request.setAttribute("waresClass", waresClassInfo);
		return "success";
	}

	/**
	 * 显示商品分类修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-12
	 */
	public String showWaresClassEditPage() throws Exception {
		String id = request.getParameter("id");
		String pname = "";
		// 显示修改页面时，给数据加锁
		boolean flag = lockData("t_base_goods_waresclass", id);
		if (flag) {
			int idLen = 0;
			WaresClass waresClassInfo = goodsService.getWaresClassInfo(id);
			if (waresClassInfo != null && StringUtils.isNotEmpty(waresClassInfo.getPid())) {
				idLen = waresClassInfo.getPid().toString().length();
				WaresClass pWCInfo = goodsService.getWaresClassInfo(waresClassInfo.getPid());
				if (null != pWCInfo) {
					pname = pWCInfo.getName();
				}
			}
			// 根据选中的商品编码获取下级本级编码长度
			int nextLen = getBaseTreeFilesNext("t_base_goods_waresclass", idLen);
			request.setAttribute("waresClass", waresClassInfo);
			request.setAttribute("oldId", waresClassInfo.getId());
			request.setAttribute("nextLen", nextLen);
			request.setAttribute("pId", id);
			request.setAttribute("lockFlag", "1");
			request.setAttribute("pname", pname);
			request.setAttribute("editFlag", canTableDataDelete("t_base_goods_waresclass", id));
		} else {
			request.setAttribute("lockFlag", "0");
		}
		return "success";
	}

	/**
	 * 显示商品分类复制页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-12
	 */
	public String showWaresClassCopyPage() throws Exception {
		String id = request.getParameter("id");
		String pname = "";
		int idLen = 0;
		WaresClass waresClassInfo = goodsService.getWaresClassInfo(id);
		if (waresClassInfo != null && StringUtils.isNotEmpty(waresClassInfo.getPid())) {
			idLen = waresClassInfo.getPid().toString().length();
			WaresClass pWCInfo = goodsService.getWaresClassInfo(waresClassInfo.getPid());
			if (null != pWCInfo) {
				pname = pWCInfo.getName();
			}
			request.setAttribute("pname", pname);
			// 根据选中的商品编码获取下级本级编码长度
			int nextLen = getBaseTreeFilesNext("t_base_goods_waresclass", idLen);
			request.setAttribute("waresClass", waresClassInfo);
			request.setAttribute("oldId", waresClassInfo.getId());
			request.setAttribute("nextLen", nextLen);
			request.setAttribute("pId", id);
		}
		return "success";
	}

	/**
	 * 获取商品分类列表
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-12
	 */
	public String getWaresClassListPage() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = goodsService.getWaresClassListPage(pageMap);
		List<WaresClass> waresClassList = pageData.getList();
		for (WaresClass waresClass : waresClassList) {
			if (StringUtils.isNotEmpty(waresClass.getState())) { // 状态
				SysCode sysCode = super.getBaseSysCodeService().showSysCodeInfo(waresClass.getState(), "state");
				if (sysCode != null) {
					waresClass.setStateName(sysCode.getCodename());
				}
			}
		}
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 获取树状商品分类列表
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-12
	 */
	public String getWaresClassTreeList() throws Exception {
		// 根目录
		List treeList = new ArrayList();
		Tree pTree = new Tree();
		pTree.setId("");
		pTree.setText("商品分类");
		pTree.setOpen("true");
		treeList.add(pTree);
		List<WaresClass> wcList = goodsService.getWaresClassTreeList();
		if (wcList.size() != 0) {
			for (WaresClass waresClassInfo : wcList) {
				Tree cTree = new Tree();
				cTree.setId(waresClassInfo.getId());
				cTree.setParentid(waresClassInfo.getPid());
				cTree.setText(waresClassInfo.getThisname());
				cTree.setState(waresClassInfo.getState());
				treeList.add(cTree);
			}
		}
		addJSONArray(treeList);
		return "success";
	}

	/**
	 * 获取树状商品分类启用列表
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年8月28日
	 */
	public String getWaresClassTreeOpenList() throws Exception {
		// 根目录
		List treeList = new ArrayList();
		Tree pTree = new Tree();
		pTree.setId("");
		pTree.setText("商品分类");
		pTree.setOpen("true");
		treeList.add(pTree);
		List<WaresClass> wcList = goodsService.getWaresClassTreeList();
		if (wcList.size() != 0) {
			for (WaresClass waresClassInfo : wcList) {
				Tree cTree = new Tree();
				cTree.setId(waresClassInfo.getId());
				cTree.setParentid(waresClassInfo.getPid());
				cTree.setText(waresClassInfo.getThisname());
				cTree.setState(waresClassInfo.getState());
				treeList.add(cTree);
			}
		}
		addJSONArray(treeList);
		return "success";
	}

	/**
	 * 获取下一级次长度
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	public String getNextWCLenght() throws Exception {
		int pLen = 0;
		String pId = request.getParameter("pId");
		if (StringUtils.isNotEmpty(pId)) {
			pLen = pId.length();
		}
		int nextLen = getBaseTreeFilesNext("t_base_goods_waresclass", pLen);
		addJSONObject("nextLen", nextLen);
		return "success";
	}

	/**
	 * 判断编码是否重复，true 不重复，false 重复
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	public String isRepeatWCID() throws Exception {
		String id = request.getParameter("id");
		boolean flag = goodsService.isRepeatWCID(id);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 判断本级名称是否重复，true 不重复，false 重复
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	public String isRepeatThisName() throws Exception {
		String thisname = request.getParameter("thisname");
		boolean flag = goodsService.isRepeatThisName(thisname);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 新增暂存商品分类
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	@UserOperateLog(key = "WaresClass", type = 2, value = "")
	public String addWaresClassHold() throws Exception {
		SysUser sysUser = getSysUser();
		waresClass.setAdduserid(sysUser.getUserid());
		waresClass.setAdddeptid(sysUser.getDepartmentid());
		waresClass.setAdddeptidname(sysUser.getDepartmentname());
		waresClass.setAddusername(sysUser.getName());
		boolean flag = goodsService.addWaresClass(waresClass);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		Map<String, String> node = new HashMap<String, String>();
		node.put("id", waresClass.getId());
		node.put("parentid", waresClass.getPid());
		node.put("text", waresClass.getThisname());
		node.put("state", waresClass.getState());
		map.put("node", node);
		addJSONObject(map);
		// 添加日志内容
		addLog("新增商品分类 编号:" + waresClass.getId(), flag);
		return "success";
	}

	/**
	 * 新增保存商品分类
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	@UserOperateLog(key = "WaresClass", type = 2, value = "")
	public String addWaresClassSave() throws Exception {
		SysUser sysUser = getSysUser();
		waresClass.setAdduserid(sysUser.getUserid());
		waresClass.setAdddeptid(sysUser.getDepartmentid());
		waresClass.setAdddeptidname(sysUser.getDepartmentname());
		waresClass.setAddusername(sysUser.getName());
		boolean flag = goodsService.addWaresClass(waresClass);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", flag);
		Map<String, String> node = new HashMap<String, String>();
		node.put("id", waresClass.getId());
		node.put("parentid", waresClass.getPid());
		node.put("text", waresClass.getThisname());
		node.put("state", waresClass.getState());
		map.put("node", node);
		addJSONObject(map);
		// 添加日志内容
		addLog("新增商品分类 编号:" + waresClass.getId(), flag);
		return "success";
	}

	/**
	 * 暂存修改商品分类信息
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	@UserOperateLog(key = "WaresClass", type = 3, value = "")
	public String editWaresClassHold() throws Exception {
		boolean flag = false;
		SysUser sysUser = getSysUser();
		waresClass.setModifyuserid(sysUser.getUserid());
		waresClass.setModifyusername(sysUser.getName());
		Map retMap = goodsService.editWaresClass(waresClass);
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(retMap.get("errorMes").toString())) {// 返回的错误信息不为空，则说明没有进行修改操作
			map.put("mes", retMap.get("errorMes"));
		} else {// 反之，则进行修改操作
			map.put("flag", retMap.get("flag"));
			if ("true".equals(retMap.get("flag").toString())) {
				flag = true;
			}
		}
		map.put("oldId", waresClass.getOldId());
		Map<String, String> node = new HashMap<String, String>();
		node.put("id", waresClass.getId());
		node.put("parentid", waresClass.getPid());
		node.put("text", waresClass.getThisname());
		node.put("state", waresClass.getState());
		map.put("node", node);
		addJSONObject(map);
		addLog("修改商品分类 编号:" + waresClass.getId(), flag);
		return "success";
	}

	/**
	 * 保存修改商品分类信息
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-13
	 */
	@UserOperateLog(key = "WaresClass", type = 3, value = "")
	public String editWaresClassSave() throws Exception {
		SysUser sysUser = getSysUser();
		waresClass.setModifyuserid(sysUser.getUserid());
		waresClass.setModifyusername(sysUser.getName());
		Map retMap = goodsService.editWaresClass(waresClass);
		addJSONObject(retMap);
		addLog("修改商品分类 编号:" + waresClass.getId(), retMap.get("flag").equals(true));
		return "success";
	}

	/**
	 * 删除商品分类
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-15
	 */
	@UserOperateLog(key = "WaresClass", type = 4, value = "")
	public String deleteWaresClass() throws Exception {
		boolean flag = false;
		String id = request.getParameter("id");
		Map retMap = new HashMap();
		String Mes = "";
		if (canTableDataDelete("t_base_goods_waresclass", id)) {// 判断是否被引用
			flag = goodsService.deleteWaresClass(id);
			if (!flag) {
				Mes = "删除失败!";
			}
		} else {
			Mes = "数据被引用,不允许删除!";
		}
		retMap.put("flag", flag);
		retMap.put("Mes", Mes);
		addJSONObject(retMap);
		// 添加日志内容
		addLog("删除商品分类 编号:" + id + "", flag);
		return "success";
	}

	/**
	 * 判断子节点是否存在商品分类列表,true 存在，false 不存在
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-15
	 */
	public String isExistChildWCList() throws Exception {
		String id = request.getParameter("id");
		boolean flag = goodsService.isExistChildWCList(id);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 启用商品分类
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-15
	 */
	@UserOperateLog(key = "WaresClass", type = 0, value = "")
	public String enableWaresClass() throws Exception {
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("openuserid", sysUser.getUserid());
		paramMap.put("openusername", sysUser.getName());
		paramMap.put("id", id);
		boolean flag = goodsService.enableWaresClass(paramMap);
		addJSONObject("flag", flag);
		// 添加日志内容
		addLog("启用商品分类 编号:" + id + "", flag);
		return "success";
	}

	/**
	 * 禁用商品分类
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-15
	 */
	@UserOperateLog(key = "WaresClass", type = 0, value = "")
	public String disableWaresClass() throws Exception {
		String id = request.getParameter("id");
		SysUser sysUser = getSysUser();
		List<WaresClass> list = goodsService.getWaresClassListByPid(id); // 查询该节点及所有子节点信息
		int successNum = 0, failureNum = 0, notAllowNum = 0, userNum = 0;
		String retids = null;
		List<String> ids = new ArrayList<String>();
		for (WaresClass waresClass : list) { // 循环所有节点信息，判断可以禁用的则禁用。
			if (!"1".equals(waresClass.getState())) {
				notAllowNum++;
			} else {
				if (!canTableDataDelete("t_base_goods_waresclass", id)) {
					userNum++;
				} else {
					WaresClass wc = new WaresClass();
					wc.setCloseuserid(sysUser.getUserid());
					wc.setCloseusername(sysUser.getName());
					wc.setId(waresClass.getId());
					boolean flag = goodsService.disableWaresClass(waresClass);
					if (flag) {
						successNum++;
						ids.add(waresClass.getId()); // 返回所有禁用的记录编号，供前台更新树节点信息
						if (StringUtils.isNotEmpty(retids)) {
							retids = "," + waresClass.getId();
						} else {
							retids = waresClass.getId();
						}
					} else {
						failureNum++;
					}
				}
			}
		}
		Map map = new HashMap();
		map.put("successNum", successNum);
		map.put("failureNum", failureNum);
		map.put("notAllowNum", notAllowNum);
		map.put("userNum", userNum);
		map.put("ids", ids);
		addJSONObject(map);
		addLog("禁用商品分类 编号:" + retids + "", true);
		return SUCCESS;
	}

	/**
	 * 获取子节点的父级信息
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jun 4, 2013
	 */
	public String getParentWaresClass() throws Exception {
		String pid = request.getParameter("pid");
		WaresClass pWCInfo = goodsService.getWaresClassInfo(pid);
		if (pWCInfo != null) {
			Map map = new HashMap();
			map.put("pWCInfo", pWCInfo);
			addJSONObject(map);
		}
		return SUCCESS;
	}

	/*----------------------------------------商品档案-----------------------------------------------*/

	public String getGoodsListForCombobox() throws Exception {
		String brandid = request.getParameter("brandid");
		List list = goodsService.getGoodsListForCombobox(brandid);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 商品档案页面（主/增）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-19
	 */
	public String showGoodsInfoPage() throws Exception {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String state = request.getParameter("state");
		String WCid = request.getParameter("WCid");
		String listType = request.getParameter("listType");
		if ("1".equals(listType)) {
			request.setAttribute("tab", "/basefiles/showPriceListPage.do");
			request.setAttribute("listid", "wares-table-goodsInfoToPriceList");
		} else if ("2".equals(listType)) {
			request.setAttribute("tab", "/basefiles/showGoodsInfoListPage.do");
			request.setAttribute("listid", "wares-table-goodsInfoList");
		}
		if (StringUtils.isEmpty(id)) {
			id = "";
		}
		if (StringUtils.isEmpty(state)) {
			state = "";
		}
		if (StringUtils.isEmpty(WCid)) {
			WCid = "";
		}
		request.setAttribute("listType", listType);
		request.setAttribute("type", type);
		request.setAttribute("id", id);
		request.setAttribute("goodsid", id);
		request.setAttribute("state", state);
		request.setAttribute("WCid", WCid);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}

	/**
	 * 显示商品档案列表页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-18
	 */
	public String showGoodsInfoListPage() throws Exception {
		// 价格套列表
		List<SysCode> priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		request.setAttribute("priceList", priceList);
		SysParam sysParam = getBaseSysParamService().getSysParam("PRICENUM");
		if (null != sysParam) {
			request.setAttribute("pricenum", sysParam.getPvalue());
		}
		// 从数据字典中获取自定义描述
		// request.setAttribute("fieldmap",
		// getRowDescFromDataDict("t_base_goods_info"));
		return "success";
	}

	/**
	 * 显示商品档案新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-18
	 */
	public String showGoodsInfoAddPage() throws Exception {
		String WCid = request.getParameter("WCid");
		if (StringUtils.isNotEmpty(WCid)) {
			WaresClass waresClass = goodsService.getWaresClassInfo(WCid);
			request.setAttribute("WCname", waresClass.getName());
		}
		SysParam sysParam = getBaseSysParamService().getSysParam("DEFAULTAXTYPE");
		if (null != sysParam) {
			request.setAttribute("defaulttaxtype", sysParam.getPvalue());
		}
		request.setAttribute("operateType", "add");
		request.setAttribute("type", "add");
		request.setAttribute("WCid", WCid);
		request.setAttribute("fieldmap", getRowDescFromDataDict("t_base_goods_info"));
		return "success";
	}

	/**
	 * 显示商品档案详情页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-18
	 */
	public String showGoodsInfoViewPage() throws Exception {
		String id = request.getParameter("id");
		GoodsInfo goodsInfo = goodsService.showGoodsInfo(id);
		if (goodsInfo != null) {
			if (StringUtils.isNotEmpty(goodsInfo.getDefaultsort())) {
				WaresClass waresClass = goodsService.getWaresClassInfo(goodsInfo.getDefaultsort());
				if (null != waresClass) {
					goodsInfo.setDefaultsortName(waresClass.getThisname());
				}
			}
			if (StringUtils.isNotEmpty(goodsInfo.getStorageid())) {
				StorageInfo storageInfo = storageService.showStorageInfo(goodsInfo.getStorageid());
				if (null != storageInfo) {
					goodsInfo.setStorageName(storageInfo.getName());
				}
			}
			if (StringUtils.isNotEmpty(goodsInfo.getStoragelocation())) {// 默认库位
				StorageLocation storageLocation = storageService.showStorageLocationInfo(goodsInfo.getStoragelocation());
				if (null != storageLocation) {
					goodsInfo.setStoragelocationName(storageLocation.getName());
				}
			}
			if (StringUtils.isNotEmpty(goodsInfo.getDefaultsupplier())) {// 默认供应商
				Map map = new HashMap();
				map.put("id", goodsInfo.getDefaultsupplier());
				BuySupplier buySupplier = getBaseBuyService().getBuySupplierBy(map);
				if (null != buySupplier) {
					goodsInfo.setDefaultsupplierName(buySupplier.getName());
				}
			}
			// 获取t_base_goods_info表的字段查看权限
			Map colMap = getAccessColumn("t_base_goods_info");
			request.setAttribute("showMap", colMap);
			request.setAttribute("fieldmap", getRowDescFromDataDict("t_base_goods_info"));
			request.setAttribute("goodsInfo", goodsInfo);
			request.setAttribute("goodsid", id);
			request.setAttribute("operateType", "view");
			request.setAttribute("type", "view");
		}
		return "success";
	}

	/**
	 * 显示辅计量单位列表（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 23, 2013
	 */
	public String showMeteringUnitInfoList() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		List<GoodsInfo_MteringUnitInfo> list = goodsService.showMeteringUnitInfoList(pageMap);
		for (GoodsInfo_MteringUnitInfo MUInfo : list) {
			if (StringUtils.isNotEmpty(MUInfo.getMeteringunitid())) {// 计量单位
				MeteringUnit meteringUnit = goodsService.showMeteringUnitInfo(MUInfo.getMeteringunitid());
				if (meteringUnit != null) {
					MUInfo.setMeteringunitName(meteringUnit.getName());
				}
			}
		}
		request.setAttribute("type", request.getParameter("type"));
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 显示价格套列表（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 23, 2013
	 */
	public String showPriceInfoList() throws Exception {
		String taxtypeid = "", type = "", goodsid = "";
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if (map.containsKey("taxtype") && map.get("taxtype") != null) {
			taxtypeid = map.get("taxtype").toString();
		}
		if (map.containsKey("type") && map.get("type") != null) {
			type = map.get("type").toString();
		}
		if (map.containsKey("oldgoodsid") && null != map.get("oldgoodsid")) {
			goodsid = map.get("oldgoodsid").toString();
		}
		if (map.containsKey("goodsid") && null != map.get("goodsid")) {
			goodsid = map.get("goodsid").toString();
		}
		pageMap.setCondition(map);
		// 获取编码表所定义的价格套列表
		List<SysCode> codeList = sysCodeService.showSysCodeListByType("price_list");
		// 将编码表所定义的价格套加入到商品价格套管理中
		List<GoodsInfo_PriceInfo> priceInfoList = new ArrayList<GoodsInfo_PriceInfo>();
		if ("add".equals(type)) {
			if (codeList.size() != 0) {
				for (SysCode sysCode : codeList) {
					GoodsInfo_PriceInfo priceInfo = new GoodsInfo_PriceInfo();
					// priceInfo.setId(Integer.parseInt(sysCode.getCode()));
					priceInfo.setCode(sysCode.getCode());
					priceInfo.setName(sysCode.getCodename());
					if (StringUtils.isNotEmpty(taxtypeid)) {
						priceInfo.setTaxtype(taxtypeid);
						TaxType taxTypeInfo = financeService.getTaxTypeInfo(taxtypeid);
						if (taxTypeInfo != null) {
							priceInfo.setTaxtypeName(taxTypeInfo.getName());
							priceInfo.setTaxtypeRate(taxTypeInfo.getRate());
						}
					}
					priceInfoList.add(priceInfo);
				}
			}
		} else if ("edit".equals(type) || "view".equals(type) || "copy".equals(type)) {
			GoodsInfo oldGoodsInfo = goodsService.showGoodsInfo(goodsid);
			if (null != oldGoodsInfo) {
				for (SysCode sysCode : codeList) {
					GoodsInfo_PriceInfo priceInfo = goodsService.getPriceDataByGoodsidAndCode(goodsid, sysCode.getCode());
					if (null != priceInfo) {// 根据修改的默认税种计算无税单价
						TaxType taxTypeInfo = financeService.getTaxTypeInfo(taxtypeid);
						if (null != taxTypeInfo) {
							priceInfo.setTaxtypeName(taxTypeInfo.getName());
							priceInfo.setTaxtypeRate(taxTypeInfo.getRate());
							if (!taxtypeid.equals(oldGoodsInfo.getDefaulttaxtype())) {
								BigDecimal rate = taxTypeInfo.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1));
								BigDecimal price = priceInfo.getTaxprice().divide(rate, 8, BigDecimal.ROUND_HALF_UP);
								priceInfo.setPrice(price);
							}
						}
						priceInfoList.add(priceInfo);
					} else {
						GoodsInfo_PriceInfo price = new GoodsInfo_PriceInfo();
						price.setCode(sysCode.getCode());
						price.setName(sysCode.getCodename());
						if (StringUtils.isNotEmpty(taxtypeid)) {
							price.setTaxtype(taxtypeid);
							TaxType taxTypeInfo = financeService.getTaxTypeInfo(taxtypeid);
							if (taxTypeInfo != null) {
								price.setTaxtypeName(taxTypeInfo.getName());
								price.setTaxtypeRate(taxTypeInfo.getRate());
							}
						}
						priceInfoList.add(price);
					}
				}
			}
		}
		addJSONArray(priceInfoList);
		request.setAttribute("type", request.getParameter("type"));
		return SUCCESS;
	}

	/**
	 * 
	 * @param paramMap
	 *            taxPrice 含税单价，rate 税率，
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jun 13, 2013
	 */
	public BigDecimal computePriceByRate(Map paramMap) throws Exception {
		if (paramMap.get("taxPrice") != null && null != paramMap.get("rate")) {
			BigDecimal price = new BigDecimal(0);
			BigDecimal bTaxPrice = new BigDecimal(paramMap.get("taxPrice").toString()); // 含税单价
			BigDecimal bTaxRate = new BigDecimal(paramMap.get("rate").toString()).divide(new BigDecimal(100));// 税率
			price = bTaxPrice.divide(bTaxRate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
			return price;
		}
		return null;
	}

	/**
	 * 显示对应仓库列表（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 23, 2013
	 */
	public String showStorageInfoList() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		List<GoodsInfo_StorageInfo> list = goodsService.showStorageInfoList(pageMap);
		for (GoodsInfo_StorageInfo storaInfo : list) {
			if (StringUtils.isNotEmpty(storaInfo.getStorageid())) {// 仓库
				StorageInfo storageInfo = storageService.showStorageInfo(storaInfo.getStorageid());
				if (storageInfo != null) {
					storaInfo.setStorageName(storageInfo.getName());
				}
			}
		}
		request.setAttribute("type", request.getParameter("type"));
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 显示对应库位列表（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date May 28, 2013
	 */
	public String showGoodsStorageLocationList() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		List<GoodsStorageLocation> list = goodsService.showGoodsStorageLocationList(pageMap);
		for (GoodsStorageLocation GoodsSL : list) {
			if (StringUtils.isNotEmpty(GoodsSL.getStoragelocationid())) {// 库位
				StorageLocation sl = storageService.showStorageLocationInfo(GoodsSL.getStoragelocationid());
				if (sl != null) {
					GoodsSL.setStoragelocationName(sl.getName());
				}
			}
		}
		request.setAttribute("type", request.getParameter("type"));
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 显示对应分类列表（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 23, 2013
	 */
	public String showWaresClassInfoList() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		List<GoodsInfo_WaresClassInfo> list = goodsService.showWaresClassInfoList(pageMap);
		for (GoodsInfo_WaresClassInfo WCInfo : list) {
			if (StringUtils.isNotEmpty(WCInfo.getWaresclass())) {
				WaresClass waresClass = goodsService.getWaresClassInfo(WCInfo.getWaresclass());
				if (waresClass != null) {
					WCInfo.setWaresclassName(waresClass.getThisname());
				}
			}
		}
		request.setAttribute("type", request.getParameter("type"));
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 显示商品档案修改页面（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-18
	 */
	public String showGoodsInfoEidtPage() throws Exception {
		String id = request.getParameter("id");
		GoodsInfo goodsInfo = goodsService.showGoodsInfo(id);
		if (goodsInfo != null) {
			if (StringUtils.isNotEmpty(goodsInfo.getDefaultsort())) {// 默认分类
				WaresClass waresClass = goodsService.getWaresClassInfo(goodsInfo.getDefaultsort());
				if (null != waresClass) {
					goodsInfo.setDefaultsortName(waresClass.getThisname());
				}
			}
			if (StringUtils.isNotEmpty(goodsInfo.getStorageid())) {// 默认仓库
				StorageInfo storageInfo = storageService.showStorageInfo(goodsInfo.getStorageid());
				if (null != storageInfo) {
					goodsInfo.setStorageName(storageInfo.getName());
				}
			}
			if (StringUtils.isNotEmpty(goodsInfo.getStoragelocation())) {// 默认库位
				StorageLocation storageLocation = storageService.showStorageLocationInfo(goodsInfo.getStoragelocation());
				if (null != storageLocation) {
					goodsInfo.setStoragelocationName(storageLocation.getName());
				}
			}
			if (StringUtils.isNotEmpty(goodsInfo.getDefaultsupplier())) {// 默认供应商
				Map map = new HashMap();
				map.put("id", goodsInfo.getDefaultsupplier());
				BuySupplier buySupplier = getBaseBuyService().getBuySupplierBy(map);
				if (null != buySupplier) {
					goodsInfo.setDefaultsupplierName(buySupplier.getName());
				}
			}
			// 获取t_base_goods_info表的字段编辑权限
			Map colMap = getEditAccessColumn("t_base_goods_info");
			request.setAttribute("showMap", colMap);
			request.setAttribute("fieldmap", getRowDescFromDataDict("t_base_goods_info"));
			request.setAttribute("editFlag", canTableDataDelete("t_base_goods_info", id));
			request.setAttribute("goodsInfo", goodsInfo);
			request.setAttribute("oldId", id);
			request.setAttribute("operateType", "edit");
			request.setAttribute("type", "edit");
		}
		return "success";
	}

	/**
	 * 显示商品档案复制页面（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-18
	 */
	public String showGoodsInfoCopyPage() throws Exception {
		String id = request.getParameter("id");
		GoodsInfo goodsInfo = goodsService.showGoodsInfo(id);
		if (goodsInfo != null) {
			if (StringUtils.isNotEmpty(goodsInfo.getDefaultsort())) {
				WaresClass waresClass = goodsService.getWaresClassInfo(goodsInfo.getDefaultsort());
				if (null != waresClass) {
					goodsInfo.setDefaultsortName(waresClass.getThisname());
				}
			}
			if (StringUtils.isNotEmpty(goodsInfo.getStorageid())) {
				StorageInfo storageInfo = storageService.showStorageInfo(goodsInfo.getStorageid());
				if (null != storageInfo) {
					goodsInfo.setStorageName(storageInfo.getName());
				}
			}
			if (StringUtils.isNotEmpty(goodsInfo.getStoragelocation())) {// 默认库位
				StorageLocation storageLocation = storageService.showStorageLocationInfo(goodsInfo.getStoragelocation());
				if (null != storageLocation) {
					goodsInfo.setStoragelocationName(storageLocation.getName());
				}
			}
			if (StringUtils.isNotEmpty(goodsInfo.getDefaultsupplier())) {// 默认供应商
				Map map = new HashMap();
				map.put("id", goodsInfo.getDefaultsupplier());
				BuySupplier buySupplier = getBaseBuyService().getBuySupplierBy(map);
				if (null != buySupplier) {
					goodsInfo.setDefaultsupplierName(buySupplier.getName());
				}
			}
			request.setAttribute("fieldmap", getRowDescFromDataDict("t_base_goods_info"));
			request.setAttribute("goodsInfo", goodsInfo);
			request.setAttribute("oldId", id);
			request.setAttribute("operateType", "copy");
			request.setAttribute("type", "copy");
		}
		return "success";
	}

	/**
	 * 商品档案获取商品分类树状信息（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-18
	 */
	public String goodsInfoWaresClassTree() throws Exception {
		List<WaresClass> WClist = goodsService.getWaresClassTreeList();
		List treeList = new ArrayList();
		Tree pTree = new Tree();
		pTree.setId("");
		pTree.setText("商品分类");
		pTree.setOpen("true");
		treeList.add(pTree);
		for (WaresClass waresClassInfo : WClist) {
			Tree cTree = new Tree();
			cTree.setId(waresClassInfo.getId());
			cTree.setText(waresClassInfo.getThisname());
			cTree.setParentid(waresClassInfo.getPid());
			cTree.setState(waresClassInfo.getState());
			treeList.add(cTree);
		}
		addJSONArray(treeList);
		return "success";
	}

	/**
	 * 根据商品分类获取商品档案列表（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-18
	 */
	public String goodsInfoListPage() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = goodsService.goodsInfoListPage(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 批量启用商品档案（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-18
	 */
	@UserOperateLog(key = "GoodsInfo", type = 0, value = "")
	public String enableGoodsInfos() throws Exception {
		String oldIdStr = request.getParameter("idStr");
		String newIdStr = "";
		String[] idArr = oldIdStr.split(",");
		int invalidNum = 0, num = 0;
		Map retMap = new HashMap();
		// 检测要启用商品的状态，选中记录的状态为“保存”或“禁用”状态下才可启用
		for (int i = 0; i < idArr.length; i++) {
			GoodsInfo goodsInfo = goodsService.showGoodsInfo(idArr[i]);
			if (goodsInfo != null) {
				if (!"2".equals(goodsInfo.getState()) && !"0".equals(goodsInfo.getState())) {// 状态不为保存或禁用状态，启用无效
					invalidNum += 1;
				} else {
					if (StringUtils.isNotEmpty(newIdStr)) {
						newIdStr += "," + idArr[i];
					} else {
						newIdStr = idArr[i];
					}
				}
			}
		}
		if (StringUtils.isNotEmpty(newIdStr)) {
			num = idArr.length - invalidNum;
			Map map = new HashMap();
			SysUser sysUser = getSysUser();
			map.put("idsArr", newIdStr.split(","));
			map.put("openuserid", sysUser.getUserid());
			map.put("openusername", sysUser.getName());
			boolean flag = goodsService.enableGoodsInfos(map);
			retMap.put("flag", flag);
			// 添加日志内容
			addLog("启用商品 编号:" + newIdStr, flag);
		}
		retMap.put("invalidNum", invalidNum);
		retMap.put("num", num);
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 批量禁用商品档案,检测要禁用商品的状态，选中记录的状态为“启用”，且判断是否被引用（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-18
	 */
	@UserOperateLog(key = "GoodsInfo", type = 0, value = "")
	public String disableGoodsInfos() throws Exception {
		String oldIdStr = request.getParameter("idStr");
		String[] idArr = oldIdStr.split(",");
		String newIdStr = "";
		int invalidNum = 0, num = 0;
		Map retMap = new HashMap();
		// 检测要禁用商品的状态，选中记录的状态为“启用”，且判断是否被引用
		for (int i = 0; i < idArr.length; i++) {
			GoodsInfo goodsInfo = goodsService.showGoodsInfo(idArr[i]);
			if (goodsInfo != null) {
				// 判断状态是否为启用，不是，则视为无效禁用记录
				if (!"1".equals(goodsInfo.getState())) {
					invalidNum += 1;
				} else {
					newIdStr += idArr[i] + ",";
				}
			}
		}
		if (StringUtils.isNotEmpty(newIdStr)) {
			String[] newIdArr = newIdStr.split(",");
			num = newIdArr.length;
			Map map = new HashMap();
			SysUser sysUser = getSysUser();
			map.put("idsArr", newIdArr);
			map.put("closeuserid", sysUser.getUserid());
			map.put("closeusername", sysUser.getName());
			boolean flag = goodsService.disableGoodsInfos(map);
			retMap.put("flag", flag);
			// 添加日志内容
			addLog("禁用商品 编号:" + newIdStr, flag);
		}
		retMap.put("invalidNum", invalidNum);
		retMap.put("num", num);
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 批量删除商品信息（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-19
	 */
	@UserOperateLog(key = "GoodsInfo", type = 4, value = "")
	public String deleteGoodsInfos() throws Exception {
		String oldIdStr = request.getParameter("idStr");
		String[] idArr = oldIdStr.split(",");
		String newIdStr = "";
		int num = 0, userNum = 0, lockNum = 0;
		Map retMap = new HashMap();
		// 检测要删除商品的状态，选中记录判断是否被引用
		for (int i = 0; i < idArr.length; i++) {
			GoodsInfo goodsInfo = goodsService.showGoodsInfo(idArr[i]);
			if (goodsInfo != null) {
				// 判断数据是否被加锁,True已被加锁。Fasle未加锁。（网络互斥）
				if (!isLock("t_base_goods_info", idArr[i])) {
					// 判断是否被引用
					if (!canTableDataDelete("t_base_goods_info", idArr[i])) {// true可以操作，false不可以操作
						userNum += 1;
					} else {
						if (StringUtils.isNotEmpty(newIdStr)) {
							newIdStr += "," + idArr[i];
						} else {
							newIdStr = idArr[i];
						}
					}
				} else {
					lockNum += 1;
				}
			}
		}
		if (StringUtils.isNotEmpty(newIdStr)) {
			String[] newIdArr = newIdStr.split(",");
			boolean flag = goodsService.deleteGoodsInfos(newIdArr);
			if (flag) {
				num = newIdArr.length;
			}
			retMap.put("flag", flag);
			// 添加日志内容
			addLog("删除商品 编号:" + newIdStr, flag);
		} else {
			addLog("商品 编号:" + oldIdStr + "不允许删除", true);
		}
		retMap.put("num", num);
		retMap.put("userNum", userNum);
		retMap.put("lockNum", lockNum);
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 判断商品ID是否重复或商品是否存在， true 重复，false 不重复
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-19
	 */
	public String isRepeatGoodsInfoID() throws Exception {
		String id = request.getParameter("id");
		boolean flag = goodsService.isRepeatGoodsInfoID(id);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 判断商品名称是否重复,true 重复，false 不重复（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-19
	 */
	public String isRepeatGoodsInfoName() throws Exception {
		String name = request.getParameter("name");
		boolean flag = goodsService.isRepeatGoodsInfoName(name);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 判断条形码是否重复 true 重复，false 不重复
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 22, 2013
	 */
	public String isRepeatGoodsInfoBarcode() throws Exception {
		String barcode = request.getParameter("barcode");
		boolean flag = goodsService.isRepeatGoodsInfoBarcode(barcode);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 判断箱装条形码是否重复 true 重复，false 不重复
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 22, 2013
	 */
	public String isRepeatGoodsInfoBoxbarcode() throws Exception {
		String boxbarcode = request.getParameter("boxbarcode");
		boolean flag = goodsService.isRepeatGoodsInfoBoxbarcode(boxbarcode);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 判断货号是否重复 true 重复，false 不重复
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 22, 2013
	 */
	public String isRepeatGoodsInfoItemno() throws Exception {
		String itemno = request.getParameter("itemno");
		boolean flag = goodsService.isRepeatGoodsInfoItemno(itemno);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 新增商品（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-4-19
	 */
	@UserOperateLog(key = "GoodsInfo", type = 2, value = "")
	public String addGoodsInfo() throws Exception {
		// 获取编辑数据 这里获取到的是json字符串
		String priceChange = request.getParameter("priceChange");
		String MUChange = request.getParameter("MUChange");
		String storageChange = request.getParameter("storageChange");
		String WCChange = request.getParameter("WCChange");
		String SLChange = request.getParameter("SLChange");
		List<GoodsStorageLocation> SLList = new ArrayList<GoodsStorageLocation>();
		if (priceChange != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(priceChange);
			priceInfoList = JSONArray.toList(json, GoodsInfo_PriceInfo.class);
			for (GoodsInfo_PriceInfo priceInfo : priceInfoList) {
				priceInfo.setGoodsid(goodsInfo.getId());
			}
		}
		if (MUChange != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(MUChange);
			meteringUnitInfoList = JSONArray.toList(json, GoodsInfo_MteringUnitInfo.class);
		}
		if (storageChange != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(storageChange);
			storageInfoList = JSONArray.toList(json, GoodsInfo_StorageInfo.class);
		}
		if (WCChange != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(WCChange);
			waresClassList = JSONArray.toList(json, GoodsInfo_WaresClassInfo.class);
		}
		if (SLChange != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(SLChange);
			SLList = JSONArray.toList(json, GoodsStorageLocation.class);
		}
		String type = request.getParameter("type");
		boolean flag = false;
		if ("save".equals(type)) {
			goodsInfo.setState("2");
		} else {
			goodsInfo.setState("3");
		}
		SysUser sysUser = getSysUser();
		Date nowDate=new Date();
		goodsInfo.setAdddeptid(sysUser.getDepartmentid());
		goodsInfo.setAdddeptname(sysUser.getDepartmentname());
		goodsInfo.setAdduserid(sysUser.getUserid());
		goodsInfo.setAddusername(sysUser.getName());
		if(StringUtils.isNotEmpty(goodsInfo.getJsgoodsid())){
			goodsInfo.setJsgoodsmodifytime(nowDate);
			goodsInfo.setJsgoodsmodifyuserid(sysUser.getUserid());
			goodsInfo.setJsgoodsmodifyusername(sysUser.getName());
		}
		// 判断商品编码是否重复, true 重复，false 不重复
		if (StringUtils.isNotEmpty(goodsInfo.getId())) {
			boolean isRepeatId = goodsService.isRepeatGoodsInfoID(goodsInfo.getId());
			if (!isRepeatId) {
				flag = goodsService.addGoodsInfo(goodsInfo, priceInfoList, meteringUnitInfoList, storageInfoList, waresClassList, SLList);
			}
		}
		addLog("新增商品 编号:" + goodsInfo.getId(), flag);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 修改商品（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Apr 23, 2013
	 */
	@UserOperateLog(key = "GoodsInfo", type = 3, value = "")
	public String editGoodsInfo() throws Exception {
		// 获取编辑数据 这里获取到的是json字符串
		String priceChange = request.getParameter("priceChange");
		String MUChange = request.getParameter("MUChange");
		String storageChange = request.getParameter("storageChange");
		String WCChange = request.getParameter("WCChange");
		String SLChange = request.getParameter("SLChange");
		String deletedMU = request.getParameter("deletedMU");
		String deletedStorage = request.getParameter("deletedStorage");
		String deletedWC = request.getParameter("deletedWC");
		String deletedSL = request.getParameter("deletedSL");
		List<GoodsInfo_PriceInfo> priceInfoList = new ArrayList<GoodsInfo_PriceInfo>();
		List<GoodsInfo_MteringUnitInfo> MUDelList = new ArrayList<GoodsInfo_MteringUnitInfo>();
		List<GoodsInfo_StorageInfo> storageDelList = new ArrayList<GoodsInfo_StorageInfo>();
		List<GoodsInfo_WaresClassInfo> WCDelList = new ArrayList<GoodsInfo_WaresClassInfo>();
		List<GoodsStorageLocation> SLDelList = new ArrayList<GoodsStorageLocation>();
		if (priceChange != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(priceChange);
			priceInfoList = JSONArray.toList(json, GoodsInfo_PriceInfo.class);
		}
		if (MUChange != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(MUChange);
			meteringUnitInfoList = JSONArray.toList(json, GoodsInfo_MteringUnitInfo.class);
			if (deletedMU != null) {
				// 把json字符串转换成对象
				JSONArray delMUjson = JSONArray.fromObject(deletedMU);
				MUDelList = JSONArray.toList(delMUjson, GoodsInfo_MteringUnitInfo.class);
				String delMUIds = "";
				for (GoodsInfo_MteringUnitInfo MUInfo : MUDelList) {
					delMUIds += MUInfo.getId() + ",";
				}
				goodsInfo.setDelMUIds(delMUIds);
			}
			if (null != meteringUnitInfoList && meteringUnitInfoList.size() != 0) {
				for (GoodsInfo_MteringUnitInfo MU : meteringUnitInfoList) {
					if ("1".equals(MU.getIsdefault())) {
						goodsInfo.setAuxunitid(MU.getMeteringunitid());
						MeteringUnit meteringUnit = goodsService.showMeteringUnitInfo(MU.getMeteringunitid());
						if (null != meteringUnit) {
							goodsInfo.setAuxunitname(meteringUnit.getName());
						}
					}
				}
			}
		}
		if (storageChange != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(storageChange);
			storageInfoList = JSONArray.toList(json, GoodsInfo_StorageInfo.class);
			if (deletedStorage != null) {
				// 把json字符串转换成对象
				JSONArray delStoragejson = JSONArray.fromObject(deletedStorage);
				storageDelList = JSONArray.toList(delStoragejson, GoodsInfo_StorageInfo.class);
				String delStorageIds = "";
				for (GoodsInfo_StorageInfo storageInfo : storageDelList) {
					delStorageIds += storageInfo.getId() + ",";
				}
				goodsInfo.setDelStorageIds(delStorageIds);
			}
		}
		if (WCChange != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(WCChange);
			waresClassList = JSONArray.toList(json, GoodsInfo_WaresClassInfo.class);
			if (deletedWC != null) {
				// 把json字符串转换成对象
				JSONArray delWCjson = JSONArray.fromObject(deletedWC);
				WCDelList = JSONArray.toList(delWCjson, GoodsInfo_WaresClassInfo.class);
				String delWCIds = "";
				for (GoodsInfo_WaresClassInfo WCInfo : WCDelList) {
					delWCIds += WCInfo.getId() + ",";
				}
				goodsInfo.setDelWCIds(delWCIds);
			}
		}
		if (SLChange != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(SLChange);
			SLList = JSONArray.toList(json, GoodsStorageLocation.class);
			if (deletedSL != null) {
				// 把json字符串转换成对象
				JSONArray delSLjson = JSONArray.fromObject(deletedSL);
				SLDelList = JSONArray.toList(delSLjson, GoodsStorageLocation.class);
				String delSLIds = "";
				for (GoodsStorageLocation SLInfo : SLDelList) {
					delSLIds += SLInfo.getId() + ",";
				}
				goodsInfo.setDelSLIds(delSLIds);
			}
		}
		String type = request.getParameter("type");
		if (!"1".equals(goodsInfo.getState()) && !"0".equals(goodsInfo.getState())) {
			if ("save".equals(type)) {
				goodsInfo.setState("2");
			} else {
				goodsInfo.setState("3");
			}
		}
		GoodsInfo oldGoodsInfo = goodsService.showGoodsInfo(goodsInfo.getOldId());
		SysUser sysUser = getSysUser();
		goodsInfo.setModifyuserid(sysUser.getUserid());
		goodsInfo.setModifyusername(sysUser.getName());

		boolean isnewjsgoods=StringUtils.isEmpty(goodsInfo.getJsgoodsid());
		boolean isoldjsgoods=StringUtils.isEmpty(oldGoodsInfo.getJsgoodsid());

		if(!(isnewjsgoods && isoldjsgoods) &&
				!(!isoldjsgoods && oldGoodsInfo.getJsgoodsid().equals(goodsInfo.getJsgoodsid()))){
			goodsInfo.setJsgoodsmodifyuserid(sysUser.getUserid());
			goodsInfo.setJsgoodsmodifyusername(sysUser.getUsername());
			goodsInfo.setJsgoodsmodifytime(new Date());
		}

		Map map = goodsService.editGoodsInfo(goodsInfo, priceInfoList, meteringUnitInfoList, storageInfoList, waresClassList, SLList);
		if (map.get("retFlag").equals(true)) {
			MeteringUnit meteringUnit = goodsService.showMeteringUnitInfo(goodsInfo.getMainunit());
			if (null != meteringUnit) {
				goodsInfo.setMainunitName(meteringUnit.getName());
			}
			if (null != meteringUnitInfoList) {
				for (GoodsInfo_MteringUnitInfo goodsMU : meteringUnitInfoList) {
					if ("1".equals(goodsMU.getIsdefault())) {
						goodsInfo.setAuxunitid(goodsMU.getMeteringunitid());
						MeteringUnit meteringUnit2 = goodsService.showMeteringUnitInfo(goodsMU.getMeteringunitid());
						if (null != meteringUnit2) {
							goodsInfo.setAuxunitname(meteringUnit2.getName());
						}
					}
				}
			}
//			addEditGoodsTaskSchedule(goodsInfo, oldGoodsInfo, 1);
		}
		addJSONObject(map);
		addLog("修改商品 编号:" + goodsInfo.getId(), map.get("retFlag").equals(true));
		return SUCCESS;
	}

	/**
	 * 查看原图（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2013-3-21
	 */
	public String showGoodsInfoOldImgPage() throws Exception {
		String photograph = request.getParameter("photograph");
		request.setAttribute("photograph", photograph);
		return "success";
	}

	/*--------------------价格套列表(商品档案)------------------------------*/

	public String showPriceListPage() throws Exception {
		return SUCCESS;
	}

	/**
	 * 从信息表中获取价格套列表(商品档案)
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date May 3, 2013
	 */
	public String priceList() throws Exception {
		String type = request.getParameter("type");
		List list = sysCodeService.showSysCodeListByType(type);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 根据价格套编号获取所属商品列表(商品档案)
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date May 4, 2013
	 */
	public String showGoodsInfoByPriceList() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = goodsService.getGoodsInfoPriceByCode(pageMap);
		List<GoodsInfo_PriceInfo> list = pageData.getList();
		if (list.size() != 0) {
			for (GoodsInfo_PriceInfo priceInfo : list) {
				if (StringUtils.isNotEmpty(priceInfo.getGoodsbrand())) {
					Brand brand = goodsService.getBrandInfo(priceInfo.getGoodsbrand());
					if (brand != null) {
						priceInfo.setGoodsbrandname((brand.getName()));
					}
				}
				if (StringUtils.isNotEmpty(priceInfo.getTaxtype())) { // 税种
					TaxType taxType = financeService.getTaxTypeInfo(priceInfo.getTaxtype());
					if (taxType != null) {
						priceInfo.setTaxtypeName(taxType.getName());
						priceInfo.setTaxtypeRate(taxType.getRate());
					}
				}
			}
		}
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 修改价格套列表（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date May 6, 2013
	 */
	public String editGoodsPriceList() throws Exception {
		String updated = request.getParameter("updated");
		boolean flag = false;
		if (updated != null) {
			JSONArray json = JSONArray.fromObject(updated);
			priceInfoList = JSONArray.toList(json, GoodsInfo_PriceInfo.class);
			if (null != priceInfoList && priceInfoList.size() != 0) {
				for (GoodsInfo_PriceInfo priceInfo : priceInfoList) {
					flag = goodsService.editPriceInfo(priceInfo);
				}
			}
		}
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 显示价格套新增（商品档案）
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date May 10, 2013
	 */
	public String showPriceAddPage() throws Exception {
		return SUCCESS;
	}

	/**
	 * 含税单价与无税单价之间的变化
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jun 6, 2013
	 */
	public String getPriceChanger() throws Exception {
		String type = request.getParameter("type"); // 1含税单价改变2无税单价改变
		String price = StringUtils.isEmpty(request.getParameter("price")) ? "0" : request.getParameter("price"); // 改变后的无税单价
		String taxRate = StringUtils.isEmpty(request.getParameter("taxtypeRate")) ? "0" : request.getParameter("taxtypeRate");// 税率
		String taxprice = StringUtils.isEmpty(request.getParameter("taxprice")) ? "0" : request.getParameter("taxprice"); // 改变后的含税单价
		BigDecimal bPrice = new BigDecimal(price);// 无税单价
		BigDecimal bTaxPrice = new BigDecimal(taxprice); // 含税单价
		BigDecimal bTaxRate = new BigDecimal(taxRate).divide(new BigDecimal(100));// 税率
		if ("1".equals(type)) {// 含税单价改变后，根据含税单价获取无税单价
			bPrice = bTaxPrice.divide(bTaxRate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
		} else {// 无税单价改变后，根据无税单价获取含税单价
			bTaxPrice = bPrice.multiply(bTaxRate.add(new BigDecimal(1)));
		}
		Map map = new HashMap();
		map.put("taxPrice", bTaxPrice);
		map.put("price", bPrice);
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 显示商品档案批量修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jul 10, 2013
	 */
	public String goodsInfoMoreEditPage() throws Exception {
		String idStr = request.getParameter("idStr");
		String unInvNum = request.getParameter("unInvNum");
		request.setAttribute("idStr", idStr);
		request.setAttribute("unInvNum", unInvNum);
		List goodstypeList = getBaseSysCodeService().showSysCodeListByType("goodstype");
		request.setAttribute("goodstypeList", goodstypeList);
        request.setAttribute("secondSupplierNum",secondSupplierNum);
		return SUCCESS;
	}

	/**
	 * 批量修改商品档案
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jul 10, 2013
	 */
	@UserOperateLog(key = "GoodsInfo", type = 3, value = "")
	public String editGoodsInfoMore() throws Exception {
		String idStr = request.getParameter("idStr");
		String sucStrids = null;
		int sucNum = 0, failNum = 0, unEditNum = 0, lockNum = 0;
		if (StringUtils.isNotEmpty(idStr)) {
			String[] idArr = idStr.substring(0, idStr.lastIndexOf(",")).split(",");
			if (idArr.length != 0) {
				for (String id : idArr) {
					GoodsInfo copyGoods = (GoodsInfo) CommonUtils.deepCopy(goodsInfo);
					// 商品档案基本信息
					copyGoods.setId(id);
					copyGoods.setOldId(id);
					GoodsInfo oldGoodsInfo = goodsService.showGoodsInfo(id);
					copyGoods.setEdittype("more");
					if (StringUtils.isEmpty(copyGoods.getBrand())) {
						copyGoods.setBrand(oldGoodsInfo.getBrand());
					} else {
						Brand brand = goodsService.getBrandInfo(copyGoods.getBrand());
						if (null != brand) {
							copyGoods.setDeptid(brand.getDeptid());
						}
					}
					if (StringUtils.isEmpty(copyGoods.getDefaultsupplier())) {
						copyGoods.setDefaultsupplier(oldGoodsInfo.getDefaultsupplier());
					}
					copyGoods.setOldbrand(oldGoodsInfo.getBrand());
					copyGoods.setOlddefaultsupplier(oldGoodsInfo.getDefaultsupplier());
                    //第二供应商
                    if(StringUtils.isEmpty(copyGoods.getSecondsupplier())){
                        copyGoods.setSecondsupplier(oldGoodsInfo.getSecondsupplier());
                    }
                    //保质期管理
                    if(StringUtils.isEmpty(copyGoods.getIsshelflife())){
                        copyGoods.setIsshelflife(oldGoodsInfo.getIsshelflife());
                    }
                    //保质期
                    if(null == copyGoods.getShelflife()){
                        copyGoods.setShelflife(oldGoodsInfo.getShelflife());
                    }
                    //保质期单位
                    if(StringUtils.isEmpty(copyGoods.getShelflifeunit())){
                        copyGoods.setShelflifeunit(oldGoodsInfo.getShelflifeunit());
                    }
					//购销类型
					if(StringUtils.isEmpty(copyGoods.getBstype())){
						copyGoods.setBstype(oldGoodsInfo.getBstype());
					}
					//商品货位
					if(StringUtils.isEmpty(copyGoods.getItemno())){
						copyGoods.setItemno(oldGoodsInfo.getItemno());
					}
					// 最高采购价
					copyGoods.setHighestbuyprice(oldGoodsInfo.getHighestbuyprice());
					// 最新采购价
					copyGoods.setNewbuyprice(oldGoodsInfo.getNewbuyprice());
					// 最新销售价
					copyGoods.setNewsaleprice(oldGoodsInfo.getNewsaleprice());
					//产地
					if(StringUtils.isEmpty(copyGoods.getProductfield())){
						copyGoods.setProductfield(oldGoodsInfo.getProductfield());
					}
					PageMap pageMap = new PageMap();
					pageMap.getCondition().put("goodsid", id);
					List<GoodsInfo_StorageInfo> sList = new ArrayList<GoodsInfo_StorageInfo>();
					List<GoodsStorageLocation> slList = new ArrayList<GoodsStorageLocation>();
					List<GoodsInfo_WaresClassInfo> wcList = new ArrayList<GoodsInfo_WaresClassInfo>();
					List<GoodsInfo_PriceInfo> pList = new ArrayList<GoodsInfo_PriceInfo>();
					boolean sflag = false, slflag = false, wcflag = false;
					// 默认仓库-对应仓库
					if (StringUtils.isNotEmpty(copyGoods.getStorageid())) {
						sList = goodsService.showStorageInfoList(pageMap);
						if (sList.size() != 0) {
							// 判断是否存在默认对应仓库，true 存在，fasle 不存在
							for (GoodsInfo_StorageInfo sInfo : sList) {
								if ("1".equals(sInfo.getIsdefault())) {
									sInfo.setStorageid(copyGoods.getStorageid());
									sflag = true;
									break;
								}
							}
							if (!sflag) {
								GoodsInfo_StorageInfo sInfo = new GoodsInfo_StorageInfo();
								sInfo.setGoodsid(id);
								sInfo.setIsdefault("1");
								sInfo.setStorageid(copyGoods.getStorageid());
								sList.add(sInfo);
							}
						} else {
							// 若默认对应仓库存在，则修改数据，反之，新增默认对应仓库
							GoodsInfo_StorageInfo sInfo = new GoodsInfo_StorageInfo();
							sInfo.setGoodsid(id);
							sInfo.setIsdefault("1");
							sInfo.setStorageid(copyGoods.getStorageid());
							sList.add(sInfo);
						}
					}
					// 默认库位-对应默认库位
					if (StringUtils.isNotEmpty(copyGoods.getStoragelocation())) {
						slList = goodsService.showGoodsStorageLocationList(pageMap);
						if (slList.size() != 0) {
							for (GoodsStorageLocation slInfo : slList) {
								if ("1".equals(slInfo.getIsdefault())) {
									slInfo.setStoragelocationid(copyGoods.getStoragelocation());
									slflag = true;
									break;
								}
							}
							if (!slflag) {
								GoodsStorageLocation slInfo = new GoodsStorageLocation();
								slInfo.setGoodsid(id);
								slInfo.setIsdefault("1");
								slInfo.setStoragelocationid(copyGoods.getStoragelocation());
								slList.add(slInfo);
							}
						} else {
							GoodsStorageLocation slInfo = new GoodsStorageLocation();
							slInfo.setGoodsid(id);
							slInfo.setIsdefault("1");
							slInfo.setStoragelocationid(copyGoods.getStoragelocation());
							slList.add(slInfo);
						}
					}
					// 默认分类-对应分类
					if (StringUtils.isNotEmpty(copyGoods.getDefaultsort())) {
						wcList = goodsService.showWaresClassInfoList(pageMap);
						if (wcList.size() != 0) {
							for (GoodsInfo_WaresClassInfo wcInfo : wcList) {
								if ("1".equals(wcInfo.getIsdefault())) {
									wcInfo.setWaresclass(copyGoods.getDefaultsort());
									wcflag = true;
									break;
								}
							}
							if (!wcflag) {
								GoodsInfo_WaresClassInfo wcInfo = new GoodsInfo_WaresClassInfo();
								wcInfo.setGoodsid(id);
								wcInfo.setIsdefault("1");
								wcInfo.setWaresclass(copyGoods.getDefaultsort());
								wcList.add(wcInfo);
							}
						} else {
							GoodsInfo_WaresClassInfo wcInfo = new GoodsInfo_WaresClassInfo();
							wcInfo.setGoodsid(id);
							wcInfo.setIsdefault("1");
							wcInfo.setWaresclass(copyGoods.getDefaultsort());
							wcList.add(wcInfo);
						}
					}
					// 默认税种-对应价格套管理
					if (StringUtils.isNotEmpty(copyGoods.getDefaulttaxtype())) {
						pList = goodsService.showPriceInfoList(pageMap);
						if (pList.size() != 0) {
							for (GoodsInfo_PriceInfo pInfo : pList) {
								pInfo.setTaxtype(copyGoods.getDefaulttaxtype());
								TaxType taxTypeInfo = financeService.getTaxTypeInfo(copyGoods.getDefaulttaxtype());
								if (taxTypeInfo != null) {
									pInfo.setTaxtypeName(taxTypeInfo.getName());
									pInfo.setTaxtypeRate(taxTypeInfo.getRate());
								}
							}
						} else {
							List<SysCode> list = sysCodeService.showSysCodeListByType("price_list");
							// 将编码表所定义的价格套加入到商品价格套管理中
							if (list.size() != 0) {
								for (SysCode sysCode : list) {
									GoodsInfo_PriceInfo priceInfo = new GoodsInfo_PriceInfo();
									priceInfo.setId(Integer.parseInt(sysCode.getCode()));
									priceInfo.setCode(sysCode.getCode());
									priceInfo.setName(sysCode.getCodename());
									priceInfo.setTaxtype(copyGoods.getDefaulttaxtype());
									TaxType taxTypeInfo = financeService.getTaxTypeInfo(copyGoods.getDefaulttaxtype());
									if (taxTypeInfo != null) {
										priceInfo.setTaxtypeName(taxTypeInfo.getName());
										priceInfo.setTaxtypeRate(taxTypeInfo.getRate());
										BigDecimal rate = taxTypeInfo.getRate().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1));
										if (null != priceInfo.getTaxprice()) {
											priceInfo.setPrice(priceInfo.getTaxprice().divide(rate, 6, BigDecimal.ROUND_HALF_UP));
										} else {
											priceInfo.setPrice(null);
										}
									}
									pList.add(priceInfo);
								}
							}
						}
					}
					Map map = goodsService.editGoodsInfo(copyGoods, pList, null, sList, wcList, slList);
					if (map.get("lockFlag").equals(true)) {
						if (map.get("unEditFlag").equals(true)) {
							if (map.get("retFlag").equals(true)) {
								sucNum++;
//								i = i + 180;
//								addEditGoodsTaskSchedule(copyGoods, oldGoodsInfo, i);
								if (StringUtils.isNotEmpty(sucStrids)) {
									sucStrids += "," + copyGoods.getId();
								} else {
									sucStrids = copyGoods.getId();
								}
							} else {
								failNum++;
							}
						} else {
							unEditNum++;
						}
					} else {
						lockNum++;
					}
				}
			}
		}
		if (sucNum > 0) {
			addLog("批量修改商品 编号:" + sucStrids, true);
		}
		Map map = new HashMap();
		map.put("sucNum", sucNum);
		map.put("failNum", failNum);
		map.put("unEditNum", unEditNum);
		map.put("lockNum", lockNum);
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 显示商品选择页面
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 2, 2013
	 */
	public String showGoodsSelectPage() throws Exception {
		String id = request.getParameter("id");
		String divid = request.getParameter("divid");
		String paramRule = request.getParameter("paramRule");
		String dialog = request.getParameter("dialog");
		request.setAttribute("paramRule", paramRule);
		request.setAttribute("divid", divid);
		request.setAttribute("id", id);
		request.setAttribute("dialog", dialog);
		// 获取商品可用量字段权限
		Map colMap = getAccessColumn("t_storage_summary");
		request.setAttribute("colMap", colMap);
		return "success";
	}

	/**
	 * 根据id值获取商品列表 id查询商品编码 商品条形码 商品助记码
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 2, 2013
	 */
	public String getGoodsSelectListData() throws Exception {
		Map map = CommonUtils.changeMapByEscapeSql(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = goodsService.getGoodsSelectListData(pageMap);
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 根据id值获取客户列表
	 * id查询客户编号 客户助记码
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date 2018-2-24
	 */
	public String getGoodsSelectListDataSimple() throws Exception {
		String id = request.getParameter("id");
		Map map = new HashMap();
		if(StringUtils.isNotEmpty(id)){
			map.put("id",id);
		}
		pageMap.setCondition(map);
		List<GoodsInfo> list = goodsService.getGoodsSelectListDataSimple(pageMap);
		Map resultMap = new HashMap();
		resultMap.put("listStr",list);
		addJSONObject(resultMap);
		return SUCCESS;
	}
	/*----------------------------------商品档案快捷新增--------------------------------*/

	/**
	 * 显示商品档案快捷页面
	 */
	public String showGoodsShortcutPage() throws Exception {
		// 价格套列表
		List<SysCode> priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		request.setAttribute("priceList", priceList);
		SysParam sysParam = getBaseSysParamService().getSysParam("PRICENUM");
		if (null != sysParam) {
			request.setAttribute("pricenum", sysParam.getPvalue());
		}
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}

	/**
	 * 显示商品档案新增快捷页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 3, 2013
	 */
	public String showGoodsShortcutAddPage() throws Exception {
		// 价格套
		List priceList = super.getBaseSysCodeService().showSysCodeListByType("price_list");
		if (priceList.size() != 0) {
			request.setAttribute("priceList", priceList);
		}
		// 商品类型
		List goodstypeList = super.getBaseSysCodeService().showSysCodeListByType("goodstype");
		// 购销类型
		List bstypeList = super.getBaseSysCodeService().showSysCodeListByType("bstype");
		SysParam sysParam = getBaseSysParamService().getSysParam("DEFAULTAXTYPE");
		if (null != sysParam) {
			request.setAttribute("defaulttaxtype", sysParam.getPvalue());
		}
		request.setAttribute("goodstypeList", goodstypeList);
		request.setAttribute("bstypeList", bstypeList);
		return SUCCESS;
	}

	/**
	 * 显示商品档案修改快捷页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 3, 2013
	 */
	public String showGoodsShortcutEditPage() throws Exception {
		String id = request.getParameter("id");
		GoodsInfo goodsInfo = goodsService.showGoodsInfo(id);
		if (null != goodsInfo) {
			// 显示修改页面时，给数据加锁,true：可以操作,false:不可以操作
			if (lockData("t_base_goods_info", id)) {
				Map map = new HashMap();
				map.put("id", goodsInfo.getDefaultsupplier());
				BuySupplier buySupplier = super.getBaseBuyService().getBuySupplierBy(map);
				if (null != buySupplier) {
					goodsInfo.setDefaultsupplierName(buySupplier.getName());
				}
				List<SysCode> priceList = super.getBaseSysCodeService().showSysCodeListByType("price_list");
				List<GoodsInfo_PriceInfo> priceList2 = goodsService.getPriceListByGoodsid(id);
				if (priceList2.size() != 0) {
					for (GoodsInfo_PriceInfo priceInfo : priceList2) {
						if (priceList.size() != 0) {
							for (SysCode sysCode : priceList) {
								if (priceInfo.getCode().equals(sysCode.getCode())) {
									sysCode.setVal(priceInfo.getTaxprice());
                                    sysCode.setBoxval(priceInfo.getBoxprice());
								}
							}
						}
					}
				}
				request.setAttribute("priceList", priceList);
				// 辅计量单位显示
				List<GoodsInfo_MteringUnitInfo> muList = goodsService.getMUListByGoodsId(id);
				if (muList.size() != 0) {
					for (GoodsInfo_MteringUnitInfo muInfo : muList) {
						if ("1".equals(muInfo.getIsdefault())) {
							request.setAttribute("goodsMUInfo", muInfo);
						}
					}
				}
				// 商品类型
				List goodstypeList = super.getBaseSysCodeService().showSysCodeListByType("goodstype");
				// 购销类型
				List bstypeList = super.getBaseSysCodeService().showSysCodeListByType("bstype");
				request.setAttribute("goodstypeList", goodstypeList);
				request.setAttribute("bstypeList", bstypeList);
				request.setAttribute("oldId", id);
				request.setAttribute("goodsInfo", goodsInfo);
				request.setAttribute("editFlag", canTableDataDelete("t_base_goods_info", id));
				request.setAttribute("lockFlag", "1");// 解锁
			} else {
				request.setAttribute("lockFlag", "0");// 加锁
			}
			// 获取t_base_goods_info表的字段查看权限
			Map colMap = getEditAccessColumn("t_base_goods_info");
			request.setAttribute("showMap", colMap);
		}
		return SUCCESS;
	}

	/**
	 * 显示商品档案复制快捷页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 3, 2013
	 */
	public String showGoodsShortcutCopyPage() throws Exception {
		String id = request.getParameter("id");
		GoodsInfo goodsInfo = goodsService.showGoodsInfo(id);
		Map map = new HashMap();
		map.put("id", goodsInfo.getDefaultsupplier());
		BuySupplier buySupplier = super.getBaseBuyService().getBuySupplierBy(map);
		if (null != buySupplier) {
			goodsInfo.setDefaultsupplierName(buySupplier.getName());
		}
		List<SysCode> priceList = super.getBaseSysCodeService().showSysCodeListByType("price_list");
		List<GoodsInfo_PriceInfo> priceList2 = goodsService.getPriceListByGoodsid(id);
		if (priceList2.size() != 0) {
			for (GoodsInfo_PriceInfo priceInfo : priceList2) {
				if (priceList.size() != 0) {
					for (SysCode sysCode : priceList) {
						if (priceInfo.getCode().equals(sysCode.getCode())) {
							sysCode.setVal(priceInfo.getTaxprice());
                            sysCode.setBoxval(priceInfo.getBoxprice());
						}
					}
				}
			}
		}
		request.setAttribute("priceList", priceList);
		List<GoodsInfo_MteringUnitInfo> muList = goodsService.getMUListByGoodsId(id);
		if (muList.size() != 0) {
			for (GoodsInfo_MteringUnitInfo muInfo : muList) {
				if ("1".equals(muInfo.getIsdefault())) {
					request.setAttribute("goodsMUInfo", muInfo);
				}
			}
		}
		// 商品类型
		List goodstypeList = super.getBaseSysCodeService().showSysCodeListByType("goodstype");
		// 购销类型
		List bstypeList = super.getBaseSysCodeService().showSysCodeListByType("bstype");
		request.setAttribute("goodstypeList", goodstypeList);
		request.setAttribute("bstypeList", bstypeList);
		request.setAttribute("goodsInfo", goodsInfo);
		return SUCCESS;
	}

	/**
	 * 显示商品档案查看快捷页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 3, 2013
	 */
	public String showGoodsShortcutViewPage() throws Exception {
		String id = request.getParameter("id");
		GoodsInfo goodsInfo = goodsService.showGoodsInfo(id);
		Map map = new HashMap();
		map.put("id", goodsInfo.getDefaultsupplier());
		BuySupplier buySupplier = super.getBaseBuyService().getBuySupplierBy(map);
		if (null != buySupplier) {
			goodsInfo.setDefaultsupplierName(buySupplier.getName());
		}
		// 价格套管理显示
		List<SysCode> priceList = super.getBaseSysCodeService().showSysCodeListByType("price_list");
		List<GoodsInfo_PriceInfo> priceList2 = goodsService.getPriceListByGoodsid(id);
		if (priceList2.size() != 0) {
			for (GoodsInfo_PriceInfo priceInfo : priceList2) {
				if (priceList.size() != 0) {
					for (SysCode sysCode : priceList) {
						if (priceInfo.getCode().equals(sysCode.getCode())) {
							sysCode.setVal(priceInfo.getTaxprice());
                            sysCode.setBoxval(priceInfo.getBoxprice());
						}
					}
				}
			}
		}
		request.setAttribute("priceList", priceList);
		// 辅计量单位显示
		List<GoodsInfo_MteringUnitInfo> muList = goodsService.getMUListByGoodsId(id);
		if (muList.size() != 0) {
			for (GoodsInfo_MteringUnitInfo muInfo : muList) {
				if ("1".equals(muInfo.getIsdefault())) {
					request.setAttribute("goodsMUInfo", muInfo);
				}
			}
		}
		// 商品类型
		List goodstypeList = super.getBaseSysCodeService().showSysCodeListByType("goodstype");
		// 购销类型
		List bstypeList = super.getBaseSysCodeService().showSysCodeListByType("bstype");
		request.setAttribute("goodstypeList", goodstypeList);
		request.setAttribute("bstypeList", bstypeList);
		request.setAttribute("goodsInfo", goodsInfo);
		// 获取t_base_goods_info表的字段查看权限
		Map colMap = getAccessColumn("t_base_goods_info");
		request.setAttribute("showMap", colMap);
		return SUCCESS;
	}

	/**
	 * 快捷新增商品
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 3, 2013
	 */
	@UserOperateLog(key = "GoodsInfo", type = 2, value = "")
	public String addGoodsInfoShortcut() throws Exception {
		SysUser sysUser = getSysUser();
		goodsInfo.setAdddeptid(sysUser.getDepartmentid());
		goodsInfo.setAdddeptname(sysUser.getDepartmentname());
		goodsInfo.setAdduserid(sysUser.getUserid());
		goodsInfo.setAddusername(sysUser.getName());
		Date nowDate=new Date();
		if(StringUtils.isNotEmpty(goodsInfo.getJsgoodsid())){
			goodsInfo.setJsgoodsmodifytime(nowDate);
			goodsInfo.setJsgoodsmodifyuserid(sysUser.getUserid());
			goodsInfo.setJsgoodsmodifyusername(sysUser.getName());
		}
		// 判断商品编码是否重复, true 重复，false 不重复
		if (StringUtils.isNotEmpty(goodsInfo.getId())) {
			boolean isRepeatId = goodsService.isRepeatGoodsInfoID(goodsInfo.getId());
			if (!isRepeatId) {
				boolean flag = goodsService.addGoodsInfoShortcut(goodsInfo, priceInfoList, goodsMUInfo);
				addLog("新增商品 编号:" + goodsInfo.getId(), flag);
				addJSONObject("flag", flag);
			}
		}
		return SUCCESS;
	}

	/**
	 * 快捷修改商品
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 5, 2013
	 */
	@UserOperateLog(key = "GoodsInfo", type = 3, value = "")
	public String editGoodsInfoShortcut() throws Exception {
		SysUser sysUser = getSysUser();
		goodsInfo.setModifyuserid(sysUser.getUserid());
		goodsInfo.setModifyusername(sysUser.getName());
		if (!"0".equals(goodsInfo.getState()) && !"1".equals(goodsInfo.getState())) {
			goodsInfo.setState("2");
		}
		GoodsInfo oldGoodsInfo = goodsService.showGoodsInfo(goodsInfo.getOldId());
		if(null!=oldGoodsInfo){
			boolean isnewjsgoods=StringUtils.isEmpty(goodsInfo.getJsgoodsid());
			boolean isoldjsgoods=StringUtils.isEmpty(oldGoodsInfo.getJsgoodsid());

			if(!(isnewjsgoods && isoldjsgoods) &&
					!(!isoldjsgoods && oldGoodsInfo.getJsgoodsid().equals(goodsInfo.getJsgoodsid()))){
				goodsInfo.setJsgoodsmodifyuserid(sysUser.getUserid());
				goodsInfo.setJsgoodsmodifyusername(sysUser.getUsername());
				goodsInfo.setJsgoodsmodifytime(new Date());
			}
		}
		Map map = goodsService.editGoodsInfoShortcut(goodsInfo, priceInfoList, goodsMUInfo);
		if (map.get("flag").equals(true)) {
			if (null != goodsMUInfo) {
				MeteringUnit meteringUnit = goodsService.showMeteringUnitInfo(goodsInfo.getMainunit());
				if (null != meteringUnit) {
					goodsInfo.setMainunitName(meteringUnit.getName());
				}
				goodsInfo.setAuxunitid(goodsMUInfo.getMeteringunitid());
				MeteringUnit meteringUnit2 = goodsService.showMeteringUnitInfo(goodsMUInfo.getMeteringunitid());
				if (null != meteringUnit2) {
					goodsInfo.setAuxunitname(meteringUnit2.getName());
				}
			}
//			addEditGoodsTaskSchedule(goodsInfo, oldGoodsInfo, 1);
		}
		addLog("修改商品 编号:" + goodsInfo.getId(), map.get("flag").equals(true));
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 添加商品修改定时器
	 * 
	 * @param goodsInfo
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Mar 4, 2014
	 */
	private void addEditGoodsTaskSchedule(GoodsInfo goodsInfo, GoodsInfo oldGoodsInfo, int i) throws Exception {
		if (goodsInfo.getOldId().equals(goodsInfo.getId())) {
			if ((null != goodsInfo.getOldbrand() && !goodsInfo.getOldbrand().equals(goodsInfo.getBrand())) || (null != goodsInfo.getOlddefaultsupplier() && !goodsInfo.getOlddefaultsupplier().equals(goodsInfo.getDefaultsupplier())) || (StringUtils.isNotEmpty(goodsInfo.getDefaultsort()) && !oldGoodsInfo.getDefaultsort().equals(goodsInfo.getDefaultsort()) || (null != goodsInfo.getMainunit() && !goodsInfo.getMainunit().equals(oldGoodsInfo.getMainunit())) || (null != goodsInfo.getAuxunitid() && !goodsInfo.getAuxunitid().equals(oldGoodsInfo.getAuxunitid())))) {
				String taskid = CommonUtils.getDataNumberSendsWithRand();
				Calendar nowTime = Calendar.getInstance();
				nowTime.add(Calendar.MINUTE, 1);
				nowTime.add(Calendar.SECOND, i);
				Date afterDate = (Date) nowTime.getTime();

				// 把执行时间转成quartz表达式 适合单次执行
				String con = CommonUtils.getQuartzCronExpression(afterDate);
				// 品牌部门
				Brand brand = goodsService.getBrandInfo(goodsInfo.getBrand());
				if (null != brand) {
					goodsInfo.setBranddept(brand.getDeptid());
				}
				Map dataMap = new HashMap();
				dataMap.put("goodsInfo", goodsInfo);
				dataMap.put("oldGoodsInfo", oldGoodsInfo);
				taskScheduleService.addTaskScheduleAndStart(taskid, "商品修改后更新相关单据", "com.hd.agent.basefiles.job.GoodsChangeJob", "goods", con, "1", dataMap);
			}
		}
	}

	/*----------------商品档案精简版-------------------*/

	/**
	 * 商品档案列表
	 */
	public String showGoodsSimplifyListPage() throws Exception {
		// 价格套列表
		List<SysCode> priceList = getBaseSysCodeService().showSysCodeListByType("price_list");
		request.setAttribute("priceList", priceList);
		request.setAttribute("listtype", "2");
		SysParam sysParam = getBaseSysParamService().getSysParam("PRICENUM");
		if (null != sysParam) {
			request.setAttribute("pricenum", sysParam.getPvalue());
		}
		Map colMap2 = getRequiredColumn("t_base_goods_info");
		Map colMap3 = getRequiredColumn("t_base_goods_info_meteringunit");
		colMap2.putAll(colMap3);
		request.setAttribute("colMap", colMap2);
        request.setAttribute("secondSupplierNum",secondSupplierNum);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		String jsGoodsVersion=getSysParamValue("HTKPJSGOODSVERSION");
		if(null==jsGoodsVersion || "".equals(jsGoodsVersion.trim())){
			jsGoodsVersion="12.0";
		}
		request.setAttribute("jsGoodsVersion",jsGoodsVersion);
		String jsGoodsidLen=getSysParamValue("JSKPGOODSIDLEN");
		if(null==jsGoodsidLen || "".equals(jsGoodsidLen.trim())){
			jsGoodsidLen="4";
		}
		request.setAttribute("jsGoodsidLen",jsGoodsidLen);
		return SUCCESS;
	}

	/**
	 * 商品档案页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Mar 17, 2014
	 */
	public String showGoodsSimplifyPage() throws Exception {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String state = request.getParameter("state");
		String WCid = request.getParameter("WCid");
		String listType = request.getParameter("listType");
		if ("1".equals(listType)) {
			request.setAttribute("tab", "/basefiles/showPriceListPage.do");
			request.setAttribute("listid", "wares-table-goodsInfoToPriceList");
		} else if ("2".equals(listType)) {
			request.setAttribute("tab", "/basefiles/showGoodsInfoListPage.do");
			request.setAttribute("listid", "wares-table-goodsInfoList");
		}
		if (StringUtils.isEmpty(id)) {
			id = "";
		}
		if (StringUtils.isEmpty(state)) {
			state = "";
		}
		if (StringUtils.isEmpty(WCid)) {
			WCid = "";
		}
		request.setAttribute("listType", listType);
		request.setAttribute("type", type);
		request.setAttribute("id", id);
		request.setAttribute("goodsid", id);
		request.setAttribute("state", state);
		request.setAttribute("WCid", WCid);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}

	/**
	 * 显示商品档案精简版新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Mar 17, 2014
	 */
	public String showGoodsSimplifyAddPage() throws Exception {
		String WCid = request.getParameter("WCid");
		request.setAttribute("WCid", WCid);
		// 价格套
		List priceList = super.getBaseSysCodeService().showSysCodeListByType("price_list");
		if (priceList.size() != 0) {
			request.setAttribute("priceList", priceList);
		}
		// 商品类型
		List goodstypeList = super.getBaseSysCodeService().showSysCodeListByType("goodstype");
		// 购销类型
		List bstypeList = super.getBaseSysCodeService().showSysCodeListByType("bstype");
		SysParam sysParam = getBaseSysParamService().getSysParam("DEFAULTAXTYPE");
		if (null != sysParam) {
			request.setAttribute("defaulttaxtype", sysParam.getPvalue());
		}
		request.setAttribute("goodstypeList", goodstypeList);
		request.setAttribute("bstypeList", bstypeList);
		Map colMap = getRequiredColumn("t_base_goods_info");
		Map colMap3 = getRequiredColumn("t_base_goods_info_meteringunit");
		colMap.putAll(colMap3);
		request.setAttribute("colMap", colMap);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		String jsGoodsVersion=getSysParamValue("HTKPJSGOODSVERSION");
		if(null==jsGoodsVersion || "".equals(jsGoodsVersion.trim())){
			jsGoodsVersion="12.0";
		}
		request.setAttribute("jsGoodsVersion",jsGoodsVersion);
		String jsGoodsidLen=getSysParamValue("JSKPGOODSIDLEN");
		if(null==jsGoodsidLen || "".equals(jsGoodsidLen.trim())){
			jsGoodsidLen="4";
		}
		request.setAttribute("jsGoodsidLen",jsGoodsidLen);
		return SUCCESS;
	}

	/**
	 * 显示商品档案精简版查看页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Mar 17, 2014
	 */
	public String showGoodsSimplifyViewPage() throws Exception {
		String id = request.getParameter("id");
		GoodsInfo goodsInfo = goodsService.showGoodsInfo(id);
		Map map = new HashMap();
		map.put("id", goodsInfo.getDefaultsupplier());
		BuySupplier buySupplier = super.getBaseBuyService().getBuySupplierBy(map);
		if (null != buySupplier) {
			goodsInfo.setDefaultsupplierName(buySupplier.getName());
		}
		// 价格套管理显示
		List<SysCode> priceList = super.getBaseSysCodeService().showSysCodeListByType("price_list");
		List<GoodsInfo_PriceInfo> priceList2 = goodsService.getPriceListByGoodsid(id);
		if (priceList2.size() != 0) {
			for (GoodsInfo_PriceInfo priceInfo : priceList2) {
				if (priceList.size() != 0) {
					for (SysCode sysCode : priceList) {
						if (null != priceInfo.getCode() && priceInfo.getCode().equals(sysCode.getCode())) {
							sysCode.setVal(priceInfo.getTaxprice());
                            sysCode.setBoxval(priceInfo.getBoxprice());
						}
					}
				}
			}
		}
		request.setAttribute("priceList", priceList);
		// 辅计量单位显示
		List<GoodsInfo_MteringUnitInfo> muList = goodsService.getMUListByGoodsId(id);
		if (muList.size() != 0) {
			for (GoodsInfo_MteringUnitInfo muInfo : muList) {
				if ("1".equals(muInfo.getIsdefault())) {
					request.setAttribute("goodsMUInfo", muInfo);
				}
			}
		}
		// 商品类型
		List goodstypeList = super.getBaseSysCodeService().showSysCodeListByType("goodstype");
		// 购销类型
		List bstypeList = super.getBaseSysCodeService().showSysCodeListByType("bstype");
		request.setAttribute("goodstypeList", goodstypeList);
		request.setAttribute("bstypeList", bstypeList);
		request.setAttribute("goodsInfo", goodsInfo);
		// 获取t_base_goods_info表的字段编辑权限
		Map colMap = getEditAccessColumn("t_base_goods_info");
		request.setAttribute("showMap", colMap);
		Map colMap2 = getRequiredColumn("t_base_goods_info");
		Map colMap3 = getRequiredColumn("t_base_goods_info_meteringunit");
		colMap2.putAll(colMap3);
		request.setAttribute("colMap", colMap2);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		String jsGoodsVersion=getSysParamValue("HTKPJSGOODSVERSION");
		if(null==jsGoodsVersion || "".equals(jsGoodsVersion.trim())){
			jsGoodsVersion="12.0";
		}
		request.setAttribute("jsGoodsVersion",jsGoodsVersion);
		String jsGoodsidLen=getSysParamValue("JSKPGOODSIDLEN");
		if(null==jsGoodsidLen || "".equals(jsGoodsidLen.trim())){
			jsGoodsidLen="4";
		}
		request.setAttribute("jsGoodsidLen",jsGoodsidLen);
		return SUCCESS;
	}

	/**
	 * 显示商品档案精简版修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Mar 18, 2014
	 */
	public String showGoodsSimplifyEditPage() throws Exception {
		String id = request.getParameter("id");
		GoodsInfo goodsInfo = goodsService.showGoodsInfo(id);
		if (null != goodsInfo) {
			// 显示修改页面时，给数据加锁,true：可以操作,false:不可以操作
			if (lockData("t_base_goods_info", id)) {
				Map map = new HashMap();
				map.put("id", goodsInfo.getDefaultsupplier());
				BuySupplier buySupplier = super.getBaseBuyService().getBuySupplierBy(map);
				if (null != buySupplier) {
					goodsInfo.setDefaultsupplierName(buySupplier.getName());
				}
				List<SysCode> priceList = super.getBaseSysCodeService().showSysCodeListByType("price_list");
				List<GoodsInfo_PriceInfo> priceList2 = goodsService.getPriceListByGoodsid(id);
				if (priceList2.size() != 0) {
					for (GoodsInfo_PriceInfo priceInfo : priceList2) {
						if (priceList.size() != 0) {
							for (SysCode sysCode : priceList) {
								if (priceInfo.getCode().equals(sysCode.getCode())) {
									sysCode.setVal(priceInfo.getTaxprice());
                                    sysCode.setBoxval(priceInfo.getBoxprice());
								}
							}
						}
					}
				}
				request.setAttribute("priceList", priceList);
				// 辅计量单位显示
				List<GoodsInfo_MteringUnitInfo> muList = goodsService.getMUListByGoodsId(id);
				if (muList.size() != 0) {
					for (GoodsInfo_MteringUnitInfo muInfo : muList) {
						if ("1".equals(muInfo.getIsdefault())) {
							request.setAttribute("goodsMUInfo", muInfo);
						}
					}
				}
				// 商品类型
				List goodstypeList = super.getBaseSysCodeService().showSysCodeListByType("goodstype");
				// 购销类型
				List bstypeList = super.getBaseSysCodeService().showSysCodeListByType("bstype");
				request.setAttribute("goodstypeList", goodstypeList);
				request.setAttribute("bstypeList", bstypeList);
				request.setAttribute("oldId", id);
				request.setAttribute("goodsInfo", goodsInfo);
				request.setAttribute("editFlag", canTableDataDelete("t_base_goods_info", id));
				request.setAttribute("lockFlag", "1");// 解锁
			} else {
				request.setAttribute("lockFlag", "0");// 加锁
			}
			// 获取t_base_goods_info表的字段编辑权限
			Map colMap = getEditAccessColumn("t_base_goods_info");
			request.setAttribute("showMap", colMap);
			Map colMap2 = getRequiredColumn("t_base_goods_info");
			Map colMap3 = getRequiredColumn("t_base_goods_info_meteringunit");
			colMap2.putAll(colMap3);
			request.setAttribute("colMap", colMap2);
			request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
			String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
			if(!"1".equals(useHTKPExport)){
				useHTKPExport="0";
			}
			request.setAttribute("useHTKPExport",useHTKPExport);
			String jsGoodsVersion=getSysParamValue("HTKPJSGOODSVERSION");
			if(null==jsGoodsVersion || "".equals(jsGoodsVersion.trim())){
				jsGoodsVersion="12.0";
			}
			request.setAttribute("jsGoodsVersion",jsGoodsVersion);
			String jsGoodsidLen=getSysParamValue("JSKPGOODSIDLEN");
			if(null==jsGoodsidLen || "".equals(jsGoodsidLen.trim())){
				jsGoodsidLen="4";
			}
			request.setAttribute("jsGoodsidLen",jsGoodsidLen);
		}
		return SUCCESS;
	}

	/**
	 * 显示商品档案精简版复制页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Mar 18, 2014
	 */
	public String showGoodsSimplifyCopyPage() throws Exception {
		String id = request.getParameter("id");
		GoodsInfo goodsInfo = goodsService.showGoodsInfo(id);
		Map map = new HashMap();
		map.put("id", goodsInfo.getDefaultsupplier());
		BuySupplier buySupplier = super.getBaseBuyService().getBuySupplierBy(map);
		if (null != buySupplier) {
			goodsInfo.setDefaultsupplierName(buySupplier.getName());
		}
		List<SysCode> priceList = super.getBaseSysCodeService().showSysCodeListByType("price_list");
		List<GoodsInfo_PriceInfo> priceList2 = goodsService.getPriceListByGoodsid(id);
		if (priceList2.size() != 0) {
			for (GoodsInfo_PriceInfo priceInfo : priceList2) {
				if (priceList.size() != 0) {
					for (SysCode sysCode : priceList) {
						if (priceInfo.getCode().equals(sysCode.getCode())) {
							sysCode.setVal(priceInfo.getTaxprice());
                            sysCode.setBoxval(priceInfo.getBoxprice());
						}
					}
				}
			}
		}
		request.setAttribute("priceList", priceList);
		List<GoodsInfo_MteringUnitInfo> muList = goodsService.getMUListByGoodsId(id);
		if (muList.size() != 0) {
			for (GoodsInfo_MteringUnitInfo muInfo : muList) {
				if ("1".equals(muInfo.getIsdefault())) {
					request.setAttribute("goodsMUInfo", muInfo);
				}
			}
		}
		// 商品类型
		List goodstypeList = super.getBaseSysCodeService().showSysCodeListByType("goodstype");
		// 购销类型
		List bstypeList = super.getBaseSysCodeService().showSysCodeListByType("bstype");
		request.setAttribute("goodstypeList", goodstypeList);
		request.setAttribute("bstypeList", bstypeList);
		request.setAttribute("goodsInfo", goodsInfo);
		Map colMap2 = getRequiredColumn("t_base_goods_info");
		request.setAttribute("colMap", colMap2);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		String useHTKPExport=getSysParamValue("USEHTKPEXPORT");
		if(!"1".equals(useHTKPExport)){
			useHTKPExport="0";
		}
		request.setAttribute("useHTKPExport",useHTKPExport);
		String jsGoodsVersion=getSysParamValue("HTKPJSGOODSVERSION");
		if(null==jsGoodsVersion || "".equals(jsGoodsVersion.trim())){
			jsGoodsVersion="12.0";
		}
		request.setAttribute("jsGoodsVersion",jsGoodsVersion);
		String jsGoodsidLen=getSysParamValue("JSKPGOODSIDLEN");
		if(null==jsGoodsidLen || "".equals(jsGoodsidLen.trim())){
			jsGoodsidLen="4";
		}
		request.setAttribute("jsGoodsidLen",jsGoodsidLen);
		return SUCCESS;
	}

	/**
	 * 显示商品档案精简版批量修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Mar 18, 2014
	 */
	public String showGoodsSimplifyMoreEditPage() throws Exception {
		String idStr = request.getParameter("idStr");
		String unInvNum = request.getParameter("unInvNum");
		request.setAttribute("idStr", idStr);
		request.setAttribute("unInvNum", unInvNum);
		List goodstypeList = getBaseSysCodeService().showSysCodeListByType("goodstype");
		request.setAttribute("goodstypeList", goodstypeList);
        request.setAttribute("secondSupplierNum",secondSupplierNum);
		// 购销类型
		List bstypeList = super.getBaseSysCodeService().showSysCodeListByType("bstype");
		request.setAttribute("bstypeList", bstypeList);
		return SUCCESS;
	}

	/**
	 * 导出商品档案精简版列表数据
	 * 
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Mar 18, 2014
	 */
	public void exportGoosSimplifyListData() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		String title = "";
		if (map.containsKey("excelTitle")) {
			title = map.get("excelTitle").toString();
		} else {
			title = "list";
		}
		if (StringUtils.isEmpty(title)) {
			title = "list";
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		// 根据定义类型获取显示的字段
		firstMap.put("id", "商品编码");
		firstMap.put("name", "商品名称");
		firstMap.put("model", "规格型号");
		firstMap.put("spell", "助记符");
		firstMap.put("barcode", "条形码");
		firstMap.put("boxbarcode", "箱装条形码");
		firstMap.put("defaultsortName", "商品分类");
		firstMap.put("brandName", "商品品牌");
		firstMap.put("deptname", "所属部门");
		firstMap.put("defaultsupplierName", "默认供应商");
		firstMap.put("secondsuppliername", "第二供应商");
		firstMap.put("glength", "长度");
		firstMap.put("ghight", "高度");
		firstMap.put("gwidth", "宽度");
		firstMap.put("totalweight", "箱重");
		firstMap.put("totalvolume", "箱体积");
		firstMap.put("mainunitName", "主单位");
		firstMap.put("auxunitname", "辅单位");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("goodstypeName", "商品类型");
		firstMap.put("storageName", "默认仓库");
		firstMap.put("isstoragelocationname", "库位管理");
		firstMap.put("itemno", "商品货位");
		firstMap.put("bstypeName", "购销类型");
		firstMap.put("isshelflifename", "保质期管理");
		firstMap.put("shelflifedetail", "保质期");
		firstMap.put("isbatchname", "批次管理");
		firstMap.put("highestbuyprice", "采购价");
		firstMap.put("lowestsaleprice", "最低销售价");
		firstMap.put("basesaleprice", "基准销售价");
		firstMap.put("newbuyprice", "最新采购价");
        firstMap.put("field12", "成本未分摊金额");
		// firstMap.put("newsaleprice", "最新销售价");
		firstMap.put("defaulttaxtypeName", "默认税种");
		firstMap.put("minimum", "最小发货单位");
        firstMap.put("productfield","产地");
		// 价格套列表
		List<SysCode> priceList = getGoodsPriceList();
		for (SysCode sysCode : priceList) {
			firstMap.put("price" + sysCode.getCode(), sysCode.getCodename());
            firstMap.put("boxprice" + sysCode.getCode(), sysCode.getCodename()+"箱价");
		}
		firstMap.put("remark", "备注");
		firstMap.put("stateName", "状态");
		result.add(firstMap);

		List<GoodsInfo> list = excelService.getGoodsList(pageMap);
		for (GoodsInfo goodsInfo : list) {
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2 = PropertyUtils.describe(goodsInfo);
			for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
				if (map2.containsKey(fentry.getKey())) { // 如果记录中包含该Key，则取该Key的Value
					for (Map.Entry<String, Object> entry : map2.entrySet()) {
						if (fentry.getKey().equals(entry.getKey())) {
							objectCastToRetMap(retMap, entry);
						}
					}
				} else {
					retMap.put(fentry.getKey(), "");
				}
			}
			result.add(retMap);
		}
		ExcelUtils.exportExcel(result, title);
	}

	/**
	 * 导入商品档案精简版导入
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Mar 18, 2014
	 */
	@UserOperateLog(key = "GoodsInfo", type = 2, value = "商品档案导入")
	public String importGoosSimplifyListData() throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
        int index = 0 ;
		try {
			String clazz = "goodsService", meth = "addShortcutGoodsExcel", module = "basefiles", pojo = "GoodsInfo";
			Object object2 = SpringContextUtils.getBean(clazz);
			Class entity = Class.forName("com.hd.agent." + module + ".model." + pojo);
			Method[] methods = object2.getClass().getMethods();
			Method method = null;
			for (Method m : methods) {
				if (m.getName().equals(meth)) {
					method = m;
				}
			}
            List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); // 获取第一行数据为字段的描述列表
            ++ index ;
			List<String> paramList2 = new ArrayList<String>();
			for (String str : paramList) {
				if ("商品编码".equals(str)) {
					paramList2.add("id");
				} else if ("商品名称".equals(str)) {
					paramList2.add("name");
				} else if ("规格型号".equals(str)) {
					paramList2.add("model");
				} else if ("助记符".equals(str)) {
					paramList2.add("spell");
				} else if ("条形码".equals(str)) {
					paramList2.add("barcode");
				} else if ("箱装条形码".equals(str)) {
					paramList2.add("boxbarcode");
				} else if ("商品分类".equals(str)) {
					paramList2.add("defaultsortName");
				} else if ("商品品牌".equals(str)) {
					paramList2.add("brandName");
				} else if ("所属部门".equals(str)) {
					paramList2.add("deptname");
				} else if ("默认供应商".equals(str)) {
					paramList2.add("defaultsupplierName");
				} else if ("第二供应商".equals(str)) {
					paramList2.add("secondsuppliername");
				} else if ("长度".equals(str)) {
					paramList2.add("glength");
				} else if ("高度".equals(str)) {
					paramList2.add("ghight");
				} else if ("宽度".equals(str)) {
					paramList2.add("gwidth");
				} else if ("箱重".equals(str)) {
					paramList2.add("totalweight");
				} else if ("箱体积".equals(str)) {
					paramList2.add("totalvolume");
				} else if ("主单位".equals(str)) {
					paramList2.add("mainunitName");
				} else if ("辅单位".equals(str)) {
					paramList2.add("auxunitname");
				} else if ("箱装量".equals(str)) {
					paramList2.add("boxnum");
				} else if ("商品类型".equals(str)) {
					paramList2.add("goodstypeName");
				} else if ("默认仓库".equals(str)) {
					paramList2.add("storageName");
				} else if ("库位管理".equals(str)) {
					paramList2.add("isstoragelocationname");
				} else if ("保质期管理".equals(str)) {
					paramList2.add("isshelflifename");
				} else if ("保质期".equals(str)) {
					paramList2.add("shelflifedetail");
				} else if ("商品货位".equals(str)) {
					paramList2.add("itemno");
				} else if ("购销类型".equals(str)) {
					paramList2.add("bstypeName");
				} else if ("批次管理".equals(str)) {
					paramList2.add("isbatchname");
				} else if ("采购价".equals(str)) {
					paramList2.add("highestbuyprice");
				} else if ("最低销售价".equals(str)) {
					paramList2.add("lowestsaleprice");
				} else if ("基准销售价".equals(str)) {
					paramList2.add("basesaleprice");
				} else if ("最新采购价".equals(str)) {
					paramList2.add("newbuyprice");
				} else if ("产地".equals(str)) {
                    paramList2.add("productfield");
                }
				// else if("最新销售价".equals(str)){
				// paramList2.add("newsaleprice");
				// }
				else if ("默认税种".equals(str)) {
					paramList2.add("defaulttaxtypeName");
				} else if ("最小发货单位".equals(str)) {
					paramList2.add("minimum");
				} else if (isExistPriceCodeName(str)) {
					String code = getBaseSysCodeService().codenametocode(str, "price_list");
					paramList2.add("price" + code);
				} else if (str.indexOf("箱价") != -1 && isExistPriceCodeName(str.substring(0,str.indexOf("箱价")))) {
                    String code = getBaseSysCodeService().codenametocode(str.substring(0,str.indexOf("箱价")), "price_list");
                    paramList2.add("boxprice" + code);
                } else if ("备注".equals(str)) {
					paramList2.add("remark");
				} else if ("状态".equals(str)) {
					paramList2.add("stateName");
				} else {
					paramList2.add("null");
				}
			}
			if (paramList.size() == paramList2.size()) {
				List result = new ArrayList();
				List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); // 获取导入数据
				List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
				if (list.size() != 0) {
					Map detialMap = new HashMap();
					for (Map<String, Object> map4 : list) {
						Object object = entity.newInstance();
						Field[] fields = entity.getDeclaredFields();
						try {
							// 获取的导入数据格式转换
							DRCastTo(map4, fields);
							// BeanUtils.populate(object, map4);
							PropertyUtils.copyProperties(object, map4);
							result.add(object);
						} catch (Exception e) {
                            e.printStackTrace();
							errorList.add(map4);
						}
					}
					if (result.size() != 0) {
						retMap = excelService.insertSalesOrder(object2, result, method);
					}
					if (errorList.size() > 0) {
						String fileid = createErrorFile(errorList);
						retMap.put("msg", "导入失败" + errorList.size() + "条");
						retMap.put("errorid", fileid);
					}
				} else {
					retMap.put("excelempty", true);
				}
			} else {
				retMap.put("versionerror", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("error", true);
		}
        if(index == 0){
            retMap.put("errorFile", true);
        }
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 导入商品出错时导出
	 * 
	 * @param errorList
	 * @return
	 * @throws Exception
	 */
	public String createErrorFile(List<Map<String, Object>> errorList) throws Exception {

		// 模板文件路径
		String tempFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/goodsModel.xls");

		List<String> dataListCell = new ArrayList<String>();
        //组装列字段
		dataListCell = adddataListCell(dataListCell);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

		// 商品模板里的字段和传过来的商品信息进行匹配
		for (Map<String, Object> map4 : errorList) {
			map4.put("goodsid", map4.get("id"));
			map4.put("goodsname", map4.get("name"));
			map4.put("brand", map4.get("brandName"));
			map4.put("defaultsort", map4.get("defaultsortName"));
			map4.put("defaultsupplier", map4.get("defaultsupplierName"));
			map4.put("defaulttaxtype", map4.get("defaulttaxtypeName"));
			map4.put("mainunit", map4.get("mainunitName"));
			map4.put("goodstype", map4.get("goodstypeName"));
			map4.put("defaultsupplier", map4.get("defaultsupplierName"));
			map4.put("isstoragelocation", map4.get("isstoragelocationname"));
			map4.put("isshelflife", map4.get("isshelflifename"));
			map4.put("shelflifeunit", map4.get("shelflifeunit"));
			map4.put("isbatch", map4.get("isbatchname"));
			map4.put("state", map4.get("stateName"));
			dataList.add(map4);
		}

		ExcelFileUtils handle = new ExcelFileUtils();
		handle.writeListData(tempFilePath, dataListCell, dataList, 0);

		SysUser sysUser = getSysUser();
		// 获取临时文件总目录
		String filepath = OfficeUtils.getFilepath();
		String subPath = CommonUtils.getYearMonthDirPath();	//年月路径 格式yyyy/MM
		String path = filepath + "/errorimportfile/" + subPath;// 创建该临时文件目录
		path=path.replaceAll("\\\\","/");
		File file = new File(path);
		// 如果临时文件目录不存在，则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Random rand = new Random();
		String randString = "" + rand.nextInt(100000);
		String fileName = dateFormat.format(new Date()) + randString;
		// 临时文件名称
		fileName = sysUser.getUserid() + fileName + ".xls";

		File errorFile = new File(path, fileName);

		if (!errorFile.exists()) {
			errorFile.createNewFile();
		}
		OutputStream os = new FileOutputStream(errorFile);

		// 将文件写到输出流并关闭资源
		handle.writeAndClose(tempFilePath, os);
		os.flush();
		os.close();
		handle.readClose(tempFilePath);

		String fullPath = "upload/errorimportfile/" + subPath + "/" + fileName;
		AttachFile attachFile = new AttachFile();
		attachFile.setExt(".xls");
		attachFile.setFilename(fileName);
		attachFile.setFullpath(fullPath);
		attachFile.setOldfilename(fileName);
		// 将临时文件信息插入数据库
		attachFileService.addAttachFile(attachFile);

		String id = "";

		if (null != attachFile) {
			id = attachFile.getId();
		}
		return id;
	}


	/**
	 * 导入商品分类列表数据
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jun 11, 2014
	 */
	public String importGoodsSortListData() throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			String clazz = "goodsService", meth = "addDRGoodsSortExcel", module = "basefiles", pojo = "WaresClass";
			Object object2 = SpringContextUtils.getBean(clazz);
			Class entity = Class.forName("com.hd.agent." + module + ".model." + pojo);
			Method[] methods = object2.getClass().getMethods();
			Method method = null;
			for (Method m : methods) {
				if (m.getName().equals(meth)) {
					method = m;
				}
			}

			List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); // 获取第一行数据为字段的描述列表
			List<String> paramList2 = new ArrayList<String>();
			for (String str : paramList) {
				if ("本级编码".equals(str)) {
					paramList2.add("thisid");
				} else if ("本级名称".equals(str)) {
					paramList2.add("thisname");
				} else if ("上级分类编码".equals(str)) {
					paramList2.add("pid");
				} else {
					paramList2.add("null");
				}
			}

			if (paramList.size() == paramList2.size()) {
				List result = new ArrayList();
				List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); // 获取导入数据
				if (list.size() != 0) {
					Map detialMap = new HashMap();
					for (Map<String, Object> map4 : list) {
						Object object = entity.newInstance();
						Field[] fields = entity.getDeclaredFields();
						// 获取的导入数据格式转换
						DRCastTo(map4, fields);
						// BeanUtils.populate(object, map4);
						PropertyUtils.copyProperties(object, map4);
						result.add(object);
					}
					if (result.size() != 0) {
						retMap = excelService.insertSalesOrder(object2, result, method);
					}
				} else {
					retMap.put("excelempty", true);
				}
			} else {
				retMap.put("versionerror", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("error", true);
		}
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 显示图片上传页面
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Feb 7, 2015
	 */
	public String showGoodsUploadImgPage() throws Exception {
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		String goodsid = request.getParameter("goodsid");
		request.setAttribute("goodsid", goodsid);
		return SUCCESS;
	}

	/**
	 * 模板导出
	 * 
	 * @return
	 * @throws Exception
	 * @author lin_xx
	 * @date Jun 19, 2015
	 */
	public String getExportMod() throws Exception {

		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		String title = "";
		if (map.containsKey("excelTitle")) {
			title = map.get("excelTitle").toString();
		} else {
			title = "list";
		}
		if (StringUtils.isEmpty(title)) {
			title = "list";
		}
		// 模板文件路径
		String tempFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/goodsModel.xls");
		String filename = title + ".xls";

		String idarrs = request.getParameter("idarrs");
		String[] idlist = idarrs.split(",");

		List<GoodsInfo> goodsInfos = goodsService.getUpLoadMod(idlist);

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < goodsInfos.size(); i++) {
			Map<String, Object> map1 = new HashMap<String, Object>();
            GoodsInfo goodsInfo = goodsInfos.get(i);
            String bstype = goodsInfo.getBstype();
            map1.put("goodsid", goodsInfo.getId());
			map1.put("goodsname", goodsInfo.getName());
            map1.put("productfield",goodsInfo.getProductfield());
			map1.put("model", goodsInfo.getModel());
			map1.put("spell", goodsInfo.getSpell());
			map1.put("barcode", goodsInfo.getBarcode());
			map1.put("boxbarcode", goodsInfo.getBoxbarcode());
			map1.put("itemno", goodsInfo.getItemno());
			map1.put("defaultsort", goodsInfo.getDefaultsortName());
			map1.put("deptname", goodsInfo.getDeptname());
			map1.put("defaultsupplier", goodsInfo.getDefaultsupplierName());
			map1.put("mainunit", goodsInfo.getMainunitName());
			map1.put("auxunitname", goodsInfo.getAuxunitname());
			map1.put("boxnum", goodsInfo.getBoxnum());
			map1.put("storageName", goodsInfo.getStorageName());
			map1.put("length", goodsInfo.getGlength());
			map1.put("hight", goodsInfo.getGhight());
			map1.put("width", goodsInfo.getGwidth());
			map1.put("totalweight", goodsInfo.getTotalweight());
			map1.put("totalvolume", goodsInfo.getTotalvolume());
			map1.put("highestbuyprice", goodsInfo.getHighestbuyprice());
			map1.put("lowestsaleprice", goodsInfo.getLowestsaleprice());
			map1.put("basesaleprice", goodsInfo.getBasesaleprice());
			map1.put("isstoragelocation", goodsInfo.getIsstoragelocationname());
			map1.put("isshelflife", goodsInfo.getIsshelflifename());
			map1.put("isbatch", goodsInfo.getIsbatchname());
			map1.put("shelflifeunit", goodsInfo.getShelflifedetail());
			map1.put("goodstype", goodsInfo.getGoodstypeName());
			map1.put("defaulttaxtype", goodsInfo.getDefaulttaxtypeName());
			map1.put("brand", goodsInfo.getBrandName());
			map1.put("remark", goodsInfo.getRemark());
			map1.put("state", goodsInfo.getStateName());
			map1.put("price1", goodsInfo.getPrice1());
            map1.put("boxprice1", goodsInfo.getBoxprice1());
			map1.put("price2", goodsInfo.getPrice2());
            map1.put("boxprice2", goodsInfo.getBoxprice2());
			map1.put("price3", goodsInfo.getPrice3());
            map1.put("boxprice3", goodsInfo.getBoxprice3());
			map1.put("price4", goodsInfo.getPrice4());
            map1.put("boxprice4", goodsInfo.getBoxprice4());
            if("1".equals(bstype)){
                map1.put("bstype", "购销");
            }else if("".equals(bstype)){
                map1.put("bstype", "可购");
            }else if("".equals(bstype)){
                map1.put("bstype", "可销");
            }
			dataList.add(map1);
		}
        //组装导出的列字段
        List<String> dataListCell = new ArrayList<String>();
        dataListCell = adddataListCell(dataListCell);

		ExcelFileUtils handle = new ExcelFileUtils();
		handle.writeListData(tempFilePath, dataListCell, dataList, 0);

		// 文件导出路径
		String path = ServletActionContext.getServletContext().getRealPath("common");
		File file = new File(path, filename);
		if (!file.exists()) {
			file.createNewFile();
		}
		OutputStream os = new FileOutputStream(file);

		// 写到输出流并关闭资源
		handle.writeAndClose(tempFilePath, os);

		os.flush();
		os.close();

		handle.readClose(tempFilePath);
		// 下载已经导出的文件到客户端
		ExcelUtils.downloadExcel(path, filename);

		return SUCCESS;
	}

	/**
	 * 根据名称获取拼音
	 * 
	 * @return
	 * @throws Exception
	 * @author 潘笑笑
	 * @date 2015-08-10
	 */
	public String getPinYingJCLen() throws Exception {
		String str = request.getParameter("str");
		String pinyin = CommonUtils.getPinYingJCLen(str);
		addJSONObject("pinyin", pinyin);
		return SUCCESS;
	}

    /**
     * 组装要导出的字段
     * @param dataListCell
     * @return
     */
	public List<String> adddataListCell(List<String> dataListCell) {

		dataListCell.add("goodsid");
		dataListCell.add("goodsname");
		dataListCell.add("brand");
		dataListCell.add("model");
		dataListCell.add("spell");
		dataListCell.add("barcode");
		dataListCell.add("defaultsort");
		dataListCell.add("deptname");
		dataListCell.add("defaultsupplier");
		dataListCell.add("mainunit");
		dataListCell.add("auxunitname");
		dataListCell.add("boxnum");
		dataListCell.add("goodstype");
		dataListCell.add("storageName");
		dataListCell.add("boxbarcode");
		dataListCell.add("itemno");
		dataListCell.add("secondsupplier");
		dataListCell.add("length");
		dataListCell.add("hight");
		dataListCell.add("width");
		dataListCell.add("totalweight");
		dataListCell.add("totalvolume");
		dataListCell.add("highestbuyprice");
		dataListCell.add("lowestsaleprice");
		dataListCell.add("basesaleprice");
		dataListCell.add("defaulttaxtype");
		dataListCell.add("isstoragelocation");
		dataListCell.add("isshelflife");
		dataListCell.add("isbatch");
		dataListCell.add("shelflifeunit");
		dataListCell.add("state");
		dataListCell.add("remark");
		dataListCell.add("price1");
		dataListCell.add("price2");
		dataListCell.add("price3");
		dataListCell.add("price4");
        dataListCell.add("boxprice1");
        dataListCell.add("boxprice2");
        dataListCell.add("boxprice3");
        dataListCell.add("boxprice4");
        dataListCell.add("productfield");
        dataListCell.add("bstype");

		return dataListCell;
	}

	/**
	 * 手机端查看商品详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String showPhoneGoodsInfo() throws Exception {

		String id = request.getParameter("id");

		GoodsInfo goodsInfo = goodsService.showGoodsInfo(id);

		if (null != goodsInfo) {

			// 购销类型
			if (StringUtils.isNotEmpty(goodsInfo.getBstype())) {
				if (goodsInfo.getBstype().equals("1")) {
					goodsInfo.setBstypeName("购销");
				} else if (goodsInfo.getBstype().equals("2")) {
					goodsInfo.setBstypeName("可购");
				} else if (goodsInfo.getBstype().equals("3")) {
					goodsInfo.setBstypeName("可销");
				} else if (goodsInfo.getBstype().equals("4")) {
					goodsInfo.setBstypeName("其他");
				}
			}

			// 商品类型
			if (StringUtils.isNotEmpty(goodsInfo.getGoodstype())) {
				if (goodsInfo.getGoodstype().equals("1")) {
					goodsInfo.setGoodstypeName("普通");
				} else if (goodsInfo.getGoodstype().equals("2")) {
					goodsInfo.setGoodstypeName("虚拟");
				} else if (goodsInfo.getGoodstype().equals("3")) {
					goodsInfo.setGoodstypeName("费用");
				} else if (goodsInfo.getGoodstype().equals("4")) {
					goodsInfo.setGoodstypeName("其他");
				}
			}
			// 库位管理
			if (StringUtils.isNotEmpty(goodsInfo.getIsstoragelocation())) {
				if (goodsInfo.getIsstoragelocation().equals("0")) {
					goodsInfo.setIsstoragelocationname("否");
				} else {
					goodsInfo.setIsstoragelocationname("是");
				}
			}
			// 保质期管理
			if (StringUtils.isNotEmpty(goodsInfo.getIsshelflife())) {
				if (goodsInfo.getIsshelflife().equals("0")) {
					goodsInfo.setIsshelflifename("否");
				} else {
					goodsInfo.setIsshelflifename("是");
				}
			}
			// 保质期
			if (null != goodsInfo.getShelflife() && StringUtils.isNotEmpty(goodsInfo.getShelflifeunit())) {

				if (goodsInfo.getShelflifeunit().equals("1")) {
					goodsInfo.setShelflifedetail(goodsInfo.getShelflife() + "天");
				} else if (goodsInfo.getShelflifeunit().equals("2")) {
					goodsInfo.setShelflifedetail(goodsInfo.getShelflife() + "周");
				} else if (goodsInfo.getShelflifeunit().equals("3")) {
					goodsInfo.setShelflifedetail(goodsInfo.getShelflife() + "月");
				} else if (goodsInfo.getShelflifeunit().equals("4")) {
					goodsInfo.setShelflifedetail(goodsInfo.getShelflife() + "年");
				}
			}
			// 默认税种
			if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())) {
				TaxType taxTypeInfo = financeService.getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
				if (null != taxTypeInfo) {
					goodsInfo.setDefaulttaxtypeName(taxTypeInfo.getName());
				}
			}
			// 批次管理
			if (StringUtils.isNotEmpty(goodsInfo.getIsbatch())) {
				if (goodsInfo.getIsstoragelocation().equals("0")) {
					goodsInfo.setIsbatchname("否");
				} else {
					goodsInfo.setIsbatchname("是");
				}
			}
			// 默认采购员
			if (StringUtils.isNotEmpty(goodsInfo.getDefaultbuyer())) {
				Personnel person = getPersonnelInfoById(goodsInfo.getDefaultbuyer());
				if (null != person) {
					goodsInfo.setDefaultbuyerName(person.getName());
				}
			}

			// 商品品牌
			if (StringUtils.isNotEmpty(goodsInfo.getBrand())) {
				Brand brand = getBaseGoodsService().getBrandInfo(goodsInfo.getBrand());
				goodsInfo.setBrandName(brand.getName());
			}
			// 默认分类（来源商品分类）
			if (StringUtils.isNotEmpty(goodsInfo.getDefaultsort())) {
				WaresClass waresClass = goodsService.getWaresClassInfo(goodsInfo.getDefaultsort());
				if (null != waresClass) {
					goodsInfo.setDefaultsortName(waresClass.getThisname());
				}
			}
			// 仓库
			if (StringUtils.isNotEmpty(goodsInfo.getStorageid())) {
				StorageInfo storageInfo = storageService.showStorageInfo(goodsInfo.getStorageid());
				if (null != storageInfo) {
					goodsInfo.setStorageName(storageInfo.getName());
				}
			}

			// 默认库位
			if (StringUtils.isNotEmpty(goodsInfo.getStoragelocation())) {
				StorageLocation storageLocation = storageService.showStorageLocationInfo(goodsInfo.getStoragelocation());
				if (null != storageLocation) {
					goodsInfo.setStoragelocationName(storageLocation.getName());
				}
			}
			// 默认供应商
			if (StringUtils.isNotEmpty(goodsInfo.getDefaultsupplier())) {
				Map map = new HashMap();
				map.put("id", goodsInfo.getDefaultsupplier());
				BuySupplier buySupplier = getBaseBuyService().getBuySupplierBy(map);
				if (null != buySupplier) {
					goodsInfo.setDefaultsupplierName(buySupplier.getName());
				}
			}
			// 第二供应商
			if (StringUtils.isNotEmpty(goodsInfo.getSecondsupplier())) {
				Map map = new HashMap();
				map.put("id", goodsInfo.getDefaultsupplier());
				BuySupplier buySupplier = getBaseBuyService().getBuySupplierBy(map);
				if (null != buySupplier) {
					goodsInfo.setSecondsuppliername(buySupplier.getName());
				}
			}
			goodsInfo.setBoxnum(goodsInfo.getBoxnum());
		}

		request.setAttribute("goodsInfo", goodsInfo);

		return SUCCESS;

	}

    /**
     * 显示商品的成本变更记录
     * @return
     * @throws Exception
     */
	public String goodesSimplifyViewCostpriceChage() throws Exception{
	    return "success";
    }

	public String getGoodesSimplifyViewCostpriceChageData(){
		Map map=CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData=goodsService.getGoodesSimplifyViewCostpriceChageData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 按航天存货格式导出商品档案
	 *
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Mar 18, 2014
	 */
	public void exportGoodsListDataForHTKP() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		map.put("isTaxrateXS",true);
		String title = "";
		if (map.containsKey("excelTitle")) {
			title = map.get("excelTitle").toString();
		} else {
			title = "商品存货信息表_";
		}
		if (StringUtils.isEmpty(title)) {
			title = "商品存货信息表_";
		}
		title=title+"_"+CommonUtils.getDataNumberSeconds();

		List<GoodsInfo> list = excelService.getGoodsList(pageMap);
		if(list==null || list.size()==0){
			return;
		}
		String tplFileDir = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/ftl/");
		Map tplDataMap=new HashMap();
		tplDataMap.put("goodsList",list);
		//创建freemarker配置实例
		Configuration config=new Configuration();
		config.setNumberFormat("#.######");
		config.setDirectoryForTemplateLoading(new File(tplFileDir));
		config.setDefaultEncoding("UTF-8");
		Template template=config.getTemplate("goodsexport_htkp.ftl");
		template.setEncoding("UTF-8");
		StringWriter stringWriter = new StringWriter();
		template.process(tplDataMap, stringWriter);

		//byte[] result =new String(stringWriter.getBuffer().toString().getBytes("UTF-8"),"gb2312").getBytes("gb2312");
		byte[] result =stringWriter.getBuffer().toString().getBytes("GB18030");
		CommonUtils.responseExportTxtFile(response,result,title,".txt");
	}

	/**
	 * 导出航天金税商品格式
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Feb 16, 2015
	 */
	public void exportGoodsListDataSplitByBrandForHTKP()throws Exception{
		Map conditioMap = CommonUtils.changeMap(request.getParameterMap());
		conditioMap.put("isflag", true);
		pageMap.setCondition(conditioMap);
		String title = "";
		if(conditioMap.containsKey("excelTitle")){
			title = conditioMap.get("excelTitle").toString();
		}
		else{
			title = "金税系统商品配置格式";
		}
		if(StringUtils.isEmpty(title)){
			title = "金税系统商品配置格式";
		}
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("goodsid", "商品编码");
		firstMap.put("jsgoodsid", "金税编码");
		firstMap.put("name", "名称");
		firstMap.put("jm", "简码");
		firstMap.put("spsm", "商品税目");
		firstMap.put("sl", "税率");
		firstMap.put("ggxh", "规格型号");
		firstMap.put("jldw", "计量单位");
		firstMap.put("dj", "单价");
		firstMap.put("hsjbz", "含税价标志");
		firstMap.put("ycbz", "隐藏标志");
		firstMap.put("zwhzyqt", "中外合作油气田");
		firstMap.put("ssflbm", "税收分类编码");
		firstMap.put("sfxsyhzc", "是否享受优惠政策");
		firstMap.put("ssflbmmc", "税收分类编码名称");
		firstMap.put("yhzclx", "优惠政策类型");
		firstMap.put("lslbs", "零税率标识");
		firstMap.put("bmbbh", "编码版本号");
		firstMap.put("issortline", "是否分类行");

		String jsgoodsidcreatemethod=(String)conditioMap.get("jsgoodsidcreatemethod");
		if (!"1".equals(jsgoodsidcreatemethod)
				&& !"2".equals(jsgoodsidcreatemethod)
				&& !"3".equals(jsgoodsidcreatemethod)) {
			jsgoodsidcreatemethod="1";
		}
		String jsgoodsidlen=(String)conditioMap.get("jsgoodsidlen");

		if("2".equals(jsgoodsidcreatemethod)){
			//设置自增
			if(null==jsgoodsidlen || "".equals(jsgoodsidlen.trim())){
				jsgoodsidlen=getSysParamValue("JSKPGOODSIDLEN");
			}
			if(null==jsgoodsidlen || "".equals(jsgoodsidlen.trim())){
				jsgoodsidlen="4";
			}
		}
		if(!StringUtils.isNumeric(jsgoodsidlen)){
			jsgoodsidlen="4";
		}
		int iJSGoodsidLen=Integer.parseInt(jsgoodsidlen);
		if(iJSGoodsidLen<0 || iJSGoodsidLen>7){
			iJSGoodsidLen=4;
		}

		List<Map> dataList = goodsService.getGoodsListForHTJS(pageMap);
		Map<String,Object> headMap=new LinkedHashMap();
		Map resultMap=new HashMap();
		String currentBrandid="";
		Map<String,String> clusteridMap=new HashMap<String, String>();
		long id=0;
		for(Map<String, Object> dataItem : dataList){
			id=id+1;
			String jstaxflag=(String)dataItem.get("jstaxflag");
			if("1".equals(jstaxflag)){
				dataItem.put("sfxsyhzc","是");
				dataItem.put("yhzclx","免税");
			}
			String brandid=(String)dataItem.get("brandid");
            String brandname = (String) dataItem.get("brandname");
			if(null!=brandid && !"".equals(brandid.trim())) {
				if (brandname == null || "".equals(brandname.trim())) {
					brandname = "其他品牌";
				}
				headMap.put(brandid.trim(),brandid.trim() );
				headMap.put("name_"+brandid.trim(),brandname);
			}else{
				continue;
            }
            brandid=brandid.trim();
			//品牌档案中的簇编码
			String clusterid=(String) dataItem.get("jsclusterid");
			if(null==clusterid || "".equals(clusterid.trim())){
				clusterid="";
				if(clusteridMap.containsKey(brandid)){
					clusterid=(String)clusteridMap.get(brandid);
				}
			}else{
				clusteridMap.put(brandid,clusterid);
			}
			clusterid=clusterid.trim();

            List<Map<String, Object>> resultList =null;

            if(resultMap.containsKey(brandid)){
                resultList=(ArrayList)resultMap.get(brandid);
            }
            if(null==resultList){
                resultList= new ArrayList<Map<String,Object>>();
                resultList.add(firstMap);
                resultMap.put(brandid,resultList);
            }
			if("".equals(currentBrandid.trim()) || !currentBrandid.equals(brandid.trim())){
            	id=1;
            	currentBrandid=brandid.trim();
				Map<String,Object>  brandMap=new LinkedHashMap();
				brandMap.put("goodsid",null);
				brandMap.put("jsgoodsid", clusterid.trim());
				brandMap.put("name", brandname);
				brandMap.put("jm",null);
				brandMap.put("spsm", null);
				brandMap.put("sl", null);
				brandMap.put("ggxh", null);
				brandMap.put("jldw", null);
				brandMap.put("dj", null);
				brandMap.put("hsjbz", null);
				brandMap.put("ycbz", null);
				brandMap.put("zwhzyqt", null);
				brandMap.put("ssflbm", null);
				brandMap.put("sfxsyhzc", null);
				brandMap.put("ssflbmmc", null);
				brandMap.put("yhzclx", null);
				brandMap.put("lslbs", null);
				brandMap.put("bmbbh", "");
				brandMap.put("issortline", "是");
				resultList.add(brandMap);
			}
			//金税编码
			String jsgoodsid=clusterid+(StringUtils.leftPad(id+"",iJSGoodsidLen,"0"));
			if("2".equals(jsgoodsidcreatemethod)){
				//使用自增
				dataItem.put("jsgoodsid",jsgoodsid);
			}else if("3".equals(jsgoodsidcreatemethod)){
				//使用空白
				dataItem.put("jsgoodsid","");
			}

			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
				if(dataItem.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
					for(Map.Entry<String, Object> entry : dataItem.entrySet()){
						if(fentry.getKey().equals(entry.getKey())){
							objectCastToRetMap(retMap,entry);
						}
					}
				}
				else{
					retMap.put(fentry.getKey(), "");
				}
			}
			resultList.add(retMap);

		}
		ExcelUtils.exportExcel(resultMap,headMap,title);
	}


	/**
	 * 商品信息EXCEL转换金税格式
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Apr 23, 2017
	 */
	public String convertGoodsExcelToXMLForHTKP() throws Exception{
		Map resultMap=new HashMap();
		//默认消息
		resultMap.put("msg","商品信息EXCEL转换金税格式失败");
		boolean flag=false;
		//模板文件路径
		String goodsToXmlForHTKPTplPath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/GoodsToXmlForHTKPTpl.xls");
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		if(null==paramList){
			resultMap.put("msg","无法读取上传文件内容");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("商品编码".equals(str)){
				paramList2.add("goodsid");
			}else if("金税编码".equals(str)){
				paramList2.add("jsgoodsid");
			}
			else if("名称".equals(str)){
				paramList2.add("name");
			}
			else if("简码".equals(str)){
				paramList2.add("jm");
			}
			else if("商品税目".equals(str)){
				paramList2.add("spsm");
			}
			else if("税率".equals(str)){
				paramList2.add("sl");
			}
			else if("规格型号".equals(str)){
				paramList2.add("ggxh");
			}
			else if("计量单位".equals(str)){
				paramList2.add("jldw");
			}
			else if("单价".equals(str)){
				paramList2.add("dj");
			}
			else if("含税价标志".equals(str)){
				paramList2.add("hsjbz");
			}
			else if("隐藏标志".equals(str)){
				paramList2.add("ycbz");
			}
			else if("中外合作油气田".equals(str)){
				paramList2.add("zwhzyqt");
			}
			else if("税收分类编码".equals(str)){
				paramList2.add("ssflbm");
			}
			else if("是否享受优惠政策".equals(str)){
				paramList2.add("sfxsyhzc");
			}
			else if("税收分类编码名称".equals(str)){
				paramList2.add("ssflbmmc");
			}
			else if("优惠政策类型".equals(str)){
				paramList2.add("yhzclx");
			}
			else if("零税率标识".equals(str)){
				paramList2.add("lslbs");
			}
			else if("编码版本号".equals(str)){
				paramList2.add("bmbbh");
			}
			else if("是否分类行".equals(str)){
				paramList2.add("issortline");
			}
			else{
				paramList2.add("null");
			}
		}

		if(paramList.size() == paramList2.size()){
			List<Map<String, Object>> list = ExcelUtils.importExcelMoreSheet(excelFile, paramList2); //获取导入数据

			String tplFileDir = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/ftl/");
			Map tplDataMap=new HashMap();
			tplDataMap.put("goodsList",list);
			tplDataMap.put("fiterStringFunc",new JSStringFilterMethodModel());
			//创建freemarker配置实例
			Configuration config=new Configuration();
			config.setNumberFormat("#.######");
			config.setDirectoryForTemplateLoading(new File(tplFileDir));
			config.setDefaultEncoding("UTF-8");
			config.setTemplateUpdateDelay(0);
			Template template=config.getTemplate("goodsexceltoxml_htkp.ftl");
			template.setEncoding("UTF-8");
			StringWriter stringWriter = new StringWriter();
			template.process(tplDataMap, stringWriter);

			//byte[] result =new String(stringWriter.getBuffer().toString().getBytes("UTF-8"),"gb2312").getBytes("gb2312");
			byte[] result =stringWriter.getBuffer().toString().getBytes("GB18030");

			IAttachFileService attachFileService=(IAttachFileService)SpringContextUtils.getBean("attachFileService");
			String fileid=attachFileService.createFileAndAttachFile(result,"txt","htkp","航天金税商品档案导入");
			resultMap.put("msg","》》商品信息EXCEL转换金税格式成功《《");
			resultMap.put("datafileid",fileid);
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}

	/**
	 * 关联金税系统商品信息与ERP系统商品信息
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Apr 23, 2017
	 */
	public String importAndUpdateJSGoodsHTKP() throws Exception{
		Map resultMap=new HashMap();
		//默认消息
		resultMap.put("msg","关联金税系统商品信息与ERP系统商品信息失败");

		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		if(null==paramList){
			resultMap.put("msg","无法读取上传文件内容");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("商品编码".equals(str)){
				paramList2.add("goodsid");
			}else if("金税编码".equals(str)){
				paramList2.add("jsgoodsid");
			}
			else if("名称".equals(str)){
				paramList2.add("name");
			}
			else if("简码".equals(str)){
				paramList2.add("jm");
			}
			else if("商品税目".equals(str)){
				paramList2.add("spsm");
			}
			else if("税率".equals(str)){
				paramList2.add("sl");
			}
			else if("规格型号".equals(str)){
				paramList2.add("ggxh");
			}
			else if("计量单位".equals(str)){
				paramList2.add("jldw");
			}
			else if("单价".equals(str)){
				paramList2.add("dj");
			}
			else if("含税价标志".equals(str)){
				paramList2.add("hsjbz");
			}
			else if("隐藏标志".equals(str)){
				paramList2.add("ycbz");
			}
			else if("中外合作油气田".equals(str)){
				paramList2.add("zwhzyqt");
			}
			else if("税收分类编码".equals(str)){
				paramList2.add("ssflbm");
			}
			else if("是否享受优惠政策".equals(str)){
				paramList2.add("sfxsyhzc");
			}
			else if("税收分类编码名称".equals(str)){
				paramList2.add("ssflbmmc");
			}
			else if("优惠政策类型".equals(str)){
				paramList2.add("yhzclx");
			}
			else if("零税率标识".equals(str)){
				paramList2.add("lslbs");
			}
			else if("编码版本号".equals(str)){
				paramList2.add("bmbbh");
			}
			else if("是否分类行".equals(str)){
				paramList2.add("issortline");
			}
			else{
				paramList2.add("null");
			}
		}
		List<String> dataCellList = new ArrayList<String>();
		dataCellList.add("goodsid");
		dataCellList.add("jsgoodsid");
		dataCellList.add("name");
		dataCellList.add("jm");
		dataCellList.add("spsm");
		dataCellList.add("sl");
		dataCellList.add("ggxh");
		dataCellList.add("jldw");
		dataCellList.add("dj");
		dataCellList.add("hsjbz");
		dataCellList.add("ycbz");
		dataCellList.add("zwhzyqt");
		dataCellList.add("ssflbm");
		dataCellList.add("sfxsyhzc");
		dataCellList.add("ssflbmmc");
		dataCellList.add("yhzclx");
		dataCellList.add("lslbs");
		dataCellList.add("bmbbh");
		dataCellList.add("errormessage");

		if(paramList.size() == paramList2.size()){
			List<Map<String, Object>> list = ExcelUtils.importExcelMoreSheet(excelFile, paramList2); //获取导入数据

			resultMap=goodsService.importAndUpdateJsgoodsForHTKP(list);

			if(resultMap.containsKey("errorDataList") && null!=resultMap.get("errorDataList")){
				List<Map<String,Object>> errorDataList=(List<Map<String,Object>>)resultMap.get("errorDataList");
				resultMap.remove("errorDataList");
				if(errorDataList.size() > 0){
					//模板文件路径
					String outTplFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/goodsToXmlForHTKP.xls");
					IAttachFileService attachFileService=(IAttachFileService)SpringContextUtils.getBean("attachFileService");
					String fileid=attachFileService.createExcelAndAttachFile(errorDataList, dataCellList, outTplFilePath,"更新商品档案相应金税商品编码失败");
					resultMap.put("msg","导入失败"+errorDataList.size()+"条");
					resultMap.put("errorid",fileid);
				}
			}
			Boolean flag=(Boolean)resultMap.get("flag");
			if(flag==true){
				String logs=(String)resultMap.get("updateLogs");
				if(null!=logs && !"".equals(logs.trim())){
					addLog("关联金税系统商品信息与ERP系统商品信息，更新金税商品字段"+logs);
				}
			}
		}
		addJSONObject(resultMap);
		return SUCCESS;
	}


	/**
	 * 显示商品档案金税相关信息页面
	 *
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2017-07-14
	 */
	public String showGoodsInfoForJSEditPage() throws Exception {
		String id = request.getParameter("id");
		GoodsInfo goodsInfo = goodsService.showGoodsInfo(id);
		request.setAttribute("goodsInfo",goodsInfo);
		return SUCCESS;
	}

	/**
	 * 更新商品档案金税相关信息
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author zhanghonghui
	 * @date Jul 14, 2017
	 */
	@UserOperateLog(key = "GoodsInfo", type = 0, value = "")
	public String updateGoodsInfoForJS() throws Exception{
		Map resultMap=new HashMap();

		String id=request.getParameter("id");
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag",false);
			resultMap.put("msg","未能找到相关商品编号");
			addJSONObject(resultMap);
			return SUCCESS;
		}
		String jsgoodsid=request.getParameter("jsgoodsid");
		String jstaxsortid=request.getParameter("jstaxsortid");
		GoodsInfo upGoodsInfo=new GoodsInfo();
		upGoodsInfo.setId(id);
		upGoodsInfo.setJsgoodsid(jsgoodsid);
		upGoodsInfo.setJstaxsortid(jstaxsortid);
		resultMap=goodsService.updateGoodsInfoForJS(upGoodsInfo);
		Boolean flag=false;
		if(resultMap.containsKey("flag")){
			flag=(Boolean)resultMap.get("flag");
			if(null==flag){
				flag=false;
			}
		}
		addLog("修改商品“"+id+"”档案金税相关信息",flag);
		addJSONObject(resultMap);
		return SUCCESS;
	}
}

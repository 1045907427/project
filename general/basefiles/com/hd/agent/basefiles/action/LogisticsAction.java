/**
 * @(#)LogisticAction.java
 *
 * @author yezhenyu
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 23, 2014 yezhenyu 创建版本
 */
package com.hd.agent.basefiles.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.LogisticsCar;
import com.hd.agent.basefiles.model.LogisticsLine;
import com.hd.agent.basefiles.model.LogisticsLineCar;
import com.hd.agent.basefiles.model.LogisticsLineCustomer;
import com.hd.agent.basefiles.model.PersonnelCustomer;
import com.hd.agent.basefiles.service.ILogisticsService;
import com.hd.agent.basefiles.service.IPersonnelService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;

/**
 * 
 * 物流档案
 * @author yezhenyu
 */
public class LogisticsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5940204850831779684L;
	private final String tableNameLine = "t_base_logistics_line";
	private final String tableNameCar = "t_base_logistics_car";

	private ILogisticsService logisticsService;
	private IPersonnelService personnelService;
	private LogisticsLine lineInfo;
	private LogisticsCar car;
	private List<LogisticsLineCar> lineCarList;
	private List<LogisticsLineCustomer> lineCustomerList;

	public IPersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(IPersonnelService personnelService) {
		this.personnelService = personnelService;
	}

	public ILogisticsService getLogisticsService() {
		return logisticsService;
	}

	public void setLogisticsService(ILogisticsService logisticsService) {
		this.logisticsService = logisticsService;
	}

	public LogisticsLine getLineInfo() {
		return lineInfo;
	}

	public void setLineInfo(LogisticsLine lineInfo) {
		this.lineInfo = lineInfo;
	}

	public LogisticsCar getCar() {
		return car;
	}

	public void setCar(LogisticsCar car) {
		this.car = car;
	}

	public List<LogisticsLineCar> getLineCarList() {
		return lineCarList;
	}

	public void setLineCarList(List<LogisticsLineCar> lineCarList) {
		this.lineCarList = lineCarList;
	}

	public List<LogisticsLineCustomer> getLineCustomerList() {
		return lineCustomerList;
	}

	public void setLineCustomerList(List<LogisticsLineCustomer> lineCustomerList) {
		this.lineCustomerList = lineCustomerList;
	}

	/**
	 * 显示线路档案列表页面
	 * 
	 * @return
	 * @author yezhenyu
	 * @date Apr 24, 2014
	 */
	public String showLineInfoListPage() {
		return SUCCESS;
	}

	/**
	 * 获取线路档案列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Apr 25, 2014
	 */
	public String lineInfoListPage() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = logisticsService.lineInfoListPage(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 显示线路档案页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Apr 25, 2014
	 */
	public String showLineInfoPage() throws Exception {
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		String state = request.getParameter("state");
		String WCid = request.getParameter("WCid");
		String listType = request.getParameter("listType");
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
		request.setAttribute("lineid", id);
		request.setAttribute("state", state);
		request.setAttribute("WCid", WCid);
		return SUCCESS;
	}

	/**
	 * 显示线路档案详情页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Apr 25, 2014
	 */
	public String showLineInfoViewPage() throws Exception {
		String id = request.getParameter("id");
		LogisticsLine lineInfo = logisticsService.showLineInfo(id);
		if (lineInfo != null) {
			Map colMap = getEditAccessColumn(tableNameLine);
			request.setAttribute("showMap", colMap);
			request.setAttribute("fieldmap", getRowDescFromDataDict(tableNameLine));
			request.setAttribute("lineInfo", lineInfo);
			request.setAttribute("lineid", id);
			request.setAttribute("operateType", "view");
			request.setAttribute("type", "view");
		}
		return SUCCESS;
	}

	/**
	 * 显示线路档案页面中，车辆列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Apr 28, 2014
	 */
	public String showCarInfoList() throws Exception {
		String lineid = request.getParameter("lineid");
		// LogisticsCar logisticsCar = logisticsService.showCarInfoList(lineid);
		// if (logisticsCar != null) {
		//
		// List<LogisticsCar> list = new ArrayList<LogisticsCar>();
		// list.add(logisticsCar);
		// addJSONArray(list);
		// }

		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);

		PageData pageData = logisticsService.showCarInfoList(pageMap);
		addJSONObject(pageData);

		request.setAttribute("operateType", "view");
		request.setAttribute("type", "view");
		request.setAttribute("lineid", lineid);

		return SUCCESS;
	}

	/**
	 * 显示线路档案页面，客户列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 2, 2014
	 */
	public String showCustomerInfoList() throws Exception {
		String lineid = request.getParameter("lineid");
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);

		PageData pageData = logisticsService.showCustomerInfoList(pageMap);
		addJSONObject(pageData);

		request.setAttribute("operateType", "view");
		request.setAttribute("type", "view");
		request.setAttribute("lineid", lineid);
		return SUCCESS;
	}

	/**
	 * 显示线路新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 2, 2014
	 */
	public String showLineInfoAddPage() throws Exception {
		Map colMap = getEditAccessColumn(tableNameLine);
		request.setAttribute("showMap", colMap);
		request.setAttribute("operateType", "add");
		request.setAttribute("type", "add");
		request.setAttribute("carlist", null);
		return SUCCESS;
	}

    /**
     * 显示线路对应客户新增页面
     *
     * @return
     * @throws Exception
     * @author yezhenyu
     * @date May 2, 2014
     */
    public String showCustomerToLineAddPage() throws Exception {
        String insertcustomerid = request.getParameter("insertcustomerid");
        request.setAttribute("insertcustomerid", insertcustomerid);
        return SUCCESS;
    }

    /**
     * 显示线路对应客户查看页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 12, 2016
     */
    public String showCustomerToLineViewPage() throws Exception {
        String insertcustomerid = request.getParameter("insertcustomerid");
        request.setAttribute("insertcustomerid", insertcustomerid);
        return SUCCESS;
    }

	/**
	 * 添加线路档案
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 5, 2014
	 */
	@UserOperateLog(key = "LogisticsLine", type = 2, value = "")
	public String addLineInfo() throws Exception {
		String carInfo = request.getParameter("carInfo");
		String customerInfo = request.getParameter("customerInfo");

		if (carInfo != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(carInfo);
			lineCarList = JSONArray.toList(json, LogisticsLineCar.class);
			for (LogisticsLineCar lineCar : lineCarList) {
				lineCar.setLineid(lineInfo.getId());
			}
		}

		if (customerInfo != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(customerInfo);
			lineCustomerList = JSONArray.toList(json, LogisticsLineCustomer.class);
			for (LogisticsLineCustomer lineCustomer : lineCustomerList) {
				lineCustomer.setLineid(lineInfo.getId());
			}
			//lineInfo.setBasecustomers(lineCustomerList.size());
		}

		String type = request.getParameter("type");
		boolean flag = false;
		// /if ("save".equals(type)) {
		lineInfo.setState("2");
		// } else {
		// lineInfo.setState("3");
		// }
		SysUser sysUser = getSysUser();
		lineInfo.setAdddeptid(sysUser.getDepartmentid());
		lineInfo.setAdddeptname(sysUser.getDepartmentname());
		lineInfo.setAdduserid(sysUser.getUserid());
		lineInfo.setAddusername(sysUser.getName());
		// 判断线路编码是否重复, true 重复，false 不重复
		if (StringUtils.isNotEmpty(lineInfo.getId())) {
			boolean isRepeatId = logisticsService.isRepeatLineInfoID(lineInfo.getId());
			if (!isRepeatId) {
				flag = logisticsService.addLineInfo(lineInfo, lineCarList, lineCustomerList);
			}
		}
		addLog("新增线路 编号:" + lineInfo.getId(), flag);
		addJSONObject("flag", flag);

		return SUCCESS;
	}

	/**
	 * 判断线路ID是否重复
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 21, 2014
	 */
	public String isRepeatLineInfoID() throws Exception {
		String id = request.getParameter("id");
		boolean flag = logisticsService.isRepeatLineInfoID(id);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 显示线路档案编辑页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 8, 2014
	 */
	public String showLineInfoEidtPage() throws Exception {
		String id = request.getParameter("id");
		LogisticsLine lineInfo = logisticsService.showLineInfo(id);
		if (lineInfo != null) {

			Map colMap = getEditAccessColumn(tableNameLine);
			request.setAttribute("showMap", colMap);
			request.setAttribute("fieldmap", getRowDescFromDataDict(tableNameLine));
			request.setAttribute("lineInfo", lineInfo);
			request.setAttribute("lineid", id);
			request.setAttribute("operateType", "edit");
			request.setAttribute("type", "edit");

			boolean canEdit = canTableDataDelete("t_base_logistics_line", id);
			request.setAttribute("canEdit", canEdit);
			
			List list = logisticsService.showCarInfoListByLineid(id);
			request.setAttribute("carlist", JSONUtils.listToJsonStr(list));
			
			String customerids = "";
			List<LogisticsLineCustomer> list2 = logisticsService.getLineCustomerListByLineid(id);
			if(list.size() != 0){
				for(LogisticsLineCustomer lineCustomer : list2){
					customerids +=  lineCustomer.getCustomerid() + ",";
				}
			}
			request.setAttribute("customerids", customerids);
		}
		return SUCCESS;
	}

	/**
	 * 编辑线路档案
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 8, 2014
	 */
	@UserOperateLog(key = "LogisticsLine", type = 3, value = "")
	public String editLineInfo() throws Exception {
		String carInfo = request.getParameter("carInfo");
		String customerInfo = request.getParameter("customerInfo");

		if (carInfo != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(carInfo);
			lineCarList = JSONArray.toList(json, LogisticsLineCar.class);
			for (LogisticsLineCar lineCar : lineCarList) {
				lineCar.setLineid(lineInfo.getId());
			}
		}

		if (customerInfo != null) {
			// 把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(customerInfo);
			lineCustomerList = JSONArray.toList(json, LogisticsLineCustomer.class);
			for (LogisticsLineCustomer lineCustomer : lineCustomerList) {
				lineCustomer.setLineid(lineInfo.getId());
			}
			//lineInfo.setBasecustomers(lineCustomerList.size());
		}

		String type = request.getParameter("type");

		SysUser sysUser = getSysUser();
		lineInfo.setModifyuserid(sysUser.getUserid());
		lineInfo.setModifyusername(sysUser.getName());

		Map map = logisticsService.editLineInfo(lineInfo, lineCarList, lineCustomerList);

		addLog("编辑线路 编号:" + lineInfo.getId(), (Boolean) map.get("flag"));
		addJSONObject(map);

		return SUCCESS;
	}

	/**
	 * 删除线路档案
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 9, 2014
	 */
	@UserOperateLog(key = "LogisticsLine", type = 4, value = "")
	public String deleteLineInfo() throws Exception {
		String oldIdStr = request.getParameter("idStr");
		String[] idArr = oldIdStr.split(",");
		String newIdStr = "";
		int num = 0, userNum = 0, lockNum = 0;
		Map retMap = new HashMap();
		// 检测要删除线路的状态，选中记录判断是否被引用
		for (int i = 0; i < idArr.length; i++) {
			// 判断数据是否被加锁,True已被加锁。Fasle未加锁。（网络互斥）
			if (!isLock("t_base_logistics_line", idArr[i])) {
				// 判断是否被引用
				if (!canTableDataDelete("t_base_logistics_line", idArr[i])) {// true可以操作，false不可以操作
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
		if (StringUtils.isNotEmpty(newIdStr)) {
			String[] newIdArr = newIdStr.split(",");
			boolean flag = logisticsService.deleteLineInfos(newIdArr);
			if (flag) {
				num = newIdArr.length;
			}
			retMap.put("flag", flag);
			// 添加日志内容
			addLog("删除线路 编号:" + newIdStr, flag);
		}
		retMap.put("num", num);
		retMap.put("userNum", userNum);
		retMap.put("lockNum", lockNum);
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 启用线路档案
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 12, 2014
	 */
	@UserOperateLog(key = "LogisticsLine", type = 3, value = "")
	public String enableLineInfos() throws Exception {
		String oldIdStr = request.getParameter("idStr");
		String newIdStr = "";
		String[] idArr = oldIdStr.split(",");
		int invalidNum = 0, num = 0;
		Map retMap = new HashMap();
		// 检测要启用线路的状态，选中记录的状态为“保存”或“禁用”状态下才可启用
		for (int i = 0; i < idArr.length; i++) {
			LogisticsLine lineInfo = logisticsService.showLineInfo(idArr[i]);
			if (lineInfo != null) {
				if (!"2".equals(lineInfo.getState()) && !"0".equals(lineInfo.getState())) {// 状态不为保存或禁用状态，启用无效
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
			boolean flag = logisticsService.enableLineInfos(map);
			retMap.put("flag", flag);
			// 添加日志内容
			addLog("启用线路 编号:" + newIdStr, flag);
		}
		retMap.put("invalidNum", invalidNum);
		retMap.put("num", num);
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 禁用线路档案
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 13, 2014
	 */
	@UserOperateLog(key = "LogisticsLine", type = 3, value = "")
	public String disableLineInfos() throws Exception {
		String oldIdStr = request.getParameter("idStr");
		String[] idArr = oldIdStr.split(",");
		String newIdStr = "";
		int invalidNum = 0, num = 0;
		Map retMap = new HashMap();
		// 检测要禁用线路的状态，选中记录的状态为“启用”，且判断是否被引用
		for (int i = 0; i < idArr.length; i++) {
			LogisticsLine lineInfo = logisticsService.showLineInfo(idArr[i]);
			if (lineInfo != null) {
				// 判断状态是否为启用，不是，则视为无效禁用记录
				if (!"1".equals(lineInfo.getState())) {
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
			boolean flag = logisticsService.disableLineInfos(map);
			retMap.put("flag", flag);
			// 添加日志内容
			addLog("禁用线路 编号:" + newIdStr, flag);
		}
		retMap.put("invalidNum", invalidNum);
		retMap.put("num", num);
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 显示线路档案复制页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 13, 2014
	 */
	public String showLineInfoCopyPage() throws Exception {
		String id = request.getParameter("id");
		LogisticsLine lineInfo = logisticsService.showLineInfo(id);
		if (lineInfo != null) {

			Map colMap = getEditAccessColumn(tableNameLine);
			request.setAttribute("showMap", colMap);
			request.setAttribute("fieldmap", getRowDescFromDataDict(tableNameLine));
			request.setAttribute("lineInfo", lineInfo);
			request.setAttribute("lineid", id);
			request.setAttribute("operateType", "copy");
			request.setAttribute("type", "copy");

			List list = logisticsService.showCarInfoListByLineid(id);
			request.setAttribute("carlist", JSONUtils.listToJsonStr(list));
			
			String customerids = "";
			List<LogisticsLineCustomer> list2 = logisticsService.getLineCustomerListByLineid(id);
			if(list.size() != 0){
				for(LogisticsLineCustomer lineCustomer : list2){
					customerids +=  lineCustomer.getCustomerid() + ",";
				}
			}
			request.setAttribute("customerids", customerids);
		}
		return SUCCESS;
	}

	/**
	 * 显示车辆档案页面
	 * 
	 * @return
	 * @author yezhenyu
	 * @date Apr 24, 2014
	 */
	public String showCarListPage() {
		return SUCCESS;
	}

	/**
	 * 获取车辆档案列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Apr 25, 2014
	 */
	public String getCarListPage() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = logisticsService.getCarListPage(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 显示车辆档案详情页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date Apr 25, 2014
	 */
	public String showCarViewPage() throws Exception {
		String id = request.getParameter("id");
		LogisticsCar car = logisticsService.showCar(id);
		if (car != null) {
			request.setAttribute("car", car);
			request.setAttribute("carid", id);
			request.setAttribute("operateType", "view");
			request.setAttribute("type", "view");
		}
		return SUCCESS;
	}

	/**
	 * 显示车辆新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 2, 2014
	 */
	public String showCarAddPage() throws Exception {
		Map colMap = getEditAccessColumn(tableNameCar);
		request.setAttribute("showMap", colMap);
		request.setAttribute("operateType", "add");
		request.setAttribute("type", "add");
		return SUCCESS;
	}

	/**
	 * 添加车辆档案
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 5, 2014
	 */
	@UserOperateLog(key = "LogisticsCar", type = 2, value = "")
	public String addCar() throws Exception {
		String type = request.getParameter("type");
		boolean flag = false;
		// /if ("save".equals(type)) {
		car.setState("2");
		// } else {
		// carInfo.setState("3");
		// }
		SysUser sysUser = getSysUser();
		car.setAdddeptid(sysUser.getDepartmentid());
		car.setAdddeptname(sysUser.getDepartmentname());
		car.setAdduserid(sysUser.getUserid());
		car.setAddusername(sysUser.getName());
		// 判断车辆编码是否重复, true 重复，false 不重复
		if (StringUtils.isNotEmpty(car.getId())) {
			boolean isRepeatId = logisticsService.isRepeatCarID(car.getId());
			if (!isRepeatId) {
				flag = logisticsService.addCar(car);
			}
		}
		addLog("新增车辆 编号:" + car.getId(), flag);
		addJSONObject("flag", flag);

		return SUCCESS;
	}

	/**
	 * 判断车辆ID是否重复
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 14, 2014
	 */
	public String isRepeatCarId() throws Exception {
		String id = request.getParameter("id");
		boolean flag = logisticsService.isRepeatCarID(id);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 判断车辆名称是否重复
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 14, 2014
	 */
	public String isRepeatCarName() throws Exception {
		String name = request.getParameter("name");
		boolean flag = logisticsService.isRepeatCarName(name);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

	/**
	 * 显示车辆档案编辑页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 8, 2014
	 */
	public String showCarEditPage() throws Exception {
		String id = request.getParameter("id");
		LogisticsCar carInfo = logisticsService.showCar(id);
		if (carInfo != null) {
			Map colMap = getEditAccessColumn(tableNameCar);
			request.setAttribute("showMap", colMap);
			
			request.setAttribute("car", carInfo);
			request.setAttribute("carid", id);
			request.setAttribute("operateType", "edit");
			request.setAttribute("type", "edit");
			
			boolean canEdit = canTableDataDelete("t_base_logistics_car", id);
			request.setAttribute("canEdit", canEdit);
		}
		return SUCCESS;
	}

	/**
	 * 编辑车辆档案
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 8, 2014
	 */
	@UserOperateLog(key = "LogisticsCar", type = 3, value = "")
	public String editCar() throws Exception {
		String type = request.getParameter("type");

		SysUser sysUser = getSysUser();
		car.setModifyuserid(sysUser.getUserid());
		car.setModifyusername(sysUser.getName());

		Map map = logisticsService.editCar(car);

		addLog("编辑车辆 编号:" + car.getId(), (Boolean) map.get("flag"));
		addJSONObject(map);

		return SUCCESS;
	}

	/**
	 * 删除车辆档案
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 9, 2014
	 */
	@UserOperateLog(key = "LogisticsCar", type = 4, value = "")
	public String deleteCar() throws Exception {
		String oldIdStr = request.getParameter("idStr");
		String[] idArr = oldIdStr.split(",");
		String newIdStr = "";
		int num = 0, userNum = 0, lockNum = 0;
		Map retMap = new HashMap();
		// 检测要删除车辆的状态，选中记录判断是否被引用
		for (int i = 0; i < idArr.length; i++) {
			// 判断数据是否被加锁,True已被加锁。Fasle未加锁。（网络互斥）
			if (!isLock("t_base_logistics_car", idArr[i])) {
				// 判断是否被引用
				if (!canTableDataDelete("t_base_logistics_car", idArr[i])) {// true可以操作，false不可以操作
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
		if (StringUtils.isNotEmpty(newIdStr)) {
			String[] newIdArr = newIdStr.split(",");
			boolean flag = logisticsService.deleteCar(newIdArr);
			if (flag) {
				num = newIdArr.length;
			}
			retMap.put("flag", flag);
			// 添加日志内容
			addLog("删除车辆 编号:" + newIdStr, flag);
		}
		retMap.put("num", num);
		retMap.put("userNum", userNum);
		retMap.put("lockNum", lockNum);
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 启用车辆档案
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 12, 2014
	 */
	@UserOperateLog(key = "LogisticsCar", type = 3, value = "")
	public String enableCar() throws Exception {
		String oldIdStr = request.getParameter("idStr");
		String newIdStr = "";
		String[] idArr = oldIdStr.split(",");
		int invalidNum = 0, num = 0;
		Map retMap = new HashMap();
		// 检测要启用车辆的状态，选中记录的状态为“保存”或“禁用”状态下才可启用
		for (int i = 0; i < idArr.length; i++) {
			LogisticsCar carInfo = logisticsService.showCar(idArr[i]);
			if (carInfo != null) {
				if (!"2".equals(carInfo.getState()) && !"0".equals(carInfo.getState())) {// 状态不为保存或禁用状态，启用无效
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
			boolean flag = logisticsService.enableCar(map);
			retMap.put("flag", flag);
			// 添加日志内容
			addLog("启用车辆 编号:" + newIdStr, flag);
		}
		retMap.put("invalidNum", invalidNum);
		retMap.put("num", num);
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 禁用车辆档案
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 13, 2014
	 */
	@UserOperateLog(key = "LogisticsCar", type = 3, value = "")
	public String disableCar() throws Exception {
		String oldIdStr = request.getParameter("idStr");
		String[] idArr = oldIdStr.split(",");
		String newIdStr = "";
		int invalidNum = 0, num = 0;
		Map retMap = new HashMap();
		// 检测要禁用车辆的状态，选中记录的状态为“启用”，且判断是否被引用
		for (int i = 0; i < idArr.length; i++) {
			LogisticsCar carInfo = logisticsService.showCar(idArr[i]);
			if (carInfo != null) {
				// 判断状态是否为启用，不是，则视为无效禁用记录
				if (!"1".equals(carInfo.getState())) {
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
			boolean flag = logisticsService.disableCar(map);
			retMap.put("flag", flag);
			// 添加日志内容
			addLog("禁用车辆 编号:" + newIdStr, flag);
		}
		retMap.put("invalidNum", invalidNum);
		retMap.put("num", num);
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 显示车辆档案复制页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 13, 2014
	 */
	public String showCarCopyPage() throws Exception {
		String id = request.getParameter("id");
		LogisticsCar car = logisticsService.showCar(id);
		if (car != null) {
			Map colMap = getEditAccessColumn(tableNameCar);
			request.setAttribute("showMap", colMap);
			request.setAttribute("car", car);
			request.setAttribute("carid", id);
			request.setAttribute("operateType", "copy");
			request.setAttribute("type", "copy");
		}
		return SUCCESS;
	}

	/**
	 * 显示线路对应车辆新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public String showCarToLineAddPage() throws Exception {
		return SUCCESS;
	}

	/**
	 * 在线路档案中，获取ID不在condition.list中的车辆档案列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 17, 2014
	 */
	public String getCarListForCombobox() throws Exception {
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = logisticsService.getCarListForCombobox(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 在线路档案中，根据ids获取车辆档案列表
	 * 
	 * @return
	 * @throws Exception
	 * @author yezhenyu
	 * @date May 19, 2014
	 */
	public String showCarListForLine() throws Exception {
		String carids = request.getParameter("carids");
		List<LogisticsCar> list = logisticsService.getCarListForLine(carids);
		addJSONArray(list);
		return SUCCESS;
	}

	/**
	 * 保存线路档案的车辆信息
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Mar 13, 2018
	 */
    @UserOperateLog(key = "LogisticsLineCar", type = 2, value = "")
	public String saveCarForLine() {
		String carids=request.getParameter("carids");
		String lineid=request.getParameter("lineid");
		Boolean flag=logisticsService.addCarForLine(lineid,carids);
		addJSONObject("flag",flag);
        addLog("添加线路" + lineid+"下的车辆"+carids, flag);
		return SUCCESS;
	}
	
	/**
	 * 获取虚拟对应客户列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 25, 2013
	 */
	public String showLineCustomerListData()throws Exception{
		String customerids = request.getParameter("customerids");
		String lineid = request.getParameter("lineid");
		List<LogisticsLineCustomer> lineCustomerList = logisticsService.getLineCustomerListByLineid(lineid);
		int lastindex = lineCustomerList.size();
		if(lineCustomerList.size() != 0){
			String insertmapstr = request.getParameter("insertmap");
			JSONObject jsonMap = JSONObject.fromObject(insertmapstr);  
			Iterator<String> it = jsonMap.keys();
			while(it.hasNext()) {
		        String key = (String) it.next();
		        String val = (String) jsonMap.get(key);
		        LogisticsLineCustomer lineCustomer3 = logisticsService.getLogisticsLineCustomer(lineid, val);
		        if(null != lineCustomer3){
		        	String[] customerArr = key.split("\\|");
			        for(String customerid : customerArr){
			        	LogisticsLineCustomer lineCustomer = new LogisticsLineCustomer();
			        	lineCustomer.setLineid(lineid);
			        	lineCustomer.setSeq(lineCustomer3.getSeq());
			        	lineCustomer.setCustomerid(customerid);
			        	Customer customer = getBaseSalesService().getOnlyCustomer(customerid);
			        	if(null != customer){
			        		lineCustomer.setCustomername(customer.getName());
			        	}
			        	lineCustomerList.add(lineCustomer);
			        }
		        }else{
		        	String[] customerArr = key.split("\\|");
			        for(String customerid : customerArr){
			        	LogisticsLineCustomer lineCustomer = new LogisticsLineCustomer();
			        	lineCustomer.setLineid(lineid);
			        	lineCustomer.setSeq(lastindex + 1);
			        	lineCustomer.setCustomerid(customerid);
			        	Customer customer = getBaseSalesService().getOnlyCustomer(customerid);
			        	if(null != customer){
			        		lineCustomer.setCustomername(customer.getName());
			        	}
			        	lineCustomerList.add(lineCustomer);
			        }
		        }
		    }
			//冒泡排序-升序
			LogisticsLineCustomer lineCustomer = new LogisticsLineCustomer();
			for (int i = 0; i < lineCustomerList.size(); i++) {
				for (int j = 0; j < lineCustomerList.size() - 1; j++) {
					if (lineCustomerList.get(i).getSeq() < lineCustomerList.get(j).getSeq()) {
						lineCustomer = lineCustomerList.get(i);
						lineCustomerList.set(i, lineCustomerList.get(j));
						lineCustomerList.set(j, lineCustomer);
					}
				}
			}
			int upseq = 0;
			for (LogisticsLineCustomer lineCustomer2 : lineCustomerList) {
				upseq++;
				lineCustomer2.setSeq(upseq);
			}
			addJSONArray(lineCustomerList);
		}else{
			int upseq = 0;
			List<LogisticsLineCustomer> llcList = new ArrayList<LogisticsLineCustomer>();
			List<PersonnelCustomer> customerList = personnelService.getPersonCustomerListByCustomerids(customerids);
			for(PersonnelCustomer pCustomer : customerList){
				upseq++;
				pCustomer.setSeq(upseq);
				LogisticsLineCustomer lineCustomer = new LogisticsLineCustomer();
				PropertyUtils.copyProperties(lineCustomer, pCustomer);
				llcList.add(lineCustomer);
			}
			addJSONArray(llcList);
		}
		return SUCCESS;
	}
	
	/**
	 * 删除线路客户后排序
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 29, 2014
	 */
	public String showLineCustomerListDataByDel()throws Exception{
		String customerids = request.getParameter("customerids");
		String lineid = request.getParameter("lineid");
		String insertmapstr = request.getParameter("insertmap");
		List<LogisticsLineCustomer> lineCustomerList = logisticsService.getLineCustomerListByLineid(lineid);
		int lastindex = lineCustomerList.size();
		List<LogisticsLineCustomer> lCList = new ArrayList<LogisticsLineCustomer>();
		List<PersonnelCustomer> customerList = personnelService.getPersonCustomerListByCustomerids(customerids);
		if(lastindex != 0){
			for(PersonnelCustomer pCustomer : customerList){
				LogisticsLineCustomer lineCustomer = logisticsService.getLogisticsLineCustomer(lineid, pCustomer.getCustomerid());
				if(null != lineCustomer){
					pCustomer.setSeq(lineCustomer.getSeq());
				}else{
					JSONObject jsonMap = JSONObject.fromObject(insertmapstr);  
					Iterator<String> it = jsonMap.keys();
					while(it.hasNext()) {
				        String key = (String) it.next();
				        String val = (String) jsonMap.get(key);
				        if(pCustomer.getCustomerid().equals(key)){
				        	LogisticsLineCustomer lineCustomer3 = logisticsService.getLogisticsLineCustomer(lineid, val);
					        if(null != lineCustomer3){
					        	pCustomer.setSeq(lineCustomer3.getSeq());
					        }else{
					        	pCustomer.setSeq(lastindex + 1);
					        }
				        }
					}
				}
				LogisticsLineCustomer lineCustomer2 = new LogisticsLineCustomer();
				PropertyUtils.copyProperties(lineCustomer2, pCustomer);
				lCList.add(lineCustomer2);
			}
			//冒泡排序-升序
			LogisticsLineCustomer lineCustomer3 = new LogisticsLineCustomer();
			for (int i = 0; i < lCList.size(); i++) {
				for (int j = 0; j < lCList.size() - 1; j++) {
					if (lCList.get(i).getSeq() < lCList.get(j).getSeq()) {
						lineCustomer3 = lCList.get(i);
						lCList.set(i, lCList.get(j));
						lCList.set(j, lineCustomer3);
					}
				}
			}
		}
		int upseq = 0;
		for (LogisticsLineCustomer pCustomer2 : lCList) {
			upseq++;
			pCustomer2.setSeq(upseq);
		}
		addJSONArray(lCList);
		return SUCCESS;
	}
	
	/**
	 * 线路档案序号修补排序
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 24, 2014
	 */
	public String sortLineCustomerList()throws Exception{
		String lineid = request.getParameter("lineid");
		String editseqstr = request.getParameter("editseq");
		String customerid = request.getParameter("customerid");
		String oldseqstr = request.getParameter("oldseq");
		String customerids = request.getParameter("customerids");
		String insertmapstr = request.getParameter("insertmap");
		int editseq = Integer.parseInt(editseqstr);
		int oldseq = Integer.parseInt(oldseqstr);
		List<LogisticsLineCustomer> list = logisticsService.getLineCustomerListByLineid(lineid);
		int lastindex = list.size();
		for(LogisticsLineCustomer lineCustomer : list){
			if(customerid.equals(lineCustomer.getCustomerid())){
				if(editseq < oldseq){
					lineCustomer.setSeq(editseq - 1);
				}else{
					lineCustomer.setSeq(editseq);
				}
			}
		}
		List<PersonnelCustomer> customerList = personnelService.getPersonCustomerListByCustomerids(customerids);
		for(PersonnelCustomer pCustomer : customerList){
			LogisticsLineCustomer lineCustomer = logisticsService.getLogisticsLineCustomer(lineid, pCustomer.getCustomerid());
			if(null != lineCustomer){
				pCustomer.setSeq(lineCustomer.getSeq());
			}else{
				JSONObject jsonMap = JSONObject.fromObject(insertmapstr);  
				Iterator<String> it = jsonMap.keys();
				while(it.hasNext()) {
			        String key = (String) it.next();
			        String val = (String) jsonMap.get(key);
			        if(pCustomer.getCustomerid().equals(key)){
			        	LogisticsLineCustomer lineCustomer2 = new LogisticsLineCustomer();
						lineCustomer2.setCustomerid(pCustomer.getCustomerid());
						lineCustomer2.setCustomername(pCustomer.getCustomername());
						lineCustomer2.setLineid(lineid);
			        	LogisticsLineCustomer lineCustomer3 = logisticsService.getLogisticsLineCustomer(lineid, val);
				        if(null != lineCustomer3){
				        	lineCustomer2.setSeq(lineCustomer3.getSeq());
				        }else{
				        	lineCustomer2.setSeq(lastindex + 1);
				        }
				        list.add(lineCustomer2);
			        }
				}
			}
		}
	    LogisticsLineCustomer lineCustomer = new LogisticsLineCustomer();
	    for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size() - 1; j++) {
				if (list.get(i).getSeq() < list.get(j).getSeq()) {
					lineCustomer = list.get(i);
					list.set(i, list.get(j));
					list.set(j, lineCustomer);
				}
			}
		}
	    int upseq = 0;
	    for(LogisticsLineCustomer lineCustomer2 : list){
	    	upseq++;
	    	lineCustomer2.setSeq(upseq);
		}
		addJSONArray(list);
		return SUCCESS;
	}

    /**
     * 显示客户坐标页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 6, 2016
     */
	public String customerToLineMapPage() throws Exception {

	    List<Map> locations = getBaseSalesService().getCustomerLocationList(null);
        String center = getSysParamValue("LocationPoint");
        String start = getSysParamValue("DeliveryStartPoint");
        String companyName = getSysParamValue("COMPANYNAME");

        List<LogisticsLineCustomer> lineCustomers = logisticsService.getLineCustomerMapList(null, false);

        request.setAttribute("locations", locations);
        request.setAttribute("center", center);
        request.setAttribute("start", start);
        request.setAttribute("companyName", companyName);
        request.setAttribute("lineCustomers", lineCustomers);
        return SUCCESS;
    }

	/**
	 * 添加线路档案的客户信息
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Mar 14, 2018
	 */
	@UserOperateLog(key = "LogisticsLineCustomer", type = 2, value = "")
    public String saveCustomerForLine() throws Exception{
		String customerids = request.getParameter("customerids");
		String lineid = request.getParameter("lineid");
		List<LogisticsLineCustomer> lineCustomerList = logisticsService.getLineCustomerListByLineid(lineid);
		int lastindex = lineCustomerList.size();
		//获取添加的客户编码，写到日志里面
		String addcustomerids="";

		if(lineCustomerList.size() != 0){
			String insertmapstr = request.getParameter("insertmap");
			JSONObject jsonMap = JSONObject.fromObject(insertmapstr);
			Iterator<String> it = jsonMap.keys();
			while(it.hasNext()) {
				String key = (String) it.next();
				String val = (String) jsonMap.get(key);
				LogisticsLineCustomer lineCustomer3 = logisticsService.getLogisticsLineCustomer(lineid, val);
				if(null != lineCustomer3){
					String[] customerArr = key.split("\\|");
					for(String customerid : customerArr){
						LogisticsLineCustomer lineCustomer = new LogisticsLineCustomer();
						lineCustomer.setLineid(lineid);
						lineCustomer.setSeq(lineCustomer3.getSeq());
						lineCustomer.setCustomerid(customerid);
						Customer customer = getBaseSalesService().getOnlyCustomer(customerid);
						if(null != customer){
							lineCustomer.setCustomername(customer.getName());
						}
						lineCustomerList.add(lineCustomer);
						if(StringUtils.isEmpty(addcustomerids)){
							addcustomerids=customerid;
						}else{
							addcustomerids+=","+customerid;
						}
					}
				}else{
					String[] customerArr = key.split("\\|");
					for(String customerid : customerArr){
						LogisticsLineCustomer lineCustomer = new LogisticsLineCustomer();
						lineCustomer.setLineid(lineid);
						lineCustomer.setSeq(lastindex + 1);
						lineCustomer.setCustomerid(customerid);
						Customer customer = getBaseSalesService().getOnlyCustomer(customerid);
						if(null != customer){
							lineCustomer.setCustomername(customer.getName());
						}
						lineCustomerList.add(lineCustomer);
						if(StringUtils.isEmpty(addcustomerids)){
							addcustomerids=customerid;
						}else{
							addcustomerids+=","+customerid;
						}
					}
				}
			}
			//冒泡排序-升序
			LogisticsLineCustomer lineCustomer = new LogisticsLineCustomer();
			for (int i = 0; i < lineCustomerList.size(); i++) {
				for (int j = 0; j < lineCustomerList.size() - 1; j++) {
					if (lineCustomerList.get(i).getSeq() < lineCustomerList.get(j).getSeq()) {
						lineCustomer = lineCustomerList.get(i);
						lineCustomerList.set(i, lineCustomerList.get(j));
						lineCustomerList.set(j, lineCustomer);
					}
				}
			}
			int upseq = 0;
			for (LogisticsLineCustomer lineCustomer2 : lineCustomerList) {
				upseq++;
				lineCustomer2.setSeq(upseq);
			}
			addJSONArray(lineCustomerList);
		}else{
			int upseq = 0;
			List<PersonnelCustomer> customerList = personnelService.getPersonCustomerListByCustomerids(customerids);
			for(PersonnelCustomer pCustomer : customerList){
				upseq++;
				pCustomer.setSeq(upseq);
				LogisticsLineCustomer lineCustomer = new LogisticsLineCustomer();
				PropertyUtils.copyProperties(lineCustomer, pCustomer);
				lineCustomerList.add(lineCustomer);
			}
		}
		Boolean flag=logisticsService.saveCustomerForLine(lineid,lineCustomerList);
		addJSONObject("flag",flag);
		// 添加日志内容
		addLog("添加线路" + lineid+"下的客户"+addcustomerids, flag);
    	return SUCCESS;
	}

	/**
	 * 删除线路的客户档案
	 * @param
	 * @return java.lang.String
	 * @throws
	 * @author luoqiang
	 * @date Mar 14, 2018
	 */
	@UserOperateLog(key = "LogisticsLineCustomer", type = 4, value = "")
	public String deleteCustomerForLine() throws Exception{
    	String lineid=request.getParameter("lineid");
    	String customerids=request.getParameter("customerids");
    	Boolean flag=logisticsService.deleteCustomerForLine(lineid,customerids);
    	addJSONObject("flag",flag);
		// 添加日志内容
		addLog("删除线路" + lineid+"下的客户"+customerids, flag);
    	return SUCCESS;
	}

    /**
     * 删除线路档案的车辆信息
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 16, 2018
     */
    @UserOperateLog(key = "LogisticsLineCar", type = 4, value = "")
	public String deleteCarForLine() {
	    String lineid=request.getParameter("lineid");
	    String carids=request.getParameter("carids");
	    Boolean flag=logisticsService.deleteCarForLine(lineid,carids);
	    addJSONObject("flag",flag);
        // 添加日志内容
        addLog("删除线路" + lineid+"下的车辆"+carids, flag);
	    return SUCCESS;
    }
}

/**
 * @(#)LogisticsServiceImpl.java
 *
 * @author yezhenyu
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Apr 24, 2014 yezhenyu 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.LogisticsMapper;
import com.hd.agent.basefiles.dao.PersonnelMapper;
import com.hd.agent.basefiles.dao.SalesAreaMapper;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.LogisticsCar;
import com.hd.agent.basefiles.model.LogisticsLine;
import com.hd.agent.basefiles.model.LogisticsLineCar;
import com.hd.agent.basefiles.model.LogisticsLineCustomer;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.SalesArea;
import com.hd.agent.basefiles.service.ILogisticsService;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysCode;

/**
 * 
 * 
 * @author yezhenyu
 */
public class LogisticsServiceImpl extends BaseFilesServiceImpl implements ILogisticsService {

	private final String tableNameLine = "t_base_logistics_line";

	private LogisticsMapper logisticsMapper;
	private PersonnelMapper personnelMapper;
	private SalesAreaMapper salesAreaMapper;

	public LogisticsMapper getLogisticsMapper() {
		return logisticsMapper;
	}

	public void setLogisticsMapper(LogisticsMapper logisticsMapper) {
		this.logisticsMapper = logisticsMapper;
	}

	public SalesAreaMapper getSalesAreaMapper() {
		return salesAreaMapper;
	}

	public void setSalesAreaMapper(SalesAreaMapper salesAreaMapper) {
		this.salesAreaMapper = salesAreaMapper;
	}

	public PersonnelMapper getPersonnelMapper() {
		return personnelMapper;
	}

	public void setPersonnelMapper(PersonnelMapper personnelMapper) {
		this.personnelMapper = personnelMapper;
	}

	@Override
	public PageData lineInfoListPage(PageMap pageMap) throws Exception {
		// 单表取字段权限
		String cols = getAccessColumnList("t_base_logistics_line", null);
		pageMap.setCols(cols);
		// 数据权限
		String sql = getDataAccessRule("t_base_logistics_line", null);
		pageMap.setDataSql(sql);
		List<LogisticsLine> list = logisticsMapper.getLineInfoListPage(pageMap);
		List<SysCode> codeList = getBaseSysCodeMapper().getSysCodeListForeign("state");
		if (list.size() != 0) {
			for (LogisticsLine logisticsLine : list) {
				if (StringUtils.isNotEmpty(logisticsLine.getState())) { // 状态
					if (codeList.size() != 0) {
						for (SysCode sysCode : codeList) {
							if (logisticsLine.getState().equals(sysCode.getCode())) {
								logisticsLine.setStateName(sysCode.getCodename());
							}
						}
					}
				}
				// 默认车辆
				String carid = logisticsLine.getCarid();
				LogisticsCar logisticsCar = logisticsMapper.getDefaultCarByLineId(carid);
				if (logisticsCar != null)
					logisticsLine.setCarname(logisticsCar.getName());
				// 区域名称
				if (StringUtils.isNotEmpty(logisticsLine.getSalesarea())) {
					Map map = new HashMap();
					map.put("id", logisticsLine.getSalesarea());
					SalesArea salesArea = salesAreaMapper.getSalesAreaDetail(map);
					if (salesArea != null) {
						String saleName = salesArea.getThisname();
						logisticsLine.setSalesarea(saleName);
					}
				}
			}
		}
		PageData pageData = new PageData(logisticsMapper.getLineInfoListCount(pageMap), list, pageMap);
		return pageData;
	}

	@Override
	public LogisticsLine showLineInfo(String id) throws Exception {
		LogisticsLine logisticsLine = logisticsMapper.getLineInfo(id);
		// 默认车辆
		String carid = logisticsLine.getCarid();
		LogisticsCar logisticsCar = logisticsMapper.getDefaultCarByLineId(carid);
		if (logisticsCar != null)
			logisticsLine.setCarname(logisticsCar.getName());

		return logisticsLine;
	}

	@Override
	public PageData showCarInfoList(PageMap pageMap) {
		int count = logisticsMapper.getCarInfoListCount(pageMap);
		List<LogisticsCar> list = logisticsMapper.getCarInfoList(pageMap);

		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}

	@Override
	public PageData showCustomerInfoList(PageMap pageMap) throws Exception {
		int count = logisticsMapper.getLineCustomerCount(pageMap);
		List<LogisticsLineCustomer> list = logisticsMapper.getLineCustomerList(pageMap);
		PageData pageData = new PageData(count, list, pageMap);
		return pageData;
	}

	@Override
	public boolean isRepeatLineInfoID(String id) throws Exception {
		return logisticsMapper.isRepeatLineInfoID(id) > 0;
	}

	@Override
	public boolean addLineInfo(LogisticsLine lineInfo, List<LogisticsLineCar> lineCarList,
			List<LogisticsLineCustomer> lineCustomerList) throws Exception {

		if (lineCarList != null) {
			for (LogisticsLineCar lineCar : lineCarList) {
				logisticsMapper.addLineCar(lineCar);
			}
		}

		if (lineCustomerList != null) {
			for (LogisticsLineCustomer lineCustomer : lineCustomerList) {
				logisticsMapper.addLineCustomer(lineCustomer);
			}
		}

		int g = logisticsMapper.addLineInfo(lineInfo);
		return g > 0;
	}

	@Override
	public Map editLineInfo(LogisticsLine lineInfo, List<LogisticsLineCar> lineCarList,
			List<LogisticsLineCustomer> lineCustomerList) throws Exception {
		String id = lineInfo.getId();
		String oldId = lineInfo.getOldid();
		Map map = new HashMap();
		boolean lockFlag = false;
		boolean flag = false;

		if (!isLockEdit("t_base_logistics_line", oldId)) {
			map.put("lockFlag", lockFlag);
			return map;
		}

		lockFlag = true;
		if (!id.equals(oldId)) {
			if (isRepeatLineInfoID(id)) {
				map.put("lockFlag", lockFlag);
				map.put("flag", flag);
				return map;
			}

			logisticsMapper.updateLineCar(lineInfo);
			logisticsMapper.updateLineCustomer(lineInfo);

		}

//		if (lineCarList != null) {
//			logisticsMapper.deleteLineCar(oldId);
//			for (LogisticsLineCar lineCar : lineCarList) {
//				logisticsMapper.addLineCar(lineCar);
//			}
//		}

//		if (lineCustomerList != null) {
//			logisticsMapper.deleteLineCustomer(oldId);
//			for (LogisticsLineCustomer lineCustomer : lineCustomerList) {
//				logisticsMapper.addLineCustomer(lineCustomer);
//			}
//		}

		int g = logisticsMapper.updateLineInfo(lineInfo);

		flag = g > 0;
		map.put("lockFlag", lockFlag);
		map.put("flag", flag);

		return map;
	}

	@Override
	public boolean deleteLineInfos(String[] idsArr) throws Exception {
		int g = logisticsMapper.deleteLineInfos(idsArr);
		int count = 0;
		if (g > 0) {
			for (String id : idsArr) {
				logisticsMapper.deleteLineCar(id);
				logisticsMapper.deleteLineCustomer(id);
				count++;
			}
			if (count == idsArr.length)
				return true;
		}
		return false;
	}

	@Override
	public boolean enableLineInfos(Map map) throws Exception {
		return logisticsMapper.enableLineInfos(map) > 0;
	}

	@Override
	public boolean disableLineInfos(Map map) throws Exception {
		return logisticsMapper.disableLineInfos(map) > 0;
	}

	@Override
	public boolean addCar(LogisticsCar carInfo) throws Exception {
		return logisticsMapper.addCar(carInfo) > 0;
	}

	@Override
	public boolean isRepeatCarID(String id) throws Exception {

		return logisticsMapper.isRepeatCarID(id) > 0;
	}

	@Override
	public boolean isRepeatCarName(String name) throws Exception {
		return logisticsMapper.isRepeatCarName(name) > 0;
	}

	@Override
	public boolean deleteCar(String[] idsArr) throws Exception {

		return logisticsMapper.deleteCar(idsArr) > 0;
	}

	@Override
	public boolean disableCar(Map map) throws Exception {
		return logisticsMapper.disableCar(map) > 0;
	}

	@Override
	public Map editCar(LogisticsCar car) throws Exception {

		String id = car.getId();
		String oldId = car.getOldid();
		Map map = new HashMap();
		boolean lockFlag = false;
		boolean flag = false;

		if (!isLockEdit("t_base_logistics_line", oldId)) {
			map.put("lockFlag", lockFlag);
			return map;
		}

		if (!id.equals(oldId)) {
			if (isRepeatLineInfoID(id)) {
				map.put("flag", flag);
				return map;
			}
		}

		int g = logisticsMapper.updateCar(car);

		flag = g > 0;
		map.put("flag", flag);

		return map;
	}

	@Override
	public boolean enableCar(Map map) throws Exception {

		return logisticsMapper.enableCar(map) > 0;
	}

	@Override
	public LogisticsCar showCar(String id) throws Exception {

		return logisticsMapper.getCar(id);
	}

	@Override
	public PageData getCarListPage(PageMap pageMap) throws Exception {
		// 单表取字段权限
		String cols = getAccessColumnList("t_base_logistics_car", null);
		pageMap.setCols(cols);
		// 数据权限
		String sql = getDataAccessRule("t_base_logistics_car", null);
		pageMap.setDataSql(sql);
		List<LogisticsCar> list = logisticsMapper.getCarListPage(pageMap);
		if (list.size() != 0) {
			for (LogisticsCar logisticsCar : list) {
				if (StringUtils.isNotEmpty(logisticsCar.getState())) { // 状态
					List<SysCode> codeList = getBaseSysCodeMapper().getSysCodeListForeign("state");
					if (codeList.size() != 0) {
						for (SysCode sysCode : codeList) {
							if (logisticsCar.getState().equals(sysCode.getCode())) {
								logisticsCar.setStateName(sysCode.getCodename());
							}
						}
					}
				}
				if (StringUtils.isNotEmpty(logisticsCar.getDriverid())) {
					Personnel personnel = personnelMapper.getPersonnelInfo(logisticsCar.getDriverid());
					if(null != personnel){
						logisticsCar.setDriverName(personnel.getName());
					}
				}
				if (StringUtils.isNotEmpty(logisticsCar.getFollowid())) {
					Personnel personnel = personnelMapper.getPersonnelInfo(logisticsCar.getFollowid());
					if(null != personnel){
						logisticsCar.setFollowName(personnel.getName());
					}
				}
			}
		}
		PageData pageData = new PageData(logisticsMapper.getCarListCount(pageMap), list, pageMap);
		return pageData;
	}

	@Override
	public PageData getCarListForCombobox(PageMap pageMap) {
		List<LogisticsCar> carList = logisticsMapper.getCarListForCombobox(pageMap);
		for (LogisticsCar logisticsCar : carList) {
			if (StringUtils.isNotEmpty(logisticsCar.getDriverid())) {
				Personnel personnel = personnelMapper.getPersonnelInfo(logisticsCar.getDriverid());
				if(null != personnel){
					logisticsCar.setDriverName(personnel.getName());
				}
			}
			if (StringUtils.isNotEmpty(logisticsCar.getFollowid())) {
				Personnel personnel = personnelMapper.getPersonnelInfo(logisticsCar.getFollowid());
				if(null != personnel){
					logisticsCar.setFollowName(personnel.getName());
				}
			}
		}
		PageData pageData = new PageData(logisticsMapper.getCarListForComboboxCount(pageMap), carList, pageMap);
		return pageData;
	}

	@Override
	public List<LogisticsCar> getCarListForLine(String carids) {
		return logisticsMapper.getCarListForLine(carids);
	}

	@Override
	public List<LogisticsCar> showCarInfoListByLineid(String id) {
		return logisticsMapper.getCarInfoListByLineid(id);
	}

	@Override
	public Map addDRLogisticsLine(LogisticsLine lineInfo) throws Exception {
		boolean flag = false;
		String failStr = "",closeVal = "",repeatVal = "",errorVal = "",msg = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0,errorNum = 0;
		try {
			if(StringUtils.isNotEmpty(lineInfo.getId())){
				if(logisticsMapper.isRepeatLineInfoID(lineInfo.getId()) > 0){
					LogisticsLine lineInfo2 = logisticsMapper.getLineInfo(lineInfo.getId());
					if(null != lineInfo2){
						if("0".equals(lineInfo2.getState())){//禁用状态，不允许导入
							if(StringUtils.isEmpty(closeVal)){
								closeVal = lineInfo2.getId();
							}
							else{
								closeVal += "," + lineInfo2.getId();
							}
							closeNum++;
						}
						else{
							SysUser sysUser = getSysUser();
							lineInfo.setModifyuserid(sysUser.getUserid());
							lineInfo.setModifyusername(sysUser.getName());
							lineInfo.setOldid(lineInfo.getId());
							flag = logisticsMapper.updateLineInfo(lineInfo) > 0;
							if(StringUtils.isEmpty(repeatVal)){
								repeatVal = lineInfo2.getId();
							}
							else{
								repeatVal += "," + lineInfo2.getId();
							}
							repeatNum++;
						}
					}
				}else{
					SysUser sysUser = getSysUser();
					lineInfo.setAdduserid(sysUser.getUserid());
					lineInfo.setAddusername(sysUser.getName());
					lineInfo.setAdddeptid(sysUser.getDepartmentid());
					lineInfo.setAdddeptname(sysUser.getDepartmentname());
					if(StringUtils.isEmpty(lineInfo.getState())){
						lineInfo.setState("1");
					}
					flag = logisticsMapper.addLineInfo(lineInfo) >0 ;
					if(!flag){
						if(StringUtils.isNotEmpty(failStr)){
							failStr += "," + lineInfo.getId();
						}
						else{
							failStr = lineInfo.getId();
						}
						failureNum++;
					}else{
						successNum++;
						if(StringUtils.isNotEmpty(lineInfo.getCarid())){
							LogisticsLineCar lineCar = new LogisticsLineCar();
							lineCar.setLineid(lineInfo.getId());
							lineCar.setId(lineInfo.getCarid());
							lineCar.setIsdefault("1");
							logisticsMapper.addLineCar(lineCar);
						}
					}
				}
			}else{
				msg = "编码为空，不允许导入";
			}
		} catch (Exception e) {
			if(StringUtils.isEmpty(repeatVal)){
				errorVal = lineInfo.getId();
			}
			else{
				errorVal += "," + lineInfo.getId();
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
		map.put("msg",msg);
		return map;
	}

	@Override
	public Map addDRLogisticsLineCar(List<LogisticsLineCar> list)
			throws Exception {
		Map map = new HashMap();
		boolean flag = true;
		if (list.size() != 0) {
			for(LogisticsLineCar lineCar : list){
				try {
					if(StringUtils.isNotEmpty(lineCar.getLineid())){
						if(logisticsMapper.isExistLineCar(lineCar.getLineid(), lineCar.getCarid()) == 0){
							LogisticsLine line = logisticsMapper.getLineInfo(lineCar.getLineid());
							if(null != line){
								if(!line.getCarid().equals(lineCar.getCarid())){
									lineCar.setId(lineCar.getCarid());
									lineCar.setIsdefault("0");
									logisticsMapper.addLineCar(lineCar);
								}
							}
						}
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
	public Map addDRLogisticsLineCustomer(List<LogisticsLineCustomer> list)
			throws Exception {
		Map map = new HashMap();
		boolean retflag = true;
		if(list.size() != 0){
			String lineid = "";
			int seq = 999999;
			for(LogisticsLineCustomer lineCustomer : list){
				try {
					if(StringUtils.isNotEmpty(lineCustomer.getLineid()) && StringUtils.isNotEmpty(lineCustomer.getCustomerid())){
						//导入前判断客户是否存在
						Customer customer = getBaseCustomerMapper().getCustomerInfo(lineCustomer.getCustomerid());
						if(null == customer){
							List<Customer> customerList = getBaseCustomerMapper().getCustomerByName(lineCustomer.getCustomerid());
							if(customerList.size() !=0){
								customer = customerList.get(0);
							}
						}
						if(null != customer){
							if(logisticsMapper.isExistLineCustomer(lineCustomer.getLineid(), lineCustomer.getCustomerid()) == 0){
								if(null != lineCustomer.getId()){
									lineCustomer.setId(null);
								}
								if(null == lineCustomer.getSeq()){
									lineCustomer.setSeq(seq--);
								}
								logisticsMapper.addLineCustomer(lineCustomer);
							}else{
								logisticsMapper.updateDRLineCustomer(lineCustomer);
							}
						}
					}
				} catch (Exception e) {
					retflag = false;
					break;
				}
			}
			LogisticsLine line = logisticsMapper.getLineInfo(list.get(0).getLineid());
			if(null != line){
				List lineCustomerList = logisticsMapper.getCustomerListByLineid(list.get(0).getLineid());
				line.setTotalcustomers(lineCustomerList.size());
				line.setOldid(list.get(0).getLineid());
				logisticsMapper.updateLineInfo(line);
			}
		}
		map.put("flag", retflag);
		return map;
	}

	@Override
	public LogisticsLineCustomer getLogisticsLineCustomer(String lineid,
			String customerid) throws Exception {
		return logisticsMapper.getLogisticsLineCustomer(lineid,customerid);
	}

	@Override
	public List getLineCustomerListByLineid(String lineid) throws Exception {
		List<LogisticsLineCustomer> list = logisticsMapper.getCustomerListByLineid(lineid);
		for(LogisticsLineCustomer lineCustomer : list){
			Customer customer = getBaseCustomerMapper().getCustomerInfo(lineCustomer.getCustomerid());
			if(null != customer){
				lineCustomer.setCustomername(customer.getName());
			}
		}
		return list;
	}

	@Override
	public Map addDRCar(List<LogisticsCar> list) throws Exception {
		String failStr = "",closeVal = "",repeatVal = "",errorVal = "";
		int closeNum = 0, repeatNum = 0,failureNum = 0,successNum = 0,errorNum = 0;
		boolean flag = false;
		if(null != list && list.size() != 0){
			for(LogisticsCar logisticsCar : list){
				try {
					logisticsCar.setState("1");
					SysUser sysUser = getSysUser();
					logisticsCar.setAdddeptid(sysUser.getDepartmentid());
					logisticsCar.setAdddeptname(sysUser.getDepartmentname());
					logisticsCar.setAdduserid(sysUser.getUserid());
					logisticsCar.setAddusername(sysUser.getName());
					if(StringUtils.isNotEmpty(logisticsCar.getId())){
						boolean repeatflag = logisticsMapper.isRepeatCarID(logisticsCar.getId()) > 0;
						if(repeatflag){//重复
							LogisticsCar carinfo = logisticsMapper.getCar(logisticsCar.getId());
							if(null != carinfo){
								if("0".equals(carinfo.getState())){//禁用状态，不允许导入
									if(StringUtils.isEmpty(closeVal)){
										closeVal = carinfo.getId();
									}
									else{
										closeVal += "," + carinfo.getId();
									}
									closeNum++;
								}
								else{
									if(StringUtils.isEmpty(repeatVal)){
										repeatVal = carinfo.getId();
									}
									else{
										repeatVal += "," + carinfo.getId();
									}
									repeatNum++;
									logisticsMapper.deleteCar(logisticsCar.getId().split(","));
									logisticsMapper.addCar(logisticsCar);
								}
							}
						}else{
							flag = logisticsMapper.addCar(logisticsCar) > 0;
							if(!flag){
								if(StringUtils.isNotEmpty(failStr)){
									failStr += "," + logisticsCar.getId(); 
								}
								else{
									failStr = logisticsCar.getId();
								}
								failureNum++;
							}
							else{
								successNum++;
							}
						}
					}else{
						String id = super.getAutoCreateSysNumbderForeign(logisticsCar, "t_base_logistics_car");
						if(StringUtils.isNotEmpty(id)){
							logisticsCar.setId(id);
							flag = logisticsMapper.addCar(logisticsCar) > 0;
							if(!flag){
								if(StringUtils.isNotEmpty(failStr)){
									failStr += "," + id; 
								}
								else{
									failStr = id;
								}
								failureNum++;
							}
							else{
								successNum++;
							}
						}
					}
				} catch (Exception e) {
					if(StringUtils.isEmpty(errorVal)){
						errorVal = logisticsCar.getId();
					}
					else{
						errorVal += "," + logisticsCar.getId();
					}
					errorNum++;
					continue;
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
		map.put("errorNum", errorNum);
		map.put("errorVal", errorVal);
		return map;
	}

    @Override
    public LogisticsCar getDefaultCarByLineId(String id) {
        return logisticsMapper.getDefaultCarByLineId(id);
    }

    @Override
    public List<LogisticsLineCustomer> getLineCustomerMapList(List<String> customerids, boolean linelocation) throws Exception {
        return logisticsMapper.getLineCustomerMapList(customerids, linelocation);
    }

    @Override
    public List<LogisticsLine> getLogisticsLines() throws Exception {
        return logisticsMapper.getLogisticsLines();
    }

	/**
	 * 线路档案添加客户信息
	 * @param lineid
	 * @param logisticsLineCustomerList
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Mar 14, 2018
	 */
	public Boolean saveCustomerForLine(String lineid,List<LogisticsLineCustomer> logisticsLineCustomerList){
		LogisticsLine logisticsLine=logisticsMapper.getLineInfo(lineid);
		if(logisticsLine==null){
			return false;
		}
        if (logisticsLineCustomerList != null&&logisticsLineCustomerList.size()>0) {
            logisticsMapper.deleteLineCustomer(lineid);
            for (LogisticsLineCustomer lineCustomer : logisticsLineCustomerList) {
				lineCustomer.setLineid(lineid);
                logisticsMapper.addLineCustomer(lineCustomer);
            }
        }
        //更新线路的客户数量
		logisticsMapper.updatLineCustomerNum(lineid);
		return true;
	}

    /**
     * 删除线路的客户档案
     * @param lineid
     * @param customerids
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 14, 2018
     */
    public Boolean deleteCustomerForLine(String lineid,String customerids) throws Exception{
        LogisticsLine logisticsLine=logisticsMapper.getLineInfo(lineid);
        if(logisticsLine==null){
            return false;
        }
        //删除要删除的客户
        Boolean flag=logisticsMapper.deleteCustomerForLine(lineid,customerids)>0;
        if(!flag){
        	return false;
		}
		//获取当前线路的客户，进行重新排序
		List<LogisticsLineCustomer> lineCustomerList = getLineCustomerListByLineid(lineid);
		int upseq = 0;
		for (LogisticsLineCustomer lineCustomer2 : lineCustomerList) {
			upseq++;
			lineCustomer2.setSeq(upseq);
		}
		//保存排序后的客户
		flag=saveCustomerForLine(lineid,lineCustomerList);
        return flag;
    }

	/**
	 * 添加线路车辆信息
	 * @param lineid
	 * @param carids
	 * @return java.util.Map
	 * @throws
	 * @author luoqiang
	 * @date Mar 13, 2018
	 */
	@Override
	public Boolean addCarForLine(String lineid,String carids){
		LogisticsLine logisticsLine=logisticsMapper.getLineInfo(lineid);
		if(logisticsLine==null){
			return false;
		}
		LogisticsLineCar logisticsLineCar=new LogisticsLineCar();
		String []cararr=carids.split(",");
		for(String carid:cararr){
			logisticsLineCar.setLineid(lineid);
			logisticsLineCar.setId(carid);
			logisticsLineCar.setIsdefault("0");
			logisticsMapper.addLineCar(logisticsLineCar);
		}
		return true;
	}

    /**
     * 删除线路的车辆信息
     * @param lineid
     * @param carids
     * @return java.lang.Boolean
     * @throws
     * @author luoqiang
     * @date Mar 16, 2018
     */
    public Boolean deleteCarForLine(String lineid,String carids){
        LogisticsLine logisticsLine=logisticsMapper.getLineInfo(lineid);
        if(logisticsLine==null){
            return false;
        }
        //删除要删除的客户
        Boolean flag=logisticsMapper.deleteCarForLine(lineid,carids)>0;
        return flag;
    }
}

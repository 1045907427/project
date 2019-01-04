/**
 * @(#)LargeSingleServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 11, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.storage.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.basefiles.model.StorageLocation;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.dao.BigSaleOutMapper;
import com.hd.agent.storage.dao.SaleoutMapper;
import com.hd.agent.storage.model.BigSaleOut;
import com.hd.agent.storage.model.BigSaleOutDetail;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.service.IBigSaleOutService;
import com.hd.agent.system.model.SysParam;

/**
 * 大单发货 service
 * 
 * @author panxiaoxiao
 */
public class BigSaleOutServiceImpl extends BaseFilesServiceImpl implements IBigSaleOutService {

	private BigSaleOutMapper bigSaleOutMapper;
	
	private SaleoutMapper saleoutMapper;
	
	private StorageSaleOutServiceImpl storageSaleOutServiceImpl;
	
	public SaleoutMapper getSaleoutMapper() {
		return saleoutMapper;
	}

	public void setSaleoutMapper(SaleoutMapper saleoutMapper) {
		this.saleoutMapper = saleoutMapper;
	}

	public BigSaleOutMapper getBigSaleOutMapper() {
		return bigSaleOutMapper;
	}

	public void setBigSaleOutMapper(BigSaleOutMapper bigSaleOutMapper) {
		this.bigSaleOutMapper = bigSaleOutMapper;
	}
	
	public StorageSaleOutServiceImpl getStorageSaleOutServiceImpl() {
		return storageSaleOutServiceImpl;
	}

	public void setStorageSaleOutServiceImpl(
			StorageSaleOutServiceImpl storageSaleOutServiceImpl) {
		this.storageSaleOutServiceImpl = storageSaleOutServiceImpl;
	}

	@Override
	public PageData getBigSaleOutList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_storage_bigsaleout", "t");
		pageMap.setDataSql(dataSql);
		List<BigSaleOut> list = bigSaleOutMapper.getBigSaleOutList(pageMap);
		for(BigSaleOut bigSaleOut : list){
			StorageInfo storageInfo = getBaseStorageMapper().showBaseStorageInfo(bigSaleOut.getStorageid());
			if(null != storageInfo){
				bigSaleOut.setStoragename(storageInfo.getName());
			}
		}
		PageData pageData = new PageData(bigSaleOutMapper.getBigSaleOutCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public PageData getSaleOutListForBigSaleOut(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_storage_saleout", "t");
		pageMap.setQueryAlias("t");
		pageMap.setDataSql(dataSql);
		PageData pageData = new PageData(saleoutMapper.getSaleOutCountForBigSaleOut(pageMap),saleoutMapper.getSaleOutListForBigSaleOut(pageMap),pageMap);
		List<Saleout> list = pageData.getList();
		for(Saleout saleout : list){
			Personnel indoorPerson = getBasePersonnelMapper().getPersonnelInfo(saleout.getIndooruserid());
			if(null!=indoorPerson){
				saleout.setIndoorusername(indoorPerson.getName());
			}
			StorageInfo storageInfo = getBaseStorageMapper().showBaseStorageInfo(saleout.getStorageid());
			if(null!=storageInfo){
				saleout.setStoragename(storageInfo.getName());
			}
			Customer customer = getBaseCustomerMapper().getCustomerInfo(saleout.getCustomerid());
			if(null!=customer){
				saleout.setCustomername(customer.getName());
			}
			DepartMent departMent = getBaseDepartMentMapper().getDepartmentInfo(saleout.getSalesdept());
			if(null!=departMent){
				saleout.setSalesdeptname(departMent.getName());
			}
			Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(saleout.getSalesuser());
			if(null!=personnel){
				saleout.setSalesusername(personnel.getName());
			}
		}
		return pageData;
	}

	@Override
	public List<String> getBigSaleOutDetailSourceidList() throws Exception {
		List<String> list = bigSaleOutMapper.getBigSaleOutDetailSourceidList();
		return list;
	}

	@Override
	public Map addBigSaleOut(String ids, String storageid) throws Exception {
		BigSaleOut bigSaleOut = new BigSaleOut();
		if (isAutoCreate("t_storage_bigsaleout")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(bigSaleOut, "t_storage_bigsaleout");
			bigSaleOut.setId(id);
		}else{
			bigSaleOut.setId("DDFH-"+CommonUtils.getDataNumberSeconds());
		}
		bigSaleOut.setStorageid(storageid);
		SysUser sysUser = getSysUser();
		bigSaleOut.setAdduserid(sysUser.getUserid());
		bigSaleOut.setAddusername(sysUser.getUsername());
		bigSaleOut.setAdddeptid(sysUser.getDepartmentid());
		bigSaleOut.setAdddeptname(sysUser.getDepartmentname());
		bigSaleOut.setStatus("2");
		boolean flag = bigSaleOutMapper.addBigSaleOut(bigSaleOut) > 0;
		if(flag){
			if(StringUtils.isNotEmpty(ids)){
				String[] idArr = ids.split(",");
				for(String saleoutid : idArr){
					BigSaleOutDetail bigSaleOutDetail = new BigSaleOutDetail();
					bigSaleOutDetail.setBillid(bigSaleOut.getId());
					bigSaleOutDetail.setSaleoutid(saleoutid);
					bigSaleOutMapper.addBigSaleOutDetail(bigSaleOutDetail);
					
					saleoutMapper.updateIsBigSaleOut("1",saleoutid);
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("id", bigSaleOut.getId());
		return map;
	}

	@Override
	public BigSaleOut getBigSaleOutInfo(String id) throws Exception {
		return bigSaleOutMapper.getBigSaleOutInfo(id);
	}

	@Override
	public PageData getBigSaleOutGoodsList(PageMap pageMap) throws Exception {
		String isInteger = "0";
		SysParam sysParamInteger = getBaseSysParamMapper().getSysParam("isInteger");
		if(null != sysParamInteger){
			isInteger = sysParamInteger.getPvalue();
		}
		PageData pageData = null;
		if(pageMap.getCondition().containsKey("id") && null != pageMap.getCondition().get("id")){
			String id = (String)pageMap.getCondition().get("id");
			BigSaleOut bigSaleOut = bigSaleOutMapper.getBigSaleOutInfo(id);
			if(null != bigSaleOut){
				List<BigSaleOutDetail> sourceList = bigSaleOutMapper.getBigSaleOutDetailList(id);
				if(sourceList.size() != 0){
					String[] saleoutidArr = new String[sourceList.size()];
					for (int i = 0; i < sourceList.size(); i++) {
						saleoutidArr[i] = sourceList.get(i).getSaleoutid();
					}
					pageMap.getCondition().put("array", saleoutidArr);
				}
				if(sourceList.size() != 0){
					List<SaleoutDetail> goodsList = bigSaleOutMapper.getBigSaleOutGoodsList(pageMap);
					if(goodsList.size() != 0){
						for(SaleoutDetail saleoutDetail : goodsList){
							GoodsInfo goodsInfo = getGoodsInfoByID(saleoutDetail.getGoodsid());
							if(goodsInfo!=null){
								goodsInfo.setItemno(getItemnoByGoodsAndStorage(saleoutDetail.getGoodsid(),saleoutDetail.getStorageid()));
							}
							StorageInfo storageInfo = getStorageInfoByID(saleoutDetail.getStorageid());
							if(null!=storageInfo){
								saleoutDetail.setStoragename(storageInfo.getName());
							}
							StorageLocation storageLocation = getStorageLocation(saleoutDetail.getStoragelocationid());
							if(null!=storageLocation){
								saleoutDetail.setStoragelocationname(storageLocation.getName());
							}
							TaxType taxType = getTaxType(saleoutDetail.getTaxtype());
							if(null!=taxType){
								saleoutDetail.setTaxtypename(taxType.getName());
							}
							saleoutDetail.setGoodsInfo(goodsInfo);
							if(BigDecimal.ZERO.compareTo(goodsInfo.getBoxnum()) != 0){
								BigDecimal auxnum = saleoutDetail.getUnitnum().divideToIntegralValue(goodsInfo.getBoxnum());
								BigDecimal auxremainder = saleoutDetail.getUnitnum().remainder(goodsInfo.getBoxnum());
								if("0".equals(isInteger)){
									saleoutDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnum.intValue()) + saleoutDetail.getAuxunitname() + auxremainder.toString() + saleoutDetail.getUnitname()));
								}else{
									saleoutDetail.setAuxnumdetail(Integer.toString(auxnum.intValue()) + saleoutDetail.getAuxunitname());
								}
							}
						}
					}
					int count = bigSaleOutMapper.getBigSaleOutGoodsCount(pageMap);
					pageData = new PageData(count,goodsList,pageMap);
					pageMap.getCondition().put("isflag", true);
					List<SaleoutDetail> sumList = bigSaleOutMapper.getBigSaleOutGoodsList(pageMap);
					for(SaleoutDetail saleoutDetail : sumList){
						if(null != saleoutDetail){
							BigDecimal auxnumSum = (null != saleoutDetail.getAuxnum()) ? saleoutDetail.getAuxnum() : BigDecimal.ZERO;
							BigDecimal auxremainderSum = (null != saleoutDetail.getAuxremainder()) ? saleoutDetail.getAuxremainder() : BigDecimal.ZERO;
							if("0".equals(isInteger)){
								saleoutDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnumSum.intValue()) + saleoutDetail.getAuxunitname() + auxremainderSum.toString()));
							}else{
								saleoutDetail.setAuxnumdetail(Integer.toString(auxnumSum.intValue()) + saleoutDetail.getAuxunitname());
							}
							saleoutDetail.setGoodsid("合计");
							saleoutDetail.setUnitname("");
							saleoutDetail.setTaxprice(null);
							saleoutDetail.setNotaxprice(null);
						}else{
							sumList = new ArrayList<SaleoutDetail>();
						}
					}
					pageData.setFooter(sumList);
				}
			}
		}
		return pageData;
	}
   
	@Override
	public PageData getUpdateBigSaleOutSourceBillList(PageMap pageMap,String ids) throws Exception {
		String isInteger = "0";
		SysParam sysParamInteger = getBaseSysParamMapper().getSysParam("isInteger");
		if(null != sysParamInteger){
			isInteger = sysParamInteger.getPvalue();
		}
		PageData pageData = null;
		if(pageMap.getCondition().containsKey("id") && null != pageMap.getCondition().get("id")){
			String id = (String)pageMap.getCondition().get("id");
			BigSaleOut bigSaleOut = bigSaleOutMapper.getBigSaleOutInfo(id);
			if(null != bigSaleOut){
				String[] idArr = ids.split(",");
				pageMap.getCondition().put("array", idArr);
				if(idArr.length!= 0){
					List<SaleoutDetail> goodsList = bigSaleOutMapper.getBigSaleOutGoodsList(pageMap);
					if(goodsList.size() != 0){
						for(SaleoutDetail saleoutDetail : goodsList){
							GoodsInfo goodsInfo = getGoodsInfoByID(saleoutDetail.getGoodsid());
							StorageInfo storageInfo = getStorageInfoByID(saleoutDetail.getStorageid());
							if(null!=storageInfo){
								saleoutDetail.setStoragename(storageInfo.getName());
							}
							StorageLocation storageLocation = getStorageLocation(saleoutDetail.getStoragelocationid());
							if(null!=storageLocation){
								saleoutDetail.setStoragelocationname(storageLocation.getName());
							}
							TaxType taxType = getTaxType(saleoutDetail.getTaxtype());
							if(null!=taxType){
								saleoutDetail.setTaxtypename(taxType.getName());
							}
							saleoutDetail.setGoodsInfo(goodsInfo);
							if(BigDecimal.ZERO.compareTo(goodsInfo.getBoxnum()) != 0){
								BigDecimal auxnum = saleoutDetail.getUnitnum().divideToIntegralValue(goodsInfo.getBoxnum());
								BigDecimal auxremainder = saleoutDetail.getUnitnum().remainder(goodsInfo.getBoxnum());
								if("0".equals(isInteger)){
									saleoutDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnum.intValue()) + saleoutDetail.getAuxunitname() + auxremainder.toString() + saleoutDetail.getUnitname()));
								}else{
									saleoutDetail.setAuxnumdetail(Integer.toString(auxnum.intValue()) + saleoutDetail.getAuxunitname());
								}
							}
						}
					}
					int count = bigSaleOutMapper.getBigSaleOutGoodsCount(pageMap);
					pageData = new PageData(count,goodsList,pageMap);
					pageMap.getCondition().put("isflag", true);
					List<SaleoutDetail> sumList = bigSaleOutMapper.getBigSaleOutGoodsList(pageMap);
					for(SaleoutDetail saleoutDetail : sumList){
						if(null != saleoutDetail){
							BigDecimal auxnumSum = (null != saleoutDetail.getAuxnum()) ? saleoutDetail.getAuxnum() : BigDecimal.ZERO;
							BigDecimal auxremainderSum = (null != saleoutDetail.getAuxremainder()) ? saleoutDetail.getAuxremainder() : BigDecimal.ZERO;
							if("0".equals(isInteger)){
								saleoutDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(Integer.toString(auxnumSum.intValue()) + saleoutDetail.getAuxunitname() + auxremainderSum.toString()));
							}else{
								saleoutDetail.setAuxnumdetail(Integer.toString(auxnumSum.intValue()) + saleoutDetail.getAuxunitname());
							}
							saleoutDetail.setGoodsid("合计");
							saleoutDetail.setUnitname("");
							saleoutDetail.setTaxprice(null);
							saleoutDetail.setNotaxprice(null);
						}else{
							sumList = new ArrayList<SaleoutDetail>();
						}
					}
					pageData.setFooter(sumList);
				}
			}
		}
		return pageData;
	}
	
	@Override
	public List getBigSaleOutSourceBillList(String id) throws Exception {
		List<Saleout> retList = new ArrayList<Saleout>();
		List<BigSaleOutDetail> sourceList = bigSaleOutMapper.getBigSaleOutDetailList(id);
		if(sourceList.size() != 0){
			for(BigSaleOutDetail bigSaleOutDetail : sourceList){
				Saleout saleout = getSaleoutMapper().getSaleOutInfo(bigSaleOutDetail.getSaleoutid());
				if(null != saleout){
					Personnel indoorPerson = getPersonnelById(saleout.getIndooruserid());
					if(null!=indoorPerson){
						saleout.setIndoorusername(indoorPerson.getName());
					}
					StorageInfo storageInfo = getStorageInfoByID(saleout.getStorageid());
					if(null!=storageInfo){
						saleout.setStoragename(storageInfo.getName());
					}
					Map map = new HashMap();
					map.put("id", saleout.getCustomerid());
					Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
					if(null!=customer){
						saleout.setCustomername(customer.getName());
					}
					DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(saleout.getSalesdept());
					if(null!=departMent){
						saleout.setSalesdeptname(departMent.getName());
					}
					Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(saleout.getSalesuser());
					if(null!=personnel){
						saleout.setSalesusername(personnel.getName());
					}
					retList.add(saleout);
				}
			}
		}
		return retList;
	}

	@Override
	public Map auditBigSaleOuts(String ids,BigSaleOut bigSaleOut1) throws Exception {
		Map map = new HashMap();
		boolean retflag = false;
		String sucids = "",unsucids = "",msg = "";
		String[] idArr = ids.split(",");
		//根据大单发货单编码获取发货单据
		for(String id : idArr){
			BigSaleOut bigSaleOut = bigSaleOutMapper.getBigSaleOutInfo(id);
			if(null != bigSaleOut){
				//审核大单发货单
				SysUser sysUser = getSysUser();
				bigSaleOut.setAudituserid(sysUser.getUserid());
				bigSaleOut.setAuditusername(sysUser.getUsername());
				if(null != bigSaleOut1 && StringUtils.isNotEmpty(bigSaleOut1.getRemark())){
					bigSaleOut.setRemark(bigSaleOut1.getRemark());
				}
				boolean flag = bigSaleOutMapper.auditBigSaleOut(bigSaleOut) > 0;
				if(flag){
					if(StringUtils.isEmpty(sucids)){
						sucids = id;
					}else{
						sucids += "," +id;
					}
				}else{
					if(StringUtils.isEmpty(unsucids)){
						unsucids = id;
					}else{
						unsucids += "," +id;
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(sucids)){
			if(StringUtils.isEmpty(msg)){
				msg = "大单发货单 编码:" + sucids + "审核成功!";
			}else{
				msg += "<br>" + "大单发货单 编码:" + sucids + "审核成功!";
			}
		}
		if(StringUtils.isNotEmpty(unsucids)){
			if(StringUtils.isEmpty(msg)){
				msg = "大单发货单 编码:" + unsucids + "审核失败!";
			}else{
				msg += "<br>" + "大单发货单 编码:" + unsucids + "审核失败!";
			}
		}
		map.put("msg", msg);
		return map;
	}

	@Override
	public Map doSaleoutBigSaleOuts(String ids) throws Exception {
		Map map = new HashMap();
		String retmsg = "";
		boolean retflag = true;
		String[] idArr = ids.split(",");
		//根据大单发货单编码获取发货单据
		for(String id : idArr){
			//审核发货单
			List<BigSaleOutDetail> sourceList = bigSaleOutMapper.getBigSaleOutDetailList(id);
			if(sourceList.size() != 0){
				for(BigSaleOutDetail bigSaleOutDetail : sourceList){
					Map saleoutMap = storageSaleOutServiceImpl.auditSaleOut(bigSaleOutDetail.getSaleoutid());
					boolean flag = saleoutMap.get("flag").equals(true);
					retflag = retflag && flag;
					String msg = (String)saleoutMap.get("msg");
					if(StringUtils.isNotEmpty(msg)){
						String msg1 = "";
						if(msg.contains(bigSaleOutDetail.getSaleoutid())){
							msg1 = msg + ";";
						}else{
							msg1 = "发货单：" + bigSaleOutDetail.getSaleoutid() + " " + msg +";";
						}
						if(StringUtils.isEmpty(retmsg)){
							retmsg = msg1;
						}else{
							retmsg += "<br>" + msg1;
						}
					}
				}
				if(retflag){
					bigSaleOutMapper.closeBigSaleOut(id);
				}else{
					throw new Exception("大单发货编号："+ids+"确认发货失败；"+retmsg);
				}
			}
		}
		map.put("msg", retmsg);
		map.put("flag", retflag);
		return map;
	}

	@Override
	public Map cancelDoSaleoutBigSaleOuts(String ids) throws Exception {
		Map map = new HashMap();
		boolean retflag = true;
		String retmsg = "";
		String[] idArr = ids.split(",");
		for(String id : idArr){
			//作废前判断来源单据是否已关闭，若存在已关闭的单据，则不允许作废，否则允许
			boolean closeflag = bigSaleOutMapper.getCloseSaleoutByBigSaleOutId(id) > 0;
			if(!closeflag){
				//反审发货单
				List<BigSaleOutDetail> sourceList = bigSaleOutMapper.getBigSaleOutDetailList(id);
				if(sourceList.size() != 0){
					for(BigSaleOutDetail bigSaleOutDetail : sourceList){
						Saleout saleout = getSaleoutMapper().getSaleOutInfo(bigSaleOutDetail.getSaleoutid());
						if(null != saleout && "3".equals(saleout.getStatus())){
							Map saleoutMap = storageSaleOutServiceImpl.oppauditSaleOut(bigSaleOutDetail.getSaleoutid());
							boolean flag = saleoutMap.get("flag").equals(true);
							retflag = retflag && flag;
						}else{
							retflag = retflag && false;
						}
					}
					if(retflag){
						bigSaleOutMapper.updateCancelCloseBigSaleOut(id);
					}
				}
			}else {
				retflag = retflag && false;
				if(StringUtils.isEmpty(retmsg)){
					retmsg = "大单发货编号："+id+"存在已关闭的来源单据，不允许作废；";
				}else{
					retmsg += "<br>" + "大单发货编号："+id+"存在已关闭的来源单据，不允许作废；";
				}
			}
		}
		map.put("flag", retflag);
		map.put("retmsg",retmsg);
		return map;
	}

	@Override
	public Map oppauditBigSaleOut(String id) throws Exception {
		Map map = new HashMap();
		boolean retflag = true;
		BigSaleOut bigSaleOut = bigSaleOutMapper.getBigSaleOutInfo(id);
		if(null != bigSaleOut){
			//反审大单发货单
			retflag = bigSaleOutMapper.oppauditBigSaleOut(bigSaleOut) >0;
			
			//反审发货单
//			List<BigSaleOutDetail> sourceList = bigSaleOutMapper.getBigSaleOutDetailList(id);
//			if(sourceList.size() != 0){
//				for(BigSaleOutDetail bigSaleOutDetail : sourceList){
//					Map retMap = storageSaleOutServiceImpl.oppauditSaleOut(bigSaleOutDetail.getSaleoutid());
//					retflag = retflag || retMap.get("flag").equals(true);
//				}
//			}
		}
		map.put("flag", retflag);
		return map;
	}

	@Override
	public Map deleteBigSaleOuts(String ids) throws Exception {
		Map map = new HashMap();
		String sucids = "",unsucids = "";
		String[] idArr = ids.split(",");
		for(String id :idArr){
			BigSaleOut bigSaleOut = bigSaleOutMapper.getBigSaleOutInfo(id);
			if(null != bigSaleOut){
				//修改发货单是否大单发货
				List<BigSaleOutDetail> sourceList = bigSaleOutMapper.getBigSaleOutDetailList(id);
				if(sourceList.size() != 0){
					for(BigSaleOutDetail bigSaleOutDetail : sourceList){
						saleoutMapper.updateIsBigSaleOut("0",bigSaleOutDetail.getSaleoutid());
					}
				}
				//删除大单发货单
				boolean flag1 = bigSaleOutMapper.deleteBigSaleOut(id) > 0;
				//删除大单发货单明细
				boolean flag2 = bigSaleOutMapper.deleteBigSaleOutDetailByBillid(id) > 0;
				if(flag1 && flag2){
					if(StringUtils.isNotEmpty(sucids)){
						sucids += "," + id;
					}else{
						sucids = id;
					}
				}else{
					if(StringUtils.isNotEmpty(unsucids)){
						unsucids += "," + id;
					}else{
						unsucids = id;
					}
				}
			}
		}
		String msg = "";
		if(StringUtils.isNotEmpty(sucids)){
			if(StringUtils.isEmpty(msg)){
				msg = "大单发货单 编号:" + sucids +" 删除成功!";
			}else{
				msg += "<br>" + "大单发货单 编号:" + sucids +" 删除成功!";
			}
		}
		if(StringUtils.isNotEmpty(unsucids)){
			if(StringUtils.isEmpty(msg)){
				msg = "大单发货单 编号:" + unsucids +" 删除失败!";
			}else{
				msg += "<br>" + "大单发货单 编号:" + unsucids +" 删除失败!";
			}
		}
		map.put("msg", msg);
		return map;
	}

	@Override
	public Boolean saveBigSaleOut(String billid, String editsaleoutid,String remark,String isdel) throws Exception {
		BigSaleOut bigSaleOut = bigSaleOutMapper.getBigSaleOutInfo(billid);
		if(null != bigSaleOut){
			bigSaleOut.setRemark(remark);
			bigSaleOutMapper.editBigSaleOut(bigSaleOut);
		}
		if(StringUtils.isNotEmpty(editsaleoutid)){
			List<BigSaleOutDetail> sourceList = bigSaleOutMapper.getBigSaleOutDetailList(billid);
			if(sourceList.size() != 0){
				for(BigSaleOutDetail bigSaleOutDetail : sourceList){
					saleoutMapper.updateIsBigSaleOut("0",bigSaleOutDetail.getSaleoutid());
				}
			}
			
			//删除指定大单发货单的所有发货单据
			bigSaleOutMapper.deleteBigSaleOutDetailByBillid(billid);
			String[] editsaleoutidArr = editsaleoutid.split(",");
			boolean flag = true;
			for(String saleoutid : editsaleoutidArr){
				BigSaleOutDetail bigSaleOutDetail = new BigSaleOutDetail();
				bigSaleOutDetail.setBillid(billid);
				bigSaleOutDetail.setSaleoutid(saleoutid);
				boolean retflag = bigSaleOutMapper.addBigSaleOutDetail(bigSaleOutDetail) > 0;
				flag = flag && retflag;
				if(retflag){
					saleoutMapper.updateIsBigSaleOut("1",saleoutid);
				}
			}
			return flag;
		}else{
			if("1".equals(isdel)){
				List<BigSaleOutDetail> sourceList = bigSaleOutMapper.getBigSaleOutDetailList(billid);
				if(sourceList.size() != 0){
					for(BigSaleOutDetail bigSaleOutDetail : sourceList){
						saleoutMapper.updateIsBigSaleOut("0",bigSaleOutDetail.getSaleoutid());
					}
				}
				
				//删除指定大单发货单的所有发货单据
				bigSaleOutMapper.deleteBigSaleOutDetailByBillid(billid);
			}
			return true;
		}
	}

	@Override
	public PageData getBigSaleOutCustomerGoodsNumList(PageMap pageMap)
			throws Exception {
		String isInteger = "0";
		SysParam sysParamInteger = getBaseSysParamMapper().getSysParam("isInteger");
		if(null != sysParamInteger){
			isInteger = sysParamInteger.getPvalue();
		}
		PageData pageData = null;
		if(pageMap.getCondition().containsKey("bigsaleoutid") && null != pageMap.getCondition().get("bigsaleoutid")){
			String id = (String)pageMap.getCondition().get("bigsaleoutid");
			List<BigSaleOutDetail> sourceList = bigSaleOutMapper.getBigSaleOutDetailList(id);
			if(sourceList.size() != 0){
				String[] saleoutidArr = new String[sourceList.size()];
				for (int i = 0; i < sourceList.size(); i++) {
					saleoutidArr[i] = sourceList.get(i).getSaleoutid();
				}
				pageMap.getCondition().put("array", saleoutidArr);
				List<SaleoutDetail> list = bigSaleOutMapper.getBigSaleOutCustomerGoodsNumList(pageMap);
				for(SaleoutDetail saleoutDetail : list){
					Customer customer = getCustomerByID(saleoutDetail.getCustomerid());
					if(null != customer){
						saleoutDetail.setCustomername(customer.getName());
					}
					if("0".equals(isInteger)){
						saleoutDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(saleoutDetail.getAuxnum().setScale(0,BigDecimal.ROUND_DOWN).toString() + saleoutDetail.getAuxunitname() + saleoutDetail.getAuxremainder().toString() + saleoutDetail.getUnitname()));
					}else{
						saleoutDetail.setAuxnumdetail(saleoutDetail.getAuxnum().setScale(0,BigDecimal.ROUND_DOWN).toString() + saleoutDetail.getAuxunitname());
					}
				}
				pageData = new PageData(bigSaleOutMapper.getBigSaleOutCustomerGoodsNumCount(pageMap),list,pageMap);
				//合计
				pageMap.getCondition().put("isflag", true);
				List<SaleoutDetail> footer = bigSaleOutMapper.getBigSaleOutCustomerGoodsNumList(pageMap);
				for(SaleoutDetail saleoutDetail : footer){
					if(null != saleoutDetail){
						saleoutDetail.setCustomername("合计");
						saleoutDetail.setUnitname("");
						if("0".equals(isInteger)){
							saleoutDetail.setAuxnumdetail(CommonUtils.strDigitNumDeal(saleoutDetail.getAuxnum().setScale(0,BigDecimal.ROUND_DOWN).toString() + saleoutDetail.getAuxunitname() + saleoutDetail.getAuxremainder().toString() + saleoutDetail.getUnitname()));
						}else{
							saleoutDetail.setAuxnumdetail(saleoutDetail.getAuxnum().setScale(0,BigDecimal.ROUND_DOWN).toString() + saleoutDetail.getAuxunitname());
						}
					}else{
						footer = new ArrayList<SaleoutDetail>();
					}
				}
				pageData.setFooter(footer);
			}
		}
		return pageData;
	}
}


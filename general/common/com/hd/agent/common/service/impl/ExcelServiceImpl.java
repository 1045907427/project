/**
 * @(#)ExcelServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.common.service.impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.storage.model.ExportLend;
import com.hd.agent.storage.model.ExportStorageOtherEnterAndOut;
import com.hd.agent.storage.model.Lend;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.common.dao.ExcelMapper;
import com.hd.agent.common.service.IExcelService;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.ExportSalesOrder;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.storage.model.ExportLend;

/**
 * 
 * 
 * @author zhengziyong
 */
public class ExcelServiceImpl extends BaseServiceImpl implements IExcelService {

	private ExcelMapper excelMapper;
	
	@Override
	public List getList(PageMap pageMap) throws Exception {
		String tn = "";
		if(pageMap.getCondition().containsKey("tn")){
			tn = pageMap.getCondition().get("tn").toString();
		}
        String cols = getAccessColumnList(tn, null);
        if(StringUtils.isEmpty(cols)){
            cols = "";
            List<TableColumn> list = getTableColumn(tn);
            for(TableColumn tableColumn : list){
                cols += tableColumn.getColumnname() + ",";
            }
            if(cols.endsWith(",")){
                cols = cols.substring(0, cols.length() - 1);
            }
        }
		pageMap.setCols(cols);
		String dataSql = getDataAccessRule(tn, null);
		pageMap.setDataSql(dataSql);
		return excelMapper.getList(pageMap);
	}

    @Override
    public Map getAutoList(PageMap pageMap) throws Exception {
        String tn = "";
        if(pageMap.getCondition().containsKey("tn")){
            tn = pageMap.getCondition().get("tn").toString();
        }
        String cols = getAccessColumnList(tn, null);
        if(StringUtils.isEmpty(cols)){
            cols = "";
            List<TableColumn> list = getTableColumn(tn);
            for(TableColumn tableColumn : list){
                cols += tableColumn.getColumnname() + ",";
            }
            if(cols.endsWith(",")){
                cols = cols.substring(0, cols.length() - 1);
            }
        }
        String otherStr = "";
        String exportCols = "";
        if(pageMap.getCondition().containsKey("commonCol")){
            String[] colsStr = cols.split(",");
            String[] commconStr = pageMap.getCondition().get("commonCol").toString().split(",");
            for(int i=0;i<commconStr.length;i++){
                boolean flag = false ;
                for(int j=0;j<colsStr.length;j++){
                    if(commconStr[i].equals(colsStr[j])){
                        exportCols += commconStr[i] + ",";
                        flag = true;
                    }
                    if(flag){
                        break;
                    }
                }
                if(!flag){
                    otherStr += commconStr[i] + ",";
                }
            }
        }
        if(exportCols.endsWith(",")){
            exportCols = exportCols.substring(0, exportCols.length() - 1);
            pageMap.setCols(exportCols);
        }else {
            pageMap.setCols(cols);
        }

        String dataSql = getDataAccessRule(tn, null);
        pageMap.setDataSql(dataSql);
        List list = excelMapper.getList(pageMap);

        Map map = new HashMap();
        map.put("list",list);
        if(StringUtils.isNotEmpty(otherStr)){
            map.put("disColumn",otherStr);
        }
        return map;
    }

    @Override
	public List<ExportSalesOrder> getBillList(PageMap pageMap) throws Exception {
		String dataSql = getAccessColumnList("t_sales_order", "a");
		pageMap.setDataSql(dataSql);
		pageMap.setQueryAlias("a");
		List<ExportSalesOrder> list = excelMapper.getSaleOrderList(pageMap);
		if(list.size() != 0){
			for(ExportSalesOrder eso : list){
				Customer customer = getBaseCustomerMapper().getCustomerInfo(eso.getCustomerid());
				if(null != customer){
					eso.setCustomername(customer.getName());
				}
				DepartMent departMent = getDepartmentByDeptid(eso.getSalesdept());
				if(null != departMent){
					eso.setSalesdeptname(departMent.getName());
				}
				GoodsInfo goodsInfo = getBaseGoodsMapper().getGoodsInfo(eso.getGoodsid());
				if(null != goodsInfo){
                    //获取商品箱装量
                    List<GoodsInfo_MteringUnitInfo> muInfo = getBaseGoodsMapper().getMUListByGoodsId(eso.getGoodsid()); //获取商品的辅助计量单位列表
                    if(muInfo.size() > 0 ){
                        eso.setBoxnum(muInfo.get(0).getRate());
                    }else if(null != goodsInfo.getBoxnum()){
                        eso.setBoxnum(goodsInfo.getBoxnum());
                    }

					eso.setGoodsname(goodsInfo.getName());
                    eso.setModel(goodsInfo.getModel());
                    BigDecimal unitnum = eso.getUnitnum();
                    if(null != goodsInfo.getSinglevolume()){
                        BigDecimal volume = unitnum.multiply(goodsInfo.getSinglevolume()).setScale(6,BigDecimal.ROUND_HALF_UP);
                        eso.setVolume(volume.toString());
                    }else{
                        eso.setVolume("0.000000");
                    }
                    if(null != goodsInfo.getGrossweight()){
                        BigDecimal grossweight = unitnum.multiply(goodsInfo.getGrossweight()).setScale(6, BigDecimal.ROUND_HALF_UP);
                        eso.setGrossweight(grossweight.toString());
                    }else{
                        eso.setGrossweight("0.000000");
                    }
				}
				Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(eso.getSalesuser());
				if(null != personnel){
					eso.setSalesusername(personnel.getName());
				}
			}
		}
		return list;	
	}

	@Override
	public List getGoodsList(PageMap pageMap) throws Exception {
		// 数据权限
		String sql = getDataAccessRule("t_base_goods_info", "a");
		pageMap.setDataSql(sql);
		Map map = getAccessColumn("t_base_goods_info");
		Map conditionMap=pageMap.getCondition();
		List<GoodsInfo> list = excelMapper.getGoodsList(pageMap);
		if(list.size() != 0){
			for(GoodsInfo goodsInfo : list){
				//主单位名称
				MeteringUnit mu = getBaseGoodsMapper().showMeteringUnitInfo(goodsInfo.getMainunit());
				if(null != mu){
					goodsInfo.setMainunitName(mu.getName());
				}
				//商品类型名称
				SysCode goodstype = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getGoodstype(), "goodstype");
				if(null != goodstype){
					goodsInfo.setGoodstypeName(goodstype.getCodename());
				}
				//状态名称
				SysCode state = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getState(), "state");
				if(null != state){
					goodsInfo.setStateName(state.getCodename());
				}
				//默认库位名称库位容量
				GoodsStorageLocation goodsStorageLocation = getBaseGoodsMapper().getSLByGoodsidAndIsdefault(goodsInfo.getId());
				if(null != goodsStorageLocation){
					goodsInfo.setSlboxnum(goodsStorageLocation.getBoxnum());
				}
				//是否保质期管理名称
				SysCode isshelflife = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getIsshelflife(), "yesorno");
				if(null != isshelflife){
					goodsInfo.setIsshelflifename(isshelflife.getCodename());
				}
				//是否批次管理
				SysCode isbatch = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getIsbatch(), "yesorno");
				if(null != isbatch){
					goodsInfo.setIsbatchname(isbatch.getCodename());
				}
				SysCode isstoragelocation = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getIsstoragelocation(), "yesorno");
				if(null != isstoragelocation){
					goodsInfo.setIsstoragelocationname(isstoragelocation.getCodename());
				}
				//保质期单位名称
				if(null != goodsInfo.getShelflifeunit()){
					if("1".equals(goodsInfo.getShelflifeunit())){
						goodsInfo.setShelflifeunitName("天");
					}
					else if("2".equals(goodsInfo.getShelflifeunit())){
						goodsInfo.setShelflifeunitName("周");
					}
					else if("3".equals(goodsInfo.getShelflifeunit())){
						goodsInfo.setShelflifeunitName("月");
					}
					else if("4".equals(goodsInfo.getShelflifeunit())){
						goodsInfo.setShelflifeunitName("年");
					}
				}
				//保质期描述
				if(null != goodsInfo.getShelflife()){
					BigDecimal shelflife = goodsInfo.getShelflife().setScale(2, BigDecimal.ROUND_HALF_UP);
					goodsInfo.setShelflifedetail(shelflife.toString() + goodsInfo.getShelflifeunitName());
				}
				//购销类型名称
				SysCode bstype = getBaseSysCodeMapper().getSysCodeInfo(goodsInfo.getBstype(), "bstype");
				if(null != bstype){
					goodsInfo.setBstypeName(bstype.getCodename());
				}
				//辅单位名称meteringunitid
				MeteringUnit meteringunit = getBaseGoodsMapper().showMeteringUnitInfo(goodsInfo.getAuxunitid());
				if(null != meteringunit){
					goodsInfo.setAuxunitname(meteringunit.getName());
				}
				if(null != map && !map.isEmpty()){
					if(!map.containsKey("costaccountprice")){
						goodsInfo.setCostaccountprice(null);
					}
					if(!map.containsKey("highestbuyprice")){
						goodsInfo.setHighestbuyprice(null);
					}
				}
				//第二供应商
				if(StringUtils.isNotEmpty(goodsInfo.getSecondsupplier())){
					String secondsuppliername = "";
					String[] secondsupplieridArr = goodsInfo.getSecondsupplier().split(",");
					for(String secondsupplierid : secondsupplieridArr){
						BuySupplier buySupplier = getBaseBuySupplierMapper().getBuySupplier(secondsupplierid);
						if(null != buySupplier){
							if(StringUtils.isEmpty(secondsuppliername)){
								secondsuppliername = buySupplier.getName();
							}else{
								secondsuppliername += "," + buySupplier.getName();
							}
						}
					}
					goodsInfo.setSecondsuppliername(secondsuppliername);
				}
				//所属部门
				DepartMent departMent = getDepartmentByDeptid(goodsInfo.getDeptid());
				if(null != departMent){
					goodsInfo.setDeptname(departMent.getName());
				}
				//采购箱价=箱装量*最高采购价
				BigDecimal rate = null != goodsInfo.getBoxnum() ? goodsInfo.getBoxnum() : BigDecimal.ZERO;
				BigDecimal highestbuyprice = null != goodsInfo.getHighestbuyprice() ? goodsInfo.getHighestbuyprice() : BigDecimal.ZERO;
				BigDecimal buyboxprice = highestbuyprice.multiply(rate);
				goodsInfo.setBuyboxprice(buyboxprice);
                //未分摊金额
                BigDecimal costDiffAmount = getBaseGoodsMapper().getCostDiffAmountByGoodsid(null,goodsInfo.getId());
                if(null==costDiffAmount){
                    costDiffAmount = BigDecimal.ZERO;
                }
                goodsInfo.setField12(costDiffAmount.toString());

				if (StringUtils.isNotEmpty(goodsInfo.getDefaulttaxtype())) {// 默认税种
					TaxType taxType = getBaseFinanceMapper().getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
					if (null != taxType) {
						goodsInfo.setDefaulttaxtypeName(taxType.getName());
						goodsInfo.setTaxrate(taxType.getRate());
						if(conditionMap.containsKey("isTaxrateXS")){
							Boolean isTaxrateXS=(Boolean)conditionMap.get("isTaxrateXS");
							if(isTaxrateXS==true){
								goodsInfo.setTaxrate(taxType.getRate().divide(new BigDecimal(100),6,BigDecimal.ROUND_HALF_UP));
							}
						}
					}
				}
			}
		}
		return list;
	}

	@Override
	public List getSupplierList(PageMap pageMap) throws Exception {
		String sql = getDataAccessRule("t_base_buy_supplier","a");
		pageMap.setDataSql(sql);
		List<BuySupplier> list = excelMapper.getSupplierList(pageMap);
		if(list.size() != 0){
			for(BuySupplier esbs : list){
				//状态
				if(null != esbs.getState()){
					SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(esbs.getState(),"state");
					if(null != sysCode){
						esbs.setStatename(sysCode.getCodename());
					}
				}
				//核销方式
				SysCode canceltypeSysCode = getBaseSysCodeMapper().getSysCodeInfo(esbs.getCanceltype(), "canceltype");
				if(null != canceltypeSysCode){
					esbs.setCanceltypename(canceltypeSysCode.getCodename());
				}
				//收回方式
				if(null != esbs.getRecoverymode()){
					SysCode recoverymodeSysCode = getBaseSysCodeMapper().getSysCodeInfo(esbs.getRecoverymode(),"recoverymode");
					if(null != recoverymodeSysCode){
						esbs.setRecoverymodename(recoverymodeSysCode.getCodename());
					}
				}
				//订单追加
				if(StringUtils.isNotEmpty(esbs.getOrderappend())){
					SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(esbs.getOrderappend(), "yesorno");
					if(null != sysCode){
						esbs.setOrderappendname(sysCode.getCodename());
					}
				}
				//所属区域
				BuyArea buyArea = getBaseBuyAreaMapper().getBuyAreaDetail(esbs.getBuyarea());
				if(null != buyArea){
					esbs.setBuyareaname(buyArea.getThisname());
				}
				//所属分类
				BuySupplierSort buySupplierSort = getBaseBuySupplierSortMapper().getBuySupplierSortDetail(esbs.getSuppliersort());
				if(null != buySupplierSort){
					esbs.setSuppliersortname(buySupplierSort.getThisname());
				}
			}
		}
		return list;
	}

	@Override
	public List getCustomerList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_base_sales_customer", "a");
		pageMap.setDataSql(dataSql);
		List<Customer> list = excelMapper.getCustomerList(pageMap);
		if(list.size() != 0){
			for(Customer esc : list){
				//公司属性
				SysCode natureSysCode = getBaseSysCodeMapper().getSysCodeInfo(esc.getNature(), "firm_nature");
				if(null != natureSysCode){
					esc.setNaturename(natureSysCode.getCodename());
				}
				//是否连锁
				if("0".equals(esc.getIschain())){
					esc.setIschainname("否");
				}else if("1".equals(esc.getIschain())){
					esc.setIschainname("是");
				}
				//是否现款
				if("0".equals(esc.getIscash())){
					esc.setIscashname("否");
				}else if("1".equals(esc.getIscash())){
					esc.setIscashname("是");
				}
				//是否总店
				SysCode islastSysCode = getBaseSysCodeMapper().getSysCodeInfo(esc.getIslast(), "islast");
				if(null != islastSysCode){
					esc.setIslastname(islastSysCode.getCodename());
				}
				//是否账期
				if("0".equals(esc.getIslongterm())){
					esc.setIslongtermname("否");
				}else if("1".equals(esc.getIslongterm())){
					esc.setIslongtermname("是");
				}
				//票种
				if("1".equals(esc.getTickettype())){
					esc.setTickettypename("增值税发票");
				}else if("2".equals(esc.getTickettype())){
					esc.setTickettypename("普通发票");
				}
				//核销方式
				SysCode canceltypeSysCode = getBaseSysCodeMapper().getSysCodeInfo(esc.getCanceltype(), "canceltype");
				if(null != canceltypeSysCode){
					esc.setCanceltypename(canceltypeSysCode.getCodename());
				}
				//价格套
				SysCode pricesortSysCode = getBaseSysCodeMapper().getSysCodeInfo(esc.getPricesort(), "price_list");
				if(null != pricesortSysCode){
					esc.setPricesortname(pricesortSysCode.getCodename());
				}
				//促销分类
				SysCode promotionsortSysCode = getBaseSysCodeMapper().getSysCodeInfo(esc.getPromotionsort(), "promotionsort");
				if(null != promotionsortSysCode){
					esc.setPromotionsortname(promotionsortSysCode.getCodename());
				}
				//ABCD等级
				if("A".equals(esc.getAbclevel())){
					esc.setAbclevelname("A");
				}else if("B".equals(esc.getAbclevel())){
					esc.setAbclevelname("B");
				}else if("C".equals(esc.getAbclevel())){
					esc.setAbclevelname("C");
				}else if("D".equals(esc.getAbclevel())){
					esc.setAbclevelname("D");
				}
				//信用等级
				SysCode creditratingSysCode = getBaseSysCodeMapper().getSysCodeInfo(esc.getCreditrating(), "creditrating");
				if(null != creditratingSysCode){
					esc.setCreditratingname(creditratingSysCode.getCodename());
				}
				//默认业务员
				if(null != esc.getSalesuserid()){
					Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(esc.getSalesuserid());
					if(null!=personnel){
						esc.setSalesusername(personnel.getName());
					}
				}
				//默认理货员
				if(null != esc.getTallyuserid()){
					Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(esc.getTallyuserid());
					if(null!=personnel){
						esc.setTallyusername(personnel.getName());
					}
				}
				//默认内勤
				if(null != esc.getIndoorstaff()){
					Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(esc.getIndoorstaff());
					if(null!=personnel){
						esc.setIndoorstaffname(personnel.getName());
					}
				}
				//收款人
				if(StringUtils.isNotEmpty(esc.getPayeeid())){
					Personnel personnel = getBasePersonnelMapper().getPersonnelInfo(esc.getPayeeid());
					if(null != personnel){
						esc.setPayeename(personnel.getName());
					}
				}
				//超账期控制
				if(null != esc.getOvercontrol()){
					if("1".equals(esc.getOvercontrol())){
						esc.setOvercontrolname("是");
					}
					else if("0".equals(esc.getOvercontrol())){
						esc.setOvercontrolname("否");
					}
				}
				//状态
				if(null != esc.getState()){
					SysCode sysCodeState = getBaseSysCodeMapper().getSysCodeInfo(esc.getState(), "state");
					if(null != sysCodeState){
						esc.setStatename(sysCodeState.getCodename());
					}
				}
			}
		}
		return list;
	}

	@Override
	public List getAnotherList(PageMap pageMap) throws Exception {
		String tn = "";
		if(pageMap.getCondition().containsKey("tn")){
			tn = pageMap.getCondition().get("tn").toString();
		}
		String dataSql = getDataAccessRule(tn, null);
		pageMap.setDataSql(dataSql);
		List list = excelMapper.getAnotherList(pageMap);
		return list;
	}



	@Override
	public Map<String, Integer> insert(Object clazz, List list, Method method) throws Exception {
		int successNum = 0,failureNum = 0,repeatNum = 0,closeNum = 0;
		Map<String, Integer> result = new HashMap<String, Integer>();
		for(Object object : list){
			try{
				Map map = (Map)method.invoke(clazz, object);
				if(map.get("flag").equals(true)){
					successNum++;
				}
				else{
					failureNum++;
				}
			}
			catch(RuntimeException e){
				System.out.print(e.getStackTrace());
				failureNum++;
			}
		}
		result.put("success", successNum);
		result.put("failure", failureNum);
		return result;
	}

	@Override
	public Map<String, Object> insertSalesOrder(Object clazz, List list,
			Method method) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map map = (Map)method.invoke(clazz,list);
		result.putAll(map);
		return result;
	}

	@Override
	public Map<String,Object> comInsertMethod(Object object, List list,
			Method method)throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		Map map = (Map)method.invoke(object,list);
		result.putAll(map);
		return result;
	}

	@Override
	public Map insertMain(Object clazz, Object object, Method method) throws Exception {
		int successNum = 0,failureNum = 0,repeatNum = 0,closeNum = 0,errorNum = 0;
		Map result = new HashMap<String, Integer>();
		try{
			Map map = (Map)method.invoke(clazz, object);
			result.putAll(map);
		}
		catch(Exception e){
			e.printStackTrace();
			failureNum++;
		}
		return result;
	}

    @Override
    public List<ExportStorageOtherEnterAndOut> getStorageOtherEnterAndOutList(PageMap pageMap ,String type) throws Exception {
        Map map = pageMap.getCondition();
        if("enter".equals(type)){
            String dataSql = getAccessColumnList("t_storage_other_enter", "a");
            pageMap.setDataSql(dataSql);
            map.put("type","1");
        }else if("out".equals(type)){
            String dataSql = getAccessColumnList("t_storage_other_out", "a");
            pageMap.setDataSql(dataSql);
            map.put("type","0");
        }
        pageMap.setCondition(map);
        pageMap.setQueryAlias("a");
        List<ExportStorageOtherEnterAndOut> list = excelMapper.getStorageOtherEnterAndOutList(pageMap);
        return list;
    }

	@Override
	public List<ExportLend> getLendList(PageMap pageMap)throws Exception {
		Map map = pageMap.getCondition();
		String dataSql = getAccessColumnList("t_storage_lend", "a");
		pageMap.setDataSql(dataSql);
		pageMap.setCondition(map);
		pageMap.setQueryAlias("a");
		List<ExportLend> list = excelMapper.getLendList(pageMap);
		return list;
	}



    @Override
	public Map<String, Object> insertCaseBySameMethod(Object object, Map map,
			Method method) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Map map2 = (Map)method.invoke(object,map);
		result.putAll(map2);
		return result;
	}

	public ExcelMapper getExcelMapper() {
		return excelMapper;
	}

	public void setExcelMapper(ExcelMapper excelMapper) {
		this.excelMapper = excelMapper;
	}


	
}


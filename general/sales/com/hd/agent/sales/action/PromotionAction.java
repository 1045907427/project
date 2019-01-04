/**
 * @(#)PromotionAction.java
 *
 * @author lin_xx
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年1月7日 lin_xx 创建版本
 */
package com.hd.agent.sales.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.sales.model.PromotionPackage;
import com.hd.agent.sales.model.PromotionPackageGroup;
import com.hd.agent.sales.model.PromotionPackageGroupDetail;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 买赠捆绑促销action
 * @author lin_xx 
 */
public class PromotionAction extends BaseSalesAction {

	private PromotionPackage promotionPackage;

	public PromotionPackage getPromotionPackage() {
		return promotionPackage;
	}

	public void setPromotionPackage(PromotionPackage promotionPackage) {
		this.promotionPackage = promotionPackage;
	}

	public String getPromotionList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesPromotionService.getPromotionData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
    //满赠菜单
    public String fullBuyFreeListPage() throws Exception{

        return SUCCESS ;
    }

    //买赠菜单
    public String buyFreeListPage() throws Exception{

        return SUCCESS;
    }

    //捆绑菜单
    public String bundleListPage() throws Exception{

        return SUCCESS;
    }

    /**
     * 跳转到page页
     * @return
     * @throws Exception
     */
	public String promotionPage() throws Exception{

        String id = request.getParameter("id");
        if(null != id){
            PromotionPackage promotionPackage = salesPromotionService.getPromotionAndGroupById(id);
            if(null != promotionPackage){
                request.setAttribute("status",promotionPackage.getStatus());
            }
        }
        request.setAttribute("id",request.getParameter("id"));
        request.setAttribute("act",request.getParameter("act"));
        request.setAttribute("type",request.getParameter("type"));

		return SUCCESS;
	}


    /**
     * 新增按钮，进入增加页面
     * @return
     * @throws Exception
     */
	public String promotionAddPage() throws Exception{

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));

        calendar.add(Calendar.DATE,1);
        request.setAttribute("dateAfter", dateFormat.format(calendar.getTime()));

		request.setAttribute("autoCreate", isAutoCreate("t_sales_promotion_package"));
		request.setAttribute("user", getSysUser());
		String act = request.getParameter("act");
		request.setAttribute("act", act);
        String detailId= getAutoCreateSysNumbderForeign(new PromotionPackageGroupDetail(), "t_sales_promotion_package_group_detail");
        request.setAttribute("detailId",detailId);
		return SUCCESS;
	}

    /**
     * 查看按钮，进入查看界面
     * @return
     * @throws Exception
     */
	public String promotionViewPage() throws Exception{
		String id = request.getParameter("id");
		PromotionPackage promotionPackage = salesPromotionService.getPromotionAndGroupById(id);

		String jsonStr = JSONUtils.listToJsonStr(promotionPackage.getPromotionGroup());
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("promotionPackage", promotionPackage);
		request.setAttribute("businessDate", promotionPackage.getBusinessdate());

        request.setAttribute("act", request.getParameter("act"));

        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
		return SUCCESS;
	}

    /**
     * 商品详细信息查看
     * @return
     * @throws Exception
     */
	public String groupDetailViewPage() throws Exception{
		String groupid = request.getParameter("groupid");
        request.setAttribute("acttype", request.getParameter("acttype"));

		PromotionPackageGroupDetail detail = salesPromotionService.getGroupDetailByid(groupid);
        String auxnumdetail = detail.getAuxnumdetail();
        if(auxnumdetail.equals("0")){
            detail.setAuxnumdetail("不限数量");
        }
		request.setAttribute("groupDetail", detail);
		//查询购买商品详情
		String bgoodsid = String.valueOf(detail.getGoodsid());
		BigDecimal numberin =getGoodsInfoByID(bgoodsid).getBoxnum();//箱装量	
		if(numberin == null ){
			numberin =  new BigDecimal(30);//如果箱装量为空，默认箱装量为30
		}
		BigDecimal price = detail.getPrice();
		BigDecimal boxprice = numberin.multiply(price);//箱价
		request.setAttribute("boxprice", boxprice);
		return SUCCESS;
	}

    /**
     * 商品详情添加
     * @return
     * @throws Exception
     */
	public String groupDetailAddPage() throws Exception{	
		String type = request.getParameter("type");
		if("1".equals(type)){
            request.setAttribute("acttype",type);
            request.setAttribute("groupname",request.getParameter("groupname"));
            request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
			return SUCCESS;
		}else if("2".equals(type)){
            request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
			return "bundleSuccess";
		}else if("3".equals(type)){
            request.setAttribute("acttype",type);
            request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
            return SUCCESS;
        }
        else{
            request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
			return SUCCESS;
		}
	}

    /**
     * 辅数量变化
     * @return
     * @throws Exception
     */
	public String auxUnitNumChange() throws Exception {
		String id = request.getParameter("id");
		String promotionNum = request.getParameter("promotionNum");
        String auxremainder = request.getParameter("auxremainder");
        String auxnum = request.getParameter("auxnum");
        String type = request.getParameter("type");
		Map num = salesPromotionService.changeAux(id,promotionNum,auxremainder,auxnum,type);
		addJSONObject(num);
		return SUCCESS;
	}
    /**
     * 赠送商品明细查询
     * @return
     * @throws Exception
     */
	public String getGiveGoodsInfo() throws Exception {
		String goodsid = request.getParameter("id");
		if(goodsid.isEmpty()){
			Map map = new HashMap();
			map.put("flag", true);
			addJSONObject(map);;
		}else{
            if(goodsid.contains(":")){
                String[] depart = goodsid.split(":");
                goodsid = depart[0];
            }
			Map givegoodMap = salesPromotionService.getClickGoodsInfo(goodsid);
			addJSONObject(givegoodMap);		
		}
        return SUCCESS;
	}

    /**
     * 商品明细修改
     * @return
     * @throws Exception
     */
	public String groupDetailEdit() throws Exception{
        request.setAttribute("acttype",request.getParameter("acttype"));
        request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}
    /**
     * 商品明细查看
     * @return
     * @throws Exception
     */
	public String viewGiveDetail() throws Exception{
		String billid = request.getParameter("id");
		String groupid = request.getParameter("groupid");
		List<PromotionPackageGroupDetail> groupdetai = salesPromotionService.viewGiveDetail(billid);
        List<PromotionPackageGroupDetail> validetail = new ArrayList<PromotionPackageGroupDetail>();
		for (int i = 0; i < groupdetai.size(); i++) {//验证同一产品组的赠送商品是否重复
			PromotionPackageGroupDetail d1 = groupdetai.get(i);
			if(d1.getGroupid().equals(groupid)){
				validetail.add(d1);
			}
		}
		addJSONArray(validetail);
		return SUCCESS;
	}
	/**
	 * 保存添加的促销活动
	 * @return
	 * @throws Exception
	 * @author lin_xx 
	 * @date 2015年1月19日
	 */
    @UserOperateLog(key="promotionPackage",type=2)
	public String addPromotionGroup() throws Exception{
		String groupInfo = request.getParameter("groupAndGoodsjson");
		List groupList =JSONUtils.jsonStrToList(groupInfo.trim(),new PromotionPackageGroup());
		//完善促销活动单的信息
		SysUser sysUser = getSysUser();
		promotionPackage.setBusinessdate(CommonUtils.getTodayDataStr());
		promotionPackage.setPrinttimes(0);
        promotionPackage.setAdduserid(sysUser.getAdduserid());
        promotionPackage.setAddusername(sysUser.getName());
        promotionPackage.setAddtime(new Date());
        promotionPackage.setAdddeptid(sysUser.getDepartmentid());
        promotionPackage.setAdddeptname(sysUser.getDepartmentname());
		String statusString  = request.getParameter("addType");
		if("saveaudit".equals(statusString)){
			promotionPackage.setStatus("3");
            promotionPackage.setAudittime(new Date());
            promotionPackage.setAudituserid(sysUser.getAdduserid());
            promotionPackage.setAuditusername(sysUser.getName());
		}else{
            promotionPackage.setStatus("2");
        }
        Map map ;
        if("2".equals(promotionPackage.getPtype())){
            map = salesPromotionService.addBundleInfo(promotionPackage, groupList);
        }else{
            map = salesPromotionService.addBuyFreeInfo(promotionPackage, groupList);
        }
        String id =  (String)map.get("id");
        boolean flag = (Boolean) map.get("flag");
        if(flag && promotionPackage.getStatus().equals("2") ){
            addLog("促销活动单编号："+ id +"新增 ", flag);
        }else{
            addLog("促销活动单编号："+ id +"保存并审核", flag);
        }
        addJSONObject(map);
		return SUCCESS;
	}
    /**
     * 保存修改的促销活动
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年1月19日
     */
    @UserOperateLog(key="promotionPackage",type=3)
    public String editPromotionGroup() throws Exception{
        String groupInfo = request.getParameter("groupAndGoodsjson");
        List groupList =JSONUtils.jsonStrToList(groupInfo.trim(),new PromotionPackageGroup());

        //完善促销活动单的信息
        SysUser sysUser = getSysUser();
        promotionPackage.setModifyuserid(sysUser.getUserid());
        promotionPackage.setModifyusername(sysUser.getName());
        promotionPackage.setModifytime(new Date());
        promotionPackage.setPrinttimes(0);
        String statusString  = request.getParameter("addType");
        if("saveaudit".equals(statusString)){//保存并审核
            PromotionPackage p = salesPromotionService.getPromotionAndGroupById(promotionPackage.getId());
            promotionPackage.setBusinessdate(CommonUtils.getTodayDataStr());
            promotionPackage.setAddtime(p.getAddtime());
            promotionPackage.setStatus("3");
            promotionPackage.setAuditusername(sysUser.getName());
            promotionPackage.setAudituserid(sysUser.getUserid());
            promotionPackage.setAudittime(new Date());
        }else{
            promotionPackage.setStatus("2");
        }
        Map map;
        if("2".equals(promotionPackage.getPtype())){
            map = salesPromotionService.addBundleInfo(promotionPackage, groupList);
        }else{
            map = salesPromotionService.addBuyFreeInfo(promotionPackage, groupList);
        }
        String id =  (String)map.get("id");
        boolean flag = (Boolean) map.get("flag");
        if(flag && promotionPackage.getStatus().equals("2") ){
            addLog("促销活动单编号："+ id +"保存", flag);
            map.put("audit",false);
        }else{
            addLog("促销活动单编号："+ id +"保存并审核", flag);
            map.put("audit",true);
        }
        addJSONObject(map);
        return SUCCESS;
    }
    /**
     * 审核促销单
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="promotionPackage")
	public String auditPromotion() throws Exception{
		String type = request.getParameter("type");
		String id = request.getParameter("id");
        if("2".equals(type)){
            PromotionPackage promotionPackage = salesPromotionService.getPromotionAndGroupById(id);
            List<PromotionPackageGroup> details = promotionPackage.getPromotionGroup();
            for(PromotionPackageGroup detail : details){
                //判断单据是否被引用
                boolean flag = salesPromotionService.isPromotionQuote(detail.getGroupid());
                if(flag){
                    addJSONObject("auditFlag", true);
                    addLog("促销单编号："+id+" 已被引用，无法反审");
                    return SUCCESS;
                }
            }
        }
		boolean flag = salesPromotionService.auditPromotion(type, id);
		addJSONObject("flag", flag);
		if("1".equals(type)){
			addLog("促销单编号："+id+"审核 ", flag);
		}else{
			addLog("促销单编号："+id+"反审", flag);
		}
		return SUCCESS;
	}
    /**
     * 进入修改页面
     * @return
     * @throws Exception
     */
	public String promotionEditPage() throws Exception{

        request.setAttribute("act",request.getParameter("act"));

		String id = request.getParameter("id");
		PromotionPackage promotionPackage = salesPromotionService.getPromotionAndGroupById(id);

		String jsonStr = JSONUtils.listToJsonStr(promotionPackage.getPromotionGroup());

        request.setAttribute("businessDate",promotionPackage.getBusinessdate());
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("promotionPackage", promotionPackage);

        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);

		if(null!=promotionPackage){
            if("1".equals(promotionPackage.getStatus()) || "2".equals(promotionPackage.getStatus()) || "6".equals(promotionPackage.getStatus())){
                return "editSuccess";
            }else{
                return "viewSuccess";
            }
		}else{
			return "addSuccess";
		}
	}
    /**
     * 促销单删除
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="promotionPackage",type=4)
	public String deletePromotion() throws Exception{
		String ids = request.getParameter("ids");
        String[] idarr = ids.split(",");
        String success = "";
        String fail = "";
        String failure = "";
        boolean flag = false;
        for(String id : idarr){
            boolean delFlag = canTableDataDelete("t_sales_promotionPackage", id); //判断是否被引用，被引用则无法删除。
            if(delFlag){
                delFlag = salesPromotionService.deletePromotionById(id);
                if(delFlag){
                    flag = true;
                    if("".equals(success)){
                        success = "成功编号：" ;
                    }
                    success += id+",";
                }else{
                    if("".equals(fail)){
                        fail = "失败编号:";
                    }
                    fail += id+",";
                }
            }else{
                failure = id+",";
            }
        }
        if(StringUtils.isNotEmpty(failure)){
            failure = "编号："+failure+"被引用，无法删除";
        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("msg", success+" "+fail+" "+failure);
        addJSONObject(map);
        addLog("促销单删除 "+success+" "+fail, flag);
		return SUCCESS;
	}

    /**
     * 验证促销编号
     * @return
     * @throws Exception
     */
	public String auditbill() throws Exception{
		String groupid = request.getParameter("groupid");
		boolean flag = salesPromotionService.auditBundleBill(groupid);
		addJSONObject("flag",flag);
		return SUCCESS;
	}

    /**
     * 捆绑明细查看
     * @return
     * @throws Exception
     */
	public String bundleDetailView() throws Exception {
		String groupid = request.getParameter("groupid");
		PromotionPackageGroup group = salesPromotionService.getBundle(groupid);
		String jsonStr = JSONUtils.listToJsonStr(group.getGroupDetails());
		request.setAttribute("group", group);
		request.setAttribute("goodsJson", jsonStr);
		return SUCCESS;
	}

    /**
     * 捆绑修改
     * @return
     * @throws Exception
     */
	public String bundleEditDetail() throws Exception{
        request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return SUCCESS;
	}

    /**
     * 作废该促销单
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="promotionPackage",type=3)
    public String promotionCancel() throws Exception{
        String type = request.getParameter("type");
        String ids = request.getParameter("ids");
        String operate = request.getParameter("operate");
        Map map = salesPromotionService.promotionCancel(ids,operate);
        map.put("type",type);
        addJSONObject(map);
        if("1".equals(type)){
            addLog("作废买赠促销单 "+ids, map);
        }else if("2".equals(type)){
            addLog("作废捆绑促销单 "+ids, map);
        }else if("3".equals(type)){
            addLog("作废满赠促销单 "+ids, map);
        }

        return SUCCESS;
    }


	
}


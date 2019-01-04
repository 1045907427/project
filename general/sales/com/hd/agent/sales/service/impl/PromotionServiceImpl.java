/**
 * @(#)PromotionServiceImpl.java
 *
 * @author lin_xx
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年1月7日 lin_xx 创建版本
 */
package com.hd.agent.sales.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.CustomerSortMapper;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.util.*;
import com.hd.agent.sales.model.PromotionPackage;
import com.hd.agent.sales.model.PromotionPackageGroup;
import com.hd.agent.sales.model.PromotionPackageGroupDetail;
import com.hd.agent.sales.service.IPromotionService;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;


public class PromotionServiceImpl extends BaseSalesServiceImpl implements IPromotionService {

    //将对应的实体类Mapper封装到了BaseSalesServiceImpl
    //这里直接调用 getSalesPromotionMapper()方法

    @Override
    public PageData getPromotionData(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_sales_promotion_package", null);
        pageMap.setDataSql(dataSql);
        List<PromotionPackage> promotion = new ArrayList<PromotionPackage>();
        promotion = getSalesPromotionMapper().getPromotionList(pageMap);
        for(PromotionPackage model : promotion) {
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(model.getApplydeptid());
            if (departMent != null) {
                model.setApplydeptid(departMent.getName());
            }
            Personnel personnel = getPersonnelById(model.getApplyuserid());
            if (personnel != null) {
                model.setApplyuserid(personnel.getName());
            }
            if ("2".equals(model.getCustomertype())) {
                if (model.getCustomerid().contains(",")) {//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for (int i = 0; i < cids.length; i++) {
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "promotionsort");
                        if (sysCode != null) {
                            if (customername == "") {
                                customername = sysCode.getCodename();
                            } else {
                                customername += "," + sysCode.getCodename();
                            }
                        }
                    }
                    model.setCustomername(customername);
                } else {
                    SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(model.getCustomerid(), "promotionsort");
                    if (sysCode != null) {
                        model.setCustomername(sysCode.getCodename());
                    }
                }
            } else if ("3".equals(model.getCustomertype())) {
                CustomerSortMapper customerSortMapper = (CustomerSortMapper) SpringContextUtils.getBean("customerSortMapper");
                Map map = new HashMap();
                if (model.getCustomerid().contains(",")) {//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for (int i = 0; i < cids.length; i++) {
                        map.put("id", cids[i]);
                        CustomerSort customerSort = customerSortMapper.getCustomerSortDetail(map);
                        if (customerSort != null) {
                            if (customername == "") {
                                customername = customerSort.getName();
                            } else {
                                customername += "," + customerSort.getName();
                            }
                        }
                    }
                    model.setCustomername(customername.toString());
                } else {
                    map.put("id", model.getCustomerid());

                    CustomerSort customerSort = customerSortMapper.getCustomerSortDetail(map);
                    if (customerSort != null) {
                        model.setCustomerid(customerSort.getId());
                        model.setCustomername(customerSort.getName());
                    }
                }
            } else if ("4".equals(model.getCustomertype())) {
                if (model.getCustomerid().contains(",")) {//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for (int i = 0; i < cids.length; i++) {
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "price_list");
                        if (sysCode != null) {
                            if (customername == "") {
                                customername = sysCode.getCodename();
                            } else {
                                customername += "," + sysCode.getCodename();
                            }
                        }
                    }
                    model.setCustomername(customername);
                } else {
                    SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(model.getCustomerid(), "price_list");
                    if (sysCode != null) {
                        model.setCustomername(sysCode.getCodename());
                    }
                }
            } else if ("5".equals(model.getCustomertype())) {
                Map map = new HashMap();
                map.put("id", model.getCustomerid());
                if (model.getCustomerid().contains(",")) {//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for (int i = 0; i < cids.length; i++) {
                        map.put("id", cids[i]);
                        SalesArea salesArea = getBaseFilesSalesAreaMapper().getSalesAreaDetail(map);
                        if (salesArea != null) {
                            if (customername == "") {
                                customername = salesArea.getName();
                            } else {
                                customername += "," + salesArea.getName();
                            }
                        }
                    }
                    model.setCustomername(customername.toString());
                } else {
                    map.put("id", model.getCustomerid());

                    SalesArea salesArea = getBaseFilesSalesAreaMapper().getSalesAreaDetail(map);
                    if (salesArea != null) {
                        model.setCustomerid(salesArea.getId());
                        model.setCustomername(salesArea.getName());
                    }
                }
            } else if ("7".equals(model.getCustomertype())) {
                if(model.getCustomerid().contains(",")){//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for(int i = 0 ; i<cids.length ;i++){
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "creditrating");
                        if (sysCode != null) {
                            if(customername == ""){
                                customername =  sysCode.getCodename();
                            }else{
                                customername += "," + sysCode.getCodename() ;
                            }
                        }
                    }
                    model.setCustomername(customername);
                }else{
                    SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(model.getCustomerid(), "creditrating");
                    if (sysCode != null) {
                        model.setCustomername(sysCode.getCodename());
                    }
                }
            } else if ("8".equals(model.getCustomertype())) {
                if (model.getCustomerid().contains(",")) {//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for (int i = 0; i < cids.length; i++) {
                        SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(cids[i], "canceltype");
                        if (sysCode != null) {
                            if (customername == "") {
                                customername = sysCode.getCodename();
                            } else {
                                customername += "," + sysCode.getCodename();
                            }
                        }
                    }
                    model.setCustomername(customername);
                } else {
                    SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(model.getCustomerid(), "canceltype");
                    if (sysCode != null) {
                        model.setCustomername(sysCode.getCodename());
                    }
                }

            } else {
                Map map = new HashMap();
                if (model.getCustomerid().contains(",")) {//客户群名称存在多个
                    String[] cids = model.getCustomerid().split(",");
                    String customername = "";
                    for (int i = 0; i < cids.length; i++) {
                        map.put("id", cids[i]);
                        Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
                        if (customer != null) {
                            if (customername == "") {
                                customername = customer.getName();
                            } else {
                                customername += "," + customer.getName();
                            }
                        }
                    }
                    model.setCustomername(customername.toString());
                } else {
                    map.put("id", model.getCustomerid());

                    Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
                    if (customer != null) {
                        model.setCustomername(customer.getName());
                    }
                }
            }
        }
        return  new PageData(getSalesPromotionMapper().getPromotionCount(pageMap), promotion, pageMap);
    }

    @Override
    public PromotionPackage getPromotionAndGroupById(String id) throws Exception {
        //通过id获取促销活动及活动产品组
        PromotionPackage promotionPackage = getSalesPromotionMapper().getPromotion(id);
        if(promotionPackage != null) {
            List<PromotionPackageGroup> promotionDetaiList = new ArrayList<PromotionPackageGroup>();
            if (!"2".equals(promotionPackage.getPtype())) {
                //买赠 package的id与package_group的billid相同
                promotionPackage.setPtype("1");
                promotionDetaiList = getSalesPromotionMapper().getGroupListByPromotion(id);
                for (PromotionPackageGroup p : promotionDetaiList) {
                    p.setStatus("3");//审核后产品组状态仍为2的问题 查看时重新设置为3
                    getSalesPromotionMapper().updateByGroup(p);//设置后更新
                    String goodsid = p.getGoodsid();
                    GoodsInfo detail = getGoodsInfoByID(goodsid);
                    p.setGoodsname(detail.getName());
                    p.setBrand(detail.getBrandName());
                    BigDecimal numberin = detail.getBoxnum();//箱装量
                    if (numberin == null) {
                        numberin = new BigDecimal(30);//如果箱装量为空，默认箱装量为30
                    }
                    BigDecimal price = p.getPrice();//买赠促销价存放在price中
                    BigDecimal boxprice1 = numberin.multiply(price);//箱价
                    String boxprice = boxprice1.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                    p.setBoxprice(boxprice);
                    p.setBoxnum(numberin);

                    if ("2".equals(p.getGtype())) {
                        p.setRemark("");
                    }
                    if ("1".equals(p.getPtype())) {
                        p.setPrice(price);
                    } else if ("2".equals(p.getPtype())) {
                        p.setUnitprice(detail.getBasesaleprice().setScale(2, BigDecimal.ROUND_HALF_UP));
                        p.setPrice(p.getUnitprice());//属性更换，price中放入商品单价
                    }

                }
            } else {
                promotionDetaiList = getSalesPromotionMapper().getBundleInfo(id);
                String remark = "";
                for (PromotionPackageGroup p : promotionDetaiList) {
                    p.setStatus("3");//审核后产品组状态仍为2的问题 查看时重新设置为3
                    getSalesPromotionMapper().updateByGroup(p);//设置后更新
                    if (remark.equals("")) {
                        remark = p.getRemark();
                    } else if (remark.equals(p.getRemark())) {
                        p.setRemark("");
                    } else {
                        remark = p.getRemark();
                    }
                    String goodsid = p.getGoodsid();
                    GoodsInfo detail = getGoodsInfoByID(goodsid);
                    p.setGoodsname(detail.getName());
                    p.setUnitname(detail.getMainunitName());
                    p.setTotalprice(p.getPrice().toString());
                    p.setTotaloldprice(p.getOldprice().toString());
                    //捆绑时防止group表中的套餐价和detail表中的商品价冲突，商品单价为goodsprice goodsoldprice
                    p.setGoodsoldprice(p.getGoodsoldprice());
                    p.setGoodsprice(p.getGoodsprice());
                    p.setBoxnum(detail.getBoxnum().setScale(BillGoodsNumDecimalLenUtils.decimalLen, BigDecimal.ROUND_HALF_UP));
                    p.setUnitprice(detail.getBasesaleprice().setScale(2, BigDecimal.ROUND_HALF_UP));
                }

            }
            promotionPackage.setPromotionGroup(promotionDetaiList);
        }else{

        }
        return promotionPackage;
    }

    @Override
    public Map changeAux(String id, String promotionNum,String auxremainder,String auxnum,String type)throws Exception {
        Map map = new HashMap();
        GoodsInfo goodsInfo = getGoodsInfoByID(id);
        BigDecimal boxnum = goodsInfo.getBoxnum();
        if("1".equals(type)){
            BigDecimal promotionNumber = new BigDecimal(promotionNum.trim());
            BigDecimal auxn = promotionNumber.divideAndRemainder(boxnum)[0];
            BigDecimal auxre = promotionNumber.divideAndRemainder(boxnum)[1];
            map.put("promotionNum",promotionNum);
            map.put("auxremainder", auxre);
            map.put("auxnum", auxn);
        }else{
            BigDecimal auxre = BigDecimal.ZERO;
            BigDecimal auxn = BigDecimal.ZERO;
            if(StringUtils.isNotEmpty(auxremainder.trim())){
                auxre = new BigDecimal(auxremainder.trim());
            }
            if(StringUtils.isNotEmpty(auxnum.trim())){
                auxn  = new BigDecimal(auxnum.trim());
            }
            BigDecimal promotionNumber = auxn.multiply(boxnum).add(auxre);

            auxn = promotionNumber.divideAndRemainder(boxnum)[0];
            auxre = promotionNumber.divideAndRemainder(boxnum)[1];
            map.put("promotionNum",promotionNumber);
            map.put("auxremainder", auxre);
            map.put("auxnum", auxn);
        }
        return map;
    }

    @Override
    public Map getClickGoodsInfo(String id) throws Exception {
        GoodsInfo goodsInfo  = getGoodsInfoByID(id);
        Map map = new HashMap();
        if(goodsInfo != null){

            map.put("goodsname", goodsInfo.getName());
            map.put("brand", goodsInfo.getBrandName());
            map.put("oldprice", goodsInfo.getBasesaleprice());
            map.put("unitname", goodsInfo .getMainunitName());
            map.put("goodsid",id);
        }
        return map;
    }

    @Override
    public Map addBuyFreeInfo(PromotionPackage promotionPackage, List groupList) throws Exception {
        Map map = new HashMap();
        String id = promotionPackage.getId();
        Date addtime = new Date();
        if( id == null || id.isEmpty()){//表示新增
            if(isAutoCreate("t_sales_promotion_package")){
                id = getAutoCreateSysNumbderForeign(promotionPackage , "t_sales_promotion_package");
            }else{
                id = "BFr-"+ CommonUtils.getDataNumberSendsWithRand();
            }
            promotionPackage.setId(id);
            promotionPackage.setAddusername(promotionPackage.getAddusername());
        }else{
            addtime = getSalesPromotionMapper().getPromotion(id).getAddtime();
            getSalesPromotionMapper().deleteByPackageId(id);
            getSalesPromotionMapper().deleteByGroupId(id);
            getSalesPromotionMapper().deleteByPromotionId(id);
        }
        String groupid = "";
        if(null == promotionPackage.getAddtime()){
            promotionPackage.setAddtime(addtime);
        }
        for(int i = 0; i < groupList.size(); i++){
            PromotionPackageGroup group = (PromotionPackageGroup) groupList.get(i);
            boolean flag = false;
            if(StringUtils.isNotEmpty(group.getGroupid())){
                flag = !groupid.equals(group.getGroupid());
            }else{
                group.setGroupid(groupid);
            }
            if(groupid == ""){
                groupid = group.getGroupid();
                String billid = promotionPackage.getId();
                group.setStatus("2");
                group.setBillid(billid);
                group.setPrice(group.getPrice());
                group.setBegindate(promotionPackage.getBegindate());
                group.setEnddate(promotionPackage.getEnddate());
                String pinyin = CommonUtils.getPinYingJCLen(group.getGroupname());
                group.setPinyin(pinyin);
                if(group.getLimitnum() == null){
                    group.setLimitnum(new BigDecimal(0));
                    group.setRemainnum(new BigDecimal(0));
                }
                if(null != group.getLimitnum() && null != group.getRemainnum() &&
                        group.getLimitnum() != group.getRemainnum()){
                    group.setRemainnum(group.getLimitnum());
                }
                //插入促销单据
                getSalesPromotionMapper().addPackageGroup(group);
            }else if(flag){
                groupid = group.getGroupid();
                String billid = promotionPackage.getId();
                group.setStatus("2");
                group.setBillid(billid);
                group.setPrice(group.getPrice());
                group.setBegindate(promotionPackage.getBegindate());
                group.setEnddate(promotionPackage.getEnddate());
                String pinyin = CommonUtils.getPinYingJCLen(group.getGroupname());
                group.setPinyin(pinyin);
                if(group.getLimitnum() == null){
                    group.setLimitnum(new BigDecimal(0));
                    group.setRemainnum(new BigDecimal(0));
                }
                if(null != group.getLimitnum() && null != group.getRemainnum() &&
                        group.getLimitnum() != group.getRemainnum()){
                    group.setRemainnum(group.getLimitnum());
                }
                //插入促销单据
                getSalesPromotionMapper().addPackageGroup(group);
            }
            PromotionPackageGroupDetail detail = new PromotionPackageGroupDetail();
            if("1".equals(group.getGtype())){
                detail = insertDetail(group,1);//1表示该条信息为所购产品
            }else{
                detail = insertDetail(group,2);//2表示该条信息为赠送商品
            }
            detail.setBillid(id);
            getSalesPromotionMapper().addPackageGroupDetail(detail);
        }
        //插入促销活动
        boolean flag = getSalesPromotionMapper().addPackage(promotionPackage) > 0;
        map.put("flag",flag);
        map.put("id",id);
        return  map;
    }

    public PromotionPackageGroupDetail insertDetail(PromotionPackageGroup group,int a ) throws Exception{
        PromotionPackageGroupDetail detail = new PromotionPackageGroupDetail();
        String id = group.getGoodsid();
        GoodsInfo goodsInfo =getGoodsInfoByID(id);
        //商品信息
        if(a == 1){
            detail.setGtype("1");
            detail.setAuxnum(group.getAuxnum());//辅单位整数
            detail.setAuxremainder(group.getAuxremainder());//辅单位余数
            if(null != group.getPrice()){
                detail.setPrice(new BigDecimal(group.getPrice()+""));
            }else{
                detail.setPrice(goodsInfo.getBasesaleprice());//前台没有传回商品价格时从商品档案中获取基本销售价
            }
            //取整插入数据库
            group.setAuxnum(group.getAuxnum());
            group.setAuxremainder(group.getAuxremainder());
            detail.setAuxnumdetail(group.getAuxnum()+group.getAuxunitname()+group.getAuxremainder()+group.getUnitname());

        }else{
            detail.setGtype("2");
            detail.setPrice(new BigDecimal(0));
            detail.setOldprice(goodsInfo.getBasesaleprice());

            group.setUnitnum(group.getUnitnum());
            detail.setAuxnumdetail(group.getUnitnum()+group.getUnitname());
        }
        detail.setGroupid(group.getGroupid());
        detail.setGoodsid(id);
        detail.setUnitnum(group.getUnitnum());//基准数量（商品促销时的购买量）
        detail.setUnitid(goodsInfo.getMainunit());
        detail.setUnitname(goodsInfo.getMainunitName());
        //商品单位
        MeteringUnit minfo = getGoodsDefaulAuxunit(id);
        detail.setAuxunitid(minfo.getId());
        detail.setAuxunitname(minfo.getName());
        //买赠
        detail.setAddtime(new Date());
        return detail;
    }
    @Override
    public List<PromotionPackageGroupDetail> viewGiveDetail(String billid) throws Exception {
        return getSalesPromotionMapper().selectPromotionPackageDetailList(billid);
    }

    @Override
    public PromotionPackageGroupDetail getGroupDetailByid(String groupid) throws Exception {
        return getSalesPromotionMapper().getGroupDetailByid(groupid);
    }

    @Override
    public boolean auditPromotion(String type, String id) throws Exception {
        SysUser sysUser = getSysUser();
        PromotionPackage p = getSalesPromotionMapper().getPromotion(id);

        boolean flag = false ;
        if("1".equals(type)){
            if("2".equals(p.getStatus())){

                PromotionPackage group = getPromotionAndGroupById(id);
                List<PromotionPackageGroup> details = group.getPromotionGroup();
                for(PromotionPackageGroup detail : details){
                    //审核前保证促销数量和剩余数量一致
                    if(!detail.getRemainnum().equals(detail.getLimitnum())){
                        detail.setRemainnum(detail.getLimitnum());
                    }
                }

                PromotionPackage packageChange = new PromotionPackage();
                packageChange.setAddusername(p.getAddusername());
                packageChange.setAudituserid(sysUser.getUserid());
                packageChange.setAuditusername(sysUser.getName());
                packageChange.setAudittime(new Date());
                packageChange.setBusinessdate(CommonUtils.getTodayDataStr());
                packageChange.setId(id);
                packageChange.setStatus("3");
                flag =  getSalesPromotionMapper().updatePackageStatus(packageChange)>0;
            }
        }else if("2".equals(type)){ //反审
            if("3".equals(p.getStatus())){ //只有状态为3（审核状态）才可进行反审

                PromotionPackage packageChange = new PromotionPackage();
                packageChange.setId(id);
                packageChange.setStatus("2");
                flag = getSalesPromotionMapper().updatePackageStatus(packageChange)>0;
            }
        }
        return flag;
    }

    @Override
    public PromotionPackageGroupDetail getGroupDetailBybuygoodid(String goodsid) throws Exception {
        return getSalesPromotionMapper().getGroupDetailBybuygoodid(goodsid);
    }

    @Override
    public boolean updateGroupAndGivePromotion(
            PromotionPackage promotionPackage, List groupList, List detailList) throws Exception {

        boolean flag = getSalesPromotionMapper().updateByPromotion(promotionPackage) >0;
        if(flag){

            for(int i = 0; i < groupList.size(); i++){

                PromotionPackageGroup group = (PromotionPackageGroup) groupList.get(i);

                getSalesPromotionMapper().updateByGroup(group);
                if(detailList != null) {

                    for (int j = 0; j < detailList.size(); j++) {
                        PromotionPackageGroupDetail detail =  (PromotionPackageGroupDetail) detailList.get(j);
                        if(detail.getBillid() == String.valueOf(group.getId())){
                            getSalesPromotionMapper().updateByDetail(detail);
                        }
                    }

                }
            }
        }
        return flag;
    }

    @Override
    public boolean deletePromotionById(String id) {
        int a = getSalesPromotionMapper().deleteByPackageId(id);
        getSalesPromotionMapper().deleteByGroupId(id);
        getSalesPromotionMapper().deleteByPromotionId(id);
        return a>0;
    }

    @Override
    public boolean auditBundleBill(String groupid) throws Exception {
        PromotionPackageGroup group = getSalesPromotionMapper().getBundleGroup(groupid);
        if(group != null){
            return true;
        }else{
            return false;
        }

    }

    //捆绑信息新增
    @Override
    public Map addBundleInfo(PromotionPackage promotionPackage,List groupList) throws Exception {
        String id = promotionPackage.getId();
        Date addtime = new Date();
        Map map = new HashMap();
        if( id.isEmpty()){//插入
            if(isAutoCreate("t_sales_promotion_package")){
                id = getAutoCreateSysNumbderForeign(promotionPackage , "t_sales_promotion_package");
            }else{
                id = "Bun-"+ CommonUtils.getDataNumberSendsWithRand();
            }
            promotionPackage.setId(id);
        }else{//更新
            addtime = getSalesPromotionMapper().getPromotion(id).getAddtime();
            getSalesPromotionMapper().deleteByPackageId(id);
            getSalesPromotionMapper().deleteByGroupId(id);
            getSalesPromotionMapper().deleteByPromotionId(id);
        }
        promotionPackage.setPtype("2");//表示为捆绑活动
        if(null == promotionPackage.getAddtime()){
            promotionPackage.setAddtime(addtime);
        }
        String groupid = "" ;
        for(int i = 0; i < groupList.size(); i++){
            PromotionPackageGroup group = (PromotionPackageGroup) groupList.get(i);
            boolean flag = !groupid.equals(group.getGroupid());
            if(groupid == "" || flag ){
                groupid = group.getGroupid();
                String billid = promotionPackage.getId();
                group.setStatus("2");
                group.setBillid(billid);
                group.setBegindate(promotionPackage.getBegindate());
                group.setEnddate(promotionPackage.getEnddate());
                String pinyin = CommonUtils.getPinYingJCLen(group.getGroupname());
                group.setPinyin(pinyin);
                group.setPrice(new BigDecimal(group.getTotalprice()));
                group.setOldprice(new BigDecimal(group.getTotaloldprice()));

                //促销数量不为0 而剩余数量为0 的情况
                if(null == group.getRemainnum() ||
                        !(group.getRemainnum().equals(group.getLimitnum()))){
                    group.setRemainnum(group.getLimitnum());
                }
                //插入促销单据
                getSalesPromotionMapper().addPackageGroup(group);
            }
        }
        for (int j = 0; j < groupList.size(); j++) {
            PromotionPackageGroup gDetail = (PromotionPackageGroup) groupList.get(j);//从group中获取detail数据
            PromotionPackageGroupDetail detail = new PromotionPackageGroupDetail();

            detail.setGoodsid(gDetail.getGoodsid());
            detail.setUnitnum(gDetail.getUnitnum());//捆绑数量
            //价格
            detail.setPrice(gDetail.getGoodsprice());
            detail.setOldprice(gDetail.getGoodsoldprice());
            //主单位
            GoodsInfo goodsInfo = getGoodsInfoByID(gDetail.getGoodsid());
            detail.setUnitid(goodsInfo.getMainunit());detail.setUnitname(gDetail.getUnitname());
            //辅单位
            MeteringUnit unit = getGoodsDefaulAuxunit(gDetail.getGoodsid());
            detail.setAuxunitid(unit.getId());detail.setAuxunitname(unit.getName());
            //主辅数量计算
            BigDecimal auxnum = detail.getUnitnum().divideAndRemainder(goodsInfo.getBoxnum())[0];
            BigDecimal auxremainder = detail.getUnitnum().divideAndRemainder(goodsInfo.getBoxnum())[1];
            detail.setAuxnum(auxnum);
            detail.setAuxremainder(auxremainder);
            detail.setAuxnumdetail(CommonUtils.strDigitNumDeal(auxnum+detail.getAuxunitname()+auxremainder+detail.getUnitname()));

            detail.setBillid(id);
            detail.setGroupid(gDetail.getGroupid());
            detail.setAddtime(new Date());
            detail.setGtype("1");//表示（捆绑）正常商品
            getSalesPromotionMapper().addPackageGroupDetail(detail);
        }
        //插入促销活动
        boolean flag = getSalesPromotionMapper().addPackage(promotionPackage) > 0;
        map.put("flag",flag);
        map.put("id",id);

        return  map;
    }



    @Override
    public PromotionPackageGroup getBundle(String groupid) throws Exception {
        PromotionPackageGroup group = getSalesPromotionMapper().getBundleGroup(groupid);
        List<PromotionPackageGroupDetail> detail = getSalesPromotionMapper().getDetailByGroupid(groupid);
        if(detail != null){
            for (int i = 0; i < detail.size(); i++) {
                PromotionPackageGroupDetail detailInfo = detail.get(i);
                String goodsid = detailInfo.getGoodsid();
                GoodsInfo gInfo = getGoodsInfoByID(goodsid);
                detailInfo.setGoodsname(gInfo.getName());
                detailInfo.setBoxnum(gInfo.getBoxnum());
                detailInfo.setUsablenum(group.getRemainnum());
            }
        }
        if(group != null){
            group.setGroupDetails(detail);
            return group;
        }else{
            return null;
        }

    }
    @Override
    public Map promotionCancel(String ids , String operate) throws Exception{
        Map returnMap = new HashMap();
        boolean flag = false;
        int failure = 0;
        int success = 0;
        if (StringUtils.isNotEmpty(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                PromotionPackage promotionPackage = getSalesPromotionMapper().getPromotion(id);
                if(null != promotionPackage){
                    if("1".equals(operate)){
                        promotionPackage.setStatus("5");//单据作废时状态为中止
                    }else{
                        promotionPackage.setStatus("2");//单据取消作废时状态为保存
                    }
                    boolean delflag = getSalesPromotionMapper().updatePackageStatus(promotionPackage) > 0;
                    if (delflag) {
                        success++;
                    } else {
                        failure++;
                    }
                }else{
                    failure++;
                }
            }
            flag = true ;
        }
        returnMap.put("flag", flag);
        returnMap.put("failure", failure);
        returnMap.put("success", success);
        return returnMap;
    }

    @Override
    public boolean isPromotionQuote(String groupid) throws Exception{
        return getSalesOrderMapper().countPromotionGroupid(groupid) > 0 ;
    }
}


package com.hd.agent.account.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.GoldTaxCustomerInvoiceMapper;
import com.hd.agent.account.model.GoldTaxCustomerInvoice;
import com.hd.agent.account.model.GoldTaxCustomerInvoiceDetail;
import com.hd.agent.account.service.IGoldTaxCustomerInvoiceService;
import com.hd.agent.basefiles.dao.JsTaxTypeCodeMapper;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.basefiles.service.IHtGoldTaxSystemService;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.ValueUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;

public class GoldTaxCustomerInvoiceServiceImpl extends BaseFilesServiceImpl implements IGoldTaxCustomerInvoiceService {
    private GoldTaxCustomerInvoiceMapper goldTaxCustomerInvoiceMapper;

    public GoldTaxCustomerInvoiceMapper getGoldTaxCustomerInvoiceMapper() {
        return goldTaxCustomerInvoiceMapper;
    }

    public void setGoldTaxCustomerInvoiceMapper(GoldTaxCustomerInvoiceMapper goldTaxCustomerInvoiceMapper) {
        this.goldTaxCustomerInvoiceMapper = goldTaxCustomerInvoiceMapper;
    }


    @Override
    public PageData getGoldTaxCustomerInvoicePageData(PageMap pageMap) throws Exception {
        List<GoldTaxCustomerInvoice> list= goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoicePageList(pageMap);
        int iTotal= goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoicePageCount(pageMap);
        PageData pageData=new PageData(iTotal,list,pageMap);
        return pageData;
    }

    @Override
    public Map addGoldTaxCustomerInvoiceAddDetail(GoldTaxCustomerInvoice goldTaxCustomerInvoice) throws Exception {
        Map resultMap= new HashMap();
        boolean flag=false;
        List<GoldTaxCustomerInvoiceDetail> detailList= goldTaxCustomerInvoice.getDetailList();

        if("2".equals(goldTaxCustomerInvoice.getStatus())){
            if(null==detailList || detailList.size()==0){
                resultMap.put("flag", false);
                resultMap.put("msg", "保存状态下，请填写客户金税开票明细数据");
                return resultMap;
            }
        }

        SysUser sysUser=getSysUser();
        Date opDate=new Date();
        if (isAutoCreate("t_goldtax_customer_invoice")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(goldTaxCustomerInvoice, "t_goldtax_customer_invoice");
            goldTaxCustomerInvoice.setId(id);
        }else{
            String id = "GTTI-"+ CommonUtils.getDataNumberSendsWithRand();
            goldTaxCustomerInvoice.setId(id);
        }
        goldTaxCustomerInvoice.setAddtime(opDate);
        goldTaxCustomerInvoice.setAdduserid(sysUser.getUserid());
        goldTaxCustomerInvoice.setAddusername(sysUser.getName());
        goldTaxCustomerInvoice.setAdddeptid(sysUser.getDepartmentid());
        goldTaxCustomerInvoice.setAdddeptname(sysUser.getDepartmentname());

        Object transactionBegin= TransactionAspectSupport.currentTransactionStatus().createSavepoint();

        flag= goldTaxCustomerInvoiceMapper.insertGoldTaxCustomerInvoice(goldTaxCustomerInvoice)>0;
        if(flag ) {
            int iseq = 1;
            BigDecimal taxamount=BigDecimal.ZERO;
            BigDecimal notaxamount=BigDecimal.ZERO;
            BigDecimal tax = BigDecimal.ZERO;
            for(GoldTaxCustomerInvoiceDetail item : detailList){
                item.setBillid(goldTaxCustomerInvoice.getId());
                item.setSeq(iseq);
                if(goldTaxCustomerInvoiceMapper.insertGoldTaxCustomerInvoiceDetail(item)>0){
                    taxamount=taxamount.add(item.getTaxamount());
                    notaxamount=notaxamount.add(item.getNotaxamount());
                    tax=tax.add(item.getTax());
                }else{
                    //如何保存失败后回滚
                    TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(transactionBegin);
                    resultMap.put("flag",false);
                    String msg="开票商品名称："+item.getGoodsname();
                    resultMap.put("msg",msg+"存入数据库时失败");
                    return resultMap;
                }

                iseq = iseq + 1;
            }
            goldTaxCustomerInvoice.setTaxamount(taxamount);
            goldTaxCustomerInvoice.setNotaxamount(notaxamount);
            goldTaxCustomerInvoice.setTax(tax);
            if(goldTaxCustomerInvoiceMapper.updateGoldTaxCustomerInvoceAmount(goldTaxCustomerInvoice)==0){
                //如何保存失败后回滚
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(transactionBegin);
                resultMap.put("flag",false);
                resultMap.put("msg","更新单据金额失败");
                return resultMap;
            }
            TransactionAspectSupport.currentTransactionStatus().releaseSavepoint(transactionBegin);
        }else{
            //如何保存失败后回滚
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(transactionBegin);
            resultMap.put("flag",false);
            resultMap.put("msg","添加单据时失败");
            return resultMap;
        }
        resultMap.put("flag",flag);
        return resultMap;
    }

    @Override
    public Map updateGoldTaxCustomerInvoiceAndDetail(GoldTaxCustomerInvoice goldTaxCustomerInvoice) throws Exception {
        Map resultMap=new HashMap();
        GoldTaxCustomerInvoice oldGoldTax = goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoice(goldTaxCustomerInvoice.getId());
        if(null==oldGoldTax){
            resultMap.put("flag",false);
            resultMap.put("msg","未找到相关单据");
            return resultMap;
        }
        if("3".equals(oldGoldTax.getStatus()) || "4".equals(oldGoldTax.getStatus())){
            resultMap.put("flag",false);
            resultMap.put("msg","审核通过的单据不可修改");
            return resultMap;
        }
        List<GoldTaxCustomerInvoiceDetail> detailList = goldTaxCustomerInvoice.getDetailList();
        if("2".equals(goldTaxCustomerInvoice.getStatus())){
            if(null==detailList || detailList.size()==0){
                resultMap.put("flag", false);
                resultMap.put("msg", "保存状态下，请填写客户金税开票明细数据");
                return resultMap;
            }
        }
        SysUser sysUser=getSysUser();
        goldTaxCustomerInvoice.setModifytime(new Date());
        goldTaxCustomerInvoice.setModifyuserid(sysUser.getUserid());
        goldTaxCustomerInvoice.setModifyusername(sysUser.getName());

        Object transactionBegin= TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        boolean flag= goldTaxCustomerInvoiceMapper.updateGoldTaxCustomerInvoce(goldTaxCustomerInvoice) > 0;
        if(flag){
            goldTaxCustomerInvoiceMapper.deleteGoldTaxCustomerInvoiceDetailByBillid(goldTaxCustomerInvoice.getId());
            int iseq = 1;
            BigDecimal taxamount=BigDecimal.ZERO;
            BigDecimal notaxamount=BigDecimal.ZERO;
            BigDecimal tax = BigDecimal.ZERO;
            for(GoldTaxCustomerInvoiceDetail item : detailList){
                item.setBillid(goldTaxCustomerInvoice.getId());
                item.setSeq(iseq);
                if(goldTaxCustomerInvoiceMapper.insertGoldTaxCustomerInvoiceDetail(item)>0){
                    taxamount=taxamount.add(item.getTaxamount());
                    notaxamount=notaxamount.add(item.getNotaxamount());
                    tax=tax.add(item.getTax());
                }else{
                    //如何保存失败后回滚
                    TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(transactionBegin);
                    resultMap.put("flag",false);
                    String msg="商品名称："+item.getSourcegoodsname();
                    resultMap.put("msg",msg+"存入数据库时失败");
                    return resultMap;
                }
                iseq = iseq + 1;

                if(StringUtils.isNotEmpty(item.getJstypeid())){
                    //if(jsTaxTypeCodeMapper.isUsedJsTaxTypeCodeById(item.getJstypeid())==0){
                     //   item.setJstypeid("");
                    //}
                }

            }
            goldTaxCustomerInvoice.setTaxamount(taxamount);
            goldTaxCustomerInvoice.setNotaxamount(notaxamount);
            goldTaxCustomerInvoice.setTax(tax);
            if(goldTaxCustomerInvoiceMapper.updateGoldTaxCustomerInvoceAmount(goldTaxCustomerInvoice)==0){
                //如何保存失败后回滚
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(transactionBegin);
                resultMap.put("flag",false);
                resultMap.put("msg","更新单据金额失败");
                return resultMap;
            }
            TransactionAspectSupport.currentTransactionStatus().releaseSavepoint(transactionBegin);
        }else{
            //如何保存失败后回滚
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(transactionBegin);
            resultMap.put("flag",false);
            resultMap.put("msg","修改单据时失败");
            return resultMap;
        }
        resultMap.put("flag",true);
        return resultMap;
    }

    @Override
    public boolean deleteGoldTaxCustomerInvoiceAndDetail(String id) throws Exception {
        boolean flag= goldTaxCustomerInvoiceMapper.deleteGoldTaxCustomerInvoice(id)>0;
        if(flag){
            goldTaxCustomerInvoiceMapper.deleteGoldTaxCustomerInvoiceDetailByBillid(id);
        }
        return flag;
    }

    @Override
    public GoldTaxCustomerInvoice getGoldTaxCustomerInvoice(String id) throws Exception {
        GoldTaxCustomerInvoice goldTaxCustomerInvoice = goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoice(id);
        return goldTaxCustomerInvoice;
    }

    @Override
    public GoldTaxCustomerInvoice getGoldTaxCustomerInvoiceAndDetail(String id) throws Exception {
        GoldTaxCustomerInvoice goldTaxCustomerInvoice = goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoice(id);
        if(null!= goldTaxCustomerInvoice){
            List<GoldTaxCustomerInvoiceDetail> detailList= goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoiceDetailListByBillid(id);
            goldTaxCustomerInvoice.setDetailList(detailList);
            GoldTaxCustomerInvoiceDetail detailSum=goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoiceDetailSum(goldTaxCustomerInvoice.getId());
            goldTaxCustomerInvoice.setDetailSum(detailSum);
        }

        return goldTaxCustomerInvoice;
    }
    @Override
    public List<GoldTaxCustomerInvoiceDetail> getGoldTaxCustomerInvoiceDetailListByBillid(String id) throws Exception {
        List<GoldTaxCustomerInvoiceDetail> detailList= goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoiceDetailListByBillid(id);
        return detailList;
    }

    @Override
    public Map auditGoldTaxCustomerInvoice(String id) throws Exception {
        Map resultMap=new HashMap();
        GoldTaxCustomerInvoice goldTaxCustomerInvoice = goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoice(id);
        if(null== goldTaxCustomerInvoice){
            resultMap.put("flag",false);
            resultMap.put("msg","未找到相关单据");
            return resultMap;
        }
        if("3".equals(goldTaxCustomerInvoice.getStatus()) || "4".equals(goldTaxCustomerInvoice.getStatus())){
            resultMap.put("flag",false);
            resultMap.put("msg","该单据已经通过审核");
            return resultMap;
        }
        List<GoldTaxCustomerInvoiceDetail> detailList= goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoiceDetailListByBillid(id);
        if(null== detailList || detailList.size()==0){
            resultMap.put("flag",false);
            resultMap.put("msg","未找到单据明细数据");
            return resultMap;
        }
        int jstypeidEmptyCount=0;
        for(GoldTaxCustomerInvoiceDetail item : detailList){
            if(StringUtils.isEmpty(item.getJstypeid())){
                jstypeidEmptyCount=jstypeidEmptyCount+1;
            }
        }
        if(jstypeidEmptyCount>0){
            resultMap.put("flag",false);
            resultMap.put("msg","数据有查询到金税编码为空的数据共有"+jstypeidEmptyCount+"条");
            return resultMap;
        }
        SysUser sysUser=getSysUser();
        GoldTaxCustomerInvoice auditOrder=new GoldTaxCustomerInvoice();
        auditOrder.setBusinessdate(CommonUtils.getTodayDataStr());
        auditOrder.setId(goldTaxCustomerInvoice.getId());
        auditOrder.setAudittime(new Date());
        auditOrder.setAudituserid(sysUser.getUserid());
        auditOrder.setAuditusername(sysUser.getName());
        boolean flag= goldTaxCustomerInvoiceMapper.auditGoldTaxCustomerInvoice(auditOrder)>0;
        resultMap.put("flag",flag);
        return resultMap;
    }

    @Override
    public Map oppauditGoldTaxCustomerInvoice(String id) throws Exception {
        Map resultMap=new HashMap();
        GoldTaxCustomerInvoice goldTaxCustomerInvoice = goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoice(id);
        if(null== goldTaxCustomerInvoice){
            resultMap.put("flag",false);
            resultMap.put("msg","未找到相关单据");
            return resultMap;
        }
        if("4".equals(goldTaxCustomerInvoice.getStatus())){
            resultMap.put("flag",false);
            resultMap.put("msg","该单据已经关闭");
            return resultMap;
        }
        if(!"3".equals(goldTaxCustomerInvoice.getStatus())){
            resultMap.put("flag",false);
            resultMap.put("msg","该单据未通过审核");
            return resultMap;
        }
        SysUser sysUser=getSysUser();
        GoldTaxCustomerInvoice auditOrder=new GoldTaxCustomerInvoice();
        auditOrder.setId(goldTaxCustomerInvoice.getId());
        auditOrder.setAudittime(new Date());
        auditOrder.setAudituserid(sysUser.getUserid());
        auditOrder.setAuditusername(sysUser.getName());
        boolean flag= goldTaxCustomerInvoiceMapper.oppauditGoldTaxCustomerInvoice(auditOrder)>0;
        resultMap.put("flag",flag);
        return resultMap;
    }

    @Override
    public boolean updateOrderJSExportTimes(GoldTaxCustomerInvoice goldTaxCustomerInvoice) throws Exception {
        if(null== goldTaxCustomerInvoice.getJxexportdatetime()){
            goldTaxCustomerInvoice.setJxexportdatetime(new Date());
        }
        return goldTaxCustomerInvoiceMapper.updateOrderJSExportTimes(goldTaxCustomerInvoice)>0;
    }

    @Override
    public Map importCustomerGoldTaxInvoiceData(String billid,List<Map<String,Object>> dataList) throws Exception{
        Map resultMap=new HashMap();
        if(null==dataList || dataList.size()==0){
            resultMap.put("flag",false);
            resultMap.put("msg","导入的明细数据为空");
            return resultMap;
        }
        GoldTaxCustomerInvoice goldTaxCustomerInvoice=goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoice(billid);
        if(null==goldTaxCustomerInvoice){
            resultMap.put("flag",false);
            resultMap.put("msg","未找到客户开票信息");
            return resultMap;
        }
        List<GoldTaxCustomerInvoiceDetail> importDataList=new ArrayList<GoldTaxCustomerInvoiceDetail>();
        List<Map<String,Object>> errorDataList=new ArrayList<Map<String, Object>>();
        Date nowDate=new Date();
        SysUser sysUser=getSysUser();
        StringBuilder upLogsb=new StringBuilder();
        int seq=0;
        //已经存在的金税编码
        Map jsTypeIdMap=new HashMap();
        BigDecimal taxamountTotal=BigDecimal.ZERO;
        BigDecimal notaxamountTotal=BigDecimal.ZERO;
        for(Map itemData : dataList){
            seq=seq+1;
            StringBuilder errorsb=new StringBuilder();
            int ierror=1;
            String jstypeid="";
            if(itemData.containsKey("jstypeid")){
                jstypeid=(String)itemData.get("jstypeid");
            }else{
                itemData.put("jstypeid","");
            }
            if(null==jstypeid){
                jstypeid="";
            }
            String sourcegoodsid="";
            if(itemData.containsKey("sourcegoodsid")){
                sourcegoodsid=(String)itemData.get("sourcegoodsid");
            }else{
                itemData.put("sourcegoodsid","");
            }
            String sourcegoodsname=(String)itemData.get("sourcegoodsname");
            String model="";
            if(itemData.containsKey("model")){
                model=(String)itemData.get("model");
            }else{
                itemData.put("model","");
            }
            String unitname = (String) itemData.get("unitname");
            String unitnumStr=(String) itemData.get("unitnum");
            String taxpriceStr=(String) itemData.get("taxprice");
            String taxamountStr=(String) itemData.get("taxamount");
            String taxrateStr=(String) itemData.get("taxrate");

            if(null==sourcegoodsname || "".equals(sourcegoodsname.trim())){
                errorsb.append(ierror);
                errorsb.append(")");
                errorsb.append("开票商品名称不能为空");
                errorsb.append("。 ");
                ierror=ierror+1;
            }
            if(null==unitname || "".equals(unitname.trim())){
                errorsb.append(ierror);
                errorsb.append(")");
                errorsb.append("计量单位不能为空");
                errorsb.append("。 ");
                ierror=ierror+1;
            }

            BigDecimal unitnum=BigDecimal.ZERO;
            if(null==unitnumStr || "".equals(unitnumStr.trim())){
                errorsb.append(ierror);
                errorsb.append(")");
                errorsb.append("数量不能为空");
                errorsb.append("。 ");
                ierror=ierror+1;
            }else if(!CommonUtils.isNumeric(unitnumStr.trim())){
                errorsb.append(ierror);
                errorsb.append(")");
                errorsb.append("数量必须为数字");
                errorsb.append("。 ");
                ierror=ierror+1;
            }else{
                //数量转换为大数字类型
                unitnum = new BigDecimal(unitnumStr.trim());
                if(BigDecimal.ZERO.compareTo(unitnum)==0) {
                    errorsb.append(ierror);
                    errorsb.append(")");
                    errorsb.append("数量不能为零");
                    errorsb.append("。 ");
                    ierror = ierror + 1;
                }
            }

            BigDecimal taxprice=BigDecimal.ZERO;
            if(null!=taxpriceStr && !"".equals(taxpriceStr.trim())){
                if(!CommonUtils.isNumeric(taxpriceStr.trim())){
                    errorsb.append(ierror);
                    errorsb.append(")");
                    errorsb.append("含税单价必须为数字");
                    errorsb.append("。 ");
                    ierror=ierror+1;
                }else{
                    taxprice = new BigDecimal(taxpriceStr.trim());
                    if(BigDecimal.ZERO.compareTo(taxprice)==0){
                        errorsb.append(ierror);
                        errorsb.append(")");
                        errorsb.append("含税单价不能为零");
                        errorsb.append("。 ");
                        ierror=ierror+1;
                    }else if(BigDecimal.ZERO.compareTo(taxprice)>0){
                        errorsb.append(ierror);
                        errorsb.append(")");
                        errorsb.append("含税单价不能小于零");
                        errorsb.append("。 ");
                        ierror=ierror+1;
                    }
                }
            }

            BigDecimal taxamount=BigDecimal.ZERO;
            if(null==taxamountStr || "".equals(taxamountStr.trim())){
                errorsb.append(ierror);
                errorsb.append(")");
                errorsb.append("含税金额不能为空");
                errorsb.append("。 ");
                ierror=ierror+1;
            }else if (!CommonUtils.isNumeric(taxamountStr.trim())) {
                errorsb.append(ierror);
                errorsb.append(")");
                errorsb.append("含税金额必须为数字");
                errorsb.append("。 ");
                ierror = ierror + 1;
            } else {
                //金额转换为大数字类型
                taxamount = new BigDecimal(taxamountStr);
                if (BigDecimal.ZERO.compareTo(taxamount) == 0) {
                    errorsb.append(ierror);
                    errorsb.append(")");
                    errorsb.append("含税金额不能为零");
                    errorsb.append("。 ");
                    ierror = ierror + 1;
                }
            }
            /*BigDecimal taxamountTmp=unitnum.multiply(taxprice).setScale(2,BigDecimal.ROUND_HALF_UP);
            if(taxamountTmp.compareTo(taxamount.setScale(2,BigDecimal.ROUND_HALF_UP))!=0){
                errorsb.append(ierror);
                errorsb.append(")");
                errorsb.append("开票商品名称："+sourcegoodsname+"单价*数量!=金额");
                errorsb.append("。 ");
                ierror=ierror+1;
            }*/

            BigDecimal taxRate=BigDecimal.ZERO;

            if(null==taxrateStr || "".equals(taxrateStr.trim())){
                errorsb.append(ierror);
                errorsb.append(")");
                errorsb.append("税率不能为空");
                errorsb.append("。 ");
                ierror=ierror+1;
            }else if(!CommonUtils.isInteger(taxrateStr.trim())){
                errorsb.append(ierror);
                errorsb.append(")");
                errorsb.append("税率必须为数字");
                errorsb.append("。 ");
                ierror=ierror+1;
            }else{
                //转化成税率大数字类型
                taxRate=new BigDecimal(taxrateStr);
                if(BigDecimal.ZERO.compareTo(taxRate)>0 ||
                        (BigDecimal.ZERO.compareTo(taxRate)!=0 && BigDecimal.ONE.compareTo(taxRate)>0)){
                    errorsb.append(ierror);
                    errorsb.append(")");
                    errorsb.append("税率必须为整数");
                    errorsb.append("。 ");
                    ierror=ierror+1;
                }
            }

            if(ierror>1){
                itemData.put("errormessage",errorsb.toString());
                errorDataList.add(itemData);
                continue;
            }
            GoldTaxCustomerInvoiceDetail goldTaxCustomerInvoiceDetail=new GoldTaxCustomerInvoiceDetail();
            goldTaxCustomerInvoiceDetail.setGoodsname(sourcegoodsname);
            goldTaxCustomerInvoiceDetail.setSourcegoodsid(sourcegoodsid);
            goldTaxCustomerInvoiceDetail.setSourcegoodsname(sourcegoodsname);
            goldTaxCustomerInvoiceDetail.setSourcetype("1");
            goldTaxCustomerInvoiceDetail.setBillid(billid);
            goldTaxCustomerInvoiceDetail.setModel(model);
            goldTaxCustomerInvoiceDetail.setUnitname(unitname);
            goldTaxCustomerInvoiceDetail.setUnitnum(unitnum);
            goldTaxCustomerInvoiceDetail.setTaxamount(taxamount);
            goldTaxCustomerInvoiceDetail.setTaxprice(taxamount.divide(unitnum,6,BigDecimal.ROUND_HALF_UP));
            goldTaxCustomerInvoiceDetail.setTaxrate(taxRate);
            //计算未税
            goldTaxCustomerInvoiceDetail.setNotaxamount(goldTaxCustomerInvoiceDetail.getTaxamount().divide(taxRate.divide(new BigDecimal(100)).add(new BigDecimal(1)),2,BigDecimal.ROUND_HALF_UP));
            //计算未税单价
            goldTaxCustomerInvoiceDetail.setNotaxprice(goldTaxCustomerInvoiceDetail.getNotaxamount().divide(goldTaxCustomerInvoiceDetail.getUnitnum(),9,BigDecimal.ROUND_HALF_UP));
            //计算税额
            goldTaxCustomerInvoiceDetail.setTax(goldTaxCustomerInvoiceDetail.getTaxamount().subtract(goldTaxCustomerInvoiceDetail.getNotaxamount()));

            taxamountTotal = taxamountTotal.add(taxamount);
            notaxamountTotal = notaxamountTotal.add(goldTaxCustomerInvoiceDetail.getNotaxamount());

            goldTaxCustomerInvoiceDetail.setJstypeid(null);
            goldTaxCustomerInvoiceDetail.setTaxfreeflag("0");
            if(BigDecimal.ZERO.compareTo(goldTaxCustomerInvoiceDetail.getTaxrate())==0){
                goldTaxCustomerInvoiceDetail.setTaxfreeflag("1");
            }


            //如果导入金税分类编码不为空，则去金税分类档案去判断是否存在
            if(!"".equals(jstypeid.trim())){
                if(jsTypeIdMap.containsKey(jstypeid.trim()) || getBaseJsTaxTypeCodeMapper().isUsedJsTaxTypeCodeById(jstypeid.trim())>0){
                    goldTaxCustomerInvoiceDetail.setJstypeid(jstypeid);
                    if(!jsTypeIdMap.containsKey(jstypeid.trim())){
                        //存入已经查询过的金税编码map
                        jsTypeIdMap.put(jstypeid.trim(),jstypeid.trim());
                    }
                }
            }

            //根据名称联出档案里的名称
            Map queryGoodsMap=new HashMap();
            queryGoodsMap.put("jstaxsortidNotEmpty","true");
            queryGoodsMap.put("getonedata","true");
            queryGoodsMap.put("namelike",sourcegoodsname.trim());
            List<GoodsInfo> goodsInfoByNameList=getBaseGoodsMapper().getGoodsInfoListByMap(queryGoodsMap);
            GoodsInfo jsGoodsInfo=null;
            if(goodsInfoByNameList.size()>0){
                jsGoodsInfo=goodsInfoByNameList.get(0);
            }

            if(null!=jsGoodsInfo && StringUtils.isNotEmpty(jsGoodsInfo.getId())){
                goldTaxCustomerInvoiceDetail.setGoodsid(jsGoodsInfo.getId());
                goldTaxCustomerInvoiceDetail.setGoodsname(jsGoodsInfo.getName());

                //如果导入的金税分类编码不存，则去判断商品档案对应的金税编码是否为空
                if(StringUtils.isEmpty(goldTaxCustomerInvoiceDetail.getJstypeid()) && StringUtils.isNotEmpty(jsGoodsInfo.getJstaxsortid())){
                    if(jsTypeIdMap.containsKey(jsGoodsInfo.getJstaxsortid()) || getBaseJsTaxTypeCodeMapper().isUsedJsTaxTypeCodeById(jsGoodsInfo.getJstaxsortid())>0){
                        goldTaxCustomerInvoiceDetail.setJstypeid(jsGoodsInfo.getJstaxsortid());
                        if(!jsTypeIdMap.containsKey(jstypeid.trim())){
                            //存入已经查询过的金税编码map
                            jsTypeIdMap.put(jsGoodsInfo.getJstaxsortid(),jsGoodsInfo.getJstaxsortid());
                        }
                    }
                }
            }
            goldTaxCustomerInvoiceDetail.setSeq(seq);
            importDataList.add(goldTaxCustomerInvoiceDetail);

        }
        if(errorDataList.size()>0){
            resultMap.put("flag",false);
            resultMap.put("msg","系统检测出有不符合规范的导入数据");
            resultMap.put("errorDataList", errorDataList);
            return resultMap;
        }
        //删除旧
        goldTaxCustomerInvoiceMapper.deleteGoldTaxCustomerInvoiceDetailByBillid(billid.trim());
        //批量更新条数
        int updateDBCount=300;
        int listLen = importDataList.size();
        boolean canInsert = listLen > 0;
        int ipage = 0;
        //数据小于等于300条时，直接插入
        boolean isupdate=false;
        if(listLen<=updateDBCount) {
            isupdate=goldTaxCustomerInvoiceMapper.insertGoldTaxCustomerInvoiceDetailBatch(importDataList)>0;
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
                List<GoldTaxCustomerInvoiceDetail> tmpList = importDataList.subList(ipage * updateDBCount, row);
                isupdate=goldTaxCustomerInvoiceMapper.insertGoldTaxCustomerInvoiceDetailBatch(tmpList)>0 || isupdate;
                ipage = ipage + 1;
            }
        }
        GoldTaxCustomerInvoiceDetail detailSum=goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoiceDetailSum(billid);
        GoldTaxCustomerInvoice upBillData=new GoldTaxCustomerInvoice();
        upBillData.setId(goldTaxCustomerInvoice.getId());
        if("1".equals(upBillData.getStatus()) || "".equals(upBillData.getStatus())) {
            upBillData.setStatus("2");
        }
        upBillData.setModifytime(new Date());
        upBillData.setModifyuserid(sysUser.getUserid());
        upBillData.setModifyusername(sysUser.getName());
        upBillData.setTax(detailSum.getTax());
        upBillData.setTaxamount(detailSum.getTaxamount());
        upBillData.setNotaxamount(detailSum.getNotaxamount());
        goldTaxCustomerInvoiceMapper.updateGoldTaxCustomerInvoce(upBillData);

        String msg="";
        taxamountTotal=taxamountTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
        if(taxamountTotal.compareTo(detailSum.getTaxamount().setScale(2,BigDecimal.ROUND_HALF_UP))!=0){
            msg="注意，待导入总金额"+taxamountTotal+",成功导入总金额"+detailSum.getTaxamount().setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        if(isupdate) {
            resultMap.put("flag", true);
            resultMap.put("msg", msg);
        }else{
            resultMap.put("flag",false);
        }
        resultMap.put("errorDataList",errorDataList);
        resultMap.put("updateLogs",upLogsb.toString());
        return resultMap;
    }
    @Override
    public boolean editGoldTaxCustomerInvoiceNo(GoldTaxCustomerInvoice goldTaxCustomerInvoice) throws Exception{
        SysUser sysUser=getSysUser();
        GoldTaxCustomerInvoice updateOrder=new GoldTaxCustomerInvoice();
        updateOrder.setId(goldTaxCustomerInvoice.getId());
        updateOrder.setInvoiceno(goldTaxCustomerInvoice.getInvoiceno());
        updateOrder.setInvoicecode(goldTaxCustomerInvoice.getInvoicecode());
        updateOrder.setModifytime(new Date());
        updateOrder.setModifyuserid(sysUser.getUserid());
        updateOrder.setModifyusername(sysUser.getName());
        return goldTaxCustomerInvoiceMapper.updateGoldTaxCustomerInvoce(updateOrder)>0;
    }
    @Override
    public Map getGoldTaxCustomerInvoiceBillXMLForHTKP(Map map) throws Exception{
        String id=(String)map.get("exportid");
        String amountoptions=(String)map.get("amountoptions");
        Map resultMap = new HashMap();
        resultMap.put("flag",false);
        GoldTaxCustomerInvoice goldTaxCustomerInvoice = goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoice(id);
        if(null == goldTaxCustomerInvoice) {
            resultMap.put("flag",false);
            resultMap.put("msg","未能找到相关单据信息");
            return resultMap;
        }
        List<GoldTaxCustomerInvoiceDetail> detailList = goldTaxCustomerInvoiceMapper.getGoldTaxCustomerInvoiceDetailListByBillid(id);


        if(null==detailList || detailList.size()==0){
            resultMap.put("flag",false);
            resultMap.put("msg", MessageFormat.format("单据{0}导出明细为空",id));
            return resultMap;
        }

        List<Map> resultDetailList=new ArrayList<Map>();
        BigDecimal negAmount=BigDecimal.ZERO;
        BigDecimal taxAmount=BigDecimal.ZERO;
        for(GoldTaxCustomerInvoiceDetail detailItem:detailList){
            if(BigDecimal.ZERO.compareTo(detailItem.getTaxamount())<0){
                taxAmount=taxAmount.add(detailItem.getTaxamount());
            }
            if(BigDecimal.ZERO.compareTo(detailItem.getTaxamount())>0){
                negAmount=negAmount.add(detailItem.getTaxamount());
                if("1".equals(amountoptions)){
                    continue;
                }
            }

            Map itemMap= PropertyUtils.describe(detailItem);
            //税率
            if(detailItem.getTaxrate().compareTo(BigDecimal.ONE)>0) {
                itemMap.put("taxrate", detailItem.getTaxrate().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
            }
            if(StringUtils.isEmpty(detailItem.getGoodsid())){
                if(StringUtils.isNotEmpty(detailItem.getSourcegoodsid())){
                    itemMap.put("goodsid",detailItem.getSourcegoodsid());
                }else if(StringUtils.isNotEmpty(detailItem.getJstypeid())){
                    itemMap.put("jstypeid",detailItem.getJstypeid());
                }
            }

            itemMap.put("syyhzcbz","0");
            itemMap.put("lslbz","");
            itemMap.put("yhzcsm","");
            if(null!=detailItem.getTaxrate()) {
                if (BigDecimal.ZERO.compareTo(detailItem.getTaxrate()) == 0) {
                    itemMap.put("lslbz", "3");
                }
                if ("1".equals(detailItem.getTaxfreeflag())) {
                    itemMap.put("syyhzcbz", "1");
                    itemMap.put("lslbz", "1");
                    itemMap.put("yhzcsm", "免税");
                }
            }
            if (itemMap.containsKey("class")){
                itemMap.remove("class");
            }
            resultDetailList.add(itemMap);
        }
        Map billMap=PropertyUtils.describe(goldTaxCustomerInvoice);
        if(billMap.containsKey("class")){
            billMap.remove("class");
        }

        String tmpstr=null;
        tmpstr= MessageFormat.format("{0} {1}",
                ValueUtils.getValueOrEmpty(goldTaxCustomerInvoice.getCustomeraddr()),
                ValueUtils.getValueOrEmpty(goldTaxCustomerInvoice.getCustomerphone()));
        billMap.put("customeradrrphone", tmpstr);
        resultMap.put("goldTaxCustomerInvoiceBill", billMap);
        resultMap.put("detailList", resultDetailList);
        resultMap.put("flag",true);
        if(BigDecimal.ZERO.compareTo(negAmount)>0){
            String msg="生成金税导入文件时，单号“"+id+"”中负数金额：￥"+negAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
            resultMap.put("amountmsg",msg);
        }
        return resultMap;
    }
}

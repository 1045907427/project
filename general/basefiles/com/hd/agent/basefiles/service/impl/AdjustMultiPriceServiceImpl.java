package com.hd.agent.basefiles.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.AdjustMultiPriceMapper;
import com.hd.agent.basefiles.dao.GoodsMapper;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.IAdjustMultiPriceService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lin_xx on 2017/3/20.
 */
public class AdjustMultiPriceServiceImpl extends FilesLevelServiceImpl implements IAdjustMultiPriceService {

    private GoodsMapper goodsMapper;

    private BaseFilesServiceImpl baseFilesServiceImpl;

    private AdjustMultiPriceMapper adjustMultiPriceMapper;

    public GoodsMapper getGoodsMapper() {
        return goodsMapper;
    }

    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    public BaseFilesServiceImpl getBaseFilesServiceImpl() {
        return baseFilesServiceImpl;
    }

    public void setBaseFilesServiceImpl(BaseFilesServiceImpl baseFilesServiceImpl) {
        this.baseFilesServiceImpl = baseFilesServiceImpl;
    }

    public AdjustMultiPriceMapper getAdjustMultiPriceMapper() {
        return adjustMultiPriceMapper;
    }

    public void setAdjustMultiPriceMapper(AdjustMultiPriceMapper adjustMultiPriceMapper) {
        this.adjustMultiPriceMapper = adjustMultiPriceMapper;
    }

    @Override
    public PageData showAdjustMultiPriceList(PageMap pageMap) throws Exception {

        String dataSql = getDataAccessRule("t_delivery_aogorder", "t");
        pageMap.setDataSql(dataSql);
        List list = adjustMultiPriceMapper.getAdjustMultiPriceListData(pageMap);
        int count = adjustMultiPriceMapper.getAdjustMultiPriceListDataCount(pageMap);
        PageData pageData = new PageData(count,list,pageMap);
        return pageData;
    }

    @Override
    public AdjustMultiPrice getAdjustMultiPriceInfo(String id) throws Exception {
        AdjustMultiPrice adjustMultiPrice = adjustMultiPriceMapper.getAdjustMultiPriceById(id);
        List<AdjustMultiPriceDetail> detailList = adjustMultiPriceMapper.getMultiPriceDetailByBillid(id);
        for(AdjustMultiPriceDetail detail : detailList ){
            GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(detail.getGoodsid());
            if(null != goodsInfo){
                detail.setGoodsname(goodsInfo.getName());
                detail.setBarcode(goodsInfo.getBarcode());
            }
        }
        adjustMultiPrice.setAdjustMultiPriceDetailList(detailList);
        return adjustMultiPrice;
    }

    @Override
    public Map addAdjustMultiPriceInfo(AdjustMultiPrice adjustMultiPrice) throws Exception {
        Map map = new HashedMap();
        if (isAutoCreate("t_base_adjust_multiprice")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(adjustMultiPrice, "t_base_adjust_multiprice");
            adjustMultiPrice.setId(id);
        } else {
            adjustMultiPrice.setId("AMPD-" + CommonUtils.getDataNumberSendsWithRand());
        }
        adjustMultiPrice.setAddtime(new Date());
        adjustMultiPrice.setBusinessdate(CommonUtils.getTodayDataStr());
        SysUser sysUser = getSysUser();
        adjustMultiPrice.setAdddeptid(sysUser.getDepartmentid());
        adjustMultiPrice.setAdddeptname(sysUser.getDepartmentname());
        adjustMultiPrice.setAdduserid(sysUser.getUserid());
        adjustMultiPrice.setAddusername(sysUser.getName());
        List<AdjustMultiPriceDetail> detailList = adjustMultiPrice.getAdjustMultiPriceDetailList();
        for(AdjustMultiPriceDetail detail : detailList){
            detail.setBillid(adjustMultiPrice.getId());
            adjustMultiPriceMapper.addMultiPriceDetail(detail);
        }
        boolean flag = adjustMultiPriceMapper.addAdjustMultiPrice(adjustMultiPrice) > 0;
        map.put("flag",flag);
        map.put("id",adjustMultiPrice.getId());
        return map;
    }

    @Override
    public Map editAdjustMultiPriceInfo(AdjustMultiPrice adjustMultiPrice) throws Exception {
        List<AdjustMultiPriceDetail> detailList = adjustMultiPrice.getAdjustMultiPriceDetailList();
        adjustMultiPriceMapper.deleteMultiPriceDetailByBillid(adjustMultiPrice.getId());
        for(AdjustMultiPriceDetail detail : detailList){
            detail.setBillid(adjustMultiPrice.getId());
            adjustMultiPriceMapper.addMultiPriceDetail(detail);
        }
        SysUser sysUser = getSysUser();
        adjustMultiPrice.setModifyusername(sysUser.getName());
        adjustMultiPrice.setModifyuserid(sysUser.getUserid());
        adjustMultiPrice.setModifytime(new Date());
        boolean flag = adjustMultiPriceMapper.updateAdjustMultiPrice(adjustMultiPrice) > 0;
        Map map = new HashedMap();
        map.put("flag", flag);
        map.put("id", adjustMultiPrice.getId());
        return map;
    }

    @Override
    public PageData getAdjustMultiPriceGoodsByBrandAndSort(PageMap pageMap) throws Exception {
        Map condition = pageMap.getCondition();
        condition.put("nopackage","1");
        condition.put("state","1");
        if(StringUtils.isNotEmpty((String)condition.get("brandArr"))){
            String [] brands = ((String) condition.get("brandArr")).split(",");
            List brandList = new ArrayList();
            for (int i = 0; i < brands.length; i++) {
                brandList.add(brands[i]);
            }
            condition.put("brandArr",brandList);
        }
        if(StringUtils.isNotEmpty((String)condition.get("defaultsortArr"))){
            String [] sorts = ((String) condition.get("defaultsortArr")).split(",");
            List sortList = new ArrayList();
            for (int i = 0; i < sorts.length; i++) {
                sortList.add(sorts[i]);
            }
            condition.put("defaultsortArr",sortList);
        }
        pageMap.setCondition(condition);
        //String sql = getDataAccessRule("t_base_goods_info", null); //数据权限
        //pageMap.setDataSql(sql);
        List<GoodsInfo> goodsInfoList = goodsMapper.getGoodsInfoListByBrandAndSort(pageMap);
        List<AdjustMultiPriceDetail> priceDetailList = new ArrayList<AdjustMultiPriceDetail>();
        for(GoodsInfo goodsInfo : goodsInfoList){
            AdjustMultiPriceDetail adjustMultiPriceDetail = new AdjustMultiPriceDetail();
            adjustMultiPriceDetail.setGoodsid(goodsInfo.getId());
            adjustMultiPriceDetail.setGoodsname(goodsInfo.getName());
            adjustMultiPriceDetail.setBarcode(goodsInfo.getBarcode());
            adjustMultiPriceDetail.setOldbuyprice(goodsInfo.getHighestbuyprice());
            adjustMultiPriceDetail.setNewbuyprice(goodsInfo.getHighestbuyprice());
            adjustMultiPriceDetail.setOldsalesprice(goodsInfo.getBasesaleprice());
            adjustMultiPriceDetail.setNewsalesprice(goodsInfo.getBasesaleprice());

            List<GoodsInfo_PriceInfo> priceList = goodsMapper.getPriceListByGoodsid(goodsInfo.getId());
            for(GoodsInfo_PriceInfo priceInfo : priceList){
                if("1".equals(priceInfo.getCode())){
                    adjustMultiPriceDetail.setOldprice1(priceInfo.getTaxprice());
                    adjustMultiPriceDetail.setNewprice1(priceInfo.getTaxprice());
                }else if("2".equals(priceInfo.getCode())){
                    adjustMultiPriceDetail.setOldprice2(priceInfo.getTaxprice());
                    adjustMultiPriceDetail.setNewprice2(priceInfo.getTaxprice());
                }else if("3".equals(priceInfo.getCode())){
                    adjustMultiPriceDetail.setOldprice3(priceInfo.getTaxprice());
                    adjustMultiPriceDetail.setNewprice3(priceInfo.getTaxprice());
                }else if("4".equals(priceInfo.getCode())){
                    adjustMultiPriceDetail.setOldprice4(priceInfo.getTaxprice());
                    adjustMultiPriceDetail.setNewprice4(priceInfo.getTaxprice());
                }else if("5".equals(priceInfo.getCode())){
                    adjustMultiPriceDetail.setOldprice5(priceInfo.getTaxprice());
                    adjustMultiPriceDetail.setNewprice5(priceInfo.getTaxprice());
                }else if("6".equals(priceInfo.getCode())){
                    adjustMultiPriceDetail.setOldprice6(priceInfo.getTaxprice());
                    adjustMultiPriceDetail.setNewprice6(priceInfo.getTaxprice());
                }else if("7".equals(priceInfo.getCode())){
                    adjustMultiPriceDetail.setOldprice7(priceInfo.getTaxprice());
                    adjustMultiPriceDetail.setNewprice7(priceInfo.getTaxprice());
                }else if("8".equals(priceInfo.getCode())){
                    adjustMultiPriceDetail.setOldprice8(priceInfo.getTaxprice());
                    adjustMultiPriceDetail.setNewprice8(priceInfo.getTaxprice());
                }else if("9".equals(priceInfo.getCode())){
                    adjustMultiPriceDetail.setOldprice9(priceInfo.getTaxprice());
                    adjustMultiPriceDetail.setNewprice9(priceInfo.getTaxprice());
                }else if("10".equals(priceInfo.getCode())){
                    adjustMultiPriceDetail.setOldprice10(priceInfo.getTaxprice());
                    adjustMultiPriceDetail.setNewprice10(priceInfo.getTaxprice());
                }
            }

            priceDetailList.add(adjustMultiPriceDetail);
        }
        int count = goodsMapper.getGoodsInfoListByBrandAndSortCount(pageMap);
        PageData pageData = new PageData(count,priceDetailList,pageMap);
        return pageData;
    }

    @Override
    public boolean deleteAdjustMultiPrice(String id) throws Exception {
        AdjustMultiPrice adjustMultiPrice = adjustMultiPriceMapper.getAdjustMultiPriceById(id);
        if ("2".equals(adjustMultiPrice.getStatus())&&adjustMultiPrice!=null) {
            int i = adjustMultiPriceMapper.deleteAdjustMultiPriceById(id);
            if (i > 0) {
                adjustMultiPriceMapper.deleteMultiPriceDetailByBillid(id);
                return true;
            }
            else{
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Map auditAdjustMultiPrice(String id) throws Exception {
        Map map = new HashedMap();
        boolean flag ;
        String msg = "";
        SysUser sysUser = getSysUser();
        AdjustMultiPrice adjustMultiPrice = adjustMultiPriceMapper.getAdjustMultiPriceById(id);
        if(null != adjustMultiPrice && "2".equals(adjustMultiPrice.getStatus())){
            adjustMultiPrice.setStatus("3");
            adjustMultiPrice.setAuditusername(sysUser.getName());
            adjustMultiPrice.setAudituserid(sysUser.getUserid());
            adjustMultiPrice.setAudittime(new Date());
            flag = adjustMultiPriceMapper.updateAdjustMultiPrice(adjustMultiPrice) > 0;
            if(flag){
                List<AdjustMultiPriceDetail> detailList = adjustMultiPriceMapper.getMultiPriceDetailByBillid(id);
                for(AdjustMultiPriceDetail detail : detailList){
                    GoodsInfo goodsInfo = goodsMapper.getGoodsInfo(detail.getGoodsid());
                    goodsInfo.setOldId(goodsInfo.getId());
                    goodsInfo.setHighestbuyprice(detail.getNewbuyprice());
                    goodsInfo.setNewbuyprice(detail.getNewbuyprice());
                    goodsInfo.setBasesaleprice(detail.getNewsalesprice());
                    int count = goodsMapper.editGoodsInfo(goodsInfo);//更新商品档案的最高，最新采购价和基准销售价
                    if(null != detail.getNewprice1()){
                        int i = updateGoodsPrice(detail.getGoodsid(),detail.getNewprice1(),"1");
                        count = count + i ;
                    }
                    if(null != detail.getNewprice2()){
                        int i = updateGoodsPrice(detail.getGoodsid(),detail.getNewprice2(),"2");
                        count = count + i ;
                    }
                    if(null != detail.getNewprice3()){
                        int i = updateGoodsPrice(detail.getGoodsid(),detail.getNewprice3(),"3");
                        count = count + i ;
                    }
                    if(null != detail.getNewprice4()){
                        int i = updateGoodsPrice(detail.getGoodsid(),detail.getNewprice4(),"4");
                        count = count + i ;
                    }
                    if(null != detail.getNewprice5()){
                        int i = updateGoodsPrice(detail.getGoodsid(),detail.getNewprice5(),"5");
                        count = count + i ;
                    }
                    if(null != detail.getNewprice6()){
                        int i = updateGoodsPrice(detail.getGoodsid(),detail.getNewprice6(),"6");
                        count = count + i ;
                    }
                    if(null != detail.getNewprice7()){
                        int i = updateGoodsPrice(detail.getGoodsid(),detail.getNewprice7(),"7");
                        count = count + i ;
                    }
                    if(null != detail.getNewprice8()){
                        int i = updateGoodsPrice(detail.getGoodsid(),detail.getNewprice8(),"8");
                        count = count + i ;
                    }
                    if(null != detail.getNewprice9()){
                        int i = updateGoodsPrice(detail.getGoodsid(),detail.getNewprice9(),"9");
                        count = count + i ;
                    }
                    if(null != detail.getNewprice10()){
                        int i = updateGoodsPrice(detail.getGoodsid(),detail.getNewprice10(),"10");
                        count = count + i ;
                    }
                    if(count == 0){
                        flag = false;
                        oppAuditAdjustMultiPrice(id);
                        msg = "审核失败，修改价格出错！";
                    }
                }

            }
        }else{
            flag = false ;
            msg = "审核失败，请检查单据及其状态";
        }
        map.put("flag",flag);
        map.put("msg",msg);
        return map;
    }

     /**
      * 更改商品档案中某个价格套价格
      * @author lin_xx
      * @date 2017/3/22
      */
    public int updateGoodsPrice(String goodsid,BigDecimal newPrice, String priceType) throws Exception {
        GoodsInfo_PriceInfo goodsInfo_PriceInfo=goodsMapper.getPriceInfoByGoodsAndCode(goodsid, priceType);
        int i = 0 ;
        if(null!=goodsInfo_PriceInfo){
            goodsInfo_PriceInfo.setTaxprice(newPrice);
            TaxType taxType=getBaseFinanceMapper().getTaxTypeInfo(goodsInfo_PriceInfo.getTaxtype());
            if(null != taxType && BigDecimal.ZERO.compareTo(taxType.getRate()) != 0){
                BigDecimal price = newPrice.divide(taxType.getRate(),6);
                goodsInfo_PriceInfo.setPrice(price);//未税单价
            }
            BigDecimal boxprice = new BigDecimal(0);
            List<GoodsInfo_MteringUnitInfo> list = goodsMapper.getMUListByGoodsId(goodsid);
            if(list.size()>0){
                GoodsInfo_MteringUnitInfo goodsInfo_mteringUnitInfo = list.get(0);
                if(null != goodsInfo_mteringUnitInfo && null != goodsInfo_mteringUnitInfo.getRate()){
                    boxprice = newPrice.multiply(goodsInfo_mteringUnitInfo.getRate());
                }
            }
            goodsInfo_PriceInfo.setBoxprice(boxprice);
            i = goodsMapper.editPriceInfo(goodsInfo_PriceInfo);
        }
        return  i;
    }

    @Override
    public Map oppAuditAdjustMultiPrice(String id) throws Exception{
        Map map = new HashedMap();
        boolean flag ;
        String msg = "";
        AdjustMultiPrice adjustMultiPrice = adjustMultiPriceMapper.getAdjustMultiPriceById(id);
        SysUser sysUser = getSysUser();
        if(null != adjustMultiPrice && "3".equals(adjustMultiPrice.getStatus())){
            adjustMultiPrice.setStatus("2");
            adjustMultiPrice.setStopuserid(sysUser.getUserid());
            adjustMultiPrice.setStopusername(sysUser.getUsername());
            adjustMultiPrice.setStoptime(new Date());
            flag = adjustMultiPriceMapper.updateAdjustMultiPrice(adjustMultiPrice) > 0 ;
        }else{
            flag = false ;
            msg = "反审失败，请检查单据及其状态";
        }
        map.put("flag",flag);
        map.put("msg",msg);

        return map;
    }



}

package com.hd.agent.oa.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.service.IOrderService;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.dao.OaOffPriceMapper;
import com.hd.agent.oa.model.OaOffPrice;
import com.hd.agent.oa.model.OaOffPriceDetail;
import com.hd.agent.oa.service.IOaOffPriceService;

/**
 * Created by limin on 2015/3/9.
 */
public class OaOffPriceServiceImpl extends BaseFilesServiceImpl implements IOaOffPriceService {

    private OaOffPriceMapper priceMapper;

    private IOrderService salesOrderService;

    public OaOffPriceMapper getPriceMapper() {
        return priceMapper;
    }

    public void setPriceMapper(OaOffPriceMapper priceMapper) {
        this.priceMapper = priceMapper;
    }

    public IOrderService getSalesOrderService() {
        return salesOrderService;
    }

    public void setSalesOrderService(IOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @Override
    public PageData selectOaOffPriceDetailList(PageMap map) throws Exception {

        List list = priceMapper.selectOaOffPriceDetailList(map);
        int cnt = priceMapper.selectOaOffPriceDetailListCnt(map);
        return new PageData(cnt, list, map);
    }

    @Override
    public int addOaOffPrice(OaOffPrice price, List<OaOffPriceDetail> list) {

        int ret = priceMapper.insertOaOffPrice(price);
        if(ret > 0) {

            for(OaOffPriceDetail detail : list) {

                if(StringUtils.isEmpty(detail.getGoodsid())) {
                    continue;
                }

                detail.setBillid(price.getId());
                priceMapper.insertOaOffPriceDetail(detail);
            }

        }

        return ret;
    }

    @Override
    public int editOaOffPrice(OaOffPrice price, List<OaOffPriceDetail> list) {

        int ret = priceMapper.updateOaOffPrice(price);
        if(ret > 0) {

            priceMapper.deleteOaOffPriceDetailByBillid(price.getId());
            for(OaOffPriceDetail detail : list) {

                if(StringUtils.isEmpty(detail.getGoodsid())) {
                    continue;
                }

                detail.setBillid(price.getId());
                priceMapper.insertOaOffPriceDetail(detail);
            }
        }

        return ret;
    }

    @Override
    public OaOffPrice selectOaOffPrice(String id) throws Exception {
        return priceMapper.selectOaOffPrice(id);
    }

    @Override
    public List<OaOffPriceDetail> selectOaOffPriceDetailListByBillid(String billid) throws Exception {
        return priceMapper.selectOaOffPriceDetailListByBillid(billid);
    }

    @Override
    public PageData getGoodsList(PageMap pageMap) throws Exception {

        String sql = getDataAccessRule("t_base_goods_info", null); //数据权限
        pageMap.setDataSql(sql);

        List<GoodsInfo> GoodsInfoList = getBaseFilesGoodsMapper().getGoodsInfoListByBrandAndSort(pageMap);
        List<Map> list = new ArrayList<Map>();
        String customerid = (String) pageMap.getCondition().get("customerid");
        for(GoodsInfo goodsInfo : GoodsInfoList){

            Map map = new HashMap();
            map.put("goodsid", goodsInfo.getId());
            map.put("goodsname", goodsInfo.getName());
            map.put("barcode", goodsInfo.getBarcode());
            BigDecimal price = getGoodsPriceByCustomer(goodsInfo.getId(),customerid);
            map.put("oldprice", price);

            BigDecimal buyprice = goodsInfo.getHighestbuyprice();
            if(goodsInfo.getCostaccountprice() != null && BigDecimal.ZERO.compareTo(goodsInfo.getCostaccountprice()) < 0) {
                buyprice = goodsInfo.getCostaccountprice();
            }
            map.put("buyprice", buyprice);
            list.add(map);
        }
        int count = getBaseFilesGoodsMapper().getGoodsInfoListByBrandAndSortCount(pageMap);
        return new PageData(count,list,pageMap);
    }
}

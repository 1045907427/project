package com.hd.agent.oa.service.impl;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.dao.OaGoodsPriceMapper;
import com.hd.agent.oa.model.OaGoodsPrice;
import com.hd.agent.oa.model.OaGoodsPriceDetail;
import com.hd.agent.oa.service.IOaGoodsPriceService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by limin on 2015/5/27.
 */
public class OaGoodsPriceServiceImpl extends BaseFilesServiceImpl implements IOaGoodsPriceService {

    private OaGoodsPriceMapper priceMapper;

    public OaGoodsPriceMapper getPriceMapper() {
        return priceMapper;
    }

    public void setPriceMapper(OaGoodsPriceMapper priceMapper) {
        this.priceMapper = priceMapper;
    }


    @Override
    public int insertOaGoodsPrice(OaGoodsPrice price, List list) throws Exception {
        int a = priceMapper.insertOaGoodsPrice(price);
        for(int i = 0 ;i<list.size();i++){
            OaGoodsPriceDetail detail = (OaGoodsPriceDetail)list.get(i);

            if(StringUtils.isEmpty(detail.getGoodsid())) {

                continue;
            }

            detail.setBillid(price.getId());
            priceMapper.insertOaGoodsPriceDetail(detail);
        }
        return a;
    }

    @Override
    public OaGoodsPrice selectOaGoodsPrice(String id) throws Exception {

        return  priceMapper.selectOaGoodsPrice(id);
    }

    @Override
    public PageData selectGoodsDetailList(PageMap pageMap) throws Exception {

        List list = priceMapper.selectGoodsPriceDetailList(pageMap);
        return new PageData(priceMapper.selectGoodsPriceDetailListCount(pageMap), list, pageMap);
    }

    @Override
    public int editOaGoodsPrice(OaGoodsPrice price, List list) throws Exception {

        int index = priceMapper.updateOaGoodsPrice(price);

        priceMapper.deleteOaGoodsDetailByBillid(price.getId());

        for(Object o :list){
            OaGoodsPriceDetail detail = (OaGoodsPriceDetail) o ;
            if(StringUtils.isEmpty(detail.getGoodsid())) {
                continue;
            }
            detail.setBillid(price.getId());

            priceMapper.insertOaGoodsPriceDetail(detail);
        }

        return index;
    }

    @Override
    public List<OaGoodsPriceDetail> selectOaGoodsPriceDetailListByBillid(String billid) {
        return priceMapper.selectOaGoodsPriceDetailListByBillid(billid);
    }
}

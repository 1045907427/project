package com.hd.agent.purchase.service.impl.ext;

import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.purchase.dao.ArrivalOrderMapper;
import com.hd.agent.purchase.service.ext.IPurchaseForSalesService;

import java.util.Map;

/**
 * Created by limin on 2016/10/28.
 */
public class PurchaseForSalesServiceImpl extends BaseFilesServiceImpl implements IPurchaseForSalesService {

    private ArrivalOrderMapper arrivalOrderMapper;

    public ArrivalOrderMapper getArrivalOrderMapper() {
        return arrivalOrderMapper;
    }

    public void setArrivalOrderMapper(ArrivalOrderMapper arrivalOrderMapper) {
        this.arrivalOrderMapper = arrivalOrderMapper;
    }

    @Override
    public Map selectLastestBuyPriceByGoodsAndBatchno(String goodsid, String batchno) {
        return arrivalOrderMapper.selectLastestBuyPriceByGoodsAndBatchno(goodsid, batchno);
    }
}

/**
 * @(#)BaseOaServiceImpl.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-1-28 limin 创建版本
 */
package com.hd.agent.oa.service.impl;

import com.hd.agent.basefiles.model.CustomerPrice;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.GoodsInfo_PriceInfo;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.oa.service.IBaseOaService;
import com.hd.agent.sales.model.OrderDetail;

import java.math.BigDecimal;

/**
 * OA通用Service 实现类
 *
 * @author limin
 */
public class BaseOaServiceImpl extends BaseFilesServiceImpl implements IBaseOaService {

    @Override
    public OrderDetail getGoodsPrice(String customerId, String goodsId) throws Exception {

        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsId); //商品信息
        OrderDetail orderDetail = new OrderDetail();

        if(goodsInfo != null){
            BigDecimal rate = new BigDecimal(1);
            TaxType taxType = getTaxType(goodsInfo.getDefaulttaxtype()); //获取默认税种
            if(taxType != null){
                orderDetail.setTaxtype(taxType.getId()); //税种档案中的编码
                orderDetail.setTaxtypename(taxType.getName()); //税种名称
                rate = taxType.getRate().divide(new BigDecimal(100));
            }

            CustomerPrice customerPrice = getCustomerPrice(customerId, goodsId);
            if(customerPrice != null){ //取合同价
                BigDecimal customerTaxPrice = customerPrice.getPrice();
                if(customerTaxPrice == null){
                    customerTaxPrice = new BigDecimal(0);
                }
                orderDetail.setFixprice(customerTaxPrice);
                orderDetail.setTaxprice(customerTaxPrice);
                orderDetail.setNotaxprice(customerTaxPrice.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP));
                orderDetail.setRemark("");
            }
            else{
                GoodsInfo_PriceInfo priceInfo = getPriceInfo(goodsId, customerId); //客户的价格套信息
                if(priceInfo != null){ //如果客户设置了价格套信息，则取价格套信息中的价格
                    orderDetail.setFixprice(priceInfo.getTaxprice());
                    orderDetail.setTaxprice(priceInfo.getTaxprice()); //从价格套中取含税价格
                    orderDetail.setNotaxprice(priceInfo.getTaxprice().divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP)); //从价格套中取无税价格
                    orderDetail.setRemark("");
                }
                else{
                    BigDecimal baseTaxPrice = goodsInfo.getBasesaleprice(); //取基准销售价
                    if(baseTaxPrice == null){
                        baseTaxPrice = new BigDecimal(0);
                    }
                    orderDetail.setFixprice(baseTaxPrice);
                    orderDetail.setTaxprice(baseTaxPrice);
                    orderDetail.setNotaxprice(baseTaxPrice.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP));
                    orderDetail.setRemark("");
                }
            }

        }
        return orderDetail;
    }
}

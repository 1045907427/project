package com.hd.agent.basefiles.service;

import com.hd.agent.basefiles.model.AdjustMultiPrice;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.Map;

/**
 * Created by lin_xx on 2017/3/20.
 */
public interface IAdjustMultiPriceService {

     /**
      * 查询商品多价调整单数据
      * @author lin_xx
      * @date 2017/3/20
      */
     public PageData showAdjustMultiPriceList(PageMap pageMap) throws Exception ;

      /**
       * 根据编号获取商品多价调整单明细
       * @author lin_xx
       * @date 2017/3/21
       */
      public AdjustMultiPrice getAdjustMultiPriceInfo(String id) throws Exception ;
     /**
      * 新增商品多价调整单
      * @author lin_xx
      * @date 2017/3/21
      */
      public Map addAdjustMultiPriceInfo(AdjustMultiPrice adjustMultiPrice) throws Exception ;
    /**
     * 修改商品多价调整单
     * @author lin_xx
     * @date 2017/3/21
     */
      public Map editAdjustMultiPriceInfo(AdjustMultiPrice adjustMultiPrice) throws Exception ;
     /**
      * 批量添加时查询对应条件的商品
      * @author lin_xx
      * @date 2017/3/23
      */
     public PageData getAdjustMultiPriceGoodsByBrandAndSort(PageMap pageMap) throws Exception ;
     /**
      * 批量删除多价调整单
      * @author lin_xx
      * @date 2017/3/24
      */
     public boolean deleteAdjustMultiPrice(String id) throws Exception;
       /**
        * 审核商品多价调整单
        * @author lin_xx
        * @date 2017/3/21
        */
       public Map auditAdjustMultiPrice(String id) throws Exception ;
         /**
          * 反审商品多价调整单
          * @author lin_xx
          * @date 2017/3/22
          */
       public Map oppAuditAdjustMultiPrice(String id) throws Exception ;


}

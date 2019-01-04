package com.hd.agent.basefiles.action;

import com.hd.agent.basefiles.model.AdjustMultiPrice;
import com.hd.agent.basefiles.model.AdjustMultiPriceDetail;
import com.hd.agent.basefiles.model.GoodsInfo_PriceInfo;
import com.hd.agent.basefiles.service.IAdjustMultiPriceService;
import com.hd.agent.basefiles.service.IGoodsService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lin_xx on 2017/3/20.
 */
public class AdjustMultiPriceAction extends FilesLevelAction {

    private AdjustMultiPrice adjustMultiPrice;

    private IAdjustMultiPriceService adjustMultiPriceService;

    private IGoodsService goodsService;

    public AdjustMultiPrice getAdjustMultiPrice() {
        return adjustMultiPrice;
    }

    public void setAdjustMultiPrice(AdjustMultiPrice adjustMultiPrice) {
        this.adjustMultiPrice = adjustMultiPrice;
    }

    public IAdjustMultiPriceService getAdjustMultiPriceService() {
        return adjustMultiPriceService;
    }

    public void setAdjustMultiPriceService(IAdjustMultiPriceService adjustMultiPriceService) {
        this.adjustMultiPriceService = adjustMultiPriceService;
    }

    public IGoodsService getGoodsService() {
        return goodsService;
    }

    public void setGoodsService(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }

     /**
      * 显示商品多价调整单页面
      * @author lin_xx
      * @date 2017/3/20
      */
     public String showAdjustMultiPriceListPage() throws Exception {
         return SUCCESS;
     }

      /**
       * 显示商品多价调整单数据
       * @author lin_xx
       * @date 2017/3/20
       */
      public String showAdjustMultiPriceData() throws Exception {
          // 获取页面传过来的参数 封装到map里面
          Map map = CommonUtils.changeMap(request.getParameterMap());
          // map赋值到pageMap中作为查询条件
          pageMap.setCondition(map);
          PageData pageData = adjustMultiPriceService.showAdjustMultiPriceList(pageMap);
          addJSONObject(pageData);
          return SUCCESS;
      }

       /**
        * 全局导出 多价调整单商品明细
        * @author lin_xx
        * @date 2017/3/24
        */
       public void exportAdjustMultiPriceDetail() throws Exception{
           Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
           String id = (String) map.get("param");
           AdjustMultiPrice adjustMultiPrice = adjustMultiPriceService.getAdjustMultiPriceInfo(id);
           List list = adjustMultiPrice.getAdjustMultiPriceDetailList();
           ExcelUtils.exportAnalysExcel(map,list);
       }

       /**
        * 获取启用的价格套
        * @author lin_xx
        * @date 2017/3/23
        */
       public Map getOpenPriceList() throws Exception{
           Map colMap = new HashedMap();
           List<SysCode> list = getGoodsPriceList();
           for(SysCode sysCode : list){
               colMap.put("price"+sysCode.getCode(),sysCode.getCode());
           }
           return colMap;
       }

     /**
     * 显示商品多价调整单新增页面
     * @author lin_xx
     * @date 2017/3/20
     */
      public String showAdjustMultiPriceAddPage() throws Exception {
          request.setAttribute("type", "add");
          Map colMap = getOpenPriceList();
          request.setAttribute("colMap",colMap);
          return SUCCESS;
      }

    /**
     * 显示商品多价调整单修改页面
     * @author lin_xx
     * @date 2017/3/20
     */
    public String showAdjustMultiPriceEditPage() throws Exception {
        request.setAttribute("type", "edit");
        Map colMap = getOpenPriceList();
        request.setAttribute("colMap",colMap);
        request.setAttribute("id",request.getParameter("id"));
        return SUCCESS;
    }

     /**
      * 商品多价调整单新增页面
      * @author lin_xx
      * @date 2017/3/20
      */
    public String adjustMultiPriceAddPage() throws Exception {
        String date = CommonUtils.getTodayDataStr();
        request.setAttribute("date", date);
        return SUCCESS;
    }

    /**
     * 商品多价调整单修改页面
     * @author lin_xx
     * @date 2017/3/20
     */
    public String adjustMultiPriceEditPage() throws Exception {
        String id = request.getParameter("id");
        AdjustMultiPrice adjustMultiPrice = adjustMultiPriceService.getAdjustMultiPriceInfo(id);
        if("2".equals(adjustMultiPrice.getStatus())){
            String jsonStr = JSONUtils.listToJsonStr(adjustMultiPrice.getAdjustMultiPriceDetailList());
            List statusList = getBaseSysCodeService().showSysCodeListByType("status");
            request.setAttribute("statusList", statusList);
            request.setAttribute("adjustMultiPrice", adjustMultiPrice);
            request.setAttribute("detailList", jsonStr);
            return SUCCESS;
        }else{
            String jsonStr = JSONUtils.listToJsonStr(adjustMultiPrice.getAdjustMultiPriceDetailList());
            List statusList = getBaseSysCodeService().showSysCodeListByType("status");
            request.setAttribute("statusList", statusList);
            request.setAttribute("adjustMultiPrice", adjustMultiPrice);
            request.setAttribute("detailList", jsonStr);
            return "viewsuccess";
        }

    }

    /**
     * 商品多价调整单明细新增页面
     * @author lin_xx
     * @date 2017/3/20
     */
    public String showAdjustMultiPriceDetailAddPage() throws Exception {
        Map colMap = getOpenPriceList();
        request.setAttribute("colMap",colMap);
        return SUCCESS;
    }

    /**
     * 商品多价调整单明细修改页面
     * @author lin_xx
     * @date 2017/3/20
     */
    public String showAdjustMultiPriceDetailEditPage() throws Exception {
        Map colMap = getOpenPriceList();
        request.setAttribute("colMap",colMap);

        return SUCCESS;
    }

     /**
      * 根据商品编号获取她启用的所有价格套价格
      * @author lin_xx
      * @date 2017/3/21
      */
    public String getPriceDataByGoodsidAndOpenCode() throws Exception {
        String goodsid = request.getParameter("goodsid");
        List<SysCode> list = getGoodsPriceList();
        Map map = new HashedMap();
        for(SysCode sysCode : list){
            GoodsInfo_PriceInfo goodsPrice = goodsService.getPriceDataByGoodsidAndCode(goodsid, sysCode.getCode());
            if(null == goodsPrice){
                map.put(sysCode.getCode(),0);
            }else{
                map.put(sysCode.getCode(),goodsPrice.getTaxprice());
            }
        }
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 新增多价调整单
     * @author lin_xx
     * @date 2017/3/21
     */
    @UserOperateLog(key = "adjustMultiPrice", type = 2)
    public String addAdjustMultiPriceSave() throws Exception {

        adjustMultiPrice.setStatus("2");
        String detailJson = request.getParameter("detailJson");
        List detailList = JSONUtils.jsonStrToList(detailJson, new AdjustMultiPriceDetail());
        adjustMultiPrice.setAdjustMultiPriceDetailList(detailList);
        Map map = adjustMultiPriceService.addAdjustMultiPriceInfo(adjustMultiPrice);
        String id = null != map.get("id") ? (String) map.get("id") : "";
        String saveaudit = request.getParameter("saveaudit");
        boolean flag = (Boolean) map.get("flag");
        String msg = "";
        if (flag && "saveaudit".equals(saveaudit)) {
            Map returnmap = adjustMultiPriceService.auditAdjustMultiPrice(adjustMultiPrice.getId());
            map.put("auditflag", returnmap.get("flag"));
            msg = (String) returnmap.get("msg");
            map.put("msg", msg);
            addLog("商品多价调整单保存审核 编号：" + id, returnmap);
        } else {
            addLog("商品多价调整单新增编号：" + id, flag);
        }
        map.put("msg",msg);
        addJSONObject(map);
        return SUCCESS;
    }

     /**
      * 修改多价调整单
      * @author lin_xx
      * @date 2017/3/21
      */
     @UserOperateLog(key = "adjustMultiPrice", type = 3)
     public String editAdjustMultiPriceSave() throws Exception {
         // 加锁
         boolean lock = isLock("t_base_adjust_multiprice", adjustMultiPrice.getId());
         if (lock) { // 被锁定不能进行修改
             addJSONObject("lock", true);
             addLog("商品多价调整单 编码：" + adjustMultiPrice.getId() + "互斥，操作", false);
             return "success";
         }
         String detailJson = request.getParameter("detailJson");
         List detailList = JSONUtils.jsonStrToList(detailJson, new AdjustMultiPriceDetail());
         adjustMultiPrice.setAdjustMultiPriceDetailList(detailList);
         Map map = adjustMultiPriceService.editAdjustMultiPriceInfo(adjustMultiPrice);
         String id = null != map.get("id") ? (String) map.get("id") : "";
         String saveaudit = request.getParameter("saveaudit");
         boolean flag = (Boolean) map.get("flag");
         if (flag && "saveaudit".equals(saveaudit)) {
             Map returnmap = adjustMultiPriceService.auditAdjustMultiPrice(adjustMultiPrice.getId());
             map.put("auditflag", returnmap.get("flag"));
             String msg = (String) returnmap.get("msg");
             map.put("msg", msg);
             addLog("商品多价调整单保存审核 编号：" + id, returnmap);
         } else {
             addLog("商品多价调整单保存编号：" + id, flag);
         }
         addJSONObject(map);
         // 解锁数据
         isLockEdit("t_base_adjust_multiprice", adjustMultiPrice.getId()); // 判断锁定并解锁

         return SUCCESS;
     }
      /**
       * 批量添加商品
       * @author lin_xx
       * @date 2017/3/22
       */
      public String adjustMultiPriceAddByBrandAndSortPage() throws Exception{
          Map colMap = getOpenPriceList();
          request.setAttribute("colMap",colMap);

          return SUCCESS;
      }

      /**
       * 根据条件查询批量添加的商品
       * @author lin_xx
       * @date 2017/3/22
       */
      public String getAdjustMultiPriceGoodsByBrandAndSort() throws Exception{
          // 获取页面传过来的参数 封装到map里面
          Map map = CommonUtils.changeMap(request.getParameterMap());
          // map赋值到pageMap中作为查询条件
          pageMap.setCondition(map);
          PageData pageData = adjustMultiPriceService.getAdjustMultiPriceGoodsByBrandAndSort(pageMap);
          addJSONObject(pageData);
          return  SUCCESS;
      }

       /**
        * 批量删除
        * @author lin_xx
        * @date 2017/3/24
        */
       @UserOperateLog(key = "adjustMultiPrice", type = 4)
       public String deletesAdjustMultiPrice() throws Exception{
           String msg = "", fmsg = "", smsg = "";
           int fnum = 0, snum = 0;
           boolean flag = false;
           String ids = request.getParameter("ids");
           String[] idArr = ids.split(",");
           for (String id : idArr) {
               if (isLock("t_base_adjust_multiprice", id)) {
                   if (StringUtils.isEmpty(fmsg)) {
                       fmsg = id;
                   } else {
                       fmsg += "," + id;
                   }
                   fnum++;
               } else {
                   boolean flag1 = adjustMultiPriceService.deleteAdjustMultiPrice(id);
                   if (StringUtils.isEmpty(smsg) && flag1) {
                       smsg = id;
                   } else {
                       smsg += "," + id;
                   }
                   snum++;
                   addLog("商品多价调整单删除编号：" + id, flag1);
               }
           }
           if (fnum != 0) {
               msg = "编号：" + fmsg + "被上锁，删除失败。共" + fnum + "条数据<br>";
           }
           if (snum != 0) {
               flag = true;
               if (StringUtils.isEmpty(msg)){
                   msg = "编号：" + smsg + "删除成功。共" + snum + "条数据<br>";
               }else{
                   msg += "编号：" + smsg + "删除成功。共" + snum + "条数据<br>";
               }
           }
           Map returnMap = new HashMap();
           returnMap.put("msg", msg);
           returnMap.put("flag",flag);
           addJSONObject(returnMap);

           return SUCCESS;
       }

        /**
         * 批量审核
         * @author lin_xx
         * @date 2017/3/24
         */
        @UserOperateLog(key = "adjustMultiPrice", type = 3)
        public String auditsAdjustMultiPrice() throws Exception {

            String msg = "", fmsg = "", smsg = "";
            int fnum = 0, snum = 0;
            boolean flag = false ;
            String ids = request.getParameter("ids");
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                if (isLock("t_base_adjust_price", id)) {
                    if (StringUtils.isEmpty(fmsg)) {
                        fmsg = id;
                    } else {
                        fmsg += "," + id;
                    }
                    fnum++;
                } else {
                    Map map = adjustMultiPriceService.auditAdjustMultiPrice(id);
                    flag = (Boolean) map.get("flag");
                    if (StringUtils.isEmpty(smsg) && flag) {
                        smsg = id;
                    } else {
                        smsg += "," + id;
                    }
                    snum++;
                    addLog("商品多价调整单审核编号：" + id, map);
                }
            }
            if (fnum != 0) {

                msg = "编号：" + fmsg + "被上锁，审核失败。共" + fnum + "条数据<br>";

            }
            if (snum != 0) {
                if (StringUtils.isEmpty(msg))
                    msg = "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
                else
                    msg += "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
            }

            Map returnMap = new HashMap();
            returnMap.put("msg", msg);
            returnMap.put("flag",flag);
            if(!ids.contains(",")){
                returnMap.put("id",ids);
            }
            addJSONObject(returnMap);
            return SUCCESS;
        }




}

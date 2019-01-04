package com.hd.agent.basefiles.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.GoodsMapper;
import com.hd.agent.basefiles.dao.JsTaxTypeCodeBarcodeMapper;
import com.hd.agent.basefiles.dao.JsTaxTypeCodeMapper;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.JsTaxTypeCode;
import com.hd.agent.basefiles.model.JsTaxTypeCodeBarcode;
import com.hd.agent.basefiles.service.IHtGoldTaxSystemService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class HtGoldTaxSystemServiceImpl extends BaseServiceImpl implements IHtGoldTaxSystemService {
    private JsTaxTypeCodeMapper jsTaxTypeCodeMapper;

    public JsTaxTypeCodeMapper getJsTaxTypeCodeMapper() {
        return jsTaxTypeCodeMapper;
    }

    public void setJsTaxTypeCodeMapper(JsTaxTypeCodeMapper jsTaxTypeCodeMapper) {
        this.jsTaxTypeCodeMapper = jsTaxTypeCodeMapper;
    }

    private JsTaxTypeCodeBarcodeMapper jsTaxTypeCodeBarcodeMapper;

    public JsTaxTypeCodeBarcodeMapper getJsTaxTypeCodeBarcodeMapper() {
        return jsTaxTypeCodeBarcodeMapper;
    }

    public void setJsTaxTypeCodeBarcodeMapper(JsTaxTypeCodeBarcodeMapper jsTaxTypeCodeBarcodeMapper) {
        this.jsTaxTypeCodeBarcodeMapper = jsTaxTypeCodeBarcodeMapper;
    }

    private GoodsMapper goodsMapper;

    public GoodsMapper getGoodsMapper() {
        return goodsMapper;
    }

    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public PageData getJsTaxTypeCodePageListData(PageMap pageMap) throws Exception {
        List<JsTaxTypeCode> list=jsTaxTypeCodeMapper.getJsTaxTypeCodePageList(pageMap);
        int itotal=jsTaxTypeCodeMapper.getJsTaxTypeCodePageCount(pageMap);
        PageData pageData= new PageData(itotal,list,pageMap);
        return pageData;
    }

    @Override
    public Map addJsTaxTypeCode(JsTaxTypeCode jsTaxTypeCode) throws Exception {
        jsTaxTypeCode.setMergeid(StringUtils.rightPad(jsTaxTypeCode.getId(),19,"0"));
        boolean isok=jsTaxTypeCodeMapper.isUsedJsTaxTypeCodeById(jsTaxTypeCode.getId())>0;
        Map resultMap=new HashMap();
        if(isok) {
            resultMap.put("flag", false);
            resultMap.put("msg", "您输入的编码已经存在");
            return resultMap;
        }
        SysUser sysUser=getSysUser();
        jsTaxTypeCode.setAdduserid(sysUser.getUserid());
        jsTaxTypeCode.setAddusername(sysUser.getName());
        jsTaxTypeCode.setAddtime(new Date());
        isok= jsTaxTypeCodeMapper.insertJsTaxTypeCode(jsTaxTypeCode)>0;
        resultMap.put("flag",isok);
        return resultMap;
    }

    @Override
    public boolean editJsTaxTypeCode(JsTaxTypeCode jsTaxTypeCode) throws Exception {

        SysUser sysUser=getSysUser();
        jsTaxTypeCode.setModifyuserid(sysUser.getUserid());
        jsTaxTypeCode.setModifyusername(sysUser.getName());
        jsTaxTypeCode.setModifytime(new Date());
        return jsTaxTypeCodeMapper.updateJsTaxTypeCode(jsTaxTypeCode) > 0;
    }
    @Override
    public Map deleteJsTaxTypeCode(String id) throws Exception {
        Map resultMap=new HashMap();
        Map queryMap=new HashMap();
        queryMap.put("jstaxsortid",id);
        List<GoodsInfo> goodsInfoList=goodsMapper.getGoodsInfoListByMap(queryMap);
        StringBuilder goodsidBuffer=new StringBuilder();
        Date operDate=new Date();
        SysUser sysUser=getSysUser();

        boolean isok= jsTaxTypeCodeMapper.deleteJsTaxTypeCodeById(id)>0;
        if(isok) {
            jsTaxTypeCodeBarcodeMapper.deleteJsTaxTypeCodeBarcodeByJsId(id);
            for (GoodsInfo goodsInfo : goodsInfoList) {
                GoodsInfo updateGoodsInfo = new GoodsInfo();
                updateGoodsInfo.setId(goodsInfo.getId());
                updateGoodsInfo.setJstaxsortid("");
                updateGoodsInfo.setJsgoodsmodifytime(operDate);
                updateGoodsInfo.setJsgoodsmodifyuserid(sysUser.getUserid());
                updateGoodsInfo.setJsgoodsmodifyusername(sysUser.getName());
               if(goodsMapper.updateGoodsInfoForJS(updateGoodsInfo)>0){
                   if (goodsidBuffer.length() > 0) {
                       goodsidBuffer.append(",");
                       goodsidBuffer.append(goodsInfo.getId());
                   }
               }
            }
        }
        resultMap.put("flag",isok);
        resultMap.put("modifygoodsid",goodsidBuffer.toString());
        return resultMap;
    }
    @Override
    public JsTaxTypeCode getJsTaxTypeCodeById(String id) throws Exception {
        return jsTaxTypeCodeMapper.getJsTaxTypeCodeById(id);
    }
    @Override
    public boolean isUsedJsTaxTypeCodeById(String id) throws Exception{
        return jsTaxTypeCodeMapper.isUsedJsTaxTypeCodeById(id)>0;
    }

    @Override
    public Map importJsTaxTypeCodeData(List<Map<String,Object>> dataList,Map optionMap) throws Exception{
        Map resultMap=new HashMap();
        if(null==dataList || dataList.size()==0){
            resultMap.put("flag",false);
            resultMap.put("msg","导入的数据为空或格式不匹配");
            return resultMap;
        }
        List<JsTaxTypeCode> importDataList=new ArrayList<JsTaxTypeCode>();
        List<Map<String,Object>> errorList=new ArrayList<Map<String, Object>>();
        Date nowDate=new Date();
        SysUser sysUser=getSysUser();
        StringBuilder upLogsb=new StringBuilder();
        String importversion=(String)optionMap.get("importversion");
        String existsidtip=(String)optionMap.get("existsidtip");
        for(Map itemData : dataList){
            String id="";
            if("2".equals(importversion)){
                String idpart = (String)itemData.get("idpart");
                String idcategory = (String) itemData.get("idcategory");
                String idchapter = (String) itemData.get("idchapter");
                String idsection = (String) itemData.get("idsection");
                String idarticle = (String) itemData.get("idarticle");
                String idparagraph = (String) itemData.get("idparagraph");
                String idsubparagraph = (String) itemData.get("idsubparagraph");
                String iditem = (String) itemData.get("iditem");
                String idsubitem = (String) itemData.get("idsubitem");
                String iddetailitem = (String) itemData.get("iddetailitem");

                if(null!=idpart){
                    id=id+idpart;
                }
                if(null!=idcategory){
                    id=id+idcategory;
                }
                if(null!=idchapter){
                    id=id+idchapter;
                }
                if(null!=idsection){
                    id=id+idsection;
                }
                if(null!=idarticle){
                    id=id+idarticle;
                }
                if(null!=idparagraph){
                    id=id+idparagraph;
                }
                if(null!=idsubparagraph){
                    id=id+idsubparagraph;
                }
                if(null!=iditem){
                    id=id+iditem;
                }
                if(null!=idsubitem){
                    id=id+idsubitem;
                }
                if(null!=iddetailitem){
                    id=id+iddetailitem;
                }

                if(null==id || "".equals(id.trim())){
                    itemData.put("errormessage","篇、类、章、节、条、款、项、目、子目、细目不能全为空");
                    errorList.add(itemData);
                    continue;
                }
            }else{
                id=(String)itemData.get("id");
            }
            if(null==id || "".equals(id.trim())){
                itemData.put("errormessage","编码不能为空");
                errorList.add(itemData);
                continue;
            }
            String goodsname=(String)itemData.get("goodsname");
            if(null==goodsname || "".equals(goodsname.trim())){
                itemData.put("errormessage","货物和劳务名称不能为空");
                errorList.add(itemData);
                continue;
            }

            boolean existsid=jsTaxTypeCodeMapper.isUsedJsTaxTypeCodeById(id)>0;

            if(!existsid) {
                JsTaxTypeCode jsTaxTypeCode = new JsTaxTypeCode();
                jsTaxTypeCode.setId(id);
                jsTaxTypeCode.setMergeid(StringUtils.rightPad(id, 19, '0'));
                jsTaxTypeCode.setGoodsname(goodsname);
                jsTaxTypeCode.setDescription((String) itemData.get("description"));
                jsTaxTypeCode.setKeyword((String) itemData.get("keyword"));
                jsTaxTypeCode.setAddtime(nowDate);
                jsTaxTypeCode.setAdduserid(sysUser.getUserid());
                jsTaxTypeCode.setAddusername(sysUser.getName());
                importDataList.add(jsTaxTypeCode);
            }else{
                if("1".equals(existsidtip)){
                    itemData.put("errormessage","编码：“"+id+"”已经存在");
                    errorList.add(itemData);
                    continue;
                }
            }
        }
        if(importDataList.size()==0){
            resultMap.put("flag",false);
            resultMap.put("errorDataList",errorList);
            resultMap.put("msg","传入数据"+dataList.size()+"条,但系统未找到相关符合导入数据");
            return resultMap;
        }
        //批量更新条数
        int updateDBCount=300;
        int listLen = importDataList.size();
        boolean canInsert = listLen > 0;
        int ipage = 0;
        //数据小于等于300条时，直接插入
        boolean isimport=false;
        if(listLen<=updateDBCount) {
            isimport=jsTaxTypeCodeMapper.insertJsTaxTypeCodeBatch(importDataList)>0;
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
                List<JsTaxTypeCode> tmpList = importDataList.subList(ipage * updateDBCount, row);
                isimport=jsTaxTypeCodeMapper.insertJsTaxTypeCodeBatch(tmpList)>0 || isimport;
                ipage = ipage + 1;
            }
        }
        if(isimport) {
            resultMap.put("flag", true);
            resultMap.put("msg", "金税税收分类导入成功");
        }else{
            resultMap.put("flag",false);
            resultMap.put("msg","金税税收分类导入未成功");
        }
        resultMap.put("errorDataList",errorList);
        resultMap.put("importLogs",upLogsb.toString());
        return resultMap;
    }


    @Override
    public PageData getJsTaxTypeCodeBarcodePageListData(PageMap pageMap) throws Exception {
        List<Map> list=jsTaxTypeCodeBarcodeMapper.getJsTaxTypeCodeBarcodePageList(pageMap);
        int itotal=jsTaxTypeCodeBarcodeMapper.getJsTaxTypeCodeBarcodePageCount(pageMap);
        PageData pageData= new PageData(itotal,list,pageMap);
        return pageData;
    }

    @Override
    public Map addJsTaxTypeCodeBarcode(JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode) throws Exception {
        Map resultMap=new HashMap();
        if(StringUtils.isEmpty(jsTaxTypeCodeBarcode.getJstypeid()) || StringUtils.isEmpty(jsTaxTypeCodeBarcode.getBarcode())){
            resultMap.put("flag", false);
            resultMap.put("msg", "您输入未的金税分类编码或条形码不能为空");
            return resultMap;
        }
        boolean isok=getJsTaxTypeCodeBarcodeCount(jsTaxTypeCodeBarcode.getJstypeid(),jsTaxTypeCodeBarcode.getBarcode())>0;

        if(isok) {
            resultMap.put("flag", false);
            resultMap.put("msg", "您输入金税分类编码和条形码编码已经存在");
            return resultMap;
        }
        SysUser sysUser=getSysUser();
        jsTaxTypeCodeBarcode.setAddtime(new Date());
        jsTaxTypeCodeBarcode.setAdduserid(sysUser.getUserid());
        jsTaxTypeCodeBarcode.setAddusername(sysUser.getName());
        isok= jsTaxTypeCodeBarcodeMapper.insertJsTaxTypeCodeBarcode(jsTaxTypeCodeBarcode)>0;
        resultMap.put("flag",isok);
        return resultMap;
    }

    @Override
    public Map editJsTaxTypeCodeBarcode(JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode) throws Exception {
        Map resultMap=new HashMap();
        if(StringUtils.isEmpty(jsTaxTypeCodeBarcode.getJstypeid()) || StringUtils.isEmpty(jsTaxTypeCodeBarcode.getBarcode())){
            resultMap.put("flag", false);
            resultMap.put("msg", "您输入未的金税分类编码或条形码不能为空");
            return resultMap;
        }
        JsTaxTypeCodeBarcode oldData=jsTaxTypeCodeBarcodeMapper.getJsTaxTypeCodeBarcodeById(jsTaxTypeCodeBarcode.getId());
        if(null==oldData){
            resultMap.put("flag", false);
            resultMap.put("msg", "未找到相关要修改的信息");
            return resultMap;
        }
        boolean isok=false;
        Map queryMap=new HashMap();
        queryMap.put("jstypeid",oldData.getJstypeid());
        queryMap.put("barcode",oldData.getBarcode());
        queryMap.put("notCurId",oldData.getId());
        isok=jsTaxTypeCodeBarcodeMapper.getJsTaxTypeCodeBarcodeCountBy(queryMap)>0;
        if(isok) {
            resultMap.put("flag", false);
            resultMap.put("msg", "您输入金税分类编码和条形码编码已经存在");
            return resultMap;
        }
        SysUser sysUser=getSysUser();
        jsTaxTypeCodeBarcode.setModifytime(new Date());
        jsTaxTypeCodeBarcode.setModifyuserid(sysUser.getUserid());
        jsTaxTypeCodeBarcode.setModifyusername(sysUser.getName());
        isok=jsTaxTypeCodeBarcodeMapper.updateJsTaxTypeCodeBarcode(jsTaxTypeCodeBarcode) > 0;
        resultMap.put("flag",isok);
        return resultMap;
    }

    @Override
    public JsTaxTypeCodeBarcode getJsTaxTypeCodeBarcodeById(String id) throws Exception {
        return jsTaxTypeCodeBarcodeMapper.getJsTaxTypeCodeBarcodeById(id);
    }
    @Override
    public int getJsTaxTypeCodeBarcodeCount(String jstypeid,String barcode) throws Exception{
        Map queryMap=new HashMap();
        queryMap.put("jstypeid",jstypeid);
        queryMap.put("barcode",barcode);
        return jsTaxTypeCodeBarcodeMapper.getJsTaxTypeCodeBarcodeCountBy(queryMap);
    }
    @Override
    public int getJsTaxTypeCodeBarcodeCountBy(Map map) throws Exception{
        return jsTaxTypeCodeBarcodeMapper.getJsTaxTypeCodeBarcodeCountBy(map);
    }

    @Override
    public Map importJsTaxTypeCodeBarcodeData(List<Map<String,Object>> dataList,Map optionMap) throws Exception{
        Map resultMap=new HashMap();
        if(null==dataList || dataList.size()==0){
            resultMap.put("flag",false);
            resultMap.put("msg","导入的数据为空或者格式不匹配");
            return resultMap;
        }
        List<JsTaxTypeCodeBarcode> importDataList=new ArrayList<JsTaxTypeCodeBarcode>();
        List<Map<String,Object>> errorList=new ArrayList<Map<String, Object>>();
        Date nowDate=new Date();
        SysUser sysUser=getSysUser();
        StringBuilder upLogsb=new StringBuilder();
        String existsidtip=(String)optionMap.get("existsidtip");
        for(Map itemData : dataList){
            String jstypeid="";
            jstypeid=(String)itemData.get("jstypeid");
            if(null==jstypeid || "".equals(jstypeid.trim())){
                itemData.put("errormessage","金税税收分类编码不能为空");
                errorList.add(itemData);
                continue;
            }
            String barcode=(String)itemData.get("barcode");
            if(null==barcode || "".equals(barcode.trim())){
                itemData.put("errormessage","商品条形码不能为空");
                errorList.add(itemData);
                continue;
            }

            boolean existsid=jsTaxTypeCodeMapper.isUsedJsTaxTypeCodeById(jstypeid)>0;
            if(!existsid){
                if("1".equals(existsidtip)){
                    itemData.put("errormessage","金税分类编码：“"+jstypeid+"”不存在");
                    errorList.add(itemData);
                    continue;
                }
            }

            existsid=getJsTaxTypeCodeBarcodeCount(jstypeid,barcode)>0;

            if(!existsid) {
                JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode = new JsTaxTypeCodeBarcode();
                jsTaxTypeCodeBarcode.setJstypeid(jstypeid);
                jsTaxTypeCodeBarcode.setBarcode(barcode);
                jsTaxTypeCodeBarcode.setAddtime(nowDate);
                jsTaxTypeCodeBarcode.setAdduserid(sysUser.getUserid());
                jsTaxTypeCodeBarcode.setAddusername(sysUser.getName());
                importDataList.add(jsTaxTypeCodeBarcode);
            }else{
                if("1".equals(existsidtip)){
                    itemData.put("errormessage","金税分类编码：“"+jstypeid+"”和条形码：“"+barcode+"”组合记录已经存在");
                    errorList.add(itemData);
                    continue;
                }
            }
        }
        if(importDataList.size()==0){
            resultMap.put("flag",false);
            resultMap.put("errorDataList",errorList);
            resultMap.put("msg","传入数据"+dataList.size()+"条,但系统未找到相关符合导入数据");
            return resultMap;
        }
        //批量更新条数
        int updateDBCount=300;
        int listLen = importDataList.size();
        boolean canInsert = listLen > 0;
        int ipage = 0;
        //数据小于等于300条时，直接插入
        boolean isimport=false;
        if(listLen<=updateDBCount) {
            isimport=jsTaxTypeCodeBarcodeMapper.insertJsTaxTypeCodeBarcodeBatch(importDataList)>0;
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
                List<JsTaxTypeCodeBarcode> tmpList = importDataList.subList(ipage * updateDBCount, row);
                isimport=jsTaxTypeCodeBarcodeMapper.insertJsTaxTypeCodeBarcodeBatch(tmpList)>0 || isimport;
                ipage = ipage + 1;
            }
        }
        if(isimport) {
            resultMap.put("flag", true);
            resultMap.put("msg", "税收分类关联商品条形码导入成功");
        }else{
            resultMap.put("flag",false);
            resultMap.put("msg","税收分类关联商品条形码导入未成功");
        }
        resultMap.put("errorDataList",errorList);
        resultMap.put("importLogs",upLogsb.toString());
        return resultMap;
    }

    @Override
    public boolean deleteJsTaxTypeCodeBarcode(String id) throws Exception {
        return jsTaxTypeCodeBarcodeMapper.deleteJsTaxTypeCodeBarcode(id)>0;
    }
    @Override
    public Map updateAndLinkGoodsInfoJsCode() throws Exception{
        Map resultMap=new HashMap();
        List<Map> dataList=jsTaxTypeCodeBarcodeMapper.getJsTaxTypeCodeBarcodeGoodsList();
        Date operDate=new Date();
        SysUser sysUser=getSysUser();
        StringBuilder opGoodsidBuffer=new StringBuilder();
        int datacount=dataList.size();
        int successcount=0;
        for (Map itemMap:dataList) {
            String jstypeid=(String)itemMap.get("jstypeid");
            String goodsid=(String)itemMap.get("goodsid");
            if(null==goodsid || "".equals(goodsid.trim())){
                continue;
            }
            GoodsInfo updateGoodsInfo = new GoodsInfo();
            updateGoodsInfo.setId(goodsid);
            updateGoodsInfo.setJstaxsortid(jstypeid);
            updateGoodsInfo.setJsgoodsmodifytime(operDate);
            updateGoodsInfo.setJsgoodsmodifyuserid(sysUser.getUserid());
            updateGoodsInfo.setJsgoodsmodifyusername(sysUser.getName());
            if(goodsMapper.updateGoodsInfoForJS(updateGoodsInfo)>0){
                successcount=successcount+1;
                if (opGoodsidBuffer.length() > 0) {
                    opGoodsidBuffer.append(",");
                    opGoodsidBuffer.append(goodsid);
                }
            }
        }
        resultMap.put("flag",successcount>0);
        resultMap.put("isuccess",successcount);
        resultMap.put("itotal",datacount);
        resultMap.put("opGoodsid",opGoodsidBuffer.toString());
        return resultMap;
    }
    @Override
    public Map updateAndCreateJsBarcodeData() throws Exception{
        Map resultMap=new HashMap();
        List<Map> dataList=jsTaxTypeCodeMapper.getGoodsJsTypeCodeList();
        Date operDate=new Date();
        SysUser sysUser=getSysUser();
        StringBuilder opGoodsidBuffer=new StringBuilder();
        List<JsTaxTypeCodeBarcode> insertDataList=new ArrayList<JsTaxTypeCodeBarcode>();
        List<JsTaxTypeCodeBarcode> updateDataList=new ArrayList<JsTaxTypeCodeBarcode>();
        int datacount=dataList.size();
        int successcount=0;
        int existscount=0;
        for (Map itemMap:dataList) {
            String jstypeid=(String)itemMap.get("jstypeid");
            String barcode=(String)itemMap.get("barcode");
            if( null==jstypeid || "".equals(jstypeid.trim()) || null==barcode || "".equals(barcode.trim())){
                continue;
            }
            boolean existsid=getJsTaxTypeCodeBarcodeCount(jstypeid,barcode)>0;

            if(!existsid) {
                JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode = new JsTaxTypeCodeBarcode();
                jsTaxTypeCodeBarcode.setJstypeid(jstypeid);
                jsTaxTypeCodeBarcode.setBarcode(barcode);
                jsTaxTypeCodeBarcode.setAddtime(operDate);
                jsTaxTypeCodeBarcode.setAdduserid(sysUser.getUserid());
                jsTaxTypeCodeBarcode.setAddusername(sysUser.getName());
                insertDataList.add(jsTaxTypeCodeBarcode);
            }else{
                existscount=existscount+1;
            }
        }
        if(insertDataList.size()==0){
            resultMap.put("flag",false);
            resultMap.put("itotal",datacount);
            resultMap.put("existscount",existscount);
            return resultMap;
        }
        //批量更新条数
        int updateDBCount=300;
        int listLen = insertDataList.size();
        boolean canInsert = listLen > 0;
        int ipage = 0;
        //数据小于等于300条时，直接插入
        boolean isimport=false;
        if(listLen<=updateDBCount) {
            isimport=jsTaxTypeCodeBarcodeMapper.insertJsTaxTypeCodeBarcodeBatch(insertDataList)>0;
            if(isimport) {
                successcount = insertDataList.size();
            }
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
                List<JsTaxTypeCodeBarcode> tmpList = insertDataList.subList(ipage * updateDBCount, row);
                boolean flag=jsTaxTypeCodeBarcodeMapper.insertJsTaxTypeCodeBarcodeBatch(tmpList)>0;
                isimport= flag || isimport;
                ipage = ipage + 1;
                if(flag){
                    successcount=successcount+tmpList.size();
                }
            }
        }
        resultMap.put("flag",successcount>0);
        resultMap.put("isuccess",successcount);
        resultMap.put("itotal",datacount);
        return resultMap;
    }


    @Override
    public PageData getUnLinkJsTypeCodeGoodsPageListData(PageMap pageMap) throws Exception{
        //String sql = getDataAccessRule("t_base_goods_info", null);
        //pageMap.setDataSql(sql);
        List<Map> list=jsTaxTypeCodeMapper.getUnLinkJsTypeCodeGoodsPageList(pageMap);
        int total=jsTaxTypeCodeMapper.getUnLinkJsTypeCodeGoodsPageCount(pageMap);
        PageData pageData=new PageData(total,list,pageMap);
        return pageData;
    }

    @Override
    public PageData getLinkJsTypeCodeGoodsPageListData(PageMap pageMap) throws Exception{
        //String sql = getDataAccessRule("t_base_goods_info", null);
        //pageMap.setDataSql(sql);
        List<Map> list=jsTaxTypeCodeMapper.getLinkJsTypeCodeGoodsPageList(pageMap);
        int total=jsTaxTypeCodeMapper.getLinkJsTypeCodeGoodsPageCount(pageMap);
        PageData pageData=new PageData(total,list,pageMap);
        return pageData;
    }
    @Override
    public Map addLinkJsTypeCode(Map map) throws Exception{
        Map resultMap=new HashMap();
        String goodsidarrs=(String)map.get("goodsidarrs");
        String jstypeid=(String)map.get("jstypeid");
        if(null==goodsidarrs || "".equals(goodsidarrs.trim())){
            resultMap.put("flag",false);
            resultMap.put("msg","请选择商品编码");
            return resultMap;
        }
        if(null==jstypeid || "".equals(jstypeid)){
            resultMap.put("flag",false);
            resultMap.put("msg","请填写税收分类编码");
            return resultMap;
        }
        Map queryMap=new HashMap();
        queryMap.put("idarrs",goodsidarrs);
        List<GoodsInfo> goodsInfoList=goodsMapper.getGoodsInfoListByMap(queryMap);
        if(null==goodsInfoList || goodsInfoList.size()==0){
            resultMap.put("flag",false);
            resultMap.put("msg","请未找到相关商品信息");
            return resultMap;
        }
        Date opDate=new Date();
        SysUser sysUser=getSysUser();
        StringBuilder logSbuffer=new StringBuilder();
        boolean isok=false;
        for(GoodsInfo goodItem:goodsInfoList){
            GoodsInfo upGoodsInfo=new GoodsInfo();
            upGoodsInfo.setId(goodItem.getId());
            upGoodsInfo.setJstaxsortid(jstypeid);
            upGoodsInfo.setJsgoodsmodifytime(opDate);
            upGoodsInfo.setJsgoodsmodifyuserid(sysUser.getUserid());
            upGoodsInfo.setJsgoodsmodifyusername(sysUser.getName());
            if(goodsMapper.updateGoodsInfoForJS(upGoodsInfo)>0){
                if(logSbuffer.length()>0){
                    logSbuffer.append(",");
                }
                logSbuffer.append(goodItem.getId());
                isok = true;
            }
            if(StringUtils.isNotEmpty(goodItem.getBarcode())){
                if(getJsTaxTypeCodeBarcodeCount(jstypeid,goodItem.getBarcode())==0) {
                    JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode=new JsTaxTypeCodeBarcode();
                    jsTaxTypeCodeBarcode.setJstypeid(jstypeid);
                    jsTaxTypeCodeBarcode.setBarcode(goodItem.getBarcode());
                    jsTaxTypeCodeBarcode.setAddtime(opDate);
                    jsTaxTypeCodeBarcode.setAdduserid(sysUser.getUserid());
                    jsTaxTypeCodeBarcode.setAddusername(sysUser.getName());
                    jsTaxTypeCodeBarcodeMapper.insertJsTaxTypeCodeBarcode(jsTaxTypeCodeBarcode);
                }
            }
        }
        resultMap.put("flag",isok);
        resultMap.put("updategoodslog",logSbuffer.toString());
        return resultMap;
    }
}

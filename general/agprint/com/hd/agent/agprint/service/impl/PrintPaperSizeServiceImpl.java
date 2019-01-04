package com.hd.agent.agprint.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintPaperSize;
import com.hd.agent.agprint.service.IPrintPaperSizeService;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by master on 2016/9/11.
 */
public class PrintPaperSizeServiceImpl extends BasePrintTempletServiceImpl implements IPrintPaperSizeService {

    @Override
    public PageData showPrintPaperSizePageListData(PageMap pageMap) throws Exception {
        Map condition=pageMap.getCondition();
        String sort=(String) condition.get("sort");
        String order=(String) condition.get("order");
        if((null==sort || "".equals(sort.trim())) || (null==order || "".equals(order.trim()))){
            condition.remove("sort");
            condition.remove("order");
            pageMap.setOrderSql("state desc,id desc");
        }
        List<PrintPaperSize> list= getPrintPaperSizeMapper().getPrintPaperSizePageList(pageMap);
        int iCount= getPrintPaperSizeMapper().getPrintPaperSizePageCount(pageMap);
        PageData pageData=new PageData(iCount, list, pageMap);
        return pageData;
    }

    @Override
    public Map addPrintPaperSize(PrintPaperSize printPaperSize) throws Exception {
        Map resultMap=new HashMap();
        Date nowDate=new Date();
        SysUser sysUser=getSysUser();
        printPaperSize.setAdduserid(sysUser.getUserid());
        printPaperSize.setAddusername(sysUser.getName());
        printPaperSize.setAddtime(nowDate);


        Map paramMap=new HashMap();
        paramMap.put("name",printPaperSize.getName());
        int hascount=getPrintPaperSizeMapper().getPrintPaperSizeCountBy(paramMap);
        if(hascount>0){
            paramMap.put("flag",false);
            paramMap.put("msg","名称：“"+printPaperSize.getName()+"” 的纸张大小配置已经存在");
            return paramMap;
        }
        paramMap.clear();
        paramMap.put("width",printPaperSize.getWidth());
        paramMap.put("height",printPaperSize.getHeight());
        hascount=getPrintPaperSizeMapper().getPrintPaperSizeCountBy(paramMap);
        if(hascount>0){
            paramMap.put("flag",false);
            paramMap.put("msg","“长度："+printPaperSize.getWidth()+",宽度："+printPaperSize.getHeight()+"” 的纸张大小配置已经存在");
            return paramMap;
        }

        if("1".equals(printPaperSize.getState())){
            printPaperSize.setOpentime(nowDate);
            printPaperSize.setOpenuserid(sysUser.getUserid());
            printPaperSize.setOpenusername(sysUser.getName());
        }else if("0".equals(printPaperSize.getState())){
            printPaperSize.setClosetime(nowDate);
            printPaperSize.setCloseuserid(sysUser.getUserid());
            printPaperSize.setCloseusername(sysUser.getName());
        }else{
            printPaperSize.setState("0");
        }

        boolean isok= getPrintPaperSizeMapper().insertPrintPaperSize(printPaperSize)>0;

        resultMap.put("flag", isok);
        return resultMap;
    }

    @Override
    public Map updatePrintPaperSize(PrintPaperSize printPaperSize) throws Exception {
        Map resultMap=new HashMap();
        if(null==printPaperSize){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到相关打印纸张大小");
            return resultMap;
        }

        PrintPaperSize oldPrintPaperSize = getPrintPaperSizeMapper().getPrintPaperSize(printPaperSize.getId());
        if(null== oldPrintPaperSize){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到相关打印纸张大小");
            return resultMap;
        }

        Map paramMap=new HashMap();
        paramMap.put("name",printPaperSize.getName());
        paramMap.put("notequalid",printPaperSize.getId());
        int hascount=getPrintPaperSizeMapper().getPrintPaperSizeCountBy(paramMap);
        if(hascount>0){
            paramMap.put("flag",false);
            paramMap.put("msg","名称：“"+printPaperSize.getName()+"” 的纸张大小配置已经存在");
            return paramMap;
        }
        paramMap.clear();
        paramMap.put("width",printPaperSize.getWidth());
        paramMap.put("height",printPaperSize.getHeight());
        paramMap.put("notequalid",printPaperSize.getId());
        hascount=getPrintPaperSizeMapper().getPrintPaperSizeCountBy(paramMap);
        if(hascount>0){
            paramMap.put("flag",false);
            paramMap.put("msg","“长度："+printPaperSize.getWidth()+",宽度："+printPaperSize.getHeight()+"” 的纸张大小配置已经存在");
            return paramMap;
        }

        SysUser sysUser=getSysUser();
        printPaperSize.setModifyuserid(sysUser.getUserid());
        printPaperSize.setModifyusername(sysUser.getName());
        printPaperSize.setModifytime(new Date());
        boolean isok= getPrintPaperSizeMapper().updatePrintPaperSize(printPaperSize)>0;

        resultMap.put("flag", isok);
        return resultMap;
    }

    @Override
    public Map deletePrintPaperSize(String id) throws Exception {
        Map resultMap=new HashMap();
        PrintPaperSize printPaperSize= getPrintPaperSizeMapper().getPrintPaperSize(id);
        if(null==printPaperSize){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到相关打印纸张大小");
            return resultMap;
        }
        if("1".equals(printPaperSize.getState())){
            resultMap.put("flag", false);
            resultMap.put("msg", "启用的打印纸张大小不能被删除");
            return resultMap;
        }
        boolean delFlag=canTableDataDictDelete("t_print_orderseq", id.trim());
        boolean isok=false;
        if(delFlag){
            isok= getPrintPaperSizeMapper().deletePrintPaperSize(id)>0;
            resultMap.put("flag", isok);
        }else{
            resultMap.put("flag", false);
            resultMap.put("msg", "被引用的数据不能被删除");
        }
        return resultMap;
    }


    @Override
    public Map deletePrintPaperSizeMore(String idarrs)throws Exception{
        Map map=new HashMap();
        int iSuccess=0;
        int iFailure=0;
        int iNohandle=0;

        if(null==idarrs || "".equals(idarrs.trim())){

            map.put("flag", false);
            map.put("isuccess", iSuccess);
            map.put("ifailure", iFailure);
            return map;
        }
        String[] idArr=idarrs.trim().split(",");
        for(String id : idArr){
            if(null==id || "".equals(id.trim())){
                continue;
            }
            Map resultMap=deletePrintPaperSize(id);
            Boolean flag=false;
            if(null!=resultMap){
                flag=(Boolean)resultMap.get("flag");
                if(null==flag){
                    flag=false;
                }
            }
            if(flag){
                iSuccess=iSuccess+1;
            }else{
                iFailure=iFailure+1;
            }
        }
        map.clear();
        if(iSuccess>0){
            map.put("flag", true);
        }else{
            map.put("flag", false);
        }
        map.put("isuccess", iSuccess);
        map.put("ifailure", iFailure);
        return map;
    }

    @Override
    public Map enablePrintPaperSize(String id) throws Exception {
        Map resultMap=new HashMap();
        PrintPaperSize printPaperSize= getPrintPaperSizeMapper().getPrintPaperSize(id);
        if(null==printPaperSize){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到相关打印纸张大小");
            return resultMap;
        }
        if("1".equals(printPaperSize.getState())){
            resultMap.put("flag", false);
            resultMap.put("msg", "当前打印纸张大小已经启用");
            return resultMap;
        }
        SysUser sysUser=getSysUser();
        PrintPaperSize upPrintPaperSize =new PrintPaperSize();
        upPrintPaperSize.setId(printPaperSize.getId());
        upPrintPaperSize.setOpentime(new Date());
        upPrintPaperSize.setOpenuserid(sysUser.getUserid());
        upPrintPaperSize.setOpenusername(sysUser.getName());
        boolean isok= getPrintPaperSizeMapper().enablePrintPaperSize(upPrintPaperSize)>0;
        resultMap.put("flag", isok);
        return resultMap;
    }

    @Override
    public Map disablePrintPaperSize(String id) throws Exception {
        Map resultMap=new HashMap();
        PrintPaperSize printPaperSize= getPrintPaperSizeMapper().getPrintPaperSize(id);
        if(null==printPaperSize){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到相关打印纸张大小");
            return resultMap;
        }
        if(!"1".equals(printPaperSize.getState())){
            resultMap.put("flag", false);
            resultMap.put("msg", "当前打印纸张大小已经禁用");
            return resultMap;
        }
        SysUser sysUser=getSysUser();
        PrintPaperSize upPrintPaperSize =new PrintPaperSize();
        upPrintPaperSize.setId(printPaperSize.getId());
        upPrintPaperSize.setClosetime(new Date());
        upPrintPaperSize.setCloseuserid(sysUser.getUserid());
        upPrintPaperSize.setCloseusername(sysUser.getName());
        boolean isok= getPrintPaperSizeMapper().disablePrintPaperSize(upPrintPaperSize)>0;
        resultMap.put("flag", isok);
        return resultMap;
    }

    @Override
    public PrintPaperSize showPrintPaperSizeInfo(String id) throws Exception {
        PrintPaperSize printPaperSize= getPrintPaperSizeMapper().getPrintPaperSize(id);
        return printPaperSize;
    }
}

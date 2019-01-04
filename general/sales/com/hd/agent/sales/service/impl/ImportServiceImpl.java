package com.hd.agent.sales.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintTemplet;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.ImportSet;
import com.hd.agent.sales.service.ImportService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LINXX on 2015/9/22.
 */
public class ImportServiceImpl extends BaseSalesServiceImpl implements ImportService {
    @Override
    public Map enableImportModel(String id) throws Exception {

        Map map=new HashMap();
        ImportSet importSet = getImportSetMapper().showImportModelById(Integer.parseInt(id));
        if(null==importSet){
            map.put("flag", false);
            map.put("msg", "未能找到相关模板信息");
            return map;
        }
        if("1".equals(importSet.getState())){
            map.put("flag", true);
            return map;
        }else{
            importSet.setState("1");
        }
        SysUser sysUser=getSysUser();
        importSet.setModifytime(new Date());
        importSet.setModifyuserid(sysUser.getUserid());
        importSet.setModifyusername(sysUser.getName());
        boolean flag=getImportSetMapper().updateImportSet(importSet)>0;
        map.put("flag", flag);
        return map;
    }

    @Override
    public Map disableImportModel(String id) throws Exception {
        Map map=new HashMap();
        ImportSet importSet = getImportSetMapper().showImportModelById(Integer.parseInt(id));
        if(null==importSet){
            map.put("flag", false);
            map.put("msg", "未能找到相关模板信息");
            return map;
        }
        if("0".equals(importSet.getState())){
            map.put("flag", true);
            return map;
        }else{
            importSet.setState("0");
        }
        SysUser sysUser=getSysUser();
        importSet.setModifytime(new Date());
        importSet.setModifyuserid(sysUser.getUserid());
        importSet.setModifyusername(sysUser.getName());
        boolean flag=getImportSetMapper().updateImportSet(importSet)>0;
        map.put("flag", flag);
        return map;
    }

    @Override
    public PageData showImportModelData(PageMap pageMap) throws Exception {

        List list = getImportSetMapper().showImportModelData(pageMap);
        int count = getImportSetMapper().showImportModelDataCount(pageMap);

        return new PageData(count, list, pageMap);
    }

    @Override
    public boolean addImportSet(ImportSet importSet) throws Exception {

       int a = getImportSetMapper().insertImportSet(importSet);

        return a>0;
    }

    @Override
    public Map deleteImportModel(String id) throws Exception {
        Map map=new HashMap();
        ImportSet importSet = getImportSetMapper().showImportModelById(Integer.parseInt(id));
        if(null==importSet){
            map.put("flag", false);
            map.put("msg", "未能找到相关模板信息");
            return map;
        }
        if("1".equals(importSet.getState())){
            map.put("flag", false);
            map.put("msg", "启用状态下不允许删除");
            return map;
        }
        boolean flag=getImportSetMapper().deleteImportModelById(Integer.parseInt(id))>0;
        map.put("flag", flag);
        return map;
    }

    @Override
    public ImportSet showImportModelById(String id) throws Exception {
        ImportSet importSet = getImportSetMapper().showImportModelById(Integer.parseInt(id));
        return importSet;
    }

    @Override
    public boolean updateImportSet(ImportSet importSet) throws Exception {
        return getImportSetMapper().updateImportSet(importSet)>0;
    }


}

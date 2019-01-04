package com.hd.agent.system.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.HashCryptUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.dao.SysDataSourceMapper;
import com.hd.agent.system.model.SysDataSource;
import com.hd.agent.system.model.SysDataSourceConstant;
import com.hd.agent.system.service.ISysDataSourceService;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by master on 2017/3/11.
 */
public class SysDataSourceServiceImpl extends BaseServiceImpl implements ISysDataSourceService {
    private SysDataSourceMapper sysDataSourceMapper;

    public SysDataSourceMapper getSysDataSourceMapper() {
        return sysDataSourceMapper;
    }

    public void setSysDataSourceMapper(SysDataSourceMapper sysDataSourceMapper) {
        this.sysDataSourceMapper = sysDataSourceMapper;
    }

    @Override
    public PageData getSysDataSourceListPageData(PageMap pageMap) throws Exception {
        List<SysDataSource> list = sysDataSourceMapper.getSysDataSourcePageList(pageMap);
        int count = sysDataSourceMapper.getSysDataSourcePageCount(pageMap);
        PageData pageData = new PageData(count, list, pageMap);
        return pageData;
    }

    @Override
    public Map addSysDataSource(SysDataSource sysDataSource) throws Exception {
        Map resultMap = new HashMap();
        if (null == sysDataSource) {
            resultMap.put("flag", false);
            resultMap.put("msg", "未找到相关数据");
            return resultMap;
        }
        Map paramMap = new HashMap();
        paramMap.put("code", sysDataSource.getCode());
        int icount = sysDataSourceMapper.getSysDataSourceCountBy(paramMap);
        if (icount > 0) {
            resultMap.put("flag", false);
            resultMap.put("msg", "代码：" + sysDataSource.getCode() + "已经存在");
            return resultMap;
        }
        paramMap.put("name", sysDataSource.getName());
        icount = sysDataSourceMapper.getSysDataSourceCountBy(paramMap);
        if (icount > 0) {
            paramMap.put("flag", false);
            paramMap.put("msg", "名称：“" + sysDataSource.getName() + "” 的数据源配置信息已经存在");
            return paramMap;
        }
        SysUser sysUser = getSysUser();
        sysDataSource.setAdduserid(sysUser.getUserid());
        sysDataSource.setAddusername(sysUser.getName());
        sysDataSource.setAddtime(new Date());
        if (StringUtils.isNotEmpty(sysDataSource.getDbpasswd())) {
            sysDataSource.setDbpasswdclear(sysDataSource.getDbpasswd());
            sysDataSource.setDbpasswd(HashCryptUtils.aesEncrypt(sysDataSource.getDbpasswd(), SysDataSourceConstant.PWD_ENCRIPT_KEY));
        }
        icount = sysDataSourceMapper.insertSysDataSource(sysDataSource);
        resultMap.put("flag", icount > 0);
        return resultMap;
    }

    @Override
    public Map updateSysDataSource(SysDataSource sysDataSource) throws Exception {
        Map resultMap = new HashMap();
        if (null == sysDataSource || StringUtils.isEmpty(sysDataSource.getId())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "未找到相关数据");
            return resultMap;
        }
        SysDataSource oldSysDataSource = sysDataSourceMapper.getSysDataSource(sysDataSource.getId());
        if (null == oldSysDataSource) {
            resultMap.put("flag", false);
            resultMap.put("msg", "未找到相关数据");
            return resultMap;
        }
        sysDataSource.setCode(oldSysDataSource.getCode());
        Map paramMap = new HashMap();
        paramMap.put("name", sysDataSource.getName());
        paramMap.put("notequalid", sysDataSource.getId());
        int hascount = sysDataSourceMapper.getSysDataSourceCountBy(paramMap);
        if (hascount > 0) {
            paramMap.put("flag", false);
            paramMap.put("msg", "名称：“" + sysDataSource.getName() + "” 的数据源配置信息已经存在");
            return paramMap;
        }

        SysUser sysUser = getSysUser();
        sysDataSource.setModifyuserid(sysUser.getUserid());
        sysDataSource.setModifyusername(sysUser.getName());
        sysDataSource.setModifytime(new Date());
        if ("1".equals(sysDataSource.getIsmodifypwd())) {
            if (StringUtils.isNotEmpty(sysDataSource.getDbpasswd())) {
                sysDataSource.setDbpasswdclear(sysDataSource.getDbpasswd());
                sysDataSource.setDbpasswd(HashCryptUtils.aesEncrypt(sysDataSource.getDbpasswd(), SysDataSourceConstant.PWD_ENCRIPT_KEY));
            } else {
                sysDataSource.setDbpasswd("");
            }
        } else {
            sysDataSource.setDbpasswd(null);
        }
        int icount = sysDataSourceMapper.updateSysDataSource(sysDataSource);
        resultMap.put("flag", icount > 0);
        return resultMap;
    }

    @Override
    public Map deleteSysDataSource(String id) throws Exception {
        Map resultMap = new HashMap();
        SysDataSource oldSysDataSource = sysDataSourceMapper.getSysDataSource(id);
        if (null == oldSysDataSource) {
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到相关数据源配置");
            return resultMap;
        }

        if ("1".equals(oldSysDataSource.getState())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "启用的数据源配置不能被删除");
            return resultMap;
        }
        boolean flag = sysDataSourceMapper.deleteSysDataSource(id) > 0;
        resultMap.put("flag", flag);
        return resultMap;
    }

    @Override
    public Map enableSysDataSource(String id) throws Exception {

        Map resultMap = new HashMap();
        SysDataSource sysDataSource = getSysDataSourceMapper().getSysDataSource(id);
        if (null == sysDataSource) {
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到相关数据源配置");
            return resultMap;
        }
        if ("1".equals(sysDataSource.getState())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "当前数据源配置已经启用");
            return resultMap;
        }
        SysUser sysUser = getSysUser();
        SysDataSource dataSource = new SysDataSource();
        dataSource.setId(id);
        dataSource.setOpentime(new Date());
        dataSource.setOpenuserid(sysUser.getUserid());
        dataSource.setOpenusername(sysUser.getName());
        boolean isok = sysDataSourceMapper.enableSysDataSource(dataSource) > 0;
        resultMap.put("flag", isok);
        return resultMap;
    }

    @Override
    public Map disableSysDataSource(String id) throws Exception {

        Map resultMap = new HashMap();
        SysDataSource sysDataSource = getSysDataSourceMapper().getSysDataSource(id);
        if (null == sysDataSource) {
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到相关数据源配置");
            return resultMap;
        }
        if (!"1".equals(sysDataSource.getState())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "当前数据源配置已经禁用");
            return resultMap;
        }
        SysUser sysUser = getSysUser();
        SysDataSource dataSource = new SysDataSource();
        dataSource.setId(id);
        dataSource.setClosetime(new Date());
        dataSource.setCloseuserid(sysUser.getUserid());
        dataSource.setCloseusername(sysUser.getName());
        boolean isok = sysDataSourceMapper.disableSysDataSource(dataSource) > 0;
        resultMap.put("flag", isok);
        return resultMap;
    }

    @Override
    public SysDataSource getSysDataSource(String id) throws Exception {
        SysDataSource sysDataSource = sysDataSourceMapper.getSysDataSource(id);
        if (null != sysDataSource) {
            if (StringUtils.isNotEmpty(sysDataSource.getDbpasswd())) {
                try {
                    sysDataSource.setDbpasswdclear(HashCryptUtils.aesDecrypt(sysDataSource.getDbpasswd(), SysDataSourceConstant.PWD_ENCRIPT_KEY));
                } catch (Exception ex) {
                }
            }
        }
        return sysDataSource;
    }

    @Override
    public SysDataSource getSysDataSourceByCode(String code) throws Exception {
        SysDataSource sysDataSource = sysDataSourceMapper.getSysDataSourceByCode(code);
        if (null != sysDataSource) {
            if (StringUtils.isNotEmpty(sysDataSource.getDbpasswd())) {
                try {
                    sysDataSource.setDbpasswdclear(HashCryptUtils.aesDecrypt(sysDataSource.getDbpasswd(), SysDataSourceConstant.PWD_ENCRIPT_KEY));
                } catch (Exception ex) {

                }
            }
        }
        return sysDataSource;
    }

    @Override
    public SysDataSource getEnableSysDataSourceByCode(String code) throws Exception {
        SysDataSource sysDataSource = sysDataSourceMapper.getEnableSysDataSourceByCode(code);
        if (null != sysDataSource) {
            if (StringUtils.isNotEmpty(sysDataSource.getDbpasswd())) {
                try {
                    sysDataSource.setDbpasswdclear(HashCryptUtils.aesDecrypt(sysDataSource.getDbpasswd(), SysDataSourceConstant.PWD_ENCRIPT_KEY));
                } catch (Exception ex) {

                }
            }
        }
        return sysDataSource;
    }

    @Override
    public int getSysDataSourceCountBy(Map map) throws Exception {
        return sysDataSourceMapper.getSysDataSourceCountBy(map);
    }

    @Override
    public Map testSysDataSource(SysDataSource dataSource) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Class.forName(dataSource.getJdbcdriver());
            System.out.println("驱动加裁成功");
        } catch (ClassNotFoundException e) {
            result.put("flag", false);
            result.put("msg", "驱动加载失败");
            return result;
        }
        try {
            Connection conn = DriverManager.getConnection(dataSource.getJdbcurl(), dataSource.getDbuser(), dataSource.getDbpasswdclear());
            Statement stmt = conn.createStatement();
            stmt.execute("select 1");
            result.put("flag", true);
            result.put("msg", "数据库连接成功");
            stmt.close();
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            result.put("flag", false);
            result.put("msg", "数据库连接失败." + ex.getMessage());
        }
        return result;
    }
}

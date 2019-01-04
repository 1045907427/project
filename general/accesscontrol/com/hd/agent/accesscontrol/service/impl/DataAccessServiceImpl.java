/**
 * @(#)DataAccessServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-20 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.dao.DataruleMapper;
import com.hd.agent.accesscontrol.model.Datarule;
import com.hd.agent.accesscontrol.service.IDataAccessService;

/**
 * 
 * 数据权限业务逻辑实现类
 * @author chenwei
 */
public class DataAccessServiceImpl implements IDataAccessService {
	
	private DataruleMapper dataruleMapper;

	public DataruleMapper getDataruleMapper() {
		return dataruleMapper;
	}

	public void setDataruleMapper(DataruleMapper dataruleMapper) {
		this.dataruleMapper = dataruleMapper;
	}

	@Override
	public boolean addDatarule(Datarule datarule) throws Exception {
		Datarule datarule2 = dataruleMapper.checkDatarule(datarule);
		if(null==datarule2){
			int i = dataruleMapper.addDatarule(datarule);
			return i>0;
		}else{
			return false;
		}
	}

	@Override
	public List showDataruleList() throws Exception {
		List list = dataruleMapper.showDataruleList();
		return list;
	}

	@Override
	public boolean deleteDatarule(String dataid) throws Exception {
		int i = dataruleMapper.deleteDatarule(dataid);
		return i>0;
	}

	@Override
	public Datarule showDataruleInfo(String dataid) throws Exception {
		Datarule datarule = dataruleMapper.showDataruleInfo(dataid);
		return datarule;
	}

	@Override
	public boolean editDatarule(Datarule datarule) throws Exception {
		int i = dataruleMapper.editDatarule(datarule);
		return i>0;
	}

	@Override
	public boolean checkDataruleTable(String tablename) throws Exception {
		int i = dataruleMapper.checkDataruleTable(tablename);
		if(i>0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public List getDataruleListByUserid(String userid) throws Exception {
		List list = dataruleMapper.getDataruleListByUserid(userid);
		return list;
	}

	@Override
	public List getDataruleOpneList(String type) throws Exception {
		List list = dataruleMapper.getDataruleOpneList(type);
		return list;
	}

    @Override
    public Datarule getDataRuleInfoByTablenameAndUserid(String userid, String tablename) throws Exception {
        if(StringUtils.isEmpty(userid)){
            Datarule datarule = dataruleMapper.getDatarule(tablename);
            return datarule;
        }else{
            Datarule datarule = dataruleMapper.getDataruleByUserid(tablename,userid);
            return datarule;
        }
    }

}


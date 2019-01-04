/**
 * @(#)FilesLevelServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-18 chenwei 创建版本
 */
package com.hd.agent.basefiles.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.dao.FilesLevelMapper;
import com.hd.agent.basefiles.model.FilesLevel;
import com.hd.agent.basefiles.service.IFilesLevelService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.system.dao.TableColumnMapper;
import com.hd.agent.system.dao.TableRelationMapper;
import com.hd.agent.system.model.TableColumn;

/**
 * 
 * 档案级次定义service实现类
 * @author chenwei
 */
public class FilesLevelServiceImpl extends BaseServiceImpl implements
		IFilesLevelService {
	
	private FilesLevelMapper filesLevelMapper;
	
	private TableColumnMapper tableColumnMapper;
	
	private TableRelationMapper tableRelationMapper;
	
	public FilesLevelMapper getFilesLevelMapper() {
		return filesLevelMapper;
	}

	public void setFilesLevelMapper(FilesLevelMapper filesLevelMapper) {
		this.filesLevelMapper = filesLevelMapper;
	}
	
	public TableColumnMapper getTableColumnMapper() {
		return tableColumnMapper;
	}

	public void setTableColumnMapper(TableColumnMapper tableColumnMapper) {
		this.tableColumnMapper = tableColumnMapper;
	}
	
	public TableRelationMapper getTableRelationMapper() {
		return tableRelationMapper;
	}

	public void setTableRelationMapper(TableRelationMapper tableRelationMapper) {
		this.tableRelationMapper = tableRelationMapper;
	}

	@Override
	public List showFilesLevelList(String tablename) throws Exception {
		List list = filesLevelMapper.showFilesLevelList(tablename);
		return list;
	}

	@Override
	public boolean saveFilesLevel(List<FilesLevel> list,String tablename) throws Exception {
		int i = filesLevelMapper.deleteFilesLevel(tablename);
		for(FilesLevel filesLevel : list){
			filesLevelMapper.addFilesLevel(filesLevel);
		}
		return true;
	}
	
	@Override
	public int getTableFileLevelLength(String tablename) throws Exception{
		Map queryMap = new HashMap();
		queryMap.put("tablename", tablename);
		queryMap.put("usepk", "1");
		List<TableColumn> list =  tableColumnMapper.getTableColumnListBy(queryMap);
		int i = 0;
		for (TableColumn column : list){
			int j = filesLevelMapper.getTableFieldMaxLength(tablename, column.getColumnname());
			if(j>0){
				i = j;
			}
		}
		return i;
	}

	@Override
	public int getFilesLevel(String tablename, int level) throws Exception {
		FilesLevel filesLevel = filesLevelMapper.getFilesLevel(tablename, String.valueOf(level));
		return null==filesLevel?0:filesLevel.getLen();
	}

    @Override
    public Map getObjectThisidByidCaseFilesLevel(String tn, String id) throws Exception {
        Map map = new HashMap();
        int len = id.length();
        List<FilesLevel> list = filesLevelMapper.showFilesLevelList(tn);
        int levellen = 0;
        for(FilesLevel filesLevel : list){
            levellen += filesLevel.getLen();
            if(len == levellen){
                String pid = id.substring(0,len-filesLevel.getLen());
                String thisid = id.substring(len-filesLevel.getLen(),len);
                map.put("thisid",thisid);
                map.put("pid",pid);
            }
        }
        return map;
    }

}


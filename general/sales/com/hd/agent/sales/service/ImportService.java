package com.hd.agent.sales.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.ImportSet;

import java.util.List;
import java.util.Map;

/**
 * Created by LINXX on 2015/9/22.
 */
public interface ImportService {

    public PageData showImportModelData(PageMap pageMap) throws Exception ;

    public boolean addImportSet(ImportSet importSet) throws Exception ;

    public Map deleteImportModel(String id) throws Exception ;

    public ImportSet showImportModelById(String id) throws Exception ;

    public boolean updateImportSet(ImportSet importSet) throws Exception ;

    public Map enableImportModel(String id) throws Exception ;

    public Map disableImportModel(String id) throws Exception ;

}

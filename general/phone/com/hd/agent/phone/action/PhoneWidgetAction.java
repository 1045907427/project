/**
 * @(#)PhoneWidgetAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 11, 2017 limin 创建版本
 */
package com.hd.agent.phone.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.system.model.ReferWindow;
import com.hd.agent.system.model.ReferWindowColumn;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.service.IDataDictionaryService;
import com.hd.agent.system.service.IReferWindowService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手机参照窗口
 */
public class PhoneWidgetAction extends BaseFilesAction {

    private IReferWindowService referWindowService;
    /**
     * 数据字典service
     */
    private IDataDictionaryService dataDictionaryService;

    public IReferWindowService getReferWindowService() {
        return referWindowService;
    }

    public void setReferWindowService(IReferWindowService referWindowService) {
        this.referWindowService = referWindowService;
    }

    public IDataDictionaryService getDataDictionaryService() {
        return dataDictionaryService;
    }

    public void setDataDictionaryService(IDataDictionaryService dataDictionaryService) {
        this.dataDictionaryService = dataDictionaryService;
    }

    /**
     * 通用参照窗口（手机）
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 17, 2017
     */
    public String widget() throws Exception {

        String referwid = request.getParameter("id");
        if(StringUtils.isEmpty(referwid)) {
            String col = request.getParameter("col");
            String name = request.getParameter("name");
            TableColumn tableColumn = dataDictionaryService.getTableColumnInfo(name, col);
            referwid = tableColumn.getReferwid();
        }
        String content = request.getParameter("content");
        String paramRule = request.getParameter("paramRule");
        String view = request.getParameter("view");
        String initvalue = request.getParameter("initValue");
        //是否去树状重复数据
        String treeDistint = request.getParameter("treeDistint");
        //数据参照窗口名称作为树状顶级父节点
        String treePName = request.getParameter("treePName");
        //tree树状是否全选 1是0否
        String isAllSelect = request.getParameter("isAllSelect");
        //参照窗口数据 不超过该数量时 全部显示下拉
        String listnum = request.getParameter("listnum");
        pageMap.getCondition().put("paramRule", paramRule);
        pageMap.getCondition().put("view", view);
        pageMap.getCondition().put("treeDistint", treeDistint);
        pageMap.getCondition().put("treePName", treePName);
        pageMap.getCondition().put("isAllSelect", isAllSelect);
        pageMap.getCondition().put("listnum", listnum);
        pageMap.getCondition().put("content", content);
        if(null!=initvalue && !"".equals(initvalue)){
            pageMap.getCondition().put("initvalue", initvalue);
        }

        Map referMap = referWindowService.getReferWindowWidget(pageMap, referwid);
        String model = (String) referMap.get("model");
        Map map = new HashMap();
        //参照窗口未启用或者不存在
        if("none".equals(model)){
            map.put("model", "none");
            request.setAttribute("model", "none");
        }else{
            if("normal".equals(model)){
                List fieldList = new ArrayList();
                //参照窗口下拉框的字段
                Map fieldMap = new HashMap();
                List<ReferWindowColumn> columnList = (List) referMap.get("column");
                int width = 0;
                for (ReferWindowColumn referWindowColumn : columnList) {
                    if("1".equals(referWindowColumn.getIsquote())){
                        fieldMap.put("id", referWindowColumn.getColname());
                        fieldMap.put("idvalue", referWindowColumn.getCol());
                    }else if("2".equals(referWindowColumn.getIsquote())){
                        fieldMap.put("name", referWindowColumn.getColname());
                        fieldMap.put("namevalue", referWindowColumn.getCol());
                    }else if("3".equals(referWindowColumn.getIsquote())){
                        fieldMap.put("id", referWindowColumn.getColname());
                        fieldMap.put("idvalue", referWindowColumn.getCol());
                        fieldMap.put("name", referWindowColumn.getColname());
                        fieldMap.put("namevalue", referWindowColumn.getCol());
                    }
                    //参照窗口下拉框的字段
                    Map fieldColunmMap = new HashMap();
                    if("1".equals(referWindowColumn.getIsquote()) || "2".equals(referWindowColumn.getIsquote())
                            || "3".equals(referWindowColumn.getIsquote()) ||"6".equals(referWindowColumn.getIsquote())
                            ||"0".equals(referWindowColumn.getIsquote())){
                        fieldColunmMap.put("field", referWindowColumn.getCol());
                        fieldColunmMap.put("title", referWindowColumn.getColname());
                        fieldColunmMap.put("sortable", "true");
                        if(null==referWindowColumn.getWidth()){
                            fieldColunmMap.put("width", 100);
                            width += 100;
                        }else{
                            fieldColunmMap.put("width", referWindowColumn.getWidth());
                            width += referWindowColumn.getWidth();
                        }
                        fieldList.add(fieldColunmMap);
                    }
                }
                if(width<=420){
                    Map dataMmap = (Map) fieldList.get(fieldList.size()-1);
                    int widthlen = (Integer) dataMmap.get("width");
                    widthlen = widthlen+420-width;
                    dataMmap.put("width", widthlen);
                    fieldList.remove(fieldList.size()-1);
                    fieldList.add(dataMmap);
                }
//                map.put("columnFieldList", fieldList);
//                map.put("column", fieldMap);
                request.setAttribute("columnList", columnList);
                request.setAttribute("column", fieldMap);
            }
//            map.put("refertype",referMap.get("refertype"));
            request.setAttribute("refertype",referMap.get("refertype"));
//            map.put("data", referMap.get("list"));
            request.setAttribute("data", JSONUtils.listToJsonStr((List)referMap.get("list")));
            ReferWindow referWindow = (ReferWindow) referMap.get("referWindow");
            map.put("type", "refer");
            request.setAttribute("type", "refer");
//            map.put("dataCount", referMap.get("dataCount"));
//            map.put("ajaxLoad", referMap.get("ajaxLoad"));
            request.setAttribute("dataCount",referMap.get("dataCount"));
            request.setAttribute("ajaxLoad",referMap.get("ajaxLoad"));
//            map.put("model", model);
//            map.put("referid", referwid);
            request.setAttribute("wname", referWindow.getWname());
            request.setAttribute("wid", referWindow.getId());
        }
        addJSONObject(map);
        if("tree".equals(model)) {
            return "tree";
        }
        return "widget";
    }

    /**
     * 客户参照窗口（手机）
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 17, 2017
     */
    public String customer() throws Exception {
        return SUCCESS;
    }

    /**
     * 商品参照窗口（手机）
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 17, 2017
     */
    public String goods() throws Exception {
        return SUCCESS;
    }

    /**
     * 供应商参照窗口（手机）
     * @return
     * @throws Exception
     * @author limin
     * @date Aug 17, 2017
     */
    public String supplier() throws Exception {
        return SUCCESS;
    }
}

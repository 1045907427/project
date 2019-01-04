package com.hd.agent.basefiles.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Bom;
import com.hd.agent.basefiles.model.BomDetail;
import com.hd.agent.basefiles.service.IBomService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import org.apache.commons.lang3.StringUtils;

/**
 * Bom Action
 * @author limin
 * @date Sep 21, 2015
 */
public class BomAction extends BaseFilesAction {

    /**
     *
     */
    private static final long serialVersionUID = -7034250181121073650L;

    /**
     * bom service
     */
    private IBomService bomService;

    private Bom bom;

    public IBomService getBomService() {
        return bomService;
    }

    public void setBomService(IBomService bomService) {
        this.bomService = bomService;
    }

    public Bom getBom() {
        return bom;
    }

    public void setBom(Bom bom) {
        this.bom = bom;
    }

    /**
     * BOM页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 21, 2015
     */
    public String bomPage() throws Exception {

        String id = request.getParameter("id");
        String type = request.getParameter("type");
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        Bom bom = bomService.selectBom(id);
        request.setAttribute("bom", bom);

        if(bom == null) {

            return SUCCESS;
        }

        if("view".equals(type) || "edit".equals(type)) {

            pageMap.getCondition().put("billid", id);
            pageMap.getCondition().put("type", "2");
            pageMap.setRows(9999);

            List list = bomService.selectBomDetailList(pageMap).getList();
            request.setAttribute("list", JSONUtils.listToJsonStr(list));
        }
        return SUCCESS;
    }

    /**
     * BOM列表页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 21, 2015
     */
    public String bomListPage() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);

        PageData pageData = bomService.selectBomList(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * BOM列表页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 21, 2015
     */
    public String bomAddPage() throws Exception {

        request.setAttribute("autoCreate", isAutoCreate("t_base_bom"));
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS;
    }

    /**
     * BOM列表页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 23, 2015
     */
    public String bomEditPage() throws Exception {

        String id = request.getParameter("id");

        Bom bom = bomService.selectBom(id);

        if(bom == null) {

            return SUCCESS;
        }

        pageMap.getCondition().put("billid", id);
        pageMap.getCondition().put("type", "2");
        pageMap.setRows(9999);

        List list = bomService.selectBomDetailList(pageMap).getList();

        request.setAttribute("bom", bom);
        request.setAttribute("list", list);
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS;
    }

    /**
     * BOM列表页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 24, 2015
     */
    public String bomViewPage() throws Exception {

        String id = request.getParameter("id");

        Bom bom = bomService.selectBom(id);

        if(bom == null) {

            return SUCCESS;
        }

        pageMap.getCondition().put("billid", id);
        pageMap.getCondition().put("type", "2");
        pageMap.setRows(9999);

        List list = bomService.selectBomDetailList(pageMap).getList();

        request.setAttribute("bom", bom);
        request.setAttribute("list", list);
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS;
    }

    /**
     * BOM列表页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 21, 2015
     */
    public String selectBomList() throws Exception{

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);

        PageData pageData = bomService.selectBomList(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 23, 2015
     */
    @UserOperateLog(key="BOM", type=2)
    public String addBom() throws Exception {

        String detailjson = request.getParameter("detailjson");

        if (isAutoCreate("t_base_bom")) {

            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(bom, "t_base_bom");
            bom.setId(id);
        } else {

            bom.setId("BM-" + CommonUtils.getDataNumber());
        }

        SysUser user = getSysUser();
        bom.setAdduserid(user.getUserid());
        bom.setAddusername(user.getName());
        bom.setAdddeptid(user.getDepartmentid());
        bom.setAdddeptname(user.getDepartmentname());

        List list = JSONUtils.jsonStrToList(detailjson, new BomDetail());

        int ret = bomService.addBom(bom, list);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", bom.getId());
        map.put("type", "add");
        addJSONObject(map);
        addLog("BOM单新增 编号：" + bom.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     *
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 23, 2015
     */
    @UserOperateLog(key="BOM", type=3)
    public String editBom() throws Exception {

        String detailjson = request.getParameter("detailjson");

        SysUser user = getSysUser();
        bom.setModifyuserid(user.getUserid());
        bom.setModifyusername(user.getName());

        List list = JSONUtils.jsonStrToList(detailjson, new BomDetail());

        int ret = bomService.editBom(bom, list);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", bom.getId());
        map.put("type", "add");
        addJSONObject(map);
        addLog("BOM单修改 编号：" + bom.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     * 打开BOM
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 24, 2015
     */
    @UserOperateLog(key="BOM", type=3)
    public String openBom() throws Exception {

        String id = request.getParameter("id");
        String status = request.getParameter("status");

        Bom bom = bomService.selectBom(id);
        bom.setStatus(status);

        SysUser user = getSysUser();
        bom.setModifyuserid(user.getUserid());
        bom.setModifyusername(user.getName());

        int ret = bomService.updateBomStatus(bom);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", bom.getId());
        map.put("type", "edit");
        addJSONObject(map);
        addLog("BOM单启用 编号：" + bom.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     * 关闭BOM
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 24, 2015
     */
    @UserOperateLog(key="BOM", type=3)
    public String closeBom() throws Exception {

        String id = request.getParameter("id");
        String status = request.getParameter("status");

        Bom bom = bomService.selectBom(id);
        bom.setStatus(status);

        SysUser user = getSysUser();
        bom.setModifyuserid(user.getUserid());
        bom.setModifyusername(user.getName());

        int ret = bomService.updateBomStatus(bom);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", bom.getId());
        map.put("type", "edit");
        addJSONObject(map);
        addLog("BOM单关闭 编号：" + bom.getId(), ret > 0);

        return SUCCESS;
    }

    /**
     * 删除BOM
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 24, 2015
     */
    @UserOperateLog(key="BOM", type=4)
    public String deleteBom() throws Exception {

        String id = request.getParameter("id");

        if(StringUtils.isEmpty(id)) {
            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", "拆装规则未选择，删除失败！");
            addJSONObject(map);
            return SUCCESS;
        }

        boolean canDelete = canTableDataDelete("t_base_bom", id);
        if(!canDelete) {
            Map map = new HashMap();
            map.put("flag", false);
            map.put("msg", "拆装规则被引用，无法删除！");
            addJSONObject(map);
            return SUCCESS;
        }

        int ret = bomService.deleteBom(id);

        Map map = new HashMap();
        map.put("flag", ret > 0);
        map.put("backid", id);
        map.put("type", "delete");
        addJSONObject(map);
        addLog("BOM单删除 编号：" + id, ret > 0);

        return SUCCESS;
    }

    /**
     * BOM列表页面
     * @return
     * @throws Exception
     * @author limin
     * @date Sep 29, 2015
     */
    public String selectBomDetailList() throws Exception{

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);

        PageData pageData = bomService.selectBomDetailList(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }
}

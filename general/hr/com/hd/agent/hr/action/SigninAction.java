/**
 * @(#)SigninAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-18 limin 创建版本
 */
package com.hd.agent.hr.action;

import java.util.*;

import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.ExcelUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.hr.model.Signin;
import com.hd.agent.hr.service.ISigninService;

/**
 * 签到管理Action
 * 
 * @author limin
 */
public class SigninAction extends BaseAction {

	/**  */
	private static final long serialVersionUID = -1108102060731270420L;

	private ISigninService signinService;
	
	public ISigninService getSigninService() {
		return signinService;
	}

	public void setSigninService(ISigninService signinService) {
		this.signinService = signinService;
	}

	/**
	 * 签到管理页面
	 * @return
	 * @author limin 
	 * @date 2014-9-18
	 */
	public String signinPage() {
		
		return SUCCESS;
	}
	
	/**
	 * 签到管理列表页面
	 * @return
	 * @author limin 
	 * @date 2014-9-18
	 */
	public String signinListPage() {
		
		return SUCCESS;
	}
	
	/**
	 * 查询签到一览
	 * @return
	 * @author limin 
	 * @throws Exception 
	 * @date 2014-9-18
	 */
	public String selectSigninList() throws Exception {
		
		Map map = CommonUtils.changeMap(request.getParameterMap());
		
		String userid = (String) map.get("userid");
		if(StringUtils.isNotEmpty(userid)) {

			String[] ids = userid.split(",");
            List<String> idList = Arrays.asList(ids);
			map.put("userid", idList);
		}
		
		pageMap.setCondition(map);
		
		PageData pageData = signinService.selectSigninList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 查看签到详情页面
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-9-20
	 */
	public String signinViewPage() throws Exception {
		
		String id = request.getParameter("id");
		Signin signin = signinService.selectSigninInfo(id);
		request.setAttribute("signin", signin);
		return SUCCESS;
	}
	
	/**
	 * 获取签到详情
	 * @return
	 * @throws Exception
	 * @author limin 
	 * @date 2014-9-20
	 */
	public String selectSigninInfo() throws Exception {
		
		String id = request.getParameter("id");
		String index = request.getParameter("index");
		Signin signin = signinService.selectSigninInfo(id);
		
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("x", signin.getAmbeginx());
		map1.put("y", signin.getAmbeginy());
		if(signin.getAmbegin() != null) {
			map1.put("time", CommonUtils.dataToStr(signin.getAmbegin(), "yyyy-MM-dd HH:mm:ss"));
		}
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("x", signin.getAmendx());
		map2.put("y", signin.getAmendy());
		if(signin.getAmend() != null) {
			map2.put("time", CommonUtils.dataToStr(signin.getAmend(), "yyyy-MM-dd HH:mm:ss"));
		}
		
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("x", signin.getPmbeginx());
		map3.put("y", signin.getPmbeginy());
		if(signin.getPmbegin() != null) {
			map3.put("time", CommonUtils.dataToStr(signin.getPmbegin(), "yyyy-MM-dd HH:mm:ss"));
		}
		
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("x", signin.getPmendx());
		map4.put("y", signin.getPmendy());
		if(signin.getPmend() != null) {
			map4.put("time", CommonUtils.dataToStr(signin.getPmend(), "yyyy-MM-dd HH:mm:ss"));
		}
		
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("x", signin.getOutx());
		map5.put("y", signin.getOuty());
		if(signin.getOuttime() != null) {
			map5.put("time", CommonUtils.dataToStr(signin.getOuttime(), "yyyy-MM-dd HH:mm:ss"));
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		
		Map map = list.get(Integer.parseInt(index));

		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示坐标
	 * @return
	 * @author limin 
	 * @date 2014-9-22
	 */
	public String signinLocationPage() {

		return SUCCESS;
	}

	/**
	 * 删除签到信息
	 *
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date 2015-3-12
     */
	@UserOperateLog(key="Signin", type=4)
    public String deleteSignin() throws Exception {

        String ids = request.getParameter("ids");
		Map map = new HashMap();

		if(StringUtils.isEmpty(ids)) {

			map.put("flag", false);
			map.put("msg", "未选择签到！");
			addJSONObject(map);
			return SUCCESS;
		}

        Map ret = signinService.deleteSignin(ids);
		int success = (Integer) ret.get("success");
		int failure = (Integer) ret.get("failure");
		String successIds = (String) ret.get("successIds");

		map.put("flag", true);
		map.put("success", success);
		map.put("failure", failure);
		addJSONObject(map);
		if(success > 0) {
			addLog("删除签到 编号：" + successIds, true);
		}
        return SUCCESS;
    }

    /**
     * 导出签到记录
     *
     * @throws Exception
     * @author limin
     * @date Sep 19, 2016
     */
    public void exportSignin() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());

        String userid = (String) map.get("userid");
        if(StringUtils.isNotEmpty(userid)) {

            String[] ids = userid.split(",");
            List<String> idList = Arrays.asList(ids);
            map.put("userid", idList);
        }

        pageMap.setCondition(map);
        pageMap.setRows(99999999);

        PageData data = signinService.selectSigninList(pageMap);

        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        } else {
            title = "签到记录";
        }

        // 数据转换，list转化符合excel导出的数据格式
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();

        firstMap.put("businessdate", "日期");
        firstMap.put("username", "用户");
        firstMap.put("deptname", "部门");
        firstMap.put("ambegin", "上午上班");
        firstMap.put("amend", "上午下班");
        firstMap.put("pmbegin", "下午上班");
        firstMap.put("pmend", "下午下班");
        firstMap.put("outtime", "外出");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        List<Signin> list = data.getList();

        for(Signin record : list){
            if(null != record){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(record);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map2.entrySet()){
                            if(fentry.getKey().equals(entry.getKey())){
                                objectCastToRetMap(retMap,entry);
                            }
                        }
                    }else{
                        retMap.put(fentry.getKey(), "");
                    }
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }
}


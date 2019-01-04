package com.hd.agent.basefiles.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.CustomerUser;
import com.hd.agent.basefiles.service.ICustomerUserService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanghongteng on 2017/5/26.
 */
public class CustomerUserAction extends FilesLevelAction{

    private CustomerUser customerUser;

    public CustomerUser getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(CustomerUser customerUser) {
        this.customerUser = customerUser;
    }

    private ICustomerUserService customerUserService;

    public ICustomerUserService getCustomerUserService() {
        return customerUserService;
    }

    public void setCustomerUserService(ICustomerUserService customerUserService) {
        this.customerUserService = customerUserService;
    }

    /**
     * 显示客户用户列表页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/26
     */
    public String showCustomerUserListPage() throws Exception {
        return "success";
    }

    /**
     * 获取客户用户列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/26
     */
    public String getCustomerUserList() throws Exception {
        SysUser sysUser = getSysUser();
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("userid", sysUser.getUserid());

        pageMap.setCondition(map);
        PageData pageData = customerUserService.getCustomerUserList(pageMap);
        addJSONObject(pageData);

        return SUCCESS;
    }

    /**
     * 显示客户用户绑定客户新增页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/26
     */
    public String showCustomerUserAddCustomerPage() throws Exception {
        String mobilephone=request.getParameter("mobilephone");
        request.setAttribute("mobilephone",mobilephone);
        return "success";
    }

    /**
     * 客户用户新增绑定用户
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/26
     */
    public String addCustomerUserAddCustomer() throws Exception {
        String mobilephone=customerUser.getMobilephone();
        String customerid=customerUser.getCustomerid();
        boolean flag = customerUserService.addCustomerUserAddCustomer(mobilephone,customerid);
        addJSONObject("flag", flag);
        return "success";
    }

    /**
     * 显示客户用户绑定客户修改页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/26
     */
    public String showCustomerUserEditCustomerPage() throws Exception {
        String mobilephone=request.getParameter("mobilephone");
        request.setAttribute("mobilephone",mobilephone);
        return "success";
    }

    /**
     * 启用客户用户列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/26
     */
    @UserOperateLog(key="customerUser",type=0,value="")
    public String enableCustomerUser() throws Exception {
        String mobilephones = request.getParameter("mobilephones");
        String openstr = "",unsucstr = "",msg = "",sucstr = "";
        int opennum = 0,sucnum = 0,unsucnum = 0;
        String[] mobilephoneArr = mobilephones.split(",");
        for(String mobilephone : mobilephoneArr){
            CustomerUser customerUser = customerUserService.showCustomerUserInfo(mobilephone);
            if("1".equals(customerUser.getState())){
                opennum++;
                if(StringUtils.isEmpty(openstr)){
                    openstr = customerUser.getMobilephone();
                }else{
                    openstr += "," + customerUser.getMobilephone();
                }
            }else{
                boolean flag = customerUserService.enableCustomerUser(mobilephone);
                if(flag){
                    sucnum++;
                    if(StringUtils.isEmpty(sucstr)){
                        sucstr = customerUser.getMobilephone();
                    }else{
                        sucstr += "," + customerUser.getMobilephone();
                    }
                }else{
                    unsucnum++;
                    if(StringUtils.isEmpty(unsucstr)){
                        unsucstr = customerUser.getMobilephone();
                    }else{
                        unsucstr += "," + customerUser.getMobilephone();
                    }
                }
            }
        }
        if(StringUtils.isNotEmpty(openstr)){
            if(StringUtils.isEmpty(msg)){
                msg = "用户:" + openstr + "已启用,共" + opennum + "条数据;<br>";
            }else{
                msg += "用户:" + openstr + "已启用,共" + opennum + "条数据;<br>";
            }
        }
        if(StringUtils.isNotEmpty(unsucstr)){
            if(StringUtils.isEmpty(msg)){
                msg = "用户:" + unsucstr + "启用失败,共" + unsucnum + "条数据;<br>";
            }else{
                msg += "用户:" + unsucstr + "启用失败,共" + unsucnum + "条数据;<br>";
            }
        }
        if(StringUtils.isNotEmpty(sucstr)){
            if(StringUtils.isEmpty(msg)){
                msg = "用户:" + sucstr + "启用成功,共" + sucnum + "条数据;<br>";
            }else{
                msg += "用户:" + sucstr + "启用成功,共" + sucnum + "条数据;<br>";
            }
        }
        if(sucnum > 0){
            //添加日志内容
            addLog("启用客户用户 手机号:"+sucstr,true);
        }
        addJSONObject("msg", msg);
        return "success";
    }


    /**
     * 禁用客户用户
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/26
     */
    @UserOperateLog(key="customerUser",type=0,value="")
    public String disableCustomerUser() throws Exception {
        String mobilephones = request.getParameter("mobilephones");
        String closestr = "",unsucstr = "",msg = "",sucstr = "";
        int closenum = 0,sucnum = 0,unsucnum = 0;
        String[] mobilephoneArr = mobilephones.split(",");
        for(String mobilephone : mobilephoneArr){
            CustomerUser customerUser = customerUserService.showCustomerUserInfo(mobilephone);
            if("0".equals(customerUser.getState())){
                closenum++;
                if(StringUtils.isEmpty(closestr)){
                    closestr = customerUser.getMobilephone();
                }else{
                    closestr += "," + customerUser.getMobilephone();
                }
            }else{
                boolean flag = customerUserService.disableCustomerUser(mobilephone);
                if(flag){
                    sucnum++;
                    if(StringUtils.isEmpty(sucstr)){
                        sucstr = customerUser.getMobilephone();
                    }else{
                        sucstr += "," + customerUser.getMobilephone();
                    }
                }else{
                    unsucnum++;
                    if(StringUtils.isEmpty(unsucstr)){
                        unsucstr = customerUser.getMobilephone();
                    }else{
                        unsucstr += "," + customerUser.getMobilephone();
                    }
                }
            }
        }
        if(StringUtils.isNotEmpty(closestr)){
            if(StringUtils.isEmpty(msg)){
                msg = "用户:" + closestr + "已禁用,共" + closenum + "条数据;<br>";
            }else{
                msg += "用户:" + closestr + "已禁用,共" + closenum + "条数据;<br>";
            }
        }
        if(StringUtils.isNotEmpty(unsucstr)){
            if(StringUtils.isEmpty(msg)){
                msg = "用户:" + unsucstr + "禁用失败,共" + unsucnum + "条数据;<br>";
            }else{
                msg += "用户:" + unsucstr + "禁用失败,共" + unsucnum + "条数据;<br>";
            }
        }
        if(StringUtils.isNotEmpty(sucstr)){
            if(StringUtils.isEmpty(msg)){
                msg = "用户:" + sucstr + "禁用成功,共" + sucnum + "条数据;<br>";
            }else{
                msg += "用户:" + sucstr + "禁用成功,共" + sucnum + "条数据;<br>";
            }
        }
        if(sucnum > 0){
            //添加日志内容
            addLog("禁用客户用户 手机号:"+sucstr,true);
        }
        addJSONObject("msg", msg);
        return "success";
    }
}

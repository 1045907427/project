package com.hd.agent.basefiles.service.impl;

import com.hd.agent.basefiles.dao.CustomerUserMapper;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.CustomerUser;
import com.hd.agent.basefiles.service.ICustomerUserService;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.sales.model.Order;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by wanghongteng on 2017/5/24.
 */
public class CustomerUserServiceImpl extends FilesLevelServiceImpl implements ICustomerUserService {

    private CustomerUserMapper customerUserMapper;

    public CustomerUserMapper getCustomerUserMapper() {
        return customerUserMapper;
    }

    public void setCustomerUserMapper(CustomerUserMapper customerUserMapper) {
        this.customerUserMapper = customerUserMapper;
    }

    /**
     * 根据手机号码获取客户用户
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public CustomerUser showCustomerUserInfo(String mobilephone){
        CustomerUser customerUser = customerUserMapper.showCustomerUserInfo(mobilephone);
        return customerUser;
    }

    /**
     * 客户用户绑定客户
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public boolean addCustomerUserAddCustomer(String mobilephone,String customerid){
        boolean flag = customerUserMapper.addCustomerUserAddCustomer(mobilephone,customerid)>0;
        return flag;
    }
    /**
     * 检测微信注册手机号码是否重复
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public boolean checkRepeatPhone(String phone)throws Exception{
        return customerUserMapper.checkRepeatPhone(phone) == 0;
    }

    /**
     * 注册客户用户
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public boolean addInviteCustomerUser(CustomerUser customerUser)throws Exception{
        boolean flag = customerUserMapper.addCustomerUser(customerUser)>0;
        return flag;
    }

    /**
     * 获取客户用户列表
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/26
     */
    public PageData getCustomerUserList(PageMap pageMap) throws Exception{
//        String sql = getDataAccessRule("t_sales_order", null); //数据权限
//        pageMap.setDataSql(sql);
        List<CustomerUser> customerUserList = customerUserMapper.SelectCustomerUserList(pageMap);
        for (CustomerUser customerUser : customerUserList) {
            String customerid = customerUser.getCustomerid();
            Customer customer = getBaseCustomerMapper().getCustomerById(customerid);
            if(null!=customer){
                customerUser.setCustomername(customer.getName());
            }

            if(StringUtils.isNotEmpty(customerUser.getState())){ //状态
                SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(customerUser.getState(), "state");
                if(sysCode != null){
                    customerUser.setStateName(sysCode.getCodename());
                }
            }
        }
        return new PageData(customerUserMapper.SelectCustomerUserListCount(pageMap), customerUserList, pageMap);
    }

    /**
     * 启用系统用户
     * @param userid
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2013-3-18
     */
    public boolean enableCustomerUser(String mobilephone)throws Exception{
        int i = customerUserMapper.enableCustomerUser(mobilephone);
        return i > 0;
    }

    /**
     * 禁用系统用户
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2013-3-18
     */
    public boolean disableCustomerUser(String mobilephone)throws Exception{
        int i = customerUserMapper.disableCustomerUser(mobilephone);
        return i > 0;
    }

}

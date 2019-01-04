package com.hd.agent.basefiles.service;


import com.hd.agent.basefiles.model.CustomerUser;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * Created by wanghongteng on 2017/5/24.
 */
public interface ICustomerUserService {
    /**
     * 根据手机号码获取客户用户
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public CustomerUser showCustomerUserInfo(String mobilephone);

    /**
     * 客户用户绑定客户
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public boolean addCustomerUserAddCustomer(String mobilephone,String customerid);

    /**
     * 检测微信注册手机号码是否重复
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public boolean checkRepeatPhone(String phone)throws Exception;

    /**
     * 检测微信注册手机号码是否重复
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public boolean addInviteCustomerUser(CustomerUser customerUser)throws Exception;

    /**
     * 获取客户用户列表
     * @param pageMap
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/26
     */
    public PageData getCustomerUserList(PageMap pageMap) throws Exception;

    /**
     * 启用客户用户
     * @param mobilephone
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/26
     */
    public boolean enableCustomerUser(String mobilephone)throws Exception;

    /**
     * 禁用客户用户
     * @param mobilephone
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/26
     */
    public boolean disableCustomerUser(String mobilephone)throws Exception;
}

package com.hd.agent.account.service.ext;

import com.hd.agent.account.model.CollectionOrder;

import java.util.List;
import java.util.Map;

public interface ICollectionOrderForOaService {

    /**
     * 根据oaid查询收款单
     * @param oaid
     * @return
     * @author limin
     * @date Mar 29, 2016
     */
    public List<CollectionOrder> selectCollectionOrderByOaid(String oaid) throws Exception;

    /**
     * 收款单添加
     * @param collectionOrder
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 6, 2013
     */
    public boolean addCollectionOrder(CollectionOrder collectionOrder) throws Exception;

    /**
     * 收款单审核
     * @param id			单据编号
     * @param issuper  		是否超级审核
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jul 8, 2013
     */
    public Map auditCollectionOrder(String id, boolean issuper) throws Exception;

}

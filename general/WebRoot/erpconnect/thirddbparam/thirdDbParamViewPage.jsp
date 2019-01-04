<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
<style type="text/css">
    .lefttable{
        width: 100%;
        height:40px;
        line-height:40px;
        text-align: center;
        cursor:default;
        /*background-color: #038bbe;*/
    }
</style>

<div id="ledger-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
    <form id="ledger-form-AccountSetAddPage" method="post" action="thirdDb/editThirdDbParam.do">
        <div id="ledger-layout-detail-north" data-options="region:'north',border:false"
             style="height:270px" >
            <input type="hidden" name="erpDb.id" value="${erpDb.id}"/>
            <table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
                <tr>
                    <td>接入系统版本</td>
                    <td>
                        <select name="erpDb.relatesystem" disabled="disabled" style="width: 150px">
                            <option VALUE="T3" <c:if test="${erpDb.relatesystem=='T3'}">selected</c:if>>用友T3</option>
                            <option VALUE="T6" <c:if test="${erpDb.relatesystem=='T6'}">selected</c:if>>用友T6</option>
                            <option VALUE="U8" <c:if test="${erpDb.relatesystem=='U8'}">selected</c:if>>用友U8</option>
                        </select>
                    </td>
                    <td>帐套名称</td>
                    <td>
                        <input type="text" readonly="readonly"  name="erpDb.dbasename" class="easyui-validatebox" validType="validName" value="${erpDb.dbasename}"/>
                    </td>
                    <td>数据库版本</td>
                    <td>
                        <input type="text"  name="erpDb.dbversion" value="${erpDb.dbversion}" readonly="readonly" />
                    </td>

                </tr>
                <tr>
                    <td>生成凭证方式</td>
                    <td>
                        <select name="erpDb.addvouchtype" disabled="disabled" style="width: 150px">
                            <option VALUE="0" <c:if test="${erpDb.addvouchtype=='0'}">selected</c:if>>一单一证</option>
                            <option VALUE="1" <c:if test="${erpDb.addvouchtype=='1'}">selected</c:if>>汇总</option>
                        </select>
                    </td>
                    <td>对应部门</td>
                    <td>
                        <select id="erp-deptid-connectionAddPage" disabled="disabled" class="len150" name="erpDb.deptid">
                            <option value=""></option>
                            <c:forEach items="${deptList}" var="list">
                                <c:choose>
                                    <c:when test="${list.id == erpDb.deptid}">
                                        <option value="${list.id }" selected="selected">${list.name }</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${list.id }">${list.name }</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                    <td>状态</td>
                    <td>
                        <select name="erpDb.state" disabled="disabled" style="width: 150px">
                            <option <c:if test="${erpDb.state=='0'}">selected</c:if>> 禁用</option>
                            <option <c:if test="${erpDb.state=='1'}">selected</c:if>> 启用</option>
                        </select>
                    </td>

                </tr>
                <tr>
                    <td>接口方式</td>
                    <td>
                        <select name="erpDb.interfacetype" disabled="disabled" style="width: 150px" id="ledger-interfacetype-thirdDbParamEditPage" >
                            <option VALUE="0" <c:if test="${erpDb.interfacetype=='0'}">selected</c:if>>数据库直连</option>
                            <option VALUE="1" <c:if test="${erpDb.interfacetype=='1'}">selected</c:if>>openAPI</option>
                        </select>
                    </td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="ledger-test-thirdDbParamEditPage" class="easyui-linkbutton button-list"
                           data-options="plain:true,iconCls:'button-edit'">账套连接测试</a>
                    </td>
                    <td>是否默认账套</td>
                    <td>
                        <select name="erpDb.isdefault" disabled="disabled" style="width: 150px;" >
                            <option VALUE="0" <c:if test="${erpDb.isdefault=='0'}">selected</c:if>>是</option>
                            <option VALUE="1" <c:if test="${erpDb.isdefault=='1'}">selected</c:if>>否</option>
                        </select>
                    </td>
                </tr>
                <tr id="ledger-dbsource">
                    <td>数据库ip</td>
                    <td>
                        <input type="text"  name="erpDb.dbIP" id="ledger-dbIP-thirdDbParamEditPage" readonly="readonly" class="easyui-validatebox" value="${erpDb.dbIP}"/>
                    </td>
                    <td>用户名</td>
                    <td>
                        <input type="text"  name="erpDb.dbusername" id="ledger-dbusername-thirdDbParamEditPage" readonly="readonly" class="easyui-validatebox" value="${erpDb.dbusername}"/>
                    </td>
                    <td>密码</td>
                    <td>
                        <input type="password"  name="erpDb.dbpassword" id="ledger-dbpassword-thirdDbParamEditPage" readonly="readonly" class="easyui-validatebox" value="${erpDb.dbpassword}"/>
                    </td>
                </tr>
                <tr id="ledger-openapi-tr1">
                    <td>用友调用方</td>
                    <td><input type="text"  value="${erpDb.from_account}" id="ledger-from_account-thirdDbParamEditPage" readonly="readonly"  class="easyui-validatebox"/></td>
                    <td>用友提供方</td>
                    <td><input type="text"  value="${erpDb.to_account}" id="ledger-to_account-thirdDbParamEditPage" readonly="readonly" class="easyui-validatebox"/></td>
                    <td>用友应用key</td>
                    <td><input type="text"  value="${erpDb.app_key}" id="ledger-app_key-thirdDbParamEditPage" readonly="readonly" class="easyui-validatebox"/></td>
                </tr>
                <tr id="ledger-openapi-tr2">
                    <td class="dbconnect">数据库名</td>
                    <td class="dbconnect">
                        <input type="text"  name="erpDb.dbname" class="easyui-validatebox" readonly="readonly" value="${erpDb.dbname}" id="ledger-dbname-thirdDbParamEditPage"/>
                    </td>
                    <td class="openapiconnect">用友应用密码</td>
                    <td class="openapiconnect"><input type="text" name="erpDb.app_secret" readonly="readonly" value="${erpDb.app_secret}" id="ledger-app_secret-thirdDbParamEditPage" class="easyui-validatebox"/></td>
                    <td class="openapiconnect">数据源序号</td>
                    <td class="openapiconnect"><input type="text"  name="erpDb.dbsourceseq" readonly="readonly"  value="${erpDb.dbsourceseq}" id="ledger-dbsourceseq-thirdDbParamEditPage" /></td>
                    <%--<td colspan="2">--%>
                        <%--<a href="javaScript:void(0);" id="ledger-dbsource-thirdDbParamEditPage" class="easyui-linkbutton button-list"--%>
                           <%--data-options="plain:true,iconCls:'button-edit'">数据源参数</a>--%>
                    <%--</td>--%>
                    <td>启用档案对应科目</td>
                    <td>
                        <select id="ledger-useRelation-thirdDbParamEditPage" onchange="changeRelation()" name="erpDb.useRelation" disabled="disabled" style="width: 150px;">
                            <option value="0" <c:if test="${erpDb.useRelation==0}">selected</c:if>>否</option>
                            <option value="1" <c:if test="${erpDb.useRelation==1}">selected</c:if>>是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>是否取总店</td>
                    <td>
                        <select id="ledger-isusepid-thirdDbParamEditPage" style="width: 150px;" disabled="disabled" name="isusepid" >
                            <option value="0" <c:if test="${erpp.isusepid==0}">selected</c:if>>否</option>
                            <option value="1" <c:if test="${erpp.isusepid==1}">selected</c:if>>是</option>
                        </select>
                    </td>
                    <td>生成凭证格式</td>
                    <td>
                        <select id="ledger-ledgerformat-thirdDbParamEditPage" style="width: 150px;" disabled="disabled" name="ledgerformat" >
                            <option <c:if test="${erpp.ledgerformat==0}">selected</c:if> value="0">一借一贷</option>
                            <option <c:if test="${erpp.ledgerformat==1}">selected</c:if> value="1">多借多贷</option>
                        </select>
                    </td>
                </tr>
            </table>

            <ul class="tags">
                <security:authorize url="/basefiles/showSubjectTab.do">
                    <li class="selectTag"><a href="javascript:void(0)">基础档案关系</a></li>
                </security:authorize>
                <security:authorize url="/basefiles/showPurchaseTab.do">
                    <li><a href="javascript:void(0)">业务参数配置</a></li>
                </security:authorize>
                <security:authorize url="/basefiles/showSaleTab.do">
                    <li style="width: 200px;"><a style="width: 200px;" href="javascript:void(0)">相关档案与会计科目对应关系</a></li>
                </security:authorize>
                <security:authorize url="/basefiles/showSaleTab.do">
                    <li style="width: 180px;"><a style="width: 180px;" href="javascript:void(0)">会计科目与商品对应关系</a></li>
                </security:authorize>
                <security:authorize url="/basefiles/showSubjectTaxTab.do">
                    <li style="width: 180px;"><a style="width: 180px;" href="javascript:void(0)">会计科目与税种对应关系</a></li>
                </security:authorize>
            </ul>
        </div>
        <div id="ledger-layout-detail-center" data-options="region:'center',border:false">
            <div class="tagsDiv">
                <div class="tagsDiv_item" style="display:block;">
                    <div class="div_tr">
                        <div class="div_td" style="width: 100px;">供应商档案</div>
                        <div class="div_td" style="width:70%;">
                            <select id="erpparam-supplierCode" name="supplierCode" style="width: 200px;">
                                <option VALUE="id" <c:if test="${erpp.supplierCode=='id'}">selected="selected"</c:if>>编码关联</option>
                                <option VALUE="spell" <c:if test="${erpp.supplierCode=='spell'}">selected="selected"</c:if>>助记符关联</option>
                            </select>
                            <%--<a href="javaScript:void(0);" id="erpconnect-button-syncSupplier" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">供应商档案同步到ERP</a>--%>
                        </div>
                    </div>
                    <div class="div_tr">
                        <div class="div_td" style="width: 100px;">客户档案</div>
                        <div class="div_td" style="width:70%;">
                            <select id="erpparam-customerCode" name="customerCode" style="width: 200px;">
                                <option VALUE="id" <c:if test="${erpp.customerCode=='id'}">selected="selected"</c:if>>编码关联</option>
                                <option VALUE="spell" <c:if test="${erpp.customerCode=='spell'}">selected="selected"</c:if>>助记符关联</option>
                            </select>
                            <%--<a href="javaScript:void(0);" id="erpconnect-button-syncCustomer" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">客户档案同步到ERP</a>--%>
                        </div>
                    </div>
                </div>
                <div class="tagsDiv_item">
                    <table>
                        <tr>
                            <td align="right" style="width: 140px">版本参数：</td>
                            <td>
                                <select id="erpparam-versionParam" name="versionParam" disabled="disabled" style="width: 200px;">
                                    <option VALUE="0" <c:if test="${erpp.versionParam=='0'}">selected="selected"</c:if>>通用参数0</option>
                                    <option VALUE="1" <c:if test="${erpp.versionParam=='1'}">selected="selected"</c:if>>通用参数1</option>
                                </select>
                            </td>
                        </tr>
                        <th colspan="5" align="left">采购进货单凭证接入配置</th>
                        <tr>
                            <td align="right" style="width: 180px"><a style="color: #1006F1">采购进货凭证</a>&nbsp;借方科目：</td>
                            <td>
                                <input id="erpparam-purchaseDcode" type="text" name="purchaseDcode" style="width: 200px;" value="${erpp.purchaseDcode}"/>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-purchaseCcode" type="text" name="purchaseCcode" style="width: 200px;" value="${erpp.purchaseCcode}"/>
                            </td>
                        </tr>
                        <%--<c:if test="${erpp.versionParam == 1}">--%>
                        <tr class="version1">
                            <td align="right" style="width: 180px"><a style="color: #1006F1">采购进货冲暂估</a>&nbsp;借方科目：</td>
                            <td>
                                <input id="erpparam-purchaseEstimateDcode" type="text" name="purchaseEstimateDcode" style="width: 200px;" value="${erpp.purchaseEstimateDcode}"/>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-purchaseEstimateCcode" type="text" name="purchaseEstimateCcode" style="width: 200px;" value="${erpp.purchaseEstimateCcode}"/>
                            </td>
                        </tr>
                        <%--</c:if>--%>
                        <th colspan="4" align="left">采购退货凭证接入配置</th>
                        <tr>
                            <td align="right" style="width: 140px">借方科目：</td>
                            <td>
                                <input id="erpparam-purchaseRejectDcode" type="text" name="purchaseRejectDcode" style="width: 200px;" value="${erpp.purchaseRejectDcode}"/>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-purchaseRejectCcode" type="text" name="purchaseRejectCcode" style="width: 200px;" value="${erpp.purchaseRejectCcode}"/>
                            </td>
                        </tr>
                        <%--<c:if test="${erpp.versionParam == 0}">--%>
                            <th class="version0" colspan="4" align="left">货款支付凭证接入配置  来源：采购发票</th>
                            <tr class="version0">
                                <td align="right" style="width: 180px">&nbsp;借方科目：</td>
                                <td>
                                    <input id="erpparam-paymentPushDcode" type="text" name="paymentPushDcode" style="width: 200px;" value="${erpp.paymentPushDcode}"/>
                                </td>
                                <td align="right" style="width: 140px">贷方科目：</td>
                                <td>
                                    <input id="erpparam-paymentTotalCcode" type="text" name="paymentTotalCcode" style="width: 200px;" value="${erpp.paymentTotalCcode}"/>
                                </td>
                                <td>
                                    <input id="erpparam-paymentPushCcode" type="text" name="paymentPushCcode" style="width: 200px;" value="${erpp.paymentPushCcode}"/>
                                </td>
                            </tr>
                        <%--</c:if>--%>
                        <%--<c:if test="${erpp.versionParam == 1}">--%>
                        <th class="version1" colspan="6" align="left">采购发票凭证接入配置</th>
                        <tr class="version1">
                            <td align="right" style="width: 140px">借方科目：</td>
                            <td>
                                <input id="erpparam-PushNotaxamountDcode" type="text" name="pushNotaxamountDcode" style="width: 200px;" value="${erpp.pushNotaxamountDcode}"/>
                            </td>
                            <td></td>
                            <td>
                                <input id="erpparam-PushAdjustDcode" type="text" name="pushAdjustDcode" style="width: 200px;" value="${erpp.pushAdjustDcode}"/>
                            </td>
                            <td></td>
                            <td>
                                <input id="erpparam-pushTaxamountCcode" type="text" name="pushTaxDcode" style="width: 200px;" value="${erpp.pushTaxDcode}"/>
                            </td>
                        </tr>
                        <tr class="version1">
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-purchasePushCcode" type="text" name="pushTaxamountCcode" style="width: 200px;" value="${erpp.pushTaxamountCcode}"/>
                            </td>
                        </tr>
                        <%--</c:if>--%>
                        <th colspan="4" align="left">销售凭证接入配置</th>
                        <tr>
                            <td align="right" style="width: 140px">接入数据（销售报表）：</td>
                            <td>
                                <select disabled="disabled" id="erpparam-salesDataType" name="salesDataType" style="width: 200px;">
                                    <option VALUE="1" <c:if test="${erpp.salesDataType=='1'}">selected="selected"</c:if>>发货出库金额+冲差金额-退货金额</option>
                                    <option VALUE="2" <c:if test="${erpp.salesDataType=='2'}">selected="selected"</c:if>>发货单金额+冲差金额-退货金额</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right" style="width: 140px">借方科目：</td>
                            <td>
                                <input id="erpparam-salesDcode" type="text" name="salesDcode" style="width: 200px;" value="${erpp.salesDcode}"/>
                            </td>
                            <%--<c:if test="${erpp.versionParam == 1}">--%>
                                <td class="version1" align="right" style="width: 140px">贷方科目（未税）：</td>
                                <td class="version1">
                                    <input id="erpparam-salesNotaxamountCcode"  type="text" name="salesNotaxamountCcode" style="width: 200px;"  value="${erpp.salesNotaxamountCcode}"/>
                                </td>
                                <td class="version1" align="right" style="width: 140px">贷方科目（税额）：</td>
                                <td class="version1">
                                    <input id="erpparam-salesTaxCcode" type="text" name="salesTaxCcode" style="width: 200px;" value="${erpp.salesTaxCcode}"/>
                                </td>
                            <%--</c:if>--%>
                            <%--<c:if test="${erpp.versionParam == 0}">--%>
                                <td class="version0" align="right" style="width: 140px">贷方科目：</td>
                                <td class="version0">
                                    <input id="erpparam-salesNotaxamountCcode1"  type="text" name="salesNotaxamountCcode1" style="width: 200px;"  value="${erpp.salesNotaxamountCcode1}"/>
                                </td>
                            <%--</c:if>--%>
                        </tr>
                        <th colspan="8" align="left">主营业务成本接入配置</th>
                        <tr>
                            <td align="right" style="width: 180px">借方科目：</td>
                            <td>
                                <input id="erpparam-mainCostDcode" type="text" name="mainCostDcode" style="width: 200px;" value="${erpp. mainCostDcode}"/>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-mainCostCcode" type="text" name="mainCostCcode" style="width: 200px;" value="${erpp. mainCostCcode}"/>
                            </td>
                        </tr>
                        <th colspan="8" align="left">收款支付凭证接入配置  来源：回笼表</th>
                        <tr>
                            <td align="right" style="width: 180px">借方科目：</td>
                            <td>
                                <%--<c:if test="${isUserRelationSubject=='0'}">--%>
                                <div class="isUserRelationSubject0">
                                    <input class="isUserRelationSubject0" id="erpparam-withdrawalDcode" type="text" name="withdrawalDcode" style="width: 200px;" value="${erpp. withdrawalDcode}"/>
                                </div>

                                <%--</c:if>--%>
                                <%--<c:if test="${isUserRelationSubject=='1'}">--%>
                                    <input class="isUserRelationSubject1"  type="text" style="width: 200px;" value="" readonly="readonly"/>
                                <%--</c:if>--%>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-withdrawalCcode" type="text" name="withdrawalCcode" style="width: 200px;" value="${erpp. withdrawalCcode}"/>
                            </td>
                        </tr>
                        <th colspan="8" align="left">收款支付凭证接入配置  来源：收款单 </th>
                        <tr>
                            <td align="right" style="width: 180px">借方科目：</td>
                            <td>
                                <%--<c:if test="${isUserRelationSubject=='0'}">--%>
                                <div class="isUserRelationSubject0">
                                    <input id="erpparam-collectionDcode" type="text" name="collectionDcode" style="width: 200px;" value="${erpp.collectionDcode}"/>
                                </div>

                                <%--</c:if>--%>
                                <%--<c:if test="${isUserRelationSubject=='1'}">--%>
                                    <input class="isUserRelationSubject1"  type="text" style="width: 200px;" value="" readonly="readonly"/>
                                <%--</c:if>--%>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-collectionCcode" type="text" name="collectionCcode" style="width: 200px;" value="${erpp.collectionCcode}"/>
                            </td>
                        </tr>
                        <th colspan="4" align="left">货款支付凭证接入配置  来源：付款单</th>
                        <tr>
                            <td align="right" style="width: 180px">借方科目：</td>
                            <td>
                                <input id="erpparam-paymentDcode" type="text" name="paymentDcode" style="width: 200px;" value="${erpp.paymentDcode}"/>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <%--<c:if test="${isUserRelationSubject=='0'}">--%>
                                <div class="isUserRelationSubject0">
                                    <input id="erpparam-paymentCcode" type="text" name="paymentCcode" style="width: 200px;" value="${erpp.paymentCcode}"/>
                                </div>

                                <%--</c:if>--%>
                                <%--<c:if test="${isUserRelationSubject=='1'}">--%>
                                    <input class="isUserRelationSubject1"  type="text"  style="width: 200px;" value="" readonly="readonly"/>
                                <%--</c:if>--%>
                            </td>
                        </tr>
                        <th colspan="5" align="left">日常费用凭证接入配置</th>
                        <tr>
                            <td align="right" style="width: 180px">借方科目：</td>
                            <td>
                                <input id="erpparam-bankDailyCostpayDcode" type="text" name="bankDailyCostpayDcode" style="width: 200px;" value="${erpp.bankDailyCostpayDcode}"/>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-bankDailyCostpayCcode" type="text" name="bankDailyCostpayCcode" style="width: 200px;" value="${erpp.bankDailyCostpayCcode}"/>
                            </td>
                        </tr>

                        <th colspan="5" align="left">客户费用凭证接入配置</th>
                        <tr>
                            <td align="right" style="width: 180px">借方科目：</td>
                            <td>
                                <%--<c:if test="${isUserRelationSubject=='0'}">--%>
                                <div class="isUserRelationSubject0">
                                    <input id="erpparam-bankCustomerCostpayDcode" type="text" name="bankCustomerCostpayDcode" style="width: 200px;" value="${erpp.bankCustomerCostpayDcode}"/>
                                </div>

                                <%--</c:if>--%>
                                <%--<c:if test="${isUserRelationSubject=='1'}">--%>
                                    <input class="isUserRelationSubject1"  type="text" style="width: 200px;" value="" readonly="readonly"/>
                                <%--</c:if>--%>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-bankCustomerCostpayCcode" type="text" name="bankCustomerCostpayCcode" style="width: 200px;" value="${erpp.bankCustomerCostpayCcode}"/>
                            </td>
                        </tr>

                        <th colspan="5" align="left">代垫录入凭证接入配置</th>
                        <tr>
                            <td align="right" style="width: 180px">借方科目：</td>
                            <td>
                                <input id="erpparam-journalsheetDcode" type="text" name="journalsheetDcode" style="width: 200px;" value="${erpp.journalsheetDcode}"/>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-journalsheetCcode" type="text" name="journalsheetCcode" style="width: 200px;" value="${erpp.journalsheetCcode}"/>
                            </td>
                        </tr>
                        <th colspan="5" align="left">其它出库凭证接入配置</th>
                        <tr>
                            <td align="right" style="width: 180px">借方科目：</td>
                            <td>
                                <%--<c:if test="${isUserRelationSubject=='0'}">--%>
                                <div class="isUserRelationSubject0">
                                    <input id="erpparam-storageotheroutDcode" type="text" name="storageotheroutDcode" style="width: 200px;" value="${erpp.storageotheroutDcode}"/>
                                </div>

                                <%--</c:if>--%>
                                <%--<c:if test="${isUserRelationSubject=='1'}">--%>
                                    <input class="isUserRelationSubject1"  type="text" style="width: 200px;" value="" readonly="readonly"/>
                                <%--</c:if>--%>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-storageotheroutCcode" type="text" name="storageotheroutCcode" style="width: 200px;" value="${erpp.storageotheroutCcode}"/>
                            </td>
                        </tr>
                        <th colspan="5" align="left">其它入库凭证接入配置</th>
                        <tr>
                            <td align="right" style="width: 180px">借方科目：</td>
                            <td>
                                <input id="erpparam-storageotherinDcode" type="text" name="storageotherinDcode" style="width: 200px;" value="${erpp.storageotherinDcode}"/>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <%--<c:if test="${isUserRelationSubject=='0'}">--%>
                                <div class="isUserRelationSubject0">
                                    <input id="erpparam-storageotherinCcode" type="text" name="storageotherinCcode" style="width: 200px;" value="${erpp.storageotherinCcode}"/>
                                </div>
                                <%--</c:if>--%>
                                <%--<c:if test="${isUserRelationSubject=='1'}">--%>
                                    <input class="isUserRelationSubject1"  type="text" style="width: 200px;" value="" readonly="readonly"/>
                                <%--</c:if>--%>
                            </td>
                        </tr>
                        <th colspan="5" align="left">报损调账单凭证接入配置</th>
                        <tr>
                            <td align="right" style="width: 180px">借方科目：</td>
                            <td>
                                <input id="erpparam-lossAdjustmentsDcode" type="text" name="lossAdjustmentsDcode" style="width: 200px;" value="${erpp.lossAdjustmentsDcode}"/>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-lossAdjustmentsCcode" type="text" name="lossAdjustmentsCcode" style="width: 200px;" value="${erpp.lossAdjustmentsCcode}"/>
                            </td>
                        </tr>
                        <th colspan="5" align="left">报溢调账单凭证接入配置</th>
                        <tr>
                            <td align="right" style="width: 180px">借方科目：</td>
                            <td>
                                <input id="erpparam-adjustmentsDcode" type="text" name="adjustmentsDcode" style="width: 200px;" value="${erpp.adjustmentsDcode}"/>
                            </td>
                            <td align="right" style="width: 140px">贷方科目：</td>
                            <td>
                                <input id="erpparam-adjustmentsCcode" type="text" name="adjustmentsCcode" style="width: 200px;" value="${erpp.adjustmentsCcode}"/>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="tagsDiv_item">
                    <%--<div class="easyui-layout" data-options="fit:true,border:false"  style="height:500px; ">--%>
                    <div style="float: left;width:15%;">
                        <div class="lefttable" onclick="showBaseTable(1)">银行档案对应关系</div>
                        <div class="lefttable" onclick="showBaseTable(2)">其它出入库类型对应关系</div>
                        <div class="lefttable" onclick="showBaseTable(3)">客户费用科目对应关系</div>
                        <div class="lefttable" onclick="showBaseTable(4)">日常费用科目对应关系</div>
                    </div>
                    <div style="float: right;width:85%;" >
                        <div id="thirdDbParam-bank-table" style="height:auto!important;height:500px;min-height:500px "></div>
                    </div>
                    <%--</div>--%>
                </div>
                <div class="tagsDiv_item">
                    <div  style="float: left;width: 220px;height: 500px;">
                        <div id="thirdDb-table-subjectList" style="height: 500px;"></div>
                        <%--<div id="thirdDb-button-subjectList" class="buttonBG">--%>
                            <%--<security:authorize url="/sales/customerBrandPricesortAddBtn.do">--%>
                                <%--<a href="javaScript:void(0);" id="thirdDb-add-addSubject" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>--%>
                            <%--</security:authorize>--%>
                            <%--<security:authorize url="/sales/customerBrandPricesortDelBtn.do">--%>
                                <%--<a href="javaScript:void(0);" id="thirdDb-delete-subjectList" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除">删除</a>--%>
                            <%--</security:authorize>--%>
                        <%--</div>--%>

                    </div>
                    <div style="float: left;width: 70%;height: 500px;">
                        <div id="thirdDb-table-goodsList" style="height: 500px;"></div>
                        <div id="thirdDb-button-goodsList"  style="height:26px;">
                            <input type="hidden" id="goodsList-form-subjectid" name="subjectid" />
                            <table>
                                <tr>
                                    <td>类型：</td>
                                    <td>
                                        <select id="goodsList-form-addtype" name="addtype">
                                            <option selected></option>
                                            <option value="1">按商品</option>
                                            <option value="2">按品牌</option>
                                        </select>
                                    </td>
                                    <td>商品：</td>
                                    <td><input type="text" id="goodsList-widget-goodsid" name="goodsid" autocomplete="off"/></td>
                                    <td>品牌：</td>
                                    <td><input type="text" id="goodsList-widget-brandid" name="brandid" autocomplete="off"/></td>
                                    <%--<td>科目：</td>--%>
                                    <%--<td><input type="text" id="goodsList-widget-subjectid" name="subjectid" autocomplete="off"/></td>--%>
                                    <td colspan="2" class="tdbutton">
                                        <a href="javascript:void(0);" id="ledger-query-goodsList" class="button-qr">查询</a>
                                        <a href="javaScript:void(0);" id="ledger-reset-goodsList" class="button-qr">重置</a>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="tagsDiv_item">
                    <div  style="float: left;width: 220px;height:500px;;">
                        <div id="thirdDb-table-taxSubjectList" style="height:100%;margin:0;padding:0;"></div>
                    </div>
                    <div style="float: left;width: 70%;height: 500px;">
                        <div id="thirdDb-table-taxList" style="height: 500px;"></div>
                        <div id="thirdDb-button-taxList">
                            <input type="hidden" id="taxList-form-subjectid" name="subjectid" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<div id="thidDbPage-dataRelation-Dialog"></div>
<div id="thidDbPage-addSubject-Dialog"></div>
<div id="thidDbPage-addGoods-Dialog"></div>
<div id="thidDbPage-editSubjectGoods-Dialog"></div>
<div id="ledger-dbsourceset-dialog"></div>
<script type="text/javascript">
    var indexmap={};
    $(".tags").find("li").click(function(){
        var index = $(this).index();
        $(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
        $(".tagsDiv .tagsDiv_item").hide().eq(index).show();
        //每个tags只有点击第一次的时候加载
        if(indexmap[index]==1){
            return;
        }
        indexmap[index]=1;
        if(index==1){
            //采购进货凭证借方
            $("#erpparam-purchaseDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
//                param:[{field:'bproperty',op:'like',value:'1'}],
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            //采购进货凭证贷方
            $("#erpparam-purchaseCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
//                param:[{field:'bproperty',op:'like',value:'0'}],
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            //收款凭证借方
            $("#erpparam-collectionDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });
            $("#erpparam-collectionCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });
            $("#erpparam-withdrawalDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });
            $("#erpparam-withdrawalCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });

            //日常费用支付凭证
            $("#erpparam-bankDailyCostpayDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });
            $("#erpparam-bankDailyCostpayCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });

            //客户费用支付凭证
            $("#erpparam-bankCustomerCostpayDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });
            $("#erpparam-bankCustomerCostpayCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });

            //货款支付凭证(付款单)借方
            $("#erpparam-paymentDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });
            $("#erpparam-paymentCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });
            //货款支付凭证(采购发票冲差)借方
            $("#erpparam-paymentPushDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            $("#erpparam-paymentPushCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            $("#erpparam-paymentTotalCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            //采购冲暂估凭证借方
            $("#erpparam-purchaseEstimateDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            //采购冲暂估凭证贷方
            $("#erpparam-purchaseEstimateCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            //采购退货凭证借方
            $("#erpparam-purchaseRejectDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            //采购退货凭证贷方
            $("#erpparam-purchaseRejectCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            //采购发票借方 未税总金额
            $("#erpparam-PushNotaxamountDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            //采购发票借方 库存商品调整
            $("#erpparam-PushAdjustDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            //采购发票借方 进项税额
            $("#erpparam-pushTaxamountCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            //采购发票贷方 含税总金额
            $("#erpparam-purchasePushCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            //代垫录入
            $("#erpparam-journalsheetDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });
            $("#erpparam-journalsheetCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });
            //其它出库
            $("#erpparam-storageotheroutDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });
            $("#erpparam-storageotheroutCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]

            });
            //其它入库
            $("#erpparam-storageotherinDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            $("#erpparam-storageotherinCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });
            //销售凭证
            $("#erpparam-salesDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });

            $("#erpparam-salesNotaxamountCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            $("#erpparam-salesNotaxamountCcode1").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            $("#erpparam-salesTaxCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
            $("#erpparam-adjustmentsDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });

            $("#erpparam-adjustmentsCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });

            $("#erpparam-lossAdjustmentsDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'},{field:'isleaf',op:'equal',value:'1'}]
            });

            $("#erpparam-lossAdjustmentsCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });

            $("#erpparam-mainCostDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });

            $("#erpparam-mainCostCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true,
                readonly:true,
                param:[{field:'dbid',op:'equal',value:'${erpDb.id}'}]
            });
        }else if(index == 2){
            $('.lefttable').eq(0).click();
            showBaseTable(1);
        }else if(index==3){
            //一级科目列表
            $('#thirdDb-table-subjectList').datagrid({
                fit:true,
                title:'一级科目列表',
                method:'post',
                pagination:false,
                pageSize:100,
                singleSelect:true,
                checkOnSelect:true,
                selectOnCheck:true,
                toolbar:"#thirdDb-button-subjectList",
                url:'thirdDb/getThirdBaseSubject.do?dbid=${erpDb.id}&basetype=0',
                columns:[[
                    {field:'id',title:'编码',width:70,sortable:true,hidden:true},
                    {field:'subjectid',title:'会计科目编码',width:100,sortable:true},
                    {field:'subjectname',title:'会计科目名称',width:120,sortable:true}
                ]],
                onClickRow:function(index,row){
                    $("#goodsList-form-subjectid").val(row.subjectid);
                    var addtype=$("#goodsList-form-addtype").val();
                    var goodsid=$("#goodsList-widget-goodsid").widget('getValue');
                    var brandid=$("#goodsList-widget-brandid").widget('getValue');
                    var subjectid=$("#goodsList-form-subjectid").val();
                    var json={
                        dbid:'${erpDb.id}',
                        addtype:addtype,
                        goodsid:goodsid,
                        brandid:brandid,
                        subjectid:subjectid
                    }
                    $("#thirdDb-table-goodsList").datagrid('load',json);
                }
            });

            //商品科目
            $('#thirdDb-table-goodsList').datagrid({
                fit:true,
                title:'商品科目列表',
                method:'post',
                rownumbers:true,
                pagination:true,
                singleSelect:false,
                checkOnSelect:true,
                selectOnCheck:true,
                toolbar:"#thirdDb-button-goodsList",
                url:'thirdDb/getGoodsSubjectListForFirst.do?dbid=${erpDb.id}',
                rowStyler: function(index,row){
                    if (row.isedit == '1'){
                        return 'background-color:rgb(190, 250, 241);';
                    }
                },
                columns:[[
                    {field:'ck',checkbox:true},
                    {field:'id',title:'编码',hidden:true},
                    {field:'firstpid',title:'一级科目',hidden:true},
                    {field:'addtype',title:'添加类型',hidden:true},
                    {field:'goodsid',title:'商品编码',width:100},
                    {field:'goodsname',title:'商品名称',width:180},
                    {field:'brandid',title:'品牌编码',width:80},
                    {field:'brandname',title:'品牌名称',width:100},
                    {field:'subjectid',title:'科目编码',width:100},
                    {field:'subjectname',title:'科目名称',width:180}
                ]],
                onDblClickRow: function(rowIndex, rowData){

                }
            });
            $("#goodsList-widget-goodsid").widget({
                referwid:'RL_T_BASE_GOODS_INFO',
                singleSelect:false,
                width:120
            })

            $("#goodsList-widget-brandid").widget({
                referwid:'RL_T_BASE_GOODS_BRAND',
                singleSelect:false,
                width:120
            })
        }else if(index==4){
            $("#taxList-widget-taxtype").widget({
                referwid:'RL_T_BASE_FINANCE_TAXTYPE',
                singleSelect:false,
                width:120
            })

            //税种科目
            $('#thirdDb-table-taxList').datagrid({
                idField:'id',
                fit:true,
                title:'税种科目列表',
                method:'post',
                rownumbers:true,
                singleSelect:false,
                pagination:true,
                checkOnSelect:true,
                selectOnCheck:true,
                toolbar:"#thirdDb-button-taxList",
                url:'thirdDb/getTaxSubjectListForFirst.do?dbid=${erpDb.id}',
                rowStyler: function(index,row){
                    if (row.isedit == '1'){
                        return 'background-color:rgb(190, 250, 241);';
                    }
                },
                columns:[[
                    {field:'ck',checkbox:true},
                    {field:'id',title:'编码',hidden:true},
                    {field:'firstpid',title:'一级科目',hidden:true},
                    {field:'taxtype',title:'税种类型',width:100},
                    {field:'taxtypeName',title:'税种名称',width:180},
                    {field:'subjectid',title:'科目编码',width:100},
                    {field:'subjectname',title:'科目名称',width:180}
                ]],
                onDblClickRow: function(rowIndex, rowData){
                    var row = $("#thirdDb-table-taxSubjectList").datagrid('getSelected');
                    if(row==null){
                        $.messager.alert('提醒', '请选择一级科目！');
                        return false;
                    }
                    $("#thidDbPage-editSubjectTax-Dialog").dialog({
                        title:'编辑税种科目关系',
                        maximizable:false,
                        width:450,
                        height:200,
                        closed:true,
                        modal:true,
                        cache:false,
                        resizable:true,
                        href: 'thirdDb/showSubjectTaxEditPage.do?id='+rowData.id,
                        queryParams:{
                            goodsname:rowData.goodsname,goodsid:rowData.goodsid,dbid:'${erpDb.id}',pid:row.subjectid,
                            addtype:rowData.addtype,brandid:rowData.brandid,brandname:rowData.brandname,subjectid:rowData.subjectid
                        },
                        buttons:[
                            {
                                text:'保存',
                                handler:function(){
                                    $("#baseSubjectRelation-form-add").submit();
                                }
                            }
                        ]
                    });
                    $("#thidDbPage-editSubjectTax-Dialog").dialog('open');
                }
            });

            //一级科目列表
            $('#thirdDb-table-taxSubjectList').datagrid({
                fit:true,
                title:'一级科目列表',
                method:'post',
                pagination:false,
                pageSize:100,
                singleSelect:true,
                checkOnSelect:true,
                selectOnCheck:true,
                toolbar:"#thirdDb-button-taxSubjectList",
                url:'thirdDb/getThirdBaseSubject.do?dbid=${erpDb.id}&basetype=1',
                columns:[[
                    {field:'id',title:'编码',width:70,sortable:true,hidden:true},
                    {field:'subjectid',title:'会计科目编码',width:100,sortable:true},
                    {field:'subjectname',title:'会计科目名称',width:120,sortable:true}
                ]],
                onClickRow:function(index,row){
                    $("#taxList-form-subjectid").val(row.subjectid);
//                    var taxtype=$("#taxList-widget-taxtype").widget('getValue');
                    var subjectid=$("#taxList-form-subjectid").val();
                    var json={
                        dbid:'${erpDb.id}',
//                        taxtype:taxtype,
                        subjectid:subjectid
                    }
                    $("#thirdDb-table-taxList").datagrid('load',json);
                }
            });
        }
    });

    function showBaseTable(basetype){
        var baseidtitle="银行档案编码";
        var basenametitle="银行档案名称";
        if(basetype==2){
            baseidtitle='出入库类型编码';
            basenametitle='出入库类型名称';
        }else if(basetype==3){
            baseidtitle='费用科目编码';
            basenametitle='费用科目名称';
        }
        $('#thirdDbParam-bank-table').datagrid({
            columns:[[
                {field:'id',title:'编码',width:100,hidden:true},
                {field:'baseid',title:baseidtitle,width:90},
                {field:'basename',title:basenametitle,width:120},
                {field:'subjectid',title:'会计科目编码',width:90,sortable:true},
                {field:'subjectname',title:'会计科目名称',width:120,sortable:true}
            ]],
            fit:true,
            method:'post',
            rownumbers:true,
//                idField:'id',
            singleSelect:true,
            url:'thirdDb/getBaseSubjectRelationData.do?basetype='+basetype+'&dbid=${erpDb.id}',
            onDblClickRow:function(rowIndex, rowData){
//                showDataRelationDialog(rowData.id,basetype,rowData.baseid,rowData.basename);
            }
        });

    }

    function showDataRelationDialog(id,basetype,baseid,basename){
        $("#thidDbPage-dataRelation-Dialog").dialog({
            title:'编辑档案科目关系',
            maximizable:false,
            width:450,
            height:200,
            closed:true,
            modal:true,
            cache:false,
            resizable:true,
            href: 'thirdDb/showDataRelationEditPage.do?id='+id,
            queryParams:{
                basetype:basetype,baseid:baseid,basename:basename,id:id,codetype:codetype,dbid:dbid
            },
            buttons:[
                {
                    text:'保存',
                    handler:function(){
                        $("#baseSubjectRelation-form-add").submit();
                    }
                }
            ],
        });
        $("#thidDbPage-dataRelation-Dialog").dialog('open');
    }




    function editAccountSet() {
        if(!$("#ledger-form-AccountSetAddPage").form('validate')){
            return false;
        }
        $("#ledger-form-AccountSetAddPage").submit();
    }

    $(function () {

        $(".lefttable").click(function(){
            $(".lefttable").css('background-color','');
            $(this).css('background-color','#038bbe');
        });

        $('.sumgroupcols').each(function(){
            $(this).click(function(){
                if($(this).attr('checked')){
                    $('.sumgroupcols').removeAttr('checked');
                    $(this).attr('checked','checked');
                }
            });
        });

        $(".checkbox1").click(function(){
            $(".checkbox1").each(function(){
                var id=$(this).attr('id')+"_hidden";
                if($(this).attr("checked")){
                    $("#"+id).val('1');
                }else{
                    $("#"+id).val('0');
                }
            });
        });

        $("#ledger-form-AccountSetAddPage").form({
            onSubmit: function(){
                loading("提交中..");
            },
            success:function(json){
                //表单提交完成后 隐藏提交等待页面
                loaded();
                if(json){
                    refreshLayout("账套明细【详情】",'thirdDb/showThirdDbParamViewPage.do?id=${erpDb.id}','view');
                    $.messager.alert("提醒","保存成功!");
                }else{
                    $.messager.alert("提醒","保存失败!");
                }

            }
        });

        $("#thirdDb-add-addSubject").click(function(){
            $("#thidDbPage-addSubject-Dialog").dialog({
                title:'新增一级科目',
                maximizable:false,
                width:650,
                height:500,
                closed:true,
                modal:true,
                cache:false,
                resizable:true,
                href: 'thirdDb/showThirdParamAddFirstSubjectPage.do',
                queryParams:{
                    dbid:'${erpDb.id}'
                },
                buttons:[
                    {
                        text:'保存',
                        handler:function(){
                            var checkedRows = $("#ledger-datagrid-thirdParamAddFirstSubjectPage").datagrid('getChecked');
                            if(checkedRows.length==0){
                                $.messager.alert('提醒', '请选择科目！');
                                return false;
                            }
                            var ids='';
                            for(var i=0;i<checkedRows.length;i++){
                                if(ids==''){
                                    ids=checkedRows[i].id;
                                }else{
                                    ids+=","+checkedRows[i].id;
                                }
                            }
                            loading('添加中');
                            $.ajax({
                                url:'thirdDb/addFirstBaseSubject.do',
                                data:{ids:ids,dbid:'${erpDb.id}'},
                                cache: false,
                                type:'post',
                                async: true,
                                success:function(res){
                                    loaded();
                                    var resjson = $.parseJSON(res);
                                    if(resjson.flag){
                                        $.messager.alert('提醒', '添加成功!');
                                        $("#thirdDb-table-subjectList").datagrid('reload');
                                        $("#thidDbPage-addSubject-Dialog").dialog('close');
                                    }else{
                                        $.messager.alert('提醒', '添加失败!');
                                        return false;
                                    }
                                }
                            });
                        }
                    }
                ],
            });
            $("#thidDbPage-addSubject-Dialog").dialog('open');

        });

        $("#thirdDb-add-goodsList").click(function(){
            var row = $("#thirdDb-table-subjectList").datagrid('getSelected');
            if(row==null){
                $.messager.alert('提醒', '请选择一级科目！');
                return false;
            }
            $("#thidDbPage-addGoods-Dialog").dialog({
                title:'新增商品科目',
                maximizable:false,
                width:650,
                height:500,
                closed:true,
                modal:true,
                cache:false,
                resizable:true,
                href: 'thirdDb/showThirdGoodsSubjectAddPage.do',
                queryParams:{
                    dbid:'${erpDb.id}',subjectid:row.subjectid,pid:row.subjectid
                },
                buttons:[
                    {
                        text:'保存',
                        handler:function(){
                            var checkedRows = $("#ledger-datagrid-thirdGoodsSubjectAddPage").datagrid('getChecked');
                            if(checkedRows.length==0){
                                $.messager.alert('提醒', '请选择商品！');
                                return false;
                            }
                            var subjectid=$("#thirdGoodsSubjectAddPage-widget-subjectid").widget('getValue');
                            if(subjectid==''){
                                $.messager.alert('提醒', '请选择科目！');
                                return false;
                            }
                            var ids='';
                            for(var i=0;i<checkedRows.length;i++){
                                if(ids==''){
                                    ids=checkedRows[i].id;
                                }else{
                                    ids+=","+checkedRows[i].id;
                                }
                            }
                            loading('添加中');
                            $.ajax({
                                url:'thirdDb/addThirdGoodsSubject.do',
                                data:{ids:ids,dbid:'${erpDb.id}',subjectid:subjectid,pid:row.subjectid},
                                cache: false,
                                type:'post',
                                async: true,
                                success:function(res){
                                    loaded();
                                    var resjson = $.parseJSON(res);
                                    if(resjson.flag){
                                        $.messager.alert('提醒', '保存成功!');
                                        $("#thirdDb-table-goodsList").datagrid('reload');
                                        $("#thidDbPage-addGoods-Dialog").dialog('close');
                                    }else{
                                        $.messager.alert('提醒', '保存失败!');
                                        return false;
                                    }
                                }
                            });
                        }
                    }
                ],
            });
            $("#thidDbPage-addGoods-Dialog").dialog('open');

        });

        $("#thirdDb-delete-goodsList").click(function(){
            var row = $("#thirdDb-table-subjectList").datagrid('getSelected');
            if(row==null){
                $.messager.alert('提醒', '请选择一级科目！');
                return false;
            }
            var checkedRows = $("#thirdDb-table-goodsList").datagrid('getChecked');
            if(checkedRows.length==0){
                $.messager.alert('提醒', '请选择商品！');
                return false;
            }
            var ids='';
            for(var i=0;i<checkedRows.length;i++){
                if(ids==''){
                    ids=checkedRows[i].goodsid;
                }else{
                    ids+=","+checkedRows[i].goodsid;
                }
            }
            loading('删除中');
            $.ajax({
                url:'thirdDb/deleteThirdGoodsSubject.do',
                data:{ids:ids,dbid:'${erpDb.id}',pid:row.subjectid},
                cache: false,
                type:'post',
                async: true,
                success:function(res){
                    loaded();
                    var resjson = $.parseJSON(res);
                    if(resjson.flag){
                        $.messager.alert('提醒', '删除成功!');
                        $("#thirdDb-table-goodsList").datagrid('reload');
                    }else{
                        $.messager.alert('提醒', '删除失败!');
                        return false;
                    }
                }
            });
        })

        <c:if test="${erpDb.interfacetype=='0'}">
            $("#ledger-dbsource").show();
            $("#ledger-openapi-tr1").hide();
            $(".dbconnect").show();
            $(".openapiconnect").hide();
        </c:if>
        <c:if test="${erpDb.interfacetype=='1'}">
            $("#ledger-openapi-tr1").show();
            $(".dbconnect").hide();
            $(".openapiconnect").show();
            $("#ledger-dbsource").hide();
        </c:if>

        //切换用友对接方式
        $("#ledger-interfacetype-thirdDbParamEditPage").change(function(){
            var val = $(this).val();
            if("0" == val){
                $("#ledger-dbsource").show();
                $("#ledger-openapi-tr1").hide();
                $("#ledger-openapi-tr2").hide();
            }else{
                $("#ledger-openapi-tr1").show();
                $("#ledger-openapi-tr2").show();
                $("#ledger-dbsource").hide();

            }

        });

        $("#thirdDb-delete-subjectList").click(function(){
            var row = $("#thirdDb-table-subjectList").datagrid('getSelected');
            if(row==null){
                $.messager.alert('提醒', '请选择一级科目！');
                return false;
            }

            loading('删除中');
            $.ajax({
                url:'thirdDb/deleteThirdSubject.do',
                data:{id:row.subjectid,dbid:'${erpDb.id}'},
                cache: false,
                type:'post',
                async: true,
                success:function(res){
                    loaded();
                    var resjson = $.parseJSON(res);
                    if(resjson.flag){
                        $.messager.alert('提醒', '删除成功!');
                        $("#thirdDb-table-subjectList").datagrid('reload');
                    }else{
                        $.messager.alert('提醒', '删除失败!');
                        return false;
                    }
                }
            });
        });
        //连接测试
        $("#ledger-test-thirdDbParamEditPage").click(function () {
            var row = $('#ledger-VoucherSet-ledgerCommonSetPage').datagrid('getSelected');
            if(row == null){
                $.messager.alert("提醒","请选择一个账套!");
                return false;
            }
            var interfacetype = $("#ledger-interfacetype-thirdDbParamEditPage").val();
            if("0" == interfacetype){
                var dbIP = $("#ledger-dbIP-thirdDbParamEditPage").val();
                var dbusername = $("#ledger-dbusername-thirdDbParamEditPage").val();
                var dbpassword = $("#ledger-dbpassword-thirdDbParamEditPage").val();
                var dbname = row.dbname;
                loading("测试中..");
                $.ajax({
                    url: 'erpconnect/testERPDBConnectByContion.do',
                    type: 'post',
                    dataType: 'json',
                    data:{dbIP:dbIP,dbusername:dbusername,dbpassword:dbpassword,id:row.id,dbname:row.dbname},
                    success: function (json) {
                        loaded();
                        if (json.flag) {
                            $.messager.alert("提醒", "连接成功。");
                        } else {
                            $.messager.alert("提醒", "连接失败。");
                        }
                    },
                    error: function () {
                        loaded();
                        $.messager.alert("错误", "连接失败");
                    }
                });
            }else{
                var from_account = $("#ledger-from_account-thirdDbParamEditPage").val();
                var to_account = $("#ledger-to_account-thirdDbParamEditPage").val();
                var app_key = $("#ledger-app_key-thirdDbParamEditPage").val();
                var app_secret = $("#ledger-app_secret-thirdDbParamEditPage").val();
                var dbsourceseq = $("#ledger-dbsourceseq-thirdDbParamEditPage").val();
//                if(dbsourceseq == ""){
//                    $.messager.alert("提醒","请先选择一个数据源!");
//                    return false;
//                }
                loading("测试中..");
                $.ajax({
                    url: 'erpconnect/testERPTokenConnect.do',
                    type: 'post',
                    dataType: 'json',
                    data:{from_account:from_account,to_account:to_account,app_key:app_key,app_secret:app_secret,dbsourceseq:dbsourceseq},
                    success: function (flag) {
                        loaded();
                        if(flag){
                            $.messager.alert("提醒", "连接成功。");
                        }else{
                            $.messager.alert("提醒", "连接失败。");
                        }
                    },
                    error: function () {
                        loaded();
                        $.messager.alert("错误", "连接失败");
                    }
                });
            }

        });



        $("#thirdDb-export-goodsList").Excel('export',{
//            queryForm: "#report-query-form-customerSalesFlowDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type:'exportUserdefined',
            name:'账套商品对应科目列表',
            url:'thirdDb/exportBaseSubjectGoods.do?dbid=${erpDb.id}'
        });

        $("#thirdDb-import-goodsList").Excel('import',{
            type:'importUserdefined',
            url:'thirdDb/importBaseSubjectGoods.do',
//            importparam:'客户编码、品牌编码、结算方式必输',
            onClose: function(){ //导入成功后窗口关闭时操作，
//                $("#sales-table-brandsettletypelist").datagrid("reload");
            }
        });
        changeVersion();
        changeRelation();
    })


    function changeVersion() {
        var version = $("#erpparam-versionParam").val();
        if (version == '0') {
            $(".version0").show();
            $(".version1").hide();
        } else if (version == '1') {
            $(".version0").hide();
            $(".version1").show();
        }
    }

    $("#ledger-query-goodsList").click(function(){
        var row = $("#thirdDb-table-subjectList").datagrid('getSelected');
        if(row==null){
            $.messager.alert('提醒', '请选择一级科目！');
            return false;
        }
        var addtype=$("#goodsList-form-addtype").val();
        var goodsid=$("#goodsList-widget-goodsid").widget('getValue');
        var brandid=$("#goodsList-widget-brandid").widget('getValue');
        var subjectid=$("#goodsList-form-subjectid").val();
        var json={
            dbid:'${erpDb.id}',
            addtype:addtype,
            goodsid:goodsid,
            brandid:brandid,
            subjectid:subjectid
        }
        $("#thirdDb-table-goodsList").datagrid('load',json);
    })

    $("#ledger-reset-goodsList").click(function(){
        var row = $("#thirdDb-table-subjectList").datagrid('getSelected');
        if(row==null){
            $.messager.alert('提醒', '请选择一级科目！');
            return false;
        }
        $("#goodsList-widget-goodsid").widget('clear');
        $("#goodsList-widget-brandid").widget('clear');
        document.getElementById("goodsList-form-addtype").selectedIndex=0
        var addtype=$("#goodsList-form-addtype").val();
        var goodsid=$("#goodsList-widget-goodsid").widget('getValue');
        var brandid=$("#goodsList-widget-brandid").widget('getValue');
        var subjectid=$("#goodsList-form-subjectid").val();
        var json={
            dbid:'${erpDb.id}',
            addtype:addtype,
            goodsid:goodsid,
            brandid:brandid,
            subjectid:subjectid
        }
        $("#thirdDb-table-goodsList").datagrid('load',json);
    })

    function changeRelation(){
        var value=$("#ledger-useRelation-thirdDbParamEditPage").val();
        if(value==0){
            $(".isUserRelationSubject0").show();
            $(".isUserRelationSubject1").hide();
        }else if(value==1){
            $(".isUserRelationSubject0").hide();
            $(".isUserRelationSubject1").show();
        }
    }

</script>
</body>
</html>

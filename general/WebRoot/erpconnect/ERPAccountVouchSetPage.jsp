<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>接入到其他ERP系统的凭证设置</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="buttonBG">
        <security:authorize url="/erpconnect/saveERPParam.do">
            <a href="javaScript:void(0);" id="erpconnect-button-save" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">保存</a>
        </security:authorize>
        <security:authorize url="/erpconnect/addSalesAccountVouch.do">
        <a href="javaScript:void(0);" id="erpconnect-button-sales" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">生成销售凭证</a>
        </security:authorize>
        <security:authorize url="/erpconnect/syncAccountCodeFromERP.do">
        <a href="javaScript:void(0);" id="erpconnect-button-synccode" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">同步会计科目</a>
        </security:authorize>
        <security:authorize url="/erpconnect/syncDsignFromERP.do">
            <a href="javaScript:void(0);" id="erpconnect-button-syncsign" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">同步凭证类别</a>
        </security:authorize>
        <security:authorize url="/erpconnect/syncVouchHistory.do">
            <a href="javaScript:void(0);" id="erpconnect-button-history" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-view'">凭证生成记录</a>
        </security:authorize>
    </div>
    <form action="erpconnect/saveERPParam.do" method="post" id="erpconnect-form-param">
        <table>
            <security:authorize url="/erpconnect/viewDataSouce.do">
                <th colspan="5" align="left">接入ERP版本</th>
                <tr>
                    <td align="right" style="width: 140px">接入系统：</td>
                    <td>
                        <select name="ERPVersion" style="width: 200px;" id="erpconnect-version">
                            <option VALUE="T3" <c:if test="${erpp.ERPVersion=='T3'}">selected="selected"</c:if>>用友T3</option>
                            <option VALUE="T6" <c:if test="${erpp.ERPVersion=='T6'}">selected="selected"</c:if>>用友T6</option>
                            <option VALUE="U8" <c:if test="${erpp.ERPVersion=='U8'}">selected="selected"</c:if>>用友U8</option>
                        </select>
                    </td>
                    <td align="right" style="width: 140px">版本参数：</td>
                    <td>
                        <select name="versionParam" style="width: 200px;">
                            <option VALUE="0" <c:if test="${erpp.versionParam=='0'}">selected="selected"</c:if>>通用参数0</option>
                            <option VALUE="1" <c:if test="${erpp.versionParam=='1'}">selected="selected"</c:if>>通用参数1</option>
                        </select>
                    </td>
                    <td align="right" style="width: 140px">凭证是否取总店客户:</td>
                    <td>
                        <select id="erpparam-isCustomerPid" name="isCustomerPid" style="width: 30px;">
                            <option value="0" <c:if test="${erpp.isCustomerPid=='0'}">selected="selected"</c:if>>否</option>
                            <option value="1" <c:if test="${erpp.isCustomerPid=='1'}">selected="selected"</c:if>>是</option>
                        </select>
                    </td>

                    <td align="right" style="width: 140px" id="isAccountDate-td1">凭证是否取会计日期</td>
                    <td id="isAccountDate-td2">
                        <select id="erpparam-isAccountDate" name="isAccountDate" style="width: 30px;">
                            <option value="0" <c:if test="${erpp.isAccountDate=='0'}">selected="selected"</c:if>>否</option>
                            <option value="1" <c:if test="${erpp.isAccountDate=='1'}">selected="selected"</c:if>>是</option>
                        </select>
                    </td>
                </tr>
            </security:authorize>
        </table>
        <h1>ERP接通数据库配置</h1>
        <div style="height: 150px">
            <div class="buttonBG" id="erp-buttons-dbConnectList"></div>
            <table id="erp-datagrid-dbConnectList" data-options="border:false"></table>
        </div>
        <table  cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;margin-bottom: 80px">
          <th colspan="4" align="left">基础档案关联配置</th>
          <tr>
              <td align="right" style="width: 140px">供应商档案：</td>
              <td>
                  <select id="erpparam-supplierCode" name="supplierCode" style="width: 200px;">
                      <option VALUE="id" <c:if test="${erpp.supplierCode=='id'}">selected="selected"</c:if>>编码关联</option>
                      <option VALUE="spell" <c:if test="${erpp.supplierCode=='spell'}">selected="selected"</c:if>>助记符关联</option>
                  </select>
              </td>
              <td colspan="4">
                <security:authorize url="/erpconnect/syncSupplierToERP.do">
                  <a href="javaScript:void(0);" id="erpconnect-button-syncSupplier" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">供应商档案同步到ERP</a>
                </security:authorize>
                  <%--<a href="javaScript:void(0);" id="erpconnect-button-syncSupplierSort" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">供应商分类档案同步到ERP</a>--%>
              </td>
          </tr>
          <tr>
              <td align="right" style="width: 140px">客户档案：</td>
              <td>
                  <select id="erpparam-customerCode" name="customerCode" style="width: 200px;">
                      <option VALUE="id" <c:if test="${erpp.customerCode=='id'}">selected="selected"</c:if>>编码关联</option>
                      <option VALUE="spell" <c:if test="${erpp.customerCode=='spell'}">selected="selected"</c:if>>助记符关联</option>
                  </select>
              </td>
              <td colspan="4">
                <security:authorize url="/erpconnect/syncSalesAreaToERP.do">
                  <%--<a href="javaScript:void(0);" id="erpconnect-button-syncSalesArea" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">销售区域档案同步到ERP</a>--%>
                </security:authorize>
                <security:authorize url="/erpconnect/syncCustomerSortToERP.do">
                  <%--<a href="javaScript:void(0);" id="erpconnect-button-syncCustomerSort" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">客户分类档案同步到ERP</a>--%>
                </security:authorize>
                <security:authorize url="/erpconnect/syncCustomerToERP.do">
                  <a href="javaScript:void(0);" id="erpconnect-button-syncCustomer" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">客户档案同步到ERP</a>
                </security:authorize>
              </td>
          </tr>
          <th colspan="5" align="left">采购凭证接入配置</th>
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
          <c:if test="${erpp.versionParam == 1}">
              <tr>
                  <td align="right" style="width: 180px"><a style="color: #1006F1">采购进货冲暂估</a>&nbsp;借方科目：</td>
                  <td>
                      <input id="erpparam-purchaseEstimateDcode" type="text" name="purchaseEstimateDcode" style="width: 200px;" value="${erpp.purchaseEstimateDcode}"/>
                  </td>
                  <td align="right" style="width: 140px">贷方科目：</td>
                  <td>
                      <input id="erpparam-purchaseEstimateCcode" type="text" name="purchaseEstimateCcode" style="width: 200px;" value="${erpp.purchaseEstimateCcode}"/>
                  </td>
              </tr>
          </c:if>
          <th colspan="4" align="left">销售凭证接入配置</th>
          <tr>
              <td align="right" style="width: 140px">接入数据（销售报表）：</td>
              <td>
                  <select id="erpparam-salesDataType" name="salesDataType" style="width: 200px;">
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
            <c:if test="${erpp.versionParam == 1}">
                <td align="right" style="width: 140px">贷方科目（未税）：</td>
                <td>
                    <input id="erpparam-salesNotaxamountCcode"  type="text" name="salesNotaxamountCcode" style="width: 200px;"  value="${erpp.salesNotaxamountCcode}"/>
                </td>
                <td align="right" style="width: 140px">贷方科目（税额）：</td>
                <td>
                    <input id="erpparam-salesTaxCcode" type="text" name="salesTaxCcode" style="width: 200px;" value="${erpp.salesTaxCcode}"/>
                </td>
            </c:if>
            <c:if test="${erpp.versionParam == 0}">
                <td align="right" style="width: 140px">贷方科目：</td>
                <td>
                    <input id="erpparam-salesNotaxamountCcode"  type="text" name="salesNotaxamountCcode" style="width: 200px;"  value="${erpp.salesNotaxamountCcode}"/>
                </td>
            </c:if>
          </tr>
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
        <c:if test="${erpp.versionParam == 1}">
            <th colspan="4" align="left">采购发票冲差凭证接入配置</th>
            <tr>
                <td align="right" style="width: 140px">借方科目：</td>
                <td>
                    <input id="erpparam-PushNotaxamountDcode" type="text" name="pushNotaxamountDcode" style="width: 200px;" value="${erpp.pushNotaxamountDcode}"/>
                </td>
                <td>
                    <input id="erpparam-PushAdjustDcode" type="text" name="pushAdjustDcode" style="width: 200px;" value="${erpp.pushAdjustDcode}"/>
                </td>
                <td>
                    <input id="erpparam-pushTaxamountCcode" type="text" name="pushTaxDcode" style="width: 200px;" value="${erpp.pushTaxDcode}"/>
                </td>
            </tr>
          <tr>
              <td align="right" style="width: 140px">贷方科目：</td>
              <td>
                  <input id="erpparam-purchasePushCcode" type="text" name="pushTaxamountCcode" style="width: 200px;" value="${erpp.pushTaxamountCcode}"/>
              </td>
          </tr>
        </c:if>
          <th colspan="8" align="left">收款支付凭证接入配置  来源：回笼表</th>
          <tr>
              <td align="right" style="width: 180px"><a style="color: #1006F1">客户收款</a>&nbsp;借方科目：</td>
              <td>
                  <input id="erpparam-withdrawalDcode" type="text" name="withdrawalDcode" style="width: 200px;" value="${erpp. withdrawalDcode}"/>
              </td>
              <td align="right" style="width: 140px">贷方科目：</td>
              <td>
                  <input id="erpparam-withdrawalCcode" type="text" name="withdrawalCcode" style="width: 200px;" value="${erpp. withdrawalCcode}"/>
              </td>
          </tr>
          <tr>
              <td align="right" style="width: 180px"><a style="color: #1006F1">银行收款</a>&nbsp;借方科目：</td>
              <td>
                  <input id="erpparam-withdrawalBankDcode" type="text" name="withdrawalBankDcode" style="width: 200px;" value="${erpp.withdrawalBankDcode}"/>
              </td>
              <td align="right" style="width: 140px">贷方科目：</td>
              <td>
                  <input id="erpparam-withdrawalBankCcode" type="text" name="withdrawalBankCcode" style="width: 200px;" value="${erpp.withdrawalBankCcode}"/>
              </td>
          </tr>
          <th colspan="8" align="left">收款支付凭证接入配置  来源：收款单 </th>
          <tr>
              <td align="right" style="width: 180px"><a style="color: #1006F1">客户收款</a>&nbsp;借方科目：</td>
              <td>
                  <input id="erpparam-collectionDcode" type="text" name="collectionDcode" style="width: 200px;" value="${erpp.collectionDcode}"/>
              </td>
              <td align="right" style="width: 140px">贷方科目：</td>
              <td>
                  <input id="erpparam-collectionCcode" type="text" name="collectionCcode" style="width: 200px;" value="${erpp.collectionCcode}"/>
              </td>
          </tr>
          <tr>
              <td align="right" style="width: 180px"><a style="color: #1006F1">银行收款</a>&nbsp;借方科目：</td>
              <td>
                  <input id="erpparam-collectionBankDcode" type="text" name="collectionBankDcode" style="width: 200px;" value="${erpp.collectionBankDcode}"/>
              </td>
              <td align="right" style="width: 140px">贷方科目：</td>
              <td>
                  <input id="erpparam-collectionBankCcode" type="text" name="collectionBankCcode" style="width: 200px;" value="${erpp.collectionBankCcode}"/>
              </td>
          </tr>
          <th colspan="4" align="left">货款支付凭证接入配置  来源：付款单</th>
          <tr>
              <td align="right" style="width: 180px"><a style="color: #1006F1">银行支付</a>&nbsp;借方科目：</td>
              <td>
                  <input id="erpparam-paymentDcode" type="text" name="paymentDcode" style="width: 200px;" value="${erpp.paymentDcode}"/>
              </td>
              <td align="right" style="width: 140px">贷方科目：</td>
              <td>
                  <input id="erpparam-paymentCcode" type="text" name="paymentCcode" style="width: 200px;" value="${erpp.paymentCcode}"/>
              </td>
          </tr>
        <c:if test="${erpp.versionParam == 0}">
            <th colspan="4" align="left">货款支付凭证接入配置  来源：采购发票</th>
            <tr>
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
        </c:if>
        <th colspan="5" align="left">费用支付凭证接入配置</th>
        <tr>
            <td align="right" style="width: 180px"><a style="color: #1006F1">银行支付</a>&nbsp;借方科目：</td>
            <td>
                <input id="erpparam-bankCostpayDcode" type="text" name="bankCostpayDcode" style="width: 200px;" value="${erpp.bankCostpayDcode}"/>
            </td>
            <td align="right" style="width: 140px">贷方科目：</td>
            <td>
                <input id="erpparam-bankCostpayCcode" type="text" name="bankCostpayCcode" style="width: 200px;" value="${erpp.bankCostpayCcode}"/>
            </td>
        </tr>
        <tr>
            <td align="right" style="width: 180px"><a style="color: #1006F1">现金支付</a>&nbsp;借方科目：</td>
            <td>
                <input id="erpparam-cashCostpayDcode" type="text" name="cashCostpayDcode" style="width: 200px;" value="${erpp.cashCostpayDcode}"/>
            </td>
            <td align="right" style="width: 140px">贷方科目：</td>
            <td>
                <input id="erpparam-cashCostpayCcode" type="text" name="cashCostpayCcode" style="width: 200px;" value="${erpp.cashCostpayCcode}"/>
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
      </table>
      </form>
  </body>
    <div id="salesAccountVouch-dialog" class="easyui-dialog" title="生成销售凭证" style="width:400px;height:260px;"
         data-options="closed:true,resizable:true,modal:true,buttons:[{
                        text:'确定',
                        handler:function(){
                            addSalesAccountVouch();
                        }
                    }]">
        <div style="padding: 20px;">
        账套名称：<select id="sales-customer-databaseid"  style="width: 200px;" name="databaseid">
            <c:forEach items="${dbList }" var="list">
                <option value="${list.id }">${list.dbasename }</option>
            </c:forEach>
        </select>&nbsp;<br/>
        凭证类别：<select id="sales-customer-sign"  style="width: 200px;" name="sign">
            <c:forEach items="${signList }" var="list">
                <option value="${list.id }">${list.codeText }</option>
            </c:forEach>
        </select><br/>
        开始日期：<input type="text" id="salesAccountVouch-begindate" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 200px;" value="${today}" /><br/>
        结束日期：<input type="text" id="salesAccountVouch-enddate" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 200px;" value="${today}"/><br/>
        操作日期：<input type="text" id="salesAccountVouch-operdate" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" style="width: 200px;" value="${today}"/><br/>
        注：生成的销售凭证数据是该日期的销售数据（同销售情况报表中的数据）
        </div>
    </div>
  <div id="erpparam-history-dialog"></div>
  <div id="erp-dialog-editConnection"></div>
  <div id="erp-dialog-addConnection"></div>
  <div id="erp-dialog-chooseAccount"></div>
  <script type="text/javascript">

      $(function () {

          $("#erpconnect-version").on('change',function(){
              var version = $(this).val();
              if(version == 'U8'){
                  $("#isAccountDate-td1").show();
                  $("#isAccountDate-td2").show();
              }else{
                  $("#isAccountDate-td1").hide();
                  $("#isAccountDate-td2").hide();
              }

          })

          $("#erp-datagrid-dbConnectList").datagrid({
              columns:[[
                  {field: 'id', title: '编号', width: 60, align: 'left', hidden: true},
                  {field: 'relatesystem', title: '接入系统', width: 60, align: 'left'},
                  {field: 'dbIP', title: '数据库IP', width: 120, align: 'left', sortable: true},
                  {field: 'dbasename', title: '数据库账套名', width: 150, align: 'center', isShow: true},
                  {field: 'dbname', title: '数据库名', width: 150, align: 'left', isShow: true},
                  {field: 'dbusername', title: '用户名', width: 60, align: 'left',editor:'text'},
//                  {field: 'dbpassword', title: '密码', width: 60, align: 'left', sortable: true},
                  {field: 'state', title: '状态', width: 80, align: 'center',
                      formatter: function (value, row, index) {
                          if(value == 0){
                              return '禁用';
                          }else{
                              return '启用';
                          }
                      }
                  },
                  {field: 'isdefault', title: '默认数据库', width: 80, align: 'center',
                      formatter: function (value, row, index) {
                          if(value == 0){
                              return '是';
                          }else{
                              return '否';
                          }
                      }
                  },
                  {field: 'deptid', title: '对应部门', width: 80, align: 'right',
                      editor: {type:'widget',option:{ referwid: 'RL_T_BASE_DEPARTMENT_1',required: false}},
                      formatter: function (value, row, index) {
                          return row.deptname;
                      }
                  }
              ]],
              fit: true,
              rownumbers: true,
              pagination: false,
              singleSelect: true,
              checkOnSelect: true,
              selectOnCheck: true,
              data: JSON.parse('${dbJson}'),
              toolbar: '#erp-buttons-dbConnectList',
              onDblClickRow: function(rowIndex, rowData){
                  beginEditConnection();
              }
          });

          $("#erp-buttons-dbConnectList").buttonWidget({
              initButton: [
                  {},
                  <security:authorize url="/erpconnect/addConnectionInfoBtn.do">
                  {
                      type: 'button-add',
                      handler: function () {
                          $('<div id="erp-dialog-addConnection-content"></div>').appendTo('#erp-dialog-addConnection');
                          $("#erp-dialog-addConnection-content").dialog({
                              title: '新增用友连接信息',
                              maximizable: true,
                              width: 350,
                              height: 300,
                              closed: false,
                              modal: true,
                              cache: false,
                              resizable: true,
                              href: 'erpconnect/showConnectionAddPage.do',
                              onClose:function () {
                                  $("#erp-dialog-addConnection-content").dialog("destroy");
                              }
                          });
                      }
                  },
                  </security:authorize>
                  <security:authorize url="/erpconnect/editConnectionInfoBtn.do">
                  {
                      type: 'button-edit',
                      handler: function () {
                          beginEditConnection();
                      }
                  },
                  </security:authorize>
                  <security:authorize url="/erpconnect/deleteConnectionInfoBtn.do">
                  {
                      type: 'button-delete',
                      handler: function () {
                          var row = $("#erp-datagrid-dbConnectList").datagrid('getSelected');
                          if(row == null){
                              $.messager.alert("提醒","请选择一条记录!");
                              return false;
                          }
                          var index = $("#erp-datagrid-dbConnectList").datagrid('getRowIndex',row);
                          $.messager.confirm("提醒","是否确定删除当前记录?",function(r){
                              if(r){
                                  loading("删除中..");
                                  $.ajax({
                                      url :'erpconnect/deleteERPDBConnect.do',
                                      type:'post',
                                      dataType:'json',
                                      data:{id:row.id},
                                      success:function(flag){
                                          loaded();
                                          if(flag){
                                              $("#erp-datagrid-dbConnectList").datagrid('deleteRow',index);
                                              $.messager.alert("提醒","删除成功");

                                          } else{
                                              $.messager.alert("提醒","删除失败");
                                          }
                                          $("#erp-datagrid-dbConnectList").datagrid("reload");
                                      }
                                  });
                              }
                          });
                      }
                  },
                  </security:authorize>

                  {}
              ],
              buttons: [
                  {},
                  <security:authorize url="/erpconnect/ableConnectionInfoBtn.do">
                  {
                      id: 'button-state',
                      name: '禁用/启用',
                      iconCls: 'button-edit',
                      handler: function () {
                          var row = $("#erp-datagrid-dbConnectList").datagrid('getSelected');
                          if(row == null){
                              $.messager.alert("提醒", "请选择一条记录进行当前操作");
                              return false;
                          }
                          loading("操作中..");
                          $.ajax({
                              url: 'erpconnect/updateERPDBConnectState.do',
                              type: 'post',
                              dataType: 'json',
                              data:{id:row.id},
                              success: function (json) {
                                  loaded();
                                  if (json.flag) {
                                      $.messager.alert("提醒", json.msg);
                                  } else {
                                      $.messager.alert("提醒",json.msg);
                                  }
                              },
                              error: function () {
                                  loaded();
                                  $.messager.alert("错误", "操作失败");
                              }
                          });

                      }
                  },
                  </security:authorize>
                  <security:authorize url="/erpconnect/testERPDBConnect.do">
                  {
                      id: 'button-connecttest',
                      name: '连接测试',
                      iconCls: 'button-getdown',
                      handler: function () {
                          var row = $("#erp-datagrid-dbConnectList").datagrid('getSelected');
                          if(row == null){
                              $.messager.alert("提醒", "请选择一条记录进行连接测试");
                              return false;
                          }
                          loading("测试中..");
                          $.ajax({
                              url: 'erpconnect/testERPDBConnect.do',
                              type: 'post',
                              dataType: 'json',
                              data:{id:row.id},
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
                      }
                  },
                  </security:authorize>
                  {},
              ],
              model:'bill',
              type: 'list',
              tname: 't_erp_db'
          });

          function beginEditConnection() {
              var row = $("#erp-datagrid-dbConnectList").datagrid('getSelected');
              if(row == null){
                  $.messager.alert("提醒","请选择一条记录!");
                  return false;
              }
              $('<div id="erp-dialog-editConnection-content"></div>').appendTo('#erp-dialog-editConnection');
              $("#erp-dialog-editConnection-content").dialog({
                  title: '修改用友连接信息',
                  width: 350,
                  height: 300,
                  closed: false,
                  modal: true,
                  cache: false,
                  maximizable: true,
                  resizable: true,
                  href: 'erpconnect/showConnectionEditPage.do?id=' + row.id,
                  onClose:function () {
                      $("#erp-dialog-editConnection-content").dialog("destroy");
                  }
              });
          }

      })

  </script>
    <script type="application/javascript">
        $(function(){

            $("#erpconnect-button-save").click(function(){
                $("#erpconnect-form-param").submit();
            });
            //采购进货凭证借方
            $("#erpparam-purchaseDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
//                param:[{field:'bproperty',op:'like',value:'1'}],
                onlyLeafCheck:true,
                singleSelect:true
            });
            //采购进货凭证贷方
            $("#erpparam-purchaseCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
//                param:[{field:'bproperty',op:'like',value:'0'}],
                onlyLeafCheck:true,
                singleSelect:true
            });
            //收款凭证借方
            $("#erpparam-collectionDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-collectionCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-withdrawalDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-withdrawalCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-collectionBankDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-collectionBankCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-withdrawalBankDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-withdrawalBankCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //费用支付凭证
            $("#erpparam-bankCostpayDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-bankCostpayCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-cashCostpayDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-cashCostpayCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //货款支付凭证(付款单)借方
            $("#erpparam-paymentDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-paymentCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //货款支付凭证(采购发票冲差)借方
            $("#erpparam-paymentPushDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-paymentPushCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-paymentTotalCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //采购冲暂估凭证借方
            $("#erpparam-purchaseEstimateDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //采购冲暂估凭证贷方
            $("#erpparam-purchaseEstimateCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //采购退货凭证借方
            $("#erpparam-purchaseRejectDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //采购退货凭证贷方
            $("#erpparam-purchaseRejectCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //采购发票借方 未税总金额
            $("#erpparam-PushNotaxamountDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //采购发票借方 库存商品调整
            $("#erpparam-PushAdjustDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //采购发票借方 进项税额
            $("#erpparam-pushTaxamountCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //采购发票贷方 含税总金额
            $("#erpparam-purchasePushCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //代垫录入
            $("#erpparam-journalsheetDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-journalsheetCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            //销售凭证
            $("#erpparam-salesDcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });

            $("#erpparam-salesNotaxamountCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpparam-salesTaxCcode").widget({
                referwid:'RT_T_ERP_ACCOUNTCODE',
                onlyLeafCheck:true,
                singleSelect:true
            });
            $("#erpconnect-button-sales").click(function(){
                var singList = '${signList}';
                if(singList == "" || singList == undefined){
                    $.messager.alert("提醒","请先选择接入的系统和参数再进行凭证添加！");
                }else{
                    $("#salesAccountVouch-dialog").dialog("open");
                }
            });

            function chooseAccount(type) {
                $('<div id="erp-dialog-chooseAccount-content"></div>').appendTo('#erp-dialog-chooseAccount');
                $("#erp-dialog-chooseAccount-content").dialog({
                    title: '选择账套',
                    width: 350,
                    height: 160,
                    closed: false,
                    modal: true,
                    cache: false,
                    maximizable: true,
                    resizable: true,
                    href: 'erpconnect/showChooseAccountPage.do?type='+type,
                    onClose:function () {
                        $("#erp-dialog-chooseAccount-content").dialog("destroy");
                    }
                });
            }

            $("#erpconnect-button-syncSalesArea").click(function(){
                $.messager.confirm("提醒","是否以同步销售区域到ERP中？",function(r){
                    if(r){
                        loading("同步中..");
                        $.ajax({
                            url: 'erpconnect/syncSalesAreaToERP.do',
                            type: 'post',
                            dataType: 'json',
                            success: function (json) {
                                loaded();
                                if (json.flag) {
                                    $.messager.alert("提醒", "同步成功。");
                                } else {
                                    $.messager.alert("提醒", "同步失败。");
                                }
                            },
                            error: function () {
                                loaded();
                                $.messager.alert("错误", "同步失败");
                            }
                        });
                    }
                });
            });
            $("#erpconnect-button-syncCustomerSort").click(function(){
                $.messager.confirm("提醒","是否以同步客户分类到ERP中？",function(r){
                    if(r){
                        loading("同步中..");
                        $.ajax({
                            url: 'erpconnect/syncCustomerSortToERP.do',
                            type: 'post',
                            dataType: 'json',
                            success: function (json) {
                                loaded();
                                if (json.flag) {
                                    $.messager.alert("提醒", "同步成功。");
                                } else {
                                    $.messager.alert("提醒", "同步失败。");
                                }
                            },
                            error: function () {
                                loaded();
                                $.messager.alert("错误", "同步失败");
                            }
                        });
                    }
                });
            });
            $("#erpconnect-button-syncSupplier").click(function(){
                chooseAccount(3);
            });
            $("#erpconnect-button-syncCustomer").click(function(){
                chooseAccount(4);
            });

            //同步会计科目
            $("#erpconnect-button-synccode").click(function(){
                chooseAccount(1);
            });
            //同步会计类别
            $("#erpconnect-button-syncsign").click(function(){
                chooseAccount(2);
            });
            //查看凭证日志
            $("#erpconnect-button-history").click(function(){
                $("#erpparam-history-dialog").dialog({
                    title:'凭证记录日志',
                    width:1200,
                    height:700,
                    closed:true,
                    modal:true,
                    cache:false,
                    maximizable:true,
                    resizable:true,
                    href:'erpconnect/showVoucherHistoeryPage.do',
                });
                $("#erpparam-history-dialog").dialog("open");
            });

            $("#erpconnect-form-param").form({
                onSubmit: function(){
                    var flag = $(this).form('validate');
                    if(flag==false){
                        return false;
                    }
                    loading("提交中..");
                },
                success:function(data){
                    //表单提交完成后 隐藏提交等待页面
                    loaded();
                    var json = $.parseJSON(data);
                    if (json.flag) {
                        $.messager.alert("提醒", "保存成功");
                    } else {
                        $.messager.alert("提醒", "保存失败");
                    }


                }
            });
        });
        function addSalesAccountVouch(){
            loading("生成中..");
            var databaseid = $("#sales-customer-databaseid").val();
            var begindate = $("#salesAccountVouch-begindate").val();
            var enddate = $("#salesAccountVouch-enddate").val();
            var operdate = $("#salesAccountVouch-operdate").val();
            var sign = $("#sales-customer-sign").val();
            $.ajax({
                url: 'erpconnect/addSalesAccountVouch.do',
                type: 'post',
                dataType: 'json',
                data:{begindate:begindate,enddate:enddate,operdate:operdate,sign:sign,databaseid:databaseid},
                success: function (json) {
                    loaded();
                    if (json.flag) {
                        $.messager.alert("提醒", "生成成功。");
                        $("#salesAccountVouch-dialog").dialog("close");
                    } else {
                        $.messager.alert("提醒", "生成失败。"+json.msg);
                        $("#salesAccountVouch-dialog").dialog("close");
                    }
                },
                error: function () {
                    loaded();
                    $.messager.alert("错误", "生成失败");
                }
            });
        }
    </script>
</html>

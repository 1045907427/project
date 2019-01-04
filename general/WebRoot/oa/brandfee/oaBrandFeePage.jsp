<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>品牌费用申请单（支付）页面</title>
    <%@include file="/include.jsp" %>
</head>
  <body>
  <style type="text/css">
      .len300 {
          width: 300px;
      }
      .len308 {
          width: 308px;
      }
      .len394 {
          width: 374px;
      }
  </style>
  <div class="easyui-layout" data-options="fit:true,border:false">
      <div data-options="region:'center',border:false">
          <div id="oa-panel-oaBrandFeePage">
          </div>
      </div>
  </div>

  <div class="easyui-menu" id="oa-contextMenu-oaBrandFeePage">
      <div id="oa-addRow-oaBrandFeePage" data-options="iconCls:'button-add'">添加</div>
      <div id="oa-editRow-oaBrandFeePage" data-options="iconCls:'button-edit'">修改</div>
      <div id="oa-removeRow-oaBrandFeePage" data-options="iconCls:'button-delete'">删除</div>
  </div>
  <div id="oa-dialog-oaBrandFeePage"></div>
  <script type="text/javascript">
      <!--

      var customerMap = {};

      var url = '';
      var type = '${param.type }';
      var id = '${param.id }';
      var step = '${param.step }';
      var from = '${param.from }';

      if(type == 'handle') {

          if(step == '99') {
              url = 'oa/brandfee/oaBrandFeeViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
          } else if(step == '02') {

              url = 'oa/brandfee/oaBrandFeeHandlePage2.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';
          } else {

              url = 'oa/brandfee/oaBrandFeeHandlePage1.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';
          }
      } else if(type == 'view') {

          url = 'oa/brandfee/oaBrandFeeViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
      } else if(type == 'print') {

          url = 'oa/brandfee/oaBrandFeePrintPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
          window.location.href = url;
      }

      $(function() {

          $('#oa-panel-oaBrandFeePage').panel({
              href: url,
              cache: false,
              maximized: true,
              border: false,
              onLoad: function() {

                  // 附件
                  $attach.attach({
                      attach: true<c:if test="${param.type eq 'view' }"> && false</c:if>,
                      businessid: '${param.id }',
                      processid: '${param.processid }'
                  });

                  $comments.comments({
                      businesskey: '${param.id }',
                      processid: '${param.processid }',
                      type: 'vertical',
                      width: '120',
                      agree: null
                  });

                  // 供应商
                  $supplierid.supplierWidget({
                      required: true,
                      onSelect: function(data) {

                          $deptid.widget('setValue', data.buydeptid);
                      }
                  });

                  // 部门
                  $deptid.widget({
                      referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
                      onlyLeafCheck: true,
                      singleSelect: true,
                      required: true,
                      width: 136
                  });

                  // 品牌
                  $brandid.widget({
                      referwid: 'RL_T_BASE_GOODS_BRAND',
                      onlyLeafCheck: true,
                      singleSelect: true,
                      required: true,
                      width: 136
                  });

                  // 费用科目
                  $expensesort.widget({
                      referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT_1',
                      onlyLeafCheck: true,
                      singleSelect: true,
                      <c:choose>
                          <c:when test="${param.type eq 'handle' and param.step ne '99' }">
                              required: true,
                          </c:when>
                          <c:when test="${param.step eq '99' }">
                              required: false,
                          </c:when>
                          <c:otherwise>
                              required: false,
                          </c:otherwise>
                      </c:choose>
                      width: 136
                  });

                  // 银行
                  $paybank.widget({
                      referwid: 'RL_T_BASE_FINANCE_BANK',
                      onlyLeafCheck: true,
                      singleSelect: true,
                      <c:choose>
                          <c:when test="${param.type eq 'handle' and param.step eq '02' }">
                              required: true,
                          </c:when>
                          <c:when test="${param.step eq '99' }">
                              required: false,
                          </c:when>
                          <c:otherwise>
                              required: false,
                          </c:otherwise>
                      </c:choose>
                      width: 136
                  });

                  var feeDetailCol = [
                      [
                          {field:'id', title: '编号', width: 80, hidden: true},
                          {field: 'reason', title: '申请事由', width: 190,
                              formatter: function(value, row, index){

                                  if(value) {
                                      return '<span title="' + value + '">' + value + '</span>';
                                  }
                              },
                              editor: {
                                  type: 'validatebox',
                                  options: {
                                      required: true,
                                      validType: ['maxByteLength[100]']
                                  }
                              }
                          },
                          {
                              field: 'factoryamount', title: '工厂投入', width: 120, align: 'right',
                              formatter: function(value, row, index){

                                  if(value) {
                                      return formatterMoney(value);
                                  }

                                  return value;
                              },
                              editor: {
                                  type: 'numberbox',
                                  options: {
                                      precision: 2,
                                      required: true
                                  }
                              }
                          },
                          {field: 'customerid', title: '客户名称', width: 220,
                              editor: {
                                  type: 'widget',
                                  options: {
                                      referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
                                      required: true,
                                      singleSelect: true,
                                      width: 215,
                                      onSelect: function(data) {
                                          customerMap[data.id] = data.name;
                                      }
                                  }
                              },
                              formatter: function(value, row, index){

                                  if(row.customername) {
                                      customerMap[value] = row.customername;
                                  }

                                  var name = value || '';
                                  if((customerMap[value] || '') != '') {

                                      name = customerMap[value];
                                  }

                                  if(name == '') {
                                      return value;
                                  }

                                  return '<span title="' + name + '">' + name + '</span>';
                              }
                          },
                          {field: 'remark', title: '备注', width: 150,
                              editor: {
                                  type: 'validatebox',
                                  options: {
                                      validType: ['maxByteLength[100]']
                                  }
                              }
                          }
                      ]
                  ];    //feeDetailCol define ends

                  $detail.datagrid({
                      columns: feeDetailCol,
                      border: true,
                      fit: true,
                      rownumbers: true,
                      showFooter: true,
                      singleSelect: true,
                      url: 'oa/brandfee/getBrandFeeDetailList.do',
                      queryParams: {billid: '${param.id}'},
                      onRowContextMenu: function(e, rowIndex, rowData){

                          e.preventDefault();

                          <c:if test="${param.type eq 'view'}">
                            return false;
                          </c:if>

                          $detail.datagrid('selectRow', rowIndex);
                          var selectedRow = $detail.datagrid('getSelected');

                          $("#oa-contextMenu-oaBrandFeePage").menu('show', {
                              left:e.pageX,
                              top:e.pageY
                          });

                          // 该行内容为空时，不能编辑
                          if((selectedRow.supplierid || '') == '' && retFeeDetailEditIndex() != rowIndex) {
                              $("#oa-contextMenu-oaBrandFeePage").menu('disableItem', '#oa-removeRow-oaBrandFeePage');
                          } else {
                              $("#oa-contextMenu-oaBrandFeePage").menu('enableItem', '#oa-removeRow-oaBrandFeePage');
                          }
                      },
                      <c:if test="${param.type eq 'handle' and param.step ne 99}">
                          onDblClickRow: editBrandFeeDetail,
                          onClickRow: endEditBrandFeeDetail,
                      </c:if>
                      onLoadSuccess: function(data) {

                          var rows = data.rows;
                          for(var i = rows.length; i < 10; i++) {

                              $detail.datagrid('appendRow', {});
                          }
                      }
                  })

              }
          });   // panel close

          $('#oa-addRow-oaBrandFeePage').click(addBrandFee);
          $('#oa-editRow-oaBrandFeePage').click(editBrandFee);
          $('#oa-removeRow-oaBrandFeePage').click(removeBrandFee);

      });   // function close

      /**
       * 添加费用明细
       */
      function addBrandFee() {

          if(retFeeDetailEditIndex() >= 0) {

              $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行添加！");
              return true;
          }

          var rows = $detail.datagrid('getRows');

          for(var i in rows) {

              var row = rows[i];
              if(row.reason) {
              } else {
                  $detail.datagrid('beginEdit', i);
                  return true;
              }
          }

          $detail.datagrid('appendRow', {});
          $detail.datagrid('beginEdit', rows.length);
      }

      /**
       * 编辑费用明细
       */
      function editBrandFee() {

          if(retFeeDetailEditIndex() >= 0) {

              $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行修改！");
              return true;
          }

          var selectedRow = $detail.datagrid('getSelected');
          if(selectedRow == null) {
              return true;
          }

          var index = $detail.datagrid('getRowIndex', selectedRow);
          $detail.datagrid('beginEdit', index);

          return true;
      }

      /**
       * 删除费用明细
       */
      function removeBrandFee() {

          var selectedRow = $detail.datagrid('getSelected');
          if(selectedRow == null) {
              return true;
          }

          var index = $detail.datagrid('getRowIndex', selectedRow);
          $detail.datagrid('deleteRow', index);

          computeTotalAmount();
          return true;
      }

      // 修改客户费用明细
      function editBrandFeeDetail() {

          var editIndex = retFeeDetailEditIndex();
          if(editIndex >= 0) {
              $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行修改。");
              return false;
          }

          var selectedRow = $detail.datagrid('getSelected');
          var index = $detail.datagrid('getRowIndex', selectedRow);

          beginEditFeeDetailRow(index);
          return true;
      }

      // 结束对客户费用支付明细的修改
      function endEditBrandFeeDetail() {

          var editIndex = retFeeDetailEditIndex();
          if(editIndex < 0) {
              return true;
          }

          $detail.datagrid('endEdit', editIndex);
          computeTotalAmount();
          return true;
      }

      // 删除客户费用支付明细
      function removeBrandFeeDetail() {

          var editIndex = retFeeDetailEditIndex();
          if(editIndex >= 0) {
              $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行删除。");
              return false;
          }

          var selectedRow = $detail.datagrid('getSelected');
          var index = $detail.datagrid('getRowIndex', selectedRow);

          $detail.datagrid('deleteRow', index);
          return true;
      }

      // 获取目前Datagrid处于编辑的行号，返回-1时，表明当前Datagrid未处于编辑状态
      function retFeeDetailEditIndex() {

          var rows = $detail.datagrid('getRows');

          for(var i = 0; i < rows.length; i++) {
              var editors = $detail.datagrid('getEditors', i);
              if(editors.length > 0) {
                  return i;
              }
          }
          return -1;
      }

      // 编辑明细
      function beginEditFeeDetailRow(index) {

          $detail.datagrid('beginEdit', index);
      }

      /**
       * 计算合计金额
       * @returns {boolean}
       */
      function computeTotalAmount() {

          if(retFeeDetailEditIndex() >= 0) {
              return true;
          }

          var rows = $detail.datagrid('getRows');
          var factoryamount = 0;
          var selfamount = 0;
          for(var i in rows) {

              var row = rows[i];
              factoryamount = parseFloat(factoryamount || 0) + parseFloat(row.factoryamount || 0);
              selfamount = parseFloat(selfamount || 0) + parseFloat(row.selfamount || 0);
          }

          var totalamount = parseFloat(factoryamount || 0) + parseFloat(selfamount || 0);
          $payamount.numberbox('setValue', totalamount);
          return true;
      }

      // 提交客户费用申请单表单
      function oabrandfee_handle_save_form_submit(callBack, args) {

          $form.form({
              onSubmit: function(param) {

                  var flag = $form.form('validate');
                  if(!flag) {

                      loaded();
                      return false;
                  }

                  $detaillist.val(JSON.stringify($detail.datagrid('getRows')));

                  loading("提交中...");
              },
              success: function(data) {

                  loaded();
                  var json;
                  try{
                      json = $.parseJSON(data);
                  }catch(e){
                      $.messager.alert('提醒', '保存失败！');
                      return false;
                  }

                  // 保存失败
                  if(data.flag) {
                      $.messager.alert('提醒', '保存失败！');
                      return false;
                  }

                  // 保存成功
                  $.messager.alert('提醒', '保存成功。');
                  if(callBack.data != undefined && callBack.data != null) {

                      callBack.data(json.backid);
                      return false;
                  }

              }
          }).submit();
      }

      // 提交表单(工作页面提交表单接口方法)
      function workFormSubmit(call, args) {

          endEditBrandFeeDetail();
          if(retFeeDetailEditIndex() >= 0) {

              $.messager.alert("提醒", "明细正处于编辑中，无法提交！");
              return true;
          }

          oabrandfee_handle_save_form_submit(call, args);
      }
      -->
  </script>
  </body>
</html>
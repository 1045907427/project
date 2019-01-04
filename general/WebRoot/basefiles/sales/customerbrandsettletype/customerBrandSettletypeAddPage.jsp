<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户品牌结算方式新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
  <div data-options="region:'north',border:false" style="height: 50px;">
    <form action="" method="post" id="sales-form-customerBrandSettletypeAdd">
      <table cellpadding="2" cellspacing="2" border="0">
        <tr>
          <td>客户：</td>
          <td><input type="text" id="sales-customerid-customerBrandSettletypeAdd" name="customerBrandSettletype.customerid"/></td>
          <td>结算方式：</td>
          <td>
            <input type="text" id="sales-settletype-customerBrandSettletypeAdd" name="customerBrandSettletype.settletype"/>
            <input type="hidden" id="sales-brandids-customerBrandSettletypeAdd" name="customerBrandSettletype.brandids"/>
          </td>
          <td>每月结算日：</td>
          <td><input id="sales-settleday-customerBrandSettletypeAdd" name="customerBrandSettletype.settleday" style="width: 128px;"></td>
        </tr>
      </table>
      <hr/>
    </form>
  </div>
  <div data-options="region:'center',border:false">
    <table id="sales-table-customerBrandSettletypeAdd"></table>
  </div>
  <div data-options="region:'south',border:false">
    <div class="buttonDetailBG" style="text-align:right;">
      <input type="button" value="保存并继续新增" id="sales-savegoon-customerBrandSettletypeAdd" />
      <input type="button" value="保存" id="sales-save-customerBrandSettletypeAdd" />
    </div>
  </div>
</div>
<div id="sales-querybutton-customerBrandSettletypeAdd" style="padding: 0px;">
  <form action=""  method="post" id="sales-form-queryBrand">
    <table cellpadding="2" cellspacing="2" border="0">
      <tr>
        <td>品牌部门：</td>
        <td> <input type="text" id="sales-branddeptid-queryBrand" name="deptid"/> </td>
        <td>供应商：</td>
        <td><input type="text" id="sales-supplierid-queryBrand" name="supplierid"/></td>
        <td>
          <a href="javaScript:void(0);" id="sales-form-queryBrand-querybutton" class="button-qr">查询</a>
          <a href="javaScript:void(0);" id="sales-form-queryBrand-reloadbutton" class="button-qr">重置</a>
        </td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript">
  //保存并继续新增
  function savegoon(){
    loading("提交中..");
    var rows = $("#sales-table-customerBrandSettletypeAdd").datagrid('getChecked');
    var brandids = "";
    for(var i=0;i<rows.length;i++){
      if(brandids==''){
        brandids = rows[i].id;
      }else{
        brandids += ","+rows[i].id;
      }
    }
    $("#sales-brandids-customerBrandSettletypeAdd").val(brandids);
    var queryJSON = $("#sales-form-customerBrandSettletypeAdd").serializeJSON();
    loading('提交中...');
    $.ajax({
      url :'basefiles/addCustomerBrandSettletype.do',
      type:'post',
      dataType:'json',
      data:queryJSON,
      success:function(json){
        loaded();
        if(json.flag){
          $.messager.alert("提醒","保存成功");
          $("#sales-form-queryBrand")[0].reset();
          $("#sales-branddeptid-queryBrand").widget('clear');
          $("#sales-supplierid-queryBrand").widget('clear');
          var query = $("#sales-form-queryBrand").serializeJSON();
          $("#sales-table-customerBrandSettletypeAdd").datagrid('load',query);

          $("#sales-form-customerBrandSettletypeAdd")[0].reset();

          $("#sales-customerid-customerBrandSettletypeAdd").widget('clear');
          $("#sales-table-customerlist-settletype").datagrid("reload");
          $('#sales-table-brandsettletypelist').datagrid('loadData', { total: 0, rows: [] });
        }else{
          $.messager.alert("提醒","保存失败");
        }
      }
    });
  }

  //新增
  function save(){
    var rows = $("#sales-table-customerBrandSettletypeAdd").datagrid('getChecked');
    var brandids = "";
    for(var i=0;i<rows.length;i++){
      if(brandids==''){
        brandids = rows[i].id;
      }else{
        brandids += ","+rows[i].id;
      }
    }
    $("#sales-brandids-customerBrandSettletypeAdd").val(brandids);
    var queryJSON = $("#sales-form-customerBrandSettletypeAdd").serializeJSON();
    loading('提交中...');
    $.ajax({
      url :'basefiles/addCustomerBrandSettletype.do',
      type:'post',
      dataType:'json',
      data:queryJSON,
      success:function(json){
        loaded();
        if(json.flag){
          $.messager.alert("提醒","保存成功");
          $("#sales-div-customerBrandSettletype").dialog("close");

          $("#sales-table-customerlist-settletype").datagrid("reload");
          $('#sales-table-brandsettletypelist').datagrid('loadData', { total: 0, rows: [] });
        }else{
          $.messager.alert("提醒","保存失败");
        }
      }
    });
  }
  $(function(){
    $("#sales-table-customerBrandSettletypeAdd").datagrid({
      fit:true,
      method:'post',
      sortName:'id',
      sortOrder:'asc',
      rownumbers:true,
      singleSelect:false,
      checkOnSelect:true,
      selectOnCheck:true,
      pagination:true,
      pageSize:500,
      toolbar:"#sales-querybutton-customerBrandSettletypeAdd",
      url:'basefiles/getBrandListPage.do',
      columns:[[
        {field:'ck',checkbox:true},
        {field:'id',title:'品牌编码'},
        {field:'name',title:'品牌名称',width:80},
        {field:'deptid',title:'所属部门',width:80,sortable:true,
          formatter:function(val,rowData,rowIndex){
            return rowData.deptName;
          }
        },
        {field:'supplierid',title:'所属供应商',width:200,sortable:true,
          formatter:function(val,rowData,rowIndex){
            return rowData.supplierName;
          }
        },
        {field:'remark',title:'备注',width:100}
      ]]
    });

    $("#sales-customerid-customerBrandSettletypeAdd").widget({ //结算方式参照窗口
      referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
      singleSelect:false,
      width:154,
      required:true,
      onlyLeafCheck:false
    });

    //结算方式
    $("#sales-settletype-customerBrandSettletypeAdd").widget({ //结算方式参照窗口
      referwid:'RL_T_BASE_FINANCE_SETTLEMENT',
      singleSelect:true,
      width:142,
      required:true,
      onlyLeafCheck:false,
      onSelect:function(data){
        if(data.type == '1'){//月结
          $("#sales-settleday-customerBrandSettletypeAdd").numberspinner({
            disabled:false,
            precision:0,
            max:31,
            min:1,
            required:true
          });
        }else{
          $("#sales-settleday-customerBrandSettletypeAdd").numberspinner({
            disabled:true,
            precision:0,
            max:31,
            min:1,
            required:false
          });
        }
      }
    });

    $("#sales-branddeptid-queryBrand").widget({
      referwid:'RL_T_BASE_DEPARTMENT_BUYER',
      singleSelect:true,
      width:130 ,
      onlyLeafCheck:false
    });
    $("#sales-supplierid-queryBrand").widget({
      referwid:'RL_T_BASE_BUY_SUPPLIER',
      singleSelect:true,
      width:154 ,
      onlyLeafCheck:false
    });
    $("#sales-form-queryBrand-querybutton").click(function(){
      var queryJSON = $("#sales-form-queryBrand").serializeJSON();
      $("#sales-table-customerBrandSettletypeAdd").datagrid("load",queryJSON);
    });
    $("#sales-form-queryBrand-reloadbutton").click(function(){
      $("#sales-branddeptid-queryBrand").widget("clear");
      $("#sales-supplierid-queryBrand").widget("clear");
      $("#sales-form-queryBrand").form("reset");
      var queryJSON = $("#sales-form-queryBrand").serializeJSON();
      $("#sales-table-customerBrandSettletypeAdd").datagrid("load",queryJSON);
    });

    //保存并继续新增
    $("#sales-savegoon-customerBrandSettletypeAdd").click(function(){
      if(!$("#sales-form-customerBrandSettletypeAdd").form('validate')){
        $.messager.alert('提醒',"有必填项未填写!");
        return false;
      }
      $.messager.confirm("提醒","是否保存?",function(r){
        if(r){
          savegoon();
        }
      });
    });
    //新增
    $("#sales-save-customerBrandSettletypeAdd").click(function(){
      if(!$("#sales-form-customerBrandSettletypeAdd").form('validate')){
        $.messager.alert('提醒',"有必填项未填写!");
        return false;
      }
      $.messager.confirm("提醒","是否保存?",function(r){
        if(r){
          save();
        }
      });
    });
  });
</script>
</body>
</html>

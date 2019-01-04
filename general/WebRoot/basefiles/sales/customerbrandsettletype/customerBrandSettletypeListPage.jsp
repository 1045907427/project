<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户品牌结算方式</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" title="" data-options="fit:true,border:true" id="sales-layout-customerBrandSettletype">
  <div data-options="region:'west',border:false" style="width:550px;">
    <div id="sales-button-customerlist-settletype" style="padding: 1px;">
      <form action="" method="post" id="sales-form-custoemrlist">
        <table>
          <tr>
            <td width="60px">编码:</td>
            <td><input type="text" name="id" style="width: 110px"/></td>
            <td width="60px">客户名称:</td>
            <td colspan="3"><input type="text" name="name" style="width: 278px" /></td>
          </tr>
          <tr>
            <td width="60px">销售区域:</td>
            <td><input type="text" id="sale-salesarea-customerlist" name="salesarea" /></td>
            <td width="60px">品牌名称:</td>
            <td><input type="text" id="sale-brand-customerlist" name="brandsettletype" /></td>
            <td width="60px">客户分类:</td>
            <td><input type="text" name="customersort" id="sale-customersort-customerlist"/></td>
          </tr>
          <tr>
            <td colspan="3"></td>
            <td colspan="3" style="text-align: right;">
              <a href="javaScript:void(0);" id="sales-query-customerlist" class="button-qr" >查询</a>
              <a href="javaScript:void(0);" id="sales-reload-customerlist" class="button-qr">重置</a>
            </td>
          </tr>
        </table>
      </form>
    </div>
    <table id="sales-table-customerlist-settletype"></table>
  </div>
  <div data-options="region:'center',border:false">
    <table id="sales-table-brandsettletypelist"></table>
    <div id="sales-button-brand-div" class="buttonBG">
      <security:authorize url="/sales/customerBrandSettletypeAddBtn.do">
        <a href="javaScript:void(0);" id="sales-add-customerBrandSettletype" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
      </security:authorize>
      <security:authorize url="/sales/customerBrandSettletypeSaveBtn.do">
        <a href="javaScript:void(0);" id="sales-save-customerBrandSettletype" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="保存">保存</a>
      </security:authorize>
      <security:authorize url="/sales/customerBrandSettletypeDelBtn.do">
        <a href="javaScript:void(0);" id="sales-delete-customerBrandSettletype" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除">删除</a>
      </security:authorize>
      <security:authorize url="/sales/customerBrandSettletypeImportBtn.do">
        <a href="javaScript:void(0);" id="sales-import-customerBrandSettletype" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-import'" title="导入">导入</a>
      </security:authorize>
    </div>
  </div>
</div>
<div id="sales-div-customerBrandSettletype"></div>
<script type="text/javascript">
  var $brandSettletypeList = $('#sales-table-brandsettletypelist');
  var editIndex = undefined,rowindex=undefined;
  var editfiled = null;
  var clickcustomerid = "";
  function endEditing(){
    if (editIndex == undefined){
      return true
    }
    var row = $brandSettletypeList.datagrid('getRows')[editIndex];
    var settletype = row.settletype;
    var settleday = row.settleday;

    var ed = $brandSettletypeList.datagrid('getEditor', {index:editIndex,field:"settletype"});
    if(null != ed){
      settletype = $(ed.target).widget("getValue");
      var settletypename = $(ed.target).widget("getText");
      $brandSettletypeList.datagrid('getRows')[editIndex]['settletypename'] = settletypename;
      $(ed.target).focus();
      $(ed.target).select();
    }
    var ed2 = $brandSettletypeList.datagrid('getEditor', {index:editIndex,field:"settleday"});
    if(null != ed2){
      settleday = $(ed2.target).numberspinner('getValue');
//      getNumberBoxObject(ed2.target).focus();
//      getNumberBoxObject(ed2.target).select();
    }

    if(undefined != settletype && "" != settletype){
      $brandSettletypeList.datagrid('endEdit', editIndex);
    }else{
      return false;
    }

    if(row.initsettletype != settletype || row.initsettleday != settleday){
      row.isedit = "1";
    }else{
      row.isedit = "0";
    }
    $brandSettletypeList.datagrid('updateRow',{index:editIndex, row:row});
    if(row.isedit == "1"){
      $brandSettletypeList.datagrid('checkRow',editIndex);
    }else{
      $brandSettletypeList.datagrid('uncheckRow',editIndex);
    }
    editIndex = undefined;
    return true;
  }

  function onClickRow(index){
    if (endEditing()){
      editIndex = index;
      rowindex=editIndex;
      $brandSettletypeList.datagrid('beginEdit', editIndex);
    }
  }

  $(function(){
    //客户列表
    $('#sales-table-customerlist-settletype').datagrid({
      fit:true,
      title:'客户列表',
      method:'post',
      rownumbers:true,
      pagination:true,
      idField:'id',
      pageSize:100,
      singleSelect:true,
      checkOnSelect:true,
      selectOnCheck:true,
      toolbar:"#sales-button-customerlist-settletype",
      frozenColumns:[[{field:'ck',width:60,checkbox:true}]],
      columns:[[
        {field:'id',title:'编码',width:70,sortable:true},
        {field:'name',title:'客户名称',width:120,sortable:true},
        {field:'settletype',title:'结算方式',width:80,sortable:true,
          formatter: function(value,row,index){
            return row.settletypename;
          }
        },
        {field:'settleday',title:'每月结算日',width:80,sortable:true},
        {field:'pid',title:'上级客户',width:80,sortable:true,
          formatter:function(val,rowData,rowIndex){
            return rowData.pname;
          }
        }
      ]],
      onClickRow:function(index,row){
        var customerid = row.id;
        clickcustomerid = row.id;
        $('#sales-table-brandsettletypelist').datagrid({
          pageNumber:1,
          url: 'basefiles/showCustomerBrandSettletypeList.do?customerid='+customerid
        });
      }
    });
    //品牌结算方式
    $('#sales-table-brandsettletypelist').datagrid({
      fit:true,
      title:'品牌列表',
      method:'post',
      sortName:'brandid',
      sortOrder:'asc',
      rownumbers:true,
      singleSelect:true,
      toolbar:"#sales-button-brand-div",
      columns:[[
        {field:'ck',checkbox:true},
        {field:'id',title:'编码',hidden:true},
        {field:'brandid',title:'品牌编码',width:80},
        {field:'brandname',title:'品牌名称',width:80},
        {field:'settletype',title:'结算方式',width:120,
          formatter: function(value,row,index){
            return row.settletypename;
          },
          styler: function(value,row,index){
            if (row.initsettletype != value){
              return 'background-color:rgb(190, 250, 241);';
            }
          },
          editor:{
            type:'comborefer',
            options:{
              referwid:'RL_T_BASE_FINANCE_SETTLEMENT',
              singleSelect:true,
              width:120,
              required:true,
              onlyLeafCheck:false,
              onSelect: function (data) {
                var row = $brandSettletypeList.datagrid('getSelected');
                var ed2 = $brandSettletypeList.datagrid('getEditor', {index:editIndex,field:"settleday"});
                if(null != ed2){
                  var settleday = $(ed2.target).numberspinner("getValue");
                  if(data.type == '2') {//日结
                    $(ed2.target).numberspinner("clear");
                  }else if(data.type == '1'){//月结
                    if(data.id != row.initsettletype){
                      $(ed2.target).numberspinner("setValue",data.days);
                    }
                  }
                }
              }
            }
          }
        },
        {field:'settleday',title:'每月结算日',width:90,
          styler: function(value,row,index){
            if (row.initsettleday != value){
              return 'background-color:rgb(190, 250, 241);';
            }
          },
          editor:{
            type:'numberspinner',
            options:{
              required:false,
              precision:0,
              max:31,
              min:1
            }
          }
        }
      ]],
      onDblClickRow: function(index, field, value){
        <security:authorize url="/sales/customerBrandSettletypeEditBtn.do">
        onClickRow(index);
        </security:authorize>
      },
      onClickRow: function(index, field, value){
        endEditing();
      }
    });

    $(document).die("keydown").live("keydown",function(event){
      if(event.ctrlKey){
        console.log(editIndex);
        editIndex=rowindex;
        $brandSettletypeList.datagrid('clearSelections');
        $brandSettletypeList.datagrid('selectRow',editIndex+1);
        onClickRow(editIndex+1);
      }
    });

    //客户分类
    $("#sale-customersort-customerlist").widget({
      referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
      singleSelect:true,
      width:100,
      onlyLeafCheck:true
    });
    $("#sale-salesarea-customerlist").widget({
      referwid:'RT_T_BASE_SALES_AREA',
      singleSelect:true,
      width:110,
      onlyLeafCheck:false
    });
    $("#sale-brand-customerlist").widget({
      referwid:'RL_T_BASE_GOODS_BRAND',
      singleSelect:true,
      width:110,
      onlyLeafCheck:false
    });

    //查询
    $("#sales-query-customerlist").click(function(){
      var queryJSON = $("#sales-form-custoemrlist").serializeJSON();
      $('#sales-table-customerlist-settletype').datagrid('options').url = 'basefiles/getCustomerListForPact.do?type=settletype';
      $("#sales-table-customerlist-settletype").datagrid("load",queryJSON);
    });

    //重置
    $("#sales-reload-customerlist").click(function(){
      $("#sales-form-custoemrlist")[0].reset();
      $("#sale-customersort-customerlist").widget('clear');
      $("#sale-salesarea-customerlist").widget('clear');
      $("#sale-brand-customerlist").widget('clear');
      $('#sales-table-customerlist-settletype').datagrid('loadData', { total: 0, rows: [] });
    });

    //新增
    $("#sales-add-customerBrandSettletype").click(function(){
      $("#sales-div-customerBrandSettletype").dialog({
        title:'客户品牌结算方式新增或覆盖',
        width:600,
        height:450,
        closed:false,
        modal:true,
        cache:false,
        maximizable:true,
        resizable:true,
        fit:true,
        href:'basefiles/showCustomerBrandSettletypeAddPage.do'
      });
    });

    //保存
    $("#sales-save-customerBrandSettletype").click(function(){
      endEditing();
      var rows = $brandSettletypeList.datagrid('getChecked');
      for(var i=0;i<rows.length;i++){
        if(rows[i].isedit != "1"){
          var index = $brandSettletypeList.datagrid('getRowIndex',rows[i]);
          $brandSettletypeList.datagrid('uncheckRow',index);
        }
      }
      rows = $brandSettletypeList.datagrid('getChecked');
      if(rows.length == 0){
        $.messager.alert("提醒","请勾选已修改的数据!");
        return false;
      }
      loading('提交中...');
      $.ajax({
        url :'basefiles/editCustomerBrandSettletype.do',
        type:'post',
        dataType:'json',
        data:{rowsjsonstr:JSON.stringify(rows)},
        success:function(json){
          loaded();
          $.messager.alert("提醒",json.msg);
          $brandSettletypeList.datagrid('reload');
        }
      });
    });

    //删除
    $("#sales-delete-customerBrandSettletype").click(function(){
      var rows = $brandSettletypeList.datagrid('getChecked');
      if(null == rows || rows.length == 0){
        $.messager.alert("提醒","请勾选要删除的品牌结算方式！");
        return false;
      }
      var idstr = "";
      for(var i=0;i<rows.length;i++){
        if(idstr == ""){
          idstr = rows[i].id;
        }else{
          idstr += "," + rows[i].id;
        }
      }
      $.messager.confirm("提醒","是否删除品牌结算方式?",function(r){
        if(r){
          loading("删除中..");
          $.ajax({
            url:'basefiles/deleteCustomerBrandSettletypes.do',
            data:{idstr:idstr,customerid:clickcustomerid},
            dataType:'json',
            type:'post',
            success:function(json){
              loaded();
              $.messager.alert("提醒",json.msg);
              $brandSettletypeList.datagrid('reload');
            }
          });
        }
      });
    });

    $("#sales-import-customerBrandSettletype").Excel('import',{
      type:'importUserdefined',
      url:'basefiles/importCustomerBrandSettletype.do',
      importparam:'客户编码、品牌编码、结算方式必输',
      onClose: function(){ //导入成功后窗口关闭时操作，
        $("#sales-table-brandsettletypelist").datagrid("reload");
      }
    });

  });
</script>
</body>
</html>

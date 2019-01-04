<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>特价调整单列表页面</title> 
    <%@include file="/include.jsp" %>   
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center'">
    		<div id="oa-queryDiv-oacustomerListPage" style="padding:5px;height:auto">
	    		<div class="buttonBG" id="oa-buttons-oacustomerListPage" style="height:26px;"></div>  
	    		<form id="oa-queryForm-oacustomerListPage">
	    			<table>
	    				<tr>
	    					<td class="right len80">客户编号：</td>
	    					<td class="len100"><input type="text" class="len80" id="oa-customerid-oacustomerListPage" name="customerid"/></td>
	    					<td class="right len80">客户名称：</td>
	    					<td class="len160"><input type="text" class="len150" id="oa-customername-oacustomerListPage" name="customername"/></td>
	    					<td class="right len80">助&nbsp;记&nbsp;符：</td>
	    					<td class="len100"><input type="text" class="len80" id="oa-shortcode-oacustomerListPage" name="shortcode"/></td>
	    				</tr>
	    				<tr>
	    					<td class="right len80">简称：</td>
	    					<td class="len100"><input type="text" class="len80" id="oa-shortname-oacustomerListPage" name="shortname"/></td>
	    					<td colspan="4">
								<a href="javascript:;" id="oa-queay-oacustomerListPage" class="easyui-linkbutton" iconCls="icon-search" plain="true"  title="[Alt+Q]查询">查询</a>
								<a href="javaScript:;" id="oa-resetQueay-oacustomerListPage" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  title="[Alt+R]重置">重置</a>
								<span id="oa-queryAdvanced-oacustomerListPage"></span>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    		<table id="oa-datagrid-oacustomerListPage" data-options="border:false"></table>
    	</div>
    </div>
    <script type="text/javascript">
    
    var VIEWTABNAME = '新客户登录票查看';
    var ADDTABNAME = '新客户登录票新增';
    var EDITTABNAME = '新客户登录票编辑'
    
    $(function(){

  		$("#oa-buttons-oacustomerListPage").buttonWidget({
	  		initButton: [
	  			{
	  				type: 'button-add',
					handler: function(data){

						top.addTab('oa/oacustomerPage.do?type=add', ADDTABNAME);
					}
	  			},
	  			{
	  				type: 'button-edit',
	  				handler: editOaCustomer
	  			},
	  			/*{
	  				type: 'button-delete',
	  				handler: deleteOaCustomer
	  			},*/
	  			{
	  				type: 'button-view',
					handler: viewOaCustomer
	  			}
	  		],
	  		buttons: [{}],
	  		model: 'base',
	  		type: 'view',
	  		tname: 't_oa_customer',
	  		taburl: '/oa/oacustomerListPage.do',
	  		id: '${param.id }'
	  	});

  		var customerListColJSON = $("#oa-datagrid-oacustomerListPage").createGridColumnLoad({
  			name: 't_oa_customer',
  			frozenCol : [[{field:'id',title:'编号',width:100},
  						  {field:'customerid',title:'客户编号',width:100},  
		        		  {field:'customername',title:'客户名称',width:150}
		    			]],
		    commonCol : [[{field: 'status', title: '状态', width: 60, formatter:
		    					function(value,row,index){
						        	if(value == "2"){
						        		return "已保存";
						        	} else if(value == "3") {
						        		return "已审核"
						        	} else if(value == "6") {
						        		return "审核中"
						        	}
						  		}
						  },
		    			  {field: 'address', title: '客户地址', width: 140}
		    			]]
  		});
  		
  		$("#oa-datagrid-oacustomerListPage").datagrid({
			authority: customerListColJSON,
			frozenColumns: customerListColJSON.frozen,
			columns: customerListColJSON.common,
			fit: true,
			title: '',
			method: 'post',
			rownumbers: true,
			pagination: true,
			idField: 'id',
			singleSelect: true,
			url: 'oa/selectOacustomerList.do',
			toolbar: '#oa-queryDiv-oacustomerListPage',
			onDblClickRow: viewOaCustomer
		}).datagrid("columnMoving");

		//回车事件
		controlQueryAndResetByKey("oa-queay-oacustomerListPage","oa-resetQueay-oacustomerListPage");

		// 查询
		$("#oa-queay-oacustomerListPage").click(function(){
       		var queryJSON = $("#oa-queryForm-oacustomerListPage").serializeJSON();
       		$("#oa-datagrid-oacustomerListPage").datagrid('load', queryJSON);
		});

		// 重置
		$("#oa-resetQueay-oacustomerListPage").click(function(){
			$(':input','#myform').not(':button,:submit,:reset,:hidden').val('');
			$("#oa-datagrid-oacustomerListPage").datagrid('load', {});
		});

		// 通用查询
   		$("#oa-queryAdvanced-oacustomerListPage").advancedQuery({ //通用查询
			//查询针对的表
	 		name:'t_oa_customer',
	 		//查询针对的表格id
	 		datagrid:'oa-datagrid-oacustomerListPage',
	 		plain:true
		});
    });
	
	// 查看OA新客户登录票
	function viewOaCustomer() {
	
		var record = $customerList.datagrid('getSelected');
		if(record == null) {
		
			$.messager.alert("提醒","请选择一条记录");
			return false;
		}
		
		if (top.$('#tt').tabs('exists', VIEWTABNAME)){
			top.closeTab( VIEWTABNAME);
		}
		top.addTab('oa/oacustomerPage.do?type=view&id=' + record.id, VIEWTABNAME);
	}
	
	/*
	// 删除OA新客户登录票
	function deleteOaCustomer() {
	
		var record = $customerList.datagrid('getSelected');
		if(record == null) {
		
			$.messager.alert("提醒","请选择一条记录");
			return false;
		}
		
		$.ajax({
			dataType: 'json',
			url: 'oa/selectOacustomerStatus.do',
			data: 'id=' + record.id,
			method: 'post',
			success: function(data, textStatus) {
				
				if(data.status == '3' || data.status == '6') {
					$.messager.alert("提醒", "该登录单在审核中或已经审核完成，无法删除！");
					return false;
				}
				if (top.$('#tt').tabs('exists', EDITTABNAME)){
					top.closeTab EDITTABNAME);
				}
				top.addTab('oa/oacustomerPage.do?type=edit&id=' + record.id, EDITTABNAME);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			
				$.messager.alert("提醒", "状态检验失败！")
			}
	}*/
	
	// 编辑OA新客户登录票
	function editOaCustomer() {
	
		var record = $customerList.datagrid('getSelected');
		if(record == null) {
		
			$.messager.alert("提醒","请选择一条记录");
			return false;
		}
		
		$.ajax({
			dataType: 'json',
			url: 'oa/selectOacustomerStatus.do',
			data: 'id=' + record.id,
			method: 'post',
			success: function(data, textStatus) {
				
				if(!data.exist) {
					$.messager.alert("提醒", "该登录单已被删除！");
					return false;
				}
				
				if(data.status == '3' || data.status == '6') {
					$.messager.alert("提醒", "该登录单在审核中或已经审核完成，无法修改！");
					return false;
				}
				if (top.$('#tt').tabs('exists', EDITTABNAME)){
					top.closeTab EDITTABNAME);
				}
				top.addTab('oa/oacustomerPage.do?type=edit&id=' + record.id, EDITTABNAME);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
			
				$.messager.alert("提醒", "状态检验失败！")
			}
		});
	}

    var $customerList = $("#oa-datagrid-oacustomerListPage");
    </script>
  </body>
</html>

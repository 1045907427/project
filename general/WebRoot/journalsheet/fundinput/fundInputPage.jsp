<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>资金录入页面</title>
	<%@include file="/include.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'north',border:false" >
		<div id="journalsheet-button-fundInput" class="buttonBG"></div>
	</div>
	<div data-options="region:'center'">
		<div id="journalsheet-table-fundInputBtn" style="padding:2px;height:auto">
			<form action="" id="fundInput-form-ListQuery" method="post">
				<table class="querytable">
					<tr>
						<td>业务日期:</td>
						<td><input id="begintime" name="begintime" class="Wdate" style="width:100px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
							到 <input id="endtime" name="endtime" class="Wdate" style="width:100px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						</td>
						<td>状态:</td>
						<td>
							<select id="journalsheet-widget-statequery" name="state" style="width: 150px;">
								<option></option>
								<option value='1' selected="selected">启用</option>
								<option value='0'>禁用</option>
							</select>
						</td>
						<td>所属部门:</td>
						<td><input id="journalsheet-widget-deptidquery" name="supplierdeptid" type="text"/></td>
					</tr>
					<tr>
						<td>供应商:</td>
						<td colspan="3"><input id="journalsheet-widget-supplierquery" name="supplierid" type="text"/></td>
						<td colspan="2">
							<a href="javaScript:void(0);" id="fundInput-query-List" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="fundInput-query-reloadList" class="button-qr">重置</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<table id="journalsheet-table-fundInput"></table>
		<div id="fundInput-dialog-operate"></div>
	</div>
</div>
<script type="text/javascript">
	var fundInput_AjaxConn = function (Data, Action, Str) {
		if(null != Str && "" != Str){
			loading(Str);
		}
		var MyAjax = $.ajax({
			type: 'post',
			cache: false,
			url: Action,
			data: Data,
			async: false,
			success:function(data){
				loaded();
			}
		})
		return MyAjax.responseText;
	}

	//根据初始的列与用户保存的列生成以及字段权限生成新的列
	var fundInputListColJson=$("#journalsheet-table-fundInput").createGridColumnLoad({
		frozenCol:[[]],
		commonCol:[[
			{field:'businessdate',title:'业务日期',width:80,sortable:true},
			{field:'id',title:'编码',width:80,sortable:true,hidden:true},
			{field:'supplierid',title:'供应商编码',width:70,sortable:true},
			{field:'supplierName',title:'供应商名称',width:210,isShow:true},
			{field:'supplierdeptid',title:'所属部门',width:70,sortable:true,
				formatter:function(val,rowData,rowIndex){
					return rowData.supplierdeptName;
				}
			},
			{field:'advanceamount',title:'预付金额',resizable:true,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'stockamount',title:'库存金额',resizable:true,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'receivableamount',title:'应收金额',resizable:true,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'actingmatamount',title:'代垫金额',resizable:true,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'payableamount',title:'应付金额',resizable:true,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'totalamount',title:'合计金额',resizable:true,sortable:true,align:'right',isShow:true,
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'norecactingmat',title:'代垫未收',resizable:true,sortable:true,align:'right',hidden:true,
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'norecexpenses',title:'费用未付',resizable:true,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'stockdiscount',title:'库存折差',resizable:true,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'currentactingmat',title:'本期代垫',resizable:true,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'remittancerecovery',title:'汇款收回',resizable:true,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'goodsrecovery',title:'货补收回',resizable:true,sortable:true,align:'right',
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return formatterMoney(val);
					}
					else{
						return "0.00";
					}
				}
			},
			{field:'remark',title:'备注',width:100,sortable:true},
			{field:'state',title:'状态',width:60,sortable:true,
				formatter:function(val,rowData,rowIndex){
					if(val != "" && val != null){
						return rowData.stateName;
					}
				}
			},
			{field:'adduserid',title:'制单人编码',width:80,sortable:true,hidden:true},
			{field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
			{field:'addtime',title:'制单时间',width:130,sortable:true,hidden:true}
		]]
	});

	//判断是否加锁
	function isLockData(id,tablename){
		var flag = false;
		$.ajax({
			url :'system/lock/isLockData.do',
			type:'post',
			data:{id:id,tname:tablename},
			dataType:'json',
			async: false,
			success:function(json){
				flag = json.flag
			}
		});
		return flag;
	}
	//加锁
	function isDoLockData(id,tablename){
		var flag = false;
		$.ajax({
			url :'system/lock/isDoLockData.do',
			type:'post',
			data:{id:id,tname:tablename},
			dataType:'json',
			async: false,
			success:function(json){
				flag = json.flag
			}
		});
		return flag;
	}
	function refreshLayout(title, url){
		$('#fundInput-dialog-operate').dialog({
			title: title,
			width: 500,
			height: 380,
			closed: false,
			cache: false,
			href: url,
			modal: true,
			onLoad:function(){
				$("#journalsheet-widget-supplierid").focus();
			}
		});
	}

	$(function(){
		//供应商查询
		$("#journalsheet-widget-supplierquery").widget({
			width:225,
			referwid: 'RL_T_BASE_BUY_SUPPLIER_1',
			singleSelect:true
		});

		$("#journalsheet-widget-deptidquery").widget({
			width:150,
			referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
			singleSelect:true
		});

		//查询
		$("#fundInput-query-List").click(function(){
			var queryJson = $("#fundInput-form-ListQuery").serializeJSON();
			$("#journalsheet-table-fundInput").datagrid("load",queryJson);
		});

		//重置按钮
		$("#fundInput-query-reloadList").click(function(){
			$("#fundInput-form-ListQuery")[0].reset();
			$("#journalsheet-widget-supplierquery").widget('clear');
			$("#journalsheet-widget-deptidquery").widget('clear');
			$("#journalsheet-table-fundInput").datagrid("load",{state:'1'});

		});
		$("#journalsheet-button-fundInput").buttonWidget({
			//初始默认按钮 根据type对应按钮事件
			initButton:[
				{},
				<security:authorize url="/journalsheet/fundinput/fundInputAddBtn.do">
				{
					type:'button-add',//新增
					handler:function(){
						refreshLayout('资金录入【新增】', 'journalsheet/fundinput/fundInputAddPage.do');
					}
				},
				</security:authorize>
				<security:authorize url="/journalsheet/fundinput/fundInputEditBtn.do">
				{
					type:'button-edit',//修改
					handler:function(){
						var fundInput=$("#journalsheet-table-fundInput").datagrid('getSelected');
						if(fundInput==null){
							$.messager.alert("提醒","请选择相应的资金录入!");
							return false;
						}
						var flag = isDoLockData(fundInput.id,"t_js_fundinput");
						if(!flag){
							$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
							return false;
						}
						refreshLayout("资金录入【修改】", 'journalsheet/fundinput/fundInputEditPage.do?id='+fundInput.id);
					}
				},
				</security:authorize>
				<security:authorize url="/journalsheet/fundinput/fundInputDelBtn.do">
				{
					type:'button-delete',//删除
					handler:function(){
						var fundInput=$("#journalsheet-table-fundInput").datagrid('getSelected');
						if(fundInput==null){
							$.messager.alert("提醒","请选择相应的资金录入!");
							return false;
						}
						var flag = isLockData(fundInput.id,"t_js_fundinput");
						if(flag){
							$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
							return false;
						}
						$.messager.confirm("提醒","是否确认删除资金录入?",function(r){
							if(r){
								var ret = fundInput_AjaxConn({id:fundInput.id},'journalsheet/fundinput/deleteFundInput.do','删除中..');
								var retJson = $.parseJSON(ret);
								if(retJson.delFlag){
									$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
									return false;
								}
								if(retJson.flag){
									$("#journalsheet-table-fundInput").datagrid('reload');
									$("#journalsheet-table-fundInput").datagrid('clearSelections');
									$.messager.alert("提醒","删除成功!");
								}
								else{
									$.messager.alert("提醒","删除失败!");
								}
							}
						});
					}
				},
				</security:authorize>
				<security:authorize url="/journalsheet/fundinput/fundInputImportBtn.do">
				{
					type:'button-import',//导入
					attr:{
						type:'importUserdefined',
						importparam:'',//参数描述
						version:'1',//导入页面显示哪个版本1：不显示，2：简化版或合同版，3：Excel文件或瑞家txt导入，4：Excel文件或三和txt导入
						url:'journalsheet/fundinput/addDRFundInput.do',
						onClose: function(){ //导入成功后窗口关闭时操作，
							var queryJSON = $("#fundInput-form-ListQuery").serializeJSON();
							$("#journalsheet-table-fundInput").datagrid("load",queryJSON);
						}
					}
				},
				</security:authorize>
				<security:authorize url="/journalsheet/fundinput/fundInputExportBtn.do">
				{
					type:'button-export',//导出
					attr:{
						queryForm: "#fundInput-form-ListQuery",
						type:'exportUserdefined',
						name:'资金录入列表',
						url:'journalsheet/fundinput/exportFundInputList.do'
					}
				},
				</security:authorize>
				{}
			],
			model:'base',
			type:'list',
			tname:'t_js_fundinput',
			id:''
		});

		$("#journalsheet-table-fundInput").datagrid({
			authority:fundInputListColJson,
			frozenColumns:[[]],
			columns:fundInputListColJson.common,
			fit:true,
			method:'post',
			showFooter: true,
			rownumbers:true,
			sortName:'businessdate',
			sortOrder:'asc',
			pagination:true,
			idField:'id',
			singleSelect:true,
			queryParams:{state:'1'},
			pageSize:100,
			toolbar:'#journalsheet-table-fundInputBtn',
			url:'journalsheet/fundinput/getFundInputListPage.do',
			onClickRow:function(rowIndex, rowData){
				$("#journalsheet-button-fundInput").buttonWidget("setDataID", {id:rowData.id, type:'view'});
				if(rowData.state == "0"){
					$("#journalsheet-button-fundInput").buttonWidget("disableButton", "button-edit");
				}
				else{
					$("#journalsheet-button-fundInput").buttonWidget("enableButton", "button-edit");
				}
			},
			onDblClickRow:function(rowIndex, rowData){
				refreshLayout("资金录入【详情】", 'journalsheet/fundinput/fundInputViewPage.do?id='+rowData.id);
			}
		}).datagrid("columnMoving");
	});
</script>
</body>
</html>

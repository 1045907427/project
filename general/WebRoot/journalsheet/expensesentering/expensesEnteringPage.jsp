<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>贷款录入页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false">
    		<div id="finance-button-expensesEntering" class="buttonBG"></div>
    	</div>
    	<div data-options="region:'center'">
    		<div id="finance-table-expensesEnterBtn" style="padding:2px;height:auto">
    			<form action="" id="expensesEntering-form-ListQuery" method="post">
	    			<table class="querytable">
	    				<tr>
	    					<td>业务日期:</td>
	    					<td><input id="begintime" name="begintime" class="Wdate" style="width:100px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
	    						 到 <input id="endtime" name="endtime" class="Wdate" style="width:100px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	    					</td>
	    					<td>科目名称:</td>
	    					<td><input id="finance-widget-subjectidquery" name="subjectid" type="text"/></td>
  							<td>查询版本:</td>
	    					<td>
	    						<select name="versionstr" style="width: 150px;">
	    							<option value="0">当前数据</option>
	    							<option value="1">历史数据</option>
	    						</select>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td>供应商:</td>
	    					<td><input id="finance-widget-supplierquery" name="supplierid" type="text"/></td>
	    					<td>所属部门:</td>
	    					<td><input id="finance-widget-supplierdeptidquery" name="supplierdeptid" type="text"/></td>
  							<td colspan="2">
	    						<a href="javaScript:void(0);" id="expensesEntering-query-List" class="button-qr">查询</a>
					    		<a href="javaScript:void(0);" id="expensesEntering-query-reloadList" class="button-qr">重置</a>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    		<table id="finance-table-expensesEntering"></table>
    		<div id="expensesEntering-dialog-operate"></div>
            <div id="expensesentering-account-dialog"></div>
    	</div>
    </div>
    <script type="text/javascript">
    	var SR_footerobject  = null;
    	//把form表单的name序列化成JSON对象
   		var initqueryJSON = $("#expensesEntering-form-ListQuery").serializeJSON();
    	var expensesEntering_AjaxConn = function (Data, Action, Str) {
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
    	var expensesEnteringListColJson=$("#finance-table-expensesEntering").createGridColumnLoad({
	     	name:'t_finance_expensesentering',
	     	frozenCol:[[{field:'ck',title:'',width:60,checkbox:true}]],
	     	commonCol:[[
	     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
				{field:'id',title:'编码',width:80,sortable:true,hidden:true},
				{field:'supplierid',title:'供应商编码',width:70,sortable:true},
				{field:'suppliername',title:'供应商名称',width:220,sortable:true},
				{field:'supplierdeptid',title:'所属部门',width:100,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				{field:'subjectid',title:'科目编码',width:80,sortable:true,hidden:true},
				{field:'subjectname',title:'科目名称',width:80,sortable:true},
				{field:'subjectexpenses',title:'科目贷款',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field: 'vouchertimes', title: '生成凭证次数', align: 'center', width:80},
				{field:'remark',title:'备注',width:100,sortable:true},
				{field:'adduserid',title:'添加人编码',width:80,sortable:true,hidden:true},
				{field:'addusername',title:'添加人',width:80,sortable:true,hidden:true},
				{field:'addtime',title:'添加时间',width:130,sortable:true,hidden:true}
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
	   		$('#expensesEntering-dialog-operate').dialog({  
			    title: title,  
			    width: 500,  
			    height: 280,  
			    closed: false,  
			    cache: false,  
			    href: url,  
			    modal: true
			});
			$('#expensesEntering-dialog-operate').dialog('open');
	   	}
	   	
	   	//通用查询组建调用
		$("#expensesEntering-query-advanced").advancedQuery({
			//查询针对的表
	 		name:'t_finance_expensesentering',
	 		//查询针对的表格id
	 		datagrid:'finance-table-expensesEntering'
		});
	   	
	   	$(function(){
	   		//科目查询
		  	$("#finance-widget-subjectidquery").widget({
		  		width:150,
				name:'t_finance_expensesentering',
				col:'subjectid',
				singleSelect:true
			});
			
			//供应商查询
		  	$("#finance-widget-supplierquery").widget({
		  		width:225,
				name:'t_finance_expensesentering',
				col:'supplierid',
				singleSelect:true
			});
			
			$("#finance-widget-supplierdeptidquery").widget({
		  		width:150,
				name:'t_finance_expensesentering',
				col:'supplierdeptid',
				singleSelect:true
			});
			
	   		//查询
			$("#expensesEntering-query-List").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#expensesEntering-form-ListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#finance-table-expensesEntering").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#expensesEntering-query-reloadList").click(function(){
				$("#finance-widget-supplierquery").widget("clear");
				$("#finance-widget-subjectidquery").widget("clear");
				$("#finance-widget-supplierdeptidquery").widget("clear");
				$("#expensesEntering-form-ListQuery")[0].reset();
				var queryJSON = $("#expensesEntering-form-ListQuery").serializeJSON();
				$("#finance-table-expensesEntering").datagrid("load",queryJSON);
				
			});
	   		$("#finance-button-expensesEntering").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/journalsheet/expensesEntering/expensesEnteringAddBtn.do">
					{
						type:'button-add',//新增 
						handler:function(){
							refreshLayout('贷款录入【新增】', 'journalsheet/expensesEntering/expensesEnteringAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/expensesEntering/expensesEnteringEditBtn.do">
		 			{
			 			type:'button-edit',//修改 
			 			handler:function(){
							var expensesEntering=$("#finance-table-expensesEntering").datagrid('getSelected');
				  			if(expensesEntering==null){
				  				$.messager.alert("提醒","请选择相应的贷款录入!");
				  				return false;
				  			}
				  			var flag = isDoLockData(expensesEntering.id,"t_finance_expensesentering");
							if(!flag){
								$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
								return false;
							}
			     			refreshLayout("贷款录入【修改】", 'journalsheet/expensesEntering/expensesEnteringEditPage.do?id='+expensesEntering.id);
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/journalsheet/expensesEntering/expensesEnteringDelBtn.do">
		 			{
			 			type:'button-delete',//删除
			 			handler:function(){
			 				var rows = $("#finance-table-expensesEntering").datagrid('getChecked');
				  			if(rows.length == 0){
				  				$.messager.alert("提醒","请选择相应的贷款录入!");
				  				return false;
				  			}
				  			var ids = "",noids = "";
				  			for(var i=0;i<rows.length;i++){
				  				var flag = isLockData(rows[i].id,"t_finance_expensesentering");
								if(flag){
									if(noids == ""){
										noids = rows[i].id;
									}else{
										noids += "," + rows[i].id;
									}
								}else{
									if(ids == ""){
										ids = rows[i].id;
									}else{
										ids += "," + rows[i].id;
									}	
								}
				  			}
				  			if(noids != ""){
				  				$.messager.alert("警告",""+noids+"数据正在被其他人操作，暂不能删除！");
				  			}
				  			if(ids != ""){
					  			$.messager.confirm("提醒","是否确认删除贷款录入?",function(r){
					  				if(r){
					  					var ret = expensesEntering_AjaxConn({ids:ids},'journalsheet/expensesEntering/deleteExpensesEntering.do','删除中..');
										var retJson = $.parseJSON(ret);
										if(retJson.delFlag){
						  					$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
						  					return false;
						  				}
										if(retJson.flag){
											$("#finance-table-expensesEntering").datagrid('reload');
											$("#finance-table-expensesEntering").datagrid('clearSelections');
											$.messager.alert("提醒","删除成功!");
										}
										else{
											$.messager.alert("提醒","删除失败!");
										}
					  				}
					  			});
				  			}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/journalsheet/expensesEntering/expensesEnteringImportBtn.do">
		 			{
			 			type:'button-import',//导入
			 			attr:{
			 				clazz: "journalSheetService", //spring中注入的类名
					 		method: "addDRExpensesEntering", //插入数据库的方法
					 		tn: "t_finance_expensesentering", //表名
				            module: 'journalsheet', //模块名，
					 		pojo: "ExpensesEntering", //实体类名，将和模块名组合成com.hd.agent.journalsheet.model.ExpensesEntering。
							onClose: function(){ //导入成功后窗口关闭时操作，
						         $("#finance-table-expensesEntering").datagrid('reload');	//更新列表	                                                                          
							}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/journalsheet/expensesEntering/expensesEnteringExportBtn.do">
		 			{
			 			type:'button-export',//导出 
			 			attr:{
							queryForm: "#expensesEntering-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
					 		tn: 't_finance_expensesentering', //表名
					 		name:'货款录入列表'
						}
		 			},
		 			</security:authorize>
					{}
	 			],
                buttons:[
                    <security:authorize url="/erpconnect/addExpensesVouch.do">
                    {
                        id:'expensesentering-account',
                        name:'生成凭证',
                        iconCls:'button-audit',
                        handler: function() {
                            var rows = $("#finance-table-expensesEntering").datagrid('getChecked');
                            var supplierids = "";
                            if (rows == null || rows.length == 0) {
                                $.messager.alert("提醒", "请选择至少一条记录");
                                return false;
                            }
                            var ids = "";
                            for(var i=0;i<rows.length;i++){
                                if("3" != rows[i].subjectid){
                                    $.messager.alert("提醒", "只有科目为费用支付的单据能生成凭证!");
                                    return false;
                                }
                                if(i==0){
                                    ids = rows[i].id;
                                }else{
                                    ids += "," + rows[i].id;
                                }
                            }
                            $("#expensesentering-account-dialog").dialog({
                                title:'费用支付凭证',
                                width:400,
                                height:260,
                                closed:false,
                                modal:true,
                                cache:false,
                                href:'erpconnect/showExpensesVouchPage.do?voucherType=1',
                                onLoad:function(){
                                    $("#expensesVouch-ids").val(ids);
                                }
                            });
                        }
                    }
                    </security:authorize>
                ],
	 			model:'base',
				type:'list',
				tname:'t_finance_expensesentering',
				id:''
     		});
     		
     		$("#finance-table-expensesEntering").datagrid({ 
     			authority:expensesEnteringListColJson,
	  	 		frozenColumns: expensesEnteringListColJson.frozen,
				columns:expensesEnteringListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'businessdate',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
	  	 		idField:'id',
				pageSize:100,
				singleSelect:false,
		 		checkOnSelect:true,
			 	selectOnCheck:true,
				toolbar:'#finance-table-expensesEnterBtn',
			    url:'journalsheet/expensesEntering/getExpensesEnteringListPage.do',
			    queryParams:initqueryJSON,
			    onClickRow:function(rowIndex, rowData){
			    	$("#finance-button-expensesEntering").buttonWidget("setDataID", {id:rowData.id, type:'view'});
			    },
		    	onDblClickRow:function(rowIndex, rowData){
	     			refreshLayout("贷款录入【详情】", 'journalsheet/expensesEntering/expensesEnteringViewPage.do?id='+rowData.id);
		    	},
		    	onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						SR_footerobject = footerrows[0];
						countTotalAmount();
					}
		 		},
				onCheckAll:function(){
					countTotalAmount();
				},
				onUncheckAll:function(){
					countTotalAmount();
				},
				onCheck:function(){
					countTotalAmount();
				},
				onUncheck:function(){
					countTotalAmount();
				}
			}).datagrid("columnMoving");
	   	});
	   	
	   	function countTotalAmount(){
   			var rows =  $("#finance-table-expensesEntering").datagrid('getChecked');
   			var subjectexpenses = 0;
       		
       		for(var i=0;i<rows.length;i++){
       			subjectexpenses = Number(subjectexpenses)+Number(rows[i].subjectexpenses == undefined ? 0 : rows[i].subjectexpenses);
       		}
       		var foot=[{suppliername:'选中合计',subjectexpenses:subjectexpenses}];
       		if(null!=SR_footerobject){
           		foot.push(SR_footerobject);
       		}
       		$("#finance-table-expensesEntering").datagrid("reloadFooter",foot);
   		}
    </script>
  </body>
</html>

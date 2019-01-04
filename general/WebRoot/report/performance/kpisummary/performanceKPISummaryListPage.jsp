<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门考核指标汇总数据页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
    <%
  	boolean isEdit=false;
  %>
  <security:authorize url="/report/performance/showPerformanceKPISummaryEditPageBtn.do">
  	<% isEdit=true; %>
  </security:authorize>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north'" style="height: 30px;">
    		<div id="performance-button-kpiSummary"></div>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
	  		<div id="performance-table-kpiSummaryBtn">
		  		<form action="" id="performance-form-kpiSummaryListQuery" method="post" style="padding-left: 5px; padding-top: 2px;">
		   			<table cellpadding="0" cellspacing="1" border="0">
		   				<tr>
		   					<td style="padding-left: 10px;">业务日期:&nbsp;</td>
	    					<td><input id="begintime" name="begintime" value="${yearfirstmonth }"  class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM',maxDate:'${yearcurmonth }'})"/>
   								到&nbsp<input id="endtime" name="endtime" value="${yearcurmonth }"  class="Wdate" style="width:100px;height: 20px;" onclick="WdatePicker({'dateFmt':'yyyy-MM',maxDate:'${yearcurmonth }'})"  />
	    					</td>	   				    					
		   					<td style="padding-left: 10px;">品牌部门:&nbsp;</td>
		   					<td><input id="performance-widget-kpiSummary-deptid" name="supplierid" type="text" style="width: 150px;"/></td>	   				    					
		   					<td>
		   						<a href="javaScript:void(0);" id="performance-query-kpiSummaryList" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
					    		<a href="javaScript:void(0);" id="performance-query-kpiSummary-reloadList" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
		   					</td>
		   				</tr>
		   			</table>
		   		</form>
	  		</div>
	  		<table id="performance-table-kpiSummary"></table>
	  		<div style="display:none">
	   			<div id="performance-dialog-add-operate"></div>
	   			<div id="performance-dialog-edit-operate"></div>
	   			<div id="performance-dialog-view-operate"></div>
	   			<a href="javaScript:void(0);" id="performance-kpisummary-buttons-exportclick" style="display: none"title="导出">导出</a>
	   		</div>
   	 	</div>
   	 </div>
   	 
    <script type="text/javascript">
		var footerobject=null;
		
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var costsFeeListColJson=$("#performance-table-kpiSummary").createGridColumnLoad({
	     	//name:'t_report_performance_kpisummary',
	     	frozenCol:[[
				{field:'idok',checkbox:true,isShow:true}
	     	]],
	     	commonCol:[[
	     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
				{field:'deptid',title:'所属部门',width:100,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.deptname;
					}
				},
				{field:'status',title:'状态',width:80,sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
						if(value=='3'){
							return "审核";
						}else{
							return "未审核";
						}	
	        		}
	        	},
				{field:'salesamount',title:'销售金额',width:80,sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value);	        		
	        		}
	        	},
				{field:'jcfhamount',title:'签呈返还',width:80,sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value);	        		
	        		}
	        	},
				{field:'hsmlamount',title:'含税毛利',width:80,sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value);	        		
	        		}
	        	},
				{field:'xjamount',title:'小计',width:80,sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value);	        		
	        		}
	        	},
				{field:'xjrate',title:'小计率',width:65,sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value)+"%";	        		
	        		}
	        	},
				{field:'fyamount',title:'费用额',width:65,sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value);	        		
	        		}
	        	},
				{field:'fyrate',title:'费用额率',width:65,sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value)+"%";	        		
	        		}
	        	},
				{field:'jlamount',title:'净利额',width:65,sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value);	        		
	        		}
	        	},
				{field:'jlrate',title:'净利率',width:65,sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value)+"%";	        		
	        		}
	        	},
				{field:'pjqmkcamount',title:'平均期末库存额',sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value);	        		
	        		}
	        	},
				{field:'pjkczzday',title:'平均库存周转天数',sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value);	        		
	        		}
	        	},
				{field:'pjzjzyamount',title:'平均资金占用额',sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value);	        		
	        		}
	        	},
				{field:'zjlrrate',title:'资金利润率',width:65,sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value)+"%";	        		
	        		}
	        	},
				{field:'qmddfyyeamount',title:'期末代垫费用余额',sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value);	        		
	        		}
	        	},
				{field:'ddfyzyrate',title:'代垫费占用率',sortable:true,align:'right',
					formatter:function(value,rowData,rowIndex){
	  					return formatterMoney(value)+"%";	        		
	        		}
	        	}			
			]]
	     });
	     
		$(function(){


			$("#performance-button-kpiSummary").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/report/performance/showPerformanceKPISummaryAddPageBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							kpiSummaryAddDialog('考核指标汇总数据【新增】', 'report/performance/showPerformanceKPISummaryAddPage.do');							
						}
					},
					</security:authorize>
					<security:authorize url="/report/performance/showPerformanceKPISummaryEditPageBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var dataRow=$("#performance-table-kpiSummary").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的考核指标汇总数据!");
								return false;
							}
							kpiSummaryEditDialog("考核指标汇总数据【更新】", 'report/performance/showPerformanceKPISummaryEditPage.do?id='+dataRow.id);
						}
					},
					</security:authorize>
					<security:authorize url="/report/performance/deletePerformanceKPISummaryBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var rows =  $("#performance-table-kpiSummary").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的考核指标汇总数据!");
								return false;
							}
							var idarrs=new Array();
							if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].id && rows[i].id!=""){
							    		idarrs.push(rows[i].id);
						    		}
					    		}
							}
							$.messager.confirm("提醒","是否确认删除部门考核指标汇总数据?",function(r){
								if(r){
				  				loading();
				  				$.ajax({
							        type: 'post',
							        cache: false,
							        url: 'report/performance/deletePerformanceKPISummaryMore.do',
							        data: {idarrs:idarrs.join(",")},
									dataType:'json',
							        success:function(json){
							        	loaded();
							        	if(json.flag==true){
						  					$.messager.alert("提醒", "删除成功数："+ json.isuccess +"<br />删除失败数："+ json.ifailure );							  					
											$("#performance-table-kpiSummary").datagrid('reload');
											$("#performance-table-kpiSummary").datagrid('clearSelections');	
						  		        }
						  		        else{
						  		        	$.messager.alert("提醒","删除失败");
						  		        }
							        }
							    });
						    }
							});
						}
					},
					</security:authorize>
					<security:authorize url="/report/performance/auditPerformanceKPISummaryMore.do">
					{
						id:'button-id-audit',
						name:'审核 ',
						iconCls:'button-audit',
						handler: function(){
							var rows =  $("#performance-table-kpiSummary").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的部门考核指标汇总数据!");
								return false;
							}
							var idarrs=new Array();
							var errorIdarr=new Array();
							if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].id && rows[i].id!=""){
							    		idarrs.push(rows[i].id);
						    		}
						    		if(rows[i].id){
							    		if(rows[i].status=='3'){
							    			errorIdarr.push(rows[i].id);
							    		}
									}
					    		}
							}
							if(errorIdarr.length>0){
								$.messager.alert("提醒","只有未审核的考核指标汇总数据才能被审核");
								return false;
							}
							$.messager.confirm("提醒","是否确认审核部门考核指标汇总数据?",function(r){
								if(r){
				  				loading();
				  				$.ajax({
							        type: 'post',
							        cache: false,
							        url: 'report/performance/auditPerformanceKPISummaryMore.do',
							        data: {idarrs:idarrs.join(",")},
									dataType:'json',
							        success:function(json){
							        	loaded();
							        	if(json.flag==true){
						  					$.messager.alert("提醒", "审核成功数："+ json.isuccess +"<br />审核失败数："+ json.ifailure );							  					
											$("#performance-table-kpiSummary").datagrid('reload');
											$("#performance-table-kpiSummary").datagrid('clearSelections');	
						  		        }
						  		        else{
						  		        	$.messager.alert("提醒","删除失败");
						  		        }
							        }
							    });
						    }
							});
						}
					},
					</security:authorize>
					<security:authorize url="/report/performance/oppauditPerformanceKPISummaryMore.do">
					{
						id:'button-id-oppaudit',
						name:'反审 ',
						iconCls:'button-oppaudit',
						handler: function(){
							var rows =  $("#performance-table-kpiSummary").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的部门考核指标汇总数据!");
								return false;
							}
							var idarrs=new Array();
							var errorIdarr=new Array();
							if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].id && rows[i].id!=""){
							    		idarrs.push(rows[i].id);
						    		}
						    		if(rows[i].id){
							    		if(rows[i].status!='3'){
							    			errorIdarr.push(rows[i].id);
							    		}
									}
					    		}
							}
							if(errorIdarr.length>0){
								$.messager.alert("提醒","抱歉，只有审核通过的考核指标汇总数据才能被反审核。");
								return false;
							}
		
							$.messager.confirm("提醒","是否反审核部门考核指标汇总数据？",function(r){
								if(r){
									loading("审核中..");
									$.ajax({   
							            url :'report/performance/oppauditPerformanceKPISummaryMore.do',
								        data: {idarrs:idarrs.join(",")},
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							  					$.messager.alert("提醒", "反审核成功数："+ json.isuccess +"<br />反审核失败数："+ json.ifailure );							  					
												$("#performance-table-kpiSummary").datagrid('reload');
												$("#performance-table-kpiSummary").datagrid('clearSelections');						            		
								            }else{
								            	$.messager.alert("提醒","反审失败");
								            }
							            }
							        });	
								}
							});
						}
					},
					</security:authorize>			
					<security:authorize url="/report/performance/performanceKPISummaryExportBtn.do">
					{
						id:'button-export-excel',
						name:'导出',
						iconCls:'button-export',
						handler: function(){
						
							var rows =  $("#performance-table-kpiSummary").datagrid('getChecked');

							//查询参数直接添加在url中         
				    		var idarrs=new Array();
				    		if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].id && rows[i].id!=""){
							    		idarrs.push(rows[i].id);
						    		}
					    		}
				    		}
				    		
							$("#performance-kpisummary-buttons-exportclick").Excel('export',{
								queryForm: "#performance-form-kpiSummaryListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
						 		type:'exportUserdefined',
						 		name:'部门考核指标汇总数据',
						 		fieldParam:{idarrs:idarrs.join(",")},
						 		url:'report/performance/exportPerformanceKPISummaryPageData.do'
							});
							$("#performance-kpisummary-buttons-exportclick").trigger("click");
						}
					},
					</security:authorize>
					{}		
				],
	 			model:'bill',
				type:'list',
				datagrid:'performance-table-kpiSummary',
				tname:'t_report_performance_kpisummary',
				id:''
     		});
			
			
			var initQueryJSON = $("#performance-form-kpiSummaryListQuery").serializeJSON()
     		$("#performance-table-kpiSummary").datagrid({ 
     			authority:costsFeeListColJson,
	  	 		frozenColumns:costsFeeListColJson.frozen,
				columns:costsFeeListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'部门考核指标汇总数据',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'businessdate',
	  	 		sortOrder:'desc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		queryParams:initQueryJSON,
				pageSize:20,
				toolbar:'#performance-table-kpiSummaryBtn',
				pageList:[20,50,200,300,500],
				url: 'report/performance/showPerformanceKPISummaryPageData.do',
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
					}
		 		},
			    onSelect:function(rowIndex, rowData){
			    	if(rowData.status=="3" || rowData.status=="4"){
			    		$("#performance-button-kpiSummary").buttonWidget("disableButton", 'button-id-edit');
			    		$("#performance-button-kpiSummary").buttonWidget("disableButton", 'button-id-delete');
			    		$("#performance-button-kpiSummary").buttonWidget("disableButton", 'button-id-audit');
			    		$("#performance-button-kpiSummary").buttonWidget("enableButton", 'button-id-oppaudit');
			    	}else{
			    		$("#performance-button-kpiSummary").buttonWidget("enableButton", 'button-id-edit');
			    		$("#performance-button-kpiSummary").buttonWidget("enableButton", 'button-id-delete');
			    		$("#performance-button-kpiSummary").buttonWidget("enableButton", 'button-id-audit');
			    		$("#performance-button-kpiSummary").buttonWidget("disableButton", 'button-id-oppaudit');
				    	
			    	}
			    },
		    	onDblClickRow:function(rowIndex, rowData){
			    	if(rowData.status=="3" || rowData.status=="4"){
			    		kpiSummaryViewDialog("考核指标汇总数据【查看】", 'report/performance/showPerformanceKPISummaryViewPage.do?id='+rowData.id);
			    	}else{
		 			<%if(isEdit){%>
		 				kpiSummaryEditDialog("考核指标汇总数据【更新】", 'report/performance/showPerformanceKPISummaryEditPage.do?id='+rowData.id);
		 			<% }else { %>
	 					kpiSummaryViewDialog("考核指标汇总数据【查看】", 'report/performance/showPerformanceKPISummaryViewPage.do?id='+rowData.id);
		 			<%}%>
			    	}
		    	},
				onCheckAll:function(){
		 			selectCountTotalAmount();
				},
				onUncheckAll:function(){
					selectCountTotalAmount();
				},
				onCheck:function(){
					selectCountTotalAmount();
				},
				onUncheck:function(){
					selectCountTotalAmount();
				}
			}).datagrid("columnMoving");

			$("#performance-widget-kpiSummary-deptid").widget({
		  		width:150,
				name:'t_report_performance_kpisummary',
				col:'deptid',
				singleSelect:true
			});

     		//查询
			$("#performance-query-kpiSummaryList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#performance-form-kpiSummaryListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#performance-table-kpiSummary").datagrid('load',queryJSON);	
			});
			
			//重置按钮
			$("#performance-query-kpiSummary-reloadList").click(function(){
				$("#performance-form-kpiSummaryListQuery")[0].reset();
				$("#performance-widget-kpiSummary-deptid").widget('clear');

	      		var queryJSON = $("#performance-form-kpiSummaryListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#performance-table-kpiSummary").datagrid('load',queryJSON);
			});
			
		});
		
		function selectCountTotalAmount(){
    		var rows =  $("#performance-table-kpiSummary").datagrid('getChecked');
    		if(null==rows || rows.length==0){
           		var foot=[];
    			if(null!=footerobject){
	        		foot.push(emptyChooseObjectFoot());
	        		foot.push(footerobject);
	    		}
    			$("#performance-table-kpiSummary").datagrid("reloadFooter",foot);
           		return false;
       		}
    		
    		var salesamount=0;
    		var jcfhamount=0;
    		var hsmlamount=0;
    		var xjamount=0;
    		var xjrate=0;
    		var fyamount=0;
    		var fyrate=0;
    		var jlamount=0;
    		var jlrate=0;
    		var pjqmkcamount=0;
    		var pjkczzday=0;
    		var pjzjzyamount=0;
    		var zjlrrate=0;
    		var qmddfyyeamount=0;
    		var ddfyzyrate=0;
    		
    		for(var i=0;i<rows.length;i++){
    			salesamount = Number(salesamount)+Number(rows[i].salesamount == undefined ? 0 : rows[i].salesamount);
    			jcfhamount = Number(jcfhamount)+Number(rows[i].jcfhamount == undefined ? 0 : rows[i].jcfhamount);
    			hsmlamount = Number(hsmlamount)+Number(rows[i].hsmlamount == undefined ? 0 : rows[i].hsmlamount);
    			xjamount = Number(xjamount)+Number(rows[i].xjamount == undefined ? 0 : rows[i].xjamount);
    			fyamount = Number(fyamount)+Number(rows[i].fyamount == undefined ? 0 : rows[i].fyamount);
    			jlamount = Number(jlamount)+Number(rows[i].jlamount == undefined ? 0 : rows[i].jlamount);
    			pjqmkcamount = Number(pjqmkcamount)+Number(rows[i].pjqmkcamount == undefined ? 0 : rows[i].pjqmkcamount);
    			pjzjzyamount = Number(pjzjzyamount)+Number(rows[i].pjzjzyamount == undefined ? 0 : rows[i].pjzjzyamount);
    			qmddfyyeamount = Number(qmddfyyeamount)+Number(rows[i].qmddfyyeamount == undefined ? 0 : rows[i].qmddfyyeamount);
    			
    			if(salesamount!=0){
        			xjrate=Number(xjamount/salesamount*100);
        			fyrate=Number(fyamount/salesamount*100);
        			jlrate=Number(jlamount/salesamount*100);
        			pjkczzday=Number(pjqmkcamount/salesamount * 30);
    			}
    			if(pjzjzyamount!=0){
        			zjlrrate=Number(jlamount / pjzjzyamount*100);
        			ddfyzyrate= Number(qmddfyyeamount / pjzjzyamount*100 );
    			}    		
    		}
    		var foot=[{deptname:'选中金额',
		    			salesamount : salesamount,
		    			jcfhamount : jcfhamount,
		    			hsmlamount : hsmlamount,
		    			xjamount : xjamount,
		    			xjrate : xjrate,
		    			fyamount : fyamount,
		    			fyrate : fyrate,
		    			jlamount : jlamount,
		    			jlrate : jlrate,
		    			pjqmkcamount : pjqmkcamount,
		    			pjkczzday : pjkczzday,
		    			pjzjzyamount : pjzjzyamount,
		    			zjlrrate : zjlrrate,
		    			qmddfyyeamount : qmddfyyeamount,
		    			ddfyzyrate : ddfyzyrate
        			}];
    		if(null!=footerobject){
        		foot.push(footerobject);
    		}
    		$("#performance-table-kpiSummary").datagrid("reloadFooter",foot);
    	}
    	function emptyChooseObjectFoot(){     		
    		var foot={deptname:'选中金额',
	    			salesamount : 0,
	    			jcfhamount : 0,
	    			hsmlamount : 0,
	    			xjamount : 0,
	    			xjrate : 0,
	    			fyamount : 0,
	    			fyrate : 0,
	    			jlamount : 0,
	    			jlrate : 0,
	    			pjqmkcamount : 0,
	    			pjkczzday : 0,
	    			pjzjzyamount : 0,
	    			zjlrrate : 0,
	    			qmddfyyeamount : 0,
	    			ddfyzyrate : 0
			};
			return foot;
    	}
    	function kpiSummaryAddDialog(title, url){
	   		$('#performance-dialog-add-operate').dialog({  
			    title: title,  
			    width: 450,  
			    height: 180,  
			    closed: false,  
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			}
			});
			$('#performance-dialog-add-operate').dialog('open');
	   	}
	    function kpiSummaryEditDialog(title, url){
	   		$('#performance-dialog-edit-operate').dialog({  
			    title: title,  
			    width: 680,  
			    height: 450,  
			    closed: false,  
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			}
			});
			$('#performance-dialog-edit-operate').dialog('open');
	   	}
	    function kpiSummaryViewDialog(title, url){
	   		$('#performance-dialog-view-operate').dialog({  
			    title: title,  
			    width: 680,  
			    height: 450,  
			    closed: false,  
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
	   			}
			});
			$('#performance-dialog-view-operate').dialog('open');
	   	}
    </script>
  </body>
</html>

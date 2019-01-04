<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门业绩考核数据页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
    <%
  	boolean isEdit=false;
  %>
  <security:authorize url="/report/performance/showPerformanceKPIScoreEditPageBtn.do">
  	<% isEdit=true; %>
  </security:authorize>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north'" style="height: 30px;">
    		<div id="performance-button-kpiScore"></div>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
	  		<div id="performance-table-kpiScoreBtn">
		  		<form action="" id="performance-form-kpiScoreListQuery" method="post" style="padding-left: 5px; padding-top: 2px;">
		   			<table cellpadding="0" cellspacing="1" border="0">
		   				<tr>
		   					<td style="padding-left: 10px;">业务日期:&nbsp;</td>
	    					<td><input id="begintime" name="begintime" value="${yearfirstmonth }"  class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM',maxDate:'${yearcurmonth }'})"/>
   								到&nbsp<input id="endtime" name="endtime" value="${yearcurmonth }"  class="Wdate" style="width:100px;height: 20px;" onclick="WdatePicker({'dateFmt':'yyyy-MM',maxDate:'${yearcurmonth }'})"  />
	    					</td>	   				    					
		   					<td style="padding-left: 10px;">品牌部门:&nbsp;</td>
		   					<td><input id="performance-widget-kpiScore-deptid" name="deptid" type="text" style="width: 150px;"/></td>	   				    					
		   					<td>
		   						<a href="javaScript:void(0);" id="performance-query-kpiScoreList" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
					    		<a href="javaScript:void(0);" id="performance-query-kpiScore-reloadList" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
		   					</td>
		   				</tr>
		   			</table>
		   		</form>
	  		</div>
	  		<table id="performance-table-kpiScore"></table>
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
		
		
	     
		$(function(){


			$("#performance-button-kpiScore").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/report/performance/showPerformanceKPIScoreAddPageBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							kpiScoreAddDialog('业绩考核数据【新增】', 'report/performance/showPerformanceKPIScoreAddPage.do');							
						}
					},
					</security:authorize>
					<security:authorize url="/report/performance/showPerformanceKPIScoreEditPageBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var dataRow=$("#performance-table-kpiScore").datagrid('getSelected');
							if(dataRow==null){
								$.messager.alert("提醒","请选择相应的业绩考核数据!");
								return false;
							}
							kpiScoreEditDialog("业绩考核数据【更新】", 'report/performance/showPerformanceKPIScoreEditPage.do?id='+dataRow.id);
						}
					},
					</security:authorize>
					<security:authorize url="/report/performance/deletePerformanceKPIScoreBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var rows =  $("#performance-table-kpiScore").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的业绩考核数据!");
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
							$.messager.confirm("提醒","是否确认删除部门业绩考核数据?",function(r){
								if(r){
				  				loading();
				  				$.ajax({
							        type: 'post',
							        cache: false,
							        url: 'report/performance/deletePerformanceKPIScoreMore.do',
							        data: {idarrs:idarrs.join(",")},
									dataType:'json',
							        success:function(json){
							        	loaded();
							        	if(json.flag==true){
						  					$.messager.alert("提醒", "删除成功数："+ json.isuccess +"<br />删除失败数："+ json.ifailure );							  					
											$("#performance-table-kpiScore").datagrid('reload');
											$("#performance-table-kpiScore").datagrid('clearSelections');	
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
					<security:authorize url="/report/performance/auditPerformanceKPIScoreMore.do">
					{
						id:'button-id-audit',
						name:'审核 ',
						iconCls:'button-audit',
						handler: function(){
							var rows =  $("#performance-table-kpiScore").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的部门业绩考核数据!");
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
								$.messager.alert("提醒","只有未审核的考核指标汇总数据才能被审核。");
								return false;
							}
							$.messager.confirm("提醒","是否确认审核部门业绩考核数据?",function(r){
								if(r){
				  				loading();
				  				$.ajax({
							        type: 'post',
							        cache: false,
							        url: 'report/performance/auditPerformanceKPIScoreMore.do',
							        data: {idarrs:idarrs.join(",")},
									dataType:'json',
							        success:function(json){
							        	loaded();
							        	if(json.flag==true){
						  					$.messager.alert("提醒", "审核成功数："+ json.isuccess +"<br />审核失败数："+ json.ifailure );							  					
											$("#performance-table-kpiScore").datagrid('reload');
											$("#performance-table-kpiScore").datagrid('clearSelections');	
						  		        }
						  		        else{
						  		        	$.messager.alert("提醒","审核失败");
						  		        }
							        }
							    });
						    }
							});
						}
					},
					</security:authorize>
					<security:authorize url="/report/performance/oppauditPerformanceKPIScoreMore.do">
					{
						id:'button-id-oppaudit',
						name:'反审 ',
						iconCls:'button-oppaudit',
						handler: function(){
							var rows =  $("#performance-table-kpiScore").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的部门业绩考核数据!");
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
								$.messager.alert("提醒","只有审核通过的考核指标汇总数据才能被反审核。");
								return false;
							}
		
							$.messager.confirm("提醒","是否反审核部门业绩考核数据？",function(r){
								if(r){
									loading("审核中..");
									$.ajax({   
							            url :'report/performance/oppauditPerformanceKPIScoreMore.do',
								        data: {idarrs:idarrs.join(",")},
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
								            if(json.flag){
							  					$.messager.alert("提醒", "反审核成功数："+ json.isuccess +"<br />反审核失败数："+ json.ifailure );							  					
												$("#performance-table-kpiScore").datagrid('reload');
												$("#performance-table-kpiScore").datagrid('clearSelections');						            		
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
					<security:authorize url="/report/performance/performanceKPIScoreExportBtn.do">
					{
						id:'button-export-excel',
						name:'导出',
						iconCls:'button-export',
						handler: function(){
						
							var rows =  $("#performance-table-kpiScore").datagrid('getChecked');

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
								queryForm: "#performance-form-kpiScoreListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
						 		type:'exportUserdefined',
						 		name:'部门业绩考核数据',
						 		fieldParam:{idarrs:idarrs.join(",")},
						 		url:'report/performance/exportPerformanceKPIScorePageData.do'
							});
							$("#performance-kpisummary-buttons-exportclick").trigger("click");
						}
					},
					</security:authorize>
					{}		
				],
	 			model:'bill',
				type:'list',
				datagrid:'performance-table-kpiScore',
				tname:'t_report_performance_kpisummary',
				id:''
     		});
			
			
			var initQueryJSON = $("#performance-form-kpiScoreListQuery").serializeJSON()
     		$("#performance-table-kpiScore").datagrid({ 
				columns:[
				     		[
					     		{field:'businessdate',title:'业务日期',width:80,sortable:true,rowspan:2},
								{field:'deptid',title:'所属部门',width:100,sortable:true,rowspan:2,
									formatter:function(val,rowData,rowIndex){
										return rowData.deptname;
									}
								},
								{field:'status',title:'状态',width:80,sortable:true,align:'right',rowspan:2,
									formatter:function(value,rowData,rowIndex){
										if(value=='3'){
											return "审核";
										}else if(value==null || value==""){
											return "";
										}else if(value==-99){
											return "";
										}else{
											return "未审核";
										}
					        		}
					        	},
								{title:"销售额指标",isShow:true,colspan:5,align:'center'},
								{title:"毛利额指标",isShow:true,colspan:9,align:'center'},
								{title:"库存周转指标",isShow:true,colspan:5,align:'center'},
								{title:"费用率指标",isShow:true,colspan:5,align:'center'},
								{field:'totalscore',title:'合计得分',width:70,align:'right',rowspan:2,
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'bonus',title:'应得奖金',width:70,align:'right',rowspan:2,
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								}
							],
							[
								{field:'salesamountindex',title:'销售额指标',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'salesamount',title:'销售额',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'salesamountindexscore',title:'指标总分',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'salesamountindexvalue',title:'指标分值',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'salesamountscore',title:'得分',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'mlamountindex',title:'毛利额指标',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'mlamount',title:'毛利额',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'mlamountscore',title:'得分',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'mlrateindex',title:'毛利率指标',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
								  		if(null!=value){
											return formatterMoney(value)+'%';
								  		}
									}
								},
								{field:'mlrate',title:'毛利率',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
								  		if(null!=value){
											return formatterMoney(value)+'%';
								  		}
									}
								},
								{field:'mlratescore',title:'得分',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'mlindexscore',title:'毛利指标总分',width:75,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'mlindexvalue',title:'毛利指标分值',width:75,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'mlscore',title:'毛利得分',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'kczlrsindex',title:'日数指标',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'kczlrs',title:'实绩',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'kczlindexscore',title:'指标总分',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'kczlindexvalue',title:'指标分值',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'kczlrsscore',title:'得分',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'fyrateindex',title:'费用率指标',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value)+'%';
									}
								},
								{field:'fyrate',title:'实绩',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value)+'%';
									}
								},
								{field:'fyrateindexscore',title:'指标总分',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'fyrateindexvalue',title:'指标分值',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'fyratescore',title:'得分',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								}
							]
						],
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'部门业绩考核数据',
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
				toolbar:'#performance-table-kpiScoreBtn',
				pageList:[20,50,200,300,500],
				url: 'report/performance/showPerformanceKPIScorePageData.do',
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
					}
		 		},
			    onSelect:function(rowIndex, rowData){
			    	if(rowData.status=="3"){
			    		$("#performance-button-kpiScore").buttonWidget("disableButton", 'button-id-edit');
			    		$("#performance-button-kpiScore").buttonWidget("disableButton", 'button-id-delete');
			    		$("#performance-button-kpiScore").buttonWidget("disableButton", 'button-id-audit');
			    		$("#performance-button-kpiScore").buttonWidget("enableButton", 'button-id-oppaudit');
			    	}else{
			    		$("#performance-button-kpiScore").buttonWidget("enableButton", 'button-id-edit');
			    		$("#performance-button-kpiScore").buttonWidget("enableButton", 'button-id-delete');
			    		$("#performance-button-kpiScore").buttonWidget("enableButton", 'button-id-audit');
			    		$("#performance-button-kpiScore").buttonWidget("disableButton", 'button-id-oppaudit');
				    	
			    	}
			    },
		    	onDblClickRow:function(rowIndex, rowData){
			    	if(rowData.status=="3" || rowData.status=="4"){
			    		kpiScoreViewDialog("业绩考核数据【查看】", 'report/performance/showPerformanceKPIScoreViewPage.do?id='+rowData.id);
			    	}else{
		 			<%if(isEdit){%>
		 				kpiScoreEditDialog("业绩考核数据【更新】", 'report/performance/showPerformanceKPIScoreEditPage.do?id='+rowData.id);
		 			<% }else { %>
	 					kpiScoreViewDialog("业绩考核数据【查看】", 'report/performance/showPerformanceKPIScoreViewPage.do?id='+rowData.id);
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

			$("#performance-widget-kpiScore-deptid").widget({
		  		width:150,
				name:'t_report_performance_kpiscore',
				col:'deptid',
				singleSelect:true,
				onlyLeafCheck:false
			});

     		//查询
			$("#performance-query-kpiScoreList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#performance-form-kpiScoreListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#performance-table-kpiScore").datagrid('load',queryJSON);	
			});
			
			//重置按钮
			$("#performance-query-kpiScore-reloadList").click(function(){
				$("#performance-form-kpiScoreListQuery")[0].reset();
				$("#performance-widget-kpiScore-deptid").widget('clear');

	      		var queryJSON = $("#performance-form-kpiScoreListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#performance-table-kpiScore").datagrid('load',queryJSON);
			});
			
		});
		
		function selectCountTotalAmount(){
    		var rows =  $("#performance-table-kpiScore").datagrid('getChecked');
    		if(null==rows || rows.length==0){
           		var foot=[];
    			if(null!=footerobject){
	        		foot.push(emptyChooseObjectFoot());
	        		foot.push(footerobject);
	    		}
    			$("#performance-table-kpiScore").datagrid("reloadFooter",foot);
           		return false;
       		}
    		
    		var salesamountindex=0;
    		var salesamount=0;
    		var mlamountindex=0;
    		var mlamount=0;
    		var bonus=0;
    		
    		for(var i=0;i<rows.length;i++){
        		salesamountindex = Number(salesamountindex)+Number(rows[i].salesamountindex == undefined ? 0 : rows[i].salesamountindex);
	    		salesamount = Number(salesamount)+Number(rows[i].salesamount == undefined ? 0 : rows[i].salesamount);
	    		mlamountindex = Number(mlamountindex)+Number(rows[i].mlamountindex == undefined ? 0 : rows[i].mlamountindex);
	    		mlamount = Number(mlamount)+Number(rows[i].mlamount == undefined ? 0 : rows[i].mlamount);
	    		bonus = Number(bonus)+Number(rows[i].bonus == undefined ? 0 : rows[i].bonus); 				
    		}
    		var foot=[{deptname:'选中金额',
    					status:-99,
		    			salesamountindex : salesamountindex,
		    			salesamount : salesamount,
		    			mlamountindex : mlamountindex,
		    			mlamount : mlamount,
		    			bonus : bonus
        			}];
    		if(null!=footerobject){
        		foot.push(footerobject);
    		}
    		$("#performance-table-kpiScore").datagrid("reloadFooter",foot);
    	}
    	function emptyChooseObjectFoot(){     		
    		var foot={deptname:'选中金额',
					status:-99,
    				salesamountindex : 0,
    				salesamount : 0,
    				mlamountindex : 0,
    				mlamount : 0,
    				bonus : 0
			};
			return foot;
    	}
    	function kpiScoreAddDialog(title, url){
	   		$('#performance-dialog-add-operate').dialog({  
			    title: title,  
			    width: 480,  
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
	    function kpiScoreEditDialog(title, url){
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
			    	$("#performanceKPIScore-form-salesamountindex").focus();
			    	$("#performanceKPIScore-form-salesamountindex").select();
	   			}
			});
			$('#performance-dialog-edit-operate').dialog('open');
	   	}
	    function kpiScoreViewDialog(title, url){
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

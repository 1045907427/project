<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门业绩考核报表页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  <body>
  	<div id="performance-table-kpiScoreReportBtn">
		<form action="" id="performance-form-kpiScoreReportListQuery" method="post" style="padding-left: 5px; padding-top: 2px;">
 			<table cellpadding="0" cellspacing="1" border="0">
 				<tr>
 					<td>年份:</td>
    				<td><input id="performance-kpiScoreReport-query-year" type="text" name="year" style="width:110px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy',maxDate:'${currentyear }'})" value="${currentyear }"/> </td>	   				    					
 					<td style="padding-left: 10px;">品牌部门:&nbsp;</td>
 					<td><input id="performance-widget-kpiScoreReport-deptid" name="deptid" type="text" style="width: 150px;"/></td>
    				<td>
 						<a href="javaScript:void(0);" id="performance-query-kpiScoreReportList" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
	    				<a href="javaScript:void(0);" id="performance-query-kpiScoreReport-reloadList" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
	    				<security:authorize url="/report/performance/exportPerformanceKPIScoreReportDataBtn.do">
							<a href="javaScript:void(0);" id="performance-kpiScoreReportList-export-btn" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
							<a href="javaScript:void(0);" id="performance-kpiScoreReportList-export-excel" style="display:none">导出</a>
						</security:authorize>
 					</td>
 				</tr>
 			</table>
 		</form>
	</div>
	<table id="performance-table-kpiScoreReport"></table>
	<div style="display:none">
		<div id="performance-dialog-add-operate"></div>
		<div id="performance-dialog-edit-operate"></div>
		<div id="performance-dialog-view-operate"></div>
		<a href="javaScript:void(0);" id="performance-kpisummary-buttons-exportclick" style="display: none"title="导出">导出</a>
	</div>
   	 
    <script type="text/javascript">
		var footerobject=null;
	     
		$(function(){
		
			var initQueryJSON = $("#performance-form-kpiScoreReportListQuery").serializeJSON()
     		$("#performance-table-kpiScoreReport").datagrid({  
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
								return formatterMoney(value);
							}
						},
						{field:'fyrate',title:'实绩',width:70,align:'right',
						  	formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
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
	  	 		title:'部门业绩考核报表',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'businessdate',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		queryParams:initQueryJSON,
				pageSize:20,
				toolbar:'#performance-table-kpiScoreReportBtn',
				pageList:[20,50,200,300,500],
				//url: 'report/performance/showPerformanceKPIScoreReportData.do',
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
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

			$("#performance-widget-kpiScoreReport-deptid").widget({
		  		width:150,
				name:'t_report_performance_kpiscore',
				col:'deptid',
				singleSelect:true,
				onlyLeafCheck:false
			});

     		//查询
			$("#performance-query-kpiScoreReportList").click(function(){
	      		var queryJSON = $("#performance-form-kpiScoreReportListQuery").serializeJSON();
	      		$("#performance-table-kpiScoreReport").datagrid({
	      			url: 'report/performance/showPerformanceKPIScoreReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			
			//重置按钮
			$("#performance-query-kpiScoreReport-reloadList").click(function(){
				$("#performance-form-kpiScoreReportListQuery")[0].reset();
				$("#performance-widget-kpiScoreReport-deptid").widget('clear');
				$("#performance-kpiScoreReport-query-groupcols").combobox('clear');
	      		$("#performance-table-kpiScoreReport").datagrid('loadData',{total:0,rows:[]});
			});

			$("#performance-kpiScoreReport-query-groupcols").combobox({
				multiple:true,
				editable:false
			});

			$("#performance-kpiScoreReportList-export-btn").click(function(){
				var year=$("#performance-kpiScoreReport-query-year").val()||"";
				var deptname=$("#performance-widget-kpiScoreReport-deptid").widget("getText")|| "" ;
				var title="部门业绩考核报表";
				if($.trim(year)!=""){
					year=year+"年 ";
				}
				title=year+$.trim(deptname)+title;
				$("#performance-kpiScoreReportList-export-excel").Excel('export',{
					queryForm: "#performance-form-kpiScoreReportListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'report/performance/exportPerformanceKPIScoreReportData.do'
				});
				$("#performance-kpiScoreReportList-export-excel").trigger("click");
			});
		});
		
		function selectCountTotalAmount(){
			var rows =  $("#performance-table-kpiScoreReport").datagrid('getChecked');
    		if(null==rows || rows.length==0){
           		var foot=[];
    			if(null!=footerobject){
	        		foot.push(emptyChooseObjectFoot());
	        		foot.push(footerobject);
	    		}
    			$("#performance-table-kpiScoreReport").datagrid("reloadFooter",foot);
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
    		$("#performance-table-kpiScoreReport").datagrid("reloadFooter",foot);
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
    	function emptyChooseObjectFoot(){     		
    		var foot={deptname:'选中金额',
					status:-99,
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
    </script>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌业务员考核报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    <table id="report-datagrid-branduserAssess"></table>
   	<div id="report-toolbar-branduserAssess">
   		<form action="" id="report-query-form-branduserAssess" method="post">
	   		<table>
	   			<tr>
	   				<td>日期:</td>
	   				<td><input id="businessdate" name="businessdate" class="Wdate" style="width:100px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" value="${today }"/></td>
	   				<td>品牌业务员:</td>
	   				<td><input type="text" id="report-query-branduser" name="branduser"/></td>
	   				<td colspan="2">
	   					<a href="javaScript:void(0);" id="report-query-branduserAssess" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
						<a href="javaScript:void(0);" id="report-reload-branduserAssess" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
	   					<security:authorize url="/report/finance/branduserAssessReportExtendExport.do">
							<a href="javaScript:void(0);" id="report-buttons-branduserAssessPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
						</security:authorize>
						<security:authorize url="/report/finance/branduserAssessReportExtendPassnum.do">
							<a href="javaScript:void(0);" id="report-passnum-branduserAssess" class="easyui-linkbutton" iconCls="icon-add" plain="true" title="生成超账金额">生成超账金额</a>
	   					</security:authorize>
	   				</td>
	   			</tr>
	   		</table>
	   	</form>
   	</div>
   	<div id="report-dialog-branduserAssessInfo"></div>
   	<div id="report-dialog-passnum">
		<table>
			<tr>
				<td>超账次数:</td>
				<td><select id="report-select-passnum">
					<option></option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
				</select></td>
			</tr>
		</table>
	</div>
   	<script type="text/javascript">
		var SR_footerobject  = null;
		
		var report_chooseNo;
    	function frm_focus(val){
    		report_chooseNo = val;
    	}
    	function frm_blur(val){
    		if(val == report_chooseNo){
    			report_chooseNo = "";
    		}
    	}
		
		var branduserAssessExtend_AjaxConn = function (Data, Action, Str) {
		   var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false
		    })
		    return MyAjax.responseText;
		}
		
   		var initQueryJSON = $("#report-query-form-branduserAssess").serializeJSON();
   		$(function(){
   			var tableColumnListJson = $("#report-datagrid-branduserAssess").createGridColumnLoad({
				frozenCol : [[
					{field:'idok',checkbox:true,isShow:true}
				]],
				commonCol : [[
					  {field:'salesarea',title:'地区',width:60,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.salesareaname;
			        	}
					  },
					  {field:'branduser',title:'销售主管',width:80,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.brandusername;
			        	}
					  },
					  {field:'wdtargetamount',title:'回笼指标',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'otherwdamount',title:'其他回笼',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'erpwdamount',title:'ERP回笼',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'realwdamount',title:'实际回笼',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'totalscore',title:'得分',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'returnamount',title:'退货金额',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'returnsubamount',title:'退货扣减',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  <security:authorize url="/report/finance/branduserAssessReportExtendPassnum.do">
					  {field:'totalpassamount1',title:'超账金额1',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'totalpasssubamount1',title:'超账扣减1',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'totalpassamount2',title:'超账金额2',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'totalpasssubamount2',title:'超账扣减2',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'totalpassamount3',title:'超账金额3',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'totalpasssubamount3',title:'超账扣减3',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'totalpassamount4',title:'超账金额4',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'totalpasssubamount4',title:'超账扣减4',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  </security:authorize>
					  {field:'bonus',title:'奖金',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'kpitargetamount',title:'kpi目标',resizable:true,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'kpibonusamount',title:'kpi奖金',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'bonusamount',title:'奖金总额',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'wardenamount',title:'区长',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'amountsum',title:'合计',resizable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  }
		         ]]
			});
   			$("#report-datagrid-branduserAssess").datagrid({ 
		 		authority:tableColumnListJson,
		 		frozenColumns: tableColumnListJson.frozen,
				columns:tableColumnListJson.common,
		 		method:'post',
	  	 		title:'',
	  	 		fit:true,
	  	 		rownumbers:true,
	  	 		//pagination:true,
	  	 		showFooter: true,
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
	  	 		//pageSize:1000,
				toolbar:'#report-toolbar-branduserAssess',
				onDblClickRow:function(rowIndex, rowData){
					<security:authorize url="/report/finance/branduserAssessExtendReportDblClick.do">
					if(rowData.branduser == "999999"){
						return false;
					}
					var businessdate = $("#businessdate").val();
   					var url = 'report/finance/showBranduserAssessExtendPage.do?branduser='+rowData.branduser+'&rowindex='+rowIndex+'&businessdate='+businessdate+'&salesarea='+rowData.salesarea;
					$("#report-dialog-branduserAssessInfo").dialog({
						title:'编辑品牌业务员分月考核信息',
			    		width:500,
			    		height:240,
			    		closed:true,
			    		modal:true,
			    		cache:false,
			    		resizable:true,
					    href: url,
					    onLoad:function(data){
					    	$("#report-numberbox-wdtargetamount").focus();
					    }
					});
					$("#report-dialog-branduserAssessInfo").dialog("open");
					</security:authorize>
					$(this).datagrid('clearSelections');
					$(this).datagrid('selectRow',rowIndex);
				},
				rowStyler: function(rowIndex, rowData){
					if(rowData.branduser == "999999"){
						return 'background-color:#E9E9E9;';
					}else if(rowData.brandusername == "合计"){
						return 'background-color:#F7FCD4;';
					}
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
			$("#report-query-branduser").widget({
				referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
	    		width:130,
				singleSelect:false
			});
			//回车事件
			controlQueryAndResetByKey("report-query-branduserAssess","report-reload-branduserAssess");
			
			//查询
			$("#report-query-branduserAssess").click(function(){
	      		var queryJSON = $("#report-query-form-branduserAssess").serializeJSON();
	      		$("#report-datagrid-branduserAssess").datagrid({
	      			url: 'report/finance/getBranduserAssessReportExtendData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		});
			});
			//重置
			$("#report-reload-branduserAssess").click(function(){
				$("#report-query-branduser").widget("clear");
				$("#report-query-form-branduserAssess")[0].reset();
				var queryJSON = $("#report-query-form-branduserAssess").serializeJSON();
	       		$("#report-datagrid-branduserAssess").datagrid('loadData',{total:0,rows:[]});
			});
			
			//生成超账金额
			$("#report-passnum-branduserAssess").click(function(){
				$("#report-dialog-passnum").dialog({
					title:'生成超账金额',
					top:30,
				    width: 250,
				    height: 120,
				    closed: false,
				    cache: false,
				    modal: true,
		    		buttons:[
						{
							text:'确定',
							handler:function(){
								var passnum = $("#report-select-passnum").val();
								var businessdate = $("#businessdate").val();
								var queryJSON = $("#report-query-form-branduserAssess").serializeJSON();
								queryJSON['passnum'] = passnum;
								loading("生成中...");
								$.ajax({   
						            url :'report/finance/getTotalPassAmount.do',
						            type:'post',
						            dataType:'json',
						            data:queryJSON,
						            success:function(retJSON){
						            	loaded();
							            if(retJSON.flag){
				   							$("#report-datagrid-branduserAssess").datagrid({
								      			url: 'report/finance/getBranduserAssessReportExtendData.do',
								      			pageNumber:1,
												queryParams:queryJSON
								      		});
								      		$("#report-dialog-passnum").dialog('close');
				   						}
						            }
						        });
							}
						}
					]
				});
			});
			
			$("#report-buttons-branduserAssessPage").Excel('export',{
				queryForm: "#report-query-form-branduserAssess", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
		 		type:'exportUserdefined',
		 		name:'品牌业务员考核统计扩展报表',
		 		url:'report/finance/exportBranduserAssessExtendReportData.do'
			});
			
			$(document).keydown(function(event){
				switch(event.keyCode){
					case 13://Enter
						if(report_chooseNo == "wdtargetamount"){
							$("#report-numberbox-otherwdamount").focus();
	 						return false;
						}
						if(report_chooseNo == "otherwdamount"){
							$("#report-numberbox-kpitargetamount").focus();
	 						return false;
						}
						if(report_chooseNo == "kpitargetamount"){
							$("#report-numberbox-kpibonusamount").focus();
	 						return false;
						}
						if(report_chooseNo == "kpibonusamount"){
							$("#report-numberbox-wardenamount").focus();
	 						return false;
						}
						if(report_chooseNo == "wardenamount"){
							$("#report-textarea-remark").focus();
	 						return false;
						}
						if(report_chooseNo == "remark"){
							$("#branduserAssessExtend-saveAgain-saveMenu").click();
						}
					break;
					case 27://Esc
						$("#report-dialog-branduserAssessInfo").dialog('close');
					break;
				}
			});
   		});
   		function countTotalAmount(){
   			var rows =  $("#report-datagrid-branduserAssess").datagrid('getChecked');
       		var otherwdamount = 0;
       		var wdtargetamount = 0;
       		var kpitargetamount = 0;
       		var kpibonusamount = 0;
       		var wardenamount = 0;
       		var totalpassamount1 = 0;
       		var totalpassamount2 = 0;
       		var totalpassamount3 = 0;
       		var totalpassamount4 = 0;
       		var totalpasssubamount1 = 0;
       		var totalpasssubamount2 = 0;
       		var totalpasssubamount3 = 0;
       		var totalpasssubamount4 = 0;
       		var erpwdamount = 0;
       		var realwdamount = 0;
       		var totalscore = 0;
       		var returnamount = 0;
       		var returnsubamount = 0;
       		var bonus = 0;
       		var bonusamount = 0;
       		var amountsum = 0;
       		for(var i=0;i<rows.length;i++){
       			if(rows[i].branduser == "999999"){
       				continue;
       			}
       			otherwdamount = Number(otherwdamount)+Number(rows[i].otherwdamount == undefined ? 0 : rows[i].otherwdamount);;
       			wdtargetamount = Number(wdtargetamount)+Number(rows[i].wdtargetamount == undefined ? 0 : rows[i].wdtargetamount);;
       			kpitargetamount = Number(kpitargetamount)+Number(rows[i].kpitargetamount == undefined ? 0 : rows[i].kpitargetamount);
	       		kpibonusamount = Number(kpibonusamount)+Number(rows[i].kpibonusamount == undefined ? 0 : rows[i].kpibonusamount);
	       		wardenamount = Number(wardenamount)+Number(rows[i].wardenamount == undefined ? 0 : rows[i].wardenamount);
	       		totalpassamount1 = Number(totalpassamount1)+Number(rows[i].totalpassamount1 == undefined ? 0 : rows[i].totalpassamount1);
	       		totalpassamount2 = Number(totalpassamount2)+Number(rows[i].totalpassamount2 == undefined ? 0 : rows[i].totalpassamount2);
	       		totalpassamount3 = Number(totalpassamount3)+Number(rows[i].totalpassamount3 == undefined ? 0 : rows[i].totalpassamount3);
	       		totalpassamount4 = Number(totalpassamount4)+Number(rows[i].totalpassamount4 == undefined ? 0 : rows[i].totalpassamount4);
	       		totalpasssubamount1 = Number(totalpasssubamount1)+Number(rows[i].totalpasssubamount1 == undefined ? 0 : rows[i].totalpasssubamount1);
	       		totalpasssubamount2 = Number(totalpasssubamount2)+Number(rows[i].totalpasssubamount2 == undefined ? 0 : rows[i].totalpasssubamount2);
	       		totalpasssubamount3 = Number(totalpasssubamount3)+Number(rows[i].totalpasssubamount3 == undefined ? 0 : rows[i].totalpasssubamount3);
	       		totalpasssubamount4 = Number(totalpasssubamount4)+Number(rows[i].totalpasssubamount4 == undefined ? 0 : rows[i].totalpasssubamount4);
	       		erpwdamount = Number(erpwdamount)+Number(rows[i].erpwdamount == undefined ? 0 : rows[i].erpwdamount);
	       		realwdamount = Number(realwdamount)+Number(rows[i].realwdamount == undefined ? 0 : rows[i].realwdamount);
	       		if(Number(wdtargetamount) != 0){
	       			totalscore = Number(realwdamount)/Number(wdtargetamount)*Number(100);
	       		}
	       		returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
	       		returnsubamount = Number(returnsubamount)+Number(rows[i].returnsubamount == undefined ? 0 : rows[i].returnsubamount);
	       		bonus = Number(totalscore)*(Number("${bonusnum}"))-(Number(returnsubamount))-(Number(totalpasssubamount1))-(Number(totalpasssubamount2))-(Number(totalpasssubamount3))-(Number(totalpasssubamount4));
	       		bonusamount = Number(bonusamount)+Number(rows[i].bonusamount == undefined ? 0 : rows[i].bonusamount);
	       		bonusamount = Number(bonus)+Number(kpibonusamount);
	       		amountsum = Number(bonusamount)+Number(wardenamount);
       		}
       		var foot=[{brandusername:'选中合计',
       			otherwdamount:otherwdamount,
       			wdtargetamount:wdtargetamount,
       			kpitargetamount:kpitargetamount,
       			kpibonusamount:kpibonusamount,
       			wardenamount:wardenamount,
       			totalpassamount1:totalpassamount1,
       			totalpassamount2:totalpassamount2,
       			totalpassamount3:totalpassamount3,
       			totalpassamount4:totalpassamount4,
       			totalpasssubamount1:totalpasssubamount1,
       			totalpasssubamount2:totalpasssubamount2,
       			totalpasssubamount3:totalpasssubamount3,
       			totalpasssubamount4:totalpasssubamount4,
       			erpwdamount:erpwdamount,
       			realwdamount:realwdamount,
       			totalscore:totalscore,
       			returnamount:returnamount,
       			returnsubamount:returnsubamount,
       			bonus:bonus,
       			bonusamount:bonusamount,
       			amountsum:amountsum
       			}];
       		if(null!=SR_footerobject){
           		foot.push(SR_footerobject);
       		}
       		$("#report-datagrid-branduserAssess").datagrid("reloadFooter",foot);
   		}
   	</script>
  </body>
</html>

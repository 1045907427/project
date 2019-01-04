<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌业务员回笼资金超账期统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-withdrawalPastDue"></table>
    	<div id="report-toolbar-withdrawalPastDue" style="padding: 0px">
            <div class="buttonBG">
                <a href="javaScript:void(0);" id="report-buttons-withdrawalPastDuePage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                <a href="javaScript:void(0);" id="report-setdays-withdrawalPastDue" class="easyui-linkbutton" iconCls="button-intervalSet" plain="true">设置区间</a>
            </div>
    		<form action="" id="report-query-form-withdrawalPastDue" method="post">
    		<table>
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>品牌部门</td>
    				<td><input type="text" id="report-query-branddept" name="branddept"/></td>
    			</tr>
    			<tr>
    				<td>品牌业务员</td>
    				<td><input type="text" id="report-query-branduser" name="branduser"/></td>
    				<td colspan="2">
    					<input id="report-query-groupcols" type="hidden" name="groupcols" value="branduser"/>
    					<a href="javaScript:void(0);" id="report-queay-withdrawalPastDue" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-withdrawalPastDue" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-paymentdaysSet-dialog"></div>
    	<div id="report-receivablePastDue-detail-dialog"></div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-withdrawalPastDue").serializeJSON();
    		$(function(){
    			$("#report-datagrid-withdrawalPastDue").datagrid({ 
					columns:[[
							  	{field:'idok',checkbox:true,isShow:true},
								  {field:'branduser',title:'品牌业务员',width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandusername;
						        	}
								  },
								  {field:'branddept',title:'品牌部门',width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.branddeptname;
						        	}
								  },
								  {field:'taxamount',title:'回笼金额',align:'right',resizable:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  <c:if test="${map.costamount == 'costamount'}">
									  {field:'costamount',title:'回笼成本',align:'right',resizable:true,sortable:true,
									  	formatter:function(value,rowData,rowIndex){
							        		return formatterMoney(value);
							        	}
									  },
								  </c:if>
								  <c:if test="${map.marginamount == 'marginamount'}">
									  {field:'marginamount',title:'回笼毛利额',align:'right',resizable:true,sortable:true,
									  	formatter:function(value,rowData,rowIndex){
							        		return formatterMoney(value);
							        	}
									  },
								  </c:if>
								  <c:if test="${map.marginrate == 'marginrate'}">
									  {field:'marginrate',title:'回笼毛利率',align:'right',width:80,
									  	formatter:function(value,rowData,rowIndex){
									  		if(value!="" && null != value){
							        			return formatterMoney(value)+"%";
							        		}
							        	}
									  },
								  </c:if>
								  {field:'unpassamount',title:'正常期金额',align:'right',resizable:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'totalpassamount',title:'超账期金额',align:'right',resizable:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
					        			return formatterMoney(value);
						        	}
								  },
								  <c:forEach items="${list }" var="list">
								  {field:'passamount${list.seq}',title:'${list.detail}',align:'right',resizable:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
					        			return formatterMoney(value);
						        	}
								  },
	    						  </c:forEach>
	    						  {field:'returnamount',title:'退货金额',align:'right',resizable:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'returnrate',title:'退货率',align:'right',width:80,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(value!=null && value!=""){
						        			return formatterMoney(value)+"%";
						        		}
						        	}
								  },
								  {field:'pushamount',title:'冲差金额',align:'right',resizable:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
					         ]],
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-withdrawalPastDue',
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
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
				});
				$("#report-query-branduser").widget({
					referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		    		width:225,
					singleSelect:false
				});
				$("#report-query-branddept").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:130,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-withdrawalPastDue","report-reload-withdrawalPastDue");
				
				//查询
				$("#report-queay-withdrawalPastDue").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-withdrawalPastDue").serializeJSON();
		      		$("#report-datagrid-withdrawalPastDue").datagrid({
		      			url: 'report/finance/showBaseWithdrawnPastdueListData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-withdrawalPastDue").click(function(){
					$("#report-query-branduser").widget("clear");
					$("#report-query-form-withdrawalPastDue")[0].reset();
					var queryJSON = $("#report-query-form-withdrawalPastDue").serializeJSON();
		       		$("#report-datagrid-withdrawalPastDue").datagrid('loadData',{total:0,rows:[],footer:[]});
				});
				
				$("#report-buttons-withdrawalPastDuePage").Excel('export',{
					queryForm: "#report-query-form-withdrawalPastDue", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'客户回笼资金超账期统计报表',
			 		url:'report/finance/exportBaseWithdrawnPastDueData.do'
				});
				
				//设置超账期区间
				$("#report-setdays-withdrawalPastDue").click(function(){
					$("#report-paymentdaysSet-dialog").dialog({
					    title: '超账期区间设置',  
					    width: 400,  
					    height: 400,  
					    closed: false,  
					    cache: false,  
					    modal: true,
					    href: 'report/paymentdays/showPaymetdaysSetPage.do'
					});
				});
    		});
    		function showDetail(customerid,pastseq,iswrite){
    			if(pastseq==null){
    				pastseq = "";
    			}
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showCustomerPastDueListPage.do?customerid='+customerid+'&seq='+pastseq+"&iswrite="+iswrite+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-receivablePastDue-detail-dialog").dialog({
				    title: '超账期销售发货回单列表',  
		    		width:800,
		    		height:400,
		    		closed:false,
		    		modal:true,
		    		maximizable:true,
		    		cache:false,
		    		resizable:true,
				    href: url
				});
    		}
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-withdrawalPastDue").datagrid('getChecked');
        		if(null==rows || rows.length==0){
            		var foot=[];
	    			if(null!=SR_footerobject){
		        		foot.push(SR_footerobject);
		    		}
	    			$("#report-datagrid-withdrawalPastDue").datagrid("reloadFooter",foot);
            		return false;
        		}
    			var taxamount = 0;
        		var costamount = 0;
        		var marginamount=0;
        		var unpassamount =0;
        		var totalpassamount = 0;
        		<c:forEach items="${list }" var="list">
				  var passamount${list.seq} = 0;
				</c:forEach>
        		var returnamount = 0;
        		var pushamount = 0;
        		for(var i=0;i<rows.length;i++){
        			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
        			costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
        			marginamount = Number(marginamount)+Number(rows[i].marginamount == undefined ? 0 : rows[i].marginamount);
        			unpassamount = Number(unpassamount)+Number(rows[i].unpassamount == undefined ? 0 : rows[i].unpassamount);
        			totalpassamount = Number(totalpassamount)+Number(rows[i].totalpassamount == undefined ? 0 : rows[i].totalpassamount);
        			<c:forEach items="${list }" var="list">
            			passamount${list.seq} = Number(passamount${list.seq})+Number(rows[i].passamount${list.seq} == undefined ? 0 : rows[i].passamount${list.seq});
    				</c:forEach>
        			returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
        			pushamount = Number(pushamount)+Number(rows[i].pushamount == undefined ? 0 : rows[i].pushamount);
        		}
        		var foot=[{brandusername:'选中合计',taxamount:taxamount,costamount:costamount,marginamount:marginamount,
        				unpassamount:unpassamount,totalpassamount:totalpassamount
        				<c:forEach items="${list }" var="list">
	    					,passamount${list.seq}:passamount${list.seq}
	    				</c:forEach>
        				,returnamount:returnamount,pushamount:pushamount}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-withdrawalPastDue").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>

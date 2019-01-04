<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>回笼基础超账期统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
      <style>
          .checkbox1{
              float:left;
              height: 22px;
              line-height: 22px;
          }
          .divtext{
              height:22px;
              line-height:22px;
              float:left;
              display: block;
          }
      </style>
  </head>
  
  <body>
    	<table id="report-datagrid-baseWithdrawnPastDue"></table>
    	<div id="report-toolbar-baseWithdrawnPastDue" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/baseWithdrawnPastDueExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-baseWithdrawnPastDuePage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
                <a href="javaScript:void(0);" id="report-setdays-baseWithdrawnPastDue" class="easyui-linkbutton" iconCls="button-intervalSet" plain="true">设置区间</a>

            </div>
    		<form action="" id="report-query-form-baseWithdrawnPastDue" method="post">
    		<table class="querytable">

    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>客户业务员:</td>
		    		<td><input type="text" id="report-query-salesuser" name="salesuser" /></td>
		    		<td>品牌业务员:</td>
		    		<td><input type="text" id="report-query-branduser" name="branduser" /></td>
    			<tr>
                <tr>
                    <td>客户名称:</td>
    				<td><input type="text" id="report-query-customerid" name="customerid"/></td>
                    <td>总店名称:</td>
                    <td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
                    <td>销售区域:</td>
		    		<td><input type="text" id="report-query-salesarea" name="salesarea" /></td>
          	  	</tr>
          	  	<tr>
          	  		 <td>销售部门:</td>
                     <td><input type="text" id="report-query-salesdept" name="salesdept" /></td>
                     <td>品牌:</td>
		    		<td><input type="text" id="report-query-brand" name="brandid" /></td>
		    		<td>品牌部门:</td>
    				<td><input type="text" id="report-query-branddept" name="branddept" /></td>
          	  	</tr>
    			<tr>
    				<td>小计列：</td>
    				<td colspan="4">
                        <input type="checkbox" class="groupcols checkbox1" value="customerid" id="report-customerid-advancedQuery"/>
                        <label for="report-customerid-advancedQuery" class="divtext">客户</label>
                        <input type="checkbox" class="groupcols checkbox1" value="pcustomerid" id="report-pcustomerid-advancedQuery"/>
                        <label for="report-pcustomerid-advancedQuery" class="divtext">总店</label>
                        <input type="checkbox" class="groupcols checkbox1" value="salesuser" id="report-salesuser-advancedQuery"/>
                        <label for="report-salesuser-advancedQuery" class="divtext">客户业务员</label>
                        <input type="checkbox" class="groupcols checkbox1" value="salesarea" id="report-salesarea1-advancedQuery"/>
                        <label for="report-salesarea1-advancedQuery" class="divtext">销售区域</label>
                        <input type="checkbox" class="groupcols checkbox1" value="salesdept" id="report-salesdept-advancedQuery"/>
                        <label for="report-salesdept-advancedQuery" class="divtext">销售部门</label>
                        <input type="checkbox" class="groupcols checkbox1" value="goodsid" id="report-goodsid1-advancedQuery"/>
                        <label for="report-goodsid1-advancedQuery" class="divtext"> 商品</label>
                        <input type="checkbox" class="groupcols checkbox1" value="branddept"  id="report-branddept1-advancedQuery"/>
                        <label for="report-branddept1-advancedQuery" class="divtext">品牌部门</label>
                        <input type="checkbox" class="groupcols checkbox1" value="branduser"  id="report-branduser1-advancedQuery" />
                        <label for="report-branduser1-advancedQuery" class="divtext">品牌业务员</label>
                        <input type="checkbox" class="groupcols checkbox1" value="brandid" id="report-brandid1-advancedQuery"/>
                        <label for="report-brandid1-advancedQuery" class="divtext">品牌</label>
    					<input id="report-query-groupcols" type="hidden" name="groupcols"/>
                    <td style="padding-left: 5px">
                    <a href="javaScript:void(0);" id="report-queay-baseWithdrawnPastDue" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-baseWithdrawnPastDue" class="button-qr">重置</a>
                    </td>
    			</tr>
                <tr>
                    <td colspan="4"></td>

                </tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-paymentdaysSet-dialog"></div>
    	<div id="report-baseWithdrawnPastDue-detail-dialog"></div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-baseWithdrawnPastDue").serializeJSON();
    		$(function(){
    			$(".groupcols").click(function(){
    				var cols = "";
    				$(".groupcols").each(function(){
    					if($(this).attr("checked")){
    						if(cols==""){
    							cols = $(this).val();
    						}else{
    							cols += ","+$(this).val();
    						}
    						$("#report-query-groupcols").val(cols);
    					}
					});
    			});
    			$("#report-datagrid-baseWithdrawnPastDue").datagrid({ 
					columns:[[
							 	 {field:'idok',checkbox:true,isShow:true},
								  {field:'customerid',title:'客户编号',sortable:true,width:60},
								  {field:'customername',title:'客户名称',width:130},
								  {field:'pcustomerid',title:'总店名称',sortable:true,width:130,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.pcustomername;
						        	}
								  },
								  {field:'salesuser',title:'客户业务员',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesusername;
						        	}
								  },
								  {field:'salesarea',title:'销售区域',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesareaname;
						        	}
								  },
								  {field:'salesdept',title:'销售部门',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesdeptname;
						        	}
								  },
								  {field:'branddept',title:'品牌部门',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.branddeptname;
						        	}
								  },
								  {field:'branduser',title:'品牌业务员',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandusername;
						        	}
								  },
								  {field:'goodsid',title:'商品编码',sortable:true,width:60},
								  {field:'goodsname',title:'商品名称',sortable:true,width:130},
								  {field:'barcode',title:'条形码',sortable:true,width:90},
								  {field:'brandid',title:'品牌名称',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
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
					toolbar:'#report-toolbar-baseWithdrawnPastDue',
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
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
		    		width:225,
					singleSelect:false
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:210,
					singleSelect:false
				});
				//客户业务员
				$("#report-query-salesuser").widget({
					referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
		    		width:210,
					singleSelect:false
				});
				//品牌业务员
				$("#report-query-branduser").widget({
					referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		    		width:210,
					singleSelect:false
				});
				//销售区域
				$("#report-query-salesarea").widget({
					referwid:'RT_T_BASE_SALES_AREA',
		    		width:210,
		    		onlyLeafCheck:false,
					singleSelect:false
				});
				 //销售部门
                $("#report-query-salesdept").widget({
	    			name:'t_sales_order',
					col:'salesdept',
	    			width:225,
					singleSelect:false,
					onlyLeafCheck:true
	    		});
             	 //品牌
				$("#report-query-brand").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:210,
					singleSelect:false
				});
				//品牌部门
				$("#report-query-branddept").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:210,
					singleSelect:false
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-baseWithdrawnPastDue","report-reload-baseWithdrawnPastDue");
				
				//查询
				$("#report-queay-baseWithdrawnPastDue").click(function(){
					setColumn();
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-baseWithdrawnPastDue").serializeJSON();
		      		$("#report-datagrid-baseWithdrawnPastDue").datagrid({
		      			url: 'report/finance/showBaseWithdrawnPastdueListData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-baseWithdrawnPastDue").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-salesdept").widget("clear");
					$("#report-query-salesarea").widget("clear");
					$("#report-query-salesuser").widget("clear");
					$("#report-query-brand").widget("clear");
					$("#report-query-branddept").widget("clear");
					$("#report-query-branduser").widget("clear");
					$("#report-query-form-baseWithdrawnPastDue")[0].reset();
					$("#report-query-groupcols").val("");
		       		$("#report-datagrid-baseWithdrawnPastDue").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-baseWithdrawnPastDuePage").Excel('export',{
					queryForm: "#report-query-form-baseWithdrawnPastDue", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'回笼超账期情况表',
			 		url:'report/finance/exportBaseWithdrawnPastDueData.do'
				});
				
				//设置超账期区间
				$("#report-setdays-baseWithdrawnPastDue").click(function(){
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
    		var $datagrid = $("#report-datagrid-baseWithdrawnPastDue");
    		function setColumn(){
    			var cols = $("#report-query-groupcols").val();
    			if(cols!=""){
	    			$datagrid.datagrid('hideColumn', "customerid");
					$datagrid.datagrid('hideColumn', "customername");
					$datagrid.datagrid('hideColumn', "pcustomerid");
					$datagrid.datagrid('hideColumn', "salesuser");
					$datagrid.datagrid('hideColumn', "salesarea");
					$datagrid.datagrid('hideColumn', "salesdept");
					$datagrid.datagrid('hideColumn', "goodsid");
					$datagrid.datagrid('hideColumn', "goodsname");
					$datagrid.datagrid('hideColumn', "barcode");
					$datagrid.datagrid('hideColumn', "brandid");
					$datagrid.datagrid('hideColumn', "branduser");
					$datagrid.datagrid('hideColumn', "branddept");
				}
				else{
					$datagrid.datagrid('showColumn', "customerid");
					$datagrid.datagrid('showColumn', "customername");
					$datagrid.datagrid('hideColumn', "pcustomerid");
					$datagrid.datagrid('hideColumn', "salesuser");
					$datagrid.datagrid('hideColumn', "salesarea");
					$datagrid.datagrid('hideColumn', "salesdept");
					$datagrid.datagrid('hideColumn', "goodsid");
					$datagrid.datagrid('hideColumn', "goodsname");
					$datagrid.datagrid('hideColumn', "barcode");
					$datagrid.datagrid('hideColumn', "brandid");
					$datagrid.datagrid('hideColumn', "branduser");
					$datagrid.datagrid('hideColumn', "branddept");
				}
				var colarr = cols.split(",");
				for(var i=0;i<colarr.length;i++){
					var col = colarr[i];
					if(col=='customerid'){
						$datagrid.datagrid('showColumn', "customerid");
						$datagrid.datagrid('showColumn', "customername");
					}else if(col=="pcustomerid"){
						$datagrid.datagrid('showColumn', "pcustomerid");
					}else if(col=="salesuser"){
						$datagrid.datagrid('showColumn', "salesuser");
					}else if(col=="salesarea"){
						$datagrid.datagrid('showColumn', "salesarea");
					}else if(col=="salesdept"){
						$datagrid.datagrid('showColumn', "salesdept");
					}else if(col=="goodsid"){
						$datagrid.datagrid('showColumn', "goodsid");
						$datagrid.datagrid('showColumn', "goodsname");
						$datagrid.datagrid('showColumn', "barcode");
						$datagrid.datagrid('showColumn', "brandid");
					}else if(col=="brandid"){
						$datagrid.datagrid('showColumn', "brandid");
						$datagrid.datagrid('showColumn', "branddept");
					}else if(col=="branduser"){
						$datagrid.datagrid('showColumn', "branduser");
					}else if(col=="branddept"){
						$datagrid.datagrid('showColumn', "branddept");
					}
				}
    		}
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-baseWithdrawnPastDue").datagrid('getChecked');
        		if(null==rows || rows.length==0){
            		var foot=[];
	    			if(null!=SR_footerobject){
		        		foot.push(SR_footerobject);
		    		}
	    			$("#report-datagrid-baseWithdrawnPastDue").datagrid("reloadFooter",foot);
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
        		var foot=[{customername:'选中合计',taxamount:taxamount,costamount:costamount,marginamount:marginamount,
        				unpassamount:unpassamount,totalpassamount:totalpassamount
        				<c:forEach items="${list }" var="list">
	    					,passamount${list.seq}:passamount${list.seq}
	    				</c:forEach>
        				,returnamount:returnamount,pushamount:pushamount}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-baseWithdrawnPastDue").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>

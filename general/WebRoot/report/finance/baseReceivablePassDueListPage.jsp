<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>应收款基础超账期统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
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
  <body>
    	<table id="report-datagrid-baseReceivablePastDue"></table>
    	<div id="report-toolbar-baseReceivablePastDue" class="buttonBG">
    		<a href="javaScript:void(0);" id="report-advancedQueay-baseReceivablePastDue" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="[Alt+Q]查询">查询</a>
    		<security:authorize url="/report/finance/baseReceivablePastDueExport.do">
				<a href="javaScript:void(0);" id="report-buttons-baseReceivablePastDuePage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
			</security:authorize>
			<a href="javaScript:void(0);" id="report-setdays-baseReceivablePastDue" class="easyui-linkbutton" iconCls="button-intervalSet" plain="true">设置区间</a>
    	</div>
    	<div style="display: none">
    		<div id="report-dialog-baseReceivablePastDue">
	    		<form action="" id="report-query-form-baseReceivablePastDue" method="post">
		    		<table cellpadding="2" style="margin-left:5px;margin-top: 5px;">
		    			<tr>
		    				<td width="70px">商品名称:</td>
		    				<td><input type="text" id="report-query-goodsid" name="goodsid" style="width: 210px;"/></td>
		    			</tr>
		    			<tr>
		    				<td width="70px">客户名称:</td>
		    				<td><input type="text" id="report-query-customerid" name="customerid" style="width: 210px;"/></td>
		    			</tr>
		    			<tr>
		    				<td>总店名称:</td>
		    				<td><input type="text" id="report-query-pcustomerid" name="pcustomerid" style="width: 210px;"/></td>
		    			</tr>
		    			<tr>
		    				<td>销售区域:</td>
		    				<td><input type="text" id="report-query-salesarea" name="salesarea" style="width: 210px;"/></td>
		    			</tr>
		    			<tr>
		    				<td>客户业务员:</td>
		    				<td><input type="text" id="report-query-salesuser" name="salesuser" style="width: 210px;"/></td>
		    			</tr>
		    			<tr>
		    				<td>品牌:</td>
		    				<td><input type="text" id="report-query-brand" name="brandid" style="width: 210px;"/></td>
		    			</tr>
		    			<tr>
		    				<td>品牌部门:</td>
		    				<td><input type="text" id="report-query-branddept" name="branddept" style="width: 210px;"/></td>
		    			</tr>
		    			<tr>
		    				<td>品牌业务员:</td>
		    				<td><input type="text" id="report-query-branduser" name="branduser" style="width: 210px;"/></td>
		    			</tr>
                        <tr>
                            <td>供应商:</td>
                            <td><input type="text" id="report-query-supplierid" name="supplierid" style="width: 210px;"/></td>
                        </tr>
                        <tr>
                            <td>销售部门:</td>
                            <td><input type="text" id="report-query-salesdept" name="salesdept" style="width: 210px;"/></td>
                        </tr>
		    			<tr>
		    				<td colspan="2">
		    					<table>
		    						<tr>
		    							<td>是否只显示超账期数据:</td>
					    				<td>
					    					<select name="ispastdue" style="width: 155px;">
					    						<option value="0" selected="selected">否</option>
					    						<option value="1">是</option>
					    					</select>
					    				</td>
		    						</tr>
		    					</table>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td>小计列:</td>
		    				<td>
                                <div style="float: left">
                                    <input type="checkbox" class="groupcols checkbox1" value="customerid" id="customerid"/>
                                    <label for="customerid" class="divtext">客户</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="pcustomerid" id="pcustomerid"/>
                                    <label for="pcustomerid" class="divtext">总店</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="salesuser" id="salesuer"/>
                                    <label for="salesuer" class="divtext">客户业务员</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="salesarea" id="salesarea"/>
                                    <label for="salesarea" class="divtext">销售区域</label>
                                    <input type="checkbox" class="groupcols checkbox1" value="salesdept" id="salesdept"/>
                                    <label for="salesdept" class="divtext">销售部门</label>
                                </div>
		    					<div style="float: left">
		    					<input type="checkbox" class="groupcols checkbox1" value="goodsid" id="goodsid"/>
                                <label for="goodsid" class="divtext">商品</label>
		    					<input type="checkbox" class="groupcols checkbox1" value="branddept" id="branddept"/>
                                <label for="branddept" class="divtext">品牌部门</label>
		    					<input type="checkbox" class="groupcols checkbox1" value="branduser" id="branduser"/>
                                <label for="branduser" class="divtext">品牌业务员</label>
		    					<input type="checkbox" class="groupcols checkbox1" value="brandid" id="brandid"/>
                                <label for="brandid" class="divtext">品牌</label>
                                <input type="checkbox" class="groupcols checkbox1" value="supplierid" id="supplierid"/>
                                <label for="supplierid" class="divtext">供应商</label>
		    					<input id="report-query-groupcols" type="hidden" name="groupcols"/>
                                </div>
		    				</td>
		    			</tr>
		    		</table>
		    	</form>
	    	</div>
    	</div>
    	<div id="report-paymentdaysSet-dialog"></div>
    	<div id="report-baseReceivablePastDue-detail-dialog"></div>
    	<script type="text/javascript">
		var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-baseReceivablePastDue").serializeJSON();
    		$(function(){
    			$(".groupcols").click(function(){
    				var cols = "";
					$("#report-query-groupcols").val(cols);
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
    			
    			var tableColumnListJson = $("#report-datagrid-baseReceivablePastDue").createGridColumnLoad({
    				frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
				    ]],
					commonCol : [[
						{field:'customerid',title:'客户编号',sortable:true,width:60},
					  	{field:'customername',title:'客户名称',width:130},
					  	{field:'pcustomerid',title:'总店名称',sortable:true,width:80,
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
                        {field:'supplierid',title:'供应商',sortable:true,width:200,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.suppliername;
                            }
                        },
					  	{field:'saleamount',title:'应收款',align:'right',width:100,sortable:true,
					  		formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
			        		}
					  	},
					 	{field:'unpassamount',title:'正常期金额',align:'right',width:100,sortable:true,
					  		formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
			        		}
					  	},
					  	{field:'totalpassamount',title:'超账期金额',align:'right',width:100,sortable:true,
					  		formatter:function(value,rowData,rowIndex){
		        				return formatterMoney(value);
			        		},
			        		styler:function(value,rowData,rowIndex){
			        			return 'color:blue';
			        		}
					  	},
					  	<c:forEach items="${list }" var="list">
					  	{field:'passamount${list.seq}',title:'${list.detail}',align:'right',width:100,sortable:true,
					  		formatter:function(value,rowData,rowIndex){
		        				return formatterMoney(value);
			        		}
					  	},
  						</c:forEach>
  						{field:'returnamount',title:'退货金额',align:'right',resizable:true,width:100,sortable:true,hidden:true,
					  		formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
			        		}
					  	},
					  	{field:'returnrate',title:'退货率',align:'right',resizable:true,width:100,sortable:true,hidden:true,
					  		formatter:function(value,rowData,rowIndex){
					  			if(value!=null && value!=""){
			        				return formatterMoney(value)+"%";
			        			}
			        		}
					  	},
					  	{field:'pushamount',title:'冲差金额',align:'right',resizable:true,width:100,sortable:true,hidden:true,
					  		formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
			        		}
					  	}
					]]
    			});

				//客户
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
					width:210,
					singleSelect:false
				});
				//总店
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
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
				//客户业务员
				$("#report-query-salesuser").widget({
					referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
					width:210,
					singleSelect:false
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
				//品牌业务员
				$("#report-query-branduser").widget({
					referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
					width:210,
					singleSelect:false
				});
				//供应商
				$("#report-query-supplierid").widget({
					referwid:'RL_T_BASE_BUY_SUPPLIER',
					width:210,
					singleSelect:false
				});
				//商品
				$("#report-query-goodsid").widget({
					referwid:'RL_T_BASE_GOODS_INFO',
					singleSelect:false,
					width:210,
					onlyLeafCheck:true
				});
				//销售部门
				$("#report-query-salesdept").widget({
					name:'t_sales_order',
					col:'salesdept',
					width:210,
					singleSelect:false,
					onlyLeafCheck:true
				});

    			$("#report-datagrid-baseReceivablePastDue").datagrid({ 
					authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-baseReceivablePastDue',
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

				//回车事件
				controlQueryAndResetByKey("report-advancedQueay-baseReceivablePastDue","report-reload-baseReceivablePastDue");
				
				$("#report-buttons-baseReceivablePastDuePage").Excel('export',{
					queryForm: "#report-query-form-baseReceivablePastDue", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'应收款超账期情况表',
			 		url:'report/finance/exportBaseReceivablePastDueData.do'
				});
				
				//高级查询
				$("#report-advancedQueay-baseReceivablePastDue").click(function(){
					$("#report-dialog-baseReceivablePastDue").dialog({
						maximizable:true,
						resizable:true,
						title: '应收款超账期情况表查询',
						top:30,
					    width: 440,
					    height: 450,
					    closed: false,
					    cache: false,
					    modal: true,
					    buttons:[
							{
								text:'确定',
								handler:function(){
									setColumn();
									//把form表单的name序列化成JSON对象
						      		var queryJSON = $("#report-query-form-baseReceivablePastDue").serializeJSON();
						      		$("#report-datagrid-baseReceivablePastDue").datagrid({
						      			url: 'report/finance/showBaseReceivablePassDueListData.do',
						      			pageNumber:1,
										queryParams:queryJSON
						      		}).datagrid("columnMoving");

						      		$("#report-dialog-baseReceivablePastDue").dialog('close');
								}
							},
							{
								text:'重置',
								handler:function(){
								$("#report-query-form-baseReceivablePastDue").form("reset");
									$(".groupcols").each(function(){
				    					if($(this).attr("checked")){
				    						$(this)[0].checked = false;
				    					}
									});
									$("#report-query-goodsid").widget("clear");
									$("#report-query-salesdept").widget("clear");
									$("#report-query-customerid").widget("clear");
									$("#report-query-pcustomerid").widget("clear");
									$("#report-query-salesarea").widget("clear");
									$("#report-query-salesuser").widget("clear");
									$("#report-query-brand").widget("clear");
									$("#report-query-branddept").widget("clear");
									$("#report-query-branduser").widget("clear");
                                    $("#report-query-supplierid").widget("clear");
									$("#report-query-groupcols").val("");
						       		$("#report-datagrid-baseReceivablePastDue").datagrid('loadData',[]);
									setColumn();
								}
							}
						],
						onClose:function(){
						}
					});
				});
				
				//设置超账期区间
				$("#report-setdays-baseReceivablePastDue").click(function(){
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
    		var $datagrid = $("#report-datagrid-baseReceivablePastDue");
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
                    $datagrid.datagrid('hideColumn', "supplierid");
				}
				else{
					$datagrid.datagrid('showColumn', "customerid");
					$datagrid.datagrid('showColumn', "customername");
					$datagrid.datagrid('hideColumn', "pcustomerid");
					$datagrid.datagrid('hideColumn', "salesuser");
					$datagrid.datagrid('hideColumn', "salesarea");
					$datagrid.datagrid('hideColumn', "salesdept");
					$datagrid.datagrid('showColumn', "goodsid");
					$datagrid.datagrid('showColumn', "goodsname");
					$datagrid.datagrid('showColumn', "barcode");
					$datagrid.datagrid('showColumn', "brandid");
					$datagrid.datagrid('hideColumn', "branduser");
					$datagrid.datagrid('hideColumn', "branddept");
                    $datagrid.datagrid('hideColumn', "supplierid");
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
					}else if(col=="supplierid"){
                        $datagrid.datagrid('showColumn', "supplierid");
                    }
				}
    		}
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-baseReceivablePastDue").datagrid('getChecked');
    			var saleamount = 0;
        		var unpassamount = 0;
        		var totalpassamount=0;
        		var returnamount = 0;
        		var pushamount = 0 ;
        		<c:forEach items="${list }" var="list">
				  var passamount${list.seq} = 0;
				</c:forEach>
        		for(var i=0;i<rows.length;i++){
        			saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
        			unpassamount = Number(unpassamount)+Number(rows[i].unpassamount == undefined ? 0 : rows[i].unpassamount);
        			totalpassamount = Number(totalpassamount)+Number(rows[i].totalpassamount == undefined ? 0 : rows[i].totalpassamount);
        			returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
        			pushamount = Number(pushamount)+Number(rows[i].pushamount == undefined ? 0 : rows[i].pushamount);
        			<c:forEach items="${list }" var="list">
            			passamount${list.seq} = Number(passamount${list.seq})+Number(rows[i].passamount${list.seq} == undefined ? 0 : rows[i].passamount${list.seq});
    				</c:forEach>
        		}
        		var foot=[{customername:'选中合计',unitname:'',saleamount:saleamount,unpassamount:unpassamount,totalpassamount:totalpassamount
            			<c:forEach items="${list }" var="list">
	    					,passamount${list.seq}:passamount${list.seq}
	    				</c:forEach>
            			,returnamount:returnamount,pushamount:pushamount
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-baseReceivablePastDue").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>

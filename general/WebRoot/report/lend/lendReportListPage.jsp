<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>仓库借货还货统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-lendReport"></table>
    	<div id="report-toolbar-lendReport" style="padding: 0px">
    		<div class="buttonBG">
		        <security:authorize url="/report/storage/lendReportExport.do">
		            <a href="javaScript:void(0);" id="report-buttons-lendReportPage" class="easyui-linkbutton button-list" iconCls="button-export" plain="true" >全局导出</a>
		        </security:authorize>
		    </div>
    		<form action="" id="report-query-form-lendReport" method="post">
    		<table>
    			<tr>
    				<td>日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }',isShowClear:false})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }',isShowClear:false})" /></td>
    				<td>仓库:</td>
    				<td><input type="text" id="report-query-storageid" name="storageid" style="width: 225px"/></td>
					<td>商品分类:</td>
					<td><input type="text" id="report-query-goodssort" name="goodssort" style="width: 150px;"/></td>
    			</tr>
    			<tr>
    				<td>商品名称:</td>
    				<td><input type="text" id="report-query-goodsid" name="goodsid" /></td>
    				<td>品牌名称:</td>
    				<td><input type="text" id="report-query-brandid" name="brandid" style="width: 225px;"/></td>
					<td>借货还货人类型:</td>
					<td>
						<input type="text" id="report-query-lendtype" name="lendtype" style="width: 150px;"/>
					</td>
    			</tr>
    			<tr>
    				<td>借货还货人:</td>
    				<td id="lendp">
    					<input type="text" id="report-query-lendid" name="lendid" style="width: 225px;"/>
    				</td>
					<td>小计列：</td>
					<td><div style="margin-top:2px">
						<div style="line-height: 25px;margin-top: 2px;">
							<label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customerid"/>客户</label>
							<label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="supplierid"/>供应商</label>
							<label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid"/>商品</label>
							<label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid"/>品牌</label>
							<label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="deptid"/>部门</label>
							<input id="report-query-groupcols" type="hidden" name="groupcols"/>
						</div>
					</div></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-lendReport" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-lendReport" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-lendReport").serializeJSON();
    		var dataListJson = $("#report-datagrid-lendReport").createGridColumnLoad({
				frozenCol : [[

			    			]],

				commonCol : [[
					{field:'storageid',title:'仓库名称',width:80,
						formatter:function(value,rowData,rowIndex){
							return rowData.storagename;
						}
					},
					{field:'lendid',title:'借货还货人',width:70,isShow:false,
						formatter:function (value,rowData,rowIndex) {
							return rowData.lendname;
						}
					},
					{field:'deptid',title:'部门',width:70,isShow:false,
						formatter:function (value,rowData,rowIndex) {
							return rowData.deptname;
						}
					},
					         {field:'goodsid',title:'商品编码',width:70},
							 {field:'goodsname',title:'商品名称',width:130,sortable:true},
							 {field:'barcode',title:'条形码',sortable:true,width:90,isShow:true},
							  {field:'brandid',title:'品牌名称',width:80,sortable:true,
								 formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
						        	}
							  },
							  {field:'goodssort',title:'商品分类',width:80,sortable:true,
								  formatter:function(value,rowData,rowIndex){
						        		return rowData.goodssortname;
						        	}
							  },
							  {field:'boxnum',title:'箱装量',width:60,aliascol:'goodsid',align:'right',
								  formatter:function(value,rowData,rowIndex){
									  return formatterBigNumNoLen(value);
						          }
							  },
							  {field:'unitname',title:'单位',width:40},
								{field:'initamount',title:'期初金额',width:80,sortable:true,align:'right',
									formatter:function(value,rowData,rowIndex){
										return formatterBigNumNoLen(value);
									}
								},
							  {field:'initnum',title:'期初数量',width:80,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'initauxnumdetail',title:'期初辅数量',width:90,sortable:true,align:'right',aliascol:'initnum'},
							{field:'enteramount',title:'还货金额',width:80,align:'right',sortable:true,
								formatter:function(value,rowData,rowIndex){
									return formatterBigNumNoLen(value);
								}
							},
							  {field:'enternum',title:'还货数量',width:80,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'enterauxnumdetail',title:'还货辅数量',width:90,sortable:true,align:'right',aliascol:'enternum'},
							{field:'outamount',title:'借货金额',width:80,align:'right',sortable:true,
								formatter:function(value,rowData,rowIndex){
									return formatterBigNumNoLen(value);
								}
							},
							  {field:'outnum',title:'借货数量',width:80,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'outauxnumdetail',title:'借货辅数量',width:90,sortable:true,align:'right',aliascol:'outnum'},
								{field:'endamount',title:'期末金额',width:90,align:'right',sortable:true,
									formatter:function(value,rowData,rowIndex){
										return formatterBigNumNoLen(value);
									}
								},
							  {field:'endnum',title:'期末数量',width:90,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterBigNumNoLen(value);
					        	}
							  },
							  {field:'endauxnumdetail',title:'期末辅数量',width:95,sortable:true,align:'right',aliascol:'endnum'}
				             ]]
			});
    		$(function(){
    			$("#report-datagrid-lendReport").datagrid({
					authority:dataListJson,
		 			frozenColumns: dataListJson.frozen,
					columns:dataListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
					toolbar:'#report-toolbar-lendReport'
				}).datagrid("columnMoving");
    			$("#report-query-supplierid").supplierWidget({
    				width:225,
    			});
				$("#report-query-goodsid").widget({
					referwid:'RL_T_BASE_GOODS_INFO',
		    		width:225,
					singleSelect:false
				});
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:225,
					singleSelect:false
				});
				$("#report-query-storageid").widget({
					referwid:'RL_T_BASE_STORAGE_INFO',
		    		width:225,
					singleSelect:true
				});
				$("#report-query-goodssort").widget({
					referwid:'RL_T_BASE_GOODS_WARESCLASS',
		    		width:150,
					singleSelect:true,
					onlyLeafCheck:false
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-lendReport","report-reload-lendReport");
				
				//查询
				$("#report-queay-lendReport").click(function(){

                    setColumn();
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-lendReport").serializeJSON();
		      		$("#report-datagrid-lendReport").datagrid({
		      			url: 'report/lend/showLendReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});

				});

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

				//重置
				$("#report-reload-lendReport").click(function(){
					$(".groupcols").each(function(){
						if($(this).attr("checked")){
							$(this)[0].checked = false;
						}
					});
					$("#report-query-groupcols").val("customerid,deptid,goodsid,supplierid,supplierid,brandid,storageid");
					$("#report-query-goodsid").goodsWidget("clear");
					$("#report-query-storageid").widget("clear");
					$("#report-query-brandid").widget("clear");
					$("#report-query-goodssort").widget("clear");
					var temp = $("#report-query-lendtype").val();
					$("#report-query-lendtype").widget("clear");
					
					$("#report-query-form-lendReport").form("reset");
					var queryJSON = $("#report-query-form-lendReport").serializeJSON();
		       		$("#report-datagrid-lendReport").datagrid('loadData',{total:0,rows:[]});
					setColumn();
				});


				function setColumn(){
					var cols = $("#report-query-groupcols").val();
					if(cols!=""){
						$("#report-datagrid-lendReport").datagrid('hideColumn', "lendid");
						$("#report-datagrid-lendReport").datagrid('hideColumn', "deptid");
						$("#report-datagrid-lendReport").datagrid('hideColumn', "goodsid");
						$("#report-datagrid-lendReport").datagrid('hideColumn', "goodsname");
						$("#report-datagrid-lendReport").datagrid('hideColumn', "goodssort");
						$("#report-datagrid-lendReport").datagrid('hideColumn', "barcode");
						$("#report-datagrid-lendReport").datagrid('hideColumn', "boxnum");
						$("#report-datagrid-lendReport").datagrid('hideColumn', "storageid");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "unitname");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "initauxnumdetail");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "enterauxnumdetail");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "outauxnumdetail");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "endauxnumdetail");
					}
					else{
						$("#report-datagrid-lendReport").datagrid('showColumn', "storageid");
						$("#report-datagrid-lendReport").datagrid('hideColumn', "deptid");
						$("#report-datagrid-lendReport").datagrid('hideColumn', "brandid");
						$("#report-datagrid-lendReport").datagrid('hideColumn', "lendid");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "goodsid");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "goodsname");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "goodssort");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "barcode");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "brandid");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "boxnum");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "unitname");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "initauxnumdetail");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "enterauxnumdetail");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "outauxnumdetail");
                        $("#report-datagrid-lendReport").datagrid('hideColumn', "endauxnumdetail");
                    }
					var colarr = cols.split(",");
					for(var i=0;i<colarr.length;i++){
						var col = colarr[i];
						if(col=='customerid'){
							$("#report-datagrid-lendReport").datagrid('showColumn', "lendid");
							//$("#report-datagrid-lendReport").datagrid('showColumn', "salesarea");
							//$("#report-datagrid-lendReport").datagrid('showColumn', "dept");
						}else if(col=="deptid"){
							$("#report-datagrid-lendReport").datagrid('showColumn', "deptid");
						}else if(col=="goodsid"){
							$("#report-datagrid-lendReport").datagrid('showColumn', "goodsid");
							$("#report-datagrid-lendReport").datagrid('showColumn', "goodsname");
							$("#report-datagrid-lendReport").datagrid('showColumn', "goodssort");
							$("#report-datagrid-lendReport").datagrid('showColumn', "barcode");
							$("#report-datagrid-lendReport").datagrid('showColumn', "boxnum");
							$("#report-datagrid-lendReport").datagrid('showColumn', "brandid");
							$("#report-datagrid-lendReport").datagrid('showColumn', "unitname");
							$("#report-datagrid-lendReport").datagrid('showColumn', "initauxnumdetail");
							$("#report-datagrid-lendReport").datagrid('showColumn', "endauxnumdetail");
							$("#report-datagrid-lendReport").datagrid('showColumn', "outauxnumdetail");
							$("#report-datagrid-lendReport").datagrid('showColumn', "enterauxnumdetail");
						}else if(col=="supplierid"){
							$("#report-datagrid-lendReport").datagrid('showColumn', "lendid");
						}else if(col=="brandid"){
							$("#report-datagrid-lendReport").datagrid('showColumn', "brandid");
						}else if(col=="storageid"){
							$("#report-datagrid-lendReport").datagrid('showColumn', "storageid");
						}
					}
				}

                //导出
                $("#report-buttons-lendReportPage").click(function(){
                    //封装查询条件
                    var objecr  = $("#report-datagrid-lendReport").datagrid("options");
                    var queryParam = objecr.queryParams;
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryParam["sort"] = objecr.sortName;
                        queryParam["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryParam);
                    var url = "report/lend/exportLendRecSendReportData.do";
                    exportByAnalyse(queryParam,"借货还货情况表","report-datagrid-lendReport",url);
                });

				$('#report-query-lendtype').widget({
					width:150,
					referwid:'RL_T_SYS_CODELENDTYPE',
					singleSelect:true,
					onlyLeafCheck:false,
					onSelect : function(data){
						$("#lendp").empty();
						var tdstr = '<input type="text" id="report-query-lendid" name="lendid" style="width: 225px"/>';
						$(tdstr).appendTo("#lendp");
						if (data.code == 1) {
							$("#report-query-lendid").widget({
								width: 225,
								referwid:'RL_T_BASE_BUY_SUPPLIER',
								singleSelect:true,
								onlyLeafCheck:false,
							})
						} else if (data.code == 2) {
							$("#report-query-lendid").widget({
								width: 225,
								referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
								singleSelect:true,
								onlyLeafCheck:false,
							})
						}
					},
					onClear:function () {
						$("#lendp").empty();
						var tdstr = '<input type="text" id="report-query-lendid" name="lendid" style="width: 225px" style="display: block"/>';
						$(tdstr).appendTo("#lendp");
					}
				});

    		});
    	</script>
  </body>
</html>

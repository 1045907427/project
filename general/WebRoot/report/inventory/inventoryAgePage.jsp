<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>库龄报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
  </head>
  <body>
    	<table id="report-datagrid-inventoryAage"></table>
    	<div id="report-toolbar-inventoryAage">
            <form action="" id="report-query-form-inventoryAage" method="post">
                <table>
                    <tr>
                        <td colspan="6">
                            <security:authorize url="/report/inventory/exportInventoryAageData.do">
                                <a href="javaScript:void(0);" id="report-export-inventoryAage" class="easyui-linkbutton" iconCls="button-export" plain="true">导出</a>
                            </security:authorize>
                            <security:authorize url="/report/paymentdays/showPaymetdaysSetPage.do">
                                <a href="javaScript:void(0);" id="report-setdays-inventoryAage" class="easyui-linkbutton" iconCls="button-intervalSet" plain="true">设置区间</a>
                            </security:authorize>
                            <security:authorize url="/report/inventory/addInventoryAgeData.do">
                                <a href="javaScript:void(0);" id="report-addInventoryAge-inventoryAage" class="easyui-linkbutton" iconCls="button-add" plain="true">生成库龄报表</a>
                            </security:authorize>
                        </td>
                    </tr>
                    <tr>
                        <td width="70px">日期:</td>
                        <td>
                            <input id="report-query-businessdate" type="text" name="businessdate" value="${today }" style="width:210px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" />
                        </td>
                        <td>库龄:</td>
                        <td>
                            <input type="text" name="age1" class="easyui-numberbox" data-options="min:0" style="width: 80px;"/> -
                            <input type="text" name="age2" class="easyui-numberbox" data-options="min:0" style="width: 80px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>商品名称:</td>
                        <td><input type="text" id="report-query-goodsid" name="goodsid" style="width: 210px;"/></td>
                        <td>品牌:</td>
                        <td><input type="text" id="report-query-brand" name="brandid" style="width: 210px;"/></td>
                        <td>供应商:</td>
                        <td><input type="text" id="report-query-supplierid" name="supplierid" style="width: 210px;"/></td>
                    </tr>
                    <tr>
                        <td>仓库:</td>
                        <td>
                            <input type="text" id="report-query-storageid" name="storageid" style="width: 210px;"/>
                        </td>
                        <td colspan="2">
                            <label class="divtext">小计列:</label>
                            <input type="checkbox" id="groupcols_storageid" class="groupcols checkbox1" value="storageid"/>
                            <label for="groupcols_storageid" class="divtext">仓库</label>
                            <input type="checkbox" id="groupcols_brandid" class="groupcols checkbox1" value="brandid"/>
                            <label for="groupcols_brandid" class="divtext">品牌</label>
                            <input type="checkbox" id="groupcols_supplierid" class="groupcols checkbox1" value="supplierid"/>
                            <label for="groupcols_supplierid" class="divtext">供应商</label>
                            <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                        </td>
                        <td colspan="2">
                            <a href="javaScript:void(0);" id="report-queay-inventoryAage" class="button-qr" plain="true">查询</a>
                            <a href="javaScript:void(0);" id="report-reload-inventoryAage" class="button-qr"plain="true">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
    	</div>
    	<div id="report-paymentdaysSet-dialog"></div>
    	<div id="report-inventoryAage-detail-dialog"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-inventoryAage").serializeJSON();
    		$(function(){
                $(".groupcols").click(function(){
                    var cols = "";
                    $("#report-query-groupcols").val("");
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
    			var tableColumnListJson = $("#report-datagrid-inventoryAage").createGridColumnLoad({
    				frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
				    ]],
					commonCol : [[
						{field:'storageid',title:'仓库编号',sortable:true,width:60},
					  	{field:'storagename',title:'仓库名称',width:80},
					  	{field:'goodsid',title:'商品编码',sortable:true,width:80},
					  	{field:'goodsname',title:'商品名称',sortable:true,width:130},
					  	{field:'brandid',title:'品牌名称',sortable:true,width:70,
					  		formatter:function(value,rowData,rowIndex){
			        			return rowData.brandname;
			        		}
					  	},
                        {field:'supplierid',title:'供应商名称',sortable:true,width:200,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return rowData.suppliername;
                            }
                        },
                        {field:'price',title:'单价',align:'right',width:60,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'unitnum',title:'库存数量',align:'right',width:100,sortable:true,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterBigNum(value);
                            }
                        },
					  	{field:'auxnumdetail',title:'库存辅数量',align:'right',width:80,sortable:true},
                        {field:'taxamount',title:'库存金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'notaxamount',title:'库存未税金额',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
					 	{field:'age',title:'库龄(天)',align:'right',width:75,sortable:true,
					  		formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
			        		}
					  	},
					  	<c:forEach items="${list }" var="list">
                        {field:'inum${list.seq}',title:'${list.detail}/数量',align:'right',width:95,sortable:true,hidden:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterBigNum(value);
                            }
                        },
                        {field:'inumdetail${list.seq}',title:'${list.detail}/辅数量',align:'right',width:95},
                        {field:'iamount${list.seq}',title:'${list.detail}/含税金额',align:'right',width:100,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'inotaxamount${list.seq}',title:'${list.detail}/未税金额',align:'right',width:100,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
  						</c:forEach>
                        {field:'addtime',title:'生成时间',align:'right',width:130,sortable:true,
                            formatter:function(val,rowData,rowIndex){
                                if(val){
                                    return val.replace(/[tT]/," ");
                                }
                            }
                        }
					]]
    			});
				//仓库
				$("#report-query-storageid").widget({
					referwid:'RL_T_BASE_STORAGE_INFO',
					width:210,
					singleSelect:true
				});
				//品牌
				$("#report-query-brand").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
					width:210,
					singleSelect:true
				});
				//供应商
				$("#report-query-supplierid").widget({
					referwid:'RL_T_BASE_BUY_SUPPLIER',
					width:210,
					singleSelect:true
				});
				//商品
				$("#report-query-goodsid").widget({
					referwid:'RL_T_BASE_GOODS_INFO',
					singleSelect:true,
					width:210,
					onlyLeafCheck:true
				});

    			$("#report-datagrid-inventoryAage").datagrid({ 
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
					toolbar:'#report-toolbar-inventoryAage',
                    onLoadSuccess:function(data){
                        if(null==data || data.rows.length==0){
                            var date = $("#report-query-businessdate").val();
                            $.messager.alert("提醒","未生成日期:"+date+"的库龄报表，可以通过生成库龄报表按钮生成数据。");
                        }
                    },
                    onDblClickRow:function(index,row){
                        $("#report-inventoryAage-detail-dialog").dialog({
                            title: '库存入库明细',
                            width: 650,
                            height: 400,
                            closed: false,
                            cache: false,
                            modal: true,
                            href: 'report/inventory/showInventoryAgeDetailLogPage.do',
                            queryParams:{businessdate:row.businessdate,storageid:row.storageid,goodsid:row.goodsid}
                        });
                    }
				});
                $("#report-addInventoryAge-inventoryAage").click(function(){
                    var date = $("#report-query-businessdate").val();
                    $.messager.confirm("提醒","是否生成日期："+date+"的库龄报表？",function(r){
                        if(r){
                            loading("生成中...");
                            $.ajax({
                                url:'report/inventory/addInventoryAgeData.do',
                                dataType:'json',
                                type:'post',
                                data:{businessdate:date},
                                success:function(json){
                                    loaded();
                                    if(json.flag == true){
                                        $.messager.alert("提醒","生成成功");
                                    }
                                    else{
                                        $.messager.alert("提醒","生成出错");
                                    }
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("错误","生成出错");
                                }
                            });
                        }
                    });
                });
                $("#report-export-inventoryAage").click(function(){
                    var queryJSON = $("#report-query-form-inventoryAage").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-inventoryAage").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/inventory/exportInventoryAageData.do";
                    exportByAnalyse(queryParam,"库龄报表","report-datagrid-inventoryAage",url);
                });
                $("#report-queay-inventoryAage").click(function(){
                    searchData();
                });
                $("#report-reload-inventoryAage").click(function(){
                    $("#report-query-groupcols").val("");
                    $("#report-query-goodsid").widget("clear");
                    $("#report-query-storageid").widget("clear");
                    $("#report-query-brand").widget("clear");
                    $("#report-query-supplierid").widget("clear");
                    $("#report-query-form-inventoryAage").form("reset");
                    searchData();
                });

				//设置超账期区间
				$("#report-setdays-inventoryAage").click(function(){
					$("#report-paymentdaysSet-dialog").dialog({
					    title: '库龄区间设置',
					    width: 400,  
					    height: 400,  
					    closed: false,  
					    cache: false,  
					    modal: true,
					    href: 'report/paymentdays/showPaymetdaysSetPage.do?type=3'
					});
				});
    		});

            var $datagrid = $("#report-datagrid-inventoryAage");
            function setColumn() {
                var list='${listStr}';
                var data=eval(list);
                var cols = $("#report-query-groupcols").val();
                if (cols != "") {
                    $datagrid.datagrid('hideColumn', "storageid");
                    $datagrid.datagrid('hideColumn', "storagename");
                    $datagrid.datagrid('hideColumn', "goodsid");
                    $datagrid.datagrid('hideColumn', "goodsname");
                    $datagrid.datagrid('hideColumn', "brandid");
                    $datagrid.datagrid('hideColumn', "supplierid");
                    $datagrid.datagrid('hideColumn', "price");
                    $datagrid.datagrid('hideColumn', "auxnumdetail");
                    for(var t=0;t<data.length;t++){
                        $datagrid.datagrid('hideColumn', "inumdetail"+(t+1));
                    }
                }else {
                    $datagrid.datagrid('showColumn', "storageid");
                    $datagrid.datagrid('showColumn', "storagename");
                    $datagrid.datagrid('showColumn', "goodsid");
                    $datagrid.datagrid('showColumn', "goodsname");
                    $datagrid.datagrid('hideColumn', "brandid");
                    $datagrid.datagrid('hideColumn', "supplierid");
                    $datagrid.datagrid('showColumn', "price");
                    $datagrid.datagrid('showColumn', "auxnumdetail");
                    for(var t=0;t<data.length;t++){
                        $datagrid.datagrid('showColumn', "inumdetail"+(t+1));
                    }
                }
                var colarr = cols.split(",");
                for (var i = 0; i < colarr.length; i++) {
                    var col = colarr[i];
                    if (col == "storageid") {
                        $datagrid.datagrid('showColumn', "storageid");
                        $datagrid.datagrid('showColumn', "storagename");
                    } else if (col == "brandid") {
                        $datagrid.datagrid('showColumn', "brandid");
                    } else if (col == "supplierid") {
                        $datagrid.datagrid('showColumn', "supplierid");
                    }
                }
            }

            function searchData(){
                var date = $("#report-query-businessdate").val();
                if(date==''){
                    $.messager.alert("提醒","未生成日期:"+date+"的库龄报表，可以通过生成库龄报表按钮生成数据。");
                    return false;
                }

                setColumn();
                var queryJSON = $("#report-query-form-inventoryAage").serializeJSON();
                $("#report-datagrid-inventoryAage").datagrid({
                    url: 'report/inventory/showInventoryAgeDataList.do',
                    pageNumber:1,
                    queryParams:queryJSON
                });
            }

        </script>
  </body>
</html>

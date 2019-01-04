<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>库存商品盘点记录报表</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center'">
    		<table id="storage-datagrid-storageGoodsCheckLogPage"></table>
            <div id="storage-datagrid-toolbar-storageGoodsCheckLogPage" style="padding:2px;height:auto">
                <form action="" id="storage-form-query-storageGoodsCheckLogPage" method="post">
                    <table class="querytable">
                        <tr>
                            <a href="javaScript:void(0);" id="storage-export-checkList" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                        </tr>
                        <tr>
                            <td>商品名称:</td>
                            <td>
                                <input id="storage-checkList-goodsid" type="text" style="width:150px;" name="goodsid"/>
                            </td>
                            <td>品牌名称:</td>
                            <td>
                                <input id="storage-checkList-brandid" type="text" style="width:150px;" name="brandid"/>
                            </td>
                            <td>商品分类:</td>
                            <td>
                                <input id="storage-checkList-goodssort" type="text" style="width:150px;" name="goodssort"/>
                            </td>
                        </tr>
                        <tr>
                            <td>仓库:</td>
                            <td><input id="storage-checkList-storageid" name="storageid" style="width:150px"/></td>
                            <td>统计最近几次盘点:</td>
                            <td>
                                <input type="text" class="easyui-numberspinner" style="width:150px;" name="nums"
                                       data-options="min:1,max:10,
                                        onChange: function(value){
                                                   chengeDataGrid(value);
                                                }"
                                       value="2"/>
                            </td>
                            <td colspan="2" style="padding-left: 20px">
                                <a href="javaScript:void(0);" id="storage-queay-checkList" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="storage-reload-checkList" class="button-qr">重置</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
    	</div>
    </div>
    <form id="storage-form-export-storageGoodsCheckLogPage">
    	<input id="storage-export-id" type="hidden" name="id"/>
    </form>
	<div id="storage-dialog-dynamicCheckList"></div>
    <script type="text/javascript">
    	var initQueryJSON = $("#storage-form-query-storageGoodsCheckLogPage").serializeJSON();
    	$(function(){
            $("#storage-checkList-goodsid").goodsWidget({});
            $("#storage-checkList-brandid").widget({
                referwid:'RL_T_BASE_GOODS_BRAND',
                width:150,
                singleSelect:false
            });
            $("#storage-checkList-goodssort").widget({
                referwid:'RL_T_BASE_GOODS_WARESCLASS',
                width:150,
                singleSelect:false
            });
            $("#storage-checkList-storageid").widget({
                width:150,
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:false,
                onlyLeafCheck:false
            });
            $("#storage-export-checkList").click(function(){
                //封装查询条件
                var objecr  = $("#storage-datagrid-storageGoodsCheckLogPage").datagrid("options");
                var queryParam = objecr.queryParams;
                if(null != objecr.sortName && null != objecr.sortOrder ){
                    queryParam["sort"] = objecr.sortName;
                    queryParam["order"] = objecr.sortOrder;
                }
                var queryParam = JSON.stringify(queryParam);
                var url = "report/storage/expotStorageGoodsCheckLogData.do";
                exportByAnalyse(queryParam,"库存商品盘点记录报表","storage-datagrid-storageGoodsCheckLogPage",url);
            });
            //查询
            $("#storage-queay-checkList").click(function(){
                var queryJSON = $("#storage-form-query-storageGoodsCheckLogPage").serializeJSON();
                $("#storage-datagrid-storageGoodsCheckLogPage").datagrid({
                    url:'report/storage/showStorageGoodsCheckLogData.do',
                    pageNumber:1,
                    queryParams:queryJSON
                });
            });
            //重置
            $("#storage-reload-checkList").click(function(){
                $("#storage-checkList-goodsid").goodsWidget("clear");
                $("#storage-checkList-brandid").widget("clear");
                $("#storage-checkList-goodssort").widget("clear");
                $("#storage-checkList-storageid").widget('clear');
                $("#storage-form-query-storageGoodsCheckLogPage").form("reset");
                var queryJSON = $("#storage-form-query-storageGoodsCheckLogPage").serializeJSON();
                $("#storage-datagrid-storageGoodsCheckLogPage").datagrid("load",queryJSON);
            });
    	});
    	function chengeDataGrid(nums){
    	    var columnsCol =[];
            var columnsColFirst = [{field:'storageid',title:'仓库',width:120,sortable:true,rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.storagename;
                    }
                },
                {field:'goodsid',title:'商品编码',width:60,sortable:true,rowspan:2},
                {field:'goodsname',title:'商品名称',width:150,rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.goodsname;
                    }
                },
                {field:'brandid',title:'品牌名称',width:60,sortable:true,rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandname;
                    }
                },
                {field:'goodssort',title:'商品分类',width:80,rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.goodssortname;
                    }
                },
                {field:'existingnum',title:'现存量',width:60,align:'right',sortable:true,rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        if(value!=0){
                            return formatterBigNumNoLen(value);
                        }else{
                            return 0;
                        }
                    }
                },
                {field:'auxexistingdetail',title:'现存箱数',width:80,align:'right',rowspan:2},
                {field:'usablenum',title:'可用量',width:60,align:'right',sortable:true,rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        if(value!=0){
                            return formatterBigNumNoLen(value);
                        }else{
                            return 0;
                        }
                    }
                },
                {field:'auxusabledetail',title:'可用箱数',width:80,align:'right',rowspan:2}
            ];
            var columnsColSecond = [];
            for(var i=1;i<=nums;i++){
                 columnsColFirst.push({title:'最近第'+i+'次盘点',colspan:7});
                columnsColSecond.push(
                    {field:'billid'+i,title:'盘点单号',width:130}
                );
                 columnsColSecond.push(
                     {field:'booknum'+i,title:'账面数量',width:80,align:'right',
                         formatter:function(value,rowData,rowIndex){
                             if(value!=0){
                                 return formatterBigNumNoLen(value);
                             }else{
                                 return 0;
                             }
                         }
                     }
                 );
                 columnsColSecond.push(
                     {field:'realnum'+i,title:'实际数量',width:80,align:'right',
                         formatter:function(value,rowData,rowIndex){
                             if(value!=0){
                                 return formatterBigNumNoLen(value);
                             }else{
                                 return 0;
                             }
                         }
                     }
                 );
                 columnsColSecond.push(
                     {field:'profitlossnum'+i,title:'盈亏数量',width:80,align:'right',
                         formatter:function(value,rowData,rowIndex){
                             if(value!=0){
                                 return formatterBigNumNoLen(value);
                             }else{
                                 return 0;
                             }
                         }
                     }
                 );
                 columnsColSecond.push(
                     {field:'istrue'+i,title:'是否正确',width:60,align:'right',sortable:true,
                         formatter:function(value,rowData,rowIndex){
                             if(value=="1"){
                                 return "是";
                             }else if(value=="0"){
                                 return "否";
                             }
                         }
                     }
                 );
                 columnsColSecond.push(
                     {field:'checkusername'+i,title:'盘点人',width:80}
                 );
                 columnsColSecond.push(
                     {field:'audittime'+i,title:'盘点时间',width:130,sortable:true}
                 );
             }
            columnsCol.push(columnsColFirst);
            columnsCol.push(columnsColSecond);
            $("#storage-datagrid-storageGoodsCheckLogPage").datagrid({
                columns:columnsCol,
                fit:true,
                method:'post',
                rownumbers:true,
                pagination:true,
                idField:'id',
                sortName:'id',
                sortOrder:'desc',
                singleSelect:true,
                sortName:'storageid',
                sortOrder:'asc',
                pageSize:100,
                queryParams:initQueryJSON,
                toolbar:'#storage-datagrid-toolbar-storageGoodsCheckLogPage'
            });
        }
        setTimeout(function(){
            chengeDataGrid(2);
        },300);
    </script>
  </body>
</html>

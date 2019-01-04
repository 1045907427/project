<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>库存报表</title>
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
    <table id="report-storageSummary-tree-table"></table>
    <div id="report-storageSummary-query-div" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/storage/exportStorageAmountReportListData.do">
                <a href="javaScript:void(0);" id="report-buttons-exportStorageReport" class="easyui-linkbutton" iconCls="button-export" plain="true" >全局导出</a>
            </security:authorize>
        </div>
    	<form action="" id="report-storageSummary-form-query">
    		<table class="querytable">

    			<tr>
    				<td>商品名称:</td>
    				<td><input id="report-storageSummary-goodsid" name="goodsid"/></td>
    				<td>商品分类:</td>
    				<td><input type="text" id="report-storageSummary-goodssort" name="goodssort" style="width:150px"/></td>
    				<td>仓库名称:</td>
    				<td><input id="report-storageSummary-storageid" name="storageid" style="width:205px"/></td>
    			</tr>
    			<tr>
    				<td>供 应 商:</td>
					<td><input id="report-storageSummary-supplierid" name="supplierid" style="width:150px"/></td>
    				<td>品牌名称:</td>
    				<td><input id="report-storageSummary-brandid" name="brandid" style="width:150px"/></td>
    				<td>部门名称:</td>
					<td><input id="report-storageSummary-branddept" name="branddept" style="width:205px"/></td>
    			</tr>
    			<tr>
    				<td colspan="4">
   						<input type="checkbox" class="groupcols checkbox1" value="storageid" id="storageid"/>
                        <label class="divtext" for="storageid">仓库</label>
    					<input type="checkbox" class="groupcols checkbox1" value="goodsid" id="goodsid"/>
                        <label class="divtext" for="goodsid">商品</label>
    					<input type="checkbox" class="groupcols checkbox1" value="brandid" id="brandid"/>
                        <label class="divtext" for="brandid">品牌</label>
    					<input type="checkbox" class="groupcols checkbox1" value="branddept" id="branddept"/>
                        <label class="divtext" for="branddept">品牌部门</label>
    					<input type="checkbox" class="groupcols checkbox1" value="supplierid" id="supplierid"/>
                        <label class="divtext" for="supplierid">供应商</label>
    					<input id="report-query-groupcols" type="hidden" name="groupcols"/>
    				</td>
                    <td></td>
    				<td>
    					<a href="javaScript:void(0);" id="report-storageSummary-queay-queryList" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-storageSummary-queay-reloadList" class="button-qr">重置</a>

					</td>
    			</tr>
    		</table>
		</form>
    </div>
    <div id="storageSummaryLog-div"></div>
    <script type="text/javascript">
    	var storageSummaryLogJson = null;
    	$(function(){
    		//根据初始的列与用户保存的列生成以及字段权限生成新的列
			var tableColJson = $("#report-storageSummary-tree-table").createGridColumnLoad({
				name :'storage_summary',
				frozenCol : [[]],
				commonCol : [[ 
								{field:'storageid',title:'仓库',width:80,
									formatter:function(value,rowData,rowIndex){
						        		return rowData.storagename;
						        	}
								},
						        {field:'goodsid',title:'商品编码',sortable:true,width:60},
						        {field:'goodsname',title:'商品名称',width:220,isShow:true},
						        {field:'spell',title:'助记符',width:60,isShow:true},   
						        {field:'barcode',title:'条形码',/*sortable:true,*/width:90,isShow:true},
								{field:'brandid',title:'品牌名称',width:80,isShow:true,isShow:true,
						        	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
						        	}
						        },
						        {field:'branddept',title:'品牌部门',width:80,isShow:true,isShow:true,
						        	formatter:function(value,rowData,rowIndex){
						        		return rowData.branddeptname;
						        	}
						        },
						        {field:'supplierid',title:'供应商编号',width:65,isShow:true},
						        {field:'suppliername',title:'供应商名称',width:150,isShow:true},
						        {field:'price',title:'单价',width:60,align:'right',aliascol:'amount',
						        	formatter:function(value,rowData,rowIndex){
						        		if(null != value && "" != value){
						        			return formatterMoney(value);
						        		}
						        	}
						        },
						        {field:'existingnum',title:'现存量',resizable:true,width:60,align:'right',
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterBigNumNoLen(value);
						        	}
						        },
						        {field:'auxexistingdetail',title:'现存箱数',resizable:true,width:80,aliascol:'existingnum',align:'right'},
						        {field:'existingamount',title:'现存金额',resizable:true,align:'right',aliascol:'amount',
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterMoney(value);
						        	}
						        },
						        {field:'usablenum',title:'可用量',resizable:true,width:60,align:'right',
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterBigNumNoLen(value);
						        	}
						        },
						        {field:'auxusabledetail',title:'可用箱数',resizable:true,width:80,aliascol:'usablenum',align:'right'},
						        {field:'usableamount',title:'可用金额',resizable:true,align:'right',aliascol:'amount',
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterMoney(value);
						        	}
						        }
						    ]]
			});
			
			$("#report-storageSummary-tree-table").datagrid({  
				authority:tableColJson,
	  	 		frozenColumns: tableColJson.frozen,
				columns:tableColJson.common,
			    fit:true,
			    rownumbers:true,
			    pagination: true,  
			    idField:'goodsid',
			    showFooter: true,
			    singleSelect:true,
			    pageSize:100,
			    toolbar:'#report-storageSummary-query-div',
                onSortColumn:function(sort, order){
                    var rows = $("#report-storageSummary-tree-table").datagrid("getRows");
                    var dataArr = [];
                    for(var i=0;i<rows.length;i++){
                        if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                            dataArr.push(rows[i]);
                        }
                    }
                    dataArr.sort(function(a,b){
                        if($.isNumeric(a[sort])){
                            if(order=="asc"){
                                return Number(a[sort])>Number(b[sort])?1:-1;
                            }else{
                                return Number(a[sort])<Number(b[sort])?1:-1;
                            }
                        }else{
                            if(order=="asc"){
                                return a[sort]>b[sort]?1:-1;
                            }else{
                                return a[sort]<b[sort]?1:-1;
                            }
                        }
                    });
                    $("#report-storageSummary-tree-table").datagrid("loadData",dataArr);
                    return false;
                }
			}).datagrid("columnMoving"); 
			//商品档案通用控件
    		$("#report-storageSummary-goodsid").widget({
				referwid:'RL_T_BASE_GOODS_INFO',
	    		width:150,
				singleSelect:false
    		}); 
    		$("#report-storageSummary-brandid").widget({
                width:150,
    			referwid:'RL_T_BASE_GOODS_BRAND',
    			singleSelect:false,
    			view:true
    		});
    		$("#report-storageSummary-storageid").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
    			singleSelect:true,
    			width:205,
    			view:true
    		});
    		$("#report-storageSummary-branddept").widget({
    			referwid:'RL_T_BASE_DEPARTMENT_BUYER',
    			singleSelect:true,
    			width:205,
    			view:true
    		});
    		//商品分类
    		$("#report-storageSummary-goodssort").widget({
    			referwid:'RL_T_BASE_GOODS_WARESCLASS',
    			singleSelect:false,
    			width:150,
    			view:true
    		});
    		$("#report-storageSummary-supplierid").supplierWidget();
    		//回车事件
			controlQueryAndResetByKey("report-storageSummary-queay-queryList","report-storageSummary-queay-reloadList");
    		
    		$("#report-storageSummary-queay-queryList").click(function(){
    			setColumn();
    			//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#report-storageSummary-form-query").serializeJSON();
	       		$("#report-storageSummary-tree-table").datagrid({
	      			url: 'report/storage/showStorageAmountReportListData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
    		});
    		$("#report-storageSummary-queay-reloadList").click(function(){
    			$("#report-storageSummary-goodsid").widget("clear"); 
    			$("#report-storageSummary-brandid").widget("clear");
    			$("#report-storageSummary-storageid").widget("clear");
    			$("#report-storageSummary-branddept").widget("clear");
    			$("#report-storageSummary-branddept").supplierWidget("clear");
    			$("#report-storageSummary-goodssort").widget('clear');
    			$("#report-query-groupcols").val("");
    			$("#report-storageSummary-form-query")[0].reset();
	       		$("#report-storageSummary-tree-table").datagrid('loadData',{total:0,rows:[],footer:[]});
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
            //导出
            $("#report-buttons-exportStorageReport").click(function(){
                //封装查询条件
                var objecr  = $("#report-storageSummary-tree-table").datagrid("options");
                var queryParam = objecr.queryParams;
                if(null != objecr.sortName && null != objecr.sortOrder ){
                    queryParam["sort"] = objecr.sortName;
                    queryParam["order"] = objecr.sortOrder;
                }
                var queryParam = JSON.stringify(queryParam);
                var url = "report/storage/exportStorageAmountReportListData.do";
                exportByAnalyse(queryParam,"综合库存统计表",'report-storageSummary-tree-table',url);
            });
    	});
    	var $datagrid = $("#report-storageSummary-tree-table");
    		function setColumn(){
    			var cols = $("#report-query-groupcols").val();
    			if(cols!=""){
    				$datagrid.datagrid('hideColumn', "storageid");
					$datagrid.datagrid('hideColumn', "goodsid");
					$datagrid.datagrid('hideColumn', "goodsname");
					$datagrid.datagrid('hideColumn', "barcode");
					$datagrid.datagrid('hideColumn', "spell");
					$datagrid.datagrid('hideColumn', "brandid");
					$datagrid.datagrid('hideColumn', "branddept");
					$datagrid.datagrid('hideColumn', "supplierid");
					$datagrid.datagrid('hideColumn', "suppliername");
				}
				else{
					$datagrid.datagrid('showColumn', "goodsid");
					$datagrid.datagrid('showColumn', "goodsname");
					$datagrid.datagrid('showColumn', "barcode");
					$datagrid.datagrid('showColumn', "spell");
					$datagrid.datagrid('showColumn', "brandid");
					$datagrid.datagrid('showColumn', "branddept");
					$datagrid.datagrid('showColumn', "supplierid");
					$datagrid.datagrid('showColumn', "suppliername");
				}
				var colarr = cols.split(",");
				for(var i=0;i<colarr.length;i++){
					var col = colarr[i];
					if(col=="storageid"){
						$datagrid.datagrid('showColumn', "storageid");
					}else if(col=="goodsid"){
						$datagrid.datagrid('showColumn', "goodsid");
						$datagrid.datagrid('showColumn', "goodsname");
						$datagrid.datagrid('showColumn', "barcode");
						$datagrid.datagrid('showColumn', "spell");
						$datagrid.datagrid('showColumn', "brandid");
						$datagrid.datagrid('showColumn', "branddept");
						$datagrid.datagrid('showColumn', "supplierid");
						$datagrid.datagrid('showColumn', "suppliername");
					}else if(col=="brandid"){
						$datagrid.datagrid('showColumn', "brandid");
						$datagrid.datagrid('showColumn', "branddept");
						$datagrid.datagrid('showColumn', "supplierid");
						$datagrid.datagrid('showColumn', "suppliername");
					}else if(col=="supplierid"){
						$datagrid.datagrid('showColumn', "supplierid");
						$datagrid.datagrid('showColumn', "suppliername");
					}else if(col=="branddept"){
						$datagrid.datagrid('showColumn', "branddept");
					}
				}
				var fields = $datagrid.datagrid('getColumnFields');
				var isHasField = false;
				for ( var i = 0; i < fields.length; i++) {
					if(fields[i]=='price'){
						isHasField = true;
						break;
					}
				}
				if(isHasField){
					if(cols.indexOf("goodsid") != -1){
						$datagrid.datagrid('showColumn', "price");
					}else{
						$datagrid.datagrid('hideColumn', "price");
					}
				}
    		}
    </script>
  </body>
</html>

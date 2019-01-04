<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>盈亏报表</title>
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
    <table id="report-adjustment-report-table"></table>
    <div id="report-adjustment-report-query-div" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/storage/exportAdjustmentReportData.do">
                <a href="javaScript:void(0);" id="report-buttons-exportStorageReport" class="easyui-linkbutton" iconCls="button-export" plain="true">全局导出</a>
            </security:authorize>
        </div>
    	<form action="" id="report-adjustment-report-form-query">
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td colspan="3"><input id="report-query-businessdate1" type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 
    					到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>仓库名称:</td>
	   				<td><input id="report-adjustment-report-storageid" name="storageid" style="width:120px"/></td>
					<td>品牌名称:</td>
					<td><input id="report-adjustment-report-brandid" name="brandid"/></td>
    			</tr>
				<tr>
					<td>商品名称:</td>
   					<td colspan="3"><input id="report-adjustment-report-goodsid" name="goodsid"/></td>
   					<td>单据类型:</td>
					<td colspan="3">
						<input type="checkbox" class="billtype checkbox1" value="1" id="billtype1"/>
						<label for="billtype1" class="divtext">报溢调账单</label>
						<input type="checkbox" class="billtype checkbox1" value="2" id="billtype2"/>
						<label for="billtype2" class="divtext">报损调账单</label>
						<input type="checkbox" class="billtype checkbox1" value="3" id="billtype3"/>
						<label for="billtype3" class="divtext">其他出库</label>
						<input type="checkbox" class="billtype checkbox1" value="4" id="billtype4"/>
						<label for="billtype4" class="divtext">其他入库</label>
						<input id="report-query-billtype" type="hidden" name="billtype"/>
					</td>
				</tr>
    			<tr>
    				<td>小计列:</td>
 					<td colspan="3">
   						<input type="checkbox" class="groupcols checkbox1" value="storageid" id="storageid"/>
                        <label for="storageid" class="divtext">仓库</label>
    					<input type="checkbox" class="groupcols checkbox1" value="goodsid" id="goodsid"/>
                        <label for="goodsid" class="divtext">商品</label>
                        <input type="checkbox" class="groupcols checkbox1" value="brandid" id="brandid"/>
                        <label for="brandid" class="divtext">品牌</label>
                        
    					<input id="report-query-groupcols" type="hidden" name="groupcols"/>
    				</td>
					<td colspan="2"></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="report-adjustment-report-queay-queryList" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="report-adjustment-report-queay-reloadList" class="button-qr">重置</a>
                    </td>
    			</tr>

    		</table>
		</form>
    </div>
    <div id="adjustment-reportLog-div"></div>
    <script type="text/javascript">
    	$(function(){
    		//根据初始的列与用户保存的列生成以及字段权限生成新的列
			var tableColJson = $("#report-adjustment-report-table").createGridColumnLoad({
				frozenCol : [[]],
				commonCol : [[ 
								{field:'storageid',title:'仓库',width:80,sortable:true,
									formatter:function(value,rowData,rowIndex){
						        		return rowData.storagename;
						        	}
								},
						        {field:'goodsid',title:'商品编码',sortable:true,width:60},
						        {field:'goodsname',title:'商品名称',width:220,isShow:true},
						        {field:'spell',title:'助记符',width:60,isShow:true},   
						        {field:'barcode',title:'条形码',width:90,isShow:true},  
								{field:'brandid',title:'品牌名称',width:80,isShow:true,isShow:true,
						        	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
						        	}
						        },
						        {field:'adjustnum',title:'盈亏数量',resizable:true,align:'right',sortable:true,
						        	formatter:function(value,rowData,rowIndex){
                                        if(value!=null &&value!=""){
                                            return Number(value);
                                        }else{
                                            return "";
                                        }
						        	}
						        },
						        {field:'amount',title:'盈亏金额',resizable:true,align:'right',sortable:true,
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterMoney(value);
						        	}
						        },
						        {field:'costamount',title:'盈亏成本金额',resizable:true,align:'right',sortable:true,
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterMoney(value);
						        	}
						        },
						        {field:'adjustnumdetail',title:'盈亏辅数量',resizable:true,align:'right'}
						    ]]
			});
			
			$("#report-adjustment-report-table").datagrid({  
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
			    toolbar:'#report-adjustment-report-query-div'
			}).datagrid("columnMoving"); 
			//商品档案通用控件
    		$("#report-adjustment-report-goodsid").widget({
				referwid:'RL_T_BASE_GOODS_INFO',
	    		width:225,
				singleSelect:false
    		}); 
    		$("#report-adjustment-report-brandid").widget({
    			referwid:'RL_T_BASE_GOODS_BRAND',
    			width:150,
    			singleSelect:false,
    			view:true
    		});
    		$("#report-adjustment-report-storageid").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
    			singleSelect:true,
    			width:150,
    			view:true
    		});
    		
    		$("#report-adjustment-report-queay-queryList").click(function(){
    			setColumn();
    			//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#report-adjustment-report-form-query").serializeJSON();
	       		$("#report-adjustment-report-table").datagrid({
	      			url: 'report/storage/getAdjustmentReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
    		});
    		$("#report-adjustment-report-queay-reloadList").click(function(){
    			$("#report-adjustment-report-goodsid").widget("clear"); 
    			$("#report-adjustment-report-brandid").widget("clear");
    			$("#report-adjustment-report-storageid").widget("clear");
                $("#report-query-billtype").val("");
    			$("#report-query-groupcols").val("");
    			$("#report-adjustment-report-form-query")[0].reset();
	       		$("#report-adjustment-report-table").datagrid('loadData',{total:0,rows:[],footer:[]});
    		});

            $(".billtype").click(function(){
                var cols = "";
                $("#report-query-billtype").val(cols);
                $(".billtype").each(function(){
                    if($(this).attr("checked")){
                        if(cols==""){
                            cols = $(this).val();
                        }else{
                            cols += ","+$(this).val();
                        }
                        $("#report-query-billtype").val(cols);
                    }
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
            //导出
            $("#report-buttons-exportStorageReport").click(function(){
                //封装查询条件
                var objecr  = $("#report-adjustment-report-table").datagrid("options");
                var queryParam = objecr.queryParams;
                if(null != objecr.sortName && null != objecr.sortOrder ){
                    queryParam["sort"] = objecr.sortName;
                    queryParam["order"] = objecr.sortOrder;
                }
                var queryParam = JSON.stringify(queryParam);
                var url = "report/storage/exportAdjustmentReportData.do";
                exportByAnalyse(queryParam,"仓库盈亏统计表",'report-adjustment-report-table',url);
            });

    	});
    	var $datagrid = $("#report-adjustment-report-table");
   		function setColumn(){
   			var cols = $("#report-query-groupcols").val();
   			if(cols!=""){
   				$datagrid.datagrid('hideColumn', "storageid");
				$datagrid.datagrid('hideColumn', "goodsid");
				$datagrid.datagrid('hideColumn', "goodsname");
				$datagrid.datagrid('hideColumn', "spell");
				$datagrid.datagrid('hideColumn', "barcode");
				$datagrid.datagrid('hideColumn', "brandid");
			}
			else{
				$datagrid.datagrid('showColumn', "goodsid");
				$datagrid.datagrid('showColumn', "goodsname");
				$datagrid.datagrid('showColumn', "barcode");
				$datagrid.datagrid('showColumn', "spell");
				$datagrid.datagrid('showColumn', "brandid");
			}
			var colarr = cols.split(",");
			console.log(colarr)
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
				}else if(col=="brandid"){
					$datagrid.datagrid('showColumn', "brandid");
				}
			}
   		}
    </script>
  </body>
</html>

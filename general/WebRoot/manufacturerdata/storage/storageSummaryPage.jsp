<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>厂方数据对接库存明细</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'north',border:false">
        <div class="buttonBG">
            <security:authorize url="/manufacturerdata/exportSummaryByStorageData.do">
                <a href="javaScript:void(0);" id="storageSummary-export-queryList" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
            </security:authorize>
        </div>
      </div>
      <div data-options="region:'center'">
          <table id="data-storageSummary-table"></table>
          <div id="storageSummary-query-div" style="padding:2px;height:auto">
              <form action="" id="storageSummary-form-query">
                  <table class="querytable">
                      <tr>
                          <td>商品名称:</td>
                          <td><input type="text" id="storageSummary-goodsid" name="goodsid" style="width:200px"/></td>
                          <td>商品分类:</td>
                          <td><input type="text" id="storageSummary-goodssort" name="goodssort" style="width:150px"/></td>
                          <td colspan="2">
                              <input type="checkbox" class="checkbox1" name="existingnum" value="existingnum" id="existingnum" checked="checked"/>
                              <label class="divtext" for="existingnum" style="font-size: 13px">剔除库存为0的商品显示</label>
                          </td>
                      </tr>
                      <tr>
                          <td>供应商:</td>
                          <td><input id="storageSummaryByStorage-supplierid" name="supplierid" style="width:200px"/></td>
                          <td>品牌名称:</td>
                          <td><input type="text" id="storageSummary-brandid" name="brandid" style="width:150px"/></td>
                          <td>对接名称:</td>
                          <td><input id="storageSummaryByStorage-markid" name="markid" style="width:150px"/></td>
                      </tr>
                      <tr>
                          <td>小计列:</td>
                          <td colspan="3">
                              <input type="checkbox" class="groupcols checkbox1" name="groupcols" value="markid" checked="checked" id="markid"/>
                              <label class="divtext" for="markid">对接方</label>
                              <input type="checkbox" class="groupcols checkbox1" name="groupcols" value="goodsid" checked="checked" id="goodsid"/>
                              <label class="divtext" for="goodsid">商品</label>
                              <input type="checkbox" class="groupcols checkbox1" name="groupcols" value="brandid" id="brandid"/>
                              <label class="divtext" for="brandid">品牌</label>
                              <input type="checkbox" class="groupcols checkbox1" name="groupcols" value="supplierid" id="supplierid"/>
                              <label class="divtext" for="supplierid">供应商</label>
                              <input id="storageSummaryByStorage-storageid-checkbox" type="checkbox" class="groupcols checkbox1" name="groupcols" value="storageid" id="storageid"/>
                              <label class="divtext" for="storageid">虚拟仓库</label>
                          </td>
                          <td colspan="2">
                              <a href="javaScript:void(0);" id="storageSummary-queay-queryList" class="button-qr">查询</a>
                              <a href="javaScript:void(0);" id="storageSummary-queay-reloadList" class="button-qr">重置</a>
                          </td>
                      </tr>
                  </table>
              </form>
          </div>
          <div id="storageSummaryLog-div"></div>
          <div id="storageSummary-waitnum-div"></div>
      </div>
  </div>
  <script type="text/javascript">
    	var initQueryJSON = $("#storageSummary-form-query").serializeJSON();
        var SMR_footerobject = null;
        var loadData = false;
    	$(function(){
    		//根据初始的列与用户保存的列生成以及字段权限生成新的列
			var tableColJson = $("#data-storageSummary-table").createGridColumnLoad({
				frozenCol : [[]],
				commonCol : [[
								{field:'idok',checkbox:true,isShow:true},
                                {field:'markid',title:'对接名称',width:80,sortable:true,
                                    formatter:function(value,rowData,rowIndex){
                                        return rowData.markname;
                                    }
                                },
								{field:'storageid',title:'所属仓库',width:80,sortable:true,
								    formatter:function(value,rowData,rowIndex){
								        return rowData.storagename;
								    }
								},

								{field:'goodsid',title:'商品编码',width:60,sortable:true},
								{field:'goodsname',title:'商品名称',width:220},
								{field:'spell',title:'助记符',width:60},
								{field:'barcode',title:'条形码',width:85,sortable:true},
                                {field:'waresclassname',title:'商品分类',width:120,sortable:true},
								{field:'brandid',title:'商品品牌',width:80,sortable:true,
									formatter:function(value,rowData,rowIndex){
				        				return rowData.brandname;
						        	}
								},
                                {field:'supplierid',title:'供应商',width:120,sortable:true,hidden:true,
                                    formatter:function(value,rowData,rowIndex){
                                        return rowData.suppliername;
                                    }
                                },
								{field:'model',title:'规格型号',width:100,hidden:true},
						        {field:'boxnum',title:'箱装量',width:45,align:'right',
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterBigNumNoLen(value);
						        	}
						        },
						        {field:'unitid',title:'单位',width:35,
						        	formatter:function(value,rowData,rowIndex){
				        				return rowData.unitname;
						        	}
						        },
						        {field:'price',title:'单价',width:50,align:'right',sortable:true,
						        	formatter:function(value,rowData,rowIndex){
                                        if(value != "" && null != value){
                                            return formatterMoney(value);
                                        }
						        	}
						        },
						        {field:'existingnum',title:'现存量',resizable:true,align:'right',sortable:true,
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterBigNumNoLen(value);
						        	}
						        },
						        {field:'auxexistingdetail',title:'现存箱数',resizable:true,align:'right'},
						        {field:'existingamount',title:'现存金额',resizable:true,align:'right',sortable:true,
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterMoney(value);
						        	}
						        }
						    ]]
			});
			
			$("#data-storageSummary-table").datagrid({
				authority:tableColJson,
	  	 		frozenColumns: tableColJson.frozen,
				columns:tableColJson.common,
			    fit:true,
			    rownumbers:true,
			    pagination: true,  
			    pageSize:100,
			    idField:'id',
                singleSelect:false,
                checkOnSelect:true,
                selectOnCheck:true,
			    showFooter:true,
			    toolbar:'#storageSummary-query-div',
                url: 'manufacturerdata/showStorageSummaryList.do',
                queryParams:initQueryJSON,
                onBeforeLoad:function(){
                    $(this).datagrid('clearChecked');
                    $(this).datagrid('clearSelections');
                    return loadData;
                },
                onDblClickRow:function(rowIndex, rowData){
                	var goodsid = rowData.goodsid;
                    var markid = rowData.markid;
                    var gflag = false;
                    $(".groupcols").each(function(){
                        if($(this).attr("checked")){
                            var val = $(this).val();
                            if(val=="goodsid"){
                                gflag = true;
                            }
                        }
                    });
                    if(gflag){
                        var url = "manufacturerdata/showDataStorageSummaryLogPage.do?goodsid="+goodsid+"&markid="+markid;
                        $('#storageSummaryLog-div').dialog({
                            title: '商品库存量追踪日志',
                            width:800,
                            height:400,
                            collapsible:false,
                            minimizable:false,
                            maximizable:true,
                            resizable:true,
                            closed: true,
                            cache: false,
                            maximized:true,
                            href: url,
                            modal: true
                        });
                        $('#storageSummaryLog-div').dialog("open");
                    }
                },
                onLoadSuccess:function(){
                    var footerrows = $(this).datagrid('getFooterRows');
                    if(null!=footerrows && footerrows.length>0){
                        SMR_footerobject = footerrows[0];
                        checkTotalAmount();
                    }
                },
                onCheckAll:function(){
                    checkTotalAmount();
                },
                onUncheckAll:function(){
                    checkTotalAmount();
                },
                onCheck:function(){
                    checkTotalAmount();
                },
                onUncheck:function(){
                    checkTotalAmount();
                }
			});
            $("#storageSummaryByStorage-markid").widget({
                referwid:'RL_T_BASE_DATACONFIG_INFO',
                singleSelect:true,
                width:130,
                view:true
            });
            $("#storageSummaryByStorage-supplierid").widget({
                referwid:'RL_T_BASE_BUY_SUPPLIER',
                width:200,
                singleSelect:false
            });
			//商品档案通用控件
    		$("#storageSummary-goodsid").widget({
                referwid:'RL_T_BASE_GOODS_INFO',
                singleSelect:false,
                width:200
            });
    		$("#storageSummary-brandid").widget({
				referwid:'RL_T_BASE_GOODS_BRAND',
    			singleSelect:false,
                width:150,
    			view:true
    		});
    		//商品分类
    		$("#storageSummary-goodssort").widget({
    			referwid:'RL_T_BASE_GOODS_WARESCLASS',
    			singleSelect:false,
                width:150,
    			view:true,
                onlyLeafCheck: false
    		});

            $("#storageSummary-export-queryList").click(function(){
                //封装查询条件
                var objecr  = $("#data-storageSummary-table").datagrid("options");
                var queryParam =  $("#storageSummary-form-query").serializeJSON();
                if(null != objecr.sortName && null != objecr.sortOrder ){
                    queryParam["sort"] = objecr.sortName;
                    queryParam["order"] = objecr.sortOrder;
                }
                var queryParam = JSON.stringify(queryParam);
                var url = "manufacturerdata/exportSummaryByStorageData.do";
                exportByAnalyse(queryParam,"库存数据","data-storageSummary-table",url);
            });

    		$("#storageSummary-queay-queryList").click(function(){
    			//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#storageSummary-form-query").serializeJSON();
	       		//把form表单的name序列化成JSON对象
	      		$("#data-storageSummary-table").datagrid({
	      			url: 'manufacturerdata/showStorageSummaryList.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		});
                reloadColumn();
    		});
    		$("#storageSummary-queay-reloadList").click(function(){
    			$("#storageSummary-goodsid").widget("clear");
    			$("#storageSummary-brandid").widget("clear");
    			$("#storageSummary-goodssort").widget("clear");
                $("#storageSummaryByStorage-storageid").widget("clear");
                $("#storageSummaryByStorage-supplierid").widget("clear");
    			$("#storageSummary-form-query")[0].reset();
	       		$("#data-storageSummary-table").datagrid('loadData',{total:0,rows:[],footer:[]});

                $("#data-storageSummary-table").datagrid('hideColumn', "storageid");
    		});

    	});

        function checkTotalAmount(){
            var rows =  $("#data-storageSummary-table").datagrid('getChecked');
            var existingnum = 0,existingauxint = 0,existingauxnum = 0,auxexistingdetail = '',existingamount = 0,
                    usablenum = 0,usableauxint = 0,usableauxnum = 0,auxusabledetail = '',usableamount = 0;
            for(var i=0;i<rows.length;i++){
                existingnum = Number(existingnum)+Number(rows[i].existingnum == undefined ? 0 : rows[i].existingnum);
                existingauxint = Number(existingauxint)+Number(rows[i].existingauxint == undefined ? 0 : rows[i].existingauxint);
                existingauxnum = Number(existingauxnum)+Number(rows[i].existingauxnum == undefined ? 0 : rows[i].existingauxnum);
                existingamount = Number(existingamount)+Number(rows[i].existingamount == undefined ? 0 : rows[i].existingamount);

                usablenum = Number(usablenum)+Number(rows[i].usablenum == undefined ? 0 : rows[i].usablenum);
                usableauxint = Number(usableauxint)+Number(rows[i].usableauxint == undefined ? 0 : rows[i].usableauxint);
                usableauxnum = Number(usableauxnum)+Number(rows[i].usableauxnum == undefined ? 0 : rows[i].usableauxnum);
                usableamount = Number(usableamount)+Number(rows[i].usableamount == undefined ? 0 : rows[i].usableamount);

            }
            auxexistingdetail = existingauxint + '箱' + existingauxnum;
            auxusabledetail = usableauxint + '箱' + usableauxnum;
            var foot=[{goodsname:'选中合计',existingnum:existingnum,auxexistingdetail:auxexistingdetail,existingamount:existingamount,usablenum:usablenum,auxusabledetail:auxusabledetail,usableamount:usableamount }];
            if(null!=SMR_footerobject){
                foot.push(SMR_footerobject);
            }
            $("#data-storageSummary-table").datagrid("reloadFooter",foot);
        }

        //重置字段列
        function reloadColumn(){
            var flag = false;
            var groupCol = "";
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    var val = $(this).val();
                    groupCol += val+",";
                }
            });
            if(groupCol.indexOf("markid")>=0){
                $("#data-storageSummary-table").datagrid('showColumn', "markid");
            }else{
                $("#data-storageSummary-table").datagrid('hideColumn', "markid");
            }
            if(groupCol.indexOf("brandid")>=0){
                $("#data-storageSummary-table").datagrid('showColumn', "brandid");
            }else{
                $("#data-storageSummary-table").datagrid('hideColumn', "brandid");
            }

            if(groupCol.indexOf("goodsid")>=0){
                $("#data-storageSummary-table").datagrid('showColumn', "goodsid");
                $("#data-storageSummary-table").datagrid('showColumn', "goodsname");
                $("#data-storageSummary-table").datagrid('showColumn', "spell");
                $("#data-storageSummary-table").datagrid('showColumn', "barcode");
                $("#data-storageSummary-table").datagrid('showColumn', "waresclassname");
                $("#data-storageSummary-table").datagrid('showColumn', "boxnum");
                $("#data-storageSummary-table").datagrid('showColumn', "supplierid");
                $("#data-storageSummary-table").datagrid('showColumn', "unitid");
                $("#data-storageSummary-table").datagrid('showColumn', "price");
            }else{
                $("#data-storageSummary-table").datagrid('hideColumn', "goodsid");
                $("#data-storageSummary-table").datagrid('hideColumn', "goodsname");
                $("#data-storageSummary-table").datagrid('hideColumn', "spell");
                $("#data-storageSummary-table").datagrid('hideColumn', "barcode");
                $("#data-storageSummary-table").datagrid('hideColumn', "waresclassname");
                $("#data-storageSummary-table").datagrid('hideColumn', "boxnum");
                $("#data-storageSummary-table").datagrid('hideColumn', "unitid");
                $("#data-storageSummary-table").datagrid('hideColumn', "price");

                if(groupCol.indexOf("supplierid")>=0){
                    $("#data-storageSummary-table").datagrid('showColumn', "supplierid");
                }else{
                    $("#data-storageSummary-table").datagrid('hideColumn', "supplierid");
                }
            }
            if(groupCol.indexOf("storageid")>=0){
                $("#data-storageSummary-table").datagrid('showColumn', "storageid");
            }else{
                $("#data-storageSummary-table").datagrid('hideColumn', "storageid");
            }
        }
        setTimeout(function(){
            loadData = true;
        },100);
    </script>
  </body>
</html>

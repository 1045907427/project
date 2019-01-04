<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>销售区域报表</title>
	<%@include file="/include.jsp" %>
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
<div id="storage-query-reportList" style="padding:2px;height:auto">
	<div class="buttonBG">
		<security:authorize url="/journalsheet/deptdailycost/exportDeptDailyCostReportDataBtn.do">
			<a href="javaScript:void(0);" id="storage-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
			<a href="javaScript:void(0);" id="storage-export-excel" style="display:none">导出</a>
		</security:authorize>
	</div>
	<form action="" id="storage-query-form-reportList" method="post">
		<table>
			<tr>
			<tr>
				<td>商品名称:</td>
				<td><input type="text" id="storage-query-goodsid" name="goodsid" style="width:200px"/></td>
				<td>商品分类:</td>
				<td><input type="text" id="storage-query-goodssort" name="goodssort" style="width:150px"/></td>
				<td colspan="2">
					<input type="checkbox" class="groupcols checkbox1" name="existingnum" value="existingnum"
						   id="existingnum" checked="checked"/>
					<label class="divtext" for="existingnum" style="font-size: 13px">剔除库存为0的商品显示</label>
				</td>
			</tr>
			<tr>
				<td>供应商:</td>
				<td><input id="storage-query-supplierid" name="supplierid" style="width:200px"/></td>
				<td>品牌名称:</td>
				<td><input type="text" id="storage-query-brandid" name="brandid" style="width:150px"/></td>
				<td>状态：</td>
				<td>
					<select name="state" style="width:150px">
						<option></option>
						<option value="0">禁用</option>
						<option value="1">启用</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>所属仓库:</td>
				<td><input id="storage-query-storageid" name="storageid" style="width:200px"/></td>
				<td>购销类型:</td>
				<td>
					<select name="bstype" style="width: 150px;" >
						<option selected></option>
						<c:forEach items="${bstypeList }" var="list">
							<option value="${list.code }">${list.codename }</option>
						</c:forEach>
					</select>
				</td>
				<td>汇总类型:</td>
				<td>
					<select name="treetype" style="width: 150px;" >
						<option value="allstorage" selected="selected">总仓库-分仓库-品牌-商品</option>
						<option value="storage">仓库-品牌-商品</option>
						<option value="supplier">供应商-品牌-商品</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="4">
				</td>
				<td class="tdbutton" colspan="2">
					<a href="javaScript:void(0);" id="storage-query-buton" class="button-qr">查询</a>
					<a href="javaScript:void(0);" id="storage-reload-buton" class="button-qr">重置</a>
				</td>
			</tr>

		</table>
	</form>
</div>
<table id="storage-table-reportList"></table>
<div class="easyui-menu" id="storage-contextMenu" style="display: none;">
	<div id="storage-contextMenu-export">导出</div>
	<div id="storage-contextMenu-expand">展开</div>
	<div id="storage-contextMenu-collapse">折叠</div>
</div>
<div style="display:none">
	<div id="storage-reportDetailList-div"></div>
</div>
<script type="text/javascript">
    var footerobject=null;

    var brandid ="";
    var bstype = "";
    var existingnum = "existingnum";
    var goodsid = "";
    var goodssort = "";
    var state= "";
    var storageid="";
    var supplierid="";
    var treetype="allstorage";


    $(function(){
        var queryInitJSON = $("#storage-query-form-reportList").serializeJSON();
        $("#storage-table-reportList").treegrid({
            columns:[[
                {field: 'name', title: '名称', width: 350, sortable: true},
                {field: 'goodsid', title: '商品编码', width: 80, aliascol: 'goodsid'},
                {field: 'spell', title: '助记符', width: 60, aliascol: 'goodsid'},
                {field: 'barcode', title: '条形码', width: 85, sortable: true, aliascol: 'goodsid'},
                {field: 'waresclassname', title: '商品分类', width: 120, aliascol: 'goodsid', sortable: true},
                {
                    field: 'brandid', title: '商品品牌', width: 80, aliascol: 'goodsid', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.brandname;
                    }
                },
                {field: 'model', title: '规格型号', width: 100, aliascol: 'goodsid', hidden: true},
                {
                    field: 'boxnum', title: '箱装量', width: 45, align: 'right', aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'unitid', title: '单位', width: 35,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.unitname;
                    }
                },
                <security:authorize url="/storage/showStorageSummaryAmount.do">
                {
                    field: 'price', title: '单价', width: 50, align: 'right', sortable: true, aliascol: 'amount',
                    formatter: function (value, rowData, rowIndex) {
                        if (value != "" && null != value) {
                            return formatterMoney(value);
                        }
                    }
                },
                {
                    field: 'basesaleprice', title: '基准价', width: 50, align: 'right', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value != "" && null != value) {
                            return formatterMoney(value);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/showStorageSummaryCostAmountView.do">
                {
                    field: 'costprice', title: '成本价', width: 50, align: 'right', sortable: true, aliascol: 'amount',
                    formatter: function (value, rowData, rowIndex) {
                        if (value != "" && null != value) {
                            return formatterMoney(value);
                        }
                    }
                },
                {
                    field: 'storageamount',
                    title: '仓库未分摊金额',
                    width: 100,
                    align: 'right',
                    sortable: true,
                    aliascol: 'amount',
                    formatter: function (value, rowData, rowIndex) {
                        if (value != "" && null != value) {
                            return formatterMoney(value);
                        }
                    }
                },
                </security:authorize>
                {
                    field: 'existingnum', title: '现存量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {field: 'auxexistingnum', title: '现存辅数量', width: 80, align: 'right', aliascol: 'existingnum'},
                {field: 'auxexistingdetail', title: '现存箱数', width: 80, align: 'right', aliascol: 'existingnum'},
                <security:authorize url="/storage/showStorageSummaryAmount.do">
                {
                    field: 'existingamount',
                    title: '现存金额',
                    resizable: true,
                    align: 'right',
                    aliascol: 'amount',
                    sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'basesaleamount',
                    title: '基准金额',
                    resizable: true,
                    align: 'right',
                    aliascol: 'amount',
                    hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/showStorageSummaryCostAmountView.do">
                {
                    field: 'costamount',
                    title: '成本金额',
                    resizable: true,
                    align: 'right',
                    aliascol: 'amount',
                    sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </security:authorize>
                {
                    field: 'usablenum', title: '可用量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {field: 'auxusabledetail', title: '可用箱数', width: 80, align: 'right', aliascol: 'usablenum'},
                <security:authorize url="/storage/showStorageSummaryAmount.do">
                {
                    field: 'usableamount',
                    title: '可用金额',
                    resizable: true,
                    align: 'right',
                    aliascol: 'amount',
                    sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </security:authorize>
                {
                    field: 'waitnum', title: '待发量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
//                        if (value != 0 && rowData.goodsid != null && rowData.goodsid != "") {
//                            return '<a href="javascript:showGoodsWaitInfo(\'' + rowData.goodsid + '\',\'' + rowData.storageid + '\')">' + formatterBigNumNoLen(value) + '</a>';
//                        } else {
                            return formatterBigNumNoLen(value);
//                        }
                    }
                },
                {
                    field: 'auxwaitdetail', title: '待发箱数', width: 80, align: 'right', aliascol: 'waitnum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.waitnum != 0) {
                            return value;
                        }
                    }
                },
                <security:authorize url="/storage/showStorageSummaryAmount.do">
                {
                    field: 'waitamount',
                    title: '待发金额',
                    resizable: true,
                    align: 'right',
                    aliascol: 'amount',
                    sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </security:authorize>
                {
                    field: 'transitnum', title: '在途量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'auxtransitdetail', title: '在途箱数', width: 80, align: 'right', aliascol: 'transitnum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.transitnum != 0) {
                            return value;
                        }
                    }
                },
                {
                    field: 'allotwaitnum', title: '调拨待发量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'auxallotwaitdetail', title: '调拨待发箱数', width: 80, align: 'right', aliascol: 'allotwaitnum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.allotwaitnum != 0) {
                            return value;
                        }
                    }
                },
                {
                    field: 'allotenternum', title: '调拨待入量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'auxallotenterdetail', title: '调拨待入箱数', width: 80, align: 'right', aliascol: 'allotenternum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.allotenternum != 0) {
                            return value;
                        }
                    }
                },
                {
                    field: 'projectedusablenum', title: '预计可用量', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'auxprojectedusabledetail',
                    title: '预计可用量箱数',
                    width: 80,
                    align: 'right',
                    aliascol: 'projectedusablenum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.projectedusablenum != 0) {
                            return value;
                        }
                    }
                },
                {
                    field: 'safenum', title: '安全库存', width: 60, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'auxsafedetail', title: '安全箱数', width: 80, align: 'right', aliascol: 'safenum',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.safenum != 0) {
                            return value;
                        }
                    }
                }

            ]],
            fit:true,
            title:"",
            method:'post',
            rownumbers:true,
            idField:'rid',
            treeField:'name',
            singleSelect:true,
            showFooter: true,
            url: 'report/storage/showStorageTreeReportListData.do',
            queryParams:queryInitJSON,
            toolbar:'#storage-query-reportList',
            onDblClickRow:function(row){
                $("#storage-table-reportList").treegrid("expand",row.rid);
                if(row.datatype=="brandid"){
					loadGoods(row.data,row.pid);
				}
            },
            onContextMenu: function(e, rowData){
                e.preventDefault();
                $(this).treegrid('select', rowData.rid);
                $("#storage-contextMenu").menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            },
        });

        $("#storage-query-storageid").widget({
            name: 't_storage_summary',
            col: 'storageid',
            singleSelect: false,
            width: 200,
            view: true
        });
        $("#storage-query-supplierid").widget({
            referwid: 'RL_T_BASE_BUY_SUPPLIER',
            width: 200,
            singleSelect: false
        });
        //商品档案通用控件
        $("#storage-query-goodsid").widget({
            referwid: 'RL_T_BASE_GOODS_INFO',
            singleSelect: false,
            width: 200
        });
        $("#storage-query-brandid").widget({
            referwid: 'RL_T_BASE_GOODS_BRAND',
            singleSelect: false,
            width: 150,
            view: true
        });
        //商品分类
        $("#storage-query-goodssort").widget({
            referwid: 'RL_T_BASE_GOODS_WARESCLASS',
            singleSelect: false,
            width: 150,
            view: true,
            onlyLeafCheck: false
        });

        function loadGoods(id,pid) {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storage-query-form-reportList").serializeJSON();
            queryJSON.brandid=brandid;
            queryJSON.bstype=bstype;
            queryJSON.existingnum=existingnum;
            queryJSON.goodsid=goodsid;
            queryJSON.goodssort=goodssort;
            queryJSON.state=state;
            queryJSON.storageid=storageid;
            queryJSON.supplierid=supplierid;
            queryJSON.treetype=treetype;
            queryJSON.expandid=id;
            queryJSON.expandpid=pid;
            queryJSON.isexpand="isexpand";
            $("#storage-table-reportList").treegrid('load', queryJSON);
        }

        $("#storage-query-buton").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storage-query-form-reportList").serializeJSON();
            brandid =queryJSON.brandid;
            bstype = queryJSON.bstype;
            existingnum = queryJSON.existingnum;
            goodsid = queryJSON.goodsid;
            goodssort = queryJSON.goodssort;
            state= queryJSON.state;
            storageid=queryJSON.storageid;
            supplierid=queryJSON.supplierid;
            treetype=queryJSON.treetype;
            $("#storage-table-reportList").treegrid('load', queryJSON);
        });
        $("#storage-reload-buton").click(function(){
            brandid ="";
            bstype = "";
            existingnum = "";
            goodsid = "";
            goodssort = "";
            state= "";
            storageid="";
            supplierid="";
            treetype="";
            $("#storage-query-form-reportList")[0].reset();
            $("#storage-table-reportList").treegrid('loadData',[]);
            $("#storage-query-salesarea").widget('clear');
        });
        $("#storage-contextMenu-export").click(function(){
            var row = $("#storage-table-reportList").treegrid('getSelected');
            var salesarea = row.id;
            var date1=$("#storage-query-businessdate1").val()||"";
            var date2=$("#storage-query-businessdate2").val()||"";
            var salesareaname = row.salesareaname ;
            var title="";
            title=date1+(date1!=""&&date2!=""?"至":"")+date2;
            title=title+$.trim(salesareaname)+"库存树状报表";
            $("#storage-export-excel").Excel('export',{
                queryForm: "#storage-query-form-reportList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                type:'exportUserdefined',
                name:title,
                url:'journalsheet/salesarea/exportSalesAreaReportData.do?id='+salesarea
            });
            $("#storage-export-excel").trigger("click");
        });
        $("#storage-contextMenu-expand").click(function(){
            var row = $("#storage-table-reportList").treegrid('getSelected');
            $("#storage-table-reportList").treegrid("expandAll",row.rid);
        });
        $("#storage-contextMenu-collapse").click(function(){
            var row = $("#storage-table-reportList").treegrid('getSelected');
            $("#storage-table-reportList").treegrid("collapseAll",row.rid);
        });
        $("#storage-export-buton").click(function(){
            var date1=$("#storage-query-businessdate1").val()||"";
            var date2=$("#storage-query-businessdate2").val()||"";
            var salesareaname=$("#storage-query-deptid").widget("getText")|| "" ;
            var title="";
            title=date1+(date1!=""&&date2!=""?"至":"")+date2;
            title=title+$.trim(salesareaname)+"库存树状报表";
            $("#storage-export-excel").Excel('export',{
                queryForm: "#storage-query-form-reportList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                type:'exportUserdefined',
                name:title,
                url:'journalsheet/salesarea/exportSalesAreaReportData.do'
            });
            $("#storage-export-excel").trigger("click");
        });
    });
</script>
</body>
</html>

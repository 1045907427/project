<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>供应商代垫应收明细</title>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<table id="report-datagrid-supplierMatcostsListPage"></table>
<div id="report-toolbar-supplierMatcostsListPage" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/account/receivable/receivablePastDueResonExport.do">
            <a href="javaScript:void(0);" id="report-export-salesuserRPDSalesFlow" class="easyui-linkbutton button-list" iconCls="button-export" plain="true">导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-supplierMatcostsListPage" method="post">
        <input type="hidden" name="supplierid" value="${supplierid }" />
        <input type="hidden" name="begintime" value="${param.businessdate1 }" />
        <input type="hidden" name="endtime" value="${param.businessdate2 }" />
        <input type="hidden" name="brandid" value="${param.brandid }" />
        <input type="hidden" name="subjectid" value="${param.subjectid }" />
        <input type="hidden" name="supplierdeptid" value="${param.supplierdeptid }" />
        <input type="hidden" name="ispastdue" value="${param.ispastdue }" />

        <input type="hidden" name="billtype" value="1" />
        <input id="report-query-groupcols" type="hidden" name="groupcols" value="supplierid"/>
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td class="tdinput" ><input id="report-query-begintime" type="text" name="begintime" style="width:125px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                    到 <input id="report-query-endtime" type="text" name="endtime" class="Wdate" style="width:125px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                <td rowspan="2" colspan="2" class="tdbutton">
                    <a href="javaScript:void(0);" id="report-queay-supplierMatcostsListPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-supplierMatcostsListPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#report-query-form-supplierMatcostsListPage").serializeJSON();
    var today='${today}';
    $(function(){
        //根据初始的列与用户保存的列生成以及字段权限生成新的列
        var tableColumnListJson = $("#report-datagrid-supplierMatcostsListPage").createGridColumnLoad({
            name: 't_js_matcostsinput',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编码', width: 125, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {field: 'paydate', title: '支付日期', width: 80, sortable: true},
                {field: 'takebackdate', title: '收回日期', width: 80, sortable: true},
                {
                    field: 'oaid', title: 'OA编号', width: 80, sortable: true,
                    formatter: function (value, row, index) {

                        if (value != undefined
                                && value != null
                                && value != ''
                                && value != '合计'
                                && value != '选中金额') {

                            return '<a href="javascript:void(0);" onclick="viewOa(\'' + value + '\')">' + value + '</a>';
                        }

                        return value;
                    }
                },
                {
                    field: 'brandid', title: '商品品牌', width: 60, sortable: true,
                    formatter: function (val, rowData, rowIndex) {
                        return rowData.brandname;
                    }
                },
                {field: 'supplierid', title: '供应商编码', width: 70, sortable: true},
                {field: 'suppliername', title: '供应商名称', width: 210, sortable: true, isShow: true},
                {
                    field: 'supplierdeptid', title: '所属部门', width: 70, sortable: true,
                    formatter: function (val, rowData, rowIndex) {
                        return rowData.supplierdeptname;
                    }
                },
                {
                    field: 'customerid', title: '客户名称', width: 130, sortable: true,
                    formatter: function (val, rowData, rowIndex) {
                        return rowData.customername;
                    }
                },
                {
                    field: 'customersort', title: '客户分类名称', width: 80, sortable: true, aliascol: 'customerid',
                    formatter: function (val, rowData, rowIndex) {
                        return rowData.customersortname;
                    }
                },
                {
                    field: 'transactorid', title: '经办人名称', width: 130, sortable: true,
                    formatter: function (val, rowData, rowIndex) {
                        return rowData.transactorname;
                    }
                },
                {
                    field: 'subjectid', title: '科目名称', width: 60, sortable: true,
                    formatter: function (val, rowData, rowIndex) {
                        return rowData.subjectname;
                    }
                },
                {
                    field: 'hcflag', title: '是否红冲', width: 60, sortable: true,
                    formatter: function (val, rowData, rowIndex) {
                        if (val == '0') {
                            return "否";
                        } else if (val == '1' || val == '2') {
                            return "是";
                        }
                    }
                },
                {
                    field: 'factoryamount', title: '工厂投入', resizable: true, sortable: true, align: 'right',
                    formatter: function (val, rowData, rowIndex) {
                        if (val != "" && val != null) {
                            return formatterMoney(val);
                        }
                        else {
                            return "0.00";
                        }
                    }
                },
                {
                    field: 'expense', title: '费用金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (val, rowData, rowIndex) {
                        return formatterMoney(val);
                    }
                },
                {
                    field: 'reimburseamount', title: '收回金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (val, rowData, rowIndex) {
                        if (rowData.writeoffamount != null && rowData.writeoffamount != "") {
                            return formatterMoney(rowData.writeoffamount);
                        }
                        else {
                            return "0.00";
                        }
                    }
                },
                {
                    field: 'actingmatamount', title: '代垫金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (val, rowData, rowIndex) {
                        if(val!=0){
                            if(rowData.duefromdate <today){
                                return "<span style='color:#f00;'>"+formatterMoney(val)+"</span>";
                            }
                        }
                        return formatterMoney(val);
                    }
                },
//                {
//                    field: 'iswriteoff', title: '核销状态', resizable: true, sortable: true, align: 'right',
//                    formatter: function (val, rowData, rowIndex) {
//                        if (val == "1") {
//                            return "核销";
//                        }
//                    }
//                },
//                {
//                    field: 'writeoffdate', title: '核销日期', resizable: true, sortable: true,
//                    formatter: function (val, rowData, rowIndex) {
//                        if (rowData.iswriteoff == '1' && val) {
//                            if (val.length >= 10) {
//                                return val.substring(0, 10);
//                            } else {
//                                return val;
//                            }
//                        }
//                    }
//                },
                {field: 'duefromdate', title: '应收日期', resizable: true, sortable: true},
                {
                    field: 'writeoffer', title: '核销人员', resizable: true, sortable: true,
                    formatter: function (val, rowData, rowIndex) {
                        if (rowData.iswriteoff == '1' && rowData.writeoffername) {
                            return rowData.writeoffername;
                        }
                    }
                },
                {field: 'remark', title: '备注', width: 80, sortable: true},
                {
                    field: 'hcreferid', title: '红冲关联代垫', width: 125, sortable: true,
                    formatter: function (val, rowData, rowIndex) {
                        if (val != null && val != "") {
                            return "<a class=\"matcinview\" herf=\"javascript:void(0);\" onclick=\"javascript:showMatcostsInputView('" + val + "')\">" + val + "</a>";
                        }
                    }
                },
                {field: 'adduserid', title: '制单人编码', width: 80, sortable: true, hidden: true},
                {field: 'addusername', title: '制单人', width: 80, sortable: true, hidden: true},
                {
                    field: 'addtime', title: '制单时间', width: 130, sortable: true, hidden: true,
                    formatter: function (val, rowData, rowIndex) {
                        if (val) {
                            return val.replace(/[tT]/, " ");
                        }
                    }
                },
                {
                    field: 'sourcefrome', title: '数据来源', resizable: true,
                    formatter: function (val, rowData, rowIndex) {
                        if (val == "0") {
                            return "手动添加";
                        } else if (val == "1") {
                            return "导入";
                        } else if (val == "2" || val == '3') {
                            return "系统生成";
                        }
                    }
                }
            ]]
        });
        //查询
        $("#report-queay-supplierMatcostsListPage").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#report-query-form-supplierMatcostsListPage").serializeJSON();
            $("#report-datagrid-supplierMatcostsListPage").datagrid('load',queryJSON);
        });
        //重置
        $("#report-reload-supplierMatcostsListPage").click(function(){
            $("#report-query-form-supplierMatcostsListPage")[0].reset();
            var queryJSON = $("#report-query-form-supplierMatcostsListPage").serializeJSON();
            $("#report-datagrid-supplierMatcostsListPage").datagrid('load',queryJSON);
        });

        $("#report-datagrid-supplierMatcostsListPage").datagrid({
            authority:tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns:tableColumnListJson.common,
            method:'post',
            title:'',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            singleSelect:true,
            pageSize:100,
            toolbar:'#report-toolbar-supplierMatcostsListPage',
            url:'journalsheet/matcostsInput/getMatcostsInputPageList.do',
            queryParams:initQueryJSON,
            rowStyler:function(index,row){

            }
        }).datagrid("columnMoving");

        $("#report-export-salesuserRPDSalesFlow").Excel('export',{
            queryForm: "#report-query-form-supplierMatcostsListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type:'exportUserdefined',
            name:'供应商：[${suppliername}]代垫应收明细表',
            url:'journalsheet/matcostsInput/exportMatcostsInputData.do'
        });

    });
    function viewOa(id) {

        top.addTab('act/workViewPage.do?processid=' + id, '工作查看');
    }

</script>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>回单核销的客户显示对应收款单页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <div id="account-collectionOrderInfo-receipt" style="padding: 0px;">
            <form action="sales/writeoffSalesReceipt.do" method="post" id="account-form-collectionorderreceipt">
                <table border="0" cellspacing="1" cellpadding="1">
                    <tr>
                        <td width="90">回单编码:</td>
                        <td><input type="text" id="account-receiptid-collectionorderreceipt" width="180" class="no_input" readonly="readonly" name="receiptids" value="${ids}"/></td>
                        <td width="90">回单金额:</td>
                        <td><input type="text" id="account-receiptamount-collectionorderreceipt" class="easyui-numberbox no_input" data-options="precision:2" readonly="readonly" name="receiptamount"  value="${receiptamount }"/></td>
                        <td width="90">客户名称:</td>
                        <td style="text-align: left;">
                            <input type="hidden" id="collectionorderreceipt-hidden-customerid" name="customerid" value="${customerCapital.id}"/>
                            <input type="text" id="collectionorderreceipt-hidden-customername" width="180" class="no_input" readonly="readonly" value="${customerCapital.customername }"/>
                        </td>
                    </tr>
                    <tr>
                        <td>剩余金额:</td>
                        <td><input type="text" id="collectionorderreceipt-writeoff-remainderamount" class="easyui-numberbox no_input" data-options="precision:2" readonly="readonly" value="${customerCapital.amount }"/></td>
                        <td>核销后剩余金额:</td>
                        <td><input type="text" id="account-writeoff-last-remainderamount" class="easyui-numberbox no_input" data-options="precision:2" readonly="readonly" value="${customerCapital.amount-receiptamount }"/></td>
                        <td>核销金额:</td>
                        <td><input type="text" id="account-writeoff-remainderamount" class="easyui-numberbox no_input" data-options="precision:2" name="writeoffamount" readonly="readonly" value="${receiptamount}"/></td>
                    </tr>
                    <tr>
                        <td>已关联金额:</td>
                        <td><input type="text" id="account-relateCollectionOrder-remainderamount" class="easyui-numberbox no_input" data-options="precision:2" name="amount" readonly="readonly"/></td>
                        <td>未关联金额:</td>
                        <td><input type="text" id="account-relateCollectionOrder-last-remainderamount" class="easyui-numberbox no_input" data-options="precision:2" readonly="readonly" value="${receiptamount}"/></td>
                        <td>尾差金额:</td>
                        <td><input type="text" id="account-relateCollectionOrder-last-tailamount" class="easyui-numberbox no_input" data-options="precision:2" name="tailamount" readonly="readonly" value="${-receiptamount }"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6" align="right">
                            <security:authorize url="/account/receivable/collectionOrderAddPage.do">
                                <a href="javaScript:void(0);" id="account-add-collectionOrder" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增收款单">新增</a>
                            </security:authorize>
                        </td>
                    </tr>
                    <input type="hidden" id="account-relateCollectionOrder-detail" name="detailList"/>
                </table>
            </form>
        </div>
        <table id="account-datagrid-collectionOrderlist-receipt"></table>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="text-align:right;">
            <c:if test="${isrelate == '2'}">
                <input type="button" value="直接调尾差" id="account-receipt-changeTailamount"/>
            </c:if>
            <input type="button" value="确定" id="account-save-writeoffreceipt" />
        </div>
    </div>
</div>
    <script type="text/javascript">
        var collectionOrderdata = JSON.parse('${list}');
        $(function(){
            $("#account-form-collectionorderreceipt").form({
                onSubmit: function(){
                    var rows = $("#account-datagrid-collectionOrderlist-receipt").datagrid("getChecked");
                    var data = [];
                    for(var i=0;i<rows.length;i++){
                        var object = {id:rows[i].id,businessdate:rows[i].businessdate,amount:rows[i].amount,writeoffamount:rows[i].writeoffamount,
                            remainderamount:rows[i].remainderamount,relateamount:rows[i].relateamount};
                        data.push(object);
                    }
                    $("#account-relateCollectionOrder-detail").val(JSON.stringify(data));
                    var flag = $(this).form('validate');
                    if(flag==false){
                        return false;
                    }
                    loading("提交中..");
                },
                success:function(data){
                    loaded();
                    var json = $.parseJSON(data);
                    if(json.flag){
                        $.messager.alert("提醒","核销成功!<br>生成销售核销编号："+json.salesInvoiceid);
                        $('#sales-dialog-receiptWriteoff1').dialog("close");
                        $("#sales-panel-receiptPage").panel('refresh');
                    }else{
                        $.messager.alert("提醒","核销失败!");
                    }
                }
            });

            $("#account-datagrid-collectionOrderlist-receipt").datagrid({
                frozenColumns:[[]],
                columns:[[
                    {field:'ck',checkbox:true},
                    {field:'id',title:'编号',width:120,sortable:true},
                    {field:'businessdate',title:'业务日期',width:80,sortable:true},
                    {field:'amount',title:'收款金额',width:80,align:'right',sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'writeoffamount',title:'已核销金额',width:80,align:'right',sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'remainderamount',title:'剩余金额',width:80,align:'right',sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'relateamount',title:'关联金额',width:80,align:'right',align:'right',
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'tailcontrol',title:'尾差控制',width:80,align:'right',align:'right',
                        formatter:function(value,rowData,rowIndex){
                            if(rowData.checkedrow == '1' && rowData.remainderamount<${positeTailAmount} && rowData.remainderamount > ${negateTailAmount} && rowData.remainderamount>0){
                                return '<a href="javascript:tailModify(\''+rowData.id+'\',\''+rowIndex+'\');">尾差调整</a>';
                            }
                        }
                    }
                ]],
                fit:true,
                method:'post',
                rownumbers:true,
                idField:'id',
                singleSelect:false,
                data: collectionOrderdata,
                toolbar:'#account-collectionOrderInfo-receipt',
                onClickCell:function(rowIndex, field, value){
                    var unrelateamount  = $("#account-relateCollectionOrder-last-remainderamount").numberbox("getValue");
                    if(field!="tailcontrol" && unrelateamount != 0){
                        $(this).datagrid("checkRow",rowIndex);
                    }
                },
                onCheck:function(rowIndex,rowData){
                    rowData['checkedrow'] = '1';
                    var unrelateamount  = $("#account-relateCollectionOrder-last-remainderamount").numberbox("getValue");
                    if(Number(unrelateamount)==0){
                        $(this).datagrid("uncheckRow",rowIndex);
                        $.messager.alert("提醒","金额已全部关联！");
                        return false;
                    }
                    if(rowData.relateamount==null || Number(rowData.relateamount)==0){
                        var unrelateamount = $("#account-relateCollectionOrder-last-remainderamount").numberbox("getValue");
                        if(Number(unrelateamount)>Number(rowData.remainderamount)){
                            rowData.relateamount=rowData.remainderamount;
                            rowData.remainderamount = 0;
                            $(this).datagrid('updateRow',{index:rowIndex, row:rowData});
                        }else{
                            rowData.remainderamount = rowData.remainderamount - unrelateamount;
                            rowData.relateamount=unrelateamount;
                            $(this).datagrid('updateRow',{index:rowIndex, row:rowData});
                        }
                    }
                    countRelateAmount();
                },
                onUncheck:function(rowIndex,rowData){
                    rowData['checkedrow'] = '0';
                    rowData.remainderamount= Number(rowData.remainderamount)+Number(rowData.relateamount);
                    rowData.relateamount=0;
                    $(this).datagrid('updateRow',{index:rowIndex, row:rowData});
                    countRelateAmount();
                },
                onCheckAll:function(){
                    $(this).datagrid("uncheckAll");
                },
                onUncheckAll:function(rows){
                    for(var i=0;i<rows.length;i++){
                        rows[i].relateamount=0;
                        $(this).datagrid('updateRow',{index:i, row:rows[i]});
                    }
                }
            });

            //确定核销
            $("#account-save-writeoffreceipt").click(function(){
                writeoffReceipt("${ids}","${customerid}","0");
            });

            //新增审核收款单
            $("#account-add-collectionOrder").click(function(){
                var customerid = $("#collectionorderreceipt-hidden-customerid").val();
                var customername = $("#collectionorderreceipt-hidden-customername").val();
                $('<div id="account-panel-collectionOrder-addauditpage1"></div>').appendTo('#account-panel-collectionOrder-addauditpage');
                $('#account-panel-collectionOrder-addauditpage1').dialog({
                    title: '收款单新增',
                    width: 650,
                    height: 310,
                    collapsible:false,
                    minimizable:false,
                    maximizable:true,
                    resizable:true,
                    cache: false,
                    href: 'account/receivable/showCollectionOrderAddAuditPage.do?customerid='+customerid+'&customername='+customername,
                    modal: true,
                    onLoad:function(){
                        $("#account-collectionOrder-amount").focus();
                    },
                    onClose:function(){
                        $('#account-panel-collectionOrder-addauditpage1').dialog("destroy");
                    }
                });
            });

            //调尾差
            $("#account-receipt-changeTailamount").click(function(){
                writeoffReceipt("${ids}","${customerid}","1");
            });
        });

        //调整尾差金额
        function tailModify(id,rowIndex){
            var rows = $("#account-datagrid-collectionOrderlist-receipt").datagrid("getChecked");
            var rowData = null;
            for(var i=0;i<rows.length;i++){
                if(rows[i].id==id){
                    rowData = rows[i];
                    break;
                }
            }
            if(null!=rowData){
                $.messager.confirm("提醒","收款单剩余:"+formatterMoney(rowData.remainderamount)+"，是否作为尾差金额？",function(r){
                    if(r){
                        rowData.relateamount = Number(rowData.relateamount) + Number(rowData.remainderamount);
                        rowData.remainderamount = 0;
                        $("#account-datagrid-collectionOrderlist-receipt").datagrid('updateRow',{index:rowIndex, row:rowData});
                        $("#account-datagrid-collectionOrderlist-receipt").datagrid('selectRow',rowIndex);
                        countRelateAmount();
                    }
                });
            }

        }
        //全局调整尾差
        function tailModifyTotal(unrelateamount){
            var receiptamount = $("#account-receiptamount-collectionorderreceipt").numberbox("getValue");//回单金额
            var lastwriteoffamount = $("#account-writeoff-last-remainderamount").numberbox("getValue");//核销后剩余金额
            var remainderamount = $("#account-relateCollectionOrder-remainderamount").numberbox("getValue");//已关联金额

            $("#account-writeoff-last-remainderamount").numberbox("setValue",Number(unrelateamount)+Number(lastwriteoffamount));
            $("#account-relateCollectionOrder-remainderamount").numberbox("setValue",Number(unrelateamount)+Number(remainderamount));
            remainderamount = $("#account-relateCollectionOrder-remainderamount").numberbox("getValue");
            $("#account-relateCollectionOrder-last-remainderamount").numberbox("setValue",Number(receiptamount)-Number(remainderamount));
        }
        //计算核销金额
        function getWriteoffAmount(){
            var tailamount = $("#account-relateCollectionOrder-last-tailamount").numberbox('getValue');
            var receiptamount = $("#account-receiptamount-collectionorderreceipt").numberbox('getValue');
            var writeoffamount = $("#account-writeoff-remainderamount").numberbox('setValue',Number(receiptamount)+Number(tailamount));
        }
        //关联金额变动
        function countRelateAmount(){
            var rows = $("#account-datagrid-collectionOrderlist-receipt").datagrid("getChecked");
            var relateamount = 0;
            for(var i=0;i<rows.length;i++){
                relateamount = Number(relateamount)+Number(rows[i].relateamount);
            }
            var totalamount = $("#account-receiptamount-collectionorderreceipt").numberbox("getValue");
            var unrelateamount = Number(totalamount)-Number(relateamount);
            $("#account-relateCollectionOrder-remainderamount").numberbox("setValue",relateamount);
            $("#account-relateCollectionOrder-last-remainderamount").numberbox("setValue",unrelateamount);
            $("#account-relateCollectionOrder-last-tailamount").numberbox("setValue",-unrelateamount);

            //若勾选了收款单，禁用调尾差按钮，否则不禁用
            if(null != rows && rows.length > 0){
                $("#account-receipt-changeTailamount").hide();
            }else{
                $("#account-receipt-changeTailamount").show();
            }

        }
        //核销回单
        function writeoffReceipt(ids,customerid,istail){
            var unrelateamount = $("#account-relateCollectionOrder-last-remainderamount").numberbox("getValue");
            var positeTailAmount = ${positeTailAmount};
            var negateTailAmount = ${negateTailAmount};
            var tailAmountLimit = "${negateTailAmount}~${positeTailAmount}";
            var tailamount = $("#account-relateCollectionOrder-last-tailamount").numberbox('getValue');
            //判断是否调尾差"0"否"1"是
            if("0" == istail){
                if(tailamount<negateTailAmount || tailamount > positeTailAmount){
                    $.messager.alert("提醒","尾差金额为："+tailamount+",超过系统设置的金额:"+tailAmountLimit+"。不能进行核销！");
                    return false;
                }
                var rows = $("#account-datagrid-collectionOrderlist-receipt").datagrid("getChecked");
                if(null==rows || rows.length==0){
                    $.messager.alert("提醒","未关联收款单");
                    return false;
                }
                if(unrelateamount>0){
                    $.messager.confirm("提醒","金额未全部关联，剩余未关联金额是否作为尾差金额。尾差金额为:"+tailamount+"？",function(r){
                        if(r){
                            tailModifyTotal(unrelateamount);
                            getWriteoffAmount();
                            $("#account-form-collectionorderreceipt").submit();
                        }
                    });
                }else{
                    getWriteoffAmount();
                    $("#account-form-collectionorderreceipt").submit();
                }
            }else{
                $.messager.confirm("提醒","客户余额为"+formatterMoney(${customerCapital.amount })+"，尾差金额为:"+tailamount+"，是否直接调尾差？",function(r){
                    if(r){
                        tailModifyTotal(unrelateamount);
                        getWriteoffAmount();
                        $("#account-form-collectionorderreceipt").submit();
                    }
                });
            }
        }
    </script>
</body>
</html>

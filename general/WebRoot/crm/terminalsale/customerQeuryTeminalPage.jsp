<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户状态查询</title>
</head>
<body>
<div id="crm-query-customerQeuryTeminalPage" style="padding:0px;height:70px">
    <div>
        <form action="" id="crm-form-customerQeuryTeminalPage" method="post">
            <table class="querytable">
                <tr>
                    <td>业务日期：</td>
                    <td class="tdinput"><input type="text" name="businessdate" style="width:95px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',isShowClear:false})" value="${businessdate }" />
                        到 <input type="text" name="businessdate1" style="width:95px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',isShowClear:false})" value="${businessdate1 }"  />
                    </td>
                    <td>商品：</td>
                    <td class="tdinput"><input type="text" id="crm-goodsid-customerQeuryTeminalPage" name="goodsid" class="len130" /> </td>
                    <td>客&nbsp;&nbsp;户：</td>
                    <td class="tdinput"><input type="text" id="crm-customer-customerQeuryTeminalPage" style="width: 160px" name="customerid" /></td>

                </tr>
                <tr>
                    <td>门店类型：</td>
                    <td>
                        <select name="sourcetype" style="width: 210px;">
                            <option value="0">新门店</option>
                            <option value="1">已开门店</option>
                            <option value="2">已关门店</option>
                        </select>
                    </td>
                    <td>品牌：</td>
                    <td class="tdinput"><input type="text" id="crm-brandid-customerQeuryTeminalPage" name="brandid" class="len130" /> </td>
                    <td>销售部门：</td>
                    <td class="tdinput">
                        <input type="text" id="crm-salesDept-customerQeuryTeminalPage" name="salesdept" style="width: 160px;" />
                    </td>
                    <td colspan="2" class="tdbutton" style="padding-left: 5px">
                        <a href="javascript:;" id="crm-queay-customerQeuryTeminalPage" class="button-qr">查询</a>
                        <a href="javascript:;" id="crm-resetQueay-customerQeuryTeminalPage" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<table id="crm-table-customerQeuryTeminalPage"></table>
<script type="text/javascript">

    var initQueryJSON = $("#crm-form-customerQeuryTeminalPage").serializeJSON();

    $(function () {

        $(".Wdate").blur(function () {
            if($(this).val()==""){
               $(this).focus();
            }
        });

        $("#crm-table-customerQeuryTeminalPage").datagrid({
            columns:[[
                {field:'customerid',title:'客户编码',width:80,align:'left',sortable:true},
                {field:'customername',title:'客户名称',width:180,align:'left',isShow:true},
                {field:'salesdept',title:'销售部门',width:100,align:'left',sortable:true},
                {field:'taxamount',title:'金额',width:80,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
            ]],
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 50],
            checkOnSelect: true,
            selectOnCheck:true,
            queryParams:initQueryJSON,
            url: 'crm/terminal/customerQeuryTeminalData.do',
            toolbar:'#crm-query-customerQeuryTeminalPage',
            onDblClickRow:function(index, data){
                top.addOrUpdateTab('crm/terminal/terminalSalesOrderPage.do?type=edit&id='+data.id, '客户销量查看');
            }

        });

        //查询
        $("#crm-queay-customerQeuryTeminalPage").click(function(){
            var queryJSON = $("#crm-form-customerQeuryTeminalPage").serializeJSON();
            $("#crm-table-customerQeuryTeminalPage").datagrid('load', queryJSON);
        });
        //重置
        $("#crm-resetQueay-customerQeuryTeminalPage").click(function(){
            $("#crm-customer-customerQeuryTeminalPage").customerWidget("clear");
            $("#crm-salesDept-customerQeuryTeminalPage").widget("clear");
            $("#crm-goodsid-customerQeuryTeminalPage").goodsWidget("clear");
            $("#crm-brandid-customerQeuryTeminalPage").widget("clear");
            $("#crm-form-customerQeuryTeminalPage").form("reset");
            var queryJSON = $("#crm-form-customerQeuryTeminalPage").serializeJSON();
            $("#crm-table-customerQeuryTeminalPage").datagrid('load', queryJSON);
        });

        //客户参照窗口
        $("#crm-customer-customerQeuryTeminalPage").customerWidget({
            singleSelect:true,
            isdatasql:false,
            onlyLeafCheck:false
        });
        //销售部门控件
        $("#crm-salesDept-customerQeuryTeminalPage").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            singleSelect:true,
            width:160,
            onlyLeafCheck:false
        });
        //商品
        $("#crm-goodsid-customerQeuryTeminalPage").goodsWidget({
            singleSelect:true,
        });
        //品牌
        $("#crm-brandid-customerQeuryTeminalPage").widget({
            name:'t_base_goods_brand',
            col:'id',
            width:130,
            singleSelect:true,
            onlyLeafCheck:false
        });



    });

    
</script>

</body>
</html>

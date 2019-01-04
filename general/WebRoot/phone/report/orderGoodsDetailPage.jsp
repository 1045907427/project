<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>订货单报表</title>
    <%@include file="/phone/common/include.jsp"%>

    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.2.0/css/bootstrap.min.css">
    <script src="phone/js/laypage/laypage.js"></script>

    <script type="text/javascript" src="phone/js/layer.js"></script>
    <script type="text/javascript" src="phone/js/iscroll.js"></script>--%>
    <script src="phone/js/jquery.mloading.js"></script>
    <link rel="stylesheet" href="phone/css/jquery.mloading.css">

    <style>
        li .div-20{
            display:inline-block;
            width: 48%;
            text-align: left;
            margin-left: 2%;
            margin-top: 5px;
            /*min-height:80px;*/
            float: left;
            height: 100%;

        }

        li .div-2{
            text-align: left;
            padding:10px 10px 10px 10px;
            white-space:normal;
            margin-top: 10px;
        }
        #main-li{
            /*min-height:60px;*/
            padding-top: 0px !important;
        }
        .ui-btn{
            font-size: 12px !important;
        }
        .report_hr{
            FILTER: alpha(opacity=100,finishopacity=0,style=3);
            height: 1px;
            margin:0 0 0 0;
            width:100%;
            color:#87CEFA;
            SIZE:1;
        }
        .center_border{
            position:absolute;
            bottom: 2%;
            top:2%;
            left:50%;
            width:1px;
            height:90%;
            background:#8DB6CD;
            z-index:1;
            margin-top: 0px;
        }
        .li_div{
            width: 90%;
        }
        .div-20 div{
            word-wrap:break-word;
            word-break:break-all;
            margin-top: 2px;
            white-space:pre-wrap
        }
        .form-group{
            padding: 0px 5px 0px 5px;
        }
        .footer_li div{
            /*margin: 10px 5px 5px 0px ;*/
            padding: 10px 5px 5px 10px ;
            /*padding-left: 10px;;*/
            /*padding: 0px 5px 5px 0px !important;*/
        }
        #report_footer_orderGoodsDetailPage{
            text-align: left;;
        }
        .divhead{
            background-color: #87CEFA;
        }
        .divhead :first-child{
            display: inline!important;
            margin-right: 5px;
        }

        .notorderunitnum{
            color: #C00000;
        }
    </style>
</head>

<body>
<div data-role="page" id="pagetwo">
    <form method="post"  class="form-horizontal" role="form" id="report_form_orderGoodsDetailPage" data-ajax="false">
        <input type="hidden" id="customerid" name="customerid" value="${paramterMap.customerid}"/>
        <input id="report-query-page" type="hidden" name="page"/>
    </form>
    <div data-role="header" data-position="fixed">
        <a href="#pageone" data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">订货单报表</h1>

        <div data-role="popup" id="myPopup" style="width: 100% ;height:90%;display: none;">
            <div>
                <ul data-role="listview"  data-inset="true" id="report_footer_orderGoodsDetailPage">
                </ul>
            </div>
        </div>
        <a onclick="addOrder()" href="javascript:void(0);" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window" id="report_addorder_orderGoodsDetailPage">生成订单</a>
        <div id="report_isordernum_orderGoodsDetailPage" style="display: none">
            <div  style="overflow:hidden;width:162px;height:40px;border: 1px solid #CCC;">
                <div id="3l" onselectstart="return false;" style="-moz-user-select:none;cursor:pointer;text-align:center;width:40px;height:40px;line-height:40px;border-right: 1px solid #CCC;float:left">
                    -
                </div>
                <div style="display: inline;width: 80px;line-height: 40px;height: 40px;">
                    <input data-role="none" type="text"  style="display: inline;width: 80px;line-height: 40px;height: 40px;" id="changenum11"/>
                </div>
                <div id="3r" onselectstart="return false;" style="-moz-user-select:none;cursor:pointer;text-align:center;width:40px;height:40px;line-height:40px;border-left: 1px solid #CCC;float:right">
                    +
                </div>
            </div>
        </div>
    </div>
    <div id="wrapper">
        <ul id="thelist" data-role="listview" data-inset="true" style="width: 100%;">
        </ul>
    </div>
</div>
</div>
<div style="display: none">
    <div id="report_numdialog_orderGoodsDetailPage">
        生成订单数量:<input type="number" min="0" id="report_ordernum_numdialog"/>
        <div id="changenum">

        </div>
        <%--<a id="report_changenum_numdialog" class="ui-btn ui-shadow ui-corner-all" href="javascript:void(0);" rel="external"  style="width: 30%;display: inline-block;">确定 </a>--%>
    </div>
</div>


<script type="text/javascript">
    function isLockData(id, tname) {
        var flag = false;
        $.ajax({
            url: 'system/lock/unLockData.do',
            type: 'post',
            data: {id: id, tname: tname},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }

    var res=$.parseJSON('${res.list}');

    showLoader();
    showData();
    hideLoader();

    function  showData() {
        if(res.length){ // 如果后台传过来有数据执行如下操作 ， 没有就执行else 告诉用户没有更多内容呢
            reloadData(res);
        }else{
            layer.open({
                content: '没有更多内容了',
                style: 'background-color:#09C1FF; color:#fff; border:none;',
                time: 2
            });
        }
    }

    //显示加载器
    function showLoader() {
        //显示加载器.for jQuery Mobile 1.2.0
        $.mobile.loading('show', {
            text: '加载中...', //加载器中显示的文字
            textVisible: true, //是否显示文字
            theme: 'c',        //加载器主题样式a-e
            textonly: false,   //是否只显示文字
            html: ""           //要显示的html内容，如图片等
        });
    }

    //隐藏加载器.for jQuery Mobile 1.2.0
    function hideLoader()
    {
        //隐藏加载器
        $.mobile.loading('hide');
    }


    //显示合计弹出框
    function showFooterData(){
        layer.open({
            content: document.getElementById("report_footer_orderGoodsDetailPage").outerHTML,
            btn: ['确定']
        });
    }


    function checkNoOrderNum(value){
        if (value== undefined||value==null||value==0){
            return 0;
        }
        return '<span style="color:#C00000">'+formatterMoney(value)+'</span>';
    }

    //空字符显示空
    function checkNumberUndefined (value){
        if (value== undefined||value==null){
            return 0;
        }
        return formatterMoney(value);
    }
    //空元素显示空
    function checkStringUndefined (value){
        if (value== undefined){
            return ' ';
        }
        return value;
    }
    //显示百分比数据
    function percentData(value){
        if(value!=null && value!=0){
            return formatterMoney(value)+"%";
        }else{
            return "";
        }
    }


    //显示每条数据头
    function showHeader(){
        for (var j = 0; j < res.length; j++) {
            var o = document.getElementById("divlist" + j).getElementsByClassName("goodsid")[0];
            document.getElementById("divhead" + j).appendChild(o);
            var o1 = document.getElementById("divlist" + j).getElementsByClassName("goodsname")[0];
            document.getElementById("divhead" + j).appendChild(o1);
        }
    }

    function showGoodsName(rowData){
        if (rowData.goodsInfo != null) {
            if (rowData.isdiscount == '1') {
                return "（折扣）" + rowData.goodsInfo.name;
            } else if (rowData.isdiscount == '2') {
                return "（折扣）" + rowData.goodsInfo.name;
            } else {
                if (rowData.deliverytype == '1') {
                    return "<font color='blue'>&nbsp;赠 </font>" + rowData.goodsInfo.name;
                } else if (rowData.deliverytype == '2') {
                    return "<font color='blue'>&nbsp;捆绑 </font>" + rowData.goodsInfo.name;
                } else {
                    return  rowData.goodsInfo.name ;
                }
            }
        }else if (rowData.goodsname) {
            return rowData.goodsname;
        }else {
            return '<a id="sales-historyprice-orderpage" gid="' + rowData.goodsid + '" ></a>';
        }
    }

    function showTaxprice(row,value){
        if (row.isdiscount != '1' && row.isdiscount != '2') {
            if (parseFloat(value) > parseFloat(row.oldprice)) {
                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                    return "<font color='blue' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + ",最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font></a>";
                } else {
                    return "<font color='blue' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + "'>" + formatterMoney(value) + "</font>";
                }
            }
            else if (parseFloat(value) < parseFloat(row.oldprice)) {
                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                    return "<font color='red' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + ",最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font>";
                } else {
                    return "<font color='red' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + "'>" + formatterMoney(value) + "</font>";
                }
            }
            else {
                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                    return "<font style='cursor: pointer;' title='最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font>";
                } else {
                    if (parseFloat(value) != parseFloat(row.demandprice) && parseFloat(row.demandprice) > 0) {
                        return "<font  style='cursor: pointer;' title='要货价:" + formatterMoney(row.demandprice) + "'>" + formatterMoney(value) + "</font>";
                    } else {
                        return formatterMoney(value);
                    }
                }
            }
        } else {
            return "";
        }
    }

    function showOrderGoodsDetailPage(id){
        location.href="phone/showOrderGoodsDetailPage.do?id="+id;
    }

    //detailid明细编号 id用来确定是页面里哪一条明细的数据
    function showNumDialog(detailid,id){
        $("#changenum").html('');
        $("#report_ordernum_numdialog").attr("max",res[id].notorderunitnum);
        $("#report_ordernum_numdialog").attr("number",parseFloat(res[id].notorderunitnum));
        var btnstr="<a id='report_changenum_numdialog' class='ui-btn ui-shadow ui-corner-all' onclick='changenum(&apos;"+id+"&apos;)' href='javascript:void(0);' rel='external'  style='width: 30%;display: inline-block;'>确定 </a>";
        $("#changenum").html(btnstr);
        layer.open({
            // title: '修改商品数量',
            // type: 1,
            skin: 'layui-layer-rim', //加上边框
            // area: ['420px', '240px'], //宽高
            content: document.getElementById("report_numdialog_orderGoodsDetailPage").outerHTML
        });

    }

    function changenum(i){
        var changenum=$("#report_ordernum_numdialog").val();
        console.log("aaaa"+changenum);
        res[i].isorderunitnum=parseFloat(changenum);
        console.log(res[i].isorderunitnum);
        res[i].isordertaxamount= parseFloat(res[i].isorderunitnum)*parseFloat(res[i].taxprice);
        reloadData(res);
        layer.closeAll();
    }

    function reloadData(res){
        $("#thelist").html('');
        //加载数据。。。
        var arr = new Array();
        for (var i=0;i<res.length;i++) {
            arr.push("<div class='divhead' id='divhead"+i+"'>["+(i+1)+"]");arr.push("</div>");
            arr.push("<li data-icon='false' class='main-li'  id='main-li'>");
            arr.push("<div id='divlist"+i+"' class='report_data'>");
            arr.push("<div class='goodsid'>商品编码:");
            arr.push(checkStringUndefined(res[i].goodsid));
            arr.push("</div>");
            arr.push("<div class='goodsname'>商品名称:");
            arr.push(showGoodsName(res[i]));
            arr.push("</div>");




            arr.push("</div>");
            arr.push("<div class='div-20' id='left"+i+"'>");
            arr.push("<div class='barcode'>条形码:");
            arr.push(checkStringUndefined(res[i].barcode));
            arr.push("</div>");

            arr.push("<div class='unitname'>单位:");
            arr.push(checkStringUndefined(res[i].unitname));
            arr.push("</div>");

            arr.push("<div class='notorderunitnum'>未生成数量:");
            arr.push(checkNoOrderNum(res[i].notorderunitnum));
            arr.push("</div>");

            arr.push("<div class='notordertaxamount'>未生成金额:");
            arr.push(checkNumberUndefined(res[i].notordertaxamount));
            arr.push("</div>");

            arr.push("<div class='referenceprice'>参考价:");
            arr.push(checkStringUndefined(res[i].referenceprice));
            arr.push("</div>");

            arr.push("<div class='auxnumdetail'>辅数量:");
            arr.push(checkStringUndefined(res[i].auxnumdetail));
            arr.push("</div>");

            arr.push("</div>");
            arr.push("<div class='center_border'></div>");
            arr.push("<div class='div-20' id='right"+i+"'>");
            arr.push("<div class='boxnum'>箱装量:");
            arr.push(checkNumberUndefined(res[i].boxnum));
            arr.push("</div>");

            arr.push("<div class='taxamount'>总金额:");
            arr.push(checkNumberUndefined(res[i].taxamount));
            arr.push("</div>");



            arr.push("<div class='orderunitnum'>已生成数量:");
            arr.push(checkNumberUndefined(res[i].orderunitnum));
            arr.push("</div>");

            arr.push("<div class='ordertaxamount'>已生成金额:");
            arr.push(checkNumberUndefined(res[i].ordertaxamount));
            arr.push("</div>");

            arr.push("<div class='taxprice'>单价:");
            arr.push(showTaxprice(res[i]),res[i].taxprice);
            arr.push("</div>");

            arr.push("<div class='isorderunitnum'>");
            arr.push(getIsOrderNumStr(i));
            arr.push("</div>");
            arr.push("</div>");
            // arr.push("<HR class='report_hr' color=#87CEFA>");
            arr.push("</li>");
        }
        $('#thelist').append(arr.join(''));
        showHeader();//显示每条数据头
        // showDataDiv("divlist","div");//将div排列成左右格式
    }

    function addOrder(){
        var flag = isLockData('${ordergoodsid}', "t_sales_goodsorder");
        if(!flag){
            layer.open({
                content: '生成订单成功',
                style: 'background-color:#09C1FF; color:#fff; border:none;',
                time: 2
            });
        }
        $("body").mLoading();
        $.ajax({
            type: "post",
            dataType: "JSON",
            url: "phone/saveOrderByOrderGoodsReport.do", // 你请求的地址
            data: {
                datalist:JSON.stringify(res)
            },
            success: function (data) {
                if(data.flag){
                    layer.open({
                        content: '生成订单成功',
                        style: 'background-color:#09C1FF; color:#fff; border:none;',
                        time: 2
                    });
                    setTimeout("document.location.reload()",2000);
                }else{
                    layer.open({
                        content: data.msg,
                        style: 'background-color:#09C1FF; color:#fff; border:none;',
                        time: 2
                    });
                    $("body").mLoading("hide");
                }
            }
        })
    }

    function getIsOrderNumStr(i){
        var str="<div  style='overflow:hidden;width:162px;height:30px;border: 1px solid #CCC;'>" +
            "<div onclick='subnum("+i+")' id='3l'onselectstart='return false;' style='-moz-user-select:none;cursor:pointer;text-align:center;width:40px;height:30px;line-height:30px;border-right: 1px solid #CCC;float:left'>-</div>" +
            "<div style='display: inline;width: 76px;line-height: 30px;height: 30px;'><input id='isordernum"+i+"' data-role='none' value='0' align='center' type='text' onblur='checknum("+i+")'  style='text-align:center;display: inline;width: 76px;line-height: 30px;height: 30px;' id='changenum11'/></div>" +
            "<div id='3r' onclick='addnum("+i+")' onselectstart='return false;' style='-moz-user-select:none;cursor:pointer;text-align:center;width:40px;height:30px;line-height:30px;border-left: 1px solid #CCC;float:right'>+</div>";
        return str;
    }

    function addnum(i){
        var isordernum=parseFloat($("#isordernum"+i).val()==undefined?0:$("#isordernum"+i).val());
        var max=parseFloat(res[i].notorderunitnum);
        if(isordernum>=max){
            res[i].isorderunitnum=max;
            $("#isordernum"+i).val(max);
        }else{
            $("#isordernum"+i).val(isordernum+1);
            res[i].isorderunitnum=isordernum+1;
        }

    }

    function subnum(i){
        var isordernum=parseFloat($("#isordernum"+i).val()==undefined?0:$("#isordernum"+i).val());
        var min=0;
        if(isordernum<=min){
            $("#isordernum"+i).val(min);
            res[i].isorderunitnum=min;
        }else{
            $("#isordernum"+i).val(isordernum-1);
            res[i].isorderunitnum=isordernum-1;
        }

    }

    function checknum(i){
        var isordernum=parseFloat($("#isordernum"+i).val()==undefined?0:$("#isordernum"+i).val());
        //输入不是数字显示0
        if(isNaN(isordernum)){
            $("#isordernum"+i).val(0);
            return ;
        }
        var max=parseFloat(res[i].notorderunitnum);
        var min=0;
        //输入数字比最大值大，显示最大值
        if(isordernum>=max){
            res[i].isorderunitnum=max;
            $("#isordernum"+i).val(max);
        }else if(isordernum<=min){//输入数字比最小值小，显示最小值
            $("#isordernum"+i).val(min);
            $("#isordernum"+i).val(min);
        }else{
            res[i].isorderunitnum=isordernum;
            $("#isordernum"+i).val(isordernum);
            // $("#isordernum"+i).val(isordernum);
        }
    }

</script>
</body>
</html>

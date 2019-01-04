<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>业务员下单展示</title>
    <%@include file="/include.jsp" %>
    <%--<meta http-equiv="refresh" content="60">--%>
    <style type="text/css">
        .list-image{
            width:160px;height:140px;
        }
        .imagediv-div{
            float: left;
            height:140px;
            margin-bottom: 10px;
            margin-right: 10px;;
        }
        .row-imagediv{
            text-align: left;
            padding-left: 30px;
            position:relative;
            bottom:80px;
            /*opacity:0.5; filter: alpha(opacity=50); background-color:#d0def0;*/
            background-color:rgba(241, 241, 241, 0.6);
            height: 15px;
            font-size : 1em;
            color : #000000;
            width:130px;
            /*font-weight:900;*/
        }
        .table_col{
            padding-bottom: 3px;
            padding-top: 3px;
            float: left;
            width:25%;
        }
        .table_row_0{
            border-style:solid; border-width:1px;
            border-bottom-color:gainsboro;
            float: left;
            clear:left;
            width:100%;
        }
        .table_row_1{
            border-style:solid; border-width:1px;
            border-bottom-color:gainsboro;
            float: left;
            clear:left;
            width:100%;
            background-color: gainsboro;
        }
        .demandTable_demangImagePage{
            float: right;clear:right;width:400px;height:290px;margin-bottom: 10px;margin-right: 10px;
        }
        .table_col_name{
            padding-bottom: 3px;
            padding-top: 3px;
            float: left;
            width:25%;
            text-overflow:ellipsis;white-space:nowrap;overflow:hidden
        }
    </style>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'" style="z-index: 5;background: rgba(241, 241, 241, 0.6)" id="demandImage_div_demangImagePage">
                    <div id="demandTable_div_demangImagePage" class="demandTable_demangImagePage">
                        <%--<div class="table_row">--%>
                            <%--<div class="table_col">姓名</div>--%>
                            <%--<div class="table_col" style="width: 25%;">客户</div>--%>
                            <%--<div class="table_col"  style="text-align: center;width: 20%;margin-right: 10px;">金额</div>--%>
                            <%--<div class="table_col" >制单日期</div>--%>
                        <%--</div>--%>
                    </div>
    </div>
    <div id="sales-dialog-demangImagePage"></div>
</div>
<script type="text/javascript">
    function refreshLayout(title, url,type){
        $('<div id="sales-dialog-demangImagePage1"></div>').appendTo('#sales-dialog-demangImagePage');
        $('#sales-dialog-demangImagePage1').dialog({
            maximizable:true,
            resizable:true,
            title: title,
            width: 920,
            height: 450,
            closed: true,
            cache: false,
            href: url,
            modal: true,
            onClose:function(){
                $('#sales-dialog-demangImagePage1').dialog("destroy");
            }
        });
        $("#sales-dialog-demangImagePage1").dialog("open");
    }
    var inittableheight='170px';
    $(function() {
        reloadPage();
       setInterval("reloadPage()",1800000);//1000为1秒钟

    })
    function reloadPage(){
        showImageList();
//        showDataTable();
    }
    function showImageList(){
        $.ajax({
            type : "post",
            dataType : "JSON",
            url : "sales/getDemandImageData.do", // 你请求的地址
            success : function(res){
                $("#demandImage_div_demangImagePage").html('');
                if(res.list.length>0) {
                    var arr1 = new Array();
                    arr1.push("<div id='demandTable_div_demangImagePage' style='height:"+inittableheight+"px;' class='demandTable_demangImagePage'>");
                    arr1.push("<div class='table_row_0'>");
                    arr1.push("<div class='table_col' style='width: 10%;'>姓名</div>");
                    arr1.push("<div class='table_col' style='width: 40%;'>客户</div>");
                    arr1.push("<div class='table_col'  style='text-align: center;width: 20%;margin-right: 10px;'>金额</div>");
                    arr1.push("<div class='table_col' >制单日期</div>");
                    arr1.push("</div>");
                    arr1.push("</div>");
                    $("#demandImage_div_demangImagePage").append(arr1.join(''));
                    showDataTable();

                    for (var i = 0; i < res.list.length; i++) {
                        var arr = new Array();
                        arr.push("<div style='width:160px;' class='imagediv-div' onclick='showDataByPersonnel(&apos;"+res.list[i].id+"&apos;)'>");
                    if(res.list[i].photograph== undefined||res.list[i].photograph==''||res.list[i].photograph==null){
                        arr.push("<img src='./image/personnel_default.jpg' alt='图片上传失败' onerror=\"javascript:this.src='./image/personnel_default.jpg'\" class='list-image'/>");
                    }
                    else{
                        arr.push("<img src='" + res.list[i].photograph + "' alt='图片上传失败' onerror=\"javascript:this.src='./image/personnel_default.jpg'\" class='list-image'/>");
                    }
                        if (res.list[i].photograph == undefined || res.list[i].photograph == null) {
                            arr.push("<div class='row-imagediv'>姓&nbsp;名:</div>")
                        }
                        else {
                            arr.push("<div class='row-imagediv'>姓&nbsp;名:" + res.list[i].name + "</div>")
                        }
                        arr.push("<div class='row-imagediv'>订单数:" + res.list[i].customernum + "</div>")
                        arr.push("<div class='row-imagediv'>总金额:" + res.list[i].taxamount + "</div>")
                        arr.push("<div class='row-imagediv'>最近上传时间:</div>")
                        if (res.list[i].addtime == undefined || res.list[i].addtime == null) {
                            arr.push("<div style='height:30px;' class='row-imagediv'></div>")
                        }
                        else {
                            arr.push("<div style='height:30px;' class='row-imagediv'>" + changeDateStr(res.list[i].addtime, 'yyyy-MM-dd hh:mm:ss') + "</div>")
                        }
                        arr.push("</div>");
                        $("#demandImage_div_demangImagePage").append(arr.join(''));
                    }
                    changeTableWidth();
                }

            },
            error : function(){

            }
        });

    }
    window.onresize = function(){
        changeTableWidth();
//        changeTableheight();
    }
    function changeTableWidth(){
        var windowwidth=document.body.clientWidth;//页面宽度
        var divwidthpx=$(".imagediv-div").css("width");//图片div宽度
        var tablewidth=400;//初始表格宽度
        var imgdivmarginpx=$(".imagediv-div").css("margin-right");//图片margin
        var imgdivmargin=imgdivmarginpx.substring(0,imgdivmarginpx.length-2);
        var tablemarginpx=$("#demandTable_div_demangImagePage").css("margin-right");//表格margin
        var tablemargin=tablemarginpx.substring(0,tablemarginpx.length-2);
        windowwidth=windowwidth-tablemargin;//计算出去表格margin的宽度
        var divwidth=divwidthpx.substring(0,divwidthpx.length-2);
        divwidth=parseFloat(divwidth)+parseFloat(imgdivmargin);//div宽度+margin
        var rownum=parseInt((windowwidth-tablewidth-imgdivmargin*2-2)/(divwidth));//计算剩余宽度（窗口宽度-表格宽度再/图片div宽度）
        if(rownum>0){
            tablewidth=(windowwidth-rownum*(divwidth))-imgdivmargin*2-2;//由于表格页面不能太小，所以计算时候rownum-1
            $("#demandTable_div_demangImagePage").css("width",tablewidth+"px");
        }
    }
    function showDataTable(){
        $.ajax({
            type : "post",
            dataType : "JSON",
            url : "sales/getDemandImageList.do", // 你请求的地址
            success : function(res){
                for(var i=0;i<res.list.length;i++){
                    var arr = new Array();
                    if(i%2!=0){
                        arr.push("<div class='table_row_1'>");
                    }else{
                        arr.push("<div class='table_row_0'>");
                    }

                    arr.push("<div class='table_col' style='width: 10%;'>"+res.list[i].addusername+"</div>");
                    arr.push("<div class='table_col_name' style='width: 40%;' title='"+res.list[i].customername+"'>("+res.list[i].customerid+")"+res.list[i].customername+"</div>")
                    arr.push("<div class='table_col' style='margin-right: 10px;text-align: center;width: 20%;'>"+formatterMoney(res.list[i].taxamount)+"</div>")
                    arr.push("<div class='table_col'>"+changeDateStr(res.list[i].addtime,'yyyy-MM-dd hh:mm')+"</div>")
                    arr.push("</div>");
                    $("#demandTable_div_demangImagePage").append(arr.join(''));
                }
                if(res.list.length<10){
                    for(var i=res.list.length;i<10;i++){
                        var arr = new Array();
                        if(i%2!=0){
                            arr.push("<div class='table_row_1'>");
                        }else{
                            arr.push("<div class='table_row_0'>");
                        }
                        arr.push("<div class='table_col' style='width: 10%;'>&nbsp;</div>");
                        arr.push("<div class='table_col_name' style='width: 40%;'>&nbsp;</div>")
                        arr.push("<div class='table_col' style='margin-right: 10px;text-align: center;width: 20%;'>&nbsp;</div>")
                        arr.push("<div class='table_col'>&nbsp;</div>")
                        arr.push("</div>");
                        $("#demandTable_div_demangImagePage").append(arr.join(''));
                    }
                }
                var arr = new Array();
                arr.push("<div class='table_row'>");
                arr.push("<div class='table_col'>当日合计:</div>");
                arr.push("<div class='table_col'>&nbsp;</div>")
                arr.push("<div class='table_col' style='margin-right: 10px;text-align: center;width: 20%;'>"+formatterMoney(res.taxamount)+"</div>")
                arr.push("<div class='table_col'></div>")
                arr.push("</div>");
                $("#demandTable_div_demangImagePage").append(arr.join(''));
//                changeTableheight();
            },
            error : function(){

            }
        });

    }
    function changeDateStr(d,datestr){
        //去掉时间字符串里的字母T
        var date=new Date(Date.parse(d.replace(/[a-zA-Z]/g,"/"))).Format(datestr);
        return date;
    }
    function showDataByPersonnel(id){
//        alert(id);
//       var url='sales/showPersonnelDemandDataPage.do?id='+id;
        refreshLayout("要货明细", 'sales/showPersonnelDemandDataPage.do?id='+id,"view");
//        alert("aaaaaa");
//        top.addOrUpdateTab(url, "要货明细");
    }

</script>
</body>
</html>

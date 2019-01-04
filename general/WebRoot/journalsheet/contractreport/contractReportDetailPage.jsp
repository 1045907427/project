<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>费用汇总统计报表明细</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/angular.min.js" charset="UTF-8"></script>
    <style>
        .title-th{
            width: 250px;
            height: 40px;
        }
        .title-td{
            width: 250px;
            height: 40px;
            text-align: center;
        }
        .title-td input{
            width: 250px;
            height: 40px;
            text-align: right;
            padding-right: 20px;
            border: none;
        }
        .content-td1{
            width: 100px;
            height: 40px;
            text-align: center;
        }
        .content-td1 input{
            width: 98px;
            height: 40px;
            text-align: right;
            padding-right: 20px;
            border: none;
        }
        .content-td2{
            width: 75px;
            height: 40px;
            text-align: center;
        }
        .content-td2 input{
            width: 75px;
            height: 40px;
            text-align: right;
            padding-right: 20px;
            border: none;
        }
        .content-td3{
            width: 75px;
            height: 40px;
            text-align: center;
        }
        .content-td3 input{
            width: 75px;
            height: 40px;
            text-align: right;
            padding-right: 10px;
            border: none;
        }
        /*.middle-box{display: table; height: 100%;width:100%; margin:0 auto; position:relative;}*/
        /*.middle-inner{display: table-cell; vertical-align:middle; *position:absolute; *top:50%; *left:50%; width:100%; text-align:center;}*/
        /*.middle-inner p{position:relative; *top:-50%; *left:-50%;}*/

    </style>
</head>
<body>
<div ng-app="myApp" ng-controller="myCtrl" ng-cloak>
    <div id="report-toolbar-contractReportDetailPage" style="padding:0px;height:auto">
        <div class="buttonBG">
            <c:if test="${state == 0 }">
                <security:authorize url="/report/storage/contractReportDetailPageMonthExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-save-contractReportDetailPage" class="easyui-linkbutton" iconCls="button-add" plain="true" title="保存">保存</a>
                </security:authorize>
            </c:if>
        </div>
        <div>
            <table border="1"  cellspacing="0" cellpadding="0"  style="border-collapse: collapse;">
                <tr>
                    <th style="width:150px">
                        <span>{{customername}}</span>
                    </th>
                    <td style="width:250px">
                        <table width="250" border="1"  cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;">
                            <tr>
                                <td style="width:150px">
                                    <table width="150" border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;">
                                        <tr><th class="title-th"><span>合同名称</span></th></tr>
                                    </table>
                                </td>
                                <td ng-repeat="title in titlelist" style="width:250px">
                                    <table width="250" border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;" id="title-{{title.contractbillid}}" contractbillid="{{title.contractbillid}}">
                                        <tr><th class="title-td"><span>{{title.contractname}}</span></th></tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <%--合同头部--%>
                <tr>
                    <th style="width:150px">
                        <span>销售状况</span>
                    </th>
                    <td style="width:250px">
                        <table width="250" border="1"  cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;">
                            <tr>
                                <td style="width:150px">
                                    <table width="150" border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;">
                                        <tr><th class="title-th"><span>销售额</span></th></tr>
                                        <tr><th class="title-th"><span>现有门店数</span></th></tr>
                                        <tr><th class="title-th"><span>本月新店数</span></th></tr>
                                        <tr><th class="title-th"><span>本月新品数</span></th></tr>
                                    </table>
                                </td>
                                <td ng-repeat="title in titlelist" style="width:250px">
                                    <table width="250" border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;" id="title-{{title.contractbillid}}" contractbillid="{{title.contractbillid}}">
                                        <tr><td class="title-td"><input id="saleamount-{{title.contractbillid}}" contractbillid="{{title.contractbillid}}" valuetype="saleamount" value="{{title.saleamount | formatToMoney}}" onchange="titleDataChange(this)"/></td></tr>
                                        <tr><td class="title-td"><input id="storenum-{{title.contractbillid}}" contractbillid="{{title.contractbillid}}" valuetype="storenum" value="{{title.storenum}}" onchange="titleDataChange(this)"></td></tr>
                                        <tr><td class="title-td"><input id="newstorenum-{{title.contractbillid}}" contractbillid="{{title.contractbillid}}" valuetype="newstorenum" value="{{title.newstorenum}}" onchange="titleDataChange(this)"/></td></tr>
                                        <tr><td class="title-td"><input id="newgoodsnum-{{title.contractbillid}}" contractbillid="{{title.contractbillid}}" valuetype="newgoodsnum" value="{{title.newgoodsnum}}" onchange="titleDataChange(this)"/></td></tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <%--合同内容--%>
                <tr>
                    <th style="width:150px" colspan="{{content.size}}">
                        <span>条款类别</span>
                    </th>
                    <td style="width:250px;">
                        <table border="1"  width="250" cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;">
                            <tr>
                                <th style="width:150px">
                                    <div style="width:150px;"><span>合同条款</span></div>
                                </th>
                                <td style="width:250px" ng-repeat="title in titlelist">
                                    <table width="250" border="1"  cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;">
                                        <tr>
                                            <th class="content-td1"><span>费用总金额</span></th>
                                            <th class="content-td2"><span>代垫</span></th>
                                            <th class="content-td3"><span>自理</span></th>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <%--合同内容--%>
                <tr ng-repeat="content in contentlist">
                    <th style="width:150px">
                        <span>{{content.calusetypename}}</span>
                    </th>
                    <td style="width:250px">
                        <table width="250" border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;">
                            <tr ng-repeat="caluse in content.caluseList">
                                <th style="width:150px" ng-if="$first" ng-repeat="index in caluse.indexlist">
                                    <div style="width: 150px"><span>{{index.contractcalusename}}</span></div>
                                </th>
                                <td style="width:250px" ng-if="!$first" ng-repeat="index in caluse.indexlist">
                                    <table width="250" border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;" id="content-{{index.contractbillid}}-{{index.contractcaluseid}}" contractcaluseid="{{index.contractcaluseid}}" calculatetype="{{index.calculatetype}}" calculateamount="{{index.calculateamount}}" isexist="{{index.isexist}}">
                                        <tr ng-if="index.isexist==1">
                                            <td class="content-td1"><input id="costamount-{{index.contractbillid}}-{{index.contractcaluseid}}" valuetype="costamount" contractbillid="{{index.contractbillid}}" contractcaluseid="{{index.contractcaluseid}}" value="{{index.costamount | formatToMoney}}" onchange="contentDataChange(this)"/></td>
                                            <td class="content-td2"><input id="matcostsamount-{{index.contractbillid}}-{{index.contractcaluseid}}" valuetype="matcostsamount" contractbillid="{{index.contractbillid}}" contractcaluseid="{{index.contractcaluseid}}" value="{{index.matcostsamount | formatToMoney}}" onchange="contentDataChange(this)"/></td>
                                            <td class="content-td3"><input id="selfamount-{{index.contractbillid}}-{{index.contractcaluseid}}" valuetype="selfamount" contractbillid="{{index.contractbillid}}" contractcaluseid="{{index.contractcaluseid}}"  value="{{index.selfamount | formatToMoney}}" onchange="contentDataChange(this)"/></td>
                                        </tr>
                                        <tr  ng-if="index.isexist==0">
                                            <td class="content-td1"></td>
                                            <td class="content-td2"></td>
                                            <td class="content-td3"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <th style="width:150px">
                        <span>合计</span>
                    </th>
                    <td style="width:250px;">
                        <table border="1"  width="250" cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;">
                            <tr>
                                <th style="width:150px">
                                    <div style="width:150px;"><span></span></div>
                                </th>
                                <td style="width:250px" ng-repeat="title in titlelist" repeat-finish>
                                    <table width="250" border="1"  cellspacing="0" cellpadding="0" style="border-collapse: collapse;border-width:0px; border-style:hidden;"  id="account-{{title.contractbillid}}" contractbillid="{{title.contractbillid}}">
                                        <tr>
                                            <td class="content-td1"><input id="account-costamount-{{title.contractbillid}}" valuetype="costamount" contractbillid="{{title.contractbillid}}" value="0" /></td>
                                            <td class="content-td2"><input id="account-matcostsamount-{{title.contractbillid}}" valuetype="matcostsamount" contractbillid="{{title.contractbillid}}" value="0" /></td>
                                            <td class="content-td3"><input id="account-selfamount-{{title.contractbillid}}" valuetype="selfamount" contractbillid="{{title.contractbillid}}" value="0"/></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<script type="text/javascript">
    var month = '${month}';
    var customerid = '${customerid}';

    var app = angular.module('myApp', []);
    app.controller('myCtrl', function($scope, $http) {
        $http.get('journalsheet/contractreport/getContractReportDetailDataByMonthAndCustomerid.do?month='+month+'&customerid='+customerid).success(function(response) {
            $scope.titlelist = response.titlelist;
            $scope.contentlist = response.contentlist;
            $scope.customername = response.customername;
        });
    });
    app.filter('formatToMoney', function() {
        var formatToMoneyFilter = function(input) {
            return input.toFixed(2);
        };
        return formatToMoneyFilter;
    });
    app.directive('repeatFinish',function($timeout) {
        return {
            link: function(scope,element,attr){
                if(scope.$last == true){
                    $timeout(function() {
                        account();
                    });
                }
            }
        }
    })
    function  titleDataChange(obj){
        var contractbillid=$(obj).attr("contractbillid");
        var valuetype=$(obj).attr("valuetype");
        var caluseList = $('table[id^="content-'+contractbillid+'"]');
        var a=RegExp("[1-9][0-9]{0,9}").test(parseInt(obj.value));
        if(!a){
            if("saleamount"==valuetype){
                obj.value=formatterMoney(0);
            }else{
                obj.value = 0;
            }
        }else{
            if("saleamount"==valuetype){
                obj.value = formatterMoney(obj.value);
            }
        }
        for(var i=0;i<caluseList.length;i++){
            var caluseobj = caluseList[i]
            var contractcaluseid = caluseobj.getAttribute("contractcaluseid");
            var calculatetype = caluseobj.getAttribute("calculatetype");
            var calculateamount = caluseobj.getAttribute("calculateamount");
            var isexist = caluseobj.getAttribute("isexist");
            if("1"==isexist){
                var costamountobj = document.getElementById("costamount-"+contractbillid+"-"+contractcaluseid);
                var matcostsamountobj = document.getElementById("matcostsamount-"+contractbillid+"-"+contractcaluseid);
                var selfamountobj = document.getElementById("selfamount-"+contractbillid+"-"+contractcaluseid);
                if(("saleamount"==valuetype&&"1"==calculatetype)||("storenum"==valuetype&&"2"==calculatetype)||("newstorenum"==valuetype&&"3"==calculatetype)||("newgoodsnum"==valuetype&&"4"==calculatetype)){
                    costamountobj.value=formatterMoney(parseFloat(obj.value)*parseFloat(calculateamount));
                    matcostsamountobj.value=formatterMoney(parseFloat(obj.value)*parseFloat(calculateamount));
                    selfamountobj.value= formatterMoney(0);

                }
            }
        }
        account();
    }

    function  contentDataChange(obj){
        var valuetype=$(obj).attr("valuetype");
        var contractbillid=$(obj).attr("contractbillid");
        var contractcaluseid=$(obj).attr("contractcaluseid");

        var costamountobj = document.getElementById("costamount-"+contractbillid+"-"+contractcaluseid);
        var matcostsamountobj = document.getElementById("matcostsamount-"+contractbillid+"-"+contractcaluseid);
        var selfamountobj = document.getElementById("selfamount-"+contractbillid+"-"+contractcaluseid);



        var a=RegExp("[1-9][0-9]{0,9}").test(parseInt(obj.value));
        if(!a){
            obj.value=formatterMoney(0);
        }else{
            obj.value=formatterMoney(obj.value);
        }
        if("costamount"==valuetype){
            matcostsamountobj.value =formatterMoney(costamountobj.value) ;
            selfamountobj.value =formatterMoney(0);
        }else if("matcostsamount"==valuetype){
            if(parseFloat(matcostsamountobj.value)>parseFloat(costamountobj.value)){
                matcostsamountobj.value= formatterMoney(costamountobj.value);
                selfamountobj.value =formatterMoney(0);
            }
            selfamountobj.value = formatterMoney(parseFloat(costamountobj.value)-parseFloat(matcostsamountobj.value));
        }else if("selfamount"==valuetype){
            console.log(costamountobj.value)
            console.log(matcostsamountobj.value)
            console.log(selfamountobj.value)
            if(parseFloat(selfamountobj.value)>parseFloat(costamountobj.value)){
                selfamountobj.value= formatterMoney(costamountobj.value);
                matcostsamountobj.value =formatterMoney(0);
            }
            matcostsamountobj.value = formatterMoney(parseFloat(costamountobj.value)-parseFloat(selfamountobj.value));
        }
        account();
    }
    //保存
    $("#report-buttons-save-contractReportDetailPage").click(function(){
        var contractReportList = new Array();
        var contractList = $('table[id^="title-"]');
        for(var i=0;i<contractList.length;i++){
            var contractobj = contractList[i];
            var contractbillid = contractobj.getAttribute("contractbillid");
            var saleamountobj = document.getElementById("saleamount-"+contractbillid);
            var storenumobj = document.getElementById("storenum-"+contractbillid);
            var newstorenumobj = document.getElementById("newstorenum-"+contractbillid);
            var newgoodsnumobj = document.getElementById("newgoodsnum-"+contractbillid);

            var caluseList = $('table[id^="content-'+contractbillid+'"]');
            for(var j=0;j<caluseList.length;j++) {
                var caluseobj = caluseList[j];
                var contractcaluseid = caluseobj.getAttribute("contractcaluseid");
                var isexist = caluseobj.getAttribute("isexist");
                if("1"==isexist){
                    var costamountobj = document.getElementById("costamount-"+contractbillid+"-"+contractcaluseid);
                    var matcostsamountobj = document.getElementById("matcostsamount-"+contractbillid+"-"+contractcaluseid);
                    var selfamountobj = document.getElementById("selfamount-"+contractbillid+"-"+contractcaluseid);
                    var contractReport = new Object();
                    contractReport.month=month;
                    contractReport.contractbillid=contractbillid;
                    contractReport.contractcaluseid=contractcaluseid;
                    contractReport.saleamount=saleamountobj.value;
                    contractReport.storenum=storenumobj.value;
                    contractReport.newstorenum=newstorenumobj.value;
                    contractReport.newgoodsnum=newgoodsnumobj.value;
                    contractReport.costamount=costamountobj.value;
                    contractReport.matcostsamount=matcostsamountobj.value;
                    contractReport.selfamount=selfamountobj.value;
                    contractReportList[contractReportList.length] = contractReport;
                }
            }
        }
        var contractReportListJson=JSON.stringify(contractReportList);
        $.ajax({
            url: 'journalsheet/contractreport/editContractReportDataList.do',
            type: 'post',
            data: {
                contractReportListJson: contractReportListJson
            },
            dataType: 'json',
            success: function (json) {
                if (json.flag) {
                    $.messager.alert("提醒","保存成功!");
                }
            }
        });
    });


    function account(){
        var accountList = $('table[id^="account-"]');
        for(var i=0;i<accountList.length;i++){
            var accountobj = accountList[i];
            var contractbillid = accountobj.getAttribute("contractbillid");
            var accountcostamountobj = document.getElementById("account-costamount-"+contractbillid);
            var accountmatcostsamountobj = document.getElementById("account-matcostsamount-"+contractbillid);
            var accountselfamountobj = document.getElementById("account-selfamount-"+contractbillid);
            accountcostamountobj.value = parseFloat(0);
            accountmatcostsamountobj.value = parseFloat(0);
            accountselfamountobj.value = parseFloat(0);
            var caluseList = $('table[id^="content-'+contractbillid+'"]');
            for(var j=0;j<caluseList.length;j++) {
                var caluseobj = caluseList[j];
                var contractcaluseid = caluseobj.getAttribute("contractcaluseid");
                var isexist = caluseobj.getAttribute("isexist");
                if("1"==isexist){
                    var costamountobj = document.getElementById("costamount-"+contractbillid+"-"+contractcaluseid);
                    var matcostsamountobj = document.getElementById("matcostsamount-"+contractbillid+"-"+contractcaluseid);
                    var selfamountobj = document.getElementById("selfamount-"+contractbillid+"-"+contractcaluseid);
                    accountcostamountobj.value = formatterMoney(parseFloat(accountcostamountobj.value)+parseFloat(costamountobj.value))
                    accountmatcostsamountobj.value = formatterMoney(parseFloat(accountmatcostsamountobj.value)+parseFloat(matcostsamountobj.value))
                    accountselfamountobj.value = formatterMoney(parseFloat(accountselfamountobj.value)+parseFloat(selfamountobj.value))
                }
            }
        }
    }
</script>
</body>
</html>

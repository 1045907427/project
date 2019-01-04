<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>银行账户余额</title>
	<%@include file="/phone/common/include.jsp"%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.2.0/css/bootstrap.min.css">
	<script src="phone/js/laypage/laypage.js"></script>

	<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="phone/js/layer.js"></script>
	<script type="text/javascript" src="phone/js/iscroll.js"></script>


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
			height:95%;
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
		#report_footer_bankAmountListPage{
			text-align: left;
		}
		.divhead{
			background-color: #87CEFA;
		}
		.divhead :first-child{
			display: inline!important;
			margin-right: 5px;
		}
	</style>
</head>

<body>
<div data-role="page" id="pageone" >
	<div data-role="header" data-position="fixed">
		<a href="javascript:location.href='phone/showPhoneReportListPage.do';" data-rel="back"  class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
		<h1 align="center">银行账户余额</h1>
	</div>

	<div data-role="main" class="ui-content">
		<div style="position:absolute;width:90%;height:71%;overFlow-x:hidden;overFlow-y:scroll;">
			<form method="post"  class="form-horizontal" role="form" id="report_form_bankAmountListPage" data-ajax="false"  action="phone/showBaseFinanceDrawnDataPage.do">
				<div class="form-group">
					<label class="col-sm-2 control-label">银行名称</label>
					<div class="col-sm-10">
						<input type="text" name="bankname" id="report_bankname_bankAmountListPage" readonly="readonly" data-clear-btn="true"/>
						<input type="hidden" name="bankid" id="report_bankid_bankAmountListPage"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">所属部门</label>
					<div class="col-sm-10">
						<input type="text" class="required" name="deptname" id="report_deptname_bankAmountListPage" value="" readonly="readonly" data-clear-btn="true"/>
						<input type="hidden" name="deptid" id="report_deptid_bankAmountListPage"/>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div style="position:fixed;bottom:0px;width: 100%;" id="footer">
		<div align="center">
			<a class="ui-btn ui-shadow ui-corner-all" href="javascript:void(0);" rel="external"  onclick="searchData()" style="width: 30%;display: inline-block;">查询 </a>
			<a class="ui-btn ui-shadow ui-corner-all" href="javascript:void(0);"  onclick="reset()" style="width: 30%;display: inline-block;">重置 </a>
		</div>
	</div>
</div>
<div data-role="page" id="pagetwo">
	<div data-role="header" data-position="fixed">
		<a href="#pageone" data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
		<h1 align="center">银行账户余额</h1>

		<div data-role="popup" id="myPopup" style="width: 100% ;height:90%;display: none;">
			<div>
				<ul data-role="listview" data-inset="true" id="report_footer_bankAmountListPage">
				</ul>
			</div>
		</div>
		<a onclick="showFooterData()" href="javascript:void(0);" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window" id="report_a_footer">显示合计</a>

	</div>
	<div id="wrapper">
		<ul id="thelist" data-role="listview" data-inset="true" style="width: 100%;">
		</ul>
	</div>
</div>
<script type="text/javascript">
    function searchData() {
        var querystring = $("#report_form_bankAmountListPage").serialize();
        location.href="phone/showBankAmountDataPage.do?"+querystring;
    }
    function reset(){
        //参照窗口需要手动清除
        $('#report_bankname_bankAmountListPage').val( '');
        $('#report_bankname_bankAmountListPage').change();
        $('#report_deptname_bankAmountListPage').val( '');
        $('#report_deptname_bankAmountListPage').change();

        document.getElementById("report_form_bankAmountListPage").reset();
    }
    $(function(){

        // 银行
        $('#report_bankname_bankAmountListPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_FINANCE_BANK',
                onSelect: 'selectBank',
                checkType : "1",
                onCheck: 'selectBank'
            });
        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_bankid_bankAmountListPage').attr('value', v);
        });

        // 部门
        $('#report_deptname_bankAmountListPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid: 'RT_T_SYS_DEPT',
                onSelect: 'selectDept',
                checkType : "1",
                onCheck: 'selectDept'
            });
        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_deptid_bankAmountListPage').attr('value', v);
        });

    });

    //选择银行
    function selectBank(data){
		$('#report_bankid_bankAmountListPage').val(data.id);
		$('#report_bankname_bankAmountListPage').val(data.name);
    }

    //选择部门
    function selectDept(data){
		$('#report_deptid_bankAmountListPage').val(data.id);
		$('#report_deptname_bankAmountListPage').val(data.name);
    }




</script>
</body>
</html>

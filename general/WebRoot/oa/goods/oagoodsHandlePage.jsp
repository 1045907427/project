<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>新品登录单审批页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-panel" data-options="fit:true,border:false">
  		<div class="easyui-layout" data-options="fit:true,border:false">
            <form action="oa/editGoods.do" id="oa-form-oagoodsHandlePage" method="post">
                <input type="hidden" name="status" value="2"/>
                <input type="hidden" name="goods.id" id="oa-id-oagoodsHandlePage" value="<c:out value='${goods.id }' escapeXml='true'></c:out>" />
                <input type="hidden" name="goodsBrandJSON" id="oa-goodsBrandJSON-oagoodsHandlePage"/>

                <div data-options="region:'center',border:false">
                    <div style="margin: 0px auto; width: 840px; border: solid 1px #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                            <table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
                                <tr>
                                    <td class="left len90">供应商：</td>
                                    <td class="len180"><input class="easyui-validatebox len150" style="margin-left: 0px;" name="goods.supplierid" id="oa-supplierid-oagoodsHandlePage" value="<c:out value='${goods.supplierid }' escapeXml='true'></c:out>"/></td>
                                    <td class="right len90">进场商家数：</td>
                                    <td class="len180"><input class="easyui-validatebox easyui-numberbox len150" style="margin-left: 0px;" name="goods.customernum" id="oa-customernum-oagoodsHandlePage" data-options="required:true, min:0, precision:0" value="<c:out value='${goods.customernum }' escapeXml='true'></c:out>"/></td>
                                    <td class="right len90">进场费用：</td>
                                    <td class="len180"><input class="easyui-validatebox easyui-numberbox len150" style="margin-left: 0px;" name="goods.costamount" id="oa-costamount-oagoodsHandlePage" data-options="required:true, min:0, precision:2" value="<c:out value='${goods.costamount }' escapeXml='true'></c:out>"/></td>
                                </tr>
                                <tr>
                                    <td class="left len90">备　注：</td>
                                    <td colspan="5">
                                        <textarea style="width: 700px; height: 50px; resize: none;" maxlength="165" name="goods.remark" id="oa-remark-oagoodsHandlePage"><c:out value='${goods.remark }'></c:out></textarea>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <div class="easyui-panel" data-options="border:false" style="height: 327px; border: solid 1px #aaa;">
                            <table id="oa-datagrid-oagoodsHandlePage">
                                <tr></tr>
                                <tr></tr>
                                <tr></tr>
                                <tr></tr>
                                <tr></tr>
                                <tr></tr>
                                <tr></tr>
                                <tr></tr>
                                <tr></tr>
                                <tr></tr>
                            </table>
                        </div>

                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                            <div id="oa-attach-oagoodsHandlePage" style="width: 830px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments-oagoodsHandlePage" style="width: 825px;">
                            </div>
                        </div>
                    </div>
                </div>
            </form>
	  	</div>
  	</div>
	<script type="text/javascript">

	$(function(){

		// 品牌参照窗口
		$("#oa-brandid-oagoodsHandlePage").widget({
			name: 't_oa_goods',
			col: 'brandid',
			singleSelect:true,
			width: 130,
			required:true,
			onlyLeafCheck:true
		});
		
		// 供应商参照窗口
		$("#oa-supplierid-oagoodsHandlePage").widget({
			name: 't_oa_goods',
			col: 'supplierid',
			singleSelect:true,
			width: 132,
			required:true,
			onlyLeafCheck:true
		});

	});
	
	var $line = $('#oa-datagrid-oagoodsHandlePage');
	var editItem = $('#oa-editRow-oagoodsPage');
	var addItem = $('#oa-addRow-oagoodsPage');
	
	var $oa_datagrid = $('#oa-datagrid-oagoodsHandlePage');

    var $attach = $('#oa-attach-oagoodsHandlePage');
    var $comments = $('#oa-comments-oagoodsHandlePage');

	</script>
  </body>
</html>
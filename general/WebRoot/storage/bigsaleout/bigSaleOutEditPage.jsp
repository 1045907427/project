<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>大单发货修改页面</title>
  </head>
  
  <body>
    <form id="storage-form-bigSaleOutAdd" action="" method="post">
    	<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
   			<tr>
   				<td width="60px" align="right">编号：</td>
   				<td style="width: 5px"></td>
   				<td><input class="len150" value="${bigSaleOut.id}" disabled="disabled"/>
   					<input id="storage-bigSaleOut-id" type="hidden" name="bigSaleOut.id" value="${bigSaleOut.id}"/>
   				</td>
   				<td width="60px" align="right">业务日期：</td>
   				<td style="width: 5px"></td>
   				<td><input type="text" class="len150 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${bigSaleOut.businessdate }" name="bigSaleOut.businessdate" disabled="disabled"/></td>
   				<td width="60px" align="right">状态：</td>
   				<td style="width: 5px"></td>
   				<td>
   					<select id="storage-bigSaleOut-status-select" disabled="disabled" class="len150">
  						<c:forEach items="${statusList }" var="list">
  							<c:choose>
  								<c:when test="${list.code == bigSaleOut.status}">
  									<option value="${list.code }" selected="selected">${list.codename }</option>
  								</c:when>
  								<c:otherwise>
  									<option value="${list.code }">${list.codename }</option>
  								</c:otherwise>
  							</c:choose>
  						</c:forEach>
  					</select>
  					<input type="hidden" id="storage-bigSaleOut-status" name="bigSaleOut.status" value="${bigSaleOut.status }"/>
   				</td>
   			</tr>
   			<tr>
   				<td width="60px" align="right">出库仓库：</td>
   				<td style="width: 5px"></td>
   				<td>
   					<select id="storage-bigSaleOut-storageid" name="bigSaleOut.storageid" class="len150" disabled="disabled">
   						<c:forEach items="${storageList }" var="list">
						<option value="${list.id }" <c:if test="${list.id == bigSaleOut.storageid}">selected="selected"</c:if>>${list.name }</option>
   						</c:forEach>
   					</select>
   					<input type="hidden" id="storage-bigSaleOut-storageid-hidden"  name="bigSaleOut.storageid" value="${bigSaleOut.storageid }"/>
   				</td>
   				<td width="60px" align="right">打印次数：</td>
   				<td style="width: 5px"></td>
   				<td>
   					<input type="text" class="len150" id="storage-bigSaleOut-printtimes" name="bigSaleOut.printtimes" value="${bigSaleOut.printtimes}" readonly="readonly" disabled="disabled"/>
   				</td>
   				<td width="60px" align="right">备注：</td>
   				<td style="width: 5px"></td>
   				<td>
   					<input type="text" id="bigSaleOut-remark" name="bigSaleOut.remark" value="<c:out value="${bigSaleOut.remark }"></c:out>" class="len150"/>
   				</td>
   			</tr>
   		</table>
    </form>
    <ul class="tags" style="min-width: 400px">
		<security:authorize url="/storage/bigSaleOutGoodsDetailTab.do">
			<li id="firstli" class="selectTag">
				<a href="javascript:void(0)">商品明细</a>
			</li>
		</security:authorize>
		<security:authorize url="/storage/bigSaleOutSourceBillTab.do">
			<li>
				<a href="javascript:void(0)">发货单据</a>
			</li>
		</security:authorize>
	</ul>
	<div class="tagsDiv" style="min-width: 800px">
		<div class="tagsDiv_item">
			<table id="bigSaleOut-table-GoodsDetail"></table>
		</div>
		<div class="tagsDiv_item">
			<table id="bigSaleOut-table-SourceBill"></table>
		</div>
	</div>
    <div id="bigSaleOut-datagrid-toolbar-SourceBill" style="padding:2px;height:auto;display: none">
        <a href="javaScript:void(0);" id="bigSaleOut-add-sourcebill" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">添加</a>
        <a href="javaScript:void(0);" id="bigSaleOut-delete-sourcebill" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
    </div>
	<script type="text/javascript">
		var $dgGoodsDetailList = null;
		var $dgSourceBillList = null;
		var initpage=true;
		var ids='';
		$(function(){
			$("#storage-buttons-bigSaleOutPage").buttonWidget("setDataID",{id:"${bigSaleOut.id}",state:"${bigSaleOut.status}",type:"edit"});
			
			if("${bigSaleOut.status}" != "3"){
				$("#storage-buttons-bigSaleOutPage").buttonWidget('disableButton','button-saleout-button');
			}else{
				$("#storage-buttons-bigSaleOutPage").buttonWidget('enableButton','button-saleout-button');
			}
			if("${bigSaleOut.status}" != "4"){
				$("#storage-buttons-bigSaleOutPage").buttonWidget('disableButton','button-cancelsaleout-button');
			}else{
				$("#storage-buttons-bigSaleOutPage").buttonWidget('enableButton','button-cancelsaleout-button');
			}

			if("${bigSaleOut.status}" != "3" && "${bigSaleOut.status}" != "4"){
				$("#storage-buttons-bigSaleOutPage").buttonWidget('disableButton','printMenuButton');
			}else{
				$("#storage-buttons-bigSaleOutPage").buttonWidget('enableButton','printMenuButton');
			}

            var height = $("#storage-panel-bigSaleOutPage").height()-95;
			$(".tags").find("li").click(function(){
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 0){
					$dgGoodsDetailList = $("#bigSaleOut-table-GoodsDetail");
					if(!$dgGoodsDetailList.hasClass("create-datagrid")){
						$dgGoodsDetailList.datagrid({
				   			authority:goodsTableColJson,
			    			columns: goodsTableColJson.common,
			    			frozenColumns: goodsTableColJson.frozen,
				   			method:'post',
				   			title:'',
                            height:height,
				   			rownumbers:true,
				  			singleSelect:true,
				  			border:false,
				  			pagination:true,
				  			showFooter: true,
				  			url:'storage/showBigSaleOutGoodsList.do',
							queryParams:{id:"${bigSaleOut.id}",initpage:initpage,ids:ids}
				    	});
						$dgGoodsDetailList.addClass("create-datagrid");
					}else{
						ids='';
						$dgSourceBillList = $("#bigSaleOut-table-SourceBill");
						var rows = $dgSourceBillList.datagrid("getRows");
						for(var i=0;i<rows.length;i++){
							if(ids == ""){
								ids = rows[i].id;
							}else{
								ids += "," + rows[i].id;
							}
						}
						var queryJSON = $("#storage-form-bigSaleOutAdd").serializeJSON();
						$dgGoodsDetailList.datagrid('options').url = 'storage/showBigSaleOutGoodsList.do?id=${bigSaleOut.id}&initpage='+initpage+'&ids='+ids;
						$dgGoodsDetailList.datagrid('load', queryJSON);
					}
				}
				if(index == 1){
					initpage=false;
					$dgSourceBillList = $("#bigSaleOut-table-SourceBill");
					if(!$dgSourceBillList.hasClass("create-datagrid")){
						$dgSourceBillList.datagrid({
				   			authority:billTableColJson,
			    			columns: billTableColJson.common,
			    			frozenColumns: billTableColJson.frozen,
				   			method:'post',
				   			title:'',
                            height:height,
				   			rownumbers:true,
				  			url:'storage/showBigSaleOutSourceBillList.do',
					  		border:false,
					  		showFooter:false,
					  		singleSelect:false,
					  		checkOnSelect:true,
					  		selectOnCheck:true,
			   				toolbar: '#bigSaleOut-datagrid-toolbar-SourceBill',
							queryParams:{id:"${bigSaleOut.id}"}
				    	});
						$dgSourceBillList.addClass("create-datagrid");
					}
				}
			});
			
			$("#firstli").click();

            $("#bigSaleOut-add-sourcebill").click(function(){
                $('#bigSaleOut-dialog-saleout').dialog({
                    title: '发货单据新增',
                    width: 600,
                    height: 450,
                    fit:true,
                    closed: false,
                    cache: false,
                    resizable: true,
                    maximizable:true,
                    href:'storage/showBigSaleOutAddSaleoutPage.do',
					queryParams:{storageid:"${bigSaleOut.storageid}"},
                    modal: true
                });
            });

            $("#bigSaleOut-delete-sourcebill").click(function(){
                var rows = $dgSourceBillList.datagrid('getChecked');
                if(rows.length == 0){
                    $.messager.alert("提醒","请选择要删除的单据!");
                    return false;
                }else{
                    $("#bigSaleOut-delete-button").val("1");
                }
                for(var i=0;i<rows.length;i++){
                    $dgSourceBillList.datagrid('deleteRow',$dgSourceBillList.datagrid('getRowIndex',rows[i]));
                }
            });
		});
	</script>
  </body>
</html>

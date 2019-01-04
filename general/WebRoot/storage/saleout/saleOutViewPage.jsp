<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>发货单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-saleOutAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="saleOut.id" value="${saleOut.id}" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input id="storage-saleOut-businessdate" type="text" class="len150" value="${saleOut.businessdate }" name="saleOut.businessdate" readonly="readonly"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="storage-saleOut-status-select" disabled="disabled" class="len150">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == saleOut.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    					</select>
    					<input type="hidden" id="storage-saleOut-status" name="saleOut.status" value="${saleOut.status }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">出库仓库：</td>
	    				<td>
	    					<select id="storage-saleOut-storageid" name="storageOtherEnter.storageid" class="len150" disabled="disabled">
	    						<c:forEach items="${storageList }" var="list">
								<option value="${list.id }" <c:if test="${list.id == saleOut.storageid}">selected="selected"</c:if>>${list.name }</option>
	    						</c:forEach>
	    					</select>
	    					<input type="hidden" id="storage-saleOut-storageid-hidden"  name="saleOut.storageid" value="${saleOut.storageid }"/>
	    				</td>
	    				<td class="len80 left">客户:</td>
	    				<td colspan="3" style="text-align: left">
	    					<input type="text" id="storage-saleOut-customerid"  style="width: 300px;" value="<c:out value="${saleOut.customername}"></c:out>" readonly="readonly"/>
	    					<span id="storage-supplier-showid-saleOut" style="margin-left:5px;line-height:25px;">编号：${saleOut.customerid}</span>
	    					<input type="hidden" name="saleOut.customerid" value="${saleOut.customerid}"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td>
	    					<input type="text" class="len150" value="<c:out value="${saleOut.salesdeptname}"></c:out>" readonly="readonly"/>
	    					<input type="hidden" name="saleOut.salesdept" value="${saleOut.salesdept }"/>
	    				</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
	    					<input type="text"  class="len150" value="<c:out value="${saleOut.salesusername }"></c:out>" readonly="readonly"/>
	    					<input type="hidden" name="saleOut.salesuser" class="len130" value="${saleOut.salesuser }"/>
	    				</td>
	    				<td class="len80 left">排序：</td>
	    				<td>
							<select id="storage-saleOut-sortby" class="len150" >
								<option value="0" selected="selected">按商品编码排序</option>
								<option value="1" >按下单顺序排序</option>
							</select>
	    					<input type="hidden" name="saleOut.sourcetype" value="${saleOut.sourcetype }"/>
	    					<input type="hidden" id="storage-saleOut-sourceid" name="saleOut.sourceid" value="${saleOut.sourceid }"/>
                            <input type="hidden" id="storage-saleOut-saleorderid" value="${saleOut.saleorderid }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售单据：</td>
	    				<td class="len165"><input class="len150" readonly='readonly' value="${saleOut.saleorderid }" /></td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left">
	    					<input type="text" name="saleOut.remark" value="<c:out value="${saleOut.remark }"></c:out>" style="width: 415px;"/>
	    				</td>
	    			</tr>
	    		</table>
	  			<input type="hidden" id="storage-saleOut-printtimes" value="${saleOut.printtimes}" />	
	  			<input type="hidden" id="storage-saleOut-phprinttimes" value="${saleOut.phprinttimes}" />	
	  			<input type="hidden" id="storage-saleOut-printlimit" value="${printlimit }"/>
	    	<input type="hidden" id="storage-saleOut-fHPrintAfterSaleOutAudit" value="${fHPrintAfterSaleOutAudit }"/>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-saleOutAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-saleOut-saleOutDetail" name="saleOutDetailJson"/>
	    </form>
    </div>
    <div id="storage-dialog-saleOutAddPage"></div>
    <script type="text/javascript">
        var saleOutDetailListCache = JSON.parse('${saleOutDetailList}');

    	$(function(){
			$("#storage-datagrid-saleOutAddPage").datagrid({ //发货单明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: false,
    			checkOnSelect:true,
    			selectOnCheck:true,
    			data: JSON.parse('${saleOutDetailList}'),
    			rowStyler:function(index, row){
    				if(row.id!=null && row.isdiscount=="0"){
	    				if(row.summarybatchid==null || row.summarybatchid==""){
	    					return 'background-color:#6293BB;color:#fff;font-weight:bold;';
	    				}
    				}
    			},
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#storage-datagrid-saleOutAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-saleOutAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			}
    		}).datagrid('columnMoving');    
    	});
    	//计算合计
    	function countTotal(){
    		var rows =  $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
    		var countNum = 0;
    		var taxamount = 0;
    		var notaxamount = 0;
    		var tax = 0;
    		var auxnum = 0;
    		var auxremainder = 0;
    		for(var i=0;i<rows.length;i++){
    			countNum = Number(countNum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
    			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
    			notaxamount = Number(notaxamount)+Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
    			tax = Number(tax)+Number(rows[i].tax == undefined ? 0 : rows[i].tax);
    			auxnum = Number(auxnum)+Number(rows[i].auxnum == undefined ? 0 : rows[i].auxnum);
    			auxremainder = Number(auxremainder)+Number(rows[i].auxremainder == undefined ? 0 : rows[i].auxremainder);
    		}
    		var auxnumstr = "";
    		if(auxnum>0){
    			auxnumstr = formatterBigNum(auxnum);
    		}
    		if(auxremainder>0){
    			auxnumstr += "," + Number(auxremainder.toFixed(${decimallen}));
    		}
    		$("#storage-datagrid-saleOutAddPage").datagrid("reloadFooter",[{goodsid:'合计',unitnum:countNum,taxamount:taxamount,notaxamount:notaxamount,tax:tax,auxnumdetail:auxnumstr}]);
    	}
    	//控制按钮状态
    	$("#storage-buttons-saleOutPage").buttonWidget("setDataID",{id:'${saleOut.id}',state:'${saleOut.status}',type:'view'});

        if("1" == "${saleOut.isbigsaleout}"){
            $("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-audit");
            $("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-bottom-audit");
        }else{
            if("2" == "${saleOut.status}"){
                $("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-audit");
                $("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-bottom-audit");
            }
        }
  		
        $("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-print-orderblank");
  		$("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-printview-orderblank");
  		$("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-print-DeliveryOrder");
  		$("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-printview-DeliveryOrder");
  		
  		<c:if test="${saleOut.phprinttimes =='0'  or '0'==printlimit }">
  			$("#storage-buttons-saleOutPage").buttonWidget("enableMenuItem","button-print-orderblank");
		</c:if>
  		
  		<c:choose>
			<c:when test="${fHPrintAfterSaleOutAudit=='1' }">
				<c:if test="${(saleOut.status=='3' or saleOut.status=='4') and (saleOut.printtimes == '0' or '0'==printlimit) }">
	  				$("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-print-DeliveryOrder");
	  			</c:if>
			</c:when>
			<c:otherwise>
	  			$("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-print-DeliveryOrder");
			</c:otherwise>
		</c:choose>
  		
    	$("#storage-hidden-billid").val("${saleOut.id}");
    	<c:if test="${saleOut.sourcetype=='0'}">
	    	$("#storage-buttons-saleOutPage").buttonWidget("disableMenuItem","relation-upper-view");
	    </c:if>
	    <c:if test="${saleOut.sourcetype!='0'}">
	    	$("#storage-buttons-saleOutPage").buttonWidget("enableMenuItem","relation-upper-view");
	    </c:if>

        //生成类型改变
        $("#storage-saleOut-sortby").change(function(){
            var saleOutDetailList = saleOutDetailListCache;
            if(saleOutDetailList.length>1){
                if($(this).val()=="0"){
                    saleOutDetailList.sort(function(a,b){
                        return a.goodsid>b.goodsid});
                }else if($(this).val()=="1"){
                    saleOutDetailList.sort(function(a,b){
                        return a.seq>b.seq});
                }
                var a = $("#storage-datagrid-saleOutAddPage").datagrid("getRows");
                var length = a.length
                for(var i = 0 ; i<length; i++){
                    $("#storage-datagrid-saleOutAddPage").datagrid("deleteRow",0);
                }
                for(var j = 0 ; j < saleOutDetailList.length ; j++){
                    $("#storage-datagrid-saleOutAddPage").datagrid('insertRow', {index: j, row: saleOutDetailList[j]});
                }

                if(saleOutDetailList.length<10){
                    var j = 10-saleOutDetailList.length;
                    for(var i=0;i<j;i++){
                        $("#storage-datagrid-saleOutAddPage").datagrid('appendRow',{});
                    }
                }
            }
        });
    </script>
  </body>
</html>

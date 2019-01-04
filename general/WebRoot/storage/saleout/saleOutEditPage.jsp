<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
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
	    				<td class="len165"><input id="storage-saleOut-id" class="len150 easyui-validatebox" name="saleOut.id" value="${saleOut.id}" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="storage-saleOut-businessdate" class="len150 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${saleOut.businessdate }" name="saleOut.businessdate" /></td>
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
	    					<input type="text"  class="len150" value="<c:out value="${saleOut.salesusername }"></c:out>"readonly="readonly"/>
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
	    				<td colspan="3" style="text-align: left">	    					
	    					<input type="text" name="saleOut.remark" value="<c:out value="${saleOut.remark }"></c:out>" style="width: 415px;"/>
	    				</td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-saleOutAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-saleOut-saleOutDetail" name="detailJson" />
	    	<input type="hidden" id="storage-saveaudit-saleOutDetail" name="saveaudit" value="save"/>
  			<input type="hidden" id="storage-saleOut-printtimes" value="${saleOut.printtimes}" />	
  			<input type="hidden" id="storage-saleOut-phprinttimes" value="${saleOut.phprinttimes}" />	
  			<input type="hidden" id="storage-saleOut-printlimit" value="${printlimit }"/>
	    	<input type="hidden" id="storage-saleOut-fHPrintAfterSaleOutAudit" value="${fHPrintAfterSaleOutAudit }"/>
	    </form>
    </div>
    <div id="storage-contextMenu-saleOutAddPage" style="display: none;">
    	<div id="storage-editRow-saleOutAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<security:authorize url="/storage/saleOutDetailDelete.do">
    	<div id="storage-removeRow-saleOutAddPage" data-options="iconCls:'button-delete'">删除</div>
    	</security:authorize>
    </div>
    <div id="storage-dialog-saleOutAddPage"></div>
    <script type="text/javascript">

		var saleOutDetailListCache = JSON.parse('${saleOutDetailList}');

    	$(function(){
    		$('#storage-contextMenu-saleOutAddPage').menu({  
			    onClick:function(item){  
			    	var flag = item.disabled;
					if(flag==true){
						return false;
					}
					if(item.text=="编辑"){
						beginEditDetail();
					}else if(item.text=="删除"){
						removeDetail();
					}
	    			
			    }  
			}); 
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
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#storage-datagrid-saleOutAddPage").datagrid('selectRow', rowIndex);
                    $("#storage-contextMenu-saleOutAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    					//beginAddDetail();
    				}
    				else{
    					if(rowData.isdiscount=='1' || rowData.isdiscount=='2'){
    						//beginEditDetailDiscount();
    					}else{
    						$("#storage-datagrid-saleOutAddPage").datagrid('unselectAll');
    						$("#storage-datagrid-saleOutAddPage").datagrid('selectRow', rowIndex);
    						beginEditDetail();
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
    		$("#storage-form-saleOutAdd").form({  
			    onSubmit: function(){  
			    	var status = $("#storage-saleOut-status").val();
			    	var json = $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
			    	if(status=="2"){
				    	for(var i=0;i<json.length;i++){
				    		if(json[i].summarybatchid==null&&json[i].summarybatchid==""){
				    			$.messager.alert("提醒","请指定商品发货仓库等信息");
				    			return false;
				    		}
				    	}
			    	}
					$("#storage-saleOut-saleOutDetail").val(JSON.stringify(json));
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		if(json.auditflag){
			    			$.messager.alert("提醒","保存审核成功"+json.msg+"</br>生成销售发货回单，编号为："+json.receiptid);
			    			
			    			$("#storage-saleOut-status-select").val(3);
							$("#storage-buttons-saleOutPage").buttonWidget("setDataID",{id:'${saleOut.status}',state:'3',type:'view'});
							
					  		$("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-print-orderblank");
					  		$("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-printview-orderblank");
					  		
			    	  		$("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-print-DeliveryOrder");
			    	  		$("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-printview-DeliveryOrder");
			    	  		
			    	  		//刷新列表
			    	  		tabsWindowURL("/storage/showSaleOutListPage.do").$("#storage-datagrid-saleoutPage").datagrid('reload');
			    	  		//关闭当前标签页
			    	  		top.closeNowTab();
			    		}else if(json.editFlag){
			    			var id = $("#storage-saleOut-id").val();
			    			var msg = json.msg.replace(/\&lt;\/br&gt;/ig,"</br>");
			    			$.messager.alert("提醒","保存成功!</br>"+msg+"</br>");
							$("#storage-panel-saleOutPage").panel({
								href:'storage/saleOutEditPage.do?id='+id,
								title:'',
							    cache:false,
							    maximized:true,
							    border:false
							});
			    	  		//$("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-print-orderblank");
			    	  		//$("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-printview-orderblank");
			    	  		
			    	  		//$("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-print-DeliveryOrder");
			    	  		//$("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-printview-DeliveryOrder");
			    		}else{
			    			$.messager.alert("提醒","保存成功");
			    			var id = $("#storage-saleOut-id").val();
			    			var msg = json.msg.replace(/\&lt;\/br&gt;/ig,"</br>");
			    			$.messager.alert("提醒","保存成功!</br>"+msg+"</br>");
							$("#storage-panel-saleOutPage").panel({
								href:'storage/saleOutEditPage.do?id='+id,
								title:'',
							    cache:false,
							    maximized:true,
							    border:false
							});
						}
			    	}else{
			    		$.messager.alert("提醒","保存失败</br>"+json.msg);
			    	}
			    }  
			}); 
    	});
    	//显示发货单明细添加页面
    	function beginAddDetail(){
    		//验证表单
			var flag = $("#storage-form-saleOutAdd").form('validate');
			if(flag==false){
				return false;
			}
			var storageid = $("#storage-saleOut-storageid").widget("getValue");
			var customerid = $("#storage-saleOut-customerid").widget("getValue");
			var sourceid = $("#storage-saleOut-sourceid").val();
			var id = $("#storage-saleOut-id").val();
			$('<div id="storage-dialog-saleOutAddPage-content"></div>').appendTo('#storage-dialog-saleOutAddPage');
    		$('#storage-dialog-saleOutAddPage-content').dialog({  
			    title: '发货单明细添加',  
			    width: 680,  
			    height: 400,  
			    collapsible:false,
			    minimizable:false,
			    maximizable:true,
			    resizable:true,
			    closed: true,  
			    cache: false,  
			    href: 'storage/showSaleOutDetailAddPage.do?storageid='+storageid+"&customerid="+customerid+"&sourceid="+sourceid+"&id="+id,  
			    buttons:[
			    	{  
	                    text:'确定',  
	                    iconCls:'button-save',  
	                    plain:true,
	                    handler:function(){  
	                    	addSaveDetail(false);
	                    }  
	                },
	                {  
	                    text:'继续添加',
	                    iconCls:'button-add',
	                    plain:true,
	                    handler:function(){  
	                    	addSaveDetail(true);
	                    }  
	                }
			    ],
			    onClose:function(){
			    	$('#storage-dialog-saleOutAddPage-content').dialog("destroy");
			    }
			});
			$('#storage-dialog-saleOutAddPage-content').dialog("open");
    	}
    	//显示发货单明细修改页面
    	function beginEditDetail(){
    		//验证表单
			var flag = $("#storage-form-saleOutAdd").form('validate');
			if(flag==false){
				return false;
			}
			var row = $("#storage-datagrid-saleOutAddPage").datagrid('getSelected');
    		if(row == null){
    			$.messager.alert("提醒", "请选择一条记录");
    			return false;
    		}
    		if(row.goodsid == undefined){
    		}else{
    			if(row.isdiscount!='1'){
	    			var storageid = $("#storage-saleOut-storageid").widget("getValue");
                    var initnum = row.initnum;
                    if(null==initnum){
                        initnum = row.unitnum
                    }
	    			$('<div id="storage-dialog-saleOutAddPage-content"></div>').appendTo('#storage-dialog-saleOutAddPage');
		    		$('#storage-dialog-saleOutAddPage-content').dialog({  
					    title: '发货单明细修改',  
					    width: 680,  
					    height: 400,  
					    collapsible:false,
					    minimizable:false,
					    maximizable:true,
					    resizable:true,
					    closed: true,  
					    cache: false,  
					    href: 'storage/showSaleOutDetailEditPage.do?storageid='+storageid+"&goodsid="+row.goodsid+"&summarybatchid="+row.summarybatchid+"&initnum="+initnum,
					    modal: true,
					    onClose:function(){
					    	$('#storage-dialog-saleOutAddPage-content').dialog("destroy");
					    },
					    onLoad:function(){
					    	$("#storage-saleOut-unitnum").focus();
					    	$("#storage-saleOut-unitnum").select();

							formaterNumSubZeroAndDot();

							$("#storage-form-saleOutDetailEditPage").form('validate');
					    }
					});
					$('#storage-dialog-saleOutAddPage-content').dialog("open");
				}
			}
    	}
    	//保存发货单明细
    	function addSaveDetail(goFlag){ //添加新数据确定后操作，
    		var flag = $("#storage-form-saleOutDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#storage-form-saleOutDetailAddPage").serializeJSON();
    		var widgetJson = $("#storage-saleOut-goodsid").goodsWidget('getObject');
    		var goodsInfo = {id:widgetJson.goodsid,name:widgetJson.goodsname,brandName:widgetJson.brandname,
    						model:widgetJson.model,barcode:widgetJson.barcode,boxnum:widgetJson.boxnum};
    		form.goodsInfo = goodsInfo;
    		var rowIndex = 0;
    		var rows = $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
    		for(var i=0; i<rows.length; i++){
    			var rowJson = rows[i];
    			if(rowJson.goodsid == undefined){
    				rowIndex = i;
    				break;
    			}
    		}
    		if(rowIndex == rows.length - 1){
    			$("#storage-datagrid-saleOutAddPage").datagrid('appendRow',{}); //如果是最后一行则添加一新行
    		}
    		$("#storage-datagrid-saleOutAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
    		if(goFlag){ //go为true确定并继续添加一条
    			var storageid = $("#storage-saleOut-storageid").widget("getValue");
				var customerid = $("#storage-saleOut-customerid").widget("getValue");
				var url ='storage/showSaleOutDetailAddPage.do?storageid='+storageid+"&customerid="+customerid;
    			$("#storage-dialog-saleOutAddPage-content").dialog('refresh', url);
    		}
    		else{ //否则直接关闭
    			$("#storage-dialog-saleOutAddPage-content").dialog('destroy');
    		}
    		$("#storage-saleOut-storageid").widget('readonly',true);
    		countTotal();
    	}
    	//修改保存
    	function editSaveDetail(goFlag){
    		var flag = $("#storage-form-saleOutDetailEditPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#storage-form-saleOutDetailEditPage").serializeJSON();
    		var row = $("#storage-datagrid-saleOutAddPage").datagrid('getSelected');
    		var rowIndex = $("#storage-datagrid-saleOutAddPage").datagrid('getRowIndex', row);
    		form.goodsInfo = row.goodsInfo;
    		if(form.unitnum==0){
    			$.messager.alert("提醒", "数量为0的明细，审核之后将会直接删除。");
    		}
    		$("#storage-datagrid-saleOutAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
    		if(goFlag){ //go为true确定并继续添加一条
    			beginAddDetail();
    		}
    		else{ //否则直接关闭
    			$("#storage-dialog-saleOutAddPage-content").dialog('destroy');
    		}
    		countTotal();
    	}
    	//删除明细
    	function removeDetail(){
    		var row = $("#storage-datagrid-saleOutAddPage").datagrid('getSelected');
	    	if(row == null){
	    		$.messager.alert("提醒", "请选择一条记录");
	    		return false;
	    	}
	    	$.messager.confirm("提醒","确定删除该商品明细?",function(r){
		    	if(r){
			   		var rowIndex = $("#storage-datagrid-saleOutAddPage").datagrid('getRowIndex', row);
			   		$("#storage-datagrid-saleOutAddPage").datagrid('deleteRow', rowIndex);
			   		countTotal(); 
			   		var rows = $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
			   		var index = -1;
			   		for(var i=0; i<rows.length; i++){
			   			if(rows[i].goodsid != undefined){
			   				index = i;
			   				break;
			  			}
			   		}
		    	}
	    	});	
    	}
    	function submitSaleOut(){
    		var json = $("#storage-datagrid-saleOutAddPage").datagrid('getRows');
    		var flag = true;
	    	for(var i=0;i<json.length;i++){
	    		if(!isObjectEmpty(json[i])){
	    			if(Number(json[i].initnum) != Number(json[i].unitnum)){
		    			flag = false;
		    			break;
		    		}
	    		}
	    	}
	    	if(flag){
				$.messager.confirm("提醒","确定保存该发货单信息？",function(r){
					if(r){
						$("#storage-form-saleOutAdd").attr("action", "storage/editSaleOutSave.do");
							$("#storage-form-saleOutAdd").submit();
					}
				});
			}else{
				$.messager.confirm("提醒","发货数量与销售发货通知单中的数量不一致，是否确定保存？",function(r){
					if(r){
	 					$("#storage-form-saleOutAdd").attr("action", "storage/editSaleOutSave.do");
	 					$("#storage-form-saleOutAdd").submit();
	 				}
				});
			}
    	}
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
    	$("#storage-buttons-saleOutPage").buttonWidget("setDataID",{id:'${saleOut.id}',state:'${saleOut.status}',type:'edit'});

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

        if("1" == "${saleOut.isbigsaleout}"){
            $("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-audit");
            $("#storage-buttons-saleOutPage").buttonWidget("disableButton","button-bottom-audit");
        }else{
            if("2" == "${saleOut.status}"){
                $("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-audit");
                $("#storage-buttons-saleOutPage").buttonWidget("enableButton","button-bottom-audit");
            }
        }
  		
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

<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>配送单详情页面</title>
	</head>

	<body>
		<div id="delivery-layout-audit" class="easyui-layout" data-options="fit:true,border:false">
			<div id="delivery-layout-audit-north" data-options="region:'north',border:false" style="height:160px">
				<form id="delivery-form-head" action="" method="post" style="padding: 3px">
					<table cellspacing="3px" cellpadding="3px" border="0">
						<tbody>
							<tr>
								<td>编号:</td>
								<td><input id="delivery-id-baseInfo" type="text" style="width: 160px;" name="delivery.id" value="${delivery.id}" readonly="readonly" class="no_input" /></td>
								<td>出车日期:</td>
								<td><input id="delivery-businessdate-baseInfo" type="text" style="width: 160px;" name="delivery.businessdate" value="${delivery.businessdate}" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',onpicked:businessdateChange})" /></td>
								<td>状态:</td>
								<td>
									<select  style="width: 160px;" disabled="disabled" class="no_input">
										<c:forEach items="${statusList}" var="list">
			    							<c:choose>
			    								<c:when test="${list.code == delivery.status}">
			    									<option value="${list.code }" selected="selected">${list.codename }</option>
			    								</c:when>
			    								<c:otherwise>
			    									<option value="${list.code }">${list.codename }</option>
			    								</c:otherwise>
			    							</c:choose>
			    						</c:forEach>
									</select>
									<input value="${delivery.status}" id="delivery-hidden-status" type="hidden" name="delivery.status"/>
								</td>
							</tr>
							<tr>
								<td>司机:</td>
								<td><input id="delivery-widget-driverid" disabled style="width: 160px;" type="text" name="delivery.driverid" value="${delivery.driverid}"/></td>
							<%--<td>--%>
									<%--<table>--%>
										<%--<tr>--%>
											<%--<td><input id="delivery-widget-driverid" type="text" name="delivery.driverid" value="${delivery.driverid}"/></td>--%>
											<%--<td>跟车:</td>--%>
											<%--<td><input id="delivery-widget-followid" type="text" name="delivery.followid" value="${delivery.followid}"/></td>--%>
										<%--</tr>--%>
									<%--</table>--%>
								<%--</td>--%>
								<td>线路名称:</td>
								<td><input type="text" style="width: 160px;" name="delivery.linename" value="${delivery.linename}" readonly="readonly" class="no_input"/>
                                    <input type="hidden" id="delivery-hidden-lineid" value="${delivery.lineid}"/>
                                </td>
								<td>车号:</td>
								<td><input type="text" id="delivery-widget-carid" name="delivery.carid" value="<c:out value="${delivery.carid}"></c:out>" /></td>
							</tr>
							<tr>
								<td>跟车:</td>
								<td><input id="delivery-widget-followid" disabled style="width: 160px;" type="text" name="delivery.followid" value="${delivery.followid}"/></td>
								<td>装车次数:</td>
								<td><input id="delivery-truck-numberbox" type="text" name="delivery.truck" value="${delivery.truck}" style="width: 160px"/></td>
								<td>送货家数:</td>
								<td><input type="text" style="width: 160px;" id="delivery-input-customernums" name="delivery.customernums" value="${delivery.customernums}" data-options="min:0"/></td>
							</tr>
							<tr>
								<td>备注:</td>
								<td colspan="5"><input type="text" style="width: 630px;" name="delivery.remark" value="<c:out value="${delivery.remark}"></c:out>" /></td>
							</tr>
						</tbody>
					</table>
				</form>
				<ul class="tags" style="min-width: 400px">
					<security:authorize url="/storage/deliveryReceitpTab.do">
						<li id="firstli" class="selectTag">
							<a href="javascript:void(0)">交接单</a>
						</li>
					</security:authorize>
					<security:authorize url="/storage/deliveryDetailTab.do">
						<li>
							<a href="javascript:void(0)">发货明细</a>
						</li>
					</security:authorize>
					<security:authorize url="/storage/deliveryOtherTab.do">
						<li>
							<a href="javascript:void(0)">奖金补贴</a>
						</li>
					</security:authorize>
                    <security:authorize url="/storage/deliveryMapTab.do">
                        <li>
                            <a href="javascript:void(0)">地图</a>
                        </li>
                    </security:authorize>
					<security:authorize url="/storage/deliveryGoodsTab.do">
						<li>
							<a href="javascript:void(0)">商品汇总</a>
						</li>
					</security:authorize>
				</ul>
			</div>
			<div id="delivery-layout-audit-center" data-options="region:'center',border:false">
				<div class="tagsDiv" style="min-width: 1024px">
					<div class="tagsDiv_item">
						<table id="delivery-table-Customer"></table>
					</div>
					<div class="tagsDiv_item">
						<table id="delivery-table-Saleout"></table>
					</div>
					<div class="tagsDiv_item">
						<form id="delivery-form-other" action="" method="post" style="padding: 0px">
							<table cellspacing="5px" cellpadding="5px" border="0">
								<tbody>
									<tr>
										<td>晚上出车:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" name="delivery.nightbonus" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox" value="${delivery.nightbonus}"/></td>
										<td>安全奖金:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" name="delivery.safebonus" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox" value="${delivery.safebonus}"/></td>
										<td>回单奖金:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" name="delivery.receiptbonus" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox" value="${delivery.receiptbonus}"/></td>
									</tr>
									<tr>
										<td>其他补助:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" name="delivery.othersubsidy" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox" value="${delivery.othersubsidy}"/></td>
										<td>送货箱数:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" id="delivery-input-count" name="delivery.boxnum" data-options="min:0,max:9999999999,precision:3" class="easyui-numberbox no_input" value="${delivery.boxnum}" readonly="readonly" /></td>
										<td>收款金额:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" id="delivery-input-collectionamount" name="delivery.collectionamount" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox no_input" value="${delivery.collectionamount}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>出车津贴:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" name="delivery.carallowance" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox no_input" value="${delivery.carallowance}" readonly="readonly"/></td>
										<td>出车补助:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" name="delivery.carsubsidy" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox no_input" value="${delivery.carsubsidy}" readonly="readonly"/></td>
										<td>家数补助:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" id="delivery-input-customersubsidy" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox no_input" name="delivery.customersubsidy" value="${delivery.customersubsidy}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>收款补助:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" id="delivery-input-collectionsubsidy" name="delivery.collectionsubsidy" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox no_input" value="${delivery.collectionsubsidy}" readonly="readonly"/></td>
										<td>装车补助:</td>
										<td style="width: 5px"></td>
										<td><input id="delivery-numberbox-trucksubsidy" type="text" style="width: 160px;" name="delivery.trucksubsidy" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox no_input" value="${delivery.trucksubsidy}" readonly="readonly"/></td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
                    <div class="tagsDiv_item">
                        <div id="storage-dummy-deliveryAuditPage" style="display: none;"></div>
                    </div>
					<div class="tagsDiv_item">
						<div id="storage-goods-deliveryAuditPage" style="display: none;"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="easyui-menu" id="storage-contextMenu-deliveryCustomer">
	    	<div id="storage-addRow-deliveryCustomer" data-options="iconCls:'button-add'">添加</div>
	    	<div id="storage-editRow-deliveryCustomer" data-options="iconCls:'button-edit'">修改</div>
	    	<div id="storage-removeRow-deliveryCustomer" data-options="iconCls:'button-delete'">删除</div>
	    </div>
	    <div class="easyui-menu" id="storage-contextMenu-deliverySaleout">
	    	<div id="storage-addRow-deliverySaleout" data-options="iconCls:'button-add'">添加</div>
	    	<div id="storage-removeRow-deliverySaleout" data-options="iconCls:'button-delete'">删除</div>
	    </div>
	    <input type="hidden" id="deliver-printtimes-hidden" value="${delivery.printtimes}"/>

		<script type="text/javascript">
		var $dgSaleoutList = $("#delivery-table-Saleout");
		var $dgCustomerList = $("#delivery-table-Customer");
        var $goods = $("#storage-goods-deliveryAuditPage");
		var delivery_editIndexCustomer = undefined;
		var customerFooter;
		var saleoutid='null';
		var saleoutaddid='null';
		var saleoutdelid='null';
		var customerdelids = 'null';
		var oldtruck = "${delivery.truck}";
		var weight = 999999999;
		var volume = 999999999;
		var boxnum = 999999999;
		var carvolume = Number("${carvolume}");
		
		function delivery_endEditingCustomer(){
   			if (delivery_editIndexCustomer == undefined){
   				return true;
   			}else{return false;}
   		}
		//添加交接单
		$("#storage-addRow-deliveryCustomer").click(function(){
			$('#delivery-dialog-customer').dialog({
			    title: '交接单新增',  
			    width: 540,
			    height: 320, 
			    closed: false,
			    cache: false,
			    href:'storage/showDeliveryAddCustomerPage.do',
			    modal: true
			});
    	});
		//编辑交接单
		$("#storage-editRow-deliveryCustomer").click(function(){
			var row = $dgCustomerList.datagrid('getSelected');
			if(row.customerid==undefined)
				return;
    		var rowIndex = $dgCustomerList.datagrid('getRowIndex', row);
    		if(delivery_endEditingCustomer()){
				$dgCustomerList.datagrid('beginEdit', rowIndex);
				delivery_editIndexCustomer = rowIndex;
			}
		});
		//删除交接单
		$("#storage-removeRow-deliveryCustomer").click(function(){
			var row = $dgCustomerList.datagrid('getSelected');
    		if(delivery_endEditingCustomer()){
				if(row.issaleout == "1"){
					$.messager.confirm("警告","该客户在发货明细中有关联单据,是否确定删除？",function(r){
						if(r){
							row = $dgCustomerList.datagrid('getSelected');
							if('null' == customerdelids){
								customerdelids = "";
							}
							if(!isObjectEmpty(row)){
								customerdelids += row.customerid + ",";
								var rowIndex = $dgCustomerList.datagrid('getRowIndex', row);
								$dgCustomerList.datagrid('deleteRow',rowIndex);
							}
							if("" != customerdelids){
								$("#delivery-delcustomerids-baseinfo").val(customerdelids);
								var customernums = $('#delivery-input-customernums').numberbox('getValue');
								setCustomerFooter();
								loadSaleoutListDel();
								row = $dgCustomerList.datagrid('getRows');
								if(row.length<=12){
									for(var i=row.length;i<12;i++){
										$dgCustomerList.datagrid('appendRow',{});
									}
								}
                                $('#delivery-input-customernums').numberbox('setValue', customernums - 1);
							}
						}
					});
				}else{
					var rowIndex = $dgCustomerList.datagrid('getRowIndex', row);
		    		$dgCustomerList.datagrid('deleteRow',rowIndex);
					setCustomerFooter();
					loadSaleoutListDel();
					row = $dgCustomerList.datagrid('getRows');
					if(row.length<=12){
						for(var i=row.length;i<12;i++){
							$dgCustomerList.datagrid('appendRow',{});
						}
					}
				}
			}
		});
    	
    	//添加发货单
    	$("#storage-addRow-deliverySaleout").click(function(){
    		var saleoutid="";
          	var rows = $dgSaleoutList.datagrid('getRows');
           	for(var i=0;i<rows.length;i++){
           		if(saleoutid == ""){
           			saleoutid = rows[i].saleoutid ;
           		}else{
           			saleoutid += "," + rows[i].saleoutid ;
           		}
           	}
           	var footerrows = $dgSaleoutList.datagrid('getFooterRows');
			weight=footerrows[0].weight;
			volume=footerrows[0].volume;
			boxnum=footerrows[0].boxnum;
            var lineid = $("#delivery-hidden-lineid").val();
            var carid = $("#delivery-widget-carid").widget('getValue');
            var deliveryid = $("#delivery-id-baseInfo").val();
			$('<div id="delivery-dialog-saleout1"></div>').appendTo('#delivery-dialog-saleout');
            $('#delivery-dialog-saleout1').dialog({  
			    title: '发货单新增',  
			    width: 600,
			    height: 450,
			    fit:true,
			    closed: true,
			    cache: false,
			    resizable: true,
			    maximizable:true,
			    href:'storage/showDeliveryAddSaleoutPage.do',
				queryParams:{lineid:lineid,carid:carid,deliveryid:deliveryid,ids:saleoutid,weight:weight,volume:volume,boxnum:boxnum},
				modal: true,
			    onClose:function(){
			    	$('#delivery-dialog-saleout1').dialog("destroy");
			    }
			});
			$("#delivery-dialog-saleout1").dialog("open");
    	});
    	
    	//删除发货单
    	$("#storage-removeRow-deliverySaleout").click(function(){
	    	$.messager.confirm("警告","是否删除该发货单?<br/>删除之后，交接单的列表内容会被刷新！",function(r){
       			if(r){
	       			loading("删除中...");
	       			if('null'==saleoutdelid){
	             		saleoutdelid = "";
	             	}
	       			var rows = $dgSaleoutList.datagrid('getChecked');
	        		for(var i=0;i<rows.length;i++){
	        			if(saleoutdelid == ""){
							saleoutdelid = rows[i].saleoutid;
						}else{
							saleoutdelid += "," + rows[i].saleoutid;
						}
	        			var index=$dgSaleoutList.datagrid('getRowIndex',rows[i]);
	        			$dgSaleoutList.datagrid('deleteRow',index);
	        		}
	             	if('null' == saleoutid){
	        			saleoutid = "";
	        		}
		          	rows = $dgSaleoutList.datagrid('getRows');
		           	for(var i=0;i<rows.length;i++){
		           		if(!isObjectEmpty(rows[i])){
		           			if(saleoutid == ""){
			           			saleoutid = rows[i].saleoutid ;
			           		}else{
			           			saleoutid += "," + rows[i].saleoutid ;
			           		}
		           		}
		           	}
					setSaleoutFooter();
					
					if(rows.length<=12){
						for(var i=rows.length;i<12;i++){
							$dgSaleoutList.datagrid('appendRow',{});
						}
					}
					loadDgCustomerListdel();
					loaded();
            	}
             });
    	});
		
		function setSaleoutFooter()
		{
			var footerrows = $dgSaleoutList.datagrid('getFooterRows');
	   		footer=footerrows[0];
   			var volume = 0;
        	var weight = 0;
        	var boxnum = 0;
       		var salesamount = 0;
			var rows=$dgSaleoutList.datagrid('getRows');
			for(var i=0;i<rows.length;i++){
				if(!isObjectEmpty(rows[i])){
					volume = Number(volume)+Number(rows[i].volume);
					weight = Number(weight)+Number(rows[i].weight);
					boxnum = Number(boxnum)+Number(rows[i].boxnum);
					salesamount = Number(salesamount) + Number(rows[i].salesamount);
				}
			}
			footer.volume=volume;
			footer.weight=weight;
			footer.boxnum=Number(boxnum.toFixed(3));
			footer.salesamount=salesamount;
			
			$dgSaleoutList.datagrid("reloadFooter",[footer]);
		}
		function setCustomerFooter()
		{
			var salesamount = 0;
			var total = 0;
   			var collectionsubsidy=0;
   			var boxnum = 0;
   			var volume = 0;
        	var weight = 0;
   			var billnums = 0;
   			//计算送货家数
   			var precustomer = "";
   			var num = "";

   			var rows=$dgCustomerList.datagrid('getRows');
			for(var i=0;i<rows.length;i++){
				if(!isObjectEmpty(rows[i])){
					salesamount = Number(salesamount)+Number(rows[i].salesamount == undefined ? 0 : rows[i].salesamount);
					total = Number(total)+Number(rows[i].collectionamount == undefined ? 0 : rows[i].collectionamount);
					boxnum = Number(boxnum)+Number(rows[i].boxnum == undefined ? 0 : rows[i].boxnum);
					volume = Number(volume)+Number(rows[i].volume == undefined ? 0 : rows[i].volume);
					weight = Number(weight)+Number(rows[i].weight == undefined ? 0 : rows[i].weight);
					billnums = Number(billnums)+Number(rows[i].billnums == undefined ? 0 : rows[i].billnums);
					
					if(precustomer == "" || precustomer != rows[i].customerid){
						num++;
						precustomer = rows[i].customerid;
					}
				}
			}
			// $("#delivery-input-customernums").numberbox('setValue',Number(num));
			
			var footerrows = $dgCustomerList.datagrid('getFooterRows');
			customerFooter=footerrows[0];
			customerFooter.salesamount=salesamount;
			customerFooter.collectionamount=total;
			customerFooter.boxnum=Number(boxnum.toFixed(3));
			customerFooter.volume=volume;
			customerFooter.weight=weight;
			customerFooter.billnums=billnums;

			//计算车辆体积各参数
			var remaindervolume = carvolume - volume;//剩余体积
			var fullloadrate = Number(volume/carvolume)*100;//满载率
			var volumemsg = "总体积："+carvolume+" 已配体积："+Number(volume).toFixed(4)+" 剩余体积："+Number(remaindervolume).toFixed(4)+" 满载率："+formatterMoney(fullloadrate)+"%";
			customerFooter.customername = volumemsg;

			$dgCustomerList.datagrid("reloadFooter",[customerFooter]);
			$("#delivery-input-count").numberbox('setValue',boxnum);
			$("#delivery-input-collectionamount").numberbox('setValue',total);
            var base = Number("${base}");
            var s = Number("${s}");
            var us = Number("${us}");
            if(total>base)
                collectionsubsidy=Number(total-base)*s+Number(base*us);
            else
                collectionsubsidy=Number(total)*us;
			$("#delivery-input-collectionsubsidy").numberbox('setValue',collectionsubsidy);
		}		
		
		//删除客户明细单据明细刷新
		function loadSaleoutListDel(){
			if($dgSaleoutList.hasClass("create-datagrid") && 'null' != customerdelids && "" != customerdelids){
				var rows = $dgSaleoutList.datagrid('getRows');
				for(var j=0;j<rows.length;j++){
					if(!isObjectEmpty(rows[j]) && customerdelids.indexOf(rows[j].customerid) != -1){
						$dgSaleoutList.datagrid('deleteRow',j);
						j--;
					}
				}
				setSaleoutFooter();
			}
		}
		
		//添加发货明细刷新交接单明细
		function loadDgCustomerListdel(){
			var rows = $dgCustomerList.datagrid('getRows');
			$.ajax({
	            url :'storage/getDeliveryCustomerListBySaleoutids.do',
	            type:'post',
	            data:{id:saleoutdelid,deliveryid:"${delivery.id}",lineid:"${delivery.lineid}"},
	            dataType:'json',
	            success:function(json){
	            	var delrows = json;
    				if(delrows.length != 0){
    					for(var i=0;i<delrows.length;i++){
    						var delobj = delrows[i];
    						$dgCustomerList.datagrid('clearSelections');
    						var flag = true;
	   						for(var j=0;j<rows.length;j++){
	   							var selectRow = rows[j];
	   							if(!isObjectEmpty(selectRow) && selectRow.issaleout == "1" && selectRow.isedit != '1' && selectRow.isedit != '2'
	   								&& delobj.customerid == selectRow.customerid){
	   								flag = false;
	   								var index = $dgCustomerList.datagrid('getRowIndex',selectRow);
		    						delobj.billnums = Number(selectRow.billnums) - Number(delobj.billnums);
		    						if(delobj.billnums < 0){
		    							delobj.billnums = Number(0);;
		    						}
		    						delobj.salesamount = Number(selectRow.salesamount).toFixed(2) - Number(delobj.salesamount).toFixed(2);
		    						if(delobj.salesamount < 0){
		    							delobj.salesamount = Number(0);
		    						}
		    						delobj.auxnum = Number(selectRow.auxnum).toFixed(2) - Number(delobj.auxnum).toFixed(2);
		    						if(delobj.auxnum < 0){
		    							delobj.auxnum = Number(0);
		    						}
		    						delobj.auxremainder = Number((Number(selectRow.auxremainder) - Number(delobj.auxremainder)).toFixed(general_bill_decimallen));
		    						if(delobj.auxremainder < 0){
		    							delobj.auxremainder = Number(0);
		    						}
		    						delobj.weight = Number(selectRow.weight).toFixed(4) - Number(delobj.weight).toFixed(4);
		    						if(delobj.weight < 0){
		    							delobj.weight = Number(0);
		    						}
		    						delobj.volume = Number(selectRow.volume).toFixed(4) - Number(delobj.volume).toFixed(4);
		    						if(delobj.volume < 0){
		    							delobj.volume = Number(0);
		    						}
		    						delobj.boxnum = Number((Number(selectRow.boxnum) - Number(delobj.boxnum)).toFixed(3));
		    						if(delobj.boxnum < 0){
		    							delobj.boxnum = Number(0);
		    						}
		    						delobj.collectionamount = Number(selectRow.collectionamount).toFixed(2) - Number(delobj.collectionamount).toFixed(2);
		    						if(delobj.collectionamount < 0){
		    							delobj.collectionamount = Number(0);
		    						}
		    						delobj.isedit = selectRow.isedit;
		    						if(delobj.billnums == 0 && Number(delobj.salesamount) == 0 && Number(delobj.collectionamount) == 0){
		    							$dgCustomerList.datagrid('deleteRow',index);
		    						}else{
		    							$dgCustomerList.datagrid('updateRow',{
		    								index:index,
		    								row:delobj
		    							});
		    						}
	   							}
	   						}
	   						saleoutdelid = 'null';
	    					loadDgCustomerList();
	    					setCustomerFooter();
    					}
    				}
	            }
	        });
		}
		//添加发货明细刷新交接单明细
		function loadDgCustomerListadd(lineid){
			var rows = $dgCustomerList.datagrid('getRows');
			$.ajax({
	            url :'storage/getDeliveryCustomerListBySaleoutids.do',
	            type:'post',
	            data:{id:saleoutaddid,deliveryid:"${delivery.id}",lineid:lineid},
	            dataType:'json',
	            success:function(json){
	            	var addrow = json;
    				if(addrow.length != 0){
    					for(var i=0;i<addrow.length;i++){
	   						var addobj = addrow[i];
	   						if('null' != customerdelids && "" != customerdelids){
	   							if(customerdelids.indexOf(addobj.customerid) != -1){
	   								customerdelids = customerdelids.replace(addobj.customerid+",","");
	   							}
	   							$("#delivery-delcustomerids-baseinfo").val(customerdelids);
	   						}
	   						$dgCustomerList.datagrid('clearSelections');
	   						var flag = true;
	   						for(var j=0;j<rows.length;j++){
	   							var selectRow = rows[j];
	   							if(!isObjectEmpty(selectRow) && selectRow.issaleout == "1" && addobj.customerid == selectRow.customerid){
	   								flag = false;
   									var index = $dgCustomerList.datagrid('getRowIndex',selectRow);
	    							addobj.billnums = Number(selectRow.billnums) + Number(addobj.billnums);
		    						addobj.salesamount = Number(selectRow.salesamount) + Number(addobj.salesamount);
		    						addobj.auxnum = Number(selectRow.auxnum) + Number(addobj.auxnum);
		    						addobj.auxremainder = Number(Number(selectRow.auxremainder) + Number(addobj.auxremainder));
		    						addobj.weight = Number(selectRow.weight) + Number(addobj.weight);
		    						addobj.volume = Number(selectRow.volume) + Number(addobj.volume);
		    						addobj.boxnum = Number((Number(selectRow.boxnum) + Number(addobj.boxnum)).toFixed(3));
		    						addobj.collectionamount = Number(selectRow.collectionamount) + Number(addobj.collectionamount);
		    						addobj.isreceipt = selectRow.isreceipt;
		    						addobj.isedit = selectRow.isedit;
		    						if(addobj.isedit == "1"){
		    							addobj.isedit = "2";
		    						}
		    						$dgCustomerList.datagrid('updateRow',{
	    								index:index,
	    								row:addobj
	    							});
	   							}
	   						}
	   						if(flag){
	   							$dgCustomerList.datagrid('appendRow',addobj);
	   						}
	   					}
	   					saleoutaddid = 'null';
	   					loadDgCustomerList();
	   					setCustomerFooter();
    				}
	            }
	        });
		}
		
		function loadDgCustomerList(){
			loading("加载中...");
			var oldrows = $dgCustomerList.datagrid('getRows');
			var oldrow = [];
			for(var i=0;i<oldrows.length;i++){
				if(!isObjectEmpty(oldrows[i])){
					oldrow.push(oldrows[i]);
				}
			}
			if(oldrow.length != 0){
				$dgCustomerList.datagrid('loadData',oldrow);
			}
			loaded();
		}
		
		function table2Json(table,name){
   			if(table!=null){
	   			if(!table.hasClass("create-datagrid"))
	   				return null;
   				var effectRow = new Object();
   				var date = table.datagrid('getRows');
   				if(date!=null){
	   				effectRow[name] = JSON.stringify(date);
	   				return effectRow;
   				}
   			}
	        return null;
   		}
		
		function delivery_JSONs(){
			var wholeInfo = $("#delivery-form-head").serializeJSON();
			var other =  $("#delivery-form-other").serializeJSON();
			var saleoutList = table2Json($dgSaleoutList,'saleoutList');
			var customerList = table2Json($dgCustomerList,'customerList');
			for(key in other){
				wholeInfo[key] = other[key];
			};
			for(key in customerList){
				wholeInfo[key] = customerList[key];
			};
			return wholeInfo;
		}
		
		function editDelivery(){
			loading('提交中...');
			var	ret;
			$.ajax({
				url:'storage/editDelivery.do',
				dataType:'json',
				type:'post',
				async:false,
				data:delivery_JSONs(),
				success:function(json){
					loaded();
					ret=json;
					return json;
				}
			});
			return ret;
		}
		
		function saveAuditDelivery(){
			loading('提交中...');
			var	ret;
			$.ajax({
				url:'storage/saveAuditDelivery.do',
				dataType:'json',
				type:'post',
				data:delivery_JSONs(),
				async:false,
				success:function(json){
					loaded();
					ret=json;
					return json;
				}
			});
			return ret;
		}
		
		function checkDelivery(){
          	var rows = $dgCustomerList.datagrid('getRows');
           	for(var i=0;i<rows.length;i++){
           		
           		var collectionamount =Number(rows[i].collectionamount);
           		var isreceipt = rows[i].isreceipt
           		if(collectionamount<=0&&isreceipt=='0')
           		{
           			$.messager.alert("提醒","收款和回单必须有其一!");
           			return false;
           		}
           	}
           	var id=$("#delivery-id-baseInfo").val();
		  	var truck = $("#delivery-truck-numberbox").numberbox('getValue');
		  	if(oldtruck != truck){
			  	var carid = $("#delivery-widget-carid").widget("getValue");
		  		var businessdate = $("#delivery-businessdate-baseInfo").val();
				var ret = delivery_AjaxConn({id:id,truck:truck,carid:carid,businessdate:businessdate},'storage/isExistSameTruck.do');
				var retjson = $.parseJSON(ret);
				if(retjson.flag){
					$.messager.alert("提醒","已存在相同出车日期、车号下的该装车次数！");
					$("#delivery-truck-numberbox").numberbox('setValue',retjson.truck);
					return false;
				}
		  	}
			loading("验收中...");
           	$.ajax({
				url:'storage/checkDelivery.do?id=${delivery.id}',
				dataType:'json',
				type:'post',
				data:delivery_JSONs(),
				success:function(json){
	            	loaded();
	            	if(json.ckflag){
						$.messager.alert("提醒","已存在相同出车日期、车号下的该装车次数！");
						return false;
					}
	            	if(json.flag){
	            	    $.messager.alert("提醒","验收成功");
	            	    if (top.$('#tt').tabs('exists',title)){
		    				tabsWindow(title).$("#storage-datagrid-deliveryPage").datagrid('reload');
		    			}
	            	    refreshPanel('${delivery.id}','view');
	            		
	            	}else{
	            		$.messager.alert("提醒","验收失败");
	            	}
	            },
	            error:function(){
	            	loaded();
	            	$.messager.alert("错误","验收出错");
	            }
			});
		}
		
		$(function(){
			if(${delivery.status}!=3){
				refreshPanel('${delivery.id}',getType('${delivery.status}'));
			}
				
			$("#storage-buttons-deliveryPage").buttonWidget("setDataID",{id:"${delivery.id}",state:"${delivery.status}",type:"edit"});
			if(${delivery.status}==3){
				$("#storage-buttons-deliveryPage").buttonWidget("enableButton","button-check");
				$("#storage-buttons-deliveryPage").buttonWidget("enableButton","printMenuButton");
				$("#storage-buttons-deliveryPage").buttonWidget("enableButton","button-save");
			}else{
				$("#storage-buttons-deliveryPage").buttonWidget("disableButton","button-check");
				$("#storage-buttons-deliveryPage").buttonWidget("disableButton","printMenuButton");
			}
			if(${delivery.status}==4){
				$("#storage-buttons-deliveryPage").buttonWidget("enableButton","button-unCheck");
			}else{
				$("#storage-buttons-deliveryPage").buttonWidget("disableButton","button-unCheck");
			}
			
			$("#delivery-input-customernums").blur(function(){
				var customersubsidy;
				var customernums=$("#delivery-input-customernums").val();
				if(customernums>${line.basecustomers}){
					customersubsidy=customernums*${line.singleallowance};
				}
				else{
					customersubsidy=${line.baseallowance}
				}
				$("#delivery-input-customersubsidy").numberbox('setValue',customersubsidy);
			});
			
			//司机
			$("#delivery-widget-driverid").widget({
				referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
				required:true,
				width:160,
				singleSelect:true
			});
			//跟车
			$("#delivery-widget-followid").widget({
				referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
				width:160,
				singleSelect:true
			});
			//车辆
			$("#delivery-widget-carid").widget({
				referwid:'RL_T_BASE_LOGISTICS_LINE_CAR',
				param:[{field:'lineid',op:'equal',value:"${delivery.lineid}"}],
				required:true,
				width:160,
				singleSelect:true
			});
			
			//装车次数
			$("#delivery-truck-numberbox").numberbox({
				min:0,
   				precision:0,
   				onChange:function(newVal,oldVal){
   					var carid = $("#delivery-widget-carid").widget("getValue");
   					$.ajax({
   						url:'storage/getTruckSubsidyByTruck.do',
						dataType:'json',
						type:'post',
						data:{truck:newVal,carid:carid},
						success:function(json){
							$("#delivery-numberbox-trucksubsidy").numberbox('setValue',json.trucksubsidy);
						}
   					});
   				}
			});
			
			//送货家数
			$("#delivery-input-customernums").numberbox({
				min:0,
   				precision:0,
   				onChange:function(newVal,oldVal){
   					$.ajax({
   						url:'storage/getCustomersubsidyByCustomernums.do',
						dataType:'json',
						type:'post',
						data:{customernums:newVal,lineid:"${delivery.lineid}"},
						success:function(json){
							$("#delivery-input-customersubsidy").numberbox('setValue',json.customersubsidy);
						}
   					});
   				}
			});
			
			$(".tags").find("li").click(function(){
				var height = $("#delivery-layout-audit-center").height()-5;
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 0){
					if(saleoutid!='null'){
						loadDgCustomerList();
						saleoutid='null';
					}
					if(!$dgCustomerList.hasClass("create-datagrid")){
						$dgCustomerList.datagrid({
				   			authority:customerTableColJson,
			    			columns: customerTableColJson.common,
			    			frozenColumns: customerTableColJson.frozen,
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				   			singleSelect:true,
				   			height:height,
				  			url:'storage/showDeliveryCustomerList.do?id=${delivery.id}',
					  		border:false,
					  		showFooter:true,
					  		onSortColumn:function(sort, order){
					  			$dgCustomerList.datagrid({
							       	url:''
							    });
			    				var rows = $dgCustomerList.datagrid("getRows");
			    				var dataArr = [];
			    				for(var i=0;i<rows.length;i++){
			    					if(rows[i].customerid!=null && rows[i].customerid!=""){
			    						dataArr.push(rows[i]);
			    					}
			    				}
			    				dataArr.sort(function(a,b){
			    					if($.isNumeric(a[sort])){
				    					if(order=="asc"){
				    						return Number(a[sort])>Number(b[sort])?1:-1
				    					}else{
				    						return Number(a[sort])<Number(b[sort])?1:-1
				    					}
			    					}else{
			    						if(order=="asc"){
				    						return a[sort]>b[sort]?1:-1
				    					}else{
				    						return a[sort]<b[sort]?1:-1
				    					}
			    					}
			    				});
			    				$dgCustomerList.datagrid("loadData",dataArr);
			    				return false;
			    			},
					  		onLoadSuccess:function(data){
					  			var rows = $dgCustomerList.datagrid('getRows');
			    				var leng = rows.length;
			    				if(leng < 12){
			    					for(var i=leng; i<12; i++){
			    						$dgCustomerList.datagrid('appendRow',{});
			    					}
			    				}
			    				else
			    					$dgCustomerList.datagrid('appendRow',{});
			    				setCustomerFooter();
		    				},
		    				onRowContextMenu:function(e, rowIndex, rowData){
			    				e.preventDefault();
			    				$dgCustomerList.datagrid('selectRow', rowIndex);
			    				var $divid = document.getElementById("storage-removeRow-deliveryCustomer")
			    				if(rowData.issaleout == "1"){
			    					$divid.style.display = "none";
			    				}else{
			    					$divid.style.display = "block";
			    				}
			                    $("#storage-contextMenu-deliveryCustomer").menu('show', {  
			                        left:e.pageX,  
			                        top:e.pageY  
			                    });
			    			},
			   				onDblClickRow:function(rowIndex, rowData){
			   					if(rowData.customerid==undefined)
			   					{
									$('#delivery-dialog-customer').dialog({
									    title: '交接单新增',  
									    width: 540,
									    height: 320,
									    closed: false,
									    cache: false,
									    href:'storage/showDeliveryAddCustomerPage.do',
									    modal: true
									});
								}
				                else if(delivery_endEditingCustomer()){
									$dgCustomerList.datagrid('beginEdit', rowIndex);
									delivery_editIndexCustomer = rowIndex;
								}
			   				},
			   				onClickRow:function(rowIndex, rowData){
            					if(!delivery_endEditingCustomer()){
            					
						   			$dgCustomerList.datagrid('clearSelections');
						   			$dgCustomerList.datagrid('endEdit', delivery_editIndexCustomer);
						   			
						   			delivery_editIndexCustomer = undefined;
						   			setCustomerFooter();
            					}
			   				}
				    	}).datagrid("columnMoving");
						$dgCustomerList.addClass("create-datagrid");
					}
				}
				if(index == 1){
					if(!$dgSaleoutList.hasClass("create-datagrid")){
						$dgSaleoutList.datagrid({
				   			authority:saleoutTableColJson,
			    			columns: saleoutTableColJson.common,
			    			frozenColumns: saleoutTableColJson.frozen,
				   			method:'post',
				   			title:'',
				   			rownumbers:true,
				   			height:height,
				  			url:'storage/showDeliverySaleoutList.do?id=${delivery.id}',
					  		border:false,
					  		showFooter:true,
					  		singleSelect:false,
				  			checkOnSelect:true,
				  			selectOnCheck:true,
					  		onLoadSuccess:function(data){
		    					var footerrows = $dgSaleoutList.datagrid('getFooterRows');
		    					
		    					weight=footerrows[0].weight;
		    					volume=footerrows[0].volume;
		    					boxnum=footerrows[0].boxnum;
		    				}
				    	}).datagrid("columnMoving");
						$dgSaleoutList.addClass("create-datagrid");
					}
				}
                if(index == 3) {

                    if(!window.mapframe1) {
                        var height = $(window).height() - 170;
                        if(height < 100) {
                            height = 100;
                        }
                        $('#storage-dummy-deliveryAuditPage').after('<iframe name="mapframe1" src="storage/deliveryCustomerMapPage.do?type=view&deliveryid=<c:out value="${delivery.id }"></c:out>" style="width: 100%; height: ' + height + 'px; border: 0px;"></iframe>');
                    }
                }

                if(index == 4) {
                    if(!$goods.hasClass("create-datagrid")){
                        $goods.datagrid({
                            columns: [[
                                {field:'goodsid',title:'商品编码',width:80,sortable:false},
                                {field:'goodsname',title:'商品名称',width:220,sortable:false},
                                {field:'unitnum',title:'数量',width:80,align: 'right', sortable:false},
                                {field:'auxnumdetail',title:'辅数量',width:100,align: 'right', sortable:false}
                            ]],
                            method:'get',
                            rownumbers:true,
                            height:height,
                            url:'storage/showDeliveryGoodsSumData.do?id=${delivery.id}',
                            border:false,
                            showFooter:true,
                            singleSelect:false,
                            checkOnSelect:true,
                            selectOnCheck:true,
                            onLoadSuccess:function(data){
                            }
                        }).datagrid("columnMoving");
                        $goods.addClass("create-datagrid");
                    }
                }

			});
			setTimeout(function(){
				$("#firstli").click();
			},50);
		});

        /**
         *
         */
        function getCustomerBills() {

            var customerBills = {};
            var rows;
            try {
                rows = $dgSaleoutList.datagrid('getRows')
            } catch (e) {
                $.ajax({
                    type: 'post',
                    url: 'storage/showDeliverySaleoutList.do',
                    data: {id: '${delivery.id}'},
                    dataType: 'json',
                    async: false,
                    success: function(json) {
                        rows = json.rows;
                    },
                    error: function () {
                        rows = new Array();
                    }
                });
            }

            for(var i in rows) {

                var row = rows[i];
                if(!customerBills[row.customerid]) {
                    customerBills[row.customerid] = {bills: new Array()};
                }

                customerBills[row.customerid].bills.push(row);
            }

            return customerBills;
        }
	</script>
	</body>
</html>

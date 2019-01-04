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
		<div id="delivery-layout-view" class="easyui-layout" data-options="fit:true,border:false">
			<div id="delivery-layout-view-north" data-options="region:'north',border:false" style="height:160px">
                <form id="delivery-form-head" action="" method="post" style="padding: 3px">
					<table cellspacing="3px" cellpadding="3px" border="0">
						<tbody>
							<tr>
								<td>编号:</td>
								<td><input id="delivery-id-baseInfo" type="text" style="width: 160px;" name="delivery.id" value="${delivery.id}" readonly="readonly" class="no_input" /></td>
								<td>出车日期:</td>
								<td><input type="text" id="delivery-businessdate-baseInfo" style="width: 160px;" name="delivery.businessdate" value="${delivery.businessdate}" readonly="readonly" class="no_input" /></td>
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
									<input value="${delivery.status}" type="hidden" name="delivery.status"/>
								</td>
							</tr>
							<tr>
								<td>司机:</td>
								<td><input id="delivery-widget-driverid" type="text" name="delivery.driverid" value="${delivery.driverid}" disabled="disabled"/></td>
								<td>线路名称:</td>
								<td><input type="text" style="width: 160px;" name="delivery.linename" value="<c:out value="${delivery.linename}"></c:out>" readonly="readonly" class="no_input" /></td>
								<td>车号:</td>
								<td><input type="text" style="width: 160px;" name="carname" value="<c:out value="${delivery.carname}"></c:out>" readonly="readonly" class="no_input"/></td>
							</tr>
							<tr>
								<td>跟车:</td>
								<td><input id="delivery-widget-followid" type="text" name="delivery.followid" value="${delivery.followid}" disabled="disabled"/></td>
								<td>装车次数:</td>
								<td><input name="delivery.truck" value="${delivery.truck}" readonly="readonly" class="no_input" style="width: 160px" /></td>
								<td>送货家数:</td>
								<td><input type="text" style="width: 160px;" id="delivery-input-customernums" name="delivery.customernums" value="${delivery.customernums}" readonly="readonly" class="no_input"/> </td>
							</tr>
							<tr>
								<td>备注:</td>
								<td  colspan="5"><input type="text" style="width: 640px;" name="delivery.remark" value="<c:out value="${delivery.remark}"></c:out>" readonly="readonly" class="no_input"/></td>
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
					<security:authorize url="/storage/deliveryGoodsTab.do">
						<li>
							<a href="javascript:void(0)">商品汇总</a>
						</li>
					</security:authorize>
                    <security:authorize url="/storage/deliveryMapTab.do">
                        <li>
                            <a href="javascript:void(0)">地图</a>
                        </li>
                    </security:authorize>
				</ul>
			</div>
			<div id="delivery-layout-view-center" data-options="region:'center',border:false">
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
										<td><input type="text" style="width: 160px;" name="delivery.nightbonus" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox" value="${delivery.nightbonus}" disabled="disabled"/></td>
										<td>安全奖金:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" name="delivery.safebonus" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox" value="${delivery.safebonus}" disabled="disabled"/></td>
										<td>回单奖金:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" name="delivery.receiptbonus" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox" value="${delivery.receiptbonus}" disabled="disabled"/></td>
									</tr>
									<tr>
										<td>其他补助:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" name="delivery.othersubsidy" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox" value="${delivery.othersubsidy}" disabled="disabled"/></td>
										<td>送货箱数:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" id="delivery-input-count" name="delivery.boxnum" data-options="min:0,max:9999999999,precision:3" class="easyui-numberbox no_input" value="${delivery.boxnum}" readonly="readonly"/></td>
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
										<td><input type="text" style="width: 160px;" id="delivery-input-customersubsidy" name="delivery.customersubsidy" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox no_input" value="${delivery.customersubsidy}" readonly="readonly"/></td>
									</tr>
									<tr>
										<td>收款补助:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" id="delivery-input-collectionsubsidy" name="delivery.collectionsubsidy" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox no_input" value="${delivery.collectionsubsidy}" readonly="readonly"/></td>
										<td>装车补助:</td>
										<td style="width: 5px"></td>
										<td><input type="text" style="width: 160px;" name="delivery.trucksubsidy" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox no_input" value="${delivery.trucksubsidy}" readonly="readonly"/></td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
					<security:authorize url="/storage/deliveryGoodsTab.do">
						<div class="tagsDiv_item">
							<div id="storage-goods-deliveryViewPage" style="display: none;"></div>
						</div>
					</security:authorize>
					<security:authorize url="/storage/deliveryMapTab.do">
						<div class="tagsDiv_item">
							<div id="storage-dummy-deliveryViewPage" style="display: none;"></div>
						</div>
					</security:authorize>
				</div>
			</div>
		</div>

		<script type="text/javascript">
		var delivery_editIndexCustomer = undefined;
		var customerFooter;
		var saleoutid='null';
		var weight;
		var volume;
		var carvolume = Number("${carvolume}");
								
		function delivery_endEditingCustomer(){
   			if (delivery_editIndexCustomer == undefined){
   				return true
   			}else{return false;}
   		}
   		
		var $dgSaleoutList = $("#delivery-table-Saleout");
		var $dgCustomerList = $("#delivery-table-Customer");
        var $goods = $("#storage-goods-deliveryViewPage");
		
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
				volume = Number(volume)+Number(rows[i].volume);
				weight = Number(weight)+Number(rows[i].weight);
				boxnum = Number(boxnum)+Number(rows[i].boxnum);
				salesamount = Number(salesamount) + Number(rows[i].salesamount);
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

   			var rows=$dgCustomerList.datagrid('getRows');
			for(var i=0;i<rows.length;i++){
				salesamount = Number(salesamount)+Number(rows[i].salesamount == undefined ? 0 : rows[i].salesamount);
				total = Number(total)+Number(rows[i].collectionamount == undefined ? 0 : rows[i].collectionamount);
				boxnum = Number(boxnum)+Number(rows[i].boxnum == undefined ? 0 : rows[i].boxnum);
				volume = Number(volume)+Number(rows[i].volume == undefined ? 0 : rows[i].volume);
				weight = Number(weight)+Number(rows[i].weight == undefined ? 0 : rows[i].weight);
				billnums = Number(billnums)+Number(rows[i].billnums == undefined ? 0 : rows[i].billnums);
			}
			var footerrows = $dgCustomerList.datagrid('getFooterRows');
			customerFooter=footerrows[0];
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
		function loadDgCustomerList()
		{
			var rows = $dgCustomerList.datagrid('getRows');
			var row = [];
			$.ajax({
	            url :'storage/getDeliveryCustomerListBySaleoutids.do',
	            type:'post',
	            data:{id:saleoutid,deliveryid:"${delivery.id}",lineid:"${delivery.lineid}"},
	            dataType:'json',
	            success:function(json){
	            	for(var i=0;i<rows.length;i++){
	            		if(rows[i].issaleout == "0"){
	            			json.push(rows[i]);
	            		}
	            	}
	            	
    				if(json.length != 0){
    					$dgCustomerList.datagrid('loadData',json);
    				}
	            }
	        });
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
			
			//for(key in saleoutList){
			//	wholeInfo[key] = saleoutList[key];
			//};
			for(key in other){
				wholeInfo[key] = other[key];
			};
			for(key in customerList){
				wholeInfo[key] = customerList[key];
			};
			//if(saleoutid!='null'){
			//	wholeInfo['saleoutid']=saleoutid;
			//}
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
					ret=json;
					return json;
				}
			});
			return ret;
		}
		
		function checkDelivery(){
			loading("验收中...");
			$.ajax({
				url:'storage/checkDelivery.do?id=${delivery.id}',
				dataType:'json',
				type:'post',
				data:delivery_JSONs(),
				success:function(json){
	            	loaded();
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
		
		function unCheckDelivery(){
			loading("反验收中...");
			$.ajax({
				url:'storage/unCheckDelivery.do?id=${delivery.id}',
				dataType:'json',
				type:'post',
				data:delivery_JSONs(),
				success:function(json){
	            	loaded();
	            	if(json.flag){
	            	    $.messager.alert("提醒","反验收成功");
	            	    if (top.$('#tt').tabs('exists',title)){
		    				tabsWindow(title).$("#storage-datagrid-deliveryPage").datagrid('reload');
		    			}
	            	    refreshPanel('${delivery.id}','audit');
	            		
	            	}else{
	            		$.messager.alert("提醒","反验收失败");
	            	}
	            },
	            error:function(){
	            	loaded();
	            	$.messager.alert("错误","反验收出错");
	            }
			});
		}
		
		$(function(){
			if("${delivery.status}"!="4"){
				refreshPanel('${delivery.id}',getType('${delivery.status}'));
			}
				
			$("#storage-buttons-deliveryPage").buttonWidget("setDataID",{id:"${delivery.id}",state:"${delivery.status}",type:"edit"});
			if("${delivery.status}"=="3"){
				$("#storage-buttons-deliveryPage").buttonWidget("enableButton","button-check");
				$("#storage-buttons-deliveryPage").buttonWidget("enableButton","printMenuButton");
				$("#storage-buttons-deliveryPage").buttonWidget("enableButton","button-save");
			}else{
				$("#storage-buttons-deliveryPage").buttonWidget("disableButton","button-check");
				$("#storage-buttons-deliveryPage").buttonWidget("disableButton","printMenuButton");
			}
			if("${delivery.status}"=="4"){
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
				width:160,
				singleSelect:true
			});
			//跟车
			$("#delivery-widget-followid").widget({
				referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
				width:160,
				singleSelect:true
			});
			
			$(".tags").find("li").click(function(){
				var height = $("#delivery-layout-view-center").height()-5;
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
					  		singleSelect:true,
					  		onLoadSuccess:function(data){
		    					var footerrows = $dgSaleoutList.datagrid('getFooterRows');
		    					
		    					weight=footerrows[0].weight;
		    					volume=footerrows[0].volume;
		    				}
				    	}).datagrid("columnMoving");
						$dgSaleoutList.addClass("create-datagrid");
					}
				}
                if(index == 4) {

                    if(!window.mapframe1) {
                        var height = $(window).height() - 170;
                        if(height < 100) {
                            height = 100;
                        }
                        $('#storage-dummy-deliveryViewPage').after('<iframe name="mapframe1" src="storage/deliveryCustomerMapPage.do?type=view&deliveryid=<c:out value="${delivery.id }"></c:out>" style="width: 100%; height: ' + height + 'px; border: 0px;"></iframe>');
                    }
                }

                if(index == 3) {
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

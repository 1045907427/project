<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>应收交接单修改页面</title>
  </head>
  
  <body>
  	<div id="account-layout-receipthanddetail" class="easyui-layout" data-options="fit:true,border:false">
  		<div id="account-layout-receipthanddetail-north" data-options="region:'north',border:false" style="height:140px">
	  		<form id="receiptHand-form-head" action="" method="post" style="padding: 3px;">
		    	<table cellpadding="3" cellspacing="3" border="0">
		    		<tr>
		   				<td>编号：</td>
		   				<td><input id="receipthand-id-baseinfo" type="text" name="receiptHand.id" value="${receiptHand.id }" class="no_input" readonly="readonly" style="width: 160px;"/></td>
		   				<td>业务日期：</td>
		   				<td><input id="receipthand-businessdate-baseinfo" type="text" name="receiptHand.businessdate" value="${receiptHand.businessdate }" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 160px;" disabled="disabled"/></td>
		   				<td>状态：</td>
		   				<td>
		   					<select  style="width: 160px;" disabled="disabled" class="no_input">
								<c:forEach items="${statusList}" var="list">
		   							<c:choose>
		   								<c:when test="${list.code == receiptHand.status}">
		   									<option value="${list.code }" selected="selected">${list.codename }</option>
		   								</c:when>
		   								<c:otherwise>
		   									<option value="${list.code }">${list.codename }</option>
		   								</c:otherwise>
		   							</c:choose>
		   						</c:forEach>
							</select>
							<input type="hidden" name="receiptHand.status" value="${receiptHand.status}"/>
							<input type="hidden" id="receiptHand-delcustomerids-baseinfo" name="receiptHand.delcustomerids"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>领单人：</td>
		   				<td><input id="receipthand-handuserid-baseinfo" type="text" name="receiptHand.handuserid" value="${receiptHand.handuserid }"/></td>
		   				<td>领单人部门：</td>
		   				<td><input id="receipthand-handdeptid-baseinfo" type="text" name="receiptHand.handdeptid" value="${receiptHand.handdeptid }"/></td>
		   				<td>客户家数：</td>
		   				<td>
		   					<table>
		   						<tr>
		   							<td><input id="receipthand-cnums-baseinfo" type="text" name="receiptHand.cnums" value="${receiptHand.cnums }" class="easyui-numberbox" data-options="min:0,precision:0" style="width: 40px;" readonly="readonly"/></td>
		   							<td>打印次数：</td>
		   							<td><input id="receipthand-printtimes-baseinfo" type="text" name="receiptHand.printtimes" value="${receiptHand.printtimes }" class="easyui-numberbox no_input" data-options="min:0,precision:0" style="width: 40px;" readonly="readonly"/></td>
		   						</tr>
		   					</table>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>发货金额：</td>
		   				<td><input id="receipthand-totalamount-baseinfo" type="text" name="receiptHand.totalamount" value="${receiptHand.totalamount }" class="easyui-numberbox no_input" data-options="precision:2" style="width: 160px;" readonly="readonly"/></td>
		   				<td>应收金额：</td>
		   				<td><input id="receipthand-collectamount-baseinfo" type="text" name="receiptHand.collectamount" value="${receiptHand.collectamount }" class="easyui-numberbox no_input" data-options="precision:2" style="width: 160px;" readonly="readonly"/>
		   					<input type="hidden" id="receipthand-uncollectamount-baseinfo" name="receiptHand.uncollectamount" value="${receiptHand.uncollectamount }"/>
		   				</td>
		   				<td>备注：</td>
		   				<td><input type="text" name="receiptHand.remark" value="${receiptHand.remark }" style="width: 160px;" /></td>
		   			</tr>
		    	</table>
		    </form>
		    <ul class="tags" style="min-width: 400px">
				<security:authorize url="/account/receipthand/customerDetailTab.do">
					<li id="firstli" class="selectTag">
						<a href="javascript:void(0)">客户明细</a>
					</li>
				</security:authorize>
				<security:authorize url="/account/receipthand/billDetailTab.do">
					<li>
						<a href="javascript:void(0)">单据明细</a>
					</li>
				</security:authorize>
			</ul>
  		</div>
	  	<div id="account-layout-receipthanddetail-center" data-options="region:'center',border:false">
			<div class="tagsDiv" style="min-width: 1024px">
				<div class="tagsDiv_item">
					<table id="receipthand-table-customer"></table>
				</div>
				<div class="tagsDiv_item">
					<table id="receipthand-table-bill"></table>
				</div>
			</div>
	  	</div>
  	</div>
	<div class="easyui-menu" id="receipthand-contextMenu-customer" style="display: none;">
    	<div id="receipthand-editRow-customer" data-options="iconCls:'button-edit'">修改</div>
    	<div id="receipthand-removeRow-customer" data-options="iconCls:'button-delete'">删除</div>
    </div>
	<div class="easyui-menu" id="receipthand-contextMenu-bill" style="display: none;">
    	<div id="receipthand-addRow-bill" data-options="iconCls:'button-add'">添加</div>
    	<div id="receipthand-removeRow-bill" data-options="iconCls:'button-delete'">删除</div>
    </div>
	<script type="text/javascript">
		var $dgCustomerList = $("#receipthand-table-customer");
		var $dgBillList = $("#receipthand-table-bill");
		var receipthand_endEditIndexCustomer = undefined;
		var relatebilldelid = 'null';//删除
		var relatebilladdid = 'null';//添加
		var relatebillids = 'null';//关联单据编码（回单编码）
		var exsitreceiptids = "";
		var customerdelids = 'null';
								
		function receipthand_endEditCustomer(){
   			if (receipthand_endEditIndexCustomer == undefined){
   				return true;
   			}else{return false;}
   		}
   		
   		//修改交接单客户明细
   		$("#receipthand-editRow-customer").click(function(){
   			var row = $dgCustomerList.datagrid('getSelected');
			if(row.customerid==undefined)
				return;
    		var rowIndex = $dgCustomerList.datagrid('getRowIndex', row);
    		if(receipthand_endEditCustomer()){
				$dgCustomerList.datagrid('beginEdit', rowIndex);
				receipthand_endEditIndexCustomer = rowIndex;
			}
   		});
   		//删除交接单客户明细
   		$("#receipthand-removeRow-customer").click(function(){
   			var row = $dgCustomerList.datagrid('getSelected');
			if(row.customerid==undefined)
				return;
			if(receipthand_endEditCustomer()){
				$.messager.confirm("警告","该客户在单据明细中有关联单据,是否确定删除？",function(r){
					if(r){
						if('null' == customerdelids){
							customerdelids = "";
						}
						if(!isObjectEmpty(row)){
							customerdelids += row.customerid + ",";
							var rowIndex = $dgCustomerList.datagrid('getRowIndex', row);
							$dgCustomerList.datagrid('deleteRow', rowIndex);
						}
						if("" != customerdelids){
							$("#receiptHand-delcustomerids-baseinfo").val(customerdelids);
							setCustomerFooter();
							loadBillListDel();
						}
					}
				});
			}
   		});
   		
		//添加交接单单据明细（追加）
		$("#receipthand-addRow-bill").click(function(){
			var receiptids = "";
			var rows = $dgBillList.datagrid('getRows');
			for(var i=0;i<rows.length;i++){
				var object = rows[i];
				if(!isObjectEmpty(object)){
					if(receiptids == ""){
						receiptids = object.relatebillid;
					}else{
						receiptids += "," + object.relatebillid;
					}
				}
			}
			exsitreceiptids = receiptids;
			$('#account-dialog-receiptHandAddReceipt').dialog({  
			    title: '单据明细新增',
			    width: 600,
			    height: 450,
				fit:true,
			    closed: false,
			    cache: false,
			    resizable: true,
			    maximizable:true,
			    href:'account/receipthand/showReceiptHandAddReceiptPage.do?receipthandid=${receiptHand.id}',
			    modal: true
			});
		});
		//删除交接单明细
		$("#receipthand-removeRow-bill").click(function(){
			$.messager.confirm("警告","是否删除该发货回单?<br/>删除之后，客户明细列表内容会被刷新！",function(r){
				if(r){
					loading("删除中...");
					if('null' == relatebilldelid){
						relatebilldelid = "";
					}
					var rows = $dgBillList.datagrid('getChecked');
					for(var i=0;i<rows.length;i++){
						if(relatebilldelid == ""){
							relatebilldelid = rows[i].relatebillid;
						}else{
							relatebilldelid += "," + rows[i].relatebillid;
						}
	        			var index=$dgBillList.datagrid('getRowIndex',rows[i]);
	        			$dgBillList.datagrid('deleteRow',index);
	        		}
	        		rows = $dgBillList.datagrid('getRows');
	        		if('null' == relatebillids){
	        			relatebillids = "";
	        		}
					for(var i=0;i<rows.length;i++){
						var object = rows[i];
						if(!isObjectEmpty(object)){
							if(relatebillids == ""){
								relatebillids = object.relatebillid;
							}else{
								relatebillids += "," + object.relatebillid;
							}
						}
					}
					
					setBillFooter();
					
					if(rows.length<=12)
						for(var i=rows.length;i<12;i++){
							$dgBillList.datagrid('appendRow',{});
						}
					loadDgCustomerListdel();
					loaded();
				}
			});
		});
		
		//删除单据明细触发客户明细
		function loadDgCustomerListdel(){
			$.ajax({
	            url :'account/receipthand/getReceiptHandCustomerListByBills.do',
	            type:'post',
	            data:{relatebillid:relatebilldelid,billid:"${receiptHand.id}"},
	            dataType:'json',
	            success:function(json){
	            	var delrows = json.list;
    				if(delrows.length != 0){
    					for(var i=0;i<delrows.length;i++){
    						var delobj = delrows[i];
    						$dgCustomerList.datagrid('clearSelections');
    						$dgCustomerList.datagrid('selectRecord',delobj.customerid);
    						var selectRow = $dgCustomerList.datagrid('getSelected');
    						//修改过的客户数据不允许刷新数据
    						if(selectRow.isedit != '1' && selectRow.isedit != '2'){
	    						var index = $dgCustomerList.datagrid('getRowIndex',selectRow);
	    						delobj.billnums = Number(selectRow.billnums) - Number(delobj.billnums);
	    						if(delobj.billnums < 0){
	    							delobj.billnums = Number(0);;
	    						}
	    						delobj.amount = Number(selectRow.amount).toFixed(2) - Number(delobj.amount).toFixed(2);
<!--	    						if(delobj.amount < 0){-->
<!--	    							delobj.amount = Number(0);-->
<!--	    						}-->
	    						delobj.collectionamount = Number(selectRow.collectionamount).toFixed(2) - Number(delobj.collectionamount).toFixed(2);
<!--	    						if(delobj.collectionamount < 0){-->
<!--	    							delobj.collectionamount = Number(0);-->
<!--	    						}-->
	    						delobj.isedit = selectRow.isedit;
	    						if(delobj.billnums == 0 && Number(delobj.amount) == 0 && Number(delobj.collectionamount) == 0){
	    							if(delobj.isedit == "0" || delobj.isedit == null){
	    								$dgCustomerList.datagrid('deleteRow',index);
	    							}else{
	    								$dgCustomerList.datagrid('updateRow',{
		    								index:index,
		    								row:delobj
		    							});
	    							}
	    						}else{
	    							if(delobj.isedit == "1"){
	    								if((Number(delobj.amount) == 0 && Number(delobj.collectionamount) == 0) && delobj.billnums != 0){
		    								delobj.isedit = "2";
		    							}
	    							}
	    							$dgCustomerList.datagrid('updateRow',{
	    								index:index,
	    								row:delobj
	    							});
	    						}
	    					}
	    					relatebilldelid = 'null';
	    					loadDgCustomerList();
	    					setCustomerFooter();
    					}
    				}
	            }
	        });
		}
		
		//添加单据明细触发客户明细
		function loadDgCustomerListadd(){
			$.ajax({
	            url :'account/receipthand/getReceiptHandCustomerListByBills.do',
	            type:'post',
	            data:{relatebillid:relatebilladdid,billid:"${receiptHand.id}"},
	            dataType:'json',
	            success:function(json){
	            	var addrow = json.list;
    				if(addrow.length != 0){
    					for(var i=0;i<addrow.length;i++){
	   						var addobj = addrow[i];
	   						if('null' != customerdelids && "" != customerdelids){
	   							if(customerdelids.indexOf(addobj.customerid) != -1){
	   								customerdelids = customerdelids.replace(addobj.customerid+",","");
	   							}
	   							$("#receiptHand-delcustomerids-baseinfo").val(customerdelids);
	   						}
	   						$dgCustomerList.datagrid('clearSelections');
	   						$dgCustomerList.datagrid('selectRecord',addobj.customerid);
    						var selectRow = $dgCustomerList.datagrid('getSelected');
    						if(null != selectRow){
    							var index = $dgCustomerList.datagrid('getRowIndex',selectRow);
    							addobj.billnums = Number(selectRow.billnums) + Number(addobj.billnums);
	    						addobj.amount = Number(selectRow.amount) + Number(addobj.amount);
	    						addobj.collectionamount = Number(selectRow.collectionamount) + Number(addobj.collectionamount);
	    						addobj.isedit = selectRow.isedit;
	    						if(addobj.isedit == "1"){
	    							addobj.isedit = "2";
	    						}
	    						$dgCustomerList.datagrid('updateRow',{
    								index:index,
    								row:addobj
    							});
    						}else{
    							$dgCustomerList.datagrid('appendRow',addobj);
    						}
	   					}
	   					relatebilladdid = 'null';
	   					loadDgCustomerList();
	   					setCustomerFooter();
    				}
	            }
	        });
		}
		
		//点击单据客户明细刷新
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
		//删除客户明细单据明细刷新
		function loadBillListDel(){
			if($dgBillList.hasClass("create-datagrid") && 'null' != customerdelids && "" != customerdelids){
				var rows = $dgBillList.datagrid('getRows');
				for(var j=0;j<rows.length;j++){
					if(!isObjectEmpty(rows[j]) && customerdelids.indexOf(rows[j].customerid) != -1){
						$dgBillList.datagrid('deleteRow',j);
						j--;
					}
				}
				setBillFooter();
			}
		}
		
		function setBillFooter(){
			var footerrows = $dgBillList.datagrid('getFooterRows');
	   		footer=footerrows[0];
   			var amount = 0;
			var rows=$dgBillList.datagrid('getRows');
			for(var i=0;i<rows.length;i++){
				var objec = rows[i];
				if(!isObjectEmpty(objec)){
					amount = Number(amount) + Number(objec.amount);
				}
			}
			footer.amount=amount;
			$dgBillList.datagrid("reloadFooter",[footer]);
		}
		
		function setCustomerFooter(){
			var billnums = 0;
			var amount = 0;
   			var collectionamount=0;
   			//计算客户家数
   			var precustomer = "";
   			var num = "";

   			var rows=$dgCustomerList.datagrid('getRows');
			for(var i=0;i<rows.length;i++){
				var object = rows[i];
				if(!isObjectEmpty(object)){
					billnums = Number(billnums)+Number(object.billnums);
					amount = Number(amount)+Number(object.amount);
					collectionamount = Number(collectionamount)+Number(object.collectionamount);
					
					if(precustomer == "" || precustomer != object.customerid){
						num++;
						precustomer = object.customerid;
					}
				}
			}
			$("#receipthand-cnums-baseinfo").numberbox('setValue',Number(num));
			$("#receipthand-totalamount-baseinfo").numberbox('setValue',Number(amount));
			$("#receipthand-collectamount-baseinfo").numberbox('setValue',Number(collectionamount));
			$("#receipthand-uncollectamount-baseinfo").val(Number(amount)-Number(collectionamount));
			var footerrows = $dgCustomerList.datagrid('getFooterRows');
			customerFooter=footerrows[0];
			customerFooter.billnums=billnums;
			customerFooter.amount=amount;
			customerFooter.collectionamount=collectionamount;
			$dgCustomerList.datagrid("reloadFooter",[customerFooter]);
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
   		
   		function receipthand_JSONs(){
    		var wholeInfo = $("#receiptHand-form-head").serializeJSON();
    		var billList = table2Json($dgBillList,'billList');
			var customerList = table2Json($dgCustomerList,'customerList');
			if(null != billList && billList.length != 0){
				for(key in billList){
					wholeInfo[key] = billList[key];
				};
			}
			if(null != customerList && customerList.length != 0){
				for(key in customerList){
					wholeInfo[key] = customerList[key];
				};
			}
    		return wholeInfo;
    	}
		
		$(function(){
			$("#account-buttons-receipthand").buttonWidget("setDataID",{id:"${receiptHand.id}",state:"${receiptHand.status}",type:"edit"});
			if("${receiptHand.status}" != "3"){
				$("#account-buttons-receipthand").buttonWidget("disableMenu","menuButton");
			}else{
				$("#account-buttons-receipthand").buttonWidget("enableMenu","menuButton");
			}
			
			//领单人
			$("#receipthand-handuserid-baseinfo").widget({
                name:'t_account_receipt',
                col:'handuserid',
    			width:160,
    			required:true,
    			singleSelect:true,
    			onSelect:function(data){
    				$("#receipthand-handdeptid-baseinfo").widget('setValue',data.deptid);
    			}
			});
			//领单人所属部门
			$("#receipthand-handdeptid-baseinfo").widget({
                name:'t_account_receipt',
                col:'handdeptid',
    			width:160,
    			singleSelect:true,
    			onlyLeafCheck:true
			});
			
			$(".tags").find("li").click(function(){
				var height = $("#account-layout-receipthanddetail-center").height()-5;
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 0){
					if('null' != relatebillids){
						if(relatebillids != ""){
							loadDgCustomerList();
							relatebilldelid = 'null';
							relatebilladdid = 'null';
							relatebillids = 'null';
						}else{
							setCustomerFooter();
							relatebilliddel = 'null';
							relatebilladdid = 'null';
							relatebillids = 'null';
						}
					}
					if(!$dgCustomerList.hasClass("create-datagrid")){
						$dgCustomerList.datagrid({
							authority:customerTableColJson,
			    			columns: customerTableColJson.common,
			    			frozenColumns: customerTableColJson.frozen,
				   			method:'post',
				   			height:height,
				   			idField:'customerid',
				   			rownumbers:true,
				   			url:'account/receipthand/getReceiptHandCustomerListByBillid.do?id=${receiptHand.id }',
					  		border:false,
					  		showFooter:true,
					  		singleSelect:true,
					  		rowStyler: function(index,row){
								if (row.isedit == "1" || row.isedit == "2"){
									return 'background-color:rgb(190, 250, 241);';
								}
							},
					  		onLoadSuccess:function(data){
					  			var length = data.rows.length;
					  			if(length < 12){
					  				for(var i=length; i<12; i++){
			    						$(this).datagrid('appendRow',{});
			    					}
					  			}else{
					  				$(this).datagrid('appendRow',{});
					  			}
					  			setCustomerFooter();
					  		},
					  		onRowContextMenu:function(e, rowIndex, rowData){
			    				e.preventDefault();
			    				$(this).datagrid('selectRow', rowIndex);
			                    $("#receipthand-contextMenu-customer").menu('show', {  
			                        left:e.pageX,  
			                        top:e.pageY  
			                    });
			    			},
					  		onDblClickRow:function(rowIndex, rowData){
					  			if(rowData.customerid != undefined && receipthand_endEditCustomer()){
					  				$(this).datagrid('beginEdit', rowIndex);
									receipthand_endEditIndexCustomer = rowIndex;
					  			}
					  		},
					  		onClickRow:function(rowIndex, rowData){
					  			if(!receipthand_endEditCustomer()){
					  				$(this).datagrid('clearSelections');
						   			$(this).datagrid('endEdit', receipthand_endEditIndexCustomer);
						   			
						   			receipthand_endEditIndexCustomer = undefined;
						   			setCustomerFooter();
					  			}
					  		}
						});
						$dgCustomerList.addClass("create-datagrid");
					}
				}
				if(index == 1){
					loadBillListDel();
					if(!$dgBillList.hasClass("create-datagrid")){
						$dgBillList.datagrid({
							authority:billTableColJson,
			    			columns: billTableColJson.common,
			    			frozenColumns: billTableColJson.frozen,
			    			url:'account/receipthand/getReceiptHandBillListByBillid.do?id=${receiptHand.id }',
				   			method:'post',
				   			rownumbers:true,
					  		border:false,
					  		height:height,
					  		showFooter:true,
					  		singleSelect:false,
					  		checkOnSelect:true,
				  			selectOnCheck:true,
				  			onLoadSuccess:function(data){
		    					var rows = $(this).datagrid('getRows');
			    				var leng = rows.length;
			    				if(leng < 12){
			    					for(var i=leng; i<12; i++){
			    						$(this).datagrid('appendRow',{});
			    					}
			    				}
			    				else
			    					$(this).datagrid('appendRow',{});
			    				loadBillListDel();
		    				},
					  		onRowContextMenu:function(e, rowIndex, rowData){
			    				e.preventDefault();
			    				$(this).datagrid('selectRow', rowIndex);
			                    $("#receipthand-contextMenu-bill").menu('show', {  
			                        left:e.pageX,  
			                        top:e.pageY  
			                    });
			    			},
			    			onDblClickRow:function(rowIndex, rowData){
			   					var receiptids = "";
								var rows = $(this).datagrid('getRows');
								for(var i=0;i<rows.length;i++){
									var object = rows[i];
									if(!isObjectEmpty(object)){
										if(receiptids == ""){
											receiptids = object.relatebillid;
										}else{
											receiptids += "," + object.relatebillid;
										}
									}
								}
								exsitreceiptids = receiptids;
								$('#account-dialog-receiptHandAddReceipt').dialog({  
								    title: '单据明细新增',
								    width: 600,
								    height: 450,
									fit:true,
								    closed: false,
								    cache: false,
								    resizable: true,
								    maximizable:true,
								    href:'account/receipthand/showReceiptHandAddReceiptPage.do?receipthandid=${receiptHand.id}',
								    modal: true
								});
			   				}
						});
						$dgBillList.addClass("create-datagrid");
					}
				}
			});
			setTimeout(function(){
				$("#firstli").click();
			},50);
			
		});
	</script>
  </body>
</html>

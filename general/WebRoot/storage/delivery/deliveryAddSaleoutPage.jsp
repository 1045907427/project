<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>配送单新增</title>
	</head>

	<body>
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center'<security:authorize url="/storage/deliveryMapTab.do"><%--,region:'west'--%></security:authorize>" style="width: 450px;">
				<table id="storage-datagrid-deliverySourcePage"></table>
				<div id="storage-toolbar-query-deliverySouceBill" style="width: 450px;padding: 0px">
					<form id="storage-form-query-deliverySouceBill">
						<table class="">
							<tr>
								<td>线路名称：</td>
								<td><input type="text" id="storage-lineid-deliverySourceQueryPage" name="lineid" value="${lineid}" /></td>
                                <td>线路内/外：</td>
                                <td>
                                    <select id="storage-input-inline" name="inline" style="width: 150px;">
                                        <option></option>
                                        <option value="1" selected="selected">线路内</option>
                                        <option value="0">线路外</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
								<td>车辆名称：</td>
								<td><input type="text" id="storage-carid-deliverySourceQueryPage" name="carid" value="${carid}" readonly="readonly" /></td>
								<td>销售区域：</td>
								<td><input type="text" id="storage-salesarea-deliverySourceQueryPage" name="salesarea"/></td>
                            </tr>
                            <tr>
                                <td>客户名称：</td>
                                <td><input type="text" id="storage-customerid-deliverySourceQueryPage" name="customerid"/></td>
                                <td>调入仓库：</td>
                                <td><input type="text" id="storage-storageid-deliverySourceQueryPage" name="storageid"/></td>
                            </tr>
                            <tr>
                                <td>备　　注：</td>
                                <td><input type="text" id="storage-input-remark" name="remark" style="width: 150px;"/></td>
                            </tr>
                            <tr>
                                <td colspan="2"></td>
                                <td colspan="2" align="right" id="storage-buttons-deliveryAddSaleoutPage">
                                    <a href="javaScript:void(0);" id="storage-query-deliveryAdd" class="button-qr">查询</a>
                                    <a href="javaScript:void(0);" id="storage-reload-deliveryAdd" class="button-qr">重置</a>
                                </td>
                            </tr>
						</table>
					</form>
				</div>
			</div>
            <div data-options="region:'south',border:false">
                <div class="buttonDetailBG" style="text-align:right;">
                    <input type="button" value="确 定" name="savenogo" id="delivery-save-saleout" />
                </div>
			</div>
            <security:authorize url="/storage/deliveryMapTab.do">
                <%--<div data-options="region:'center',border:false">--%>
                    <%--<iframe name="mapframe2" src="storage/deliveryAddSaleoutMapPage.do?type=edit" style="width: 100%; height: 100%; border: 0px;">--%>
                    <%--</iframe>--%>
                <%--</div>--%>
            </security:authorize>
		</div>
<!--		<input id="storage-nochecked-detail-deliveryDetailPage" type="hidden" />-->
<!--		<input id="storage-car-volume" type="hidden" value="" />-->
<!--		<input id="storage-car-weight" type="hidden" value="" />-->
<!--		<div id="storage-panel-deliveryDetailPage"></div>-->
<!--		<div id="storage-panel-selectDetailPage"></div>-->
		<script type="text/javascript">
  		var totalFooter = {};
  		var maxVolume=999999999;
  		var maxWeight=999999999;
  		var maxBoxnum=999999999;
  		var lineid=undefined;
		$(function(){
					
			var deliveryJson = $("#storage-datagrid-deliverySourcePage").createGridColumnLoad({
				frozenCol : [[]],
				commonCol : [[
					{field:'ck', checkbox:true},
					{field:'saleoutid', title:'编号', width:130,sortable:true},
					{field:'businessdate', title:'业务日期', width:70,sortable:true},
					{field:'orderid', title:'订单编号', width:130,sortable:true},
					{field:'customerid',title:'客户编码',width:80,align:'left',sortable:true},
					{field:'customername',title:'客户名称',width:100,align:'left',
                        formatter:function(value,row,index){
                            if(row.billtype == 3) {
                                return '[仓库]' + value;
                            }
                            return value;
                        }
                    },
					{field:'salesarea',title:'销售区域',width:80,
						formatter:function(value,row,index){
							return row.salesareaname;
						}
					},
					{field:'salesamount',title:'销售额',width:100,align:'right',sortable:true,
						formatter:function(value,row,index){
							return formatterMoney(value);
						}
					},
					{field:'boxnum',title:'商品箱数',width:80,align:'right',sortable:true,
						formatter:function(value,row,index){
							return formatterMoney(value,3);
						}
					},
					{field:'weight',title:'商品重量',width:80,align:'right',sortable:true,
						formatter:function(value,row,index){
							return formatterMoney(value)+" kg";
						}
					},
					{field:'volume',title:'商品体积',width:80,align:'right',sortable:true,
						formatter:function(value,row,index){
							return formatterMoney(value,4)+" m³";
						}
					},
					{field:'status',title:'状态',width:60,
						formatter:function(value,rowData,rowIndex){
							return getSysCodeName("status",value);
						}
					},
					{field:'deliverytype',title:'来源单据类型',width:80,
						formatter:function(value,rowData,rowIndex){
							if(value=='0'){
								return "销售发货单";
							}else if(value=='1'){
								return "代配送出库单";
							}
						}
					},
					{field:'remark',title:'备注',width:80,align:'left'}
				]]
			});
			
			$("#storage-datagrid-deliverySourcePage").datagrid({
				authority:deliveryJson,
		 		frozenColumns: deliveryJson.frozen,
				columns:deliveryJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'saleoutid',
		 		singleSelect:false,
		 		selectOnCheck:true,
		 		checkOnSelect:true,
				sortName:'customerid,saleoutid',
				sortOrder:'asc',
				pageSize:1000,
				pageList:[20,100,200,500,1000],
				showFooter:true,
				toolbar:'#storage-toolbar-query-deliverySouceBill',
				onBeforeLoad:function(){

                    var o = $("#storage-datagrid-deliverySourcePage").datagrid('options').queryParams;
                    try {
                        if(!isEmpty(o)) {

                            window.mapframe2.loading('正在处理...');
                        }
                    } catch (e) {}
				},
		    	onCheckAll:function(rows){
					getAmount();
		    	},
		    	onCheck:function(rowIndex,rowData){
					getAmount(rowIndex,rowData);
		    	},
		    	onUncheck:function(rowIndex,rowData){
					getAmount(rowIndex,rowData);
		    	},
		    	onLoadSuccess:function(data){
                    try {
                        window.mapframe2.loaded();
                    } catch (e) {}

                    try {
                        var customerBills = getCustomerBills();
                        window.mapframe2.removeCustomerMarkers();
                        window.mapframe2.initSelectedCustomerMarker(customerBills);
                    } catch (e) {}

		    		var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						if(footerrows[0]!=null &&footerrows[0].saleoutid=="合计"){
							totalFooter = footerrows[0];
						}
					}
					if(data.volume != undefined){
						maxVolume=data.volume;
					}
					if(data.weight != undefined){
						maxWeight=data.weight;
					}
					if(data.boxnum != undefined){
						maxBoxnum=data.boxnum;
					}
		    	}
			}).datagrid("columnMoving");
			
    		$("#storage-lineid-deliverySourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_LOGISTICS_LINE',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		
    		$("#storage-salesarea-deliverySourceQueryPage").widget({ 
    			referwid:'RT_T_BASE_SALES_AREA',
    			singleSelect:false,
    			width:150,
    			onlyLeafCheck:false
    		});
    		
    		$("#storage-carid-deliverySourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_LOGISTICS_CAR',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		
    		$("#storage-customerid-deliverySourceQueryPage").widget({ 
    			<c:choose>
    				<c:when test="${customerwidget == 1}">
    					referwid:'RL_T_BASE_SALES_CUSTOMER_FOR_DELIVERY_1',
    				</c:when>
    				<c:when test="${customerwidget == 2}">
    					referwid:'RL_T_BASE_SALES_CUSTOMER_FOR_DELIVERY_2',
    				</c:when>
    			</c:choose>
    			singleSelect:false,
    			width:150,
    			onlyLeafCheck:true
    		});
            $("#storage-storageid-deliverySourceQueryPage").widget({
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:false,
                width:150
            });
			
			$("#storage-query-deliveryAdd").click(function(){
				totalFooter={};
				$("#storage-datagrid-deliverySourcePage").datagrid('loadData',{total:0,rows:[],footer:[totalFooter]});
				$("#storage-datagrid-deliverySourcePage").datagrid('clearChecked');
				$("#storage-datagrid-deliverySourcePage").datagrid('clearSelections');
				var queryJSON = $("#storage-form-query-deliverySouceBill").serializeJSON();
				queryJSON['saleoutids']='${ids}';
				queryJSON['volume']='${volume}';
				queryJSON['weight']='${weight}';
				queryJSON['boxnum']='${boxnum}';
				queryJSON['deliveryid']='${deliveryid}';
				queryJSON['carid']=$("#storage-carid-deliverySourceQueryPage").widget('getValue');
				queryJSON['customerid']=$("#storage-customerid-deliverySourceQueryPage").widget('getValue');
		       	$("#storage-datagrid-deliverySourcePage").datagrid({
		       		url: 'storage/getSaleOutListForAddDelivery.do',
	      			pageNumber:1,
					queryParams:queryJSON
		       	}).datagrid("columnMoving");
			});
    		
    		$("#storage-reload-deliveryAdd").click(function(){
				$("#storage-datagrid-deliverySourcePage").datagrid('loadData',{total:0,rows:[],footer:[]});
				$("#storage-datagrid-deliverySourcePage").datagrid('clearChecked');
				$("#storage-datagrid-deliverySourcePage").datagrid('clearSelections');
				$("#storage-form-query-deliverySouceBill")[0].reset();
<!--				$("#storage-nochecked-detail-deliveryDetailPage").val("");-->
<!--				$("#storage-car-volume").val("");-->
<!--				$("#storage-car-weight").val("");-->
				$("#storage-lineid-deliverySourceQueryPage").widget('setValue',"${lineid}");
				$("#storage-carid-deliverySourceQueryPage").widget('setValue',"${carid}");
				$("#storage-salesarea-deliverySourceQueryPage").widget('clear');
				$("#storage-customerid-deliverySourceQueryPage").widget('clear');
			});
    		
		});
		//获取合计
    	function getAmount(){
    		var rowArr = $("#storage-datagrid-deliverySourcePage").datagrid("getChecked");
    		var volume = 0;
	        var weight = 0;
	        var salesamount = 0;
	        var remainvolume = 0;
	        var remainweight = 0;
	        var boxnum = 0;
	       
			for(var i=0;i<rowArr.length;i++){
				volume = Number(volume)+Number(rowArr[i].volume);
				weight = Number(weight)+Number(rowArr[i].weight);
				boxnum = Number(boxnum)+Number(rowArr[i].boxnum);
				salesamount = Number(salesamount) + Number(rowArr[i].salesamount);
			}
			remainvolume=formatterMoney(Number(maxVolume)-Number(volume)) + " m³";
			remainweight=formatterMoney(Number(maxWeight)-Number(weight)) + " kg";
	   		if(rowArr.length==0){
				$("#storage-datagrid-deliverySourcePage").datagrid("reloadFooter",[totalFooter]);
			}else{
				$("#storage-datagrid-deliverySourcePage").datagrid("reloadFooter",[{saleoutid:'选中合计',businessdate:"空余重量",orderid:remainweight,customerid:"空余体积",customername:remainvolume,boxnum:boxnum,volume:volume,weight:weight,salesamount:salesamount},totalFooter] );
	   		}
	   		if(volume > maxVolume && weight > maxWeight && boxnum > maxBoxnum)
	   		{
	   			$.messager.alert("提醒","已超体积或超重或超箱数！");
	   			return;
	   		}
	   		if(volume > maxVolume)
	   		{
	   			$.messager.alert("提醒","已超体积！");
	   			return;
	   		}
	   		if(weight > maxWeight)
	   		{
	   			$.messager.alert("提醒","已超重！");
	   			return;
	   		}
	   		if(boxnum > maxBoxnum)
	   		{
	   			$.messager.alert("提醒","已超箱数！");
	   			return;
	   		}
	   	}
	   	$("#delivery-save-saleout").click(function(){
    		var rows = $("#storage-datagrid-deliverySourcePage").datagrid('getChecked');
    		if(rows.length == 0){
    			$.messager.alert("提醒","请勾选发货单！");
    			return false;
    		}
			var lineid = $("#storage-lineid-deliverySourceQueryPage").widget("getValue");
    		$.messager.confirm("警告","是否添加选中的单据?<br/>添加之后，交接单的列表内容会被刷新！",function(r){
				if(r){
					//删除空白行
					var salerows=$dgSaleoutList.datagrid('getRows');
					var index=0;
                    var existCustomeridArray = new Array();
					for(var i=0;i<salerows.length;i++){
						if(salerows[i].customerid==undefined){
							$dgSaleoutList.datagrid('deleteRow',i);
							i--;
							continue;
						}
                        existCustomeridArray.push(salerows[i].customerid);
					}

                    var addedCustomeridArray = new Array();

					//插入选中行
					if('null' == saleoutaddid){
			  			saleoutaddid = "";
			  		}
			   		saleoutid='${ids}';
			   		var customernum = 0;
					for(var i=0;i<rows.length;i++){
						if(saleoutaddid == ""){
		        			saleoutaddid = rows[i].saleoutid ;
		             	}else{
		             		saleoutaddid += "," + rows[i].saleoutid ;
		             	}
						if(saleoutid == ""){
		        			saleoutid = rows[i].saleoutid ;
		             	}else{
		             		saleoutid += "," + rows[i].saleoutid ;
		             	}

						$dgSaleoutList.datagrid('appendRow',{
		    				deliveryid : '${deliveryid}',
							saleoutid	: rows[i].saleoutid,
							deliverytype	: rows[i].deliverytype,
							orderid	: rows[i].orderid,
							businessdate	: rows[i].businessdate,
							customerid	: rows[i].customerid,
							customername	: rows[i].customername,
							salesamount	: rows[i].salesamount,
							weight	: rows[i].weight,
							volume	: rows[i].volume,
							boxnum	: rows[i].boxnum,
							salesuser	: rows[i].salesuser,
							salesusername : rows[i].salesusername,
                            billtype : rows[i].billtype
						});

                        if(existCustomeridArray.indexOf(rows[i].customerid) < 0 && addedCustomeridArray.indexOf(rows[i].customerid) < 0) {
                            customernum ++;
                        }

                        addedCustomeridArray.push(rows[i].customerid);
					}
					//修改发货单合计
					setSaleoutFooter();

                    $("#delivery-input-customernums").numberbox('setValue',Number($("#delivery-input-customernums").numberbox('getValue')) + customernum);

					$("#delivery-dialog-saleout1").dialog('close',true);

					//补充空白行
					rows = $dgSaleoutList.datagrid('getRows');
	    			var leng = rows.length;
	    			if(leng < 12){
	    				for(var i=leng; i<12; i++){
	    					$dgSaleoutList.datagrid('appendRow',{});
	    				}
	    			}
	    			else
	    				$dgSaleoutList.datagrid('appendRow',{});

					//loadDgCustomerList();
					loadDgCustomerListadd(lineid);
				}
			});
    	});

        /**
         *
         */
        function getCustomerBills() {

            var rows = $('#storage-datagrid-deliverySourcePage').datagrid('getRows');
            var selectedRows = $('#storage-datagrid-deliverySourcePage').datagrid('getSelections');

            var customerBills = {};
            for(var i in rows) {

                var row = rows[i];
                row.check = false;
                for(var j in selectedRows) {

                    var selectedRow = selectedRows[j];
                    if(row.saleoutid == selectedRow.saleoutid) {
                        row.check = true;
                        break;
                    }
                }
                if(!customerBills[row.customerid]) {
                    customerBills[row.customerid] = {bills: new Array()};
                }

                customerBills[row.customerid].bills.push(row);
            }

            return customerBills;
        }

        /**
         * 选中单据
         *
         * @param saleoutid
         * @param checkFlag true:选中； false:不选
         * @returns {boolean}
         */
        function checkBill(saleoutid, checkFlag) {

            var rows = $('#storage-datagrid-deliverySourcePage').datagrid('getRows');
            for(var i = 0; i < rows.length; i++) {

                var row = rows[i];
                if(row.saleoutid == saleoutid) {
                    $("#storage-datagrid-deliverySourcePage").datagrid(checkFlag ? 'checkRow' : 'uncheckRow', i);
                    return true;
                }
            }

            return true;
        }

        /**
         * 选中客户所有单据
         *
         * @param customerid
         * @param checkFlag true:选中； false:不选
         * @returns {boolean}
         */
        function checkCustomer(customerid, checkFlag) {

            var rows = $('#storage-datagrid-deliverySourcePage').datagrid('getRows');
            for(var i = 0; i < rows.length; i++) {

                var row = rows[i];
                if(row.customerid == customerid) {
                    $('#storage-datagrid-deliverySourcePage').datagrid(checkFlag ? 'selectRow' : 'unselectRow', i);
                }
            }

            return true;
        }

        /**
         * 获取单据状态体积，重量，箱数，销售额
         *
         * @returns {{}}
         */
        function getBillStatus() {

            var data = $("#storage-datagrid-deliverySourcePage").datagrid("getData");
            var currentRows = $dgSaleoutList.datagrid("getRows");
            var selectedRows = $("#storage-datagrid-deliverySourcePage").datagrid("getSelections");
            var volume = 0;
            var weight = 0;
            var boxnum = 0;
            var salesamount = 0;
            for (var i = 0; i < currentRows.length; i++) {
                volume = Number(volume) + Number(currentRows[i].volume || 0);
                weight = Number(weight) + Number(currentRows[i].weight || 0);
                boxnum = Number(boxnum) + Number(currentRows[i].boxnum || 0);
                salesamount = Number(salesamount) + Number(currentRows[i].salesamount || 0);
            }

            for (var i = 0; i < selectedRows.length; i++) {
                volume = Number(volume) + Number(selectedRows[i].volume || 0);
                weight = Number(weight) + Number(selectedRows[i].weight || 0);
                boxnum = Number(boxnum) + Number(selectedRows[i].boxnum || 0);
                salesamount = Number(salesamount) + Number(selectedRows[i].salesamount || 0);
            }

            return {
                volume: volume,
                weight: weight,
                boxnum: boxnum,
                salesamount: salesamount,
                maxvolume: parseFloat(data.footer[0].customername.split(' ')[0] || 0),
                maxweight: parseFloat(data.footer[0].orderid.split(' ')[0] || 0)
            };
        }

        /**
         * 判断对象是否为{} 或 null
         *
         * @param obj
         * @returns {boolean}
         */
        function isEmpty(obj) {
            for (var name in obj) {
                return false;
            }
            return true;
        }

  	</script>
	</body>
</html>

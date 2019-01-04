<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>配送单新增</title>
		<%@include file="/include.jsp"%>
	</head>
    <style type="text/css">
        .checkbox1{
            float:left;
            height: 22px;
            line-height: 22px;
        }
        .divtext{
            height:22px;
            line-height:22px;
            float:left;
            display: block;
        }
    </style>
	<body>
        <div class="easyui-layout" data-options="fit:true" id="storage-layout-deliveryAddPage">
            <div data-options="region:'north',border:false">
                <div class="buttonBG">
                    <a href="javaScript:void(0);" id="storage-add-deliveryAdd" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="生成配送单">生成配送单</a>
                    <security:authorize url="/storage/showDeliveryAddMapPage.do">
                        <a href="javaScript:void(0);" id="storage-showmap-deliveryAdd" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'" title="显示地图">显示地图</a>
                    </security:authorize>
                </div>
            </div>
            <div data-options="region:'center'">
                <table id="storage-datagrid-deliverySourcePage"></table>
                <div id="storage-toolbar-query-deliverySouceBill" style="padding:2px;height:auto">
                    <form action="" id="storage-form-query-deliverySouceBill" method="post">
                        <input type="hidden" name="startpoint" id="storage-startpoint-deliveryAddPage"/>
                        <table class="querytable">
                            <tr>
                                <td>线&nbsp;路：</td>
                                <td><input type="text" id="storage-lineid-deliverySourceQueryPage" name="lineid" class="easyui-validatebox" required="true"/></td>
                                <td>车&nbsp;&nbsp;辆：</td>
                                <td><input type="text" id="storage-carid-deliverySourceQueryPage" name="carid" value="${carid}" class="easyui-validatebox" required="true"/></td>
                                <td>客户：</td>
                                <td><input type="text" id="storage-customerid-deliverySourceQueryPage" name="carid" value="${customerid}"/></td>
                            </tr>
                            <tr>
                                <td>体&nbsp;积：</td>
                                <td><input type="text" id="storage-type-volume" name="volume" style="width: 150px;" class="easyui-numberbox" data-options="min:0,precision:4<c:if test="${deliveryPriority != '3'}">,disabled:true</c:if>"/></td>
                                <td>重&nbsp;&nbsp;量：</td>
                                <td><input type="text" id="storage-type-weight" name="weight" style="width: 150px;" class="easyui-numberbox" data-options="min:0,precision:2<c:if test="${deliveryPriority != '1'}">,disabled:true</c:if>"/></td>
                                <td>箱数：</td>
                                <td><input type="text" id="storage-type-boxnum" name="boxnum" style="width: 150px;" class="easyui-numberbox" data-options="min:0,precision:3<c:if test="${deliveryPriority != '2'}">,disabled:true</c:if>"/></td>
                            </tr>
                            <tr>
                                <td>单据号：</td>
                                <td><input type="text" id="storage-input-saleoutid" name="saleoutid" style="width: 150px;"/></td>
                                <td>来源类型：</td>
    	                        <td>
									<select id="storage-deliverytype" name="billtype" style="width:150px;">
										<option ></option>
										<option value="1" selected="selected">销售发货单</option>
                                        <option value="2">代配送出库单</option>
                                        <c:if test="${deliveryAddAllocate eq '1'}">
                                            <option value="3">调拨单</option>
                                        </c:if>
									</select>
    	                        </td>
                                <td>仓库：</td>
                                <td><input type="text" id="storage-storageid-deliverySourceQueryPage" name="storageid" /></td>
                            </tr>
                            <tr>
                                <td>线路内/外：</td>
                                <td>
                                    <select id="storage-inline" name="inline" style="width:150px;">
                                        <option ></option>
                                        <option value="1" selected="selected">线路内</option>
                                        <option value="2">线路外</option>
                                    </select>
                                </td>
                                <td>备&nbsp;&nbsp;注：</td>
                                <td><input type="text" id="storage-input-remark" name="remark" style="width:150px;" autocomplete="off"/></td>
                                <td colspan="2">
                                    <input type="checkbox" id="volume" class="checkbox1 deliveryPriority" value="3" <c:if test="${deliveryPriority == '3'}">checked="checked"</c:if>/>
                                    <label for="volume" class="divtext">体积</label>
                                    <input type="checkbox" id="weight" class="checkbox1 deliveryPriority" value="1" <c:if test="${deliveryPriority == '1'}">checked="checked"</c:if>/>
                                    <label for="weight" class="divtext">重量</label>
                                    <input type="checkbox" id="boxnum" class="checkbox1 deliveryPriority" value="2" <c:if test="${deliveryPriority == '2'}">checked="checked"</c:if>/>
                                    <label for="boxnum" class="divtext">箱数</label>
                                    <input id="storage-hidden-deliveryPriority" type="hidden"  name="deliveryPriority" value="${deliveryPriority}"/>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td colspan="2">
                                    <a href="javaScript:void(0);" id="storage-query-deliveryAdd" class="button-qr">查询</a>
                                    <a href="javaScript:void(0);" id="storage-reload-deliveryAdd" class="button-qr">重置</a>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
            <%--<div data-options="region:'east',collapsed:true" style="width: 100%;">--%>
                <%--<iframe name="mapframe" src="basefiles/customerMapPage.do?type=edit" style="width: 100%; height: 100%; border: 0px;">--%>
                <%--</iframe>--%>
            <%--</div>--%>
        </div>
		<input id="storage-nochecked-detail-deliveryDetailPage" type="hidden" />
		<input id="storage-car-volume" type="hidden" value="" />
		<input id="storage-car-weight" type="hidden" value="" />
		<div id="storage-panel-deliveryDetailPage"></div>
		<div id="storage-panel-selectDetailPage"></div>
        <div id="storage-map-deliveryAddPage"></div>
		<script type="text/javascript">
		var totalFooter = {};
  		var maxVolume=999999999;
  		var maxWeight=999999999;
  		var maxBox=999999999;
  		var lineid='';
        var totalpriority = $("#storage-hidden-deliveryPriority").val();

  		//加锁
	    function isDoLockData(id,tablename){
	    	var flag = false;
	    	$.ajax({
	            url :'system/lock/isDoLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
	    }

        function deliveryPriorityShow(){
            var priority = "";
            $("#storage-hidden-deliveryPriority").val(priority);
            $(".deliveryPriority").each(function(){
                if($(this).attr("checked")){
                    if(priority==""){
                        priority = $(this).val();
                    }else{
                        priority += ","+$(this).val();
                    }
                    $("#storage-hidden-deliveryPriority").val(priority);
                    if("1" == $(this).val()){
                        $("#storage-type-weight").numberbox('enable');
                    }
                    if("2" == $(this).val()){
                        $("#storage-type-boxnum").numberbox('enable');
                    }
                    if("3" == $(this).val()){
                        $("#storage-type-volume").numberbox('enable');
                    }
                }else{
                    if("1" == $(this).val()){
                        $("#storage-type-weight").numberbox('disable');
                    }
                    if("2" == $(this).val()){
                        $("#storage-type-boxnum").numberbox('disable');
                    }
                    if("3" == $(this).val()){
                        $("#storage-type-volume").numberbox('disable');
                    }
                }
            });
            totalpriority = priority;
        }

		$(function(){
            $(".deliveryPriority").click(function(){
                deliveryPriorityShow();
            });

			var deliveryJson = $("#storage-datagrid-deliverySourcePage").createGridColumnLoad({
				frozenCol : [[]],
				commonCol : [[
							{field:'ck', checkbox:true},
							{field:'saleoutid', title:'编号', width:140,sortable:true},
							{field:'businessdate', title:'业务日期', width:85,sortable:true},
							{field:'orderid', title:'订单编号', width:130,sortable:true},
							{field:'customerid',title:'客户编码',width:80,align:'left'},
							{field:'customername',title:'客户名称',width:100,align:'left',
                                formatter:function(value,row,index){
							        if(row.billtype == '3') {
							            return '[仓库]' + value;
                                    }
                                    return value;
                                }
                            },
                            {field:'storageid',title:'仓库',width:100,align:'left',
                                formatter:function(value, row, index){
                                    return row.storagename;
                                }
                            },
							{field:'salesamount',title:'销售额',width:100,align:'right',sortable:true,
								formatter:function(value,row,index){
									return formatterMoney(value);
								}
							},
							{field:'boxnum',title:'商品箱数',width:80,align:'right',sortable:true,
								formatter:function(value,row,index){
									return formatterBigNumNoLen(value);
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
                            {field:'billtype',title:'单据类型',width:100,align:'left',
                                formatter:function(value,row,index){
                                    if(value == '1') {
                                        return '发货单';
                                    } else if(value == '2') {
                                        return '代配送出库单';
                                    } else if(value == '3') {
                                        return '调拨单';
                                    }
                                }
                            },
//							{field:'deliverytype',title:'来源单据类型',width:80,
//							  	formatter:function(value,rowData,rowIndex){
//							  		if(value=='0'){
//					        		    return "销售发货单";
//							  		}else if(value=='1'){
//										return "代配送出库单";
//									}
//					        	}
//							},
                            {field:'inline',title:'线路内/外',width:80,
                                formatter:function(value, rowData, rowIndex){
                                    if(value == '1'){
                                        return "线路内";
                                    }else if(value == '0'){
                                        return "线路外";
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
				sortName:'seq,customerid',
				sortOrder:'asc',
				pageSize:1000,
				pageList:[20,100,200,500,1000],
				showFooter:true,
				toolbar:'#storage-toolbar-query-deliverySouceBill',
                <%--rowStyler: function(index, row){--%>
                    <%--if (row.inline == '1' && index % 2 == 0){--%>
                        <%--return 'background-color: rgba(114, 202, 158, 0.35);';--%>
                    <%--}--%>
                    <%--if (row.inline == '1' && index % 2 == 1){--%>
                        <%--return 'background-color: rgba(114, 202, 158, 0.45);';--%>
                    <%--}--%>
                <%--},--%>
		    	onUncheckAll:function(rows){
					getAmount();
		    	},
		    	onCheckAll:function(rows){
					getAmount();
		    	},
		    	onCheck:function(){
					getAmount();
		    	},
		    	onUncheck:function(){
					getAmount();
		    	},
		    	onLoadSuccess:function(data){
		    		var footerrows = $(this).datagrid('getFooterRows');
		    		if(null!=footerrows && footerrows.length>0){
						if(footerrows[0]!=null &&footerrows[0].saleoutid=="合计"){
							totalFooter = footerrows[0];
						}
					}
					maxVolume=data.volume;
					maxWeight=data.weight;
					maxBox=data.boxnum;
					var rows = data.rows;
					for(var i= 0;i<rows.length;i++){
						if("1" == rows[i].check){
							$(this).datagrid('checkRow',i);
							getAmount();
						}
					}
		    	}
			}).datagrid("columnMoving");

    		$("#storage-lineid-deliverySourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_LOGISTICS_LINE',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true,
    			onSelect:function(data){
    				$("#storage-carid-deliverySourceQueryPage").widget({ 
		    			referwid:'RL_T_BASE_LOGISTICS_LINE_CAR',
		    			param:[{field:'lineid',op:'equal',value:data.id}],
		    			singleSelect:true,
		    			width:150,
		    			onlyLeafCheck:true,
		    			setValueSelect:true,
		    			initValue:data.carid,
		    			onSelect:function(cardata){
		    				getVolumeAndWeightForCar(cardata.id);
		    			},
		    			onClear:function(data){
		    				$("#storage-type-volume").numberbox('clear');
		    				$("#storage-type-weight").numberbox('clear');
		    				$("#storage-type-boxnum").numberbox('clear');
		    			},
		    			onLoadSuccess:function(){
		    				$(this).widget('setValue',data.carid);
		    			}
		    		});
		    		getVolumeAndWeightForCar(data.carid);
    			},
    			onClear:function(data){
    				$("#storage-carid-deliverySourceQueryPage").widget('clear');
    			}
    		});
    		
    		$("#storage-carid-deliverySourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_LOGISTICS_LINE_CAR',
    			param:[{field:'lineid',op:'equal',value:'null'}],
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});

            $("#storage-storageid-deliverySourceQueryPage").widget({
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:false,
                width:150
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
			
			$("#storage-query-deliveryAdd").click(function(){
	    		var lineid = $("#storage-lineid-deliverySourceQueryPage").widget("getValue");
    			var carid = $("#storage-carid-deliverySourceQueryPage").widget("getValue");
				if(!$("#storage-form-query-deliverySouceBill").form('validate') || lineid == '' || carid == '')
	    		{
	    			$.messager.alert("提醒","请选择正确的线路和车辆数据");
	    			return false;
	    		}
	    		
				totalFooter={};
				$("#storage-datagrid-deliverySourcePage").datagrid('loadData',{total:0,rows:[],footer:[totalFooter]});
				$("#storage-datagrid-deliverySourcePage").datagrid('clearChecked');
				$("#storage-datagrid-deliverySourcePage").datagrid('clearSelections');
				var queryJSON = $("#storage-form-query-deliverySouceBill").serializeJSON();
				queryJSON['carid']=$("#storage-carid-deliverySourceQueryPage").widget('getValue');
				queryJSON['customerid']=$("#storage-customerid-deliverySourceQueryPage").widget('getValue');
		       	$("#storage-datagrid-deliverySourcePage").datagrid({
		       		url: 'storage/getSaleOutListForDelivery.do',
	      			pageNumber:1,
					queryParams:queryJSON
		       	}).datagrid("columnMoving");
			});
			//导出
			$("#storage-export-deliveryAdd").Excel('export',{
				queryForm: "#storage-form-query-deliverySouceBill",
		 		type:'exportUserdefined',
		 		name:'新增配送单列表',
		 		url:'sales/exportReceiptAndRejectBillList.do'
			});
			$("#storage-customerid-deliveryDetailSourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_SALES_CUSTOMER',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		$("#storage-pcustomerid-deliveryDetailSourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		
    		//配送单 生成
			$("#storage-add-deliveryAdd").click(function(){
				var lineid = $("#storage-lineid-deliverySourceQueryPage").widget("getValue");
    			var carid = $("#storage-carid-deliverySourceQueryPage").widget("getValue");
				if(!$("#storage-form-query-deliverySouceBill").form('validate') || lineid == '' || carid == '')
	    		{
	    			$.messager.alert("提醒","请选择正确的线路和车辆数据");
	    			return false;
	    		}
				$.messager.confirm("提醒","是否生成新的配送单？",function(r){
					if(r){
						addDelivery();
					}
				});
			});
			
			$("#storage-reload-deliveryAdd").click(function(){
				$("#storage-datagrid-deliverySourcePage").datagrid('loadData',{total:0,rows:[],footer:[]});
				$("#storage-datagrid-deliverySourcePage").datagrid('clearChecked');
				$("#storage-datagrid-deliverySourcePage").datagrid('clearSelections');
				$("#storage-nochecked-detail-deliveryDetailPage").val("");
				$("#storage-car-volume").val("");
				$("#storage-car-weight").val("");
				$("#storage-lineid-deliverySourceQueryPage").widget("clear");
				$("#storage-carid-deliverySourceQueryPage").widget("clear");
				$("#storage-customerid-deliverySourceQueryPage").widget("clear");
				$("#storage-type-volume").numberbox('clear');
				$("#storage-type-weight").numberbox('clear');
				$("#storage-type-boxnum").numberbox('clear');
				$("#storage-form-query-deliverySouceBill")[0].reset();
                deliveryPriorityShow();
			});
			
			$("#storage-select-deliverySourceQueryPage").change(function(){
				var type = $("#storage-select-deliverySourceQueryPage").val();
				var str = '';
				var num = 2;
				if("0" == type){
					str = '<input type="text" id="storage-type_'+type+'" name="boxnum" style="width: 95px;"/>';
				}else if("1" == type){
					str = '<input type="text" id="storage-type_'+type+'" name="volume" style="width: 95px;"/>';
					num = 4;
				}else if("2" == type){
					str = '<input type="text" id="storage-type_'+type+'" name="weight" style="width: 95px;"/>';
				}
				$("#storage-span-deliverySourceQueryPage").html(str);
				$("#storage-type_"+type+"").numberbox({
					min:0,
   				 	precision:num
				});
			});

            /**
             * 查看地图
             */
            $('#storage-showmap-deliveryAdd').click(function(e) {

                var param = $("#storage-datagrid-deliverySourcePage").datagrid('options').queryParams;
                var lineid = param.lineid;
                if(!lineid) {
                    $.messager.alert('提醒', '列表中不存在单据！');
                    return false;
                }

                var maxVolume = $('#storage-type-volume').numberbox('getValue') || 0;
                var maxWeight = $('#storage-type-weight').numberbox('getValue') || 0;
                var maxBoxnum = $('#storage-type-boxnum').numberbox('getValue') || 0;

                var rowArr = $("#storage-datagrid-deliverySourcePage").datagrid("getChecked");
                var volume = 0;
                var weight = 0;
                var boxnum = 0;
                var salesamount = 0;
                for(var i=0;i<rowArr.length;i++){
                    volume = Number(volume)+Number(rowArr[i].volume || 0);
                    weight = Number(weight)+Number(rowArr[i].weight || 0);
                    boxnum = Number(boxnum)+Number(rowArr[i].boxnum || 0);
                    salesamount = Number(salesamount) + Number(rowArr[i].salesamount || 0);
                }

                $('#storage-map-deliveryAddPage').dialog({
                    title: '地图',
                    closed: false,
                    cache: false,
                    maximizable: true,
                    maximized: true,
                    resizable: true,
                    content: '<iframe name="mapframe" src="storage/deliveryAddMapPage.do?type=edit&lineid=' + lineid + '&v=' + volume + '&mv=' + maxVolume + '&w=' + weight + '&mw='+ maxWeight + '&b=' + boxnum + '&mb=' + maxBoxnum + '" style="width: 100%; height: 100%; border: 0px;"> </iframe>',
                    modal: true
                });
            });
		});
		//获取合计
    	function getAmount(){
    		var rowArr = $("#storage-datagrid-deliverySourcePage").datagrid("getChecked");
    		var volume = 0;
	        var weight = 0;
	        var boxnum = 0;
	        var remainvolume = 0;
	        var remainweight = 0;
	        var salesamount = 0;
			for(var i=0;i<rowArr.length;i++){
				volume = Number(volume)+Number(rowArr[i].volume);
				weight = Number(weight)+Number(rowArr[i].weight);
				boxnum = Number(boxnum)+Number(rowArr[i].boxnum);
				salesamount = Number(salesamount) + Number(rowArr[i].salesamount);
			}
			if(maxVolume != undefined){
                maxVolume = $("#storage-type-volume").numberbox('getValue');
				remainvolume=formatterMoney(Number(maxVolume)-Number(volume),4) + " m³";
			}
			if(maxWeight != undefined){
                maxWeight = $("#storage-type-weight").numberbox('getValue');
				remainweight=formatterMoney(Number(maxWeight)-Number(weight)) + " kg";
			}
            if(maxBox != undefined){
                maxBox = $("#storage-type-boxnum").numberbox('getValue');
            }
			var foot = [{saleoutid:'选中合计',businessdate:"空余重量",orderid:remainweight,customerid:"空余体积",customername:remainvolume,boxnum:boxnum,volume:volume,weight:weight,salesamount:salesamount}];
			
			if(rowArr.length==0){
				$("#storage-datagrid-deliverySourcePage").datagrid("reloadFooter",[totalFooter]);
			}else{
				$("#storage-datagrid-deliverySourcePage").datagrid("reloadFooter",[{saleoutid:'选中合计',businessdate:"空余重量",orderid:remainweight,customerid:"空余体积",customername:remainvolume,boxnum:boxnum,volume:volume,weight:weight,salesamount:salesamount},totalFooter] );
	   		}

            var vflag = false,wflag = false,bflag = false;
            var msg = "";
            if(totalpriority.indexOf("3") != -1 && volume > maxVolume){
                vflag = true;
            }
            if(totalpriority.indexOf("1") != -1 && weight > maxWeight){
                wflag = true;
                $.messager.alert("提醒","已超重！");
                return;
            }
            if(totalpriority.indexOf("2") != -1 && boxnum > maxBox){
                bflag = true;
            }
            if(vflag){
                msg = "超体积";
            }
            if(wflag){
                if(msg == ""){
                    msg = "超重";
                }else{
                    msg += "或超重";
                }
            }
            if(bflag){
                if(msg == ""){
                    msg = "超箱数";
                }else{
                    msg += "或超箱数";
                }
            }
            if(msg != ""){
	   			$.messager.alert("提醒","已"+msg+"!");
	   			return;
	   		}
	   	}
    	
    	function addDelivery(){
    		var lineid = $("#storage-lineid-deliverySourceQueryPage").widget("getValue");
   			var carid = $("#storage-carid-deliverySourceQueryPage").widget("getValue");
    		var rowArr = $("#storage-datagrid-deliverySourcePage").datagrid("getChecked");
    		var ids = null;
			for(var i=0;i<rowArr.length;i++){
				if(ids==null){
					ids = rowArr[i].saleoutid;// "'" + rowArr[i].saleoutid + "'";
				}else{
					ids +="," + rowArr[i].saleoutid;
				}
			}
			loading('提交中...');
            var startpoint = $('#storage-startpoint-deliveryAddPage').val();
			$.ajax({
				url:'storage/delivery/addDeliveryList.do',
				dataType:'json',
				type:'post',
				data:{ids:ids,lineid:lineid,carid:carid, start: startpoint},
				success:function(json){
					loaded();
					if(json.flag){
	            		$.messager.alert("提醒","生成成功");
	            		$("#storage-datagrid-deliverySourcePage").datagrid('reload'); 
	            		var flag = isDoLockData(json.id,"t_storage_logistics_delivery");
		 				if(!flag){
		 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
		 					return false;
		 				}
	            		var title = parent.getNowTabTitle();
	            		top.addOrUpdateTab('storage/delivery/showDeliveryPage.do?id='+ json.id+'&type=edit', "配送单查看");
	            		//top.closeTab(title);
					}else{
						$.messager.alert("提醒","生成失败<br/>");
					}
				},
				error:function(){
					loaded();
				}
			});
    	}
    	
    	function getVolumeAndWeightForCar(carid){
    		$.ajax({
	            url :'storage/getVolumeAndWeightForCar.do?carid='+carid,
	            type:'post',
	            dataType:'json',
	            success:function(json){
	            	if(Number(json.volume) != 0){
    					$("#storage-type-volume").numberbox('setValue',json.volume);
    				}else{
    					$("#storage-type-volume").numberbox('clear');
    				}
    				if(Number(json.weight) != 0){
    					$("#storage-type-weight").numberbox('setValue',json.weight);
    				}else{
    					$("#storage-type-weight").numberbox('clear');
    				}
    				if(Number(json.boxnum) != 0){
    					$("#storage-type-boxnum").numberbox('setValue',json.boxnum);
    				}else{
    					$("#storage-type-boxnum").numberbox('clear');
    				}
	            },
	            error:function(){
	            	loaded();
	            	$.messager.alert("错误","获取体积、重量、箱数出错");
	            }
	        });
    	}

        /**
         *
         */
        function getCustomerBills() {

            var customerBills = {};
            var rows = $("#storage-datagrid-deliverySourcePage").datagrid('getRows');
            var checkedRows = $("#storage-datagrid-deliverySourcePage").datagrid('getChecked');
            for(var i in rows) {

                var row = rows[i];

                // 判断该行是否被选中
                var check = false;
                row.check = false;
                for(var j in checkedRows) {

                    var checkedRow = checkedRows[j];
                    if(checkedRow.saleoutid == row.saleoutid) {
                        check = true;
                        row.check = true;
                        break;
                    }
                }

                var row = rows[i];

                if(!customerBills[row.customerid]) {
                    customerBills[row.customerid] = {};
                }

                if(!customerBills[row.customerid].bills) {
                    customerBills[row.customerid].bills = new Array();
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

            var rows = $("#storage-datagrid-deliverySourcePage").datagrid('getRows');
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

            var rows = $("#storage-datagrid-deliverySourcePage").datagrid('getRows');
            for(var i = 0; i < rows.length; i++) {

                var row = rows[i];
                if(row.customerid == customerid) {
                    $("#storage-datagrid-deliverySourcePage").datagrid(checkFlag ? 'checkRow' : 'uncheckRow', i);
                }
            }

            return true;
        }

        /**
         * 获取单据状态体积，重量，箱数，销售额
         *
         * @returns {{}}
         */
        function getBillStatistic() {

            var rowArr = $("#storage-datagrid-deliverySourcePage").datagrid("getChecked");
            var volume = 0;
            var weight = 0;
            var boxnum = 0;
            var salesamount = 0;
            for(var i=0;i<rowArr.length;i++){
                volume = Number(volume)+Number(rowArr[i].volume);
                weight = Number(weight)+Number(rowArr[i].weight);
                boxnum = Number(boxnum)+Number(rowArr[i].boxnum);
                salesamount = Number(salesamount) + Number(rowArr[i].salesamount);
            }

            return {
                volume: volume,
                weight: weight,
                boxnum: boxnum,
                salesamount: salesamount
            };
        }

        /**
         *
         * @param billid
         */
        function getBillStatus(billid) {

            var rowArr = $("#storage-datagrid-deliverySourcePage").datagrid("getChecked");
            for(var i in rowArr) {

                var row = rowArr[i];
                if(row.saleoutid == billid) {
                    return true;
                }
            }

            return false;
        }
    	
  	</script>
	</body>
</html>

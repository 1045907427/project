<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>销售出库单明细批次修改</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<div id="storage-batchdiv-purchaseEnterAddPage">
			<table>
				<tr>
					<td>商品名称：</td>
					<td colspan="3">
						<input type="text" id="storage-purchaseEnter-batch-goodsname" style="width: 300px;" class="no_input" readonly="readonly"/>
					</td>
					<td>箱装量：</td>
					<td>
						<input type="text" id="storage-purchaseEnter-batch-boxnum" style="width: 100px;" class="formaterNum no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>总入库数量：</td>
					<td>
						<input type="text" id="storage-purchaseEnter-batch-auxnum" style="width: 100px;" class="formaterNum no_input" readonly="readonly"/>
						<input type="hidden" id="storage-purchaseEnter-batch-totalnum" style="width: 100px;" class="no_input" readonly="readonly"/>
					</td>
					<td>已确定批次数量：</td>
					<td>
						<input type="text" id="storage-purchaseEnter-batch-addbatchnum" style="width: 100px;" class="no_input" readonly="readonly" value="0"/>
						<input type="hidden" id="storage-purchaseEnter-batch-addbatchnum-hidden" value="0"/>
					</td>
					<td>未确定批次数量：</td>
					<td>
						<input type="text" id="storage-purchaseEnter-batch-nonum" style="width: 100px;" class="formaterNum no_input" readonly="readonly" value="0"/>
						<input type="hidden" id="storage-purchaseEnter-batch-nonum-hidden" value="0"/>
					</td>
				</tr>
			</table>
		</div>
		<table id="storage-batchdatagrid-purchaseEnterAddPage"></table>
    </div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
			<input type="button" value="确 定" name="savegoon" id="storage-savebatch-purchaseEnterDetailEditPage" />
		</div>
	</div>
</div>
   <script type="text/javascript">
   		var goodsname = $("#storage-purchaseEnter-goodsname").val();
   		var barcode = $("#storage-purchaseEnter-goodsbarcode").val();
   		var boxnum = $("#storage-purchaseEnter-boxnum").val();
   		var totalunitnum = $("#storage-purchaseEnter-unitnum").val();
   		var auxnumdetali = $("#storage-purchaseEnter-auxunitnumdetail").val();
   		var unitname = $("#storage-purchaseEnter-goodsunitname").val();
   		var auxunitname = $("#storage-purchaseEnter-auxunitname").val();
   		$("#storage-purchaseEnter-batch-goodsname").val("${goodsid}"+goodsname);
   		$("#storage-purchaseEnter-batch-boxnum").val(formatterBigNumNoLen(boxnum));
   		$("#storage-purchaseEnter-batch-totalnum").val(totalunitnum);
   		$("#storage-purchaseEnter-batch-auxnum").val(auxnumdetali);
   		$("#storage-purchaseEnter-batch-nonum").val(auxnumdetali);
   		$("#storage-purchaseEnter-batch-nonum-hidden").val(totalunitnum)
   		$(function(){
   			$("#storage-batchdatagrid-purchaseEnterAddPage").datagrid({
    			columns: [[
							{field:'barcode', title:'条形码',width:130,
								editor:{
							    	type:'text'
							    }
							},
							{field:'produceddate', title:'生产日期',width:100,
								editor:{
						        	type:'dateText',
						        	options:{
						        		maxDate:'%y-%M-%d',
						        		onSelect:function(){
						        			getBacthno();
						        		}
						        	}
						        }
							},
							{field:'deadline', title:'截止日期',width:100,
								editor:{
						        	type:'dateText',
						        	options:{
                                        dateFmt:'yyyy-MM-dd',
                                        onSelect:function(){
                                            getBacthnoByDeadline();
                                        }
                                    }
						        }	
							},
							{field:'batchno', title:'批次号',width:100,
								editor:{
						        	type:'readtext'
						        }	
							},
							{field:'storagelocationid', title:'库位',width:170,
								formatter:function(value,rowData,rowIndex){
						       		return rowData.storagelocationname;
						        },
						        editor:{
						        	type:'widget',
						        	options:{
					    				name:'t_storage_purchase_enter_detail',
					    	    		width:160,
					    				col:'storagelocationid',
					    				singleSelect:true
						        	}
						        }
							},
							{field:'auxnum', title:'箱数',width:80,editor:'numberbox'},
							{field:'num', title:'个数',width:80,
								editor:{
									type:'numberbox',
									options:{
										precision:${decimallen}
									}
								}
							}
    			         ]],
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
    			toolbar:'#storage-batchdiv-purchaseEnterAddPage',
    			data:[{},{},{},{},{},{},{},{},{},{}],
    			onClickRow: function(index,row){
   					onClickCell(index, "produceddate");
    			}
    		});
   			$("#storage-savebatch-purchaseEnterDetailEditPage").click(function(){
   				endEditing();
   				var totalnum = $("#storage-purchaseEnter-batch-totalnum").val();
   				var addtotalnum = $("#storage-purchaseEnter-batch-addbatchnum-hidden").val();
   				if(addtotalnum!=totalnum){
   					$.messager.alert("提醒","数量未全部分配批次!");
   					return false;
   				}
   				addBatchNumDetail();
   			});
   		});
   		var editIndex = undefined;
        function endEditing(){
            if (editIndex == undefined){return true}
            if ($("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('validateRow', editIndex)){
                var ed = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getEditor', {index:editIndex,field:'storagelocationid'});
                var storagelocationObject = $(ed.target).widget("getObject");
                if(null!=storagelocationObject){
                	var storagelocationname = storagelocationObject.name;
                    $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getRows')[editIndex]['storagelocationname'] = storagelocationname;
                }
                var auxed = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getEditor', {index:editIndex,field:'auxnum'});
                var ued = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getEditor', {index:editIndex,field:'num'});
                var addaux = $(auxed.target).val();
                if(null==addaux || ""==addaux){
                	addaux = 0;
                }
                var addu =  $(ued.target).val();
                if(null==addu || ""==addu){
                	addu = 0;
                }
                var thisaddunitnum = Number(addaux*boxnum) + Number(addu);
                $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getRows')[editIndex]['unitnum'] = thisaddunitnum;
                $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('endEdit', editIndex);
                countAddNum();
                editIndex = undefined;
                return true;
            } else {
                return false;
            }
        }
        function onClickCell(index, field){
            if (editIndex != index){
                if (endEditing()){
                	$("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('selectRow', index).datagrid('beginEdit', index);
                    var ed = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getEditor', {index:index,field:field});
                    if (ed){
                        ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
                    }
                    
                    var barcodeED = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getEditor', {index:index,field:'barcode'});
                    if(barcodeED){
                    	var bar = $(barcodeED.target).val();
                    	if(null==bar || bar==""){
                    		$(barcodeED.target).val(barcode);
                    	}
                    }
                    editIndex = index;
                } else {
                	$("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('selectRow', editIndex);
                }
            }
        }
        //获取批次号
        function getBacthno(){
        	var ped = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getEditor', {index:editIndex,field:'produceddate'});
            var produceddate = $(ped.target).val();
            if(null!=produceddate && produceddate!=""){
				var goodsid = "${goodsid}";
				$.ajax({   
		            url :'storage/getBatchno.do',
		            type:'post',
		            data:{produceddate:produceddate,goodsid:goodsid},
		            dataType:'json',
		            async:false,
		            success:function(json){
		            	var bed = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getEditor', {index:editIndex,field:'batchno'});
		            	$(bed.target).val(json.batchno);
		            	if(json.deadline!=null && json.deadline!=""){
		            		var ded = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getEditor', {index:editIndex,field:'deadline'});
			            	$(ded.target).val(json.deadline);
		            	}
		            }
		        });
            }
        }
        function getBacthnoByDeadline(){
            var ped = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getEditor', {index:editIndex,field:'deadline'});
            var deadline = $(ped.target).val();
            if(null!=deadline && deadline!=""){
                var goodsid = "${goodsid}";
                $.ajax({
                    url :'storage/getBatchnoByDeadline.do',
                    type:'post',
                    data:{deadline:deadline,goodsid:goodsid},
                    dataType:'json',
                    async:false,
                    success:function(json){
                        var bed = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getEditor', {index:editIndex,field:'batchno'});
                        $(bed.target).val(json.batchno);
                        if(json.produceddate!=null && json.produceddate!=""){
                            var ded = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getEditor', {index:editIndex,field:'produceddate'});
                            $(ded.target).val(json.produceddate);
                        }
                    }
                });
            }
        }
        function countAddNum(){
        	var rows = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getRows');
        	if(null!=rows){
        		var totalnum = 0;
        		for(var i=0;i<rows.length;i++){
        			if(rows[i].produceddate!=null && rows[i].produceddate!=""){
        				var unitnum = rows[i].unitnum;
        				if(unitnum==null || unitnum==""){
        					unitnum = 0;
        				}
        				totalnum += unitnum;
        			}
        		}
        		var unaddnum = totalunitnum-totalnum;
        		if(totalnum>0){
        			var aAunnum = parseInt(totalnum/boxnum);
        			var aIntnum = totalnum%boxnum;
        			var anumdetal = "";
        			if(aAunnum>0){
        				anumdetal = aAunnum + auxunitname;
        			}
        			if(aIntnum>0){
        				anumdetal += Number(aIntnum.toFixed(${decimallen})) + unitname;
        			}
        			$("#storage-purchaseEnter-batch-addbatchnum").val(anumdetal);
        			$("#storage-purchaseEnter-batch-addbatchnum-hidden").val(totalnum);
        		}else{
        			$("#storage-purchaseEnter-batch-addbatchnum").val("");
        			$("#storage-purchaseEnter-batch-addbatchnum-hidden").val(0);
        		}
        		if(unaddnum>0){
        			var uAunnum = parseInt(unaddnum/boxnum);
        			var uIntnum = unaddnum%boxnum;
        			var unumdetal = "";
        			if(uAunnum>0){
        				unumdetal = uAunnum + auxunitname;
        			}
        			if(uIntnum>0){
        				unumdetal += Number(uIntnum.toFixed(${decimallen})) + unitname;
        			}
        			$("#storage-purchaseEnter-batch-nonum").val(unumdetal);
        			$("#storage-purchaseEnter-batch-nonum-hidden").val(unaddnum);
        		}else{
        			$("#storage-purchaseEnter-batch-nonum").val("");
        			$("#storage-purchaseEnter-batch-nonum-hidden").val(0);
        		}
        		
        		if(totalnum>totalunitnum){
        			$.messager.alert("提醒","分配数量超过总数量!");
        		}
        	}
        }
        function addBatchNumDetail(){
        	var rows = $("#storage-batchdatagrid-purchaseEnterAddPage").datagrid('getRows');
        	if(null!=rows && rows.length>0){
        		var form = $("#storage-form-purchaseEnterDetailEditPage").serializeJSON();
        		var row = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getSelected');
        		var rowIndex = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getRowIndex', row);
        		form.goodsInfo = row.goodsInfo;
        		form.buyorderid = row.buyorderid;
        		form.buyorderdetailid = row.buyorderdetailid;
        		form.field01 = row.field01;
        		form.field02 = row.field02;
        		$("#storage-datagrid-purchaseEnterAddPage").datagrid("deleteRow",rowIndex);
        		for(var i=0;i<rows.length;i++){
        			if(null!=rows[i].produceddate && rows[i].produceddate!=""){
        				var newObject = copyObject(form);
        				var unitnum = rows[i].unitnum;
        				var uAunnum = parseInt(unitnum/boxnum);
            			var uIntnum = unitnum%boxnum;
            			var unumdetal = "";
            			if(uAunnum>0){
            				unumdetal = uAunnum + auxunitname;
            			}
            			if(uIntnum>0){
            				unumdetal += Number(uIntnum.toFixed(${decimallen})) + unitname;
            			}
        				var taxamount = Number(form.taxprice * unitnum).toFixed(2);
        				var notaxamount = Number(taxamount/1.17).toFixed(2);
        				var tax = taxamount - notaxamount;
        				var barcode = rows[i].barcode;
        				var batchno = rows[i].batchno;
            			var produceddate = rows[i].produceddate;
            			var deadline = rows[i].deadline;
            			var storagelocationid = rows[i].storagelocationid;
            			var storagelocationname = rows[i].storagelocationname;
            			
            			newObject.unitnum = unitnum;
            			newObject.auxnum = uAunnum;
            			newObject.auxremainder = uIntnum;
            			newObject.auxnumdetail = unumdetal;
            			newObject.taxamount = taxamount;
            			newObject.notaxamount = notaxamount;
            			newObject.tax = tax;
                        if(form.initnum>0){
                            newObject.initnum = unitnum;
                        }
            			newObject.barcode = barcode;
            			newObject.batchno = batchno;
            			newObject.produceddate = produceddate;
            			newObject.deadline = deadline;
            			newObject.storagelocationid = storagelocationid;
            			newObject.storagelocationname = storagelocationname;
            			
            			$("#storage-datagrid-purchaseEnterAddPage").datagrid('insertRow',{index:rowIndex, row:newObject});
            			rowIndex ++;
        			}
        		}
        		$('#storage-dialog-batchno-purchaseEnterAddPage-content').dialog("destroy");
        		$("#storage-dialog-purchaseEnterAddPage-content").dialog('destroy');
        	}
        }
        function copyObject( obj ){
            return JSON.parse( JSON.stringify( obj ) );
        }
   </script>
  </body>
</html>

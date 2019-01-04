<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
			<form id="account-form-goldTaxCustomerInvoiceDetailAddPage" method="post">
				<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="text-align: right;">开票商品名称：</td>
						<td colspan="2"><input type="text" id="account-goldTaxCustomerInvoiceDetail-goodsname" name="goodsname"  style="width:300px;" class="easyui-validatebox " data-options="required:true,validType:['maxByteLength[250]']" tabindex="1"  /></td>
						<td>
							<a href="javaScript:void(0);" onclick="javascript:showDetailGoldTaxGoodsInfoDialog()" id="account-goldTaxCustomerInvoiceDetail-goodsname-addfromerp-btn" class="easyui-linkbutton button-list" data-options="plain:false,iconCls:'button-add'">档案填入</a>
							<input type="hidden" id="account-goldTaxCustomerInvoiceDetail-goodsid" name="goodsid" />
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">金税分类编码：</td>
						<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-jstypeid" name="jstypeid" style="width:150px; " tabindex="2" /></td>
						<td style="text-align: right;">规格型号：</td>
						<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-model" name="model" style="width:150px;"  tabindex="3" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">计量单位：</td>
						<td>
							<input type="text" id="account-goldTaxCustomerInvoiceDetail-unitname" name="unitname" style="width:130px;" class="easyui-validatebox "  required="required"  tabindex="4" />
							<a href="javaScript:void(0);" onclick="javascript:showDetailUnitnameInfoDialog();" id="account-goldTaxCustomerInvoiceDetail-unitname-addfromerp-btn" class="easyui-linkbutton button-list" data-options="plain:false,iconCls:'button-add'">档案填入</a>
						</td>
						<td style="text-align: right;">数量：</td>
						<td>
							<input type="text" id="account-goldTaxCustomerInvoiceDetail-unitnum" class="easyui-validatebox formaterNum" name="unitnum" style="width:150px;" required="required" validType="intOrFloat"  tabindex="5" />
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">含税单价：</td>
						<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-taxprice" name="taxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"  tabindex="6" /></td>
						<td style="text-align: right;">含税金额：</td>
						<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-taxamount" name="taxamount" class="easyui-validatebox <c:if test="${colMap.taxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> style="width:150px;" tabindex="7" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">未税单价：</td>
						<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-notaxprice" name="notaxprice" class="easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat" style="width:150px;" tabindex="8" /></td>
						<td style="text-align: right;">未税金额：</td>
						<td>
							<input type="text" id="account-goldTaxCustomerInvoiceDetail-notaxamount" name="notaxamount" class="easyui-validatebox <c:if test="${colMap.notaxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat" style="width:150px;" tabindex="9" />
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">税率：</td>
						<td>
							<input type="text" id="account-goldTaxCustomerInvoiceDetail-taxrate" name="taxrate" class="easyui-validatebox <c:if test="${colMap.taxrate == null }">readonly</c:if>"  required="required" validType="integer" style="width:150px;" tabindex="10" />
						</td>
						<td style="text-align: right;">税额：</td>
						<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-tax" name="tax" class="easyui-numberbox readonly" data-options="precision:2" readonly="readonly" style="width:150px; "/></td>
					</tr>
					<tr>
						<td style="text-align: right;">免税标识：</td>
						<td>
							<select id="account-goldTaxCustomerInvoiceDetail-taxfreeflag" name="taxfreeflag" style="width:150px;" tabindex="11">
								<option value="0">其他税率</option>
								<option value="1">普通零税率</option>
								<option value="2">免税</option>
							</select>
						</td>
						<td style="text-align: right;">备注：</td>
						<td>
							<input type="text" id="account-goldTaxCustomerInvoiceDetail-remark" name="remark" style="width:150px;" tabindex="12"/>
						</td>
					</tr>
				</table>
				<input type="hidden" id="account-goldTaxCustomerInvoiceDetail-id" name="id" />
				<input type="hidden" id="account-goldTaxCustomerInvoiceDetail-sourcetype" name="sourcetype" value="0" />
			</form>
  		</div>
      <div data-options="region:'south',border:false">
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
	  			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span><input type="button" value="继续添加" name="savegoon" id="account-goldTaxCustomerInvoiceDetailAddPage-addSaveGoOn" />
	  			<input type="button" value="确 定" name="savenogo" id="account-goldTaxCustomerInvoiceDetailAddPage-addSave" />
  			</div>
	  	</div>
  	</div>
  	<script type="text/javascript">

        $("#account-goldTaxCustomerInvoiceDetail-jstypeid").widget({
            referwid:'RL_T_BASE_JSTAXTYPECODE',
            singleSelect:true,
            width:'150',
            required:true,
            onSelect: function(data) {
            	$("#account-goldTaxCustomerInvoiceDetail-model").focus();
            	$("#account-goldTaxCustomerInvoiceDetail-model").select();
        	}
        });


        function orderDetailAddSaveGoOnDialog(){
  			var $DetailOper=$("#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content");
  	  		if($DetailOper.size()>0){
  				$DetailOper.dialog('close');
  	  		}
  		   	$('<div id="account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content"></div>').appendTo('#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper');
  	  		$DetailOper=$("#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content");
  	  		
  			$DetailOper.dialog({
  				title:'开票信息新增(按ESC退出)',
  			    width: 600,  
  			    height: 440,
  			    closed: true,  
  			    cache: false, 
  			    modal: true,
  			    maximizable:true,
  			    href:"account/goldtaxcustomerinvoice/showGoldTaxCustomerInvoiceDetailAddPage.do",
			    onLoad:function(){
			    	$("#account-goldTaxCustomerInvoiceDetail-goodsname").focus();
		    	},
			    onClose:function(){
		            $DetailOper.dialog("destroy");
		        }
  			});
  			$DetailOper.dialog("open");
  		}
		function saveOrderDetail(isGoOn){
	    	$("#account-goldTaxCustomerInvoiceDetail-remark").focus();
  			var flag=$("#account-form-goldTaxCustomerInvoiceDetailAddPage").form('validate');
  			if(!flag){
	  			return false;
  			}

            var taxrate=$("#account-goldTaxCustomerInvoiceDetail-taxrate").val() || 0;
            var taxfreeflag=$("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").val() || 0;
            if(taxrate<0){
                $.messager.alert("提醒","税率不能小于0！");
                $("#account-goldTaxCustomerInvoiceDetail-taxrate").focus();
                $("#account-goldTaxCustomerInvoiceDetail-taxrate").select();
                return false;
            }else if(taxrate==0 && taxfreeflag==0){
                $.messager.alert("提醒","当前税率为零，请选择免税标识。");
                $("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").focus();
                return false;
			}
            if(taxfreeflag==1 || taxfreeflag==2){
                if(taxrate!=0){
                    $("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").val(0);
                }
			}

			var unitnum=$("#account-goldTaxCustomerInvoiceDetail-unitnum").val();
			if(unitnum==null || $.trim(unitnum)=="" || unitnum==0){
				$.messager.alert("提醒","抱歉，请填写数量！");
                $("#account-goldTaxCustomerInvoiceDetail-unitnum").focus();
                $("#account-goldTaxCustomerInvoiceDetail-unitnum").select();
				return false;
			}
            var taxprice=$("#account-goldTaxCustomerInvoiceDetail-taxprice").val();
            if(taxprice==null || $.trim(taxprice)=="" || taxprice==0|| taxprice<0){
                $.messager.alert("提醒","抱歉，请填写含税单价！");
                $("#account-goldTaxCustomerInvoiceDetail-taxprice").focus();
                $("#account-goldTaxCustomerInvoiceDetail-taxprice").select();
                return false;
            }
            var taxamount=$("#account-goldTaxCustomerInvoiceDetail-taxamount").val();
            if(taxamount==null || $.trim(taxamount)=="" || taxamount==0){
                $.messager.alert("提醒","抱歉，请填写含税金额！");
                $("#account-goldTaxCustomerInvoiceDetail-taxamount").focus();
                $("#account-goldTaxCustomerInvoiceDetail-taxamount").select();
                return false;
            }
            var notaxprice=$("#account-goldTaxCustomerInvoiceDetail-notaxprice").val();
            if(notaxprice==null || $.trim(notaxprice)=="" || notaxprice==0 || notaxprice<0){
                $.messager.alert("提醒","抱歉，请填写未税单价！");
                $("#account-goldTaxCustomerInvoiceDetail-notaxprice").focus();
                $("#account-goldTaxCustomerInvoiceDetail-notaxprice").select();
                return false;
            }
            var notaxamount=$("#account-goldTaxCustomerInvoiceDetail-notaxamount").val();
            if(notaxamount==null || $.trim(notaxamount)=="" || notaxamount==0){
                $.messager.alert("提醒","抱歉，请填写未税金额！");
                $("#account-goldTaxCustomerInvoiceDetail-notaxamount").focus();
                $("#account-goldTaxCustomerInvoiceDetail-notaxamount").select();
                return false;
            }


			var formdata=$("#account-form-goldTaxCustomerInvoiceDetailAddPage").serializeJSON();
			//保存前判断单价*数量 是否跟金额一致
			var taxprice = formdata.taxamount/formdata.unitnum;
			if(Number(taxprice).toFixed(2) != Number(formdata.taxprice).toFixed(2)){
				$.messager.alert("提醒","单价*数量与金额不一致，无法保存！");
				return false;
			}
			if(formdata){
			    formdata.upid=new Date().getTime();
				var index=getAddRowIndex();
				$("#account-goldTaxCustomerInvoiceAddPage-goldTaxCustomerInvoicetable").datagrid('updateRow',{
					index:index,
					row:formdata
				});
				footerReCalc();
			}
			if(isGoOn){
				orderDetailAddSaveGoOnDialog();
			}else{
	  			$("#account-goldTaxCustomerInvoiceAddPage-dialog-DetailOper-content").dialog("close");	
			}		
		}


  		$(document).ready(function(){
    		
  			$("#account-goldTaxCustomerInvoiceDetail-unitnum").change(function(){
    			if($(this).validatebox('isValid')){
                    unitnumChange();
    			}
    		});
    		
    		$("#account-goldTaxCustomerInvoiceDetail-taxprice").change(function(){
        		if($(this).validatebox('isValid') && !$(this).hasClass("readonly")){
        			priceChange("1",this);
        		}
    		});
            $("#account-goldTaxCustomerInvoiceDetail-taxprice").focus(function(){
                if(!$(this).hasClass("readonly")){
                    $(this).select();
                }else{
                    $("#account-goldTaxCustomerInvoiceDetail-taxamount").focus();
                    $("#account-goldTaxCustomerInvoiceDetail-taxamount").select();
                }
            });

            $("#account-goldTaxCustomerInvoiceDetail-taxamount").change(function(){
                if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
                    amountChange("1","account-goldTaxCustomerInvoiceDetail-taxamount");
                }
            });
            $("#account-goldTaxCustomerInvoiceDetail-taxamount").focus(function(){
                if(!$(this).hasClass("readonly")){
                    $(this).select();
                }else{
                    $("#account-goldTaxCustomerInvoiceDetail-notaxprice").focus();
                    $("#account-goldTaxCustomerInvoiceDetail-notaxprice").select();
                }
            });

    		$("#account-goldTaxCustomerInvoiceDetail-notaxprice").change(function(){
        		if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
        			priceChange("2",this);
        		}
    		});

    		$("#account-goldTaxCustomerInvoiceDetail-notaxprice").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
		   			$("#account-goldTaxCustomerInvoiceDetail-notaxamount").focus();
		   			$("#account-goldTaxCustomerInvoiceDetail-notaxamount").select();
        		}
    		});


            $("#account-goldTaxCustomerInvoiceDetail-notaxamount").focus(function(){
                if(!$(this).hasClass("readonly")){
                    $(this).select();
                }else{
                    $("#account-goldTaxCustomerInvoiceDetail-taxrate").focus();
                    $("#account-goldTaxCustomerInvoiceDetail-taxrate").select();
                }
            });

            $("#account-goldTaxCustomerInvoiceDetail-notaxamount").change(function(){
                if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
                    amountChange("2","account-goldTaxCustomerInvoiceDetail-notaxamount");
                }
            });
            $("#account-goldTaxCustomerInvoiceDetail-taxrate").focus(function(){
                if(!$(this).hasClass("readonly")){
                    $(this).select();
                }else{
                    $("#account-goldTaxCustomerInvoiceDetail-remark").blur();
                    $("#account-goldTaxCustomerInvoiceDetail-remark").select();
                }
            });
            $("#account-goldTaxCustomerInvoiceDetail-taxrate").change(function(){
                if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
                    unitnumChange();
                    var taxfreeflag=$("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").val();
                    if($(this).val()==0){
                        if(taxfreeflag!=1 && taxfreeflag!=2) {
                            $("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").val("1");
                        }
					}else if(taxfreeflag!=0){
                        $("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").val("0");
					}
                }
            });
            $("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").focus(function(){
                if(!$(this).hasClass("readonly")){
                    $(this).select();
                }else{
                    $("#account-goldTaxCustomerInvoiceDetail-remark").focus();
                    $("#account-goldTaxCustomerInvoiceDetail-remark").select();
                }
            });
			
	  		$("#account-goldTaxCustomerInvoiceDetailAddPage-addSave").click(function(){
				saveOrderDetail(false);		
	  		});

	  		$("#account-goldTaxCustomerInvoiceDetailAddPage-addSaveGoOn").click(function(){
				saveOrderDetail(true);		
	  		});

	  		$("#account-goldTaxCustomerInvoiceDetail-goodsname").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#account-goldTaxCustomerInvoiceDetail-jstypeid").blur();
		   			$("#account-goldTaxCustomerInvoiceDetail-jstypeid").focus();
		   			$("#account-goldTaxCustomerInvoiceDetail-jstypeid").select();
				}
		    });


            $("#account-goldTaxCustomerInvoiceDetail-model").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    $("#account-goldTaxCustomerInvoiceDetail-model").blur();
                    $("#account-goldTaxCustomerInvoiceDetail-unitname").focus();
                    $("#account-goldTaxCustomerInvoiceDetail-unitname").select();
                }
            });
            $("#account-goldTaxCustomerInvoiceDetail-unitname").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    $("#account-goldTaxCustomerInvoiceDetail-unitname").blur();
                    $("#account-goldTaxCustomerInvoiceDetail-unitnum").focus();
                    $("#account-goldTaxCustomerInvoiceDetail-unitnum").select();
                }
            });

	  		$("#account-goldTaxCustomerInvoiceDetail-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#account-goldTaxCustomerInvoiceDetail-unitnum").blur();
		   			$("#account-goldTaxCustomerInvoiceDetail-taxprice").focus();
				}
		    });
            $("#account-goldTaxCustomerInvoiceDetail-taxprice").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    $("#account-goldTaxCustomerInvoiceDetail-taxprice").blur();
                    $("#account-goldTaxCustomerInvoiceDetail-taxamount").focus();
                }
            });
            $("#account-goldTaxCustomerInvoiceDetail-taxamount").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    $("#account-goldTaxCustomerInvoiceDetail-taxamount").blur();
                    $("#account-goldTaxCustomerInvoiceDetail-notaxprice").focus();
                }
            });
            $("#account-goldTaxCustomerInvoiceDetail-notaxprice").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    $("#account-goldTaxCustomerInvoiceDetail-notaxprice").blur()
                    $("#account-goldTaxCustomerInvoiceDetail-notaxamount").focus();
                    $("#account-goldTaxCustomerInvoiceDetail-notaxamount").select();
                }
            });
            $("#account-goldTaxCustomerInvoiceDetail-notaxamount").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    $("#account-goldTaxCustomerInvoiceDetail-notaxamount").blur()
                    $("#account-goldTaxCustomerInvoiceDetail-taxrate").focus();
                    $("#account-goldTaxCustomerInvoiceDetail-taxrate").select();
                }
            });
            $("#account-goldTaxCustomerInvoiceDetail-taxrate").die("keydown").live("keydown",function(event){
                //enter
                if(event.keyCode==13){
                    $("#account-goldTaxCustomerInvoiceDetail-taxrate").blur()
                    $("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").focus();
                    $("#account-goldTaxCustomerInvoiceDetail-taxfreeflag").select();
                }
            });
	  		$("#account-goldTaxCustomerInvoiceDetail-remark").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#account-goldTaxCustomerInvoiceDetail-remark").blur();
		   			$("#account-goldTaxCustomerInvoiceDetailAddPage-addSaveGoOn").focus();
				}
		    });
	  		$("#account-goldTaxCustomerInvoiceDetailAddPage-addSaveGoOn").die("keydown").live("keydown",function(event){
	  			if(event.keyCode==13){
	  				saveOrderDetail(true);
	  			}
	  		});
  		});
  	</script>
  </body>
</html>

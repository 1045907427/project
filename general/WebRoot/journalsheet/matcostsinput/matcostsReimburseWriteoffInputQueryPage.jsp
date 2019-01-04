<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫收回新增查询</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    	<table id="journalsheet-table-matcostsReimburseWriteoffInputQuery"></table>
  		<div id="journalsheet-toolbar-matcostsReimburseWriteoffInputQuery">  		
  			<table>
					<tr>
    				<td align="right">可核销收回金额：</td>
    				<td><input id="journalsheet-numberic-selectReimburseAmount-matcostsReimburseWriteoffInputQuery" value="${param.amount}" type="text" style="width: 130px;border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" /></td>
    				<td align="right">选中代垫金额：</td>
    				<td><input id="journalsheet-numberic-selectInputAmount-matcostsReimburseWriteoffInputQuery" value="0" type="text" style="width: 130px;border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly"/></td>
    				<td align="right">核销日期:</td>
    				<td><input type="text" id="journalsheet-businessdate-matcostsReimburseWriteoffInputQuery"  value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/></td>
   				</tr>
			</table>
  			<form id="journalsheet-form-matcostsReimburseWriteoffInputQuery" method="post"> 				
  				<table>    			
	    			<tr>
	    				<td align="right">业务日期:</td>
	    				<td><input type="text" name="begintime" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="endtime" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td align="right">供应商:</td>
	    				<td><input id="journalsheet-widget-supplier-matcostsReimburseWriteoffInputQuery" type="text" readonly="readonly" style="width: 130px;" value="${param.supplierid}"/></td>
	    			</tr>
	    			<tr>
	    				<td align="right">OA编号：</td>
	    				<td colspan="3"><input id="report-query-aoid" type="text" name="oaidarr" style="width: 450px;"/>(多个数据，请使用英文逗号)</td>
	    			</tr>
	    			<tr>
						<td colspan="4" align="right">
                            <div>
                                <a href="javaScript:void(0);" id="journalsheet-query-matcostsReimburseWriteoffInputQuery" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="journalsheet-reload-matcostsReimburseWriteoffInputQuery" class="button-qr">重置</a>
                                <a href="javaScript:void(0);" id="journalsheet-okSelect-matcostsReimburseWriteoffInputQuery" class="button-qr" style="width:120px;letter-spacing:normal">确定选中并核销</a>
                            </div>
                        </td>
	    			</tr>
    			</table>
    			<input type="hidden" id="journalsheet-hidden-supplier-matcostsReimburseWriteoffInputQuery" name="supplierid" value="${param.supplierid}" />
  			</form>
  			<input type="hidden" id="journalsheet-reimburseIdarrs-matcostsReimburseWriteoffInputQuery" name="reimburseIdarrs" value="${param.idarr}"/>
  		</div>
  		<script type="text/javascript">
  			var Settle_footerobject=null;
  			var reimburseWriteoffInputColJson=null;
  			$(document).ready(function(){
  				//根据初始的列与用户保存的列生成以及字段权限生成新的列
  		    	reimburseWriteoffInputColJson=$("#journalsheet-table-matcostsReimburseWriteoffInputQuery").createGridColumnLoad({
  			     	name:'t_js_matcostsinput',
  			     	frozenCol:[[
  						{field:'idok',checkbox:true,isShow:true}
  			     	]],
  			     	commonCol:[[
  		  				{field:'id',title:'编码',width:120,sortable:true},
  			     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
  						{field:'oaid',title:'OA编号',width:80,sortable:true},
  						{field:'brandid',title:'商品品牌',width:60,sortable:true,
  							formatter:function(val,rowData,rowIndex){
  								return rowData.brandname;
  							}
  						},
  						{field:'supplierid',title:'供应商编码',width:70,sortable:true},
  						{field:'suppliername',title:'供应商名称',width:210,sortable:true,isShow:true},
  						{field:'supplierdeptid',title:'所属部门',width:70,sortable:true,
  							formatter:function(val,rowData,rowIndex){
  								return rowData.supplierdeptname;
  							}
  						},
  						{field:'customerid',title:'客户名称',width:130,sortable:true,
  							formatter:function(val,rowData,rowIndex){
  								return rowData.customername;
  							}
  						},
  						{field:'subjectid',title:'科目名称',width:60,sortable:true,
  							formatter:function(val,rowData,rowIndex){
  								return rowData.subjectname;
  							}
  						},
  						{field:'factoryamount',title:'工厂投入',resizable:true,sortable:true,align:'right',
  							formatter:function(val,rowData,rowIndex){
  								if(val != "" && val != null){
  									return formatterMoney(val);
  								}
  								else{
  									return "0.00";
  								}
  							}
  						},
  						<%-- 通用版没有
  						{field:'htcompdiscount',title:'电脑折让',resizable:true,sortable:true,align:'right',
  							formatter:function(val,rowData,rowIndex){
  								if(val != "" && val != null){
  									return formatterMoney(val);
  								}
  								else{
  									return "0.00";
  								}
  							}
  						},
  						{field:'htpayamount',title:'支付金额',resizable:true,sortable:true,align:'right',
  							formatter:function(val,rowData,rowIndex){
  								if(val != "" && val != null){
  									return formatterMoney(val);
  								}
  								else{
  									return "0.00";
  								}
  							}
  						},
  						{field:'branchaccount',title:'转入分公司',resizable:true,sortable:true,align:'right',
  							formatter:function(val,rowData,rowIndex){
  								if(val != "" && val != null){
  									return formatterMoney(val);
  								}
  								else{
  									return "0.00";
  								}
  							}
  						},
  						--%>
  						{field:'reimbursetype',title:'收回方式',width:60,sortable:true,hidden:true,
  							formatter:function(val,rowData,rowIndex){
  								return rowData.reimbursetypename;
  							}
  						},
  						{field:'remark',title:'备注',width:80,sortable:true},
  						{field:'adduserid',title:'制单人编码',width:80,sortable:true,hidden:true},
  						{field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
  						{field:'addtime',title:'制单时间',width:130,sortable:true,hidden:true}
  					]]
  			     });

  		    
  				
  				//查询
  				$("#journalsheet-query-matcostsReimburseWriteoffInputQuery").click(function(){
  	  				var supplierid=$("#journalsheet-widget-supplier-matcostsReimburseWriteoffInputQuery").widget("getValue");
  	  				if(null==supplierid || ""==supplierid){
	  	  				$.messager.alert("提示","请选择供应商！");
						return false;
  	  				}
  					//把form表单的name序列化成JSON对象
  		      		var queryJSON = $("#journalsheet-form-matcostsReimburseWriteoffInputQuery").serializeJSON();
		      		$("#journalsheet-table-matcostsReimburseWriteoffInputQuery").datagrid({
			      		url:'journalsheet/matcostsInput/getMatcostsInputPageList.do?showCanWriteoff=true',
		      			pageNumber:1,
						queryParams:queryJSON
		      		}).datagrid("columnMoving");
  				});

  				//重置
  				$("#journalsheet-reload-matcostsReimburseWriteoffInputQuery").click(function(){
  					$("#journalsheet-form-matcostsReimburseWriteoffQuery").form("reset");
  					$("#journalsheet-numberic-selectInputAmount-matcostsReimburseWriteoffInputQuery").numberbox("setValue",0);
  					$("#journalsheet-table-matcostsReimburseWriteoffInputQuery").datagrid('loadData',{total:0,rows:[]});
					$("#journalsheet-table-matcostsReimburseWriteoffInputQuery").datagrid("clearChecked");
  				});

  				

  				//供应商查询
  			  	$("#journalsheet-widget-supplier-matcostsReimburseWriteoffInputQuery").widget({
  			  		width:284,
  					name:'t_js_matcostsinput',
  					col:'supplierid',
  					singleSelect:true,
  					view:true,
  					onSelect:function(data){
  						$("#journalsheet-hidden-supplier-matcostsReimburseWriteoffInputQuery").val(data.id);
  					},
  					onClear:function(){
  						$("#journalsheet-hidden-supplier-matcostsReimburseWriteoffInputQuery").val("");  						
  					}
  				});
  			  	$("#journalsheet-numberic-selectReimburseAmount-matcostsReimburseWriteoffInputQuery").numberbox({
					 precision:2,
					 groupSeparator:','
				});
				$("#journalsheet-numberic-selectInputAmount-matcostsReimburseWriteoffInputQuery").numberbox({
					 precision:2,
					 groupSeparator:','
				});
				
  				$("#journalsheet-okSelect-matcostsReimburseWriteoffInputQuery").click(function(){
					var supplierid=$("#journalsheet-widget-supplier-matcostsReimburseWriteoffInputQuery").widget("getValue");
					if(null==supplierid || ""==supplierid){
						$.messager.alert("提示","抱歉，未找到相关供应商！");
						return false;
					}
  	  				var businessdate=$("#journalsheet-businessdate-matcostsReimburseWriteoffInputQuery").val();
	  	  			if(businessdate==""){
  	  					$.messager.alert("提示","抱歉，请选择核销日期");
						return false;
	  	  			}
  					var reimburseIdarrs= $("#journalsheet-reimburseIdarrs-matcostsReimburseWriteoffInputQuery").val();
  					if(reimburseIdarrs==null || $.trim(reimburseIdarrs)==""){
  	  					$.messager.alert("提示","抱歉，我们未能从当前步骤找到收回信息，请退回第一步");
						return false;
  					}
  					reimburseIdarrs=$.trim(reimburseIdarrs);
  					var rows =  $("#journalsheet-table-matcostsReimburseWriteoffInputQuery").datagrid('getChecked');
  					if(null==rows || rows.length==0){
	  	  				$.messager.alert("提示","请选择要收回的代垫");
						return false;
  					}
  					var idarr=new Array();
  					var amount=0;
  					for(var i=0;i<rows.length;i++){
						if(rows[i].supplierid!=supplierid){
							$.messager.alert("提示","查询条件里的供应商与查询结果里的供应商不一致！");
							return false;
						}
  						if(rows[i].iswriteoff!='1'){
  	  	  					if(rows[i].id){
  	  	  	  					idarr.push(rows[i].id);
  	  	  					}
  	  	    				amount = Number(amount)+Number(rows[i].factoryamount == undefined ? 0 : rows[i].factoryamount);
  	  					}
  					}
  					
  					if(idarr.length==0){
	  	  				$.messager.alert("提示","请选择要收回的代垫");
						return false;
  					}
					var selectReimburseAmount=$("#journalsheet-numberic-selectReimburseAmount-matcostsReimburseWriteoffInputQuery").numberbox("getValue");
  					
  					if(selectReimburseAmount<amount){
	  	  				$.messager.alert("提示","抱歉，收回的金额要大于代垫金额");
	  	  				$("#journalsheet-numberic-selectInputAmount-matcostsReimburseWriteoffInputQuery").numberbox("setValue",amount);
						return false;  	  					
  					}
  					var supplierid=$("#journalsheet-widget-supplier-matcostsReimburseWriteoffInputQuery").widget("getValue");
  					if(null==supplierid || ""==supplierid){
	  	  				$.messager.alert("提示","请选择供应商！");
						return false;
	  				}
	  				
	  	  			$.messager.confirm("提醒","是否核销当前代垫?",function(r){
						if(r){
			  	  			var queryJSON = {};
			  	  			queryJSON.inputIdarrs=idarr.join(",");
			  	  			queryJSON.reimburseIdarrs=reimburseIdarrs;
			  	  			queryJSON.businessdate=businessdate;
							loading("正在核销代垫中，请稍后...");
				  	  		$.ajax({
				  	  		   type: "POST",
				  	  		   url: "journalsheet/matcostsInput/matcostsReimburseWriteoff.do",
				  	  		   cache: false,		  	  		   
				  	  		   data: queryJSON,
				  	  		   dataType:'json',
				  	  		   success: function(data){
								   loaded();
					  	  		   if(typeof(data)=="undefined"){
					  	  				$.messager.alert("提示","抱歉，操作未成功");
						  	  		   return false;
					  	  		   }
					  	  		   if(data.flag==true){
					  	  				$.messager.alert("提示","核销成功");
					  	  				$("#journalsheet-numberic-selectReimburseAmount-matcostsReimburseWriteoffInputQuery").numberbox("setValue","0");
					  	  				$("#journalsheet-reimburseIdarrs-matcostsReimburseWriteoffInputQuery").val("");
					  	 				$('#matcostsInputWriteoffInputQueryPage-dialog-operate-content').dialog('close');
					  	       			queryJSON = $("#journalsheet-form-matcostsReimburseWriteoffQuery").serializeJSON();					
					  	 				$('#journalsheet-table-matcostsReimburseWriteoffQuery').datagrid('load',queryJSON);
					  	 				reimburse_RefreshDataGrid();
					  	 				input_RefreshDataGrid();					  	 				
					  	  		   }else{
					  	  				$.messager.alert("提示","抱歉，核销失败");
					  	  				realoadCanWriteoffReimburseamount();
					  	  				return false;
					  	  		   }
				  	  		   },
								error: function(jqXHR, textStatus, errorMsg) {
									loaded();
								},
								complete:function(XHR, TS){
									loaded();
								}
				  	  		});
						}
	  	  			});
  				});
  			});
  			function reimburse_RefreshDataGrid(){
  				try{
  					var queryJSON ={};
  	  				if($('#journalsheet-table-matcostsReimburse').size()>0){
  					queryJSON = $("#matcostsReimburse-form-ListQuery").serializeJSON();					
  	 				$('#journalsheet-table-matcostsReimburse').datagrid('load',queryJSON);
  	  				}else{	
	  	  				var pageListUrl="/journalsheet/matcostsInput/matcostsReimbursePage.do";	
  	  	  				var tabWin=tabsWindowURL(pageListUrl);
  	  	  				if(tabWin!=null){
	  						queryJSON = tabWin.$("#matcostsReimburse-form-ListQuery").serializeJSON();		
	  						tabWin.$("#journalsheet-table-matcostsReimburse").datagrid('load',queryJSON);
  	  	  				}
  	  				}
  				}catch(e){
  				}
  			}
  			function input_RefreshDataGrid(){
  				try{
  					var queryJSON ={};
  	  				if($('#journalsheet-table-matcostsInput').size()>0){
  					queryJSON = $("#matcostsInput-form-ListQuery").serializeJSON();					
  	 				$('#journalsheet-table-matcostsInput').datagrid('load',queryJSON);
  	  				}else{	
	  	  				var pageListUrl="/journalsheet/matcostsInput/matcostsReimbursePage.do";	
  	  	  				var tabWin=tabsWindowURL(pageListUrl);
  	  	  				if(tabWin!=null){
	  						queryJSON = tabWin.$("#matcostsInput-form-ListQuery").serializeJSON();		
	  						tabWin.$("#journalsheet-table-matcostsInput").datagrid('load',queryJSON);
  	  	  				}
  	  				}
  				}catch(e){
  				}
  			}
  			function wruteoffInputCountTotalAmount(){
  	    		var rows =  $("#journalsheet-table-matcostsReimburseWriteoffInputQuery").datagrid('getChecked');
  	    		if(null==rows || rows.length==0){
  	           		var foot=[];
  	    			if(null!=Settle_footerobject){
  		        		foot.push(Settle_footerobject);
  		    		}  		    		
  	    			$("#journalsheet-table-matcostsReimburseWriteoffInputQuery").datagrid("reloadFooter",foot);
					try{
	    				$("#journalsheet-numberic-selectInputAmount-matcostsReimburseWriteoffInputQuery").numberbox("setValue",0);
					}catch(ex){
						
					}
  	           		return false;
  	       		}
  	    		var factoryamount = 0;
  	    		var htcompdiscount = 0;
  	    		var htpayamount=0;
  	    		var branchaccount=0;
  	    		var amount=0;
  	    		for(var i=0;i<rows.length;i++){
  	    			factoryamount = Number(factoryamount)+Number(rows[i].factoryamount == undefined ? 0 : rows[i].factoryamount);
  	    			htcompdiscount = Number(htcompdiscount)+Number(rows[i].htcompdiscount == undefined ? 0 : rows[i].htcompdiscount);
  	    			htpayamount = Number(htpayamount)+Number(rows[i].htpayamount == undefined ? 0 : rows[i].htpayamount);
  	    			branchaccount = Number(branchaccount)+Number(rows[i].branchaccount == undefined ? 0 : rows[i].branchaccount);
  	    			if(rows[i].iswriteoff!='1'){
  	    				amount = Number(amount)+Number(rows[i].factoryamount == undefined ? 0 : rows[i].factoryamount);
  	    			}
  	    		}
  	    		var foot=[{suppliername:'选中金额',factoryamount:factoryamount,htcompdiscount:htcompdiscount,htpayamount:htpayamount,branchaccount:branchaccount}];
  	    		if(null!=Settle_footerobject){
  	        		foot.push(Settle_footerobject);
  	    		}
  	    		$("#journalsheet-numberic-selectInputAmount-matcostsReimburseWriteoffInputQuery").numberbox("setValue",amount);
  	    		$("#journalsheet-table-matcostsReimburseWriteoffInputQuery").datagrid("reloadFooter",foot);
  	    	}
  	    	function realoadCanWriteoffReimburseamount(){
  	    		loading("重新计算可核销收回金额");
	  				try{
	  	       			var queryJSON = $("#journalsheet-form-matcostsReimburseWriteoffQuery").serializeJSON();					
	  	 				$('#journalsheet-table-matcostsReimburseWriteoffQuery').datagrid('load',queryJSON);
	  	 				var idarrs=$("#journalsheet-reimburseIdarrs-matcostsReimburseWriteoffInputQuery").val()||"";
	  	 				var idArr=idarrs.split(',');
	  	 				var iLen=idArr.length;
	  	 				if(iLen>2){
	  	  	 				iLen=iLen-2;
	  	 				}
	  	 				for(var i=0;i<iLen;i++){
	  	  	 				var item=idArr[i];
	  	  	 				if(item==null || item==""){
	  	  	  	 				continue;
	  	  	 				}
	  	  	 				$('#journalsheet-table-matcostsReimburseWriteoffQuery').datagrid("selectRecord",item);
	  	 				} 
	  	 				var rows= $('#journalsheet-table-matcostsReimburseWriteoffQuery').datagrid("getChecked");
	  	 				var idarr=new Array();
	  					var amount=0;
	  					for(var i=0;i<rows.length;i++){
	  	  					if(rows[i].iswriteoff!='1'){
	  	  	  					if(rows[i].id){
	  	  	  	  					idarr.push(rows[i].id);
	  	  	  					}
	  	  	  					amount=Number(amount)+Number(rows[i].remainderamount == undefined ? 0 : rows[i].remainderamount);
	  	  					}
	  					}
	  					if(idarr.length==0){
	  	  	  				loaded();
		  	  				$.messager.alert("提示","抱歉，未能找到相关收回信息");
		  	 				$('#matcostsInputWriteoffInputQueryPage-dialog-operate-content').dialog('close');
		  	  				return false;
	  					}
	  					$("#journalsheet-reimburseIdarrs-matcostsReimburseWriteoffInputQuery").val(idarr.join(","));
					    $("#journalsheet-numberic-selectReimburseAmount-matcostsReimburseWriteoffInputQuery").numberbox("setValue",amount);
	  				}catch(ex){
	  	 				$('#matcostsInputWriteoffInputQueryPage-dialog-operate-content').dialog('close');
	  				}
  	  			loaded();
  	    	}
  	    	
  	    	function createReimburseWriteoffInputQueryDataGrid(){
  	    		var url='journalsheet/matcostsInput/getMatcostsInputPageList.do?showCanWriteoff=true';
  				var flag = $("#journalsheet-form-matcostsReimburseWriteoffInputQuery").form('validate');
  				var queryFormJSON="";
	  		   	if(flag==false){
	  		   		url='';
	  		   	}else{
	  		   		queryFormJSON = $("#journalsheet-form-matcostsReimburseWriteoffInputQuery").serializeJSON();
	  		   	}
  	    		$("#journalsheet-table-matcostsReimburseWriteoffInputQuery").datagrid({ 
  	     			authority:reimburseWriteoffInputColJson,
  		  	 		frozenColumns:reimburseWriteoffInputColJson.frozen,
  					columns:reimburseWriteoffInputColJson.common,
  		  	 		fit:true,
  		  	 		method:'post',
  		  	 		//title:'代垫录入列表',
  		  	 		showFooter: true,
  		  	 		rownumbers:true,
  		  	 		sortName:'businessdate',
  		  	 		sortOrder:'asc',
  		  	 		pagination:true,
  			 		idField:'id',
  		  	 		singleSelect:false,
  			 		checkOnSelect:true,
  			 		selectOnCheck:true,
  					pageSize:500,
  					//url:url,
					queryParams : queryFormJSON,
  					toolbar:'#journalsheet-toolbar-matcostsReimburseWriteoffInputQuery',
  					pageList:[50,100,200,500,1000],
  				  	onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							Settle_footerobject = footerrows[0];
						}
			 		},
					onBeforeLoad:function(){
					},
					onCheckAll:function(){		  					
			 			wruteoffInputCountTotalAmount();
					},
					onUncheckAll:function(){
						wruteoffInputCountTotalAmount();
					},
					onCheck:function(){
						wruteoffInputCountTotalAmount();
					},
					onUncheck:function(){
						wruteoffInputCountTotalAmount();
					}
  				}).datagrid("columnMoving");
  	    	}
  		</script>
  </body>
</html>

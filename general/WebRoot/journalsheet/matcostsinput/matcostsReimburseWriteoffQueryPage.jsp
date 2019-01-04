<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫收回核销查询</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    	<table id="journalsheet-table-matcostsReimburseWriteoffQuery"></table>
  		<div id="journalsheet-toolbar-matcostsReimburseWriteoffQuery">
  			<form id="journalsheet-form-matcostsReimburseWriteoffQuery" method="post">
  				<table>
	    			<tr>
	    				<td>业务日期:</td>
	    				<td>
	    				<input type="text" name="begintime" id="journalsheet-date-matcostsReimburseWriteoffQuery-begintime" value="${begintime }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到
	    				<input type="text" name="endtime" id="journalsheet-date-matcostsReimburseWriteoffQuery-endtime" value="${endtime }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
	    				<td>供应商:</td>
	    				<td><input id="journalsheet-widget-supplier-matcostsReimburseWriteoffQuery" name="supplierid" value="${param.supplierid }" type="text" style="width: 130px;"/></td>
	    			</tr>
	    			<tr>
	    				<td>收回方式:</td>
                        <td>
                            <input id="journalsheet-widget-reimbursetype-matcostsReimburseWriteoffQuery" type="text" name="reimbursetype" style="width: 130px;"/>&nbsp;&nbsp;
                        </td>
	    				<td colspan="2" align="right">
                                <a href="javaScript:void(0);" id="journalsheet-query-matcostsReimburseWriteoffQuery" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="journalsheet-reload-matcostsReimburseWriteoffQuery"  class="button-qr">重置</a>
                                <a href="javaScript:void(0);" id="journalsheet-okSelect-matcostsReimburseWriteoffQuery" class="button-qr" style="width:130px;letter-spacing:normal">确定选中并下一步</a>
                        </td>
	    			</tr>
    			</table>
  			</form>
  		</div>
  		<div style="display:none">
	  		<div id="matcostsInputWriteoffInputQueryPage-dialog-operate"></div>
  		</div>
  		<script type="text/javascript">
  			var Settle_footerobject=null;
  			var reimburseWriteoffQueryColJson=null;
  			$(document).ready(function(){
  				//根据初始的列与用户保存的列生成以及字段权限生成新的列
  		    	reimburseWriteoffQueryColJson=$("#journalsheet-table-matcostsReimburseWriteoffQuery").createGridColumnLoad({
  			     	name:'t_js_matcostsinput',
  			     	frozenCol:[[
  						{field:'idok',checkbox:true,isShow:true}
  			     	]],
  			     	commonCol:[[
  						{field:'id',title:'编码',width:120,sortable:true,hidden:true},
  			     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
						{field:'supplierid',title:'供应商编码',width:70,sortable:true},
						{field:'suppliername',title:'供应商名称',width:210,sortable:true,isShow:true},
						{field:'supplierdeptid',title:'所属部门',width:70,sortable:true,
							formatter:function(val,rowData,rowIndex){
								return rowData.supplierdeptname;
							}
						},
						{field:'reimbursetype',title:'收回方式',width:60,sortable:true,hidden:true,
							formatter:function(val,rowData,rowIndex){
								return rowData.reimbursetypename;
							}
						}
						<c:forEach items="${reimbursetypeList }" var="list">
						  ,{field:'reimburse_${list.code}',title:'${list.codename}',align:'right',resizable:true,isShow:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
						  </c:forEach>,
						{field:'reimburseamount',title:'收回金额',resizable:true,sortable:true,align:'right',hidden:true,
							formatter:function(val,rowData,rowIndex){
								if(val != "" && val != null){
									return formatterMoney(val);
								}
								else{
									return "0.00";
								}
							}
						},
						{field:'iswriteoff',title:'核销状态',resizable:true,sortable:true,align:'right',
							formatter:function(val,rowData,rowIndex){
								if(val=="1"){
									return "核销";
								}else if(val=='2'){
									return "核销中";
								}else if(val==null || val==""){
								}else {
									return "未核销";
								}
							}
						},
						{field:'writeoffamount',title:'核销金额',resizable:true,sortable:true,align:'right',
							formatter:function(val,rowData,rowIndex){
								if(rowData.iswriteoff=='1' || rowData.iswriteoff=='2'){
									if(val != "" && val != null){
										return formatterMoney(val);
									}else{
										return "0.00";
									}
								}else{
									return "0.00";
								}						
							}
						},
						{field:'remainderamount',title:'未核销收回',resizable:true,sortable:true,align:'right',
							formatter:function(val,rowData,rowIndex){
								if(val != "" && val != null){
									return formatterMoney(val);
								}					
							}
						},
						{field:'writeoffdate',title:'核销日期',resizable:true,sortable:true,hidden:true,
							formatter:function(val,rowData,rowIndex){
								if((rowData.iswriteoff=='1' || rowData.iswriteoff=='2') && val){
									if(val.length>=10){
										return val.substring(0,10);
									}else{
										return val;
									}
								}
							}
						},
						{field:'writeoffer',title:'核销人员',resizable:true,sortable:true,hidden:true,
							formatter:function(val,rowData,rowIndex){
								if((rowData.iswriteoff=='1' || rowData.iswriteoff=='2') && rowData.writeoffername){
									return rowData.writeoffername;
								}
							}
						},
						{field:'remark',title:'备注',width:80,sortable:true},
						{field:'adduserid',title:'制单人编码',width:80,sortable:true,hidden:true},
						{field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
						{field:'addtime',title:'制单时间',width:130,sortable:true,hidden:true,
							formatter:function(val,rowData,rowIndex){
								if(val){
									return val.replace(/[tT]/," ");
								}
							}
						}
  					]]
  			     });

  		   
  				
  				//查询
  				$("#journalsheet-query-matcostsReimburseWriteoffQuery").click(function(){
  	  				var supplierid=$("#journalsheet-widget-supplier-matcostsReimburseWriteoffQuery").widget("getValue");
  	  				if(null==supplierid || ""==supplierid){
	  	  				$.messager.alert("提示","请选择供应商！");
						return false;
  	  				}
  					//把form表单的name序列化成JSON对象
  		      		var queryJSON = $("#journalsheet-form-matcostsReimburseWriteoffQuery").serializeJSON();
		      		$("#journalsheet-table-matcostsReimburseWriteoffQuery").datagrid({
			      		url:'journalsheet/matcostsInput/getMatcostsReimbursePageList.do?showAllData=true&showCanWriteoff=true',
		      			pageNumber:1,
						queryParams:queryJSON
		      		}).datagrid("columnMoving");
  				});

  				//重置
  				$("#journalsheet-reload-matcostsReimburseWriteoffQuery").click(function(){
  					$("#journalsheet-widget-supplier-matcostsReimburseWriteoffQuery").widget("clear");
  					$("#journalsheet-form-matcostsReimburseWriteoffQuery").form("reset");
  					$("#journalsheet-table-matcostsReimburseWriteoffQuery").datagrid('loadData',{total:0,rows:[]});
					$("#journalsheet-table-matcostsReimburseWriteoffQuery").datagrid("clearChecked");
  				});

  				

  				//供应商查询
  			  	$("#journalsheet-widget-supplier-matcostsReimburseWriteoffQuery").widget({
  			  		width:320,
  					name:'t_js_matcostsinput',
  					col:'supplierid',
  					singleSelect:true,
  					view:true
  				});
  			  
	  			//收回方式
	  			$("#journalsheet-widget-reimbursetype-matcostsReimburseWriteoffQuery").widget({
	  		  		width:120,
	  				name:'t_js_matcostsinput',
	  				col:'reimbursetype',
	  				singleSelect:true,
	  				initSelectNull:true
	  			});
  				
				//选中数据
  				$("#journalsheet-okSelect-matcostsReimburseWriteoffQuery").click(function(){
  	  				var supplierid=$("#journalsheet-widget-supplier-matcostsReimburseWriteoffQuery").widget("getValue");
  	  				if(null==supplierid || ""==supplierid){
	  	  				$.messager.alert("提示","请选择供应商！");
						return false;
	  				}
  					var rows =  $("#journalsheet-table-matcostsReimburseWriteoffQuery").datagrid('getChecked');
  					if(null==rows || rows.length==0){
	  	  				$.messager.alert("提示","请选择要核销的收回");
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
  	  	  					amount=Number(amount)+Number(rows[i].remainderamount == undefined ? 0 : rows[i].remainderamount);
  	  					}
  					}
  					if(idarr.length==0){
	  	  				$.messager.alert("提示","请选择要核销的收回");
						return false;
  					}
					var reqparams={};
					reqparams.idarr=idarr.join(",");
					reqparams.amount=amount;
					reqparams.supplierid=supplierid;

					$('<div id="matcostsInputWriteoffInputQueryPage-dialog-operate-content"></div>').appendTo('#matcostsInputWriteoffInputQueryPage-dialog-operate');
					$('#matcostsInputWriteoffInputQueryPage-dialog-operate-content').dialog({
					    title: '代垫收回核销：第二步、选择代垫',  
					  	//width: 680,  
					    //height: 300,
					    fit:true,  
					    closed: true,  
					    cache: false,
						method:'post',
						queryParams:reqparams,
					    href: 'journalsheet/matcostsInput/matcostsReimburseWriteoffInputQueryPage.do',
						maximizable:true,
						resizable:true,  
					    modal: true,
					    onLoad:function(){
						    createReimburseWriteoffInputQueryDataGrid();
					    },
						onClose:function(){
							$('#matcostsInputWriteoffInputQueryPage-dialog-operate-content').dialog('destroy');
						}
					});
					$('#matcostsInputWriteoffInputQueryPage-dialog-operate-content').dialog('open');
  				});
  				//收回方式
  				$("#journalsheet-widget-reimbursetype-matcostsInputSettleAdd").widget({
  			  		width:120,
  					name:'t_js_matcostsinput',
  					col:'reimbursetype',
  					singleSelect:true,
  					initSelectNull:true
  				});
  				//收回金额
  				$("#journalsheet-number-reimburseamount-matcostsInputSettleAdd").numberbox({
  					 precision:2,
  					 groupSeparator:','
  				});
  				
  			});
  			function writeoffQueryCountTotalAmount(){
  	    		var rows =  $("#journalsheet-table-matcostsReimburseWriteoffQuery").datagrid('getChecked');
  	    		if(null==rows || rows.length==0){
  	           		var foot=[];
  	    			if(null!=Settle_footerobject){
  		        		foot.push(Settle_footerobject);
  		    		}
  	    			$("#journalsheet-table-matcostsReimburseWriteoffQuery").datagrid("reloadFooter",foot);
  	           		return false;
  	       		}
  	    		var reimburseamount = 0;
  	    		var writeoffamount = 0;
  	    		var remainderamount=0;
  	    		for(var i=0;i<rows.length;i++){
  	    			reimburseamount = Number(reimburseamount)+Number(rows[i].reimburseamount == undefined ? 0 : rows[i].reimburseamount);
  	    			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
  	    			remainderamount = Number(remainderamount)+Number(rows[i].remainderamount == undefined ? 0 : rows[i].remainderamount);
  	    		}
  	    		var foot=[{suppliername:'选中金额',reimburseamount:reimburseamount,writeoffamount:writeoffamount,remainderamount:remainderamount}];
  	    		if(null!=Settle_footerobject){
  	        		foot.push(Settle_footerobject);
  	    		}
  	    		$("#journalsheet-table-matcostsReimburseWriteoffQuery").datagrid("reloadFooter",foot);
  	    	}
  			
  			function createReimburseWriteoffQueryDataGrid(){
  				var url='journalsheet/matcostsInput/getMatcostsReimbursePageList.do?showAllData=true&showCanWriteoff=true';
  				var flag = $("#journalsheet-form-matcostsReimburseWriteoffQuery").form('validate');
  				var queryFormJSON={};
	  		   	if(flag==false){
	  		   		url='';
	  		   	}else{
	  		   		queryFormJSON = $("#journalsheet-form-matcostsReimburseWriteoffQuery").serializeJSON();
	  		   	}
  				
  				$("#journalsheet-table-matcostsReimburseWriteoffQuery").datagrid({ 
  	     			authority:reimburseWriteoffQueryColJson,
  		  	 		frozenColumns:reimburseWriteoffQueryColJson.frozen,
  					columns:reimburseWriteoffQueryColJson.common,
  		  	 		fit:true,
  		  	 		method:'post',
  		  	 		//title:'代垫录入列表',
  		  	 		showFooter: true,
  		  	 		rownumbers:true,
  		  	 		sortName:'businessdate',
  		  	 		sortOrder:'asc',
  		  	 		//pagination:true,
  			 		idField:'id',
  		  	 		singleSelect:false,
  			 		checkOnSelect:true,
  			 		selectOnCheck:true,
  					//pageSize:20,
  					//url:url,
					queryParams : queryFormJSON,
  					toolbar:'#journalsheet-toolbar-matcostsReimburseWriteoffQuery',
  					pageList:[10,20,30,50,200],
  				  	onLoadSuccess:function(rowData){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							Settle_footerobject = footerrows[0];
						}
						checkTheDataGridByReimburseId(rowData.rows);
			 		},
					onCheckAll:function(){
			 			writeoffQueryCountTotalAmount();
					},
					onUncheckAll:function(){
						writeoffQueryCountTotalAmount();
					},
					onCheck:function(){
						writeoffQueryCountTotalAmount();
					},
					onUncheck:function(){
						writeoffQueryCountTotalAmount();
					}
  				}).datagrid("columnMoving");
  			}
  			
  			function checkTheDataGridByReimburseId(rowData){
  				if( typeof(reimburseWriteoffIdarr)!="undefined" 
  	  					&& reimburseWriteoffIdarr!=null
  	  						){
  					
  					if(typeof(reimburseWriteoffQueryPageJsonForm)!="undefined" 
  		  	  					&& reimburseWriteoffQueryPageJsonForm!=null
  		  	  						){
  						var queryFormJSON = $("#journalsheet-form-matcostsReimburseWriteoffQuery").serializeJSON();
  						if(reimburseWriteoffQueryPageJsonForm.supplierid!=queryFormJSON.supplierid){
  							return false;
  						}
  					}
  					for(var i=0;i<rowData.length;i++){
  						for(var j=0;j<reimburseWriteoffIdarr.length;j++){
  							if(rowData[i].id==reimburseWriteoffIdarr[j]){
  								$("#journalsheet-table-matcostsReimburseWriteoffQuery").datagrid("checkRow",i);
  								continue;
  							}
  						}
  					}
  				}
  			}
  		</script>
  </body>
</html>

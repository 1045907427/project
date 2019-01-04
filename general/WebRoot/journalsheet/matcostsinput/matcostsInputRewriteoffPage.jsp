<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫反核销</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    	<table id="journalsheet-table-matcostsInputRewirteoffPage"></table>
  		<div id="journalsheet-toolbar-matcostsInputRewirteoffPage">  		
  			<table>
  				<tr>
    				<td align="right">代垫编码：</td>
    				<td><input value="${matcostsInput.id }" type="text" style="width: 140px;border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" /></td>
    				<td align="right">OA编号：</td>
    				<td><input value="${matcostsInput.oaid }" type="text" style="width: 130px;border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly"/></td>
   				</tr>
				<tr>
    				<td align="right">工厂投入：</td>
    				<td><input id="journalsheet-numberic-factoryamount-matcostsInputRewirteoffPage" value="${matcostsInput.factoryamount }" type="text" style="width: 140px;border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" /></td>
    				<td align="right">核销金额：</td>
    				<td><input id="journalsheet-numberic-writeoffamount-matcostsInputRewirteoffPage" value="${matcostsInput.writeoffamount }" type="text" style="width: 130px;border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly"/></td>
   				</tr>
				<c:if test="${noDataFound != 'true'}">
   				<tr>
   					<td colspan="4">   						
						<a href="javaScript:void(0);" id="journalsheet-okSelect-matcostsInputRewirteoffPage" class="easyui-linkbutton" iconCls="button-save" plain="true" title="" >确定并反核销</a>
					</td>
   				</tr>
   				</c:if>
   				<tr>
   					<td colspan="4">
   						<div style="line-height:20px;padding-top:10px;font-weight:bold;">   						
							以下为核销记录：
						</div>
					</td>
   				</tr>
			</table>
  			<input type="hidden" id="journalsheet-matcostsInputId-matcostsInputRewirteoffPage" name="id" value="${matcostsInput.id }"/>
  		</div>
  		
  		<script type="text/javascript">
  			var rewriteOff_statement_FooterObject=null;
  			var queryStatementUrl="journalsheet/matcostsInput/getMatcostsStatementPageListData.do?myqueryflag=1";
  			var noDataFound=false;
  			<c:choose>
  				<c:when test="${!empty(matcostsInput.id) }">
  					queryStatementUrl=queryStatementUrl+"&payid=${matcostsInput.id }";
  				</c:when>
  				<c:otherwise>
  					noDataFound=true;
  					queryStatementUrl=queryStatementUrl+"&noDataFound=true";
  				</c:otherwise>
  			</c:choose>
  			<c:if test="${noDataFound=='true'}">
  				if(!noDataFound){
					queryStatementUrl=queryStatementUrl+"&noDataFound=true";
  				}
  			</c:if>
  			$(document).ready(function(){
  				//根据初始的列与用户保存的列生成以及字段权限生成新的列
  		    	var matcostsInputListColJson=$("#journalsheet-table-matcostsInputRewirteoffPage").createGridColumnLoad({
  		    		name:'t_js_matcostsinput_statement',
  			     	frozenCol:[[
  			     	]],
  			     	commonCol:[[
  			    		{field:'id',title:'编码',width:120,sortable:true,hidden:true},
  			    		{field:'oaid',title:'OA编码',width:80,sortable:true,hidden:true},
  			    		{field:'supplierid',title:'供应商编码',width:70,sortable:true,isShow:true},
  						{field:'suppliername',title:'供应商名称',width:210,sortable:true,isShow:true},
  						{field:'supplierdeptid',title:'所属部门',width:70,sortable:true,isShow:true,
  							formatter:function(val,rowData,rowIndex){
  								return rowData.supplierdeptname;
  							}
  						},
  			     		{field:'payid',title:'代垫录入编号',width:125,sortable:true,hidden:true},
  						{field:'factoryamount',title:'工厂投入',resizable:true,sortable:true,align:'right',hidden:true,
  							formatter:function(val,rowData,rowIndex){
								return formatterMoney(val);
  							}
  						},
  						{field:'remainderamount',title:'代垫剩余金额',resizable:true,sortable:true,align:'right',hidden:true,
  							formatter:function(val,rowData,rowIndex){
								return formatterMoney(val);
  							}
  						},
  			     		{field:'takebackid',title:'代垫收回编号',width:125,sortable:true},
  						{field:'takebackamount',title:'收回总金额',resizable:true,sortable:true,align:'right',hidden:true,
  							formatter:function(val,rowData,rowIndex){
								return formatterMoney(val);
  							}
  						},
  						{field:'tbremainderamount',title:'核销前收回剩余金额',resizable:true,sortable:true,align:'right',
  							formatter:function(val,rowData,rowIndex){
  								return formatterMoney(val);
  							}
  						},
  						{field:'tbremainderamountafter',title:'核销后收回剩余金额',resizable:true,sortable:true,align:'right',isShow:true,
  							formatter:function(val,rowData,rowIndex){
  								return formatterMoney(val);
  							}
  						}
  						<c:forEach items="${reimbursetypeList }" var="list">
  						  ,{field:'reimburse_${list.code}',title:'${list.codename}-核销',align:'right',resizable:true,isShow:true,
  						  	formatter:function(value,rowData,rowIndex){
  				        		return formatterMoney(value);
  				        	}
  						  }
  						  </c:forEach>,
  						{field:'relateamount',title:'核销合计金额',resizable:true,sortable:true,align:'right',
  							formatter:function(val,rowData,rowIndex){
								return formatterMoney(val);
  							}
  						},
  						{field:'iswriteoff',title:'核销状态',resizable:true,sortable:true,align:'right',
  							formatter:function(val,rowData,rowIndex){
  								if(val=="1"){
  									return "核销";
  								}
  							}
  						},
  						{field:'writeoffdate',title:'核销日期',resizable:true,sortable:true,
  							formatter:function(val,rowData,rowIndex){
  								if(rowData.iswriteoff=='1' && val){
  									if(val.length>=10){
  										return val.substring(0,10);
  									}else{
  										return val;
  									}
  								}
  							}
  						},
  						{field:'adduserid',title:'核销人员',resizable:true,sortable:true,
  							formatter:function(val,rowData,rowIndex){
  								if(rowData.iswriteoff=='1' && rowData.addusername){
  									return rowData.addusername;
  								}
  							}
  						},
  						{field:'ddbusinessdate',title:'代垫业务日期',width:130,sortable:true,isShow:true,
  							formatter:function(val,rowData,rowIndex){
  								if(val){
  									return val.replace(/[tT]/," ");
  								}
  							}
  						},
  						{field:'addtime',title:'添加时间',width:130,sortable:true,hidden:true,
  							formatter:function(val,rowData,rowIndex){
  								if(val){
  									return val.replace(/[tT]/," ");
  								}
  							}
  						}
  					]]
  			     });

  		    
  				$("#journalsheet-table-matcostsInputRewirteoffPage").datagrid({ 
  	     			authority:matcostsInputListColJson,
  		  	 		frozenColumns:matcostsInputListColJson.frozen,
  					columns:matcostsInputListColJson.common,
  		  	 		fit:true,
  		  	 		method:'post',
  		  	 		//title:'${matcostsInput.id }代垫核销记录',
  		  	 		showFooter: true,
  		  	 		rownumbers:true,
  		  	 		sortName:'businessdate',
  		  	 		sortOrder:'asc',
  		  	 		pagination:true,
  			 		idField:'id',
  		  	 		singleSelect:false,
  			 		checkOnSelect:true,
  			 		selectOnCheck:true,
  					pageSize:50,
  					url: queryStatementUrl,
  					toolbar:'#journalsheet-toolbar-matcostsInputRewirteoffPage',
  					pageList:[10,20,30,50,200],
					onLoadSuccess:function(){
		     			var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							rewriteOff_statement_FooterObject = footerrows[0];
						}
			 		},
					onCheckAll:function(){
			 			rewriteOffStatementCountTotalAmount();
					},
					onUncheckAll:function(){
						rewriteOffStatementCountTotalAmount();
					},
					onCheck:function(){
						rewriteOffStatementCountTotalAmount();
					},
					onUncheck:function(){
						rewriteOffStatementCountTotalAmount();
					}
  				}).datagrid("columnMoving");

  			  	$("#journalsheet-numberic-factoryamount-matcostsInputRewirteoffPage").numberbox({
					 precision:2,
					 groupSeparator:','
				});
				$("#journalsheet-numberic-writeoffamount-matcostsInputRewirteoffPage").numberbox({
					 precision:2,
					 groupSeparator:','
				});
				<c:if test="${noDataFound != 'true'}">
  				$("#journalsheet-okSelect-matcostsInputRewirteoffPage").click(function(){
  	  				var inputId=$.trim("${matcostsInput.id }");
  	  				if(inputId==null || inputId==""){
	  	  				$.messager.alert("提示","抱歉，未能找到相关代垫信息");
		  	  		   	return false;
  	  				}
  	  			$.messager.confirm("提醒","是否确认反核销代垫${matcostsInput.id }?",function(r){
	  				if(r){
	  	  				var queryJson={id:inputId};
						loading("正在反核销代垫中,请稍候...");
	  					$.ajax({
				  	  		   type: "POST",
				  	  		   url: "journalsheet/matcostsInput/matcostsInputRewriteoff.do",
				  	  		   cache: false,		  	  		   
				  	  		   data: queryJson,
				  	  		   dataType:'json',
				  	  		   success: function(data){
								   loaded();
					  	  		   if(typeof(data)=="undefined"){
					  	  				$.messager.alert("提示","抱歉，操作未成功");
						  	  		   return false;
					  	  		   }
					  	  		   if(data.flag==true){
					  	  				$.messager.alert("提示","核销成功");
					  	 				reimburse_RefreshDataGrid();
					  	 				input_RefreshDataGrid();		
	
										$('#matcostsInputRewriteoff-dialog-operate').dialog('close');			  	 				
					  	  		   }else{
						  	  		   if(data.msg){
						  	  				$.messager.alert("提示","抱歉，核销失败!<br/>"+data.msg);
						  	  				return false;
						  	  		   }else{
						  	  				$.messager.alert("提示","抱歉，核销失败");
						  	  				return false;
						  	  		   }
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
  				</c:if>
  			});
  			function rewriteOffStatementCountTotalAmount(){
  	    		var rows =  $("#journalsheet-table-matcostsInputRewirteoffPage").datagrid('getChecked');
  	    		if(null==rows || rows.length==0){
  	           		var foot=[];
  	           		if(null!=rewriteOff_statement_FooterObject){
  		        		foot.push(rewriteoffEmptyChooseObjectFoot());
  		        		foot.push(rewriteOff_statement_FooterObject);
  		    		}
  	    			$("#journalsheet-table-matcostsInputRewirteoffPage").datagrid("reloadFooter",foot);
  	           		return false;
  	       		}
  	    		var relateamount = 0;
  	    		
  	    		<c:forEach items="${reimbursetypeList }" var="list">
  	    			var reimburse_${list.code}=0;
  	    		</c:forEach>
  	    		
  	    		for(var i=0;i<rows.length;i++){
  	    			relateamount = Number(relateamount)+Number(rows[i].relateamount == undefined ? 0 : rows[i].relateamount);

  	        		<c:forEach items="${reimbursetypeList }" var="list">
  	        			reimburse_${list.code} = Number(reimburse_${list.code})+Number(rows[i].reimburse_${list.code} == undefined ? 0 : rows[i].reimburse_${list.code});
  	        		</c:forEach>

  	    		}
  	    		var foot=[{suppliername:'选中金额',
  	        				relateamount:relateamount

  	        				<c:forEach items="${reimbursetypeList }" var="list">
  								,reimburse_${list.code} : reimburse_${list.code}
  							</c:forEach>
  	        			}];
  	    		if(null!=rewriteOff_statement_FooterObject){
  	        		foot.push(rewriteOff_statement_FooterObject);
  	    		}
  	    		$("#journalsheet-table-matcostsInputRewirteoffPage").datagrid("reloadFooter",foot);
  	    	}
  	    	function rewriteoffEmptyChooseObjectFoot(){
  	    		var relateamount=0;

  	    		<c:forEach items="${reimbursetypeList }" var="list">
  	    			var reimburse_${list.code}=0;
  	    		</c:forEach>

  	    		var foot={suppliername:'选中金额',
  					relateamount:relateamount
  					<c:forEach items="${reimbursetypeList }" var="list">
  						,reimburse_${list.code} : reimburse_${list.code}
  					</c:forEach>
  				};
  				return foot;
  	    	}
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
  		</script>
  </body>
</html>

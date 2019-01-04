<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>联系人新增</title>
    <%@include file="/include.jsp" %>  
  </head>
  <body>
	<div id="contacter-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
  		<form action="basefiles/addContacter.do" id="contacter-form-contacterAddPage" method="post">
  			<input type="hidden" name="addType" id="contacter-addType-contacterAddPage" />
	    	<div id="contacter-layout-detail-north" data-options="region:'north',border:false">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
			    	<tr>
			    		<td class="right len100">编码：</td>
			    		<td><input class="easyui-validatebox" name="contacter.id" maxlength="20" validType="validUsed" id="contacter-id-contacterAddPage" data-options="required:true" /></td>
			    		<td class="right len100">姓名：</td>
			    		<td><input class="easyui-validatebox" maxlength="25" name="contacter.name" /></td>
			    		<td class="right len100">状态：</td>
			    		<td><select disabled="disabled" class="len130 easyui-combobox"><option>新增</option></select></td>
			    	</tr>
			    </table>
			    <ul class="tags">
		    		<li class="selectTag"><a href="javascript:;">基本信息</a></li>
		    		<li><a href="javascript:;">对应分类</a></li>
		    		<li><a href="javascript:;">业务活动记录</a></li>
		    		<li><a href="javascript:;">自定义信息</a></li>
		    	</ul>
	    	</div>
	    	<div id="contacter-layout-detail-center" data-options="region:'center',border:false">
	    		<div class="tagsDiv">
		    		<div class="tagsDiv_item" style="display:block;">
		    			<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    					<tr>
	    						<td class="right len100">姓名拼音：</td>
	    						<td class="len165"><input type="text" name="contacter.spell" maxlength="25" class="len130" /></td>
	    						<td class="right len100">初次接触时间：</td>
	    						<td class="len165"><input type="text" name="contacter.firstcall" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" class="len130" /></td>
	    						<td rowspan="7" colspan="2">
	    							<div class="easyui-panel" data-options="fit:true,border:false" style="padding:10px 30px;">
	    								<div class="easyui-panel" id="contacter-imgPanel-contacterAddPage" style="width:150px;height:180px;overflow:hidden;">
	    								</div>
	    								<div class="easyui-panel" data-options="border:false" style="width:200px;height:25px;margin-top:5px;">
	    									照片上传：<input type="button" value="浏览" id="contacter-imgBrowse-contacterAddPage" /><input type="button" value="查看原图" id="contacter-imgShow-contacterAddPage" />
	    									<input type="hidden" name="contacter.image" id="contacter-image-contacterAddPage" />
	    								</div>
	    							</div>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td class="right">默认分类：</td>
	    						<td>
	    							<input type="text" readonly="readonly" id="contacter-sortName-contacterAddPage" class="len130" />
	    							<input type="hidden" name="contacter.linkmansort" readonly="readonly" id="contacter-sort-contacterAddPage" />
	    						</td>
	    						<td class="right">所属供应商：</td>
	    						<td style="white-space:nowrap;"><input type="text" name="contacter.supplier" id="contacter-supplier-contacterAddPage" /></td>
	    					</tr>
	    					<tr>
	    						<td class="right">所属客户：</td>
	    						<td><input type="text" name="contacter.customer" id="contacter-customer-contacterAddPage" class="len130" /></td>
	    						<td class="right">职务名称：</td>
	    						<td><input type="text" name="contacter.job" maxlength="25" class="len130" /></td>
	    					</tr>
	    					<tr>
	    						<td class="right">性别：</td>
	    						<td>
	    							<select name="contacter.sex" class="len130 easyui-combobox">
	    								<c:forEach items="${sexList }" var="list">
	    								<option value="${list.code }">${list.codename }</option>
	    								</c:forEach>
	    							</select>
	    						</td>
	    						<td class="right">婚姻状态：</td>
	    						<td>
	    							<select name="contacter.maritalstatus" class="len130 easyui-combobox">
	    								<c:forEach items="${marryList }" var="list">
	    								<option value="${list.code }">${list.codename }</option>
	    								</c:forEach>
	    							</select>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td class="right">出生日期：</td>
	    						<td><input type="text" name="contacter.birthday" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" class="len130" /></td>
	    						<td class="right">年龄：</td>
	    						<td><input type="text" class="easyui-validatebox len130" name="contacter.age" maxlength="3" validType="integer" /></td>
	    					</tr>
	    					<tr>
	    						<td class="right">身份证号码：</td>
	    						<td><input type="text" name="contacter.idcard" maxlength="18" class="easyui-validatebox len130" validType="idcard" /></td>
	    						<td class="right">民族：</td>
	    						<td>
	    							<select name="contacter.nation" class="len130 easyui-combobox">
	    								<c:forEach items="${nationList }" var="list">
	    								<option value="${list.code }">${list.codename }</option>
	    								</c:forEach>
	    							</select>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td class="right">籍贯：</td>
	    						<td><input type="text" name="contacter.nativeplace" maxlength="10" class="len130" /></td>
	    						<td class="right">政治面貌：</td>
	    						<td>
	    							<select name="contacter.polstatus" class="len130 easyui-combobox">
	    								<c:forEach items="${polList }" var="list">
	    								<option value="${list.code }">${list.codename }</option>
	    								</c:forEach>
	    							</select>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td class="right">电话：</td>
	    						<td><input type="text" name="contacter.tel" class="easyui-validatebox len130" maxlength="20" /></td>
	    						<td class="right">传真：</td>
	    						<td><input type="text" name="contacter.fax" class="easyui-validatebox len130" maxlength="20" validType="faxno" /></td>
	    						<td class="right len100">邮箱：</td>
	    						<td class="len165"><input type="text" name="contacter.email" class="easyui-validatebox len130" maxlength="100" validType="email" /></td>
	    					</tr>
	    					<tr>
	    						<td class="right">手机号码：</td>
	    						<td><input type="text" name="contacter.mobile" class="easyui-validatebox len130" maxlength="20" /></td>
	    						<td class="right">QQ：</td>
	    						<td><input type="text" name="contacter.qq" class="easyui-validatebox len130" maxlength="20" validType="qq" /></td>
	    						<td class="right">MSN：</td>
	    						<td><input type="text" name="contacter.msn" class="easyui-validatebox len130" maxlength="50" validType="msn" /></td>
	    					</tr>
	    					<tr>
	    						<td class="right">最佳联系时间：</td>
	    						<td colspan="3"><input type="text" name="contacter.bestcall" style="width:415px;" maxlength="50" /></td>
	    						<td class="right">最近联系时间：</td>
	    						<td><input type="text" readonly="readonly" class="len130" /></td>
	    					</tr>
	    					<tr>
	    						<td class="right">居住地址：</td>
	    						<td colspan="3"><input type="text" name="contacter.liveaddr" maxlength="100" style="width:415px;" /></td>
	    						<td class="right">居住地邮编：</td>
	    						<td><input type="text" name="contacter.livezip" class="easyui-validatebox len130" maxlength="6" validType="zip" /></td>
	    					</tr>
	    					<tr>
	    						<td class="right">户籍地址：</td>
	    						<td colspan="3"><input type="text" name="contacter.nativeaddr" maxlength="100" style="width:415px;" /></td>
	    						<td class="right">户籍地邮编：</td>
	    						<td><input type="text" name="contacter.nativezip" class="easyui-validatebox len130" maxlength="6" validType="zip" /></td>
	    					</tr>
	    					<tr>
	    						<td class="right">家庭状况：</td>
	    						<td colspan="5"><textarea cols="80" rows="1" name="contacter.family"></textarea></td>
	    					</tr>
	    					<tr>
	    						<td class="right">兴趣爱好：</td>
	    						<td colspan="5"><textarea cols="80" rows="1" name="contacter.hobby"></textarea></td>
	    					</tr>
	    					<tr>
	    						<td class="right">备注：</td>
	    						<td colspan="5"><textarea cols="80" rows="1" name="contacter.remark"></textarea></td>
	    					</tr>
	    				</table>
		    		</div>
		    		<div class="tagsDiv_item">
		    			<table id="contacter-sortList-contacterAddPage"></table>
		    		</div>
		    		<div class="tagsDiv_item">
		    		</div>
		    		<div class="tagsDiv_item">
		    			<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px" id="contacter-field-contacterAddPage">
	    					<tr>
	    						<td class="len100 right"><span class="field-contacter" rel="field01"></span></td>
	    						<td><input type="text" name="contacter.field01" maxlength="50" /></td>
	    						<td class="len100 right"><span class="field-contacter" rel="field02"></span></td>
	    						<td><input type="text" name="contacter.field02" maxlength="50" /></td>
	    						<td class="len100 right"><span class="field-contacter" rel="field03"></span></td>
	    						<td><input type="text" name="contacter.field03" maxlength="50" /></td>
	    					</tr>
	    					<tr>
	    						<td class="right"><span class="field-contacter" rel="field04"></span></td>
	    						<td><input type="text" name="contacter.field04" maxlength="50" /></td>
	    						<td class="right"><span class="field-contacter" rel="field05"></span></td>
	    						<td><input type="text" name="contacter.field05" maxlength="50" /></td>
	    						<td class="right"><span class="field-contacter" rel="field06"></span></td>
	    						<td><input type="text" name="contacter.field06" maxlength="50" /></td>
	    					</tr>
	    					<tr>
	    						<td class="right"><span class="field-contacter" rel="field07"></span></td>
	    						<td><input type="text" name="contacter.field07" maxlength="50" /></td>
	    						<td class="right"><span class="field-contacter" rel="field08"></span></td>
	    						<td><input type="text" name="contacter.field08" maxlength="50" /></td>
	    						<td class="right"><span class="field-contacter" rel="field09"></span></td>
	    						<td><input type="text" name="contacter.field09" maxlength="50" /></td>
	    					</tr>
	    					<tr>
	    						<td class="right"><span class="field-contacter" rel="field10"></span></td>
	    						<td colspan="5"><textarea cols="80" rows="3" name="contacter.field10"></textarea></td>
	    					</tr>
	    					<tr>
	    						<td class="right"><span class="field-contacter" rel="field11"></span></td>
	    						<td colspan="5"><textarea cols="80" rows="3" name="contacter.field11"></textarea></td>
	    					</tr>
	    					<tr>
	    						<td class="right"><span class="field-contacter" rel="field12"></span></td>
	    						<td colspan="5"><textarea cols="80" rows="3" name="contacter.field12"></textarea></td>
	    					</tr>
	    				</table>
		    		</div>
		    	</div>
	    	</div>
    	</form>
	</div>
    <script type="text/javascript">
    	$(function(){
    		//$("#contacter-id-contacterAddPage").change(function(){
    		//	validUsed("basefiles/contacterNoUsed.do", $(this).val(), '', "该编号已被使用，请另输入编号！");
    		//});
    		$("#contacter-customer-contacterAddPage").customerWidget({ //所属客户
    			onSelect: function(data){
    				$("#contacter-supplier-contacterAddPage").supplierWidget('clear');
    				$("#contacter-supplier-contacterAddPage").supplierWidget('disable');
    			},
    			onClear: function(){
    				$("#contacter-supplier-contacterAddPage").supplierWidget('enable');
    			}
    		});
    		$("#contacter-supplier-contacterAddPage").supplierWidget({ //所属供应商
    			onSelect: function(data){
    				$("#contacter-customer-contacterAddPage").customerWidget('clear');
    				$("#contacter-customer-contacterAddPage").customerWidget('disable');
    			},
    			onClear: function(){
    				$("#contacter-customer-contacterAddPage").customerWidget('enable');
    			}
    		});
    		$("#contacter-imgBrowse-contacterAddPage").upload({
    			auto: true,
				del: false,
				type: 'upload',
				onComplete:function(json){
					$("#contacter-image-contacterAddPage").val(json.fullPath);
					$("#contacter-imgPanel-contacterAddPage").html("<img id='contacter-img-preview' src='"+json.fullPath+"' style='width:150px;height:180px;' />");
					$("#z-upload-dialog").dialog('close',true);
				}
    		});
    		$("#contacter-imgShow-contacterAddPage").click(function(){ //查看原图
    			var photograph = document.getElementById("contacter-img-preview").getAttribute("src");
				$('#contacter-window-showOldImg').window({  
					    title: '查看原图',  
					    width: $(window).width(),  
					    height: $(window).height(),  
					    closed: true,  
					    cache: false,  
					    modal: true 
					});
				$("#contacter-window-showOldImg").window("open");
				$("#contacter-window-showOldImg").window("refresh","basefiles/showContacterOldImgPage.do?photograph="+photograph);
    			//window.open($("#contacter-image-contacterAddPage").val(),"原图查看");
    		});
    		$("#contacter-buttons-contacterPage").buttonWidget("initButtonType", 'add');
			$.ajax({ //获取自定义字段的名称，数据来源数据字典
				url:'basefiles/contacterDIYFieldName.do',
				dataType:'json',
				type:'post',
				success:function(json){
					$("#contacter-field-contacterAddPage").find(".field-contacter").each(function(){
						var rel = $(this).attr("rel");
						$(this).text(json[rel] + "：");
					});
				}
			});
			$(".tags li").click(function(){ //选项选择事件
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 1){
					var height = $(window).height()-124;
					if(!$sortList.hasClass("create-datagrid")){
						$sortList.datagrid({ //客户分类行编辑
							height:height,
							border:false,
							idField:'linkmansort',
							singleSelect:true,
							rownumbers:true,
							columns:[[
										{field:'linkmansort',title:'联系人分类',width:120,
											formatter: function(value,row,index){
												return row.linkmansortname + "<input type='hidden' value='"+value+"' name='contacterAndSort.linkmansort' />" + "<input type='hidden' value='"+row.linkmansortname+"' name='contacterAndSort.linkmansortname' />";
											},
											editor:{
												type:'comborefer',
												options:{
													name:'t_base_linkman_info',
													col:'linkmansort',
													singleSelect:true
												}
											}
										},
										{field:'isdefault',title:'是否默认分类',width:120,
											formatter: function(value,row,index){
												if(value == "0"){
													return "否 <input type='hidden' value='"+value+"' name='contacterAndSort.isdefault' />";
												}
												if(value == "1"){
													return "是 <input type='hidden' value='"+value+"' name='contacterAndSort.isdefault' />";
												}
											},
											editor:{
												type:'defaultSelect',
												options:{
													valueField:'value',  
					                                textField:'text',  
													classid:'contacterSortDefault',
					                                data:[
					                                		{value:'1',text:'是'},
					                                		{value:'0',text:'否'}
					                                	]
												}
											}
										},
										{field:'remark',title:'备注',width:280,
											formatter: function(value, row, index){
												return value + "<input type='hidden' value='"+value+"' name='contacterAndSort.remark' />"
											},
											editor:{
												type:'validatebox'
											}
										}
									]],
							toolbar : [{
				                text : "添加", //添加新行
				                iconCls : "button-add",
				                handler : function() {
				                	if(endEditing()){
				                		if(disabledSelectAdd()){
				                			$sortList.datagrid('appendRow',{isdefault:'disabled'});
				                		}
				                		else{
					                		$sortList.datagrid('appendRow',{isdefault:'0'});
					                	}  
						                editIndex = $sortList.datagrid('getRows').length-1;  
						                $sortList.datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
					                }
				                }
				        	},
				        	{
				        		text: "确定", //确定行编辑完成，并回写默认项
				        		iconCls: "button-save",
				        		handler: function(){
				        			if(endEditing()){return}
				        			var ed = $sortList.datagrid('getEditor', {index:editIndex,field:'linkmansort'});
						            var linkmansortname = $(ed.target).widget("getText");
						   			$sortList.datagrid('getRows')[editIndex]['linkmansortname'] = linkmansortname;
						   			$sortList.datagrid('endEdit', editIndex);
						   			var data = $sortList.datagrid('getRows');
									var defaultIndex = -1;
									for(var i=0;i<data.length;i++){
										if(data[i]['isdefault'] == "1"){
											defaultIndex = i;
    										$("#contacter-sortName-contacterAddPage").val(data[i]['linkmansortname']);
    										$("#contacter-sort-contacterAddPage").val(data[i]['linkmansort']);
										}
									}
									if(defaultIndex == -1){
										$("#contacter-sortName-contacterAddPage").val("");
    									$("#contacter-sort-contacterAddPage").val("");
									}
						   			editIndex = undefined;
						   			$sortList.datagrid('clearSelections');
				        		}
				        	},
				        	{
				        		text: "修改", //修改选中行
				        		iconCls: "button-edit",
				        		handler: function(){
				        			if(endEditing()){return}
						   			$sortList.datagrid('beginEdit', editIndex);
				        			disabledSelectEdit(editIndex);
				        		}
				        	},
				        	{
				        		text: "删除", //删除选中行
				        		iconCls: "button-delete",
				        		handler: function(){
				        			if(endEditing()){return}
						            $sortList.datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
						   			editIndex = undefined;
				        		}
				        	}],
				        	onClickRow: function(rowIndex, rowData){ //选中行
				        		if(rowIndex != editIndex){
					        		if(endEditing()){
					        			$sortList.datagrid('selectRow', rowIndex).datagrid('beginEdit', rowIndex); 
					        			editIndex = rowIndex;
					        			disabledSelectEdit(editIndex);
					        		}
					        		else{
					        			$sortList.datagrid('selectRow', editIndex);
					        		}
				        		}
				        	}
						});
						$sortList.addClass("create-datagrid")
					}
				}
			});
    		$("#contacter-sortName-contacterAddPage").dblclick(function(){ //选择对应分类选项
    			selectTags(1)
    		});
    	});
		var editIndex = undefined;
		var $sortList = $("#contacter-sortList-contacterAddPage"); //分类datagrid的div对象
		function endEditing(){
	  		if (editIndex == undefined){
	  			return true
	  		}
	  		else{
	  			return false;
	  		}
		}
		function disabledSelectAdd(){ //添加行时判断默认分类选项是否可用
			var data = $sortList.datagrid('getRows');
			if(data.length == 0){return false}
			for(var i=0;i<data.length;i++){
				if(data[i]['isdefault'] == "1"){
					return true;
				}
			}
			return false;
		}
		function disabledSelectEdit(editIndex){ //修改行时判断默认分类选项是否可用
			var data = $sortList.datagrid('getRows');
			if(data[editIndex]['isdefault'] == "1"){
				$(".contacterSortDefault").removeAttr("disabled");
			}
			else{
				var bl = false;
				for(var i=0;i<data.length;i++){
					if(data[i]['isdefault'] == "1"){
						bl = true;
					}
				}
				if(bl){
					$(".contacterSortDefault").attr("disabled", "disabled");
				}
				else{
					$(".contacterSortDefault").removeAttr("disabled");
				}
			}
		}
    	function selectTags(index){ //选择第index个选项
   			$(".tags li").eq(index).click();
    	}
    </script>
  </body>
</html>

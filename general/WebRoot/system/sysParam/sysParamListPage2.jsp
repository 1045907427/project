<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>系统参数列表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
  	
 		<div data-options="region:'north',split:false,border:false" >
    		<div class="buttonBG" id="system-button-syscode"></div>
    	</div>
 		<div data-options="region:'center'" style="border: 0px;" >
 			<div id="personnel-layout-detail-north" data-options="region:'north',border:false" style="height:35px">
 				<ul class="tags">
 					
				</ul>
	 	   </div>
	 	   <div id="system-form-tabDetail" data-options="region:'center',border:false,fit:true,height:600px">
	 	   </div>
		   <div id = 'system-pannel-sysCodePanel'></div>
		   <div id = 'system-dialog-sysCodeDialog'></div>
 	</div>
 	
 	<script>
 		var oldFormData = new Object()//用于验证form是否发生改变
 		
 		var nowIndex = ''
 		var nowCodeVal = ''
 		
 		//加载Panel
 		function loadPanel(Tabindex,codeVal){
 				nowIndex=Tabindex;
 				nowCodeVal=codeVal;
 				var url = 'sysParam/sysParamModualPage.do?codeVal='+codeVal
	 			$(".tags li").removeClass("selectTag").eq(Tabindex).addClass("selectTag");
	 			$("#system-pannel-sysCodePanel").panel({
					href:url,
					title:'',
				    cache:false,
				    maximized:true,
				    border:false,
				    loadingMessage:'数据加载中...',
				    onLoad:function(){
// 				    	loadDetailData(codeVal)
				    }
				});
 		}
 		
 		
 		//检验表单是否修改过,修改过提醒,是否保存,否则直接切换
 		function showTabForm(Tabindex,codeVal){
 			newFormData = $("#system-form-syscodedetail").serializeJSON();
 			if(easyEqual(oldFormData,newFormData)){
 				loadPanel(Tabindex,codeVal)
 			}else{
 				$.messager.confirm("提醒","您已修改系统参数,是否保存修改?",function(r){
 					if(r){
 						saveSyscode()
 					}else{
 						loadPanel(Tabindex,codeVal)
 					}
 				});
 			}
 		}
 		
 		//保存修改
 		function saveSyscode(){
 			newFormData = $("#system-form-syscodedetail").serializeJSON();
 			loading();
 			$.ajax({
 				type:'post',
 				dataType:'json',
 				data:{updateInfo:JSON.stringify(newFormData)},
 				async:false,
 				url:'sysParam/updateSysParamByModual.do',
 				success:function(data){
 					loaded();
 					if(data.flag){
 						$.messager.alert("提醒",data.msg);
 						loadPanel(nowIndex,nowCodeVal)
 					}
 				}
 			})
 		}
 		
 		//加载每个模块Panel的data和tooltip
 		function loadDetailData(modialId){
	 		$.ajax({
 				type:'post',
 				dataType:'json',
 				data:{id:modialId},
 				async:false,
 				url:'sysParam/showSysParamListByModualId.do',
 				success:function(data){
//  					console.log(data.values)
 					$('#system-form-syscodedetail').form('load',data.values);
 				}
 			})
 			
	 		oldFormData = $("#system-form-syscodedetail").serializeJSON();	 		
	 		$("#system-form-syscodedetail  td").each(function(index,domEle){
		 		var name = $(this).attr('id');
			 	if(name){
			 		$(this).attr('onclick',$.formatString("getDetailByName('{0}')",name))
			 		$(this).mouseout(function(){
			 			$(this).css('cursor','default');
			 		})
			 		$(this).mouseover(function(){
			 			$(this).css('cursor','pointer');
			 		})
			 	}
		 	})
 		}
 		
 		
 		//点击查看详情
 		function getDetailByName(name){
			var url="sysParam/showSysParamInfoByName.do?name="+name;
	  		$("#system-dialog-sysCodeDialog").dialog({
	  				title:'系统参数详情',
	  				width:390,
	  				height:400,
	  				closed:false,
	  				cache:false,
	  				href:url,
	  				modal:true
 			})
 		}			
 		
 		function loadDropdown(){
   			//系统参数模块
   			$("#sysParam-moduleid-widget").widget({
   				width:204,
   				referwid:'RL_T_SYSPARAM_MODULE',
    			singleSelect:true,
             	required:true
   			});
   		}
 		
 		
 		$.formatString = function(str) {
			for ( var i = 0; i < arguments.length - 1; i++) {
				str = str.replace("{" + i + "}", arguments[i + 1]);
			}
			return str;
		};
		
		
		//判断两个对象属性,值是否相同
		function easyEqual(){
 				var result = true;
        		var attributeLengthA = 0, attributeLengthB = 0;
        		for (var o in arguments[0]){
        			if(o=="oldFromData") continue;
		            //判断两个对象的同名属性是否相同（数字或字符串）
		            if (typeof arguments[0][o] == 'number' || typeof arguments[0][o] == 'string'){
		                result = eval("arguments[0]['" + o + "'] == arguments[1]['" + o + "']");
		                if(result==false){
		                	break ;
		                }
		            }
            		++attributeLengthA;
        		}
        	    for (var o in arguments[1]) {
        	    	if(o=="oldFromData") continue;
            		++attributeLengthB;
        		}
		        //如果两个对象的属性数目不等，则两个对象也不等
		        if (attributeLengthA != attributeLengthB)
		            result = false;
		        return result;
 			}
		
		
		
 		$(function(){
 		
 			$.ajax({
 				type:'post',
 				dataType:'json',
 				async:false,
 				url:'sysParam/getSysParamModuleList.do',
 				success:function(data){
 					var items = data.rows;
 					var tmpStr = ''
 					for(var i = 0 ;i < items.length;i++){
 						if(i == 0){
	 						tmpStr = tmpStr + ($.formatString('<li class="selectTag"><a href="javascript:void(0)" onclick = showTabForm({0},{1})>{2}</a></li>',i,items[i].code,items[i].codename));
 							showTabForm(0,items[i].code)
 						}else{
	 						tmpStr = tmpStr + ($.formatString('<li><a href="javascript:void(0)" onclick = showTabForm({0},{1})>{2}</a></li>',i,items[i].code,items[i].codename));
 						}
 					}
 					$('.tags').html(tmpStr)
 				}
 			})
 			
 			
 			$("#system-button-syscode").buttonWidget({
 				initButton:[
 					{
							type:'button-save',
							handler:function(){
								saveSyscode()
							}
 					}
 				]
 			})
 			
 			$("#system-button-syscode").buttonWidget("enableButton","button-save");
 			
 		})
 	</script>	
  </body>
</html>

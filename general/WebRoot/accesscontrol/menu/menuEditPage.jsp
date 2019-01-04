<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>子菜单添加页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
      <form action="accesscontrol/editOperate.do" method="post" id="accesscontrol-form-editMenu">
	   	<div id="accesscontrol-menuEditPage" class="pageContent">
			<p>
				<label>菜单名称:</label>
				<input type="text" name="operate.operatename" class="easyui-validatebox" required="true" style="width:200px;" value="${operate.operatename }"/>
			</p>
			<p>
				<label>菜单描述:</label>
				<input type="text" name="operate.description" class="easyui-validatebox" required="true" style="width:200px;" value="${operate.description }"/>
			</p>
			<p>
				<label>链接地址:</label>
				<input type="text" name="operate.url" class="easyui-validatebox" required="true"  style="width:200px;" value="${operate.url }"/>
			</p>
            <p>
                <label>数据权限:</label>
                <input type="text" id="accesscontrol-menu-tablename" name="operate.tablename"  style="width:200px;" value="${operate.tablename }"/>
            </p>
			<p>
				<label>图片:</label>
				<input type="text" id="accesscontrol-menuEdit-operateimage" name="operate.image" style="width:200px;" readonly="readonly" value="${operate.image}"/>
				<a id="accesscontrol-menuEidt-imageSelect" href="javaScript:void(0);">选择</a>
			</p>
            <p>
                <label>顺序:</label>
                <input type="text" name="operate.seq" class="easyui-numberbox" data-options="min:0,max:99999,required:true,missingMessage:'请输入0到99999之间的数字'" style="width:200px;" value="${operate.seq }"/>
            </p>
			<p>
				<label>父菜单:</label>
				<input type="hidden" name="oldPid" value="${operate.pid }"/>
				<input type="text" id="accesscontrol-menuEdit-pid" style="width:200px;" name="operate.pid" value="${operate.pid }"/>
			</p>
	    </div>
	    <input type="hidden" name="operate.operateid" value="${operate.operateid }"/>
	    <div id="accesscontrol-menuEdit-image" class="menuImage">
	    	<ul id="accesscontrol-menuEdit-image-ul"></ul>
		</div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#accesscontrol-menuEdit-pid").widget({
    			referwid:'RT_T_AC_OPERATE',
    			singleSelect:true,
    			onlyLeafCheck:false
    		});
            $("#accesscontrol-menu-tablename").widget({
                referwid:'RL_T_AC_DATARULE_TABLENAME',
                singleSelect:true,
                onlyLeafCheck:false
            });
     		$("#accesscontrol-form-editMenu").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    },  
			    success:function(data){
			    	//转为json对象
			    	var json = $.parseJSON(data);
			        if(json.flag==true){
			        	$.messager.alert("提醒","修改成功");
			        	
			        	$('#accesscontrol-table-menuList').datagrid('reload');
			        	$.fn.zTree.init($("#accesscontrol-tree-menu"), treeSetting,null);
			        	$("#accesscontrol-dialog-operate").dialog('close',true);
			        }else{
			        	$.messager.alert("提醒","修改失败");
			        }
			    }  
			}); 
			$("#accesscontrol-edit-editMenuSubmit").click(function(){
				$.messager.confirm("提醒", "是否确定修改?", function(r){
					if (r){
						$("#accesscontrol-form-editMenu").submit();
					}
				});
			});
			$("#accesscontrol-menuEidt-imageSelect").click(function(){
				var  max= $("#accesscontrol-menuEdit-operateimage").offset();
				var min = $("#accesscontrol-menuEditPage").offset();
				var left = max.left-min.left+5;
				var top = max.top-min.top+50;
				$("#accesscontrol-menuEdit-image").css({left:left + "px", top:top + "px"}).slideDown("fast");
				$.ajax({   
		            url :'accesscontrol/showMenuImageList.do',
		            type:'post',
		            dataType:'json',
		            success:function(json){
		            	$("#accesscontrol-menuEdit-image-ul").html("");
		            	for(var i=0;i<json.length;i++){
		            		var html = '<li><a href="javaScript:void(0);" image="'+json[i]+'"><img src="image/menu/'+json[i]+'"></a></li>';
		            		$("#accesscontrol-menuEdit-image-ul").append(html);
		            	}
		            }
		        });
				
				$("#accesscontrol-menuEdit-image").show();
				$("body").bind("mousedown", function(event){
					if (!(event.target.id == "accesscontrol-menuEdit-operateimage" || event.target.id == "accesscontrol-menuEdit-image" || $(event.target).parents("#accesscontrol-menuEdit-image").length>0)) {
						$("#accesscontrol-menuEdit-image").hide();
					}
				});
			});
			$("#accesscontrol-menuEdit-image-ul li img").live("mouseover",function() {
				$(this).addClass("menuImageOn");
			});
			$("#accesscontrol-menuEdit-image-ul li img").live("mouseout",function() {
				$("#accesscontrol-menuEdit-image-ul li img").removeClass("menuImageOn");
			});
			$("#accesscontrol-menuEdit-image-ul li a").live("click",function() {
				var image = $(this).attr("image");
				$("#accesscontrol-menuEdit-operateimage").attr("value","image/menu/"+image);
				$("#accesscontrol-menuEdit-image").hide();
			});
     	});
    </script>
  </body>
</html>

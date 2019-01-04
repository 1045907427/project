<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>拜访记录明细图片</title>
    <%@include file="/include.jsp" %>    
  </head>
  <body>
  <script type="text/javascript" src="js/jquery.rotate.js" charset="UTF-8"></script>
  <script type="text/javascript" src="js/jquery.browser.js" charset="UTF-8"></script>
  <style>
  	.divcont{
  	
  	}
  	.notwrap{  		
			width:150px;
			white-space: pre-wrap;
			word-wrap: break-word;
			word-break:break-all;
  	}
  	td.titleTd{
  		width:95px;
  		text-align:right;
  	}
  	input.len1{
  		width:160px;
  	}
  	input.len2{
  		width:120px;
  	}
  	img{
  		border:0px;
  	}
  	tr.expand{
  		display:"";
  	}
  	tr.collapse{
  		display:none;
  	}
  </style>
  <div id="crm-layout-CrmVisitRecordPage" class="easyui-layout"  data-options="fit:true,border:false">
		<div data-options="region:'north',border:false" class="datagrid-toolbar">
	  	<table  cellspacing="0" cellpadding="3" >
	  		<tr cando="expand" class="collapse">
	  			<td class="titleTd">单据编号：</td>
	  			<td><input type="text" value="${resultMap.id }" class="len1" readonly="readonly" /></td>	  			
	  			<td class="titleTd">业务日期：</td>
	  			<td><input type="text" value="${resultMap.businessdate }" class="len2" readonly="readonly" /></td>	
	  			<td class="titleTd">星期：</td>
	  			<td>
	  			<input type="text" id="crm-crmVisitRecordDetailImg-weekday"  class="len2" readonly="readonly" /></td>
	  		</tr>
	  		<tr cando="expand" class="collapse">
	  			<td class="titleTd">客户名称：</td>
	  			<td><input type="text" title="${resultMap.customerid } ${resultMap.customername }" value="${resultMap.customerid } ${resultMap.customername }"  class="len1" readonly="readonly" /></td>	  			
	  			<td class="titleTd">业务员名称：</td>
	  			<td><input type="text" value="${resultMap.personname }" class="len2" readonly="readonly"  /></td>	
	  			<td class="titleTd">主管名称：</td>
	  			<td>
	  			<input type="text" value="${resultMap.leadname }" class="len2" readonly="readonly" ></td>
	  		</tr>
	  		<tr cando="expand" class="collapse">
	  			<td class="titleTd">上级客户：</td>
	  			<td><input type="text" title="${resultMap.pcustomerid } ${resultMap.pcustomername }" value="${resultMap.pcustomerid } ${resultMap.pcustomername }"  class="len1" readonly="readonly" /></td>	  			
	  			<td class="titleTd">客户分类：</td>
	  			<td><input type="text" value="${resultMap.customersortname }"  class="len2" readonly="readonly" /></td>	
	  			<td class="titleTd">销售区域：</td>
	  			<td>
	  			<input type="text" value="${resultMap.salesareaname }"  class="len2" readonly="readonly" ></td>
	  		</tr>
	  		<tr cando="expand" class="collapse">
	  			<td class="titleTd">销售部门：</td>
	  			<td><input type="text" value="${resultMap.salesdeptname }" class="len1" readonly="readonly" /></td>	  			
	  			<td class="titleTd">品牌名称：</td>
	  			<td><input type="text" value="${resultMap.brandname }"  class="len2" readonly="readonly" ></td>	
	  			<td class="titleTd">陈列标准：</td>
	  			<td>
	  			<input type="text" value="${resultMap.standardname }" class="len2" readonly="readonly" ></td>
	  		</tr>
	  		<tr cando="expand" class="collapse">
	  			<td class="titleTd">是否检查：</td>
	  			<td colspan="5"><input type="text" id="crm-crmVisitRecordDetailImg-isqa" class="len1" readonly="readonly" ></td>
	  		</tr>
	  		<tr cando="expand" class="collapse">
	  			<td class="titleTd">单据备注：</td>
	  			<td colspan="5">
	  				<textarea readonly="readonly" cols="0" rows="0" style="height:35px;width:640px;">${resultMap.remark }</textarea>
	  			</td>
	  		</tr>
	  		<tr cando="expand" class="collapse">
	  			<td class="titleTd">明细备注：</td>
	  			<td colspan="5">
	  				<textarea readonly="readonly" cols="0" rows="0" style="height:35px;width:640px;">${resultMap.detailremark }</textarea>
	  			</td>
	  		</tr>
	  		<tr>
	  			<td colspan="6">
	  				<div style="margin-left:300px;line-height:35px;">
		  				<a href="javaScript:void(0);" id="crm-btn-CrmVisitRecordPage-left" class="easyui-linkbutton" iconCls="button-back" plain="true" >图片向左旋转</a>
						<a href="javaScript:void(0);" id="crm-btn-CrmVisitRecordPage-right" class="easyui-linkbutton" iconCls="button-next" plain="true" >图片向右旋转</a>
						<a href="javascript:void(0);" id="crm-btn-CrmVisitRecordPage-expand" class="easyui-linkbutton" iconCls="button-down" plain="true" expand="1">展开详细信息</a>
					</div>
	  			</td>
	  		</tr>
	  	</table>
  	</div>
	<div data-options="region:'center',border:false">
	  	<img id="crm-crmVisitRecordDetailImg-img" src="${resultMap.detailimg }" alt="展示图片" title="明细编号${resultMap.detailid }"></img>
  	</div>
  </div>
  <script type="text/javascript">
  	$(document).ready(function(){
  		$("#crm-crmVisitRecordDetailImg-weekday").val(showWeekday("${resultMap.weekday }"));
  		$("#crm-crmVisitRecordDetailImg-isqa").val(showIsqa("${resultMap.isqa}"));
  		
  		$("#crm-btn-CrmVisitRecordPage-expand").click(function(){
  			var expand=$(this).attr("expand") || "0";
  			var imgPanelOptions={};
  			if(expand==0){
  				$(this).attr("expand","1");
  				$("tr[cando='expand']").hide();
  				$(this).linkbutton({    
  				    iconCls: 'button-down',
  				    text:'展开详细信息'
  				});  
				$("#crm-layout-CrmVisitRecordPage").layout("panel","north").panel('resize',{height:65});
				$("#crm-layout-CrmVisitRecordPage").layout("resize");
  			}
  			else
  			{
  				$(this).attr("expand","0");
  				$("tr[cando='expand']").show();  
  				$(this).linkbutton({    
  				    iconCls: 'button-upload',
  				    text:'收起详细信息'
  				});  

				$("#crm-layout-CrmVisitRecordPage").layout("panel","north").panel('resize',{height:300});
				$("#crm-layout-CrmVisitRecordPage").layout("resize");
  			}
  		});
  		
  		$("#crm-btn-CrmVisitRecordPage-left").click(function(){
  			rotateImg("left");
  		});
  		$("#crm-btn-CrmVisitRecordPage-right").click(function(){
  			rotateImg("right");
  		});
  	});
	  function showWeekday(value){
	  	if(value == "1"){
			return "星期一";
		}else if(value == "2"){
			return "星期二";
		}else if(value == "3"){
			return "星期三";
		}else if(value == "4"){
			return "星期四";
		}else if(value == "5"){
			return "星期五";
		}else if(value == "6"){
			return "星期六";
		}else if(value == "7"){
			return "星期七";
		}else{
			return "";
		}
	  }
	  
	  function showIsqa(value){
		  if(value=='0'){
  			return "未检查";
	  	  }else if(value=='1'){
	  		return "合格";
	      }else if(value=='2'){
	    	return "不合格";
	  	  }else{
	  		return "未检查";
	  	  }
	  }
	  
	  function rotateImg(direct){
		  if(direct!="left"){
			  direct="left"
		  }else{
			  direct="right";
		  }
		  var $img=$("#crm-crmVisitRecordDetailImg-img");

		  if($img.attr("src") ==""){
			 return false;
		  }
		  
		  var panelOptions = $("#crm-layout-CrmVisitRecordPage").layout("panel","center").panel('options') || {};
          var maxWidth = 800;
		  if(panelOptions.width){
			  maxWidth=panelOptions.width-20;
		  }
		  
		  var width = $img.width(),
          height = $img.height();
		  var raw_height = height,
          raw_width = width;
	
	      if (width > maxWidth) {
	          height = maxWidth / width * height;
	          width = maxWidth;
	      };
	      
	      $img.attr({width: width ,height : height, maxWidth : maxWidth , raw_width: raw_width ,raw_height: raw_height});
	      if(direct=="left"){
	    	  $img.rotate('left', maxWidth);
	      }
	      if(direct=="right"){
	    	  $img.rotate('right', maxWidth);	    	  
	      }
		  
	  }
	  

  </script>
</body>
</html>
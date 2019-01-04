<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>/系统参数/电商信息</title>
    <%@include file="/include.jsp" %>
    
  </head>
  
  <body>
					<form action="" method="post" id="system-form-syscodedetail">
						<table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
							<tr>
								<td align="right" style="width: 140px" id='ebgoodsprice'>电商商品信息单价：</td>
								<td>
									<input type="text" style="width: 180px" name='ebgoodsprice'>
								</td>
								
								<td align="right" style="width: 140px" id = 'defaultebgoodsrate'>默认占库存比重：</td>
								<td>
									<input type="text" style="width: 180px" name='defaultebgoodsrate'>
								</td>
								
								<td align="right" style="width: 140px" id = 'ebgoodsUsableStorageid'>是否根据仓库获取获取电商商品可用量：</td>
								<td>
									<input type="text" style="width: 180px" id = "ebgoodsUsableStorageidNAME" name='ebgoodsUsableStorageid'>
								</td>
							</tr>
							
						</table>
					</form>
					
	<script>
			$(function(){
				loadDetailData(6)
				
				//是否根据仓库获取获取电商商品可用量
				$("#ebgoodsUsableStorageidNAME").widget({
		     		width:180,
					referwid:'RL_T_BASE_STORAGE_INFO',
					singleSelect:false,
					onlyLeafCheck:false
     			});
				
				
			})
					
					
	</script>
  </body>
</html>

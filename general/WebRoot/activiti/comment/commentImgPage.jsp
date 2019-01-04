<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>审批流程图</title>
  </head>
  <body>
  	<style type="text/css">
  		.current{border:3px solid red;border-radius:10px;}
		.box1{padding:5px;position:absolute;line-height:22px;background:#FFFFA3;border:1px solid #F1D031;font-size:12px;color:#555555;display:none;z-index:999;}
		.box1 table{}
		.tip-table th {
			width: 70px;
		}
		.tip-table td {
			width: 150px;
		}
		.tip-table {
			width: 220px;
		}
	</style>
	<div id="workflow_tips">
		<span><label style="color: #008000;">绿色节点为已办理节点；</label></span>
		<span><label style="color: #FF0000">红色节点为当前正在办理节点。</label></span><br/>
		<span><label style="color: #000000">流程图在某些浏览器下可能无法正常显示，请切换至Chrome、FireFox等浏览器下查看。</label></span>
	</div>
	<div id="activiti-image-commentImgPage">
		<!-- <img src="image/loading.gif" style="vertical-align: top" /> 流程图加载中... -->
	</div>
	<script type="text/javascript">
		var base_href = $('base')[0].href;
		$(function(){
			
			$('base').remove();
			$.ajax({
				url:'act/getDefinitionDiagram.do',
				type:'post',
				dataType:'json',
				//data:'definitionkey=${definitionkey}',
                data: {definitionkey: '${definitionkey }', definitionid: '${definitionid }'},
				success:function(json){

					$("#activiti-image-commentImgPage").html(json.svg);
					$.getJSON("act/getCommentImgInfo.do", {"instanceid": "${instanceid }"}, function(json){
						
						var transform = $('svg').children().first().next().children().first().attr('transform');
						
						$('svg').append('<g class="stencils" id="transform_svg" transform="' + transform + '"></g>');
						
						var svg = new Array();
						for(var i = 0; i < json.length; i ++) {
						
							var j = json[i];
							
							// 开始/结束节点
							if(j.shape == 'NoneStartEventActivityBehavior' || j.shape == 'NoneEndEventActivityBehavior') {
							
								var cx = j.x + 0.5 * j.width;
								var cy = j.y + 0.5 * j.height;

                                if(j.starttime || j.endtime) {
                                    svg.push('<circle id="' + j.taskkey + '_svg" cx="' + cx + '" cy="' + cy + '" r="' + 0.5 * j.width + '" stroke="green" fill="white" stroke-width="2" fill-opacity="0.1"/>');
                                }

							// 用户任务
							} else if(j.shape == 'UserTaskActivityBehavior' || j.shape == 'ParallelMultiInstanceBehavior') {
							
								if(j.task) {
								
									if(j.current) {
										svg.push('<rect id="' + j.taskkey + '_svg" x="' + j.x + '" y="' + j.y + '" width="' + j.width + '" height="' + j.height + '" rx="10" ry="10" stroke="red" stroke-width="2" fill="#ffffcc" fill-opacity="0.1"/> ');
									} else {
										svg.push('<rect id="' + j.taskkey + '_svg" x="' + j.x + '" y="' + j.y + '" width="' + j.width + '" height="' + j.height + '" rx="10" ry="10" stroke="green" stroke-width="2" fill="#ffffcc" fill-opacity="0.1"/> ');
									}
								}
							
							// 分支
							} else if(j.shape == 'ExclusiveGatewayActivityBehavior') {

								svg.push('');
							}
							
							if(j.current) {
							
								// break;
							}
						}
						
						$('#transform_svg').html(svg.join(''));
												
						var str = $("#activiti-image-commentImgPage").html();
						$("#activiti-image-commentImgPage").html('');
						$("#activiti-image-commentImgPage").html(str);
												
						for(var i = 0; i < json.length; i ++) {
						
							var j = json[i];
							var html = new Array();
							
							//////////////////////////////////////////////////////////

							if(j.event == "start" || j.event == "end"){
								html.push("<table class=\"tip-table\" style='float:left;'>");
								html.push("<tr>");
								html.push("<th>节点名称</th><td>"+j.taskName+"</td>");
								html.push("</tr>");
								html.push("<tr>");
								html.push("<th>节点标识</th><td>"+j.taskkey+"</td>");
								html.push("</tr>");
                                if(j.handlename){
                                    html.push("<tr><th>　处理人</th><td>"+j.handlename+"</td></tr>");
                                }
                                if(j.state){
                                    html.push("<tr><th>办理状态</th><td>"+j.state+"</td></tr>");
                                }
                                if(j.agree){
                                    html.push("<tr><th>是否同意</th><td>"+j.agree+"</td></tr>");
                                }
                                if(j.info3 != undefined){
                                    html.push("<tr><td>"+j.info3+"</td></tr>");
                                }
                                if(j.starttime){
                                    html.push("<tr><th>开始时间</th><td>"+j.starttime+"</td></tr>");
                                }
                                if(j.endtime){
                                    html.push("<tr><th>结束时间</th><td>"+j.endtime+"</td></tr>");
                                }
                                html.push("</table>");

							} else if(j.shape == 'UserTaskActivityBehavior') {

								var t = j.task;
								
								if(t == undefined) {
									continue;
								}

								for(var m=0; m<t.length; m++){
									var k = t[m];
									html.push("<table class=\"tip-table\" style='float:left;'>");
                                    html.push("<tr>");
                                    html.push("<th>节点名称</th><td>"+j.taskName+"</td>");
                                    html.push("</tr>");
                                    html.push("<tr>");
                                    html.push("<th>节点标识</th><td>"+j.taskkey+"</td>");
                                    html.push("</tr>");
									if(k.viewer != undefined){
										html.push("<tr>");
										html.push("<td>"+k.viewer+" 等人已查阅</td>");
										html.push("</tr>");
									}
									if(k.handlename){
										html.push("<tr><th>　处理人</th><td>"+k.handlename+"</td></tr>");
									}
									if(k.state){
                                        html.push("<tr><th>办理状态</th><td>"+k.state+"</td></tr>");
									}
									if(k.agree){
                                        html.push("<tr><th>是否同意</th><td>"+k.agree+"</td></tr>");
									}
									if(k.info3 != undefined){
										html.push("<tr><td>"+k.info3+"</td></tr>");
									}
									if(k.starttime){
                                        html.push("<tr><th>开始时间</th><td>"+k.starttime+"</td></tr>");
									}
									if(k.endtime){
                                        html.push("<tr><th>结束时间</th><td>"+k.endtime+"</td></tr>");
									}
									html.push("</table>");
								}

							} else if(j.shape == 'ParallelMultiInstanceBehavior') {

                                var t = j.task;

                                if(t == undefined) {
                                    continue;
                                }

                                for(var m=0; m<t.length; m++){

                                    var k = t[m];
                                    html.push("<table class=\"tip-table\" style='float:left;'>");
                                    html.push("<tr>");
                                    html.push("<th>节点名称</th><td>"+j.taskName+"</td>");
                                    html.push("</tr>");
                                    html.push("<tr>");
                                    html.push("<th>节点标识</th><td>"+j.taskkey+"</td>");
                                    html.push("</tr>");
                                    if(k.viewer != undefined){
                                        html.push("<tr>");
                                        html.push("<td>"+k.viewer+" 等人已查阅</td>");
                                        html.push("</tr>");
                                    }
                                    if(k.handlename){
                                        html.push("<tr><th>　处理人</th><td>"+k.handlename+"</td></tr>");
                                    }
                                    if(k.state){
                                        html.push("<tr><th>办理状态</th><td>"+k.state+"</td></tr>");
                                    }
                                    if(k.agree){
                                        html.push("<tr><th>是否同意</th><td>"+k.agree+"</td></tr>");
                                    }
                                    if(k.info3 != undefined){
                                        html.push("<tr><td>"+k.info3+"</td></tr>");
                                    }
                                    if(k.starttime){
                                        html.push("<tr><th>开始时间</th><td>"+k.starttime+"</td></tr>");
                                    }
                                    if(k.endtime){
                                        html.push("<tr><th>结束时间</th><td>"+k.endtime+"</td></tr>");
                                    }
                                    html.push("</table>");
                                }

                            } else {
								continue;
							}
							//////////////////////////////////////////////////////////
							
							$('#' + j.taskkey + '_svg').tooltip({
							    position: 'right',
							    content: html.join(''),
							    onShow: function(e){

							        $(this).tooltip('tip').css({
							            backgroundColor: '#fefefe',
							            borderColor: '#ddd',
                                        left: e.clientX + 'px',
                                        top: e.clientY + 'px'
							        });
                                    $(this).tooltip('arrow').remove();
							    }
							});

							if(j.current) {
							
								// break;
							}

						}
						
						return false;
					});

				}
			});

		});
	</script>
  </body>
</html>

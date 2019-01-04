<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>表单预览</title>
    <!-- 该页面针对于新版（Ueditor）Form编辑页面 -->
    <script type="text/javascript" src="../activiti/js/jquery.js" charset="UTF-8"></script>
    <script type="text/javascript">
        $(function(){
            <c:choose>
                <c:when test="${param.flag eq '2'}">
                    $('div#preview')[0].innerHTML = '';
                    $('div#preview').append(parent.retHtml());
                </c:when>
                <c:otherwise>
                    //$('body').append(parent.retHtml());
                    $('div#preview')[0].innerHTML = '';
                    $('div#preview').append(parent.retHtml());
                </c:otherwise>
            </c:choose>
            // 设置父页面iframe高度
            parent.setFrameHeight($('div#preview')[0].clientHeight);
        });

        function retItems() {

            var items = new Array();
            $('input[type=text]').each(function() {

                items.push($(this)[0].outerHTML);
            });
            $('textarea').each(function() {

                items.push($(this)[0].outerHTML);
            });
            $('select').each(function() {

                items.push($(this)[0].outerHTML);
            });
            $('span[plugins=radios]').each(function() {

                items.push($(this)[0].outerHTML);
            });
            $('span[plugins=checkboxs]').each(function() {

                items.push($(this)[0].outerHTML);
            });

            return items;
        }

    </script>
    <style type="text/css">
        /*table {
            border-collapse: collapse;
            border:1px solid #7babcf;
        }*/
    </style>
    <style>
        html,body{
            /*height:100%;*/
            /*width:100%;*/
            padding:0;
            margin:0;
        }
        #preview{
            /*width:100%;*/
            /*height:100%;*/
            padding:0;
            margin:0;
        }
        #preview *{font-family:sans-serif;/*font-size:16px;*/}
    </style>
    <style id="table">
        #preview table.noBorderTable td,#preview table.noBorderTable th,#preview table.noBorderTable caption{border:1px dashed #ddd !important}#preview table.sortEnabled tr.firstRow th,#preview table.sortEnabled tr.firstRow td{padding-right:20px; background-repeat: no-repeat;background-position: center right; background-image:url(../../themes/default/images/sortable.png);}#preview table.sortEnabled tr.firstRow th:hover,#preview table.sortEnabled tr.firstRow td:hover{background-color: #EEE;}#preview table{margin-bottom:10px;border-collapse:collapse;display:table;}#preview td,#preview th{ background:white; padding: 5px 10px;border: 1px solid #DDD;}#preview caption{border:1px dashed #DDD;border-bottom:0;padding:3px;text-align:center;}#preview th{border-top:1px solid #BBB;background:#F7F7F7;}#preview table tr.firstRow th{border-top:2px solid #BBB;background:#F7F7F7;}#preview tr.ue-table-interlace-color-single td{ background: #fcfcfc; }#preview tr.ue-table-interlace-color-double td{ background: #f7faff; }#preview td p{margin:0;padding:0;}
        #preview table.noBorderTable td,#preview table.noBorderTable th,#preview table.noBorderTable caption{border:1px dashed #ddd !important}#preview table.sortEnabled tr.firstRow th,#preview table.sortEnabled tr.firstRow td{padding-right:20px; background-repeat: no-repeat;background-position: center right; background-image:url(../../themes/default/images/sortable.png);}#preview table.sortEnabled tr.firstRow th:hover,#preview table.sortEnabled tr.firstRow td:hover{background-color: #EEE;}#preview table{margin-bottom:10px;border-collapse:collapse;display:table;}#preview td,#preview th{ background:white; padding: 5px 10px;border: 1px solid #DDD;}#preview caption{border:1px dashed #DDD;border-bottom:0;padding:3px;text-align:center;}#preview th{border-top:1px solid #BBB;background:#F7F7F7;}#preview table tr.firstRow th{border-top:2px solid #BBB;background:#F7F7F7;}#preview tr.ue-table-interlace-color-single td{ background: #fcfcfc; }#preview tr.ue-table-interlace-color-double td{ background: #f7faff; }#preview td p{margin:0;padding:0;}</style><style id="chartsContainerHeight">
    .edui-chart-container { height:500px}
    .edui-chart-container { height:500px}
    </style>
    <style>#ads_c_tpc, .a_fl, .a_fr, .a_h, .a_mu, .a_pr, .ad_footerbanner, #ad_headerbanner, .ad_headerbanner, [id^="ad_thread"], .archiver_banner, .wp.a_f, .wp.a_t, a[href^="http://sina.allyes.com/main/adfclick?"], a[target="_blank"][onmousedown^="this.href='http://p.ujian.cc/?url="] { display: none !important; }</style>
</head>
<body class="view">
<!-- <div id="acitivit-div-formPreviewPage3"></div> -->
<div id="preview" style="margin:8px"><table><tbody><tr class="firstRow"><td width="99" valign="top" style="word-break: break-all;">aaaa</td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td></tr><tr><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td></tr><tr><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td><td width="99" valign="top"><br></td></tr></tbody></table><p><br></p></div>
</body>
</html>

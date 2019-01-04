(function () {
    jQuery.extend({
        AgReportPrint: function (opttype, options) {
            if (undefined != options) {
                var stype="clodopprint";
                if($.isFunction(opttype)){
                    stype=opttype();
                }else{
                    stype=opttype;
                }
                if (stype == "view") {
                    options = jQuery.extend({}, printViewdefaults, options);
                    options.justprint = false;
                    //parseurl(options);
                    //agReportprintView(options);
                    agReportprintPopView(options);
                } else if (stype == "popview" || stype == "pdfview" ) {
                    options = jQuery.extend({}, printViewdefaults, options);
                    options.justprint = false;
                    agReportprintPopView(options);
                } else if (stype == "viewshow") {
                    options = jQuery.extend({}, printViewdefaults, options);
                    options.justprint = false;
                    agReportprintViewShow(options);
                } else if (stype == "appletprint") {
			        	appletprintHandle(options);
                } else if (stype == "clodopprint") {
                    ajaxHtmlPrintHandle(options);
                } else if (stype == "clodopprintview") {
                    ajaxHtmlPrintViewHandle(options);
                } else if (stype == "ajaxhtmlprint") {
                    ajaxHtmlPrintHandle(options);
                } else {
                    ajaxHtmlPrintHandle(options);
                }
            }
        }
    });
	appletprintHandle=function(options){		
		try{
			setTimeout(function(){
				options.beforeHandle();
			},50);
		}catch(ex){
		}
		options = jQuery.extend({}, printDefaults, options);
		options.justprint=true;
		parseurl(options);	

		//options.libs = options.webpath+"applets/FDSoftJRPrint.jnlp"
		options.libs='reportprint.jar?v=1.0.6,jasperreports-applet-5.6.0.jar,jasperreports-5.6.0.jar,commons-collections-3.2.1.jar,commons-logging-1.1.jar,commons-beanutils-1.8.2.jar,jasperreports-fonts-5.6.0.jar,commons-digester-2.1.jar';
					
		if("withbarcode"==options.libtype){
			options.libs='reportprint.jar?v=1.0.6,barcode4j-2.1.jar,batik-anim.jar,batik-awt-util.jar,batik-bridge.jar,batik-css.jar,batik-dom.jar,batik-ext.jar,batik-gvt.jar,batik-parser.jar,batik-script.jar,batik-svg-dom.jar,batik-svggen.jar,batik-transcoder.jar,batik-util.jar,batik-xml.jar,commons-beanutils-1.8.2.jar,commons-collections-3.2.1.jar,commons-digester-2.1.jar,commons-logging-1.1.jar,iText-2.1.7.js2.jar,iTextAsian.jar,jasperreports-5.6.0.jar,jasperreports-applet-5.6.0.jar,jasperreports-fonts-5.6.0.jar,jfreechart-1.0.12.jar,xml-apis-ext.jar,xml-apis.jar';
			//options.libs = options.webpath+"applets/FDSoftJRPrintFull.jnlp"
		}
		//初始化一次打印				
		agReportprint(options);	
		try{
			setTimeout(function(){
				options.afterHandle();
			},100);
		}catch(ex){
		}
	};
    ajaxHtmlPrintHandle = function (options) {
        setTimeout(function () {
            try {
                options.beforeHandle();
            } catch (ex) {
            }
        }, 50);

        var theLodopApp = MyLodop.getLodop();
        theLodopApp = MyLodop.getLodop();
        if (!theLodopApp && document.readyState !== "complete") {
            $.messager.alert("提醒", "C-Lodop没准备好，请稍后再试！");
            return;
        }
        if (!theLodopApp) {
            if (top.$("#mylodop-download-dialog").size() == 0) {
                $.messager.alert("提醒", "C-Lodop没准备好，请稍后再试！");
            }
            return;
        }
        theLodopApp.PRINT_INIT('');
        options = jQuery.extend({}, printDefaults, options);
        options.justprint = true;
        options.viewtype = "ajaxhtml";
        parseAjaxHtmlPrintUrl(options);
        var printPageDataUrl = options.webpath + "agprint/tplprintjob/getPrintJobDetailContentData.do?jobdetailid=";
        $.messager.alert("提醒", "打印处理中..");
        loading('打印处理中..');

        var printmethodtype = options.printmethodtype || "1";
        if (printmethodtype != '1' && printmethodtype != '2') {
            printmethodtype = '1';
        }
        options.printmethodtype = printmethodtype;
        try {
            $.ajax({
                type: 'post',
                cache: false,
                url: options.reporturl,
                data: options.reporturlparams,
                dataType: 'json',
                success: function (data) {
                    loaded();
                    try {
                        if (data.flag == true) {
                            if (data.printdata == null || !isArray(data.printdata) || data.printdata.length == 0) {
                                $.messager.alert("提醒", "打印获取数据失败，请重新再试!");
                                return;
                            }
                            var printJobId = "";

                            printJobId = data.printJobId || "";

                            if (data.printname != null && data.printname != "") {
                                theLodopApp.PRINT_INIT(data.printname);
                            } else if (printJobId != "") {
                                theLodopApp.PRINT_INIT("单据打印作业-" + printJobId);
                            } else {
                                theLodopApp.PRINT_INIT("单据打印时间戳-" + (Date.parse(new Date()) / 1000));
                            }

                            //theLodopApp.SET_PRINTER_INDEXA(-1);
                            theLodopApp.SET_PRINT_PAGESIZE(0, 0, 0, "A4");
                            if (data.paperwidth != null && data.paperheight != null) {
                                var papersizename = $.trim(data.papersizename || "");
                                if (papersizename == "") {
                                    papersizename = "HBN_" + data.paperwidth + "*" + data.paperheight;
                                }
                                data.papersizename = papersizename;
                                //为空时动态使用打印纸张
                                theLodopApp.SET_PRINT_PAGESIZE(0, (data.paperwidth * 100), (data.paperheight * 100), "CreateCustomPage");
                                theLodopApp.SET_PRINT_MODE("CREATE_CUSTOM_PAGE_NAME", papersizename);//对新建的纸张重命名
                                //theLodopApp.SET_PRINT_MODE("POS_BASEON_PAPER",true);
                            }
                            //theLodopApp.SET_PRINT_MODE("FULL_WIDTH_FOR_OVERFLOW",true);
                            theLodopApp.SET_PRINT_MODE("FULL_HEIGHT_FOR_OVERFLOW",true);
                            var printmsg = "本次将会打印" + data.printdata.length + "页";
                            if (data.printdata.length > 200) {
                                printmsg = printmsg + "。<br/>注意：当前打印页数超过200页，打印处理会很慢。";
                            }
                            $.messager.alert("提醒", printmsg);
                            //try{
                            //	theLodopApp.SET_PRINT_MODE("SEND_RAW_DATA_ENCODE","UTF-8");
                            //}catch(e){

                            //}
                            var canPrint = false;
                            var addLodopListCount = 0;
                            var lodophtmlmodel=data.lodophtmlmodel || "1";
                            for (var i = 0; i < data.printdata.length; i++) {
                                var printdata = data.printdata[i];
                                var printOrderId = printdata.printOrderId || "";
                                var printSourceOrderId = printdata.printSourceOrderId || "";
                                var orderCurrentPage = printdata.orderCurrentPage || "";
                                var pagedata = printdata.pagedata || "";
                                if (pagedata == "") {
                                    if (printOrderId != "") {
                                        var msg = "打印单据号：" + printOrderId;
                                        if (printSourceOrderId != "") {
                                            msg = msg + " 上游单据号：" + printSourceOrderId;
                                        }
                                        if (orderCurrentPage != "") {
                                            msg = msg + " 第" + printSourceOrderId + "页未能打印";
                                        }
                                        $.messager.alert("提醒", msg);
                                    }
                                    continue;
                                }
                                //theLodopApp.ADD_PRINT_URL(0,0,"100%","100%",printPageDataUrl+pagedata);
                                var remoteprintdata = AgReportPrint.getRemotePrintPageData(printPageDataUrl + pagedata);
                                if (remoteprintdata != "") {
                                    if (i > 0) {
                                        theLodopApp.NEWPAGE();
                                    }
                                    if(lodophtmlmodel=="2") {
                                        theLodopApp.ADD_PRINT_HTML(0, 0, "100%", "100%", remoteprintdata);
                                    }else {
                                        theLodopApp.ADD_PRINT_HTM(0,0,"100%","100%",remoteprintdata);
                                    }
                                    addLodopListCount = addLodopListCount + 1;
                                }
                                //canPrint=true;
                            }
                            if (addLodopListCount == 0) {
                                $.messager.alert("提醒", msg);
                                canPrint = false;
                            } else {
                                canPrint = true;
                            }
                            if (canPrint) {
							    theLodopApp.SET_PRINT_STYLEA(0,"HtmWaitMilSecs",1000);
                                if (!AgReportPrint || !AgReportPrint.lodopPrintType || AgReportPrint.lodopPrintType == "printview") {
                                    theLodopApp.PREVIEW();
                                } else {
                                    if (printmethodtype == 1) {
                                        theLodopApp.PRINTA();
                                    } else {
                                        theLodopApp.PRINT();
                                    }
                                }
                                if (printJobId != "") {
                                    if (theLodopApp.CVERSION) {
                                        theLodopApp.On_Return = function (TaskID, Value) {
                                            if (Value == true) {
                                            } else {
                                                $.messager.alert("提醒", "您取消打印或打印未成功!");
                                            }
                                        };
                                    }
                                }
                            } else {
                                //theLodopApp.PRINT_INIT("");
                            }
                        } else {
                            if (data.msg != null && data.msg != "") {
                                $.messager.alert("提醒", data.msg);
                            }
                        }
                    } catch (e) {
                        $.messager.alert("提醒", "解析打印数据时异常，请重新再试!");
                        //theLodopApp.PRINT_INIT("");
                    }

                    setTimeout(function () {
                        try {
                            options.afterHandle();
                        } catch (ex) {
                        }
                    }, 100);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $.messager.alert("提醒", "打印获取数据失败，请重新再试!");
                    loaded();
                }
            });

        } catch (e) {
            loaded();
        }
    };
    ajaxHtmlPrintViewHandle = function (options) {
        setTimeout(function () {
            try {
                options.beforeHandle();
            } catch (ex) {
            }
        }, 50);

        var theLodopApp = MyLodop.getLodop();
        theLodopApp = MyLodop.getLodop();
        if (!theLodopApp && document.readyState !== "complete") {
            $.messager.alert("提醒", "C-Lodop没准备好，请稍后再试！");
            return;
        }
        if (!theLodopApp) {
            if (top.$("#mylodop-download-dialog").size() == 0) {
                $.messager.alert("提醒", "C-Lodop没准备好，请稍后再试！");
            }
            return;
        }
        theLodopApp.PRINT_INIT('');
        options = jQuery.extend({}, printDefaults, options);
        options.justprint = true;
        options.viewtype = "ajaxhtml";
        parseAjaxHtmlPrintUrl(options);
        var printPageDataUrl = options.webpath + "agprint/tplprintjob/getPrintJobDetailContentData.do?jobdetailid="
        $.messager.alert("提醒", "打印预览处理中..");
        loading('打印预览处理中..');

        var printmethodtype = options.printmethodtype || "1";
        if (printmethodtype != '1' && printmethodtype != '2') {
            printmethodtype = '1';
        }
        try {
            $.ajax({
                type: 'post',
                cache: false,
                url: options.reporturl,
                data: options.reporturlparams,
                dataType: 'json',
                success: function (data) {
                    loaded();
                    try {
                        if (data.flag == true) {
                            if (data.printdata == null || !isArray(data.printdata) || data.printdata.length == 0) {
                                $.messager.alert("提醒", "打印预览获取数据失败，请重新再试!");
                                return;
                            }
                            var printJobId = "";
                            printJobId = data.printJobId || "";
                            if (data.printname != null && data.printname != "") {
                                theLodopApp.PRINT_INIT(data.printname);
                            } else if (printJobId != "") {
                                theLodopApp.PRINT_INIT("单据打印作业-" + printJobId);
                            } else {
                                theLodopApp.PRINT_INIT("单据打印时间戳-" + (Date.parse(new Date()) / 1000));
                            }
                            //theLodopApp.SET_PRINTER_INDEXA(-1);
                                theLodopApp.SET_PRINT_PAGESIZE(0, 0, 0, "A4");
                            if (data.paperwidth != null && data.paperheight != null) {
                                var papersizename = $.trim(data.papersizename || "");
                                if (papersizename == "") {
                                    papersizename = "HBN_" + data.paperwidth + "*" + data.paperheight;
                                }
                                data.papersizename = papersizename;
                                //为空时动态使用打印纸张
                                theLodopApp.SET_PRINT_PAGESIZE(0, (data.paperwidth * 100), (data.paperheight * 100), "CreateCustomPage");
                                theLodopApp.SET_PRINT_MODE("CREATE_CUSTOM_PAGE_NAME", papersizename);//对新建的纸张重命名
                                //theLodopApp.SET_PRINT_MODE("POS_BASEON_PAPER",true);
                            }
                            //theLodopApp.SET_PRINT_MODE("FULL_WIDTH_FOR_OVERFLOW",true);
                            theLodopApp.SET_PRINT_MODE("FULL_HEIGHT_FOR_OVERFLOW",true);
                            var printmsg = "本次将会预览" + data.printdata.length + "页";
                            if (data.printdata.length > 200) {
                                printmsg = printmsg + "。<br/>注意：当前打印预览页数超过200页，打印预览处理会很慢。";
                            }
                            $.messager.alert("提醒", printmsg);
                            //try{
                            //	theLodopApp.SET_PRINT_MODE("SEND_RAW_DATA_ENCODE","UTF-8");
                            //}catch(e){
                            //}
                            var canPrint = false;
                            var addLodopListCount = 0;
                            var lodophtmlmodel=data.lodophtmlmodel || "1";
                            for (var i = 0; i < data.printdata.length; i++) {
                                var printdata = data.printdata[i];
                                var printOrderId = printdata.printOrderId || "";
                                var printSourceOrderId = printdata.printSourceOrderId || "";
                                var orderCurrentPage = printdata.orderCurrentPage || "";
                                var pagedata = printdata.pagedata || "";
                                if (pagedata == "") {
                                    if (printOrderId != "") {
                                        var msg = "打印预览单据号：" + printOrderId;
                                        if (printSourceOrderId != "") {
                                            msg = msg + " 上游单据号：" + printSourceOrderId;
                                        }
                                        if (orderCurrentPage != "") {
                                            msg = msg + " 第" + printSourceOrderId + "页未能打印预览";
                                        }
                                        $.messager.alert("提醒", msg);
                                    }
                                    continue;
                                }
                                //theLodopApp.ADD_PRINT_URL(0,0,"100%","100%",printPageDataUrl+pagedata);
                                var remoteprintdata = AgReportPrint.getRemotePrintPageData(printPageDataUrl + pagedata);
                                if (remoteprintdata != "") {
                                    if (i > 0) {
                                        theLodopApp.NEWPAGE();
                                    }
                                    if(lodophtmlmodel=="2") {
                                        theLodopApp.ADD_PRINT_HTML(0, 0, "100%", "100%", remoteprintdata);
                                    }else {
                                        theLodopApp.ADD_PRINT_HTM(0,0,"100%","100%",remoteprintdata);
                                    }
                                    addLodopListCount = addLodopListCount + 1;
                                }
                                //canPrint=true;
                            }
                            if (addLodopListCount == 0) {
                                $.messager.alert("提醒", msg);
                                canPrint = false;
                            } else {
                                canPrint = true;
                            }
                          //  if (canPrint) {
                              //  theLodopApp.SET_SHOW_MODE("HIDE_PBUTTIN_PREVIEW", 0);//隐藏打印按钮
                              //  theLodopApp.SET_SHOW_MODE("HIDE_SBUTTIN_PREVIEW", 0);//隐藏设置按钮
                                theLodopApp.PREVIEW();
                          //  } else {
                                //theLodopApp.PRINT_INIT("");
                          //  }
                        } else {
                            if (data.msg != null && data.msg != "") {
                                $.messager.alert("提醒", data.msg);
                            }
                        }
                    } catch (e) {
                        $.messager.alert("提醒", "解析打印预览数据时异常，请重新再试!");
                        //theLodopApp.PRINT_INIT("");
                    }
                    setTimeout(function () {
                        try {
                            options.afterHandle();
                        } catch (ex) {
                        }
                    }, 100);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $.messager.alert("提醒", "打印预览获取数据失败，请重新再试!");
                    loaded();
                }
            });
        } catch (e) {
            loaded();
        }
    };
	agReportprint=function(options){	
		var objectTagHtml=agReportPrintCreateObjectTag(options);
		var utc=getUtc();
		var id='agreportprint-print-div-'+createUUID(16,10);
		
		var $newElem=top.$("<div id='"+id+"' utc='"+utc+"' style='height:0px;'></div>");
	    $newElem.html(objectTagHtml);

	    top.$("body").append($newElem);
	    
	    try{
	    	if(top.removeExpiredAgPrintTagInterval==null || top.removeExpiredAgPrintTagInterval <=0){
	    		top.startRemoveExpiredPrintTagFunc();
	    	}
	    }catch(e){
	    	
	    }
	};
	
	agReportPrintCreateObjectTag=function(options){
		var htmlsb=new Array();
		htmlsb.push("<OBJECT classid=\"clsid:8AD9C840-044E-11D1-B3E9-00805F499D93\" ");
		htmlsb.push("WIDTH = \""+options.width+"\" HEIGHT = \""+options.height+"\"  codebase=\""+options.webpath+"applets/jre-6u45-windows-i586.exe\">");
		htmlsb.push("<PARAM NAME = \"CODE\" VALUE  = \"com.hd.agent.applet.JRPrinterApplet.class\" /> ");
		htmlsb.push("<PARAM NAME = \"CODEBASE\" VALUE  = \""+options.webpath+"applets\" /> ");
		//htmlsb.push("<PARAM NAME = \"jnlp_href\" VALUE  = \""+options.libs+"\" /> ");
		htmlsb.push("<PARAM NAME = \"ARCHIVE\" VALUE  = \""+options.libs+"\" /> ");
		htmlsb.push("<PARAM NAME = \"type\" VALUE =\"application/x-java-applet;version=1.2.2\" /> ");
		htmlsb.push("<PARAM NAME = \"scriptable\" VALUE =\"false\" /> ");
		if(options.showprintdialog==false){
			htmlsb.push("<PARAM NAME = \"REPORT_SHOWPRINTDIALOG\" VALUE=\"false\"/>");
		} else {
			htmlsb.push("<PARAM NAME = \"REPORT_SHOWPRINTDIALOG\" VALUE=\"true\"/>");
		}
		if(options.showdebug==true){
			htmlsb.push("<PARAM NAME = \"REPORT_DEBUG\" VALUE=\"true\"/> ");
		}
		htmlsb.push("<PARAM NAME = \"REPORT_URL\" VALUE=\""+options.reporturl+"\" /> ");
		htmlsb.push("<PARAM NAME = \"REPORT_URLPARAMS\" VALUE=\""+options.reporturlparams+"\" /> ");
		htmlsb.push("<PARAM NAME = \"REPORT_STARTPRINT\" VALUE=\"true\" /> ");
		htmlsb.push("<COMMENT>");
		htmlsb.push("<EMBED ");
		htmlsb.push("type = \"application/x-java-applet;version=1.5\" ");
		htmlsb.push("CODE = \"com.hd.agent.applet.JRPrinterApplet.class\" ");
		htmlsb.push("CODEBASE = \""+options.webpath+"applets\" ");
		//htmlsb.push("jnlp_href = \""+options.libs+"\" ");
		htmlsb.push("ARCHIVE = \""+options.libs+"\" ");
		htmlsb.push("scriptable = \"false\" ");
		if(options.showprintdialog==false){
			htmlsb.push("REPORT_SHOWPRINTDIALOG='false' ");
		}else{
			htmlsb.push("REPORT_SHOWPRINTDIALOG='true' ");
		}
		if(options.showdebug==true){
			htmlsb.push("REPORT_DEBUG='true' ");
		}
		htmlsb.push("REPORT_URL = \""+options.requesturl+"\" ");
		htmlsb.push("REPORT_URLPARAMS = \""+options.requesturlparams+"\" ");
		htmlsb.push("pluginspage = \""+options.webpath+"applets/jre-6u45-windows-i586.exe\" width=\""+options.width+"\" height=\""+options.height+"\">");
		htmlsb.push("<PARAM NAME = \"jnlp_href\" VALUE  = \""+options.libs+"\" /> ");
		htmlsb.push("</EMBED>")
		htmlsb.push("<NOEMBED>");
		htmlsb.push("</NOEMBED>");
		htmlsb.push("</EMBED>");
		htmlsb.push("</COMMENT>");
		htmlsb.push("</OBJECT>");
		return htmlsb.join("");
	};
	
    agReportprintPopView = function (options) {
        if ($("#agreportprint-printview-div").length < 1) {
            $("body").append("<div id='agreportprint-printview-div' style=\"display:none\"></div>");
        } else {
            $("#agreportprint-printview-div").remove();
            $("body").append("<div id='agreportprint-printview-div' style=\"display:none\"></div>");
        }

        var urlparam = JSON.stringify(options);
        /*
         $.ajax({
         type: "GET",
         url: "common/agReportprintPopViewPage.do",
         data:{urlparam:encodeURI(urlparam)},
         dataType: "html"
         });
         */
        var width = 1012;
        var height = 800;
        var openparam = "top=0, left=0, toolbar=no, menubar=no, scrollbars=yes,location=no, status=no,fullscreen=yes";
        if (top.window && top.window.screen) {
            var tmpwidth = top.window.screen.availWidth;
            var tmpheight = top.window.screen.availHeight;
            if (tmpwidth && tmpwidth > width) {
                width = tmpwidth;
            }
            if (tmpheight && tmpheight > height) {
                height = tmpheight;
            }
            openparam = openparam + ", resizable=yes";
        } else {
            openparam = openparam + ", resizable=yes";
        }
        openparam = openparam + ",width=" + width + ",height=" + height;
        var url = parseRequestUrl(options, "common/agReportprintPopViewPage.do");
        //url=url+"?urlparam="+encodeURI(encodeURI(urlparam));
        //var opwin=window.open (url, "打印预览",openparam);

        var formarr = new Array();
        formarr.push("<form method=\"post\" action=\"" + url + "\"");
        formarr.push(" target=\"agentprintViewWindow\" ");
        formarr.push(">");
        formarr.push("<textarea name=\"urlparam\">" + encodeURI(urlparam) + "<\/textarea>");
        formarr.push("<input type=\"submit\" name=\"submit\" id=\"agentprintViewFormBtn\">");
        formarr.push("</form>");

        $("#agreportprint-printview-div").append(formarr.join(""));
        setTimeout(function () {
            window.open('about:blank', 'agentprintViewWindow', openparam);
            $("#agentprintViewFormBtn").trigger("click");
            $("#agreportprint-printview-div").remove();
        }, 2);
    };
    agReportprintViewShow = function (options) {
        options.parseNotJoinUrlParam = true;
        parseurl(options);
        printViewIFrameShow(options);
    };
    printViewIFrameShow = function (options) {

        /*
         if($("#agreportprint-print-div").length < 1){
         $("body").append("<div id='agreportprint-print-div' style=\"display:none\"></div>");
         }else{
         $("#agreportprint-print-div").remove();
         $("body").append("<div id='agreportprint-print-div'></div>");
         }
         */

        var width = options.popwidth || 1024;
        var height = options.popheight || 800;
        if (top.window && top.window.screen) {
            var tmpwidth = top.window.screen.availWidth;
            var tmpheight = top.window.screen.availHeight;
            if (tmpwidth && tmpwidth > width) {
                width = tmpwidth - 30;
            }
            if (tmpheight && tmpheight > height) {
                height = tmpheight - 20;
            }
        }
        var width = options.popwidth || 1024;
        var height = options.popheight || 800;
        if (top.window && top.window.screen) {
            var tmpwidth = top.window.screen.availWidth;
            var tmpheight = top.window.screen.availHeight;
            if (tmpwidth && tmpwidth > width) {
                width = tmpwidth - 30;
            }
            if (tmpheight && tmpheight > height) {
                height = tmpheight - 20;
            }
        }
        var iframe = $('#iframeView');
        iframe.css("display", "");
        iframe.attr('height', height).attr('width', width).attr('marginheight', '0').attr('marginwidth', '0').attr('frameborder', '0');

        $("#formDiv").html("");
        if (options.urlparam && typeof options.urlparam === "object") {
            var formarr = new Array();
            for (var key in options.urlparam) {
                if (key != "") {
                    formarr.push("<input type=\"hidden\" name=\"");
                    formarr.push(key);
                    formarr.push("\"");
                    if (options.urlparam[key] != null) {
                        formarr.push(" value=")
                        var updata = options.urlparam[key];
                        if (updata instanceof Array) {
                            formarr.push(updata.join(','));
                        } else {
                            formarr.push(JSON.stringify(updata));
                        }
                    } else {
                        formarr.push(" value=''");
                    }
                    formarr.push(" />");
                }
            }
            $("#formDiv").html(formarr.join(""));
        }
        $("#iframeViewFrom").attr("action", options.requesturl);
        $("#submitFormBtn").trigger("click");
    };

    parseurl = function (options) {
        var url = options.url || "";
        var isParams = options.parseNotJoinUrlParam || false;
        var dataparams = options.urlparam;
        var cmdparams = options.urlcmdparam;
        var webpath = options.webpath;
        var requesturl = "";
        if (webpath.length > 0) {
            if (webpath.lastIndexOf("/") != (webpath.length - 1)) {
                webpath = webpath + "/";
            }
        } else {
            webpath = "/";
        }
        options.webpath = webpath;
        if (url.length > 0) {
            if (url.indexOf("/") == 0) {
                url = url.substring(1);
            }
        }
        requesturl = webpath + url;
        var viewtype = "";
        if (options.viewtype && options.viewtype == "applet") {
            viewtype = "applet";
        } else if (options.viewtype && options.viewtype == "html") {
            viewtype = "html";
        } else {
            viewtype = "pdf";
            options.viewtype = "pdf";
        }
        var urlpobj = {viewtype: viewtype};
        if (dataparams) {
            if (typeof dataparams === "object") {
                if (isParams) {
                    dataparams = {};
                }
                dataparams = jQuery.param(dataparams);
            }
        } else {
            dataparams = "";
        }
        if (options.justprint == true) {
            urlpobj.justprint = "true";
        } else {
            urlpobj.justprint = "false";
        }
        urlpobj.rndcache = Math.random();

        if (cmdparams) {
            if (typeof dataparams === "object") {
                dataparams = jQuery.extend({}, urlpobj, dataparams);
            }
        }

        var urlps = jQuery.param(urlpobj);
        requesturl = requesturl + ( /\?/.test(requesturl) ? "&" : "?" ) + urlps;

        options.reporturl = requesturl;
        options.reporturlparams = dataparams;
        options.requesturl = requesturl + "&" + dataparams;
    };
    parseAjaxHtmlPrintUrl = function (options) {
        var url = options.url || "";
        var isParams = options.parseNotJoinUrlParam || false;
        var dataparams = options.urlparam;
        var cmdparams = options.urlcmdparam;
        var webpath = options.webpath;
        var requesturl = "";
        if (webpath.length > 0) {
            if (webpath.lastIndexOf("/") != (webpath.length - 1)) {
                webpath = webpath + "/";
            }
        } else {
            webpath = "/";
        }
        options.webpath = webpath;
        if (url.length > 0) {
            if (url.indexOf("/") == 0) {
                url = url.substring(1);
            }
        }
        requesturl = webpath + url;
        var viewtype = "ajaxhtml";
        var urlpobj = {viewtype: viewtype};
        if (dataparams) {
            if (typeof dataparams === "object") {
                if (isParams) {
                    dataparams = {};
                }
                dataparams = jQuery.param(dataparams);
            }
        } else {
            dataparams = "";
        }
        if (options.justprint == true) {
            urlpobj.justprint = "true";
        } else {
            urlpobj.justprint = "false";
        }
        urlpobj.rndcache = Math.random();
        urlpobj.printapp = "lodop";

        if (cmdparams) {
            if (typeof cmdparams === "object") {
                urlpobj = jQuery.extend({}, urlpobj, cmdparams);
            }
        }

        var urlps = jQuery.param(urlpobj);
        requesturl = requesturl + ( /\?/.test(requesturl) ? "&" : "?" ) + urlps;

        options.reporturl = requesturl;
        options.reporturlparams = dataparams;
    };
    parseRequestUrl = function (options, url) {
        //弹出窗口url
        var url = url || "";
        var webpath = options.webpath;
        var requesturl = "";
        if (webpath.length > 0) {
            if (webpath.lastIndexOf("/") != (webpath.length - 1)) {
                webpath = webpath + "/";
            }
        } else {
            webpath = "/";
        }
        options.webpath = webpath;
        if (url.length > 0) {
            if (url.indexOf("/") == 0) {
                url = url.substring(1);
            }
        }
        requesturl = webpath + url;
        return requesturl;
    };


    createUUID = function (len, radix) {
        var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var chars = CHARS, uuid = [], i;
        radix = radix || chars.length;

        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
        } else {
            // rfc4122, version 4 form
            var r;

            // rfc4122 requires these characters
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';

            // Fill in random data.  At i==19 set the high bits of clock sequence as
            // per rfc4122, sec. 4.1.5
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }

        return uuid.join('');
    };
    getUtc = function () {
        var mm = top.$("div[id^='agreportprint-print-div']").size();
        var d = new Date();
        return Date.UTC(d.getFullYear(),
            d.getMonth(),
            d.getDate(),
            d.getHours(),
            d.getMinutes(),
            d.getSeconds(),
            d.getMilliseconds() + mm);
    };
    isArray = function (arr) {
        return typeof arr == "object" && arr.constructor == Array;
    };
    printDefaults = {
        width: 1,
        height: 1,
        title: '报表打印',
        url: '',
        urlcmdparam: '', /*url命令行参数*/
        urlparam: '', /*url数据参数*/
        checkLimitCountId: '', /*限制urlparam里打印单据数量的编号*/
        checkLimitCountNum: 50, /**/
        webpath: '',
        libtype: '',
        printapp: 'applet',
        showdebug: false,
        showprintdialog: true,
        initapplet: true,
        printmethodtype: '1',
        beforeHandle: function (data) {
        },
        afterHandle: function (data) {
        }
    };
    printViewdefaults = {
        width: $(window).width(),
        height: $(window).height(),
        popwidth: top.$(window).width(),
        popheight: top.$(window).height(),
        title: '报表打印预览',
        url: '',
        urlcmdparam: '', /*url命令行参数*/
        urlparam: '', /*url数据参数*/
        webpath: '',
        showdebug: true,
        viewtype: 'pdf'
    };
})(jQuery);

;if (typeof AgReportPrint == "undefined" || !AgReportPrint) {
    var AgReportPrint = {};
}
AgReportPrint.printToolType =function () {
    var type="clodopprint";
    var okarr=["clodopprint","1","appletprint","2","javaprint"];
    if(sysPrintToolTypeCache!=null && $.isPlainObject(sysPrintToolTypeCache)){
          type=sysPrintToolTypeCache.AgPrintToolType || "clodopprint";
    }
    type=$.trim(type);
    if($.inArray(type,okarr)<0){
        type="clodopprint";
    }
    if(type=="1"){
        type="clodopprint";
    }else if(type=="2"){
        type="appletprint";
    }
    return type;
};
AgReportPrint.printViewToolType  = function () {
    var type="clodopprintview";
    var okarr=["clodopprintview","1","pdfview","2","popview"];
    if(sysPrintToolTypeCache!=null && $.isPlainObject(sysPrintToolTypeCache)){
        type=sysPrintToolTypeCache.AgPrintViewToolType || "clodopprintview";
    }
    type=$.trim(type);
    if($.inArray(type,okarr)<0){
        type="clodopprintview";
    }
    if(type=="1"){
        type="clodopprintview";
    }else if(type=="2"){
        type="popview";
    }
    return type;
};
AgReportPrint.lodopPrintType = 'print';


/**
 * 根据编码获取打印分类代码
 */
AgReportPrint.getPrintTempletSubject = function (code) {
    var subjectJson = null;
    try {
        subjectJson = printTempletSubjectCache;
    } catch (ex) {

    }
    code = $.trim(code || "");
    var theSubject = null;
    if (subjectJson != null && code != "") {
        var subjectInfo = subjectJson[code];
        return subjectInfo;
    } else {
        return null;
    }
};
/**
 * 打印模板是否手动选择
 * @param code
 * @returns {boolean}
 */
AgReportPrint.isShowPrintTempletManualSelect = function (code) {
    var flag = false;
    var printSubject = AgReportPrint.getPrintTempletSubject(code);
    if (printSubject != null) {
        var uselinktype = printSubject["uselinktype"] || "";
        if (uselinktype == "2") {
            flag = true;
        }
    }
    return flag;
};
/**
 * 打印模板是否手动选择
 * @param code
 * @returns {boolean}
 */
AgReportPrint.isShowPrintTempletManualSelectByMuti = function (codearr) {
    if (codearr == null || !(codearr.constructor === Array) || codearr.length == 0) {
        return false;
    }
    var flag = false;
    for (var i = 0; i < codearr.length; i++) {
        var code = codearr[i];
        if (code == null) {
            continue;
        }
        var printSubject = AgReportPrint.getPrintTempletSubject(code);
        if (printSubject != null) {
            var uselinktype = printSubject["uselinktype"] || "";
            if (uselinktype == "2") {
                flag = true;
                break;
            }
        }
    }
    return flag;
};
/**
 * 打印模板选择项
 * @param renderTo select下拉框ID
 * @param code 模板代码
 * @param codereqparam {} 模板请求参数
 * @returns {boolean}
 */
AgReportPrint.createPrintTempletSelectOption=function(renderTo,code,codereqparam){
	renderTo= $.trim(renderTo || "");
	code = $.trim(code || "");
	if(renderTo=="" || code==""){
		return false;
	}
	
    if (codereqparam == null || !$.isPlainObject(codereqparam)) {
        codereqparam = {}
    }
	var options={
        renderTo:renderTo,
		code:code,
		codereqparam:codereqparam
	};
    AgReportPrint.createPrintTempletSelectOptionByMap(options);
};
/**
 * 打印模板选择项,参数使用键对
 * @param options {renderTo:renderTo,code:code,codereqparam:codereqparam}
 * @returns {boolean}
 */
AgReportPrint.createPrintTempletSelectOptionByMap=function(options){
    if(options==null || !(options.constructor === Object)){
        return false;
    }
    if(options.renderTo==null){
        return false;
    }
    var renderTo= $.trim(options.renderTo || "");
    var code = $.trim(options.code || "");
    if(renderTo=="" || code==""){
        return false;
    }

    renderTo= $.trim(renderTo || "");
    code = $.trim(code || "");
    if(renderTo=="" || code==""){
        return false;
    }
    var renderToId=renderTo;
    if(renderToId.indexOf("#")!=0){
        renderToId='#'+renderToId;
    }
    if($(renderToId).size()==0){
        return false;
    }
    
    var requestparam=options.codereqparam || {};
    if (!$.isPlainObject(requestparam) || typeof(requestparam)!="object") {
        requestparam = {};
    }
    requestparam.code=code;
    $(renderToId).html("");
    var isShowLoading=options.isShowLoading || "0";

    if("1"==isShowLoading || "true"==isShowLoading || true==isShowLoading){
    	isShowLoading="1";
	}
	if("1"==isShowLoading){
        loading("加载打印模板资源中...");
	}
    $.ajax({
        url :'agprint/tplmanage/showPrintTempletForSelect.do',
        type:'post',
        dataType:'json',
        data:requestparam,
        success:function(data){
            var htmlsb=new Array();
            if (data!=null && data.length>0) {
                $.each(data, function (i, item) {
                    htmlsb.push("<option value=\"" + item.id + "\">");
                    htmlsb.push(item.name);
                    htmlsb.push("</option>");
                });
            }else{
                htmlsb.push("<option value=\"NODATA\">");
                htmlsb.push("请选择　　");
                htmlsb.push("</option>");
            }
            $(renderToId).html(htmlsb.join(""));
            if("1"==isShowLoading){
            	loaded();
            }
        },
        error:function(){
            $(renderToId).html("");
            if("1"==isShowLoading){
                loaded();
            }
        }
    });
};
/**
 * 打印模板手动选择html代码
 * @param renderTo 模板隐藏div
 * @param dialogid 对话框div ID
 * @param code 模板代码
 * @param templetid 模板编号
 * @param codereqparam 模板请求参数
 */
AgReportPrint.createPrintTempletManualSelectDialogHtml = function (renderTo, dialogid, code, templetid) {
    var options={
        renderTo:renderTo,
        dialogid:dialogid,
        code:code,
        templetid:templetid
    };
    AgReportPrint.createPrintTempletManualSelectDialogHtmlByMap(options);
};

/**
 * 打印模板手动选择html代码
 * @param options 参数
 * {
 *	renderTo:'包含dialog的div', //必填写
 *	dialogid:'dialog所在div', //必填写
 *	code:'模板代码' //必填写
 *  templetid:'' //模板下接div 后缀id，默认templetid,如果要取 dialogid + "-form-" + templetid
 *  codereqparam:{} //模板下接请求参数
 * }
 */
AgReportPrint.createPrintTempletManualSelectDialogHtmlByMap = function (options) {
    var renderTo = $.trim(options.renderTo || "");
    var dialogid = $.trim(options.dialogid || "");
    var code = $.trim(options.code || "");
    if (renderTo == "" || code == "") {
        return false;
    }
    var templetid = options.templetid || "";
    templetid = $.trim(templetid);
    if ( templetid == "") {
        templetid = "templetid";
    }
    var templetiddivname = dialogid + "-form-" + templetid;
    var renderToId = renderTo;
    if (renderToId.indexOf("#") != 0) {
        renderToId = '#' + renderToId;
    }
    if ($(renderToId).size() == 0) {
        $("<div style=\"display:none\" id=\"" + renderToId.replace(/#/g, '') + "\"></div>").appendTo($("body"));
    }
    if ($("#" + dialogid).size() > 0) {
        var isok = true;
        try {
            $("#" + dialogid).dialog("destroy");
        } catch (e) {
            isok = false;
        }
        if (!isok) {
            try {
                $("#" + dialogid).window("destroy");
            } catch (e) {
                isok = false;
            }
        }
        try {
            $("#" + dialogid).remove();
        } catch (e) {

        }
    }
    var htmlsb = new Array();
    htmlsb.push("<div id=\"");
    htmlsb.push(dialogid);
    htmlsb.push("\">");
    htmlsb.push("<div style=\"margin:5px\">");
    htmlsb.push("<form action=\"\" id=\"");
    htmlsb.push(dialogid);
    htmlsb.push("-form\" method=\"post\">");
    htmlsb.push("<table>");
    htmlsb.push("<tr>");
    htmlsb.push("<td>打印模板：<\/td>");
    htmlsb.push("<td>");
    htmlsb.push("<select id=\"");
    htmlsb.push(templetiddivname);
    htmlsb.push("\" name=\"");
    htmlsb.push(templetid);
    htmlsb.push("\">");
    htmlsb.push("</select>");
    htmlsb.push("</td>");
    htmlsb.push("</tr>");
    htmlsb.push("</table>");
    htmlsb.push("</form>");
    htmlsb.push("</div>");
    $(renderToId).append(htmlsb.join(''));
    var codereqparam =options.codereqparam || {};
    AgReportPrint.createPrintTempletSelectOption(templetiddivname, code,codereqparam);
};

AgReportPrint.getObjectValue = function (options, key) {
    if (options == null
        || !(options.constructor === Object)
        || key == null
        || $.trim(key) == "") {
        return "";
    }
    key = $.trim(key);
    var value = options[key] || "";
    return $.trim(value);
};

/**
 * 打印模板手动选择html代码
 * @param options 参数
 * {
 *	renderTo:'包含dialog的div', //必填写
 *	dialogid:'dialog所在div', //必填写
 *	templetarr: //必填写
 *	[
 *		{
 *		code:'模板代码', //必填写
 *	    codereqparam:{}, //传递给后台请求
 *		labelname:'标签名称', //必填写
 *		templetidfieldname:'模板编号表单名称' //必填写
 *		}
 *	]
 * }
 */
AgReportPrint.createPrintTempletManualSelectDialogHtmlByMuti = function (options) {
    if (options == null || !(options.constructor === Object)) {
        return false;
    }
    if (options.renderTo == null) {
        return false;
    }
    var renderTo = $.trim(options.renderTo || "");
    if (renderTo == "") {
        return false;
    }
    var renderToId = renderTo;
    if (renderToId.indexOf("#") != 0) {
        renderToId = '#' + renderToId;
    }
    if ($(renderToId).size() == 0) {
        return false;
    }
    var dialogid = $.trim(options.dialogid || "");
    if (dialogid == "") {
        return false;
    }
    var templetArr = options.templetarr;
    if (options.templetarr == null || !(options.templetarr.constructor === Array) || options.templetarr.length == 0) {
        return false;
    }
    if ($("#" + dialogid).size() > 0) {
        $("#" + dialogid).remove();
    }
    var selectOptions = {};
    selectOptions.selectarr = new Array();
    selectOptions.codearr = new Array();
    var htmlsb = new Array();
    htmlsb.push("<div id=\"");
    htmlsb.push(dialogid);
    htmlsb.push("\">");
    htmlsb.push("<div style=\"margin:5px\">");
    htmlsb.push("<form action=\"\" id=\"");
    htmlsb.push(dialogid);
    htmlsb.push("-form\" method=\"post\">");
    htmlsb.push("<table>");
    for (var i = 0; i < templetArr.length; i++) {
        var tmpObj = templetArr[i];
        var code = AgReportPrint.getObjectValue(tmpObj, "code");
        if (code == "") {
            continue;
        }
        if (!AgReportPrint.isShowPrintTempletManualSelect(code)) {
            continue;
        }
        var labelname = AgReportPrint.getObjectValue(tmpObj, "labelname");
        if (labelname == "") {
            labelname = code;
        }
        var templetidfieldname = AgReportPrint.getObjectValue(tmpObj, "templetidfieldname");
        if (templetidfieldname == "") {
            templetidfieldname = "templetid";
        }
        templetidfieldname = $.trim(templetidfieldname);
        var selectdivid = dialogid + "-form-" + templetidfieldname;
        htmlsb.push("<tr>");
        htmlsb.push("<td>");
        htmlsb.push(labelname);
        htmlsb.push("：<\/td>");
        htmlsb.push("<td>");
        htmlsb.push("<select id=\"");
        htmlsb.push(selectdivid);
        htmlsb.push("\" name=\"");
        htmlsb.push(templetidfieldname);
        htmlsb.push("\">");
        htmlsb.push("</select>");
        htmlsb.push("</td>");
        htmlsb.push("</tr>");
        var selectItem = {};
        selectItem.code = code;
        selectItem.selectdivid = selectdivid;
        selectOptions.selectarr.push(selectItem);
        selectOptions.codearr.push(code);
    }
    htmlsb.push("</table>");
    htmlsb.push("</form>");
    htmlsb.push("</div>");
    $(renderToId).append(htmlsb.join(''));

    selectOptions.codereqparam =options.codereqparam || {};
    AgReportPrint.createPrintTempletSelectOptionByMuti(selectOptions);
};
/**
 * 打印模板选择项
 * @param options 参数
 * {
 *	codearr:'要显示的下拉框的模板代码,数组', //必填写
 *  codereqparam:{},传递后台请求
 *	selectarr: //必填写
 *	[
 *		{
 *		code:'模板代码', //必填写
 *		selectdivid:'下拉框DIV编号' //必填写
 *		}
 *	]
 * }
 */
AgReportPrint.createPrintTempletSelectOptionByMuti = function (options) {
    if (options == null || !(options.constructor === Object)) {
        return false;
    }
    var codearr = options.codearr || [];
    if (codearr.length == 0) {
        return false;
    }
    var selectArr = options.selectarr || [];
    if (selectArr.length == 0) {
        return false;
    }
    var codereqparam =options.codereqparam || {};
    var requestparam={};
    if (codereqparam && typeof codereqparam === "object") {
        requestparam = $.extend(requestparam, codereqparam);
    }
    requestparam.codearrs = codearr.join(",")
    $.ajax({
        url: 'agprint/tplmanage/showPrintTempletForSelect.do',
        type: 'post',
        dataType: 'json',
        data: requestparam,
        success: function (data) {
            for (var i = 0; i < selectArr.length; i++) {
                var itemObj = selectArr[i];
                if (itemObj == null) {
                    continue;
                }
                var code = itemObj.code || "";
                code = $.trim(code) || "";
                if (code == "") {
                    continue;
                }
                var renderToId = itemObj.selectdivid || "";
                renderToId = $.trim(renderToId);
                if (renderToId == "") {
                    continue;
                }
                if (renderToId.indexOf("#") != 0) {
                    renderToId = '#' + renderToId;
                }
                if ($(renderToId).size() == 0) {
                    continue;
                }
                var htmlsb = [];
                if (data) {
                    $.each(data, function (i, item) {
                        if (item.code == code) {
                            htmlsb.push("<option value=\"" + item.id + "\">");
                            htmlsb.push(item.name);
                            htmlsb.push("</option>");
                        }
                    });
                }
                $(renderToId).html(htmlsb.join(""));
            }
        },
        error: function () {
        }
    });
};

AgReportPrint.getRemotePrintPageData = function (url) {
    if (url == null || $.trim(url) == "") {
        return "";
    }
    var printdata = "";
    $.ajax({
        type: 'post',
        cache: false,
        url: url,
        dataType: 'html',
        async: false,
        success: function (data) {
            if (data != null) {
                printdata = $.trim(data);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });
    return printdata;
};

AgReportPrint.useSaleOrderPrintSplit=function(handleFunc){
        $.ajax({
            type: 'post',
            cache: false,
            url: 'sysParam/getSysParamInfoJsonByName.do',
            data: {name: 'saleOrderPrintSplitType'},
            dataType: 'json',
            success: function (json) {
                if (json != null && $.isPlainObject(json) && json.flag == true) {
                    var data = json.data || {};
                    var pval = data.pvalue || 0;
					if(pval==1){
                        if(handleFunc!=null && typeof(handleFunc) == "function" ){
                            try {
                                handleFunc();
                            }catch(e){

                            }
                        }
					}
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                pval = 0;
            }
        });
};

/**
 *
 * @param option
 * 感叹号表示必填项,其他可选填
 * !id:弹出窗口ID 如果弹出层页面(自定义弹出层内容)已存在则跳过创建.自定义时,form表单id为 弹窗id+"-form"
 * !code:对应的打印模板类型
 *      string 时 journalsheet_matreimburse
 *      object 时 {codename:"类型名", labelname:"字段说明", templetid: "对应提交时的name属性" }
 *      array  时 为 object类型的集合
 * codereqparam:下拉框请求参数 {mark:'标识',markempty:'为空',marknotin:'除了这些外，以英文逗号分隔'}
 * tableId:对应页面的表格ID (例:普通列表页通过选择表格记录打印,报表页通过提交查询条件来打印),如果不是通过字段id进行传递,则重写getData,不要填写tableId
 * queryForm:查询表单ID (例:报表页通过提交查询条件来打印)
 * webpath:根目录,默认取当前域名
 * !url_preview: 预览提交地址
 * !url_print: 打印提交地址
 * width: 弹出层宽度
 * height: 弹出层高度
 * !btnPreview: 预览按钮ID
 * !btnPrint: 打印按钮ID
 * printlimit: 打印次数限制,设置为1时,有打印过的单据将不能打印,如果需要动态设置,则重写GetData方法,对option参数进行修改
 * exPrintParam: 打印表单提交时的扩展参数
 * single: 是否单条记录打印,默认false多条记录
 * getData: 返回要打印的数据,中止返回false,例如 未审核的数据不让打印等在此处进行判断
 */
AgReportPrint.init = function (option) {
    var webpath=null;
    if(typeof erp_base_project_urlpath != "undefined"){
        webpath=erp_base_project_urlpath;
    }else if(typeof project_webrequest_path != "undefined"){
        webpath = project_webrequest_path;
    }
    var defaultOpt = {
        id: "dialogid",
        code: null,
        codereqparam:{},
        tableId: "",
        queryForm: "",
        webpath: webpath,
        url_preview: "",
        url_print: "",
        width: 350,
        height: 180,
        btnPreview: "",
        btnPrint: "",
        printlimit: "0",
        exPrintParam: {},
        libtype: "",
        printViewToolType:AgReportPrint.printViewToolType,
        printToolType:AgReportPrint.printToolType,
        single: false,
        getData: AgReportPrint.defaultGetData,
        onPrintSuccess: AgReportPrint.defaultOnPrintSuccess,
        printAfterHandler: AgReportPrint.defaultPrintAfterHandler
    };
    option = $.extend({}, defaultOpt, option);
    if (option.code == null) {
        $.messager.alert("错误", "请设置code参数");
        return;
    }
    if (!Array.isArray(option.code)) {
        if (typeof option.code === "string")
            option.code = [{codename: option.code, labelname: "打印模板", templetid: "templetid"}];
        else if (typeof option.code === "object")
            option.code = [option.code];
        else {
            $.messager.alert("错误", "无法识别打印模板代码参数");
            return;
        }
    }

    var $body = $("body");
    var $div = $("#" + option.id);
    //是否手动选择
    if (AgReportPrint.isShowPrintTempletManual(option.code)) {
        //弹出层已存在则跳过
        if ($div.length == 0) {
            var selectOptions = {selectarr: [], codearr: []};
            var html_dia = [];
            html_dia.push('<div style="display:none" id="' + option.id + '">');
            html_dia.push('<div style="margin:5px;">');
            html_dia.push('<form action="" id="' + option.id + '-form" method="post">');
            html_dia.push('<table>');
            for (var i = 0; i < option.code.length; i++) {
                var selectid = option.id + "-form-" + option.code[i].templetid;
                html_dia.push('<tr><td>' + option.code[i].labelname + '：</td>');
                html_dia.push('<td><select id="' + selectid + '" name="' + option.code[i].templetid + '"></select></td></tr>');
                selectOptions.codearr.push(option.code[i].codename);
                selectOptions.selectarr.push({code: option.code[i].codename, selectdivid: selectid});
            }
            html_dia.push('</table>');
            html_dia.push('</form>');
            html_dia.push('</div>');
            html_dia.push('</div>');
            $body.append(html_dia.join(""));
            selectOptions.codereqparam =option.codereqparam || {};
            AgReportPrint.createPrintTempletSelectOptionByMuti(selectOptions);
        }
    }
    if (option.btnPreview != "") {
        AgReportPrint.bindPreview(option);
    }
    if (option.btnPrint != "") {
        AgReportPrint.bindPrint(option);
    }
};

AgReportPrint.isShowPrintTempletManual = function (codeArray) {
    if (codeArray == null || !(codeArray.constructor === Array) || codeArray.length == 0) {
        return false;
    }
    var flag = false;
    for (var i = 0; i < codeArray.length; i++) {
        var code = codeArray[i];
        if (code == null) {
            continue;
        }
        var printSubject = AgReportPrint.getPrintTempletSubject(code.codename);
        if (printSubject != null) {
            var uselinktype = printSubject["uselinktype"] || "";
            if (uselinktype == "2") {
                flag = true;
                break;
            }
        }
    }
    return flag;
};

/**
 * 返回默认待打印的记录集合
 * @param tableId 调用init方法时传入的option.tableId
 * @param printParam 弹出层的form表单数据 + 调用init方法是传入的option.exPrintParam
 *        printParam.printIds 当缺少tableId又需要对打印次数进行判断时,将id集合赋值到该属性,进行后续的是否可重复打印的判断.
 * @param isPrint 是否为打印 true为打印,false为预览
 * @param option 方法的option数值,用于动态修改原赋值
 * @returns {*}
 */
AgReportPrint.defaultGetData = function (tableId, printParam, isPrint, option) {
    if (tableId != "") {
        var data = $("#" + tableId).datagrid("getChecked");
        if (data.length == 0) {
            $.messager.alert("错误", "请选择要打印的数据");
            return false;
        }
        return data;
    } else {
        return true;
    }
};
AgReportPrint.defaultPrintAfterHandler = function (option, printParam) {
};

AgReportPrint.bindPreview = function (option) {
    $("body").on("click", "#" + option.btnPreview, function () {
        var div_dialog = $("#" + option.id);
        if (AgReportPrint.isShowPrintTempletManual(option.code)) {
            div_dialog.dialog({
                title: '打印预览参数设置',
                width: option.width,
                height: option.height,
                closed: false,
                cache: false,
                modal: true,
                buttons: [
                    {
                        text: '打印预览',
                        iconCls: 'button-preview',
                        plain: true,
                        handler: function () {
                            div_dialog.dialog("close");
                            var printParam = $("#" + option.id + "-form").serializeJSON();
                            delete printParam.oldFromData;
                            printParam = $.extend({}, option.exPrintParam, printParam);
                            AgReportPrint.printPreviewHandle(option, printParam);
                        }
                    }
                ]
            });
            div_dialog.show().dialog("open");
        } else {
            AgReportPrint.printPreviewHandle(option, option.exPrintParam);
        }
    });
};
AgReportPrint.printPreviewHandle = function (option, printParam) {
    if (option.url_preview == "") {
        $.messager.alert("提示", "请配置打印预览地址");
        return;
    }
    var dataList = option.getData(option.tableId, printParam, false, option);
    if (dataList == false)
        return;
    if (option.tableId != "") {
        if (dataList.length == 0)
            return;
        if (option.single)
            printParam.id = dataList[0].id;
        else
            printParam.idarrs = dataList[0].id;
    }
    if (option.queryForm != "") {
        var queryJSON = $("#" + option.queryForm).serializeJSON();
        printParam = $.extend({}, printParam, queryJSON);
    }
    if(option.webpath==null) {
        $.messager.alert("错误", "erp_base_project_urlpath 路径参数为空");
        return;
    }
    var printViewToolType= option.printViewToolType;
    if(printViewToolType == null || printViewToolType ==""){
        printViewToolType = AgReportPrint.printViewToolType;
    }
    top.$.AgReportPrint(printViewToolType, {
        webpath: option.webpath,
        url: option.url_preview,
        libtype: option.libtype,
        urlparam: printParam
    });
};

AgReportPrint.bindPrint = function (option) {
    $("body").on("click", "#" + option.btnPrint, function () {
        var div_dialog = $("#" + option.id);
        if (AgReportPrint.isShowPrintTempletManual(option.code)) {
            div_dialog.dialog({
                title: '打印参数设置',
                width: option.width,
                height: option.height,
                closed: false,
                cache: false,
                modal: true,
                buttons: [
                    {
                        text: '直接打印',
                        iconCls: 'button-print',
                        plain: true,
                        handler: function () {
                            div_dialog.dialog("close");
                            var printParam = $("#" + option.id + "-form").serializeJSON();
                            delete printParam.oldFromData;
                            printParam.printmethodtype = 2;
                            AgReportPrint.printHandle(option, printParam);
                        }
                    },
                    {
                        text: '打印',
                        iconCls: 'button-print',
                        plain: true,
                        handler: function () {
                            div_dialog.dialog("close");
                            var printParam = $("#" + option.id + "-form").serializeJSON();
                            delete printParam.oldFromData;
                            AgReportPrint.printHandle(option, printParam);
                        }
                    }
                ]
            });
            div_dialog.show().dialog("open");
        } else {
            AgReportPrint.printHandle(option, {});
        }
    });
};
AgReportPrint.printHandle = function (option, printParam) {
    if (option.url_print == "") {
        $.messager.alert("错误", "请配置打印地址");
        return;
    }
    var dataList = option.getData(option.tableId, printParam, true, option);
    if (dataList == false)
        return;
    var ids = [];
    var printIds = [];
    if (option.tableId != "") {
        if (dataList.length == 0)
            return;
        for (var i = 0; i < dataList.length; i++) {
            ids.push(dataList[i].id);
            if (dataList[i].printtimes > 0)
                printIds.push(dataList[i].id);
        }
        if (option.single)
            printParam.id = ids[0];
        else
            printParam.idarrs = ids.join(",");
    } else {
        printIds = printParam.printIds || [];
    }
    if (option.queryForm != "") {
        var queryJSON = $("#" + option.queryForm).serializeJSON();
        printParam = $.extend({}, printParam, queryJSON);
    }
    if (printIds.length) {
        if (option.printlimit == "1") {
            $.messager.alert("错误", "抱歉，以下编号的单据已经打印过一次，不能进行再一次打印。<br/>单据编号:" + printIds.join(","));
            return false;
        }
        $.messager.confirm("确认", "以下编号的单据已经打印过了,是否还要继续打印？<br/>单据编号:" + printIds.join(","), function (r) {
            if (!r) return;
            AgReportPrint.printHandle2(option, printParam, dataList);
        });
    } else {
        AgReportPrint.printHandle2(option, printParam, dataList);
    }
};
AgReportPrint.printHandle2 = function (option, printParam, dataList) {
    printParam = $.extend({}, option.exPrintParam, printParam);

    if(option.webpath==null) {
        $.messager.alert("错误", "erp_base_project_urlpath 路径参数为空");
        return;
    }

    var printToolType= option.printToolType;
    if(printToolType == null || printToolType ==""){
        printToolType = AgReportPrint.printToolType;
    }
    top.$.AgReportPrint(printToolType, {
        webpath: option.webpath,
        url: option.url_print,
        urlparam: printParam,
        libtype: option.libtype,
        showprintdialog: true,
        printmethodtype: printParam.printmethodtype || "1",
        afterHandle: function () {
            option.printAfterHandler(option, printParam);
        }
    });
    option.onPrintSuccess(option);
};
AgReportPrint.defaultOnPrintSuccess = function (option) {
    if (option.tableId != "") {
        var $grid = $("#" + option.tableId);
        var dataList = $grid.datagrid("getChecked");
        for (var i = 0; i < dataList.length; i++) {
            if (dataList[i].printtimes && !isNaN(dataList[i].printtimes)) {
                dataList[i].printtimes = dataList[i].printtimes + 1;
            } else {
                dataList[i].printtimes = 1;
            }
            var rowIndex = $grid.datagrid('getRowIndex', dataList[i].id);
            $grid.datagrid('updateRow', {index: rowIndex, row: dataList[i]});
        }
    }
};
;if (typeof MyLodop == "undefined" || !MyLodop) {
	    var MyLodop = {};
}
MyLodop.CLODOP_CVERSION="2.0.6.8";
//====页面引用CLodop云打印必须的JS文件：====

MyLodop.CLodop_Server=[
	"http://localhost:8000/CLodopfuncs.js?priority=1",
	"http://localhost:18000/CLodopfuncs.js?priority=2"];


MyLodop.initMyLodopPrint=function(){
	//让其它电脑的浏览器通过本机打印（适用例子）：
	try {
		var lodopscript = null;
		var lodophead = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
		var rnd=Math.random()*100+1;
		for (var i = 0; i < MyLodop.CLodop_Server.length; i++) {
			lodopscript = document.createElement("script");
			lodopscript.src = MyLodop.CLodop_Server[i]+"&rnd="+rnd;
			lodophead.insertBefore(lodopscript, lodophead.firstChild);
		}
	}catch(e){

	}
}
MyLodop.checkExistsUrl=function(url){
	var flag=false;
	url= $.trim(url||"");
	if(url==""){
		return flag;
	}
	$.ajax({
		url: url,
		type: 'GET',
		async:false,
		complete: function(response){
			if(response.status == 200){
				flag=true;
			}else{
				flag=false;
			}
		}
	});
	return flag;
}

MyLodop.initMyLodopPrint();

//====获取LODOP对象的主过程：====
MyLodop.getLodop=function(oOBJECT,oEMBED){
    var LODOP=null;
    try{
        //var isIE = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
        try{ LODOP=getCLodop();} catch(err) {};
        if (!LODOP && document.readyState!=="complete") {alert("C-Lodop没准备好，请稍后再试！"); return false;};
        if (!LODOP) {
        	MyLodop.lodopShowDowload(false);
            return false;
        } else {
		   if (CLODOP.CVERSION<MyLodop.CLODOP_CVERSION) {
			   MyLodop.lodopShowDowload(true);
			   return false;
		   };
		   if (oEMBED && oEMBED.parentNode) oEMBED.parentNode.removeChild(oEMBED);
		   if (oOBJECT && oOBJECT.parentNode) oOBJECT.parentNode.removeChild(oOBJECT);
		};
        //===如下空白位置适合调用统一功能(如注册语句、语言选择等):===
		LODOP.SET_LICENSES("浙江三歆机电有限公司","C05479D5AB0D1F47BB77D05CD9F0A3A1","浙江三歆机电有限公司","C626394CEE5BCE435C8F6C77CBFCD71B");
		LODOP.SET_LICENSES("THIRD LICENSE","","Fende Software Co., Ltd.","370FCCD05838D7F08BFEDE1100D8100D");
        //===========================================================
        return LODOP;
    } catch(err) {
    	alert("getLodop出错:"+err);
    	return false;
    };
};

MyLodop.lodopShowDowload=function(forUpdate){
	if(top.$("#mylodop-download-dialog").size()>0){
		top.$("#mylodop-download-dialog").remove();
	}
	var cpuType=MyLodop.getCPUType();
	try{
		var htmlsb=new Array();
		htmlsb.push("<div style=\"marign:20px;padding:20px;\">");
		if(forUpdate){
			htmlsb.push("您当前的CLodop云打印控件需要更新：<br/>");
		}else {
			htmlsb.push("<b style='color:#f00'>1)</b>");
			htmlsb.push("如果您已经安装了CLodop云打印控件：<br/>");
			htmlsb.push("<div style=\"line-height:25px;\">");
			htmlsb.push("&nbsp;1)麻烦您确认一下CLodop云打印控件是否启动？<br/>");
			htmlsb.push("&nbsp;端口：8000(默认) 或 18000(默认) <br/>");
			htmlsb.push("</div>");
			htmlsb.push("<hr style=\"border: 1px dashed #ccc;\" />");
			htmlsb.push("<b style='color:#f00'>2)</b>");
			htmlsb.push("如果您还未安装CLodop云打印控件：<br/>");
		}
		htmlsb.push("<div style=\"line-height:25px;\">");
		//htmlsb.push("&nbsp;您的系统是<b style=\"color:#00f;\">"+cpuType+"</b>位系统");
		if(cpuType==64){
			//htmlsb.push("推荐使用&nbsp;<b style=\"color:#00f;\">下载2</b>");
		}
		htmlsb.push("<br/>");
		htmlsb.push("&nbsp;下载1：<a href=\"appdown/lodop/HBN_PRINT_WIN32NT_2.163.zip\" target=\"_blank\" style=\"text-decoration: underline;\">32位系统版</a>");
		htmlsb.push("<br/>");
		if(cpuType==64) {
			//htmlsb.push("&nbsp;下载2：<a href=\"appdown/lodop/HBN_PRINT_WIN64NT_2.163.zip\" target=\"_blank\" style=\"text-decoration: underline;\">64位系统版</a>");
			//htmlsb.push("<br/>");
		}
		htmlsb.push("安装成功后请“Ctrl+F5”强制刷新浏览器。<br/>");
		htmlsb.push("</div>");
		htmlsb.push("</div>");
		top.$("body").append("<div id=\"mylodop-download-dialog\"></div>");
		var $newElem=top.$("#mylodop-download-dialog");
		$newElem.dialog({
			title:'打印控件下载',
		    width: 310,
		    height: 280,
		    closed: true,
		    cache: false,
			content : htmlsb.join(''),
		    modal: true,
		    onClose:function(){
		    	$newElem.dialog("destroy");
		    }
		});
		$newElem.dialog("open");
	}catch(e){
		var htmlsb=new Array();
		htmlsb.push("<div id=\"mylodop-download-dialog\" style=\"position: absolute; z-index: 1100; display: block; top: 100px; border: 1px solid rgb(107, 151, 193); color: rgb(0, 0, 0); font-size: 13px; width: 388px; left: 517.5px;height: 280px; background: rgb(245, 245, 245);\">");
		htmlsb.push("<div style=\"font-style: normal; font-variant: normal; font-weight: bold; font-stretch: normal; font-size: 13px; font-family: Arial; line-height: 25px; height: 27px; text-indent: 5px; color: white; background: rgb(139, 172, 207);\">");
		htmlsb.push("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;打印控件下载");
		htmlsb.push("<button style=\"margin-left: 5px; position: absolute; height: 20px; line-height: 100px; width: 34px; left: 3px; border: 0px; top: 5px; background: url(&quot;image/c_favicon.ico&quot;) 0px 0px no-repeat scroll transparent;\"></button>");
		htmlsb.push("<button style=\"cursor:pointer;margin-right: 5px; position: absolute; height: 20px; line-height: 100px; width: 34px; right: 3px; border: 0px; top: 4px; background: url(&quot;image/c_winclose.png&quot;) 0px 0px no-repeat scroll transparent;\" onclick='javascript:top.$(\"#mylodop-download-dialog\").remove();'></button>");
		htmlsb.push("</div>");
		htmlsb.push("<div style=\"color: rgb(0, 0, 0); border: 0px; left: 0px; top: 0px; width: 386px; height: 230px; background: rgb(245, 245, 245);\">");
		htmlsb.push("<span style=\"position: absolute; width: 310px; left: 46px; top: 67px;\">");
		if(forUpdate){
			htmlsb.push("您当前的CLodop云打印控件需要更新：<br/>");
		}else {
			htmlsb.push("<b style='color:#f00'>1)</b>");
			htmlsb.push("如果您已经安装了CLodop云打印控件：<br/>");
			htmlsb.push("<div style=\"line-height:25px;\">");
			htmlsb.push("&nbsp;麻烦您确认一下CLodop云打印控件是否启动？<br/>");
			htmlsb.push("&nbsp;端口：8000(默认) 或 18000(默认) <br/>");
			htmlsb.push("</div>");
			htmlsb.push("<hr style=\"border: 1px dashed #ccc;\" />");
			htmlsb.push("<b style='color:#f00'>2)</b>");
			htmlsb.push("如果您还未安装CLodop云打印控件：<br/>");
		}
		htmlsb.push("<div style=\"line-height:25px;\">");
		//htmlsb.push("&nbsp;您的系统是<b style=\"color:#00f;\">"+cpuType+"</b>位系统");
		if(cpuType==64){
			//htmlsb.push("推荐使用&nbsp;<b style=\"color:#00f;\">下载2</b>");
		}
		htmlsb.push("<br/>");
		htmlsb.push("&nbsp;下载1：<a href=\"appdown/lodop/HBN_PRINT_WIN32NT_2.163.zip\" target=\"_blank\" style=\"text-decoration: underline;\">32位系统版</a>");
		htmlsb.push("<br/>");
        if(cpuType==64) {
            //htmlsb.push("&nbsp;下载2：<a href=\"appdown/lodop/HBN_PRINT_WIN64NT_2.163.zip\" target=\"_blank\" style=\"text-decoration: underline;\">64位系统版</a>");
            //htmlsb.push("<br/>");
        }
		htmlsb.push("安装成功后请“Ctrl+F5”强制刷新浏览器。<br/>");
		htmlsb.push("</div>");
		htmlsb.push("</span>");
		htmlsb.push("</div>");
		htmlsb.push("</div>");
		top.$("body").append(htmlsb.join(""));
	}
}

MyLodop.lodopLoad=function(url) {
	if(url==null || url==""){
		return false;
	}
    $.ajax({
        type: "GET",
        cache: false,
        async:false,
        url: url,
        data: "",
        success: function() {
           return true;
        },
        error: function() {
           return false;
        }
    });
    return false;
}
MyLodop.getCPUType=function(){
	var agent=navigator.userAgent.toLowerCase();
	var type=32;
	try {
		if (agent.indexOf("win64") >= 0 || agent.indexOf("wow64") >= 0) {
			type=64;
		}
	}catch(e){

	}
	return type;

}
MyLodop.needCLodop=function (){
	try{
		var ua=navigator.userAgent;
		if (ua.match(/Windows\sPhone/i) !=null) return true;
		if (ua.match(/iPhone|iPod/i) != null) return true;
		if (ua.match(/Android/i) != null) return true;
		if (ua.match(/Edge\D?\d+/i) != null) return true;

		var verTrident=ua.match(/Trident\D?\d+/i);
		var verIE=ua.match(/MSIE\D?\d+/i);
		var verOPR=ua.match(/OPR\D?\d+/i);
		var verFF=ua.match(/Firefox\D?\d+/i);
		var x64=ua.match(/x64/i);
		if ((verTrident==null)&&(verIE==null)&&(x64!==null))
			return true; else
		if ( verFF !== null) {
			verFF = verFF[0].match(/\d+/);
			if ((verFF[0]>= 42)||(x64!==null)) return true;
		} else
		if ( verOPR !== null) {
			verOPR = verOPR[0].match(/\d+/);
			if ( verOPR[0] >= 32 ) return true;
		} else
		if ((verTrident==null)&&(verIE==null)) {
			var verChrome=ua.match(/Chrome\D?\d+/i);
			if ( verChrome !== null ) {
				verChrome = verChrome[0].match(/\d+/);
				if (verChrome[0]>=42) return true;
			};
		};
		return false;
	} catch(err) {return true;};
};
// function androidWidget(data){
// 	window.androidForWeb.showWidget(JSON.stringify(data));
// }
function androidCallBack(param,dataStr) {
	var data = $.parseJSON(dataStr);
	var param = $.parseJSON(param);
	if(null!=param.onSelect && param.onSelect!=""){
		window[param.onSelect](data); 
	}
	if(null!=param.onCheck && param.onCheck!=""){
		window[param.onCheck](data); 
	}
}
function androidDateWidget(date,format,funs){
    if(window.androidForWeb && window.androidForWeb.datePicker) {
        window.androidForWeb.datePicker(date,format,funs);
    }
}
function backMain(){
	if(window.androidForWeb && window.androidForWeb.backMain) {
        window.androidForWeb.backMain();
	}
}
function alertMsg(msg){
    if(window.androidForWeb && window.androidForWeb.alertMsg) {
        window.androidForWeb.alertMsg(msg);
    } else {
    	alert(msg);
	}
}
function androidLoading(){
    if(window.androidForWeb && window.androidForWeb.showLoading) {
        window.androidForWeb.showLoading();
    } else {
    	loading();
	}
}
function androidLoaded(){
    if(window.androidForWeb && window.androidForWeb.hideLoading) {
        window.androidForWeb.hideLoading();
    } else {
        loaded();
    }
}
function androidUpload(funs){
    window.androidForWeb.uploadFile(funs);
}

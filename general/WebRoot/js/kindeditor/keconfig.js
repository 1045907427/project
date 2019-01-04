
if (typeof KEditor == "undefined") {
	KEditor = {};
}

KEditor.keditor = {};
KEditor.kcssdata = 'div,p{font-size:16px;margin-bottom:15px;}';
KEditor.kuploadjson = 'common/commonUploadAction/kindEditorUpload.do';
KEditor.kditem = [
				'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
				'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
				'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
				'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
				'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
				'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 
				'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
				'anchor', 'link', 'unlink', '|', 'about']; 
KEditor.keoptions={
			allowPreviewEmoticons: false,
			allowFileManager: false,
			resizeType: 1};

KEditor.getOptions=function(options){
	options=options||{};
	jQuery.extend(KEditor.keoptions,options);
	return KEditor.keoptions;
}
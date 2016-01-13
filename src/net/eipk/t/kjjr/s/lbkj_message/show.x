<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="#{DocumentRoot}/eipk/lib/html5.js"></script>
<script type="text/javascript" src="#{DocumentRoot}/eipk/lib/respond.min.js"></script>
<script type="text/javascript" src="#{DocumentRoot}/eipk/lib/PIE_IE678.js"></script>
<![endif]-->
<link href="#{DocumentRoot}/web/css/style.css" rel="stylesheet" type="text/css" />
<link href="#{DocumentRoot}/web/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
<link href="#{DocumentRoot}/web/jq/uploadifive.css" rel="stylesheet" type="text/css">
<!--[if IE 6]>
<script type="text/javascript" src="#{DocumentRoot}/eipk/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<script src="#{DocumentRoot}/web/jq/jq.js" type="text/javascript"></script>
<script src="#{DocumentRoot}/web/jq/layer/layer.js" type="text/javascript"></script>
<script src="#{DocumentRoot}/web/jq/uploadifive.js" type="text/javascript"></script>
<title>#{AppName}</title>
<script>
$(function(){
	$('#file_upload').uploadifive({
	//'auto'			: false,
	'uploadScript'	: '#{DocumentRoot}/s/article/upload.x',
	'buttonText'	: '选择要上传的文件',
	'formData'		: {'article_id' : #{article_id}},
	'queueID'		: 'upload_div',
	'fileSizeLimit'	: '10MB',
	'multi'			: false,
	'fileType'		: '*/*',
	'width'			: 120,
	'onError'	: function(file, fileType, data) {
	},
	'onUpload'		: function(file) {
		//$('#file_upload').data('uploadifive').settings.formData = { 'par': 'test'};
	},
	'onUploadComplete' : function(file, data) {
		location.replace(location.href);
	}
	});
})
function del(id){
	$.ajax({
		  type: "POST",
		  url: "#{DocumentRoot}/s/file/del.x",
		  data: {
			  id:id
		  }
	}).done(function( result ) {
		var msg;
		if(result.success==1){
			msg='文件已删除';
		}
		else{
			msg='文件删除失败';
		}
		layer.alert(msg, {icon: 1, title:'系统提示'},function(index){
			location.replace(location.href);
		});
	});
}
</script>
</head>
<body>
<table style="width:100%;"><tr style="background-color:#f5f5f5">
<td class="tl">
<i class="iconfont">&#xe67f;</i> 首页 
<span>&gt;</span> 网站内容管理 <span>&gt;</span> 内容查阅
</td><td class="tr">
<a class="btn" href="#{DocumentRoot}/s/article/man.x">返回</a>
</td></tr>
</table>
<div class="clear"></div>
<table class="table_a" style="width:95%;">
<tr><td style="width:100px;">附件</td>
<td class="tl">
<div class="uploadifive-queue-item">
<br>
<input id="file_upload" name="file_upload" type="file"/>
</div>
<div id="upload_div"></div>
#Begin{file_list}
<div class="uploadifive-queue-item">
<a class="btn" href="javascript:del(#{id})" style="float:right;">删除</a><br>
#{EipkSortId}、<a href="#{DocumentRoot}/s/file/down.x?id=#{id}">#{fileName}</a>(#{fileSize})
</div>
#End{file_list}
</td></tr>
<tr><td style="width:100px;">发布栏目</td>
<td class="tl">#{sectionName}</td></tr>
<tr><td style="width:100px;">发布部门</td>
<td class="tl">#{pubUnit}</td></tr>
<tr><td style="width:100px;">发布日期</td>
<td class="tl">#Date{pubDate,yyyy-MM-dd}</td></tr>
<tr><td style="width:100px;">文件号</td>
<td class="tl">#{docId}</td></tr>
<tr><td style="width:100px;">标题</td>
<td class="tl">#{title}</td></tr>
<tr><td style="width:100px;">正文</td>
<td class="tl">#{content}</td></tr>

</table>
</body>
</html>
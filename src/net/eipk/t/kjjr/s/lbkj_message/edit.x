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
<!--[if IE 6]>
<script type="text/javascript" src="#{DocumentRoot}/eipk/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<script src="#{DocumentRoot}/web/jq/jq.js"></script>
<script src="#{DocumentRoot}/web/jq/layer/layer.js" type="text/javascript"></script>
<script src="#{DocumentRoot}/ckeditor/ckeditor.js"></script>
<script src="#{DocumentRoot}/eipk/lib/My97DatePicker/WdatePicker.js"></script>
<title>#{AppName}</title>
<script>
function save(){
	if(checkForm()){
		$.ajax({
			  type: "POST",
			  url: "#{DocumentRoot}/s/article/edit.x",
			  data: { 
				  article_id:$("#article_id").val(),
				  section_id:$("#section_id").val(),
				  pub_unit:$("#pub_unit").val(),
				  pub_date:$("#pub_date").val(),
				  doc_id:$("#doc_id").val(),
				  title:$("#title").val(),
				  content:CKEDITOR.instances.content.getData()
			  }
		}).done(function( result ) {
			layer.alert('保存成功', {icon: 1, title:'系统提示'},function(index){
				location='#{DocumentRoot}/s/article/man.x';
			});			
		});
	}	
}
function checkForm(){
	if($("#section_id").val()=='0'){
		$("#sys_msg").text("栏目不能为空!");
		return false;
	}
	else if($("#pub_unit").val()==''){
		$("#sys_msg").text("发布来源不能为空!");
		return false;
	}
	else if($("#pub_date").val()==''){
		$("#sys_msg").text("发布日期不能为空!");
		return false;
	}
	else if($("#title").val()==''){
		$("#sys_msg").text("标题不能为空!");
		return false;
	}
	else{
		return true;
	}
}
</script>
</head>
<body>
<table style="width:100%;"><tr style="background-color:#f5f5f5"><td class="tl">
<i class="iconfont">&#xe67f;</i> 首页 
<span>&gt;</span> 网站内容管理<span>&gt;</span> 内容编辑
</td></tr>
</table>
<div class="clear"></div>
<table class="table_a" style="width:95%;">
<form id="formEntry">
<input type="hidden" id="article_id" value="#{id}">
<tr><td style="width:100px;"></td>
<td class="tl"><span id="sys_msg" style="color:red;"></span></td></tr>
<tr><td style="width:100px;"><span style="color:red;">*</span>发布栏目：</td>
<td class="tl">
<select id="section_id">
<option value="0">选择（...）</option>
#Begin{section_list}
<option value="#{section_id}">#{sectionName}</option>
#End{section_list}
</select>
<script>
$("#section_id").val(#{sectionId});
</script>
</td></tr>
<tr><td style="width:100px;"><span style="color:red;">*</span>内容来源：</td>
<td class="tl">
<input type="text" value="#{pubUnit}" placeholder="" id="pub_unit" style="width:150px;">
</td></tr>
<tr><td style="width:100px;"><span style="color:red;">*</span>发布日期：</td>
<td class="tl">
<input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="pub_date" style="width:120px;" value="#Date{pubDate,yyyy-MM-dd}" style="cursor:pointer;">
</td></tr>
<tr><td style="width:100px;">文件号：</td>
<td class="tl">
<input type="text" value="#{docId}" placeholder="" id="doc_id">
</td></tr>
<tr><td style="width:100px;"><span style="color:red;">*</span>标题：</td>
<td class="tl">
<input type="text" value="#{title}" placeholder="" id="title" style="width:400px;">
</td></tr>
<tr><td style="width:100px;">正文</td>
<td class="tl">
<textarea class="ckeditor" cols="80" name="content" id="content" rows="30">
#{content}
</textarea>
</td></tr>
<tr><td style="width:100px;"></td>
<td class="tl">
<input type="button" onClick="save();" value="保存">&nbsp;
<input type="reset" value="重置">&nbsp;
<input type="button" onClick="location='#{DocumentRoot}/s/article/man.x';" value="返回">
</td></tr>
</form>
</table>
</body>
</html>
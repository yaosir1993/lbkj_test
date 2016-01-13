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
<script src="#{DocumentRoot}/web/jq/jq.js" type="text/javascript"></script>
<script src="#{DocumentRoot}/web/jq/layer/layer.js" type="text/javascript"></script>
<title>#{AppName}</title>
<script>
function save(){
	if(checkForm()){
		$.ajax({
			  type: "POST",
			  url: "#{DocumentRoot}/s/user/u_password.x",
			  data: { 
				  password:$("#password").val(),
				  repassword:$("#repassword").val()
			  }
		}).done(function( result ) {
			layer.alert(result.msg, {icon: 1, title:'系统提示'},function(index){
				location.replace=location.href;
			});
		});
	}	
}
function checkForm(){
	if($("#password").val()==''||$("#repassword").val()==''){
		$("#sys_msg").text("密码不能为空!");
		return false;
	}
	else if($("#password").val()!=$("#repassword").val()){
		$("#sys_msg").text("两次输入密码不一致!");
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
<span>&gt;</span> 系统操作 <span>&gt;</span> 修改密码
</td></tr>
</table>
<div class="clear"></div>
<table class="table_a" style="width:95%;">
<form id="formEntry">
<tr>
<td></td>
<td class="tl"><span id="sys_msg" style="color:red;"></span></td>
</tr>
<tr>
<td><span style="color:red">*</span> 新密码：</td>
<td class="tl"><input type="password" id="password" size="30" value=""/></td>
</tr>
<tr>
<td><span style="color:red">*</span> 请再输一次新密码：</td>
<td class="tl"><input type="password" id="repassword" size="30" value=""/></td>
</tr>
<tr>
<td></td>
<td class="tl">
<input type="button" onClick="save();" value="更新">
</td>
</tr>
</form>
</table>
</body>
</html>
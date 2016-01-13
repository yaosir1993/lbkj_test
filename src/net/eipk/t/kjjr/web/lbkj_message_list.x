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
<title>#{AppName}</title>
</head>
<body>
<table style="width:98%">
	#Begin{message_list}
		<tr>
		<td class="tl" style="font-size:15px"> #{mtitle}</td>
		<td rowspan="2" style="width:95px;"><a href="#{DocumentRoot}/web/lbkj_message_show.x?mid=#{mid}" class="btn"><i class="iconfont">&#xe623;</i> 查看详情</a></td>
		</tr>
		<tr>
		<td class="tl" style="width:100%;color:#ccc" colspan="2"> 发布日期：#Date{insertdate,yyyy-MM-dd}　阅读次数：#{browse}</td>
		</tr>		
		<tr><td colspan="2"><hr style="border:1px dotted #8B0000;"></td></tr>
	#End{message_list}	
</table>
<table style="width:98%">
<tr>
<td class="tr">#{split_page_str}</td>
</tr>
</table>
</body>
</html>
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
<title>#{AppName}</title>
</head>
<body>
<table style="width:98%">
<tr><td style="font-size:16px">#{mtitle}</td></tr>
<tr><td style="font-size:14px;color:#ccc">发布日期:#Date{insertdate,yyyy-MM-dd}　阅读次数:#{browse}</td></tr>
<tr>
<td class="tl">
#{mtext}</td>
</tr>
<tr><td class="tl" style="font-weight:bold">文件标题</td></tr>
<tr><td class="tl">

<div class="uploadifive-queue-item">
<a href="#{DocumentRoot}/file.xwebdown?real=文件名">文件名</a>文件大小
</div>

</td></tr>
<tr><td class="tr"><a href="javascript:history.back();" class="btn"><i class="iconfont">&#xe678;</i> 返回</a></td></tr>
</table>
</body>
</html>
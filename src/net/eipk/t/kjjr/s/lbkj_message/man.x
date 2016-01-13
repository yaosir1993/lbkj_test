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
function search(){
	location.replace('#{DocumentRoot}/s/article/man.x?clear=1&search_section='+$("#section_id").val()+'&search_article_title='+$("#article_title").val());
}
</script>
</head>
<body>
<table style="width:100%;"><tr style="background-color:#f5f5f5"><td class="tl">
<i class="iconfont">&#xe67f;</i> 首页 
<span>&gt;</span> 网站内容管理 <span>&gt;</span> 发布管理
</td><td class="tr">
<a class="btn" href="javascript:location.replace(location.href);" title="刷新" >
<i class="iconfont">&#xe68f;</i>
</a>
</td></tr>
</table>
<div class="clear"></div>
<table style="width:95%;">
<tr>
<td class="tl">
<select id="section_id" class="select">
	<option value="0">选择栏目（全部）</option>
#Begin{section_list}
<option value="#{id}">#{sectionName}</option>
#End{section_list}
</select>
<input type="text" id="article_title" placeholder="搜索标题内容" style="width:200px">
<input type="button" value="内容搜索" onclick="search();"/>
</td>
<td class="tr">
</td>
</tr>
<tr>
<td class="tl">
<a class="btn" href="#{DocumentRoot}/s/article/edit.x">
<i class="iconfont">&#xe610;</i> 新发布
</a>
</td>
<td class="tr">#{split_page_str}</td>
</tr>

</table>
<table class="table_a" style="width:95%">
<tr>
	<th>序号</th>
	<th>栏目</th>
	<th>标题</th>
	<th>发布日期</th>
	<th>浏览次数</th>
	<th>操作</th>
</tr>	
#Begin{article_list}
<tr>
	<td>#{EipkSortId}</td>
	<td>#{sectionName}</td>
	<td class="tl">
	<a href="#{DocumentRoot}/s/article/show.x?article_id=#{id}" title="查看详细">
	#{title}
	</a>
	</td>
	<td>#Date{pubDate,yyyy-MM-dd}</td>
	<td>#{hits}</td>
	<td>
	<a class="btn" href="#{DocumentRoot}/s/article/show.x?article_id=#{id}" title="查看详细">
		<i class="iconfont">&#xe616;</i> 查看
	</a>
	&nbsp;&nbsp;
	<a class="btn" href="#{DocumentRoot}/s/article/edit.x?article_id=#{id}" title="编辑">
		<i class="iconfont">&#xe6df;</i> 编辑
	</a>
	&nbsp;&nbsp;
	<a class="btn" href="javascript:article_del(this,#{id});" title="删除">
		<i class="iconfont">&#xe6e2;</i> 删除
	</a>
	</td>
</tr>
#End{article_list}
</table>
<table style="width:95%;">
<tr>
<td class="tr">#{split_page_str}</td>
</tr>
</table>

<script>
function article_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			  type: "POST",
			  url: "#{DocumentRoot}/s/article/del.x",
			  data: { 
				  article_id:id
			  }
		}).done(function( result ) {
			location.replace(location.href);
		});
	});
}
</script> 
</body>
</html>
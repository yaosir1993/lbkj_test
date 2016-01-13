<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<title>#{AppName}</title>
<!--[if lt IE 9]>
<script type="text/javascript" src="#{DocumentRoot}/eipk/lib/html5.js"></script>
<script type="text/javascript" src="#{DocumentRoot}/eipk/lib/respond.min.js"></script>
<script type="text/javascript" src="#{DocumentRoot}/eipk/lib/PIE_IE678.js"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="#{DocumentRoot}/eipk/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<link href="#{DocumentRoot}/web/iconfont/iconfont.css" rel="stylesheet" type="text/css" />
<link href="#{DocumentRoot}/web/css/style.css" rel="stylesheet" type="text/css" />
<script src="#{DocumentRoot}/web/jq/jq.js" type="text/javascript"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=SoikDnnys7nNpCicZRcnABrz" type="text/javascript"></script>
<script>
$(document).ready(function(){ 
	$(".show_layer").hide();
	$("##{current_layer}").show();
});
function show_layer(obj){
	$(".show_layer").hide();
	obj.fadeIn();
}
function reload_vcode_image(){
	$("#rand_image").attr("src","#{DocumentRoot}/eipk/image.jsp?"+Math.random());	
}
function login(){
	if(checkForm()){
		$.ajax({
			  type: "POST",
			  url: "#{DocumentRoot}/lbkj_login.x",
			  data: { username:$("#username").val(),password:$("#password").val(),check_code:$("#check_code").val() }
			}).done(function( result ) {
				if(result.id!=1){
					$("#sys_msg").text(result.msg);
				}
				else{
					location='#{DocumentRoot}/s/lbkj_main.x';
				}
		});
	}
}
function checkForm(){
	if($("#username").val()==''){
		$("#sys_msg").text("用户名不能为空!");
		return false;
	}
	else if($("#password").val()==''){
		$("#sys_msg").text("密码不能为空!");
		return false;
	}
	else if($("#check_code").val()==''){
		$("#sys_msg").text("验证码不能为空!");
		return false;
	}
	else{
		return true;
	}
}
</script>
</head>
<body>
<table style="width:100%;">
<tr style="height:100px;background:#4b8bc9;"><td>
	<table style="width:1280px;">
	<tr>
	<td style="width:100px;"><img src="#{DocumentRoot}/web/images/logo.png"></td>
	<td style="width:220px;font-size:18px;color:#333333;">
	灵宝科技信息管理平台<br>
	<hr style="height:2px;border:none;border-top:2px groove skyblue;">
	http://127.0.0.1:8080/www_lbkj_com/
	</td>
	<td style="text-align:right;width:960px;">
		<ul id="nav">
		    <li><a href="javascript:show_layer($('#layer6'));"><i class="iconfont">&#xe6a3;</i> 联系我们</a></li>
		    <li><a href="javascript:show_layer($('#layer5'));"><i class="iconfont">&#xe643;</i> 金融机构</a></li>
		    <li><a href="javascript:show_layer($('#layer4'));"><i class="iconfont">&#xe63e;</i> 科技金融政策</a></li>
		    <li><a href="javascript:show_layer($('#layer3'));"><i class="iconfont">&#xe63a;</i> 科技信贷</a></li>
		    <li><a href="javascript:show_layer($('#layer2'));"><i class="iconfont">&#xe616;</i> 工作动态</a></li>
		    <li><a href="javascript:show_layer($('#layer1'));"><i class="iconfont">&#xe625;</i> 平台简介</a></li>
		</ul>
	</td>
	</tr>
	</table>
</td></tr>

<tr style="height:800px;background:#d7edfc;"><td>
	<table style="width:1280px;">
	<tr>
	<td style="width:1020px;">
	<!-- 平台简介 -->
		<div id="layer1" class="show_layer">
			<table>
			<tr>
			<td style="width:820px;">
	<div class="title_bar">
		<table style="height:80px;">
		<tr style="height:50px;">
		<td class="tl" style="font-size:20px;width:390px;"><i class="iconfont">&#xe625;</i> 平台简介</td>
		<td class="tr" style="font-size:12px;color:#ccc;width:390px;">About Us <i class="iconfont">&#xe698;</i></td>
		</tr>
		<tr style="height:30px;"><td colspan="2" class="vt"><hr style="border:1px solid #ccc;"></td></tr>
		</table>
	</div>	
	<div class="div_scroll">
<table style="width:780px"><tr><td class="tl" style="line-height:180%">
　　河南省科技金融信息服务平台由河南省科学技术厅主办，河南省科研生产试验基地管理服务中心承办。<br>
　　河南省科技金融信息服务平台是针对科技企业不同发展阶段的融资需求和金融机构的业务需求，集政策、产品、信息服务等综合性金融服务于一体，发挥政府引导作用，整合银行、担保、保险、创投等金融资源，创新科技金融产品，为我省科技企业提供一站式、个性化、全方位的金融服务。<br>
　　平台的建设有利于科技资源和金融资源的有效对接，对构建具有区域特色的多元化、多层次、多渠道科技金融体系具有积极意义。

</td></tr></table>
</div>
	<div class="end_spacing">
	</div>
			</td>
			<td style="width:400px;">
			
				<img src="#{DocumentRoot}/web/images/banner.png" style="width:400px;height:630px;">
			</td>
			</tr>
			</table>
		</div>
		
		<!-- 工作动态 -->
		<div id="layer2" class="show_layer">
			<table>
			<tr>
			<td style="width:820px;">
	<div class="title_bar">
		<table style="height:80px;">
		<tr style="height:50px;">
		<td class="tl" style="font-size:20px;width:390px;"><i class="iconfont">&#xe616;</i> 工作动态</td>
		<td class="tr" style="font-size:12px;color:#ccc;width:390px;">Our Jobs <i class="iconfont">&#xe6bf;</i></td>
		</tr>
		<tr style="height:30px;"><td colspan="2" style="vertical-align:top;"><hr style="border:1px solid #ccc;"></td></tr>
		</table>
	</div>	
	<div id="gzdt_div" class="div_scroll" style="height:550px">
	<iframe name="article_frame" scrolling="auto" frameborder="0" src="#{DocumentRoot}/web/lbkj_message_list.x?cid=1" style="height:530px"></iframe>
	</div>	
			</td>
			<td style="width:400px;">
				<img src="#{DocumentRoot}/web/images/banner.png" style="width:400px;height:630px;">
			</td>
			</tr>
			</table>
		</div>
		
		<!-- 科技信贷 -->
		<div id="layer3" class="show_layer">
			<table>
			<tr>
			<td style="width:820px;">
	<div class="title_bar">
		<table style="height:80px;">
		<tr style="height:50px;">
		<td class="tl" style="font-size:20px;width:390px;"><i class="iconfont">&#xe63a;</i> 科技信贷</td>
		<td class="tr" style="font-size:12px;color:#ccc;width:390px;">ST Credit <i class="iconfont">&#xe6b5;</i></td>
		</tr>
		<tr style="height:30px;"><td colspan="2" class="vt"><hr style="border:1px solid #ccc;"></td></tr>
		</table>
	</div>	
	<div class="div_scroll">
科技信贷


	</div>
	<div class="end_spacing">
	</div>
			</td>
			<td style="width:400px;">
				<img src="#{DocumentRoot}/web/images/banner.png" style="width:400px;height:630px;">
			</td>
			</tr>
			</table>
		</div>
		
		<!-- 科技金融政策 -->
		<div id="layer4" class="show_layer">
			<table>
			<tr>
			<td style="width:820px;">
	<div class="title_bar">
		<table style="height:80px;">
		<tr style="height:50px;">
		<td class="tl" style="font-size:20px;width:390px;"><i class="iconfont">&#xe63e;</i> 科技金融政策</td>
		<td class="tr" style="font-size:12px;color:#ccc;width:390px;">ST & F Policy <i class="iconfont">&#xe626;</i></td>
		</tr>
		<tr style="height:30px;"><td colspan="2" class="vt"><hr style="border:1px solid #ccc;"></td></tr>
		</table>
	</div>	
	<div id="jrzc_div" class="div_scroll" style="height:550px">
	<iframe name="article_frame" scrolling="auto" frameborder="0" src="#{DocumentRoot}/web/lbkj_message_list.x?cid=2" style="height:530px"></iframe>
	</div>
	
			</td>
			<td style="width:400px;">
				<img src="#{DocumentRoot}/web/images/banner.png" style="width:400px;height:630px;">
			</td>
			</tr>
			</table>
		</div>		
		
		<!-- 金融机构 -->
		<div id="layer5" class="show_layer">
			<table>
			<tr>
			<td style="width:820px;">
	<div class="title_bar">
		<table style="height:80px;">
		<tr style="height:50px;">
		<td class="tl" style="font-size:20px;width:390px;"><i class="iconfont">&#xe643;</i> 金融机构</td>
		<td class="tr" style="font-size:12px;color:#ccc;width:390px;">Financial Institution <i class="iconfont">&#xe61c;</i></td>
		</tr>
		<tr style="height:30px;"><td colspan="2" class="vt"><hr style="border:1px solid #ccc;"></td></tr>
		</table>
	</div>	
	<div class="div_scroll">
金融机构


	</div>
	<div class="end_spacing">
	</div>
			</td>
			<td style="width:400px;">
				<img src="#{DocumentRoot}/web/images/banner.png" style="width:400px;height:630px;">
			</td>
			</tr>
			</table>
		</div>
		
		<!-- 联系我们 -->
		<div id="layer6" class="show_layer">
			<table>
			<tr>
			<td style="width:820px;">
	<div class="title_bar">
		<table style="height:80px;">
		<tr style="height:50px;">
		<td class="tl" style="font-size:20px;width:390px;"><i class="iconfont">&#xe6a3;</i> 联系我们</td>
		<td class="tr" style="font-size:12px;color:#ccc;width:390px;">Contact Us <i class="iconfont">&#xe6c7;</i></td>
		</tr>
		<tr style="height:30px;"><td colspan="2" class="vt"><hr style="border:1px solid #ccc;"></td></tr>
		</table>
	</div>	
	<div class="div_scroll">
<table style="width:780px"><tr><td class="tl" style="line-height:180%">
主　　办：河南省科学技术厅<br>
承　　办：河南省科研生产试验基地管理服务中心<br>
地　　址：河南省郑州市金水区政六街32号<br>
电　　话：0371-65727058　　　电子邮箱：hnkjjr@126.com<br><br>
业务电话：0371-56013633　　　电子邮箱：hnkjjrfwpt@163.com
</td></tr></table>

	</div>
	<div class="end_spacing">
	</div>
			</td>
			<td style="width:400px;">
				<div id="lxwm" style="width:400px;height:630px"></div>
			</td>
			</tr>
			</table>
		</div>
	<!-- 主界面结束 -->
	
	<!-- 登录窗口-->
		<div id="login_layer" class="show_layer">
			<table>
			<tr>
			<td style="width:820px;">
	<div class="title_bar">
		<table style="height:80px;">
		<tr style="height:50px;">
		<td class="tl" style="font-size:20px;width:390px;"><i class="iconfont">&#xe61d;</i> 系统登录</td>
		<td class="tr" style="font-size:12px;color:#ccc;width:390px;">Login Form <i class="iconfont">&#xe602;</i></td>
		</tr>
		<tr style="height:30px;"><td colspan="2" class="vt"><hr style="border:1px solid #ccc;"></td></tr>
		</table>
	</div>	
	<div class="div_scroll" style="text-align:center;">
	<table class="table_none">
	<form id="formEntry">
	<tr>
		<td style="width:50px;"></td>
		<td class="tl" style="width:480px;"><span id="sys_msg" style="color:red;"></span></td>
	</tr>
	<tr>
		<td><i class="iconfont">&#xe60d;</i></td>
		<td class="tl"><input id="username" type="text" placeholder="账户"></td>
	</tr>
	<tr>
		<td><i class="iconfont">&#xe60e;</i></td>
		<td class="tl"><input id="password" type="password" placeholder="密码"></td>
	</tr>
	<tr>
		<td></td>
		<td class="tl">
		<input id="check_code" type="text" placeholder="验证码">
        <a href="javascript:reload_vcode_image();" title="刷新验证码"><img id="rand_image" src="#{DocumentRoot}/eipk/image.jsp" align="absmiddle"/></a>
		</td>
	</tr>
	<tr>
		<td></td>
		<td class="tl">
		<a href="javascript:login();" class="btn"><i class="iconfont">&#xe603;</i>&nbsp;登&nbsp;录&nbsp;</a>
        <input type="reset" value="&nbsp;取&nbsp;&nbsp;消&nbsp;">
		</td>
	</tr>
	</form>
	</table>	

	</div>
	<div class="end_spacing">
	</div>
			</td>
			<td style="width:400px;">
				<img src="#{DocumentRoot}/web/images/banner.png" style="width:400px;height:630px;">
			</td>
			</tr>
			</table>
		</div>	
	</td>
	</tr>
	</table>
</td></tr>
<tr style="height:62px;background:#4b8bc9;"><td>
	<table style="width:1280px;"><tr><td class="tl" style="width:100%;color:white;font-size:14px;">
	Copyright&copy;2015河南省科技金融服务平台. All Rights Reserved 【<a href="javascript:show_layer($('#login_layer'));reload_vcode_image();">内部办公</a>】 
	系统版本：#{SysVersion}
	</td></tr></table>
</td></tr>
</table>
</body>
</html>
<script type="text/javascript">
var map = new BMap.Map("lxwm");
var point = new BMap.Point(113.691, 34.787);
var marker = new BMap.Marker(point);
map.addOverlay(marker);
map.centerAndZoom(point, 17);
var opts = {
  width : 260,
  height: 80,
  title : "河南省科研生产试验基地管理服务中心" ,
  enableMessage:true,
  message:""
}
var infoWindow = new BMap.InfoWindow("地址：河南省郑州市金水区政六街32号　电话：0371-65727058", opts);
marker.addEventListener("click", function(){          
	map.openInfoWindow(infoWindow,point);
});
</script>
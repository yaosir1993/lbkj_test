package net.eipk.kjjr.controller;

import net.eipk.core.SysUtil;
import net.eipk.core.mvc.Model;
import net.eipk.core.mvc.annotation.EipkController;
import net.eipk.core.mvc.annotation.EipkRequest;

@EipkController
public class Error {

	@EipkRequest("/err")
	public String showError(Model m){
		int err_id = SysUtil.StrToInt(m.getPar("id"));
		String msg="";
		String action="";
		switch(err_id){
		case 0:
			msg = "无错误提示信息。";
			break;
		case 1:
			msg = "要访问的页面或数据不存在，请确认您的操作正确。";
			action="<a href=\""+m.getAttr("DocumentRoot")+"\">返回系统首页</a>";
			break;
		case 2:
			msg = "输入的验证码不正确。";
			action="重新登录系统";
			break;
		case 3:
			msg = "用户名或密码不正确。";
			action="重新登录系统";
			break;
		case 4:
			msg = "未登录系统。";
			action="请登录系统再执行相关操作";
			break;
		case 5:
			msg = "非法操作。";
			action="请确认操作正确";
			break;
		}
		m.setAttr("msg", msg);
		m.setAttr("action", action);
		return "";
	}
}

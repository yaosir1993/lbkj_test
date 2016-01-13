package net.eipk.kjjr.controller;

import java.util.Map;

import net.eipk.core.mvc.Model;
import net.eipk.core.mvc.annotation.EipkController;
import net.eipk.core.mvc.annotation.EipkRequest;
import net.eipk.kjjr.util.Lbkj_RoleUtil;

@EipkController
public class Lbkj_Main {
	@SuppressWarnings("unchecked")
	@EipkRequest("/s/lbkj_main")
	public String showMainPage(Model m){

		Map<String,Object> user=(Map<String,Object>)m.getSessionPar("loginUser");

		m.setAttr("username", user.get("username"));
		m.setAttr("loginUserRole", Lbkj_RoleUtil.getRoleByUserId((Integer)user.get("uid")).get("username"));
		System.out.println("不知不觉的我来到了这里1！Control+main"+m.getAttr("loginUserRole"));
		System.out.println("不知不觉的我来到了这里2！Control+main"+m.getAttr("username"));
		return "";
	}
}

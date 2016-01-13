package net.eipk.kjjr.controller;

import java.util.Map;

import net.eipk.core.mvc.Model;
import net.eipk.core.mvc.annotation.EipkController;
import net.eipk.core.mvc.annotation.EipkRequest;

@EipkController
public class Welcome {
	@SuppressWarnings("unchecked")
	@EipkRequest("/s/welcome")
	public String Show(Model m){

		Map<String,Object> user=(Map<String,Object>) m.getSessionPar("loginUser");

		m.setAttr("lastLoginIp", user.get("lastLoginIp"));
		m.setAttr("lastLoginTime", user.get("lastLoginTime"));

		return "";
	}
}

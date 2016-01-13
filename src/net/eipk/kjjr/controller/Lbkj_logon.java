package net.eipk.kjjr.controller;

import java.util.HashMap;
import java.util.Map;

import net.eipk.core.jpa.Lbkj_DB;
import net.eipk.core.mvc.AjaxData;
import net.eipk.core.mvc.Model;
import net.eipk.core.mvc.RequestMethod;
import net.eipk.core.mvc.annotation.EipkController;
import net.eipk.core.mvc.annotation.EipkRequest;

@EipkController
public class Lbkj_logon {

	@EipkRequest(value="/lbkj_login",method=RequestMethod.POST,ajax=true)
	public AjaxData checkLogin(Model m){
		
		String rand_img_str = (String)m.getSessionPar("rand_img_str");
		Map<String,Object> result=new HashMap<String,Object>();
		System.out.println(rand_img_str+"我是session验证码");
		if(rand_img_str.equals(m.getPar("check_code"))){
			String sql="select * from h_user where username=? and password=?";
			Lbkj_DB db=new Lbkj_DB();
			Map<String,Object> user=db.querySingle(sql,m.getPar("username"),m.getPar("password"));
			if(user!=null){				
				m.setSessionPar("loginUser", user);
				//更新登录时间，ip地址变化，插入日志信息
				//sql="update h_user set lastLoginIp=?,lastLoginTime=? where userCode=?";
				//db.execute(sql, SysUtil.getIpAddr(m.getRequest()),SysUtil.getNowTime(),user.get("userCode").toString());
				//sql="insert into t_log (runner,event,run_time,ip) values (?,?,?,?)";
				//db.execute(sql, m.getPar("username"),"登录系统",SysUtil.getNowTime(),SysUtil.getIpAddr(m.getRequest()));
				
				result.put("id", 1);
				result.put("msg", "登陆成功。");				
			}
			else{
				result.put("id", -2);
				result.put("msg", "用户名或密码错误。");
			}
			db.close();
		}
		else{
			result.put("id", -1);
			result.put("msg", "验证码不正确。");
		}
		return new AjaxData(result);
	}
	
	@EipkRequest("/lbkj_logout")
	public String logout(Model m){
		m.removeSessionPar("loginUser");
		m.setAttr("error_msg", "");
		
		return "/web/index.x";
	}
	
	
}

package net.eipk.kjjr.controller;

import java.util.HashMap;
import java.util.Map;

import net.eipk.core.SysUtil;
import net.eipk.core.jpa.Lbkj_DB;
import net.eipk.core.mvc.AjaxData;
import net.eipk.core.mvc.Model;
import net.eipk.core.mvc.RequestMethod;
import net.eipk.core.mvc.annotation.EipkController;
import net.eipk.core.mvc.annotation.EipkRequest;

@EipkController
public class Lbkj_UserCtrl {
	
	
	@EipkRequest("/s/lbkj_user/u_password")
	public String editPassword(Model m){

		//
		return "";
	}
	
	//修改密码
	@SuppressWarnings("unchecked")
	@EipkRequest(value="/s/lbkj_user/u_password",method=RequestMethod.POST,ajax=true)
	public AjaxData editPasswordDo(Model m){
		Map<String,Object> result=new HashMap<String,Object>();
		
		String password=m.getPar("password");
		String repassword=m.getPar("repassword");
		
		if(password.equals(repassword)){
			Map<String,Object> user=(Map<String,Object>)m.getSessionPar("loginUser");
			Lbkj_DB db=new Lbkj_DB();
			String sql="update h_user set password=? where uid=?";
			db.execute(sql, password,user.get("uid"));			
			db.close();
			
			result.put("id", 1);
			result.put("msg", "修改成功。");
			//result.put("url", "");
		}
		else{
			result.put("id", 2);
			result.put("msg", "修改失败。");
		}
		
		return new AjaxData(result);
	}
	
	
	//查看用户
		@SuppressWarnings("unchecked")
		@EipkRequest(value="/s/lbkj_user/u_info",method=RequestMethod.POST,ajax=true)
		public String getuserinfoDo(Model m){
			
				Map<String,Object> user=(Map<String,Object>)m.getSessionPar("loginUser");
				Lbkj_DB db=new Lbkj_DB();
				String sql="select uid,uname from h_user where uid=?";
				Map<String,Object> getuser=db.querySingle(sql,user.get("uid"));
				db.execute(sql,user.get("uid"));			
				db.close();
				
				
				m.setAttr("user", getuser);
				m.setAttr("uid", user.get("uid"));
				
			return "";
		}
		
		//添加用户
				@EipkRequest(value="/s/lbkj_user/u_add",method=RequestMethod.POST,ajax=true)
				public AjaxData adduserDo(Model m){
					
					String username = m.getPar("username");
					String password = m.getPar("password");
					
					Lbkj_DB db=new Lbkj_DB();
						String sql="insert into values(?,?)";
						db.execute(sql,username,password);		
						db.close();
						
					return null;
				}
				
				//删除用户
			
				@EipkRequest(value="/s/lbkj_user/u_del",method=RequestMethod.POST,ajax=true)
				public String deluserDo(Model m){
					
					int uid=SysUtil.StrToInt(m.getPar("uid"));
					
					Lbkj_DB db=new Lbkj_DB();
						String sql="delete from h_user where uid=?";
						db.execute(sql,uid);			
						db.close();
						
						
					return null;
				}		
}

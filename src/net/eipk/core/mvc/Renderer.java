package net.eipk.core.mvc;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 1.初始化
 * 2.根据不同的视图请求（URL地址的分割部分）选择不同的模板进行渲染
 */
public class Renderer {
	static protected final Logger log = Logger.getLogger("Eipk");
	Model m;
	HttpServletRequest request;
	HttpServletResponse response;
	String view;

	public Renderer(HttpServletResponse response,HttpServletRequest request,String view) {
		this.request=request;
		this.response=response;
		this.m=new Model(request);
		m.setAttr("DocumentRoot", this.request.getContextPath());
		System.out.println(this.request.getContextPath()+"项目路径");
		m.setAttr("AppName", "灵宝科技");
		m.setAttr("lastLoginIp", "192.168.1.1");
		System.out.println(m.getAttr("AppName"));
		m.setAttr("SysVersion", "V0.1");
		if(view.equals("")){
			this.view=request.getRequestURI().substring(request.getContextPath().length()+1, request.getRequestURI().length()-2);
			System.out.println(this.view+"视图输出");
		}
		else{
			//如果视图请求为空字符串，则返回当前视图
			this.view=view;
		}
	}
	public void render() {
		try{
			//模板需要的是一个请求，把数据写入，输出封装好数据（model）的response
			Template t=new Template(request);
			t.getTemplate(view);
			t.write(m);
			t.out(response);
		}catch(IOException e){
			log.log(Level.INFO, "Eipk MVC Renderer failed.");
		}
	}
}
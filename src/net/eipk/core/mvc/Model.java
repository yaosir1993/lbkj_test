package net.eipk.core.mvc;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class Model {
	private HttpServletRequest request;
	public Model(){
	}
	public Model(HttpServletRequest request){
		this.request=request;		
	}
	public String getPar(String param){
		String rtn=request.getParameter(param);
		if(rtn==null){
			rtn="";
		}
		return rtn;
	}
	public Object getAttr(String param){
		return request.getAttribute(param);
	}
	public void setAttr(String param,Object value){
		if(value!=null)
			request.setAttribute(param, value);
		else
			request.setAttribute(param, "");
	}
	public Object getSessionPar(String param){
		return request.getSession().getAttribute(param);
	}
	public void setSessionPar(String param,Object value){
		request.getSession().setAttribute(param, value);
	}
	public void removeSessionPar(String param){
		request.getSession().removeAttribute(param);
	}

	public Enumeration<String> getAttrs(){
		return request.getAttributeNames();
	}
	public String getMethod(){
		return request.getMethod();
	}
	public void setQueryString(String queryString){
		request.setAttribute("QueryString", queryString);
	}

	public HttpServletRequest getRequest(){
		return this.request;
	}
}

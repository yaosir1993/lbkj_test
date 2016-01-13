package net.eipk.core.filter;

import java.io.*;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

@WebFilter("/s/*")
public class BLoginFilter implements Filter
{
	private FilterConfig filterConfig = null;
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		Map<String,Object> loginUser=(Map<String,Object>)req.getSession().getAttribute("loginUser");
		if(loginUser!=null){
			chain.doFilter(req, res);
		}
		else{
			res.sendRedirect(req.getContextPath()+"/err.x?id=4");
		}
	}
	@Override
	public void destroy() {
	}
	@Override
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
		if (filterConfig == null);
	}
	public void log(String msg)
	{
		this.filterConfig.getServletContext().log(msg);
	}
}
package net.eipk.core.filter;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

@WebFilter("/s/*")
public class AGZipFilter implements Filter
{
	private FilterConfig filterConfig = null;
	static protected final Logger log = Logger.getLogger("Eipk");
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
		HttpServletRequest req = (HttpServletRequest)request ;
        HttpServletResponse res = (HttpServletResponse)response ;
        GzipHttpServletResponse gzipResponse = new GzipHttpServletResponse(res) ;
        try {
        	if(req.getRequestURI().indexOf("xdownload")>0){
        		chain.doFilter(req, res) ;
        	}
        	else{
        		chain.doFilter(req, gzipResponse) ;
    	        ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
    	        GZIPOutputStream out = new GZIPOutputStream(baos) ;
    	        byte[] bs = gzipResponse.getOldBytes() ;
    	        if(bs.length==0){
    	        	log.log(Level.INFO, "net.eipk.core.filter.AJfGZipFilter.doFilter() :request 0 url:"+req.getRequestURI());
    	        }
    	        //log.log(Level.INFO, "net.eipk.core.filter.AJfGZipFilter.doFilter() :before gzip size:"+bs.length);
    	        out.write(bs) ;
    	        out.close() ;
    	        bs = baos.toByteArray() ;
    	        //log.log(Level.INFO, "net.eipk.core.filter.AJfGZipFilter.doFilter() :after gzip size:"+bs.length);
    	        res.setHeader("Content-Encoding", "gzip");
    	        res.setContentLength(bs.length) ;
    	        ServletOutputStream so = res.getOutputStream() ;
    	        so.write(bs) ;
    	        so.flush();
    	        so.close();
        	}
		} catch (ServletException | IOException e) {
			e.printStackTrace();
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
	public void log(String msg)	{
		this.filterConfig.getServletContext().log(msg);
	}
}
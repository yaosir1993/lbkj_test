package net.eipk.core.mvc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.eipk.core.jpa.Lbkj_DB;

@WebServlet("/s/file.xdownload")
public class DownloadDispather extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static protected final Logger log = Logger.getLogger("Eipk");

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String real_name=request.getParameter("real");
		Map<String,Object> file_info=new HashMap<String,Object>();
		Lbkj_DB db=new Lbkj_DB();		
		file_info=db.querySingle("select fileName,fileType from t_file where fileDiskName=?", real_name);
		db.close();
		String log_name=(String)file_info.get("fileName");		
		real_name=request.getServletContext().getRealPath("/upload/")+real_name+"."+file_info.get("fileType");
		response.reset();
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/x-download");
		log_name=URLEncoder.encode(log_name,"utf-8");
		response.addHeader("Content-Disposition","attachment;filename=\""+log_name+"\"");
		ServletOutputStream out = response.getOutputStream();
		InputStream inStream=new FileInputStream(real_name);
		byte[] b = new byte[1024];
		int len;
		while((len=inStream.read(b)) >0)
			out.write(b,0,len);
		response.setStatus( HttpServletResponse.SC_OK );
		response.flushBuffer();
		out.close();
		inStream.close();
	}
}

package net.eipk.kjjr.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.eipk.core.SysUtil;
import net.eipk.core.jpa.Lbkj_DB;
import net.eipk.core.mvc.AjaxData;
import net.eipk.core.mvc.Model;
import net.eipk.core.mvc.RequestMethod;
import net.eipk.core.mvc.annotation.EipkController;
import net.eipk.core.mvc.annotation.EipkRequest;

/**
 * @alter_data 2016/01/13 15:08
 */

@EipkController
public class Lbkj_Message {
	//管理员首页加载内容
	@EipkRequest("/s/lbkj_message/man")
	public String messageMan(Model m){

		String search_where="";
		if(SysUtil.StrToInt(m.getPar("clear"))==0){
			search_where=m.getSessionPar("search_where")!=null?(String)m.getSessionPar("search_where"):"";
		}
		else{
			int search_column=SysUtil.StrToInt(m.getPar("search_column"));
			String search_message_title=m.getPar("search_message_title");
			if(search_column!=0){
				search_where+=" and cid="+search_column;
			}
			if(!search_message_title.equals("")){
				search_where+=" and mtitle like '%"+search_message_title+"%'";
			}
		}
		m.setSessionPar("search_where", search_where);

		String sql="";

		Lbkj_DB db=new Lbkj_DB();

		sql="select mmid,title from h_message order by mmid";
		List<Map<String,Object>> column_list=db.query(sql);
		//查询指定栏目的内容
		sql="select count(*) as num from h_message m,h_column c where m.cid=c.cid "+search_where;
		int total=SysUtil.StrToInt(""+db.querySingle(sql).get("num"));
		int pagth=SysUtil.StrToInt(m.getPar("pagth"));
		int start=SysUtil.getSplitPageStartId(total, SysUtil.PAGE_SIZE, pagth);
		sql="select m.mid,m.mtitle,m.mtext,m.insertdate from h_message m,h_column c where m.cid=c.cid "+search_where+" order by insertdate desc limit ?,?";
		List<Map<String,Object>> message_list=db.query(sql,start,SysUtil.PAGE_SIZE);
		
		db.close();
		
		m.setAttr("split_page_str", SysUtil.getSplitPageStr(m.getRequest(), total,SysUtil.PAGE_SIZE,pagth));
		m.setAttr("column_list", column_list); 	//栏目列表
		m.setAttr("message_list", message_list);	//文章列表

		return "";
	}
	//获取指定栏目，单一文章详情
	@EipkRequest("/s/lbkj_message/show")
	public String messageShowGet(Model m){
		int message_mid=SysUtil.StrToInt(m.getPar("message_mid"));

		if(message_mid!=0){
			Lbkj_DB db=new Lbkj_DB();
			String sql="select m.mtitle,m.mtext,m.insertdate,c.cname from h_message m,h_column c where m.cid=c.cid  and m.mid=?";
			Map<String,Object> message=db.querySingle(sql,message_mid);

			/*sql="select f.mid,f.fileName,f.fileSize from t_appendix a,t_file f where a.filemid=f.mid and a.messagemid=? order by a.mid";
			List<Map<String,Object>> file_list=db.query(sql, message_mid);
			 */
			db.close();

			m.setAttr("message", message);
			/*m.setAttr("file_list", file_list);*/
		}
		else{
			m.setAttr("cname", "");
			m.setAttr("mtitle", "");
			m.setAttr("mtext", "");
			m.setAttr("insertdate", "");
				
		}
		m.setAttr("message_mid", message_mid);
		return "";
	}
	
	//指定栏目下的，对文章的内容获取（指定ID）
	@EipkRequest("/s/lbkj_message/edit")
	public String messageEditGet(Model m){
		System.out.println("在修改前需要获取详细信息！");
		int message_mid=SysUtil.StrToInt(m.getPar("message_mid"));
		Lbkj_DB db=new Lbkj_DB();
		String sql="select cid ,cname from h_column order by cid";
		List<Map<String,Object>> column_list=db.query(sql);
		if(message_mid>0){
			sql="select mid,mtitle,mtext,insertdate,cid from h_message where mid=?";
			Map<String,Object> message=db.querySingle(sql,message_mid);
			m.setAttr("message", message);
		}
		else{
			m.setAttr("mid", 0);
			m.setAttr("mtitle", "");
			m.setAttr("insertdate", new Date());
			m.setAttr("mtext", "");
			m.setAttr("cid", 0);
		}
		db.close();
		m.setAttr("column_list", column_list);
		return "";
	}
	
	//在网页上编辑（修改）文章----或者发布新的文章
	@SuppressWarnings("unchecked")
	@EipkRequest(value="/s/lbkj_message/edit",method=RequestMethod.POST,ajax=true)
	public AjaxData messageEditPost(Model m){
		
		
		Map<String,Object> user=(Map<String,Object>)m.getSessionPar("loginUser");
		
		int message_mid=SysUtil.StrToInt(m.getPar("message_mid"));
		int column_mid=SysUtil.StrToInt(m.getPar("column_mid"));

		String insertdate=m.getPar("insertdate");
		String mtitle=m.getPar("mtitle");
		String mtext=m.getPar("mtext");

		String sql="";
		Lbkj_DB db=new Lbkj_DB();
		if(message_mid==0){
			System.out.println("我要添加文章了"+user.get("mid"));
			sql="insert into h_message (mtitle,mtext,insertdate,cid,uid) values (?,?,?,?,?,?)";
			db.execute(sql, mtitle,mtext,insertdate,column_mid,(Integer)user.get("mid"));
		}
		else{
			System.out.println("我要修改文章了"+user.get("mid"));
			sql="update h_message set mtitle=?,insertdate=?,cid=? where mid=?";
			db.execute(sql, mtitle,mtext,insertdate,column_mid,message_mid);
		}
		db.close();

		return null;
	}
	
	//删除文章
	@EipkRequest(value="/s/lbkj_message/del",method=RequestMethod.POST,ajax=true)
	public AjaxData messageDelPost(Model m){

		int message_mid=SysUtil.StrToInt(m.getPar("message_mid"));
		String sql="delete from h_message where mid=?";
		Lbkj_DB db=new Lbkj_DB();
		db.execute(sql, message_mid);
		db.close();

		return null;
	}
	
	
	
	
	/************************************************未启用状态***************************************************/
	
	//上传新文件---未启用
	@EipkRequest(value="/s/lbkj_message/upload",method=RequestMethod.POST,ajax=true)
	public AjaxData messageUploadFile(Model m){
		Map<String,Object> result=new HashMap<String,Object>();
		//文件保存路径
		String savePath = m.getRequest().getServletContext().getRealPath("/upload/");
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
        	saveDirFile.mkdirs();
        }
        FileItemFactory fac = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fac);
        upload.setHeaderEncoding("utf-8");

		List<?> fileList = null;
        try {
        	fileList = upload.parseRequest(m.getRequest());
        } catch (FileUploadException e) {
        	e.printStackTrace();
        	return null;
        }
        Iterator<?> par_itr=fileList.iterator(),file_itr = fileList.iterator();
        while (par_itr.hasNext()) {
        	FileItem item = (FileItem) par_itr.next();
        	if (item.isFormField()) {
        		try {
	    			String par_name=item.getFieldName();
	    			BufferedReader br;
					br = new BufferedReader( new InputStreamReader(item.getInputStream()));
					if(par_name!=null){
	    				result.put(par_name, br.readLine());
	    			}
	    			br.close();
        		} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }

        while (file_itr.hasNext()) {
        	FileItem item = (FileItem) file_itr.next();
        	String fileName = item.getName();
        	if (!item.isFormField()) {
        		try {
        			int index=fileName.lastIndexOf("\\");
        			if(index!=-1) {
        				fileName=fileName.substring(index+1);
        			}
        			result.put("fileName", fileName);
        			result.put("fileType", fileName.substring(fileName.lastIndexOf(".")+1));
        			String file_disk_name=java.util.UUID.randomUUID().toString();
        			fileName=file_disk_name+fileName.substring(fileName.lastIndexOf("."));
        			File uploadedFile = new File(savePath, fileName);
        			item.write(uploadedFile);
        			result.put("fileSize", SysUtil.FileSizeStr(item.getSize()));
        			result.put("fileDiskName", file_disk_name);
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        	}
        }
        Lbkj_DB db=new Lbkj_DB();
        String sql="insert into t_file (fileName,fileDiskName,fileSize,fileType) values (?,?,?,?)";
        int file_mid=db.execute(sql, result.get("fileName"),result.get("fileDiskName"),result.get("fileSize"),result.get("fileType"));
        sql="insert into t_appendix (messagemid,filemid) values (?,?)";
        db.execute(sql, SysUtil.StrToInt(""+result.get("message_mid")),file_mid);
        db.close();

		return new AjaxData(result);
	}
}

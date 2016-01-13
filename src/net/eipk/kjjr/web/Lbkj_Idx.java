package net.eipk.kjjr.web;

import java.util.List;
import java.util.Map;

import net.eipk.core.SysUtil;
import net.eipk.core.jpa.Lbkj_DB;
import net.eipk.core.mvc.Model;
import net.eipk.core.mvc.annotation.EipkController;
import net.eipk.core.mvc.annotation.EipkRequest;

/**
 * @author 首页---内容展示---点不同的按钮会有不同的显示内容--在登录页面
 */

@EipkController
public class Lbkj_Idx {
	@EipkRequest("/web/lbkj_index")
	public String showIdx(Model m){
		System.out.println("我来了1");
		String current_layer=m.getPar("cl");
		if(current_layer==null||current_layer.equals("")){
			current_layer="layer1";
		}
		m.setAttr("current_layer", current_layer);

		return "";
	}
	
	@EipkRequest("/web/lbkj_message_list")
	public String article_list(Model m){
		System.out.println("我来了2");
		int cid=SysUtil.StrToInt(m.getPar("cid"));
		if(cid<1||cid>2){
			cid=1;
		}
		
		String sql="";
		Lbkj_DB db=new Lbkj_DB();
		sql="select count(*) as num from h_message where m.cid= ?";
		int total=SysUtil.StrToInt(""+db.querySingle(sql,cid).get("num"));
		int pagth=SysUtil.StrToInt(m.getPar("pagth"));
		int start=SysUtil.getSplitPageStartId(total, 5, pagth);
		sql="select mid,mtitle,mtext,insertdate from h_message where cid=? order by insertdate desc limit ?,?";
		List<Map<String,Object>> message_list=db.query(sql,cid,start,5);		
		db.close();
		
		m.setAttr("split_page_str", SysUtil.getSplitPageStr(m.getRequest(), total,5,pagth,"&cid="+cid));
		m.setAttr("message_list", message_list);

		return "";		
	}
	
	@EipkRequest("/web/lbkj_message_show")
	public String article_show(Model m){
		System.out.println("我来了3");
		int mid=SysUtil.StrToInt(m.getPar("mid"));

		if(mid!=0){
			Lbkj_DB db=new Lbkj_DB();
			String sql="select * from h_message where mid=?";
			Map<String,Object> message=db.querySingle(sql,mid);

			/*sql="select f.id,f.fileName,f.fileDiskName,f.fileSize from t_appendix a,t_file f where a.fileId=f.id and a.articleId=? order by a.id";
			List<Map<String,Object>> file_list=db.query(sql, a_id);*/

			db.close();

			m.setAttr("message", message);
			//m.setAttr("file_list", file_list);
			/*if(file_list!=null&&file_list.size()>0){
				m.setAttr("file_title", "【本文附件】");
			}*/
		}
		m.setAttr("mid", mid);
		
		return "";
	}
}

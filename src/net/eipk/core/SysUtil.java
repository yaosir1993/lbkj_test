package net.eipk.core;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.eipk.core.jpa.Lbkj_DB;


public class SysUtil{

	protected static final Logger log = Logger.getLogger("Eipk");
	public static final int PAGE_SIZE = 20;
	public static final String SYS_HAVE_OR_UNHAVE_OPTIONS[]={"无","有"};
	public static final String SYS_IS_OR_NOT_OPTIONS[]={"否","是"};

	public static String getNowDate(){
		String rtn="";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		rtn=sdf.format(dt);
		return rtn;
	}
	public static String getNowTime(){
		String rtn="";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		rtn=sdf.format(dt);
		return rtn;
	}
	public static String formatDate(Date dt,String format){
		String rtn="";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		rtn=sdf.format(dt);
		return rtn;
	}
	public static int StrToInt(String str){
		int rtn=0;
		try	{
			rtn=Integer.parseInt(str,10);
		}
		catch(NumberFormatException e){
			//log.log(Level.INFO, "net.eipk.core.SysUtil.StrToInt() failed:["+str+"],already set value to [0]");
		}
		return rtn;
	}
	public static double StrToDouble(String str){
		double rtn=0;
		try	{
			rtn=Double.parseDouble(str);
		}
		catch(Exception e){
			log.log(Level.INFO, "net.eipk.core.SysUtil.StrToDouble() failed:["+str+"]");
		}
		return rtn;
	}
	public static float StrToFloat(String str)	{
		float rtn=0;
		try	{
			rtn=Float.parseFloat(str);
		}
		catch(Exception e){
			log.log(Level.INFO, "net.eipk.core.SysUtil.StrToFloat() failed:["+str+"]");
		}
		return rtn;
	}
	public static String DateToStr(Date d){
		java.text.DateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(d);
	}

	public static int DoubleToInt(double d){
		try	{
			return Integer.parseInt(new java.text.DecimalFormat("0").format(d));
		}
		catch(Exception e){
			log.log(Level.INFO, "net.eipk.core.SysUtil.DoubleToInt() failed:["+d+"]");
			return 0;
		}
	}
	public static String FileSizeStr(long fileS) {//转换文件大小
       DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) +"GB";
        }
        return fileSizeString;
	}

	public static <T> String ObjToJson(T obj){
		String rtn="";
		ObjectMapper objMapper=new ObjectMapper();
		try{
			rtn=objMapper.writeValueAsString(obj);
		}catch(Exception e){
			log.log(Level.INFO, "net.eipk.core.SysUtil.ObjToJson() failed:["+obj+"]");
		}

		return rtn;
	}

	public static String getSelectStr(String options[],String values[],String selected){
		String rtn="";
		for(int i=0;i<options.length;i++)
			if(values[i].equals(selected))
				rtn+="<option value=\""+values[i]+"\" selected>"+options[i]+"</option>";
			else
				rtn+="<option value=\""+values[i]+"\">"+options[i]+"</option>";
		return rtn;
	}
	public static String getSelectStr(String options[],int values[],int selected){
		String rtn="";
		for(int i=0;i<options.length;i++)
			if(values[i]==selected)
				rtn+="<option value=\""+values[i]+"\" selected>"+options[i]+"</option>";
			else
				rtn+="<option value=\""+values[i]+"\">"+options[i]+"</option>";
		return rtn;
	}

	public static String getCollectValue(String[] result_values,String[] search_values,String search_value){
		String rtn="";
		for(int i=0;i<search_values.length;i++)	{
			if(search_values[i].equals(search_value)){
				rtn=result_values[i];
				break;
			}
			else
				continue;
		}
		return rtn;
	}
	public static String getCollectValue(String[] result_values,int[] search_values, int search_value) {
		String rtn="";
		for(int i=0;i<search_values.length;i++)	{
			if(search_values[i]==search_value){
				rtn=result_values[i];
				break;
			}
			else
				continue;
		}
		return rtn;
	}

	public static String getSplitPageStr(HttpServletRequest request,int total,int pagesize,int pagth){
		String rtn="";
		int pages=total/pagesize;
		if(total%pagesize!=0)
			pages=pages+1;
		if(total<=pagesize)
				pages=1;
		if(pagth==0)
			pagth=1;
		if(pagth>pages)
			pagth=pages;
		rtn+="共"+total+"条,分"+pages+"页,每页"+pagesize+"条,本页为第"+pagth+"页.";
		if(pagth>1)
			rtn+=" <a class=\"btn\" href=\""+request.getRequestURI()+"?pagth="+(pagth-1)+"\"><i class=\"iconfont\">&#xe69b;</i> 上一页</a> ";
		else
			rtn+="<span style=\"color:#cccccc;\">上一页</span> ";
		if(pagth<pages)
			rtn+=" <a class=\"btn\" href=\""+request.getRequestURI()+"?pagth="+(pagth+1)+"\"><i class=\"iconfont\">&#xe69a;</i> 下一页</a>";
		else
			rtn+="<span style=\"color:#cccccc;\">下一页</span> ";
		rtn+=" 跳转第<input id=\"jumppage\" type=\"text\" size=\"2\" name=\"jumppage\">页<input type=\"button\" onclick=\"location.href='"+request.getRequestURI()+"?pagth='+document.getElementById('jumppage').value;\" value=\"跳\">";
		return rtn;
	}
	public static String getSplitPageStr(HttpServletRequest request,int total,int pagesize,int pagth,String query_str){
		String rtn="";
		int pages=total/pagesize;
		if(total%pagesize!=0)
			pages=pages+1;
		if(total<=pagesize)
				pages=1;
		if(pagth==0)
			pagth=1;
		if(pagth>pages)
			pagth=pages;
		rtn+="共"+total+"条,分"+pages+"页,每页"+pagesize+"条,本页为第"+pagth+"页.";
		if(pagth>1)
			rtn+=" <a class=\"btn\" href=\""+request.getRequestURI()+"?pagth="+(pagth-1)+query_str+"\"><i class=\"iconfont\">&#xe69b;</i> 上一页</a> ";
		else
			rtn+="<span style=\"color:#cccccc;\">上一页</span> ";
		if(pagth<pages)
			rtn+=" <a class=\"btn\" href=\""+request.getRequestURI()+"?pagth="+(pagth+1)+query_str+"\"><i class=\"iconfont\">&#xe69a;</i> 下一页</a>";
		else
			rtn+="<span style=\"color:#cccccc;\">下一页</span> ";
		rtn+=" 跳转第<input id=\"jumppage\" type=\"text\" size=\"2\" name=\"jumppage\">页<input type=\"button\" onclick=\"location.href='"+request.getRequestURI()+"?pagth='+document.getElementById('jumppage').value+'"+query_str+"';\" value=\"跳\">";
		return rtn;
	}
	public static int getSplitPageStartId(int total,int pagesize,int pagth){
		int pages=total/pagesize;
		if(total%pagesize!=0)
			pages=pages+1;
		if(total<=pagesize)
				pages=1;
		if(pagth==0)
			pagth=1;
		if(pagth>pages)
			pagth=pages;
		return pagesize*(pagth-1);
	}
	public static String getIpAddr(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}
	public static void log(String runner,String event,String ip,String info){
		Lbkj_DB db=new Lbkj_DB();
		String sql="insert into t_log (runner,event,run_time,ip,info) values (?,?,?,?,?)";
		db.execute(sql, runner,event,getNowTime(),ip,info);
		db.close();
	}
}
//数据库封装类DB
//2015-10-26
//功能：数据库封装类V3.00
package net.eipk.core.jpa;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.eipk.core.SysUtil;

public class Lbkj_DB{
	protected static final Logger log = Logger.getLogger("Eipk");

	private String jndiName;
	private Connection tmpConn;
	private PreparedStatement tmpStmt=null;
	private ResultSet tmpRs=null;

	private InitialContext ctx=null;
	private DataSource ds=null;

	private String recent_sql="";

	public Lbkj_DB()	{
		this.jndiName="jdbc/lbkj";
		init();
	}

	private void init()	{
		try	{
			ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:/comp/env/"+jndiName);
			tmpConn=ds.getConnection();
		}
		catch(Exception e)	{
			log.log(Level.INFO, "net.eipk.core.jpa.DB.init() failed."+e.toString());
		}
	}
	private void addSql(String sql,Object... params)	{
		Clear();
		this.recent_sql=sql;
		//log.log(Level.INFO, "net.eipk.core.jpa.DB.addSql(sql):["+sql+"]");
		try{
			tmpStmt=tmpConn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			for (int i = 0;i < params.length ; i++ ){
				Object param = params[i];
				if (param instanceof Integer) {
					int v = ((Integer) param).intValue();
					tmpStmt.setInt(i + 1, v);
				} else if (param instanceof String) {
					String v = (String) param;
					tmpStmt.setString(i + 1, v);
				} else if (param instanceof Double) {
					double v = ((Double) param).doubleValue();
					tmpStmt.setDouble(i + 1, v);
				} else if (param instanceof Float) {
					float v = ((Float) param).floatValue();
					tmpStmt.setFloat(i + 1, v);
				} else if (param instanceof Long) {
					long v = ((Long) param).longValue();
					tmpStmt.setLong(i + 1, v);
				} else if (param instanceof Boolean) {
					boolean v = ((Boolean) param).booleanValue();
					tmpStmt.setBoolean(i + 1, v);
				} else if (param instanceof Date) {
					tmpStmt.setDate(i + 1, (Date) param);
				} else if (param instanceof Byte){
					Byte v = ((Byte) param).byteValue();
					tmpStmt.setByte(i + 1, v);
				} else if (param instanceof Timestamp){
					tmpStmt.setTimestamp(i + 1, (Timestamp) param);
				} else if (param instanceof Time){
					tmpStmt.setTime(i + 1, (Time) param);
				}
			}
		}catch(Exception e){
			log.log(Level.INFO, "net.eipk.core.jpa.DB.addSql(sql):["+sql+"]----"+e.toString());
		}
	}
	private void Clear()	{
		if(tmpRs!=null)	{
			try{
				tmpRs.close();
			}catch(Exception e){
				log.log(Level.INFO, "net.eipk.core.jpa.DB.Clear() failed."+e.toString());
			}
			finally	{
				tmpRs=null;
			}
		}
		if(tmpStmt!=null){
			try{
				tmpStmt.close();
			}catch(Exception e){
				log.log(Level.INFO, "net.eipk.core.jpa.DB.Clear():"+e.toString());
			}
			finally	{
				tmpStmt=null;
			}
		}
	}

	/**
	 * 平台数据库接口：事务支持
	 */
	public void setAutoCommit(boolean commitState){
		try{
			tmpConn.setAutoCommit(commitState);
		}catch(Exception e){}
	}
	public void commit(){
		try{
			tmpConn.commit();
		}catch(Exception e){}
	}
	public void rollback(){
		try{
			tmpConn.rollback();
		}catch(Exception e){}
	}

	public void absolute(int i){
		try{
			tmpRs.absolute(i);
		}catch(Exception e){}
	}
	public void last(){
		try{
			tmpRs.last();
		}catch(Exception e){}
	}
	public void first(){
		try{
			tmpRs.first();
		}
		catch(Exception e){}
	}
	public boolean next(){
		boolean rtn=false;
		try{
			if(tmpRs.next())
				rtn=true;
		}
		catch(Exception e){}
		return rtn;
	}
	public void close(){
		try{
			tmpRs.close();
		}
		catch(Exception e){}
		finally	{
			tmpRs=null;
		}

		try{
			tmpStmt.close();
		}
		catch(Exception e){}
		finally	{
			tmpStmt=null;
		}

		try{
			tmpConn.close();
		}
		catch(Exception e){}
		finally	{
			tmpConn=null;
		}
	}
	public String getString(String FieldName){
		String rtn="";
		try{
			rtn=tmpRs.getString(FieldName).trim();
		}catch(Exception e){}
		if(rtn==null)
			rtn="";
		return rtn;
	}
	public String getString(int i){
		String rtn="";
		try{
			rtn=tmpRs.getString(i).trim();
		}catch(Exception e){}
		if(rtn==null)
			rtn="";
		return rtn;
	}
	public int getInt(String FieldName){
		int rtn=0;
		try{
			rtn=tmpRs.getInt(FieldName);
		}catch(Exception e){}
		return rtn;
	}
	public int getInt(int i){
		int rtn=0;
		try{
			rtn=tmpRs.getInt(i);
		}catch(Exception e){}
		return rtn;
	}
	public long getLong(String FieldName){
		long rtn=0;
		try{
			rtn=tmpRs.getLong(FieldName);
		}catch(Exception e){}
		return rtn;
	}
	public long getLong(int i){
		long rtn=0;
		try{
			rtn=tmpRs.getLong(i);
		}catch(Exception e){}
		return rtn;
	}
	public byte getByte(String FieldName){
		byte rtn=0;
		try{
			rtn=tmpRs.getByte(FieldName);
		}catch(Exception e){}
		return rtn;
	}
	public byte getByte(int i){
		byte rtn=0;
		try{
			rtn=tmpRs.getByte(i);
		}catch(Exception e){}
		return rtn;
	}
	public float getFloat(String FieldName){
		float rtn=0;
		try{
			rtn=tmpRs.getFloat(FieldName);
		}catch(Exception e){}
		return rtn;
	}
	public float getFloat(int i){
		float rtn=0;
		try{
			rtn=tmpRs.getFloat(i);
		}catch(Exception e){}
		return rtn;
	}
	public double getDouble(String FieldName){
		double rtn=0;
		try{
			rtn=tmpRs.getDouble(FieldName);
		}catch(Exception e){}
		return rtn;
	}
	public double getDouble(int i){
		double rtn=0;
		try{
			rtn=tmpRs.getDouble(i);
		}catch(Exception e){}
		return rtn;
	}
	public java.sql.Date getDate(String FieldName){
		java.sql.Date rtn=null;
		try{
			rtn=tmpRs.getDate(FieldName);
		}catch(Exception e){}
		return rtn;
	}
	public java.sql.Date getDate(int i){
		java.sql.Date rtn=null;
		try{
			rtn=tmpRs.getDate(i);
		}catch(Exception e){}
		return rtn;
	}
	public java.util.Date getDateTime(String FieldName){
		java.util.Date rtn=null;
		long rtn_long=0;
		try{
				rtn_long=tmpRs.getLong(FieldName);
		}catch(Exception e){}

		rtn=new java.util.Date(rtn_long);

		return rtn;
	}
	public java.util.Date getDateTime(int i){
		java.util.Date rtn=null;
		long rtn_long=0;
		try{
			rtn_long=tmpRs.getLong(i);
		}catch(Exception e){}

		rtn=new java.util.Date(rtn_long);

		return rtn;
	}

	public Timestamp getTimestamp(String FieldName)
	{
		Timestamp rtn=null;
		try{
			rtn=tmpRs.getTimestamp(FieldName);
		}catch(Exception e){}
		return rtn;
	}
	public Timestamp getTimestamp(int i){
		Timestamp rtn=null;
		try{
			rtn=tmpRs.getTimestamp(i);
		}catch(Exception e){}
		return rtn;
	}

	public boolean getBoolean(String FieldName){
		boolean rtn=false;
		try{
			rtn=tmpRs.getBoolean(FieldName);
		}catch(Exception e){}
		return rtn;
	}
	public boolean getBoolean(int i){
		boolean rtn=false;
		try{
			rtn=tmpRs.getBoolean(i);
		}catch(Exception e){}
		return rtn;
	}
	public int execute(String sql,Object... params){
		addSql(sql,params);
		int i=0;
		try{
			tmpStmt.executeUpdate();
			tmpRs=tmpStmt.getGeneratedKeys();
			if(tmpRs.next()){
				i=tmpRs.getInt(1);
			}
		}catch(Exception e){
			i=-2;
			log.log(Level.INFO, "net.eipk.core.jpa.DB.execute():["+e.getMessage()+"],sql:"+this.recent_sql);
		}
		return i;
	}
	public List<Map<String,Object>> query(String sql,Object... params){
		addSql(sql,params);
		try{
			tmpRs=tmpStmt.executeQuery();
			int start=0;
			if(sql.indexOf("limit")>0&&params.length>1){
				start=(Integer)params[params.length-2];
			}
			List<Map<String,Object>> rs_map = new ArrayList<Map<String,Object>>();
	        ResultSetMetaData md = tmpRs.getMetaData();
	        int columnCount = md.getColumnCount();
	        while (tmpRs.next()) {
	            Map<String,Object> row = new HashMap<String,Object>();
	            for (int i = 1; i <= columnCount; i++) {
	            	//log.log(Level.INFO, "["+md.getColumnLabel(i)+"]:["+md.getColumnTypeName(i)+"]:["+tmpRs.getObject(i)+"]");
	                //row.put(md.getColumnName(i), tmpRs.getObject(i));
	            	row.put(md.getColumnLabel(i), tmpRs.getObject(i));
	            }
	            row.put("EipkSortId", tmpRs.getRow()+start);
	            rs_map.add(row);
	        }

	        return rs_map;
		}catch(Exception e){
			log.log(Level.INFO, "["+SysUtil.getNowTime()+"]net.eipk.core.jpa.DB.query():["+e.getMessage()+"],sql:"+this.recent_sql);
			return null;
		}
	}
	public ResultSet queryRs(String sql,Object... params){
		addSql(sql,params);
		try{
			tmpRs=tmpStmt.executeQuery();
	        return tmpRs;
		}catch(Exception e){
			log.log(Level.INFO, "["+SysUtil.getNowTime()+"]net.eipk.core.jpa.DB.query():["+e.getMessage()+"],sql:"+this.recent_sql);
			return null;
		}
	}
	public Map<String,Object> querySingle(String sql,Object... params){
		addSql(sql,params);
		try{
			tmpRs=tmpStmt.executeQuery();

			Map<String,Object> row = new HashMap<String,Object>();
	        ResultSetMetaData md = tmpRs.getMetaData();
	        int columnCount = md.getColumnCount();

	        if (tmpRs.next()) {
	            for (int i = 1; i <= columnCount; i++) {
	                row.put(md.getColumnLabel(i), tmpRs.getObject(i));
	            }
	        }
	        else{
	        	row=null;
	        }

	        return row;
		}catch(Exception e){
			log.log(Level.INFO, "["+SysUtil.getNowTime()+"]net.eipk.core.jpa.DB.query():["+e.getMessage()+"],sql:"+this.recent_sql);
			return null;
		}
	}
}
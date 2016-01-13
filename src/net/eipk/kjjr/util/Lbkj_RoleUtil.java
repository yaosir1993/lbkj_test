
package net.eipk.kjjr.util;

import java.util.Map;

import net.eipk.core.jpa.Lbkj_DB;


/**
 *
 * @author Administrator
 * 权限验证--根据角色id，查找是否有次用户，没有就不让进
 *
 */
public class Lbkj_RoleUtil {
	public static Map<String,Object> getRoleByUserId(int userId){
		System.out.println("我来过这里,这里是权限认证！");
		String sql="select uid,username,password where uid=?";
		Lbkj_DB db=new Lbkj_DB();
		Map<String,Object> rtn=db.querySingle(sql,userId);
		db.close();
		return rtn;
	}
}

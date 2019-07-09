package net.chinahrd.mvc.app;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.chinahrd.eis.permission.model.RbacUser;



/**
 * 保存APP登录用户的信息,后期可以考虑放在NoSQL里面
 * @author guanjian
 *
 */
public class AppUserMapping {
	private static Map<String, RbacUser> map = new HashMap<String, RbacUser>();
	
	public static RbacUser getUserByToken(String token){
		return map.get(token);
	}
	
	public static String addUserInMap(RbacUser user){
		UUID uuid = UUID.randomUUID();
		String token = uuid.toString().replaceAll("-", "");
		map.put(token, user);
		return token;
	}
	
	public static boolean tokenIsExist(String token){
		return map.containsKey(token);
	}

	public static void removeToken(String token){
		map.remove(token);
	}
}

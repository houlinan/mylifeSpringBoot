package cn.hgxsp.mylife.Utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;


public class MD5Utils {

	/**
	 * @Description: 对字符串进行md5加密
	 */
	public static String getMD5Str(String strValue)  {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			String newstr = Base64.encodeBase64String(md5.digest(strValue.getBytes()));
			return newstr;
		}catch (Exception e ){
			return null ;
		}

	}

	public static void main(String[] args) {
		try {
			String md5 = getMD5Str("imooc");
			System.out.println(md5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

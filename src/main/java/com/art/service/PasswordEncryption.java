package com.art.service;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.tomcat.util.codec.binary.Base64;

public class PasswordEncryption {
	public static String toSHA1(String password) {
		String salt = "hellodaylamahoa123123123@iaoufhaihoihaiovoiahoiheiuhaen";
		String result = null;
		password += salt;
		try {
			byte[] dataByte = password.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			result = Base64.encodeBase64String(md.digest(dataByte));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
	
//	public static void main(String[] args) {
//		System.out.println(toSHA1("123123a"));
//	}
	
//123123a:	pgPPTyq4xNL/lOXiLqlAA7/gWAA=
//123123:	UQ8ysuoNLexnVnGpx8MxSWCSNEw=
//123456:	0X+sjUIK6fG8ISJ+y1tBZm6tOis=
//123457:	BGL0ZzVdSOFZttQVm6Jga4405ms=
//123458:	m7kukF4ZPiJerdUHJVptvfgxz6Q=
}

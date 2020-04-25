package com.hbtangxun.boxuegu.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 工具类：MD5加密
 */
public class MD5Utils {

    public static String md5(String text){

        MessageDigest digest =null;

        try {
            digest = MessageDigest.getInstance("md5");
            byte[] bytes=digest.digest(text.getBytes());
            StringBuilder builder=new StringBuilder();

            for (byte b:bytes){
                int num=b & 0xff;
                String hexString = Integer.toHexString(num);
                if(hexString.length() ==1) {
                    builder.append("0"+hexString);
                }else {
                    builder.append(hexString);
                }
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

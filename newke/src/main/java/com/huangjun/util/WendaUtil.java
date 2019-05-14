package com.huangjun.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WendaUtil {
    private static final Logger logger = LoggerFactory.getLogger(WendaUtil.class);
    public static final int SYSTEM_USER_ID = 0; 
    
    
    
    public static String MD5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("生成MD5失败", e);
            return null;
        }
    }
    
    public static List<Integer> toListInteger(List<Object> list) {
		List<Integer> l = new ArrayList<Integer>();
		for(Object o : list) {
			l.add(Integer.parseInt(o.toString()));
		}
		return l;
	}
	
    public static List<Integer> setToList(Set<String> set){
		List<Integer> list = new ArrayList<Integer>();
		for(String s : set) {
			list.add(Integer.parseInt(s));
		}
		return list;
	}
    
    public static List<String> setToListString(Set<String> set){
		List<String> list = new ArrayList<String>();
		for(String s : set) {
			list.add(s);
		}
		return list;
	}
    
    
    
}

package com.huangjun.util;

public class HTMLFliterUtil {
	public static String  fliterHtml(String text) {
		return text.replace("<", "&lt;").replace(">", "&gt;");
	}
	
	
	
}

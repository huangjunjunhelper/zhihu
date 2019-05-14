package com.huangjun.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.util.StringUtils;

public class SensitiveWordFliterUtil {
	
	private class Node{
		private  boolean flag = false;
		private  Map<Character,Node> subNode = new HashMap<>();
		
		public Node getSubNode(Character c) {
			return subNode.get(c);
		}
		
		public void addSubNode(Character c,Node node) {
			subNode.put(c, node);
		}
		
		public void setFlag(boolean flag) {
			this.flag = flag;
		}
		
		public boolean getFlag() {
			return flag;
		}
	}
	
	private Node root = new Node();
	private static final String REPLACEMENT = "**";
	
	private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !Character.isLetterOrDigit(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }
	
	public void readFile() throws IOException {
		FileReader fr = new FileReader("sensitiveWord.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		while((line=br.readLine())!=null) {
			line = line.trim();
			if(!("".equals(line)))
				addWord(line.trim());
		}
	}
	
	private void addWord(String text) throws IOException {
		Node currentNode = root;
		for(int i=0;i<text.length();i++) {
			char c = text.charAt(i);
			Node nextNode = currentNode.getSubNode(c);
			if(nextNode==null) {
				Node newNode = new Node();
				currentNode.addSubNode(c,newNode);
				nextNode = newNode;
			}
				currentNode = nextNode;
			if(i==text.length()-1) {
				currentNode.setFlag(true);
			}
		}
	}
	
	public String fliterSensitiveWord(String text) {
		if(text==null||"".equals(text)) return "";
		if(StringUtils.isEmptyOrWhitespace(text)) return text;
		StringBuilder result = new StringBuilder();
		int i = 0;
		int j = 0;
		Node currentNode = root;
		while(i<=text.length()-1) {
			char c = text.charAt(j);
			if(isSymbol(c)) {
				if(i==j) {
					result.append(c);
					i++;
				}
				j++;
				continue;		
			}
			currentNode  = currentNode.getSubNode(c);
			if(currentNode==null) {
				i++;
				j = i;
				result.append(c);
				currentNode = root;
			}
			else if(currentNode.getFlag()){
				result.append(REPLACEMENT);
				j++;
				i = j;
				currentNode = root;
			}else {
				j++;
			}
			
		}
		return result.toString();
	}
	
	public static void main(String[] argv) throws IOException {
        SensitiveWordFliterUtil filter = new SensitiveWordFliterUtil();
        filter.readFile();
        String s = filter.fliterSensitiveWord("你好色色  &&&情,来赌博");
        System.out.println(s);
    }
}

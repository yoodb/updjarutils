package com.yoodb.blog;

import com.yoodb.blog.SignUtils;
import com.yoodb.blog.UpdJarContent;


public class Tests {
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String proName ="Resources";
		UpdJarContent ut = new UpdJarContent();
		SignUtils ss = new SignUtils();
		ut.updJarContent(ss,proName);
	}
}

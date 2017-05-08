package com.yoodb.blog;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Tests {
	
	private static Log log = LogFactory.getLog(Tests.class);
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String proName ="Resources";
		ArrayList<File> filelist = UpdJarContent.searchJarFiles("E://");
		for (File file : filelist) {
			UpdJarContent ut = new UpdJarContent();
			SignUtils ss = new SignUtils();
			String path = file.getAbsolutePath();
			ss.setJarPath(path);
			log.info("需要解压的压缩包路径-->" + path);
			String fileName = file.getName().replace(".jar", "");
			ss.setOriginalUrl(fileName);
			log.info("用于压缩包删除前缀目录的名称-->" + fileName);
			String filePath = path.replace(".jar", "");
			ss.setFilePath(filePath);
			log.info("解压后的文件夹目录路径-->" + filePath);
			String newFilePath = filePath.substring(0, filePath.length()-1) + "_1.jar";
			ss.setJarNewPath(newFilePath);
			log.info("新打包后的压缩包路径-->" + newFilePath);
			ut.updJarContent(ss,proName);
		}
	}
}

package com.yoodb.blog;

import java.io.File;
import java.io.FileOutputStream;
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
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String infoPath = "E:\\files\\";
		String logFilePath = "info.log";
		UpdJarContent.createFile(infoPath, logFilePath);
		FileOutputStream logfile = new FileOutputStream(infoPath + logFilePath);
		String proName ="Resources";
		ArrayList<File> filelist = UpdJarContent.searchJarFiles("E:\\wso2esb-5.0.1-SNAPSHOT\\repository\\components\\plugins");
		for (File file : filelist) {
			UpdJarContent ut = new UpdJarContent();
			SignUtils ss = new SignUtils();
			String path = file.getAbsolutePath();
			ss.setJarPath(path);
			logfile.write(("------" + path + "------").getBytes());
			logfile.write("\r\n".getBytes());
			log.info("需要解压的压缩包路径-->" + path);
			
			String fileName = file.getName().replace(".jar", "");
			ss.setOriginalUrl(fileName);
			log.info("用于压缩包删除前缀目录的名称-->" + fileName);
			
			String filePath = infoPath + fileName;
			ss.setFilePath(filePath);
			log.info("解压后的文件夹目录路径-->" + filePath);
			
			String newFilePath = infoPath + file.getName();
			ss.setJarNewPath(newFilePath);
			log.info("新打包后的压缩包路径-->" + newFilePath);
			
			ut.updJarContent(ss,logfile,proName);
			logfile.write(("------" + path + "------").getBytes());
			logfile.write("\r\n".getBytes());
			//DeleteDirectory.deleteDir(new File(filePath));
			//log.info("删除临时解压文件 -->" + filePath + "<-- 成功！");
		}
		logfile.close();
	}
	
}

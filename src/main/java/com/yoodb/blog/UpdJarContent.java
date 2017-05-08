package com.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UpdJarContent {
	
	private static Log log = LogFactory.getLog(UpdJarContent.class);
	
	private static ArrayList<String> filelist = new ArrayList<String>();
	
	public void updJarContent(SignUtils sign,FileOutputStream logfile,String... proName) throws Exception {
		List<String> filePathlist = new ArrayList<String>();
		for (int i = 0; i < proName.length; i++) {
			Decompression.uncompress(new File(sign.getJarPath()),new File( sign.getFilePath()));
			getFiles(sign.getFilePath());
			for (String string : filelist) {
				if(string.contains(proName[i])){
					logfile.write((string).getBytes());
					logfile.write("\r\n".getBytes());
					filePathlist.add(string);
					Properties pro = new Properties();
					InputStream in = new BufferedInputStream (new FileInputStream(string));
					pro.load(in);
					Iterator<String> it = pro.stringPropertyNames().iterator();
					FileOutputStream yfile = new FileOutputStream(string);
					FileOutputStream originalfile = new FileOutputStream(string.replace(proName[i], "original_file"));
					pro.store(originalfile, "file update...");
					while(it.hasNext()){
						String key = it.next();
						String value = pro.getProperty(key);
						log.info("key-->" + key + "    value-->" + value);
						String cntext = null;
						try {
							cntext = TranslateUtil.translate(value,TranslateUtil.ENGLISH,TranslateUtil.CHINA);
						} catch (Exception e) {
							// TODO: handle exception
							log.error(e.getMessage());
							Thread.sleep(3000);
							cntext = TranslateUtil.translate(value,TranslateUtil.ENGLISH,TranslateUtil.CHINA);
						}
						
						log.info("key-->" + key + "    value-->" + cntext);
						pro.put(key, cntext);
					}
					pro.store(yfile, "file update...");
					yfile.close();
					originalfile.close();
					in.close();
				}
				
			}
			Compressor zc = new Compressor(sign.getJarNewPath());
			zc.setOriginalUrl(sign.getOriginalUrl());
			zc.compress(sign.getFilePath());
			log.info("恭喜修改压缩文件成功！！！！，修改文件如下：");
			for (String string : filePathlist) {
				log.info("update file path is [" + string + "]");
			}
			
		}
	}
	
	/**
	 * 将文件夹下所有文件存储
	 * @param filePath
	 */
	public static ArrayList<String> getFiles(String filePath) {
		File root = new File(filePath);
		File[] files = root.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				getFiles(file.getAbsolutePath());
			}else{
				filelist.add(file.getAbsolutePath());
			}
		}
		return filelist;
	}
	
	/**
	 * 将文件夹下所有jar存储
	 * @param filePath
	 */
	public static ArrayList<File> searchJarFiles(String filePath) {
		ArrayList<File> filelist = new ArrayList<File>();
        File f = new File(filePath);
        if (!f.exists()) {
        	log.info(filePath + " not exists");
            return null;
        }
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File file = fa[i];
            if (file.isDirectory()) {
            	log.info(file.getName() + " [目录]");
            } else {
            	String path = file.getAbsolutePath();
				if(path.contains("ui_") && path.contains(".jar") && !path.contains("_1.jar")){
					filelist.add(file);
				}
            }
        }
        return filelist;
    }
	
	public static void createFile(String path,String fileName) {
		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		File file = new File(f, fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

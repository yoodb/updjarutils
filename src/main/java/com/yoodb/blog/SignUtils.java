package com.yoodb.blog;

public class SignUtils {
	/**
	 * 用于压缩包删除前缀目录
	 */
	public String originalUrl = "org.wso2.carbon.service.mgt.ui_4.7.0";
	
	/**
	 * 解压后的文件夹目录
	 */
	public String filePath = "E:\\org.wso2.carbon.service.mgt.ui_4.7.0";
	
	/**
	 * 需要解压的文件夹
	 */
	public String jarPath = "E:\\org.wso2.carbon.service.mgt.ui_4.7.0.jar";
	
	/**
	 * 新打包后的压缩包名称
	 */
	public String jarNewPath = "E:\\org.wso2.carbon.service.mgt.ui_4.7.1.jar";

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getJarPath() {
		return jarPath;
	}

	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}

	public String getJarNewPath() {
		return jarNewPath;
	}

	public void setJarNewPath(String jarNewPath) {
		this.jarNewPath = jarNewPath;
	}
	
}

package com.yoodb.blog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

/**
 * @author mrwang
 */
@SuppressWarnings("deprecation")
public class HttpClientUtil {
	
	protected static Log log = LogFactory.getLog(HttpClientUtil.class);
	
	protected static HttpClient httpclient = null;
	
	protected static int maxTotal = 200;
	
	protected static int maxPerRoute = 20;
	
	protected static String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";
	
	static {
		if (httpclient == null) {
			SchemeRegistry reg = new SchemeRegistry();
			reg.register(new Scheme("http", 80, PlainSocketFactory
					.getSocketFactory()));
			reg.register(new Scheme("https", 443, SSLSocketFactory
					.getSocketFactory()));
			ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
					reg);
			cm.setMaxTotal(maxTotal);
			cm.setDefaultMaxPerRoute(maxPerRoute);
			httpclient = new DefaultHttpClient(cm);
		}
	}

	/**
	 * 下载后回传到InputStream
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static InputStream downloadAsStream(String url) throws Exception {
		InputStream is = (InputStream) download(url, null, null, false);
		return is;
	}

	/**
	 * 下载后存储到File对象中
	 * @param url
	 * @param saveFile
	 * @throws Exception
	 */
	public static void download(String url, File saveFile) throws Exception {
		download(url, saveFile, null, false);
	}

	/**
	 * 下载
	 * @param url
	 * @param saveFile
	 * @param params
	 * @param isPost
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public static Object download(final String url, final File saveFile,
			final Map<String, String> params, final boolean isPost)
			throws Exception {
		boolean saveToFile = saveFile != null;
		if (saveToFile && saveFile.getParentFile().exists() == false) {
			saveFile.getParentFile().mkdirs();
		}
		Exception err = null;
		HttpRequestBase request = null;
		HttpResponse response = null;
		HttpEntity entity = null;
		FileOutputStream fos = null;
		Object result = null;
		try {
			if (isPost) {
				request = new HttpPost(url);
			} else {
				request = new HttpGet(url);
			}
			addHeaderAndParams(request, params);
			response = httpclient.execute(request);
			entity = response.getEntity();
			entity = new BufferedHttpEntity(entity);
			if (saveToFile) {
				fos = new FileOutputStream(saveFile);
				IOUtils.copy(entity.getContent(), fos);
				result = saveFile;
			} else {
				result = new BufferedInputStream(entity.getContent());
			}
		} catch (Exception e) {
			err = e;
		} finally {
			IOUtils.closeQuietly(fos);
			request = null;
			response = null;
			entity = null;
			if (err != null) {
				throw err;
			}
			return result;
		}
	}

	protected static void addHeaderAndParams(final HttpRequestBase request,
			final Map<String, String> params) {
		request.addHeader("User-Agent", userAgent);
		if (params != null) {
			BasicHttpParams myParams = new BasicHttpParams();
			for (String key : params.keySet()) {
				myParams.setParameter(key, params.get(key));
			}
			request.setParams(myParams);
		}
	}

	public static HttpClient getHttpclient() {
		return httpclient;
	}

	public static void setHttpclient(HttpClient httpclient) {
		HttpClientUtil.httpclient = httpclient;
	}

	public static int getMaxTotal() {
		return maxTotal;
	}

	public static void setMaxTotal(int maxTotal) {
		HttpClientUtil.maxTotal = maxTotal;
	}

	public static int getMaxPerRoute() {
		return maxPerRoute;
	}

	public static void setMaxPerRoute(int maxPerRoute) {
		HttpClientUtil.maxPerRoute = maxPerRoute;
	}

	public static String getUserAgent() {
		return userAgent;
	}

	public static void setUserAgent(String userAgent) {
		HttpClientUtil.userAgent = userAgent;
	}
}

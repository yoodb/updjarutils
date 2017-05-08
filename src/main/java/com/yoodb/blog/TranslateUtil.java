package com.yoodb.blog;

import java.io.InputStream;
import java.net.URLEncoder;
import java.text.MessageFormat;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 翻译帮助工具
 * @author mrwang
 *
 */
public class TranslateUtil {
	protected static final String URL_TEMPLATE = "https://translate.google.cn/?langpair={0}&text={1}";
	
	protected static final String ID_RESULTBOX = "result_box";
	
	protected static final String ENCODING = "UTF-8";
	
	protected static final String AUTO = "auto";
	
	protected static final String TAIWAN = "zh-TW";
	
	protected static final String CHINA = "zh-CN";
	
	protected static final String ENGLISH = "en";
	
	protected static final String JAPAN = "ja";

	/**
	 * Google自动判断识别语言体系
	 * @param text
	 * @param target_lang
	 * @return
	 * @throws Exception
	 */
	public static String translate(final String text, final String target_lang)
			throws Exception {
		return translate(text, AUTO, target_lang);
	}

	/**
	 * Google翻译
	 * @param text
	 * @param src_lang
	 * @param target_lang
	 * @return
	 * @throws Exception
	 */
	public static String translate(final String text, final String src_lang,
			final String target_lang) throws Exception {
		InputStream is = null;
		Document doc = null;
		Element ele = null;
		try {
			String url = MessageFormat.format(URL_TEMPLATE,
					URLEncoder.encode(src_lang + "|" + target_lang, ENCODING),
					URLEncoder.encode(text, ENCODING));
			is = HttpClientUtil.downloadAsStream(url);
			doc = Jsoup.parse(is, ENCODING, "");
			ele = doc.getElementById(ID_RESULTBOX);
			String result = ele.text();
			return result;
		} finally {
			IOUtils.closeQuietly(is);
			is = null;
			doc = null;
			ele = null;
		}
	}

	/**
	 * Google翻译: 简体中文--> 繁体中文
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String cn2tw(final String text) throws Exception {
		return translate(text, CHINA, TAIWAN);
	}

	/**
	 * Google翻译: 繁体中文--> 简体中文
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String tw2cn(final String text) throws Exception {
		return translate(text, TAIWAN, CHINA);
	}

	/**
	 * Google翻译: 英文-->简体中文
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String en2tw(final String text) throws Exception {
		return translate(text, ENGLISH, CHINA);
	}

	/**
	 * Google翻译: 简体中文-->英文
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String tw2en(final String text) throws Exception {
		return translate(text, CHINA, ENGLISH);
	}

	/**
	 * Google翻译: 日文-->简体中文
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String jp2tw(final String text) throws Exception {
		return translate(text, JAPAN, CHINA);
	}

	/**
	 * Google翻译: 简体中文-->日文
	 * @param text
	 * @return
	 * @throws Exception
	 */
	public static String tw2jp(final String text) throws Exception {
		return translate(text, CHINA, JAPAN);
	}

}

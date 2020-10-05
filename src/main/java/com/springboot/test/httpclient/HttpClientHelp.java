package com.springboot.test.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * HttpClientg工具类
 * @author wangjitao
 * 2014-05-13
 *	
 */
public class HttpClientHelp {
	private final static Logger LOG = LoggerFactory.getLogger(HttpClient.class);
	// 创建默认的httpClient客户端
	public HttpClient httpClient = null;

	// get模式
	public HttpGet httpGet = null;

	// post模式
	private HttpPost httpPost = null;

	// 执行请求，获取服务器响应
	private HttpResponse response = null;

	// 请求的实体
	private HttpEntity entity = null;

	private List<NameValuePair> params;

	/**
	 * 释放资源
	 * 
	 * @param httpGet
	 * @param httpPost
	 * @param httpClient
	 */
	private void releaseSource(HttpGet httpGet, HttpPost httpPost,
			HttpClient httpClient) {
		if (httpGet != null) {
			httpGet.abort();
		}
		if (httpPost != null) {
			httpPost.abort();
		}
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}
	/**
	 * get方式提交并且返回Entity字符串
	 * 
	 * @param url
	 *            提交的url
	 * @return
	 */
	public String byGetMethodToHttpEntity(String url) {
		StringBuffer buff = new StringBuffer();
		// 创建线程安全的httpClient
		httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		// 创建一个HttpGet请求，作为目标地址。
		httpGet = new HttpGet(url);
		try {
			response = httpClient.execute(httpGet);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				entity = response.getEntity();
				buff.append(EntityUtils.toString(entity));
			}
		} catch (ClientProtocolException e) {
			LOG.error("details:"+e);
		} catch (IOException e) {
			LOG.error("details:"+e);
		} finally {
			releaseSource(httpGet, null, httpClient);
		}
		return buff.toString();
	}
	/**
	 * Post方式提交并且返回Entity字符串
	 * 
	 * @param url
	 *            提交的url
	 * @param params
	 *            队列参数
	 * @param urlEncoded
	 *            url编码
	 * @return
	 */
	public String byPostMethodToHttpEntity(String url,List<NameValuePair> params, String urlEncoded) {
		List<NameValuePair> param=params;
		StringBuffer buff = new StringBuffer();
		// 创建线程安全的httpClient
		httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
		// 创建一个HttpGet请求，作为目标地址。
		httpPost = new HttpPost(url);
		try {
			if (param != null || (param = this.params) != null) {
				// 格式化参数列表并提交
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(param, urlEncoded);
				httpPost.setEntity(uefEntity);
			}
			response = httpClient.execute(httpPost);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				entity = response.getEntity();
				buff.append(EntityUtils.toString(entity));
			}
		} catch (ClientProtocolException e) {
			LOG.error("details:"+e);
		} catch (IOException e) {
			LOG.error("details:"+e);
		} finally {
			releaseSource(null, httpPost, httpClient);
		}
		return buff.toString();
	}

	public <V> HttpClientHelp addParam(String name, V value) {
		if (this.params == null)
			params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair(name, value + ""));
		return this;
	}
}

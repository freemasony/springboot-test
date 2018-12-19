package com.springboot.test.common;


import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Map;


/**
 * Created with IntelliJ IDEA. User: Administrator Date: 14-5-15 Time: 下午3:41 To change this template use File | Settings | File Templates.
 */
public class HttpClientUtil
{

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	static
	{
		disableSslVerification();
	}

	public static String postFileRequest(String reqURL, Part[] parts, String encodeCharset)
	{
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000 * 100);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000 * 100);
		PostMethod filePost = new PostMethod(reqURL);
		filePost.getParams().setContentCharset(encodeCharset);
		try
		{
			MultipartRequestEntity multi = new MultipartRequestEntity(parts, filePost.getParams());
			filePost.setRequestEntity(multi);
			int status = httpClient.executeMethod(filePost);
			if (status == HttpStatus.SC_OK)
			{
				InputStream in = filePost.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, encodeCharset));
				String tempbf;
				StringBuffer html = new StringBuffer(100);
				while ((tempbf = br.readLine()) != null)
				{
					html.append(tempbf);
				}
				return html.toString();
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
		} finally
		{
			filePost.releaseConnection();
		}
		return null;
	}

	private static void disableSslVerification()
	{
		try
		{
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager()
			{
				public X509Certificate[] getAcceptedIssuers()
				{
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType)
				{
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType)
				{
				}
			}};

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier()
			{
				public boolean verify(String hostname, SSLSession session)
				{
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		} catch (KeyManagementException e)
		{
			e.printStackTrace();
		}
	}

	public static String postRequest(String reqURL, NameValuePair[] parameters, String encodeCharset)
	{
		return postRequest(reqURL, parameters, encodeCharset,60000);
	}

    /**
     * post请求。
     * @param reqURL
     * @param parameters
     * @param encodeCharset
     * @param timeOut
     * @return
     */
    public static String postRequest(String reqURL, NameValuePair[] parameters, String encodeCharset,int timeOut)
    {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
        PostMethod filePost = new PostMethod(reqURL);
        filePost.getParams().setContentCharset(encodeCharset);
        try
        {
            filePost.addParameters(parameters);
            int status = httpClient.executeMethod(filePost);
            if (status == HttpStatus.SC_OK)
            {
                InputStream in = filePost.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, encodeCharset));
                String tempbf;
                StringBuffer html = new StringBuffer(100);
                while ((tempbf = br.readLine()) != null)
                {
                    html.append(tempbf);
                }
                return html.toString();
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
            logger.error(ex.getMessage(), ex);
        } finally
        {
            filePost.releaseConnection();
        }
        return null;
    }

	public static String postByteRequest(String reqURL, byte[] bytes, String encodeCharset)
	{
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000);
		PostMethod filePost = new PostMethod(reqURL);
		filePost.getParams().setContentCharset(encodeCharset);
		try
		{
			ByteArrayRequestEntity byteArrayRequestEntity = new ByteArrayRequestEntity(bytes);
			filePost.setRequestEntity(byteArrayRequestEntity);
			int status = httpClient.executeMethod(filePost);
			logger.info("receipt postByteRequest url:" + reqURL + " status:" + status);
			if (status == HttpStatus.SC_OK)
			{
				InputStream in = filePost.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, encodeCharset));
				String tempbf;
				StringBuffer html = new StringBuffer(100);
				while ((tempbf = br.readLine()) != null)
				{
					html.append(tempbf);
				}
				return html.toString();
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
		} finally
		{
			filePost.releaseConnection();
		}
		return null;
	}


	public static String getGetResponse(String url)
	{
		String html = "";
		// 构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(50000);

		// 创建GET方法的实例
		GetMethod getMethod = new GetMethod(url);

        getMethod.getParams().setContentCharset("utf-8");
        // 使用系统提供的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		try
		{
			// 执行getMethod
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK)
			{
				System.err.println("Method failed: " + getMethod.getStatusLine());
			}
			// 处理内容
            InputStream in = getMethod.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String tempbf;
            StringBuffer htmlBuffer = new StringBuffer(100);
            while ((tempbf = br.readLine()) != null)
            {
                htmlBuffer.append(tempbf);
            }
			html = htmlBuffer.toString();
		} catch (HttpException e)
		{
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("Please check your provided http address!");
			e.printStackTrace();
		} catch (IOException e)
		{
			// 发生网络异常
			e.printStackTrace();
		} finally
		{
			// 释放连接
			getMethod.releaseConnection();
		}
		return html;
	}

    public static String getGetResponse(String url, String encode)
    {
        String html = "";
        // 构造HttpClient的实例
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(50000);

        // 创建GET方法的实例
        GetMethod getMethod = new GetMethod(url);

        getMethod.getParams().setContentCharset(encode);
        // 使用系统提供的默认的恢复策略
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        try
        {
            // 执行getMethod
            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK)
            {
                System.err.println("Method failed: " + getMethod.getStatusLine());
            }
            // 处理内容
            InputStream in = getMethod.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String tempbf;
            StringBuffer htmlBuffer = new StringBuffer(100);
            while ((tempbf = br.readLine()) != null)
            {
                htmlBuffer.append(tempbf);
            }
            html = htmlBuffer.toString();
        } catch (HttpException e)
        {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            System.out.println("Please check your provided http address!");
            e.printStackTrace();
        } catch (IOException e)
        {
            // 发生网络异常
            e.printStackTrace();
        } finally
        {
            // 释放连接
            getMethod.releaseConnection();
        }
        return html;
    }
	//post
	public static String HttpURLClient(String url, Map<String, String> map)
	{
		URL loginUrl;
		String result = "";
		try
		{
			loginUrl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) loginUrl.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);
			StringBuffer sb = new StringBuffer();
			int i = 0;
			if (map != null)
			{
				for (Map.Entry<String, String> entry : map.entrySet())
				{
					if (i > 0)
					{
						sb.append("&");
					}
					sb.append(entry.getKey()).append("=").append(entry.getValue());
					i++;
				}
				connection.getOutputStream().write(sb.toString().getBytes());
			}
			connection.getOutputStream().flush();
			connection.getOutputStream().close(); // flush and close

			if (connection.getResponseCode() == 200)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
				String line = "";
				while ((line = br.readLine()) != null)
				{
					result += line;
				}
				br.close();
			}
			return result;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * post json
	 * @param reqURL
	 * @param json
	 * @param encodeCharset
	 * @return
	 */
	public static String postJsonRequest(String reqURL, String json, String encodeCharset)
	{
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(50000);
		PostMethod filePost = new PostMethod(reqURL);
		filePost.getParams().setContentCharset(encodeCharset);
		try
		{
			RequestEntity se = new StringRequestEntity(json, "application/json", encodeCharset);
			filePost.setRequestEntity(se);

			int status = httpClient.executeMethod(filePost);
			if (status == HttpStatus.SC_OK)
			{
				InputStream in = filePost.getResponseBodyAsStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, encodeCharset));
				String tempbf;
				StringBuffer html = new StringBuffer(100);
				while ((tempbf = br.readLine()) != null)
				{
					html.append(tempbf);
				}
				return html.toString();
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			logger.error(ex.getMessage(), ex);
		} finally
		{
			filePost.releaseConnection();
		}
		return null;
	}
    /**
     * 由于查询牛豆流水记录和牛豆详细信息速度很慢，经常容易超时，因此添加一个50秒的长连接读取方法
     * @param reqURL
     * @param parameters
     * @param encodeCharset
     * @return
     */
    public static String postRequestLong(String reqURL, NameValuePair[] parameters, String encodeCharset)
    {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(50000);
        PostMethod filePost = new PostMethod(reqURL);
        filePost.getParams().setContentCharset(encodeCharset);
        try
        {
            filePost.addParameters(parameters);
            int status = httpClient.executeMethod(filePost);
            logger.info("postRequestLong reqURL:" + reqURL +"  status:"+status);
            if (status == HttpStatus.SC_OK)
            {
                InputStream in = filePost.getResponseBodyAsStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in, encodeCharset));
                String tempbf;
                StringBuffer html = new StringBuffer(100);
                while ((tempbf = br.readLine()) != null)
                {
                    html.append(tempbf);
                }
                logger.info("postRequestLong return:" + html.toString());
                return html.toString();
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
            logger.error(ex.getMessage(), ex);
        } finally
        {
            filePost.releaseConnection();
        }
        return null;
    }
    /**
     * 写入文件到HttpServletResponse中
     * @param reqURL
     * @param parameters
     * @param encodeCharset
     * @param fileName
     * @param response
     * @throws Exception
     */
    public static void postRequestAndReturnStream(String reqURL, NameValuePair[] parameters, String encodeCharset, String fileName, HttpServletResponse response) throws Exception
    {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(50000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(50000);
        PostMethod filePost = new PostMethod(reqURL);
        filePost.getParams().setContentCharset(encodeCharset);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".csv").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try
        {
            filePost.addParameters(parameters);
            int status = httpClient.executeMethod(filePost);
            if (status == HttpStatus.SC_OK)
            {
                InputStream is = filePost.getResponseBodyAsStream();
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))){
                    bos.write(buff, 0, bytesRead);
                }
            }
        } catch (final IOException e){
            throw e;
        } catch (Exception ex)
        {
            ex.printStackTrace();
            logger.error(ex.getMessage(), ex);
        } finally{
            if (bis != null){
                bis.close();
            }
            if (bos != null){
                bos.close();
            }
        }
    }



}

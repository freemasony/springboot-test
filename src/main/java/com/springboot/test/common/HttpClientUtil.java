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

	public static void main(String[] args) throws Exception
	{
		//		String targetURL ="http://10.171.38.139:8080/cfs-web-img-1.0.1/avatar?m=upload";
		//		String encodeCharset = "UTF-8";
		//		File targetFile = new File("d:\\3.jpg");
		//		Part[] parts = {new FilePart("file", targetFile), new StringPart("userId", "123456")};
		//		System.out.println(postFileRequest(targetURL, parts, encodeCharset));

		//System.out.println(postByteRequest("https://buy.itunes.apple.com/verifyReceipt", String.format("{\"receipt-data\":\"%s\"}",
		//																									  "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")
		//		.getBytes(), CharEncoding.UTF_8));
		String json = "[{\"seq\":3,\"query\":\"阿道夫32\",\"hidden\":0,\"hour_span\":24,\"valid\":1}]";
		postJsonRequest("http://192.168.20.162:8881/set_hot_query/v1", json, "UTF-8");
	}

}

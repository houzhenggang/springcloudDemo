package com.sensor.sellCabinet.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.sensor.sellCabinet.config.WechatConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author andy
 * @version 2.2
 */
@Slf4j
public class HttpUtils {

	private static final String DEFAULT_CHARSET = "UTF-8";
	
	private static final int CONNECT_TIME_OUT = 5000; //链接超时时间3秒
	
	private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT).build();
	
	private static SSLContext wx_ssl_context = null; //微信支付ssl证书
	
	static{
		try {
			log.info("---------get SSL-----------");
			String os = System.getProperties().getProperty("os.name");
			String sslPath = "";
			if (os.equals("Windows 7")){
				sslPath = "D:\\SVN\\xjSellCabinet\\pig\\apiclient_cert.p12";
			}else if (os.equals("Linux")){
				sslPath = "/usr/local/sellCabinet/apiclient_cert.p12";
			}
			FileInputStream instream = new FileInputStream(new File(sslPath));
			KeyStore keystore = KeyStore.getInstance("PKCS12");
			char[] keyPassword = ("1555272431").toCharArray(); //证书密码
			keystore.load(instream, keyPassword);
			wx_ssl_context = SSLContexts.custom().loadKeyMaterial(keystore, keyPassword).build();
			log.info("---------SSL ready-----------");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * @description 功能描述: get 请求
	 * @param url 请求地址
	 * @param params 参数
	 * @param headers headers参数
	 * @return 请求失败返回null
	 */
	public static String get(String url, Map<String, String> params, Map<String, String> headers) {
		CloseableHttpClient httpClient = null;
		if (params != null && !params.isEmpty()) {
			StringBuffer param = new StringBuffer();
			boolean flag = true; // 是否开始
			for (Entry<String, String> entry : params.entrySet()) {
				if (flag) {
					param.append("?");
					flag = false;
				} else {
					param.append("&");
				}
				param.append(entry.getKey()).append("=");
				
				try {
					param.append(URLEncoder.encode(entry.getValue(), DEFAULT_CHARSET));
				} catch (UnsupportedEncodingException e) {
					//编码失败
				}
			}
			url += param.toString();
		}

		String body = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(REQUEST_CONFIG)
					.build();
			HttpGet httpGet = new HttpGet(url);
			response = httpClient.execute(httpGet);
			body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}

	/**
	 * @description 功能描述: get 请求
	 * @param url 请求地址
	 * @return 请求失败返回null
	 */
	public static String get(String url) {
		return get(url, null);
	}

	/**
	 * @description 功能描述: get 请求
	 * @param url 请求地址
	 * @param params 参数
	 * @return 请求失败返回null
	 */
	public static String get(String url, Map<String, String> params) {
		return get(url, params, null);
	}

	/**
	 * @description 功能描述: post 请求
	 * @param url 请求地址
	 * @param params 参数
	 * @return 请求失败返回null
	 */
	public static String post(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		String body = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(REQUEST_CONFIG)
					.build();
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, DEFAULT_CHARSET));
			response = httpClient.execute(httpPost);
			body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}

	/**
	 * @description 功能描述: post 请求
	 * @param url 请求地址
	 * @param s 参数xml
	 * @return 请求失败返回null
	 */
	public static String post(String url, String s) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost(url);
		String body = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(REQUEST_CONFIG)
					.build();
			httpPost.setEntity(new StringEntity(s, DEFAULT_CHARSET));
			response = httpClient.execute(httpPost);
			body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}

	/**
	 * @description 功能描述: post https请求，服务器双向证书验证
	 * @param url 请求地址
	 * @param params 参数
	 * @return 请求失败返回null
	 */
	 public static String posts(String url, Map<String, String> params) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		if (params != null && !params.isEmpty()) {
			for (Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		String body = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(REQUEST_CONFIG)
					.setSSLSocketFactory(getSSLConnectionSocket())
					.build();
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, DEFAULT_CHARSET));
			response = httpClient.execute(httpPost);
			body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}
	
	/**
	 * @description 功能描述: post https请求，服务器双向证书验证
	 * @param url 请求地址
	 * @param s 参数xml
	 * @return 请求失败返回null
	 */
	public static String posts(String url, String s) {
		CloseableHttpClient httpClient = null;
		HttpPost httpPost = new HttpPost(url);
		String body = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.custom()
					.setDefaultRequestConfig(REQUEST_CONFIG)
					.setSSLSocketFactory(getSSLConnectionSocket())
					.build();
			httpPost.setEntity(new StringEntity(s, DEFAULT_CHARSET)); 
			response = httpClient.execute(httpPost);
			body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return body;
	}
	//调起接口
	public static JsonMap httpRequest(String requestUrl, String requestMethod, String outputStr) {
		String rtn = null;
		JsonMap rtnMap = null ;
		try {
			
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false); 
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr&&requestMethod.equals("POST")) {
				outputStr = "param = "+outputStr;
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			rtn = buffer.toString();
			JSONObject json = new JSONObject();
			json = JSONObject.fromObject(rtn);
			rtnMap = new JsonMap(json.getString("returnMsg"), json.getString("returnCode"), json.getString("retnrnJson"));
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnMap;
	}
	//获取ssl connection链接
	private static SSLConnectionSocketFactory getSSLConnectionSocket() {
		return new SSLConnectionSocketFactory(wx_ssl_context,
				new String[] {"TLSv1", "TLSv1.1", "TLSv1.2"},
				null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	}
	/**
	 * get
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGet(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpGet request = new HttpGet(buildUrl(host, path, querys));
        for (Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }
        
        return httpClient.execute(request);
    }
	
	private static HttpClient wrapClient(String host) {
		HttpClient httpClient = new DefaultHttpClient();
		if (host.startsWith("https://")) {
			sslClient(httpClient);
		}
		
		return httpClient;
	}
	
	private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
    	StringBuilder sbUrl = new StringBuilder();
    	sbUrl.append(host);
    	if (!StringUtils.isBlank(path)) {
    		sbUrl.append(path);
        }
    	if (null != querys) {
    		StringBuilder sbQuery = new StringBuilder();
        	for (Entry<String, String> query : querys.entrySet()) {
        		if (0 < sbQuery.length()) {
        			sbQuery.append("&");
        		}
        		if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
        			sbQuery.append(query.getValue());
                }
        		if (!StringUtils.isBlank(query.getKey())) {
        			sbQuery.append(query.getKey());
        			if (!StringUtils.isBlank(query.getValue())) {
        				sbQuery.append("=");
        				sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
        			}        			
                }
        	}
        	if (0 < sbQuery.length()) {
        		sbUrl.append("?").append(sbQuery);
        	}
        }
    	
    	return sbUrl.toString();
    }
	private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                } 
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                	
                }
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                	
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
        	throw new RuntimeException(ex);
        }
    }
	/**
	 * Post String
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			String body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);
    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }
        if (StringUtils.isNotBlank(body)) {
        	request.setEntity(new StringEntity(body, "utf-8"));
        }
        return httpClient.execute(request);
    }
	
}

package cn.sdu.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	public static final String BASE_URL = "http://192.168.253.6:8080/server/";
	public static final int METHOD_GET = 1;
	public static final int METHOD_POST = 2;

	public static long getLength(HttpEntity entity) {
		if (entity != null) {
			return entity.getContentLength();
		}
		return -1;
	}

	public static InputStream getStream(HttpEntity entity) throws IllegalStateException, IOException {
		if (entity != null)
			return entity.getContent();

		return null;
	}
	
	public static HttpEntity getEntity(String uri, List<NameValuePair> params, int method)
			throws ClientProtocolException, IOException {
		HttpEntity entity = null;
		// 创建客户端
		HttpClient client = new DefaultHttpClient();
		// 创建请求
		HttpUriRequest request = null;
		switch (method) {
		case METHOD_GET:
			StringBuilder sb = new StringBuilder(uri);
			if (params != null && !params.isEmpty()) {
				sb.append('?');
				for (NameValuePair p : params) {
					sb.append(p.getName()).append('=').append(p.getValue()).append('&');
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			request = new HttpGet(sb.toString());
			break;

		case METHOD_POST:
			request = new HttpPost(uri);
			if (params != null && !params.isEmpty()) {
				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params);
				((HttpPost) request).setEntity(reqEntity);
			}
			break;
		}
		// 执行请求
		HttpResponse resp = client.execute(request);
		// 判断请求结果
		if (resp.getStatusLine().getStatusCode() == 200) {
			entity = resp.getEntity();
		}
		return entity;
	}

	/**
	 * 获取服务端的json
	 * @param uri
	 * @param params
	 * @param method
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getNovel(String uri, List<NameValuePair> params, int method)
			throws ClientProtocolException, IOException {
		String string = "";
		// 创建客户端
		HttpClient client = new DefaultHttpClient();
		// 创建请求
		HttpUriRequest request = null;
		switch (method) {
		case METHOD_GET:
			StringBuilder sb = new StringBuilder(uri);
			if (params != null && !params.isEmpty()) {
				sb.append('?');
				for (NameValuePair p : params) {
					sb.append(p.getName()).append('=').append(p.getValue()).append('&');
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			request = new HttpGet(sb.toString());
			break;

		case METHOD_POST:
			request = new HttpPost(uri);
			if (params != null && !params.isEmpty()) {
				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params,HTTP.UTF_8);
				((HttpPost) request).setEntity(reqEntity);
			}
			break;
		}
		// 执行请求
		HttpResponse resp = client.execute(request);
		// 判断请求结果
		if (resp.getStatusLine().getStatusCode() == 200) {
			String temp = EntityUtils.toString(resp.getEntity());
			if(temp.length() > 0){
				string = temp.trim().toString();
			}else{
				string = "error";
			}
		}
		return string;
	}
}

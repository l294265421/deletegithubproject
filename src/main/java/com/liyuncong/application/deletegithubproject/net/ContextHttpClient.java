package com.liyuncong.application.deletegithubproject.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.liyuncong.application.deletegithubproject.common.Constants;

/**
 * 
 * 包含context的httpClient，一次逻辑上相关的会话，应该使用同一个httpClient，
 * 不同的会话应该使用不同的httpClient
 * @author liyuncong
 *
 */
public class ContextHttpClient implements Closeable{
	private CloseableHttpClient httpClient;
	private HttpClientContext context;
	private String host;
	private List<BasicHeader> headers;
	
	public ContextHttpClient() {
		this("");
	}
	
	public ContextHttpClient(String host) {
		httpClient = HttpClients.createDefault();
		
		HttpContext httpContext = new BasicHttpContext();
		context = HttpClientContext.adapt(httpContext);
		this.host = host;
		
		// 默认header
		headers = new LinkedList<>();
		headers.add(new BasicHeader("Accept", "text/html, application/xhtml+xml, image/jxr, */*"));
		headers.add(new BasicHeader("Accept-Encoding", "gzip, deflate"));
		headers.add(new BasicHeader("Accept-Language", "zh-CN"));
		headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; LCTE; rv:11.0) like Gecko"));
		headers.add(new BasicHeader("Connection", "Keep-Alive"));
		headers.add(new BasicHeader("Host", host));
	}
	
	/**
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NullPointerException if url is null
	 * @throws IllegalArgumentException if url is empty
	 */
	private HttpPost getHttpPost(String url, Map<String, String> parameters) throws UnsupportedEncodingException {
		if (url == null) {
			throw new NullPointerException("url is null");
		}
		
		if (url.length() == 0) {
			throw new IllegalArgumentException("url is empty");
		}
		
		HttpPost httpPost = new HttpPost(url);
		config(httpPost);
		
		List<NameValuePair> nvps = new LinkedList<NameValuePair>();
		if (parameters != null) {
			for(Entry<String, String> entry : parameters.entrySet()) {
				NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(),
						entry.getValue());
				nvps.add(nameValuePair);
			}
		}
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		return httpPost;
	}
	
	/**
	 * 
	 * @param url
	 * @throws IllegalArgumentException if url is empty
	 * @return
	 */
	private HttpGet getHttpGet(String url) {
		Objects.requireNonNull(url, "url must be not null");
		if (url.length() == 0) {
			throw new IllegalArgumentException("url must not be empty");
		}
		HttpGet httpGet = new HttpGet(url);
		config(httpGet);
		return httpGet;
	}
	
	private String sendRequest(HttpRequestBase request) throws IOException {
		// 执行请求用execute方法，content用来帮我们附带上额外信息
		CloseableHttpResponse response = httpClient.execute(request, context);
		// 得到相应实体、包括响应头以及相应内容
		Header location = response.getFirstHeader("Location");
		if (location != null) {
			return get(location.getValue());
		}
		HttpEntity entity = response.getEntity();
		// 得到response的内容
		return EntityUtils.toString(entity, Constants.FROM_CHARSET);
	}
	
	public String get(String url) throws IOException {
		HttpGet get = getHttpGet(url);
		return sendRequest(get);
	}
	
	public String post(String url, Map<String, String> parameters) throws IOException {
		HttpPost post = getHttpPost(url, parameters);
		return sendRequest(post);
	}
	
	/**
	 * 配置请求
	 * @param request
	 */
	private void config(HttpRequestBase request) {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(5000).setConnectTimeout(5000)
				.setSocketTimeout(5000).setRedirectsEnabled(true).build();
		request.setConfig(requestConfig);
		
		for (BasicHeader basicHeader : headers) {
			request.addHeader(basicHeader);
		}
	}

	@Override
	public void close() throws IOException {
		if (httpClient != null) {
			httpClient.close();
		}
	}
	
	public void addHead(String name, String value) {
		headers.add(new BasicHeader(name, value));
	}
	
	public boolean removeHead(String name) {
		Iterator<BasicHeader> iterator = headers.iterator();
		while (iterator.hasNext()) {
			BasicHeader basicHeader = iterator.next();
			if (basicHeader.getName().equals(name)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}
}

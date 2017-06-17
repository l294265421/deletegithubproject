package com.liyuncong.application.deletegithubproject.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liyuncong.application.deletegithubproject.net.ContextHttpClient;

/**
 * （基于HttpClient实现）登陆github,删除指定的项目;
 * 还没能成功
 * @author liyuncong
 *
 */
public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) throws IOException {
		ContextHttpClient client = new ContextHttpClient("github.com");
		
		String loginPage = client.get("https://github.com/login");
		String authenticityToken = getAuthenticityToken(loginPage);
		
		Map<String, String> loginParameters = new HashMap<>();
		loginParameters.put("commit", "Sign in");
		loginParameters.put("utf8", "✓");
		loginParameters.put("authenticity_token", authenticityToken);
		loginParameters.put("login", "294265421@qq.com");
		loginParameters.put("password", "******");
		
		client.post("https://github.com/session", loginParameters);
		
		String projectName = "imagerecognition1";
		String urlPrefix = "https://github.com/l294265421/";
		
		client.get(urlPrefix + projectName + "/settings");
		
		Map<String, String> deleteParameter = new HashMap<>();
		deleteParameter.put("utf8", "✓");
		deleteParameter.put("authenticity_token", authenticityToken);
		deleteParameter.put("_method", "delete");
		deleteParameter.put("verify", projectName);
		String afterDelete = client.post(urlPrefix + projectName + "/settings/delete", deleteParameter);
		
		System.out.println(afterDelete);
		
		client.close();
	}
	
	private static String getAuthenticityToken(String loginPage) {
		Document document = Jsoup.parse(loginPage);
		return document.select("input[name=authenticity_token]").first().val();
	}
}

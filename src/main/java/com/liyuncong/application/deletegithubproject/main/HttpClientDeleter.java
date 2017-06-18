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
 * （基于HttpClient）登陆github,删除指定的项目
 * @author liyuncong
 *
 */
public class HttpClientDeleter extends Deleter {
	private Logger logger = LoggerFactory.getLogger(HttpClientDeleter.class);
	private ContextHttpClient client;
	private String authenticityToken; 

	@Override
	protected boolean init() {
		client = new ContextHttpClient("github.com");
		
		String loginPage = "";
		try {
			loginPage = client.get(loginPageUrl);
		} catch (IOException e) {
			logger.error("faild to load home page: {}", e);
			return false;
		}
		authenticityToken = getAuthenticityToken(loginPage);
		
		Map<String, String> loginParameters = new HashMap<>();
		loginParameters.put("commit", "Sign in");
		loginParameters.put("utf8", "✓");
		loginParameters.put("authenticity_token", authenticityToken);
		loginParameters.put("login", userInfo.getLoginName());
		loginParameters.put("password", userInfo.getPassword());
		
		try {
			client.post("https://github.com/session", loginParameters);
		} catch (IOException e) {
			logger.error("failed to login:{}", e);
			return false;
		}
		return true;
	}

	@Override
	protected boolean deleteProject(String projectName) {
		String urlPrefix = "https://github.com/" + userInfo.getUserName() + "/";
		
		try {
			client.get(urlPrefix + projectName + "/settings");
		} catch (IOException e) {
			logger.error("failed to load settings page:{}", e);
			return false;
		}
		
		Map<String, String> deleteParameter = new HashMap<>();
		deleteParameter.put("utf8", "✓");
		deleteParameter.put("authenticity_token", authenticityToken);
		deleteParameter.put("_method", "delete");
		deleteParameter.put("verify", projectName);
		try {
			client.post(urlPrefix + projectName + "/settings/delete", deleteParameter);
		} catch (IOException e) {
			logger.error("failed to delete project:{} error: {}", projectName, e);
			return false;
		}
		return true;
	}

	@Override
	protected void destroy() {
		if (client != null) {
			try {
				client.close();
			} catch (IOException e) {
				logger.error("failed to close client:{}", e);
			}
		}
	}

	private String getAuthenticityToken(String loginPage) {
		Document document = Jsoup.parse(loginPage);
		return document.select("input[name=authenticity_token]").first().val();
	}
}

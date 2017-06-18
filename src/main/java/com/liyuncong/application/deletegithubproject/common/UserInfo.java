package com.liyuncong.application.deletegithubproject.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 
 * @author liyuncong
 *
 */
public class UserInfo {
	private String loginName;
	private String password;
	private String userName;
	
	public UserInfo() throws IOException {
		Properties properties = new Properties();
		properties.load(new InputStreamReader(new FileInputStream("conf/user_info.properites"), Constants.FROM_CHARSET));
		loginName = properties.getProperty("login_name");
		if (loginName == null) {
			throw new IllegalArgumentException("loginName must be set");
		}
		password = properties.getProperty("password");
		if (password == null) {
			throw new IllegalArgumentException("password must be set");
		}
		userName = properties.getProperty("user_name");
		if (userName == null) {
			throw new IllegalArgumentException("userName must be set");
		}
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UserInfo [loginName=" + loginName + ", password=" + password + ", userName=" + userName + "]";
	}
	
}

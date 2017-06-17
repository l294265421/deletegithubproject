package com.liyuncong.application.deletegithubproject.main;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.liyuncong.application.deletegithubproject.common.FileUtil;
import com.liyuncong.application.deletegithubproject.common.UserInfo;

/**
 * 
 * （基于selenium实现）登陆github,删除指定的项目
 * @author liyuncong
 *
 */
public class Main2 {
	public static void main(String[] args) throws IOException {
//		System.setProperty("webdriver.ie.driver", "driver/IEDriverServer.exe");
//		WebDriver webDriver = new InternetExplorerDriver();
//		System.setProperty("webdriver.firefox.bin",
//				"D:/program files (x86)/Mozilla Firefox/firefox.exe");
//		WebDriver webDriver = new FirefoxDriver();
		
		UserInfo userInfo = new UserInfo();
		
		System.setProperty("webdriver.chrome.bin",
		"C:/Program Files (x86)/Google/Chrome/Application/chrome.exe");
		System.setProperty("webdriver.chrome.driver",
				"driver/chromedriver.exe");
		RemoteWebDriver webDriver = new ChromeDriver();
		webDriver.get("https://github.com/login");
		WebElement loginField = webDriver.findElement(By.id("login_field"));
		loginField.sendKeys(userInfo.getLoginName());
		WebElement password = webDriver.findElement(By.id("password"));
		password.sendKeys(userInfo.getPassword());
		WebElement commit = webDriver.findElement(By.name("commit"));
		commit.click();
		
		List<String> projectNames = FileUtil.readAllLines("conf/project_name");
		for (String projectName : projectNames) {
			if (projectName == null || projectName.length() == 0) {
				continue;
			}
			String urlPrefix = "https://github.com/" + userInfo.getUserName() + "/";
			
			webDriver.get(urlPrefix + projectName + "/settings");
			
			WebElement deleteButton = webDriver.findElement(By.xpath("//button[@data-facebox='#delete_repo_confirm']"));
			deleteButton.click();
			
			List<WebElement> verifys = webDriver.findElements(By.name("verify"));
			verifys.get(verifys.size() - 1).sendKeys(projectName);
			
			WebElement confirmCommit = webDriver.findElements(By.xpath("//button[text()='I understand the consequences, delete this repository']")).get(1);
			confirmCommit.click();
		}
		
		webDriver.close();
	}
}

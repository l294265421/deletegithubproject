package com.liyuncong.application.deletegithubproject.main;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * 
 * （基于selenium实现）登陆github,删除指定的项目
 * @author liyuncong
 *
 */
public class SeleniumDeleter extends Deleter {
	private WebDriver webDriver;

	@Override
	protected boolean init() {
		System.setProperty("webdriver.chrome.bin",
		browserInfo.getBrowser());
		System.setProperty("webdriver.chrome.driver",
				browserInfo.getDriver());
		webDriver = new ChromeDriver();
		webDriver.get(loginPageUrl);
		WebElement loginField = webDriver.findElement(By.id("login_field"));
		loginField.sendKeys(userInfo.getLoginName());
		WebElement password = webDriver.findElement(By.id("password"));
		password.sendKeys(userInfo.getPassword());
		WebElement commit = webDriver.findElement(By.name("commit"));
		commit.click();
		return true;
	}

	@Override
	protected boolean deleteProject(String projectName) {
		String urlPrefix = "https://github.com/" + userInfo.getUserName() + "/";
		
		webDriver.get(urlPrefix + projectName + "/settings");
		
		WebElement deleteButton = webDriver.findElement(By.xpath("//button[@data-facebox='#delete_repo_confirm']"));
		deleteButton.click();
		
		List<WebElement> verifys = webDriver.findElements(By.name("verify"));
		verifys.get(verifys.size() - 1).sendKeys(projectName);
		
		WebElement confirmCommit = webDriver.findElements(By.xpath("//button[text()='I understand the consequences, delete this repository']")).get(1);
		confirmCommit.click();
		return true;
	}

	@Override
	protected void destroy() {
		if (webDriver != null) {
			webDriver.quit();
		}
	}

}

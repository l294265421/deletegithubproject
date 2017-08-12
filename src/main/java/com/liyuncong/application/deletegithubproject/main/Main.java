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
 * @author liyuncong
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		Deleter deleter = new SeleniumDeleter();
		deleter.delete();
		deleter.destroy();
	}
}

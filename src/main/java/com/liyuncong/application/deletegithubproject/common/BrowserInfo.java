package com.liyuncong.application.deletegithubproject.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class BrowserInfo {
    private String browser;
    private String driver;

    public BrowserInfo() throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream("conf/browser.properties"), Constants.FROM_CHARSET));
        browser = properties.getProperty("browser");
        if (browser == null) {
            throw new IllegalArgumentException("browser path must be set");
        }
        driver = properties.getProperty("driver");
        if (driver == null) {
            throw new IllegalArgumentException("browser driver must be set");
        }
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}

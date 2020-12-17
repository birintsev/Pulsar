package org.example.action;

import org.example.data.Resolutions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeBrowser implements Action {
    private ChromeOptions chromeOptions = new ChromeOptions();
    @Override
    public WebDriver runDriver(String resolution) {
        System.setProperty("webdriver.chrome.driver","./webdriver/chromedriver.exe");
        chromeOptions.addArguments(resolution);
        return new ChromeDriver(chromeOptions);
    }
}

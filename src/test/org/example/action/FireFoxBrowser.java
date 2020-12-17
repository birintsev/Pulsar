package org.example.action;

import org.example.data.Resolutions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FireFoxBrowser implements Action {
    private FirefoxOptions firefoxOptions = new FirefoxOptions();
    @Override
    public WebDriver runDriver(String resolution) {
        System.setProperty("webdriver.gecko.driver","./webdriver/geckodriver.exe");
        firefoxOptions.addArguments(resolution);
        return new FirefoxDriver(firefoxOptions);
    }
}

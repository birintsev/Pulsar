package org.example.pages;

import org.example.logger.ScreenShot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObject {
    protected WebDriver driver;
    protected ScreenShot screenShot = new ScreenShot();
    protected WebDriverWait wait;
    public PageObject(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,30);
        PageFactory.initElements(driver,this);
    }
}

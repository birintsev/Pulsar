package org.example.pages;

import org.example.entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

public class SubscribePage extends PageObject {
    private User user;
    private Actions actions;

    private String currentUrl;

    private By subscribe = By.xpath("(//button[@id='subscribe-33']//span)[1]");
    private By myhosts = By.xpath("//div[contains(text(), 'My Hosts')]");

    public SubscribePage(WebDriver driver, User user) {
        super(driver);
        this.user = user;
        actions = new Actions(driver);
    }

    private void click(){
        try{
            Thread.sleep(1000);
            screenShot.crateScreenshot(this.getClass().getName());
            wait.until(ExpectedConditions.visibilityOfElementLocated(subscribe));
            currentUrl = driver.getCurrentUrl();
            actions.moveToElement(driver.findElement(subscribe));
            actions.perform();
            ((JavascriptExecutor) driver).executeScript("document.getElementById('subscribe-33').click()");
            Thread.sleep(1000);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public HomePage subscribe(){
        click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(myhosts));
        driver.findElement(myhosts).click();
        return new HomePage(driver,user);
    }
}

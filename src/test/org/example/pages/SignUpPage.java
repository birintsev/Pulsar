package org.example.pages;

import org.example.logger.FileIO;
import org.example.logger.ScreenShot;
import org.example.entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;

public class SignUpPage extends PageObject{
    private User user = new User();
    private ArrayList<String> tabs;
    private Actions actions;
    private ScreenShot screenShot = new ScreenShot();

    private By username = By.xpath("//input[@name='username']");
    private By email = By.xpath("//input[@name='email']");
    private By password = By.xpath("//input[@name='password']");
    private By repassword = By.xpath("//input[@name='password_repeat']");
    private By age = By.xpath("//input[@name='age']");
    private By sumbit = By.xpath("//button[@type='submit']");
    private String script = "document.querySelector(\"#root > div > div.jss3\").setAttribute('hidden','true')";

    private EmailPage emailPage;

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    private void inputData(){
        actions = new Actions(driver);
        driver.findElement(username).sendKeys(user.getUsername());
        driver.findElement(email).sendKeys(user.getEmail());
        driver.findElement(password).sendKeys(user.getPassword());
        driver.findElement(repassword).sendKeys(user.getPassword());
        driver.findElement(age).sendKeys(String.valueOf(user.getAge()));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript(script);
        actions.moveToElement(driver.findElement(sumbit));
        actions.perform();
        screenShot.crateScreenshot(this.getClass().getName());
        FileIO.logUser(user);
        driver.findElement(sumbit).click();

    }
    private void gotoEmailPageAndSet(){
        emailPage = new EmailPage(driver, user);
        emailPage.openNewTab();
        tabs = emailPage.getTabs();
        user.setEmail(emailPage.getEmail());
        driver.switchTo().window(tabs.get(0));
    }
    public EmailPage submit(){
        gotoEmailPageAndSet();
        inputData();
        driver.switchTo().window(tabs.get(1));
        return new EmailPage(driver, user);
    }

    public User getUser() {
        return user;
    }
}

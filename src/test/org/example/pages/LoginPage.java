package org.example.pages;

import org.example.entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends PageObject{
    private User user;

    private By email = By.xpath("//input[@name='email']");
    private By pass = By.xpath("//input[@name='password']");
    private By submit = By.xpath("//button[@type='submit']");


    public LoginPage(WebDriver driver, User user) {
        super(driver);
        this.user = user;
    }

    private void inputData(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        driver.findElement(email).sendKeys(user.getEmail());
        driver.findElement(pass).sendKeys(user.getPassword());
        screenShot.crateScreenshot(this.getClass().getName());
        driver.findElement(submit).click();
    }


    public HomePage login(){
        inputData();
        return new HomePage(driver, user);
    }
}

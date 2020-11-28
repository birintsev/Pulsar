package org.example.pages;

import org.example.entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends PageObject{
    private User user;

    private By email = By.xpath("//span[contains(text(), '@')]");
    private By title = By.xpath("//div[contains(text(), 'Available')]");
    private By buttonAdd = By.xpath("//button//span[contains(text(), 'Add')]");

    public HomePage(WebDriver driver, User user) {
        super(driver);
        this.user = user;
    }
    public String getEmail(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        screenShot.crateScreenshot(this.getClass().getName());
        return driver.findElement(email).getText();
    }
    public String pageTitle(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(title));
        return driver.findElement(title).getText();
    }
    public boolean loaded(){
        if(!driver.getTitle().isEmpty() && driver.getTitle().equals("Pulsar")){
            return true;
        }
        return false;
    }
    public NewServerPage addNewServer(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(buttonAdd));
        driver.findElement(buttonAdd).click();
        return new NewServerPage(driver,user);
    }
}

package org.example.pages;

import org.example.entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends PageObject{
    private User user;

    private By email = By.xpath("//span[@id='user-email']");
    private By allHosts = By.xpath("//div[contains(text(), 'All Hosts')]");
    private By viewButton = By.xpath("//button[@id='view-button-0']");


    public HomePage(WebDriver driver, User user) {
        super(driver);
        this.user = user;
    }
    public String getEmail(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        screenShot.crateScreenshot(this.getClass().getName());
        return driver.findElement(email).getText();
    }
    public boolean loaded(){
        if(!driver.getTitle().isEmpty() && driver.getTitle().equals("Pulsar")){
            return true;
        }
        return false;
    }
    public PremiumPage changeSubscription(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        driver.findElement(email).click();
        return new PremiumPage(driver,user);
    }
    public SubscribePage addNewServer(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(allHosts));
        driver.findElement(allHosts).click();
        return new SubscribePage(driver,user);
    }
    public ServerInfoPage resultsOfAddedServer(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewButton));
        driver.findElement(viewButton).click();
        return new ServerInfoPage(driver);
    }
}

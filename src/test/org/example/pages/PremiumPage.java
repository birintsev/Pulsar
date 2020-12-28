package org.example.pages;

import org.example.entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PremiumPage extends PageObject {
    private User user;

    private By input = By.xpath("//div[@role='button']");
    private By text = By.xpath("//li[contains(text(), 'Premium')]");
    private By allHosts = By.xpath("//div[contains(text(), 'All Hosts')]");
    private By updateButton = By.xpath("(//button//span)[3]");
    private By emailInput = By.xpath("//input[@aria-invalid='false']");

    private String email;

    public PremiumPage(WebDriver driver, User user) {
        super(driver);
        this.user = user;
    }

    private void click(){
        driver.findElement(input).click();
        email = driver.findElement(emailInput).getAttribute("value");
        try{
            Thread.sleep(1000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(text));
            driver.findElement(text).click();
            Thread.sleep(1000);
            screenShot.crateScreenshot(this.getClass().getName());
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public String getEmail() {
        return email;
    }

    public HomePage subscribe(){
        click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(updateButton));
        driver.findElement(updateButton).click();
        driver.findElement(allHosts).click();
        return new HomePage(driver,user);
    }
}

package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ServerInfoPage extends PageObject{

    private By dates = By.xpath("//input[@type='datetime-local']");
    private By chart = By.xpath("(//div[@class='recharts-wrapper'])[1]");
    private By signOutButton = By.xpath("//span[contains(text(), 'Sign Out')]");
    private By name = By.xpath("//div[contains(text(), 'Public')]");

    private String serverName;

    public void changeDate(){
        driver.findElements(dates).get(0).sendKeys("13");
        driver.findElements(dates).get(1).sendKeys("15");
        wait.until(ExpectedConditions.visibilityOfElementLocated(chart));
        serverName = driver.findElement(name).getText();
        try{
            Thread.sleep(1000);
            screenShot.crateScreenshot(this.getClass().getName());
            Thread.sleep(1000);
            driver.findElement(signOutButton).click();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
    public String getServerName(){
        return serverName;
    }

    public ServerInfoPage(WebDriver driver) {
        super(driver);
    }

}

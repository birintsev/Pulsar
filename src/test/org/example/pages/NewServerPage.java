package org.example.pages;

import org.example.data.RandomData;
import org.example.data.ServerInfo;
import org.example.entities.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewServerPage extends PageObject {
    private User user;

    private By serverName = By.xpath("//input[@name='name']");
    private By publicKey = By.xpath("//input[@name='public_key']");
    private By privateKey = By.xpath("//input[@name='private_key']");
    private By buttonAdd = By.xpath("//button[@type='submit']");

    public NewServerPage(WebDriver driver, User user) {
        super(driver);
        this.user = user;
    }

    private void fillFields(){
        driver.findElement(serverName).sendKeys(RandomData.generatePass(10));
        driver.findElement(publicKey).sendKeys(ServerInfo.PUBLIC_KEY);
        driver.findElement(privateKey).sendKeys(ServerInfo.PRIVATE_KEY);
        try{
            Thread.sleep(1000);
            screenShot.crateScreenshot(this.getClass().getName());
            driver.findElement(buttonAdd).click();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public HomePage addServer(){
        fillFields();
        return new HomePage(driver,user);
    }
}

package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class TranslationPage extends PageObject{
    private By option = By.xpath("//select");
    private By fixedText = By.xpath("//h6");

    private Select select;

    public TranslationPage(WebDriver driver) {
        super(driver);
    }
    private boolean selectUkrainian(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(option));
        select = new Select(driver.findElement(option));
        select.selectByVisibleText("Українська");
        driver.navigate().refresh();
        try {
            Thread.sleep(2000);
            screenShot.crateScreenshot(getClass().getName()+"_ua");
        }catch (Exception e){
            System.out.println(e);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(fixedText));
        return driver.findElement(fixedText).getText().contains("сервери");
    }
    private boolean selectEnglish(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(option));
        select = new Select(driver.findElement(option));
        select.selectByVisibleText("English");
        driver.navigate().refresh();
        try {
            Thread.sleep(2000);
            screenShot.crateScreenshot(getClass().getName()+"_eng");
        }catch (Exception e){
            System.out.println(e);
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(fixedText));
        return driver.findElement(fixedText).getText().contains("servers");
    }

    public boolean submitCreation(){
        if(selectUkrainian()){
            return selectEnglish();
        }
        return false;
    }
}

package org.example;


import org.example.data.PageList;
import org.example.pages.TranslationPage;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Translation
{
    private WebDriver driver;
    private ChromeOptions chromeOptions = new ChromeOptions();
    private TranslationPage translationPage;

    @BeforeTest
    public void setup(){
        System.setProperty("webdriver.chrome.driver","./webdriver/chromedriver.exe");
        chromeOptions.addArguments("--kiosk");
        driver = new ChromeDriver(chromeOptions);
        driver.get(PageList.LOGIN_PAGE);
    }
    @Test
    public void translated(){
        translationPage = new TranslationPage(driver);
        Assert.assertNotNull(translationPage);
        Assert.assertTrue(translationPage.submitCreation());
    }
    @AfterTest
    public void shutdown(){
        driver.quit();
    }
}

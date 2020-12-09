package org.example;


import org.example.data.PageList;
import org.example.data.PermanentUserData;
import org.example.entities.User;
import org.example.pages.*;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Login
{
    private WebDriver driver;
    private ChromeOptions chromeOptions = new ChromeOptions();
    private LoginPage loginPage;
    private HomePage homePage;

    @BeforeTest
    public void setup(){
        System.setProperty("webdriver.chrome.driver","./webdriver/chromedriver.exe");
        chromeOptions.addArguments("--kiosk");
        driver = new ChromeDriver(chromeOptions);
        driver.get(PageList.LOGIN_PAGE);
    }
    @Test
    public void login(){
        loginPage = new LoginPage(driver, new User(
                PermanentUserData.USERNAME,
                PermanentUserData.PASSWORD,
                PermanentUserData.EMAIL,
                PermanentUserData.AGE));
        homePage = loginPage.login();
        Assert.assertEquals(PermanentUserData.EMAIL,homePage.getEmail());
    }
    @AfterTest
    public void shutdown(){
        driver.quit();
    }
}

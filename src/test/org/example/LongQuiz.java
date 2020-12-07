package org.example;


import org.example.data.PageList;
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
public class LongQuiz
{
    private WebDriver driver;
    private ChromeOptions chromeOptions = new ChromeOptions();
    private SignUpPage signUpPage;
    private EmailPage emailPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private NewServerPage newServerPage;

    @BeforeTest
    public void setup(){
        System.setProperty("webdriver.chrome.driver","./webdriver/chromedriver.exe");
        chromeOptions.addArguments("--kiosk");
        driver = new ChromeDriver(chromeOptions);
        driver.get(PageList.MAIN_PAGE);
    }
    @Test
    public void authAndReg(){

        signUpPage = new SignUpPage(driver);
        emailPage = signUpPage.submit();
        loginPage = emailPage.submitCreation();
        Assert.assertEquals(PageList.LOGIN_PAGE, emailPage.getFinalURL());
        homePage = loginPage.login();
        Assert.assertTrue(homePage.loaded());
    }
    @Test
    public void newServerAdd(){
        Assert.assertEquals("Available Servers", homePage.pageTitle());
        Assert.assertEquals(signUpPage.getUser().getEmail(),homePage.getEmail());
        newServerPage = homePage.addNewServer();
    }
    @Test
    public void reviewAddedServer(){
        homePage = newServerPage.addServer();
        Assert.assertTrue(true);
    }
    @AfterTest
    public void shutdown(){
        driver.quit();
    }
}

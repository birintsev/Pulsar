package org.example;


import org.example.data.PageList;
import org.example.data.PermanentUserData;
import org.example.entities.User;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.pages.SubscribePage;
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
public class ServerAdd
{
    private WebDriver driver;
    private ChromeOptions chromeOptions = new ChromeOptions();
    private LoginPage loginPage;
    private HomePage homePage;
    private SubscribePage subscribePage;

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
    @Test
    public void server(){
        subscribePage = homePage.addNewServer();
        homePage = subscribePage.subscribe();
        Assert.assertTrue(true);
    }
    @AfterTest
    public void shutdown(){
        driver.quit();
    }
}

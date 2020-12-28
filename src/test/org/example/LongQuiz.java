package org.example;


import org.example.action.Action;
import org.example.action.ChromeBrowser;
import org.example.action.FireFoxBrowser;
import org.example.data.PageList;
import org.example.data.Resolutions;
import org.example.pages.*;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LongQuiz {
    private WebDriver driver;
    private SignUpPage signUpPage;
    private EmailPage emailPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private PremiumPage premiumPage;
    private SubscribePage subscribePage;
    private ServerInfoPage infoPage;

    private Map<String, Action> browser = new HashMap<String, Action>(){{
        put("chrome", new ChromeBrowser());
        put("firefox", new FireFoxBrowser());
    }};

    @BeforeTest
    public void setup(){
        //тут менять строчку chrome/firefix и стат поля с класса Resolutions
        driver = browser.get("chrome").runDriver(Resolutions.RESOLUTION_1920x1080);
        driver.get(PageList.MAIN_PAGE);
    }
    @Test
    public void authAndReg(){

        signUpPage = new SignUpPage(driver);
        emailPage = signUpPage.submit();
        loginPage = emailPage.submitCreation();
        homePage = loginPage.login();
        Assert.assertTrue(homePage.loaded());
    }
    @Test
    public void premiumAcc(){
        Assert.assertEquals(signUpPage.getUser().getEmail(),homePage.getEmail());
        premiumPage = homePage.changeSubscription();
        homePage = premiumPage.subscribe();
        Assert.assertEquals(signUpPage.getUser().getEmail(),premiumPage.getEmail());
    }
    @Test
    public void serverAdd(){
        Assert.assertEquals(signUpPage.getUser().getEmail(),homePage.getEmail());
        subscribePage = homePage.addNewServer();
    }
    @Test
    public void subscriptionAdd(){
        homePage = subscribePage.subscribe();
        Assert.assertEquals(PageList.ALL_HOST_PAGE,subscribePage.getCurrentUrl());
    }
    @Test
    public void turnToHome(){
        infoPage = homePage.resultsOfAddedServer();
        infoPage.changeDate();
        Assert.assertEquals("Public Key: gor-tss",infoPage.getServerName());
    }
    @AfterTest
    public void shutdown(){
        driver.quit();
    }
}

package org.example.pages;

import org.example.data.PageList;
import org.example.entities.User;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailPage extends PageObject{
    private User user;
    private ArrayList<String> tabs;
    private JavascriptExecutor js;
    private String finalURL;

    private By emailField = By.xpath("//span[@id='email']");
    private By message = By.xpath("//td[contains(text(), 'tss')]");
    private By frame = By.xpath("//iframe");
    private By elem = By.xpath("//button//span[contains(text(), 'Sign In')]");

    private String jsScript = "alert(document.getElementById('iframeMail').contentWindow.document.body.innerHTML);";
    private String regexp = "href=[\\'\"]?([^\\'\" >]+)";

    public EmailPage(WebDriver driver,User user) {
        super(driver);
        this.user = user;
    }
    public ArrayList<String> openNewTab(){
        ((JavascriptExecutor)driver).executeScript("window.open()");
        tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get(PageList.FAKE_MAIL_URL);
        return tabs;
    }
    public String getEmail(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        return driver.findElement(emailField).getText();
    }
    public void clickToSubmit(){
        js = (JavascriptExecutor)driver;
        wait.until(ExpectedConditions.visibilityOfElementLocated(message));
        driver.findElement(message).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(frame));
        String acceptLink = "";
        try {
            Thread.sleep(5000);
            screenShot.crateScreenshot(this.getClass().getName());
            js.executeScript(jsScript);
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String buffered = alert.getText();
            alert.accept();
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher =pattern.matcher(buffered);
            if (matcher.find())
            {
                acceptLink = matcher.group(1);
            }
        }catch (Exception ignored){}
        driver.get(acceptLink);
        finalURL = driver.getCurrentUrl();
    }

    public String getFinalURL() {
        return finalURL;
    }

    public ArrayList<String> getTabs() {
        return tabs;
    }

    public LoginPage submitCreation(){
        clickToSubmit();
        wait.until(ExpectedConditions.visibilityOfElementLocated(elem));
        return new LoginPage(driver, user);
    }
}

package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class ResultPage {
    private WebDriver driver;
    private Wait wait;
    private JavascriptExecutor je;

    @FindBy(id="return-home-success")
    WebElement returnHomeSuccessButton;
    public ResultPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        this.je = (JavascriptExecutor) driver;
    }

    public void returnToHomePage() {
        wait.until(ExpectedConditions.visibilityOf(returnHomeSuccessButton));
        je.executeScript("arguments[0].click();",  returnHomeSuccessButton);

    }
}

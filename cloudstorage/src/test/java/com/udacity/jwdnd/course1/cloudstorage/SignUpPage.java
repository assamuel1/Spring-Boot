package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.function.Function;

public class SignUpPage {
    private final WebDriver driver;
    private final Wait wait;
    private final JavascriptExecutor je;

    public SignUpPage(WebDriver driver){
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        this.je = (JavascriptExecutor) driver;
    }

    //Elements on this page
    @FindBy(id="inputFirstName")
    private WebElement inputFirstNameField;

    //Elements on this page
    @FindBy(id="inputLastName")
    private WebElement inputLastNameField;

    //Elements on this page
    @FindBy(id="inputUsername")
    private WebElement inputUserNameField;

    @FindBy(id="inputPassword")
    private WebElement inputPasswordField;

    @FindBy(id="signUpButton")
    private WebElement signUpButton;

    @FindBy(id="back-to-login")
    private WebElement backToLoginLink;

    public void signup(String firstName, String lastName, String username, String password) {
        this.inputFirstNameField.sendKeys(firstName);
        this.inputLastNameField.sendKeys(lastName);
        this.inputUserNameField.sendKeys(username);
        this.inputPasswordField.sendKeys(password);
        this.signUpButton.click();


    }

    public void backToLoginPage() {
        wait.until(ExpectedConditions.elementToBeClickable(backToLoginLink));
        je.executeScript("arguments[0].click();",  backToLoginLink);
    }
}

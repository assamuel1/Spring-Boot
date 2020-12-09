package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    //Elements on this page
    @FindBy(id="inputUsername")
    private WebElement inputUserNameField;

    @FindBy(id="inputPassword")
    private WebElement inputPasswordField;

    @FindBy(id="submitbutton")
    private WebElement submitButton;

    @FindBy(id="signup-link")
    private WebElement signUpLink;

//Action that can be performed on this page object
    public void login(String username, String password){
        this.inputUserNameField.sendKeys(username);
        this.inputPasswordField.sendKeys(password);
        this.submitButton.click();
    }

    public void ClickOnSignUpLink(){
        this.signUpLink.click();
    }

}

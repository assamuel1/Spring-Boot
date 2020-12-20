package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class HomePage {

    private WebDriver driver;
    private Wait wait;
    private final JavascriptExecutor je;

    //Notes Tab related UI elements
    @FindBy(id = "nav-notes-tab")
    private WebElement navnotestab;

    @FindBy(id = "add-note-button")
    private WebElement addNewNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInputField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInputField;

    @FindBy(id = "noteSaveChanges-Button")
    private WebElement noteSubmitButton;

    @FindBy(id = "noteEditButton")
    private WebElement noteEditButton;

    @FindBy(id = "noteDeleteButton")
    private WebElement noteDeleteButton;

    @FindBy(id = "noteModalLabel")
    private WebElement noteModalLabel;


    @FindBy(id = "nav-files-tab")
    private WebElement navfilestab;

    //Credentials Tab related UI elements
    @FindBy(id = "nav-credentials-tab")
    private WebElement navcredentialstab;


    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInputField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInputField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInputField;

    @FindBy(id = "credential-Edit-Button")
    private WebElement credentialEditButton;

    @FindBy(id = "credential-Delete-Button")
    private WebElement credentialDeleteButton;

    @FindBy(id = "credentialModalLabel")
    private WebElement credentialModalLabel;

    @FindBy(id = "credentialSaveChanges-Button")
    private WebElement credentialSaveChanges;

    @FindBy(id = "Logout-Button")
    private WebElement logoutButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        this.je = (JavascriptExecutor) driver;
    }

    //Methods
    //Click 'Notes' tab
    public void clickNotesTabButton() {
        wait.until(ExpectedConditions.elementToBeClickable(navnotestab));
        //navnotestab.click();
        je.executeScript("arguments[0].click();", navnotestab);
    }

    public void createNote(String noteTitleValue, String noteDescriptionValue) throws InterruptedException {
        addNewNoteButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(noteModalLabel));
        noteTitleInputField.sendKeys(noteTitleValue);
        noteDescriptionInputField.sendKeys(noteDescriptionValue);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(noteSubmitButton));
        je.executeScript("arguments[0].click();", noteSubmitButton);


    }

    public void updateNote(String noteTitleValue, String noteDescriptionValue) throws InterruptedException {

        wait.until(ExpectedConditions.elementToBeClickable(noteModalLabel));
        noteTitleInputField.clear();
        noteTitleInputField.sendKeys(noteTitleValue);
        noteDescriptionInputField.clear();
        noteDescriptionInputField.sendKeys(noteDescriptionValue);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(noteSubmitButton));
        je.executeScript("arguments[0].click();", noteSubmitButton);


    }

    public void clickNotesEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(noteEditButton));
        je.executeScript("arguments[0].click();", noteEditButton);
    }

    public boolean verifyNotesExists(String noteTitle, String noteDescription) {

        Boolean noteExists = false;
        try{

            WebElement notesRow = driver.findElement(By.id("noteTitleRow"));
            WebElement notesDescriptionRow = driver.findElement(By.id("noteDescriptionRow"));

            if (notesRow.getText().equals(noteTitle) && notesDescriptionRow.getText().equals(noteDescription)) {
                noteExists = true;
            }
        } catch (NoSuchElementException e) {
            return noteExists;
        }
        return noteExists;

    }



    public void clickLogoutButton() {
        je.executeScript("arguments[0].click();", logoutButton);
    }

    public void clickDeleteNotesButton() {
        wait.until(ExpectedConditions.elementToBeClickable(noteDeleteButton));
        je.executeScript("arguments[0].click();", noteDeleteButton);
    }

    public void clickCredentialsTabButton() {
        wait.until(ExpectedConditions.elementToBeClickable(navcredentialstab));
        je.executeScript("arguments[0].click();", navcredentialstab);
    }

    public void createCredentials(String url, String username, String password) throws InterruptedException {
        addCredentialButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(credentialModalLabel));
        credentialUrlInputField.sendKeys(url);
        credentialUsernameInputField.sendKeys(username);
        credentialPasswordInputField.sendKeys(password);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(credentialSaveChanges));
        je.executeScript("arguments[0].click();", credentialSaveChanges);
    }

    public void updateCredentials(String url, String username, String password) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(credentialModalLabel));
        credentialUrlInputField.clear();
        credentialUrlInputField.sendKeys(url);
        credentialUsernameInputField.clear();
        credentialUsernameInputField.sendKeys(username);
        credentialPasswordInputField.clear();
        credentialPasswordInputField.sendKeys(password);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(credentialSaveChanges));
        je.executeScript("arguments[0].click();", credentialSaveChanges);
    }

    public boolean verifyCredentialExists(String URL, String credentialUsername, String credentialPassword) {
        Boolean CredentialExists = false;

      try {
          WebElement url = driver.findElement(By.id("credential-url"));
          WebElement username = driver.findElement(By.id("credential-username"));
          WebElement password = driver.findElement(By.id("credential-password"));

          if (url.getAttribute("value").equals(URL) && username.getAttribute("value").equals(credentialUsername) && password.getAttribute("value").equals(credentialPassword)) {
              CredentialExists = true;
          }
      }catch  (NoSuchElementException e) {
            return CredentialExists;
      }
        return CredentialExists;

    }

    public void clickCredentialsEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(credentialEditButton));
        je.executeScript("arguments[0].click();", credentialEditButton);
    }

    public void clickDeleteCredentialsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(credentialDeleteButton));
        je.executeScript("arguments[0].click();", credentialDeleteButton);
    }

}


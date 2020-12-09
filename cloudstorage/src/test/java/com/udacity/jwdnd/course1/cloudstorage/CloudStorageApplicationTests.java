package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {


	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseURL;
	private  Wait wait;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + port;
		this.wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(5))
				.ignoring(NoSuchElementException.class);

	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Order(1)
	@Test
	public void testUserCannotAccessHomePageWithoutLoggingIn() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login Page", driver.getTitle());
		Thread.sleep(5000);
	}

	@Order(2)
    @Test
    public void testNewSignedUpUserCanLogin() throws InterruptedException {

        // CREATING USER
        // User data to be used:
        String firstName = "Second";
        String lastName = "Test";
        String username = "test2";
        String password = "password";

        // Verify '/signup' endpoint takes us to Sign Up page.
        driver.get(baseURL + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        // Initializing Selenium Page Object and signing up the new user.
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signup(firstName, lastName, username, password);
        signUpPage.backToLoginPage();
        Thread.sleep(5000);

        // Verify we were successfully redirected to Login page.
        Assertions.assertEquals("Login Page", driver.getTitle());

        // Initializing Selenium Page Object and logging new user in.
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password); // Automatically redirects to home page.

        // Verify we were successfully redirected to Home page.
        Assertions.assertEquals("Home Page", driver.getTitle());

        //Logout of the application
        HomePage homePage = new HomePage(driver);
        homePage.clickLogoutButton();
        Thread.sleep(5000);

        //Verify the user cannot access Home Page
        driver.get(baseURL + "/home");
        Thread.sleep(5000);
        Assertions.assertNotEquals("Home Page", driver.getTitle());
        Assertions.assertEquals("Login Page", driver.getTitle());
        System.out.print("testNewSignedUpUserCanLogin - Pass");
    }
	/**
	 * Test that logs in an existing user, creates a note and verifies
	 * that the note details are visible in the note list.
	 */
	//@Order(3)
	@Test
	public void testCreateNoteWithExistingUser() throws InterruptedException {

		// CREATING USER
		// User data to be used:
		String firstName = "Joe";
		String lastName = "Doe";
		String username = "joedoe";
		String password = "doe024";

		// Verify '/signup' endpoint takes us to Sign Up page.
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		// Initializing Selenium Page Object and signing up the new user.
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.signup(firstName, lastName, username, password);
		signUpPage.backToLoginPage();
		Thread.sleep(5000);

		// Verify we were successfully redirected to Login page.
		Assertions.assertEquals("Login Page", driver.getTitle());

		// Initializing Selenium Page Object and logging new user in.
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password); // Automatically redirects to home page.

		// Verify we were successfully redirected to Home page.
		Assertions.assertEquals("Home Page", driver.getTitle());

		// CREATING NOTE
		// Go to Notes Section and Create note
		String noteTitle = "Grocery";
		String noteDescription = "Buy supplies for birthday party";

		HomePage homePage = new HomePage(driver);
		homePage.clickNotesTabButton();
		Thread.sleep(5000);
		homePage.createNote(noteTitle, noteDescription); // Automatically redirects to result page.

		// Verify we were successfully redirected to Result Page and go back to home page

		wait.until(ExpectedConditions.titleContains("Result Page"));
		Assertions.assertEquals("Result Page", driver.getTitle());
		ResultPage resultPage = new ResultPage(driver);
		resultPage.returnToHomePage(); // back to homepage;

        wait.until(ExpectedConditions.titleContains("Home Page"));
		homePage.clickNotesTabButton();

        Thread.sleep(5000);

		// VERIFY NOTE WAS CREATED
		homePage.clickNotesEditButton();
		Assertions.assertTrue(homePage.verifyNotesExists(noteTitle, noteDescription));


        //Logout of the application
        homePage.clickLogoutButton();
        Thread.sleep(5000);
	}

    @Order(4)
	@Test
    public void testUpdateNoteWithExistingUser() throws InterruptedException {

        // CREATING USER
		// User data to be used:
		String firstName = "Fourth";
		String lastName = "Test";
		String username = "Test4";
		String password = "Password4";

		// Verify '/signup' endpoint takes us to Sign Up page.
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		// Initializing Selenium Page Object and signing up the new user.
		SignUpPage signUpPage = new SignUpPage(driver);
		signUpPage.signup(firstName, lastName, username, password);
		signUpPage.backToLoginPage();
		Thread.sleep(5000);

		// Verify we were successfully redirected to Login page.
		Assertions.assertEquals("Login Page", driver.getTitle());

		// Initializing Selenium Page Object and logging new user in.
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password); // Automatically redirects to home page.

		// Verify we were successfully redirected to Home page.
		Assertions.assertEquals("Home Page", driver.getTitle());

		// CREATING NOTE
		// Go to Notes Section and Create note
		String noteTitle = "Bake Cake for party";
		String noteDescription = "Lego Cake with candy";

		HomePage homePage = new HomePage(driver);
		homePage.clickNotesTabButton();
		Thread.sleep(5000);
		homePage.createNote(noteTitle, noteDescription); // Automatically redirects to result page.

		// Verify we were successfully redirected to Result Page and go back to home page

		wait.until(ExpectedConditions.titleContains("Result Page"));
		Assertions.assertEquals("Result Page", driver.getTitle());
		ResultPage resultPage = new ResultPage(driver);
		resultPage.returnToHomePage(); // back to homepage;

        wait.until(ExpectedConditions.titleContains("Home Page"));
		homePage.clickNotesTabButton();

        Thread.sleep(5000);

		// VERIFY NOTE WAS CREATED
		homePage.clickNotesEditButton();
		Assertions.assertTrue(homePage.verifyNotesExists(noteTitle, noteDescription));


        //Logout of the application
        homePage.clickLogoutButton();
        Thread.sleep(5000);

        // LOGGING IN EXISTING USER AND EDITING NOTE.
        String editedNoteTitle = "Bake Cake";
        String editedNoteDescription = "Lego Cake with candy. Red Velvet cake with Cream cheese Frosting. Make it eggless";
        loginPage.login(username, password);
        wait.until(ExpectedConditions.titleContains("Home Page"));
        Assertions.assertEquals("Home Page", driver.getTitle());

        homePage.clickNotesTabButton();
        homePage.clickNotesEditButton();

       // homePage.waitNoteModelPage();
        //Notes firstNote = homePage.findNote();
        homePage.updateNote(editedNoteTitle, editedNoteDescription); //Redirects to result page
        wait.until(ExpectedConditions.titleContains("Result Page"));
        Assertions.assertEquals("Result Page", driver.getTitle());
        resultPage.returnToHomePage(); // Redirects to home page

        // VERIFY EDITED NOTE
        homePage.clickNotesTabButton();
        homePage.clickNotesEditButton();
        //homePage.waitNoteModelPage();
        Assertions.assertFalse(homePage.verifyNotesExists(editedNoteTitle, editedNoteDescription));

        //Logout of the application
        homePage.clickLogoutButton();
        Thread.sleep(5000);
    }


    @Order(5)
    @Test
    public void testDeleteNoteWithExistingUser() throws InterruptedException {
        // CREATING USER
        // User data to be used:
        String firstName = "Fifth";
        String lastName = "Test";
        String username = "Test5";
        String password = "Password5";

        // Verify '/signup' endpoint takes us to Sign Up page.
        driver.get(baseURL + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        // Initializing Selenium Page Object and signing up the new user.
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signup(firstName, lastName, username, password);
        signUpPage.backToLoginPage();
        Thread.sleep(5000);

        // Verify we were successfully redirected to Login page.
        Assertions.assertEquals("Login Page", driver.getTitle());

        // Initializing Selenium Page Object and logging new user in.
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password); // Automatically redirects to home page.

        // Verify we were successfully redirected to Home page.
        Assertions.assertEquals("Home Page", driver.getTitle());

        // CREATING NOTE
        // Go to Notes Section and Create note
        String noteTitleToBeDeleted = "Tentative party";
        String noteDescriptionToBeDeleted = "Lego Cake with candy";

        HomePage homePage = new HomePage(driver);
        homePage.clickNotesTabButton();
        Thread.sleep(5000);
        homePage.createNote(noteTitleToBeDeleted, noteDescriptionToBeDeleted); // Automatically redirects to result page.

        // Verify we were successfully redirected to Result Page and go back to home page

        wait.until(ExpectedConditions.titleContains("Result Page"));
        Assertions.assertEquals("Result Page", driver.getTitle());
        ResultPage resultPage = new ResultPage(driver);
        resultPage.returnToHomePage(); // back to homepage;

        wait.until(ExpectedConditions.titleContains("Home Page"));
        homePage.clickNotesTabButton();

        Thread.sleep(5000);

        // VERIFY NOTE WAS CREATED
        homePage.clickNotesEditButton();
        Assertions.assertTrue(homePage.verifyNotesExists(noteTitleToBeDeleted, noteDescriptionToBeDeleted));


        //Logout of the application
        homePage.clickLogoutButton();
        Thread.sleep(5000);


        loginPage.login(username, password);
        wait.until(ExpectedConditions.titleContains("Home Page"));
        Assertions.assertEquals("Home Page", driver.getTitle());

        homePage.clickNotesTabButton();
        homePage.clickDeleteNotesButton();

        wait.until(ExpectedConditions.titleContains("Result Page"));
        Assertions.assertEquals("Result Page", driver.getTitle());
        resultPage.returnToHomePage(); // back to homepage;

        wait.until(ExpectedConditions.titleContains("Home Page"));
        homePage.clickNotesTabButton();

        Thread.sleep(5000);
        Assertions.assertFalse(homePage.verifyNotesExists(noteTitleToBeDeleted, noteDescriptionToBeDeleted));

        //Logout of the application
        homePage.clickLogoutButton();

    }

    @Order(6)
    @Test
    public void testCreateCredentialWithExistingUser() throws InterruptedException {
        // CREATING USER
        // User data to be used:
        String firstName = "Sixth";
        String lastName = "Test";
        String username = "Test6";
        String password = "Password6";

        // Verify '/signup' endpoint takes us to Sign Up page.
        driver.get(baseURL + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        // Initializing Selenium Page Object and signing up the new user.
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signup(firstName, lastName, username, password);
        signUpPage.backToLoginPage();
        Thread.sleep(5000);

        // Verify we were successfully redirected to Login page.
        Assertions.assertEquals("Login Page", driver.getTitle());

        // Initializing Selenium Page Object and logging new user in.
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password); // Automatically redirects to home page.

        // Verify we were successfully redirected to Home page.
        Assertions.assertEquals("Home Page", driver.getTitle());

        String credentialURL = "https://www.gmsil.com";
        String credentialusername = "username";
        String credentialpassword = "password";


        HomePage homePage = new HomePage(driver);
        homePage.clickCredentialsTabButton();
        Thread.sleep(5000);
        homePage.createCredentials(credentialURL, credentialusername, credentialpassword); // Au
        wait.until(ExpectedConditions.titleContains("Result Page"));
        Assertions.assertEquals("Result Page", driver.getTitle());
        ResultPage resultPage = new ResultPage(driver);
        resultPage.returnToHomePage(); // back to homepage;

        wait.until(ExpectedConditions.titleContains("Home Page"));
        homePage.clickCredentialsTabButton();
        Assertions.assertTrue(homePage.verifyCredentialExists(credentialURL, credentialusername, credentialpassword));

        //Logout of the application
        homePage.clickLogoutButton();

    }


    @Test
    public void testUpdateCredentialWithExistingUser() throws InterruptedException {

    String firstName = "Seventh";
        String lastName = "Test";
        String username = "Test7";
        String password = "Password7";

        // Verify '/signup' endpoint takes us to Sign Up page.
        driver.get(baseURL + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        // Initializing Selenium Page Object and signing up the new user.
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signup(firstName, lastName, username, password);
        signUpPage.backToLoginPage();
        Thread.sleep(5000);

        // Verify we were successfully redirected to Login page.
        Assertions.assertEquals("Login Page", driver.getTitle());

        // Initializing Selenium Page Object and logging new user in.
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password); // Automatically redirects to home page.

        // Verify we were successfully redirected to Home page.
        Assertions.assertEquals("Home Page", driver.getTitle());

        String credentialURL = "https://www.gmail.com";
        String credentialusername = "username";
        String credentialpassword = "password";


        HomePage homePage = new HomePage(driver);
        homePage.clickCredentialsTabButton();
        Thread.sleep(5000);
        homePage.createCredentials(credentialURL, credentialusername, credentialpassword); // Au
        wait.until(ExpectedConditions.titleContains("Result Page"));
        Assertions.assertEquals("Result Page", driver.getTitle());
        ResultPage resultPage = new ResultPage(driver);
        resultPage.returnToHomePage(); // back to homepage;

        wait.until(ExpectedConditions.titleContains("Home Page"));
        homePage.clickCredentialsTabButton();
        Assertions.assertTrue(homePage.verifyCredentialExists(credentialURL, credentialusername, credentialpassword));

        //Logout of the application
        homePage.clickLogoutButton();
        Thread.sleep(5000);

            //Login again
        String editedURL = "www.gmail.com";
        String editedCredentialUsername = "editedURL";
        String editedCredentialPassword = "editedPassword";
        loginPage.login(username, password);
        wait.until(ExpectedConditions.titleContains("Home Page"));
        Assertions.assertEquals("Home Page", driver.getTitle());

        homePage.clickCredentialsTabButton();
        homePage.clickCredentialsEditButton();

        // homePage.waitNoteModelPage();
        //Notes firstNote = homePage.findNote();
        homePage.updateCredentials(editedURL, editedCredentialUsername, editedCredentialPassword );
        wait.until(ExpectedConditions.titleContains("Result Page"));
        Assertions.assertEquals("Result Page", driver.getTitle());
        resultPage.returnToHomePage(); // Redirects to home page

        // VERIFY EDITED NOTE
        homePage.clickNotesTabButton();
        //homePage.clickNotesEditButton();
        Assertions.assertTrue(homePage.verifyCredentialExists(editedURL, editedCredentialUsername, editedCredentialPassword));

        //Logout of the application
        homePage.clickLogoutButton();
        Thread.sleep(5000);


    }

    @Test
    public void testDeleteCredentialWithExistingUser() throws InterruptedException {
        // CREATING USER
        // User data to be used:
        String firstName = "Eight";
        String lastName = "Test";
        String username = "Test8";
        String password = "Password8";

        // Verify '/signup' endpoint takes us to Sign Up page.
        driver.get(baseURL + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        // Initializing Selenium Page Object and signing up the new user.
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signup(firstName, lastName, username, password);
        signUpPage.backToLoginPage();
        Thread.sleep(5000);

        // Verify we were successfully redirected to Login page.
        Assertions.assertEquals("Login Page", driver.getTitle());

        // Initializing Selenium Page Object and logging new user in.
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password); // Automatically redirects to home page.

        // Verify we were successfully redirected to Home page.
        Assertions.assertEquals("Home Page", driver.getTitle());

        // CREATING NOTE
        // Go to Notes Section and Create note
        String URLToBeDeleted = "www.google.com";
        String CredentialUsernameToBeDeleted = "username";
        String CredentialPasswordToBeDeleted = "password";

        HomePage homePage = new HomePage(driver);
        homePage.clickCredentialsTabButton();
        Thread.sleep(5000);
        homePage.createCredentials(URLToBeDeleted, CredentialUsernameToBeDeleted, CredentialPasswordToBeDeleted); // Automatically redirects to result page.

        // Verify we were successfully redirected to Result Page and go back to home page

        wait.until(ExpectedConditions.titleContains("Result Page"));
        Assertions.assertEquals("Result Page", driver.getTitle());
        ResultPage resultPage = new ResultPage(driver);
        resultPage.returnToHomePage(); // back to homepage;

        wait.until(ExpectedConditions.titleContains("Home Page"));
        homePage.clickCredentialsTabButton();

        Thread.sleep(5000);

        // VERIFY CREDENTIAL  WAS CREATED
        homePage.clickCredentialsEditButton();
        Assertions.assertTrue(homePage.verifyCredentialExists(URLToBeDeleted, CredentialUsernameToBeDeleted, CredentialPasswordToBeDeleted));


        //Logout of the application
        homePage.clickLogoutButton();
        Thread.sleep(5000);


        loginPage.login(username, password);
        wait.until(ExpectedConditions.titleContains("Home Page"));
        Assertions.assertEquals("Home Page", driver.getTitle());

        homePage.clickCredentialsTabButton();
        homePage.clickDeleteCredentialsButton();

        wait.until(ExpectedConditions.titleContains("Result Page"));
        Assertions.assertEquals("Result Page", driver.getTitle());
        resultPage.returnToHomePage(); // back to homepage;

        wait.until(ExpectedConditions.titleContains("Home Page"));
        homePage.clickCredentialsTabButton();

        Thread.sleep(5000);
        Assertions.assertFalse(homePage.verifyCredentialExists(URLToBeDeleted, CredentialUsernameToBeDeleted, CredentialPasswordToBeDeleted));

        //Logout of the application
        homePage.clickLogoutButton();
    }
}

package com.example.NewPro_cloudeStorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.JVM)
class NewProCloudeStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}
	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getHomePageUnauthorized(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

	}

	@Test
	public void credentialCreation(){
		getLoginPage();
		credentialCreation();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Assertions.assertEquals("https://t.co/W2aaRMyKgD"
				,driver.findElement(By.id("urlDisplay")).getText());
		Assertions.assertEquals("Mohd"
				,driver.findElement(By.id("usernameDisplay")).getText());
		Assertions.assertNotEquals("Moha-a"
				,driver.findElement(By.id("passwordDisplay")).getText());

	}
	@Test
	public void editCredentials() throws InterruptedException {
		credentialCreation();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editCredentialsBtn")));
		WebElement editCredentialsButton= driver.findElement(By.id("editCredentialsBtn"));
		editCredentialsButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement url = driver.findElement(By.id("credential-url"));
		url.click();
		url.clear();
		url.sendKeys("https://t.co/W2aaRMyKgD");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		String password = driver.findElement(By.id("credential-password")).getAttribute("value");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-credential-button")));
		WebElement submitCredential = driver.findElement(By.id("submit-credential-button"));
		submitCredential.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertTrue(driver.findElement(By.id("table-cred-url")).getText().contains("https://t.co/W2aaRMyKgD"));

		Assertions.assertNotEquals(driver.findElement(By.id("table-cred-password")).getText(), password);

		Thread.sleep(3000);

	}
	@Test
	public void deleteCredentials() throws InterruptedException {
		credentialCreation();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-credential-button")));
		WebElement deleteCredentialsButton= driver.findElement(By.id("delete-credential-button"));
		deleteCredentialsButton.click();

		WebElement credentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credList = credentialTable.findElements(By.tagName("tbody"));

		Assertions.assertEquals(0, credList.size());

		Thread.sleep(3000);
	}
	@Test
	public void createNote() throws InterruptedException {

		doMockSignUp("Mohd","omran","Mohd_12","882");


		doLogIn("Mohd_12", "882");


		WebElement notesTab= driver.findElement(By.id("nav-notes-tab"));
		notesTab.click();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes")));
		Assertions.assertTrue(driver.findElement(By.id("nav-notes")).isDisplayed());


		WebElement addNoteButton= driver.findElement(By.id("addNoteBtn"));
		addNoteButton.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement title = driver.findElement(By.id("note-title"));
		title.click();
		title.sendKeys("Test Note");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement description = driver.findElement(By.id("note-description"));
		description.click();
		description.sendKeys("Testing a create note ...");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-note-btn")));
		WebElement submitNote = driver.findElement(By.id("submit-note-btn"));
		submitNote.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("table-note-title")).getText().contains("Test Note"));


		Thread.sleep(3000);

	}
	@Test
	public void editNote() throws InterruptedException {
		createNote();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-Btn")));
		WebElement editNote = driver.findElement(By.id("edit-Btn"));
		editNote.click();


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement description = driver.findElement(By.id("note-description"));
		description.click();
		description.clear();
		description.sendKeys("Testing a edited note ...");


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit-note-btn")));
		WebElement submitNote = driver.findElement(By.id("submit-note-btn"));
		submitNote.click();



		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("table-note-description")).getText().contains("edited description"));

	}
	@Test
	public void deleteNote() throws InterruptedException {

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		createNote();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note-btn")));
		WebElement deleteNote = driver.findElement(By.id("delete-note-btn"));
		deleteNote.click();


		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("Tbody"));

		Assertions.assertEquals(0, notesList.size());
	}

	private void doMockSignUp(String firstName, String lastName, String userName, String password){

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);


		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();


		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}




	private void doLogIn(String userName, String password)
	{

		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	@Test
	public void testRedirection() {

		doMockSignUp("Redirection","Test","RT","123");


		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	@Test
	public void testBadUrl() {

		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");


		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	@Test
	public void testLargeUpload() {

		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

}

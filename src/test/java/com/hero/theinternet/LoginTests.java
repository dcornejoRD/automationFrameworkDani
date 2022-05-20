package com.hero.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {
	private WebDriver driver;
	String url;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setUp(@Optional("chrome") String Browser) {

		switch (Browser) {
		case "chrome":

			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			driver = new ChromeDriver();
			break;

		case "firefox":
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
			driver = new FirefoxDriver();
			break;
		default:
			System.out.println("Do not how to start " + Browser + " instarting Chrome");
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
			driver = new ChromeDriver();
			break;
		}

		url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened");

		driver.manage().window().maximize();

	}

	@AfterMethod(alwaysRun = true)
	private void teardown() {
		driver.quit();
	}

	@Test(priority = 1, groups = { "positiveTests", "smokeTests" })
	public void positiveLoginTest() {

		WebElement userNameField = driver.findElement(By.id("username"));
		userNameField.sendKeys("tomsmith");

		WebElement passwordField = driver.findElement(By.name("password"));
		passwordField.sendKeys("SuperSecretPassword!");
		sleep(2000);

		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();

		sleep(2000);
		String expectedUrl = "http://the-internet.herokuapp.com/secure";
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "URL NOT MATCH");

		WebElement logOutButton = driver.findElement(By.xpath("//a[@href='/logout']"));
		Assert.assertTrue(logOutButton.isDisplayed(), "Button Logout is displayed");

		WebElement successMessage = driver.findElement(By.cssSelector("div#flash"));
		String actualMessage = successMessage.getText();
		// Assert.assertEquals(successMessage.getText(),"You logged into a secure area!"
		// , "Message is not the same");
		Assert.assertTrue(actualMessage.contains("You logged into a secure area!"),
				"Message sucess is not the same as expected.\nActual Message: " + actualMessage);

		sleep(3000);

	}

	@Parameters({ "username", "password", "expectedMessage" })
	@Test(priority = 1, groups = { "NegativeTests", "smokeTests" })
	public void NegativeLoginTest(String username, String password, String expectedErrorMessage) {

		WebElement userNameField = driver.findElement(By.id("username"));
		userNameField.sendKeys(username);

		WebElement passwordField = driver.findElement(By.name("password"));
		passwordField.sendKeys(password);

		WebElement logInButton = driver.findElement(By.tagName("button"));
		logInButton.click();

		WebElement failedMessage = driver.findElement(By.cssSelector("#flash"));

		String getAlertMessage = failedMessage.getText();

		Assert.assertTrue(getAlertMessage.contains(expectedErrorMessage),
				"Message  is not the same as expected.\nActual Message: " + expectedErrorMessage);

	}

	private void sleep(long m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

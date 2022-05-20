package com.hero.theinternet;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;



public class NegativeTest{
	

	
	String getAlertMessage="";

@Parameters({ "username", "password", "expectedMessage" })	
@Test
public void invalidPassword(String username, String password, String expectedErrorMessage) {
	System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
	WebDriver driver = new ChromeDriver();
	
	//open test page 
	String testUrl="http://the-internet.herokuapp.com/login";
	driver.get(testUrl);
	driver.manage().window().maximize();
	System.out.println("Page is opened.");

	
	WebElement userNameField= driver.findElement(By.id("username"));
	userNameField.sendKeys(username);
	
	WebElement passwordField= driver.findElement(By.name("password"));
	passwordField.sendKeys(password);
	
	
	WebElement logInButton= driver.findElement(By.tagName("button"));
	logInButton.click();
	
	
	WebElement failedMessage= driver.findElement(By.cssSelector("#flash"));
	
	
	getAlertMessage =failedMessage.getText();

	Assert.assertTrue( getAlertMessage.contains(expectedErrorMessage), "Message  is not the same as expected.\nActual Message: "+expectedErrorMessage);

	driver.quit();
	
	

}



}

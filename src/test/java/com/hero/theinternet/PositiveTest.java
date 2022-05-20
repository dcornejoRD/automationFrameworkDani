package com.hero.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTest {
	
	@Test
	public void loginTest() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
		WebDriver driver = new ChromeDriver();
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is oepned");
		
		
		WebElement userNameField= driver.findElement(By.id("username"));
		userNameField.sendKeys("tomsmith");
		
		
		
		WebElement passwordField= driver.findElement(By.name("password"));
		passwordField.sendKeys("SuperSecretPassword!");
		sleep(2000);  

		WebElement logInButton= driver.findElement(By.tagName("button"));
		logInButton.click();
		
		sleep(2000);  
		String expectedUrl="http://the-internet.herokuapp.com/secure";
		String actualUrl=driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, expectedUrl, "URL NOT MATCH");
	
		
		WebElement logOutButton= driver.findElement(By.xpath("//a[@href='/logout']"));
		Assert.assertTrue(logOutButton.isDisplayed(), "Button Logout is displayed");
		
		
		WebElement successMessage= driver.findElement(By.cssSelector("div#flash"));
		String actualMessage= successMessage.getText();
		//Assert.assertEquals(successMessage.getText(),"You logged into a secure area!" , "Message is not the same");
		Assert.assertTrue(actualMessage.contains("You logged into a secure area!"), "Message sucess is not the same as expected.\nActual Message: "+actualMessage);

		sleep(3000);

		driver.manage().window().maximize();

		
		
		driver.quit();
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

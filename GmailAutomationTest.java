package com.joct.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.joct.pages.ComposeEmail;
import com.joct.pages.LoginPage;
import com.joct.pages.Logout;

public class GmailAutomationTest {

	WebDriver driver;

	@Test(alwaysRun = true)
	@Parameters("browser")
	public void openBrowser(String browser) {
		if(browser.equals("chrome")) {
		System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		}
		else {
			System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		// maximize the window
		driver.manage().window().maximize();
		// load the web page
		driver.get("http://gmail.com");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@Test(dependsOnMethods = "openBrowser")
	@Parameters({ "username", "password"})
	public void loginGmail(String username,String password) throws Exception {
		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
		login.loginGmailApp(username,password);
	}

	@Test(dependsOnMethods = "loginGmail")
	public void composeEmail() throws InterruptedException {
		ComposeEmail compose = PageFactory.initElements(driver, ComposeEmail.class);
		compose.waitForInboxPage();
		compose.sendEmail();

	}

	@Test(dependsOnMethods = "composeEmail")
	public void logout() throws InterruptedException {
		Logout logout = PageFactory.initElements(driver, Logout.class);
		logout.gmailSignout();

	}
	
	@AfterClass
	public void closeBrowser(){
		driver.close();
	}
}

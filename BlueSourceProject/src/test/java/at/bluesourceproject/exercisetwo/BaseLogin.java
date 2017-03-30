package at.bluesourceproject.exercisetwo;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import common.HomepagePageObject;
import common.LoginPageObject;


public class BaseLogin {

	@Test
	public void loginTest() {
		
		//Set the properties file location.
		ResourceBundle file = ResourceBundle.getBundle("dataFile");
		
		// set the path to the chrome driver.
		System.setProperty("webdriver.chrome.driver",
				this.getClass().getResource("/drivers/chromedriver.exe").getPath());

		// instantiates a new chromedriver.
		WebDriver driver = new ChromeDriver();

		// navigate to the bluesource qa environment.
		LoginPageObject loginpage = new LoginPageObject(driver);
		loginpage.getBlueSource();

		// login.
		loginpage.successfulLogin(file.getString("baseUsername"));

		// verify that the user is logged into the homepage.
		HomepagePageObject homepage = new HomepagePageObject(driver);
		Assert.assertTrue(homepage.employeePageDisplays(), "The user did not login successfully, please investigate.");
		Reporter.log("The user logged in succesfully.<br>");

		// verify that the summary of paid time off is visible.
		Assert.assertTrue(homepage.confirmTimeOff(), "The 'Time Off' pane is not visible, please investigate.");
		Reporter.log("The 'Time Off' pane is visible, user is able to verify all vacation time.<br>");

		// verify that the general information pane is visible.
		Assert.assertTrue(homepage.confirmGeneralInfo(), "The 'General Information' pane is not visible, please investigate.");
		Reporter.log("The 'General Information' pane is visible, user is able to verify all general information.<br>");
		
		driver.quit();

	}

}

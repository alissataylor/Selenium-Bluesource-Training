package at.bluesourceproject.exercisetwo;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.BaseClass;
import common.LoginPageObject;
import common.TitlePageObject;

public class Title extends BaseClass {
	WebDriver driver;

	@Test
	public void failAddTitle() {
		// Set the properties file location.
		ResourceBundle empFile = ResourceBundle.getBundle("dataFile");

		// set the path to the chrome driver.
		System.setProperty("webdriver.chrome.driver",
				this.getClass().getResource("/drivers/chromedriver.exe").getPath());

		// instantiates a new chromedriver.
		driver = new ChromeDriver();

		// Navigate to Bluesource.
		LoginPageObject loginpage = new LoginPageObject(driver);
		loginpage.getBlueSource();

		// Login as an admin user.
		loginpage.successfulLogin(empFile.getString("adminUsername"));

		// navigate to the titles page.
		TitlePageObject title = new TitlePageObject(driver);
		title.navTitles();

		// verify that user navigated to the titles page.
		Assert.assertTrue(title.successTitlesNav(), "The user did not navigate to the departments page.");
		Reporter.log("The user successfully navigated to the departments page.");

		// click the 'new title' link.
		title.clickTitlesLink();

		// click the 'create title' button.
		title.clickCreateTitle();

		// verify that the error message displayed when leaving the 'Name' field
		// blank.
		Assert.assertTrue(title.titleErrorMessageDisplayed(),
				"The error message was not displayed, please investigate.");
		Reporter.log("The error message was displayed, new title not created.");

	}

	@Test(dependsOnMethods = "failAddTitle")
	public void addTitle() {

		// Set the properties file location.
		ResourceBundle empFile = ResourceBundle.getBundle("dataFile");

		// enter the new title name.
		TitlePageObject title = new TitlePageObject(driver);
		title.enterTitle(empFile.getString("newTitle"));

		// click 'create title.
		title.clickCreateTitle();

		// verify a success message appears after entering a new title.
		Assert.assertTrue(title.titleSuccessMessageDisplayed(),
				"The success message was not displayed, please investigate.");
		Reporter.log("The success message was displayed, new title was created.");

		// verify that the new title is in the list of current titles.
		Assert.assertTrue(title.titleCreated(empFile.getString("newTitle")),
				"The new titleis not listed in the title list.");
		Reporter.log("The title was listed on the page.");

	}

	@Test(dependsOnMethods = { "failAddTitle", "addTitle", })
	public void failUpdateTitle() {

		// Set the properties file location.
		ResourceBundle empFile = ResourceBundle.getBundle("dataFile");
		TitlePageObject title = new TitlePageObject(driver);

		// loop through the page to find the title previously created.
		title.updateTitle(empFile.getString("newTitle"));

		// verify that the user is on the 'edit title' page.
		Assert.assertTrue(title.updateTitleNav(),
				"The user did not navigate to the 'edit title' page, please investigate");
		Reporter.log("The user navigated to the 'edit title' page.");

		// delete the title name out of the field.
		title.deleteTitleName();

		// click the 'Update Title' button.
		title.clickCreateTitle();

		// verify the 'error' message appeared for the title not being
		// updated.
		Assert.assertTrue(title.titleErrorUpdateMessageDisplayed(),
				"The error message was not displayed, please investigate.");
		Reporter.log("The error message was displayed, title not updated.");

	}

	@Test(dependsOnMethods = { "failAddTitle", "addTitle", "failUpdateTitle" })
	public void updateTitle() {

		// Set the properties file location.
		ResourceBundle empFile = ResourceBundle.getBundle("dataFile");
		
		// enter new title name.
		TitlePageObject title = new TitlePageObject(driver);
		
		// delete the department name.
		title.deleteTitleName();
		title.enterNewTitleName(empFile.getString("newTitleName"));

		// click 'update department' button.
		title.clickCreateTitle();

		// verify that the success message appeared after updating the
		// department.
		Assert.assertTrue(title.updateTitleSuccessMessage(),
				"The success message was not displayed, please investigate.");
		Reporter.log("The success message was displayed, title updated.");

		// verify that the updated title is listed in the list of
		// departments.
		Assert.assertTrue(title.titleUpdated(empFile.getString("newTitleName")),
				"The updated title is not listed in the titles list.");
		Reporter.log("The title was updated and listed on the page.");
	}
	
	@Test(dependsOnMethods = { "failAddTitle", "addTitle", "failUpdateTitle", "updateTitle" })
	public void deleteTitle() {

			// Set the properties file location.
			ResourceBundle empFile = ResourceBundle.getBundle("dataFile");

			// loop through and locate the 'trash can' icon and click the icon.
			TitlePageObject title = new TitlePageObject(driver);
			title.deleteTitle(empFile.getString("newTitleName"));
			
			//accepts the java script pop up to delete the department.
			javaScriptPopUp(driver);
			
			//verify that the department deleted message appears at the top of the page.
			Assert.assertTrue(title.deleteTitleSuccessMessage(),
					"The success message was not displayed, please investigate.");
			Reporter.log("The success message was displayed, title was deleted.");
			
			//verify that the department has been deleted from the list.
			Assert.assertTrue(title.titleDeleted(empFile.getString("newTitleName")),
					"The updated title was listed in the titles list, please investigate.");
			Reporter.log("The title was not listed on the page, it was successfully deleted.");
			
		}
	}
	



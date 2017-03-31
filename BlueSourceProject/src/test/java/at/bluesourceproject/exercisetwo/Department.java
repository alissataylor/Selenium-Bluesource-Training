package at.bluesourceproject.exercisetwo;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import common.BaseClass;
import common.DepartmentPageObject;
import common.LoginPageObject;

public class Department extends BaseClass {
	WebDriver driver;

	@Test
	public void failAddDepartments() {

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

		// Navigate to the departments page.
		DepartmentPageObject departments = new DepartmentPageObject(driver);
		departments.navDepartments();

		// verify navigated to the departments page.
		Assert.assertTrue(departments.successDeptNav(), "The user did not navigate to the departments page.");
		Reporter.log("The user successfully navigated to the departments page.");

		// click the 'add department' link.
		departments.addDeparment();

		// click the 'create department' button.
		departments.clickCreateDepartment();

		// verify that the error message displayed when leaving the 'Name' field
		// blank.
		Assert.assertTrue(departments.deptErrorMessageDisplayed(),
				"The error message was not displayed, please investigate.");
		Reporter.log("The error message was displayed, department not created.");
	}

	@Test(dependsOnMethods = "failAddDepartments")
	public void createDepartment() {

		// Set the properties file location.
		ResourceBundle empFile = ResourceBundle.getBundle("dataFile");

		// enter the department name.
		DepartmentPageObject departments = new DepartmentPageObject(driver);
		departments.enterDeptName(empFile.getString("departmentName"));

		// click 'create department' button.
		departments.clickCreateDepartment();

		// Verify a success message appears after creating a new department.
		Assert.assertTrue(departments.deptSuccessMessageDisplayed(),
				"The success message was not displayed, please investigate.");
		Reporter.log("The success message was displayed, department was created.");

		// Verify the new department is in the list of current departments.
		Assert.assertTrue(departments.departmentCreated(empFile.getString("departmentName")),
				"The new department is not listed in the departments list.");
		Reporter.log("The department was listed on the page.");

	}

	@Test(dependsOnMethods = { "failAddDepartments", "createDepartment" })
	public void failUpdateDept() {

		// Set the properties file location.
		ResourceBundle empFile = ResourceBundle.getBundle("dataFile");
		DepartmentPageObject departments = new DepartmentPageObject(driver);

		// loop through the table to find the department previously created.
		departments.updateDepartment(empFile.getString("departmentName"));

		// verify that the user is on the 'edit department' page.
		Assert.assertTrue(departments.updateDepartmentNav(),
				"The user did not navigate to the 'edit department' page, please investigate");
		Reporter.log("The user navigated to the 'edit department' page.");

		// delete the department name.
		departments.deleteDepartmentName();

		// click the 'create department' button.
		departments.clickCreateDepartment();

		// verify the 'error' message appeared for the department not being
		// updated.
		Assert.assertTrue(departments.deptErrorMessageDisplayed(),
				"The error message was not displayed, please investigate.");
		Reporter.log("The error message was displayed, department not updated.");
	}

	@Test(dependsOnMethods = { "failAddDepartments", "createDepartment", "failUpdateDept" })
	public void updateDepartment() {

		// Set the properties file location.
		ResourceBundle empFile = ResourceBundle.getBundle("dataFile");

		// enter new department name.
		DepartmentPageObject departments = new DepartmentPageObject(driver);
		// delete the department name.
		departments.deleteDepartmentName();
		departments.enterNewDeptName(empFile.getString("newDepartmentName"));

		// click 'update department' button.
		departments.clickCreateDepartment();

		// verify that the success message appeared after updating the
		// department.
		Assert.assertTrue(departments.updateDepartmentSuccessMessage(),
				"The success message was not displayed, please investigate.");
		Reporter.log("The success message was displayed, department updated.");

		// verify that the updated department is listed in the list of
		// departments.
		Assert.assertTrue(departments.departmentCreated(empFile.getString("newDepartmentName")),
				"The updated department is not listed in the departments list.");
		Reporter.log("The department was updated and listed on the page.");
	}

	@Test(dependsOnMethods = { "failAddDepartments", "createDepartment", "failUpdateDept", "updateDepartment" })
	public void deleteDepartment() {

		// Set the properties file location.
		ResourceBundle empFile = ResourceBundle.getBundle("dataFile");

		// loop through and locate the 'trash can' icon and click the icon.
		DepartmentPageObject departments = new DepartmentPageObject(driver);
		departments.deleteDepartment(empFile.getString("newDepartmentName"));
		
		//accepts the java script pop up to delete the department.
		javaScriptPopUp(driver);
		
		//verify that the department deleted message appears at the top of the page.
		Assert.assertTrue(departments.deleteDepartmentSuccessMessage(),
				"The success message was not displayed, please investigate.");
		Reporter.log("The success message was displayed, department was deleted.");
		
		//verify that the department has been deleted from the list.
		Assert.assertTrue(departments.departmentDeleted(empFile.getString("newDepartmentName")),
				"The updated department was listed in the departments list, please investigate.");
		Reporter.log("The department was not listed on the page, it was successfully deleted.");
		
		driver.quit();
	}
}

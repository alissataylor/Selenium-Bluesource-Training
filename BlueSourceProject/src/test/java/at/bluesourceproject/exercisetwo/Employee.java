package at.bluesourceproject.exercisetwo;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import common.EmployeesPageObject;
import common.LoginPageObject;

public class Employee {
	WebDriver driver;
	

	@Test
	public void createEmployee() {

		// Set the properties file location.
		ResourceBundle empFile = ResourceBundle.getBundle("dataFile");

		// set the path to the chrome driver.
		System.setProperty("webdriver.chrome.driver",
				this.getClass().getResource("/drivers/chromedriver.exe").getPath());

		// instantiates a new chromedriver.
		WebDriver driver = new ChromeDriver();

		// Navigate to Bluesource.
		LoginPageObject loginpage = new LoginPageObject(driver);
		loginpage.getBlueSource();

		// Login as an admin user.
		loginpage.successfulLogin(empFile.getString("adminUsername"));

		// navigate to create a new employee.
		EmployeesPageObject createEmployee = new EmployeesPageObject(driver);
		createEmployee.clickEmployees();
		createEmployee.clickAdd();

		// Verifies that the 'add employee' form is displayed.
		Assert.assertTrue(createEmployee.addEmployeeTableDisplays(), "The employee table is not visible.");
		Reporter.log("The 'add employee' form is displayed.");

		// Fills in the required data fields in the new employee form.
		createEmployee.createEmployee(empFile.getString("newUsername"), empFile.getString("firstName"),
				empFile.getString("lastName"), (empFile.getString("manager")));

		// Verify success message displays that the employee was successfully added.
		Assert.assertTrue(createEmployee.successMessageDisplays(),
				"The new employee was not created, please investigate.");
		Reporter.log("The new employee was created.");

		// Verify that the new employee is displayed in the list of employees.
		Assert.assertTrue(createEmployee.newEmployeeIsDisplayed(empFile.getString("employeeName"),
				empFile.getString("firstName"), empFile.getString("lastName")),
				"The new employee is not displayed in the employee's table");
		Reporter.log("The employee was not found in the employee table.");
		
		driver.quit();

	}

	@Test(dependsOnMethods = "createEmployee")
	public void employeeReportsToMgr() {

		// Set the properties file location.
		ResourceBundle empFile = ResourceBundle.getBundle("dataFile");

		// set the path to the chrome driver.
		System.setProperty("webdriver.chrome.driver",
				this.getClass().getResource("/drivers/chromedriver.exe").getPath());

		// instantiates a new chromedriver.
		WebDriver driver = new ChromeDriver();

		// Navigate to Bluesource.
		LoginPageObject loginpage = new LoginPageObject(driver);
		loginpage.getBlueSource();

		// Login as a manager.
		loginpage.successfulLogin(empFile.getString("mgrUsername"));

		// select the managers 'direct reports' button in the drop down menu.
		EmployeesPageObject verifyEmployee = new EmployeesPageObject(driver);
		verifyEmployee.selectManagerReports();

		// verifies that 'direct' is selected in the dropdown menu.
		Assert.assertTrue(verifyEmployee.directReportsSelected(), "The direct reports option was not selected.");
		Reporter.log("Direct reports was selected in the drop down menu on the managers page.");

		// Searches for the employee name.
		verifyEmployee.employeeSearch(empFile.getString("employeeName"));

		// Verify that the employee is listed in the manager's view that they
		// are associated with.
		Assert.assertTrue(
				verifyEmployee.empListedInMgrTable(empFile.getString("firstName"), empFile.getString("lastName")),
				"The new employee is not displayed in the managers table");
		Reporter.log("The employee was found in the managers table.");
		
		driver.quit();
	}
}

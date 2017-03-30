package common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DepartmentPageObject {

	WebDriver driver;

	// Constructor
	public DepartmentPageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		// setting the path to the chromedriver. Do I need this here?
		System.setProperty("webdriver.chrome.driver",
				this.getClass().getResource("/drivers/chromedriver.exe").getPath());
	}

	// ------------------------------
	// Variable Declaration
	// ------------------------------
	@FindBy(xpath = "/html/body/header/div/nav/ul/li[1]/ul/li/a[1]")
	private WebElement lnkDeparments;

	@FindBy(xpath = "/html/body/header/div/nav/ul/li[1]/a")
	private WebElement lnkAdmin;

	@FindBy(linkText = "Add Department")
	private WebElement addDepartment;

	@FindBy(xpath = "//*[@id='content']/h1")
	private WebElement deptLogo;

	@FindBy(xpath = "//*[@id='content']/div")
	private WebElement deptError;

	@FindBy(name = "commit")
	private WebElement btnCreateDepartment;

	@FindBy(id = "department_name")
	private WebElement deptName;

	@FindBy(xpath = "//*[@id='content']/div[1]")
	private WebElement deptSuccess;

	@FindBy(xpath = "//*[@id='content']/div[1]")
	private WebElement deptUpdated;

	@FindBy(xpath = "//*[@id='content']/div[1]")
	private WebElement deptDeleted;

	// ------------------------------
	// Methods/Actions
	// ------------------------------

	// click the departments link.
	public void clickDepartments() {
		lnkDeparments.click();
	}

	// click the admin drop down.
	public void adminDropDown() {
		lnkAdmin.click();
	}

	// wrapper method to navigate to the departments page.
	public void navDepartments() {
		adminDropDown();
		clickDepartments();
	}

	// click add department.
	public void addDeparment() {
		addDepartment.click();
	}

	// verify navigated to the departments page.
	public boolean successDeptNav() {
		if (deptLogo.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// verify that the error message displayed for the fail to add a department.
	public boolean deptErrorMessageDisplayed() {
		if (deptError.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// click the 'create department' button.
	public void clickCreateDepartment() {
		btnCreateDepartment.click();
	}

	// enter the department name.
	public void enterDeptName(String departmentName) {
		deptName.sendKeys(departmentName);
	}

	// enter the new department name.
	public void enterNewDeptName(String newDepartmentName) {
		deptName.sendKeys(newDepartmentName);
	}

	// verify that the success message displayed after adding a department.
	public boolean deptSuccessMessageDisplayed() {
		if (deptSuccess.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// Loop through the list of departments to see if the department was
	// successfully created.
	public boolean departmentCreated(String departmentName) {
		// sets the flag to false.
		boolean flag1 = false;

		// builds a list of elements with the class name 'list group item'.
		List<WebElement> depts = driver.findElements(By.className("list-group-item"));

		// loops through the list of departments and looks for the department
		// that was created.
		for (WebElement dept : depts) {
			if (dept.getText().trim().contains(departmentName)) {
				System.out.println("Successfully located the new department.");
				flag1 = true;
				break;
			}
		}
		// returns true if the department was located, false if not.
		if (flag1) {
			return true;
		} else {
			return false;
		}

	}

	// loops through the page to find the department created and click the
	// 'pencil' to edit.
	public boolean updateDepartment(String departmentName) {

		// sets the flag to false.
		boolean flag1 = false;

		// builds a list of elements with the class name 'list group item'.
		List<WebElement> depts = driver.findElements(By.className("list-group-item"));

		// loops through the list of departments and looks for the department
		// that was created.
		for (WebElement dept : depts) {
			if (dept.getText().trim().contains(departmentName)) {
				System.out.println("Successfully located the new department.");
				List<WebElement> objs = dept.findElements(By.tagName("a"));
				for (WebElement obj : objs) {
					if (obj.getAttribute("href").contains("edit")) {
						System.out.println("Successfully located the 'edit' link inside the object.");
						obj.click();
						flag1 = true;
						break;
					}
				}
			}
			if (flag1) {
				break;
			}
		}
		// returns true if the department was located, false if not.
		if (flag1) {
			return true;
		} else {
			return false;
		}

	}

	// delete the department name out of the department field.
	public void deleteDepartmentName() {
		deptName.clear();
	}

	// verify that you navigated to the 'edit department' page.
	public boolean updateDepartmentNav() {
		if (btnCreateDepartment.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// verify that the success message displayed after updating a department.
	public boolean updateDepartmentSuccessMessage() {
		if (deptUpdated.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean deleteDepartment(String departmentName) {
		// sets the flag to false.
		boolean flag1 = false;

		// builds a list of elements with the class name 'list group item'.
		List<WebElement> depts = driver.findElements(By.className("list-group-item"));

		// loops through the list of departments and looks for the department
		// that was created.
		for (WebElement dept : depts) {
			if (dept.getText().trim().contains(departmentName)) {
				System.out.println("Successfully located the new department.");
				List<WebElement> objs = dept.findElements(By.tagName("span"));
				for (WebElement obj : objs) {
					if (obj.getAttribute("class").contains("trash")) {
						System.out.println("Successfully located the 'delete' link inside the object.");
						obj.click();
						flag1 = true;
						break;
					}
				}
			}
			if (flag1) {
				break;
			}
		}
		// returns true if the department was located, false if not.
		if (flag1) {
			return true;
		} else {
			return false;
		}
	}

	// checks for the department deleted success message.
	public boolean deleteDepartmentSuccessMessage() {
		if (deptDeleted.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean departmentDeleted(String departmentName) {
		// sets the flag to false.
		boolean flag1 = false;

		// builds a list of elements with the class name 'list group item'.
		List<WebElement> depts = driver.findElements(By.className("list-group-item"));

		// loops through the list of departments and looks for the department
		// that was created.
		for (WebElement dept : depts) {
			if (dept.getText().trim().contains(departmentName)) {
				System.out.println("Located the department, the department was not expected to be listed. Please investigate.");
				flag1 = true;
				break;
			}
		}
		// returns true if the department was located, false if not.
		if (!flag1) {
			return true;
		} else {
			return false;
		}

	}
	
	public void javaScriptPopUp() {
		driver.switchTo().alert().accept();
	}
}

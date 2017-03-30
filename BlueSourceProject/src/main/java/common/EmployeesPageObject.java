package common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class EmployeesPageObject {

	WebDriver driver;

	// Constructor
	public EmployeesPageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		// setting the path to the chromedriver. Do I need this here?
		System.setProperty("webdriver.chrome.driver",
				this.getClass().getResource("/drivers/chromedriver.exe").getPath());
	}
	// ------------------------------
	// Variable Declaration
	// ------------------------------

	@FindBy(xpath = "//*[@id='all-content']/div[2]/div/div[2]/button")
	private WebElement btnAdd;

	@FindBy(id = "employee_username")
	private WebElement txtNewUsername;

	@FindBy(id = "employee_first_name")
	private WebElement txtFirstName;

	@FindBy(id = "employee_last_name")
	private WebElement txtLastName;

	@FindBy(name = "commit")
	private WebElement btnCreateEmployee;

	@FindBy(linkText = "Employees")
	private WebElement lnkEmployees;

	@FindBy(xpath = "//*[@id='all-content']/div[1]")
	private WebElement msgEmployeeCreated;

	@FindBy(id = "search-bar")
	private WebElement txtSearchBar;

	@FindBy(xpath = "//*[@id='modal_1']/div/div")
	private WebElement modAddEmployee;

	@FindBy(xpath = "//*[@id='resource-content']/div[1]/table")
	private WebElement empSearchTable;
	
	@FindBy(id = "filter_btn_text")
	private WebElement drpReports;
	
	@FindBy(id = "filter_btn_text")
	private WebElement drpDirect;
	
	@FindBy(xpath = "//*[@id='resource-content']/div[1]")
	private WebElement mgrTable;
	

	// ------------------------------
	// Methods/Actions
	// ------------------------------

	// click the employee link in the navigation bar.
	public void clickEmployees() {
		lnkEmployees.click();
	}

	// clicks the add button.
	public void clickAdd() {
		btnAdd.click();
	}

	// enter's the new employee's username into the username field.
	public void enterNewUsername(String newUsername) {
		txtNewUsername.sendKeys(newUsername);
	}

	// enter's the new employee's first name into the first name field.
	public void enterFirstName(String firstName) {
		txtFirstName.sendKeys(firstName);
	}

	// enter's the new employee's last name into the last name field.
	public void enterLastName(String lastName) {
		txtLastName.sendKeys(lastName);
	}

	// enter's the new employee's manager.
	public void enterManager(String manager) {
		Select drpManager = new Select(driver.findElement(By.id("employee_manager_id")));
		drpManager.selectByValue(manager);
	}

	// clicks the create employee button.
	public void clickCreateEmployee() {
		btnCreateEmployee.click();
	}

	// Verify success message displays that the employee was successfully
	// added.
	public boolean successMessageDisplays() {
		if (msgEmployeeCreated.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean addEmployeeTableDisplays() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(modAddEmployee));
		if (modAddEmployee.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// Wrapper method to create an employee.
	public void createEmployee(String newUsername, String firstName, String lastName, String manager) {
		enterNewUsername(newUsername);
		enterFirstName(firstName);
		enterLastName(lastName);
		enterManager(manager);
		clickCreateEmployee();
	}

	// Search for an employee in the search bar.
	public void employeeSearch(String employeeName) {
		Actions actions = new Actions(driver);
		actions.moveToElement(txtSearchBar);
		actions.click();
		actions.sendKeys(employeeName);
		actions.build().perform();
	}

	//Loop through the table of employee's to see if the employee was successfully created.
	public boolean employeeCreated(String firstName, String lastName) {
		// wait for the employee search table to load.
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(empSearchTable));

		// sets the flag to false.
		boolean flag1 = false;
		boolean flag2 = false;

		// Calculates the number of rows in the table.
		WebElement employeeTable = driver.findElement(By.xpath("//*[@id='resource-content']/div[1]/table"));
		List<WebElement> rows = employeeTable.findElements(By.tagName("tr"));

		// Loop through the table to see if the first name and last name are
		// stored in the table.
		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (WebElement cell : cells) {
				if (cell.getText().trim().equals(firstName)) {
					System.out.println(
							"Successfully located the desired First Name");
					flag1 = true;
				}
				if (cell.getText().trim().equals(lastName)) {
					System.out.println("Successfully located the desired Last Name");
					flag2 = true;
				}
				if (flag1 & flag2) {
					System.out.println("Successfully located the desired First and Last Name, exit loop.");
					break;
				}
			}
			if (flag1 & flag2) {
				System.out.println("Successfully located the desired First and Last Name, exit loop.");
				break;
			}
		}
		if (flag1 & flag2) {
			return true;
		} else {
			return false;
		}
	}

	// Wrapper method to verify the employee was created.
	public boolean newEmployeeIsDisplayed(String employeeName, String firstName, String lastName) {
		employeeSearch(employeeName);
		return employeeCreated(firstName, lastName);
	}

	//Selects the 'direct' reports in the drop down menu when logged in as a manager.
	public void selectManagerReports() {
		drpReports.click();
		Boolean isFound = false;
		
		List<WebElement> reportsDrpDown = driver.findElements(By.className("filter_list_item"));
		
		for (WebElement item : reportsDrpDown) {
			if (item.getText().contains("Direct")){
				item.click();
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			Reporter.log("The 'Direct' option was not found in the dropdown list.");
		}
		
	}
	
	//Verifies that the direct reports option was selected.
	public boolean directReportsSelected() {
		if (drpDirect.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean empListedInMgrTable(String firstName, String lastName) {
		// wait for the employee search table to load.
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(mgrTable));

		// sets the flag to false.
		boolean flag1 = false;
		boolean flag2 = false;

		// Calculates the number of rows in the table.
		WebElement managerTable = driver.findElement(By.xpath("//*[@id='resource-content']/div[1]"));
		List<WebElement> rows = managerTable.findElements(By.tagName("tr"));

		// Loop through the table to see if the first name and last name are
		// stored in the table.
		for (WebElement row : rows) {
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (WebElement cell : cells) {
				if (cell.getText().trim().equals(firstName)) {
					System.out.println(
							"Successfully located the desired First Name.");
					flag1 = true;
				}
				if (cell.getText().trim().equals(lastName)) {
					System.out.println("Successfully located the desired Last Name.");
					flag2 = true;
				}
				if (flag1 & flag2) {
					System.out.println("Successfully located the desired First and Last Name, exit loop.");
					break;
				}
			}
			if (flag1 & flag2) {
				System.out.println("Successfully located the desired First and Last Name, exit loop.");
				break;
			}
		}
		if (flag1 & flag2) {
			return true;
		} else {
			return false;
		}
	}
}

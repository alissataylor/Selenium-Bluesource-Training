package common;

import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageObject {

	// ------------------------
	// Variable Declaration
	// ------------------------

	WebDriver driver;

	@FindBy(id = "employee_username")
	private WebElement txtUsername;

	@FindBy(id = "employee_password")
	private WebElement txtPassword;

	@FindBy(name = "commit")
	private WebElement btnLogin;

	@FindBy(linkText = "Logout")
	private WebElement lnkLogout;

	// Constructor
	public LoginPageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// -------------------------
	// Methods/Actions
	// -------------------------

	// Set the properties file location.
	ResourceBundle data = ResourceBundle.getBundle("dataFile");
	

	// Navigates to the bluesource QA page.
	public void getBlueSource() {
		driver.get(data.getString("URL"));
	}

	// Entering the username into the username field.
	public void enterUsername(String username) {
		txtUsername.sendKeys(username);
	}

	// Entering the password into the password field.
	public void enterPassword() {
		txtPassword.sendKeys("1234");
	}

	// Clicking the login button.
	public void clickLogin() {
		btnLogin.click();
	}

	// Method for a successful login.
	public void successfulLogin(String username) {
		enterUsername(username);
		enterPassword();
		clickLogin();
	}

	// Clicking the logout button.
	public void clickLogout() {
		lnkLogout.click();
	}

	// Method for a logging out.
	public void logout() {
		clickLogout();

	}
}

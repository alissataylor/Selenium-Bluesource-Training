package common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomepagePageObject {

	WebDriver driver;

	public HomepagePageObject(WebDriver driver) {

		// need John to teach me what this is and how it works. Class
		// constructor, but what is that?
		this.driver = driver;
		PageFactory.initElements(driver, this);

		// setting the path to the chromedriver. Do I need this here?
		System.setProperty("webdriver.chrome.driver",
				this.getClass().getResource("/drivers/chromedriver.exe").getPath());

	}

	// ------------------------------
	// Variable Declaration
	// ------------------------------

	@FindBy(id = "help-btn")
	private WebElement btnHelp;

	@FindBy(xpath = "//*[@id='accordion']/div/div[7]/h4")
	private WebElement lblGeneralInfo;

	@FindBy(xpath = "//*[@id='accordion']/div/div[5]/h4")
	private WebElement lblTimeOff;

	@FindBy(xpath = "//*[@id='content']/h1")
	private WebElement lblLogin;

	// ------------------------------
	// Methods/Actions
	// ------------------------------

	// Checks to see if the employee page is displayed.
	public boolean employeePageDisplays() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(btnHelp));
		if (btnHelp.isDisplayed()) {
			return true;
		} else {
			return false;
		}

	}

	// Checks to see if the Time Off banner is displayed.
	public boolean confirmTimeOff() {
		if (lblTimeOff.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// Checks to see if the General Info banner is displayed.
	public boolean confirmGeneralInfo() {
		if (lblGeneralInfo.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// Verifying that the user is logged out.
	public boolean ConfirmLogout() {
		if (lblLogin.isDisplayed()) {
			return true;
		} else {
			return false;
		}

	}
}

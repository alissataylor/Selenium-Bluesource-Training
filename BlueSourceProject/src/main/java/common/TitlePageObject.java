package common;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TitlePageObject {

	WebDriver driver;

	// Constructor
	public TitlePageObject(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

		// setting the path to the chromedriver. Do I need this here?
		System.setProperty("webdriver.chrome.driver",
				this.getClass().getResource("/drivers/chromedriver.exe").getPath());
	}

	// ------------------------------
	// Variable Declaration
	// ------------------------------

	@FindBy(xpath = "//*[@id='content']/div")
	private WebElement titleError;

	@FindBy(xpath = "/html/body/header/div/nav/ul/li[1]/a")
	private WebElement lnkAdmin;

	@FindBy(linkText = "Titles")
	private WebElement lnkTitles;

	@FindBy(css = "#content > h1")
	private WebElement titleLogo;

	@FindBy(linkText = "New Title")
	private WebElement titlesLink;

	@FindBy(name = "commit")
	private WebElement btnCreateTitle;

	@FindBy(id = "title_name")
	private WebElement titleName;

	@FindBy(css = "#content > div")
	private WebElement titleSuccess;

	@FindBy(css = "#content > h1")
	private WebElement editTitle;

	@FindBy(css = "#content > div > ul > li")
	private WebElement titleUpdateError;

	@FindBy(css = "#content > div")
	private WebElement titleUpdated;

	@FindBy(xpath = "//*[@id='content']/div")
	private WebElement titleDeleted;

	// ------------------------------
	// Methods/Actions
	// ------------------------------

	// click the admin drop down.
	public void adminDropDown() {
		lnkAdmin.click();
	}

	// click the 'titles' navigation link.
	public void clickTitles() {
		lnkTitles.click();
	}

	// wrapper method to navigate to the titles page.
	public void navTitles() {
		adminDropDown();
		clickTitles();
	}

	// verify that the error message displayed for the fail to add a title.
	public boolean titleErrorMessageDisplayed() {
		if (titleError.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// verify navigated to the titles page.
	public boolean successTitlesNav() {
		if (titleLogo.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// click 'new title'.
	public void clickTitlesLink() {
		titlesLink.click();
	}

	// click the 'Create Title' button.
	public void clickCreateTitle() {
		btnCreateTitle.click();
	}

	// enter a new title name.
	public void enterTitle(String newTitle) {
		titleName.sendKeys(newTitle);
	}

	// verify that the success message displayed after adding a new title.
	public boolean titleSuccessMessageDisplayed() {
		if (titleSuccess.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// Loop through the list of titles to see if the title was
	// successfully created.
	public boolean titleCreated(String newTitle) {
		// sets the flag to false.
		boolean flag1 = false;

		// builds a list of elements with the tag name 'td'.
		List<WebElement> titles = driver.findElements(By.tagName("td"));

		// loops through the list of titles and looks for the title
		// that was created.
		for (WebElement title : titles) {
			if (title.getText().trim().contains(newTitle)) {
				System.out.println("Successfully located the new title.");
				flag1 = true;
				break;
			}
		}
		// returns true if the title was located, false if not.
		if (flag1) {
			return true;
		} else {
			return false;
		}

	}

	// loops through the page to find the department created and click the
	// 'pencil' to edit.
	public boolean updateTitle(String newTitle) {

		// sets the flag to false.
		boolean flag1 = false;

		// builds a list of elements with the class name 'list group item'.
		List<WebElement> titles = driver.findElements(By.tagName("td"));

		// loops through the list of departments and looks for the department
		// that was created.
		for (WebElement title : titles) {
			if (title.getText().trim().contains(newTitle)) {
				System.out.println("Successfully located the new title.");
				List<WebElement> objs = title.findElements(By.tagName("a"));
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
		// returns true if the title was located, false if not.
		if (flag1) {
			return true;
		} else {
			return false;
		}

	}

	// verify that you navigated to the 'edit title' page.
	public boolean updateTitleNav() {
		if (editTitle.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// delete the department name out of the title field.
	public void deleteTitleName() {
		titleName.clear();
	}

	// verify that the error message displayed for the fail to add a title.
	public boolean titleErrorUpdateMessageDisplayed() {
		if (titleUpdateError.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// enter the new title name.
	public void enterNewTitleName(String newTitleName) {
		titleName.sendKeys(newTitleName);
	}

	// verify that the success message displayed after updating a department.
	public boolean updateTitleSuccessMessage() {
		if (titleUpdated.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// Loop through the list of departments to see if the department was
	// successfully created.
	public boolean titleUpdated(String newTitleName) {
		// sets the flag to false.
		boolean flag1 = false;

		// builds a list of elements with the tag name 'td'.
		List<WebElement> titles = driver.findElements(By.tagName("td"));

		// loops through the list of departments and looks for the title
		// that was created.
		for (WebElement title : titles) {
			if (title.getText().trim().contains(newTitleName)) {
				System.out.println("Successfully located the new title.");
				flag1 = true;
				break;
			}
		}
		// returns true if the title was located, false if not.
		if (flag1) {
			return true;
		} else {
			return false;
		}

	}

	public boolean deleteTitle(String newTitle) {
		// sets the flag to false.
		boolean flag1 = false;

		// builds a list of elements with the tag name 'td'.
		List<WebElement> titles = driver.findElements(By.tagName("td"));

		// loops through the list of titles and looks for the title
		// that was created.
		for (WebElement title : titles) {
			if (title.getText().trim().contains(newTitle)) {
				System.out.println("Successfully located the new title.");
				List<WebElement> objs = title.findElements(By.tagName("span"));
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

	// checks for the title deleted success message.
	public boolean deleteTitleSuccessMessage() {
		if (titleDeleted.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean titleDeleted(String newTitle) {
		// sets the flag to false.
		boolean flag1 = false;

		// builds a list of elements with the tag name 'td'.
		List<WebElement> titles = driver.findElements(By.tagName("td"));

		// loops through the list of titles and looks for the title
		// that was created.
		for (WebElement title : titles) {
			if (title.getText().trim().contains(newTitle)) {
				System.out.println(
						"Located the title, the title was not expected to be listed. Please investigate.");
				flag1 = true;
				break;
			}
		}
		// returns true if the title was located, false if not.
		if (!flag1) {
			return true;
		} else {
			return false;
		}

	}
}

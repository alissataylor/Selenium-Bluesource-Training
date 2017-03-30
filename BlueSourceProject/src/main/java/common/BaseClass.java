package common;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

public class BaseClass {

	protected WebDriver driver = null;

	//accepts the java script pop up when deleting a department or title.
	public void javaScriptPopUp() {
		driver.switchTo().alert().accept();
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
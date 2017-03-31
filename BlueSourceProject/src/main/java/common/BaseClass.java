package common;

import org.openqa.selenium.WebDriver;


public class BaseClass {

	protected WebDriver driver = null;

	//accepts the java script pop up when deleting a department or title.
	public void javaScriptPopUp(WebDriver driver) {
		driver.switchTo().alert().accept();
	}

	//@AfterClass
	//public void tearDown() {
		//driver.quit();
	//}
}
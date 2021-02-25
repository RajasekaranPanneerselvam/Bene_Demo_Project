package afta.src.test.java.com.TAFrameworkJAVA.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Test {

	public void search(WebDriver driver) {
		driver.findElement(By.cssSelector("input[value='SEARCH'], input[type='submit']")).click();
	}
}

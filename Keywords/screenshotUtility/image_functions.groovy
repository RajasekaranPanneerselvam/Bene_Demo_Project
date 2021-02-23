package screenshotUtility

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import javax.imageio.ImageIO

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import java.awt.image.BufferedImage;


import ru.yandex.qatools.ashot.comparison.ImageDiffer
import ru.yandex.qatools.ashot.comparison.ImageDiff
import groovy.ui.SystemOutputInterceptor

import com.kms.katalon.core.webui.common.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.*;
import com.kms.katalon.core.webui.driver.DriverFactory
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.Screenshot

import internal.GlobalVariable

public class image_functions {

	@Keyword
	public void takeWebElementScreenshot(TestObject object) {
		println("0000")
		WebElement element = WebUiCommonHelper.findWebElement(object, 30)
		WebDriver driver = DriverFactory.getWebDriver();

		println("1111")
		Screenshot screenshot = new AShot().takeScreenshot(driver,element)
		ImageIO.write(screenshot.getImage(), "PNG", new File(System.getProperty("user.dir") + "/Screenshots/ElementScreenshot.png"))
		println("2222")
	}

	@Keyword
	public boolean compareImages(TestObject object) {
		println("3333")
		WebElement element = WebUiCommonHelper.findWebElement(object, 30)
		WebDriver driver = DriverFactory.getWebDriver();

		BufferedImage expectedImage = ImageIO.read(new File(System.getProperty("user.dir") + "\\Screenshots\\ElementScreenshot.png"));
		Screenshot actuallogoImage = new AShot().takeScreenshot(driver,element)
		BufferedImage actual_image = actuallogoImage.getImage();

		ImageDiffer imgDiffer = new ImageDiffer();
		ImageDiff diff  = imgDiffer.makeDiff(expectedImage, actual_image)

		print("Printing the diff : ")
		print(diff.hasDiff())
		return diff.hasDiff()
	}
}
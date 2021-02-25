package com.TAFrameworkJAVA.support;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.internal.WrapsElement;

import net.sourceforge.tess4j.Tesseract;

/**
 * Wrapper for reading text from the image which is present in a web site or as a file.
 */
public class OCRTextReader {

	/**
	 * Wrapper to read the text present in a image which is a web element.
	 * 
	 * @param webElement
	 *            : WebElement of the image
	 * @return textFromImage
	 *  		  : Text that is decoded from the image in web.
	 */
	public static String CaptureTextFromImageInWebElement(WebElement webElement)
            throws Exception {
         
        // Capture the image of the driver.
		File imageOfDriver = CaptureTheImageOfDriver(webElement);
        
		// Capture the image of the WebElement.
		imageOfDriver = CaptureTheImageOfWebElement(webElement, imageOfDriver);
        
		// Get the text from the file using Tesseract doOCR.
     	String textFromImage = GetTextFromImageCapturedBySelenium(imageOfDriver);
        
     	// Return the text in the File.
        return textFromImage;
    }
	
	/**
	 * Wrapper to capture the image of browser view.
	 * 
	 * @param webElement
	 *            : WebElement of the image
	 * @return imageOfWebview
	 *  		  : Instance of the image captured.
	 */
	private static File CaptureTheImageOfDriver(WebElement webElement)
	{
		// Get the WebDriver of the WebElement
        WebDriver webDriver;
		if(WrapsElement.class.isAssignableFrom(webElement.getClass()))
        	  webDriver = ((WrapsDriver)((WrapsElement)webElement).getWrappedElement()).getWrappedDriver();
        	else
        	  webDriver = ((WrapsDriver)webElement).getWrappedDriver();
        
        // Get the entire screenshot from the driver of passed WebElement
        File imageOfWebview = ((TakesScreenshot) webDriver)
                .getScreenshotAs(OutputType.FILE);
        
        // Return the instance of the image created from the driver.
        return imageOfWebview;
	}
	
	/**
	 * Wrapper to capture the image of web element.
	 * 
	 * @param webElement
	 *            : WebElement of the image
	 * @param imageOfWebview
	 *            : Instance of the image captured from web view.           
	 * @return imageOfWebview
	 *  		  : Instance of the image of web element.
	 */
	private static File CaptureTheImageOfWebElement(WebElement webElement, File imageOfWebview) throws Exception
	{
		// Create an instance of buffered image from captured screenshot.
        BufferedImage sourceImage = ImageIO.read(imageOfWebview);
 
        // Get the width and height of the WebElement.
        int width = webElement.getSize().getWidth();
        int height = webElement.getSize().getHeight();
 
        // Create a rectangle using width and height.
        Rectangle rectangle = new Rectangle(width, height);
 
        // Get the location of WebElement in a Point.
        Point webElementLocation = webElement.getLocation();
 
        // Create image  for element using its location and size.
        BufferedImage imageOfWebElement = sourceImage.getSubimage(
        		webElementLocation.getX(), webElementLocation.getY(), 
        		rectangle.width, rectangle.height);
 
        // Write back the image data for element in File object.
        ImageIO.write(imageOfWebElement, "png", imageOfWebview);
        
        // Return the instance of the image which has the web element.
        return imageOfWebview;
	}
	
	/**
	 * Wrapper to read the text in image captured from web element.
	 * 
	 * @param imageFile
	 *            : Image of the web element.
	 * @return textFromImage
	 *  		  : Text that is decoded from the image in web.
	 */
	private static String GetTextFromImageCapturedBySelenium(File imageFile) throws Exception {
		
        // Create an instance of Tesseract class.
        Tesseract instance = new Tesseract();
        
        // Set the language and path to store the temp files(screenshot).
        instance.setDatapath(Paths.get("").toAbsolutePath().toString()+ "\\src\\main\\resources\\OCRtestdata");
        instance.setLanguage("eng");
        
        // Retrieve the text from image captured by Selenium.
        String textFromImage = instance.doOCR(imageFile);
        
        // Returns the text from the file.
        return textFromImage;
        
    }
	
	/**
	 * Wrapper to read the text in images which are stored as files.
	 * 
	 * @param filePath
	 *            : Path of the file.
	 * @return textFromFile
	 *  		  : Text that is decoded from the image.
	 */
	public static String CaptureTextFromFiles(String filePath) throws Exception 
	{
		// Create an instance of the file class.
		File fileObject = new File( filePath );
		
		// Get the text from the file using Tesseract doOCR.
		String textFromFile = GetTextFromImageCapturedBySelenium(fileObject);
		
        // Return the text from the File.
        return textFromFile;
	}
}

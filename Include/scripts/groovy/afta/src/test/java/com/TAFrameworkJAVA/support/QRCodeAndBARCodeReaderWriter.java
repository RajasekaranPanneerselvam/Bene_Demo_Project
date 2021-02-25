package com.TAFrameworkJAVA.support;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;

import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.xml.XmlTest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Wrapper for reading the content from QRCode and BarCode from files,
 * reading the content from QRCode which is available as a web element and
 * creating QRCode and BarCode into files.
 * 
 * Reading the content from bar code which is available as web element is not applicable.
 */
public class QRCodeAndBARCodeReaderWriter {

	private static XmlTest test = Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest();
	private static String aspireProxyAddress = test.getParameter("aspireProxyAddress");
	private static int aspirePortNumber = Integer.parseInt(test.getParameter("aspirePortNumber"));
	
	/**
	 * Wrapper to process the security code and read the content from it.
	 * 
	 * @param webElement
	 *            : WebElement of the security code.
	 * @return decodedText
	 *  		  : Text that is decoded from security code.
	 */
	public static String DecodeTextFromSecurityCodesInWebElement(WebElement webElement) throws Exception {
						
		// Get the src attribute of the WebElement.
		String srcOfWebElement = webElement.getAttribute("src");
		
		// Open a connection to the src url of image.
		InputStream inputStream = OpenConnectionToSrcURLOfWebElement(srcOfWebElement);
        
		// Store the WebElement as an image.
		BufferedImage bufferedImage = ImageIO.read(inputStream);
		
		// Decode the text from the buffered image generated.
		String decodedText = DecodeTextFromBufferedImage(bufferedImage);
				
		// Returns the text in the QR/BAR code.
		return decodedText;
		
    }
	
	/**
	 * Wrapper to read the content present in a security code which has been stored in BufferedImage instance.
	 * 
	 * @param bufferedImage
	 *            : Security code stored in BufferedImage instance.
	 * @return decodedText
	 *  		  : Text that is decoded from security code.
	 */
	private static String DecodeTextFromBufferedImage(BufferedImage bufferedImage) throws Exception
	{
		// Clear all the other parts in the image, and convert the image into bitmap.
		LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(luminanceSource));
					  				
		// Store the decoded value of QR/BAR code.
		Result decodedValue = new MultiFormatReader().decode(bitmap);
					
		// Returns the text in the QR/BAR code.
		return decodedValue.getText();
	}
	
	/**
	 * Wrapper to open the connection to reach the url of the image.
	 * 
	 * @param srcOfWebElement
	 *            : Src attribute of the web element.
	 * @return inputStream
	 *  		  : .
	 */
	private static InputStream OpenConnectionToSrcURLOfWebElement(String srcOfWebElement) throws Exception
	{
		URL urlOfImage = new URL(srcOfWebElement);
		
		// Define the proxy's host name and port.
        SocketAddress address = new InetSocketAddress( aspireProxyAddress,  aspirePortNumber);
        
        // Create an HTTP Proxy using the above SocketAddress.
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);

        // Open the connection to the src of image.
        URLConnection openConn = urlOfImage.openConnection(proxy);
        InputStream inputStream = openConn.getInputStream();
        
        return inputStream;
	}
	
	/**
	 * Wrapper to read the content of security codes which are available in files.
	 * 
	 * @param filePath
	 *            : Path of the file which has the security code.
	 * @return decodedText
	 *  		  : Content of the security code.
	 */
	public static String DecodeTextFromSecurityCodesInFile(String filePath) throws Exception
	{
		// Store the file as an image.
		BufferedImage bufferedImage = ImageIO.read(new File( filePath ));
		
		// Decode the text from buffered image generated.
		String decodedText = DecodeTextFromBufferedImage(bufferedImage);;
		
        // Return the text in the QRCode/BarCode.
        return decodedText;
	}
	
	/**
	 * Wrapper to open the connection to reach the url of the image.
	 * 
	 * @param textInCode
	 *            : Content that has to be added in security code.
	 * @param filePath
	 *            : Path of the file.
	 * @param codeType
	 *            : BarCode/QRCode.
	 * @param fileWidth
	 *            : Width of the file/code.
	 * @param fileHeight
	 *            : Height of the file/code.
	 */
	public static void GenerateQRCODEorBARCODE(String textInCode, String filePath, String codeType, int fileWidth, int fileHeight) throws Exception {		
		
		// Changes the code type given to uppercase characters.
		codeType = codeType.toUpperCase();
		
		// Create an object of BitMatrix class.
		BitMatrix bitMatrix = null;
		
		switch(codeType) {
		
			case "BARCODE" : 
				// Set Bar Code format.
			    bitMatrix = new Code128Writer().encode(textInCode, BarcodeFormat.CODE_128, fileWidth, fileHeight);			
				break;
			case "QRCODE" :
				// Set QR Code format.
				bitMatrix = new QRCodeWriter().encode(textInCode, BarcodeFormat.QR_CODE, fileWidth, fileHeight);
				break;	
			default:	
				Log.event("The code type:" + codeType +" passed is invalid");
		}
		
		// Write the Bar Code/QR Code into file system.
	    MatrixToImageWriter.writeToStream(bitMatrix, 
	    		filePath.split("\\.")[filePath.split("\\.").length-1], new FileOutputStream(new File(filePath)));
	}
}

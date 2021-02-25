package afta.src.test.java.com.AFTA.test;

import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ApplitoolVisualTests {
     //String apiKey = System.getenv("MY_APPLITOOLS_API_KEY");
     String apiKey ="okvan1iNfBFQqZIeOXO1D8zZmYHv0GW1diIp7Tck0QM110";
   
     // Initialize the Runner for your test.
     EyesRunner runner = new ClassicRunner();

     // Initialize the eyes SDK
     Eyes eyes = new Eyes(runner);
     eyes.setApiKey(apiKey);                                        // Note 2

     WebDriver innerDriver = new ChromeDriver(); 
     //Add optional global setup/defaults before the eyes.open      // Note 4
     RectangleSize viewportSize = new RectangleSize(1024,768);
     innerDriver = eyes.open(innerDriver,"Hello World App", "Hello World Test", viewportSize);      // Note 5
     
     try {
         String website = "https://applitools.com/helloworld";
         innerDriver.get(website);       
         //ensure that the entire webpage is captured even if it is larger than the viewport
         eyes.setForceFullPageScreenshot(true);
         //image matching is done less strictly
         eyes.setMatchLevel(MatchLevel.LAYOUT);

         eyes.checkWindow("initial screen");                        // Note 7
         TestResults testResult = eyes.close(false);                // Note 8
     } 
     
     finally {
    	 // End the test.
    	 eyes.closeAsync();
         eyes.abortIfNotClosed(); 
         innerDriver.quit();      
     }
      
     // Wait and collect all test results
     TestResultsSummary allTestResults = runner.getAllTestResults();
     // Print results
     System.out.println(allTestResults);
}
}

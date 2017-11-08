import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.RectangleSize;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Example {

    private static RemoteWebDriver startDriver() throws MalformedURLException {

        DesiredCapabilities cap = new DesiredCapabilities(DesiredCapabilities.chrome());
        ChromeOptions options = new ChromeOptions();

        options.addArguments("ignore-certificate-errors");
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
    }

    public static void main(String[] args) throws MalformedURLException, URISyntaxException {

        // Start browser
        WebDriver driver = startDriver();

        // Initialize the eyes SDK and set your private API key.
        Eyes eyes = new Eyes();
        eyes.setServerUrl(new URI("https://adidaseyesapi.applitools.com"));

        // Change the key below
        eyes.setApiKey("YOUR_API_KEY");


        eyes.open(driver, "ISTA", "ISTA 2017 Example");

        driver.get("https://istacon.org/");

        new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOfElementLocated(By.linkText("AGENDA")));

        // Check homepage
        eyes.checkWindow("Home Page");

        driver.findElement(By.linkText("AGENDA")).click();

        // Check agenda page
        eyes.checkWindow("Agenda");

        driver.findElement(By.linkText("Make it visible")).click();

        // Check only session description
        eyes.checkElement(By.cssSelector(".session-description"));

        // Close applitools session
        try {
            eyes.close();
            driver.quit();
        } catch (Exception e) {
            driver.quit();
            eyes.abortIfNotClosed();
        }
    }
}

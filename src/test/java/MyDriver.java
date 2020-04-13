import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class MyDriver {

    private WebDriver driver;

    public MyDriver(String browser) {
        switch (browser) {
            case "remoteFirefox":
                try {
                    driver = new RemoteWebDriver(new URL("http://192.168.1.63:5555/wd/hub"), DesiredCapabilities.firefox());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                break;
            case "explorer":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                break;
        }
        driver.manage().window().maximize();
    }

    public org.openqa.selenium.WebDriver getDriver() {
        return this.driver;
    }
}

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class TestSignUpPage {
    private WebDriver driver;
    private SignUpPage signUpPage;
    private MainPage mainPage;
    private String homePage = "https://stackoverflow.com/users/signup?ssrc=head&returnurl=%2fusers%2fstory%2fcurrent";

    @Before
    public void setSignUpPagee() {
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");

        if ((System.getProperty("os.name").substring(0, 3)).equals("Lin")) {
            System.setProperty("webdriver.gecko.driver", "Drivers/Linux/geckodriver");
        } else {
            System.setProperty("webdriver.gecko.driver", "Drivers\\Windows\\geckodriver.exe");
        }

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        driver = new FirefoxDriver(firefoxOptions);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://stackoverflow.com");
        mainPage = new MainPage(driver);
        signUpPage = new SignUpPage(driver);
        mainPage.clickOnSignUp(driver);
    }

    @After
    public void exit() {
        driver.quit();
    }

    @Test
    public void checkingLinksOnSignUpOnPage() {
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0, 1000)", "");
        signUpPage.clickOnPage(driver, signUpPage.getLinkLogIn());
        String urlLogIn = driver.getCurrentUrl();
        Assert.assertEquals("https://stackoverflow.com/users/login?ssrc=head&returnurl=%2fusers%2fstory%2fcurrent", urlLogIn);
        signUpPage.clickOnSignUp(driver);

        jse.executeScript("window.scrollBy(0, 1000)", "");
        signUpPage.clickOnPage(driver, signUpPage.getLinkTalent());
        String urlTalent = driver.getCurrentUrl();
        Assert.assertEquals("https://talent.stackoverflow.com/users/login", urlTalent);
        driver.get(homePage);
    }
}

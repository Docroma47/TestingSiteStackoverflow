import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import java.util.concurrent.TimeUnit;
import static java.lang.Thread.sleep;

public class TestMainPage {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setMainPage() {
        if ((System.getProperty("os.name").substring(0, 3)).equals("Lin")) {
            System.setProperty("webdriver.gecko.driver", "Drivers//Linux//geckodriver");
        } else {
            System.setProperty("webdriver.gecko.driver", "Drivers\\Windows\\geckodriver.exe");
        }

        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://stackoverflow.com");
        mainPage = new MainPage(driver);
    }

    @After
    public void exit() {
        driver.quit();
    }

    @Test
    public void TestClickSignUp() {
        SignUpPage signUpPage = mainPage.ClickOnSignUp(mainPage.getButtonSignUp());
        String text = signUpPage.getHeadingText(By.xpath("//label[@for='email']"));
        Assert.assertEquals("Email", text);
    }
}

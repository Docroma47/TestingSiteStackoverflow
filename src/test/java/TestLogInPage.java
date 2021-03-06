import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static java.util.concurrent.TimeUnit.SECONDS;

public class TestLogInPage {
  private static WebDriver driver;
  private static LogInPage logInPage;
  private static MainPage mainPage;
  private String homePage = "https://stackoverflow.com";

  @BeforeClass
  public static void setLogInPage() {
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
    driver.manage().timeouts().implicitlyWait(5, SECONDS);
    driver.manage().window().maximize();
    driver.get("https://stackoverflow.com");
    mainPage = new MainPage(driver);
    logInPage = new LogInPage(driver);
  }

  @AfterClass
  public static void exit() {
    driver.quit();
  }

  @Test
  public void checkingAlertsAndInputData() {
    logInPage.clickOnLogIn(driver);
    logInPage.inputLogIn("romakolotov47@gmail.com", "79213980538r");
    logInPage.clickSubmitButton();
    logInPage.clickOnPage(driver, By.xpath("//a[@href='/users/13143191/docmom47']"));
    String text = logInPage.getHeadingText(driver, By.xpath("//div[@id='mainbar-full']/div[1]/div[3]/div[1]"));
    Assert.assertEquals("Docmom47", text);
    logInPage.clickOnPage(driver, By.xpath("//div[@role='menubar']/ol/li[6]"));
    logInPage.clickOnPage(driver, By.xpath("//a[text()='log out']"));
    logInPage.clickOnPage(driver, By.xpath("//button[text()='Log out']"));
    Assert.assertEquals("https://stackoverflow.com/", driver.getCurrentUrl());
  }

  @Test
  public void checkingLinksOnLogInPage() {
    logInPage.clickOnLogIn(driver);
    logInPage.clickImageHomeButton();
    Assert.assertEquals("https://stackoverflow.com/", driver.getCurrentUrl());

    Assert.assertEquals("https://stackoverflow.com/users/account-recovery", linksLogInPage(logInPage.getLinkPassword()));

    Assert.assertEquals("https://stackoverflow.com/users/signup?ssrc=head", linksLogInPage(logInPage.getLinkSignUp()));
    driver.get(homePage);

    Assert.assertEquals("https://talent.stackoverflow.com/users/login", linksLogInPage(logInPage.getLinkTalent()));
  }

  public String linksLogInPage(By xpath) {
    logInPage.clickOnLogIn(driver);
    logInPage.clickOnPage(driver, xpath);
    return driver.getCurrentUrl();
  }
}

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;


public class TestMainPage {
    private static WebDriver driver;
    private static MainPage mainPage;
    private String homePage = "https://stackoverflow.com";

    @BeforeClass
    public static void setMainPage() {
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
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://stackoverflow.com");
        mainPage = new MainPage(driver);
    }

    @AfterClass
    public static void exit() {
        driver.quit();
    }

    @Test
    public void testButtonOnTopBarMainPage() {
        Assert.assertEquals("https://stackoverflow.com/users/signup?ssrc=head&returnurl=%2fusers%2fstory%2fcurrent", getUrlPage(mainPage.getButtonSignUp()));
        mainPage.clickHomeButton(driver);

        Assert.assertEquals("https://stackoverflow.com/users/login?ssrc=head&returnurl=https%3a%2f%2fstackoverflow.com%2f", getUrlPage(mainPage.getButtonLogIn()));
        mainPage.clickHomeButton(driver);

        Assert.assertTrue(st(mainPage.getButtonProduct(), By.xpath("//div[@id=\"products-popover\"]//a[@href=\"/questions\"]")));

        Assert.assertEquals("https://stackoverflow.com/teams/customers", getUrlPage(mainPage.getButtonCustomers()));
        mainPage.clickOnPage(driver, By.xpath("//div[@id=\"product-main-nav\"]//a[@href=\"/\"]"));

        Assert.assertEquals("https://stackoverflow.com/teams/use-cases", getUrlPage(mainPage.getButtonUseCases()));
        mainPage.clickOnPage(driver, By.xpath("//div[@id=\"product-main-nav\"]//a[@href=\"/\"]"));

        Assert.assertTrue(st(mainPage.getPanel(), mainPage.getPanel()));

        Assert.assertEquals("https://stackoverflow.com/users", getUrlPage(mainPage.getPanelButtonUsers()));

        Assert.assertEquals("https://stackoverflow.com/", getUrlPage(By.xpath("//a[@href=\"https://stackoverflow.com\"]//span[text()='Stack Overflow']")));
    }

    @Test
    public void testFooterButton() {
        if (driver.findElement(By.xpath("//a[@aria-label=\"notice-dismiss\"]")).isDisplayed()) {
            mainPage.clickOnPage(driver, By.xpath("//a[@aria-label=\"notice-dismiss\"]"));
        }
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0, 5000)", "");

        Assert.assertEquals("Welcome to the Stack Overflow Help Center!", getNameButton(mainPage.getFooterButtonHelp(), By.xpath("//div[@class=\"wiki-ph-content\"]//h2")));
        mainPage.clickOnPage(driver, mainPage.getHomeImageButton());

        jse.executeScript("window.scrollBy(0, 5000)", "");

        mainPage.clickOnPage(driver, mainPage.getFooterButtonRightPanelOther());
        Assert.assertEquals("https://data.stackexchange.com/", getUrlPage(By.xpath("//a[@href=\"https://data.stackexchange.com\"]")));
        driver.get(homePage);

        jse.executeScript("window.scrollBy(0, 5000)", "");

        mainPage.clickOnPage(driver, mainPage.getFooterButtonRightPanelOther());
        Assert.assertEquals("Help", getNameButton(mainPage.getFooterButtonBack(), mainPage.getFooterButtonHelp()));

        mainPage.clickOnPage(driver, mainPage.getFooterButtonHome());
        jse.executeScript("window.scrollBy(0, -5000)", "");
    }

    public String getUrlPage(By xpath) {
        mainPage.clickOnPage(driver, xpath);
        return driver.getCurrentUrl();
    }

    public Boolean st(By xpathOnClick, By xpathElementBool) {
        mainPage.clickOnPage(driver, xpathOnClick);
        return driver.findElement(xpathElementBool).isDisplayed();
    }

    public String getNameButton(By xpathClick, By xpathReturnString) {
        mainPage.clickOnPage(driver, xpathClick);
        return mainPage.getHeadingText(driver, xpathReturnString);
    }

}

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;


public class TestMainPage {
    private static WebDriver driver;
    private static MainPage mainPage;
    private String homePage = "https://stackoverflow.com";

    @BeforeClass
    public static void setMainPage() {
        if ((System.getProperty("os.name").substring(0, 3)).equals("Lin")) {
            System.setProperty("webdriver.gecko.driver", "Drivers/Linux/geckodriver");
        } else {
            System.setProperty("webdriver.gecko.driver", "Drivers\\Windows\\geckodriver.exe");
        }
        driver = new FirefoxDriver();
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
        mainPage.clickOnSignUp(driver);
        String urlSignUp = driver.getCurrentUrl();
        Assert.assertEquals("https://stackoverflow.com/users/signup?ssrc=head&returnurl=%2fusers%2fstory%2fcurrent", urlSignUp);
        mainPage.clickHomeButton(driver);

        mainPage.clickOnLogIn(driver);
        String urlLogIn = driver.getCurrentUrl();
        Assert.assertEquals("https://stackoverflow.com/users/login?ssrc=head&returnurl=https%3a%2f%2fstackoverflow.com%2f", urlLogIn);
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getButtonProduct());
        Boolean panelProductActive = driver.findElement(By.xpath("//div[@id=\"products-popover\"]//a[@href=\"/questions\"]")).isDisplayed();
        Assert.assertTrue(panelProductActive);


        mainPage.clickOnPage(driver, mainPage.getButtonCustomers());
        String urlCustomers = driver.getCurrentUrl();
        Assert.assertEquals("https://stackoverflow.com/teams/customers", urlCustomers);
        mainPage.clickOnPage(driver, By.xpath("//div[@id=\"product-main-nav\"]//a[@href=\"/\"]"));


        mainPage.clickOnPage(driver, mainPage.getButtonUseCases());
        String urlUseCase = driver.getCurrentUrl();
        Assert.assertEquals("https://stackoverflow.com/teams/use-cases", urlUseCase);
        mainPage.clickOnPage(driver, By.xpath("//div[@id=\"product-main-nav\"]//a[@href=\"/\"]"));

        mainPage.clickOnPage(driver, mainPage.getPanel());
        Boolean panelOnMainPageActive = driver.findElement(mainPage.getPanel()).isDisplayed();
        Assert.assertTrue(panelOnMainPageActive);
        mainPage.clickOnPage(driver, mainPage.getPanelButtonUsers());
        String urlUsers = driver.getCurrentUrl();
        Assert.assertEquals("https://stackoverflow.com/users", urlUsers);
        mainPage.clickOnPage(driver, By.xpath("//a[@href=\"https://stackoverflow.com\"]//span[text()='Stack Overflow']"));
        String urlHome = driver.getCurrentUrl();
        Assert.assertEquals("https://stackoverflow.com/", urlHome);
    }

    @Test
    public void testFooterButton() {
        System.out.println(driver.findElement(By.xpath("//a[@aria-label=\"notice-dismiss\"]")).isDisplayed());
        if (driver.findElement(By.xpath("//a[@aria-label=\"notice-dismiss\"]")).isDisplayed()) {
            mainPage.clickOnPage(driver, By.xpath("//a[@aria-label=\"notice-dismiss\"]"));
        }
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0, 5000)", "");

        mainPage.clickOnPage(driver, mainPage.getFooterButtonHelp());
        String textHelpCenter = mainPage.getHeadingText(driver, By.xpath("//div[@class=\"wiki-ph-content\"]//h2"));
        Assert.assertEquals("Welcome to the Stack Overflow Help Center!", textHelpCenter);

        mainPage.clickOnPage(driver, mainPage.getHomeImageButton());
        jse.executeScript("window.scrollBy(0, 5000)", "");

        mainPage.clickOnPage(driver, mainPage.getFooterButtonRightPanelOther());
        mainPage.clickOnPage(driver, By.xpath("//a[@href=\"https://data.stackexchange.com\"]"));
        String urlData = driver.getCurrentUrl();
        Assert.assertEquals("https://data.stackexchange.com/", urlData);

        driver.get(homePage);
        jse.executeScript("window.scrollBy(0, 5000)", "");
        mainPage.clickOnPage(driver, mainPage.getFooterButtonRightPanelOther());


        mainPage.clickOnPage(driver, mainPage.getFooterButtonBack());
        String buttonHelp = mainPage.getHeadingText(driver, mainPage.getFooterButtonHelp());
        Assert.assertEquals("Help", buttonHelp);
        mainPage.clickOnPage(driver, mainPage.getFooterButtonHome());
        jse.executeScript("window.scrollBy(0, -5000)", "");
    }

}

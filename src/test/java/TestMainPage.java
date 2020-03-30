import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxBinary;

import javax.swing.*;
import java.util.concurrent.TimeUnit;
import static java.lang.Thread.sleep;

public class TestMainPage {
    private WebDriver driver;
    private MainPage mainPage;
    private String homePage = "https://stackoverflow.com";

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
    }

    @Test
    public void testSearchOnSite() {
        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "What does “connection reset by peer” mean?");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        WebElement question = driver.findElement(By.xpath("//*[@id=\"question-summary-1434451\"]"));
        Assert.assertTrue(question.isDisplayed());
        Assert.assertEquals("Q: What does “connection reset by peer” mean?", mainPage.getHeadingText(driver, By.xpath("//*[@id=\"question-summary-1434451\"]//h3//a[1]")));
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "#$!#");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        By mainbar = By.xpath("//div[@id=\"mainbar\"]//div[3]/div[1]");
        String errorSearchText = mainPage.getHeadingText(driver, By.xpath("//div[@id=\"mainbar\"]//div[@class=\"ta-center fs-body1 mt4\"]"));
        Assert.assertEquals("0 results", mainPage.getHeadingText(driver, mainbar));
        Assert.assertEquals("Try different or less specific keywords.", errorSearchText);
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "score:30000");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        String textQuestion = mainPage.getHeadingText(driver, By.xpath("//div[@id=\"answer-id-11227902\"]//h3//a[1]"));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id=\"answer-id-11227902\"]")).isDisplayed());
        Assert.assertEquals("A: Why is processing a sorted array faster than processing an unsorted array?", textQuestion);
    }

    @Test
    public void testCorrectInputInSearchBar() {
        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "score:40000");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        By bar = By.xpath("//div[@id=\"mainbar\"]//div[3]/div[1]");
        Assert.assertEquals("0 results", mainPage.getHeadingText(driver, bar));
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "score:30000");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        String textQuestion = mainPage.getHeadingText(driver, By.xpath("//div[@id=\"answer-id-11227902\"]//h3//a[1]"));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id=\"answer-id-11227902\"]")).isDisplayed());
        Assert.assertEquals("A: Why is processing a sorted array faster than processing an unsorted array?", textQuestion);
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "score:-1");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        By mainbar = By.xpath("//div[@id=\"mainbar\"]//div[3]/div[1]");
        Assert.assertEquals("500 results", mainPage.getHeadingText(driver, mainbar));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id=\"answer-id-11227902\"]")).isDisplayed());
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "[$@#!]");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        By bar2 = By.xpath("//div[@id=\"mainbar\"]/div[3]/div/div[1]");
        By textCenter = By.xpath("//div[@id=\"questions\"]");
        Assert.assertEquals("0 questions", mainPage.getHeadingText(driver, bar2));
        Assert.assertEquals("No questions found. Perhaps you'd like to select a different tab?", mainPage.getHeadingText(driver, textCenter));
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "[java]");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        By bar3 = By.xpath("//div[@id=\"mainbar\"]//h1");
        Assert.assertEquals("Questions tagged [java]", mainPage.getHeadingText(driver, bar3));
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "[java, http]");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        By bar4 = By.xpath("//div[@id=\"mainbar\"]//h1");
        By bar5 = By.xpath("//div[@id=\"mainbar\"]/div[3]/div/div[1]");
        Assert.assertEquals("0 questions", mainPage.getHeadingText(driver, bar5));
        Assert.assertEquals("Questions tagged [javahttp]", mainPage.getHeadingText(driver, bar4));
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "[java][http]");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        By bar6 = By.xpath("//div[@id=\"mainbar\"]//h1");
        By bar7 = By.xpath("//div[@class=\"mb24\"]");//answers:518
        Assert.assertEquals("All Questions", mainPage.getHeadingText(driver, bar6));
        Assert.assertEquals("Tagged with java http", mainPage.getHeadingText(driver, bar7));
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "answers:518");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        By bar8 = By.xpath("//div[@id=\"mainbar\"]//h1");
        By bar9 = By.xpath("//div[@id=\"mainbar\"]//div[3]/div[1]");
        Assert.assertEquals("1 results", mainPage.getHeadingText(driver, bar9));
        Assert.assertEquals("Search Results", mainPage.getHeadingText(driver, bar8));
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "answers:700");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        By bar10 = By.xpath("//div[@id=\"mainbar\"]//div[3]/div[1]");
        By bar11 = By.xpath("//div[@id=\"mainbar\"]//div[@class=\"ta-center fs-body1 mt4\"][2]");
        Assert.assertEquals("0 results", mainPage.getHeadingText(driver, bar10));
        Assert.assertEquals("Try different or less specific keywords.", mainPage.getHeadingText(driver, bar11));
        mainPage.clickHomeButton(driver);

        mainPage.clickOnPage(driver, mainPage.getSearchField());
        mainPage.inputSearchBar(driver, "[comments], answers:518, isaccepted:no");
        driver.findElement(mainPage.getSearchField()).sendKeys(Keys.ENTER);
        By bar12 = By.xpath("//div[@id=\"question-summary-184618\"]//h3//a[1]");
        By bar13 = By.xpath("//div[@id='mainbar']//div[4]/div[1]");
        Assert.assertEquals("Q: What is the best comment in source code you have ever encountered? [closed]", mainPage.getHeadingText(driver, bar12));
        Assert.assertEquals("1 results", mainPage.getHeadingText(driver, bar13));
        mainPage.clickHomeButton(driver);
    }
}

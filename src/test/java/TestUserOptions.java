import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class TestUserOptions {
    private static WebDriver driver;
    private static LogInPage logInPage;
    private By preference = By.xpath("//a[text()='preferences']");
    private By editProfile = By.xpath("//a[text()='Edit profile and settings']");
    private By profile = By.xpath("//a[@class='my-profile js-gps-track']");

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
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://stackoverflow.com");
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnLogIn(driver);
        logInPage = new LogInPage(driver);
        logInPage.inputLogIn("romakolotov47@gmail.com", "79213980538r");
        logInPage.clickSubmitButton();
    }

    @AfterClass
    public static void exit() {
        driver.quit();
    }

    @Test
    public void checkingColorPage() {
        String colorWhite = "rgb(255, 255, 255)";
        String colorBlack = "rgb(45, 45, 45)";
        By darkMode = By.xpath("//input[@id='enableForcedDarkmode']");
        By lightMode = By.xpath("//input[@id='enableForcedLightmode']");
        logInPage.clickOnPage(driver, profile);
        logInPage.clickOnPage(driver, editProfile);
        logInPage.clickOnPage(driver, preference);
        if (driver.findElement(darkMode).isSelected()) logInPage.clickOnPage(driver, lightMode);
        Assert.assertEquals(colorWhite, logInPage.colorPage(driver));
        logInPage.clickOnPage(driver, darkMode);
        Assert.assertEquals(colorBlack, logInPage.colorPage(driver));
        logInPage.clickOnPage(driver, lightMode);
    }

    @Test
    public void checkingRadioButton() throws InterruptedException {
        By enableKeyboard = By.xpath("//input[@id='keyboardShortcuts']");
        By radioButton = By.xpath("//input[@id='hotNetworkQuestions']");
        By network = By.xpath("/html/body/div[3]/div[2]/div[2]/div[8]");
        By hideLeftPanel = By.xpath("//input[@id=\"flag-leftnav\"]");

        logInPage.clickOnPage(driver, profile);
        logInPage.clickOnPage(driver, editProfile);
        logInPage.clickOnPage(driver, preference);

        if (!driver.findElement(enableKeyboard).isSelected()) logInPage.clickOnPage(driver, enableKeyboard);

        driver.findElement(By.tagName("body")).sendKeys(Keys.SHIFT + "?");
        sleep(1000);
        driver.findElement(By.tagName("body")).sendKeys("G");
        sleep(1000);
        driver.findElement(By.tagName("body")).sendKeys("H");
        sleep(1000);
        Assert.assertEquals("https://stackoverflow.com/", driver.getCurrentUrl());
        driver.navigate().back();

        if (driver.findElement(enableKeyboard).isSelected()) logInPage.clickOnPage(driver, enableKeyboard);

        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='left-sidebar']")).isDisplayed());
        logInPage.clickOnPage(driver, hideLeftPanel);
        Assert.assertFalse(driver.findElement(By.xpath("//div[@id='left-sidebar']")).isDisplayed());
        logInPage.clickOnPage(driver, hideLeftPanel);

        if (!driver.findElement(radioButton).isSelected()) logInPage.clickOnPage(driver, radioButton);
        logInPage.clickHomeButton(driver);
        Assert.assertEquals(driver.findElement(network).getAttribute("id"), "recent-tags");
        driver.navigate().back();
        if (driver.findElement(radioButton).isSelected()) logInPage.clickOnPage(driver, radioButton);
    }

    @Test
    public void inputProfile() throws InterruptedException {
        By displayName = By.xpath("//input[@id='displayName']");
        By location = By.xpath("//input[@id=\"location\"]");
        By title = By.xpath("//input[@id=\"Title\"]");
        By realName = By.xpath("//input[@id=\"RealName\"]");
        By buttonSave = By.xpath("//input[@value=\"Save Profile\"]");
        By textAboutMe = By.xpath("//*[@id=\"wmd-input\"]");
        By errorText = By.xpath("//div[@class=\"form-error\"]");

        JavascriptExecutor jse = (JavascriptExecutor)driver;

        logInPage.clickOnPage(driver, editProfile);
        String name = randomName();

        logInPage.clearField(driver, location);
        logInPage.inputTextInPage(location, "Russia123", driver);
        logInPage.clearField(driver, title);
        logInPage.inputTextInPage(title, "BULDOZERS!!!", driver);
        logInPage.clearField(driver, textAboutMe);
        logInPage.inputTextInPage(textAboutMe, "WAAAAAAAGH! I need more imperial guard! FOR THE EMPIRE!AND ME!3", driver);
        jse.executeScript("window.scrollBy(0, 1000)", "");
        logInPage.clearField(driver, realName);
        logInPage.inputTextInPage(realName, randomName().substring(0, 4), driver);
        sleep(2000);
        logInPage.clickOnPage(driver, buttonSave);
        driver.navigate().refresh();
        jse.executeScript("window.scrollBy(0, -1000)", "");

        Assert.assertEquals("Russia123", driver.findElement(location).getAttribute("value"));
        Assert.assertEquals("BULDOZERS!!!", driver.findElement(title).getAttribute("value"));
        Assert.assertEquals("WAAAAAAAGH! I need more imperial guard! FOR THE EMPIRE!AND ME!3", driver.findElement(textAboutMe).getAttribute("value"));
        jse.executeScript("window.scrollBy(0, 1000)", "");
        Assert.assertEquals(name.substring(0, 4), driver.findElement(realName).getAttribute("value"));
        jse.executeScript("window.scrollBy(0, -1000)", "");

        logInPage.clearField(driver, displayName);
        logInPage.inputTextInPage(displayName, name , driver);
        jse.executeScript("window.scrollBy(0, 1000)", "");
        logInPage.clickOnPage(driver, buttonSave);
        String error = logInPage.getHeadingText(driver, errorText);
        Assert.assertEquals("Oops! There was a problem updating your profile:\n" +
                "temporary error updating your profile -- please try again!\n" +
                "Display name may only be changed once every 30 days; you may change again on Apr 30 at 15:47", error);
        jse.executeScript("window.scrollBy(0, -1000)", "");
    }

    @Test
    public  void button() {
        By developerStory = By.xpath("//div[text()='Developer Story']");
        By activity = By.xpath("//a[text()='Activity']");
        By prof = By.xpath("//a[text()='Profile']");

        By developStoryPreference = By.xpath("//a[text()='Developer Story preferences']");
        By jobPreferences = By.xpath("//a[text()='Job preferences']");
        By editEmailSettings = By.xpath("//a[text()='edit email settings']");
        By tagWatchingIgnoring = By.xpath("//a[text()='tag watching & ignoring']");
        By communityDigests = By.xpath("//a[text()='community digests']");
        By questionSubscriptions = By.xpath("//a[text()='question subscriptions ']");
        By savedJobSearchAlerts = By.xpath("//a[text()='saved job search alerts']");
        By flair = By.xpath("//a[text()='flair']");
        By applications = By.xpath("//a[text()='applications']");
        By myLogins = By.xpath("//a[text()='my logins']");
        By hideCommunities = By.xpath("//a[text()='hide communities']");
        By deleteProfile = By.xpath("//a[text()='delete profile']");
        By privileges = By.xpath("//a[text()='privileges']");

        logInPage.clickOnPage(driver, developStoryPreference);
        Assert.assertEquals("Developer Story preferences", driver.findElement(By.xpath("/html//h1")).getText());
        logInPage.clickOnPage(driver, jobPreferences);
        Assert.assertEquals("Job preferences", driver.findElement(By.xpath("/html//h1")).getText());
        logInPage.clickOnPage(driver, editEmailSettings);
        Assert.assertEquals("Edit Email Settings", driver.findElement(By.xpath("/html//h1")).getText());
        logInPage.clickOnPage(driver, tagWatchingIgnoring);
        Assert.assertEquals("Tag Watching", driver.findElement(By.xpath("//div[1]/h1")).getText());
        logInPage.clickOnPage(driver, communityDigests);
        Assert.assertEquals("Community Digests", driver.findElement(By.xpath("/html//h1")).getText());
        logInPage.clickOnPage(driver, questionSubscriptions);
        Assert.assertEquals("Stack Exchange", driver.findElement(By.xpath("//header//h1")).getText());
        driver.navigate().back();
        logInPage.clickOnPage(driver, savedJobSearchAlerts);
        Assert.assertEquals("Saved Job Search Alerts", driver.findElement(By.xpath("//h1")).getText());
        logInPage.clickOnPage(driver, flair);
        Assert.assertEquals("Flair", driver.findElement(By.xpath("//h1")).getText());
        logInPage.clickOnPage(driver, applications);
        Assert.assertEquals("Applications", driver.findElement(By.xpath("//h1")).getText());
        logInPage.clickOnPage(driver, myLogins);
        Assert.assertEquals("My Logins", driver.findElement(By.xpath("//h1")).getText());
        logInPage.clickOnPage(driver, hideCommunities);
        Assert.assertEquals("Hide Communities", driver.findElement(By.xpath("//h1")).getText());
        logInPage.clickOnPage(driver, deleteProfile);
        Assert.assertEquals("Delete Profile", driver.findElement(By.xpath("//h1")).getText());
        logInPage.clickOnPage(driver, privileges);
        System.out.println(logInPage.getHeadingText(driver, By.xpath("//h1[@id='h-badges']/a[1]")));
        Assert.assertEquals("Help Center", driver.findElement(By.xpath("//h1[@id='h-badges']/a[1]")).getText());
        driver.navigate().back();
        logInPage.clickOnPage(driver, developerStory);
        Assert.assertEquals("Kickstart your Developer Story.", driver.findElement(By.xpath("//div[@id='content']/div[3]/div[1]//h2")).getText());
        logInPage.clickOnPage(driver, activity);
        Assert.assertEquals("Answers (0)", driver.findElement(By.xpath("//div[@id='user-panel-answers']//h3//a")).getText());
        logInPage.clickOnPage(driver, prof);
        Assert.assertEquals("Docmom47", driver.findElement(By.xpath("//h2//div")).getText());
    }

    @Test
    public void tags() {
        By addTag = By.xpath("//div[@id='watching-1']//a[text()='Add a tag']");
        By inputTagField = By.xpath("//div[@id=\"watching-1\"]//div[@class=\"input-group\"]//input");
        By buttonDone = By.xpath("//div[@id=\"watching-1\"]//a[text()='Done']");
        By watchTag = By.xpath("//button[text()='Watch tag']");
        By tagWatchingIgnoring = By.xpath("//a[text()='tag watching & ignoring']");

        logInPage.clickOnPage(driver, editProfile);
        logInPage.clickOnPage(driver, tagWatchingIgnoring);

        Assert.assertEquals(2, size());
        for (WebElement linkDelete : listTags()){
            linkDelete.click();
        }

        Assert.assertEquals(0, size());

        logInPage.clickOnPage(driver, addTag);
        logInPage.inputTextInPage(inputTagField, "java", driver);
        logInPage.clickOnPage(driver, watchTag);
        logInPage.inputTextInPage(inputTagField, "automated-tests", driver);
        logInPage.clickOnPage(driver, watchTag);
        logInPage.clickOnPage(driver, buttonDone);

        Assert.assertEquals(2, size());
    }

    public List<WebElement> listTags() {
        return driver.findElements(By.xpath("//table//form//a[1]"));
    }

    public int size() {
        return listTags().size();
    }

    public String randomName() {
        Random random = new Random();
        int randomIntName = random.nextInt(999);
        return "Roma" + randomIntName;
    }
}

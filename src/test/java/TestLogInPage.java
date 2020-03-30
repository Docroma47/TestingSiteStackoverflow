import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class TestLogInPage {
    private WebDriver driver;
    private LogInPage logInPage;
    private MainPage mainPage;
    private String homePage = "https://stackoverflow.com/users/login?ssrc=head";

    @Before
    public void setMainPage() {
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        if ((System.getProperty("os.name").substring(0, 3)).equals("Lin")) {
            System.setProperty("webdriver.gecko.driver", "Drivers//Linux//geckodriver");
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
        logInPage = new LogInPage(driver);
        mainPage.clickOnLogIn(driver);
    }

    @After
    public void exit() {
        driver.quit();
    }

    @Test
    public void checkingAlertsAndInputData() {
        driver.findElement(logInPage.getInputEmail()).sendKeys("GAS");
        driver.findElement(logInPage.getInputPassword()).sendKeys("123456");
        logInPage.clickSubmitButton();

        Boolean errorEmail = driver.findElement(logInPage.getAlertEmail()).isDisplayed();
        Boolean errorPas = driver.findElement(logInPage.getAlertPassword()).isDisplayed();
        String testError = logInPage.getHeadingText(driver, logInPage.getAlertEmail());
        Assert.assertEquals("The email is not a valid email address.", testError);
        Assert.assertTrue(errorEmail);
        Assert.assertFalse(errorPas);
        clearAllField();

        logInPage.inputLogIn("", "");
        logInPage.clickSubmitButton();
        Boolean errorEmail2 = driver.findElement(logInPage.getAlertEmail()).isDisplayed();
        Boolean errorPas2 = driver.findElement(logInPage.getAlertPassword()).isDisplayed();
        String testError2 = logInPage.getHeadingText(driver, logInPage.getAlertEmail());
        String testErrorPassword2 = logInPage.getHeadingText(driver, logInPage.getAlertPassword());
        Assert.assertTrue(errorEmail2);
        Assert.assertTrue(errorPas2);
        Assert.assertEquals("Email cannot be empty.", testError2);
        Assert.assertEquals("Password cannot be empty.", testErrorPassword2);
        clearAllField();

        logInPage.inputLogIn("ROMa", "");
        logInPage.clickSubmitButton();
        String testErrorPassword3 = logInPage.getHeadingText(driver, logInPage.getAlertPassword());
        Boolean errorPas3 = driver.findElement(logInPage.getAlertPassword()).isDisplayed();
        Assert.assertEquals("Password cannot be empty.", testErrorPassword3);
        Assert.assertTrue(errorPas3);
        clearAllField();

        logInPage.inputLogIn("", "1234556");
        logInPage.clickSubmitButton();
        String testErrorEmail4 = logInPage.getHeadingText(driver, logInPage.getAlertEmail());
        Boolean errorEmail4 = driver.findElement(logInPage.getAlertEmail()).isDisplayed();
        Assert.assertEquals("Email cannot be empty.", testErrorEmail4);
        Assert.assertTrue(errorEmail4);

        logInPage.inputLogIn("dsadasdasd", "1234556");
        logInPage.clickSubmitButton();
        String testErrorEmail5 = logInPage.getHeadingText(driver, logInPage.getAlertEmail());
        Boolean errorEmail5 = driver.findElement(logInPage.getAlertEmail()).isDisplayed();
        Assert.assertEquals("The email is not a valid email address.", testErrorEmail5);
        Assert.assertTrue(errorEmail5);
        driver.get(homePage);
    }

    @Test
    public void checkingDifferentService() {
        logInPage.clickOnPage(driver, logInPage.getButtonGitHub());
        String textGitHub = logInPage.getHeadingText(driver, By.xpath("//div[@class='auth-form-body mt-3']//p"));
        Assert.assertEquals("Sign in to GitHub\n" +
                "to continue to Stack Overflow", textGitHub);
        driver.get(homePage);

        logInPage.clickOnPage(driver, logInPage.getButtonGoogle());
        String textGoogle = logInPage.getHeadingText(driver, By.xpath("//div[text()='Войдите в аккаунт Google']"));
        Assert.assertEquals("Войдите в аккаунт Google", textGoogle);
        driver.get(homePage);

        logInPage.clickOnPage(driver, logInPage.getButtonFaceBook());
        String textFaceBook = logInPage.getHeadingText(driver, By.xpath("//div[@id='header_block']/span"));
        Assert.assertEquals("Вход на Facebook", textFaceBook);
        driver.get(homePage);
    }

    @Test
    public void checkingLinksOnLogInPage() {
        logInPage.clickImageHomeButton();
        Assert.assertEquals("https://stackoverflow.com/", driver.getCurrentUrl());

        logInPage.clickOnLogIn(driver);
        logInPage.clickOnPage(driver, logInPage.getLinkPassword());
        Assert.assertEquals("https://stackoverflow.com/users/account-recovery", driver.getCurrentUrl());

        logInPage.clickOnLogIn(driver);
        logInPage.clickOnPage(driver, logInPage.getLinkSignUp());
        Assert.assertEquals("https://stackoverflow.com/users/signup?ssrc=head", driver.getCurrentUrl());
        driver.get(homePage);

        logInPage.clickOnPage(driver, logInPage.getLinkTalent());
        Assert.assertEquals("https://talent.stackoverflow.com/users/login", driver.getCurrentUrl());
    }


    private void clearAllField() {
        driver.findElement(logInPage.getInputEmail()).clear();
        driver.findElement(logInPage.getInputPassword()).clear();
    }
}

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class TestSignUpPage {
    private WebDriver driver;
    private SignUpPage signUpPage;
    private MainPage mainPage;
    private String homePage = "https://stackoverflow.com/users/signup?ssrc=head&returnurl=%2fusers%2fstory%2fcurrent";

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
        signUpPage = new SignUpPage(driver);
        mainPage.clickOnSignUp(driver);
    }

    @After
    public void exit() {
        driver.quit();
    }

    @Test
    public void checkingAlertsAndInputData() {
        driver.findElement(signUpPage.getInputEmail()).sendKeys("GAS");
        driver.findElement(signUpPage.getInputPassword()).sendKeys("123456");
        signUpPage.clickSubmitButton();
        Boolean errorEmail = driver.findElement(signUpPage.getAlertEmail()).isDisplayed();
        Boolean errorPas = driver.findElement(signUpPage.getAlertPassword()).isDisplayed();
        String testError = signUpPage.getHeadingText(driver, signUpPage.getAlertPassword());
        Assert.assertEquals("Please add one of the following things to make your password stronger:\n" +
                "letters", testError);
        Assert.assertFalse(errorEmail);
        Assert.assertTrue(errorPas);
        clearAllField();

        signUpPage.inputSignUp("", "", "");
        signUpPage.clickSubmitButton();
        Boolean errorEmail2 = driver.findElement(signUpPage.getAlertEmail()).isDisplayed();
        String testError2 = signUpPage.getHeadingText(driver, signUpPage.getAlertEmail());
        Assert.assertTrue(errorEmail2);
        Assert.assertEquals("The email is not a valid email address.", testError2);
        clearAllField();

        signUpPage.inputSignUp("", "roma@mail.ru", "");
        signUpPage.clickSubmitButton();
        Boolean errorPas2 = driver.findElement(signUpPage.getAlertPassword()).isDisplayed();
        String testErrorPas2 = signUpPage.getHeadingText(driver, signUpPage.getAlertPassword());
        Assert.assertTrue(errorPas2);
        Assert.assertEquals("Password cannot be empty.", testErrorPas2);
    }

    @Test
    public void checkingDifferentService() {
        signUpPage.clickOnPage(driver, signUpPage.getButtonGitHub());
        String textGitHub = signUpPage.getHeadingText(driver, By.xpath("//div[@class='auth-form-body mt-3']//p"));
        System.out.println(textGitHub);
        Assert.assertEquals("Sign in to GitHub\n" +
                "to continue to Stack Overflow", textGitHub);
        driver.get(homePage);

        signUpPage.clickOnPage(driver, signUpPage.getButtonGoogle());
        String textGoogle = signUpPage.getHeadingText(driver, By.xpath("//div[text()='Войдите в аккаунт Google']"));
        System.out.println(textGoogle);
        Assert.assertEquals("Войдите в аккаунт Google", textGoogle);
        driver.get(homePage);

        signUpPage.clickOnPage(driver, signUpPage.getButtonFaceBook());
        String textFaceBook = signUpPage.getHeadingText(driver, By.xpath("//div[@id='header_block']/span"));
        System.out.println(textFaceBook);
        Assert.assertEquals("Вход на Facebook", textFaceBook);
        driver.get(homePage);
    }

    @Test
    public void checkingLinksOnLogInPage() {
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

    private void clearAllField() {
        driver.findElement(signUpPage.getInputEmail()).clear();
        driver.findElement(signUpPage.getInputPassword()).clear();
        driver.findElement(signUpPage.getInputName()).clear();
    }
}

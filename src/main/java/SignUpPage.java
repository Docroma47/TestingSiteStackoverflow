import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage {
    private WebDriver driver;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    private By buttonGoogle = By.xpath("//div[@id='openid-buttons']//button[1]");
    private By buttonGitHub = By.xpath("//div[@id='openid-buttons']//button[2]");
    private By buttonFaceBook = By.xpath("//div[@id='openid-buttons']//button[3]");
    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputName = By.xpath("//input[@id='display-name']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By buttonSignUp = By.xpath("//*[@id='submit-button']");
    private By linkLogIn = By.xpath("//a[@name='login']");
    private By linkFreeTrial = By.xpath("//div[@class='fs-body1 fc-light']//a[@target='_blank']");
    private By linkTalent = By.xpath("//a[@name='talent']");

    public void clickOnSignUp(By xpath) {
        driver.findElement(xpath).click();
    }

    public LogInPage clickOnLogIn(By xpath) {
        driver.findElement(xpath).click();
        return new LogInPage(driver);
    }

    public MainPage clickHomeButton(By xpath) {
        driver.findElement(xpath).click();
        return new MainPage(driver);
    }

    public void inputText(By xpath, String text) {
        driver.findElement(xpath).sendKeys(text);
    }

    public String getHeadingText(By xpath) {
        return driver.findElement(xpath).getText();
    }

    public void logIn(String email, String password, String name) {
        inputText(inputEmail, email);
        inputText(inputName, name);
        inputText(inputPassword, password);
        clickOnSignUp(buttonSignUp);
    }

    public By getLinkTalent() {
        return linkTalent;
    }

    public By getInputPassword() {
        return inputPassword;
    }

    public By getInputEmail() {
        return inputEmail;
    }

    public By getButtonGoogle() {
        return buttonGoogle;
    }

    public By getButtonGitHub() {
        return buttonGitHub;
    }

    public By getButtonFaceBook() {
        return buttonFaceBook;
    }

    public By getButtonSignUp() {
        return buttonSignUp;
    }

    public By getInputName() {
        return inputName;
    }

    public By getLinkFreeTrial() {
        return linkFreeTrial;
    }

    public By getLinkLogIn() {
        return linkLogIn;
    }
}


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogInPage {
    private WebDriver driver;

    public LogInPage(WebDriver driver) {
        this.driver = driver;
    }

    private By homeImageButton = By.xpath("//div[@class='container']//a[@href='https://stackoverflow.com']");
    private By buttonGoogle = By.xpath("//div[@id='openid-buttons']//button[1]");
    private By buttonGitHub = By.xpath("//div[@id='openid-buttons']//button[2]");
    private By buttonFaceBook = By.xpath("//div[@id='openid-buttons']//button[3]");
    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By buttonLogIn = By.xpath("//*[@id='submit-button']");
    private By linkPassword = By.xpath("//a[text()='Forgot password?']");
    private By linkSignUp = By.xpath("//div[@class='grid--cell']//a[text()='Sign up']");
    private By linkTalent = By.xpath("//a[@name='talent']");

    public void clickOnLogInPage(By xpath) {
        driver.findElement(xpath).click();
    }

    public SignUpPage clickOnSignUp(By xpath) {
        driver.findElement(xpath).click();
        return new SignUpPage(driver);
    }

    public MainPage clickHomeButton(By xpath) {
        driver.findElement(xpath).click();
        return new MainPage(driver);
    }

    public void inputText(By xpath, String text) {
        driver.findElement(xpath).sendKeys(text);
    }

    public void logIn(String email, String password) {
        inputText(inputEmail, email);
        inputText(inputPassword, password);
        clickOnLogInPage(buttonLogIn);
    }

    public String getHeadingText(By xpath) {
        return driver.findElement(xpath).getText();
    }

    public By getHomeImageButton() {
        return homeImageButton;
    }

    public By getButtonLogIn() {
        return buttonLogIn;
    }

    public By getButtonFaceBook() {
        return buttonFaceBook;
    }

    public By getButtonGitHub() {
        return buttonGitHub;
    }

    public By getButtonGoogle() {
        return buttonGoogle;
    }

    public By getInputEmail() {
        return inputEmail;
    }

    public By getInputPassword() {
        return inputPassword;
    }

    public By getLinkPassword() {
        return linkPassword;
    }

    public By getLinkSignUp() {
        return linkSignUp;
    }

    public By getLinkTalent() {
        return linkTalent;
    }
}

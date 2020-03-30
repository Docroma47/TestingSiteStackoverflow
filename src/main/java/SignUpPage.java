import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage extends PageNavigation {
    private WebDriver driver;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
    }

    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputName = By.xpath("//input[@id='display-name']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By submitSignUp = By.xpath("//*[@id='submit-button']");
    private By linkLogIn = By.xpath("//a[@name='login']");
    private By linkFreeTrial = By.xpath("//div[@class='fs-body1 fc-light']//a[@target='_blank']");
    private By linkTalent = By.xpath("//a[@name='talent']");
    private By alertEmail = By.xpath("//form[@id='login-form']/div[2]/p[1]");
    private By alertPassword =  By.xpath("//form[@id='login-form']/div[3]/p[2]");

    private void inputText(By xpath, String text) {
        driver.findElement(xpath).sendKeys(text);
    }

    public void inputSignUp(String name, String email, String password) {
        inputText(inputEmail, email);
        inputText(inputName, name);
        inputText(inputPassword, password);
    }

    public void clickSubmitButton() {
        driver.findElement(submitSignUp).click();
    }

    public By getAlertPassword() {
        return alertPassword;
    }

    public By getAlertEmail() {
        return alertEmail;
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

    public By getSubmitSignUp() {
        return submitSignUp;
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


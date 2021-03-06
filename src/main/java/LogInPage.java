import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogInPage extends PageNavigation {
  private WebDriver driver;

  public LogInPage(WebDriver driver) {
    this.driver = driver;
  }

  private By homeImageButton = By.xpath("//div[@class='container']//a[@href='https://stackoverflow.com']");
  private By inputEmail = By.xpath("//input[@id='email']");
  private By inputPassword = By.xpath("//input[@id='password']");
  private By submitLogIn = By.xpath("//*[@id='submit-button']");
  private By linkPassword = By.xpath("//a[text()='Forgot password?']");
  private By linkSignUp = By.xpath("//div[@class='grid--cell']//a[text()='Sign up']");
  private By linkTalent = By.xpath("//a[@name='talent']");

  public void clickImageHomeButton() {
    driver.findElement(homeImageButton).click();
  }

  private void inputText(By xpath, String text) {
    driver.findElement(xpath).sendKeys(text);
  }

  public void inputLogIn(String email, String password) {
    inputText(inputEmail, email);
    inputText(inputPassword, password);
  }

  public void clickSubmitButton() {
    driver.findElement(submitLogIn).click();
  }

  public By getHomeImageButton() {
    return homeImageButton;
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

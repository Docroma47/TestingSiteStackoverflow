import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignUpPage extends PageNavigation {
  private WebDriver driver;

  public SignUpPage(WebDriver driver) {
    this.driver = driver;
  }

  private By linkLogIn = By.xpath("//a[@name='login']");
  private By linkTalent = By.xpath("//a[@name='talent']");

  public By getLinkTalent() {
    return linkTalent;
  }

  public By getLinkLogIn() {
    return linkLogIn;
  }
}


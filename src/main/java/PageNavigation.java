import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class PageNavigation {
    private By buttonLogIn = By.xpath("//header//li[@class='-ctas']//a[1]");
    private By buttonSignUp = By.xpath("//header//li[@class='-ctas']//a[2]");
    private By homeImageButton = By.xpath("//header//span[@class='-img _glyph']");
    private By colorPage = By.xpath("//body");

    public void clickOnPage(WebDriver driver, By xpath) {
        driver.findElement(xpath).click();
    }

    public void clickOnLogIn(WebDriver driver) {
        driver.findElement(buttonLogIn).click();
    }

    public void clickOnSignUp(WebDriver driver) {
        driver.findElement(buttonSignUp).click();
    }

    public void inputTextInPage(By xpath, String text, WebDriver driver) {
        driver.findElement(xpath).sendKeys(text);
    }

    public void clickHomeButton(WebDriver driver) {
        driver.findElement(homeImageButton).click();
    }

    public String getHeadingText(WebDriver driver,By xpath) {
        return driver.findElement(xpath).getText();
    }

    public String colorPage(WebDriver driver) {
        return driver.findElement(colorPage).getCssValue("background-color");
    }

    public void clearField(WebDriver driver, By xpath) {
        driver.findElement(xpath).clear();
    }

    public By getHomeImageButton() {
        return homeImageButton;
    }

    public By getButtonLogIn() {
        return buttonLogIn;
    }

    public By getButtonSignUp() {
        return buttonSignUp;
    }
}

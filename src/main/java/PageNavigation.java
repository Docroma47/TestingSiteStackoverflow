import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class PageNavigation {
    private By buttonLogIn = By.xpath("//header//li[@class='-ctas']//a[1]");
    private By buttonSignUp = By.xpath("//header//li[@class='-ctas']//a[2]");
    private By homeImageButton = By.xpath("//header//span[@class='-img _glyph']");
    private By searchField = By.xpath("//header//input[@placeholder='Search…']");
    private By buttonGoogle = By.xpath("//div[@id='openid-buttons']//button[1]");
    private By buttonGitHub = By.xpath("//div[@id='openid-buttons']//button[2]");
    private By buttonFaceBook = By.xpath("//div[@id='openid-buttons']//button[3]");
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

    public void inputSearchBar(WebDriver driver, String text) {
        driver.findElement(searchField).sendKeys(text);
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

    public By getButtonFaceBook() {
        return buttonFaceBook;
    }

    public By getButtonGitHub() {
        return buttonGitHub;
    }

    public By getButtonGoogle() {
        return buttonGoogle;
    }

    public By getHomeImageButton() {
        return homeImageButton;
    }

    public By getSearchField() {
        return searchField;
    }

    public By getButtonSignUp() {
        return buttonSignUp;
    }

    public By getButtonLogIn() {
        return buttonLogIn;
    }
}

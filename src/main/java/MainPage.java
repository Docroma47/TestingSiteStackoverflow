import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By panel = By.xpath("//header//span[@class='ps-relative']");
    private By homeImageButton = By.xpath("//header//span[@class='-img _glyph']");
    private By buttonProduct = By.xpath("//header//li[@class='grid--cell']//a");
    private By buttonCustomers = By.xpath("//header//a[text()='Customers']");
    private By buttonUseCases = By.xpath("//header//a[text()='Use cases']");
    private By searchField = By.xpath("//header//input[@placeholder='Search…']");
    private By buttonLogIn = By.xpath("//header//li[@class='-ctas']//a[1]");
    private By buttonSignUp = By.xpath("//header//li[@class='-ctas']//a[2]");
    private By panelButtonUsers = By.xpath("//a[@id='nav-users']");
    private By buttonDevelopers = By.xpath("//a[@href='#for-developers']");
    private By buttonBusinesses = By.xpath("//a[@href='#for-businesses']");
    private By footerButtonHome = By.xpath("//footer//a[@href='https://stackoverflow.com']");
    private By footerButtonHelp = By.xpath("//footer//a[text()='Help']");
    private By footerButtonRightPanelOther = By.xpath("//footer//a[@data-target='Other']");
    private By footerButtonBack = By.xpath("//footer//a[@class='site-footer--back js-footer-back']");

    public void ClickOnMainPage(By xpath) {
        driver.findElement(xpath).click();
    }

    public LogInPage ClickOnLogIn(By xpath) {
        driver.findElement(xpath).click();
        return new LogInPage(driver);
    }

    public SignUpPage ClickOnSignUp(By xpath) {
        driver.findElement(xpath).click();
        return new SignUpPage(driver);
    }

    public MainPage inputText(By xpath, String text) {
        driver.findElement(xpath).sendKeys(text);
        return this;
    }

    public String getHeadingText(By xpath) {
        return driver.findElement(xpath).getText();
    }

    public By getButtonBusinesses() {
        return buttonBusinesses;
    }

    public By getButtonCustomers() {
        return buttonCustomers;
    }

    public By getButtonDevelopers() {
        return buttonDevelopers;
    }

    public By getButtonLogIn() {
        return buttonLogIn;
    }

    public By getButtonProduct() {
        return buttonProduct;
    }

    public By getButtonSignUp() {
        return buttonSignUp;
    }

    public By getButtonUseCases() {
        return buttonUseCases;
    }

    public By getFooterButtonBack() {
        return footerButtonBack;
    }

    public By getFooterButtonHelp() {
        return footerButtonHelp;
    }

    public By getFooterButtonHome() {
        return footerButtonHome;
    }

    public By getFooterButtonRightPanelOther() {
        return footerButtonRightPanelOther;
    }

    public By getHomeImageButton() {
        return homeImageButton;
    }

    public By getPanel() {
        return panel;
    }

    public By getPanelButtonUsers() {
        return panelButtonUsers;
    }

    public By getSearchField() {
        return searchField;
    }
}

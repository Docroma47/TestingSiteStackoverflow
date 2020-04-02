import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends PageNavigation {
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    private By panel = By.xpath("//header//span[@class='ps-relative']");
    private By buttonProduct = By.xpath("//header//li[@class='grid--cell']//a");
    private By buttonCustomers = By.xpath("//header//a[text()='Customers']");
    private By buttonUseCases = By.xpath("//header//a[text()='Use cases']");
    private By panelButtonUsers = By.xpath("//a[@id='nav-users']");
    private By footerButtonHome = By.xpath("//div[@class=\"site-footer--logo\"]//a");
    private By footerButtonHelp = By.xpath("//footer//a[text()='Help']");
    private By footerButtonRightPanelOther = By.xpath("//footer//a[@data-target='Other']");
    private By footerButtonBack = By.xpath("//footer//a[@class='site-footer--back js-footer-back']");

    public By getButtonCustomers() {
        return buttonCustomers;
    }

    public By getButtonProduct() {
        return buttonProduct;
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

    public By getFooterButtonRightPanelOther() {
        return footerButtonRightPanelOther;
    }

    public By getPanel() {
        return panel;
    }

    public By getFooterButtonHome() {
        return footerButtonHome;
    }

    public By getPanelButtonUsers() {
        return panelButtonUsers;
    }

}

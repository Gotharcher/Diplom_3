package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private final By titleMainPage = By.xpath(".//main//h1[text()='Соберите бургер']");
    private final By createOrderButton = By.xpath(".//button[text()='Оформить заказ']");
    private final By enterAccountButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By personalTextLink = By.xpath(".//p[text()='Личный Кабинет']");
    private final By bunsPanel = By.xpath(".//span[text()='Булки']/..");
    private final By saucesPanel = By.xpath(".//span[text()='Соусы']/..");
    private final By fillingsPanel = By.xpath(".//span[text()='Начинки']/..");

    private WebDriver driver;

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    public void waitLoading() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(titleMainPage));
    }

    public boolean createOrderDispalyed(){
        return driver.findElement(createOrderButton).isDisplayed();
    }

    public void clickPersonalText(){
        driver.findElement(personalTextLink).click();
    }

    public void clickEnterAccount(){
        driver.findElement(enterAccountButton).click();
    }

    public void clickBuns(){
        driver.findElement(bunsPanel).click();
    }

    public void clickSauces(){
        driver.findElement(saucesPanel).click();
    }

    public void clickFillings(){
        driver.findElement(fillingsPanel).click();
    }

    public boolean bunsSelected(){
        return driver.findElement(bunsPanel).getAttribute("class").contains("current");
    }

    public boolean saucesSelected(){
        return driver.findElement(saucesPanel).getAttribute("class").contains("current");
    }

    public boolean fillingsSelected(){
        return driver.findElement(fillingsPanel).getAttribute("class").contains("current");
    }
}

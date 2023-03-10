package pages;

import model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {


    private final By inputEmail = By.xpath(".//label[text()='Email']/..//input");
    private final By inputPassword = By.xpath(".//label[text()='Пароль']/..//input");
    private final By buttonLogin = By.xpath(".//button[text()='Войти']");
    private final By titleLogin = By.xpath(".//main//h2[text() = 'Вход']");

    private final WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
    }

    public void waitLoading() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(titleLogin));
    }

    public void inputPassword(String text){
        driver.findElement(inputPassword).sendKeys(text);
    }
    public void inputEmail(String text){
        driver.findElement(inputEmail).sendKeys(text);
    }

    public void clickLogin(){
        driver.findElement(buttonLogin).click();
    }

    public void enterCredsAndClickLogin(User user){
        inputEmail(user.getEmail());
        inputPassword(user.getPassword());
        clickLogin();
    }
}

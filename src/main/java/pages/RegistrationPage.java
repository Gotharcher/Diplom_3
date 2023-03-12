package pages;

import model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
    private final By nameInput = By.xpath(".//label[text()='Имя']/..//input");
    private final By emailInput = By.xpath(".//label[text()='Email']/..//input");
    private final By passwordInput = By.xpath(".//*[text()='Пароль']/..//input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By shortPasswordMessage = By.xpath(".//p[text()='Некорректный пароль']");
    private final By signInHyperlink = By.xpath(".//a[text()='Войти']");
    private final By titleRegister = By.xpath(".//main//h2[text()='Регистрация']");

    private final WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterRegisterUserFields(User user) {
        inputName(user.getName());
        inputEmail(user.getEmail());
        inputPassword(user.getPassword());
    }

    public void waitLoading() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(titleRegister));
    }

    public void inputName(String text) {
        driver.findElement(nameInput).sendKeys(text);
    }

    public void inputEmail(String text) {
        driver.findElement(emailInput).sendKeys(text);
    }

    public void inputPassword(String text) {
        driver.findElement(passwordInput).sendKeys(text);
    }

    public void clickFinallyRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public boolean checkShortPasswordError() {
        return driver.findElement(shortPasswordMessage).isDisplayed();
    }

    public void clickSingInHyperlink() {
        driver.findElement(signInHyperlink).click();
    }

}

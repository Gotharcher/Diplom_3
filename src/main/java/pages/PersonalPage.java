package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PersonalPage {
    private final By exitButton = By.xpath(".//button[text()='Выход']");
    private final By linkProfile = By.xpath(".//a[text()='Профиль']");
    private final By signConstructor = By.xpath(".//p[text()='Конструктор']");
    private final By logo = By.className("AppHeader_header__logo__2D0X2");

    private final WebDriver driver;

    public PersonalPage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitLoading() {
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(linkProfile));
    }

    public void clickExitButton() {
        driver.findElement(exitButton).click();
    }

    public void clickLogo() {
        driver.findElement(logo).click();
    }

    public void clickConstructor() {
        driver.findElement(signConstructor).click();
    }
}

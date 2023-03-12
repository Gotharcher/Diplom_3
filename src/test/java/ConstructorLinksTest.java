import env.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class ConstructorLinksTest {
    public WebDriver driver;
    public MainPage mainPage;

    @Before
    public void setUp() {
        driver = SetUpWebDriverEnviroment.setUpDriver();
        mainPage = new MainPage(driver);
        driver.get(Constants.SITE_ADDRESS);
    }

    @Test
    public void checkSaucesPass() {
        mainPage.clickSauces();
        assertTrue("Выбрана вкладка соусов", mainPage.saucesSelected());
    }

    @Test
    public void checkFillingsPass() {
        mainPage.clickFillings();
        assertTrue("Выбрана вкладка начинок", mainPage.fillingsSelected());
    }

    @Test
    public void checkBunsPass() {
        mainPage.clickFillings();
        mainPage.clickBuns();
        assertTrue("Выбрана вкладка соусов", mainPage.bunsSelected());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}

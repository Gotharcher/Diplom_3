import env.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ConstructorLinksTest {
    private final String browserName;
    public WebDriver driver;
    public MainPage mainPage;

    public ConstructorLinksTest(String browserName) {
        this.browserName = browserName;
    }

    @Parameterized.Parameters
    public static Object[][] selectBrowser() {
        return new Object[][]{
                {"chrome"},
                {"yandex"},
        };
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        if (browserName.equals("yandex")) {
            System.setProperty("webdriver.chrome.driver", Constants.YABROWSER_PATH);
        }
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

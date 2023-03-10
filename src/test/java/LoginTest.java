import env.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import model.User;
import model.UserRequest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import pages.RestorePasswordPage;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class LoginTest {

    public WebDriver driver;
    public LoginPage loginPage;
    public MainPage mainPage;
    public static String accessToken;
    public static User user;

    private final String browserName;

    public LoginTest(String browserName) {
        this.browserName = browserName;
    }

    @Parameterized.Parameters
    public static Object[][] selectBrowser() {
        return new Object[][]{
                {"chrome"},
                {"yandex"},
        };
    }

    @BeforeClass
    public static void beforeClass(){
        RestAssured.baseURI = Constants.SITE_ADDRESS;
        user = User.createRandomUser();
        accessToken = UserRequest.createUser(user).path("accessToken");
    }

    @Before
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        if(browserName.equals("yandex")){
            System.setProperty("webdriver.chrome.driver", Constants.YABROWSER_PATH);
        }
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void checkLoginFromLoginPage(){
        driver.get(Constants.SITE_ADDRESS+Constants.LOGIN_URL);
        checks();
    }

    @Test
    public void checkLoginFromMainPagePersonal(){
        driver.get(Constants.SITE_ADDRESS);
        mainPage.waitLoading();
        mainPage.clickPersonalText();
        checks();
    }

    @Test
    public void checkLoginFromMainPageEnterAccount(){
        driver.get(Constants.SITE_ADDRESS);
        mainPage.waitLoading();
        mainPage.clickEnterAccount();
        checks();
    }

    @Test
    public void checkLoginFromRegistrationHyperlink(){
        driver.get(Constants.SITE_ADDRESS+Constants.REGISTER_URL);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.clickSingInHyperlink();
        checks();
    }

    @Test
    public void checkLoginFromForgotPasswordHyperlink(){
        driver.get(Constants.SITE_ADDRESS+Constants.FORGOT_URL);
        RestorePasswordPage restorePasswordPage = new RestorePasswordPage(driver);
        restorePasswordPage.clickSingInHyperlink();
        checks();
    }

    public void checks(){
        assertTrue("Зашли на страницу логина", pageURLIsLoginPage());
        loginPage.enterCredsAndClickLogin(user);
        assertTrue(loadedPageAfterSuccessfulLogin());
    }

    @Step("We are at Login page")
    public boolean pageURLIsLoginPage(){
        loginPage.waitLoading();
        return driver.getCurrentUrl().equals(Constants.SITE_ADDRESS+Constants.LOGIN_URL);
    }

    @Step("After login, we have to be redirected to main page and button Create Order is present")
    public boolean loadedPageAfterSuccessfulLogin(){
        mainPage.waitLoading();
        return mainPage.createOrderDispalyed();
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @AfterClass
    public static void afterClass() {
        UserRequest.deleteUser(accessToken);
    }
}

import env.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.User;
import model.UserRequest;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import pages.LoginPage;
import pages.MainPage;
import pages.PersonalPage;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PersonalTest {

    public static String accessToken, refreshToken;
    public static User user;
    public WebDriver driver;
    public PersonalPage personalPage;
    public MainPage mainPage;
    public LoginPage loginPage;

    @BeforeClass
    public static void beforeClass() {
        RestAssured.baseURI = Constants.SITE_ADDRESS;
        user = User.createRandomUser();
        Response response = UserRequest.createUser(user);
        accessToken = response.path("accessToken");
        refreshToken = response.path("refreshToken");
    }

    @AfterClass
    public static void afterClass() {
        UserRequest.deleteUser(accessToken);
    }

    @Before
    public void setUp() {
        driver = SetUpWebDriverEnviroment.setUpDriver();
        mainPage = new MainPage(driver);
        personalPage = new PersonalPage(driver);
        loginPage = new LoginPage(driver);
        driver.get(Constants.SITE_ADDRESS);
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
    }

    @Test
    public void checkPersonalExitButton() {
        driver.get(Constants.SITE_ADDRESS + "/account");
        personalPage.waitLoading();
        personalPage.clickExitButton();
        loginPage.waitLoading();
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        assertNull("Access-токен очистился", localStorage.getItem("accessToken"));
        assertEquals("Оказались на странице логина после выхода", Constants.SITE_ADDRESS + Constants.LOGIN_URL, driver.getCurrentUrl());
    }

    @Test
    public void checkFromPersonaToMainViaLogoPassage() {
        driver.get(Constants.SITE_ADDRESS + "/account");
        personalPage.waitLoading();
        personalPage.clickLogo();
        mainPage.waitLoading();
        assertEquals("Оказались на главной странице после Лого", Constants.SITE_ADDRESS + "/", driver.getCurrentUrl());
    }

    @Test
    public void checkFromPersonaToMainViaConstructorPassage() {
        driver.get(Constants.SITE_ADDRESS + "/account");
        personalPage.waitLoading();
        personalPage.clickConstructor();
        mainPage.waitLoading();
        assertEquals("Оказались на главной странице после Конструктора", Constants.SITE_ADDRESS + "/", driver.getCurrentUrl());
    }

    @Test
    public void checkFromMainToPersonalPassage() {
        mainPage.waitLoading();
        mainPage.clickPersonalText();
        personalPage.waitLoading();
        assertEquals("Оказались в личном кабинете после главной страницы", Constants.SITE_ADDRESS + Constants.PERSONAL, driver.getCurrentUrl());
    }

    @After
    public void tearDown() {
        System.out.println(driver.manage().getCookies());
        driver.quit();
    }
}

import env.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.User;
import model.UserRequest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.RegistrationPage;

import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class RegistrationTest{

    public WebDriver driver;
    public RegistrationPage registrationPage;
    public String accessToken;

    private final String browserName;

    public RegistrationTest(String browserName) {
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
    public void setUp(){
        RestAssured.baseURI = Constants.SITE_ADDRESS;
        WebDriverManager.chromedriver().setup();
        if(browserName.equals("yandex")){
            System.setProperty("webdriver.chrome.driver", Constants.DRIVER_FOLDER + "yandexdriver.exe");
        }
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        registrationPage = new RegistrationPage(driver);
        driver.get(Constants.SITE_ADDRESS+Constants.REGISTER_URL);
        registrationPage.waitLoading();
    }

    @Test
    public void registerTest(){
        User user = User.createRandomUser();
        registrationPage.enterRegisterUserFields(user);
        registrationPage.clickFinallyRegisterButton();
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//main//h2[text() = 'Вход']")));
        Assert.assertEquals("Перешли на страницу логина", Constants.SITE_ADDRESS+Constants.LOGIN_URL, driver.getCurrentUrl());
        Response response = UserRequest.authUser(user);
        Assert.assertEquals("Удалось залогиниться с данными созданного пользователя", 200, response.statusCode());
        accessToken = response.path("accessToken");
    }

    @Test
    public void shortPasswordTest(){
        User user = User.createRandomUser();
        user.setPassword("12345");
        registrationPage.enterRegisterUserFields(user);
        registrationPage.clickFinallyRegisterButton();
        Assert.assertTrue("Отображается ошибка о некорректном пароле", registrationPage.checkShortPasswordError());
        Assert.assertEquals("Остались на странице логина", Constants.SITE_ADDRESS+Constants.REGISTER_URL, driver.getCurrentUrl());
        Response response = UserRequest.authUser(user);
        Assert.assertFalse("Не удалось залогиниться с данными созданного пользователя", response.path("success"));
    }

    @After
    public void tearDown(){
        driver.quit();
        if(accessToken != null) {
            UserRequest.deleteUser(accessToken);
        }

    }
}

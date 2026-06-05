
/*

DriverRulesAndBaseTest — абстрактный базовый класс для UI‑тестов, реализующий паттерн Base Test Class (базовый тестовый класс).

Основная задача: обеспечить единую инфраструктуру для всех тестов:

инициализацию WebDriver и настройку браузера;

создание экземпляров Page Object‑классов (POM);

подготовку тестовых данных (создание пользователя);

очистку после тестов (удаление пользователя, закрытие браузера);

общую логику навигации (открытие страницы авторизации с разных точек входа).

Ключевые функции
Класс отвечает за:

Инициализацию WebDriver — запускает браузер (Chrome или Yandex) с нужными настройками.

Конфигурацию браузера — задаёт аргументы командной строки (например, --no-sandbox, --disable-dev-shm-usage).

Управление временем ожидания — устанавливает неявное ожидание (implicitlyWait) для поиска элементов.

Максимизацию окна браузера — обеспечивает стабильное отображение элементов.

Открытие стартовой страницы — переходит на BASE_URL приложения.

Создание экземпляров POM‑классов — инициализирует объекты страниц (HomePage, LoginPage и т. д.) для взаимодействия с UI.

Подготовку тестовых данных — генерирует модель пользователя (UserModel) и создаёт пользователя через API.

Очистка после тестов — удаляет созданного пользователя и закрывает браузер.

Общую логику навигации — метод openLoginPage() позволяет открыть страницу авторизации с разных стартовых точек.


 */



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import API_INTERACTION.UserActivities;
import PARAMETERS.ApiEndpoints;
import PARAMETERS.LoginStartingPoints;
import DATA_GENERATION.UserDataGenerator;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import USER_REQUESTS.UserModel;
import org.junit.After;
import org.junit.Before;
import POM.LoginPage;
import POM.HomePage;
import POM.PasswordRecoveryPage;
import POM.RegistrationPage;

import java.time.Duration;

public abstract class DriverRulesAndBaseTest {

    protected WebDriver driver;
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected PasswordRecoveryPage passwordRecoveryPage;
    protected RegistrationPage registrationPage;
    protected UserActivities userActivities;
    protected UserModel userModel;
    protected String accessToken;

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        System.out.println("Запуск теста для браузера: " + browser);

        if ("yandex".equals(browser)) {
            WebDriverManager.chromedriver().driverVersion("149.0.7827.54").setup();
            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:/Program Files (x86)/Yandex/YandexBrowser/application/browser.exe");
            options.addArguments("--remote-allow-origins=*", "--no-sandbox",
                    "--disable-dev-shm-usage", "--disable-gpu");

            try {
                driver = new ChromeDriver(options);
                System.out.println("Яндекс Браузер успешно запущен");
            } catch (Exception e) {
                System.err.println("Ошибка запуска Яндекс Браузера: " + e.getMessage());
                e.printStackTrace();
                throw e;
            }
        } else {
            WebDriverManager.chromedriver().driverVersion("149.0.7827.54").setup();
            driver = new ChromeDriver();
            System.out.println("Chrome успешно запущен");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get(ApiEndpoints.BASE_URL);
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        passwordRecoveryPage = new PasswordRecoveryPage(driver);
        registrationPage = new RegistrationPage(driver);
        userActivities = new UserActivities();
        if (!(this instanceof RegistrationTest)) {
            userModel = UserDataGenerator.GenerateUser();
            accessToken = userActivities.createUser(userModel).extract().path("accessToken");
        }
    }

    @After
    public void tearDown() {
        if (accessToken != null) userActivities.deleteUser(accessToken);
        if (driver != null) driver.quit();
    }

    @Step("Открытие страниц авторизации через разные {entryPoint}")
    public void openLoginPage(LoginStartingPoints entryPoint) {
        switch (entryPoint) {
            case HOME_PAGE:
                homePage.clickLoginButton(); break;
            case PROFILE:
                homePage.clickProfileButton(); break;
            case REGISTER_PAGE:
                homePage.clickProfileButton();
                loginPage.loginPageToLoad();
                loginPage.clickLinkRegister();
                registrationPage.clickLinkLogin(); break;
            case PASSWORD_RECOVERY_PAGE:
                homePage.clickProfileButton();
                loginPage.loginPageToLoad();
                loginPage.clickLinkRecoverPassword();
                passwordRecoveryPage.clickLinkLogin(); break;
        }
    }

}
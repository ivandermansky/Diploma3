
/*

LoginTest — параметризованный UI‑тест, наследующий базовую инфраструктуру от DriverRulesAndBaseTest. Он проверяет авторизацию пользователя через разные точки входа в систему.

Ключевые связи:

наследуется от DriverRulesAndBaseTest → получает готовую инфраструктуру для тестов;
использует LoginStartingPoints → задаёт параметры для параметризованного запуска;
взаимодействует с LoginPage, HomePage (из POM) → для работы с UI;
опирается на ApiEndpoints → для проверки URL после авторизации;
использует данные из UserModel → для ввода учётных данных.

Как работает LoginTest?

НАСЛЕДОВАНИЕ И ПАРАМЕТРИЗАЦИЯ
Наследование: LoginTest extends DriverRulesAndBaseTest — получает:

настройку браузера (Chrome/Yandex);
создание тестового пользователя через API;
очистку после теста (удаление пользователя, закрытие браузера);
метод openLoginPage() для открытия страницы авторизации с разных точек входа.

Параметризация: аннотирован @RunWith(Parameterized.class) — тест запускается несколько раз с разными параметрами.

 */


import PARAMETERS.ApiEndpoints;
import PARAMETERS.LoginStartingPoints;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginTest extends DriverRulesAndBaseTest {

    private final LoginStartingPoints entryPoint;

    public LoginTest(LoginStartingPoints entryPoint) {
        this.entryPoint = entryPoint;
    }

    @Parameterized.Parameters(name = "Место входа: {0}")
    public static Object[][] testData() {
        return new Object[][] {
                {LoginStartingPoints.HOME_PAGE},
                {LoginStartingPoints.PROFILE},
                {LoginStartingPoints.PASSWORD_RECOVERY_PAGE},
                {LoginStartingPoints.REGISTER_PAGE}
        };
    }

    @Test
    @DisplayName("Авторизация пользователя через разные точки входа")
    @Description("Проверка, что пользователь успешно авторизуется через разные точки входа")
    public void loginFromDifferentEntryPointTest() {
        openLoginPage(entryPoint);
        loginPage.loginPageToLoad();
        loginPage.enterEmail(userModel.getEmail());
        loginPage.enterPassword(userModel.getPassword());
        loginPage.clickButtonLogin();
        homePage.constructorToLoad();
        assertEquals(ApiEndpoints.BASE_URL + "/", driver.getCurrentUrl());
    }

}
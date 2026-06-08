
/*

RegistrationTest — тестовый класс для проверки функциональности регистрации пользователя. Он наследует базовую инфраструктуру от DriverRulesAndBaseTest и использует ряд других классов для выполнения своих задач.

Ключевые связи:

RegistrationTest extends DriverRulesAndBaseTest — наследует общую инфраструктуру для тестов (настройка браузера, очистка после тестов и т. д.);

использует UserDataGenerator — для генерации тестовых данных пользователя;
опирается на ApiEndpoints — для получения URL страниц приложения;
взаимодействует с POM‑классами (HomePage, LoginPage, RegistrationPage) — для работы с UI;
использует UserModel — для хранения данных тестового пользователя;

 */


import parameters.ApiEndpoints;
import data.UserDataGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegistrationTest extends DriverRulesAndBaseTest {

    private static final String EXPECTED_LOGIN_URL = ApiEndpoints.BASE_URL + "/login";
    private static final String EXPECTED_ERROR_TEXT = "Некорректный пароль";
    private String tempToken;

    @Before
    public void initUser() {
        userModel = UserDataGenerator.GenerateUser();
    }

    @After
    public void deleteCreatedUser() {
        if (tempToken == null) {
            tempToken = userActivities.loginUser(userModel).extract().path("accessToken");
        }
        if (tempToken != null) userActivities.deleteUser(tempToken);
    }

    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверка, что регистрация проходит, и открывается страница авторизации")
    public void registerSuccessfullyWithValidDataTest() {
        goToRegisterPage();
        registrationPage.setName(userModel.getName())
                .setEmail(userModel.getEmail())
                .setPassword(userModel.getPassword())
                .clickButtonRegister();
        loginPage.loginPageToLoad();
        assertEquals(EXPECTED_LOGIN_URL, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Некорректный пароль. Минимальная допустимая длина пароля — шесть символов.")
    @Description("Проверить, что при вводе в поле пароля менее 6 символов, появляется ошибка")
    public void showErrorForShortPasswordTest() {
        goToRegisterPage();
        registrationPage.setName(userModel.getName())
                .setEmail(userModel.getEmail())
                .setPassword("qwe")
                .clickButtonRegister();
        String error = registrationPage.getExceptionText();
        assertEquals(EXPECTED_ERROR_TEXT, error);
    }

    @Step("Открытие страницы авторизации через кнопку 'Личный кабинет'")
    private void goToRegisterPage() {
        homePage.clickProfileButton();
        loginPage.loginPageToLoad();
        loginPage.clickLinkRegister();
    }

}
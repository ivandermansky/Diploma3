
/*

Класс UserActivities — это API‑клиент для работы с пользовательскими операциями в веб‑приложении.
Он реализует паттерн Service Layer / API Client: инкапсулирует HTTP‑запросы к серверу и предоставляет простой Java‑интерфейс для тестов.

Класс отвечает за:

- отправку HTTP‑запросов к API‑эндпоинтам;

- формирование тела запросов (JSON) из Java‑объектов;

- настройку заголовков (например, Authorization);

- возврат ответа в формате ValidatableResponse для последующей валидации;

- интеграцию с Allure — добавление понятных шагов в отчёты.

 */

package api;

import parameters.ApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import user.LoginRequest;
import user.UserModel;

import static io.restassured.RestAssured.given;

public class UserActivities {

    @Step("Создать пользователя")
    public ValidatableResponse createUser(UserModel userModel) {
        return given()
                .baseUri(ApiEndpoints.BASE_URL)
                .contentType(ContentType.JSON)
                .body(userModel)
                .when()
                .post(ApiEndpoints.REGISTER_USER)
                .then();
    }

    @Step("Авторизовать пользователя")
    public ValidatableResponse loginUser(UserModel userModel) {
        LoginRequest request = new LoginRequest(userModel.getEmail(), userModel.getPassword());
        return given()
                .baseUri(ApiEndpoints.BASE_URL)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN_USER)
                .then();
    }

    @Step("Удалить пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .baseUri(ApiEndpoints.BASE_URL)
                .header("Authorization", accessToken)
                .when()
                .delete(ApiEndpoints.DELETE_USER)
                .then();
    }

}
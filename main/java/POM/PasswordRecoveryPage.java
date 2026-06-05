
/*

Класс PasswordRecoveryPage — это Page Object (объект страницы) в рамках паттерна Page Object Model (POM) для страницы восстановления пароля веб‑приложения.

Основная задача: инкапсулировать логику взаимодействия с элементами страницы восстановления пароля — скрыть детали работы с Selenium за простым Java‑методом. Это упрощает написание и поддержку UI‑тестов, связанных с восстановлением доступа.

КЛЮЧЕВЫЕ ФУНКЦИИ
Класс отвечает за:

Локацию элементов — хранит локатор ссылки «Войти» (linkLogin).
Взаимодействие с элементами — предоставляет метод для клика по ссылке, позволяющей перейти со страницы восстановления пароля на страницу авторизации.
Интеграцию с Allure — аннотация @Step добавляет понятный шаг в отчёты Allure, делая их читаемыми для нетехнических специалистов.
Упрощение тестового кода — тестировщик вызывает готовый метод вместо написания сырых Selenium‑команд.


 */


package POM;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordRecoveryPage {

    private final WebDriver driver;

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By linkLogin = By.linkText("Войти");

    @Step("Клин на ссылку 'Войти' из страницы восстановления пароля")
    public void clickLinkLogin() {
        driver.findElement(linkLogin).click();
    }

}
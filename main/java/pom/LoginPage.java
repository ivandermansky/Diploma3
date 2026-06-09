
/*

Класс LoginPage — это Page Object (объект страницы) в рамках паттерна Page Object Model (POM) для страницы авторизации веб‑приложения.

Основная задача: инкапсулировать логику взаимодействия с элементами страницы входа в систему — скрыть детали работы с Selenium за простыми Java‑методами. Это упрощает написание и поддержку UI‑тестов.

КЛЮЧЕВЫЕ ФУНКЦИИ
Класс отвечает за:

Локацию элементов — хранит XPath‑ и link‑локаторы всех важных элементов страницы авторизации (emailField, passwordField, buttonLogin и т.д.).
Взаимодействие с элементами — предоставляет методы для ввода данных в поля, кликов по кнопкам и ссылкам.
Ожидание загрузки — использует WebDriverWait для ожидания готовности элементов к взаимодействию.
Интеграцию с Allure — аннотации @Step добавляют понятные шаги в отчёты Allure, делая их читаемыми для нетехнических специалистов.
Упрощение тестового кода — тестировщик вызывает готовые методы вместо написания сырых Selenium‑команд.


 */


package pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By headerLogin = By.xpath(".//h2[text()='Вход']");
    private final By emailField = By.xpath("//input[@name='name' or @type='text']");
    private final By passwordField = By.xpath("//input[@name='Пароль' or @type='password']");
    private final By buttonLogin = By.xpath(".//button[text()='Войти']");
    private final By registerLink = By.linkText("Зарегистрироваться");
    private final By recoverPasswordButton = By.linkText("Восстановить пароль");

    @Step("Загрузка страницы авторизации")
    public void loginPageToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> d.findElement(headerLogin).isDisplayed());
    }

    @Step("Ввести электронную почту: {email}")
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Ввести пароль")
    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажать на кнопку 'Войти'")
    public void clickButtonLogin() {
        driver.findElement(buttonLogin).click();
    }

    @Step("Нажать на кнопку 'Зарегистрироваться'")
    public void clickLinkRegister() {
        driver.findElement(registerLink).click();
    }

    @Step("Нажать на кнопку 'Восстановить пароль'")
    public void clickLinkRecoverPassword() {
        driver.findElement(recoverPasswordButton).click();
    }

}
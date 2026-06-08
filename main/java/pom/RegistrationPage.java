
/*

Класс RegistrationPage — это Page Object (объект страницы) в рамках паттерна Page Object Model (POM) для страницы регистрации веб‑приложения.

Основная задача: инкапсулировать логику взаимодействия с элементами страницы регистрации — скрыть детали работы с Selenium за простыми Java‑методами. Это упрощает написание, поддержку и повторное использование UI‑тестов, связанных с регистрацией пользователей.

КЛЮЧЕВЫЕ ФУНКЦИИ
Класс отвечает за:

Локацию элементов — хранит XPath‑ и link‑локаторы всех важных элементов страницы регистрации (nameField, emailField, passwordField и т. д.).
Взаимодействие с элементами — предоставляет методы для ввода данных в поля, кликов по кнопкам и ссылкам.
Ожидание загрузки и готовности элементов — использует WebDriverWait и ExpectedConditions для надёжного взаимодействия с элементами (ожидание видимости, кликабельности).
Получение данных с страницы — позволяет извлекать текст ошибок и другие данные для валидации.
Интеграцию с Allure — аннотации @Step добавляют понятные шаги в отчёты Allure, делая их читаемыми для нетехнических специалистов.
Упрощение тестового кода — тестировщик вызывает готовые методы вместо написания сырых Selenium‑команд.
Цепочечные вызовы — методы setName(), setEmail(), setPassword() возвращают this, позволяя выстраивать цепочки вызовов.


 */


package pom;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private final By nameField = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By emailField = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordField = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By buttonRegister = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By errorTextPassword = By.xpath(".//p[@class='input__error text_type_main-default']");
    private final By linkLogin = By.linkText("Войти");

    @Step("Ввод имени: {name}")
    public RegistrationPage setName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        return this;
    }

    @Step("Ввод email: {email}")
    public RegistrationPage setEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        return this;
    }

    @Step("Ввод пароля")
    public RegistrationPage setPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        return this;
    }

    @Step("Клик на кнопку 'Зарегистрироваться'")
    public void clickButtonRegister() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonRegister)).click();
    }

    @Step("Текст ошибки под полем пароль отображается")
    public String getExceptionText() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorTextPassword));
        return error.getText();
    }

    @Step("Клик на ссылку 'Войти' для перехода на страницу авторизации")
    public void clickLinkLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(linkLogin)).click();
    }



}
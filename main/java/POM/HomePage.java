
/*

Класс HomePage — это Page Object (объект страницы) в рамках паттерна Page Object Model (POM) для главной страницы веб‑приложения «Стелларбургерс».

Основная задача: инкапсулировать логику взаимодействия с элементами главной страницы — скрыть детали работы с Selenium за простыми Java‑методами.

КЛЮЧЕВЫЕ ФУНКЦИИ
Класс отвечает за:

Локацию элементов — хранит XPath‑локаторы всех важных элементов главной страницы (LoginButton, ProfileButton и т.д.).
Взаимодействие с элементами — предоставляет методы для кликов, проверок видимости и получения атрибутов.
Ожидание загрузки — использует WebDriverWait для ожидания готовности элементов к взаимодействию.
Прокрутку к элементам — применяет JavascriptExecutor для прокрутки страницы, чтобы элемент стал видимым.
Интеграцию с Allure — аннотации @Step добавляют понятные шаги в отчёты Allure.
Типобезопасность — использует BurgerIngredients (enum) для передачи секций конструктора, исключая ошибки ввода.


 */



package POM;

import PARAMETERS.BurgerIngredients;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    private final By ProfileButton = By.xpath("//a[@href='/account']");
    private final By LoginButton = By.xpath(".//button[text()='Войти в аккаунт']");
    private final By constructorHeader = By.xpath(".//h1[text()='Соберите бургер']");
    private final By BunsList = By.xpath("//span[text()='Булки']/parent::div");
    private final By SaucesList = By.xpath("//span[text()='Соусы']/parent::div");
    private final By FillingsList = By.xpath("//span[text()='Начинки']/parent::div");

    @Step("Клик по кнопке 'Войти в аккаунт' из главной страницы")
    public void clickLoginButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(LoginButton))
                .click();
    }

    @Step("Клик по кнопке 'Личный кабинет' из главной страницы")
    public void clickProfileButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(ProfileButton))
                .click();
    }

    @Step("Отображение конструктора на главной странице")
    public void constructorToLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(constructorHeader));
    }

    @Step("Клики по секциям конструктора: {section}")
    public void clickConstructorSection(BurgerIngredients section) {
        By tabLocator = getSectionLocator(section);
        WebElement tabElement = new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.elementToBeClickable(tabLocator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tabElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tabElement);
        new WebDriverWait(driver, Duration.ofSeconds(50))
                .until(ExpectedConditions.attributeContains(tabLocator, "class", "tab_tab_type_current"));
    }

    @Step("Получить атрибут 'class' секций: {section}")
    public String getClassName(BurgerIngredients section) {
        return driver.findElement(getSectionLocator(section)).getAttribute("class");
    }

    private By getSectionLocator(BurgerIngredients section) {
        switch (section) {
            case BUNS: return BunsList;
            case SAUCES: return SaucesList;
            case FILLINGS: return FillingsList;
            default:
                throw new IllegalArgumentException("Раздел конструктора неизвестен: " + section);
        }
    }

}
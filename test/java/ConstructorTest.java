
/*

ConstructorTest — параметризованный UI‑тест, наследующий базовую инфраструктуру от DriverRulesAndBaseTest.
Он проверяет работу конструктора бургеров на главной странице, используя классы:

HomePage (из POM) — для взаимодействия с элементами конструктора;
BurgerIngredients (enum) — для передачи тестируемых секций;
ApiEndpoints — для получения базового URL приложения.

Иерархия и связи:

ConstructorTest ↔ DriverRulesAndBaseTest (наследование)
ConstructorTest → HomePage (использование)
ConstructorTest → BurgerIngredients (параметры теста)
ConstructorTest → ApiEndpoints (URL приложения)
HomePage → BurgerIngredients (взаимодействие с секциями)


 */


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import parameters.ApiEndpoints;
import parameters.BurgerIngredients;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pom.HomePage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ConstructorTest extends DriverRulesAndBaseTest {

    private WebDriver driver;
    private HomePage homePage;
    private final BurgerIngredients burgerIngredients;

    public ConstructorTest(BurgerIngredients burgerIngredients) {
        this.burgerIngredients = burgerIngredients;
    }

    @Parameterized.Parameters(name = "Раздел конструктора бургеров: {0}")
    public static Object[][] testData() {
        return new Object[][] {
                {BurgerIngredients.FILLINGS},
                {BurgerIngredients.SAUCES},
                {BurgerIngredients.BUNS}
        };
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        driver.get(ApiEndpoints.BASE_URL);
        homePage.constructorToLoad();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Навигация по разделам конструктора бургеров")
    @Description("Проверка, что при клике на раздел открывается раздел")
    public void switchConstructorSectionTest() {
        if (burgerIngredients == BurgerIngredients.FILLINGS) homePage.clickConstructorSection(BurgerIngredients.FILLINGS);
        else homePage.clickConstructorSection(BurgerIngredients.FILLINGS);
        homePage.clickConstructorSection(burgerIngredients);
        String cls = homePage.getClassName(burgerIngredients);
        assertTrue("Раздел не активен: " + burgerIngredients, cls.contains("tab_tab_type_current"));
    }

}
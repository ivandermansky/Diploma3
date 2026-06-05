
/*

Класс ApiEndpoints — это централизованное хранилище URL‑эндпоинтов API приложения «Стелларбургерс» (Stellar Burgers).
Его основная задача — хранить и предоставлять пути к API‑методам в виде констант.

Можно сказать, что это единый справочник URL, который используют другие классы для отправки HTTP‑запросов.

Класс состоит из статических финальных полей (public static final), каждое из которых задаёт один URL‑компонент:

BASE_URL — базовый URL сервера: "https://stellarburgers.education-services.ru". Это корень всех запросов.

REGISTER_USER — путь для регистрации пользователя: "/api/auth/register".

LOGIN_USER — путь для авторизации пользователя: "/api/auth/login".

DELETE_USER — путь для удаления пользователя: "/api/auth/user".

 */

package PARAMETERS;

public class ApiEndpoints {

    public static final String BASE_URL = "https://stellarburgers.education-services.ru";
    public static final String REGISTER_USER = "/api/auth/register";
    public static final String LOGIN_USER = "/api/auth/login";
    public static final String DELETE_USER = "/api/auth/user";

}
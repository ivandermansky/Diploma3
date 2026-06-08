
/*

Класс LoginRequest — это DTO (Data Transfer Object, объект передачи данных), предназначенный для передачи учётных данных пользователя (email и пароля) в запросах к API при авторизации.

Основная задача: структурировать и инкапсулировать данные, необходимые для выполнения операции входа в систему, чтобы передавать их между компонентами приложения или в HTTP‑запросах.

КЛЮЧЕВЫЕ ФУНКЦИИ
Класс отвечает за:

Хранение данных авторизации — содержит поля для email и пароля пользователя.
Сериализацию в JSON — может быть автоматически преобразован в JSON‑формат для отправки в теле HTTP‑запроса (например, POST /api/auth/login).
Десериализацию из JSON — может принимать данные из JSON‑ответа сервера или запроса клиента и заполнять свои поля.
Упрощение передачи параметров — заменяет набор отдельных аргументов (email, password) на единый объект.
Типизацию данных — чётко определяет, какие данные ожидаются для авторизации (строка email, строка пароля).

@AllArgsConstructor — генерирует конструктор со всеми полями (LoginRequest(email, password)).

@NoArgsConstructor — генерирует конструктор без аргументов.

 */

package user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String email;
    private String password;

}
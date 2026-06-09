
/*

Класс UserDataGenerator — это генератор тестовых данных для пользователей.
Его основная задача — автоматически создавать реалистичные тестовые данные (имя, email, пароль) для использования в тестах.

Класс отвечает за:

- генерацию случайных, но правдоподобных данных пользователя;

- обеспечение уникальности данных при каждом вызове (снижает риск конфликтов при регистрации);

- соответствие данных бизнес‑требованиям (например, длина пароля);

- упрощение подготовки тестовых данных — не нужно вручную придумывать имена и email‑адреса.

 */


package data;

import com.github.javafaker.Faker;
import user.UserModel;

import java.util.Locale;

public class UserDataGenerator {

    private static final Faker faker = new Faker(Locale.ENGLISH);

    public static UserModel GenerateUser() {
        return new UserModel(generateName(), generateEmail(), generateValidPassword());
    }

    public static String generateName() {
        return faker.name().firstName();
    }

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static String generateValidPassword() {
        return faker.internet().password(6, 15, true, true);
    }

}
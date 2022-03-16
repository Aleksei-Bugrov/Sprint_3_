package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.Courier.Courier;
import ru.yandex.praktikum.Courier.CourierClient;
import ru.yandex.praktikum.Courier.CourierCredentials;

import static org.junit.Assert.assertEquals;

public class TestWhenLoginCourier {

    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Авторизация несуществующего курьера")
    @Description("Такой учетки нет в базе")
    public void loginNonExistentCourier() {
        Courier courier = Courier.getAllRandom();
        String errorCode = courierClient.requestForNonExistentLoginAndPassword(CourierCredentials.from(courier));

        assertEquals("Что-то пошло не так...", errorCode, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    @Description("Авторизация курьера, с ошибкой в поле - пароль")
    public void courierLoginWithoutPass() {
        Courier firstCourier = Courier.getAllRandom();
        courierClient.create(firstCourier);
        courierClient.login(CourierCredentials.from(firstCourier));

        Courier secondCourier = new Courier(firstCourier.login, firstCourier.password + RandomStringUtils.randomAlphabetic(1), firstCourier.firstName);
        String errorCode = courierClient.requestForNonExistentLoginAndPassword(CourierCredentials.from(secondCourier));

        assertEquals("Что-то пошло не так...", errorCode, "Учетная запись не найдена");

    }

    @Test
    @DisplayName("Авторизация с неверным логином")
    @Description("Авторизация курьера, с ошибкой в поле - логин")
    public void courierLoginWithoutLogin() {
        Courier firstCourier = Courier.getAllRandom();
        courierClient.create(firstCourier);
        courierClient.login(CourierCredentials.from(firstCourier));

        Courier secondCourier = new Courier(firstCourier.login + RandomStringUtils.randomAlphabetic(1), firstCourier.password, firstCourier.firstName);
        String errorCode = courierClient.requestForNonExistentLoginAndPassword(CourierCredentials.from(secondCourier));

        assertEquals("Что-то пошло не так...", errorCode, "Учетная запись не найдена");

    }

    @Test
    @DisplayName("Авторизация с пустым паролем")
    @Description("Авторизация курьера, без ввода пароля")
    public void courierLoginFromNullPass() {
        Courier firstCourier = Courier.getAllRandom();
        courierClient.create(firstCourier);
        courierClient.login(CourierCredentials.from(firstCourier));

        Courier secondCourier = new Courier(firstCourier.login, "", firstCourier.firstName);
        String errorCode = courierClient.requestWithoutLogin(CourierCredentials.from(secondCourier));

        assertEquals("Что-то пошло не так...", errorCode, "Недостаточно данных для входа");

    }

    @Test
    @DisplayName("Авторизация с пустым логином")
    @Description("Авторизация курьера, без ввода логина")
    public void courierLoginFromNullLogin() {
        Courier firstCourier = Courier.getAllRandom();
        courierClient.create(firstCourier);
        courierClient.login(CourierCredentials.from(firstCourier));

        Courier secondCourier = new Courier("", firstCourier.password, firstCourier.firstName);
        String errorCode = courierClient.requestWithoutLogin(CourierCredentials.from(secondCourier));

        assertEquals("Что-то пошло не так...", errorCode, "Недостаточно данных для входа");

    }
}
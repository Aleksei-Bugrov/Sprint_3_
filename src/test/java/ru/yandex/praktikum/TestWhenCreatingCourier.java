package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import ru.yandex.praktikum.Courier.Courier;
import ru.yandex.praktikum.Courier.CourierClient;
import ru.yandex.praktikum.Courier.CourierCredentials;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;


public class TestWhenCreatingCourier {

    private CourierClient courierClient;
    private int courierId;


    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }


    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }


    @Test
    @DisplayName("Регистрация курьера")
    @Description("Создали курьера и прошли авторизацию")
    public void courierBeCreatedWithValidData() {

        Courier courier = Courier.getAllRandom();

        boolean isCourierCreated = courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.from(courier));

        assertThat("Курьер не создан", isCourierCreated);
        assertThat("Данные не корректны", courierId, is(not(0)));

    }

    @Test
    @DisplayName("Курьер уже был создан")
    @Description("Проверили, что нельзя создать одинаковых курьеров")
    public void createAnExistingCourier() {

        Courier firstCourier = Courier.getAllRandom();

        Courier secondCourier = new Courier(firstCourier.login, firstCourier.password, firstCourier.firstName);
        courierClient.create(firstCourier);
        courierId = courierClient.login(CourierCredentials.from(firstCourier));

        int code = courierClient.creatingAnAlreadyCreated(secondCourier);
        assertThat("Этот логин уже используется", code, is(409));

    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("Проверили, что нельзя создать курьера с пустым логином")
    public void creatingCourierWithoutLogin() {

        Courier courier = Courier.getRandomNoLogin();
        int errorCode = courierClient.createNoData(courier);

        assertThat("Недостаточно данных для создания учетной записи", errorCode, is(400));

    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Проверили, что нельзя создать курьера с пустым паролем")
    public void creatingCourierWithoutPassword() {

        Courier courier = Courier.getRandomNoPassword();
        int errorCode = courierClient.createNoData(courier);

        assertThat("Недостаточно данных для создания учетной записи", errorCode, is(400));
    }


}

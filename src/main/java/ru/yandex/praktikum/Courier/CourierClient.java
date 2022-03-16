package ru.yandex.praktikum.Courier;

import io.qameta.allure.Step;
import ru.yandex.praktikum.BaseSpec;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseSpec {

    public final String PATH = BASE_URL + "courier/";

    @Step("Create courier {courier}")
    public boolean create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    @Step("Login as {courierCredentials}")
    public int login(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then().assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Delete courier {courierId}")
    public void delete(int courierId) {
        given()
                .spec(getBaseSpec())
                .when()
                .delete(PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }

    public int creatingAnAlreadyCreated(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .assertThat()
                .statusCode(409)
                .extract()
                .path("code");
    }

    public int createNoData(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("code");
    }

    public String requestForNonExistentLoginAndPassword(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when().post(PATH + "login/")
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }

    public String requestWithoutLogin(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(PATH + "login/")
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

}

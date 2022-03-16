package ru.yandex.praktikum.Order;

import io.restassured.response.Response;
import ru.yandex.praktikum.BaseSpec;
import ru.yandex.praktikum.Order.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseSpec {

    private final String ORDER_PATH = "http://qa-scooter.praktikum-services.ru/api/v1/orders/";


    public int createOrder(Order order) {
        return given().spec(getBaseSpec()).body(order).when().post(ORDER_PATH).then().assertThat().statusCode(201).extract().statusCode();
    }

    public Response getListOrder() {
        return given().spec(getBaseSpec()).get(ORDER_PATH);
    }

    public int getOrderTrack(Order order) {
        return given().spec(getBaseSpec()).body(order).when().post(ORDER_PATH).then().assertThat().statusCode(201).extract().path("track");

    }

}

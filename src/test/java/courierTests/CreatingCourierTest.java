package courierTests;

import courier.Courier;
import courier.CourierClient;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.CoreMatchers.*;

public class CreatingCourierTest {

    CourierClient courierClient;
    Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandomCourier();
    }
    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Создание нового курьера")
    public void successCreatingCourierTest() {
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(201);
        createResponse.assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание одинаковых курьеров")
    public void creatingIdenticalCourierTest() {
        courierClient.createCourier(courier);
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(409);
        createResponse.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    public void creatingExistingLoginCourierTest() {
        courierClient.createCourier(courier);
        String firstCourierLogin = courier.getLogin();
        Courier secondCourier = Courier.getRandomCourier();
        secondCourier.setLogin(firstCourierLogin);
        ValidatableResponse createResponse = courierClient.createCourier(secondCourier).statusCode(409);
        createResponse.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void creatingCourierWithoutLoginTest() {
        courier.setLogin("");
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(400);
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void creatingCourierWithoutPasswordTest() {
        courier.setPassword("");
        ValidatableResponse createResponse = courierClient.createCourier(courier).statusCode(400);
        createResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
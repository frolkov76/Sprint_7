package orderTests;

import order.Order;
import order.OrderClient;
import order.OrderGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderTest {
    private OrderClient orderClient;
    private Order order;
    private int statusCode;

    public OrderTest(Order order, int statusCode) {
        this.order = order;
        this.statusCode = statusCode;
    }
    @Parameterized.Parameters(name = "Тестовые данные[{index}] : Заказ {0}, StatusCode {1}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {OrderGenerator.getWithBlack(), SC_CREATED},
                {OrderGenerator.getWithGrey(), SC_CREATED},
                {OrderGenerator.getWithBlackAndGrey(), SC_CREATED},
                {OrderGenerator.getWithoutColours(), SC_CREATED}
        };
    }
    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Проверка, что в теле ответа содержится track")
    public void orderCanBeCreated() {
        ValidatableResponse responseCreate = orderClient.create(order);
        int actualStatusCode = responseCreate.extract().statusCode();
        int track = responseCreate.extract().path("track");
        assertThat("Expected track number", track, notNullValue());
        assertEquals("Status Code incorrect", statusCode, actualStatusCode);
    }
}

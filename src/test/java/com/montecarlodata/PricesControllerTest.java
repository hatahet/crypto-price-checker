package com.montecarlodata;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PricesControllerTest {

    @Test
    public void testGetPricesForLast24HoursEndpoint() {
        given()
                .when().get("/prices/btcusd")
                .then()
                .statusCode(200);
    }
}

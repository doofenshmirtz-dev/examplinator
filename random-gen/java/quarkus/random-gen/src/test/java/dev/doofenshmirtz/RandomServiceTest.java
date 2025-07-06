package dev.doofenshmirtz;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class RandomServiceTest {
    @Test
    void testHelloEndpoint() {
        given()
                .when().get("/getRandom")
                .then()
                .statusCode(200);
    }

}
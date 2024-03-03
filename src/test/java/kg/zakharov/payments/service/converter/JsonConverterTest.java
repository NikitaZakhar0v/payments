package kg.zakharov.payments.service.converter;

import kg.zakharov.payments.model.response.DepositPaymentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest
class JsonConverterTest {


    private final JsonConverter jsonConverter;

    @Autowired
    JsonConverterTest(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    @Test
    void testToModelWithException() {
        String json = "invalid json";
        try {
            jsonConverter.toModel(json, DepositPaymentResponse.class);
        } catch (JsonConverter.JsonConverterException e) {
            assertTrue(true);
        }
        fail();
    }

}

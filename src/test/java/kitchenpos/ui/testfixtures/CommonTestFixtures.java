package kitchenpos.ui.testfixtures;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonTestFixtures {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

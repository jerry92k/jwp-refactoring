package kitchenpos.ui;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import kitchenpos.application.ProductService;
import kitchenpos.domain.Product;
import kitchenpos.ui.testfixtures.CommonTestFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerTest {

    private static final String BASE_PATH = "/api/products";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("상품 등록")
    @Test
    void create() throws Exception {
        //given
        String productName = "육개장";
        BigDecimal price = BigDecimal.valueOf(9000);
        Product requestProduct = new Product(productName, price);
        Product expectedProduct = new Product(1L, productName, price);
        given(productService.create(any())).willReturn(expectedProduct);

        //when, then
        mockMvc.perform(post(BASE_PATH).content(CommonTestFixtures.asJsonString(requestProduct))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(expectedProduct.getId()))
            .andExpect(jsonPath("$.name").value(expectedProduct.getName()));
    }

    @DisplayName("상품 목록 조회")
    @Test
    void list() throws Exception {
        //given
        List<Product> expectedProducts = Arrays.asList(
            new Product(1L, "육개장", BigDecimal.valueOf(9000)),
            new Product(1L, "과메기", BigDecimal.valueOf(22000)));
        given(productService.list()).willReturn(expectedProducts);

        //when, then
        mockMvc.perform(get(BASE_PATH)).andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[*]['id']",
                containsInAnyOrder(
                    expectedProducts.stream().mapToInt(product -> product.getId().intValue())
                        .boxed()
                        .toArray(Integer[]::new))));
    }
}

package kitchenpos.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.common.exception.PriceNotAcceptableException;
import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import kitchenpos.common.vo.Price;

class ProductTest {

    @DisplayName("상품 등록")
    @ParameterizedTest
    @CsvSource(value = {"초코퍼지,5000", "무료아이스크림,0", "할인케이크,10000.24"})
    void construct(String name, BigDecimal decimal) {
        Price price = Price.valueOf(decimal);
        Product product = new Product(name, price);
        Product expectedProduct = new Product(name, price);
        assertThat(product.getName()).isEqualTo(expectedProduct.getName());
        assertThat(product.getPrice()).isEqualTo(expectedProduct.getPrice());
    }

    @DisplayName("상품 가격은 0원 이상이어야 한다.")
    @Test
    void construct_exception1() {
        assertThatThrownBy(() -> new Product("잘못된상품", Price.valueOf(BigDecimal.valueOf(-10000))))
            .isInstanceOf(PriceNotAcceptableException.class);
    }

    @DisplayName("상품 가격은 필수값이다.")
    @Test
    void construct_exception2() {
        assertThatThrownBy(() -> new Product("가격미정 상품", null))
            .isInstanceOf(PriceNotAcceptableException.class);
    }
}

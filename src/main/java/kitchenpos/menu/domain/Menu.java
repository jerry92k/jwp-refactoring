package kitchenpos.menu.domain;

import kitchenpos.product.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Price price;

    @Column(name = "menu_group_id")
    private Long menuGroupId;

    @OneToMany(mappedBy = "menuId")
    private List<MenuProduct> menuProducts;

    public Menu() {
    }

    public Menu(String name, Price price, Long menuGroupId) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
    }

    public Menu(String name, BigDecimal price, Long menuGroupId, List<MenuProduct> menuProducts) {
        this.name = name;
        this.price = new Price(price);
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;

        Price totalPrice = menuProducts.stream()
                .map(menuProduct -> menuProduct.getProduct()
                        .getPrice().multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                .reduce(new Price(BigDecimal.ZERO), Price::add)
                ;

        if (this.price.compareTo(totalPrice) > 0) {
            throw new IllegalArgumentException("상품의 총 가격보다 메뉴의 가격이 더 높을수는 없습니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Long getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}

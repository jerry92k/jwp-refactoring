package kitchenpos.menu.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import kitchenpos.common.vo.Price;
import kitchenpos.menugroup.domain.MenuGroup;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    @Column(nullable = false)
    private Price price;

    @JoinColumn(name = "menu_group_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MenuGroup menuGroup;

    @Embedded
    private MenuProducts menuProducts = new MenuProducts();

    protected Menu() {
    }

    public Menu(String name, Price price, MenuGroup menuGroup) {
        this(null, name, price, menuGroup, new ArrayList<>());
    }

    public Menu(String name, Price price, MenuGroup menuGroup,
        List<MenuProduct> menuProducts) {
        this(null, name, price, menuGroup, menuProducts);
    }

    public Menu(Long id, String name, Price price, MenuGroup menuGroup) {
        this(id, name, price, menuGroup, new ArrayList<>());
    }

    public Menu(Long id, String name, Price price, MenuGroup menuGroup,
        List<MenuProduct> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        changeMenuProducts(menuProducts);
    }

    private void changeMenuProducts(List<MenuProduct> inputMenuProducts) {
        for (MenuProduct menuProduct : inputMenuProducts) {
            menuProduct.assignMenu(this);
        }
        menuProducts.changeMenuProducts(inputMenuProducts, price);
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

    public BigDecimal getPriceVal() {
        return price.getPrice();
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public List<MenuProduct> getMenuProductList() {
        return menuProducts.getMenuProducts();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Menu)) {
            return false;
        }

        Menu menu = (Menu) o;
        return Objects.equals(getId(), menu.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

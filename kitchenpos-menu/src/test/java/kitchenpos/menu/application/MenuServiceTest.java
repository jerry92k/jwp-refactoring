package kitchenpos.menu.application;

import static org.assertj.core.api.Assertions.assertThat;

import kitchenpos.menugroup.application.MenuGroupService;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.MenuProductValidator;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.product.domain.Product;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.menu.dto.MenuResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kitchenpos.menugroup.MenuGroupTestFixtures;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import kitchenpos.menu.MenuTestFixtures;
import kitchenpos.common.vo.Price;
import kitchenpos.common.vo.Quantity;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuGroupService menuGroupService;

    @Mock
    private MenuProductValidator menuProductValidator;

    private MenuGroup 추천메뉴;
    private Product 타코야끼;
    private Product 뿌링클;

    @BeforeEach
    void setUp() {
        //background
        타코야끼 = new Product(1L, "타코야끼", Price.valueOf(BigDecimal.valueOf(12000)));
        뿌링클 = new Product(2L, "뿌링클", Price.valueOf(BigDecimal.valueOf(15000)));
        추천메뉴 = new MenuGroup(1L, "추천메뉴");
    }

    @DisplayName("메뉴를 등록할 수 있다.")
    @Test
    void create() {
        //given
        MenuGroupTestFixtures.메뉴_그룹_존재여부_조회시_응답_모킹(menuGroupService, 추천메뉴);

        List<MenuProduct> menuProducts = Arrays.asList(
            new MenuProduct(타코야끼.getId(), new Quantity(3L)),
            new MenuProduct(뿌링클.getId(), new Quantity(1L)));

        Menu menu = new Menu("타코야끼와 뿌링클", Price.valueOf(BigDecimal.valueOf(51000)), 추천메뉴,
            menuProducts);
        MenuTestFixtures.메뉴_저장_결과_모킹(menuRepository, menu);

        //when
        MenuRequest menuRequest = MenuTestFixtures.convertToMenuRequest(menu);
        MenuResponse menuResponse = menuService.create(menuRequest);

        //then
        assertThat(menuResponse.getName()).isEqualTo(menu.getName());
    }

    @DisplayName("메뉴 목록을 조회할 수 있다.")
    @Test
    void list() {
        // given
        List<MenuProduct> menuProducts = Arrays.asList(
            new MenuProduct(타코야끼.getId(), new Quantity(3L)),
            new MenuProduct(뿌링클.getId(), new Quantity(1L)));
        List<Menu> menus = Arrays.asList(
            new Menu(1L, "타코야끼와 뿌링클", Price.valueOf(BigDecimal.valueOf(51000)), 추천메뉴,
                menuProducts));
        MenuTestFixtures.메뉴_전체조회_모킹(menuRepository, menus);

        //when
        List<MenuResponse> findMenus = menuService.list();

        //then
        assertThat(findMenus.size()).isEqualTo(menus.size());
        메뉴목록_검증(findMenus, menus);
    }

    private void 메뉴목록_검증(List<MenuResponse> findMenus, List<Menu> menus) {
        List<Long> findProductIds = findMenus.stream()
            .map(MenuResponse::getId)
            .collect(Collectors.toList());
        List<Long> expectProductIds = menus.stream()
            .map(Menu::getId)
            .collect(Collectors.toList());
        assertThat(findProductIds).containsAll(expectProductIds);
    }
}

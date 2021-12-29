package kitchenpos.ordertable.ui;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kitchenpos.ordertable.application.TableService;
import kitchenpos.ordertable.common.CommonTestFixtures;
import kitchenpos.ordertable.domain.OrderTable;
import kitchenpos.ordertable.dto.OrderTableRequest;
import kitchenpos.ordertable.dto.OrderTableResponse;
import java.util.Arrays;
import java.util.List;

import kitchenpos.ordertable.TableTestFixtures;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import kitchenpos.ordertable.vo.NumberOfGuests;

@WebMvcTest(TableRestController.class)
class TableRestControllerTest {

    private static final String BASE_PATH = "/api/tables";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TableService tableService;

    @DisplayName("주문 테이블 등록")
    @Test
    void create() throws Exception {
        //given
        int numberOfGuests = 6;
        boolean orderClose = false;
        OrderTableRequest requestOrderTable = TableTestFixtures.convertToOrderTableRequest(
            numberOfGuests, orderClose);
        OrderTableResponse expectedOrderTable = OrderTableResponse.from(
            new OrderTable(1L, new NumberOfGuests(numberOfGuests), orderClose));
        given(tableService.create(any())).willReturn(expectedOrderTable);

        //when, then
        mockMvc.perform(post(BASE_PATH)
                .content(CommonTestFixtures.asJsonString(requestOrderTable))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(expectedOrderTable.getId()));
    }

    @DisplayName("주문 테이블 조회")
    @Test
    void list() throws Exception {
        //given
        List<OrderTableResponse> expectedOrderTables = OrderTableResponse.fromList(Arrays.asList(
            new OrderTable(1L, new NumberOfGuests(6), false),
            new OrderTable(2L, new NumberOfGuests(3), false)
        ));

        given(tableService.list()).willReturn(expectedOrderTables);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_PATH))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[*]['id']",
                containsInAnyOrder(
                    expectedOrderTables.stream()
                        .mapToInt(t -> t.getId().intValue())
                        .boxed()
                        .toArray(Integer[]::new))));
    }

    @DisplayName("테이블 상태 변경")
    @Test
    void changeEmpty() throws Exception {
        //given
        boolean changeEmpty = false;
        OrderTableRequest requestOrderTable = TableTestFixtures.convertToIsOrderCloseChangeRequest(
            changeEmpty);
        OrderTableResponse expectedOrderTable = OrderTableResponse.from(
            new OrderTable(1L, new NumberOfGuests(6), changeEmpty));
        given(tableService.changeOrderClose(any(), any())).willReturn(expectedOrderTable);

        //when,then
        mockMvc.perform(put(BASE_PATH + "/{orderTableId}/order_close", expectedOrderTable.getId())
                .content(CommonTestFixtures.asJsonString(requestOrderTable))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(expectedOrderTable.getId()))
            .andExpect(jsonPath("$.orderClose").value(expectedOrderTable.isOrderClose()));
    }

    @DisplayName("테이블 방문자 수 변경")
    @Test
    void changeNumberOfGuests() throws Exception {
        //given
        int numberOfGuests = 5;
        OrderTableRequest requestOrderTable = TableTestFixtures.convertToNumberOfGuestsChangeRequest(
            numberOfGuests);

        OrderTableResponse expectedOrderTable = OrderTableResponse.from(
            new OrderTable(1L, new NumberOfGuests(numberOfGuests), false));
        given(tableService.changeNumberOfGuests(
            any(), any())).willReturn(expectedOrderTable);

        //when,then
        mockMvc.perform(
                put(BASE_PATH + "/{orderTableId}/number-of-guests", expectedOrderTable.getId())
                    .content(CommonTestFixtures.asJsonString(requestOrderTable))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(expectedOrderTable.getId()))
            .andExpect(jsonPath("$.numberOfGuests").value(expectedOrderTable.getNumberOfGuests()));
    }
}

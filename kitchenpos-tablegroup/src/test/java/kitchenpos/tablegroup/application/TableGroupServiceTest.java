package kitchenpos.tablegroup.application;

import static org.assertj.core.api.Assertions.assertThat;

import kitchenpos.ordertable.domain.OrderTable;
import kitchenpos.tablegroup.domain.TableGroup;
import kitchenpos.tablegroup.domain.TableGroupRepository;
import kitchenpos.tablegroup.dto.TableGroupRequest;
import kitchenpos.tablegroup.dto.TableGroupResponse;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import kitchenpos.tablegroup.TableGroupTestFixtures;
import kitchenpos.ordertable.vo.NumberOfGuests;
import kitchenpos.tablegroup.dto.TableId;

@ExtendWith(MockitoExtension.class)
class TableGroupServiceTest {

    @Mock
    private TableGroupRepository tableGroupRepository;

    @InjectMocks
    private TableGroupService tableGroupService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @DisplayName("테이블 그룹을 등록할 수 있다.")
    @Test
    void create() {
        //given
        List<TableId> orderTables = Arrays.asList(
           new TableId(1L), new TableId(2L));
        TableGroupRequest tableGroupRequest = TableGroupTestFixtures.convertToTableGroupRequest(
            orderTables);
        TableGroup tableGroup = new TableGroup(1L);
        TableGroupTestFixtures.테이블그룹_저장_결과_모킹(tableGroupRepository, tableGroup);

        //when
        TableGroupResponse savedTableGroup = tableGroupService.create(tableGroupRequest);

        //then
        assertThat(savedTableGroup.getId()).isEqualTo(tableGroup.getId());
    }
}

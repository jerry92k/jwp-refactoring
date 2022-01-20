package kitchenpos.tablegroup;

import static org.mockito.BDDMockito.given;

import kitchenpos.tablegroup.domain.TableGroup;
import kitchenpos.tablegroup.domain.TableGroupRepository;
import kitchenpos.tablegroup.dto.TableGroupRequest;
import kitchenpos.tablegroup.dto.TableId;

import java.util.List;

import org.mockito.ArgumentMatchers;

public class TableGroupTestFixtures {

    public static void 테이블그룹_저장_결과_모킹(TableGroupRepository tableGroupRepository,
        TableGroup tableGroup) {
        given(tableGroupRepository.save(ArgumentMatchers.any()))
            .willReturn(tableGroup);
    }

    public static TableGroupRequest convertToTableGroupRequest(List<TableId> orderTables) {
        return new TableGroupRequest(orderTables);
    }
}

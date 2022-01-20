package kitchenpos.tablegroup.dto;

public class TableId {

	private Long id;

	private TableId() {
	}

	public TableId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}

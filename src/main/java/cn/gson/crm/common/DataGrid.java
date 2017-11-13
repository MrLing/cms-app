package cn.gson.crm.common;

import org.springframework.data.domain.Page;

public final class DataGrid<T> {

	private Long total;

	private Iterable<T> rows;

	private Iterable<?> footer;

	public DataGrid(Iterable<T> rows) {
		this.rows = rows;
	}

	public DataGrid(Long total, Iterable<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public DataGrid(Page<T> page) {
		this.total = page.getTotalElements();
		this.rows = page.getContent();
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Iterable<T> getRows() {
		return rows;
	}

	public void setRows(Iterable<T> rows) {
		this.rows = rows;
	}

	public Iterable<?> getFooter() {
		return footer;
	}

	public void setFooter(Iterable<?> footer) {
		this.footer = footer;
	}
}

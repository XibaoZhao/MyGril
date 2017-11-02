package com.example.domain;

import java.util.List;

public class GridPageBean<T> {
	private int page;// 当前页
	private int total;// 一共多少页
	private long records;// 查处结果总行数
	private List<T> rows;// 当前页每一行的内容

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}

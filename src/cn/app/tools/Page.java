package cn.app.tools;

public class Page {
	private Integer currPageNo; // 当前页码 curr是current单词的简写，当前的意思
	private Integer pageSize; // 每一页显示的数据行数，页面大小
	private Integer totalCount; // 总的记录数。
	private Integer totalPageCount; // 总的页数
	
	public void setTotalCount(Integer totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			this.totalPageCount =this.totalCount % this.pageSize == 0 ? this.totalCount/ this.pageSize: this.totalCount / this.pageSize + 1;
		}
	}
	
	public Integer getCurrPageNo() {
		return currPageNo;
	}

	public void setCurrPageNo(Integer currPageNo) {
		this.currPageNo = currPageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

}

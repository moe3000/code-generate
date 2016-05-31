package com.generate.action.utils;

import java.util.List;

/**
 * 分页查询类 
 */
public class Pagination extends BaseQuery {


	private static final long serialVersionUID = -5703424773591759935L;
	
	//默认每页显示数目
	public static final int DEF_COUNT = 10;
	
	//总记录数
    private int total = 0;
    
    //每页显示数目
    private int pageSize = 10;
    
    //当前页数
    private int pageNo = 1;
    
    //查询的起始位置,从0开始计算（实际mysql是0，oracle是1,sqlserver是1）
    @SuppressWarnings("unused")
	private int startRow = 0;
    
    //查询的起始位置,从1开始计算（实际mysql是0，oracle是1,sqlserver是1）
    @SuppressWarnings("unused")
	private int oneStartRow = 1;
    
    
    //查询的结束位置,mysql不需要
    @SuppressWarnings("unused")
	private int endRow = 0;
       
    //查询出来的数据
    @SuppressWarnings("rawtypes")
	private List data;
    
    
    private String orderStr;
    
    /**
     * 调整分页,处理查询的范围不在总数范围内的情况。
     */
    private void adjustPage() {
        if (total < 0) {
            total = 0;
        }
        if (pageSize <= 0) {
            pageSize = DEF_COUNT;
        }
        if ((pageNo - 1) * pageSize >= total) {
            pageNo = getTotalPage();
        }
        if (pageNo <= 0) {
            pageNo = 1;
        }
    }


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
		adjustPage();
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public int getPageNo() {
		return pageNo;
	}


	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}


	public int getStartRow() {
        if (pageNo > 1) {
            return (pageSize * (pageNo - 1));
        } else {
            return 0;
        }
	}


	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}


	public int getEndRow() {
        if (pageNo > 0) {
            return Math.min(pageSize * pageNo, total);
        } else {
            return 0;
        }
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getOneStartRow() {
        if (pageNo > 1) {
            return (pageSize * (pageNo - 1)) + 1;
        } else {
            return 1;
        }
	}

	public void setOneStartRow(int oneStartRow) {
		this.oneStartRow = oneStartRow;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}
	
	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}
	
    public int getTotalPage() {
        int totalPage = this.getTotal() / this.getPageSize();
        if (this.getTotal() % this.getPageSize() != 0 || totalPage == 0) {
            totalPage++;
        }
        return totalPage;
    }

}

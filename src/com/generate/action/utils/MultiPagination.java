package com.generate.action.utils;

/**
 * 页码带分页的分页类
 */
public class MultiPagination extends Pagination {
	

	private static final long serialVersionUID = 6933723641111195429L;
	//每页中页码的数目
    public static final int PAGE_COUNT = 10;
    
    public boolean isFirstPage() {
        return this.getPageNo() <= PAGE_COUNT;
    }

    public boolean isLastPage() {
        return getTotalPage() <= ((this.getPageNo()-1)/PAGE_COUNT+1)*PAGE_COUNT;
    }

    public int getNextPage() {
        if (isLastPage()) {
            return this.getPageNo();
        } else {
            return Math.min(this.getPageNo() + 10, getTotalPage());
        }
    }

    public int getPrePage() {
        if (isFirstPage()) {
            return this.getPageNo();
        } else {
            return Math.max(this.getPageNo() - 10,1);
        }
    }
    
    public int getBeginPage() {
        if (this.getPageNo() > 0) {
            return (PAGE_COUNT * ((this.getPageNo()-1)/PAGE_COUNT)) + 1;
        } else {
            return 0;
        }
    }

    public int getEndPage() {
        if (this.getPageNo() > 0) {
            return Math.min(PAGE_COUNT * ((this.getPageNo()-1)/PAGE_COUNT+1), getTotalPage());
        } else {
            return 0;
        }
    }
}

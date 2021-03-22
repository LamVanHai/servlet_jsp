package com.lamvanhai.paging;

public class PageRequest implements PageAble {
    private int pageIndex;
    private int pageSize;

    public PageRequest() {

    }

    private PageRequest(int pageIndex, int pageSize) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public static PageRequest of(int pageIndex, int pageSize) {
        return new PageRequest(pageIndex, pageSize);
    }

    @Override
    public int getOffSet() {
        return (pageIndex - 1) * pageSize;
    }

    @Override
    public int getSize() {
        return pageSize;
    }

    @Override
    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

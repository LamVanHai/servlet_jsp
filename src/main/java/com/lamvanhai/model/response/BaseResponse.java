package com.lamvanhai.model.response;

import java.util.List;

public class BaseResponse<T> {
    private long totalItem;
    private List<T> data;
    private long totalPage;
    private long currentPage;

    private BaseResponse(long totalItem, List<T> data) {
        this.totalItem = totalItem;
        this.data = data;
    }

    public static BaseResponse of(long totalItem, List data) {
        return new BaseResponse(totalItem, data);
    }

    public long getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(long totalItem) {
        this.totalItem = totalItem;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }
}

package com.sicau.devicemanagement.domain.model;

public class PostEntity {

    private int size = 10;

    private int page = 1;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

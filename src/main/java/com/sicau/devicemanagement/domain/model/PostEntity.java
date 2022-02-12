package com.sicau.devicemanagement.domain.model;

public class PostEntity {

    private int size;

    private int page;

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

    public PostEntity() {}

    public PostEntity(int size, int page) {
        this.size = size;
        this.page = page;
    }
}

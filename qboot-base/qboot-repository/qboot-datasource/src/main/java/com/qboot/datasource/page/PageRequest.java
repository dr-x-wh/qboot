package com.qboot.datasource.page;

/**
 * One-based page request with a conservative upper bound for public APIs.
 */
public record PageRequest(int page, int size) {

    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 200;

    public PageRequest {
        if (page < 1) {
            throw new IllegalArgumentException("页码不能小于1");
        }
        if (size < 1 || size > MAX_SIZE) {
            throw new IllegalArgumentException("每页条数必须在1到" + MAX_SIZE + "之间");
        }
    }

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size);
    }

    public static PageRequest defaults() {
        return new PageRequest(DEFAULT_PAGE, DEFAULT_SIZE);
    }

    public long offset() {
        return Math.multiplyExact((long) page - 1, size);
    }

}

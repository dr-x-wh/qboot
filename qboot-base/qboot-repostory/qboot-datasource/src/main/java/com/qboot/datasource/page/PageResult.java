package com.qboot.datasource.page;

import java.util.List;
import java.util.Objects;

/**
 * Immutable page result that can be returned directly by the HTTP layer.
 */
public record PageResult<T>(List<T> items, long total, int page, int size, long totalPages) {

    public PageResult {
        items = List.copyOf(Objects.requireNonNull(items, "分页数据不能为空"));
        if (total < 0) {
            throw new IllegalArgumentException("总记录数不能小于0");
        }
        if (page < 1) {
            throw new IllegalArgumentException("页码不能小于1");
        }
        if (size < 1) {
            throw new IllegalArgumentException("每页条数不能小于1");
        }
        if (totalPages != calculateTotalPages(total, size)) {
            throw new IllegalArgumentException("总页数与总记录数、每页条数不匹配");
        }
    }

    public static <T> PageResult<T> of(List<T> items, long total, PageRequest pageRequest) {
        Objects.requireNonNull(pageRequest, "分页参数不能为空");
        return new PageResult<>(items, total, pageRequest.page(), pageRequest.size(), calculateTotalPages(total, pageRequest.size()));
    }

    private static long calculateTotalPages(long total, int size) {
        return total == 0 ? 0 : 1 + (total - 1) / size;
    }

    public boolean hasNext() {
        return page < totalPages;
    }

    public boolean hasPrevious() {
        return page > 1 && total > 0;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

}

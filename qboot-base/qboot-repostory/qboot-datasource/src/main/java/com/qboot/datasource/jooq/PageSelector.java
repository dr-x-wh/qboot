package com.qboot.datasource.jooq;

import com.qboot.datasource.page.PageRequest;
import com.qboot.datasource.page.PageResult;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.SelectLimitStep;

import java.util.List;
import java.util.Objects;

/**
 * Executes simple offset pagination for a jOOQ select query.
 */
@RequiredArgsConstructor
public final class PageSelector {

    private final DSLContext dslContext;

    public <R extends Record> PageResult<R> fetch(SelectLimitStep<R> query, PageRequest pageRequest) {
        return fetch(query, pageRequest, record -> record);
    }

    public <R extends Record, T> PageResult<T> fetch(SelectLimitStep<R> query, PageRequest pageRequest, RecordMapper<? super R, T> mapper) {
        Objects.requireNonNull(query, "查询条件不能为空");
        Objects.requireNonNull(pageRequest, "分页参数不能为空");
        Objects.requireNonNull(mapper, "记录映射器不能为空");

        long total = dslContext.fetchCountLarge(query);
        if (total == 0) {
            return PageResult.of(List.of(), total, pageRequest);
        }

        List<T> items = query.offset(pageRequest.offset()).limit(pageRequest.size()).fetch(mapper);
        return PageResult.of(items, total, pageRequest);
    }

}

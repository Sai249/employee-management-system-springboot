package com.employee.management.dto;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public class PageResponse<T> {
    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final boolean first;
    private final boolean last;

    private PageResponse(Page<T> page) {
        content = page.getContent();
        this.page = page.getNumber();
        size = page.getSize();
        totalElements = page.getTotalElements();
        totalPages = page.getTotalPages();
        first = page.isFirst();
        last = page.isLast();
    }

    public static <S, T> PageResponse<T> from(Page<S> page, Function<S, T> mapper) {
        Page<T> mapped = page.map(mapper);
        return new PageResponse<>(mapped);
    }

    public List<T> getContent() { return content; }
    public int getPage() { return page; }
    public int getSize() { return size; }
    public long getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public boolean isFirst() { return first; }
    public boolean isLast() { return last; }
}

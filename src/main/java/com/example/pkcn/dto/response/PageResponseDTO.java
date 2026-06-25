package com.example.pkcn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PageResponseDTO<T> {
    @JsonProperty("results")
    private List<T> items;
    @JsonProperty("current_page")
    private int currentPage;
    @JsonProperty("page_size")
    private int pageSize;
    @JsonProperty("total_pages")
    private int totalPages;

    public PageResponseDTO(List<T> items, int currentPage, int pageSize, long totalElements) {
        this.items = items;
        this.currentPage = currentPage;
        this.pageSize = pageSize;

        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
    }

    public List<T> getItems() { return items; }
    public int getCurrentPage() { return currentPage; }
    public int getPageSize() { return pageSize; }
    public int getTotalPages() { return totalPages; }
}
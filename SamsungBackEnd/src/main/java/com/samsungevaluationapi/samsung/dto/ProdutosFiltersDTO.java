package com.samsungevaluationapi.samsung.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public class ProdutosFiltersDTO {
    @Nullable
    @JsonProperty(value = "documentNumberFilter")
    private String documentNumberFilter;
    @Nullable
    @JsonProperty(value = "startDateFilter")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDateFilter;

    @Nullable
    @JsonProperty(value = "endDateFilter")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDateFilter;
    @Nullable
    @JsonProperty(value = "currencyCodeFilter")
    private List<String> currencyCodeFilter;

    public ProdutosFiltersDTO() {
    }

    public ProdutosFiltersDTO(@Nullable String documentNumberFilter, @Nullable LocalDate startDateFilter,
                              @Nullable LocalDate endDateFilter, @Nullable List<String> currencyCodeFilter) {
        this.documentNumberFilter = documentNumberFilter;
        this.startDateFilter = startDateFilter;
        this.endDateFilter = endDateFilter;
        this.currencyCodeFilter = currencyCodeFilter;
    }

    @Nullable
    public String getDocumentNumberFilter() {
        return documentNumberFilter;
    }

    public void setDocumentNumberFilter(@Nullable String documentNumberFilter) {
        this.documentNumberFilter = documentNumberFilter;
    }

    @Nullable
    public LocalDate getStartDateFilter() {
        return startDateFilter;
    }

    public void setStartDateFilter(@Nullable LocalDate startDateFilter) {
        this.startDateFilter = startDateFilter;
    }

    @Nullable
    public LocalDate getEndDateFilter() {
        return endDateFilter;
    }

    public void setEndDateFilter(@Nullable LocalDate endDateFilter) {
        this.endDateFilter = endDateFilter;
    }

    @Nullable
    public List<String> getCurrencyCodeFilter() {
        return currencyCodeFilter;
    }

    public void setCurrencyCodeFilter(@Nullable List<String> currencyCodeFilter) {
        this.currencyCodeFilter = currencyCodeFilter;
    }
}

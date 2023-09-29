package com.samsungevaluationapi.samsung.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DocumentoResponse {
    private Long documentId;
    private String documentNumber;
    private String notaFiscal;
    private LocalDate documentDate;
    private Double documentValue;
    private String currencyCode;
    private String currencyDesc;
    private Double documentValueUSD;
    private Double documentValuePEN;
    private Double documentValueBRL;
}

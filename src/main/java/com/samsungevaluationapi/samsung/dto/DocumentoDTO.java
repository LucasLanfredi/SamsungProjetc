package com.samsungevaluationapi.samsung.dto;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class DocumentoDTO {

    public Long documentId;
    public String documentNumber;
    public String notaFiscal;
    public String documentDate;
    public Double documentValue;
    public String currencyCode;

}

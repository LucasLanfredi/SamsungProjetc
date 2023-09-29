package com.samsungevaluationapi.samsung.dto;

import lombok.Data;

@Data
public class CotacaoMoedaDTO {
    private String fromCurrencyCode;
    private String toCurrencyCode;
    private String cotacao;
    private String dataHoraCotacao;
}

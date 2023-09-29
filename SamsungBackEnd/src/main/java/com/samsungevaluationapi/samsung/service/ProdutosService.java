package com.samsungevaluationapi.samsung.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsungevaluationapi.samsung.RestService.RestService;
import com.samsungevaluationapi.samsung.dto.CotacaoMoedaDTO;
import com.samsungevaluationapi.samsung.dto.DocumentoDTO;
import com.samsungevaluationapi.samsung.dto.DocumentoResponse;
import com.samsungevaluationapi.samsung.dto.MoedaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProdutosService {

    @Autowired
    private RestService restService;

    private final static String URL_MOEDAS = "https://sdshealthcheck.cellologistics.com.br/sds-devs-evaluation/evaluation/currency";

    private final static String URL_COTACOES = "https://sdshealthcheck.cellologistics.com.br/sds-devs-evaluation/evaluation/quotation";

    private final static String URL_DOCUMENTOS = "https://sdshealthcheck.cellologistics.com.br/sds-devs-evaluation/evaluation/docs";

    private final static String MOEDA_PEN = "PEN";

    private final static String MOEDA_BRL = "BRL";

    private final static String MOEDA_USD = "USD";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public List<MoedaDTO> getAllMoedas() {
        String json = restService.get(URL_MOEDAS, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<List<MoedaDTO>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<CotacaoMoedaDTO> getAllCotacoes() {
        String json = restService.get(URL_COTACOES, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<List<CotacaoMoedaDTO>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<DocumentoDTO> getAllDocumentos() {
        String json = restService.get(URL_DOCUMENTOS, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, new TypeReference<List<DocumentoDTO>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<DocumentoResponse> criarDocumentos() {
        List<DocumentoDTO> documentoDTOs = getAllDocumentos();
        List<MoedaDTO> moedas = getAllMoedas();
        List<CotacaoMoedaDTO> cotacoes = getAllCotacoes();

        Map<String, MoedaDTO> moedasPorCodigo = new HashMap<>();

        for (MoedaDTO moeda : moedas) {
            moedasPorCodigo.put(moeda.getCurrencyCode(), moeda);
        }

        List<DocumentoResponse> documentosResponse = new ArrayList<>();

        for (DocumentoDTO documentoDTO : documentoDTOs) {
            DocumentoResponse documentoResponse = new DocumentoResponse();
            documentoResponse.setDocumentId(documentoDTO.getDocumentId());
            documentoResponse.setDocumentNumber(documentoDTO.getDocumentNumber());
            documentoResponse.setNotaFiscal(documentoDTO.getNotaFiscal());
            documentoResponse.setDocumentDate(LocalDate.parse(documentoDTO.getDocumentDate(), formatter));
            documentoResponse.setDocumentValue(documentoDTO.getDocumentValue());
            documentoResponse.setCurrencyCode(documentoDTO.getCurrencyCode());

            MoedaDTO moedaDTO = moedasPorCodigo.get(documentoDTO.getCurrencyCode());

            if (moedaDTO != null) {
                documentoResponse.setCurrencyDesc(moedaDTO.getCurrencyDesc());

                Double documentValueUSD = converterMoeda(documentoDTO, MOEDA_USD, cotacoes);
                documentoResponse.setDocumentValueUSD(documentValueUSD);

                Double documentValuePEN = converterMoeda(documentoDTO, MOEDA_PEN, cotacoes);
                documentoResponse.setDocumentValuePEN(documentValuePEN);

                Double documentValueBRL = converterMoeda(documentoDTO, MOEDA_BRL, cotacoes);
                documentoResponse.setDocumentValueBRL(documentValueBRL);

                documentosResponse.add(documentoResponse);
            }
        }

        return documentosResponse;
    }


    public List<DocumentoResponse> criarDocumentoComFiltro(
            String documentNumberFilter,
            LocalDate startDateFilter,
            LocalDate endDateFilter,
            String currencyCodeFilter
    ) {
        List<DocumentoDTO> documentoDTOs = getAllDocumentos();
        List<MoedaDTO> moedas = getAllMoedas();
        List<CotacaoMoedaDTO> cotacoes = getAllCotacoes();

        Map<String, MoedaDTO> moedasPorCodigo = moedas.stream()
                .collect(Collectors.toMap(MoedaDTO::getCurrencyCode, moeda -> moeda));

        List<DocumentoResponse> documentosResponse = new ArrayList<>();

        for (DocumentoDTO documentoDTO : documentoDTOs) {
            LocalDate documentDate = LocalDate.parse(documentoDTO.getDocumentDate(), formatter);

            if ((documentNumberFilter == null || documentoDTO.getDocumentNumber().equals(documentNumberFilter))
                    && (startDateFilter == null || !documentDate.isBefore(startDateFilter))
                    && (endDateFilter == null || !documentDate.isAfter(endDateFilter))
                    && (currencyCodeFilter == null || documentoDTO.getCurrencyCode().equals(currencyCodeFilter))) {

                DocumentoResponse documentoResponse = new DocumentoResponse();
                documentoResponse.setDocumentId(documentoDTO.getDocumentId());
                documentoResponse.setDocumentNumber(documentoDTO.getDocumentNumber());
                documentoResponse.setNotaFiscal(documentoDTO.getNotaFiscal());
                documentoResponse.setDocumentDate(documentDate);
                documentoResponse.setDocumentValue(documentoDTO.getDocumentValue());
                documentoResponse.setCurrencyCode(documentoDTO.getCurrencyCode());

                MoedaDTO moedaDTO = moedasPorCodigo.get(documentoDTO.getCurrencyCode());

                if (moedaDTO != null) {
                    documentoResponse.setCurrencyDesc(moedaDTO.getCurrencyDesc());

                    Double documentValueUSD = converterMoeda(documentoDTO, MOEDA_USD, cotacoes);
                    documentoResponse.setDocumentValueUSD(documentValueUSD);

                    Double documentValuePEN = converterMoeda(documentoDTO, MOEDA_PEN, cotacoes);
                    documentoResponse.setDocumentValuePEN(documentValuePEN);

                    Double documentValueBRL = converterMoeda(documentoDTO, MOEDA_BRL, cotacoes);
                    documentoResponse.setDocumentValueBRL(documentValueBRL);

                    documentosResponse.add(documentoResponse);
                }
            }
        }

        return documentosResponse;
    }

    private Double converterMoeda(
            DocumentoDTO documentoDTO,
            String destinoCurrencyCode,
            List<CotacaoMoedaDTO> cotacoes
    ) {
        Double valorDocumento = documentoDTO.getDocumentValue();
        String origemCurrencyCode = documentoDTO.getCurrencyCode();
        String documentDate = documentoDTO.getDocumentDate();

        if (origemCurrencyCode.equals(destinoCurrencyCode)) {
            return valorDocumento;
        }

        Optional<CotacaoMoedaDTO> cotacaoMaisProxima = cotacoes.stream()
                .filter(c -> origemCurrencyCode.equals(c.getFromCurrencyCode()) && destinoCurrencyCode.equals(c.getToCurrencyCode()))
                .filter(c -> {
                    String dataCotacao = c.getDataHoraCotacao();
                    return dataCotacao.compareTo(documentDate) <= 0;
                })
                .max(Comparator.comparing(c -> c.getDataHoraCotacao()));

        if (cotacaoMaisProxima.isPresent()) {
            Double cotacaoValor = Double.parseDouble(cotacaoMaisProxima.get().getCotacao());
            return valorDocumento * cotacaoValor;
        } else {
            return null;
        }
    }

}


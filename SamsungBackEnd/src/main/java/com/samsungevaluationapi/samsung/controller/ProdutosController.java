package com.samsungevaluationapi.samsung.controller;

import com.samsungevaluationapi.samsung.dto.DocumentoResponse;
import com.samsungevaluationapi.samsung.dto.MoedaDTO;
import com.samsungevaluationapi.samsung.dto.ProdutosFiltersDTO;
import com.samsungevaluationapi.samsung.service.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutosController {

    @Autowired
    ProdutosService produtosService;


    @GetMapping("/moedas")
    public List<String> getAllMoedas() {
        return produtosService.getAllMoedas().stream()
                .map(MoedaDTO::getCurrencyCode)
                .collect(Collectors.toList());
    }

    @PostMapping("/buscarDocumentoComFiltro")
    public List<DocumentoResponse> buscarDocumentoPorNome(@RequestBody @Nullable ProdutosFiltersDTO documentoFilter) {
        return produtosService.criarDocumentoComFiltro(documentoFilter.getDocumentNumberFilter(), documentoFilter.getStartDateFilter(),
                documentoFilter.getEndDateFilter(), documentoFilter.getCurrencyCodeFilter());
    }

    @GetMapping("/todosDocumentos")
    public List<DocumentoResponse> buscarTodosDocumentos() {
        return produtosService.criarDocumentos();
    }

}

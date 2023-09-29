package com.samsungevaluationapi.samsung.controller;

import com.samsungevaluationapi.samsung.dto.DocumentoResponse;
import com.samsungevaluationapi.samsung.dto.MoedaDTO;
import com.samsungevaluationapi.samsung.dto.ProdutosFiltersDTO;
import com.samsungevaluationapi.samsung.service.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    ProdutosService produtosService;


    @GetMapping("/moedas")
    public List<MoedaDTO> getAllMoedas() {
        return produtosService.getAllMoedas();
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

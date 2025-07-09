package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufjf.ssapi.api.dto.ReceitaDTO;
import br.ufjf.ssapi.model.entity.Receita;
import br.ufjf.ssapi.service.ReceitaService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/receitas")
@CrossOrigin
public class ReceitaController {
     private final ReceitaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Receita> receitas = service.getReceitas();
        return ResponseEntity.ok(receitas.stream().map(ReceitaDTO::create).collect(Collectors.toList()));
    }
}

package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufjf.ssapi.api.dto.MedicamentoDTO;
import br.ufjf.ssapi.model.entity.Medicamento;
import br.ufjf.ssapi.service.MedicamentoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/medicamentos")
@CrossOrigin
public class MedicamentoController {
    private final MedicamentoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Medicamento> medicamentos = service.getMedicamentos();
        return ResponseEntity.ok(medicamentos.stream().map(MedicamentoDTO::create).collect(Collectors.toList()));
    }
}

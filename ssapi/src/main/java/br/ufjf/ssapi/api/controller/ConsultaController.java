package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufjf.ssapi.api.dto.ConsultaDTO;
import br.ufjf.ssapi.model.entity.Consulta;
import br.ufjf.ssapi.service.ConsultaService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/consultas")
@CrossOrigin
public class ConsultaController {
     private final ConsultaService service;


    @GetMapping()
    public ResponseEntity get() {
        List<Consulta> Consultas = service.getConsultas();
        return ResponseEntity.ok(Consultas.stream().map(ConsultaDTO::create).collect(Collectors.toList()));
    }
}

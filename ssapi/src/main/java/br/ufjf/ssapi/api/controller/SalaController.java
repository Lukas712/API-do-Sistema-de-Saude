package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufjf.ssapi.api.dto.SalaDTO;
import br.ufjf.ssapi.model.entity.Sala;
import br.ufjf.ssapi.service.SalaService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/salas")
@CrossOrigin
public class SalaController {
    private final SalaService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Sala> salas = service.getSalas();
        return ResponseEntity.ok(salas.stream().map(SalaDTO::create).collect(Collectors.toList()));
    }
}

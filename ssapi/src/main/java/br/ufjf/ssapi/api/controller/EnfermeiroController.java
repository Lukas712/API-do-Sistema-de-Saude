package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.ufjf.ssapi.api.dto.EnfermeiroDTO;
import br.ufjf.ssapi.model.entity.Enfermeiro;
import br.ufjf.ssapi.service.EnfermeiroService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/enfermeiros")
@CrossOrigin
public class EnfermeiroController {
    private final EnfermeiroService service;


    @GetMapping()
    public ResponseEntity get() {
        List<Enfermeiro> enfermeiros = service.getEnfermeiros();
        return ResponseEntity.ok(enfermeiros.stream().map(EnfermeiroDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Enfermeiro> enfermeiro = service.getEnfermeiro(id);
        if (!enfermeiro.isPresent()) {
            return new ResponseEntity("Enfermeiro não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(enfermeiro.map(EnfermeiroDTO::create));
    }
}

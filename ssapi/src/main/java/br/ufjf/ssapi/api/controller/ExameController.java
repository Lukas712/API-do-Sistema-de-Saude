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


import br.ufjf.ssapi.api.dto.ExameDTO;
import br.ufjf.ssapi.model.entity.Exame;
import br.ufjf.ssapi.service.ExameService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/exames")
@CrossOrigin
public class ExameController {
    private final ExameService service;


    @GetMapping()
    public ResponseEntity get() {
        List<Exame> exame = service.getExames();
        return ResponseEntity.ok(exame.stream().map(ExameDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Exame> exame = service.getExame(id);
        if (!exame.isPresent()) {
            return new ResponseEntity("Exame não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(exame.map(ExameDTO::create));
    }
}

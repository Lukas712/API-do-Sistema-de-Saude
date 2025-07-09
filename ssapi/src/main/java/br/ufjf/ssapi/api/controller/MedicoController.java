package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.ufjf.ssapi.api.dto.MedicoDTO;
import br.ufjf.ssapi.model.entity.Medico;
import br.ufjf.ssapi.service.MedicoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/medicos")
@CrossOrigin
public class MedicoController {
    private final MedicoService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Medico> medicos = service.getMedicos();
        return ResponseEntity.ok(medicos.stream().map(MedicoDTO::create).collect(Collectors.toList()));
    }
}

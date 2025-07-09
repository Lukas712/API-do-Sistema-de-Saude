package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufjf.ssapi.api.dto.EspecialidadeDTO;
import br.ufjf.ssapi.model.entity.Especialidade;
import br.ufjf.ssapi.service.EspecialidadeService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/especialidades")
@CrossOrigin
public class EspecialidadeController {
    private final EspecialidadeService service;


    @GetMapping()
    public ResponseEntity get() {
        List<Especialidade> especialidades = service.getEspecialidades();
        return ResponseEntity.ok(especialidades.stream().map(EspecialidadeDTO::create).collect(Collectors.toList()));
    }
}

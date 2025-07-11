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


import br.ufjf.ssapi.api.dto.AssistenteAdministrativoDTO;
import br.ufjf.ssapi.model.entity.AssistenteAdministrativo;
import br.ufjf.ssapi.service.AssistenteAdministrativoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/assistentesAdministrativos")
@CrossOrigin
public class AssistenteAdministrativoController {
    private final AssistenteAdministrativoService service;


    @GetMapping()
    public ResponseEntity get() {
        List<AssistenteAdministrativo> assistenteAdministrativos = service.getAssistenteAdministrativos();
        return ResponseEntity.ok(assistenteAdministrativos.stream().map(AssistenteAdministrativoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AssistenteAdministrativo> assistenteAdministrativo = service.getAssistenteAdministrativo(id);
        if (!assistenteAdministrativo.isPresent()) {
            return new ResponseEntity("Assistente Administrativo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(assistenteAdministrativo.map(AssistenteAdministrativoDTO::create));
    }
}

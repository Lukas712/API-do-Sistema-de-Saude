package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}

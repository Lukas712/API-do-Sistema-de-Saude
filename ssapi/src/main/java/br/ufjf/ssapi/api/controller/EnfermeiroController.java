package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufjf.ssapi.api.dto.AdminDTO;
import br.ufjf.ssapi.api.dto.AssistenteAdministrativoDTO;
import br.ufjf.ssapi.api.dto.EnfermeiroDTO;
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Admin;
import br.ufjf.ssapi.model.entity.AssistenteAdministrativo;
import br.ufjf.ssapi.model.entity.Enfermeiro;
import br.ufjf.ssapi.model.entity.Hospital;
import br.ufjf.ssapi.service.EnfermeiroService;
import br.ufjf.ssapi.service.HospitalService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/enfermeiros")
@CrossOrigin
public class EnfermeiroController {
    private final EnfermeiroService service;
    private final HospitalService hospitalService;


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

     @PostMapping()
    public ResponseEntity post(@RequestBody EnfermeiroDTO dto) {
        try {
            Enfermeiro enfermeiro = converter(dto);
            enfermeiro = service.salvar(enfermeiro);
            return new ResponseEntity(enfermeiro, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Enfermeiro converter(EnfermeiroDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Enfermeiro enfermeiro = modelMapper.map(dto, Enfermeiro.class);
        if (dto.getIdHospital() != null) {
            Optional<Hospital> hospital = hospitalService.getHospital(dto.getIdHospital());
            if (!hospital.isPresent()) {
                enfermeiro.setHospital(null);
            } else {
                enfermeiro.setHospital(hospital.get());
            }
        }
        return enfermeiro;
    }

}

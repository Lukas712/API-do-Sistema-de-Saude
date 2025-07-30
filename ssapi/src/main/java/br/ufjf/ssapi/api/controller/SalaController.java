package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufjf.ssapi.api.dto.AdminDTO;
import br.ufjf.ssapi.api.dto.ExameDTO;
import br.ufjf.ssapi.api.dto.SalaDTO;
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Admin;
import br.ufjf.ssapi.model.entity.Enfermeiro;
import br.ufjf.ssapi.model.entity.Exame;
import br.ufjf.ssapi.model.entity.Hospital;
import br.ufjf.ssapi.model.entity.Paciente;
import br.ufjf.ssapi.model.entity.Sala;
import br.ufjf.ssapi.service.EnfermeiroService;
import br.ufjf.ssapi.service.ExameService;
import br.ufjf.ssapi.service.HospitalService;
import br.ufjf.ssapi.service.PacienteService;
import br.ufjf.ssapi.service.SalaService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/salas")
@CrossOrigin
public class SalaController {
    private final SalaService service;
    private final ExameService exameService;
    private final HospitalService hospitalService;

    @GetMapping()
    public ResponseEntity get() {
        List<Sala> salas = service.getSalas();
        return ResponseEntity.ok(salas.stream().map(SalaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Sala> sala = service.getSala(id);
        if (!sala.isPresent()) {
            return new ResponseEntity("Sala não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sala.map(SalaDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody SalaDTO dto) {
        try {
            Sala sala = converter(dto);
            sala = service.salvar(sala);
            return new ResponseEntity(sala, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SalaDTO dto) {
        if (!service.getSala(id).isPresent()) {
            return new ResponseEntity("Sala não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Sala sala = converter(dto);
            sala.setId(id);
            service.salvar(sala);
            return ResponseEntity.ok(sala);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Sala> sala = service.getSala(id);
        if (!sala.isPresent()) {
            return new ResponseEntity("Sala não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(sala.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Sala converter(SalaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Sala sala = modelMapper.map(dto, Sala.class);
        if (dto.getIdExame() != null) {
            Optional<Exame> exame = exameService.getExame(dto.getIdExame());
            if (!exame.isPresent()) {
                sala.setExame(null);
            } else {
                sala.setExame(exame.get());
            }
        }
        if (dto.getIdHospital() != null) {
            Optional<Hospital> hospital = hospitalService.getHospital(dto.getIdHospital());
            if (!hospital.isPresent()) {
                sala.setHospital(null);
            } else {
                sala.setHospital(hospital.get());
            }
        }
        return sala;
    }
}
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
import br.ufjf.ssapi.api.dto.EnfermeiroDTO;
import br.ufjf.ssapi.api.dto.ExameDTO;
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Enfermeiro;
import br.ufjf.ssapi.model.entity.Exame;
import br.ufjf.ssapi.model.entity.Hospital;
import br.ufjf.ssapi.service.EnfermeiroService;
import br.ufjf.ssapi.service.ExameService;
import br.ufjf.ssapi.service.HospitalService;
import br.ufjf.ssapi.service.PacienteService;
import br.ufjf.ssapi.model.entity.Paciente;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/exames")
@CrossOrigin
public class ExameController {
    private final ExameService service;
    private final EnfermeiroService enfermeiroService;
    private final PacienteService pacienteService;


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

    @PostMapping()
    public ResponseEntity post(@RequestBody ExameDTO dto) {
        try {
            Exame exame = converter(dto);
            exame = service.salvar(exame);
            return new ResponseEntity(exame, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Exame converter(ExameDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Exame exame = modelMapper.map(dto, Exame.class);
        if (dto.getIdEnfermeiro() != null) {
            Optional<Enfermeiro> enfermeiro = enfermeiroService.getEnfermeiro(dto.getIdEnfermeiro());
            if (!enfermeiro.isPresent()) {
                exame.setEnfermeiro(null);
            } else {
                exame.setEnfermeiro(enfermeiro.get());
            }
        }
        if(dto.getIdPaciente() != null) {
            Optional<Paciente> paciente = pacienteService.getPaciente(dto.getIdPaciente());
            if (!paciente.isPresent()) {
                exame.setPaciente(null);
            } else {
                exame.setPaciente(paciente.get());
            }
        }
        return exame;
    }
}

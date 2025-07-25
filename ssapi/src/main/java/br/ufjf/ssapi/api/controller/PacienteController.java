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

import br.ufjf.ssapi.api.dto.EspecialidadeDTO;
import br.ufjf.ssapi.api.dto.PacienteDTO;
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Especialidade;
import br.ufjf.ssapi.model.entity.Paciente;
import br.ufjf.ssapi.service.PacienteService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pacientes")
@CrossOrigin
public class PacienteController {
    private final PacienteService service;

    @GetMapping()
    public ResponseEntity get() {
        List<Paciente> pacientes = service.getPacientes();
        return ResponseEntity.ok(pacientes.stream().map(PacienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Paciente> paciente = service.getPaciente(id);
        if (!paciente.isPresent()) {
            return new ResponseEntity("Paciente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(paciente.map(PacienteDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody EspecialidadeDTO dto) {
        try {
            Paciente paciente = converter(dto);
            paciente = service.salvar(paciente);
            return new ResponseEntity(paciente, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Paciente converter(EspecialidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Paciente paciente = modelMapper.map(dto, Paciente.class);

        return paciente;
    }
}

package br.ufjf.ssapi.api.controller;

import io.swagger.annotations.*;

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
import br.ufjf.ssapi.api.dto.EspecialidadeDTO;
import br.ufjf.ssapi.api.dto.PacienteDTO;
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Admin;
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
    @ApiOperation("Obtém todos os pacientes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pacientes encontrados"),
            @ApiResponse(code = 404, message = "Pacientes não encontrados")
    })
    public ResponseEntity get() {
        List<Paciente> pacientes = service.getPacientes();
        return ResponseEntity.ok(pacientes.stream().map(PacienteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtém um paciente pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paciente encontrado"),
            @ApiResponse(code = 404, message = "Paciente não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Paciente> paciente = service.getPaciente(id);
        if (!paciente.isPresent()) {
            return new ResponseEntity("Paciente não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(paciente.map(PacienteDTO::create));
    }

    @PostMapping()
    @ApiOperation("Cria um novo paciente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paciente criado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao criar o paciente")
    })
    public ResponseEntity post(@RequestBody PacienteDTO dto) {
        try {
            Paciente paciente = converter(dto);
            paciente = service.salvar(paciente);
            return new ResponseEntity(paciente, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um paciente existente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Paciente atualizado com sucesso"),
        @ApiResponse(code = 400, message = "Erro ao atualizar o paciente")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PacienteDTO dto) {
        if (!service.getPaciente(id).isPresent()) {
            return new ResponseEntity("Paciente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Paciente paciente = converter(dto);
            paciente.setId(id);
            service.salvar(paciente);
            return ResponseEntity.ok(paciente);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um paciente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Paciente excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o paciente")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Paciente> paciente = service.getPaciente(id);
        if (!paciente.isPresent()) {
            return new ResponseEntity("Paciente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(paciente.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Paciente converter(PacienteDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Paciente paciente = modelMapper.map(dto, Paciente.class);

        return paciente;
    }
}

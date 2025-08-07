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
import br.ufjf.ssapi.api.dto.ConsultaDTO;
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Admin;
import br.ufjf.ssapi.model.entity.Consulta;
import br.ufjf.ssapi.model.entity.Hospital;
import br.ufjf.ssapi.model.entity.Paciente;
import br.ufjf.ssapi.model.entity.Receita;
import br.ufjf.ssapi.service.ConsultaService;
import br.ufjf.ssapi.service.HospitalService;
import br.ufjf.ssapi.service.PacienteService;
import br.ufjf.ssapi.service.ReceitaService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/consultas")
@CrossOrigin
public class ConsultaController {
     private final ConsultaService service;
     private final PacienteService pacienteService;
     private final ReceitaService receitaService;


    @GetMapping()
    @ApiOperation("Obtém todos as consultas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Consultas encontradas"),
            @ApiResponse(code = 404, message = "Consultas não encontradas")
    })
    public ResponseEntity get() {
        List<Consulta> Consultas = service.getConsultas();
        return ResponseEntity.ok(Consultas.stream().map(ConsultaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtém uma consulta pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Consulta encontrada"),
            @ApiResponse(code = 404, message = "Consulta não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Consulta> consulta = service.getConsulta(id);
        if (!consulta.isPresent()) {
            return new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(consulta.map(ConsultaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Cria uma nova consulta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Consulta criada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao criar a consulta")
    })
    public ResponseEntity post(@RequestBody ConsultaDTO dto) {
        try {
            Consulta consulta = converter(dto);
            consulta = service.salvar(consulta);
            return new ResponseEntity(consulta, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma consulta existente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Consulta atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar a consulta")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ConsultaDTO dto) {
        if (!service.getConsulta(id).isPresent()) {
            return new ResponseEntity("Consulta não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Consulta consulta = converter(dto);
            consulta.setId(id);
            service.salvar(consulta);
            return ResponseEntity.ok(consulta);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui uma consulta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Consulta excluída com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a consulta")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Consulta> consulta = service.getConsulta(id);
        if (!consulta.isPresent()) {
            return new ResponseEntity("Consulta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(consulta.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

     public Consulta converter(ConsultaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Consulta consulta = modelMapper.map(dto, Consulta.class);
        if (dto.getIdReceita() != null) {
            Optional<Receita> receita = receitaService.getReceita(dto.getIdReceita());
            if (!receita.isPresent()) {
                consulta.setReceita(null);
            } else {
                consulta.setReceita(receita.get());
            }
        }
        if (dto.getIdPaciente() != null) {
            Optional<Paciente> paciente = pacienteService.getPaciente(dto.getIdPaciente());
            if (!paciente.isPresent()) {
                consulta.setPaciente(null);
            } else {
                consulta.setPaciente(paciente.get());
            }
        }
        return consulta;
    }
}

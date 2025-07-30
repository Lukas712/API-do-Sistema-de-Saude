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
import br.ufjf.ssapi.api.dto.EnfermeiroDTO;
import br.ufjf.ssapi.api.dto.EspecialidadeDTO;
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Admin;
import br.ufjf.ssapi.model.entity.Especialidade;
import br.ufjf.ssapi.model.entity.Hospital;
import br.ufjf.ssapi.service.EspecialidadeService;
import br.ufjf.ssapi.service.HospitalService;
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

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Especialidade> especialidade = service.getEspecialidade(id);
        if (!especialidade.isPresent()) {
            return new ResponseEntity("Especialidade não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(especialidade.map(EspecialidadeDTO::create));
    }

     @PostMapping()
    public ResponseEntity post(@RequestBody EspecialidadeDTO dto) {
        try {
            Especialidade especialidade = converter(dto);
            especialidade = service.salvar(especialidade);
            return new ResponseEntity(especialidade, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody EspecialidadeDTO dto) {
        if (!service.getEspecialidade(id).isPresent()) {
            return new ResponseEntity("Especialidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Especialidade especialidade = converter(dto);
            especialidade.setId(id);
            service.salvar(especialidade);
            return ResponseEntity.ok(especialidade);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Especialidade> especialidade = service.getEspecialidade(id);
        if (!especialidade.isPresent()) {
            return new ResponseEntity("Especialidade não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(especialidade.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Especialidade converter(EspecialidadeDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Especialidade especialidade = modelMapper.map(dto, Especialidade.class);
        
        return especialidade;
    }
}

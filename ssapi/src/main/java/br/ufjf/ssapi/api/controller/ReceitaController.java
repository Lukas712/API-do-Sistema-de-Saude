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
import br.ufjf.ssapi.api.dto.ReceitaDTO;
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Admin;
import br.ufjf.ssapi.model.entity.Especialidade;
import br.ufjf.ssapi.model.entity.Receita;
import br.ufjf.ssapi.service.ReceitaService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/receitas")
@CrossOrigin
public class ReceitaController {
    private final ReceitaService service;

    @GetMapping()
    @ApiOperation("Obtém todas as receitas")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Receitas encontradas"),
            @ApiResponse(code = 404, message = "Receitas não encontradas")
    })
    public ResponseEntity get() {
        List<Receita> receitas = service.getReceitas();
        return ResponseEntity.ok(receitas.stream().map(ReceitaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtém uma receita pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Receita encontrada"),
            @ApiResponse(code = 404, message = "Receita não encontrada")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Receita> receita = service.getReceita(id);
        if (!receita.isPresent()) {
            return new ResponseEntity("Receita não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(receita.map(ReceitaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Cria uma nova receita")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Receita criada com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao criar a receita")
    })
    public ResponseEntity post(@RequestBody ReceitaDTO dto) {
        try {
            Receita receita = converter(dto);
            receita = service.salvar(receita);
            return new ResponseEntity(receita, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma receita existente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Receita atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar a receita")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ReceitaDTO dto) {
        if (!service.getReceita(id).isPresent()) {
            return new ResponseEntity("Receita não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Receita receita = converter(dto);
            receita.setId(id);
            service.salvar(receita);
            return ResponseEntity.ok(receita);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui uma receita")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Receita excluída com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a receita")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Receita> receita = service.getReceita(id);
        if (!receita.isPresent()) {
            return new ResponseEntity("Receita não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(receita.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Receita converter(ReceitaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Receita receita = modelMapper.map(dto, Receita.class);

        return receita;
    }
}

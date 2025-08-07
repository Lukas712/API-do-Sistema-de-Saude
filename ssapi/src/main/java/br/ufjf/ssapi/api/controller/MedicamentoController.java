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
import br.ufjf.ssapi.api.dto.MedicamentoDTO;
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Admin;
import br.ufjf.ssapi.model.entity.Medicamento;
import br.ufjf.ssapi.model.entity.Receita;
import br.ufjf.ssapi.service.MedicamentoService;
import br.ufjf.ssapi.service.ReceitaService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/medicamentos")
@CrossOrigin
public class MedicamentoController {
    private final MedicamentoService service;
    private final ReceitaService receitaService;

    @GetMapping()
    @ApiOperation("Obtém todos os medicamentos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Medicamentos encontrados"),
            @ApiResponse(code = 404, message = "Medicamentos não encontrados")
    })
    public ResponseEntity get() {
        List<Medicamento> medicamentos = service.getMedicamentos();
        return ResponseEntity.ok(medicamentos.stream().map(MedicamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtém um medicamento pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Medicamento encontrado"),
            @ApiResponse(code = 404, message = "Medicamento não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Medicamento> medicamento = service.getMedicamento(id);
        if (!medicamento.isPresent()) {
            return new ResponseEntity("Medicamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(medicamento.map(MedicamentoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Cria um novo medicamento")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Medicamento criado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao criar o medicamento")
    })
    public ResponseEntity post(@RequestBody MedicamentoDTO dto) {
        try {
            Medicamento medicamento = converter(dto);
            medicamento = service.salvar(medicamento);
            return new ResponseEntity(medicamento, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um medicamento existente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Medicamento atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar o medicamento")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody MedicamentoDTO dto) {
        if (!service.getMedicamento(id).isPresent()) {
            return new ResponseEntity("Medicamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Medicamento medicamento = converter(dto);
            medicamento.setId(id);
            service.salvar(medicamento);
            return ResponseEntity.ok(medicamento);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um medicamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Medicamento excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o medicamento")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Medicamento> medicamento = service.getMedicamento(id);
        if (!medicamento.isPresent()) {
            return new ResponseEntity("Medicamento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(medicamento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Medicamento converter(MedicamentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Medicamento medicamento = modelMapper.map(dto, Medicamento.class);
        if(medicamento.getReceita() != null){
            Optional<Receita> receita = receitaService.getReceita(dto.getIdReceita());
            if(!receita.isPresent()){
                medicamento.setReceita(null);
            } else{
                medicamento.setReceita(receita.get());
            }
        }
    
        return medicamento;
    }
}

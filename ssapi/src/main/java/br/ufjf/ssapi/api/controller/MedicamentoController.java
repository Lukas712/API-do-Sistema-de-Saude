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

import br.ufjf.ssapi.api.dto.MedicamentoDTO;
import br.ufjf.ssapi.exception.DefaultException;
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
    public ResponseEntity get() {
        List<Medicamento> medicamentos = service.getMedicamentos();
        return ResponseEntity.ok(medicamentos.stream().map(MedicamentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Medicamento> medicamento = service.getMedicamento(id);
        if (!medicamento.isPresent()) {
            return new ResponseEntity("Medicamento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(medicamento.map(MedicamentoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody MedicamentoDTO dto) {
        try {
            Medicamento medicamento = converter(dto);
            medicamento = service.salvar(medicamento);
            return new ResponseEntity(medicamento, HttpStatus.CREATED);
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

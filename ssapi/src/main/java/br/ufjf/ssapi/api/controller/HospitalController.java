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
import br.ufjf.ssapi.api.dto.HospitalDTO;
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Admin;
import br.ufjf.ssapi.model.entity.Especialidade;
import br.ufjf.ssapi.model.entity.Hospital;
import br.ufjf.ssapi.service.HospitalService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hospitals")
@CrossOrigin
public class HospitalController {
    private final HospitalService service;


    @GetMapping()
    @ApiOperation("Obtém todos os hospitais")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Hospitais encontrados"),
            @ApiResponse(code = 404, message = "Hospitais não encontrados")
    })
    public ResponseEntity get() {
        List<Hospital> hospital = service.getHospitals();
        return ResponseEntity.ok(hospital.stream().map(HospitalDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtém um hospital pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Hospital encontrado"),
            @ApiResponse(code = 404, message = "Hospital não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Hospital> hospital = service.getHospital(id);
        if (!hospital.isPresent()) {
            return new ResponseEntity("Hospital não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hospital.map(HospitalDTO::create));
    }

    @PostMapping()
    @ApiOperation("Cria um novo hospital")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Hospital criado com sucesso"),
            @ApiResponse(code = 404, message = "Erro ao criar o hospital")
    })
    public ResponseEntity post(@RequestBody HospitalDTO dto) {
        try {
            Hospital hospital = converter(dto);
            hospital = service.salvar(hospital);
            return new ResponseEntity(hospital, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um hospital existente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Hospital atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar o hospital")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody HospitalDTO dto) {
        if (!service.getHospital(id).isPresent()) {
            return new ResponseEntity("Hospital não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Hospital hospital = converter(dto);
            hospital.setId(id);
            service.salvar(hospital);
            return ResponseEntity.ok(hospital);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um hospital")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Hospital excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o hospital")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Hospital> hospital = service.getHospital(id);
        if (!hospital.isPresent()) {
            return new ResponseEntity("Hospital não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(hospital.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Hospital converter(HospitalDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Hospital hospital = modelMapper.map(dto, Hospital.class);
        
        return hospital;
    }
}

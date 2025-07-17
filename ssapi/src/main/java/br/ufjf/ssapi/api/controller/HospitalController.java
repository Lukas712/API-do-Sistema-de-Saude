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
import br.ufjf.ssapi.api.dto.HospitalDTO;
import br.ufjf.ssapi.exception.DefaultException;
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
    public ResponseEntity get() {
        List<Hospital> hospital = service.getHospitals();
        return ResponseEntity.ok(hospital.stream().map(HospitalDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Hospital> hospital = service.getHospital(id);
        if (!hospital.isPresent()) {
            return new ResponseEntity("Hospital não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(hospital.map(HospitalDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody HospitalDTO dto) {
        try {
            Hospital hospital = converter(dto);
            hospital = service.salvar(hospital);
            return new ResponseEntity(hospital, HttpStatus.CREATED);
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

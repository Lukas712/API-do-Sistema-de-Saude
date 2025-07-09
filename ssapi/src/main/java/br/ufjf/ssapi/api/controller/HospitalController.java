package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufjf.ssapi.api.dto.HospitalDTO;
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
}

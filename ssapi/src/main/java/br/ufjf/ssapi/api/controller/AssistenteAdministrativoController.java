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
import br.ufjf.ssapi.api.dto.AssistenteAdministrativoDTO;
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Admin;
import br.ufjf.ssapi.model.entity.Hospital;
import br.ufjf.ssapi.model.entity.AssistenteAdministrativo;
import br.ufjf.ssapi.service.AssistenteAdministrativoService;
import br.ufjf.ssapi.service.HospitalService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/assistentesAdministrativos")
@CrossOrigin
public class AssistenteAdministrativoController {
    private final AssistenteAdministrativoService service;
    private final HospitalService hospitalService;


    @GetMapping()
    public ResponseEntity get() {
        List<AssistenteAdministrativo> assistenteAdministrativos = service.getAssistenteAdministrativos();
        return ResponseEntity.ok(assistenteAdministrativos.stream().map(AssistenteAdministrativoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<AssistenteAdministrativo> assistenteAdministrativo = service.getAssistenteAdministrativo(id);
        if (!assistenteAdministrativo.isPresent()) {
            return new ResponseEntity("Assistente Administrativo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(assistenteAdministrativo.map(AssistenteAdministrativoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AssistenteAdministrativoDTO dto) {
        try {
            AssistenteAdministrativo assistenteAdministrativo = converter(dto);
            assistenteAdministrativo = service.salvar(assistenteAdministrativo);
            return new ResponseEntity(assistenteAdministrativo, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AssistenteAdministrativoDTO dto) {
        if (!service.getAssistenteAdministrativo(id).isPresent()) {
            return new ResponseEntity("Assistente Administrativo não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            AssistenteAdministrativo assistenteAdministrativo = converter(dto);
            assistenteAdministrativo.setId(id);
            service.salvar(assistenteAdministrativo);
            return ResponseEntity.ok(assistenteAdministrativo);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<AssistenteAdministrativo> assistenteAdministrativo = service.getAssistenteAdministrativo(id);
        if (!assistenteAdministrativo.isPresent()) {
            return new ResponseEntity("Assistente não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(assistenteAdministrativo.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public AssistenteAdministrativo converter(AssistenteAdministrativoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        AssistenteAdministrativo assistenteAdministrativo = modelMapper.map(dto, AssistenteAdministrativo.class);
        if (dto.getIdHospital() != null) {
            Optional<Hospital> hospital = hospitalService.getHospital(dto.getIdHospital());
            if (!hospital.isPresent()) {
                assistenteAdministrativo.setHospital(null);
            } else {
                assistenteAdministrativo.setHospital(hospital.get());
            }
        }
        return assistenteAdministrativo;
    }

}

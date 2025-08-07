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
import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Admin;
import br.ufjf.ssapi.model.entity.Hospital;
import br.ufjf.ssapi.service.AdminService;
import br.ufjf.ssapi.service.HospitalService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admins")
@CrossOrigin
public class AdminController {
    private final AdminService service;
    private final HospitalService hospitalService;

    @GetMapping()
    @ApiOperation("Obtém todos os administradores")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Administradores encontrados"),
            @ApiResponse(code = 404, message = "Administradores não encontrados")
    })
    public ResponseEntity get() {
        List<Admin> admins = service.getAdmins();
        return ResponseEntity.ok(admins.stream().map(AdminDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtém um administrador pelo ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Administrador encontrado"),
            @ApiResponse(code = 404, message = "Administrador não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Admin> admin = service.getAdmin(id);
        if (!admin.isPresent()) {
            return new ResponseEntity("Admin não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(admin.map(AdminDTO::create));
    }

    @PostMapping()
    @ApiOperation("Cria um novo administrador")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Novo administrador criado com sucesso!"),
            @ApiResponse(code = 404, message = "Erro ao criar o administrador")
    })
    public ResponseEntity post(@RequestBody AdminDTO dto) {
        try {
            Admin admin = converter(dto);
            admin = service.salvar(admin);
            return new ResponseEntity(admin, HttpStatus.CREATED);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um administrador existente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Administrador atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao atualizar o administrador")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AdminDTO dto) {
        if (!service.getAdmin(id).isPresent()) {
            return new ResponseEntity("Admin não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Admin admin = converter(dto);
            admin.setId(id);
            service.salvar(admin);
            return ResponseEntity.ok(admin);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um administrador")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Administrador excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o administrador")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Admin> admin = service.getAdmin(id);
        if (!admin.isPresent()) {
            return new ResponseEntity("Aluno não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(admin.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (DefaultException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Admin converter(AdminDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Admin admin = modelMapper.map(dto, Admin.class);
        if (dto.getIdHospital() != null) {
            Optional<Hospital> hospital = hospitalService.getHospital(dto.getIdHospital());
            if (!hospital.isPresent()) {
                admin.setHospital(null);
            } else {
                admin.setHospital(hospital.get());
            }
        }
        return admin;
    }

}

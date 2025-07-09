package br.ufjf.ssapi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufjf.ssapi.api.dto.AdminDTO;
import br.ufjf.ssapi.model.entity.Admin;
import br.ufjf.ssapi.service.AdminService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admins")
@CrossOrigin
public class AdminController {
    private final AdminService service;


    @GetMapping()
    public ResponseEntity get() {
        List<Admin> admins = service.getAdmins();
        return ResponseEntity.ok(admins.stream().map(AdminDTO::create).collect(Collectors.toList()));
    }
}

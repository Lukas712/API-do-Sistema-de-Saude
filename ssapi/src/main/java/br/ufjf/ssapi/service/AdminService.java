package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Admin;
import br.ufjf.ssapi.model.repository.AdminRepository;


@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdmin(Long id) {
        return adminRepository.findById(id);
    }

    @Transactional
    public Admin salvar(Admin admin) {
        validar(admin);
        return adminRepository.save(admin);
    }

    @Transactional
    public void excluir(Admin admin) {
        Objects.requireNonNull(admin.getId());
        adminRepository.delete(admin);
    }

    public void validar(Admin admin) throws DefaultException {
        if (!admin.validaNome()) {
            throw new DefaultException("Nome inválido");
        }
        if (!admin.validaEmail()) {
            throw new DefaultException("O email está no formato incorreto.");
        }
        if (!admin.validaCPF()) {
            throw new DefaultException("O cpf está no formato incorreto.");
        }
        if (!admin.validaDataNascimento()) {
            throw new DefaultException("A data está no formato incorreto.");
        }
        if (!admin.validaTelefone()) {
            throw new DefaultException("O telefone está no formato incorreto.");
        }

        if (!admin.validaHospital()) {
            throw new DefaultException("Hospital não encontrado");
        }
        if(!admin.validaGenero()){
            throw new DefaultException("Gênero inválido!.");
        }
    }
}
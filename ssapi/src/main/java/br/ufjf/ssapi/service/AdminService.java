package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

import org.springframework.stereotype.Service;
import br.ufjf.ssapi.model.repository.*;
import br.ufjf.ssapi.model.entity.*;
import br.ufjf.ssapi.exception.PasswordException;
import org.springframework.transaction.annotation.Transactional;


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

    public void validar(Admin admin) throws PasswordException {
        if (admin.getNome() == null || admin.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
    }
}
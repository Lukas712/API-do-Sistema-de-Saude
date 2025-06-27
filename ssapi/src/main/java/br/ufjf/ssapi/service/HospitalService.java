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
public class HospitalService {

    private final HospitalRepository HospitalRepository;

    public HospitalService(HospitalRepository HospitalRepository) {
        this.HospitalRepository = HospitalRepository;
    }

    public List<Hospital> getHospitals() {
        return HospitalRepository.findAll();
    }

    public Optional<Hospital> getHospital(Long id) {
        return HospitalRepository.findById(id);
    }

    @Transactional
    public Hospital salvar(Hospital Hospital) {
        validar(Hospital);
        return HospitalRepository.save(Hospital);
    }

    @Transactional
    public void excluir(Hospital Hospital) {
        Objects.requireNonNull(Hospital.getId());
        HospitalRepository.delete(Hospital);
    }

    public void validar(Hospital Hospital) throws PasswordException {
        if (Hospital.getNome() == null || Hospital.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
        if (Hospital.getLocal() == null || Hospital.getNome().isEmpty()) {
            throw new PasswordException("O local não pode ser vazio.");
        }
        if (Hospital.validaCNPJ(Hospital.getCnpj())) {
            throw new PasswordException("CNPJ está no formato incorreto.");
        }
    }
}
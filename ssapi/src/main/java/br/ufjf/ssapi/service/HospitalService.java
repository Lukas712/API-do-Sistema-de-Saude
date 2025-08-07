package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

import org.springframework.stereotype.Service;
import br.ufjf.ssapi.model.repository.*;
import br.ufjf.ssapi.model.entity.*;
import br.ufjf.ssapi.exception.DefaultException;
import org.springframework.transaction.annotation.Transactional;


@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public List<Hospital> getHospitals() {
        return hospitalRepository.findAll();
    }

    public Optional<Hospital> getHospital(Long id) {
        return hospitalRepository.findById(id);
    }

    @Transactional
    public Hospital salvar(Hospital hospital) {
        validar(hospital);
        return hospitalRepository.save(hospital);
    }

    @Transactional
    public void excluir(Hospital hospital) {
        Objects.requireNonNull(hospital.getId());
        hospitalRepository.delete(hospital);
    }

    public void validar(Hospital hospital) throws DefaultException {
        if(!hospital.validaNome()) {
            throw new DefaultException("Nome inválido");
        }
        if(!hospital.validaLocal()) {
            throw new DefaultException("Local inválido");
        }
        if(!hospital.validaCNPJ()) {
            throw new DefaultException("CNPJ inválido");
        }
    }
}
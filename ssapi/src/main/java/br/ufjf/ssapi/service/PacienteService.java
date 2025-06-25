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
public class PacienteService {

    private final PacienteRepository PacienteRepository;

    public PacienteService(PacienteRepository PacienteRepository) {
        this.PacienteRepository = PacienteRepository;
    }

    public List<Paciente> getPacientes() {
        return PacienteRepository.findAll();
    }

    public Optional<Paciente> getPaciente(Long id) {
        return PacienteRepository.findById(id);
    }

    @Transactional
    public Paciente salvar(Paciente Paciente) {
        validar(Paciente);
        return PacienteRepository.save(Paciente);
    }

    @Transactional
    public void excluir(Paciente Paciente) {
        Objects.requireNonNull(Paciente.getId());
        PacienteRepository.delete(Paciente);
    }

    public void validar(Paciente Paciente) throws PasswordException {
        if (Paciente.getNome() == null || Paciente.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
    }
}
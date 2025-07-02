package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.PasswordException;
import br.ufjf.ssapi.model.entity.Paciente;
import br.ufjf.ssapi.model.repository.PacienteRepository;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> getPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> getPaciente(Long id) {
        return pacienteRepository.findById(id);
    }

    @Transactional
    public Paciente salvar(Paciente paciente) {
        validar(paciente);
        return pacienteRepository.save(paciente);
    }

    @Transactional
    public void excluir(Paciente paciente) {
        Objects.requireNonNull(paciente.getId());
        pacienteRepository.delete(paciente);
    }

    public void validar(Paciente paciente) throws PasswordException {
        if (paciente.getNome() == null || paciente.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
        if (!paciente.validaEmail(paciente.getEmail())) {
            throw new PasswordException("O email está no formato incorreto.");
        }
        if (!paciente.validaCPF(paciente.getCpf())) {
            throw new PasswordException("O cpf está no formato incorreto.");
        }
        if (!paciente.validaDataNascimento(paciente.getDataNascimento())) {
            throw new PasswordException("A data está no formato incorreto.");
        }
        if (!paciente.validaTelefone(paciente.getTelefone())) {
            throw new PasswordException("O telefone está no formato incorreto.");
        }
    }

}
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
        if (Paciente.validaEmail(Paciente.getEmail())) {
            throw new PasswordException("O email está no formato incorreto.");
        }
        if (Paciente.validaCPF(Paciente.getCpf())) {
            throw new PasswordException("O cpf está no formato incorreto.");
        }
        if (Paciente.validaDataNascimento(Paciente.getDataNascimento())) {
            throw new PasswordException("A data está no formato incorreto.");
        }
        if (Paciente.validaTelefone(Paciente.getTelefone())) {
            throw new PasswordException("O telefone está no formato incorreto.");
        }
    }

}
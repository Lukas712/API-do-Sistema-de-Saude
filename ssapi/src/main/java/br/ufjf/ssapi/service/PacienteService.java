package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.DefaultException;
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

    public void validar(Paciente paciente) throws DefaultException {
        if (!paciente.validaNome()) {
            throw new DefaultException("O nome não pode ser vazio.");
        }
        if (!paciente.validaEmail()) {
            throw new DefaultException("O email está no formato incorreto.");
        }
        if (!paciente.validaCPF()) {
            throw new DefaultException("O cpf está no formato incorreto.");
        }
        if (!paciente.validaDataNascimento()) {
            throw new DefaultException("A data está no formato incorreto.");
        }
        if (!paciente.validaTelefone()) {
            throw new DefaultException("O telefone está no formato incorreto.");
        }
        if(!paciente.validaGenero()){
            throw new DefaultException("Gênero inválido!.");
        }
    }

}
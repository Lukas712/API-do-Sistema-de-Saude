package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.model.entity.Medico;
import br.ufjf.ssapi.model.repository.MedicoRepository;

@Service
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public List<Medico> getMedicos() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> getMedico(Long id) {
        return medicoRepository.findById(id);
    }

    @Transactional
    public Medico salvar(Medico medico) {
        validar(medico);
        return medicoRepository.save(medico);
    }

    @Transactional
    public void excluir(Medico medico) {
        Objects.requireNonNull(medico.getId());
        medicoRepository.delete(medico);
    }

    public void validar(Medico medico) throws DefaultException {
        if(!medico.validaNome()) {
            throw new DefaultException("Nome vazio ou inválido.");
        }
        if(!medico.validaCrm()) {
            throw new DefaultException("CRM inválido");
        }
        if(!medico.validaEspecialidade()) {
            throw new DefaultException("Especialidade inválida");
        }
        if(!medico.validaHospital()) {
            throw new DefaultException("Hospital inválido");
        }
        if (!medico.validaEmail()) {
            throw new DefaultException("O email está no formato incorreto.");
        }
        if (!medico.validaCPF()) {
            throw new DefaultException("O cpf está no formato incorreto.");
        }
        if (!medico.validaDataNascimento()) {
            throw new DefaultException("A data está no formato incorreto.");
        }
        if (!medico.validaTelefone()) {
            throw new DefaultException("O telefone está no formato incorreto.");
        }
        if(!medico.validaGenero()){
            throw new DefaultException("Gênero inválido!.");
        }
    }
}
package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.PasswordException;
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

    public void validar(Medico medico) throws PasswordException {
        if (medico.getNome() == null || medico.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
        if (medico.getCrm() == null || medico.getNome().isEmpty()) {
            throw new PasswordException("O crm não pode ser vazio.");
        }
        if (!medico.validaEmail(medico.getEmail())) {
            throw new PasswordException("O email está no formato incorreto.");
        }
        if (!medico.validaCPF(medico.getCpf())) {
            throw new PasswordException("O cpf está no formato incorreto.");
        }
        if (!medico.validaDataNascimento(medico.getDataNascimento())) {
            throw new PasswordException("A data está no formato incorreto.");
        }
        if (!medico.validaTelefone(medico.getTelefone())) {
            throw new PasswordException("O telefone está no formato incorreto.");
        }
    }
}
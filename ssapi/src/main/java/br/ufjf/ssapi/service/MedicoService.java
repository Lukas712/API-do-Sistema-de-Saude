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

    private final MedicoRepository MedicoRepository;

    public MedicoService(MedicoRepository MedicoRepository) {
        this.MedicoRepository = MedicoRepository;
    }

    public List<Medico> getMedicos() {
        return MedicoRepository.findAll();
    }

    public Optional<Medico> getMedico(Long id) {
        return MedicoRepository.findById(id);
    }

    @Transactional
    public Medico salvar(Medico Medico) {
        validar(Medico);
        return MedicoRepository.save(Medico);
    }

    @Transactional
    public void excluir(Medico Medico) {
        Objects.requireNonNull(Medico.getId());
        MedicoRepository.delete(Medico);
    }

    public void validar(Medico Medico) throws PasswordException {
        if (Medico.getNome() == null || Medico.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
        if (Medico.getCrm() == null || Medico.getNome().isEmpty()) {
            throw new PasswordException("O crm não pode ser vazio.");
        }
        if (!Medico.validaEmail(Medico.getEmail())) {
            throw new PasswordException("O email está no formato incorreto.");
        }
        if (!Medico.validaCPF(Medico.getCpf())) {
            throw new PasswordException("O cpf está no formato incorreto.");
        }
        if (!Medico.validaDataNascimento(Medico.getDataNascimento())) {
            throw new PasswordException("A data está no formato incorreto.");
        }
        if (!Medico.validaTelefone(Medico.getTelefone())) {
            throw new PasswordException("O telefone está no formato incorreto.");
        }
    }
}
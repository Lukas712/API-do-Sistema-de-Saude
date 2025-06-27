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
public class AssistenteAdministrativoService {

    private final AssistenteAdministrativoRepository AssistenteAdministrativoRepository;

    public AssistenteAdministrativoService(AssistenteAdministrativoRepository AssistenteAdministrativoRepository) {
        this.AssistenteAdministrativoRepository = AssistenteAdministrativoRepository;
    }

    public List<AssistenteAdministrativo> getAssistenteAdministrativos() {
        return AssistenteAdministrativoRepository.findAll();
    }

    public Optional<AssistenteAdministrativo> getAssistenteAdministrativo(Long id) {
        return AssistenteAdministrativoRepository.findById(id);
    }

    @Transactional
    public AssistenteAdministrativo salvar(AssistenteAdministrativo AssistenteAdministrativo) {
        validar(AssistenteAdministrativo);
        return AssistenteAdministrativoRepository.save(AssistenteAdministrativo);
    }

    @Transactional
    public void excluir(AssistenteAdministrativo AssistenteAdministrativo) {
        Objects.requireNonNull(AssistenteAdministrativo.getId());
        AssistenteAdministrativoRepository.delete(AssistenteAdministrativo);
    }

    public void validar(AssistenteAdministrativo AssistenteAdministrativo) throws PasswordException {
        if (AssistenteAdministrativo.getNome() == null || AssistenteAdministrativo.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
        if (AssistenteAdministrativo.getNome() == null || AssistenteAdministrativo.getNome().isEmpty()) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
        if (AssistenteAdministrativo.validaEmail(AssistenteAdministrativo.getEmail())) {
            throw new PasswordException("O email está no formato incorreto.");
        }
        if (AssistenteAdministrativo.validaCPF(AssistenteAdministrativo.getCpf())) {
            throw new PasswordException("O cpf está no formato incorreto.");
        }
        if (AssistenteAdministrativo.validaDataNascimento(AssistenteAdministrativo.getDataNascimento())) {
            throw new PasswordException("A data está no formato incorreto.");
        }
        if (AssistenteAdministrativo.validaTelefone(AssistenteAdministrativo.getTelefone())) {
            throw new PasswordException("O telefone está no formato incorreto.");
        }
    }
}
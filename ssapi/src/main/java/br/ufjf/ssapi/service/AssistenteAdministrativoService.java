package br.ufjf.ssapi.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufjf.ssapi.exception.PasswordException;
import br.ufjf.ssapi.model.entity.AssistenteAdministrativo;
import br.ufjf.ssapi.model.repository.AssistenteAdministrativoRepository;


@Service
public class AssistenteAdministrativoService {

    private final AssistenteAdministrativoRepository assistenteAdministrativoRepository;

    public AssistenteAdministrativoService(AssistenteAdministrativoRepository assistenteAdministrativoRepository) {
        this.assistenteAdministrativoRepository = assistenteAdministrativoRepository;
    }

    public List<AssistenteAdministrativo> getAssistenteAdministrativos() {
        return assistenteAdministrativoRepository.findAll();
    }

    public Optional<AssistenteAdministrativo> getAssistenteAdministrativo(Long id) {
        return assistenteAdministrativoRepository.findById(id);
    }

    @Transactional
    public AssistenteAdministrativo salvar(AssistenteAdministrativo assistenteAdministrativo) {
        validar(assistenteAdministrativo);
        return assistenteAdministrativoRepository.save(assistenteAdministrativo);
    }

    @Transactional
    public void excluir(AssistenteAdministrativo assistenteAdministrativo) {
        Objects.requireNonNull(assistenteAdministrativo.getId());
        assistenteAdministrativoRepository.delete(assistenteAdministrativo);
    }

    public void validar(AssistenteAdministrativo assistenteAdministrativo) throws PasswordException {
        if (!assistenteAdministrativo.validaNome(assistenteAdministrativo.getNome())) {
            throw new PasswordException("O nome não pode ser vazio.");
        }
        if (!assistenteAdministrativo.validaEmail(assistenteAdministrativo.getEmail())) {
            throw new PasswordException("O email está no formato incorreto.");
        }
        if (!assistenteAdministrativo.validaCPF(assistenteAdministrativo.getCpf())) {
            throw new PasswordException("O cpf está no formato incorreto.");
        }
        if (!assistenteAdministrativo.validaDataNascimento(assistenteAdministrativo.getDataNascimento())) {
            throw new PasswordException("A data está no formato incorreto.");
        }
        if (!assistenteAdministrativo.validaTelefone(assistenteAdministrativo.getTelefone())) {
            throw new PasswordException("O telefone está no formato incorreto.");
        }
    }
}
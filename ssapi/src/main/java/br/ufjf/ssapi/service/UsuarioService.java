package br.ufjf.ssapi.service;

import br.ufjf.ssapi.exception.DefaultException;
import br.ufjf.ssapi.exception.PasswordException;
import br.ufjf.ssapi.model.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import br.ufjf.ssapi.model.entity.UsuarioLogin;

import br.ufjf.ssapi.exception.DefaultException;


@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository repository;

    public List<UsuarioLogin> getUsuarios() {
        return repository.findAll();
    }

    public Optional<UsuarioLogin> getUsuarioById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public UsuarioLogin salvar(UsuarioLogin usuario){
        validar(usuario);
        return repository.save(usuario);
    }

    public UserDetails autenticar(UsuarioLogin usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());

        if (senhasBatem){
            return user;
        }
        throw new DefaultException("Senha inválida");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UsuarioLogin usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String[] roles = usuario.isAdmin()
                ? new String[]{"ADMIN", "USER"}
                : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }

    @Transactional
    public void excluir(UsuarioLogin usuario) {
        Objects.requireNonNull(usuario.getId());
        repository.delete(usuario);
    }

    public void validar(UsuarioLogin usuario) {
        if (usuario.getLogin() == null || usuario.getLogin().trim().equals("")) {
            throw new DefaultException("Login inválido");
        }
        if (usuario.getCpf() == null || usuario.getCpf().trim().equals("")) {
            throw new DefaultException("CPF inválido");
        }
    }
}

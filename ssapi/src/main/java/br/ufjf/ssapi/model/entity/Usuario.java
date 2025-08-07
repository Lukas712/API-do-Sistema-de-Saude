package br.ufjf.ssapi.model.entity;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private String telefone;
    private String genero;

    public boolean validaEmail() {
        if (email == null || email.isEmpty())
            return false;

        String regex = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, email);

    }

    public boolean validaCPF() {
        if (cpf == null || email.isEmpty())
            return false;

        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}"))
            return false;

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int resto = 11 - (soma % 11);
        int digito1 = (resto == 10 || resto == 11) ? 0 : resto;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        resto = 11 - (soma % 11);
        int digito2 = (resto == 10 || resto == 11) ? 0 : resto;

        return (digito1 == Character.getNumericValue(cpf.charAt(9))
                && (digito2 == Character.getNumericValue(cpf.charAt(10))));
    }

    public boolean validaTelefone() {
        if (telefone == null || telefone.isEmpty())
            return false;

        String numero = telefone.replaceAll("[^0-9]", "");

        if (numero.length() < 8 || numero.length() > 11)
            return false;

        if (numero.length() == 11) {
            return numero.matches("\\d{2}9\\d{8}");
        }

        return true;
    }

    public boolean validaNome() {
        if (nome == null || nome.isEmpty())
            return false;

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$";
        return Pattern.matches(regex, nome);
    }

    public boolean validaGenero() {
        if (genero == null || genero.isEmpty())
            return false;

        String[] generosValidos = { "Masculino", "Feminino", "Outro" };
        for (String gen : generosValidos) {
            if (gen.equalsIgnoreCase(genero)) {
                return true;
            }
        }
        return false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean validaDataNascimento() {
        if (dataNascimento == null)
            return false;

        LocalDate nascimento = dataNascimento.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate hoje = LocalDate.now();

        if (nascimento.isAfter(hoje))
            return false;

        Period periodo = Period.between(nascimento, hoje);
        return periodo.getYears() >= 1;
    }
}

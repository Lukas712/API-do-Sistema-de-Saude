package br.ufjf.ssapi.model.entity;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass

public abstract class Usuario {
    private Long id;

    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private String telefone;
    private String genero;

    public boolean validaEmail(String email) {
        if (email == null || email.isEmpty())
            return false;

        String regex = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regex, email);

    }

    public boolean validaCPF(String cpf) {
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

    public boolean validaTelefone(String telefone) {
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

    public boolean validaDataNascimento(Date dataNac) {
        if (dataNac == null)
            return false;

        LocalDate nascimento = dataNac.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate hoje = LocalDate.now();

        if (nascimento.isAfter(hoje))
            return false;

        Period periodo = Period.between(nascimento, hoje);
        return periodo.getYears() >= 1;
    }
}

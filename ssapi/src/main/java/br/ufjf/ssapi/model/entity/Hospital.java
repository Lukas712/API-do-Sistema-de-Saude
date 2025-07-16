package br.ufjf.ssapi.model.entity;

import java.util.Date;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;




@Entity
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;
    private String local;

    public boolean validaNome(String nome){
        if (nome == null || nome.isEmpty())
            return false;

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$";
        return Pattern.matches(regex, nome);
    }

    public boolean validaLocal(String local) {
        if (local == null || local.isEmpty())
            return false;

        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ0-9\\s,.-]+$";
        return Pattern.matches(regex, local);
    }

    public boolean validaCNPJ(String cnpj) {
        if (cnpj == null || cnpj.isEmpty())
            return false;

        cnpj = cnpj.replaceAll("[^0-9]", "");

        if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}"))
            return false;

        int[] pesos = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        int digito1 = (resto < 2) ? 0 : 11 - resto;

        soma = 0;
        for (int i = 0; i < 13; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i)) * pesos[i];
        }
        resto = soma % 11;
        int digito2 = (resto < 2) ? 0 : 11 - resto;

        return (digito1 == Character.getNumericValue(cnpj.charAt(12))
                && digito2 == Character.getNumericValue(cnpj.charAt(13)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}

package br.ufjf.ssapi.model.entity;

import java.util.Date;

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
}

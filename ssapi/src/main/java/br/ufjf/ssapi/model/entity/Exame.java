package br.ufjf.ssapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exame{
    private String descricao;
    private Laudo laudo;
    private Paciente paciente;
    private Enfermeiro enfermeiro;
    private Date validade;
}

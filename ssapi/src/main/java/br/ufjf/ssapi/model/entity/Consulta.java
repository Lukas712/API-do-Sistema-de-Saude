package br.ufjf.ssapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consulta {
    private String descricao;
    private Receita receita;
    private Sala sala;
    private Medico medico;
    private Paciente paciente;
}

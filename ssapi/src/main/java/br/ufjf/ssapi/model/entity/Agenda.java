package br.ufjf.ssapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agenda<Agendador> {
    private int ano;
    private int mes;
    private int dia;
    private int hora;
    private int minuto;

    private Agendador agendador;
}

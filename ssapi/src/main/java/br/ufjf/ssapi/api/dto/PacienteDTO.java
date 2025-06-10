package br.ufjf.ssapi.api.dto;

import java.util.Date;

import org.modelmapper.ModelMapper;

import br.ufjf.ssapi.model.entity.Paciente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PacienteDTO {

    private Long id;
    private String nome;
    private String cpf;
    private Date dataNascimento;
    private String email;
    private String telefone;
    private String genero;

    public static PacienteDTO create(Paciente paciente){
        ModelMapper modelMapper = new ModelMapper();
        PacienteDTO dto = modelMapper.map(paciente, PacienteDTO.class);
        return dto;
    }
}

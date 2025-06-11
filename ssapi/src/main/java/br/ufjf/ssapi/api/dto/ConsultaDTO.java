package br.ufjf.ssapi.api.dto;

import org.modelmapper.ModelMapper;

import br.ufjf.ssapi.model.entity.Consulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ConsultaDTO {
    private Long id;
    private String descricao;
    
    private Long idReceita;
    private String nomeReceita;
    private Long idPaciente;
    private String nomePaciente;

    public static ConsultaDTO create (Consulta consulta){
        ModelMapper modelMapper = new ModelMapper();
        ConsultaDTO dto = modelMapper.map(consulta, ConsultaDTO.class);
        dto.nomeReceita = consulta.getReceita().getDescricao();
        dto.nomePaciente = consulta.getPaciente().getNome();
        return dto;
    }
}

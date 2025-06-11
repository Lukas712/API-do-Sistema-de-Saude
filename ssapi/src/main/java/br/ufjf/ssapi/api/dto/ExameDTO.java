package br.ufjf.ssapi.api.dto;

import java.util.Date;

import org.modelmapper.ModelMapper;

import br.ufjf.ssapi.model.entity.Exame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExameDTO {
    
    private Long id;
    private String descricao;
    private String laudo;
    private Date validade;
    
    private Long idPaciente;
    private String nomePaciente;
    private Long idEnfermeiro;
    private String nomeEnfermeiro;

    public static ExameDTO create(Exame exame){
        ModelMapper modelMapper = new ModelMapper();
        ExameDTO dto = modelMapper.map(exame, ExameDTO.class);
        dto.nomePaciente = exame.getPaciente().getNome();
        dto.nomeEnfermeiro = exame.getEnfermeiro().getNome();
        return dto;
    }
}

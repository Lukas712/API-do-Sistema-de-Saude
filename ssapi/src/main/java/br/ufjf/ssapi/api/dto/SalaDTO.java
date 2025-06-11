package br.ufjf.ssapi.api.dto;

import org.modelmapper.ModelMapper;

import br.ufjf.ssapi.model.entity.Sala;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SalaDTO {
    private Long id;
    private String equipamento;
    
    private Long idExame;
    private String nomeExame;
    private Long idHospital;
    private String nomeHospital;

    public static SalaDTO create(Sala sala){
        ModelMapper modelMapper = new ModelMapper();
        SalaDTO dto = modelMapper.map(sala, SalaDTO.class);
        dto.nomeExame = sala.getExame().getDescricao();
        dto.nomeHospital = sala.getHospital().getNome();
        return dto;
    }
}

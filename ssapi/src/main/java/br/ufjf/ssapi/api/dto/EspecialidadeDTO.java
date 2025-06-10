package br.ufjf.ssapi.api.dto;

import org.modelmapper.ModelMapper;
import br.ufjf.ssapi.model.entity.Especialidade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EspecialidadeDTO {

    private Long id;
    private String nome;
    private String area;

    public static EspecialidadeDTO create(Especialidade especialidade){
        ModelMapper modelMapper = new ModelMapper();
        EspecialidadeDTO dto = modelMapper.map(especialidade, EspecialidadeDTO.class);
        return dto;
    }
}

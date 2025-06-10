package br.ufjf.ssapi.api.dto;

import org.modelmapper.ModelMapper;

import br.ufjf.ssapi.model.entity.Receita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReceitaDTO {
    private Long id;
    private String descricao;

    public static ReceitaDTO create(Receita receita){
        ModelMapper modelMapper = new ModelMapper();
        ReceitaDTO dto = modelMapper.map(receita, ReceitaDTO.class);
        return dto;
    }
}

package br.ufjf.ssapi.api.dto;

import org.modelmapper.ModelMapper;

import br.ufjf.ssapi.model.entity.Medicamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MedicamentoDTO {
    private Long id;
    private String nome;
    
    private Long idReceita;
    private String nomeReceita;

    public static MedicamentoDTO create(Medicamento medicamento){
        ModelMapper modelMapper = new ModelMapper();
        MedicamentoDTO dto = modelMapper.map(medicamento, MedicamentoDTO.class);
        dto.nomeReceita = medicamento.getReceita().getDescricao();
        return dto;
    }
}

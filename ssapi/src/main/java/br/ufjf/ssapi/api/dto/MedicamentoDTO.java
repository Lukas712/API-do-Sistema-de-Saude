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

    public static MedicamentoDTO create(Medicamento medicamento){
        ModelMapper modelMapper = new ModelMapper();
        MedicamentoDTO dto = modelMapper.map(medicamento, MedicamentoDTO.class);
        return dto;
    }
}

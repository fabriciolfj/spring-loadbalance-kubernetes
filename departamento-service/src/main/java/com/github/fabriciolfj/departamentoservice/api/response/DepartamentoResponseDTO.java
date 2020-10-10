package com.github.fabriciolfj.departamentoservice.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DepartamentoResponseDTO {

    private String id;
    private String nome;
    private List<FuncionarioResponseDTO> funcionarios;
}

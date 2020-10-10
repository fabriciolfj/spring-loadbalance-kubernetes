package com.github.fabriciolfj.departamentoservice.api.mapper.impl;

import com.github.fabriciolfj.departamentoservice.api.mapper.DepartamentoMapper;
import com.github.fabriciolfj.departamentoservice.api.response.DepartamentoResponseDTO;
import com.github.fabriciolfj.departamentoservice.api.response.FuncionarioResponseDTO;
import com.github.fabriciolfj.departamentoservice.domain.documents.Departamento;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@NoArgsConstructor
public abstract class DepartamentoMapperDecorated implements DepartamentoMapper {

    @Autowired
    private DepartamentoMapper mapper;

    @Override
    public DepartamentoResponseDTO toResponse(final Departamento departamento, final List<FuncionarioResponseDTO> funcionarios) {
        var response = mapper.toResponse(departamento, funcionarios);
        response.setFuncionarios(funcionarios);
        return response;
    }
}

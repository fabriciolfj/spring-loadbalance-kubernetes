package com.github.fabriciolfj.funcionarioservice.domain.service;

import com.github.fabriciolfj.funcionarioservice.api.dto.request.FuncionarioRequestDTO;
import com.github.fabriciolfj.funcionarioservice.api.dto.response.FuncionarioResponseDTO;
import com.github.fabriciolfj.funcionarioservice.api.exceptions.FuncionarioCreateException;
import com.github.fabriciolfj.funcionarioservice.api.exceptions.FuncionarioNotFoundException;
import com.github.fabriciolfj.funcionarioservice.api.mapper.FuncionarioMapper;
import com.github.fabriciolfj.funcionarioservice.domain.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
@Slf4j
public class FuncionarioService {

    private final FuncionarioRepository repository;
    private final FuncionarioMapper mapper;

    public List<FuncionarioResponseDTO> findAll() {
        log.info("M=findAll R=List<FuncionarioResponseDTO>");
        return repository.findAll()
                .stream().map(mapper::toResponseDTO)
                .collect(toList());
    }

    public FuncionarioResponseDTO findById(final String id) {
        log.info("M=findById R=FuncionarioResponseDTO");
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new FuncionarioNotFoundException("Funcionario: " + id + " não localizado"));
    }

    public List<FuncionarioResponseDTO> findByFuncionarioPorDepartamento(final String departamentoId) {
        log.info("M=findByFuncionarioPorDepartamento R=List<Funcionario>");
        return repository.findByDepartamentoId(departamentoId)
                .stream().map(mapper::toResponseDTO)
                .collect(toList());
    }

    public FuncionarioResponseDTO create(final FuncionarioRequestDTO dto) {
        log.info("M=create R=FuncionarioResponseDTO");
        return Optional.ofNullable(dto)
                .map(mapper::toDocument)
                .map(repository::save)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new FuncionarioCreateException("Falha ao criar o funcionario: " + dto));
    }

}

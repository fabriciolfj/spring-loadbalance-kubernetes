package com.github.fabriciolfj.departamentoservice.domain.service;

import com.github.fabriciolfj.departamentoservice.api.exceptions.DepartamentoCreateException;
import com.github.fabriciolfj.departamentoservice.api.exceptions.DepartamentoNotFoundException;
import com.github.fabriciolfj.departamentoservice.api.mapper.DepartamentoMapper;
import com.github.fabriciolfj.departamentoservice.api.request.DepartamentoRequestDTO;
import com.github.fabriciolfj.departamentoservice.api.response.DepartamentoResponseDTO;
import com.github.fabriciolfj.departamentoservice.api.response.FuncionarioResponseDTO;
import com.github.fabriciolfj.departamentoservice.domain.documents.Departamento;
import com.github.fabriciolfj.departamentoservice.domain.integracao.http.FuncionarioClient;
import com.github.fabriciolfj.departamentoservice.domain.repository.DepartamentoRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.of;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartamentoService {

    private final DepartamentoRepository repository;
    private final DepartamentoMapper mapper;
    private final FuncionarioClient funcionarioClient;
    private final RestTemplate restTemplate;
    private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

    public DepartamentoResponseDTO create(final DepartamentoRequestDTO dto) {
        log.info("M=create dto={}", dto);
        return of(dto)
                .map(mapper::toDocument)
                .map(repository::save)
                .map(entity -> mapper.toResponse(entity, Collections.EMPTY_LIST))
                .orElseThrow(() -> new DepartamentoCreateException("Falha ao criar o departamento: " + dto));
    }

    public List<DepartamentoResponseDTO> findAll() {
        log.info("M=findAll");
        return repository.findAll().stream()
                .map(entity -> mapper.toResponse(entity, Collections.emptyList()))
                .collect(Collectors.toList());
    }

    public DepartamentoResponseDTO findById(@NonNull final String id) {
        log.info("M=findById id:{}", id);
        return repository.findById(id)
                .map(departamento -> {
                    var funcionarios = funcionarioClient.findByDepartamento(id);
                    log.info("Chamada=buscar funcionarios departamento {} retorno={}",id, funcionarios);
                    return mapper.toResponse(departamento, funcionarios);
                }).orElseThrow(() -> new DepartamentoNotFoundException("Falha ao buscar os funcionarios do departamento ou departamento inexistente."));
    }

    public DepartamentoResponseDTO findByIdRest(@NonNull final String id) {
        log.info("M=findById id:{}", id);
        return repository.findById(id)
                .map(departamento -> {
                    var funcionarios = restTemplate.getForObject("http://funcionario/v1/departamento/{departmentId}", FuncionarioResponseDTO[].class, id);
                    log.info("Chamada=(resttemplate)buscar funcionarios departamento {} retorno={}",id, funcionarios);
                    return mapper.toResponse(departamento, List.of(funcionarios));
                }).orElseThrow(() -> new DepartamentoNotFoundException("Falha ao buscar os funcionarios do departamento ou departamento inexistente."));
    }

    public DepartamentoResponseDTO findByIdDelay(@NonNull final String id) {
        log.info("M=findByIdDelay R=Departamento");
        return repository.findById(id)
                .map(departamento -> {
                    Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("delay-circuit");
                    var funcionarios = circuitBreaker.run(() ->  funcionarioClient.findByDepartamentoDelay(id));
                    return mapper.toResponse(departamento, funcionarios);
                }).orElseThrow(() -> new DepartamentoNotFoundException("Falha ao buscar os funcionarios do departamento ou departamento inexistente."));
    }

}

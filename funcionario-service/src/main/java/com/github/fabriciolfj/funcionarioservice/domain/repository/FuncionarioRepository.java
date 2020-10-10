package com.github.fabriciolfj.funcionarioservice.domain.repository;

import com.github.fabriciolfj.funcionarioservice.domain.documents.Funcionario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FuncionarioRepository extends MongoRepository<Funcionario, String> {

    List<Funcionario> findByDepartamentoId(final String departamentoId);
}

package com.github.fabriciolfj.departamentoservice.domain.repository;

import com.github.fabriciolfj.departamentoservice.domain.documents.Departamento;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartamentoRepository extends MongoRepository<Departamento, String> {
}

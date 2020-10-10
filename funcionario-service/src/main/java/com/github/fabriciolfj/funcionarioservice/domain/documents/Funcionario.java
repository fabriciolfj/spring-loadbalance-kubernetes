package com.github.fabriciolfj.funcionarioservice.domain.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "funcionario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    private String id;
    private String nome;
    private String departamentoId;
}

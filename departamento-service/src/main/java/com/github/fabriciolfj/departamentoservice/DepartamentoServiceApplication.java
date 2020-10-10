package com.github.fabriciolfj.departamentoservice;

import com.github.fabriciolfj.departamentoservice.domain.integracao.http.FuncionarioClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackageClasses = FuncionarioClient.class)
public class DepartamentoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepartamentoServiceApplication.class, args);
	}

}

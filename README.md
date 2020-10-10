### Spring Cloud Kubernetes Load Balancer

O suporte do Spring Cloud Kubernetes Load Balancer foi adicionado no último lançamento do Spring Cloud Hoxton.SR8. Foi provavelmente o último projecto em Spring Cloud que utilizou o Ribbon como equilibrador de carga do lado do cliente. A implementação actual baseia-se no projecto Spring Cloud LoadBalancer. Fornece dois modos de comunicação. O primeiro deles detecta os endereços IP de todos os pods em funcionamento dentro de um determinado serviço. O segundo deles utiliza o nome Kubernetes Service para pesquisar todas as instâncias alvo.
Neste artigo, vou mostrar-lhe como utilizar o módulo Spring Cloud Kubernetes LoadBalancer na sua aplicação. Primeiro, irei demonstrar as diferenças entre os modos POD e SERVICE. Depois, permitiremos o balanceamento de carga através de múltiplos espaços de nomes. Finalmente, implementaremos um mecanismo de tolerância a falhas com o projecto Spring Cloud Circuit Breaker.

##### Utilizaremos as depêndencias abaixo:
```
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-kubernetes-loadbalancer</artifactId>
</dependency>
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

##### Implementando o client funcionário
O Spring Cloud OpenFeign é um cliente REST declarativo. Por conseguinte, é necessário criar uma interface com métodos e anotações Spring MVC. É importante definir o nome correto dentro da anotação @FeignClient. Este nome tem de ser o mesmo que o nome do serviço Kubernetes alvo. No exemplo de código que se segue, vê-se uma implementação do cliente do serviço funcionário dentro do serviço departamento.
```
@FeignClient(name = "funcionario")
public interface FuncionarioClient {
    
    @GetMapping("/departamento/{id}")
    List<FuncionarioResponseDTO> findByDepartamento(@PathVariable("id") final String id);
}
```
OpenFeign é ativado após anotar a classe principal com @EnableFeignClients(basePackageClasses = FuncionarioClient.class), em seguido pode-se injeta-lo para uso.

RestTemplate é oposição ao OpenFeign, este é de baixo nível e precisamos anota-lo com @LoadBalanced.

```
@Bean
@LoadBalanced
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

Na classe DepartamentoService, existem 2 métodos de busca por id, um utilizando o OpenFeign e outro RestTemplate, justamente para demonstrar os 2 modos de comunicação salientados no inicio.

##### Deploy da aplicação em kubernetes
Temos dois micro-serviços, cada um deles funcionará em duas instâncias, ambos se comunicarão com mongodb. Existe também a aplicação gateway, construída em cima do Spring Cloud Gateway, que fornece um único ponto final API a todos os serviços. Naturalmente todas as nossas aplicações usufruirão do Spring Cloud Load Balancer para a gestão de tráfego.
Na raiz do projeto existe uma pasta k8s, onde estão os manifestos para deploy no kubernetes. Para essa poc, criei o ambiente com base no multipass e microk8s.

##### Comunicação modo pod
Por padrão, o balanceador de carga Spring Cloud Kubernetes usa o modo POD. Neste modo obtém a lista de pontos finais do kubernetes, para detectar o endereço IP de todos os pods da aplicação. Nesse caso, a única coisa a fazer é desativar a propriedade salientada abaixo.

```
spring:
  cloud:
    loadbalancer:
      ribbon:
        enabled: false
```

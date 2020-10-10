### Spring Cloud Kubernetes Load Balancer

O suporte do Spring Cloud Kubernetes Load Balancer foi adicionado no último lançamento do Spring Cloud Hoxton.SR8. Foi provavelmente o último projecto em Spring Cloud que utilizou o Ribbon como equilibrador de carga do lado do cliente. A implementação actual baseia-se no projecto Spring Cloud LoadBalancer. Fornece dois modos de comunicação. O primeiro deles detecta os endereços IP de todos os pods em funcionamento dentro de um determinado serviço. O segundo deles utiliza o nome Kubernetes Service para pesquisar todas as instâncias alvo.
Neste artigo, demonstro o uso do Spring Cloud Kubernetes LoadBalancer na sua aplicação. Primeiro, demonstrarei as diferenças entre os modos POD e SERVICE. Depois, permitiremos o balanceamento de carga através de múltiplos namespaces. Finalmente, implementaremos um mecanismo de tolerância a falhas com o projecto Spring Cloud Circuit Breaker.

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
O Spring Cloud OpenFeign é um cliente REST declarativo, ou seja, é necessário criar uma interface com métodos e anotações Spring MVC. É importante definir o nome correto dentro da anotação @FeignClient. Este nome tem de ser o mesmo que o nome do serviço Kubernetes alvo. No exemplo abaixo, vê-se uma implementação do cliente do serviço funcionário dentro da aplicação departamento-service.
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

Na classe DepartamentoService existem 2 métodos de busca por id, um utilizando o OpenFeign e outro RestTemplate, justamente para demonstrar os 2 modos de comunicação salientados no inicio.

##### Deploy da aplicação em kubernetes
Possuímos dois micro-serviços, cada um deles funcionará em duas instâncias, ambos se comunicarão com mongodb. Há também a aplicação gateway, construída em cima do Spring Cloud Gateway, que fornece um único ponto final a todos os serviços. Naturalmente todas as nossas aplicações usufruirão do Spring Cloud Load Balancer para a gestão de tráfego.
Na raiz do projeto existe uma pasta k8s, onde estão os manifestos para deploy no kubernetes relacionados ao mongodb. Para essa poc, criei o ambiente com base no multipass e microk8s.

##### Comunicação modo pod
Por padrão o load balance Spring Cloud Kubernetes, usa o modo POD. Neste modo obtém a lista de pontos finais do kubernetes, para detectar o endereço IP de todos os pods da aplicação. Nesse caso, a única coisa a fazer é desativar a propriedade salientada abaixo.

```
spring:
  cloud:
    loadbalancer:
      ribbon:
        enabled: false
```

##### Comunicação através de multiplos namespaces
Por padrão o spring cloud kubernetes, permite fazer o load balance dentro do mesmo namespace. Para permitir realizar a descoberta em vários namespaces, habilite a configuração abaixo:

```
spring:
  cloud:
    kubernetes:
      discovery:
        all-namespaces: true
```

##### Load balance no spring cloud gateway
O Spring Cloud Gateway utiliza o mesmo mecanismo load balance, das demais aplicações spring cloud. Para o permitir em kubernetes, precisamos de incluir a dependencia spring-cloud-starter-kubernetes-loadbalancer. Devemos também ativar o Spring Cloud DiscoveryClient e desactivar o Spring Cloud Ribbon.
Gateway funciona como um portal de entrada do nosso sistema, podemos chamar qualquer uma das nossas aplicações utilizando as rotas já definidas. Exemplo:
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-kubernetes-loadbalancer</artifactId>
</dependency>
```      

```
curl http://ip do multipass:8080/departamento/id do departamento
```
Ou podemos utilizar o swagger: ttp://ip multipass:8080/swagger-ui/index.html
Obs: O serviço do gateway está com a porta 8080 exposta nesse exemplo, ou seja, nodeport: 8080.

##### Circuit breaker
Estamos utilizando no serviço de departamento, o circuit break Resilience4j, para isso colocamos a dependência abaixo:
```
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>
```

##### Conclusão
Load balance é um dos padrões chave numa arquitectura de microserviços e Spring Cloud está a substituindo o cliente Ribbon. Por padrão, o balanceamento de carga no Kubernetes é baseado em serviços, no entanto, é necessário utilizar ferramentas adicionais para mecanismos de encaminhamento mais avançados. O Spring Cloud Kubernetes vem com algumas características interessantes, uma delas é a capacidade de balanceamento de carga através de múltiplos namespaces. Também pode utilizar os componentes adicionais Spring Cloud como um disjuntor. Em comparação com as ferramentas do Istio, ainda não é muito. Será uma hipótese de melhoria? Veremos, mas o Spring Cloud Kubernetes é atualmente um dos projetos mais populares do Spring Cloud, será uma boa escolha ao migrar sua arquitectura de microserviços Spring Cloud para Kubernetes.

### Spring Cloud Kubernetes Load Balancer

O suporte do Spring Cloud Kubernetes Load Balancer foi adicionado no último lançamento do Spring Cloud Hoxton.SR8. Foi provavelmente o último projecto em Spring Cloud que utilizou o Ribbon como equilibrador de carga do lado do cliente. A implementação actual baseia-se no projecto Spring Cloud LoadBalancer. Fornece dois modos de comunicação. O primeiro deles detecta os endereços IP de todas os pods em funcionamento dentro de um determinado serviço. O segundo deles utiliza o nome Kubernetes Service para pesquisar todas as instâncias alvo.
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

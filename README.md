# spring-cloud

#Pendências
##Spring-bus
###Para funcionar o @RefreshScope tem q enviar uma request sem corpo para http://localhost:8888/actuator/bus-refresh (endpoint do config-server), ai atualiza os properties dos clients

##Api-Gateway
###testar o Zuul e o Spring-Api-Gateway de formas separadas para entender melhor

##Zuul
Para testar o zuul, todas as chamadas tem que começar com http:8080/api/{endpoint-servico}
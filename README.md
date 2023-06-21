Como rodar o projeto:

- Ter jdk ou jre instalada (java 17+)
- Clonar o projeto para a sua máquina
- Ter instalado o Docker
  - Inicializar mongodb no docker, dentro da pasta /docker do projeto:
    - comando p/ rodar mongodb pelo terminal:
      ``` bash
      docker-compose -f mongodb-docker-compose.yml up -d
      ```
- Ter uma ide para rodar o projeto pela classe main BackExempleApplication
- Acessar a rota http://localhost:8081 e testar a api

- Documentacao: http://localhost:8081/swagger-ui/index.html

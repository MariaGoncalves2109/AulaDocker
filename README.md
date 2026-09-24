# Solutions 02 - Docker

## 1. Docker Básico

### a) Dockerfile e Construção de Imagens

Primeiro, deve escrever o ficheiro Java `HelloWorld.java`, que imprime na consola um simples "Hello World!".

Depois, deve escrever o ficheiro `Dockerfile`, utilizando os seguintes comandos (referência em [https://docs.docker.com/reference/dockerfile/](https://docs.docker.com/reference/dockerfile/)):

- **FROM:** Coloca uma imagem base a partir da qual a aplicação será construída.  
  Ao indicar uma imagem com o `FROM`, o Docker faz o download do repositório, como por exemplo [https://hub.docker.com/](https://hub.docker.com/).
  Pode indicar vários `FROM` no mesmo Dockerfile, cada um correspondente a um novo estágio do processo de construção do container, e é especialmente útil quando utilizado juntamente com o `COPY`.

- **COPY:** Copia os ficheiros da máquina onde se corre o Docker para o sistema de ficheiros da imagem.

- **RUN:** Permite correr instruções durante a criação da imagem para criar uma nova camada por cima da imagem.  
  Cada camada corresponde aos comandos corridos do Dockerfile e adiciona ficheiros ao sistema de ficheiros.

- **ENTRYPOINT:** Define o executável padrão para a aplicação.  
  Quando o container é iniciado, a aplicação definida em `ENTRYPOINT` é corrida.

- **CMD:** Define o comando a ser corrido quando se executa um container a partir de uma imagem.  
  Caso o container seja iniciado com um comando via `docker run`, esse comando substitui o `CMD`.  
  É tipicamente utilizado para passar argumentos ao `ENTRYPOINT`.

- As diferenças entre `RUN`, `CMD` e `ENTRYPOINT` são discutidas em [https://www.docker.com/blog/docker-best-practices-choosing-between-run-cmd-and-entrypoint/](https://www.docker.com/blog/docker-best-practices-choosing-between-run-cmd-and-entrypoint/).

- **VOLUME:** Cria um novo volume vazio sempre que o container é construído.

Depois, deve construir o container, correndo:

```bash
docker build --tag 'hello-world-app' .
```

Sugestão: altere o Dockerfile, de modo a copiar o ficheiro HelloWorld.java para uma pasta no docker.
Que alterações deverá fazer ao Dockerfile?

### b) Execução de Containers

Para iniciar o container, corra:

```bash
docker run hello-world-app
```

Pode também iniciar o container em modo iterativo, para ter acesso à linha de comandos, por exemplo.
Para tal, deverá correr:

```bash
docker run -it hello-world-app
```

Para ver os containers ativos, corra:

```bash
docker ps
```

Para aceder à shell, deve correr:

```bash
docker exec <container-id> <command>
```

### c) Volumes e Persistência de Dados

Explique a diferença entre volumes e bind mounts.

**Volumes:**

**Bind mounts:**

Para pôr em prática o uso de volumes, deve escrever um ficheiro `Logger.java` com a seguinte funcionalidade:

- Leia o nome do utilizador da consola e imprima uma mensagem de boas-vindas;
- Crie um ficheiro chamado `/logs/log.txt`, onde escreve o nome do utilizador;
- Caso o ficheiro já exista, leia o conteúdo do ficheiro, imprima-o na consola e substitua-o no ficheiro pelo nome do utilizador.

Depois, deve voltar a escrever um `Dockerfile` que compile e corra o programa `Logger.java` e, em seguida, construir o container.  
Crie um volume e inspecione-o:

```bash
docker volume create logs
docker volume inspect logs
```

Inicie o container com o volume com o comando:

```bash
docker run -v logs:/logs -it logger-app
```

Insira o seu nome e inspecione o output. Depois, inicie novamente o container, insira um nome diferente e verifique o output.

Sugestão: de seguida, apague o volume e volte a correr os passos anteriores.
```bash
docker volume rm logs
```

Poderá ter também que eliminar o container que criou anteriormente, para depois eliminar o volume:
```bash
docker container rm <container-id>
```

Nota alguma diferença?

## 3. Maven
O principal ficheiro para configuração do Maven é o `pom.xml` (Project Object Model). O ficheiro deve estar no mesmo nível da pasta `src`:

```
project-root/
 ├── src/
 │    └── main/
 │         └── java/
 │              └── HelloWorld.java
 └── pom.xml
```

O ficheiro é um `.xml` definido por um conjunto de tags. A seguir vamos fazer uma breve descrição das principais:


```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    ...

</project>
```

- `<?xml>` define a versão e o encoding que será utilizado no ficheiro. 
- `<project>` Elemento raiz que contém toda a configuração do projeto. As tags `<xmlns>`, `<xmlns:xsi>` e `<xsi:schemaLocation>` definem o namespace e a localização do esquema XML para validação.

```xml
    <groupId>pt.ul.fc.css.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>Demo project</description>

    <properties>
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
```

- `<groupId>` define o identificador do grupo, normalmente utilizando uma convenção de nome reverso de domínio (e.g., example.css.fc.ul.pt vira pt.ul.fc.css.example)
- `<artifactId>` identificador único do artefato (projeto).
- `<version>` versão atual do projeto
- `<name>` nome da projeto
- `<description>` curta descrição sobre o projeto

- `<properties>` define um conjunto de tags que podem ser importantes para o projeto
- `<java.version>` especifica a versão do Java para qual o projeto será compilada
- `<maven.compiler.source>` and `<maven.compiler.target>` especifica a versão do maven que será utilizada

```xml
    <dependencies>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.2</version>
        </dependency>
    </dependencies>
```

- `<dependencies>` começa o conjunto de dependências que serão utilizadas pelo projeto
- `<dependency>` indica qual dependência será descarregada. Por defeito, o maven descarrega a biblioteca diretamente do Maven Repository
  - `<groupId>` identifica o grupo da dependência.
  - `<artifactId>` nome do artefato
  - `<version>` versão da biblioteca


```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.2.4</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>DBtest</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
                </plugin>
            </plugins>
    </build>
```

- `<build>` define um conjunto de identificadores que serão utilizados durante o build do projeto. Isso inclue `<plugin>` e `<resources>`
- `<plugins>` define o conjunto de plugins que serão utilizados
  - `<plugin>` define as informações de um plugin específico
  - `<groupId>` nome do grupo do plugin
  - `<artifactId>` nome do artefato
  - `<version>` versão do plugin
  - `<executions>` define em qual fase do ciclo de vida (neste caso, `<phase> package </phase>`) o plugin será executado.
  - `<goals>` metas onde o plugin será executado.
    - `<goal>shade</goal>` meta específica do plugin `maven-shade-plugin` que é usada para empacotar o projeto juntamente com todas as suas dependências em um único arquivo JAR (conhecido como `uber-jar`).
  - `<configuration>` configurações do plugin
  - `<transformers>` transformers são usados para modificar recursos do JAR durante o processo de empacotamento.
    - `<transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">` Define um transformer que para o manifesto do JAR.
      - `<mainClass>DBtest</mainClass>` Pelo transformer, especifica que o entry point do JAR empacotado será DBtest. Essa informação é importante para o manifesto, pois garante que não é necessário dizer a main no CLI.


Você pode utilizar o Maven para gerenciar as dependências do seu projeto, adicionando diversas bibliotecas. Por exemplo, para incluir o JUnit, basta adicionar a dependência correspondente no seu arquivo `pom.xml`. Por exemplo, pode inserir o JUnit como dependência utilizando a configuração definida no [repositório Maven do JUnit](https://mvnrepository.com/artifact/junit/junit).

Certifique-se de que seu arquivo `pom.xml` esteja devidamente configurado com todas as dependências e plugins necessários para o projeto.

Após a configuração, você pode utilizar os seguintes comandos:


- Compilar o projeto
    ```bash
    mvn compile
    ```

- Executar os tests
    ```bash
    mvn test
    ```

- Compilar e empacotar o projeto:
  ```bash
  mvn clean package
  ```
Após gerar o ficheiro .jar (por defeito é criado dentro da pasta `target`) pode executá-lo normalmente:
   ```bash
   java -jar target/<nomedojar>
   ``` 


Para utilizar num contexto de containers, lembrem-se de adicionar o pom.xml para dentro do container no Dockerfile

``` ADD pom.xml . ```


## 4. Docker compose

Para executar multiplos containers ao mesmo tempo, vamos fazer uso do Docker Compose. Para isso, é necessário ter um `docker-compose.yml` bem escrito com sua configuração. Segue um exemplo de `docker-compose.yml` e a descrição dos comandos do mesmo.

```yaml
services:
  # DB*****************************************
  pgserver:
    image: postgres:latest
    container_name: pgserver
    volumes:
      - postgres-data:/var/lib/postgresql/data
    expose:
      - 5432
    ports:
      - 5432:5432
    networks:
      - dbnet
    environment:
      - POSTGRES_USER=user
      - POSTGRES_PASSWORD=password
      - POSTGRES_DB=mydatabase
    restart: unless-stopped

  # APP*****************************************
  springbootapp:
    image: myapp:latest
    build:
      context: .
    container_name: java_app
    restart: unless-stopped
    depends_on:
      - pgserver
    networks:
      - dbnet
    command: /bin/bash

volumes:
  postgres-data:

networks:
  dbnet:
    driver: bridge
```

- `services` define quais serão os serviços (containers) que serão instanciados pelo Docker Compose
  - `pgserver` nome do serviço com a base de dados.
    - `image` especifica qual será a imagem utilizada pelo container. No exemplo temos `postgres` como imagem e `latest` como a versão utilizada.
    - `volumes` define o mapeamento de volumes, ou seja, associa um volume (ou caminho do host) a um caminho no container.
    - `expose` portos que serão expostas pelo container.
    - `ports` como será realizado o mapeamento dos portos. A sintaxe é `HOST_PORT:CONTAINER_PORT`.
    - `network` Especifica a(s) rede(s) à(s) qual(is) o container estará conectado. No exemplo, estará conectado a `dbnet`
    - `environment` define variáveis de ambiente que serão passadas para o container.
    - `restart` define a política de reinicialização do container. No exemplo, deve continuar a recomeçar o container caso não seja explicitamente interrompido.
  - `springbootapp` nome do serviço para a aplicação Spring Boot
    - `build` define como será realizado o build da imagem que criará o container
      - `context` indica o caminho para o diretório onde se encontra o Dockerfile. No exemplo, o Dockerfile está na própria pasta do serviço.
      - `container_name` define explicitamente o nome do container
      - `depends_on` permite definir, para esse serviço, quais outros serviços ele depende. Assim, os serviços listados em `depends_on` serão iniciados antes que o serviço springbootapp seja iniciado.
      - `command` especifica o comando que será executado ao iniciar o container.
- `volumes` define os volumes que serão utilizados
    - `postgres-data` nome do volume que será utilizado para armazenar dados persistentes do PostgreSQL
- `networks` define as redes que serão utilizadas
    - `dbnet` nome da rede
        - `driver` tipo da rede que será utilizada. No exemplo, é utilizado o driver bridge, que cria uma rede interna para os containers.


Uma vez que tenha o `docker-compose.yml` bem configurado, só é necessário executar `docker compose up` para que o Docker Compose instancie os containers definidios. `docker compose up --build` faz build novamente da imagem (importante caso se esteja a alterar o código fonte).

- Para executar um comando específico em um dos containers, se pode utilizar
    ```bash
    docker exec -it pgserver psql -U user -d mydatabase
    ```

    Neste caso, é executado no `pgserver` o comando `psql` com o utilizador `user` para base de dados `mydatabase`.

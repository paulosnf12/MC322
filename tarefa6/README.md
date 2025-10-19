# RPG Narrativo - Tarefa 6

Este projeto é a implementação de um Jogo Narrativo de RPG desenvolvido em Java, como parte da disciplina MC322 - Programação Orientada a Objetos da Universidade Estadual de Campinas (Unicamp). Esta sexta tarefa expande o jogo com funcionalidades de persistência de dados, permitindo salvar e carregar o progresso, e aprofunda conceitos de design orientado a objetos através de uma refatoração significativa dos sistemas de combate e recompensa.

## Descrição Geral

A Tarefa 6 transforma o RPG de uma experiência de sessão única para uma aventura contínua. A principal funcionalidade adicionada é a **persistência de dados**: a capacidade de salvar o estado atual do jogo em um arquivo e carregá-lo posteriormente. Para isso, foi utilizada a biblioteca **JAXB (Jakarta XML Binding)** para serializar o estado do jogo em arquivos XML.

Além disso, o projeto passou por uma importante refatoração para aplicar corretamente os conceitos de **Agregação e Composição**, tornando o código mais eficiente, coeso e alinhado com as boas práticas de programação orientada a objetos.

## Destaques Principais: Persistência e Refatoração de Agregação

O foco desta tarefa foi adicionar funcionalidades de persistência e refinar a arquitetura do código.

*   **Salvar e Carregar Jogo com JAXB**: Foi implementada uma classe `GerenciadorDePersistencia` responsável por converter a sessão de jogo (o objeto `Batalha`) em um arquivo XML e vice-versa. Agora, os jogadores podem interromper sua aventura e retomá-la de onde pararam.
*   **Refatoração com Agregação e Composição**: O núcleo do jogo foi revisado para aplicar conceitos avançados de OO:
    *   **Sistema de Recompensa (Agregação)**: Em vez de monstros carregarem instâncias de `Arma` que talvez nunca sejam usadas, eles agora possuem uma lista de especificações (`ArmaDropSpec`). A arma só é instanciada no momento exato do "drop", otimizando o uso de memória.
    *   **Sistema de Combate (Agregação Compartilhada)**: Ações de combate genéricas (como o ataque de um `Espirito`) agora são implementadas como uma única instância estática (`static final`), compartilhada por todos os monstros daquele tipo. Isso reduz drasticamente a criação de objetos duplicados.
    *   **Lógica do Jogo (Composição)**: A classe `Batalha` agora é a verdadeira dona do estado do jogo, gerenciando o ciclo de vida do `Heroi` e das fases, enquanto a classe `Main` atua apenas como um ponto de entrada e gerenciador de menus.

## Estrutura do Projeto

A estrutura foi adaptada para incluir as bibliotecas JAXB e a pasta de saves, mantendo a organização:

<p align="center">
  <img src="estrutura.png" alt="Estrutura do projeto da Tarefa 6"/>
</p>

*   **`lib/`**: Pasta que contém bibliotecas externas (`.jar`). Agora abriga não apenas o JUnit, mas também os módulos da implementação JAXB (`jaxb-runtime`, `jakarta.xml.bind-api`, etc.).
*   **`saves/`**: Nova pasta, criada automaticamente, onde os arquivos de progresso do jogo (em formato `.xml`) são armazenados.
*   **`test/`**: Mantém a estrutura de testes, que continuam a garantir a robustez do código após a refatoração.
*   **`docs/`**: Contém a documentação HTML gerada pelo Javadoc.

## Como Compilar e Executar

### Pré-requisitos

*   [Java Development Kit (JDK) 21](https://www.oracle.com/java/technologies/downloads/) ou superior.
*   Um ambiente de terminal Bash (como Git Bash no Windows, ou qualquer terminal Linux/macOS).

### Passos para Compilação e Execução

1.  **Clone o Repositório**:
    Clone o repositório e navegue até o diretório da `tarefa6`.
    ```bash
    git clone https://github.com/paulosnf12/MC322.git
    cd MC322/tarefa6
    ```

2.  **Para Compilar o Projeto (Código-fonte + Testes)**:
    Este comando compila todos os arquivos das pastas `src` e `test`, utilizando as bibliotecas da pasta `lib` e salvando os arquivos `.class` na pasta `bin`.
    ```bash
    javac -d bin -cp "lib/*" -sourcepath src $(find src -name "*.java") $(find test -name "*.java")
    ```

3.  **Para Executar o Jogo Principal**:
    Para jogar, use o comando correspondente ao seu sistema operacional. Ele executa a classe `Main` a partir dos arquivos compilados.

    *   **Linux (ou Bash no macOS):**
        ```bash
        java -cp "bin:lib/*" com.rpg.game.Main
        ```
    *   **Windows (usando Git Bash):**
        ```bash
        java -cp "bin;lib/*" com.rpg.game.Main
        ```

4.  **Para Executar os Testes Unitários**:
    Este comando utiliza o executor do JUnit para rodar os testes. Use a versão correspondente ao seu SO.

    *   **Linux (ou Bash no macOS):**
        ```bash
        java -jar lib/junit-standalone-1.13.4.jar --class-path "bin:lib/jakarta.xml.bind-api-4.0.0.jar:lib/jaxb-runtime-4.0.4.jar" --scan-classpath
        ```
    *   **Windows (usando Git Bash):**
        ```bash
        java -jar lib/junit-standalone-1.13.4.jar --class-path "bin;lib/jakarta.xml.bind-api-4.0.0.jar;lib/jaxb-runtime-4.0.4.jar" --scan-classpath
        ```

5.  **Para Gerar a Documentação Javadoc**:
    Este comando lê os comentários Javadoc no seu código-fonte (`src`) e gera um site HTML na pasta `docs`.
    ```bash
    javadoc -d docs -cp "lib/*" -sourcepath src -subpackages com.rpg -author -version -linksource
    ```

## Acessando a Documentação

A documentação completa do projeto, gerada pelo Javadoc, está localizada na pasta `docs/`. Para visualizá-la, abra o arquivo **`docs/index.html`** no seu navegador de preferência.

## Implementações e Validação dos Requisitos

*   **`GerenciadorDePersistencia` e JAXB**
    *   **Objetivo**: Implementar a lógica para salvar e carregar o estado do jogo.
    *   **Implementação**: A classe `GerenciadorDePersistencia` foi criada com métodos estáticos (`salvarBatalha`, `carregarBatalha`, `listarJogosSalvos`) que utilizam JAXB para serializar/desserializar o objeto `Batalha` para/de um arquivo XML na pasta `saves/`.
    *   **Anotações JAXB**: Todas as classes que fazem parte do estado do jogo (personagens, itens, fases, etc.) foram anotadas com `@XmlElement`, `@XmlRootElement` e `@XmlSeeAlso` para guiar o processo de serialização, especialmente para lidar com herança e interfaces. Campos que não devem ser salvos, como `Random`, foram marcados como `transient`.

*   **Atualização da Interface do Usuário**
    *   **Objetivo**: Permitir que o jogador acesse as novas funcionalidades.
    *   **Implementação**: O menu principal na classe `Main` agora verifica a existência de arquivos na pasta `saves/` e exibe dinamicamente a opção "Carregar Jogo". O menu pós-combate na classe `Batalha` agora inclui a opção "Salvar Jogo", que invoca o `GerenciadorDePersistencia`.

*   **Refatoração do Sistema de Recompensa**
    *   **Objetivo**: Aplicar o conceito de Agregação para otimizar a criação de itens.
    *   **Implementação**: A classe `Monstro` não armazena mais uma lista de `Arma`. Em vez disso, ela armazena uma lista de `ArmaDropSpec`, uma classe que contém as "instruções" para criar uma arma (nome da classe, dano base, nível mínimo). A arma real só é instanciada através de reflexão (`Reflection API`) quando o método `droparLoot()` é chamado, evitando a criação de objetos desnecessários.

*   **Refatoração do Sistema de Combate**
    *   **Objetivo**: Aplicar o conceito de Agregação Compartilhada para reutilizar ações de combate.
    *   **Implementação**: Classes de monstros como `Goblin`, `Vampiro` e `Espirito` agora utilizam uma única instância `static final` de sua ação de ataque principal (ex: `ATAQUE_GOBLIN_INSTANCE`). Todas as instâncias de `Goblin` no jogo apontam para este mesmo objeto de ação, economizando memória e reforçando a ideia de que a ação é uma capacidade compartilhada da espécie, não de cada indivíduo.

## Créditos

Este projeto foi desenvolvido como parte de um trabalho acadêmico.

*   **Desenvolvedores do Projeto**:
    *   Bárbara Maria Barreto Fonseca de Cerqueira César
    *   Paulo Santos do Nascimento Filho
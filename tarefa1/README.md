# RPG - Jogo Narrativo (MC322 - Programação Orientada a Objetos)

## Descrição Geral

Este projeto consiste na implementação de um jogo de RPG (Role-Playing Game) simplificado, desenvolvido como parte da disciplina de Programação Orientada a Objetos (MC322) da UNICAMP. O objetivo principal é aplicar conceitos fundamentais de POO, como encapsulamento, herança e polimorfismo, para simular um cenário de sobrevivência onde um herói enfrenta uma série de monstros.

Inspirado nos complexos e influentes jogos de RPG, o projeto busca recriar a essência da progressão de personagens, mundos interativos e narrativas ricas por meio de um sistema incremental em Java.

## Componentes Principais do Jogo

O jogo é estruturado em torno dos seguintes componentes, conforme descrito no enunciado:

*   **Sistema de Personagens:** Representação de avatares de jogadores (heróis) e NPCs (monstros), incluindo atributos como força, pontos de vida, e sistemas de progressão (níveis e experiência).
*   **Mundo Virtual:** Embora simplificado para este projeto, o conceito de um espaço onde a aventura acontece é fundamental.
*   **Sistema de Navegação e Interação:** Gerencia como os personagens se movem e interagem com o ambiente.
*   **Interface do Jogador (UI) e Estado do Jogo:** Elementos visuais (neste caso, saída de console) que comunicam o estado do jogo e do personagem.
*   **Motor de Regras e Mecânicas de Jogo:** Algoritmos que definem como as ações (ataques, habilidades) são resolvidas com base nas regras do jogo.
*   **Sistema de Combate:** Regras que governam os confrontos entre heróis e monstros.
*   **Gerenciador de Narrativa e Missões:** Controla o fluxo da história principal e dos eventos do jogo.

## Objetivos do Laboratório

Os principais objetivos alcançados com este projeto foram:

*   Familiarização com a linguagem Java (Versão 21) e o ambiente de desenvolvimento (VSCode).
*   Compreensão e aplicação dos conceitos de Programação Orientada a Objetos: classes, atributos e métodos.
*   Utilização do conceito de Herança para criar hierarquias de personagens (Herói, Monstro).
*   Implementação de Classes Abstratas (`Personagem`, `Heroi`, `Monstro`) para definir contratos e comportamentos comuns.
*   Criação de um sistema funcional que demonstra a interação entre objetos de diferentes classes.

## Cenário de Sobrevivência

A classe `Main` simula um desafio de sobrevivência. Neste cenário, um único herói enfrenta e deve sobreviver a três encontros consecutivos com monstros diferentes, um em cada turno.

**Fluxo do Desafio:**

1.  **Criação dos Personagens:** Uma instância de herói e três instâncias de monstros diferentes são criadas.
2.  **Apresentação do Desafio:** Uma mensagem inicial introduz o cenário, e o status inicial do herói é exibido.
3.  **Simulação dos Turnos:** Um laço de repetição executa 3 vezes, simulando cada turno.
    *   A chegada de cada monstro é anunciada.
    *   O herói ataca o monstro.
    *   O monstro ataca o herói.
    *   Verificação de sobrevivência do herói: se os pontos de vida chegarem a zero, o jogo termina com uma mensagem de "Game Over".
    *   O status do herói e do monstro são exibidos ao final de cada turno.
4.  **Conclusão:** Se o herói sobreviver aos três turnos, uma mensagem de vitória é exibida.

## Estrutura do Projeto

O projeto está organizado na seguinte estrutura de diretórios e classes, refletindo a abordagem orientada a objetos:
!Estrutura(estrutura.png)

**Explicação das Classes:**

*   **`Personagem.java` (Classe Abstrata):** Base para todas as entidades vivas no jogo. Define atributos comuns como `nome`, `pontosDeVida`, `forca` e métodos abstratos como `atacar()`.
*   **`Heroi.java` (Classe Abstrata):** Herda de `Personagem`. Base para todas as classes jogáveis. Adiciona atributos como `nivel` e `experiencia`, e o método abstrato `usarHabilidadeEspecial()`.
*   **`Monstro.java` (Classe Abstrata):** Herda de `Personagem`. Base para todos os inimigos do jogo. Adiciona o atributo `xpConcedido`.
*   **Classes Concretas de Herói:**
    *   **`Elfo.java`:** Uma implementação concreta de `Heroi`. Provavelmente possui um atributo único como "precisão" ou relacionado a arcos, e implementa a lógica de ataque e habilidade especial.
    *   **`Paladino.java`:** Outra implementação concreta de `Heroi`. Pode ter um atributo único como "fúria" ou "fé", com sua própria lógica de ataque e habilidade especial.
*   **Classes Concretas de Monstro:**
    *   **`Goblin.java`:** Uma implementação concreta de `Monstro`. Define seus próprios atributos e lógica de ataque.
    *   **`Espirito.java`:** Outra implementação concreta de `Monstro`. Provavelmente com atributos e comportamentos únicos.
    *   **`Vampiro.java`:** Mais uma implementação concreta de `Monstro`, com características próprias.
*   **Classes Auxiliares (Itens/Habilidades):**
    *   **`Arcos.java`:** Pode representar um tipo de arma ou habilidade específica para classes como `Elfo`.
    *   **`Espada.java`:** Similar a `Arcos.java`, pode representar uma arma ou habilidade para classes como `Paladino`.
*   **`Main.java`:** Contém a lógica principal do jogo, orquestrando o cenário de sobrevivência, a criação de personagens, a simulação de turnos e a exibição dos resultados.

## Como Compilar e Executar

Para compilar e executar o projeto, siga os passos abaixo (utilizando os mesmos comandos que os PEDs usarão para avaliação):

1.  Navegue até o diretório `tarefa1` no seu terminal:
    ```bash
    cd tarefa1
    ```
2.  Compile todos os arquivos `.java` no diretório `src` e coloque os arquivos `.class` no diretório `bin`:
    ```bash
    javac -d bin $(find src -name "*.java")
    ```
3.  Execute a classe `Main` a partir do diretório `bin`:
    ```bash
    java -cp bin Main
    ```

Certifique-se de que a sua classe `Main` (ou equivalente, caso utilize pacotes) contenha o método `public static void main(String[] args)` e esteja organizada de acordo com a estrutura de diretórios definida para que a compilação e execução ocorram corretamente.

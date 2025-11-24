# 🎮 Lights Out - Projeto Final MC322

Um jogo **Lights Out** completo desenvolvido em Java com JavaFX, implementando padrões de orientação a objetos e arquitetura MVC.

## 📋 Sobre o Jogo

**Lights Out** é um puzzle onde o objetivo é desligar todas as luzes do tabuleiro. Ao clicar em uma célula, ela e suas células adjacentes (cima, baixo, esquerda, direita) têm seus estados invertidos. O desafio está em encontrar a sequência correta de cliques para resolver o tabuleiro.

## ✨ Funcionalidades

### 🎯 Mecânicas de Jogo
- **3 Níveis de Dificuldade**:
  - 🟢 **Fácil**: Tabuleiro 3x3 (9 células)
  - 🟡 **Médio**: Tabuleiro 5x5 (25 células)
  - 🔴 **Difícil**: Tabuleiro 7x7 (49 células)
- **Sistema de Turnos**: Cada jogo consiste em 3 turnos da dificuldade escolhida
- **Sistema de Pontuação**: Pontuação baseada em movimentos e tempo
- **Cronômetro**: Acompanhamento do tempo decorrido em tempo real
- **Reiniciar Tabuleiro**: Possibilidade de resetar o tabuleiro para o estado inicial

### 💾 Persistência
- **Salvar Jogo**: Salve seu progresso a qualquer momento
- **Carregar Jogo**: Continue de onde parou
- **Ranking de Pontuações**: Sistema de recordes por dificuldade
- **Histórico de Partidas**: Visualize estatísticas de todas as partidas

### 🎨 Interface Gráfica
- **Menu Principal**: Interface moderna e intuitiva
- **Painel de Jogo**: Visualização clara do tabuleiro e informações do jogo
- **Painel de Pontuações**: Ranking agrupado por dificuldade com estatísticas
- **Feedback Visual**: Indicação clara de células ligadas/desligadas

## 🛠️ Tecnologias Utilizadas

- **Java 21**: Linguagem de programação
- **JavaFX 21**: Framework para interface gráfica
- **Gradle**: Sistema de build e gerenciamento de dependências
- **JUnit 5**: Framework de testes unitários

## 📦 Requisitos

- **Java Development Kit (JDK) 21** ou superior
- **Gradle 9.0** ou superior (incluído via wrapper)

## 🚀 Como Executar

### Pré-requisitos
Certifique-se de ter o JDK 21 instalado e configurado no seu sistema.

### Executando o Jogo

#### Linux/macOS:
```bash
./gradlew clean run
```

#### Windows:
```cmd
gradlew.bat clean run
```

### Gerando a Documentação Javadoc

```bash
./gradlew javadoc
```

A documentação será gerada em: `app/build/docs/javadoc/`

Abra o arquivo `index.html` no navegador para visualizar.

### Executando os Testes

```bash
./gradlew test
```

Os relatórios de teste estarão em: `app/build/test-results/`

## 📁 Estrutura do Projeto

```
projeto_final/
├── app/
│   ├── src/
│   │   ├── main/java/projeto_final/
│   │   │   ├── abstracts/          # Classes abstratas
│   │   │   │   ├── ComponenteGrafico.java
│   │   │   │   ├── Dificuldade.java
│   │   │   │   └── ElementoJogo.java
│   │   │   ├── controller/         # Controladores (MVC)
│   │   │   │   └── Game.java
│   │   │   ├── exceptions/         # Exceções customizadas
│   │   │   │   ├── ConfiguracaoInvalidaException.java
│   │   │   │   ├── DadosCorruptosException.java
│   │   │   │   └── MovimentoInvalidoException.java
│   │   │   ├── interfaces/          # Interfaces
│   │   │   │   ├── Desenhavel.java
│   │   │   │   ├── EventListener.java
│   │   │   │   ├── Pontuavel.java
│   │   │   │   └── Salvavel.java
│   │   │   ├── model/               # Modelos (MVC)
│   │   │   │   ├── Celula.java
│   │   │   │   ├── DificuldadeFacil.java
│   │   │   │   ├── DificuldadeMedio.java
│   │   │   │   ├── DificuldadeDificil.java
│   │   │   │   ├── EstadoJogo.java
│   │   │   │   ├── GerenciadorArquivos.java
│   │   │   │   ├── GerenciadorPontuacoes.java
│   │   │   │   ├── Jogador.java
│   │   │   │   ├── PontuacaoRecord.java
│   │   │   │   └── Tabuleiro.java
│   │   │   ├── view/                # Views (MVC)
│   │   │   │   ├── MenuPrincipal.java
│   │   │   │   ├── PainelJogo.java
│   │   │   │   └── PainelPontuacao.java
│   │   │   └── App.java            # Classe principal
│   │   └── test/java/projeto_final/ # Testes unitários
│   │       ├── abstracts/
│   │       ├── controller/
│   │       ├── exceptions/
│   │       ├── fixtures/
│   │       └── model/
│   └── build.gradle
├── gradle/
├── gradlew
├── gradlew.bat
└── readme.md
```

## 🏗️ Arquitetura

O projeto segue o padrão **MVC (Model-View-Controller)**:

- **Model**: Classes em `model/` e `abstracts/` que representam a lógica de negócio
- **View**: Classes em `view/` que gerenciam a interface gráfica
- **Controller**: Classe `Game` que coordena a interação entre Model e View

### Padrões de Design Implementados

- **Strategy**: Dificuldades do jogo (`DificuldadeFacil`, `DificuldadeMedio`, `DificuldadeDificil`)
- **Template Method**: `ComponenteGrafico` define o fluxo de renderização
- **Observer**: `EventListener` para processamento de eventos
- **Singleton**: `GerenciadorArquivos` e `GerenciadorPontuacoes`

## 📊 Sistema de Pontuação

A pontuação é calculada usando a fórmula:

```
Pontuação = (1000 / movimentos) × (300 / tempo_segundos) × multiplicador_dificuldade
```

Onde:
- **Fácil**: multiplicador = 1.0
- **Médio**: multiplicador = 1.5
- **Difícil**: multiplicador = 2.0

A pontuação é acumulada ao longo dos 3 turnos do jogo.

## 🎮 Como Jogar

1. **Iniciar Novo Jogo**:
   - Clique em "Novo Jogo" no menu principal
   - Digite seu nome
   - Escolha a dificuldade (Fácil, Médio ou Difícil)

2. **Jogar**:
   - Clique nas células para alternar seu estado e das adjacentes
   - O objetivo é desligar todas as células
   - Complete 3 turnos para finalizar o jogo

3. **Funcionalidades Durante o Jogo**:
   - **Reiniciar**: Reseta o tabuleiro para o estado inicial
   - **Salvar**: Salva o progresso atual
   - **Voltar ao Menu**: Retorna ao menu principal

4. **Visualizar Pontuações**:
   - Acesse "Ver Pontuações" no menu principal
   - Veja o ranking agrupado por dificuldade
   - Confira suas estatísticas e recordes

## 🧪 Testes

O projeto inclui uma suíte completa de testes unitários cobrindo:

- Modelos (`Celula`, `Tabuleiro`, `Jogador`, `PontuacaoRecord`)
- Controller (`Game`, persistência)
- Classes abstratas (`DificuldadeFacil`, `DificuldadeMedio`, `DificuldadeDificil`)
- Exceções customizadas
- Gerenciadores (`GerenciadorPontuacoes`)

Execute os testes com:
```bash
./gradlew test
```

## 📚 Documentação

A documentação completa da API está disponível em Javadoc. Para gerá-la:

```bash
./gradlew javadoc
```

Acesse: `app/build/docs/javadoc/index.html`

## 🎯 Funcionalidades Implementadas

- ✅ Sistema de dificuldades (Fácil, Médio, Difícil)
- ✅ Sistema de turnos (3 turnos por jogo)
- ✅ Sistema de pontuação e recordes
- ✅ Salvamento e carregamento de jogos
- ✅ Ranking de pontuações por dificuldade
- ✅ Interface gráfica moderna
- ✅ Cronômetro em tempo real
- ✅ Reiniciar tabuleiro
- ✅ Testes unitários completos
- ✅ Documentação Javadoc completa

## 📝 Notas de Desenvolvimento

- O projeto utiliza serialização Java para persistência de dados
- Os arquivos de jogo são salvos em formato binário (`.dat`)
- O sistema de pontuações mantém um recorde por jogador por dificuldade
- A interface gráfica é responsiva e se adapta ao tamanho do tabuleiro

## 👥 Autores

- **Bárbara**
- **Lucas**
- **Paulo**

**Projeto Final MC322** - Universidade Estadual de Campinas (UNICAMP)

## 📄 Licença

Este projeto foi desenvolvido como trabalho acadêmico para a disciplina MC322.

---

<p align="center">
  <img src="ex_aplicacao_javafx.png" alt="Interface do Jogo"/>
</p>

<p align="center">
  <img src="prot_1.png" alt="Protótipo 1"/>
</p>

<p align="center">
  <img src="prot_2.png" alt="Protótipo 2"/>
</p>

## 🎥 Vídeo Demonstrativo

[Assista ao vídeo demonstrativo](https://github.com/user-attachments/assets/b0d3d935-b501-47c3-9ff2-ae45bc749f00)

# 🛡️ Tarefa 1 - MC322: RPG - Jogo Narrativo

Este projeto simula um jogo narrativo de RPG, com heróis enfrentando monstros em um cenário de sobrevivência.

---

## 📦 Estrutura de Classes

### 🔹 Classe Abstrata: `Personagem`

Classe base para todos os personagens do jogo.

### 🔹 Classe Abstrata: `Heroi`

Deriva de `Personagem`. Define comportamentos e atributos básicos de um herói.

### 🔹 Classes Concretas de Heróis

Implementações específicas de heróis com características únicas.

### 🔹 Classe Abstrata: `Monstro`

Também herda de `Personagem`. Define as bases para monstros no jogo.

### 🔹 Classe Concreta de Monstro

Implementação específica de um monstro com suas habilidades e atributos.

---

## 🎮 Classe Principal (`Main`) - Cenário de Sobrevivência

Simula a execução do jogo:

- **Criação dos Personagens:** Heróis e monstros são instanciados com atributos definidos.
- **Apresentação do Desafio:** O objetivo é sobrevivência durante um número de turnos.
- **Simulação dos Turnos em Loop:** A cada turno, ações são executadas entre heróis e monstros.
- **Conclusão do Desafio:**
  - Se o herói sobreviver por **3 turnos**, ocorre **vitória**.
  - Caso contrário, o desafio é **falho**.

---


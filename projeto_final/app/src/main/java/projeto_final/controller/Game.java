// Em: src/main/java/projeto_final/controller/Game.java
package projeto_final.controller;

import projeto_final.model.Tabuleiro;

public class Game {
    private Tabuleiro tabuleiro;

    public void iniciarNovoJogo() {
        // Por enquanto, vamos criar um tabuleiro 3x3 fixo para o nível fácil.
        this.tabuleiro = new Tabuleiro(3);
    }

    // Método para a View chamar quando uma célula for clicada
    public void processarJogada(int linha, int coluna) {
        if (tabuleiro != null) {
            tabuleiro.alternarCelula(linha, coluna);
            verificarVitoria();
        }
    }

    private void verificarVitoria() {
        // Lógica de vitória será adicionada aqui depois.
    }

    // Getter para a View acessar o tabuleiro
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}
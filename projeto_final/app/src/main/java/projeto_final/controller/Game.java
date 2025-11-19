// Em: src/main/java/projeto_final/controller/Game.java
package projeto_final.controller;

import projeto_final.model.Tabuleiro;

public class Game {
    private Tabuleiro tabuleiro;
    
    // Novos atributos conforme especificação [cite: 161, 162]
    private int movimentos;
    private long tempoInicio; // Armazena o tempo em milissegundos
    private boolean jogoEmAndamento;
    private boolean vitoria; // Atributo para sinalizar vitória

    public Game() {
        this.movimentos = 0;
        this.jogoEmAndamento = false;
    }

    public void iniciarNovoJogo() {
        this.movimentos = 0;
        this.tempoInicio = System.currentTimeMillis();
        this.jogoEmAndamento = true;
        this.vitoria = false; // Resetar estado de vitória
        this.tabuleiro = new Tabuleiro(3); 
    }

    public void processarJogada(int linha, int coluna) {
        // Só processa se o jogo estiver valendo
        if (tabuleiro != null && jogoEmAndamento) {
            tabuleiro.alternarCelula(linha, coluna);
            this.movimentos++;
            verificarVitoria(); // Verifica imediatamente após o movimento
        }
    }

    private void verificarVitoria() {
        if (tabuleiro.todasDesligadas()) {
            this.jogoEmAndamento = false; // Para o jogo
            this.vitoria = true;          // Marca vitória
        }
    }

    // Getter para a View saber se ganhou
    public boolean isVitoria() {
        return vitoria;
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    // Getters necessários para a View
    public int getMovimentos() {
        return movimentos;
    }

    public long getTempoDecorrrido() {
        if (!jogoEmAndamento) return 0;
        return (System.currentTimeMillis() - tempoInicio) / 1000; // Retorna em segundos
    }
}
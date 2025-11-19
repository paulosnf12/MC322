// Em: src/main/java/projeto_final/model/Tabuleiro.java
package projeto_final.model;

public class Tabuleiro {
    private final int dimensao;
    private final Celula[][] celulas;

    public Tabuleiro(int dimensao) {
        this.dimensao = dimensao;
        this.celulas = new Celula[dimensao][dimensao];
        inicializarCelulas();
    }

    /**
     * Verifica se todas as células do tabuleiro estão desligadas.
     * @return true se todas estiverem apagadas (vitória), false caso contrário.
     */
    public boolean todasDesligadas() {
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                // Se encontrar pelo menos uma célula ligada, o jogo não acabou.
                if (celulas[i][j].isLigada()) {
                    return false;
                }
            }
        }
        // Se chegou até aqui, todas estão desligadas.
        return true;
    }

    private void inicializarCelulas() {
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                celulas[i][j] = new Celula();
            }
        }
        // Futuramente, aqui você chamará o método para gerar um padrão inicial solucionável.
        // Por enquanto, vamos deixar uma luz acesa para teste.
        celulas[1][1].alternar();
    }

    /**
     * Alterna o estado de uma célula e suas vizinhas diretas.
     * @param linha A linha da célula clicada.
     * @param coluna A coluna da célula clicada.
     */
    public void alternarCelula(int linha, int coluna) {
        // Validação para não clicar fora do tabuleiro
        if (linha < 0 || linha >= dimensao || coluna < 0 || coluna >= dimensao) {
            return;
        }

        // Alterna a própria célula
        celulas[linha][coluna].alternar();
        // Alterna a de cima
        if (linha > 0) celulas[linha - 1][coluna].alternar();
        // Alterna a de baixo
        if (linha < dimensao - 1) celulas[linha + 1][coluna].alternar();
        // Alterna a da esquerda
        if (coluna > 0) celulas[linha][coluna - 1].alternar();
        // Alterna a da direita
        if (coluna < dimensao - 1) celulas[linha][coluna + 1].alternar();
    }
    
    // Métodos para a View poder "ler" o estado do tabuleiro
    public int getDimensao() {
        return dimensao;
    }

    public Celula getCelula(int linha, int coluna) {
        return celulas[linha][coluna];
    }
}
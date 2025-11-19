// Em: src/main/java/projeto_final/view/PainelJogo.java
package projeto_final.view;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import projeto_final.controller.Game;
import projeto_final.model.Tabuleiro;

public class PainelJogo extends GridPane {

    private final Game game;
    private final Rectangle[][] celulasVisuais;

    public PainelJogo(Game game) {
        this.game = game;
        Tabuleiro tabuleiro = game.getTabuleiro();
        int dimensao = tabuleiro.getDimensao();
        this.celulasVisuais = new Rectangle[dimensao][dimensao];

        this.setAlignment(Pos.CENTER);
        this.setHgap(5); // Espaçamento horizontal
        this.setVgap(5); // Espaçamento vertical

        // Cria a representação visual de cada célula do modelo
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                Rectangle rect = new Rectangle(80, 80); // Célula de 80x80 pixels
                rect.setStroke(Color.BLACK); // Borda preta

                final int linha = i;
                final int coluna = j;

                // A MÁGICA ACONTECE AQUI:
                // Quando o retângulo for clicado...
                rect.setOnMouseClicked(event -> {
                    // 1. Avisamos o controlador que uma jogada foi feita.
                    game.processarJogada(linha, coluna);
                    // 2. Pedimos para a tela se atualizar.
                    atualizar();
                });

                celulasVisuais[i][j] = rect;
                this.add(rect, j, i); // Adiciona ao grid na (coluna, linha)
            }
        }
        atualizar(); // Desenha o estado inicial
    }

    /**
     * Lê o estado de cada célula no modelo e atualiza a cor correspondente na tela.
     */
    public void atualizar() {
        Tabuleiro tabuleiro = game.getTabuleiro();
        for (int i = 0; i < tabuleiro.getDimensao(); i++) {
            for (int j = 0; j < tabuleiro.getDimensao(); j++) {
                if (tabuleiro.getCelula(i, j).isLigada()) {
                    celulasVisuais[i][j].setFill(Color.YELLOW); // Cor para "ligada"
                } else {
                    celulasVisuais[i][j].setFill(Color.DARKSLATEGRAY); // Cor para "desligada"
                }
            }
        }
    }
}
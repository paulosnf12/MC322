// Em: src/main/java/projeto_final/view/PainelJogo.java
package projeto_final.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import projeto_final.controller.Game;
import projeto_final.model.Tabuleiro;

// Mudamos de GridPane para BorderPane para acomodar a barra de info
public class PainelJogo extends BorderPane { 
    private final Game game;
    private final Rectangle[][] celulasVisuais;
    private Label lblTempo;
    private Label lblMovimentos;
    private GridPane gridTabuleiro;
    private Timeline timeline; // Atributo de classe para controle do tempo

    public PainelJogo(Game game) {
        this.game = game;
        int dimensao = game.getTabuleiro().getDimensao();
        this.celulasVisuais = new Rectangle[dimensao][dimensao];

        inicializarLayout();
        inicializarTabuleiro(dimensao);
        iniciarCronometro(); // Inicia a atualização da tela
    }

    private void inicializarLayout() {
        // --- Barra Superior (Info) ---
        HBox barraInfo = new HBox(20);
        barraInfo.setAlignment(Pos.CENTER);
        barraInfo.setPadding(new Insets(10));
        barraInfo.setStyle("-fx-background-color: #DDDDDD;");

        lblMovimentos = new Label("Movimentos: 0");
        lblTempo = new Label("Tempo: 0s");
        
        // Estilização básica
        Font fonteInfo = new Font("Arial", 16);
        lblMovimentos.setFont(fonteInfo);
        lblTempo.setFont(fonteInfo);

        barraInfo.getChildren().addAll(lblMovimentos, lblTempo);
        this.setTop(barraInfo); // Adiciona ao topo do BorderPane

        // --- Grid do Tabuleiro ---
        gridTabuleiro = new GridPane();
        gridTabuleiro.setAlignment(Pos.CENTER);
        gridTabuleiro.setHgap(5);
        gridTabuleiro.setVgap(5);
        this.setCenter(gridTabuleiro); // Adiciona ao centro
    }

    private void inicializarTabuleiro(int dimensao) {
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                Rectangle rect = new Rectangle(80, 80);
                rect.setStroke(Color.BLACK);
                
                final int linha = i;
                final int coluna = j;

                rect.setOnMouseClicked(event -> {
                    // 1. Processa a jogada no controller
                    game.processarJogada(linha, coluna);
                    
                    // 2. Atualiza o visual (tabuleiro e contadores)
                    atualizar(); 

                    // 3. CORREÇÃO: Verifica a vitória após cada clique
                    if (game.isVitoria()) {
                        pararCronometro();
                        exibirMensagemVitoria();
                    }
                });

                celulasVisuais[i][j] = rect;
                gridTabuleiro.add(rect, j, i);
            }
        }
        atualizar(); // Desenha o estado inicial
    }

    // Método chamado periodicamente para atualizar o cronômetro
    private void iniciarCronometro() {
        // Usamos 'this.timeline' para referenciar o atributo da classe,
        // e não criar uma variável local nova.
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            lblTempo.setText("Tempo: " + game.getTempoDecorrrido() + "s");
        }));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.timeline.play();
    }

    // Novo método para parar o tempo ao vencer
    private void pararCronometro() {
        if (this.timeline != null) {
            this.timeline.stop();
        }
    }

    // Novo método para exibir o pop-up de vitória
    private void exibirMensagemVitoria() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Parabéns!");
        alert.setHeaderText("Você Venceu!");
        
        String estatisticas = String.format(
            "Você apagou todas as luzes!\n\nMovimentos: %d\nTempo: %d segundos", 
            game.getMovimentos(), 
            game.getTempoDecorrrido()
        );
        
        alert.setContentText(estatisticas);
        alert.showAndWait();
    }

    public void atualizar() {
        // Atualiza Tabuleiro
        Tabuleiro tabuleiro = game.getTabuleiro();
        for (int i = 0; i < tabuleiro.getDimensao(); i++) {
            for (int j = 0; j < tabuleiro.getDimensao(); j++) {
                if (tabuleiro.getCelula(i, j).isLigada()) {
                    celulasVisuais[i][j].setFill(Color.YELLOW);
                } else {
                    celulasVisuais[i][j].setFill(Color.DARKSLATEGRAY);
                }
            }
        }
        // Atualiza Movimentos
        lblMovimentos.setText("Movimentos: " + game.getMovimentos());
    }
}
// Em: src/main/java/projeto_final/view/PainelJogo.java
package projeto_final.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import projeto_final.controller.Game;
import projeto_final.exceptions.MovimentoInvalidoException;
import projeto_final.interfaces.Desenhavel;
import projeto_final.interfaces.EventListener;
import projeto_final.model.Tabuleiro;

/**
 * Classe que representa o painel principal do jogo Lights Out.
 * <p>
 * Esta classe estende {@code BorderPane} do JavaFX e implementa as interfaces
 * {@code Desenhavel} e {@code EventListener}. Ela renderiza o tabuleiro do jogo,
 * exibe informações como tempo decorrido e número de movimentos, e processa
 * os cliques do jogador nas células.
 * </p>
 * <p>
 * O painel atualiza automaticamente a visualização do tabuleiro e mantém
 * um cronômetro que atualiza o tempo decorrido a cada segundo.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.interfaces.Desenhavel
 * @see projeto_final.interfaces.EventListener
 * @see projeto_final.controller.Game
 * @see projeto_final.model.Tabuleiro
 */
// Mudamos de GridPane para BorderPane para acomodar a barra de info
// Implementa Desenhavel e EventListener conforme o diagrama
public class PainelJogo extends BorderPane implements Desenhavel, EventListener { 
    private final Game game;
    private final Rectangle[][] celulasVisuais;
    private Label lblTempo;
    private Label lblMovimentos;
    private Label lblJogador;
    private GridPane gridTabuleiro;
    private Timeline timeline; // Atributo de classe para controle do tempo
    private Button btnSalvar;
    private Button btnVoltarMenu;
    private Runnable callbackVoltarMenu; // Callback para voltar ao menu

    /**
     * Construtor que cria o painel de jogo.
     * 
     * @param game Instância do controlador do jogo
     */
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

        // Obtém o nome do jogador ou usa padrão
        String nomeJogador = "Jogador";
        if (game.getJogador() != null && game.getJogador().getNome() != null) {
            nomeJogador = game.getJogador().getNome();
        }
        
        lblJogador = new Label("Jogador: " + nomeJogador);
        lblMovimentos = new Label("Movimentos: 0");
        lblTempo = new Label("Tempo: 0s");
        
        // Estilização básica
        Font fonteInfo = new Font("Arial", 16);
        lblJogador.setFont(fonteInfo);
        lblJogador.setStyle("-fx-font-weight: bold;");
        lblMovimentos.setFont(fonteInfo);
        lblTempo.setFont(fonteInfo);

        barraInfo.getChildren().addAll(lblJogador, lblMovimentos, lblTempo);
        this.setTop(barraInfo); // Adiciona ao topo do BorderPane

        // --- Grid do Tabuleiro ---
        gridTabuleiro = new GridPane();
        gridTabuleiro.setAlignment(Pos.CENTER);
        gridTabuleiro.setHgap(5);
        gridTabuleiro.setVgap(5);
        this.setCenter(gridTabuleiro); // Adiciona ao centro
        
        // --- Barra Inferior (Botões) ---
        HBox barraBotoes = new HBox(10);
        barraBotoes.setAlignment(Pos.CENTER);
        barraBotoes.setPadding(new Insets(10));
        barraBotoes.setStyle("-fx-background-color: #EEEEEE;");
        
        btnSalvar = new Button("Salvar Jogo");
        btnSalvar.setOnAction(event -> salvarJogo());
        
        btnVoltarMenu = new Button("Voltar ao Menu");
        btnVoltarMenu.setOnAction(event -> voltarAoMenu());
        
        barraBotoes.getChildren().addAll(btnSalvar, btnVoltarMenu);
        this.setBottom(barraBotoes); // Adiciona ao rodapé do BorderPane
    }
    
    /**
     * Salva o jogo atual.
     */
    private void salvarJogo() {
        try {
            game.salvarJogo();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Jogo Salvo");
            alert.setHeaderText("Sucesso!");
            alert.setContentText("O jogo foi salvo com sucesso.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro ao Salvar");
            alert.setHeaderText("Erro");
            alert.setContentText("Não foi possível salvar o jogo.\n\n" +
                               "Detalhes: " + e.getMessage());
            alert.showAndWait();
        }
    }
    
    /**
     * Define o callback para voltar ao menu.
     * 
     * @param callback Callback a ser executado quando o usuário quiser voltar ao menu
     */
    public void setCallbackVoltarMenu(Runnable callback) {
        this.callbackVoltarMenu = callback;
    }
    
    /**
     * Volta ao menu principal.
     * <p>
     * Pede confirmação ao usuário e executa o callback se fornecido.
     * </p>
     */
    private void voltarAoMenu() {
        // Para o cronômetro
        pararCronometro();
        
        // Pede confirmação
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Voltar ao Menu");
        alert.setHeaderText("Deseja realmente voltar ao menu?");
        alert.setContentText("O progresso atual será perdido se não for salvo.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                // Executa o callback se fornecido
                if (callbackVoltarMenu != null) {
                    callbackVoltarMenu.run();
                }
            }
        });
    }

    private void inicializarTabuleiro(int dimensao) {
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                Rectangle rect = new Rectangle(80, 80);
                rect.setStroke(Color.BLACK);
                
                final int linha = i;
                final int coluna = j;

                rect.setOnMouseClicked(event -> {
                    try {
                        // 1. Processa a jogada no controller
                        game.processarJogada(linha, coluna);
                        
                        // 2. Atualiza o visual (tabuleiro e contadores)
                        atualizar(); 

                        // 3. Verifica a vitória após cada clique
                        if (game.isVitoria()) {
                            pararCronometro();
                            exibirMensagemVitoria();
                        }
                    } catch (MovimentoInvalidoException e) {
                        // Exibe mensagem de erro ao usuário
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Movimento Inválido");
                        alert.setHeaderText("Não foi possível realizar o movimento");
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
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
    
    // Implementação da interface Desenhavel
    @Override
    public void desenhar() {
        atualizar();
    }
    
    // Implementação da interface EventListener
    @Override
    public void processarEvento(Object evento) {
        if (evento instanceof String) {
            String tipoEvento = (String) evento;
            switch (tipoEvento) {
                case "ATUALIZAR":
                    atualizar();
                    break;
                case "VITORIA":
                    pararCronometro();
                    exibirMensagemVitoria();
                    break;
                default:
                    // Processa outros eventos se necessário
                    break;
            }
        }
    }
    
    // Método exibir() para compatibilidade com ComponenteGrafico
    public void exibir() {
        desenhar();
    }
}
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
    private Rectangle[][] celulasVisuais;
    private Label lblTempo;
    private Label lblMovimentos;
    private Label lblPontuacao;
    private Label lblJogador;
    private Label lblDificuldade;
    private GridPane gridTabuleiro;
    private Timeline timeline; // Atributo de classe para controle do tempo
    private Button btnSalvar;
    private Button btnReiniciar;
    private Button btnVoltarMenu;
    private Runnable callbackVoltarMenu; // Callback para voltar ao menu

    /**
     * Construtor que cria o painel de jogo.
     * 
     * @param game Instância do controlador do jogo
     */
    public PainelJogo(Game game) {
        this.game = game;
        inicializarLayout();
        inicializarTabuleiro();
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
        
            // Obtém informação da dificuldade e turno atual
            String infoDificuldade = "Turno: -";
            if (game.getDificuldade() != null) {
                int turnoAtual = game.getTurnoAtual();
                int totalTurnos = game.getTotalTurnos();
                infoDificuldade = String.format("Turno %d/%d - %s", turnoAtual, totalTurnos, game.getDificuldade().getNome());
            }
        
        lblJogador = new Label("Jogador: " + nomeJogador);
        lblDificuldade = new Label(infoDificuldade);
        lblMovimentos = new Label("Movimentos: 0");
        lblTempo = new Label("Tempo: 0s");
        lblPontuacao = new Label("Pontos: 0");
        
        // Estilização básica
        Font fonteInfo = new Font("Arial", 16);
        lblJogador.setFont(fonteInfo);
        lblJogador.setStyle("-fx-font-weight: bold;");
        lblDificuldade.setFont(fonteInfo);
        lblDificuldade.setStyle("-fx-font-weight: bold; -fx-text-fill: #0066CC;");
        lblMovimentos.setFont(fonteInfo);
        lblTempo.setFont(fonteInfo);
        lblPontuacao.setFont(fonteInfo);
        lblPontuacao.setStyle("-fx-font-weight: bold; -fx-text-fill: #009900;");

        barraInfo.getChildren().addAll(lblJogador, lblDificuldade, lblMovimentos, lblTempo, lblPontuacao);
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
        
        btnReiniciar = new Button("Reiniciar Tabuleiro");
        btnReiniciar.setOnAction(event -> reiniciarTabuleiro());
        
        btnVoltarMenu = new Button("Voltar ao Menu");
        btnVoltarMenu.setOnAction(event -> voltarAoMenu());
        
        barraBotoes.getChildren().addAll(btnSalvar, btnReiniciar, btnVoltarMenu);
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
     * Reinicia o tabuleiro atual para o estado inicial.
     * <p>
     * Reseta o tabuleiro, movimentos e tempo, mas mantém o mesmo padrão inicial.
     * </p>
     */
    private void reiniciarTabuleiro() {
        // Pede confirmação ao usuário
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Reiniciar Tabuleiro");
        alert.setHeaderText("Deseja realmente reiniciar o tabuleiro?");
        alert.setContentText("O tabuleiro será resetado para o estado inicial.\n" +
                           "Movimentos e tempo serão zerados.");
        
        alert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                // Reinicia o jogo
                game.reiniciar();
                
                // Reinicia o cronômetro
                pararCronometro();
                iniciarCronometro();
                
                // Atualiza a visualização
                atualizar();
                
                // Mensagem de confirmação
                Alert confirmacao = new Alert(AlertType.INFORMATION);
                confirmacao.setTitle("Tabuleiro Reiniciado");
                confirmacao.setHeaderText("Sucesso!");
                confirmacao.setContentText("O tabuleiro foi reiniciado com sucesso.");
                confirmacao.showAndWait();
            }
        });
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

    private void inicializarTabuleiro() {
        criarTabuleiroVisual();
        atualizar(); // Desenha o estado inicial
    }
    
    /**
     * Cria ou recria o tabuleiro visual baseado na dimensão atual.
     */
    private void criarTabuleiroVisual() {
        // Limpa o grid atual
        gridTabuleiro.getChildren().clear();
        
        // Obtém a dimensão atual do tabuleiro
        int dimensao = game.getTabuleiro().getDimensao();
        
        // Cria novo array de células visuais
        celulasVisuais = new Rectangle[dimensao][dimensao];
        
        // Calcula tamanho das células baseado na dimensão (ajusta para caber melhor)
        // Aumentado para melhor visualização: células maiores em tabuleiros maiores
        double tamanhoCelula = Math.min(100, 600.0 / dimensao);
        
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                Rectangle rect = new Rectangle(tamanhoCelula, tamanhoCelula);
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
                            // A pontuação já foi calculada e somada em processarVitoria()
                            // Atualiza a interface imediatamente para mostrar a nova pontuação
                            atualizar();
                            exibirMensagemVitoria();
                            // Atualiza novamente após a mensagem para garantir que está sincronizado
                            atualizar();
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
    }

    // Método chamado periodicamente para atualizar o cronômetro
    private void iniciarCronometro() {
        // Usamos 'this.timeline' para referenciar o atributo da classe,
        // e não criar uma variável local nova.
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            lblTempo.setText("Tempo: " + game.getTempoDecorrrido() + "s");
            // Atualiza também a pontuação periodicamente para garantir que está atualizada
            int pontuacao = game.getPontuacao();
            lblPontuacao.setText(String.format("Pontos: %d", pontuacao));
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
        // Verifica se completou todos os turnos
        // IMPORTANTE: avancarParaProximoTurno() já foi chamado em processarVitoria()
        int turnoAtual = game.getTurnoAtual();
        int totalTurnos = game.getTotalTurnos();
        boolean completouTodos = game.completouTodosTurnos();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        
        if (completouTodos) {
            // Mensagem especial para completar todos os turnos
            alert.setTitle("PARABÉNS!");
            
            // Obtém a pontuação do jogo
            int pontuacao = game.getPontuacao();
            String nomeDificuldade = game.getDificuldade() != null ? game.getDificuldade().getNome() : "";
            boolean bateuRecorde = game.bateuRecorde();
            
            String mensagem;
            if (bateuRecorde) {
                // Mensagem especial para recorde
                alert.setHeaderText("🏆 NOVO RECORDE! 🏆");
                mensagem = String.format(
                    "🎉 PARABÉNS! 🎉\n\n" +
                    "Você completou todos os 3 turnos na dificuldade %s!\n\n" +
                    "🌟 VOCÊ BATEU SEU RECORDE! 🌟\n\n" +
                    "Pontuação Final: %d pontos\n\n" +
                    "Você é um verdadeiro mestre do Lights Out!",
                    nomeDificuldade,
                    pontuacao
                );
            } else {
                alert.setHeaderText("Você Completou Todos os Turnos!");
                mensagem = String.format(
                    "🎉 INCRÍVEL! 🎉\n\n" +
                    "Você completou todos os 3 turnos na dificuldade %s!\n\n" +
                    "Você é um verdadeiro mestre do Lights Out!\n\n" +
                    "Pontuação Final: %d pontos",
                    nomeDificuldade,
                    pontuacao
                );
            }
            
            alert.setContentText(mensagem);
            alert.showAndWait();
            
            // Atualiza a pontuação na interface antes de voltar ao menu
            atualizar();
            
            // Volta ao menu após completar tudo
            if (callbackVoltarMenu != null) {
                callbackVoltarMenu.run();
            }
        } else {
            // Mensagem normal de vitória com progressão de turnos
            alert.setTitle("Parabéns!");
            
            // Obtém informações do turno que acabou de ser vencido
            // (já avançou para o próximo, então usa informações salvas)
            int turnoAnterior = turnoAtual - 1; // O turno que acabou de ser vencido
            
            int movimentos = game.getMovimentosUltimoTurno();
            long tempo = game.getTempoUltimoTurno();
            int pontuacao = game.getPontuacao();
            String nomeDificuldade = game.getDificuldade() != null ? game.getDificuldade().getNome() : "";
            
            // Verifica se ainda há turnos para completar
            if (turnoAtual <= totalTurnos) {
                // Ainda há mais turnos
                alert.setHeaderText("Turno " + turnoAnterior + " Completado!");
                
                String estatisticas = String.format(
                    "Você completou o turno %d de %d!\n\n" +
                    "Dificuldade: %s\n" +
                    "Movimentos: %d\n" +
                    "Tempo: %d segundos\n" +
                    "Pontuação do Jogo: %d pontos\n\n" +
                    "Avançando para o próximo turno...",
                    turnoAnterior,
                    totalTurnos,
                    nomeDificuldade,
                    movimentos, 
                    tempo,
                    pontuacao
                );
                
                alert.setContentText(estatisticas);
                alert.showAndWait();
                
                // Atualiza o tabuleiro para o próximo turno
                atualizarTabuleiroParaProximoTurno();
                
                // Reinicia o cronômetro para o novo turno
                iniciarCronometro();
                
                // Força atualização da pontuação após avançar de turno
                atualizar();
            } else {
                // Último turno (não deveria chegar aqui, mas por segurança)
                alert.setHeaderText("Você Venceu!");
                
                String estatisticas = String.format(
                    "Você apagou todas as luzes!\n\nMovimentos: %d\nTempo: %d segundos", 
                    movimentos, 
                    tempo
                );
                
                alert.setContentText(estatisticas);
                alert.showAndWait();
            }
        }
    }
    
    /**
     * Atualiza o tabuleiro visual quando avança para próximo turno.
     */
    private void atualizarTabuleiroParaProximoTurno() {
        // Recria o tabuleiro visual (mesma dimensão, novo tabuleiro)
        criarTabuleiroVisual();
        
        // Reinicia o cronômetro
        pararCronometro();
        iniciarCronometro();
        
        // Atualiza a informação de turno e dificuldade
        if (game.getDificuldade() != null) {
            int turnoAtual = game.getTurnoAtual();
            int totalTurnos = game.getTotalTurnos();
            lblDificuldade.setText(String.format("Turno %d/%d - %s", turnoAtual, totalTurnos, game.getDificuldade().getNome()));
        }
        
        // Atualiza a visualização
        atualizar();
    }

    public void atualizar() {
        // Atualiza Tabuleiro
        Tabuleiro tabuleiro = game.getTabuleiro();
        if (tabuleiro != null) {
            int dimensao = tabuleiro.getDimensao();
            
            // Se a dimensão mudou, recria o tabuleiro visual
            if (celulasVisuais == null || celulasVisuais.length != dimensao || 
                (celulasVisuais.length > 0 && celulasVisuais[0].length != dimensao)) {
                criarTabuleiroVisual();
            }
            
            // Atualiza as cores das células
            if (celulasVisuais != null) {
                for (int i = 0; i < dimensao && i < celulasVisuais.length; i++) {
                    for (int j = 0; j < dimensao && j < celulasVisuais[i].length; j++) {
                        if (celulasVisuais[i][j] != null) {
                            if (tabuleiro.getCelula(i, j).isLigada()) {
                                celulasVisuais[i][j].setFill(Color.YELLOW);
                            } else {
                                celulasVisuais[i][j].setFill(Color.DARKSLATEGRAY);
                            }
                        }
                    }
                }
            }
        }
        // Atualiza Movimentos
        lblMovimentos.setText("Movimentos: " + game.getMovimentos());
        
        // Atualiza Pontuação - mostra a pontuação do jogo
        int pontuacao = game.getPontuacao();
        if (lblPontuacao != null) {
            String textoPontuacao = String.format("Pontos: %d", pontuacao);
            lblPontuacao.setText(textoPontuacao);
            // Força atualização do label
            lblPontuacao.requestLayout();
        }
        
        // Atualiza informação de turno e dificuldade
        if (game.getDificuldade() != null) {
            int turnoAtual = game.getTurnoAtual();
            int totalTurnos = game.getTotalTurnos();
            lblDificuldade.setText(String.format("Turno %d/%d - %s", turnoAtual, totalTurnos, game.getDificuldade().getNome()));
        }
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
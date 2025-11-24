package projeto_final.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import projeto_final.abstracts.ComponenteGrafico;
import projeto_final.interfaces.Desenhavel;
import projeto_final.interfaces.EventListener;

/**
 * Classe que representa o menu principal do jogo.
 * <p>
 * Esta classe herda de {@code ComponenteGrafico} e implementa as interfaces
 * {@code Desenhavel} e {@code EventListener}. Ela fornece a interface gráfica
 * inicial do jogo com opções para iniciar um novo jogo, carregar um jogo
 * salvo ou sair da aplicação.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.abstracts.ComponenteGrafico
 * @see projeto_final.interfaces.Desenhavel
 * @see projeto_final.interfaces.EventListener
 */
public class MenuPrincipal extends ComponenteGrafico implements Desenhavel, EventListener {
    /** Layout principal do menu */
    private VBox layout;
    
    /** Botão para iniciar um novo jogo */
    private Button btnNovoJogo;
    
    /** Botão para carregar um jogo salvo */
    private Button btnCarregarJogo;
    
    /** Botão para ver pontuações */
    private Button btnVerPontuacoes;
    
    /** Botão para sair da aplicação */
    private Button btnSair;
    
    /**
     * Construtor que cria o menu principal.
     * <p>
     * Inicializa o layout e os componentes do menu.
     * </p>
     */
    public MenuPrincipal() {
        this.layout = new VBox(25);
        this.layout.setAlignment(Pos.CENTER);
        this.layout.setPadding(new Insets(40, 50, 40, 50));
        this.layout.setBackground(new Background(new BackgroundFill(
            Color.web("#2C3E50"), 
            CornerRadii.EMPTY, 
            Insets.EMPTY
        )));
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Título do jogo
        Label lblTitulo = new Label("💡 LIGHTS OUT");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        lblTitulo.setTextFill(Color.web("#ECF0F1"));
        lblTitulo.setPadding(new Insets(0, 0, 30, 0));
        
        // Subtítulo
        Label lblSubtitulo = new Label("Desafie sua mente!");
        lblSubtitulo.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        lblSubtitulo.setTextFill(Color.web("#BDC3C7"));
        lblSubtitulo.setPadding(new Insets(0, 0, 40, 0));
        
        // Botão Novo Jogo
        btnNovoJogo = criarBotaoEstilizado("🎮 Novo Jogo", "#27AE60", "#229954");
        btnNovoJogo.setPrefWidth(250);
        btnNovoJogo.setPrefHeight(50);
        
        // Botão Carregar Jogo
        btnCarregarJogo = criarBotaoEstilizado("📂 Carregar Jogo", "#3498DB", "#2980B9");
        btnCarregarJogo.setPrefWidth(250);
        btnCarregarJogo.setPrefHeight(50);
        
        // Botão Ver Pontuações
        btnVerPontuacoes = criarBotaoEstilizado("🏆 Ver Pontuações", "#F39C12", "#E67E22");
        btnVerPontuacoes.setPrefWidth(250);
        btnVerPontuacoes.setPrefHeight(50);
        
        // Botão Sair
        btnSair = criarBotaoEstilizado("🚪 Sair", "#E74C3C", "#C0392B");
        btnSair.setPrefWidth(250);
        btnSair.setPrefHeight(50);
        
        layout.getChildren().addAll(
            lblTitulo,
            lblSubtitulo,
            btnNovoJogo,
            btnCarregarJogo,
            btnVerPontuacoes,
            btnSair
        );
    }
    
    /**
     * Cria um botão estilizado com efeito hover.
     * 
     * @param texto Texto do botão
     * @param corNormal Cor de fundo normal
     * @param corHover Cor de fundo no hover
     * @return Botão estilizado
     */
    private Button criarBotaoEstilizado(String texto, String corNormal, String corHover) {
        Button botao = new Button(texto);
        botao.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        botao.setTextFill(Color.WHITE);
        botao.setStyle(String.format(
            "-fx-background-color: %s; " +
            "-fx-background-radius: 10; " +
            "-fx-border-radius: 10; " +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2); " +
            "-fx-cursor: hand;",
            corNormal
        ));
        
        // Efeito hover
        botao.setOnMouseEntered(e -> {
            botao.setStyle(String.format(
                "-fx-background-color: %s; " +
                "-fx-background-radius: 10; " +
                "-fx-border-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 8, 0, 0, 3); " +
                "-fx-cursor: hand; " +
                "-fx-scale-x: 1.05; " +
                "-fx-scale-y: 1.05;",
                corHover
            ));
        });
        
        botao.setOnMouseExited(e -> {
            botao.setStyle(String.format(
                "-fx-background-color: %s; " +
                "-fx-background-radius: 10; " +
                "-fx-border-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2); " +
                "-fx-cursor: hand; " +
                "-fx-scale-x: 1.0; " +
                "-fx-scale-y: 1.0;",
                corNormal
            ));
        });
        
        // Efeito de clique
        botao.setOnMousePressed(e -> {
            botao.setStyle(String.format(
                "-fx-background-color: %s; " +
                "-fx-background-radius: 10; " +
                "-fx-border-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 1); " +
                "-fx-cursor: hand; " +
                "-fx-scale-x: 0.98; " +
                "-fx-scale-y: 0.98;",
                corHover
            ));
        });
        
        botao.setOnMouseReleased(e -> {
            botao.setStyle(String.format(
                "-fx-background-color: %s; " +
                "-fx-background-radius: 10; " +
                "-fx-border-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2); " +
                "-fx-cursor: hand; " +
                "-fx-scale-x: 1.0; " +
                "-fx-scale-y: 1.0;",
                corNormal
            ));
        });
        
        return botao;
    }
    
    @Override
    public void exibir() {
        // O layout já está configurado, este método pode ser usado para mostrar na tela
    }
    
    @Override
    public void atualizar() {
        // Atualiza o estado visual do menu se necessário
    }
    
    @Override
    public void desenhar() {
        // Desenha o menu na interface
        exibir();
    }
    
    @Override
    public void processarEvento(Object evento) {
        // Processa eventos do menu (cliques em botões, etc.)
        if (evento instanceof String) {
            String tipoEvento = (String) evento;
            switch (tipoEvento) {
                case "NOVO_JOGO":
                    // Lógica para novo jogo
                    break;
                case "CARREGAR_JOGO":
                    // Lógica para carregar jogo
                    break;
                case "SAIR":
                    // Lógica para sair
                    break;
            }
        }
    }
    
    /**
     * Retorna o layout principal do menu.
     * 
     * @return Layout VBox do menu
     */
    public VBox getLayout() {
        return layout;
    }
    
    /**
     * Retorna o botão de novo jogo.
     * 
     * @return Botão de novo jogo
     */
    public Button getBtnNovoJogo() {
        return btnNovoJogo;
    }
    
    /**
     * Retorna o botão de carregar jogo.
     * 
     * @return Botão de carregar jogo
     */
    public Button getBtnCarregarJogo() {
        return btnCarregarJogo;
    }
    
    /**
     * Retorna o botão de ver pontuações.
     * 
     * @return Botão de ver pontuações
     */
    public Button getBtnVerPontuacoes() {
        return btnVerPontuacoes;
    }
    
    /**
     * Retorna o botão de sair.
     * 
     * @return Botão de sair
     */
    public Button getBtnSair() {
        return btnSair;
    }
}


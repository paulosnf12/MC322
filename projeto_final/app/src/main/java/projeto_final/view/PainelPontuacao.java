package projeto_final.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import projeto_final.abstracts.ComponenteGrafico;
import projeto_final.model.GerenciadorPontuacoes;
import projeto_final.model.Jogador;
import projeto_final.model.PontuacaoRecord;

/**
 * Classe que representa o painel de pontuação do jogo.
 * <p>
 * Esta classe herda de {@code ComponenteGrafico} e implementa a interface
 * {@code Desenhavel}. Ela exibe as estatísticas do jogador, incluindo
 * pontuação total, número de partidas jogadas e recordes por dificuldade.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.abstracts.ComponenteGrafico
 * @see projeto_final.interfaces.Desenhavel
 * @see projeto_final.model.Jogador
 */
public class PainelPontuacao extends ComponenteGrafico {
    private VBox layout;
    private VBox conteudoPontuacoes;
    private ScrollPane scrollPane;
    private Label lblTitulo;
    private VBox containerEstatisticas;
    private Button btnVoltar;
    @SuppressWarnings("unused")
    private Jogador jogador; // Mantido para possível uso futuro
    private Runnable callbackVoltar;
    
    /**
     * Construtor que cria o painel de pontuação.
     * <p>
     * Se um jogador for fornecido, exibe suas estatísticas pessoais.
     * Caso contrário, exibe todas as pontuações salvas.
     * </p>
     * 
     * @param jogador Jogador cujas estatísticas serão exibidas (pode ser null)
     * @param callbackVoltar Callback para voltar ao menu principal
     */
    public PainelPontuacao(Jogador jogador, Runnable callbackVoltar) {
        this.jogador = jogador;
        this.callbackVoltar = callbackVoltar;
        this.layout = new VBox(15);
        this.layout.setAlignment(Pos.CENTER);
        this.layout.setPadding(new Insets(20));
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        // Título estilizado
        lblTitulo = new Label("🏆 RECORDES DOS JOGADORES 🏆");
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        lblTitulo.setTextFill(Color.web("#2C3E50"));
        lblTitulo.setPadding(new Insets(0, 0, 20, 0));
        
        // Área de scroll para as pontuações
        conteudoPontuacoes = new VBox(8);
        conteudoPontuacoes.setAlignment(Pos.TOP_CENTER);
        conteudoPontuacoes.setPadding(new Insets(15));
        
        scrollPane = new ScrollPane(conteudoPontuacoes);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(450);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background: #F5F5F5; -fx-border-color: #BDC3C7; -fx-border-radius: 5;");
        
        // Container para estatísticas
        containerEstatisticas = new VBox(5);
        containerEstatisticas.setAlignment(Pos.CENTER);
        containerEstatisticas.setStyle("-fx-background-color: #ECF0F1; -fx-padding: 10; -fx-background-radius: 5;");
        containerEstatisticas.setPadding(new Insets(10));
        
        // Botão estilizado
        btnVoltar = new Button("← Voltar ao Menu");
        btnVoltar.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; -fx-font-size: 14px; " +
                          "-fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5;");
        btnVoltar.setOnAction(event -> {
            if (callbackVoltar != null) {
                callbackVoltar.run();
            }
        });
        btnVoltar.setOnMouseEntered(e -> btnVoltar.setStyle("-fx-background-color: #2980B9; -fx-text-fill: white; " +
                                                           "-fx-font-size: 14px; -fx-font-weight: bold; " +
                                                           "-fx-padding: 10 20; -fx-background-radius: 5;"));
        btnVoltar.setOnMouseExited(e -> btnVoltar.setStyle("-fx-background-color: #3498DB; -fx-text-fill: white; " +
                                                          "-fx-font-size: 14px; -fx-font-weight: bold; " +
                                                          "-fx-padding: 10 20; -fx-background-radius: 5;"));
        
        layout.getChildren().addAll(
            lblTitulo,
            scrollPane,
            containerEstatisticas,
            btnVoltar
        );
        
        carregarPontuacoes();
    }
    
    @Override
    public void exibir() {
        // O layout já está configurado e inicializado no construtor
        // Este método é chamado quando o componente precisa ser exibido
        // As pontuações são carregadas na inicialização
    }
    
    /**
     * Carrega e exibe as pontuações do arquivo, agrupadas por dificuldade.
     */
    private void carregarPontuacoes() {
        try {
            List<PontuacaoRecord> todasPontuacoes = GerenciadorPontuacoes.carregarPontuacoes();
            conteudoPontuacoes.getChildren().clear();
            
            if (todasPontuacoes.isEmpty()) {
                Label lblVazio = new Label("📭 Nenhuma pontuação registrada ainda.\n\nComplete uma partida para aparecer aqui!");
                lblVazio.setStyle("-fx-font-style: italic; -fx-font-size: 16px; -fx-text-fill: #7F8C8D;");
                lblVazio.setAlignment(Pos.CENTER);
                lblVazio.setPadding(new Insets(50, 0, 0, 0));
                conteudoPontuacoes.getChildren().add(lblVazio);
            } else {
                // Agrupa pontuações por dificuldade
                List<PontuacaoRecord> pontuacoesFacil = filtrarPorDificuldade(todasPontuacoes, "Fácil");
                List<PontuacaoRecord> pontuacoesMedio = filtrarPorDificuldade(todasPontuacoes, "Médio");
                List<PontuacaoRecord> pontuacoesDificil = filtrarPorDificuldade(todasPontuacoes, "Difícil");
                
                // Exibe seção de Fácil
                if (!pontuacoesFacil.isEmpty()) {
                    exibirSecaoDificuldade("🟢 FÁCIL", pontuacoesFacil, "#27AE60");
                }
                
                // Exibe seção de Médio
                if (!pontuacoesMedio.isEmpty()) {
                    exibirSecaoDificuldade("🟡 MÉDIO", pontuacoesMedio, "#F39C12");
                }
                
                // Exibe seção de Difícil
                if (!pontuacoesDificil.isEmpty()) {
                    exibirSecaoDificuldade("🔴 DIFÍCIL", pontuacoesDificil, "#E74C3C");
                }
            }
            
            // Atualiza estatísticas gerais
            atualizarEstatisticas(todasPontuacoes);
            
        } catch (IOException e) {
            Label lblErro = new Label("❌ Erro ao carregar pontuações: " + e.getMessage());
            lblErro.setStyle("-fx-text-fill: #E74C3C; -fx-font-size: 14px; -fx-font-weight: bold;");
            lblErro.setPadding(new Insets(20));
            conteudoPontuacoes.getChildren().add(lblErro);
        }
    }
    
    /**
     * Filtra pontuações por dificuldade.
     * 
     * @param pontuacoes Lista completa de pontuações
     * @param dificuldade Nome da dificuldade a filtrar
     * @return Lista filtrada e ordenada por pontuação (maior primeiro)
     */
    private List<PontuacaoRecord> filtrarPorDificuldade(List<PontuacaoRecord> pontuacoes, String dificuldade) {
        List<PontuacaoRecord> filtradas = new ArrayList<>();
        for (PontuacaoRecord record : pontuacoes) {
            if (record.getDificuldade().equalsIgnoreCase(dificuldade)) {
                filtradas.add(record);
            }
        }
        // Ordena por pontuação (maior primeiro)
        Collections.sort(filtradas, new Comparator<PontuacaoRecord>() {
            @Override
            public int compare(PontuacaoRecord r1, PontuacaoRecord r2) {
                return Integer.compare(r2.getPontuacao(), r1.getPontuacao());
            }
        });
        return filtradas;
    }
    
    /**
     * Exibe uma seção de pontuações para uma dificuldade específica.
     * 
     * @param titulo Título da seção
     * @param pontuacoes Lista de pontuações da dificuldade
     * @param corCorpo Cor de destaque para a seção
     */
    private void exibirSecaoDificuldade(String titulo, List<PontuacaoRecord> pontuacoes, String corCorpo) {
        // Título da seção
        Label lblTituloSecao = new Label(titulo);
        lblTituloSecao.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        lblTituloSecao.setTextFill(Color.web(corCorpo));
        lblTituloSecao.setPadding(new Insets(20, 0, 10, 0));
        lblTituloSecao.setStyle("-fx-background-color: " + corCorpo + "20; -fx-background-radius: 5; -fx-padding: 10;");
        conteudoPontuacoes.getChildren().add(lblTituloSecao);
        
        // Cabeçalho da tabela
        GridPane cabecalho = criarCabecalhoTabela();
        conteudoPontuacoes.getChildren().add(cabecalho);
        
        // Exibe cada pontuação da dificuldade
        int posicao = 1;
        for (PontuacaoRecord record : pontuacoes) {
            GridPane linhaPontuacao = criarLinhaPontuacao(record, posicao);
            conteudoPontuacoes.getChildren().add(linhaPontuacao);
            posicao++;
        }
        
        // Espaçamento entre seções
        Label espacamento = new Label("");
        espacamento.setPadding(new Insets(15, 0, 0, 0));
        conteudoPontuacoes.getChildren().add(espacamento);
    }
    
    /**
     * Cria o cabeçalho da tabela de pontuações.
     * 
     * @return GridPane com o cabeçalho
     */
    private GridPane criarCabecalhoTabela() {
        GridPane cabecalho = new GridPane();
        cabecalho.setHgap(15);
        cabecalho.setPadding(new Insets(10, 15, 10, 15));
        cabecalho.setStyle("-fx-background-color: #34495E; -fx-background-radius: 5;");
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(100);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPrefWidth(120);
        
        cabecalho.getColumnConstraints().addAll(col1, col2, col3, col4);
        
        Label lblPos = new Label("#");
        Label lblJogador = new Label("Jogador");
        Label lblTempo = new Label("Tempo");
        Label lblPontos = new Label("Pontuação");
        
        for (Label lbl : new Label[]{lblPos, lblJogador, lblTempo, lblPontos}) {
            lbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            lbl.setTextFill(Color.WHITE);
        }
        
        cabecalho.add(lblPos, 0, 0);
        cabecalho.add(lblJogador, 1, 0);
        cabecalho.add(lblTempo, 2, 0);
        cabecalho.add(lblPontos, 3, 0);
        
        return cabecalho;
    }
    
    /**
     * Cria uma linha da tabela de pontuações.
     * 
     * @param record Registro de pontuação
     * @param posicao Posição no ranking
     * @return GridPane com a linha formatada
     */
    private GridPane criarLinhaPontuacao(PontuacaoRecord record, int posicao) {
        GridPane linha = new GridPane();
        linha.setHgap(15);
        linha.setPadding(new Insets(12, 15, 12, 15));
        
        // Cores e estilos baseados na posição
        String corFundo = "#FFFFFF";
        String corTexto = "#2C3E50";
        String emoji = "";
        FontWeight pesoFonte = FontWeight.NORMAL;
        
        if (posicao == 1) {
            corFundo = "#FFF9C4";
            corTexto = "#F39C12";
            emoji = "🥇";
            pesoFonte = FontWeight.BOLD;
        } else if (posicao == 2) {
            corFundo = "#E8E8E8";
            corTexto = "#95A5A6";
            emoji = "🥈";
            pesoFonte = FontWeight.BOLD;
        } else if (posicao == 3) {
            corFundo = "#F5E6D3";
            corTexto = "#D68910";
            emoji = "🥉";
            pesoFonte = FontWeight.BOLD;
        }
        
        linha.setStyle(String.format("-fx-background-color: %s; -fx-background-radius: 5;", corFundo));
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(100);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPrefWidth(120);
        
        linha.getColumnConstraints().addAll(col1, col2, col3, col4);
        
        Label lblPos = new Label(emoji + " " + posicao);
        Label lblJogador = new Label(record.getNomeJogador());
        Label lblTempo = new Label(formatarTempo(record.getTempoTotal()));
        Label lblPontos = new Label(String.format("%,d", record.getPontuacao()));
        
        for (Label lbl : new Label[]{lblPos, lblJogador, lblTempo, lblPontos}) {
            lbl.setFont(Font.font("Arial", pesoFonte, 13));
            lbl.setTextFill(Color.web(corTexto));
        }
        
        GridPane.setHalignment(lblPos, HPos.CENTER);
        GridPane.setHalignment(lblPontos, HPos.RIGHT);
        GridPane.setHalignment(lblTempo, HPos.RIGHT);
        
        linha.add(lblPos, 0, 0);
        linha.add(lblJogador, 1, 0);
        linha.add(lblTempo, 2, 0);
        linha.add(lblPontos, 3, 0);
        
        return linha;
    }
    
    /**
     * Formata o tempo em segundos para uma string legível.
     * 
     * @param segundos Tempo em segundos
     * @return String formatada (ex: "2m 30s" ou "45s")
     */
    private String formatarTempo(long segundos) {
        if (segundos < 60) {
            return segundos + "s";
        }
        long minutos = segundos / 60;
        long segundosRestantes = segundos % 60;
        if (segundosRestantes == 0) {
            return minutos + "m";
        }
        return minutos + "m " + segundosRestantes + "s";
    }
    
    /**
     * Atualiza as estatísticas gerais exibidas.
     * 
     * @param pontuacoes Lista de pontuações carregadas
     */
    private void atualizarEstatisticas(List<PontuacaoRecord> pontuacoes) {
        containerEstatisticas.getChildren().clear();
        
        if (pontuacoes.isEmpty()) {
            Label lblVazio = new Label("📊 Total de partidas: 0");
            lblVazio.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            lblVazio.setTextFill(Color.web("#2C3E50"));
            containerEstatisticas.getChildren().add(lblVazio);
            return;
        }
        
        int totalPartidas = pontuacoes.size();
        int pontuacaoMaxima = pontuacoes.get(0).getPontuacao(); // Já está ordenado
        long tempoTotalGeral = 0;
        
        for (PontuacaoRecord record : pontuacoes) {
            tempoTotalGeral += record.getTempoTotal();
        }
        
        long tempoMedio = tempoTotalGeral / totalPartidas;
        
        HBox statsBox = new HBox(30);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(5));
        
        Label lblTotal = new Label("📊 Total: " + totalPartidas);
        Label lblMax = new Label("⭐ Máxima: " + String.format("%,d", pontuacaoMaxima));
        Label lblTempoMedio = new Label("⏱️ Tempo médio: " + formatarTempo(tempoMedio));
        
        for (Label lbl : new Label[]{lblTotal, lblMax, lblTempoMedio}) {
            lbl.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            lbl.setTextFill(Color.web("#2C3E50"));
        }
        
        statsBox.getChildren().addAll(lblTotal, lblMax, lblTempoMedio);
        containerEstatisticas.getChildren().add(statsBox);
    }
    
    @Override
    public void atualizar() {
        // Atualiza as pontuações recarregando do arquivo
        carregarPontuacoes();
    }
    
    /**
     * Retorna o layout do painel de pontuação.
     * 
     * @return Layout VBox do painel
     */
    public VBox getLayout() {
        return layout;
    }
    
    /**
     * Define o jogador cujas estatísticas serão exibidas.
     * <p>
     * Atualiza automaticamente a exibição após definir o novo jogador.
     * </p>
     * 
     * @param jogador Novo jogador
     */
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
        atualizar();
    }
}


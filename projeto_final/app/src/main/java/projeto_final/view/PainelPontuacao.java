package projeto_final.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import projeto_final.abstracts.ComponenteGrafico;
import projeto_final.interfaces.Desenhavel;
import projeto_final.model.Jogador;

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
public class PainelPontuacao extends ComponenteGrafico implements Desenhavel {
    private VBox layout;
    private Label lblPontuacaoTotal;
    private Label lblPartidasJogadas;
    private Label lblRecordes;
    private Jogador jogador;
    
    /**
     * Construtor que cria o painel de pontuação para um jogador.
     * 
     * @param jogador Jogador cujas estatísticas serão exibidas
     */
    public PainelPontuacao(Jogador jogador) {
        this.jogador = jogador;
        this.layout = new VBox(15);
        this.layout.setAlignment(Pos.CENTER);
        this.layout.setPadding(new Insets(20));
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        lblPontuacaoTotal = new Label("Pontuação Total: 0");
        lblPartidasJogadas = new Label("Partidas Jogadas: 0");
        lblRecordes = new Label("Recordes por Dificuldade:");
        
        layout.getChildren().addAll(
            new Label("=== ESTATÍSTICAS ==="),
            lblPontuacaoTotal,
            lblPartidasJogadas,
            lblRecordes
        );
    }
    
    @Override
    public void exibir() {
        // Exibe o painel de pontuação
        atualizar();
    }
    
    @Override
    public void atualizar() {
        if (jogador != null) {
            lblPontuacaoTotal.setText("Pontuação Total: " + jogador.getPontuacaoTotal());
            lblPartidasJogadas.setText("Partidas Jogadas: " + jogador.getPartidasJogadas());
            
            // Atualiza informações de recordes
            StringBuilder recordes = new StringBuilder("Recordes por Dificuldade:\n");
            // Aqui poderia adicionar os recordes específicos se necessário
            lblRecordes.setText(recordes.toString());
        }
    }
    
    @Override
    public void desenhar() {
        // Desenha o painel de pontuação na interface
        exibir();
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


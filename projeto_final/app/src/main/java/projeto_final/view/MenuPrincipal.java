package projeto_final.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
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
    
    /** Botão para sair da aplicação */
    private Button btnSair;
    
    /**
     * Construtor que cria o menu principal.
     * <p>
     * Inicializa o layout e os componentes do menu.
     * </p>
     */
    public MenuPrincipal() {
        this.layout = new VBox(20);
        this.layout.setAlignment(Pos.CENTER);
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        btnNovoJogo = new Button("Novo Jogo");
        btnCarregarJogo = new Button("Carregar Jogo");
        btnSair = new Button("Sair");
        
        layout.getChildren().addAll(btnNovoJogo, btnCarregarJogo, btnSair);
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
     * Retorna o botão de sair.
     * 
     * @return Botão de sair
     */
    public Button getBtnSair() {
        return btnSair;
    }
}


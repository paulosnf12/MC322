// Em: src/main/java/projeto_final/model/Celula.java
package projeto_final.model;

import projeto_final.abstracts.ElementoJogo;

/**
 * Classe que representa uma célula do tabuleiro do jogo Lights Out.
 * <p>
 * Cada célula possui uma posição (x, y) no tabuleiro e um estado
 * (ligada ou desligada). O objetivo do jogo é desligar todas as células.
 * </p>
 * <p>
 * Esta classe herda de {@code ElementoJogo} e faz parte da composição
 * do {@code Tabuleiro}.
 * </p>
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 * @see projeto_final.abstracts.ElementoJogo
 * @see projeto_final.model.Tabuleiro
 */
public class Celula extends ElementoJogo {
    /** Coordenada X (linha) da célula no tabuleiro */
    private int x;
    
    /** Coordenada Y (coluna) da célula no tabuleiro */
    private int y;
    
    /** Estado da célula: true se ligada, false se desligada */
    private boolean ligada;

    /**
     * Construtor padrão que cria uma célula na posição (0, 0) desligada.
     */
    public Celula() {
        // Toda célula começa desligada por padrão.
        this.ligada = false;
        this.x = 0;
        this.y = 0;
    }
    
    /**
     * Construtor que cria uma célula na posição especificada.
     * 
     * @param x Coordenada X (linha) da célula
     * @param y Coordenada Y (coluna) da célula
     */
    public Celula(int x, int y) {
        this.ligada = false;
        this.x = x;
        this.y = y;
    }

    /**
     * Inicializa a célula, desligando-a.
     * <p>
     * Implementação do método abstrato da classe pai {@code ElementoJogo}.
     * Este método garante que a célula esteja em um estado inicial válido
     * (desligada) quando for inicializada ou resetada.
     * </p>
     * 
     * @see projeto_final.abstracts.ElementoJogo#inicializar()
     */
    @Override
    public void inicializar() {
        this.ligada = false;
    }

    /**
     * Inverte o estado da célula (de ligada para desligada e vice-versa).
     * <p>
     * Este método é chamado quando o jogador clica na célula ou quando
     * uma célula adjacente é clicada.
     * </p>
     */
    public void alternar() {
        this.ligada = !this.ligada;
    }

    /**
     * Verifica se a célula está ligada.
     * 
     * @return true se a célula estiver ligada, false caso contrário
     */
    public boolean isLigada() {
        return ligada;
    }
    
    /**
     * Retorna o estado da célula (método alternativo para compatibilidade).
     * 
     * @return true se a célula estiver ligada, false caso contrário
     */
    public boolean getLigada() {
        return ligada;
    }
    
    /**
     * Define o estado da célula.
     * 
     * @param estado true para ligar, false para desligar
     */
    public void setLigada(boolean estado) {
        this.ligada = estado;
    }
    
    /**
     * Retorna a coordenada X (linha) da célula.
     * 
     * @return Coordenada X
     */
    public int getX() {
        return x;
    }
    
    /**
     * Define a coordenada X (linha) da célula.
     * 
     * @param x Nova coordenada X
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     * Retorna a coordenada Y (coluna) da célula.
     * 
     * @return Coordenada Y
     */
    public int getY() {
        return y;
    }
    
    /**
     * Define a coordenada Y (coluna) da célula.
     * 
     * @param y Nova coordenada Y
     */
    public void setY(int y) {
        this.y = y;
    }
}
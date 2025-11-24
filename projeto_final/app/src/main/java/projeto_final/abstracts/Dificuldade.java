package projeto_final.abstracts;

import java.io.Serializable;

/**
 * Classe abstrata que define os atributos e comportamentos de uma dificuldade do jogo.
 * <p>
 * Esta classe implementa o padrão Strategy, permitindo que diferentes níveis
 * de dificuldade sejam definidos com características específicas (dimensão do
 * tabuleiro, multiplicador de pontuação, etc.).
 * </p>
 * <p>
 * Classes filhas devem definir os valores de {@code nome}, {@code dimensao} e
 * {@code multiplicador} no construtor. Estes campos são protegidos para permitir
 * acesso direto pelas classes filhas, mas não devem ser modificados após a inicialização.
 * </p>
 * <p>
 * Implementa {@code Serializable} para permitir persistência do estado do jogo.
 * </p>
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 * @see projeto_final.model.DificuldadeFacil
 * @see projeto_final.model.DificuldadeMedio
 * @see projeto_final.model.DificuldadeDificil
 */
public abstract class Dificuldade implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Nome da dificuldade (ex: "Fácil", "Médio", "Difícil") */
    protected String nome;
    
    /** Dimensão do tabuleiro (número de linhas/colunas) */
    protected int dimensao;
    
    /** Multiplicador aplicado à pontuação base */
    protected double multiplicador;
    
    /**
     * Retorna o nome da dificuldade.
     * 
     * @return Nome da dificuldade
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Retorna a dimensão do tabuleiro para esta dificuldade.
     * <p>
     * A dimensão determina o tamanho do tabuleiro (ex: 3x3, 5x5, 7x7).
     * </p>
     * 
     * @return Dimensão do tabuleiro (número de linhas/colunas)
     */
    public int getDimensao() {
        return dimensao;
    }
    
    /**
     * Retorna o multiplicador de pontuação para esta dificuldade.
     * <p>
     * O multiplicador é aplicado à pontuação base para calcular a pontuação
     * final. Dificuldades maiores têm multiplicadores maiores.
     * </p>
     * 
     * @return Multiplicador de pontuação (valor positivo)
     */
    public double getMultiplicador() {
        return multiplicador;
    }
}


package projeto_final.model;

import projeto_final.abstracts.Dificuldade;

/**
 * Classe concreta que representa a dificuldade fácil do jogo.
 * <p>
 * Esta classe estende {@code Dificuldade} e implementa o nível de dificuldade
 * mais fácil, caracterizado por:
 * </p>
 * <ul>
 *   <li>Tabuleiro 3x3 (9 células)</li>
 *   <li>Multiplicador de pontuação: 1.0</li>
 * </ul>
 * <p>
 * Esta é uma implementação concreta do padrão Strategy, fornecendo uma
 * estratégia específica de dificuldade para o jogo.
 * </p>
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 * @see projeto_final.abstracts.Dificuldade
 */
public class DificuldadeFacil extends Dificuldade {
    /**
     * Construtor que inicializa a dificuldade fácil.
     * <p>
     * Configura os atributos protegidos da classe pai:
     * </p>
     * <ul>
     *   <li>{@code nome}: "Fácil"</li>
     *   <li>{@code dimensao}: 3 (tabuleiro 3x3)</li>
     *   <li>{@code multiplicador}: 1.0</li>
     * </ul>
     */
    public DificuldadeFacil() {
        this.nome = "Fácil";
        this.dimensao = 3;
        this.multiplicador = 1.0;
    }
}


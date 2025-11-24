package projeto_final.model;

import projeto_final.abstracts.Dificuldade;

/**
 * Classe concreta que representa a dificuldade difícil do jogo.
 * <p>
 * Esta classe estende {@code Dificuldade} e implementa o nível de dificuldade
 * mais desafiador, caracterizado por:
 * </p>
 * <ul>
 *   <li>Tabuleiro 7x7 (49 células)</li>
 *   <li>Multiplicador de pontuação: 2.0</li>
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
public class DificuldadeDificil extends Dificuldade {
    /**
     * Construtor que inicializa a dificuldade difícil.
     * <p>
     * Configura os atributos protegidos da classe pai:
     * </p>
     * <ul>
     *   <li>{@code nome}: "Difícil"</li>
     *   <li>{@code dimensao}: 7 (tabuleiro 7x7)</li>
     *   <li>{@code multiplicador}: 2.0</li>
     * </ul>
     */
    public DificuldadeDificil() {
        this.nome = "Difícil";
        this.dimensao = 7;
        this.multiplicador = 2.0;
    }
}


package projeto_final.model;

import projeto_final.abstracts.Dificuldade;

/**
 * Classe concreta que representa a dificuldade média do jogo.
 * <p>
 * Esta classe estende {@code Dificuldade} e implementa o nível de dificuldade
 * intermediário, caracterizado por:
 * <ul>
 *   <li>Tabuleiro 5x5 (25 células)</li>
 *   <li>Multiplicador de pontuação: 1.5</li>
 * </ul>
 * </p>
 * <p>
 * Esta é uma implementação concreta do padrão Strategy, fornecendo uma
 * estratégia específica de dificuldade para o jogo.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.abstracts.Dificuldade
 */
public class DificuldadeMedio extends Dificuldade {
    /**
     * Construtor que inicializa a dificuldade média.
     * <p>
     * Configura os atributos protegidos da classe pai:
     * <ul>
     *   <li>{@code nome}: "Médio"</li>
     *   <li>{@code dimensao}: 5 (tabuleiro 5x5)</li>
     *   <li>{@code multiplicador}: 1.5</li>
     * </ul>
     * </p>
     */
    public DificuldadeMedio() {
        this.nome = "Médio";
        this.dimensao = 5;
        this.multiplicador = 1.5;
    }
}


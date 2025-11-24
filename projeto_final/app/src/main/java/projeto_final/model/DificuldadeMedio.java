package projeto_final.model;

import projeto_final.abstracts.Dificuldade;

/**
 * Classe que representa a dificuldade média do jogo.
 * <p>
 * Esta classe implementa o nível de dificuldade intermediário, caracterizado por:
 * <ul>
 *   <li>Tabuleiro 5x5 (25 células)</li>
 *   <li>Multiplicador de pontuação: 1.5</li>
 * </ul>
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
     * Configura o nome como "Médio", dimensão do tabuleiro como 5x5
     * e multiplicador de pontuação como 1.5.
     * </p>
     */
    public DificuldadeMedio() {
        this.nome = "Médio";
        this.dimensao = 5;
        this.multiplicador = 1.5;
    }
}


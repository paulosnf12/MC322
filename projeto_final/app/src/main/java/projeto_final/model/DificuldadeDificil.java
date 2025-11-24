package projeto_final.model;

import projeto_final.abstracts.Dificuldade;

/**
 * Classe que representa a dificuldade difícil do jogo.
 * <p>
 * Esta classe implementa o nível de dificuldade mais desafiador, caracterizado por:
 * <ul>
 *   <li>Tabuleiro 7x7 (49 células)</li>
 *   <li>Multiplicador de pontuação: 2.0</li>
 * </ul>
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.abstracts.Dificuldade
 */
public class DificuldadeDificil extends Dificuldade {
    /**
     * Construtor que inicializa a dificuldade difícil.
     * <p>
     * Configura o nome como "Difícil", dimensão do tabuleiro como 7x7
     * e multiplicador de pontuação como 2.0.
     * </p>
     */
    public DificuldadeDificil() {
        this.nome = "Difícil";
        this.dimensao = 7;
        this.multiplicador = 2.0;
    }
}


package projeto_final.model;

import projeto_final.abstracts.Dificuldade;

/**
 * Classe que representa a dificuldade fácil do jogo.
 * <p>
 * Esta classe implementa o nível de dificuldade mais fácil, caracterizado por:
 * <ul>
 *   <li>Tabuleiro 3x3 (9 células)</li>
 *   <li>Multiplicador de pontuação: 1.0</li>
 * </ul>
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.abstracts.Dificuldade
 */
public class DificuldadeFacil extends Dificuldade {
    /**
     * Construtor que inicializa a dificuldade fácil.
     * <p>
     * Configura o nome como "Fácil", dimensão do tabuleiro como 3x3
     * e multiplicador de pontuação como 1.0.
     * </p>
     */
    public DificuldadeFacil() {
        this.nome = "Fácil";
        this.dimensao = 3;
        this.multiplicador = 1.0;
    }
}


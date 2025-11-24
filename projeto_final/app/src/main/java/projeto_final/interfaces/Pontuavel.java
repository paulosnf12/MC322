package projeto_final.interfaces;

/**
 * Interface para elementos que podem calcular pontos.
 * <p>
 * Esta interface define o contrato para cálculo de pontuação baseado no
 * estado atual de um elemento do jogo. Classes que implementam esta interface
 * devem fornecer uma forma de calcular uma pontuação baseada em seu estado atual.
 * </p>
 * <p>
 * A pontuação calculada por este método pode ser usada para diferentes
 * propósitos, como exibição em tempo real ou cálculo de pontuação final.
 * </p>
 * <p>
 * Exemplo de classe que implementa esta interface: {@code Tabuleiro}.
 * </p>
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 * @see projeto_final.model.Tabuleiro
 */
public interface Pontuavel {
    /**
     * Calcula e retorna a pontuação baseada no estado atual do elemento.
     * <p>
     * A implementação deve considerar o estado interno do objeto
     * para calcular uma pontuação apropriada. O valor retornado deve
     * ser sempre não negativo.
     * </p>
     * 
     * @return A pontuação calculada (valor inteiro não negativo)
     */
    int calcularPontos();
}


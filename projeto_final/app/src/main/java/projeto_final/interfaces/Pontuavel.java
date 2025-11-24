package projeto_final.interfaces;

/**
 * Interface para elementos que podem calcular pontos.
 * <p>
 * Classes que implementam esta interface devem fornecer uma forma
 * de calcular uma pontuação baseada em seu estado atual.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
public interface Pontuavel {
    /**
     * Calcula e retorna a pontuação baseada no estado atual do elemento.
     * <p>
     * A implementação deve considerar o estado interno do objeto
     * para calcular uma pontuação apropriada.
     * </p>
     * 
     * @return A pontuação calculada (valor inteiro não negativo)
     */
    int calcularPontos();
}


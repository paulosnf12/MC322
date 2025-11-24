package projeto_final.interfaces;

/**
 * Interface para elementos que podem ser desenhados na tela.
 * <p>
 * Classes que implementam esta interface devem fornecer uma implementação
 * do método {@code desenhar()} para renderizar o elemento na interface gráfica.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
public interface Desenhavel {
    /**
     * Desenha o elemento na interface gráfica.
     * <p>
     * Este método é responsável por renderizar visualmente o elemento
     * que implementa esta interface.
     * </p>
     */
    void desenhar();
}


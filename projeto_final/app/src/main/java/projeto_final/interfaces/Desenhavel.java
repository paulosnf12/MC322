package projeto_final.interfaces;

/**
 * Interface para elementos que podem ser desenhados na tela.
 * <p>
 * Esta interface define o contrato para renderização de elementos visuais
 * na interface gráfica do jogo. Classes que implementam esta interface devem
 * fornecer uma implementação do método {@code desenhar()} para renderizar
 * o elemento na tela.
 * </p>
 * <p>
 * A classe abstrata {@code ComponenteGrafico} implementa esta interface
 * usando o padrão Template Method, fornecendo uma estrutura base para
 * renderização que pode ser estendida pelas classes filhas.
 * </p>
 * <p>
 * Exemplos de classes que implementam esta interface: {@code MenuPrincipal},
 * {@code PainelJogo}, {@code PainelPontuacao}.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.abstracts.ComponenteGrafico
 */
public interface Desenhavel {
    /**
     * Desenha o elemento na interface gráfica.
     * <p>
     * Este método é responsável por renderizar visualmente o elemento
     * que implementa esta interface. A implementação deve atualizar
     * todos os aspectos visuais do componente para refletir seu estado atual.
     * </p>
     * <p>
     * Este método pode ser chamado a qualquer momento para atualizar
     * a visualização do componente.
     * </p>
     */
    void desenhar();
}


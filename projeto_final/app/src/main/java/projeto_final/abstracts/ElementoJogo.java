package projeto_final.abstracts;

import java.io.Serializable;

/**
 * Classe abstrata base para elementos do jogo.
 * <p>
 * Esta classe define o contrato básico para todos os elementos que compõem
 * o jogo, fornecendo um método abstrato de inicialização que deve ser
 * implementado pelas classes filhas. Implementa {@code Serializable} para
 * permitir persistência do estado do jogo.
 * </p>
 * <p>
 * O padrão Template Method é aplicado através do método abstrato
 * {@link #inicializar()}, que deve ser implementado pelas classes filhas
 * para configurar o estado inicial do elemento.
 * </p>
 * <p>
 * Exemplos de elementos do jogo incluem: {@code Tabuleiro}, {@code Celula}.
 * </p>
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 * @see projeto_final.model.Tabuleiro
 * @see projeto_final.model.Celula
 */
public abstract class ElementoJogo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Inicializa o elemento do jogo.
     * <p>
     * Este método deve ser implementado pelas classes filhas para configurar
     * o estado inicial do elemento. É chamado quando o elemento é criado
     * ou quando precisa ser resetado.
     * </p>
     * <p>
     * A implementação deve garantir que o elemento esteja em um estado válido
     * e pronto para uso após a inicialização.
     * </p>
     */
    public abstract void inicializar();
}


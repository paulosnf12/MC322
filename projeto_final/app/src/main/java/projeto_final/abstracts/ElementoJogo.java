package projeto_final.abstracts;

import java.io.Serializable;

/**
 * Classe abstrata base para elementos do jogo.
 * <p>
 * Esta classe define o contrato básico para todos os elementos que compõem
 * o jogo, fornecendo um método abstrato de inicialização que deve ser
 * implementado pelas classes filhas.
 * </p>
 * <p>
 * Exemplos de elementos do jogo incluem: {@code Tabuleiro}, {@code Celula}.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.model.Tabuleiro
 * @see projeto_final.model.Celula
 */
public abstract class ElementoJogo implements Serializable {
    /**
     * Inicializa o elemento do jogo.
     * <p>
     * Este método deve ser implementado pelas classes filhas para configurar
     * o estado inicial do elemento. É chamado quando o elemento é criado
     * ou quando precisa ser resetado.
     * </p>
     */
    public abstract void inicializar();
}


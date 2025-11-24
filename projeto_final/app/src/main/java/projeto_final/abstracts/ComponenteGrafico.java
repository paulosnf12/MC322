package projeto_final.abstracts;

/**
 * Classe abstrata base para componentes gráficos da interface.
 * <p>
 * Esta classe define o contrato básico para todos os componentes visuais
 * da interface do jogo, fornecendo métodos abstratos para exibição e
 * atualização que devem ser implementados pelas classes filhas.
 * </p>
 * <p>
 * Exemplos de componentes gráficos incluem: {@code MenuPrincipal},
 * {@code PainelJogo}, {@code PainelPontuacao}.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.view.MenuPrincipal
 * @see projeto_final.view.PainelJogo
 * @see projeto_final.view.PainelPontuacao
 */
public abstract class ComponenteGrafico {
    /**
     * Exibe o componente na interface.
     * <p>
     * Este método deve ser implementado pelas classes filhas para renderizar
     * o componente na tela. É chamado quando o componente precisa ser
     * exibido pela primeira vez.
     * </p>
     */
    public abstract void exibir();
    
    /**
     * Atualiza o estado visual do componente.
     * <p>
     * Este método deve ser implementado pelas classes filhas para atualizar
     * a representação visual do componente com base no estado atual do modelo.
     * É chamado sempre que há mudanças que afetam a visualização.
     * </p>
     */
    public abstract void atualizar();
}


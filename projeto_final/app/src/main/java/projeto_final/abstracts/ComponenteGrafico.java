package projeto_final.abstracts;

import projeto_final.interfaces.Desenhavel;

/**
 * Classe abstrata base para componentes gráficos da interface.
 * <p>
 * Esta classe implementa o padrão Template Method, fornecendo a estrutura
 * básica para renderização de componentes visuais. Ela implementa a interface
 * {@code Desenhavel} e define métodos abstratos que devem ser implementados
 * pelas classes filhas.
 * </p>
 * <p>
 * O fluxo de renderização é:
 * <ol>
 *   <li>{@link #desenhar()} - método template que orquestra a renderização</li>
 *   <li>{@link #exibir()} - método abstrato para renderização inicial</li>
 *   <li>{@link #atualizar()} - método abstrato para atualização do estado visual</li>
 * </ol>
 * </p>
 * <p>
 * Exemplos de componentes gráficos incluem: {@code MenuPrincipal},
 * {@code PainelJogo}, {@code PainelPontuacao}.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.interfaces.Desenhavel
 * @see projeto_final.view.MenuPrincipal
 * @see projeto_final.view.PainelJogo
 * @see projeto_final.view.PainelPontuacao
 */
public abstract class ComponenteGrafico implements Desenhavel {
    /**
     * Exibe o componente na interface.
     * <p>
     * Este método deve ser implementado pelas classes filhas para renderizar
     * o componente na tela. É chamado quando o componente precisa ser
     * exibido pela primeira vez ou quando precisa ser completamente redesenhado.
     * </p>
     */
    public abstract void exibir();
    
    /**
     * Atualiza o estado visual do componente.
     * <p>
     * Este método deve ser implementado pelas classes filhas para atualizar
     * a representação visual do componente com base no estado atual do modelo.
     * É chamado sempre que há mudanças que afetam a visualização, mas não
     * requerem uma renderização completa.
     * </p>
     */
    public abstract void atualizar();
    
    /**
     * Desenha o componente na interface gráfica.
     * <p>
     * Implementação do Template Method que define o fluxo padrão de renderização:
     * primeiro exibe o componente e depois atualiza seu estado visual.
     * </p>
     * <p>
     * Classes filhas podem sobrescrever este método se precisarem de um
     * comportamento diferente, mas geralmente não é necessário.
     * </p>
     * 
     * @see projeto_final.interfaces.Desenhavel#desenhar()
     */
    @Override
    public void desenhar() {
        exibir();
        atualizar();
    }
}


package projeto_final.interfaces;

/**
 * Interface para componentes que processam eventos do sistema.
 * <p>
 * Esta interface implementa o padrão Observer, permitindo que componentes
 * recebam e processem eventos gerados durante a execução do jogo.
 * </p>
 * <p>
 * Classes que implementam esta interface devem:
 * </p>
 * <ul>
 *   <li>Identificar o tipo do evento recebido</li>
 *   <li>Executar a ação apropriada baseada no tipo de evento</li>
 *   <li>Tratar eventos desconhecidos ou inválidos adequadamente</li>
 * </ul>
 * <p>
 * Exemplos de classes que implementam esta interface: {@code MenuPrincipal},
 * {@code PainelJogo}.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.view.MenuPrincipal
 * @see projeto_final.view.PainelJogo
 */
public interface EventListener {
    /**
     * Processa um evento recebido.
     * <p>
     * Este método é chamado quando um evento é disparado no sistema.
     * A implementação deve determinar o tipo do evento e executar
     * a ação apropriada.
     * </p>
     * <p>
     * O parâmetro {@code evento} pode ser de qualquer tipo, e a implementação
     * deve usar {@code instanceof} ou outros mecanismos de verificação de tipo
     * para determinar como processar o evento.
     * </p>
     * 
     * @param evento O evento a ser processado (pode ser de qualquer tipo)
     */
    void processarEvento(Object evento);
}


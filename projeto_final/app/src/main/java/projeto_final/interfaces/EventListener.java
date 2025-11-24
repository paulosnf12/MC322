package projeto_final.interfaces;

/**
 * Interface para componentes que processam eventos do sistema.
 * <p>
 * Classes que implementam esta interface podem receber e processar
 * eventos gerados durante a execução do jogo, seguindo o padrão Observer.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
public interface EventListener {
    /**
     * Processa um evento recebido.
     * <p>
     * Este método é chamado quando um evento é disparado no sistema.
     * A implementação deve determinar o tipo do evento e executar
     * a ação apropriada.
     * </p>
     * 
     * @param evento O evento a ser processado (pode ser de qualquer tipo)
     */
    void processarEvento(Object evento);
}


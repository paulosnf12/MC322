package projeto_final.exceptions;

/**
 * Exceção lançada quando configuração do tabuleiro é matematicamente impossível de resolver.
 * <p>
 * Esta exceção é lançada quando o sistema detecta que a configuração inicial
 * do tabuleiro não possui solução, o que não deveria acontecer se a geração
 * de configurações estiver funcionando corretamente.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
public class ConfiguracaoInvalidaException extends Exception {
    
    /**
     * Construtor que cria uma exceção sem mensagem.
     */
    public ConfiguracaoInvalidaException() {
        super();
    }
    
    /**
     * Construtor que cria uma exceção com mensagem específica.
     * 
     * @param mensagem Mensagem de erro descritiva
     */
    public ConfiguracaoInvalidaException(String mensagem) {
        super(mensagem);
    }
    
    /**
     * Construtor que cria uma exceção com mensagem e causa.
     * 
     * @param mensagem Mensagem de erro descritiva
     * @param causa Exceção que causou esta exceção
     */
    public ConfiguracaoInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}


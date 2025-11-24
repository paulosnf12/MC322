package projeto_final.exceptions;

/**
 * Exceção lançada quando arquivo de save está corrompido ou ilegível.
 * <p>
 * Esta exceção é lançada quando o sistema tenta carregar um arquivo salvo
 * mas encontra dados corrompidos, formato inválido ou arquivo ilegível.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
public class DadosCorruptosException extends Exception {
    
    /**
     * Construtor que cria uma exceção sem mensagem.
     */
    public DadosCorruptosException() {
        super();
    }
    
    /**
     * Construtor que cria uma exceção com mensagem específica.
     * 
     * @param mensagem Mensagem de erro descritiva
     */
    public DadosCorruptosException(String mensagem) {
        super(mensagem);
    }
    
    /**
     * Construtor que cria uma exceção com mensagem e causa.
     * 
     * @param mensagem Mensagem de erro descritiva
     * @param causa Exceção que causou esta exceção
     */
    public DadosCorruptosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}


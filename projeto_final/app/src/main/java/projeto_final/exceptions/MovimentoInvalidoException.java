package projeto_final.exceptions;

/**
 * Exceção lançada quando há tentativa de interagir com posição inválida do tabuleiro.
 * <p>
 * Esta exceção é lançada quando o jogador tenta clicar em uma célula que está
 * fora dos limites do tabuleiro ou quando há algum problema com as coordenadas fornecidas.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
public class MovimentoInvalidoException extends Exception {
    
    /**
     * Construtor que cria uma exceção sem mensagem.
     */
    public MovimentoInvalidoException() {
        super();
    }
    
    /**
     * Construtor que cria uma exceção com mensagem específica.
     * 
     * @param mensagem Mensagem de erro descritiva
     */
    public MovimentoInvalidoException(String mensagem) {
        super(mensagem);
    }
    
    /**
     * Construtor que cria uma exceção com mensagem e causa.
     * 
     * @param mensagem Mensagem de erro descritiva
     * @param causa Exceção que causou esta exceção
     */
    public MovimentoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}


package projeto_final.interfaces;

/**
 * Interface para elementos que podem ser salvos e carregados de arquivos.
 * <p>
 * Esta interface define o contrato para persistência de objetos, permitindo
 * que o estado de um objeto seja salvo em arquivo e restaurado posteriormente.
 * </p>
 * <p>
 * Classes que implementam esta interface devem:
 * </p>
 * <ul>
 *   <li>Garantir que o objeto seja serializável (implementar {@code Serializable})</li>
 *   <li>Implementar {@link #salvar()} para persistir o estado atual</li>
 *   <li>Implementar {@link #carregar(String)} para restaurar o estado de um arquivo</li>
 * </ul>
 * <p>
 * Exemplos de classes que implementam esta interface: {@code Game}, {@code GerenciadorArquivos},
 * {@code GerenciadorPontuacoes}.
 * </p>
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 * @see java.io.Serializable
 */
public interface Salvavel {
    /**
     * Salva o estado atual do objeto em um arquivo.
     * <p>
     * Este método deve persistir o estado completo do objeto em um arquivo,
     * permitindo que seja restaurado posteriormente através do método
     * {@link #carregar(String)}.
     * </p>
     * <p>
     * A implementação deve tratar exceções de I/O adequadamente, seja
     * relançando-as ou registrando-as em log.
     * </p>
     */
    void salvar();
    
    /**
     * Carrega o estado do objeto a partir de um arquivo.
     * <p>
     * Este método deve restaurar o estado do objeto a partir de um
     * arquivo previamente salvo pelo método {@link #salvar()}.
     * </p>
     * <p>
     * A implementação deve validar a integridade do arquivo antes de
     * tentar carregá-lo e tratar adequadamente casos de arquivo corrompido
     * ou inexistente.
     * </p>
     * 
     * @param arquivo Caminho do arquivo a ser carregado
     */
    void carregar(String arquivo);
}


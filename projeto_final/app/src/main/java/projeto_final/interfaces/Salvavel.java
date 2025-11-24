package projeto_final.interfaces;

/**
 * Interface para elementos que podem ser salvos e carregados de arquivos.
 * <p>
 * Classes que implementam esta interface devem fornecer funcionalidade
 * para persistir e restaurar o estado do objeto em arquivos.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
public interface Salvavel {
    /**
     * Salva o estado atual do objeto em um arquivo.
     * <p>
     * Este método deve persistir o estado completo do objeto,
     * permitindo que seja restaurado posteriormente através do método
     * {@link #carregar(String)}.
     * </p>
     */
    void salvar();
    
    /**
     * Carrega o estado do objeto a partir de um arquivo.
     * <p>
     * Este método deve restaurar o estado do objeto a partir de um
     * arquivo previamente salvo pelo método {@link #salvar()}.
     * </p>
     * 
     * @param arquivo Caminho do arquivo a ser carregado
     */
    void carregar(String arquivo);
}


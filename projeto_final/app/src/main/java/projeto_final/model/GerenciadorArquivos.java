package projeto_final.model;

import java.io.*;
import java.util.Properties;
import projeto_final.exceptions.DadosCorruptosException;
import projeto_final.interfaces.Salvavel;

/**
 * Classe Singleton responsável por gerenciar operações de arquivo.
 * <p>
 * Esta classe implementa o padrão Singleton para garantir que apenas uma
 * instância seja criada durante a execução da aplicação. Ela fornece
 * métodos estáticos para salvar e carregar objetos, validar integridade
 * de arquivos e gerenciar configurações do jogo.
 * </p>
 * <p>
 * A classe também implementa a interface {@code Salvavel} para permitir
 * que o próprio gerenciador possa ser salvo/carregado se necessário.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.interfaces.Salvavel
 */
public class GerenciadorArquivos implements Salvavel {
    private static GerenciadorArquivos instancia;
    private static final String DIR_CONFIG = "config/";
    private static final String ARQUIVO_CONFIG = "config.properties";
    
    /**
     * Construtor privado para garantir o padrão Singleton.
     * <p>
     * Cria o diretório de configuração se ele não existir.
     * </p>
     */
    private GerenciadorArquivos() {
        // Construtor privado para garantir Singleton
        File dir = new File(DIR_CONFIG);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    /**
     * Retorna a instância única do GerenciadorArquivos (Singleton).
     * @return A instância do GerenciadorArquivos
     */
    public static GerenciadorArquivos getInstancia() {
        if (instancia == null) {
            instancia = new GerenciadorArquivos();
        }
        return instancia;
    }
    
    /**
     * Salva um objeto em um arquivo.
     * <p>
     * Cria o diretório do arquivo se ele não existir.
     * </p>
     * 
     * @param dados O objeto a ser salvo
     * @param arquivo O caminho do arquivo
     * @throws IOException Se houver erro de I/O ao salvar
     */
    public static void salvar(Object dados, String arquivo) throws IOException {
        // Cria o diretório se não existir
        File arquivoFile = new File(arquivo);
        File diretorio = arquivoFile.getParentFile();
        if (diretorio != null && !diretorio.exists()) {
            diretorio.mkdirs();
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(arquivo))) {
            oos.writeObject(dados);
        } catch (IOException e) {
            throw new IOException("Erro ao salvar arquivo: " + arquivo, e);
        }
    }
    
    /**
     * Carrega um objeto de um arquivo (método estático auxiliar).
     * <p>
     * Valida a integridade do arquivo antes de tentar carregá-lo.
     * </p>
     * 
     * @param arquivo O caminho do arquivo
     * @return O objeto carregado
     * @throws IOException Se houver erro de I/O
     * @throws DadosCorruptosException Se o arquivo estiver corrompido ou ilegível
     */
    public static Object carregarObjeto(String arquivo) throws IOException, DadosCorruptosException {
        // Valida integridade antes de carregar
        if (!validarIntegridade(arquivo)) {
            throw new DadosCorruptosException("Arquivo corrompido ou ilegível: " + arquivo);
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(arquivo))) {
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new DadosCorruptosException("Classe não encontrada ao carregar arquivo: " + arquivo, e);
        } catch (IOException e) {
            throw new IOException("Erro ao ler arquivo: " + arquivo, e);
        }
    }
    
    /**
     * Valida a integridade de um arquivo.
     * @param arquivo O caminho do arquivo
     * @return true se o arquivo existe e pode ser lido, false caso contrário
     */
    public static boolean validarIntegridade(String arquivo) {
        File f = new File(arquivo);
        return f.exists() && f.canRead() && f.length() > 0;
    }
    
    /**
     * Salva as configurações do jogo.
     * @param config As propriedades de configuração
     */
    public static void salvarConfiguracao(Properties config) {
        try (FileOutputStream fos = new FileOutputStream(DIR_CONFIG + ARQUIVO_CONFIG)) {
            config.store(fos, "Configurações do Jogo");
        } catch (IOException e) {
            System.err.println("Erro ao salvar configuração: " + e.getMessage());
        }
    }
    
    /**
     * Carrega as configurações do jogo.
     * @return As propriedades de configuração, ou um Properties vazio se não existir
     */
    public static Properties carregarConfiguracao() {
        Properties config = new Properties();
        File arquivo = new File(DIR_CONFIG + ARQUIVO_CONFIG);
        if (arquivo.exists()) {
            try (FileInputStream fis = new FileInputStream(arquivo)) {
                config.load(fis);
            } catch (IOException e) {
                System.err.println("Erro ao carregar configuração: " + e.getMessage());
            }
        }
        return config;
    }
    
    /**
     * Salva o estado do gerenciador (implementação da interface Salvavel).
     * <p>
     * Por padrão, não há estado a salvar para o gerenciador, pois ele
     * é uma classe utilitária sem estado persistente.
     * </p>
     */
    @Override
    public void salvar() {
        // Implementação da interface Salvavel
        // Salva o estado do gerenciador se necessário
        // Por padrão, não há estado a salvar para o gerenciador
    }
    
    /**
     * Carrega o estado do gerenciador (implementação da interface Salvavel).
     * <p>
     * Por padrão, não há estado a carregar para o gerenciador, pois ele
     * é uma classe utilitária sem estado persistente.
     * </p>
     * 
     * @param arquivo Caminho do arquivo (não utilizado na implementação atual)
     */
    @Override
    public void carregar(String arquivo) {
        // Implementação da interface Salvavel
        // Carrega o estado do gerenciador se necessário
        // Por padrão, não há estado a carregar para o gerenciador
        // Este método pode usar o método estático carregarObjeto internamente se necessário
    }
}


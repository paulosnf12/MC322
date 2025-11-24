package projeto_final.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import projeto_final.interfaces.Salvavel;

/**
 * Classe responsável por gerenciar a persistência de pontuações usando serialização de objetos.
 * <p>
 * Esta classe implementa a interface {@code Salvavel} para gerenciar a persistência
 * da lista de pontuações. As pontuações são salvas como objetos serializados em um
 * arquivo binário e ordenadas por pontuação (maior primeiro).
 * </p>
 * <p>
 * A classe utiliza o padrão Singleton para garantir uma única instância e métodos
 * estáticos para facilitar o uso sem necessidade de instanciação.
 * </p>
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 * @see projeto_final.interfaces.Salvavel
 * @see projeto_final.model.PontuacaoRecord
 */
public class GerenciadorPontuacoes implements Salvavel {
    /** Instância única do gerenciador (Singleton) */
    private static GerenciadorPontuacoes instancia;
    
    /** Nome do arquivo de pontuações */
    private static final String ARQUIVO_PONTUACOES = "pontuacoes.dat";
    
    /** Lista de pontuações em memória */
    private List<PontuacaoRecord> pontuacoes;
    
    /**
     * Construtor privado para garantir o padrão Singleton.
     */
    private GerenciadorPontuacoes() {
        this.pontuacoes = new ArrayList<>();
        try {
            this.pontuacoes = carregarPontuacoes();
        } catch (IOException e) {
            // Se houver erro ao carregar, inicia com lista vazia
            this.pontuacoes = new ArrayList<>();
        }
    }
    
    /**
     * Retorna a instância única do GerenciadorPontuacoes (Singleton).
     * 
     * @return A instância do GerenciadorPontuacoes
     */
    public static GerenciadorPontuacoes getInstancia() {
        if (instancia == null) {
            instancia = new GerenciadorPontuacoes();
        }
        return instancia;
    }
    
    /**
     * Salva ou atualiza uma pontuação no arquivo.
     * <p>
     * Se o jogador já possui um recorde na mesma dificuldade, atualiza apenas se a nova
     * pontuação for maior. Caso contrário, adiciona um novo registro.
     * O arquivo é ordenado por pontuação (maior primeiro) após a operação.
     * </p>
     * 
     * @param record Registro de pontuação a ser salvo
     * @return true se houve recorde (novo ou atualizado), false caso contrário
     * @throws IOException Se houver erro ao escrever no arquivo
     */
    public static boolean salvarOuAtualizarPontuacao(PontuacaoRecord record) throws IOException {
        return getInstancia().salvarOuAtualizarPontuacaoInstancia(record);
    }
    
    /**
     * Método de instância para salvar ou atualizar uma pontuação.
     * 
     * @param record Registro de pontuação a ser salvo
     * @return true se houve recorde (novo ou atualizado), false caso contrário
     * @throws IOException Se houver erro ao escrever no arquivo
     */
    private boolean salvarOuAtualizarPontuacaoInstancia(PontuacaoRecord record) throws IOException {
        // Garante que a lista está atualizada
        this.pontuacoes = carregarPontuacoes();
        
        // Busca se já existe um recorde para este jogador nesta dificuldade
        PontuacaoRecord recordeExistente = buscarRecorde(this.pontuacoes, record.getNomeJogador(), record.getDificuldade());
        
        boolean houveRecorde = false;
        
        if (recordeExistente != null) {
            // Já existe um recorde - atualiza apenas se a nova pontuação for maior
            if (record.getPontuacao() > recordeExistente.getPontuacao()) {
                // Remove o recorde antigo
                this.pontuacoes.remove(recordeExistente);
                // Adiciona o novo recorde
                this.pontuacoes.add(record);
                houveRecorde = true;
            }
        } else {
            // Não existe recorde - adiciona novo
            this.pontuacoes.add(record);
            houveRecorde = true; // Primeiro recorde do jogador nesta dificuldade
        }
        
        // Ordena por pontuação (maior primeiro)
        Collections.sort(this.pontuacoes, new Comparator<PontuacaoRecord>() {
            @Override
            public int compare(PontuacaoRecord r1, PontuacaoRecord r2) {
                return Integer.compare(r2.getPontuacao(), r1.getPontuacao());
            }
        });
        
        // Salva todas as pontuações no arquivo
        salvar();
        
        return houveRecorde;
    }
    
    /**
     * Busca um recorde existente para um jogador em uma dificuldade específica.
     * 
     * @param pontuacoes Lista de pontuações
     * @param nomeJogador Nome do jogador
     * @param dificuldade Nome da dificuldade
     * @return Registro encontrado, ou null se não existir
     */
    private static PontuacaoRecord buscarRecorde(List<PontuacaoRecord> pontuacoes, String nomeJogador, String dificuldade) {
        for (PontuacaoRecord record : pontuacoes) {
            if (record.getNomeJogador().equalsIgnoreCase(nomeJogador) && 
                record.getDificuldade().equalsIgnoreCase(dificuldade)) {
                return record;
            }
        }
        return null;
    }
    
    /**
     * Carrega todas as pontuações do arquivo.
     * 
     * @return Lista de registros de pontuação, ordenada por pontuação (maior primeiro)
     * @throws IOException Se houver erro ao ler o arquivo
     */
    @SuppressWarnings("unchecked")
    public static List<PontuacaoRecord> carregarPontuacoes() throws IOException {
        File arquivo = new File(ARQUIVO_PONTUACOES);
        
        if (!arquivo.exists()) {
            return new ArrayList<>(); // Retorna lista vazia se o arquivo não existir
        }
        
        List<PontuacaoRecord> pontuacoes = new ArrayList<>();
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                pontuacoes = (List<PontuacaoRecord>) obj;
            }
        } catch (ClassNotFoundException e) {
            throw new IOException("Erro ao carregar pontuações: classe não encontrada", e);
        } catch (ClassCastException e) {
            throw new IOException("Erro ao carregar pontuações: formato inválido", e);
        }
        
        // Ordena por pontuação (maior primeiro)
        Collections.sort(pontuacoes, new Comparator<PontuacaoRecord>() {
            @Override
            public int compare(PontuacaoRecord r1, PontuacaoRecord r2) {
                return Integer.compare(r2.getPontuacao(), r1.getPontuacao());
            }
        });
        
        return pontuacoes;
    }
    
    /**
     * Salva o estado atual do gerenciador (lista de pontuações) em um arquivo.
     * <p>
     * Implementação da interface {@code Salvavel}. Salva a lista de pontuações
     * em memória no arquivo usando serialização de objetos.
     * </p>
     * 
     * @see projeto_final.interfaces.Salvavel#salvar()
     */
    @Override
    public void salvar() {
        try {
            File arquivo = new File(ARQUIVO_PONTUACOES);
            
            // Cria o diretório pai se não existir
            File parentDir = arquivo.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
                oos.writeObject(this.pontuacoes);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar pontuações: " + e.getMessage());
            // Não relança para não quebrar o fluxo do jogo
        }
    }
    
    /**
     * Carrega o estado do gerenciador (lista de pontuações) a partir de um arquivo.
     * <p>
     * Implementação da interface {@code Salvavel}. Carrega a lista de pontuações
     * do arquivo e atualiza o estado interno do gerenciador.
     * </p>
     * 
     * @param arquivo Caminho do arquivo a ser carregado (ignorado, usa ARQUIVO_PONTUACOES)
     * @see projeto_final.interfaces.Salvavel#carregar(String)
     */
    @Override
    public void carregar(String arquivo) {
        try {
            this.pontuacoes = carregarPontuacoes();
        } catch (IOException e) {
            System.err.println("Erro ao carregar pontuações: " + e.getMessage());
            // Se houver erro, mantém a lista atual ou inicia com lista vazia
            if (this.pontuacoes == null) {
                this.pontuacoes = new ArrayList<>();
            }
        }
    }
    
    /**
     * Retorna o caminho do arquivo de pontuações.
     * 
     * @return Caminho do arquivo
     */
    public static String getCaminhoArquivo() {
        return ARQUIVO_PONTUACOES;
    }
}


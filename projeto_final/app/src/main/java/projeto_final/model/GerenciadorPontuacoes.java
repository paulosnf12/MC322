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

/**
 * Classe responsável por gerenciar a persistência de pontuações usando serialização de objetos.
 * <p>
 * As pontuações são salvas como objetos serializados em um arquivo binário.
 * O arquivo é ordenado por pontuação (maior primeiro).
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
public class GerenciadorPontuacoes {
    /** Nome do arquivo de pontuações */
    private static final String ARQUIVO_PONTUACOES = "pontuacoes.dat";
    
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
        List<PontuacaoRecord> pontuacoes = carregarPontuacoes();
        
        // Busca se já existe um recorde para este jogador nesta dificuldade
        PontuacaoRecord recordeExistente = buscarRecorde(pontuacoes, record.getNomeJogador(), record.getDificuldade());
        
        boolean houveRecorde = false;
        
        if (recordeExistente != null) {
            // Já existe um recorde - atualiza apenas se a nova pontuação for maior
            if (record.getPontuacao() > recordeExistente.getPontuacao()) {
                // Remove o recorde antigo
                pontuacoes.remove(recordeExistente);
                // Adiciona o novo recorde
                pontuacoes.add(record);
                houveRecorde = true;
            }
        } else {
            // Não existe recorde - adiciona novo
            pontuacoes.add(record);
            houveRecorde = true; // Primeiro recorde do jogador nesta dificuldade
        }
        
        // Ordena por pontuação (maior primeiro)
        Collections.sort(pontuacoes, new Comparator<PontuacaoRecord>() {
            @Override
            public int compare(PontuacaoRecord r1, PontuacaoRecord r2) {
                return Integer.compare(r2.getPontuacao(), r1.getPontuacao());
            }
        });
        
        // Salva todas as pontuações no arquivo
        salvarTodasPontuacoes(pontuacoes);
        
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
     * Salva uma nova pontuação no arquivo (método legado para compatibilidade).
     * <p>
     * Este método chama {@link #salvarOuAtualizarPontuacao(PontuacaoRecord)}.
     * </p>
     * 
     * @param record Registro de pontuação a ser salvo
     * @throws IOException Se houver erro ao escrever no arquivo
     * @deprecated Use {@link #salvarOuAtualizarPontuacao(PontuacaoRecord)} em vez disso
     */
    @Deprecated
    public static void salvarPontuacao(PontuacaoRecord record) throws IOException {
        salvarOuAtualizarPontuacao(record);
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
     * Salva todas as pontuações no arquivo usando serialização de objetos.
     * 
     * @param pontuacoes Lista de registros de pontuação a serem salvos
     * @throws IOException Se houver erro ao escrever no arquivo
     */
    private static void salvarTodasPontuacoes(List<PontuacaoRecord> pontuacoes) throws IOException {
        File arquivo = new File(ARQUIVO_PONTUACOES);
        
        // Cria o diretório pai se não existir
        File parentDir = arquivo.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(pontuacoes);
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


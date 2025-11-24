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
     * Salva uma nova pontuação no arquivo.
     * <p>
     * O arquivo é ordenado por pontuação (maior primeiro) após adicionar o novo registro.
     * </p>
     * 
     * @param record Registro de pontuação a ser salvo
     * @throws IOException Se houver erro ao escrever no arquivo
     */
    public static void salvarPontuacao(PontuacaoRecord record) throws IOException {
        List<PontuacaoRecord> pontuacoes = carregarPontuacoes();
        pontuacoes.add(record);
        
        // Ordena por pontuação (maior primeiro)
        Collections.sort(pontuacoes, new Comparator<PontuacaoRecord>() {
            @Override
            public int compare(PontuacaoRecord r1, PontuacaoRecord r2) {
                return Integer.compare(r2.getPontuacao(), r1.getPontuacao());
            }
        });
        
        // Salva todas as pontuações no arquivo
        salvarTodasPontuacoes(pontuacoes);
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


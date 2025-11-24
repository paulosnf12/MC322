package projeto_final.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import projeto_final.abstracts.Dificuldade;

/**
 * Classe que representa um jogador e gerencia suas estatísticas.
 * <p>
 * Esta classe armazena informações sobre o jogador, incluindo nome,
 * pontuação total, número de partidas jogadas e recordes por dificuldade.
 * </p>
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 */
public class Jogador implements Serializable {
    private static final long serialVersionUID = 1L;
    /** Nome do jogador */
    private String nome;
    
    /** Pontuação total acumulada em todas as partidas */
    private int pontuacaoTotal;
    
    /** Número total de partidas jogadas */
    private int partidasJogadas;
    
    /** Mapa que armazena o recorde de pontuação para cada dificuldade */
    private Map<Dificuldade, Integer> recordePorDificuldade;
    
    /**
     * Construtor que cria um novo jogador.
     * 
     * @param nome Nome do jogador
     */
    public Jogador(String nome) {
        this.nome = nome;
        this.pontuacaoTotal = 0;
        this.partidasJogadas = 0;
        this.recordePorDificuldade = new HashMap<>();
    }
    
    /**
     * Adiciona pontos à pontuação total do jogador.
     * <p>
     * Este método também incrementa o contador de partidas jogadas.
     * </p>
     * 
     * @param pontos Pontos a serem adicionados (deve ser não negativo)
     */
    public void adicionarPontuacao(int pontos) {
        this.pontuacaoTotal += pontos;
        this.partidasJogadas++;
    }
    
    /**
     * Retorna o recorde do jogador para uma dificuldade específica.
     * 
     * @param dificuldade A dificuldade para a qual se deseja o recorde
     * @return O recorde (pontos) para a dificuldade, ou 0 se não houver recorde
     */
    public int getRecorde(Dificuldade dificuldade) {
        return recordePorDificuldade.getOrDefault(dificuldade, 0);
    }
    
    /**
     * Atualiza o recorde para uma dificuldade específica se a pontuação for maior.
     * <p>
     * O recorde só é atualizado se a nova pontuação for maior que o recorde atual.
     * </p>
     * 
     * @param dificuldade A dificuldade
     * @param pontos A pontuação obtida
     */
    public void atualizarRecorde(Dificuldade dificuldade, int pontos) {
        int recordeAtual = getRecorde(dificuldade);
        if (pontos > recordeAtual) {
            recordePorDificuldade.put(dificuldade, pontos);
        }
    }
    
    /**
     * Retorna o nome do jogador.
     * 
     * @return Nome do jogador
     */
    public String getNome() {
        return nome;
    }
    
    /**
     * Define o nome do jogador.
     * 
     * @param nome Novo nome do jogador
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    /**
     * Retorna a pontuação total acumulada.
     * 
     * @return Pontuação total
     */
    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }
    
    /**
     * Retorna o número de partidas jogadas.
     * 
     * @return Número de partidas jogadas
     */
    public int getPartidasJogadas() {
        return partidasJogadas;
    }
}


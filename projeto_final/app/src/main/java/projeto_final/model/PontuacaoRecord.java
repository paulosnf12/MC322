package projeto_final.model;

import java.io.Serializable;

/**
 * Classe que representa um registro de pontuação de uma partida completa.
 * <p>
 * Armazena informações sobre uma partida vencida: nome do jogador, dificuldade,
 * tempo total (soma dos 3 turnos) e pontuação final.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
public class PontuacaoRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Nome do jogador */
    private String nomeJogador;
    
    /** Nome da dificuldade jogada */
    private String dificuldade;
    
    /** Tempo total em segundos (soma dos 3 turnos) */
    private long tempoTotal;
    
    /** Pontuação final da partida */
    private int pontuacao;
    
    /**
     * Construtor que cria um registro de pontuação.
     * 
     * @param nomeJogador Nome do jogador
     * @param dificuldade Nome da dificuldade
     * @param tempoTotal Tempo total em segundos
     * @param pontuacao Pontuação final
     */
    public PontuacaoRecord(String nomeJogador, String dificuldade, long tempoTotal, int pontuacao) {
        this.nomeJogador = nomeJogador;
        this.dificuldade = dificuldade;
        this.tempoTotal = tempoTotal;
        this.pontuacao = pontuacao;
    }
    
    /**
     * Retorna o nome do jogador.
     * 
     * @return Nome do jogador
     */
    public String getNomeJogador() {
        return nomeJogador;
    }
    
    /**
     * Retorna o nome da dificuldade.
     * 
     * @return Nome da dificuldade
     */
    public String getDificuldade() {
        return dificuldade;
    }
    
    /**
     * Retorna o tempo total em segundos.
     * 
     * @return Tempo total em segundos
     */
    public long getTempoTotal() {
        return tempoTotal;
    }
    
    /**
     * Retorna a pontuação final.
     * 
     * @return Pontuação final
     */
    public int getPontuacao() {
        return pontuacao;
    }
    
    /**
     * Retorna uma representação em string do registro.
     * 
     * @return String formatada com as informações do registro
     */
    @Override
    public String toString() {
        return String.format("%s | %s | %d segundos | %d pontos", 
                nomeJogador, dificuldade, tempoTotal, pontuacao);
    }
}


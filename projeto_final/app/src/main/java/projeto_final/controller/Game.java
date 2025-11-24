// Em: src/main/java/projeto_final/controller/Game.java
package projeto_final.controller;

import java.io.IOException;
import java.io.Serializable;
import projeto_final.abstracts.Dificuldade;
import projeto_final.exceptions.MovimentoInvalidoException;
import projeto_final.interfaces.Salvavel;
import projeto_final.model.EstadoJogo;
import projeto_final.model.GerenciadorArquivos;
import projeto_final.model.GerenciadorPontuacoes;
import projeto_final.model.Jogador;
import projeto_final.model.PontuacaoRecord;
import projeto_final.model.Tabuleiro;

/**
 * Classe controladora principal do jogo Lights Out.
 * <p>
 * Esta classe implementa o padrão MVC como Controller, gerenciando a lógica
 * do jogo, estado, pontuação, movimentos e interação entre o modelo ({@code Tabuleiro},
 * {@code Jogador}) e a view ({@code PainelJogo}).
 * </p>
 * <p>
 * A classe também implementa a interface {@code Salvavel} para permitir
 * salvar e carregar o estado do jogo.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.interfaces.Salvavel
 * @see projeto_final.model.Tabuleiro
 * @see projeto_final.model.Jogador
 * @see projeto_final.model.EstadoJogo
 */
public class Game implements Salvavel, Serializable {
    private static final long serialVersionUID = 1L;
    /** Tabuleiro do jogo (composição) */
    private Tabuleiro tabuleiro;
    
    /** Estado atual do jogo */
    private EstadoJogo estado;
    
    /** Pontuação do jogo (incrementada a cada turno vencido) */
    private int pontuacao;
    
    /** Movimentos do último turno completado (para exibição) */
    private int movimentosUltimoTurno;
    
    /** Tempo do último turno completado (para exibição) */
    private long tempoUltimoTurno;
    
    /** Número de movimentos realizados */
    private int movimentos;
    
    /** Tempo de início do jogo em milissegundos */
    private long tempoInicio;
    
    /** Tempo total acumulado dos turnos anteriores (em segundos) */
    private long tempoTotalAcumulado;
    
    /** Indica se o jogo está em andamento */
    private boolean jogoEmAndamento;
    
    /** Indica se o jogador venceu */
    private boolean vitoria;
    
    /** Jogador atual (agregação) */
    private Jogador jogador;
    
    /** Dificuldade atual do jogo */
    private Dificuldade dificuldade;
    
    /** Número do turno atual (1, 2 ou 3) */
    private int turnoAtual;
    
    /** Número total de turnos por jogo */
    private static final int TOTAL_TURNOS = 3;
    
    /** Caminho padrão para salvar o jogo */
    private static final String ARQUIVO_SAVE = "save/jogo.sav";

    /**
     * Construtor que cria uma nova instância do jogo.
     * <p>
     * Inicializa o jogo no estado MENU com valores padrão.
     * </p>
     */
    public Game() {
        this.movimentos = 0;
        this.jogoEmAndamento = false;
        this.estado = EstadoJogo.MENU;
        this.pontuacao = 0;
        this.turnoAtual = 0;
        this.movimentosUltimoTurno = 0;
        this.tempoUltimoTurno = 0;
        this.tempoTotalAcumulado = 0;
        this.bateuRecorde = false;
    }

    /**
     * Inicia um novo jogo com a dificuldade escolhida.
     * <p>
     * O jogador joga 3 turnos da mesma dificuldade escolhida.
     * </p>
     * 
     * @param dificuldade A dificuldade escolhida pelo jogador
     */
    public void iniciarNovoJogo(Dificuldade dificuldade) {
        // Reinicia o jogo
        this.dificuldade = dificuldade;
        this.turnoAtual = 1; // Começa no turno 1
        this.movimentos = 0;
        this.pontuacao = 0; // Pontuação do jogo começa em zero
        this.movimentosUltimoTurno = 0;
        this.tempoUltimoTurno = 0;
        this.tempoTotalAcumulado = 0; // Zera o tempo total acumulado
        this.bateuRecorde = false; // Reseta o flag de recorde
        this.tempoInicio = System.currentTimeMillis();
        this.jogoEmAndamento = true;
        this.vitoria = false;
        this.estado = EstadoJogo.JOGANDO;
        
        // Cria o primeiro tabuleiro
        this.tabuleiro = new Tabuleiro(dificuldade.getDimensao());
    }

    /**

     * <p> a célula clicada e suas adjacentes, incrementa o contador
     * Alterna a célula clicada e suas adjacentes, incrementa o contador e verifica se o jogador venceu.
     * </p>
     * 
     * @param linha Linha da célula clicada
     * @param coluna Coluna da célula clicada
     * @throws MovimentoInvalidoException Se as coordenadas forem inválidas
     */
    public void processarJogada(int linha, int coluna) throws MovimentoInvalidoException {
        // Só processa se o jogo estiver valendo
        if (tabuleiro != null && jogoEmAndamento) {
            tabuleiro.alternarCelula(linha, coluna);
            this.movimentos++;
            verificarVitoria(); // Verifica imediatamente após o movimento
        }
    }

    /**
     * Verifica se o jogador venceu o jogo.
     * <p>
     * Se todas as células estiverem desligadas, marca a vitória e
     * processa os pontos.
     * </p>
     */
    private void verificarVitoria() {
        if (tabuleiro != null && tabuleiro.verificarVitoria()) {
            this.jogoEmAndamento = false; // Para o jogo
            this.vitoria = true;          // Marca vitória
            this.estado = EstadoJogo.VITORIA;
            processarVitoria();
        }
    }
    
    /**
     * Processa a vitória do jogador, calculando pontuação e atualizando estatísticas.
     * <p>
     * A fórmula de pontuação é: (1000 / movimentos) × (300 / tempo_segundos) × multiplicador_dificuldade
     * </p>
     * <p>
     * Após processar a vitória, verifica se há próxima dificuldade e avança automaticamente.
     * </p>
     */
    public void processarVitoria() {
        if (tabuleiro != null && dificuldade != null && movimentos > 0) {
            long tempoSegundos = getTempoDecorrrido();
            int pontuacaoTurno = 0;
            
            // Salva informações do turno antes de avançar (para exibição)
            this.movimentosUltimoTurno = this.movimentos;
            this.tempoUltimoTurno = tempoSegundos;
            
            // Acumula o tempo deste turno ao tempo total
            this.tempoTotalAcumulado += tempoSegundos;
            
            // Calcula a pontuação do turno apenas se tempo > 0
            if (tempoSegundos > 0 && movimentos > 0) {
                // Fórmula: (1000 / movimentos) × (300 / tempo_segundos) × multiplicador_dificuldade
                double pontosBase = (1000.0 / movimentos) * (300.0 / tempoSegundos);
                double pontuacaoCalculada = pontosBase * dificuldade.getMultiplicador();
                pontuacaoTurno = (int) Math.round(pontuacaoCalculada);
                
                // Garante que a pontuação não seja negativa
                if (pontuacaoTurno < 0) {
                    pontuacaoTurno = 0;
                }
            }

            // Incrementa a pontuação do jogo com a pontuação obtida neste turno
            this.pontuacao += pontuacaoTurno;
            
            // Garante que a pontuação total não seja negativa
            if (this.pontuacao < 0) {
                this.pontuacao = 0;
            }
            
            if (jogador != null) {
                jogador.adicionarPontuacao(pontuacaoTurno);
                jogador.atualizarRecorde(dificuldade, pontuacaoTurno);
            }
            
            // Verifica se há próximo turno
            boolean avancou = avancarParaProximoTurno();
            
            // Se completou todos os turnos, salva a pontuação e verifica recorde
            if (!avancou && completouTodosTurnos()) {
                salvarPontuacaoFinal(); // Salva e atualiza bateuRecorde
            }
        }
    }
    
    /**
     * Avança para o próximo turno.
     * <p>
     * Se houver próximo turno (até 3 turnos), inicia um novo tabuleiro com a mesma dificuldade.
     * Se completou todos os 3 turnos, marca o jogo como completamente vencido.
     * </p>
     * 
     * @return true se avançou para próximo turno, false se completou todos os turnos
     */
    public boolean avancarParaProximoTurno() {
        turnoAtual++;
        
        if (turnoAtual <= TOTAL_TURNOS) {
            // Ainda há turnos para completar
            // Mantém a mesma dificuldade
            
            // Reseta contadores para o novo turno (mas mantém a pontuação do jogo e tempo total)
            this.movimentos = 0;
            this.tempoInicio = System.currentTimeMillis(); // Novo tempo de início para o próximo turno
            this.vitoria = false;
            this.jogoEmAndamento = true;
            this.estado = EstadoJogo.JOGANDO;
            
            // Cria novo tabuleiro com a mesma dificuldade
            this.tabuleiro = new Tabuleiro(dificuldade.getDimensao());
            
            return true;
        } else {
            // Completou todos os 3 turnos!
            this.jogoEmAndamento = false;
            this.estado = EstadoJogo.VITORIA;
            return false;
        }
    }
    
    /**
     * Verifica se o jogador completou todos os turnos.
     * 
     * @return true se completou todos os 3 turnos, false caso contrário
     */
    public boolean completouTodosTurnos() {
        return turnoAtual > TOTAL_TURNOS;
    }
    
    /**
     * Retorna o número do turno atual (1, 2 ou 3).
     * 
     * @return Número do turno atual
     */
    public int getTurnoAtual() {
        return turnoAtual;
    }
    
    /**
     * Retorna o total de turnos por jogo.
     * 
     * @return Total de turnos (sempre 3)
     */
    public int getTotalTurnos() {
        return TOTAL_TURNOS;
    }
    
    /**
     * Reinicia o jogo.
     */
    public void reiniciar() {
        if (tabuleiro != null) {
            tabuleiro.resetar();
        }
        this.movimentos = 0;
        // Não reseta a pontuação do jogo ao reiniciar o turno - mantém a pontuação acumulada
        this.tempoInicio = System.currentTimeMillis();
        this.jogoEmAndamento = true;
        this.vitoria = false;
        this.estado = EstadoJogo.JOGANDO;
    }
    
    /**
     * Salva o estado atual do jogo em um arquivo.
     * <p>
     * Implementação da interface {@code Salvavel}. Persiste o estado completo
     * do jogo (tabuleiro, jogador, pontuação, turno atual, etc.) em um arquivo
     * binário usando serialização de objetos.
     * </p>
     * <p>
     * O arquivo é salvo no caminho definido por {@code ARQUIVO_SAVE}. Se houver
     * erro durante o processo de salvamento, o erro é registrado em log mas não
     * é relançado para não interromper o fluxo do jogo.
     * </p>
     * 
     * @see projeto_final.interfaces.Salvavel#salvar()
     */
    @Override
    public void salvar() {
        try {
            GerenciadorArquivos.salvar(this, ARQUIVO_SAVE);
        } catch (IOException e) {
            System.err.println("Erro ao salvar jogo: " + e.getMessage());
            // Não relança para não quebrar o fluxo do jogo
        }
    }
    
    /**
     * Carrega o estado do jogo a partir de um arquivo.
     * <p>
     * Implementação da interface {@code Salvavel}. Restaura o estado completo
     * do jogo (tabuleiro, jogador, pontuação, turno atual, etc.) a partir de um
     * arquivo binário previamente salvo.
     * </p>
     * <p>
     * O método valida a integridade do arquivo antes de tentar carregá-lo e
     * trata adequadamente casos de arquivo corrompido ou inexistente, lançando
     * {@code RuntimeException} com mensagem apropriada.
     * </p>
     * 
     * @param arquivo Caminho do arquivo a ser carregado
     * @throws RuntimeException Se houver erro ao carregar o arquivo ou se os dados estiverem corrompidos
     * @see projeto_final.interfaces.Salvavel#carregar(String)
     */
    @Override
    public void carregar(String arquivo) {
        try {
            Object obj = GerenciadorArquivos.carregarObjeto(arquivo);
            if (obj instanceof Game) {
                Game jogoSalvo = (Game) obj;
                this.tabuleiro = jogoSalvo.tabuleiro;
                this.estado = jogoSalvo.estado;
                this.pontuacao = jogoSalvo.pontuacao;
                this.movimentos = jogoSalvo.movimentos;
                this.tempoInicio = jogoSalvo.tempoInicio;
                this.jogoEmAndamento = jogoSalvo.jogoEmAndamento;
                this.vitoria = jogoSalvo.vitoria;
                this.jogador = jogoSalvo.jogador;
                this.dificuldade = jogoSalvo.dificuldade;
                this.turnoAtual = jogoSalvo.turnoAtual;
            } else {
                throw new projeto_final.exceptions.DadosCorruptosException(
                    "Arquivo não contém um jogo válido: " + arquivo
                );
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar jogo: " + e.getMessage());
            throw new RuntimeException("Falha ao carregar jogo", e);
        } catch (projeto_final.exceptions.DadosCorruptosException e) {
            System.err.println("Dados corrompidos: " + e.getMessage());
            throw new RuntimeException("Falha ao carregar jogo: dados corrompidos", e);
        }
    }
    
    /**
     * Salva o jogo usando o caminho padrão.
     * <p>
     * Método de conveniência que chama {@link #salvar()}.
     * </p>
     */
    public void salvarJogo() {
        salvar();
    }
    
    /**
     * Carrega o jogo a partir de um arquivo.
     * <p>
     * Método de conveniência que chama {@link #carregar(String)}.
     * Este método trata exceções e não as relança, permitindo que
     * a interface gráfica trate os erros de forma mais amigável.
     * </p>
     * 
     * @param arquivo Caminho do arquivo a ser carregado
     * @throws RuntimeException Se houver erro ao carregar ou se o arquivo estiver corrompido
     */
    public void carregarJogo(String arquivo) {
        try {
            carregar(arquivo);
        } catch (RuntimeException e) {
            // Relança para que a interface possa tratar
            throw e;
        }
    }

    /**
     * Verifica se o jogador venceu o nível atual.
     * <p>
     * Retorna true se o jogador venceu o nível atual, mas ainda não avançou
     * para a próxima dificuldade ou completou todas as dificuldades.
     * </p>
     * 
     * @return true se o jogador venceu o nível atual, false caso contrário
     */
    public boolean isVitoria() {
        return vitoria && !jogoEmAndamento;
    }
    
    /**
     * Verifica se o jogo está em andamento.
     * 
     * @return true se o jogo está em andamento, false caso contrário
     */
    public boolean getJogoEmAndamento() {
        return jogoEmAndamento;
    }

    /**
     * Retorna o tabuleiro do jogo.
     * 
     * @return O tabuleiro atual
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * Retorna o número de movimentos realizados.
     * 
     * @return Número de movimentos
     */
    public int getMovimentos() {
        return movimentos;
    }

    /**
     * Retorna o tempo decorrido desde o início do jogo.
     * 
     * @return Tempo decorrido em segundos, ou 0 se o jogo não estiver em andamento
     */
    public long getTempoDecorrrido() {
        return (System.currentTimeMillis() - tempoInicio) / 1000; // Retorna em segundos
    }
    
    /**
     * Retorna o estado atual do jogo.
     * 
     * @return Estado atual do jogo
     */
    public EstadoJogo getEstado() {
        return estado;
    }
    
    /**
     * Define o estado do jogo.
     * 
     * @param estado Novo estado do jogo
     */
    public void setEstado(EstadoJogo estado) {
        this.estado = estado;
    }
    
    /**
     * Retorna a pontuação do jogo.
     * <p>
     * A pontuação é incrementada a cada turno vencido e mantida durante todo o jogo.
     * </p>
     * 
     * @return Pontuação atual do jogo
     */
    public int getPontuacao() {
        return pontuacao;
    }
    
    /**
     * Retorna os movimentos do último turno completado.
     * 
     * @return Movimentos do último turno vencido
     */
    public int getMovimentosUltimoTurno() {
        return movimentosUltimoTurno;
    }
    
    /**
     * Retorna o tempo do último turno completado (em segundos).
     * 
     * @return Tempo do último turno vencido em segundos
     */
    public long getTempoUltimoTurno() {
        return tempoUltimoTurno;
    }
    
    /**
     * Retorna o tempo total acumulado de todos os turnos (em segundos).
     * 
     * @return Tempo total acumulado em segundos
     */
    public long getTempoTotalAcumulado() {
        return tempoTotalAcumulado;
    }
    
    /** Indica se o jogador bateu um recorde na última partida */
    private boolean bateuRecorde;
    
    /**
     * Salva a pontuação final quando o jogador completa todos os turnos.
     * <p>
     * Cria um registro de pontuação com nome do jogador, dificuldade,
     * tempo total e pontuação final, e salva no arquivo de pontuações.
     * Atualiza apenas se houver batido o recorde anterior.
     * </p>
     * 
     * @return true se houve recorde (novo ou atualizado), false caso contrário
     */
    private boolean salvarPontuacaoFinal() {
        if (jogador != null && dificuldade != null) {
            try {
                String nomeJogador = jogador.getNome();
                String nomeDificuldade = dificuldade.getNome();
                long tempoTotal = tempoTotalAcumulado;
                int pontuacaoFinal = pontuacao;
                
                PontuacaoRecord record = new PontuacaoRecord(
                    nomeJogador,
                    nomeDificuldade,
                    tempoTotal,
                    pontuacaoFinal
                );
                
                boolean houveRecorde = GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record);
                this.bateuRecorde = houveRecorde;
                return houveRecorde;
            } catch (IOException e) {
                System.err.println("Erro ao salvar pontuação: " + e.getMessage());
                // Não relança para não quebrar o fluxo do jogo
                this.bateuRecorde = false;
                return false;
            }
        }
        this.bateuRecorde = false;
        return false;
    }
    
    /**
     * Retorna se o jogador bateu um recorde na última partida.
     * 
     * @return true se bateu recorde, false caso contrário
     */
    public boolean bateuRecorde() {
        return bateuRecorde;
    }
    
    
    /**
     * Retorna o jogador atual.
     * 
     * @return Jogador atual, ou null se não houver jogador associado
     */
    public Jogador getJogador() {
        return jogador;
    }
    
    /**
     * Define o jogador atual.
     * 
     * @param jogador Jogador a ser associado ao jogo
     */
    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
    
    /**
     * Retorna a dificuldade atual do jogo.
     * 
     * @return Dificuldade atual, ou null se não houver dificuldade definida
     */
    public Dificuldade getDificuldade() {
        return dificuldade;
    }
}
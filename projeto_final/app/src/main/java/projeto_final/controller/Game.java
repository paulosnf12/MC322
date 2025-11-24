// Em: src/main/java/projeto_final/controller/Game.java
package projeto_final.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import projeto_final.abstracts.Dificuldade;
import projeto_final.exceptions.MovimentoInvalidoException;
import projeto_final.interfaces.Salvavel;
import projeto_final.model.DificuldadeDificil;
import projeto_final.model.DificuldadeFacil;
import projeto_final.model.DificuldadeMedio;
import projeto_final.model.EstadoJogo;
import projeto_final.model.GerenciadorArquivos;
import projeto_final.model.Jogador;
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
    
    /** Pontuação atual do jogo */
    private int pontuacao;
    
    /** Número de movimentos realizados */
    private int movimentos;
    
    /** Tempo de início do jogo em milissegundos */
    private long tempoInicio;
    
    /** Indica se o jogo está em andamento */
    private boolean jogoEmAndamento;
    
    /** Indica se o jogador venceu */
    private boolean vitoria;
    
    /** Jogador atual (agregação) */
    private Jogador jogador;
    
    /** Dificuldade atual do jogo */
    private Dificuldade dificuldade;
    
    /** Lista de dificuldades na ordem de progressão */
    private List<Dificuldade> dificuldades;
    
    /** Índice da dificuldade atual na progressão */
    private int indiceDificuldadeAtual;
    
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
        this.indiceDificuldadeAtual = 0;
        inicializarDificuldades();
    }
    
    /**
     * Inicializa a lista de dificuldades na ordem de progressão.
     * <p>
     * A ordem é: Fácil → Médio → Difícil
     * </p>
     */
    private void inicializarDificuldades() {
        this.dificuldades = new ArrayList<>();
        this.dificuldades.add(new DificuldadeFacil());
        this.dificuldades.add(new DificuldadeMedio());
        this.dificuldades.add(new DificuldadeDificil());
    }
    
    /**
     * Inicia um novo jogo com a dificuldade especificada.
     * 
     * @param dificuldade Dificuldade do jogo a ser iniciado
     */
    public void iniciarJogo(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
        this.movimentos = 0;
        this.pontuacao = 0;
        this.tempoInicio = System.currentTimeMillis();
        this.jogoEmAndamento = true;
        this.vitoria = false;
        this.estado = EstadoJogo.JOGANDO;
        this.tabuleiro = new Tabuleiro(dificuldade.getDimensao());
    }

    /**
     * Inicia um novo jogo com sistema de progressão de dificuldade.
     * <p>
     * Inicia na primeira dificuldade (Fácil) e progride automaticamente
     * para Médio e depois Difícil conforme o jogador vence cada nível.
     * </p>
     */
    public void iniciarNovoJogo() {
        // Reinicia a progressão
        this.indiceDificuldadeAtual = 0;
        this.movimentos = 0;
        this.pontuacao = 0;
        this.tempoInicio = System.currentTimeMillis();
        this.jogoEmAndamento = true;
        this.vitoria = false;
        this.estado = EstadoJogo.JOGANDO;
        
        // Inicia com a primeira dificuldade (Fácil)
        if (dificuldades == null || dificuldades.isEmpty()) {
            inicializarDificuldades();
        }
        this.dificuldade = dificuldades.get(0);
        this.tabuleiro = new Tabuleiro(dificuldade.getDimensao());
    }

    /**
     * Processa uma jogada do jogador.
     * <p>
     * Alterna a célula clicada e suas adjacentes, incrementa o contador
     * de movimentos e verifica se o jogador venceu.
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
            if (tempoSegundos > 0) {
                // Fórmula: (1000 / movimentos) × (300 / tempo_segundos) × multiplicador_dificuldade
                double pontosBase = (1000.0 / movimentos) * (300.0 / tempoSegundos);
                this.pontuacao = (int) (pontosBase * dificuldade.getMultiplicador());
            } else {
                this.pontuacao = 0;
            }
            
            if (jogador != null) {
                jogador.adicionarPontuacao(pontuacao);
                jogador.atualizarRecorde(dificuldade, pontuacao);
            }
            
            // Verifica se há próxima dificuldade
            avancarParaProximaDificuldade();
        }
    }
    
    /**
     * Avança para a próxima dificuldade na progressão.
     * <p>
     * Se houver próxima dificuldade, inicia um novo tabuleiro com ela.
     * Se completou todas as dificuldades, marca o jogo como completamente vencido.
     * </p>
     * 
     * @return true se avançou para próxima dificuldade, false se completou todas
     */
    public boolean avancarParaProximaDificuldade() {
        if (dificuldades == null || dificuldades.isEmpty()) {
            inicializarDificuldades();
        }
        
        indiceDificuldadeAtual++;
        
        if (indiceDificuldadeAtual < dificuldades.size()) {
            // Ainda há dificuldades para completar
            Dificuldade proximaDificuldade = dificuldades.get(indiceDificuldadeAtual);
            this.dificuldade = proximaDificuldade;
            
            // Reseta contadores para o novo nível
            this.movimentos = 0;
            this.pontuacao = 0;
            this.tempoInicio = System.currentTimeMillis();
            this.vitoria = false;
            this.jogoEmAndamento = true;
            this.estado = EstadoJogo.JOGANDO;
            
            // Cria novo tabuleiro com a próxima dificuldade
            this.tabuleiro = new Tabuleiro(dificuldade.getDimensao());
            
            return true;
        } else {
            // Completou todas as dificuldades!
            this.jogoEmAndamento = false;
            this.estado = EstadoJogo.VITORIA;
            return false;
        }
    }
    
    /**
     * Verifica se o jogador completou todas as dificuldades.
     * 
     * @return true se completou todas as dificuldades, false caso contrário
     */
    public boolean completouTodasDificuldades() {
        if (dificuldades == null || dificuldades.isEmpty()) {
            return false;
        }
        return indiceDificuldadeAtual >= dificuldades.size();
    }
    
    /**
     * Retorna o número da dificuldade atual na progressão (1, 2 ou 3).
     * 
     * @return Número da dificuldade atual (1 = Fácil, 2 = Médio, 3 = Difícil)
     */
    public int getNumeroDificuldadeAtual() {
        return indiceDificuldadeAtual + 1;
    }
    
    /**
     * Retorna o total de dificuldades na progressão.
     * 
     * @return Total de dificuldades (sempre 3)
     */
    public int getTotalDificuldades() {
        if (dificuldades == null || dificuldades.isEmpty()) {
            return 3; // Padrão
        }
        return dificuldades.size();
    }
    
    /**
     * Reinicia o jogo.
     */
    public void reiniciar() {
        if (tabuleiro != null) {
            tabuleiro.resetar();
        }
        this.movimentos = 0;
        this.pontuacao = 0;
        this.tempoInicio = System.currentTimeMillis();
        this.jogoEmAndamento = true;
        this.vitoria = false;
        this.estado = EstadoJogo.JOGANDO;
    }
    
    /**
     * Salva o estado atual do jogo em um arquivo.
     * <p>
     * Implementação da interface {@code Salvavel}.
     * </p>
     * 
     * @throws IOException Se houver erro de I/O ao salvar
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
     * Implementação da interface {@code Salvavel}.
     * </p>
     * 
     * @param arquivo Caminho do arquivo a ser carregado
     * @throws projeto_final.exceptions.DadosCorruptosException Se o arquivo estiver corrompido
     * @throws IOException Se houver erro de I/O
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
     * @throws DadosCorruptosException Se o arquivo estiver corrompido
     * @throws RuntimeException Se houver erro ao carregar
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
        if (!jogoEmAndamento) return 0;
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
     * Retorna a pontuação atual do jogo.
     * 
     * @return Pontuação atual
     */
    public int getPontuacao() {
        return pontuacao;
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
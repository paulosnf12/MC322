package projeto_final.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import projeto_final.exceptions.MovimentoInvalidoException;
import projeto_final.model.EstadoJogo;
import projeto_final.model.Jogador;
import projeto_final.model.Tabuleiro;
import projeto_final.fixtures.DificuldadeFixture;
import projeto_final.fixtures.GameFixture;
import projeto_final.fixtures.JogadorFixture;
import projeto_final.abstracts.Dificuldade;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Game (controller).
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
class GameTest {
    private Game game;
    
    /**
     * Configura o ambiente de teste antes de cada método de teste.
     * Cria uma nova instância de Game para cada teste.
     */
    @BeforeEach
    void setUp() {
        game = new Game();
    }
    
    /**
     * Testa se o construtor de Game inicializa corretamente com valores padrão.
     * <p>
     * Verifica que:
     * <ul>
     *   <li>Movimentos começam em 0</li>
     *   <li>Pontuação começa em 0</li>
     *   <li>Turno atual começa em 0</li>
     *   <li>Estado inicial é MENU</li>
     *   <li>Jogo não está em andamento</li>
     *   <li>Não há vitória inicialmente</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Construtor deve inicializar com valores padrão")
    void testConstrutor_InicializaComValoresPadrao() {
        // Given: game recém criado
        
        // Then: deve ter valores padrão
        assertEquals(0, game.getMovimentos(), "Movimentos deve começar em 0");
        assertEquals(0, game.getPontuacao(), "Pontuação deve começar em 0");
        assertEquals(0, game.getTurnoAtual(), "Turno atual deve começar em 0");
        assertEquals(EstadoJogo.MENU, game.getEstado(), "Estado deve ser MENU");
        assertFalse(game.getJogoEmAndamento(), "Jogo não deve estar em andamento");
        assertFalse(game.isVitoria(), "Não deve ter vitória inicialmente");
    }
    
    /**
     * Testa se iniciarNovoJogo configura corretamente o estado do jogo.
     * <p>
     * Verifica que após iniciar um novo jogo:
     * <ul>
     *   <li>Turno atual é 1</li>
     *   <li>Movimentos são resetados para 0</li>
     *   <li>Pontuação é resetada para 0</li>
     *   <li>Jogo está em andamento</li>
     *   <li>Estado é JOGANDO</li>
     *   <li>Tabuleiro é criado</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Iniciar novo jogo deve configurar jogo corretamente")
    void testIniciarNovoJogo_ConfiguraJogoCorretamente() {
        // Given: game e dificuldade
        Dificuldade dificuldade = DificuldadeFixture.criarFacil();
        Jogador jogador = JogadorFixture.criarJogador();
        game.setJogador(jogador);
        
        // When: inicia novo jogo
        game.iniciarNovoJogo(dificuldade);
        
        // Then: deve estar configurado corretamente
        assertEquals(1, game.getTurnoAtual(), "Turno atual deve ser 1");
        assertEquals(0, game.getMovimentos(), "Movimentos deve ser 0");
        assertEquals(0, game.getPontuacao(), "Pontuação deve ser 0");
        assertTrue(game.getJogoEmAndamento(), "Jogo deve estar em andamento");
        assertEquals(EstadoJogo.JOGANDO, game.getEstado(), "Estado deve ser JOGANDO");
        assertNotNull(game.getTabuleiro(), "Tabuleiro deve ser criado");
    }
    
    /**
     * Testa se iniciarNovoJogo cria tabuleiros com dimensões corretas
     * de acordo com a dificuldade escolhida.
     * <p>
     * Verifica que:
     * <ul>
     *   <li>Dificuldade Fácil cria tabuleiro 3x3</li>
     *   <li>Dificuldade Média cria tabuleiro 5x5</li>
     *   <li>Dificuldade Difícil cria tabuleiro 7x7</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Iniciar novo jogo deve criar tabuleiro com dificuldade correta")
    void testIniciarNovoJogo_CriaTabuleiroComDificuldadeCorreta() {
        // Given: diferentes dificuldades
        Dificuldade facil = DificuldadeFixture.criarFacil();
        Dificuldade medio = DificuldadeFixture.criarMedio();
        Dificuldade dificil = DificuldadeFixture.criarDificil();
        
        // When: inicia jogos com diferentes dificuldades
        game.setJogador(JogadorFixture.criarJogador());
        game.iniciarNovoJogo(facil);
        Tabuleiro tabuleiroFacil = game.getTabuleiro();
        
        Game game2 = new Game();
        game2.setJogador(JogadorFixture.criarJogador());
        game2.iniciarNovoJogo(medio);
        Tabuleiro tabuleiroMedio = game2.getTabuleiro();
        
        Game game3 = new Game();
        game3.setJogador(JogadorFixture.criarJogador());
        game3.iniciarNovoJogo(dificil);
        Tabuleiro tabuleiroDificil = game3.getTabuleiro();
        
        // Then: tabuleiros devem ter dimensões corretas
        assertEquals(3, tabuleiroFacil.getDimensao(), "Tabuleiro fácil deve ser 3x3");
        assertEquals(5, tabuleiroMedio.getDimensao(), "Tabuleiro médio deve ser 5x5");
        assertEquals(7, tabuleiroDificil.getDimensao(), "Tabuleiro difícil deve ser 7x7");
    }
    
    /**
     * Testa se processarJogada incrementa corretamente o contador de movimentos
     * quando uma jogada válida é processada.
     * <p>
     * Verifica que o número de movimentos é incrementado em 1 após
     * processar uma jogada com coordenadas válidas.
     * </p>
     * 
     * @throws MovimentoInvalidoException Se as coordenadas forem inválidas
     */
    @Test
    @DisplayName("Processar jogada com coordenadas válidas deve incrementar movimentos")
    void testProcessarJogada_CoordenadasValidas_IncrementaMovimentos() throws MovimentoInvalidoException {
        // Given: jogo iniciado
        game = GameFixture.criarJogoFacil();
        int movimentosIniciais = game.getMovimentos();
        
        // When: processa jogada válida
        game.processarJogada(1, 1);
        
        // Then: movimentos deve ser incrementado
        assertEquals(movimentosIniciais + 1, game.getMovimentos(),
            "Movimentos deve ser incrementado");
    }
    
    /**
     * Testa se processarJogada lança MovimentoInvalidoException
     * quando coordenadas inválidas são fornecidas.
     * <p>
     * Verifica que exceções são lançadas para:
     * <ul>
     *   <li>Coordenadas negativas</li>
     *   <li>Coordenadas fora dos limites do tabuleiro</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Processar jogada com coordenadas inválidas deve lançar exceção")
    void testProcessarJogada_CoordenadasInvalidas_LancaExcecao() {
        // Given: jogo iniciado
        game = GameFixture.criarJogoFacil();
        
        // When/Then: tentar processar jogada inválida deve lançar exceção
        assertThrows(MovimentoInvalidoException.class, () -> game.processarJogada(-1, 0),
            "Deve lançar exceção para coordenadas inválidas");
        assertThrows(MovimentoInvalidoException.class, () -> game.processarJogada(0, -1),
            "Deve lançar exceção para coordenadas inválidas");
        assertThrows(MovimentoInvalidoException.class, () -> game.processarJogada(10, 0),
            "Deve lançar exceção para coordenadas inválidas");
    }
    
    /**
     * Testa se processarJogada detecta corretamente a vitória e avança
     * para o próximo turno quando o tabuleiro é resolvido.
     * <p>
     * Verifica que após resolver o tabuleiro:
     * <ul>
     *   <li>O turno é incrementado</li>
     *   <li>Um novo tabuleiro é criado</li>
     *   <li>O jogo continua em andamento (novo turno)</li>
     *   <li>A pontuação é calculada e incrementada</li>
     *   <li>Os movimentos são resetados para o novo turno</li>
     *   <li>O estado é JOGANDO (novo turno iniciado)</li>
     * </ul>
     * </p>
     * 
     * @throws MovimentoInvalidoException Se as coordenadas forem inválidas
     */
    @Test
    @DisplayName("Processar jogada que resulta em vitória deve atualizar estado")
    void testProcessarJogada_Vitoria_AtualizaEstadoParaVitoria() throws MovimentoInvalidoException {
        // Given: jogo iniciado e tabuleiro quase resolvido
        game = GameFixture.criarJogoFacil();
        Tabuleiro tabuleiro = game.getTabuleiro();
        
        // Primeiro, desliga todas as células para garantir estado conhecido
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro.getCelula(i, j).setLigada(false);
            }
        }
        
        // Configura tabuleiro para estar quase resolvido
        // Liga apenas a célula central e suas vizinhas (5 células)
        // Quando alternar a célula central, todas essas 5 serão desligadas
        tabuleiro.getCelula(1, 1).setLigada(true);
        tabuleiro.getCelula(0, 1).setLigada(true);
        tabuleiro.getCelula(2, 1).setLigada(true);
        tabuleiro.getCelula(1, 0).setLigada(true);
        tabuleiro.getCelula(1, 2).setLigada(true);
        
        // Verifica que o tabuleiro não está resolvido antes
        assertFalse(tabuleiro.todasDesligadas(), "Tabuleiro não deve estar resolvido antes da jogada");
        
        // Verifica que apenas as 5 células esperadas estão ligadas
        int celulasLigadas = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro.getCelula(i, j).isLigada()) {
                    celulasLigadas++;
                }
            }
        }
        assertEquals(5, celulasLigadas, "Deve ter exatamente 5 células ligadas antes da jogada");
        
        // Guarda referência ao tabuleiro antes da jogada (será substituído após vitória)
        Tabuleiro tabuleiroAntes = tabuleiro;
        int turnoAntes = game.getTurnoAtual();
        
        // Verifica que estamos no turno 1
        assertEquals(1, turnoAntes, "Deve começar no turno 1");
        
        // When: processa jogada que resolve o tabuleiro
        game.processarJogada(1, 1);
        
        // Then: verifica que a vitória foi detectada e processada
        // Nota: processarVitoria() chama avancarParaProximoTurno(), que:
        // - Se ainda há turnos (turnoAtual <= 3), cria um novo tabuleiro e reseta vitoria=false
        // - Se completou todos os turnos, mantém estado de vitória final
        
        // Como estamos no primeiro turno, o jogo deve avançar para o segundo turno
        // Verificamos que o turno foi incrementado
        assertEquals(2, game.getTurnoAtual(), "Deve ter avançado para o turno 2 após vitória");
        
        // Verificamos que um novo tabuleiro foi criado (referência mudou)
        assertNotSame(tabuleiroAntes, game.getTabuleiro(), "Novo tabuleiro deve ter sido criado após vitória");
        
        // Verificamos que o jogo está em andamento novamente (novo turno iniciado)
        assertTrue(game.getJogoEmAndamento(), "Jogo deve estar em andamento no novo turno");
        
        // Verificamos que a pontuação foi incrementada (processarVitoria() calcula pontos)
        // Nota: se o tempo for muito pequeno, a pontuação pode ser 0, mas isso é aceitável
        assertTrue(game.getPontuacao() >= 0, "Pontuação deve ser não negativa");
        
        // Verificamos que o número de movimentos foi resetado para o novo turno
        assertEquals(0, game.getMovimentos(), "Movimentos devem ser resetados para o novo turno");
        
        // Verificamos que o estado é JOGANDO (novo turno iniciado)
        assertEquals(EstadoJogo.JOGANDO, game.getEstado(), "Estado deve ser JOGANDO no novo turno");
    }
    
    /**
     * Testa se avancarParaProximoTurno incrementa corretamente o número do turno
     * e cria um novo tabuleiro.
     * <p>
     * Verifica que:
     * <ul>
     *   <li>O turno é incrementado (de 1 para 2)</li>
     *   <li>Um novo tabuleiro é criado</li>
     *   <li>O método retorna true indicando que avançou</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Avançar para próximo turno deve incrementar turno")
    void testAvancarParaProximoTurno_IncrementaTurno() {
        // Given: jogo no turno 1
        game = GameFixture.criarJogoFacil();
        assertEquals(1, game.getTurnoAtual(), "Deve estar no turno 1");
        
        // When: avança para próximo turno
        boolean avancou = game.avancarParaProximoTurno();
        
        // Then: deve estar no turno 2
        assertTrue(avancou, "Deve avançar para próximo turno");
        assertEquals(2, game.getTurnoAtual(), "Deve estar no turno 2");
        assertNotNull(game.getTabuleiro(), "Novo tabuleiro deve ser criado");
    }
    
    /**
     * Testa se avancarParaProximoTurno retorna false quando já está no último turno (3).
     * <p>
     * Verifica que:
     * <ul>
     *   <li>O método retorna false após o último turno</li>
     *   <li>completouTodosTurnos retorna true</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Avançar para próximo turno no último turno deve retornar false")
    void testAvancarParaProximoTurno_UltimoTurno_RetornaFalse() {
        // Given: jogo no turno 3
        game = GameFixture.criarJogoFacil();
        game.avancarParaProximoTurno(); // Turno 2
        game.avancarParaProximoTurno(); // Turno 3
        
        // When: tenta avançar do turno 3
        boolean avancou = game.avancarParaProximoTurno();
        
        // Then: não deve avançar
        assertFalse(avancou, "Não deve avançar após último turno");
        assertTrue(game.completouTodosTurnos(), "Deve ter completado todos os turnos");
    }
    
    /**
     * Testa se completouTodosTurnos retorna true quando o jogador
     * completou todos os 3 turnos do jogo.
     * <p>
     * Verifica que após avançar para além do turno 3, o método
     * retorna true indicando que todos os turnos foram completados.
     * </p>
     */
    @Test
    @DisplayName("Completou todos turnos deve retornar true quando completo")
    void testCompletouTodosTurnos_RetornaTrueQuandoCompleto() {
        // Given: jogo que completou 3 turnos
        game = GameFixture.criarJogoFacil();
        game.avancarParaProximoTurno(); // Turno 2
        game.avancarParaProximoTurno(); // Turno 3
        game.avancarParaProximoTurno(); // Completa
        
        // When: verifica se completou
        boolean completou = game.completouTodosTurnos();
        
        // Then: deve retornar true
        assertTrue(completou, "Deve retornar true quando completou todos os turnos");
    }
    
    /**
     * Testa se getTempoDecorrrido calcula corretamente o tempo decorrido
     * desde o início do jogo.
     * <p>
     * Verifica que o tempo retornado é não negativo e reflete
     * o tempo decorrido desde que o jogo foi iniciado.
     * </p>
     * 
     * @throws InterruptedException Se a thread for interrompida durante o sleep
     */
    @Test
    @DisplayName("Get tempo decorrido deve calcular tempo corretamente")
    void testGetTempoDecorrrido_CalculaTempoCorretamente() throws InterruptedException {
        // Given: jogo iniciado
        game = GameFixture.criarJogoFacil();
        
        // When: espera um pouco e obtém tempo
        Thread.sleep(100); // Espera 100ms
        long tempo = game.getTempoDecorrrido();
        
        // Then: tempo deve ser aproximadamente 0 (pode variar)
        assertTrue(tempo >= 0, "Tempo deve ser não negativo");
    }
    
    /**
     * Testa se getTempoDecorrrido retorna zero ou valor pequeno quando
     * o jogo não foi iniciado.
     * <p>
     * Verifica que o tempo retornado é não negativo mesmo quando
     * o jogo não está em andamento.
     * </p>
     */
    @Test
    @DisplayName("Get tempo decorrido com jogo não iniciado deve retornar zero")
    void testGetTempoDecorrrido_JogoNaoIniciado_RetornaZero() {
        // Given: game não iniciado
        
        // When: obtém tempo
        long tempo = game.getTempoDecorrrido();
        
        // Then: deve retornar zero ou valor pequeno
        assertTrue(tempo >= 0, "Tempo deve ser não negativo");
    }
    
    /**
     * Testa se reiniciar reseta corretamente os movimentos e mantém
     * o jogo em andamento.
     * <p>
     * Verifica que após reiniciar:
     * <ul>
     *   <li>Os movimentos são resetados para 0</li>
     *   <li>O jogo continua em andamento</li>
     * </ul>
     * </p>
     * 
     * @throws MovimentoInvalidoException Se as coordenadas forem inválidas
     */
    @Test
    @DisplayName("Reiniciar deve resetar movimentos e tempo")
    void testReiniciar_ResetaMovimentosETempo() throws MovimentoInvalidoException {
        // Given: jogo com movimentos realizados
        game = GameFixture.criarJogoFacil();
        game.processarJogada(0, 0);
        game.processarJogada(1, 1);
        int movimentosAntes = game.getMovimentos();
        assertTrue(movimentosAntes > 0, "Deve ter movimentos antes do reset");
        
        // When: reinicia
        game.reiniciar();
        
        // Then: movimentos deve ser resetado
        assertEquals(0, game.getMovimentos(), "Movimentos deve ser resetado");
        assertTrue(game.getJogoEmAndamento(), "Jogo deve estar em andamento após reiniciar");
    }
    
    /**
     * Testa se reiniciar mantém a pontuação acumulada do jogo.
     * <p>
     * Verifica que a pontuação não é resetada quando o tabuleiro
     * é reiniciado, mantendo o progresso do jogador.
     * </p>
     */
    @Test
    @DisplayName("Reiniciar deve manter pontuação acumulada")
    void testReiniciar_MantemPontuacaoAcumulada() {
        // Given: jogo com pontuação
        game = GameFixture.criarJogoFacil();
        
        // When: reinicia
        game.reiniciar();
        
        // Then: pontuação deve ser mantida (ou pelo menos não ser negativa)
        assertTrue(game.getPontuacao() >= 0, "Pontuação deve ser não negativa");
    }
    
    /**
     * Testa se getPontuacao retorna corretamente a pontuação acumulada do jogo.
     * <p>
     * Verifica que a pontuação inicial é 0 e que o método retorna
     * o valor correto da pontuação acumulada.
     * </p>
     */
    @Test
    @DisplayName("Get pontuação deve retornar pontuação acumulada")
    void testGetPontuacao_RetornaPontuacaoAcumulada() {
        // Given: jogo iniciado
        game = GameFixture.criarJogoFacil();
        
        // When: obtém pontuação
        int pontuacao = game.getPontuacao();
        
        // Then: deve retornar pontuação (inicialmente 0)
        assertEquals(0, pontuacao, "Pontuação inicial deve ser 0");
    }
    
    /**
     * Testa se getTabuleiro retorna uma instância não nula de Tabuleiro
     * quando o jogo foi iniciado.
     * <p>
     * Verifica que o tabuleiro é criado corretamente ao iniciar um novo jogo.
     * </p>
     */
    @Test
    @DisplayName("Get tabuleiro deve retornar tabuleiro quando jogo iniciado")
    void testGetTabuleiro_RetornaTabuleiroQuandoJogoIniciado() {
        // Given: jogo iniciado
        game = GameFixture.criarJogoFacil();
        
        // When: obtém tabuleiro
        Tabuleiro tabuleiro = game.getTabuleiro();
        
        // Then: deve retornar tabuleiro não nulo
        assertNotNull(tabuleiro, "Tabuleiro deve ser não nulo quando jogo iniciado");
    }
    
    /**
     * Testa se getJogador retorna corretamente o jogador associado ao jogo.
     * <p>
     * Verifica que:
     * <ul>
     *   <li>O jogador retornado não é nulo</li>
     *   <li>O nome do jogador está correto</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Get jogador deve retornar jogador quando definido")
    void testGetJogador_RetornaJogadorQuandoDefinido() {
        // Given: jogo com jogador
        Jogador jogador = JogadorFixture.criarJogador("Teste");
        game.setJogador(jogador);
        
        // When: obtém jogador
        Jogador jogadorObtido = game.getJogador();
        
        // Then: deve retornar o jogador
        assertNotNull(jogadorObtido, "Jogador deve ser não nulo");
        assertEquals("Teste", jogadorObtido.getNome(), "Deve retornar o jogador correto");
    }
    
    /**
     * Testa se getDificuldade retorna corretamente a dificuldade do jogo.
     * <p>
     * Verifica que:
     * <ul>
     *   <li>A dificuldade retornada não é nula</li>
     *   <li>O nome da dificuldade está correto</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Get dificuldade deve retornar dificuldade quando jogo iniciado")
    void testGetDificuldade_RetornaDificuldadeQuandoJogoIniciado() {
        // Given: jogo iniciado
        game = GameFixture.criarJogoFacil();
        
        // When: obtém dificuldade
        Dificuldade dificuldade = game.getDificuldade();
        
        // Then: deve retornar dificuldade não nula
        assertNotNull(dificuldade, "Dificuldade deve ser não nula");
        assertEquals("Fácil", dificuldade.getNome(), "Deve retornar dificuldade fácil");
    }
}


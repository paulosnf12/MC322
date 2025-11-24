package projeto_final.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import projeto_final.abstracts.Dificuldade;
import projeto_final.fixtures.DificuldadeFixture;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Jogador.
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
class JogadorTest {
    private Jogador jogador;
    
    @BeforeEach
    void setUp() {
        jogador = new Jogador("JogadorTeste");
    }
    
    @Test
    @DisplayName("Construtor deve criar jogador com nome")
    void testConstrutor_CriaJogadorComNome() {
        // Given: nome específico
        String nome = "João";
        
        // When: cria jogador
        Jogador novoJogador = new Jogador(nome);
        
        // Then: deve ter o nome correto
        assertEquals(nome, novoJogador.getNome(), "Jogador deve ter o nome especificado");
    }
    
    @Test
    @DisplayName("Construtor deve inicializar com valores padrão")
    void testConstrutor_InicializaComValoresPadrao() {
        // Given: jogador recém criado
        
        // Then: deve ter valores padrão
        assertEquals(0, jogador.getPontuacaoTotal(), "Pontuação total deve começar em 0");
        assertEquals(0, jogador.getPartidasJogadas(), "Partidas jogadas deve começar em 0");
    }
    
    @Test
    @DisplayName("Adicionar pontuação deve incrementar pontuação total")
    void testAdicionarPontuacao_IncrementaPontuacaoTotal() {
        // Given: jogador com pontuação inicial
        int pontuacaoInicial = jogador.getPontuacaoTotal();
        int pontosAdicionar = 100;
        
        // When: adiciona pontuação
        jogador.adicionarPontuacao(pontosAdicionar);
        
        // Then: pontuação total deve ser incrementada
        assertEquals(pontuacaoInicial + pontosAdicionar, jogador.getPontuacaoTotal(),
            "Pontuação total deve ser incrementada");
    }
    
    @Test
    @DisplayName("Adicionar pontuação deve incrementar partidas jogadas")
    void testAdicionarPontuacao_IncrementaPartidasJogadas() {
        // Given: jogador com número inicial de partidas
        int partidasIniciais = jogador.getPartidasJogadas();
        
        // When: adiciona pontuação
        jogador.adicionarPontuacao(50);
        
        // Then: número de partidas deve ser incrementado
        assertEquals(partidasIniciais + 1, jogador.getPartidasJogadas(),
            "Partidas jogadas deve ser incrementado");
    }
    
    @Test
    @DisplayName("Adicionar pontuação múltiplas vezes deve acumular")
    void testAdicionarPontuacao_MultiplasVezes_Acumula() {
        // Given: jogador inicial
        
        // When: adiciona pontuação múltiplas vezes
        jogador.adicionarPontuacao(100);
        jogador.adicionarPontuacao(200);
        jogador.adicionarPontuacao(50);
        
        // Then: pontuação total deve ser a soma
        assertEquals(350, jogador.getPontuacaoTotal(),
            "Pontuação total deve ser a soma de todas as pontuações");
        assertEquals(3, jogador.getPartidasJogadas(),
            "Partidas jogadas deve ser 3");
    }
    
    @Test
    @DisplayName("Get pontuação total deve retornar pontuação acumulada")
    void testGetPontuacaoTotal_RetornaPontuacaoAcumulada() {
        // Given: jogador com pontuações adicionadas
        jogador.adicionarPontuacao(100);
        jogador.adicionarPontuacao(200);
        
        // When: obtém pontuação total
        int pontuacaoTotal = jogador.getPontuacaoTotal();
        
        // Then: deve retornar a soma
        assertEquals(300, pontuacaoTotal, "Deve retornar a pontuação acumulada");
    }
    
    @Test
    @DisplayName("Atualizar recorde com pontuação maior deve atualizar recorde")
    void testAtualizarRecorde_NovaPontuacaoMaior_AtualizaRecorde() {
        // Given: jogador com recorde inicial
        Dificuldade dificuldade = DificuldadeFixture.criarFacil();
        jogador.atualizarRecorde(dificuldade, 100);
        assertEquals(100, jogador.getRecorde(dificuldade), "Recorde inicial deve ser 100");
        
        // When: atualiza com pontuação maior
        jogador.atualizarRecorde(dificuldade, 200);
        
        // Then: recorde deve ser atualizado
        assertEquals(200, jogador.getRecorde(dificuldade),
            "Recorde deve ser atualizado quando nova pontuação é maior");
    }
    
    @Test
    @DisplayName("Atualizar recorde com pontuação menor não deve atualizar")
    void testAtualizarRecorde_NovaPontuacaoMenor_NaoAtualiza() {
        // Given: jogador com recorde inicial
        Dificuldade dificuldade = DificuldadeFixture.criarFacil();
        jogador.atualizarRecorde(dificuldade, 200);
        assertEquals(200, jogador.getRecorde(dificuldade), "Recorde inicial deve ser 200");
        
        // When: atualiza com pontuação menor
        jogador.atualizarRecorde(dificuldade, 100);
        
        // Then: recorde não deve ser atualizado
        assertEquals(200, jogador.getRecorde(dificuldade),
            "Recorde não deve ser atualizado quando nova pontuação é menor");
    }
    
    @Test
    @DisplayName("Atualizar recorde com pontuação igual não deve atualizar")
    void testAtualizarRecorde_NovaPontuacaoIgual_NaoAtualiza() {
        // Given: jogador com recorde inicial
        Dificuldade dificuldade = DificuldadeFixture.criarFacil();
        jogador.atualizarRecorde(dificuldade, 200);
        
        // When: atualiza com pontuação igual
        jogador.atualizarRecorde(dificuldade, 200);
        
        // Then: recorde deve permanecer o mesmo
        assertEquals(200, jogador.getRecorde(dificuldade),
            "Recorde deve permanecer o mesmo quando nova pontuação é igual");
    }
    
    @Test
    @DisplayName("Get recorde sem recorde deve retornar zero")
    void testGetRecorde_SemRecorde_RetornaZero() {
        // Given: jogador sem recordes
        Dificuldade dificuldade = DificuldadeFixture.criarFacil();
        
        // When: obtém recorde
        int recorde = jogador.getRecorde(dificuldade);
        
        // Then: deve retornar zero
        assertEquals(0, recorde, "Deve retornar 0 quando não há recorde");
    }
    
    @Test
    @DisplayName("Get recorde com recorde deve retornar recorde")
    void testGetRecorde_ComRecorde_RetornaRecorde() {
        // Given: jogador com recorde
        Dificuldade dificuldade = DificuldadeFixture.criarFacil();
        int recordeEsperado = 500;
        jogador.atualizarRecorde(dificuldade, recordeEsperado);
        
        // When: obtém recorde
        int recorde = jogador.getRecorde(dificuldade);
        
        // Then: deve retornar o recorde
        assertEquals(recordeEsperado, recorde, "Deve retornar o recorde armazenado");
    }
    
    @Test
    @DisplayName("Jogador deve manter recordes separados por dificuldade")
    void testRecordes_SeparadosPorDificuldade() {
        // Given: diferentes dificuldades
        Dificuldade facil = DificuldadeFixture.criarFacil();
        Dificuldade medio = DificuldadeFixture.criarMedio();
        Dificuldade dificil = DificuldadeFixture.criarDificil();
        
        // When: atualiza recordes para cada dificuldade
        jogador.atualizarRecorde(facil, 100);
        jogador.atualizarRecorde(medio, 200);
        jogador.atualizarRecorde(dificil, 300);
        
        // Then: cada dificuldade deve ter seu próprio recorde
        assertEquals(100, jogador.getRecorde(facil), "Fácil deve ter recorde 100");
        assertEquals(200, jogador.getRecorde(medio), "Médio deve ter recorde 200");
        assertEquals(300, jogador.getRecorde(dificil), "Difícil deve ter recorde 300");
    }
    
    @Test
    @DisplayName("Get nome deve retornar nome do jogador")
    void testGetNome_RetornaNomeDoJogador() {
        // Given: jogador com nome específico
        String nomeEsperado = "Maria";
        Jogador jogadorComNome = new Jogador(nomeEsperado);
        
        // When: obtém nome
        String nome = jogadorComNome.getNome();
        
        // Then: deve retornar o nome correto
        assertEquals(nomeEsperado, nome, "Deve retornar o nome do jogador");
    }
    
    @Test
    @DisplayName("Get partidas jogadas deve retornar número correto")
    void testGetPartidasJogadas_RetornaNumeroCorreto() {
        // Given: jogador inicial
        
        // When: adiciona pontuações (cada uma conta como uma partida)
        jogador.adicionarPontuacao(100);
        jogador.adicionarPontuacao(200);
        
        // Then: número de partidas deve ser correto
        assertEquals(2, jogador.getPartidasJogadas(),
            "Deve retornar o número correto de partidas jogadas");
    }
}


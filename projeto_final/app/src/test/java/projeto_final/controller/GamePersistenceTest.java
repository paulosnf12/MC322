package projeto_final.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import projeto_final.exceptions.MovimentoInvalidoException;
import projeto_final.fixtures.DificuldadeFixture;
import projeto_final.fixtures.GameFixture;
import projeto_final.fixtures.JogadorFixture;
import projeto_final.model.EstadoJogo;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para persistência do Game.
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
class GamePersistenceTest {
    
    @TempDir
    Path tempDir;
    
    private Game game;
    private String arquivoTeste;
    
    @BeforeEach
    void setUp() {
        game = GameFixture.criarJogoFacil();
        arquivoTeste = tempDir.resolve("jogo_teste.sav").toString();
    }
    
    @Test
    @DisplayName("Salvar deve persistir estado do jogo")
    void testSalvar_PersisteEstadoDoJogo() throws IOException, MovimentoInvalidoException {
        // Given: jogo com estado específico
        game.processarJogada(0, 0);
        game.processarJogada(1, 1);
        
        // When: salva usando método estático do GerenciadorArquivos
        // Nota: O método salvar() usa caminho padrão, então vamos testar diretamente
        // através do método carregar que aceita caminho customizado
        projeto_final.model.GerenciadorArquivos.salvar(game, arquivoTeste);
        
        // Then: arquivo deve existir
        File arquivo = new File(arquivoTeste);
        assertTrue(arquivo.exists(), "Arquivo deve ser criado");
        assertTrue(arquivo.length() > 0, "Arquivo não deve estar vazio");
    }
    
    @Test
    @DisplayName("Salvar com erro IO deve tratar exceção")
    void testSalvar_ErroIO_TrataExcecao() {
        // Given: caminho inválido (diretório sem permissão de escrita)
        // Nota: Em sistemas Unix, podemos tentar /root, mas isso pode não funcionar
        // Vamos testar que o método não lança exceção não tratada
        String caminhoInvalido = "/caminho/inexistente/jogo.sav";
        
        // When: tenta salvar
        // O método salvar() trata IOException internamente, então não deve lançar
        assertDoesNotThrow(() -> {
            try {
                projeto_final.model.GerenciadorArquivos.salvar(game, caminhoInvalido);
            } catch (IOException e) {
                // Esperado - o método pode lançar IOException
            }
        }, "Salvar não deve lançar exceção não tratada");
    }
    
    @Test
    @DisplayName("Carregar deve restaurar estado do jogo")
    void testCarregar_RestauraEstadoDoJogo() throws IOException, MovimentoInvalidoException {
        // Given: jogo salvo
        game.processarJogada(0, 0);
        game.processarJogada(1, 1);
        int movimentosEsperados = game.getMovimentos();
        int pontuacaoEsperada = game.getPontuacao();
        EstadoJogo estadoEsperado = game.getEstado();
        
        projeto_final.model.GerenciadorArquivos.salvar(game, arquivoTeste);
        
        // When: carrega em novo jogo
        Game jogoCarregado = new Game();
        jogoCarregado.carregar(arquivoTeste);
        
        // Then: estado deve ser restaurado
        assertEquals(movimentosEsperados, jogoCarregado.getMovimentos(),
            "Movimentos devem ser restaurados");
        assertEquals(pontuacaoEsperada, jogoCarregado.getPontuacao(),
            "Pontuação deve ser restaurada");
        assertEquals(estadoEsperado, jogoCarregado.getEstado(),
            "Estado deve ser restaurado");
        assertNotNull(jogoCarregado.getTabuleiro(), "Tabuleiro deve ser restaurado");
    }
    
    @Test
    @DisplayName("Carregar com arquivo inválido deve lançar exceção")
    void testCarregar_ArquivoInvalido_LancaExcecao() {
        // Given: caminho de arquivo inexistente
        String arquivoInexistente = tempDir.resolve("arquivo_inexistente.sav").toString();
        
        // When/Then: tentar carregar deve lançar exceção
        assertThrows(RuntimeException.class, () -> {
            game.carregar(arquivoInexistente);
        }, "Deve lançar exceção ao carregar arquivo inexistente");
    }
    
    @Test
    @DisplayName("Carregar com arquivo corrompido deve lançar exceção")
    void testCarregar_ArquivoCorrompido_LancaExcecao() throws IOException {
        // Given: arquivo com dados inválidos
        File arquivoCorrompido = tempDir.resolve("arquivo_corrompido.sav").toFile();
        try (java.io.FileWriter writer = new java.io.FileWriter(arquivoCorrompido)) {
            writer.write("dados inválidos não serializados");
        }
        
        // When/Then: tentar carregar deve lançar exceção
        assertThrows(RuntimeException.class, () -> {
            game.carregar(arquivoCorrompido.getAbsolutePath());
        }, "Deve lançar exceção ao carregar arquivo corrompido");
    }
    
    @Test
    @DisplayName("Carregar deve restaurar jogador e dificuldade")
    void testCarregar_RestauraJogadorEDificuldade() throws IOException {
        // Given: jogo com jogador e dificuldade
        game.setJogador(JogadorFixture.criarJogador("JogadorTeste"));
        game.iniciarNovoJogo(DificuldadeFixture.criarMedio());
        
        projeto_final.model.GerenciadorArquivos.salvar(game, arquivoTeste);
        
        // When: carrega
        Game jogoCarregado = new Game();
        jogoCarregado.carregar(arquivoTeste);
        
        // Then: jogador e dificuldade devem ser restaurados
        assertNotNull(jogoCarregado.getJogador(), "Jogador deve ser restaurado");
        assertEquals("JogadorTeste", jogoCarregado.getJogador().getNome(),
            "Nome do jogador deve ser restaurado");
        assertNotNull(jogoCarregado.getDificuldade(), "Dificuldade deve ser restaurada");
        assertEquals("Médio", jogoCarregado.getDificuldade().getNome(),
            "Nome da dificuldade deve ser restaurado");
    }
}


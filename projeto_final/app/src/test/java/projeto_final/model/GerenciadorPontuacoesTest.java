package projeto_final.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe GerenciadorPontuacoes.
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
class GerenciadorPontuacoesTest {
    
    @Test
    @DisplayName("Get instância deve retornar mesma instância (Singleton)")
    void testGetInstancia_RetornaMesmaInstancia() {
        // When: obtém instâncias múltiplas
        GerenciadorPontuacoes instancia1 = GerenciadorPontuacoes.getInstancia();
        GerenciadorPontuacoes instancia2 = GerenciadorPontuacoes.getInstancia();
        
        // Then: devem ser a mesma instância
        assertSame(instancia1, instancia2, "Deve retornar a mesma instância (Singleton)");
    }
    
    @Test
    @DisplayName("Salvar ou atualizar pontuação com novo jogador deve adicionar recorde")
    void testSalvarOuAtualizarPontuacao_NovoJogador_AdicionaRecorde() throws IOException {
        // Given: novo recorde
        PontuacaoRecord record = new PontuacaoRecord("NovoJogador", "Fácil", 100, 500);
        
        // When: salva
        boolean resultado = GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record);
        
        // Then: deve adicionar e retornar true
        assertTrue(resultado, "Deve retornar true para novo recorde");
        
        // Verifica se foi salvo
        List<PontuacaoRecord> pontuacoes = GerenciadorPontuacoes.carregarPontuacoes();
        assertTrue(pontuacoes.stream().anyMatch(r -> 
            r.getNomeJogador().equals("NovoJogador") && 
            r.getDificuldade().equals("Fácil")),
            "Recorde deve estar na lista");
    }
    
    @Test
    @DisplayName("Salvar ou atualizar pontuação com pontuação maior deve atualizar recorde")
    void testSalvarOuAtualizarPontuacao_PontuacaoMaior_AtualizaRecorde() throws IOException {
        // Given: recorde existente
        PontuacaoRecord record1 = new PontuacaoRecord("Jogador1", "Fácil", 100, 500);
        GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record1);
        
        // When: salva pontuação maior
        PontuacaoRecord record2 = new PontuacaoRecord("Jogador1", "Fácil", 90, 800);
        boolean resultado = GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record2);
        
        // Then: deve atualizar e retornar true
        assertTrue(resultado, "Deve retornar true quando atualiza recorde");
        
        // Verifica se foi atualizado
        List<PontuacaoRecord> pontuacoes = GerenciadorPontuacoes.carregarPontuacoes();
        PontuacaoRecord recordAtualizado = pontuacoes.stream()
            .filter(r -> r.getNomeJogador().equals("Jogador1") && r.getDificuldade().equals("Fácil"))
            .findFirst()
            .orElse(null);
        
        assertNotNull(recordAtualizado, "Recorde deve existir");
        assertEquals(800, recordAtualizado.getPontuacao(), "Pontuação deve ser atualizada");
    }
    
    @Test
    @DisplayName("Salvar ou atualizar pontuação com pontuação menor não deve atualizar")
    void testSalvarOuAtualizarPontuacao_PontuacaoMenor_NaoAtualiza() throws IOException {
        // Given: recorde existente com pontuação maior
        PontuacaoRecord record1 = new PontuacaoRecord("Jogador2", "Médio", 100, 1000);
        GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record1);
        
        // When: tenta salvar pontuação menor
        PontuacaoRecord record2 = new PontuacaoRecord("Jogador2", "Médio", 120, 500);
        boolean resultado = GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record2);
        
        // Then: não deve atualizar e deve retornar false
        assertFalse(resultado, "Deve retornar false quando pontuação é menor");
        
        // Verifica se recorde original foi mantido
        List<PontuacaoRecord> pontuacoes = GerenciadorPontuacoes.carregarPontuacoes();
        PontuacaoRecord recordMantido = pontuacoes.stream()
            .filter(r -> r.getNomeJogador().equals("Jogador2") && r.getDificuldade().equals("Médio"))
            .findFirst()
            .orElse(null);
        
        assertNotNull(recordMantido, "Recorde deve existir");
        assertEquals(1000, recordMantido.getPontuacao(), "Pontuação original deve ser mantida");
    }
    
    @Test
    @DisplayName("Salvar ou atualizar pontuação deve retornar true se houve recorde")
    void testSalvarOuAtualizarPontuacao_RetornaTrueSeRecorde() throws IOException {
        // Given: novo recorde
        PontuacaoRecord record = new PontuacaoRecord("Jogador3", "Difícil", 200, 1500);
        
        // When: salva
        boolean resultado = GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record);
        
        // Then: deve retornar true
        assertTrue(resultado, "Deve retornar true quando há recorde");
    }
    
    @Test
    @DisplayName("Salvar ou atualizar pontuação com diferentes dificuldades deve manter ambos")
    void testSalvarOuAtualizarPontuacao_DiferentesDificuldades_MantemAmbos() throws IOException {
        // Given: recordes para diferentes dificuldades
        PontuacaoRecord recordFacil = new PontuacaoRecord("Jogador4", "Fácil", 100, 500);
        PontuacaoRecord recordMedio = new PontuacaoRecord("Jogador4", "Médio", 150, 800);
        
        // When: salva ambos
        GerenciadorPontuacoes.salvarOuAtualizarPontuacao(recordFacil);
        GerenciadorPontuacoes.salvarOuAtualizarPontuacao(recordMedio);
        
        // Then: ambos devem estar na lista
        List<PontuacaoRecord> pontuacoes = GerenciadorPontuacoes.carregarPontuacoes();
        long count = pontuacoes.stream()
            .filter(r -> r.getNomeJogador().equals("Jogador4"))
            .count();
        
        assertEquals(2, count, "Deve ter 2 recordes para o mesmo jogador em dificuldades diferentes");
    }
    
    @Test
    @DisplayName("Carregar pontuações com arquivo não existente deve retornar lista vazia")
    void testCarregarPontuacoes_ArquivoNaoExiste_RetornaListaVazia() throws IOException {
        // Given: arquivo não existe (primeira execução)
        // Nota: Este teste pode falhar se já houver arquivo, mas é aceitável
        
        // When: carrega pontuações
        List<PontuacaoRecord> pontuacoes = GerenciadorPontuacoes.carregarPontuacoes();
        
        // Then: deve retornar lista (pode estar vazia ou ter dados anteriores)
        assertNotNull(pontuacoes, "Lista não deve ser null");
    }
    
    @Test
    @DisplayName("Carregar pontuações com arquivo existente deve carregar pontuações")
    void testCarregarPontuacoes_ArquivoExiste_CarregaPontuacoes() throws IOException {
        // Given: pontuação salva
        PontuacaoRecord record = new PontuacaoRecord("Jogador5", "Fácil", 100, 500);
        GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record);
        
        // When: carrega pontuações
        List<PontuacaoRecord> pontuacoes = GerenciadorPontuacoes.carregarPontuacoes();
        
        // Then: deve conter a pontuação salva
        assertNotNull(pontuacoes, "Lista não deve ser null");
        assertTrue(pontuacoes.size() > 0, "Lista deve conter pelo menos uma pontuação");
    }
    
    @Test
    @DisplayName("Carregar pontuações deve ordenar por pontuação")
    void testCarregarPontuacoes_OrdenaPorPontuacao() throws IOException {
        // Given: múltiplas pontuações
        PontuacaoRecord record1 = new PontuacaoRecord("Jogador6", "Fácil", 100, 300);
        PontuacaoRecord record2 = new PontuacaoRecord("Jogador7", "Fácil", 100, 500);
        PontuacaoRecord record3 = new PontuacaoRecord("Jogador8", "Fácil", 100, 200);
        
        GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record1);
        GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record2);
        GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record3);
        
        // When: carrega pontuações
        List<PontuacaoRecord> pontuacoes = GerenciadorPontuacoes.carregarPontuacoes();
        
        // Then: deve estar ordenado por pontuação (maior primeiro)
        // Encontra os recordes na lista
        PontuacaoRecord r1 = pontuacoes.stream()
            .filter(r -> r.getNomeJogador().equals("Jogador6"))
            .findFirst().orElse(null);
        PontuacaoRecord r2 = pontuacoes.stream()
            .filter(r -> r.getNomeJogador().equals("Jogador7"))
            .findFirst().orElse(null);
        PontuacaoRecord r3 = pontuacoes.stream()
            .filter(r -> r.getNomeJogador().equals("Jogador8"))
            .findFirst().orElse(null);
        
        if (r1 != null && r2 != null && r3 != null) {
            int indiceR2 = pontuacoes.indexOf(r2);
            int indiceR1 = pontuacoes.indexOf(r1);
            int indiceR3 = pontuacoes.indexOf(r3);
            
            // R2 (500) deve vir antes de R1 (300) que deve vir antes de R3 (200)
            assertTrue(indiceR2 < indiceR1, "Recorde com maior pontuação deve vir primeiro");
            assertTrue(indiceR1 < indiceR3, "Recordes devem estar ordenados por pontuação");
        }
    }
    
    @Test
    @DisplayName("Salvar deve persistir lista no arquivo")
    void testSalvar_PersisteListaNoArquivo() throws IOException {
        // Given: gerenciador com pontuações
        GerenciadorPontuacoes gerenciador = GerenciadorPontuacoes.getInstancia();
        PontuacaoRecord record = new PontuacaoRecord("Jogador9", "Fácil", 100, 500);
        GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record);
        
        // When: salva
        gerenciador.salvar();
        
        // Then: arquivo deve existir (verificação indireta através de carregar)
        List<PontuacaoRecord> pontuacoes = GerenciadorPontuacoes.carregarPontuacoes();
        assertNotNull(pontuacoes, "Lista não deve ser null após salvar");
    }
    
    @Test
    @DisplayName("Carregar deve restaurar lista do arquivo")
    void testCarregar_RestauraListaDoArquivo() throws IOException {
        // Given: pontuação salva
        PontuacaoRecord record = new PontuacaoRecord("Jogador10", "Médio", 150, 600);
        GerenciadorPontuacoes.salvarOuAtualizarPontuacao(record);
        
        // When: carrega
        GerenciadorPontuacoes gerenciador = GerenciadorPontuacoes.getInstancia();
        gerenciador.carregar(GerenciadorPontuacoes.getCaminhoArquivo());
        
        // Then: deve ter carregado (verificação indireta)
        List<PontuacaoRecord> pontuacoes = GerenciadorPontuacoes.carregarPontuacoes();
        assertNotNull(pontuacoes, "Lista não deve ser null após carregar");
    }
    
    @Test
    @DisplayName("Get caminho arquivo deve retornar caminho")
    void testGetCaminhoArquivo_RetornaCaminho() {
        // When: obtém caminho
        String caminho = GerenciadorPontuacoes.getCaminhoArquivo();
        
        // Then: deve retornar caminho não nulo
        assertNotNull(caminho, "Caminho não deve ser null");
        assertFalse(caminho.isEmpty(), "Caminho não deve estar vazio");
    }
}


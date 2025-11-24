package projeto_final.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe PontuacaoRecord.
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 */
class PontuacaoRecordTest {
    
    @Test
    @DisplayName("Construtor deve criar record com dados corretos")
    void testConstrutor_CriaRecordComDadosCorretos() {
        // Given: dados do record
        String nomeJogador = "João";
        String dificuldade = "Fácil";
        long tempoTotal = 120;
        int pontuacao = 500;
        
        // When: cria record
        PontuacaoRecord record = new PontuacaoRecord(nomeJogador, dificuldade, tempoTotal, pontuacao);
        
        // Then: deve ter os dados corretos
        assertEquals(nomeJogador, record.getNomeJogador(), "Nome do jogador deve ser correto");
        assertEquals(dificuldade, record.getDificuldade(), "Dificuldade deve ser correta");
        assertEquals(tempoTotal, record.getTempoTotal(), "Tempo total deve ser correto");
        assertEquals(pontuacao, record.getPontuacao(), "Pontuação deve ser correta");
    }
    
    @Test
    @DisplayName("Getters devem retornar valores corretos")
    void testGetters_RetornamValoresCorretos() {
        // Given: record criado
        PontuacaoRecord record = new PontuacaoRecord("Maria", "Médio", 300, 1000);
        
        // When/Then: getters devem retornar valores corretos
        assertEquals("Maria", record.getNomeJogador(), "getNomeJogador deve retornar nome correto");
        assertEquals("Médio", record.getDificuldade(), "getDificuldade deve retornar dificuldade correta");
        assertEquals(300, record.getTempoTotal(), "getTempoTotal deve retornar tempo correto");
        assertEquals(1000, record.getPontuacao(), "getPontuacao deve retornar pontuação correta");
    }
    
    @Test
    @DisplayName("Serialização deve serializar e deserializar corretamente")
    void testSerializacao_SerializaEDeserializaCorretamente() throws IOException, ClassNotFoundException {
        // Given: record original
        PontuacaoRecord recordOriginal = new PontuacaoRecord("Pedro", "Difícil", 600, 2000);
        
        // When: serializa e deserializa
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(recordOriginal);
        }
        
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        PontuacaoRecord recordDeserializado;
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            recordDeserializado = (PontuacaoRecord) ois.readObject();
        }
        
        // Then: dados devem ser iguais
        assertEquals(recordOriginal.getNomeJogador(), recordDeserializado.getNomeJogador(),
            "Nome deve ser preservado após serialização");
        assertEquals(recordOriginal.getDificuldade(), recordDeserializado.getDificuldade(),
            "Dificuldade deve ser preservada após serialização");
        assertEquals(recordOriginal.getTempoTotal(), recordDeserializado.getTempoTotal(),
            "Tempo total deve ser preservado após serialização");
        assertEquals(recordOriginal.getPontuacao(), recordDeserializado.getPontuacao(),
            "Pontuação deve ser preservada após serialização");
    }
    
    @Test
    @DisplayName("ToString deve retornar string formatada")
    void testToString_RetornaStringFormatada() {
        // Given: record
        PontuacaoRecord record = new PontuacaoRecord("Ana", "Fácil", 90, 750);
        
        // When: obtém string
        String str = record.toString();
        
        // Then: deve conter informações do record
        assertNotNull(str, "toString não deve retornar null");
        assertTrue(str.contains("Ana"), "String deve conter nome do jogador");
        assertTrue(str.contains("Fácil"), "String deve conter dificuldade");
        assertTrue(str.contains("90"), "String deve conter tempo");
        assertTrue(str.contains("750"), "String deve conter pontuação");
    }
}


package projeto_final.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe MovimentoInvalidoException.
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 */
class MovimentoInvalidoExceptionTest {
    
    @Test
    @DisplayName("Construtor sem parâmetros deve criar exceção")
    void testConstrutor_SemParametros_CriaExcecao() {
        // When: cria exceção sem parâmetros
        MovimentoInvalidoException excecao = new MovimentoInvalidoException();
        
        // Then: deve ser criada
        assertNotNull(excecao, "Exceção deve ser criada");
        assertNull(excecao.getMessage(), "Mensagem deve ser null");
    }
    
    @Test
    @DisplayName("Construtor com mensagem deve criar exceção com mensagem")
    void testConstrutor_Mensagem_CriaExcecaoComMensagem() {
        // Given: mensagem específica
        String mensagem = "Coordenadas inválidas: (5, 5)";
        
        // When: cria exceção com mensagem
        MovimentoInvalidoException excecao = new MovimentoInvalidoException(mensagem);
        
        // Then: deve ter a mensagem correta
        assertNotNull(excecao, "Exceção deve ser criada");
        assertEquals(mensagem, excecao.getMessage(), "Mensagem deve ser a especificada");
    }
    
    @Test
    @DisplayName("Construtor com mensagem e causa deve criar exceção com causa")
    void testConstrutor_MensagemECausa_CriaExcecaoComCausa() {
        // Given: mensagem e causa
        String mensagem = "Erro ao processar movimento";
        Throwable causa = new IllegalArgumentException("Causa original");
        
        // When: cria exceção com mensagem e causa
        MovimentoInvalidoException excecao = new MovimentoInvalidoException(mensagem, causa);
        
        // Then: deve ter mensagem e causa corretas
        assertNotNull(excecao, "Exceção deve ser criada");
        assertEquals(mensagem, excecao.getMessage(), "Mensagem deve ser a especificada");
        assertEquals(causa, excecao.getCause(), "Causa deve ser a especificada");
    }
}


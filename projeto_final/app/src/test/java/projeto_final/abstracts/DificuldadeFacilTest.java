package projeto_final.abstracts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import projeto_final.model.DificuldadeFacil;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe DificuldadeFacil.
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
class DificuldadeFacilTest {
    
    @Test
    @DisplayName("Construtor deve configurar nome correto")
    void testConstrutor_ConfiguraNomeCorreto() {
        // Given: dificuldade fácil criada
        DificuldadeFacil dificuldade = new DificuldadeFacil();
        
        // Then: nome deve ser "Fácil"
        assertEquals("Fácil", dificuldade.getNome(), "Nome deve ser 'Fácil'");
    }
    
    @Test
    @DisplayName("Construtor deve configurar dimensão correta")
    void testConstrutor_ConfiguraDimensaoCorreta() {
        // Given: dificuldade fácil criada
        DificuldadeFacil dificuldade = new DificuldadeFacil();
        
        // Then: dimensão deve ser 3
        assertEquals(3, dificuldade.getDimensao(), "Dimensão deve ser 3");
    }
    
    @Test
    @DisplayName("Construtor deve configurar multiplicador correto")
    void testConstrutor_ConfiguraMultiplicadorCorreto() {
        // Given: dificuldade fácil criada
        DificuldadeFacil dificuldade = new DificuldadeFacil();
        
        // Then: multiplicador deve ser 1.0
        assertEquals(1.0, dificuldade.getMultiplicador(), 0.001,
            "Multiplicador deve ser 1.0");
    }
    
    @Test
    @DisplayName("Get nome deve retornar nome correto")
    void testGetNome_RetornaNomeCorreto() {
        // Given: dificuldade fácil
        DificuldadeFacil dificuldade = new DificuldadeFacil();
        
        // When: obtém nome
        String nome = dificuldade.getNome();
        
        // Then: deve retornar "Fácil"
        assertEquals("Fácil", nome, "getNome deve retornar 'Fácil'");
    }
    
    @Test
    @DisplayName("Get dimensão deve retornar dimensão correta")
    void testGetDimensao_RetornaDimensaoCorreta() {
        // Given: dificuldade fácil
        DificuldadeFacil dificuldade = new DificuldadeFacil();
        
        // When: obtém dimensão
        int dimensao = dificuldade.getDimensao();
        
        // Then: deve retornar 3
        assertEquals(3, dimensao, "getDimensao deve retornar 3");
    }
    
    @Test
    @DisplayName("Get multiplicador deve retornar multiplicador correto")
    void testGetMultiplicador_RetornaMultiplicadorCorreto() {
        // Given: dificuldade fácil
        DificuldadeFacil dificuldade = new DificuldadeFacil();
        
        // When: obtém multiplicador
        double multiplicador = dificuldade.getMultiplicador();
        
        // Then: deve retornar 1.0
        assertEquals(1.0, multiplicador, 0.001, "getMultiplicador deve retornar 1.0");
    }
}


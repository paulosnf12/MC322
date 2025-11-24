package projeto_final.abstracts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import projeto_final.model.DificuldadeDificil;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe DificuldadeDificil.
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
class DificuldadeDificilTest {
    
    @Test
    @DisplayName("Construtor deve configurar nome correto")
    void testConstrutor_ConfiguraNomeCorreto() {
        DificuldadeDificil dificuldade = new DificuldadeDificil();
        assertEquals("Difícil", dificuldade.getNome(), "Nome deve ser 'Difícil'");
    }
    
    @Test
    @DisplayName("Construtor deve configurar dimensão correta")
    void testConstrutor_ConfiguraDimensaoCorreta() {
        DificuldadeDificil dificuldade = new DificuldadeDificil();
        assertEquals(7, dificuldade.getDimensao(), "Dimensão deve ser 7");
    }
    
    @Test
    @DisplayName("Construtor deve configurar multiplicador correto")
    void testConstrutor_ConfiguraMultiplicadorCorreto() {
        DificuldadeDificil dificuldade = new DificuldadeDificil();
        assertEquals(2.0, dificuldade.getMultiplicador(), 0.001,
            "Multiplicador deve ser 2.0");
    }
    
    @Test
    @DisplayName("Get nome deve retornar nome correto")
    void testGetNome_RetornaNomeCorreto() {
        DificuldadeDificil dificuldade = new DificuldadeDificil();
        assertEquals("Difícil", dificuldade.getNome(), "getNome deve retornar 'Difícil'");
    }
    
    @Test
    @DisplayName("Get dimensão deve retornar dimensão correta")
    void testGetDimensao_RetornaDimensaoCorreta() {
        DificuldadeDificil dificuldade = new DificuldadeDificil();
        assertEquals(7, dificuldade.getDimensao(), "getDimensao deve retornar 7");
    }
    
    @Test
    @DisplayName("Get multiplicador deve retornar multiplicador correto")
    void testGetMultiplicador_RetornaMultiplicadorCorreto() {
        DificuldadeDificil dificuldade = new DificuldadeDificil();
        assertEquals(2.0, dificuldade.getMultiplicador(), 0.001,
            "getMultiplicador deve retornar 2.0");
    }
}


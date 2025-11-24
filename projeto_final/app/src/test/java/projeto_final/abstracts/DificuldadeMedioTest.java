package projeto_final.abstracts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import projeto_final.model.DificuldadeMedio;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe DificuldadeMedio.
 * 
 * @author Projeto Final MC322
 * @version 1.0
 */
class DificuldadeMedioTest {
    
    @Test
    @DisplayName("Construtor deve configurar nome correto")
    void testConstrutor_ConfiguraNomeCorreto() {
        DificuldadeMedio dificuldade = new DificuldadeMedio();
        assertEquals("Médio", dificuldade.getNome(), "Nome deve ser 'Médio'");
    }
    
    @Test
    @DisplayName("Construtor deve configurar dimensão correta")
    void testConstrutor_ConfiguraDimensaoCorreta() {
        DificuldadeMedio dificuldade = new DificuldadeMedio();
        assertEquals(5, dificuldade.getDimensao(), "Dimensão deve ser 5");
    }
    
    @Test
    @DisplayName("Construtor deve configurar multiplicador correto")
    void testConstrutor_ConfiguraMultiplicadorCorreto() {
        DificuldadeMedio dificuldade = new DificuldadeMedio();
        assertEquals(1.5, dificuldade.getMultiplicador(), 0.001,
            "Multiplicador deve ser 1.5");
    }
    
    @Test
    @DisplayName("Get nome deve retornar nome correto")
    void testGetNome_RetornaNomeCorreto() {
        DificuldadeMedio dificuldade = new DificuldadeMedio();
        assertEquals("Médio", dificuldade.getNome(), "getNome deve retornar 'Médio'");
    }
    
    @Test
    @DisplayName("Get dimensão deve retornar dimensão correta")
    void testGetDimensao_RetornaDimensaoCorreta() {
        DificuldadeMedio dificuldade = new DificuldadeMedio();
        assertEquals(5, dificuldade.getDimensao(), "getDimensao deve retornar 5");
    }
    
    @Test
    @DisplayName("Get multiplicador deve retornar multiplicador correto")
    void testGetMultiplicador_RetornaMultiplicadorCorreto() {
        DificuldadeMedio dificuldade = new DificuldadeMedio();
        assertEquals(1.5, dificuldade.getMultiplicador(), 0.001,
            "getMultiplicador deve retornar 1.5");
    }
}


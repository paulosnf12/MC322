package projeto_final.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Celula.
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 */
class CelulaTest {
    private Celula celula;
    
    /**
     * Configura o ambiente de teste antes de cada método de teste.
     * Cria uma nova instância de Celula para cada teste.
     */
    @BeforeEach
    void setUp() {
        celula = new Celula();
    }
    
    /**
     * Testa se o construtor padrão cria uma célula desligada na posição (0, 0).
     * <p>
     * Verifica que:
     * <ul>
     *   <li>A célula começa desligada</li>
     *   <li>A coordenada X é 0</li>
     *   <li>A coordenada Y é 0</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Construtor padrão deve criar célula desligada na origem")
    void testConstrutorPadrao_CriaCelulaDesligadaNaOrigem() {
        // Given: célula criada com construtor padrão
        
        // Then: deve estar desligada e na posição (0, 0)
        assertFalse(celula.isLigada(), "Célula deve começar desligada");
        assertEquals(0, celula.getX(), "Coordenada X deve ser 0");
        assertEquals(0, celula.getY(), "Coordenada Y deve ser 0");
    }
    
    /**
     * Testa se o construtor com coordenadas cria uma célula com as coordenadas
     * especificadas e estado desligado.
     * <p>
     * Verifica que:
     * <ul>
     *   <li>As coordenadas X e Y são definidas corretamente</li>
     *   <li>A célula começa desligada</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("Construtor com coordenadas deve criar célula com coordenadas corretas")
    void testConstrutorComCoordenadas_CriaCelulaComCoordenadasCorretas() {
        // Given: coordenadas específicas
        int x = 5;
        int y = 3;
        
        // When: cria célula com coordenadas
        Celula celulaComCoordenadas = new Celula(x, y);
        
        // Then: deve ter as coordenadas corretas e estar desligada
        assertEquals(x, celulaComCoordenadas.getX(), "Coordenada X deve ser " + x);
        assertEquals(y, celulaComCoordenadas.getY(), "Coordenada Y deve ser " + y);
        assertFalse(celulaComCoordenadas.isLigada(), "Célula deve começar desligada");
    }
    
    /**
     * Testa se inicializar() desliga a célula, independentemente do estado anterior.
     * <p>
     * Verifica que mesmo que a célula esteja ligada, após chamar inicializar(),
     * ela fica desligada.
     * </p>
     */
    @Test
    @DisplayName("Inicializar deve desligar a célula")
    void testInicializar_DesligaCelula() {
        // Given: célula ligada
        celula.setLigada(true);
        assertTrue(celula.isLigada(), "Célula deve estar ligada antes da inicialização");
        
        // When: inicializa a célula
        celula.inicializar();
        
        // Then: deve estar desligada
        assertFalse(celula.isLigada(), "Célula deve estar desligada após inicialização");
    }
    
    /**
     * Testa se alternar() muda o estado de ligada para desligada.
     * <p>
     * Verifica que quando uma célula ligada é alternada, ela fica desligada.
     * </p>
     */
    @Test
    @DisplayName("Alternar deve mudar de ligada para desligada")
    void testAlternar_LigadaParaDesligada() {
        // Given: célula ligada
        celula.setLigada(true);
        assertTrue(celula.isLigada(), "Célula deve estar ligada inicialmente");
        
        // When: alterna a célula
        celula.alternar();
        
        // Then: deve estar desligada
        assertFalse(celula.isLigada(), "Célula deve estar desligada após alternar");
    }
    
    /**
     * Testa se alternar() muda o estado de desligada para ligada.
     * <p>
     * Verifica que quando uma célula desligada é alternada, ela fica ligada.
     * </p>
     */
    @Test
    @DisplayName("Alternar deve mudar de desligada para ligada")
    void testAlternar_DesligadaParaLigada() {
        // Given: célula desligada
        assertFalse(celula.isLigada(), "Célula deve estar desligada inicialmente");
        
        // When: alterna a célula
        celula.alternar();
        
        // Then: deve estar ligada
        assertTrue(celula.isLigada(), "Célula deve estar ligada após alternar");
    }
    
    /**
     * Testa se alternar() duas vezes retorna a célula ao estado original.
     * <p>
     * Verifica que alternar() é uma operação idempotente quando aplicada duas vezes,
     * retornando a célula ao estado inicial.
     * </p>
     */
    @Test
    @DisplayName("Alternar duas vezes deve retornar ao estado original")
    void testAlternar_DuasVezes_RetornaEstadoOriginal() {
        // Given: célula desligada
        boolean estadoOriginal = celula.isLigada();
        
        // When: alterna duas vezes
        celula.alternar();
        celula.alternar();
        
        // Then: deve estar no estado original
        assertEquals(estadoOriginal, celula.isLigada(), 
            "Célula deve estar no estado original após alternar duas vezes");
    }
    
    /**
     * Testa se setLigada() define corretamente o estado da célula.
     * <p>
     * Verifica que:
     * <ul>
     *   <li>setLigada(true) liga a célula</li>
     *   <li>setLigada(false) desliga a célula</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("SetLigada deve definir estado corretamente")
    void testSetLigada_DefineEstadoCorretamente() {
        // Given: célula desligada
        assertFalse(celula.isLigada(), "Célula deve estar desligada inicialmente");
        
        // When: define como ligada
        celula.setLigada(true);
        
        // Then: deve estar ligada
        assertTrue(celula.isLigada(), "Célula deve estar ligada após setLigada(true)");
        
        // When: define como desligada
        celula.setLigada(false);
        
        // Then: deve estar desligada
        assertFalse(celula.isLigada(), "Célula deve estar desligada após setLigada(false)");
    }
    
    /**
     * Testa se isLigada() retorna corretamente o estado atual da célula.
     * <p>
     * Verifica que:
     * <ul>
     *   <li>isLigada() retorna false quando a célula está desligada</li>
     *   <li>isLigada() retorna true quando a célula está ligada</li>
     * </ul>
     * </p>
     */
    @Test
    @DisplayName("IsLigada deve retornar estado correto")
    void testIsLigada_RetornaEstadoCorreto() {
        // Given: célula desligada
        assertFalse(celula.isLigada(), "isLigada deve retornar false quando desligada");
        
        // When: liga a célula
        celula.setLigada(true);
        
        // Then: isLigada deve retornar true
        assertTrue(celula.isLigada(), "isLigada deve retornar true quando ligada");
    }
    
    /**
     * Testa se getLigada() retorna o mesmo valor que isLigada().
     * <p>
     * Verifica que ambos os métodos retornam o mesmo valor booleano,
     * garantindo consistência na API.
     * </p>
     */
    @Test
    @DisplayName("GetLigada deve retornar mesmo valor que isLigada")
    void testGetLigada_RetornaMesmoValorQueIsLigada() {
        // Given: célula em estado qualquer
        celula.setLigada(true);
        
        // Then: getLigada e isLigada devem retornar o mesmo valor
        assertEquals(celula.isLigada(), celula.getLigada(), 
            "getLigada deve retornar o mesmo valor que isLigada");
    }
    
    /**
     * Testa se getX() retorna corretamente a coordenada X da célula.
     * <p>
     * Verifica que o método retorna a coordenada X que foi definida no construtor.
     * </p>
     */
    @Test
    @DisplayName("GetX deve retornar coordenada X")
    void testGetX_RetornaCoordenadaX() {
        // Given: célula com coordenada X específica
        int xEsperado = 7;
        Celula celulaComX = new Celula(xEsperado, 0);
        
        // Then: getX deve retornar a coordenada correta
        assertEquals(xEsperado, celulaComX.getX(), "getX deve retornar a coordenada X correta");
    }
    
    /**
     * Testa se getY() retorna corretamente a coordenada Y da célula.
     * <p>
     * Verifica que o método retorna a coordenada Y que foi definida no construtor.
     * </p>
     */
    @Test
    @DisplayName("GetY deve retornar coordenada Y")
    void testGetY_RetornaCoordenadaY() {
        // Given: célula com coordenada Y específica
        int yEsperado = 9;
        Celula celulaComY = new Celula(0, yEsperado);
        
        // Then: getY deve retornar a coordenada correta
        assertEquals(yEsperado, celulaComY.getY(), "getY deve retornar a coordenada Y correta");
    }
    
    /**
     * Testa se setX() atualiza corretamente a coordenada X da célula.
     * <p>
     * Verifica que após chamar setX(), a coordenada X é atualizada
     * e getX() retorna o novo valor.
     * </p>
     */
    @Test
    @DisplayName("SetX deve atualizar coordenada X")
    void testSetX_AtualizaCoordenadaX() {
        // Given: célula com X inicial
        int xInicial = celula.getX();
        int xNovo = 10;
        
        // When: atualiza coordenada X
        celula.setX(xNovo);
        
        // Then: X deve ser atualizado
        assertEquals(xNovo, celula.getX(), "setX deve atualizar a coordenada X");
        assertNotEquals(xInicial, celula.getX(), "X deve ser diferente do valor inicial");
    }
    
    /**
     * Testa se setY() atualiza corretamente a coordenada Y da célula.
     * <p>
     * Verifica que após chamar setY(), a coordenada Y é atualizada
     * e getY() retorna o novo valor.
     * </p>
     */
    @Test
    @DisplayName("SetY deve atualizar coordenada Y")
    void testSetY_AtualizaCoordenadaY() {
        // Given: célula com Y inicial
        int yInicial = celula.getY();
        int yNovo = 15;
        
        // When: atualiza coordenada Y
        celula.setY(yNovo);
        
        // Then: Y deve ser atualizado
        assertEquals(yNovo, celula.getY(), "setY deve atualizar a coordenada Y");
        assertNotEquals(yInicial, celula.getY(), "Y deve ser diferente do valor inicial");
    }
}


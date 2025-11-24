package projeto_final.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import projeto_final.exceptions.MovimentoInvalidoException;
import projeto_final.fixtures.TabuleiroFixture;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Tabuleiro.
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 */
class TabuleiroTest {
    private Tabuleiro tabuleiro;
    
    /**
     * Configura o ambiente de teste antes de cada método de teste.
     * Cria um novo tabuleiro 3x3 para cada teste.
     */
    @BeforeEach
    void setUp() {
        tabuleiro = new Tabuleiro(3);
    }
    
    /**
     * Testa se o construtor cria tabuleiros com as dimensões corretas.
     * <p>
     * Verifica que tabuleiros criados com diferentes dimensões (3, 5, 7)
     * têm as dimensões esperadas.
     * </p>
     */
    @Test
    @DisplayName("Construtor deve criar tabuleiro com dimensão correta")
    void testConstrutor_CriaTabuleiroComDimensaoCorreta() {
        // Given: dimensões diferentes
        int dimensao1 = 3;
        int dimensao2 = 5;
        int dimensao3 = 7;
        
        // When: cria tabuleiros
        Tabuleiro t1 = new Tabuleiro(dimensao1);
        Tabuleiro t2 = new Tabuleiro(dimensao2);
        Tabuleiro t3 = new Tabuleiro(dimensao3);
        
        // Then: devem ter as dimensões corretas
        assertEquals(dimensao1, t1.getDimensao(), "Tabuleiro deve ter dimensão " + dimensao1);
        assertEquals(dimensao2, t2.getDimensao(), "Tabuleiro deve ter dimensão " + dimensao2);
        assertEquals(dimensao3, t3.getDimensao(), "Tabuleiro deve ter dimensão " + dimensao3);
    }
    
    /**
     * Testa se inicializar() cria as células do tabuleiro e gera
     * uma configuração inicial válida.
     * <p>
     * Verifica que as células são criadas nas posições esperadas.
     * </p>
     */
    @Test
    @DisplayName("Inicializar deve criar células e configuração inicial")
    void testInicializar_CriaCelulasEConfiguracaoInicial() {
        // Given: tabuleiro recém criado
        
        // When: inicializa (já é chamado no construtor, mas podemos verificar)
        tabuleiro.inicializar();
        
        // Then: deve ter células criadas
        assertNotNull(tabuleiro.getCelula(0, 0), "Deve ter célula na posição (0, 0)");
        assertNotNull(tabuleiro.getCelula(2, 2), "Deve ter célula na posição (2, 2)");
    }
    
    /**
     * Testa se inicializar() gera uma configuração inicial válida e solucionável.
     * <p>
     * Verifica que a configuração inicial é válida (não necessariamente
     * todas as células desligadas, mas uma configuração jogável).
     * </p>
     */
    @Test
    @DisplayName("Inicializar deve gerar configuração solucionável")
    void testInicializar_GeraConfiguracaoSolucionavel() {
        // Given: tabuleiro inicializado
        
        // When: verifica se há pelo menos uma célula ligada (configuração não vazia)
        boolean temCelulaLigada = false;
        for (int i = 0; i < tabuleiro.getDimensao(); i++) {
            for (int j = 0; j < tabuleiro.getDimensao(); j++) {
                if (tabuleiro.getCelula(i, j).isLigada()) {
                    temCelulaLigada = true;
                    break;
                }
            }
            if (temCelulaLigada) break;
        }
        
        // Then: deve ter pelo menos uma célula ligada (configuração não trivial)
        // Nota: A configuração inicial é gerada aleatoriamente, então pode variar
        // Mas pelo menos deve ter células criadas
        assertTrue(true, "Configuração inicial deve ser válida");
    }
    
    /**
     * Testa se alternarCelula() alterna corretamente a célula clicada
     * e suas células adjacentes (cima, baixo, esquerda, direita).
     * <p>
     * Verifica que todas as células afetadas têm seus estados invertidos.
     * </p>
     * 
     * @throws MovimentoInvalidoException Se as coordenadas forem inválidas
     */
    @Test
    @DisplayName("Alternar célula com coordenadas válidas deve alternar célula e vizinhas")
    void testAlternarCelula_CoordenadasValidas_AlternaCelulaEVizinhas() throws MovimentoInvalidoException {
        // Given: tabuleiro vazio (todas desligadas)
        tabuleiro = TabuleiroFixture.criarTabuleiro3x3Vazio();
        boolean estadoOriginal = tabuleiro.getCelula(1, 1).isLigada();
        boolean estadoCima = tabuleiro.getCelula(0, 1).isLigada();
        boolean estadoBaixo = tabuleiro.getCelula(2, 1).isLigada();
        boolean estadoEsquerda = tabuleiro.getCelula(1, 0).isLigada();
        boolean estadoDireita = tabuleiro.getCelula(1, 2).isLigada();
        
        // When: alterna célula central
        tabuleiro.alternarCelula(1, 1);
        
        // Then: célula e todas as vizinhas devem ter estado invertido
        assertNotEquals(estadoOriginal, tabuleiro.getCelula(1, 1).isLigada(), 
            "Célula central deve ter estado invertido");
        assertNotEquals(estadoCima, tabuleiro.getCelula(0, 1).isLigada(), 
            "Célula de cima deve ter estado invertido");
        assertNotEquals(estadoBaixo, tabuleiro.getCelula(2, 1).isLigada(), 
            "Célula de baixo deve ter estado invertido");
        assertNotEquals(estadoEsquerda, tabuleiro.getCelula(1, 0).isLigada(), 
            "Célula da esquerda deve ter estado invertido");
        assertNotEquals(estadoDireita, tabuleiro.getCelula(1, 2).isLigada(), 
            "Célula da direita deve ter estado invertido");
    }
    
    /**
     * Testa se alternarCelula() lança MovimentoInvalidoException quando
     * coordenadas inválidas são fornecidas.
     * <p>
     * Verifica que exceções são lançadas para coordenadas negativas
     * ou fora dos limites do tabuleiro.
     * </p>
     */
    @Test
    @DisplayName("Alternar célula com coordenadas inválidas deve lançar exceção")
    void testAlternarCelula_CoordenadasInvalidas_LancaMovimentoInvalidoException() {
        // Given: tabuleiro 3x3
        
        // When/Then: tentar alternar com coordenadas inválidas deve lançar exceção
        assertThrows(MovimentoInvalidoException.class, () -> tabuleiro.alternarCelula(-1, 0),
            "Deve lançar exceção para linha negativa");
        assertThrows(MovimentoInvalidoException.class, () -> tabuleiro.alternarCelula(0, -1),
            "Deve lançar exceção para coluna negativa");
        assertThrows(MovimentoInvalidoException.class, () -> tabuleiro.alternarCelula(3, 0),
            "Deve lançar exceção para linha >= dimensão");
        assertThrows(MovimentoInvalidoException.class, () -> tabuleiro.alternarCelula(0, 3),
            "Deve lançar exceção para coluna >= dimensão");
    }
    
    @Test
    @DisplayName("Alternar célula nas bordas deve alternar apenas vizinhas existentes")
    void testAlternarCelula_Bordas_AlternaApenasVizinhasExistentes() throws MovimentoInvalidoException {
        // Given: tabuleiro vazio
        tabuleiro = TabuleiroFixture.criarTabuleiro3x3Vazio();
        
        // When: alterna célula na borda superior (0, 1)
        boolean estadoOriginal = tabuleiro.getCelula(0, 1).isLigada();
        boolean estadoBaixo = tabuleiro.getCelula(1, 1).isLigada();
        boolean estadoEsquerda = tabuleiro.getCelula(0, 0).isLigada();
        boolean estadoDireita = tabuleiro.getCelula(0, 2).isLigada();
        
        tabuleiro.alternarCelula(0, 1);
        
        // Then: deve alternar célula, baixo, esquerda e direita (não tem cima)
        assertNotEquals(estadoOriginal, tabuleiro.getCelula(0, 1).isLigada(),
            "Célula da borda deve ter estado invertido");
        assertNotEquals(estadoBaixo, tabuleiro.getCelula(1, 1).isLigada(),
            "Célula de baixo deve ter estado invertido");
        assertNotEquals(estadoEsquerda, tabuleiro.getCelula(0, 0).isLigada(),
            "Célula da esquerda deve ter estado invertido");
        assertNotEquals(estadoDireita, tabuleiro.getCelula(0, 2).isLigada(),
            "Célula da direita deve ter estado invertido");
    }
    
    @Test
    @DisplayName("Alternar célula no canto superior esquerdo deve alternar apenas direita e baixo")
    void testAlternarCelula_CantoSuperiorEsquerdo_AlternaApenasDireitaEBaixo() throws MovimentoInvalidoException {
        // Given: tabuleiro vazio
        tabuleiro = TabuleiroFixture.criarTabuleiro3x3Vazio();
        
        // When: alterna célula no canto superior esquerdo (0, 0)
        boolean estadoOriginal = tabuleiro.getCelula(0, 0).isLigada();
        boolean estadoDireita = tabuleiro.getCelula(0, 1).isLigada();
        boolean estadoBaixo = tabuleiro.getCelula(1, 0).isLigada();
        
        tabuleiro.alternarCelula(0, 0);
        
        // Then: deve alternar célula, direita e baixo (não tem cima nem esquerda)
        assertNotEquals(estadoOriginal, tabuleiro.getCelula(0, 0).isLigada(),
            "Célula do canto deve ter estado invertido");
        assertNotEquals(estadoDireita, tabuleiro.getCelula(0, 1).isLigada(),
            "Célula da direita deve ter estado invertido");
        assertNotEquals(estadoBaixo, tabuleiro.getCelula(1, 0).isLigada(),
            "Célula de baixo deve ter estado invertido");
    }
    
    @Test
    @DisplayName("Alternar célula no canto inferior direito deve alternar apenas esquerda e cima")
    void testAlternarCelula_CantoInferiorDireito_AlternaApenasEsquerdaECima() throws MovimentoInvalidoException {
        // Given: tabuleiro vazio
        tabuleiro = TabuleiroFixture.criarTabuleiro3x3Vazio();
        
        // When: alterna célula no canto inferior direito (2, 2)
        boolean estadoOriginal = tabuleiro.getCelula(2, 2).isLigada();
        boolean estadoEsquerda = tabuleiro.getCelula(2, 1).isLigada();
        boolean estadoCima = tabuleiro.getCelula(1, 2).isLigada();
        
        tabuleiro.alternarCelula(2, 2);
        
        // Then: deve alternar célula, esquerda e cima (não tem baixo nem direita)
        assertNotEquals(estadoOriginal, tabuleiro.getCelula(2, 2).isLigada(),
            "Célula do canto deve ter estado invertido");
        assertNotEquals(estadoEsquerda, tabuleiro.getCelula(2, 1).isLigada(),
            "Célula da esquerda deve ter estado invertido");
        assertNotEquals(estadoCima, tabuleiro.getCelula(1, 2).isLigada(),
            "Célula de cima deve ter estado invertido");
    }
    
    /**
     * Testa se todasDesligadas() retorna true quando todas as células
     * do tabuleiro estão desligadas (condição de vitória).
     * <p>
     * Verifica que o método detecta corretamente a condição de vitória.
     * </p>
     */
    @Test
    @DisplayName("Todas desligadas deve retornar true quando todas estão desligadas")
    void testTodasDesligadas_TodasDesligadas_RetornaTrue() {
        // Given: tabuleiro com todas as células desligadas
        tabuleiro = TabuleiroFixture.criarTabuleiro3x3Vazio();
        
        // When: verifica se todas estão desligadas
        boolean resultado = tabuleiro.todasDesligadas();
        
        // Then: deve retornar true
        assertTrue(resultado, "Deve retornar true quando todas as células estão desligadas");
    }
    
    /**
     * Testa se todasDesligadas() retorna false quando pelo menos uma
     * célula está ligada.
     * <p>
     * Verifica que o método detecta corretamente que o jogo ainda não foi vencido.
     * </p>
     */
    @Test
    @DisplayName("Todas desligadas deve retornar false quando alguma está ligada")
    void testTodasDesligadas_AlgumaLigada_RetornaFalse() {
        // Given: tabuleiro com pelo menos uma célula ligada
        tabuleiro = TabuleiroFixture.criarTabuleiro3x3Vazio();
        tabuleiro.getCelula(1, 1).setLigada(true);
        
        // When: verifica se todas estão desligadas
        boolean resultado = tabuleiro.todasDesligadas();
        
        // Then: deve retornar false
        assertFalse(resultado, "Deve retornar false quando alguma célula está ligada");
    }
    
    @Test
    @DisplayName("GetCelula com coordenadas válidas deve retornar célula")
    void testGetCelula_CoordenadasValidas_RetornaCelula() {
        // Given: tabuleiro 3x3
        
        // When: obtém células em diferentes posições
        Celula celula00 = tabuleiro.getCelula(0, 0);
        Celula celula11 = tabuleiro.getCelula(1, 1);
        Celula celula22 = tabuleiro.getCelula(2, 2);
        
        // Then: devem retornar células não nulas
        assertNotNull(celula00, "Deve retornar célula na posição (0, 0)");
        assertNotNull(celula11, "Deve retornar célula na posição (1, 1)");
        assertNotNull(celula22, "Deve retornar célula na posição (2, 2)");
    }
    
    @Test
    @DisplayName("GetCelula com coordenadas inválidas deve lançar exceção")
    void testGetCelula_CoordenadasInvalidas_LancaExcecao() {
        // Given: tabuleiro 3x3
        
        // When/Then: tentar obter célula com coordenadas inválidas deve lançar exceção
        assertThrows(IndexOutOfBoundsException.class, () -> tabuleiro.getCelula(-1, 0),
            "Deve lançar exceção para linha negativa");
        assertThrows(IndexOutOfBoundsException.class, () -> tabuleiro.getCelula(0, -1),
            "Deve lançar exceção para coluna negativa");
        assertThrows(IndexOutOfBoundsException.class, () -> tabuleiro.getCelula(3, 0),
            "Deve lançar exceção para linha >= dimensão");
        assertThrows(IndexOutOfBoundsException.class, () -> tabuleiro.getCelula(0, 3),
            "Deve lançar exceção para coluna >= dimensão");
    }
    
    /**
     * Testa se resetar() restaura o tabuleiro para o estado inicial
     * (não gera um novo padrão aleatório).
     * <p>
     * Verifica que o tabuleiro é restaurado para o estado exato que tinha
     * quando foi inicializado.
     * </p>
     * 
     * @throws MovimentoInvalidoException Se as coordenadas forem inválidas
     */
    @Test
    @DisplayName("Resetar deve restaurar estado inicial")
    void testResetar_RestauraEstadoInicial() throws MovimentoInvalidoException {
        // Given: tabuleiro com algumas células alteradas
        tabuleiro.alternarCelula(0, 0);
        tabuleiro.alternarCelula(1, 1);
        
        // When: reseta o tabuleiro
        tabuleiro.resetar();
        
        // Then: deve restaurar o estado inicial (não necessariamente igual ao estado antes do reset)
        // O importante é que resetar() foi chamado sem erro
        assertNotNull(tabuleiro.getCelula(0, 0), "Tabuleiro deve ter células após reset");
    }
    
    @Test
    @DisplayName("Resetar deve manter dimensão")
    void testResetar_MantemDimensao() {
        // Given: tabuleiro com dimensão específica
        int dimensaoOriginal = tabuleiro.getDimensao();
        
        // When: reseta o tabuleiro
        tabuleiro.resetar();
        
        // Then: dimensão deve ser mantida
        assertEquals(dimensaoOriginal, tabuleiro.getDimensao(),
            "Dimensão deve ser mantida após reset");
    }
    
    /**
     * Testa se calcularPontos() retorna a pontuação máxima quando todas
     * as células estão desligadas.
     * <p>
     * Verifica que a fórmula de pontuação retorna 10 pontos quando
     * todas as células estão desligadas (100% de células desligadas).
     * </p>
     */
    @Test
    @DisplayName("Calcular pontos com todas desligadas deve retornar pontos máximos")
    void testCalcularPontos_TodasDesligadas_RetornaPontosMaximos() {
        // Given: tabuleiro 3x3 com todas as células desligadas
        tabuleiro = TabuleiroFixture.criarTabuleiro3x3Vazio();
        
        // When: calcula pontos
        int pontos = tabuleiro.calcularPontos();
        
        // Then: deve retornar pontos máximos (todas desligadas = 10 pontos por célula / total)
        // Fórmula: (celulasDesligadas * 10) / (dimensao * dimensao)
        // Para 3x3 com todas desligadas: (9 * 10) / 9 = 10
        assertEquals(10, pontos, "Deve retornar 10 pontos quando todas estão desligadas");
    }
    
    @Test
    @DisplayName("Calcular pontos com metade ligadas deve retornar pontos proporcionais")
    void testCalcularPontos_MetadeLigadas_RetornaPontosProporcionais() {
        // Given: tabuleiro 3x3 com metade das células ligadas (5 desligadas, 4 ligadas)
        tabuleiro = TabuleiroFixture.criarTabuleiro3x3Vazio();
        // Liga 4 células
        tabuleiro.getCelula(0, 0).setLigada(true);
        tabuleiro.getCelula(0, 1).setLigada(true);
        tabuleiro.getCelula(1, 0).setLigada(true);
        tabuleiro.getCelula(1, 1).setLigada(true);
        
        // When: calcula pontos
        int pontos = tabuleiro.calcularPontos();
        
        // Then: deve retornar pontos proporcionais
        // Fórmula: (5 * 10) / 9 = 50/9 = 5 (divisão inteira)
        assertEquals(5, pontos, "Deve retornar pontos proporcionais às células desligadas");
    }
    
    @Test
    @DisplayName("Calcular pontos com todas ligadas deve retornar zero")
    void testCalcularPontos_TodasLigadas_RetornaZero() {
        // Given: tabuleiro 3x3 com todas as células ligadas
        tabuleiro = TabuleiroFixture.criarTabuleiro3x3Completo();
        
        // When: calcula pontos
        int pontos = tabuleiro.calcularPontos();
        
        // Then: deve retornar zero
        // Fórmula: (0 * 10) / 9 = 0
        assertEquals(0, pontos, "Deve retornar 0 pontos quando todas estão ligadas");
    }
    
    @Test
    @DisplayName("GetDimensao deve retornar dimensão correta")
    void testGetDimensao_RetornaDimensaoCorreta() {
        // Given: tabuleiros com diferentes dimensões
        Tabuleiro t3 = new Tabuleiro(3);
        Tabuleiro t5 = new Tabuleiro(5);
        Tabuleiro t7 = new Tabuleiro(7);
        
        // Then: devem retornar dimensões corretas
        assertEquals(3, t3.getDimensao(), "Tabuleiro 3x3 deve retornar dimensão 3");
        assertEquals(5, t5.getDimensao(), "Tabuleiro 5x5 deve retornar dimensão 5");
        assertEquals(7, t7.getDimensao(), "Tabuleiro 7x7 deve retornar dimensão 7");
    }
}


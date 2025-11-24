package projeto_final.fixtures;

import projeto_final.model.Tabuleiro;

/**
 * Classe auxiliar para criar fixtures de Tabuleiro em diferentes estados
 * para uso em testes unitários.
 * <p>
 * Esta classe fornece métodos estáticos para criar tabuleiros em estados
 * específicos e conhecidos, facilitando a escrita de testes determinísticos.
 * </p>
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 */
public class TabuleiroFixture {
    
    /**
     * Cria um tabuleiro 3x3 com todas as células desligadas.
     * 
     * @return Tabuleiro 3x3 com todas as células desligadas
     */
    public static Tabuleiro criarTabuleiro3x3Vazio() {
        Tabuleiro tabuleiro = new Tabuleiro(3);
        // Desliga todas as células manualmente
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro.getCelula(i, j).setLigada(false);
            }
        }
        return tabuleiro;
    }
    
    /**
     * Cria um tabuleiro 3x3 com todas as células ligadas.
     * 
     * @return Tabuleiro 3x3 com todas as células ligadas
     */
    public static Tabuleiro criarTabuleiro3x3Completo() {
        Tabuleiro tabuleiro = new Tabuleiro(3);
        // Liga todas as células manualmente
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro.getCelula(i, j).setLigada(true);
            }
        }
        return tabuleiro;
    }
    
    /**
     * Cria um tabuleiro 3x3 com um padrão específico para testes.
     * <p>
     * Padrão criado:
     * L D L
     * D L D
     * L D L
     * (L = ligada, D = desligada)
     * </p>
     * 
     * @return Tabuleiro 3x3 com padrão alternado
     */
    public static Tabuleiro criarTabuleiro3x3Padrao() {
        Tabuleiro tabuleiro = new Tabuleiro(3);
        boolean[][] padrao = {
            {true, false, true},
            {false, true, false},
            {true, false, true}
        };
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro.getCelula(i, j).setLigada(padrao[i][j]);
            }
        }
        return tabuleiro;
    }
    
    /**
     * Cria um tabuleiro 5x5 com todas as células desligadas.
     * 
     * @return Tabuleiro 5x5 com todas as células desligadas
     */
    public static Tabuleiro criarTabuleiro5x5Vazio() {
        Tabuleiro tabuleiro = new Tabuleiro(5);
        // Desliga todas as células manualmente
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tabuleiro.getCelula(i, j).setLigada(false);
            }
        }
        return tabuleiro;
    }
    
    /**
     * Cria um tabuleiro com dimensão específica e todas as células desligadas.
     * 
     * @param dimensao Dimensão do tabuleiro
     * @return Tabuleiro com todas as células desligadas
     */
    public static Tabuleiro criarTabuleiroVazio(int dimensao) {
        Tabuleiro tabuleiro = new Tabuleiro(dimensao);
        // Desliga todas as células manualmente
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                tabuleiro.getCelula(i, j).setLigada(false);
            }
        }
        return tabuleiro;
    }
    
    /**
     * Cria um tabuleiro com dimensão específica e todas as células ligadas.
     * 
     * @param dimensao Dimensão do tabuleiro
     * @return Tabuleiro com todas as células ligadas
     */
    public static Tabuleiro criarTabuleiroCompleto(int dimensao) {
        Tabuleiro tabuleiro = new Tabuleiro(dimensao);
        // Liga todas as células manualmente
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                tabuleiro.getCelula(i, j).setLigada(true);
            }
        }
        return tabuleiro;
    }
    
    /**
     * Configura um tabuleiro existente com um padrão específico.
     * 
     * @param tabuleiro Tabuleiro a ser configurado
     * @param padrao Matriz booleana representando o padrão (true = ligada, false = desligada)
     */
    public static void configurarPadrao(Tabuleiro tabuleiro, boolean[][] padrao) {
        int dimensao = tabuleiro.getDimensao();
        for (int i = 0; i < dimensao && i < padrao.length; i++) {
            for (int j = 0; j < dimensao && j < padrao[i].length; j++) {
                tabuleiro.getCelula(i, j).setLigada(padrao[i][j]);
            }
        }
    }
    
    /**
     * Verifica se um tabuleiro está em um estado específico.
     * 
     * @param tabuleiro Tabuleiro a ser verificado
     * @param padraoEsperado Matriz booleana representando o padrão esperado
     * @return true se o tabuleiro corresponde ao padrão, false caso contrário
     */
    public static boolean verificarPadrao(Tabuleiro tabuleiro, boolean[][] padraoEsperado) {
        int dimensao = tabuleiro.getDimensao();
        if (padraoEsperado.length != dimensao) {
            return false;
        }
        
        for (int i = 0; i < dimensao; i++) {
            if (padraoEsperado[i].length != dimensao) {
                return false;
            }
            for (int j = 0; j < dimensao; j++) {
                if (tabuleiro.getCelula(i, j).isLigada() != padraoEsperado[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}


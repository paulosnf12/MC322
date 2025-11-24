// Em: src/main/java/projeto_final/model/Tabuleiro.java
package projeto_final.model;

import projeto_final.abstracts.ElementoJogo;
import projeto_final.exceptions.MovimentoInvalidoException;
import projeto_final.interfaces.Pontuavel;

/**
 * Classe que representa o tabuleiro do jogo Lights Out.
 * <p>
 * O tabuleiro é uma grade quadrada de células que podem estar ligadas ou desligadas.
 * O objetivo do jogo é desligar todas as células. Quando uma célula é clicada,
 * ela e suas células adjacentes (cima, baixo, esquerda, direita) têm seus estados
 * invertidos.
 * </p>
 * <p>
 * Esta classe herda de {@code ElementoJogo} e implementa {@code Pontuavel}
 * para calcular a pontuação baseada no estado do tabuleiro.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.abstracts.ElementoJogo
 * @see projeto_final.interfaces.Pontuavel
 * @see projeto_final.model.Celula
 */
public class Tabuleiro extends ElementoJogo implements Pontuavel {
    /** Dimensão do tabuleiro (número de linhas e colunas) */
    private final int dimensao;
    
    /** Matriz bidimensional de células que compõem o tabuleiro */
    private final Celula[][] celulas;
    
    /** Estado inicial do tabuleiro (para restaurar ao reiniciar) */
    private boolean[][] estadoInicial;

    /**
     * Construtor que cria um novo tabuleiro com a dimensão especificada.
     * 
     * @param dimensao Dimensão do tabuleiro (número de linhas/colunas)
     */
    public Tabuleiro(int dimensao) {
        this.dimensao = dimensao;
        this.celulas = new Celula[dimensao][dimensao];
        inicializar();
    }
    
    /**
     * Inicializa o tabuleiro, criando as células e gerando a configuração inicial.
     * <p>
     * Implementação do método abstrato da classe pai {@code ElementoJogo}.
     * Este método é responsável por:
     * <ol>
     *   <li>Criar e inicializar todas as células do tabuleiro</li>
     *   <li>Gerar uma configuração inicial válida e solucionável</li>
     * </ol>
     * </p>
     * 
     * @see projeto_final.abstracts.ElementoJogo#inicializar()
     */
    @Override
    public void inicializar() {
        inicializarCelulas();
        gerarConfiguracaoInicial();
    }

    /**
     * Verifica se todas as células do tabuleiro estão desligadas.
     * <p>
     * Este método é usado para determinar se o jogador venceu o jogo.
     * </p>
     * 
     * @return true se todas as células estiverem desligadas (vitória), false caso contrário
     */
    public boolean todasDesligadas() {
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                // Se encontrar pelo menos uma célula ligada, o jogo não acabou.
                if (celulas[i][j].isLigada()) {
                    return false;
                }
            }
        }
        // Se chegou até aqui, todas estão desligadas.
        return true;
    }

    /**
     * Inicializa todas as células do tabuleiro.
     * <p>
     * Cria uma nova instância de {@code Celula} para cada posição
     * do tabuleiro e inicializa cada uma.
     * </p>
     */
    private void inicializarCelulas() {
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                celulas[i][j] = new Celula(i, j);
                celulas[i][j].inicializar();
            }
        }
    }
    
    /**
     * Gera uma configuração inicial solucionável do tabuleiro.
     * <p>
     * Este método cria um padrão inicial de células ligadas que pode
     * ser resolvido pelo jogador. A estratégia é: começar com todas as
     * células desligadas, depois fazer uma sequência aleatória de movimentos
     * válidos para criar um padrão solucionável.
     * </p>
     * <p>
     * Após gerar a configuração, salva o estado inicial para poder
     * restaurá-lo posteriormente.
     * </p>
     */
    private void gerarConfiguracaoInicial() {
        // Estratégia: fazer uma sequência aleatória de movimentos válidos
        // para garantir que sempre há solução
        java.util.Random random = new java.util.Random();
        int numMovimentos = 3 + random.nextInt(5); // Entre 3 e 7 movimentos iniciais
        
        for (int i = 0; i < numMovimentos; i++) {
            int linha = random.nextInt(dimensao);
            int coluna = random.nextInt(dimensao);
            // Alterna a célula e suas adjacentes (sem lançar exceção)
            alternarCelulaInterna(linha, coluna);
        }
        
        // Salva o estado inicial após gerar a configuração
        salvarEstadoInicial();
    }
    
    /**
     * Salva o estado atual do tabuleiro como estado inicial.
     * <p>
     * Este método é chamado após gerar a configuração inicial para
     * permitir restaurar o tabuleiro ao estado original ao reiniciar.
     * </p>
     */
    private void salvarEstadoInicial() {
        estadoInicial = new boolean[dimensao][dimensao];
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                estadoInicial[i][j] = celulas[i][j].isLigada();
            }
        }
    }
    
    /**
     * Restaura o tabuleiro para o estado inicial salvo.
     * <p>
     * Restaura cada célula para o estado que tinha quando o jogo foi iniciado.
     * </p>
     */
    private void restaurarEstadoInicial() {
        if (estadoInicial != null) {
            for (int i = 0; i < dimensao; i++) {
                for (int j = 0; j < dimensao; j++) {
                    celulas[i][j].setLigada(estadoInicial[i][j]);
                }
            }
        }
    }
    
    /**
     * Método auxiliar para alternar célula sem validação (usado na geração inicial).
     * 
     * @param linha Linha da célula
     * @param coluna Coluna da célula
     */
    private void alternarCelulaInterna(int linha, int coluna) {
        // Alterna a própria célula
        celulas[linha][coluna].alternar();
        // Alterna a de cima
        if (linha > 0) celulas[linha - 1][coluna].alternar();
        // Alterna a de baixo
        if (linha < dimensao - 1) celulas[linha + 1][coluna].alternar();
        // Alterna a da esquerda
        if (coluna > 0) celulas[linha][coluna - 1].alternar();
        // Alterna a da direita
        if (coluna < dimensao - 1) celulas[linha][coluna + 1].alternar();
    }

    /**
     * Alterna o estado de uma célula e suas vizinhas diretas.
     * <p>
     * Lança exceção se as coordenadas forem inválidas.
     * </p>
     * 
     * @param linha A linha da célula clicada
     * @param coluna A coluna da célula clicada
     * @throws MovimentoInvalidoException Se as coordenadas estiverem fora dos limites do tabuleiro
     */
    public void alternarCelula(int linha, int coluna) throws MovimentoInvalidoException {
        // Validação para não clicar fora do tabuleiro
        if (linha < 0 || linha >= dimensao || coluna < 0 || coluna >= dimensao) {
            throw new MovimentoInvalidoException(
                String.format("Coordenadas inválidas: (%d, %d). Tabuleiro tem dimensão %dx%d", 
                    linha, coluna, dimensao, dimensao)
            );
        }

        // Alterna a própria célula
        celulas[linha][coluna].alternar();
        // Alterna a de cima
        if (linha > 0) celulas[linha - 1][coluna].alternar();
        // Alterna a de baixo
        if (linha < dimensao - 1) celulas[linha + 1][coluna].alternar();
        // Alterna a da esquerda
        if (coluna > 0) celulas[linha][coluna - 1].alternar();
        // Alterna a da direita
        if (coluna < dimensao - 1) celulas[linha][coluna + 1].alternar();
    }
    
    /**
     * Retorna a dimensão do tabuleiro.
     * 
     * @return Dimensão do tabuleiro (número de linhas/colunas)
     */
    public int getDimensao() {
        return dimensao;
    }

    /**
     * Retorna a célula na posição especificada.
     * 
     * @param linha Linha da célula (índice baseado em 0)
     * @param coluna Coluna da célula (índice baseado em 0)
     * @return A célula na posição especificada
     */
    public Celula getCelula(int linha, int coluna) {
        return celulas[linha][coluna];
    }
    
    /**
     * Verifica se o jogador venceu o jogo.
     * @return true se todas as células estiverem desligadas, false caso contrário
     */
    public boolean verificarVitoria() {
        return todasDesligadas();
    }
    
    /**
     * Reseta o tabuleiro para o estado inicial.
     * <p>
     * Restaura o tabuleiro para o estado exato que tinha quando o jogo
     * foi iniciado, não gera um novo padrão aleatório.
     * </p>
     */
    public void resetar() {
        if (estadoInicial != null) {
            // Restaura o estado inicial salvo
            restaurarEstadoInicial();
        } else {
            // Se não houver estado inicial salvo (não deveria acontecer),
            // gera um novo padrão e salva
            for (int i = 0; i < dimensao; i++) {
                for (int j = 0; j < dimensao; j++) {
                    celulas[i][j].setLigada(false);
                }
            }
            gerarConfiguracaoInicial();
        }
    }
    
    /**
     * Calcula a pontuação baseada no estado atual do tabuleiro.
     * <p>
     * Implementação da interface {@code Pontuavel}. A pontuação é calculada
     * com base na proporção de células desligadas no tabuleiro.
     * </p>
     * <p>
     * <strong>Nota:</strong> A pontuação final do jogo é calculada no {@code Game}
     * usando a fórmula: (1000 / movimentos) × (300 / tempo_segundos) × multiplicador_dificuldade.
     * Este método fornece uma pontuação base que pode ser usada para outros propósitos.
     * </p>
     * 
     * @return Pontuação calculada (valor inteiro não negativo) baseada na proporção
     *         de células desligadas
     * @see projeto_final.interfaces.Pontuavel#calcularPontos()
     */
    @Override
    public int calcularPontos() {
        // Calcula pontos baseado no número de células desligadas
        int celulasDesligadas = 0;
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                if (!celulas[i][j].isLigada()) {
                    celulasDesligadas++;
                }
            }
        }
        // Pontos baseados na proporção de células desligadas
        return (celulasDesligadas * 10) / (dimensao * dimensao);
    }
}
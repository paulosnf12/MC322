package projeto_final.fixtures;

import projeto_final.controller.Game;
import projeto_final.model.Jogador;
import projeto_final.model.DificuldadeFacil;
import projeto_final.model.DificuldadeMedio;
import projeto_final.model.DificuldadeDificil;
import projeto_final.abstracts.Dificuldade;

/**
 * Classe auxiliar para criar fixtures de Game em diferentes estados
 * para uso em testes unitários.
 * <p>
 * Esta classe fornece métodos estáticos para criar instâncias de Game
 * em estados específicos e conhecidos, facilitando a escrita de testes.
 * </p>
 * 
 * @author Bárbara, Lucas e Paulo
 * @version 1.0
 */
public class GameFixture {
    
    /**
     * Cria um jogo novo com dificuldade fácil e jogador padrão.
     * 
     * @return Game configurado com dificuldade fácil
     */
    public static Game criarJogoFacil() {
        Game game = new Game();
        Jogador jogador = new Jogador("JogadorTeste");
        Dificuldade dificuldade = new DificuldadeFacil();
        game.setJogador(jogador);
        game.iniciarNovoJogo(dificuldade);
        return game;
    }
    
    /**
     * Cria um jogo novo com dificuldade média e jogador padrão.
     * 
     * @return Game configurado com dificuldade média
     */
    public static Game criarJogoMedio() {
        Game game = new Game();
        Jogador jogador = new Jogador("JogadorTeste");
        Dificuldade dificuldade = new DificuldadeMedio();
        game.setJogador(jogador);
        game.iniciarNovoJogo(dificuldade);
        return game;
    }
    
    /**
     * Cria um jogo novo com dificuldade difícil e jogador padrão.
     * 
     * @return Game configurado com dificuldade difícil
     */
    public static Game criarJogoDificil() {
        Game game = new Game();
        Jogador jogador = new Jogador("JogadorTeste");
        Dificuldade dificuldade = new DificuldadeDificil();
        game.setJogador(jogador);
        game.iniciarNovoJogo(dificuldade);
        return game;
    }
    
    /**
     * Cria um jogo novo com jogador e dificuldade personalizados.
     * 
     * @param nomeJogador Nome do jogador
     * @param dificuldade Dificuldade do jogo
     * @return Game configurado
     */
    public static Game criarJogo(String nomeJogador, Dificuldade dificuldade) {
        Game game = new Game();
        Jogador jogador = new Jogador(nomeJogador);
        game.setJogador(jogador);
        game.iniciarNovoJogo(dificuldade);
        return game;
    }
    
    /**
     * Cria um jogo vazio (sem iniciar).
     * 
     * @return Game no estado inicial
     */
    public static Game criarJogoVazio() {
        return new Game();
    }
    
    /**
     * Cria um jogo com um número específico de movimentos realizados.
     * 
     * @param numMovimentos Número de movimentos a realizar
     * @return Game após realizar os movimentos
     */
    public static Game criarJogoComMovimentos(int numMovimentos) {
        Game game = criarJogoFacil();
        // Realiza movimentos válidos (célula central repetidamente)
        for (int i = 0; i < numMovimentos; i++) {
            try {
                game.processarJogada(1, 1);
            } catch (Exception e) {
                // Ignora exceções para simplificar o teste
            }
        }
        return game;
    }
}


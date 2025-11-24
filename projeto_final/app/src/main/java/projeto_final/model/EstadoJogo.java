package projeto_final.model;

/**
 * Enum que representa os possíveis estados do jogo.
 * <p>
 * Este enum é usado pela classe {@code Game} para controlar o estado atual
 * da aplicação e determinar quais ações são permitidas em cada momento.
 * </p>
 * 
 * @author Projeto Final MC322
 * @version 1.0
 * @see projeto_final.controller.Game
 */
public enum EstadoJogo {
    /** Estado inicial, exibindo o menu principal */
    MENU,
    
    /** Estado em que o jogo está em andamento */
    JOGANDO,
    
    /** Estado em que o jogo está pausado */
    PAUSADO,
    
    /** Estado quando o jogador venceu a partida */
    VITORIA,
    
    /** Estado quando o jogador perdeu a partida */
    DERROTA
}

